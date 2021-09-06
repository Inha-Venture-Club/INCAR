package ac.inhaventureclub.incar.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.databinding.ActivityPostBinding;
import ac.inhaventureclub.incar.fragment.PostingBoardFragment;
import ac.inhaventureclub.incar.object.PostingObject;

public class PostActivity extends AppCompatActivity {

    private ActivityPostBinding postBinding;
    PostingBoardFragment postingboardFragment = new PostingBoardFragment();

    public TextView departureText;
    public TextView destinationText;
    public TextView user_nameText;
    public TextView whenGoText;
    public TextView timeText;
    public TextView messageText;
    public TextView carInfoText;
    public TextView priceText;
    public TextView commentText;

    public String strPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        postBinding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        String postingDetail = getIntent().getStringExtra("postingDetail");

        PostingObject postingObject = new Gson().fromJson(postingDetail, PostingObject.class);


        if(postingObject !=null){
            postBinding.tvPostOriginCity.setText(postingObject.DEPARTURE_DETAIL);
            postBinding.tvPostDestCity.setText(postingObject.ARRIVE_DETAIL);
            postBinding.tvPostInfoWordCont.setText(postingObject.EXPLANATION);
            postBinding.tvPostInfoCapaCont.setText(postingObject.CAR_TYPE);
            if (postingObject.PRICE == -1) {

            } else if (postingObject.PRICE != -1) {
                strPrice = String.valueOf(postingObject.PRICE);
            }
            postBinding.tvPostCostCont.setText(strPrice);

            //postBinding.postNameText.setText(postingObject.);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
            if (postingObject.WHEN_GO != null){
                Date date = null;//시간형태로 가져와줌
                try {
                    date = simpleDateFormat.parse(postingObject.WHEN_GO);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat viewFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
                String stringDate = viewFormat.format(date);
                Date date2 = null;
                try {
                    date2 = simpleDateFormat.parse(postingObject.WHEN_GO);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat viewFormat2 = new SimpleDateFormat("a hh:mm", Locale.KOREA);
                String stringTime = viewFormat2.format(date2);
                Log.d("dhdhdhd", "onBind: "+stringDate);
                postBinding.tvPostDateCont.setText(stringDate);
                postBinding.tvPostDateTime.setText(stringTime);
            }
            if(postingObject !=null) {
                postBinding.postNameText.setText(postingObject.USER_ID);
                //TODO postingobject user 조인 후 수정
                //postBinding.postNameText.setText(postingObject.);
                //postBinding.postPostProfileMessage.setText(incar.USER.INTRODUCTION);
            }
        }else {
            Toast.makeText(getApplicationContext(), "PostingObject가 null 입니다", Toast.LENGTH_SHORT).show();
        }


        

        /* find */
        ImageButton imageButton = findViewById(R.id.post_btn_back);
        /* logo button */
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        ProfileFragment profileFragment = new ProfileFragment();
//
//        LinearLayout postProfileItem = findViewById(R.id.post_profile_item);
//
//        postProfileItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                intent.putExtra("userId", postingObject.USER_ID);
//                startActivity(intent);
//                //startActivityForResult(intent, 300);
//                //getSupportFragmentManager().beginTransaction().replace(R.id.post_activity_layout, profileFragment).commit();
//            }
//        });

    }

}

