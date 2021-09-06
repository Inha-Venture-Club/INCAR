package ac.inhaventureclub.incar.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.activity.PostActivity;
import ac.inhaventureclub.incar.adapter.RequestRecyclerAdapter;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.PostingObject;


public class RequestFragment extends Fragment {

    private RecyclerView recyclerView;
    private RequestRecyclerAdapter adapter;
    //private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostingObject> list = new ArrayList<>();

    public String result;
//
//    public RequestFragment() {
//        // Required empty public constructor
//    }

    private FragmentManager fragmentManager;




    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_postingmanagement_myrequests, container, false);



        recyclerView = view.findViewById(R.id.request_recycler);

        recyclerView.setHasFixedSize(true);
        adapter = new RequestRecyclerAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TextView textView = view.findViewById(R.id.request_text);


        Button reviewBtn = (Button)view.findViewById(R.id.request_reviewBtn);

//        reviewBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                //getChildFragmentManager().beginTransaction().replace(R.id.postingmanagement_blank, createRatingFragment).commit();
//            }
//        });


        new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                //TODO user 조인 후 지우기
                result = HttpManager.postData("{ \"POSTING_IDX\":"+ incar.USER.ID+" }", "/requestWithPostingidx");
                //result = HttpManager.postData("{ \"POSTING_IDX\":"+ incar.USER.+" }", "/requestWithPostingidx");
                //result = HttpManager.getData("/posting"); // null: error, !null: ok
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
//                Gson gson = new Gson();
//                ArrayList<PostingObject> postingObjectList = gson.fromJson(result, new TypeToken<ArrayList<PostingObject>>(){}.getType());
//                adapter = new PostingManagementMyPostingRecyclerAdapter(getActivity(), postingObjectList);
//                recyclerView.setAdapter(adapter);
                if(result!= null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    ArrayList<PostingObject> postingObjectList = gson.fromJson(result, new TypeToken<ArrayList<PostingObject>>(){}.getType());
                    adapter = new RequestRecyclerAdapter(getActivity(), postingObjectList);
                    recyclerView.setAdapter(adapter);
                    adapter.setItemClick(new RequestRecyclerAdapter.ItemClick() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), PostActivity.class);
                            intent.putExtra("postingDetail", new Gson().toJson(postingObjectList.get(position)));
                            startActivity(intent);
                            Toast.makeText(getContext(), "현재 선택된 아이템 : "+position, Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    if(getContext()!=null) {
                        recyclerView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);

                    }
                }
            }
        }.execute();
        // Inflate the layout for this fragment
        return view;
    }

}
