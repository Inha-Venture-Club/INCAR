package ac.inhaventureclub.incar.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.activity.PostActivity;
import ac.inhaventureclub.incar.adapter.PostingManagementMyPostingRecyclerAdapter;
import ac.inhaventureclub.incar.adapter.RatingReceiveRecyclerAdapter;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.databinding.FragmentProfileBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.PostingObject;
import ac.inhaventureclub.incar.object.RequestObject;

public class ProfileFragment extends Fragment {

    private RatingBar ratingBar;

    private RecyclerView recyclerView_posting;
    private RecyclerView recyclerView_rating;
    private PostingManagementMyPostingRecyclerAdapter adapter_posting;
    private RatingReceiveRecyclerAdapter adapter_rating;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostingObject> list_posting = new ArrayList<>();
    private ArrayList<RequestObject> list_rating = new ArrayList<>();

    private FragmentProfileBinding fragmentProfileBinding;

    public String result_posting;
    public String result_rating;
    public String result_user;


    //@SuppressLint("StaticFieldLeak")
    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        if (incar.USER != null){
            fragmentProfileBinding.profileDetailName.setText(incar.USER.NAME+" "+(incar.IS_GUEST == 0? "운전자" : "탑승자"));
            fragmentProfileBinding.profileDetailMessage.setText(incar.USER.INTRODUCTION);
            fragmentProfileBinding.profileDetailGender.setText(incar.USER.GENDER==0? "여":"남");
            //TODO 학과
            //fragmentProfileBinding.profileDetailDepartment.setText(incar.USER.);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            if (incar.USER.BIRTH != null){
                try {
                    Date date = simpleDateFormat.parse(incar.USER.BIRTH);
                    SimpleDateFormat viewFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                    String stringDate = viewFormat.format(date);
                    fragmentProfileBinding.profileDetailBirth.setText(stringDate);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
            switch (incar.USER.JOB_IDX){
                case 1:
                    fragmentProfileBinding.profileDetailJob.setText("학부생");
                case 2:
                    fragmentProfileBinding.profileDetailJob.setText("교수");
                case 3:
                    fragmentProfileBinding.profileDetailJob.setText("교직원");
                case 4:
                    fragmentProfileBinding.profileDetailJob.setText("기타");
            }
        }
        //TODO 로그인 후 지우기
        else {
            fragmentProfileBinding.profileDetailName.setText("홍다솔"+" "+(incar.IS_GUEST == 0? "운전자" : "탑승자"));
            fragmentProfileBinding.profileDetailMessage.setText("안녕하세요 반갑습니다.");
            fragmentProfileBinding.profileDetailJob.setText("학부생");
            fragmentProfileBinding.profileDetailBirth.setText("2000년 02월 01일");
            fragmentProfileBinding.profileDetailGender.setText("여");
            fragmentProfileBinding.profileDetailDepartment.setText("정보통신공학과");

        }

        //fragmentProfileBinding.

        ratingBar = fragmentProfileBinding.profileRatingBar;

        ratingBar.setOnRatingBarChangeListener(new Listener());

        //profile_posting_recycler
        recyclerView_posting = fragmentProfileBinding.profilePostingRecycler;

        recyclerView_posting.setHasFixedSize(true);
        adapter_posting = new PostingManagementMyPostingRecyclerAdapter(getActivity(), list_posting);
        recyclerView_posting.setLayoutManager(new LinearLayoutManager(getActivity()));

        //profile_rating_recycler
        recyclerView_rating  = fragmentProfileBinding.profileRatingRecycler;

        recyclerView_rating.setHasFixedSize(true);
        adapter_rating = new RatingReceiveRecyclerAdapter(getActivity(), list_rating);
        recyclerView_rating.setLayoutManager(new LinearLayoutManager(getActivity()));

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                //TODO "12181837"자리에 incar.USER.ID
                result_posting = HttpManager.postData("{ \"USER_ID\":"+"USER.ID"+" }", "/postingsWithUserId");
                //result_posting = HttpManager.getData("/posting"); // null: error, !null: ok
                if (incar.IS_GUEST==0){
                    result_rating = HttpManager.postData("12181837", "/requestAndPostingWithPostingUserid");
                }else if (incar.IS_GUEST==1){
                    result_rating = HttpManager.postData("12181837", "/requestAndPostingWithRequestUserid");
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(result_posting!=null && result_rating!=null) {
                    Gson gson = new Gson();
                    ArrayList<PostingObject> postingObjectList = gson.fromJson(result_posting, new TypeToken<ArrayList<PostingObject>>(){}.getType());
                    adapter_posting = new PostingManagementMyPostingRecyclerAdapter(getActivity(), postingObjectList);
                    recyclerView_posting.setAdapter(adapter_posting);

                    adapter_posting.setItemClick(new PostingManagementMyPostingRecyclerAdapter.ItemClick() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), PostActivity.class);
                            intent.putExtra("postingDetail", new Gson().toJson(postingObjectList.get(position)));
                            startActivity(intent);
                            Toast.makeText(getContext(), "현재 선택된 아이템 : "+position, Toast.LENGTH_LONG).show();
                        }
                    });

                    ArrayList<RequestObject> requestObjectList = gson.fromJson(result_rating, new TypeToken<ArrayList<RequestObject>>(){}.getType());
                    adapter_rating = new RatingReceiveRecyclerAdapter(getActivity(), requestObjectList);
                    recyclerView_rating.setAdapter(adapter_rating);

                }else{
                    Toast.makeText(getContext(), "null 입니다", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

        ImageView back = fragmentProfileBinding.profileBack;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // Inflate the layout for this fragment
        return fragmentProfileBinding.getRoot();
    }

    class Listener implements RatingBar.OnRatingBarChangeListener{

        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){
            ratingBar.setRating(rating);
        }
    }

}