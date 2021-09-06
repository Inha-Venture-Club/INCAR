package ac.inhaventureclub.incar.fragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.adapter.RatingGiveRecyclerAdapter;
import ac.inhaventureclub.incar.adapter.RatingReceiveRecyclerAdapter;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.RequestObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingsFragment extends Fragment {

    private RecyclerView recyclerViewReceive;
    private RecyclerView recyclerViewGive;
    private RatingReceiveRecyclerAdapter adapter_receive;
    private RatingGiveRecyclerAdapter adapter_give;
    private ArrayList<RequestObject>list_receive = new ArrayList<>();
    private ArrayList<RequestObject>list_give = new ArrayList<>();
    private String result_received;
    private String result_give;

//    private class RatingReceiveView extends AsyncTask {
//        private final String json;
//        private final String address;
//
//        public RatingReceiveView(String json, String address){
//            this.json = json;
//            this.address = address;
//        }
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            result = HttpManager.getData(address); // null: error, !null: ok
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            if(result!= null) {
//                Gson gson = new Gson();
//                ArrayList<RequestObject> ratingList = gson.fromJson(result, new TypeToken<ArrayList<RequestObject>>(){}.getType());
//                if(result == null) {
//                    adapter_receive = new RatingReceiveRecyclerAdapter(getActivity(), ratingList);
//                } else {
//                    //adapter_receive.setList(receivedList);
//                    adapter_receive.notifyDataSetChanged();
//                }
//                recyclerView.setAdapter(adapter_receive);
//            }else{
//                Toast.makeText(getContext(), "null 입니다", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ratings, container, false);

        recyclerViewReceive = view.findViewById(R.id.receivedRatingRecycler);

        recyclerViewReceive.setHasFixedSize(true);
        recyclerViewReceive.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter_receive = new RatingReceiveRecyclerAdapter(getActivity(), list_receive);

        TextView textView = view.findViewById(R.id.rating_text);
        TextView ratingGiveText = view.findViewById(R.id.rating_give);
        TextView ratingReceiveText=view.findViewById(R.id.rating_receive);


        recyclerViewGive = view.findViewById(R.id.giveRatingRecycler);

        recyclerViewGive.setHasFixedSize(true);
        adapter_give = new RatingGiveRecyclerAdapter(getActivity(), list_give);
        recyclerViewGive.setLayoutManager(new LinearLayoutManager(getActivity()));

//        RequestObject requestObject = new RequestObject();
//        requestObject.POSTING_IDX = incar.IS_GUEST;

        new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                recyclerViewGive.setVisibility(View.GONE);
                recyclerViewReceive.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                ratingGiveText.setVisibility(View.GONE);
                ratingReceiveText.setVisibility(View.GONE);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                //result_received = HttpManager.postData("{POSTING_IDX:0}","/requestPostingidx");
                //result_received = HttpManager.postData("{ \"POSTING_IDX\":"+"36"+" }", "/requestWithPostingidx");
                if (incar.IS_GUEST==0){
                    result_received = HttpManager.postData("incar.USER.ID", "/requestAndPostingWithPostingUserid");
                    result_give = HttpManager.postData("incar.USER.ID", "/requestAndPostingWithPostingUserid");// null: error, !null: ok
                }else if(incar.IS_GUEST==1){
                    result_received = HttpManager.postData("{ \"USER_ID\":"+ incar.USER.ID+" }", "/requestAndPostingWithRequestUserid");
                    result_give = HttpManager.postData("{ \"USER_ID\":"+ incar.USER.ID+" }","/requestAndPostingWithRequestUserid");// null: error, !null: ok
                }

                //result_received = HttpManager.getData("/request");

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(result_received != null && result_give != null) {
                    recyclerViewGive.setVisibility(View.VISIBLE);
                    recyclerViewReceive.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    ratingGiveText.setVisibility(View.VISIBLE);
                    ratingReceiveText.setVisibility(View.VISIBLE);
                    try {
                        Gson gson = new Gson();
                        Log.i("result_received", result_received);
                        ArrayList<RequestObject> receivedObjectList = gson.fromJson(result_received, new TypeToken<ArrayList<RequestObject>>(){}.getType());
                        adapter_receive = new RatingReceiveRecyclerAdapter(getActivity(), receivedObjectList);
                        recyclerViewReceive.setAdapter(adapter_receive);

                        ArrayList<RequestObject> giveObjectList = gson.fromJson(result_give, new TypeToken<ArrayList<RequestObject>>(){}.getType());
                        adapter_give = new RatingGiveRecyclerAdapter(getActivity(), giveObjectList);
                        recyclerViewGive.setAdapter(adapter_give);
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getContext(), "받은 평가, 한 평가가 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    if(getContext()!=null) {
                        recyclerViewGive.setVisibility(View.GONE);
                        recyclerViewReceive.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        ratingGiveText.setVisibility(View.GONE);
                        ratingReceiveText.setVisibility(View.GONE);
                    }
                }
            }
        }.execute();



        //new RatingReceiveView("/requestWithUserid").execute();


        return view;
        // Inflate the layout for this fragment
    }

}
