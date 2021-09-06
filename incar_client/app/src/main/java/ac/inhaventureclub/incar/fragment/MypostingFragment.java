package ac.inhaventureclub.incar.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.activity.PostActivity;
import ac.inhaventureclub.incar.adapter.PostingManagementMyPostingRecyclerAdapter;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.PostingObject;

public class MypostingFragment extends Fragment{

    private RecyclerView recyclerView;
    private PostingManagementMyPostingRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostingObject> list = new ArrayList<>();

    public String result;
    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @SuppressLint("StaticFieldLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_postingmanagement_mypostings, container, false);

        recyclerView = view.findViewById(R.id.posting_recycler);
        TextView textView = view.findViewById(R.id.posting_text);

        recyclerView.setHasFixedSize(true);
        //adapter = new PostingManagementMyPostingRecyclerAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        PostingObject postingObject = new PostingObject();
//        postingObject.USER_ID = incar.USER.ID;

        new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);

            }

            @Override
            protected Object doInBackground(Object[] objects) {
                //result = HttpManager.getData("/posting");
                //result = HttpManager.postData("{ \"IS_GUEST\":"+incar.IS_GUEST+" }","/posting"); // null: error, !null: ok
                //TODO "12181837"자리에 incar.USER.ID
                result = HttpManager.postData("{ \"USER_ID\":"+ incar.USER.ID+" }", "/postingsWithUserId");
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
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
                    Date date = new Date();
                    ArrayList<PostingObject> postingClone = (ArrayList<PostingObject>) postingObjectList.clone();
                    for (PostingObject p: postingClone) {
                        try {
                            if (date.after(simpleDateFormat.parse(p.WHEN_GO))) {
                                postingObjectList.remove(p);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter = new PostingManagementMyPostingRecyclerAdapter(getActivity(), postingObjectList);
                    recyclerView.setAdapter(adapter);
                    adapter.setItemClick(new PostingManagementMyPostingRecyclerAdapter.ItemClick() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), PostActivity.class);
                            intent.putExtra("postingDetail", new Gson().toJson(postingObjectList.get(position)));
                            startActivity(intent);
                            Toast.makeText(getContext(), "현재 선택된 아이템 : "+position, Toast.LENGTH_LONG).show();
                        }
                    });

                }else{
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }

            }
        }.execute();


        return view;
    }
}
