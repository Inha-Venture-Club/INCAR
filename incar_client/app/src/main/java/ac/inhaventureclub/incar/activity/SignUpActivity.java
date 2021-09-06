package ac.inhaventureclub.incar.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.databinding.ActivitySignUpBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.Result;
import ac.inhaventureclub.incar.object.UserObject;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding signUpBinding;
    public String spw, spw_chk, id, name, birth, intro;
    public String result;
    public String strColor = "#00BCD4";
    public String strColor2 = "#FFA500";
    UserObject userObject = new UserObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("[TAG/SignUpActivity]", "onCreate start!");

        // [BINDING]
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUpBinding.setActivity(this);

        // [etEmailSignUp/ TextChangedListener] : 인하대학교 Email 형식에 맞는지 확인
        signUpBinding.etEmailSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                // refactoring-dasol
                if (isEmail(s.toString())) {
                    signUpBinding.tvId.setVisibility(View.GONE);
                } else if (isEmail2(s.toString())) {
                    signUpBinding.tvId.setVisibility(View.GONE);
                } else {
                    signUpBinding.tvId.setText("인하대학교 Email 형식에 맞지 않습니다.");
                    signUpBinding.tvId.setVisibility(View.VISIBLE);
                }
            }
        });

        // [etPasswordConfirmSignUp/ TextChangedListener] : 비밀번호 일치 여부 확인
        signUpBinding.etPasswordConfirmSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (signUpBinding.etPasswordConfirmSignUp.getText().toString().equals(signUpBinding.etPasswordSignUp.getText().toString())) {
                    signUpBinding.tvPw.setVisibility(View.GONE);
                } else {
                    signUpBinding.tvPw.setText("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                    signUpBinding.tvPw.setVisibility(View.VISIBLE);
                }
            }
        });

        // 앱 권한 요청
        // refactoring-dasol: requestCode를 static으로 선언하여 관리
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }

        //[btnEmailCertification/ onClickLister] : 학교 이메일 인증버튼을 누른 경우
        // * 이메일 미입력시 → Toast
        // * 잘못된 이메일 포맷 입력시 → Toast
        // * 정상적인 경우 →
        signUpBinding.btnEmailCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[TAG/SignUpActivity/btnEmailCertification]", "onClick : btn is pressed");

                if (signUpBinding.etEmailSignUp.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else if (signUpBinding.tvId.getVisibility() != View.GONE) {
                    Toast.makeText(getApplicationContext(), "이메일 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // (추가한 코드-dasol) id값 EmailCertificationActivity로 넘기기

//                    Intent intent = new Intent(SignUpActivity.this, EmailCertificationActivity.class);
//                    intent.putExtra("id", signUpBinding.etEmailSignUp.getText().toString());
//                    // userObject.ID = signUpBinding.etEmailSignUp.getText().toString().split("@")[0];
//                    Log.i("[TAG/SignUpActivity/btnEmailCertification]", "intent : id = etEmailSignUp: "+signUpBinding.etEmailSignUp.getText().toString()+" (requestCode: 3)");
//                    startActivityForResult(intent, 3);


                    new AsyncTask<Object, Object, Object>() { // DoIn실행하기 전에, 후에 뭘 할거냐를 정해주기위해 param Ob(전),Ob(중),Ob(후)가 필요해. 근데 우린 필요없 지금.
                        @Override
                        protected Object doInBackground(Object[] objects) { //doInBackground:"나는 background에서 동작할거야"
                            Log.i("[TAG/SignUpActivity/btnEmailCertification/doInBackground] ", "start!");

                            // user의 ID가 이미 존재하는지 확인
                            // refactoring-dasol: 엥 근데 이거 userWithIdAndPw가 아니라 userWithId 해야하지 않나?
                            // * 존재하는 경우, result에는 User의 JSON이 담김
                            try{
                                Log.i("[TAG/SignUpActivity/btnEmailCertification/doInBackground] ", "post : address=/email; json="+userObject.ID);
                                // result = HttpManager.postData(new Gson().toJson(userObject.ID), "/userWithIdAndPw"); // null: error, !null: ok
                                result = HttpManager.postData(new Gson().toJson(signUpBinding.etEmailSignUp.getText().toString().split("@")[0]), "/userWithId"); // null: error, !null: ok
                                Log.i("[TAG/SignUpActivity/btnEmailCertification/doInBackground] ", "post : result="+result);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.i("[TAG/SignUpActivity/btnEmailCertification/doInBackground] ", "end!");
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            Log.i("[TAG/SignUpActivity/btnEmailCertification/onPostExecute] ", "start!");

                            super.onPostExecute(o);
                            if(result == null){ // 서버와의 연결이 끊어진 경우
                                Toast.makeText(getApplicationContext(), "서버와 연결이 끊겼습니다.", Toast.LENGTH_SHORT).show();
                            } else if (result.equals(signUpBinding.etEmailSignUp.getText().toString())) { // 이미 존재하는 ID
                                Toast.makeText(getApplicationContext(), "이미 가입되어 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (isEmail(signUpBinding.etEmailSignUp.getText().toString()) || isEmail2(signUpBinding.etEmailSignUp.getText().toString())) { // 옳은 경우
                                Intent intent = new Intent(SignUpActivity.this, SchoolCertificationActivity.class);
                                Log.i("[TAG/SignUpActivity/btnEmailCertification/onPostExecute] ", "Intent: SignUpActivity → SchoolCertificationActivity (requestCode:2)");
                                startActivityForResult(intent, 2);
                                // incar.USER=new Gson().fromJson(result,UserObject.class);
                                // finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "인하대학교 Email 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.i("[TAG/SignUpActivity/btnEmailCertification/onPostExecute] ", "end!");
                        }
                    }.execute(); //실행
                }
                Log.i("[TAG/SignUpActivity/btnEmailCertification/onClick] ", "end!");
            }
        });


        // [btnViewTerms/onClickListener] : 약관보기 버튼 누른 경우
        // Provision을 실행 (?)
        signUpBinding.btnViewTerms.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Log.i("[TAG/SignUpActivity/btnViewTerms/onClickListener] ", "start!");
                Intent intent = new Intent(SignUpActivity.this, Provision.class);
                startActivityForResult(intent, 1);
                Log.i("[TAG/SignUpActivity/btnViewTerms/onClickListener] ", "end!");
            }
        });


        // [btnCancelSignUp/onClickListener] : 회원가입 취소 버튼을 누른 경우
        // SignUpActivity를 종료하고 LoginActivity로 이동
        signUpBinding.btnCancelSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Log.i("[TAG/SignUpActivity/btnCancelSignUp/onClickListener] ", "start!");
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                Log.i("[TAG/SignUpActivity/btnCancelSignUp/onClickListener] ", "end!");
            }
        });

        // [btnCertification] : (최종) 회원가입하기 버튼을 누른 경우
        signUpBinding.btnCertification.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Log.i("[TAG/SignUpActivity/btnCertification/onClick] ", "start!");

                // 화면상의 정보를 SignUpActivity의 public 멤버변수에 저장
                id = signUpBinding.etEmailSignUp.getText().toString();
                name = signUpBinding.etNameSignUp.getText().toString();
                birth = signUpBinding.etBirthSignUp.getText().toString();
                intro = signUpBinding.etIntroductionSignUp.getText().toString();
                spw = signUpBinding.etPasswordSignUp.getText().toString();
                spw_chk = signUpBinding.etPasswordConfirmSignUp.getText().toString();
                Log.i("[TAG/SignUpActivity/btnCertification/onClick] ", "public 멤버변수에 저장 완료");

                // 회원가입을 하기에 괜찮은 상태인지 체크
                if (id.matches("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!signUpBinding.tvId.getText().toString().matches("이메일 인증이 완료되었습니다.")) {
                    Toast.makeText(getApplicationContext(), "이메일 인증을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (spw.matches("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (spw_chk.matches("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (name.matches("")) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!signUpBinding.btnMan.isChecked() && !signUpBinding.btnWoman.isChecked()) {
                    Toast.makeText(getApplicationContext(), "성별을 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (signUpBinding.etBirthSignUp.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "생년원일을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!signUpBinding.btnProfessor.isChecked() && !signUpBinding.btnStudent.isChecked() && !signUpBinding.btnFaculty.isChecked()) {
                    Toast.makeText(getApplicationContext(), "직업을 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!signUpBinding.cbTerms.isChecked()) {
                    Toast.makeText(getApplicationContext(), "약관 확인을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else { // 올바른 경우
                    Log.i("[TAG/SignUpActivity/btnCertification/onClick] ", "모든 정보가 올바름 확인 완료");

                    // SignUpActivity의 userObject에 화면상의 정보를 저장
                    userObject.ID = signUpBinding.etEmailSignUp.getText().toString().split("@")[0];
                    userObject.PW = signUpBinding.etPasswordSignUp.getText().toString();
                    userObject.NAME = signUpBinding.etNameSignUp.getText().toString();
                    userObject.BIRTH = signUpBinding.etBirthSignUp.getText().toString();
                    userObject.INTRODUCTION = signUpBinding.etIntroductionSignUp.getText().toString();
                    if (signUpBinding.btnMan.isChecked()) { userObject.GENDER = 0; }
                    else if (signUpBinding.btnWoman.isChecked()) { userObject.GENDER = 1; }
                    else if (signUpBinding.btnStudent.isChecked()) { userObject.JOB_IDX = 0; }
                    else if (signUpBinding.btnProfessor.isChecked()) { userObject.JOB_IDX = 1; }
                    else if (signUpBinding.btnFaculty.isChecked()) { userObject.JOB_IDX = 2; }
                    Log.i("[TAG/SignUpActivity/btnCertification/onClick] ", "userObject에 저장 완료");

                    new AsyncTask<Object, Object, Object>() { // DoIn실행하기 전에, 후에 뭘 할거냐를 정해주기위해 param Ob(전),Ob(중),Ob(후)가 필요해. 근데 우린 필요없 지금.

                        @Override
                        protected Object doInBackground(Object[] objects) { //doInBackground:"나는 background에서 동작할거야"
                            Log.i("[TAG/SignUpActivity/btnCertification/onClick/doInBackground] ", "start!");
                            Log.i("[TAG/SignUpActivity/btnCertification/onClick/doInBackground]", "post : address=/user; json="+userObject.toString());
                            result = HttpManager.postData(new Gson().toJson(userObject), "/user"); // null: error, !null: ok
                            // result 결과는 Result(true, "ID:"+id+", PW:"+pw) or Result(false, "FAILED")
                            Log.i("[TAG/SignUpActivity/btnCertification/onClick/doInBackground]", "post : result="+result);
                            Log.i("[TAG/SignUpActivity/btnCertification/onClick/doInBackground] ", "end!");
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            Log.i("[TAG/SignUpActivity/btnCertification/onClick/onPostExecute] ", "start!");
                            super.onPostExecute(o);
                            if (result != null) {
                                UserObject userObject1 = new Gson().fromJson(result, UserObject.class);
                                if (userObject1 == null) {
                                    Result result1 = new Gson().fromJson(result, Result.class);
                                    Toast.makeText(getApplicationContext(), result1.comment, Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    incar.USER= userObject1;

                                    Toast.makeText(getApplicationContext(), "인카 회원가입이 되었습니다.", Toast.LENGTH_SHORT).show();
                                    // Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "서버연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }.execute(); //실행
            }
        }
        });
    }

    public static boolean isEmail(String email) {
        boolean returnValue = false;
        String regex = "^[_a-zA-Z0-9-\\.]+@(inha)+\\.(edu)+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isEmail2(String email) {
        boolean returnValue = false;
        String regex = "^[_a-zA-Z0-9-\\.]+@(inha)+\\.(ac)+\\.(kr)+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            returnValue = true;
        }
        return returnValue;
    }

    public void onHide(View view) {
        Log.i("[TAG/SignUpActivity/onHide] ", "start!");
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        Log.i("[TAG/SignUpActivity/onHide] ", "end!");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //requestCode==123:: 호출된거
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("[TAG/SignUpActivity/onRequestPermissionsResult] ", "start!");
        Log.i("[TAG/SignUpActivity/onRequestPermissionsResult] ", "end!");
    }

    // onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("[TAG/SignUpActivity/onActivityResult]","start!");
        Log.i("[TAG/SignUpActivity/onActivityResult]","requestCode="+requestCode+"  resultCode="+resultCode);

        if (requestCode == 1) { // 약관 보기
            Log.i("[TAG/SignUpActivity/onActivityResult] requestCode=1 : ", "start!");
            signUpBinding.cbTerms.setChecked(resultCode == 1);
        } else if (requestCode == 2) { // SignUpActivity → schoolCertificationActivity
            Log.i("[TAG/SignUpActivity/onActivityResult] requestCode=2 : ", "start!");
            if(resultCode==2){
                Log.i("[TAG/SignUpActivity/onActivityResult] resultCode=2 : ", "start!");
                Intent intent = new Intent(SignUpActivity.this, EmailCertificationActivity.class);
                Log.i("[TAG/SignUpActivity/onActivityResult] requestCode=2 : ", "intent : id = etEmailSignUp: "+signUpBinding.etEmailSignUp.getText().toString()+" (requestCode: 3)");
                intent.putExtra("id", signUpBinding.etEmailSignUp.getText().toString());
                startActivityForResult(intent, 3);
            }
        } else if (requestCode == 3) { // SignUpActivity → EmailCertificationActivity
            Log.i("[TAG/SignUpActivity/onActivityResult] requestCode=3 : ", "start!");
            if (resultCode == 3) {
                Log.i("[TAG/SignUpActivity/onActivityResult] resultCode=3 : ", "start!");
                signUpBinding.btnEmailCertification.setClickable(false);
                signUpBinding.etPasswordSignUp.requestFocus();
                signUpBinding.etEmailSignUp.setFocusableInTouchMode(false);
                signUpBinding.etEmailSignUp.setTextColor(Color.parseColor(strColor));
                signUpBinding.tvId.setText("이메일 인증이 완료되었습니다.");
                signUpBinding.tvId.setTextColor(Color.parseColor(strColor2));
                signUpBinding.tvId.setVisibility(View.VISIBLE);
            }
        } else if(resultCode==2){
            Log.i("[TAG/SignUpActivity/onActivityResult] resultCode=2 : ", "start!");
            Intent intent = new Intent(SignUpActivity.this, EmailCertificationActivity.class);
            Log.i("[TAG/SignUpActivity/onActivityResult] requestCode=2 : ", "intent : id = etEmailSignUp: "+signUpBinding.etEmailSignUp.getText().toString()+" (requestCode: 3)");
            intent.putExtra("id", signUpBinding.etEmailSignUp.getText().toString());
            startActivityForResult(intent, 3);
        }

    }
}