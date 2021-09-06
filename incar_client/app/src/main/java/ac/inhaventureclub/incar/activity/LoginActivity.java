package ac.inhaventureclub.incar.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.databinding.ActivityLoginBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.Result;
import ac.inhaventureclub.incar.object.UserObject;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;
    public String id = "aaaaa";
    public String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.setActivity(this);


        loginBinding.etId.getText();//써진 data를 직접 가져오는 역할
        loginBinding.etPassword.setText("");
        //etID.getText().toString();//하면 string이라는 형태로 받아줘.

        // class 내부에 있는
        loginBinding.etPassword.setText("");
        loginBinding.etPassword.getText();
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }


        loginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserObject userObject = new UserObject();
                if (loginBinding.etId.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (loginBinding.etPassword.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                userObject.ID = loginBinding.etId.getText().toString();
                userObject.PW = loginBinding.etPassword.getText().toString();

                new AsyncTask<Object, Object, Object>() { // DoIn실행하기 전에, 후에 뭘 할거냐를 정해주기위해 param Ob(전),Ob(중),Ob(후)가 필요해. 근데 우린 필요없 지금.

                    @Override
                    protected Object doInBackground(Object[] objects) { //doInBackground:"나는 background에서 동작할거야"
                        result = HttpManager.postData(new Gson().toJson(userObject), "/userWithIdAndPw"); // null: error, !null: ok
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        if (result != null) {
                            UserObject userObject1 = new Gson().fromJson(result, UserObject.class);
                            if (userObject1.ID == null) {
                                    Result result1 = new Gson().fromJson(result, Result.class);
                                    Toast.makeText(getApplicationContext(), result1.comment, Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                incar.USER=userObject1;
                                finish();
                            }
//                            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
//                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "서버연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();
                            return;
//                            Intent intent = new Intent(LoginActivity.this, Provision.class);
//                            startActivity(intent);
                        }
                    }
                }.execute(); //실행


                Log.i("TAG", "ID: " + loginBinding.etId.getText() + "  PASSWORD:" + loginBinding.etPassword.getText());//id를 onClick이라는 event에 받아온거야.


            }
        }); //화면에 터치하는걸 click이라고 하는데, click하면 여기 안에있는 함수에 event를 던져주는거야.

        loginBinding.btnGoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

//비밀번호 찾기 아직 미흡해서 모두 주석처리
//        loginBinding.btnFindIdAndPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, FindIdAndPasswordActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void onHide(View view) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //requestCode==123:: 호출된거
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}