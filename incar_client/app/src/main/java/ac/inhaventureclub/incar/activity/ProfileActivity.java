package ac.inhaventureclub.incar.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.adapter.PostingManagementMyPostingRecyclerAdapter;
import ac.inhaventureclub.incar.adapter.RatingReceiveRecyclerAdapter;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.databinding.ActivityProfileBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.PostingObject;
import ac.inhaventureclub.incar.object.RequestObject;

public class ProfileActivity extends AppCompatActivity {
    private RatingBar ratingBar;

    private RecyclerView recyclerView_posting;
    private RecyclerView recyclerView_rating;
    private PostingManagementMyPostingRecyclerAdapter adapter_posting;
    private RatingReceiveRecyclerAdapter adapter_rating;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostingObject> list_posting = new ArrayList<>();
    private ArrayList<RequestObject> list_rating = new ArrayList<>();

    private ActivityProfileBinding activityProfileBinding;

    public String result_user;

    public String result_posting;
    public String result_rating;
    public String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        userId = getIntent().getStringExtra("userId");

        //profile_posting_recycler
        recyclerView_posting = activityProfileBinding.activityProfilePostingRecycler;

        recyclerView_posting.setHasFixedSize(true);
        adapter_posting = new PostingManagementMyPostingRecyclerAdapter(getApplicationContext(), list_posting);
        recyclerView_posting.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //profile_rating_recycler
        recyclerView_rating  = activityProfileBinding.activityProfileRatingRecycler;

        recyclerView_rating.setHasFixedSize(true);
        adapter_rating = new RatingReceiveRecyclerAdapter(getApplicationContext(), list_rating);
        recyclerView_rating.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    //result_user = HttpManager.postData("{ \"ID\":" + userId + " }", "/userWithId");
                    //result_posting = HttpManager.postData("{ \"ID\":" + userId + " }", "/requestAndPostingWithPostingUserid");
                    //TODO "12181837"자리에 incar.USER.ID
                    result_posting = HttpManager.postData("{ \"USER_ID\":"+"12181837"+" }", "/postingsWithUserId");
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
                        adapter_posting = new PostingManagementMyPostingRecyclerAdapter(getApplicationContext(), postingObjectList);
                        recyclerView_posting.setAdapter(adapter_posting);

                        adapter_posting.setItemClick(new PostingManagementMyPostingRecyclerAdapter.ItemClick() {
                            @Override
                            public void onClick(View view, int position) {
                                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                                intent.putExtra("postingDetail", new Gson().toJson(postingObjectList.get(position)));
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "현재 선택된 아이템 : "+position, Toast.LENGTH_LONG).show();
                            }
                        });

                        ArrayList<RequestObject> requestObjectList = gson.fromJson(result_rating, new TypeToken<ArrayList<RequestObject>>(){}.getType());
                        adapter_rating = new RatingReceiveRecyclerAdapter(getApplicationContext(), requestObjectList);
                        recyclerView_rating.setAdapter(adapter_rating);

                    }else{
                        Toast.makeText(getApplicationContext(), "null 입니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        ImageView back = findViewById(R.id.activity_profile_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
