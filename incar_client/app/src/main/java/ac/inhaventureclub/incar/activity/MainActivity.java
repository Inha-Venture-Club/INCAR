package ac.inhaventureclub.incar.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ac.inhaventureclub.incar.NoticeFragment;
import ac.inhaventureclub.incar.Programmer_IntroductionFragment;
import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.SettingFragment;
import ac.inhaventureclub.incar.activity.register.CarPoolRegisterActivity;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.fragment.AddressFragment;
import ac.inhaventureclub.incar.fragment.CreateRatingFragment;
import ac.inhaventureclub.incar.fragment.PostedWritingFragment;
import ac.inhaventureclub.incar.fragment.PostingBoardFragment;
import ac.inhaventureclub.incar.fragment.PostingManagementFragment;
import ac.inhaventureclub.incar.fragment.ProfileFragment;
import ac.inhaventureclub.incar.fragment.RequestFragment;
import ac.inhaventureclub.incar.object.PostingObject;
import ac.inhaventureclub.incar.object.RequestObject;

import static ac.inhaventureclub.incar.fragment.AddressFragment.ARRIVE_OK;
import static ac.inhaventureclub.incar.fragment.AddressFragment.DEPART_OK;
import static ac.inhaventureclub.incar.fragment.PostingBoardFragment.ARRIVE_STATE;
import static ac.inhaventureclub.incar.fragment.PostingBoardFragment.DEPART_STATE;
import static ac.inhaventureclub.incar.fragment.PostingBoardFragment.REQ_ARRIVE;
import static ac.inhaventureclub.incar.fragment.PostingBoardFragment.REQ_DEPART;


public class MainActivity extends PopUpUtilActivity implements NavigationView.OnNavigationItemSelectedListener, AddressFragment.OnAddressFragmentListener, PostingBoardFragment.OnPostingBoardListener{

    private int counter;


    public RequestObject requestObject;
    public ArrayList<Integer> addrSelectedList = new ArrayList<Integer>();
    public int state;

    public void setState(int state){ this.state = state;}
    public int getState(){ return state;}
    public void setAddrSelectedList(ArrayList<Integer> addrSelectedList){ this.addrSelectedList = addrSelectedList;}
    public ArrayList<Integer> getAddrSelectedList(){ return addrSelectedList;}

    public PostingObject postingObject;

    /* Activity -> Fragment */
//    public int getIsAddrDepartSelected(){return this.isAddrDepartSelected;}
//    public int getIsAddrArriveSelected(){return this.isAddrArriveSelected;}

    /* Fragment */
    PostingBoardFragment postingboardFragment = new PostingBoardFragment();
    PostingManagementFragment postingmanagementFragment = new PostingManagementFragment();
    NoticeFragment noticeFragment = new NoticeFragment();
    SettingFragment settingFragment = new SettingFragment();
    Programmer_IntroductionFragment programmer_introductionFragment = new Programmer_IntroductionFragment();


    PostedWritingFragment postedWritingFragment = new PostedWritingFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    CreateRatingFragment createRatingFragment = new CreateRatingFragment();
    RequestFragment requestFragment = new RequestFragment();
    /* others */
    Toolbar toolbar;

    //private TextView profileMore ;





    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.i("MainActivity_Oncreate","main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.include_contentmain,postingboardFragment).commit();

        Log.i("first popup", "onCreate: ");

        try {
            Intent intent = getIntent();

            if (intent.getStringExtra("login").equals("login")) {

                popUpMakeWithoutIntentWithVertical( "코로나로 인해 개발이 잠정적 중단되었습니다."
                        +" 아직 구현되지 않은 기능들이 많습니다."
                        +" 혹시 개선의 여지가 있거나 좋은 아이디어가 있으시다면 구글 플레이 스토어에 댓글 써주시면 감사드리겠습니다.");
            }

        } catch (Exception e) {

        }


//        Log.d("DeviceToken", FirebaseInstanceId.getInstance().getToken().toString());
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>(){
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task){
//                        if(!task.isSuccessful()){
//                            Log.w("FCM Log", "getInstanceId failed", task.getException());
//                        }
//                    }
//                });

//        Toast.makeText(getApplicationContext(), Integer.toString(isAddrDepartSelected), Toast.LENGTH_LONG).show();

//        Bundle extras = getIntent().getExtras();
//        if(extras!=null){
//            isAddrDepartSelected = extras.getInt("isAddrDepartSelected");
//            isAddrArriveSelected = extras.getInt("isAddrArriveSelected");
//            if(isAddrDepartSelected==1){
//                if(extras.getIntegerArrayList("addrDepartSelectedList")!=null) addrDepartSelectedList = extras.getIntegerArrayList("addrDepartSelectedList");
//            }
//            if(isAddrArriveSelected==1){
//                if(extras.getIntegerArrayList("addrArriveSelectedList")!=null) addrArriveSelectedList = extras.getIntegerArrayList("addrArriveSelectedList");
//            }
//        }

        /* ActionBar */
        toolbar = findViewById(R.id.toolbar_appbarmain_hamburger);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer ,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nv_activitymain);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);

        Button userChangingBtn = headerview.findViewById(R.id.user_changing);

        TextView profileName = headerview.findViewById(R.id.nav_profile_name);
        TextView profileBirth = headerview.findViewById(R.id.nav_profile_birth);
        TextView profileJob =headerview.findViewById(R.id.nav_profile_job);

        //TODO 사용자 전환
        userChangingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incar.IS_GUEST = (incar.IS_GUEST ==0)? 1:0;
                SharedPreferences sharedPreferences = getSharedPreferences("is_guest", MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putInt( "is_guest" , incar.IS_GUEST ); // value : 저장될 값, //1 == 손님, 0 == 운전자
                ed.commit();

                Toast.makeText(getApplicationContext(), "사용자가 변경되었습니다. 현재 상태 : "+(incar.IS_GUEST == 0? "운전자" : "탑승자"), Toast.LENGTH_SHORT).show();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);


                postingmanagementFragment.textChange();

                if (incar.USER != null && incar.USER.NAME!=null) {
                    profileName.setText(incar.USER.NAME + " " + (incar.IS_GUEST == 0 ? "운전자" : "탑승자"));
                }else {
                    profileName.setText("userName" + " " + (incar.IS_GUEST == 0 ? "운전자" : "탑승자"));
                }
            }
        });

        if (incar.USER != null && incar.USER.NAME!=null){
            profileName.setText(incar.USER.NAME+" "+(incar.IS_GUEST == 0? "운전자" : "탑승자"));
            //TODO 학과
            //fragmentProfileBinding.profileDetailDepartment.setText(incar.USER.);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            if (incar.USER.BIRTH != null){
                try {
                    Date date = simpleDateFormat.parse(incar.USER.BIRTH);
                    SimpleDateFormat viewFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                    String stringDate = viewFormat.format(date);
                    profileBirth.setText(stringDate);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
            switch (incar.USER.JOB_IDX){
                case 1:
                    profileJob.setText("학부생");
                case 2:
                    profileJob.setText("교수");
                case 3:
                    profileJob.setText("교직원");
                case 4:
                    profileJob.setText("기타");
            }
        }
        //TODO 로그인 후 지우기
        else {
            profileName.setText("홍다솔"+" "+(incar.IS_GUEST == 0? "운전자" : "탑승자"));
            profileJob.setText("학부생");
            profileBirth.setText("2000년 02월 01일");

        }



        /* find */
        Button logoButton = findViewById(R.id.ib_appbarmain_mainlogo);


        /* logo button */
        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.include_contentmain, postingboardFragment).commit();
            }
        });

        Button logoButton2 = findViewById(R.id.notice_screen);
        TextView textview = findViewById(R.id.tv_notice);
        logoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textview.setVisibility(View.GONE);
                //Intent intent = new Intent(MainActivity.this, PostActivity.class);

                popUpMakeWithoutIntentWithVertical("아직 알람 기능이 구현되지 않았습니다.");
            }
        });



        TextView profileMore = (TextView) headerview.findViewById(R.id.profile_more);



        //TODO 프로필 더보기 서비스 준비중입니다.
        profileMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                /* 밍경이꺼 */
////                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
////                intent.putExtra("profile", new Gson().toJson(postingObject));
////                startActivityForResult(intent, 310);
//
//                String userid = "12181837";
//
//                /* 다솔꺼 */
//                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                intent.putExtra("userId", userid);
//                //intent.putExtra("userId", incar.USER.ID);
//                startActivity(intent);
//                //getSupportFragmentManager().beginTransaction().replace(R.id.include_contentmain, profileFragment).commit();
//
//                //getSupportFragmentManager().beginTransaction().replace(R.id.include_contentmain, profileFragment).commit();
//
//                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                drawer.closeDrawer(GravityCompat.START);

                popUpMakeWithoutIntentWithVertical("아직 더보기 기능이 구현되지 않았습니다.");
            }
        });




        Log.i("MainActivity", "main activity finish");
    }

//    public void SetListener(){
//
//        //View.OnClickListener Listener = new View.OnClickListener()
//        profileMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.include_contentmain, profileFragment).commit();
//            }
//        });
//    }



    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;

        if (position == 0){
//            curFragment = new PostingManagementFragment();
//            toolbar.setTitle("첫번쨰 화면");
            Intent intent = new Intent(getApplicationContext(), CarPoolRegisterActivity.class);
            startActivity(intent);
            Log.i("MainActivity: ", intent.toString());
            return;
        } else if(position ==1){
            curFragment =  postingmanagementFragment;
        } else if(position ==2){
            curFragment = new NoticeFragment();
        } else if(position ==3){
            curFragment = new SettingFragment();
        }else if(position ==4){
            curFragment = new Programmer_IntroductionFragment();
        }else if(position ==5){
            //탈퇴하기 팝업
            popUpMakeWithoutIntentWithVertical(" 아직 탈퇴하기 기능이 구현되지 않았습니다.");
        }else if(position ==6){
            //로그아웃 팝업
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        if (curFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.include_contentmain, curFragment).commit();
        }
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if(id == R.id.item_drawer_newpost){
            Toast.makeText(this,"글등록",Toast.LENGTH_LONG).show();
            onFragmentSelected(0,null);
        }else if (id==R.id.item_drawer_postmanagement){
            Toast.makeText(this,"글관리",Toast.LENGTH_LONG).show();
            onFragmentSelected(1,null);
        }else if (id==R.id.item_drawer_notice){
            Toast.makeText(this,"공지",Toast.LENGTH_LONG).show();
            onFragmentSelected(2,null);
        }else if (id == R.id.item_drawer_settings){
            Toast.makeText(this,"설정",Toast.LENGTH_LONG).show();
            onFragmentSelected(3,null);
        }else if (id == R.id.item_drawer_devintro){
            Toast.makeText(this,"개발자소개",Toast.LENGTH_LONG).show();
            onFragmentSelected(4,null);
        }else if(id == R.id.item_drawer_withdrawal){
            Toast.makeText(this,"탈퇴하는 팝업 띄우기",Toast.LENGTH_LONG).show();
            onFragmentSelected(5,null);
        } else if(id == R.id.item_drawer_logout){
            Toast.makeText(this,"로그아웃하는 팝업 띄우기",Toast.LENGTH_LONG).show();
            onFragmentSelected(6,null);
            //이거 뭐지? onFragmentSelected(8,null);
//        }else if(id == R.id.profile_more){
//            Toast.makeText(this, "프로필", Toast.LENGTH_LONG).show();
//            onFragmentSelected(3, null);
        }
//        else if(id == R.id.item_drawer_newpost){
//            Toast.makeText(this,"등록하기",Toast.LENGTH_LONG).show();
//            onFragmentSelected(7, null);
//
//        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_contentmain, fragment).commit();
    }

    /* AddressFragment로 부터 받은 data들을 PostingBoardFragment에 전송 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //요청하기 페이지에서 연결해야하는데 민경이한테 최신 파일이 안받아져서 아직 구현못함.
//        if(requestCode == 2){
//            TextView textview = findViewById(R.id.tv_notice);
//            counter = 0;
//            textview.setText(String.valueOf(++counter));
//            textview.setVisibility(View.VISIBLE);
//        }
        if(requestCode == REQ_DEPART){
            if(resultCode == DEPART_OK){
                if(data.getIntegerArrayListExtra("addrSelectedList")!=null){
                    addrSelectedList = data.getIntegerArrayListExtra("addrSelectedList");
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", DEPART_STATE);
                    bundle.putIntegerArrayList("addrSelectedList", addrSelectedList);
                    postingboardFragment.setArguments(bundle);
                    postingboardFragment.receiveData(DEPART_STATE,addrSelectedList);
                    postingboardFragment.setAddrKeep(addrSelectedList,DEPART_STATE);
                }
            }
        }
        if(requestCode == REQ_ARRIVE){
            if(resultCode == ARRIVE_OK){
                if(data.getIntegerArrayListExtra("addrSelectedList")!=null){
                    addrSelectedList = data.getIntegerArrayListExtra("addrSelectedList");
                    Bundle bundle = new Bundle();
                    bundle.putInt("state", ARRIVE_STATE);
                    bundle.putIntegerArrayList("addrSelectedList", addrSelectedList);
                    postingboardFragment.setArguments(bundle);
                    postingboardFragment.receiveData(ARRIVE_STATE,addrSelectedList);
                    postingboardFragment.setAddrKeep(addrSelectedList,ARRIVE_STATE);
                }
            }
        }

    }

}

