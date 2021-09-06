package ac.inhaventureclub.incar.activity.register;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.activity.PopUpUtilActivity;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.databinding.ActivityCarPoolRegisterBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.IncarMapAddress;
import ac.inhaventureclub.incar.object.PostingObject;

public class CarPoolRegisterActivity extends PopUpUtilActivity implements CalendarDialog.OnCalendarDialogListener {


    // CalendarDialog.OnCalendarDialogListener 구현 부분
    public ArrayList<String> receivedStrWhenGoDateList = new ArrayList<>() ;
    public void setReceivedStrWhenGoDateList(ArrayList<String> _selectedStrWhenGoDateList){
        receivedStrWhenGoDateList = _selectedStrWhenGoDateList;
    };
    public ArrayList<String> getReceivedStrWhenGoDateList(){
        return this.receivedStrWhenGoDateList;
    }
    public void renewCalenderItem(){
        ArrayList<String> whenGoDateList = this.getReceivedStrWhenGoDateList();
        if(whenGoDateList!=null){
            for(int i=0; i<whenGoDateList.size(); i++){
                this.addTextViewToLayout(carPoolRegisterBinding.layoutDate,whenGoDateList.get(i));
                Log.i("renewCalenderItem: ",Integer.toString(i)+"번째: "+whenGoDateList.get(i));
            }
        }
    }


    private ActivityCarPoolRegisterBinding carPoolRegisterBinding;

    public int postTypeCheckNum = 0;
    public String strDepartAdress;
    public int departAddressCode;
    public String strDepartDetailAdress;
    public String strDepartX;
    public String strDepartY;
    public String strArriveAdress;
    public int arriveAddressCode;
    public String strArriveDetailAdress;
    public String strArriveX;
    public String strArriveY;
    public String strWhenGoDate;
    public String strWhenGoTime;
    public String strAmPm;
    public String strHour;
    public String strMinute;
    public String strPrice;
    public int price = -1;
    public String strCarType;
    public String strExplanation;
    public String strNow;

    public ArrayList<PostingObject> postingObjectList = new ArrayList<>();
    public String result;
    public IncarMapAddress incarMapAddress = new IncarMapAddress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pool_register);

        carPoolRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_car_pool_register);
        carPoolRegisterBinding.setActivity(this);

        carPoolRegisterBinding.tvCalender.setClickable(true);

        carPoolRegisterBinding.imageViewLeftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        /* Dasol */
        carPoolRegisterBinding.imageViewTypecheckbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (postTypeCheckNum == 0) {

                    carPoolRegisterBinding.imageViewTypecheckbox1.setVisibility(View.INVISIBLE);
                    carPoolRegisterBinding.imageViewTypecheckbox2.setVisibility(View.VISIBLE);

                    postTypeCheckNum = 1;
                }
            }
        });

        carPoolRegisterBinding.imageViewTypecheckbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (postTypeCheckNum == 1) {

                    carPoolRegisterBinding.imageViewTypecheckbox1.setVisibility(View.VISIBLE);
                    carPoolRegisterBinding.imageViewTypecheckbox2.setVisibility(View.INVISIBLE);

                    postTypeCheckNum = 0;
                }
            }
        });

        carPoolRegisterBinding.tvDepartureDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarPoolRegisterActivity.this, CacaoMapActivity.class);
                intent.putExtra("where","출발지로 선택하기");
                startActivityForResult(intent, 100);
            }
        });

        carPoolRegisterBinding.tvArriveDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarPoolRegisterActivity.this, CacaoMapActivity.class);
                intent.putExtra("where","도착지로 선택하기");
                startActivityForResult(intent, 100);
            }

        });

        carPoolRegisterBinding.tvCalender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    CalendarDialog calendarDialog = new CalendarDialog();
                    calendarDialog.show(getSupportFragmentManager(),null); // tag는 굳이..

                    popUpMakeWithoutIntentWithVertical("아직 날짜 표시 부분이 미완성입니다." +
                            " 선택하신 날짜는 제대로 반영됩니다. ");
                }
                return false;
            }
        });

//        //carPoolRegisterBinding.tvCalender.setOnClickListener(new View.OnClickListener() {
//        carPoolRegisterBinding.tvCalender.setOnTouchListener(new View.OnTouchListener() {
//
//            //public void onClick(View v) {
//            @Override
//            public void onTouch(View v) {
//
//                CalendarDialog calendarDialog = new CalendarDialog();
//                calendarDialog.show(getSupportFragmentManager(),null); // tag는 굳이..
//
//                /* onDismiss가 화면을 닫아버림... -> 캘린더가 안 뜸.
//                calendarDialog.onDismiss(new DialogInterface() {
//                    @Override
//                    public void cancel() {
//
//                    }
//
//                    @Override
//                    public void dismiss() { // dialog가 없어질 때 여기로 event가 떨어진다.
//                        strWhenGoDateList = calendarDialog.selectedStrWhenGoDateList;
//                    }
//                });
//
//                */
//            }
//        });

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }

        // ---------------------------------------------------- 등록 누른 이후 ----------------------------------------------------

        carPoolRegisterBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setEnabled(false); // 등록버튼이 v로 옴. 등록 버튼이 클릭이 안됨. 그니까 한번밖에 못눌러.

                // ---------------------------------------------------- 등록 눌렀을때 만약 필수로 입력해야 할 값이 없으면 팝업 띄우고 다시 액티비티 띄우는 코드 추가하기 ----------------------------------------------------


                strDepartDetailAdress = carPoolRegisterBinding.tvDepartureDetail.getText().toString();
                strArriveDetailAdress = carPoolRegisterBinding.tvArriveDetail.getText().toString();

                strPrice = carPoolRegisterBinding.etPrice.getText().toString();
                if (!strPrice.isEmpty()) {
                    price = Integer.parseInt(strPrice);
                }
                strCarType = carPoolRegisterBinding.etCarType.getText().toString();
                strExplanation = carPoolRegisterBinding.etExplanation.getText().toString();
                // 0000년 00월00일 00시00분
                strAmPm = carPoolRegisterBinding.spinnerAmpm.getSelectedItem().toString();
                strHour = carPoolRegisterBinding.spinnerHour.getSelectedItem().toString();
                strHour = strHour.replace("시", "");
                strMinute = carPoolRegisterBinding.spinnerMinute.getSelectedItem().toString();
                strMinute = strMinute.replace("분", "");
                // --------------------------------------------- 지도 구현 이후에 밑에 주석 풀고 출발지/도착지 필수로 만들기. ---------------------------------------------

                if (strDepartDetailAdress.isEmpty()) {
                    popUpMakeWithoutIntent("출발지 선택은 필수입니다.");
                } else if (strArriveDetailAdress.isEmpty()) {
                    popUpMakeWithoutIntent("도착지 선택은 필수입니다.");
                } else if (receivedStrWhenGoDateList.size() == 0) {
                      popUpMakeWithoutIntent("출발 날짜 선택은 필수입니다.");
                  } else if (strPrice.length() > 11) {
                      popUpMakeWithoutIntent("금액은 최대 11자입니다.");
                  } else if (strCarType.length() > 10) {
                      popUpMakeWithoutIntent("차종은 최대 10자입니다.");
                  } else if (strExplanation.length() > 50) {
                      popUpMakeWithoutIntent("추가 내용은 최대 50자입니다.");
                  } else {
                      insertDB(v);
                  }

                  v.setEnabled(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // CacaoMapActivity에서 출발지 혹은 도착지 선택 버튼을 클릭해서 넘어온 경우
        if (requestCode == 100) {
            if (resultCode == 011) {

                /*
                data. 해서 객체 꺼내고, 밑에 변수에 값 담기
                strDepartAdress = ;
                strDepartDetailAdress = ;
                strDepartX = ;
                                strDepartY = ;
                strArriveAdress = ;
                strArriveDetailAdress = ;
                strArriveX = ;
                strArriveY = ;
                 */

                incarMapAddress = new Gson().fromJson(data.getStringExtra("incarMapAddress"), IncarMapAddress.class);

                String checkStr = "";
                String departure;
                String arrive;
                departure = data.getStringExtra("departure");
                arrive = data.getStringExtra("arrive");

                if (departure != null) {
                    checkStr = departure;
                } else if (arrive != null) {
                    checkStr = arrive;
                }

                if (checkStr.equals("departure")) {
                    //strDepartAdress =
                    strDepartDetailAdress = incarMapAddress.place_name;
                    strDepartX = incarMapAddress.x;
                    strDepartY = incarMapAddress.y;
                    carPoolRegisterBinding.tvDepartureDetail.setText(strDepartDetailAdress);

                } else if (checkStr.equals("arrive")) {
                    //strArriveAdress =
                    strArriveDetailAdress = incarMapAddress.place_name;
                    strArriveX = incarMapAddress.x;
                    strArriveY = incarMapAddress.y;
                    carPoolRegisterBinding.tvArriveDetail.setText(strArriveDetailAdress);
                }

                // incarMapAddress에서 subString써서 strArress로 가공해서 구 정보만 넣어주고
                // strAddress에서 구 정보만 담긴거를 int로 전환해주어야함!

            }
        }

    }

    public void insertDB(View v) {

        if (strAmPm.equals("오전")) {

            int intHour = Integer.parseInt(strHour);

            if (intHour == 12) {
                strHour = "00";
            }

            strWhenGoTime = strHour + strMinute;

        } else if (strAmPm.equals("오후")) {

            int intHour = Integer.parseInt(strHour);

            if (intHour != 12) {
                intHour += 12;
                strHour = Integer.toString(intHour);
            }

            strWhenGoTime = strHour + strMinute;

        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        strNow = simpleDateFormat.format(date);

        incar incar = (ac.inhaventureclub.incar.application.incar)getApplicationContext();

        for (int i =0; i<receivedStrWhenGoDateList.size(); i++) {

            PostingObject postingObject = new PostingObject();

            // 타세요/태워주세요 정보 저장
            if (postTypeCheckNum == 0) {
                postingObject.IS_GUEST = 0;
            } else if (postTypeCheckNum == 1) {
                postingObject.IS_GUEST = 1;
            }

            strWhenGoDate = receivedStrWhenGoDateList.get(i);
            postingObject.WHEN_GO = strWhenGoDate + strWhenGoTime;
            postingObject.PRICE = price; // price default값은 -1임.
            postingObject.CAR_TYPE = strCarType;
            postingObject.EXPLANATION = strExplanation;
            postingObject.DEPARTURE_IDX = departAddressCode;
            postingObject.DEPARTURE_DETAIL = strDepartDetailAdress;
            postingObject.DEPARTURE_X = strDepartX;
            postingObject.DEPARTURE_Y = strDepartY;
            postingObject.ARRIVE_IDX = arriveAddressCode;
            postingObject.ARRIVE_DETAIL = strArriveDetailAdress;
            postingObject.ARRIVE_X = strArriveX;
            postingObject.ARRIVE_Y = strArriveY;
            if (incar.USER.ID != null) {
                postingObject.USER_ID = incar.USER.ID;
            }
            postingObject.REG_TIME = strNow;

            postingObjectList.add(postingObject);
        }

        //Log.d("JSON Test", postingObjectList.toString());

        new  AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                result = HttpManager.postData(new Gson().toJson(postingObjectList), "/postingServices"); // null: error, !null: ok
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                v.setEnabled(true);
                // 제대로 서버랑 통신 완료되고 게시글 작성되면, 팝업 띄우고,
                if (result != null ) {
                    // ---------------- 여기 아래에도 body에 객체 비었는지, 안 비었는지 확인해야함 ----------------
                    popUpMakeWithoutIntentWithFinish("등록 완료!");
                    //finish(); 이러면 팝업 안보이고 꺼져버림.
                }
                else if (result == null){
                    Toast.makeText(getApplicationContext(), "등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
                }

            }
        }.execute();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //requestCode==123:: 호출된거
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* Dasol */

    private void addTextViewToLayout(LinearLayout linearLayout, String data){
        TextView textView = new TextView(this);
        textView.setBackground(getResources().getDrawable(R.drawable.shape_postingboard_addressitem));
        textView.setText(data);
        textView.setTextSize(15);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setPadding(10,5,10,5);
        LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewParam.setMargins(10,5,10,5);
        linearLayout.addView(textView,textViewParam);
    }



    /* ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆ */
    /*
    주원오빠 해야 할 일
            1. 캘린더를 다시 누르면 이전의 값이 저장은 됨. 근데 캘린더에는 반영이 안됨
               ==> 내가 만든 postingboard부분 참고해서 데이터 이전의 값 calender에 선택되도록 반영되는거 구현해야 함
            2. 또한 기존에 선택되었던 날짜를 다시 누르면 중복으로 추가가 되는 문제 발생함
               ==> 기존에 선택되었던 날짜는 재선택시 ArrayList에서 remove해야 함
            3. 캘린더에 horizontalScrollView로 바꿨는데, 드래그를 할 때 다시 캘린더로 들어가는 문제 발생
               ==> 이건 나도 해결하지 못한 부분이라 오빠가 찾아주면 좋을듯....ㅎ
               ==> 일단 문제 원인은:
                    1) HorizontalScrollView는 onClick이 안됨
                        clickable = true로 해도 안됨
                    2) 그래서 어쩔 수 없이 onTouch를 사용했음
                    3) 그랬더니 calender dialog가 엄청 여러개가 떴음 (누르는 시간이 5초면 더 많이 뜨고 이런식..)
                    4) 그래서 ACTION_UP일 때만 실행되도록 했음 (131번 line)
                    5) 그랬더니 드래그할 때는 ACTION_DRAG로 인식돼서 값들이 잘 보이지만 드래그를 놓는 순간 dialog가 뜨게 됨

    홧팅!
     */
    /* ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆ */


}