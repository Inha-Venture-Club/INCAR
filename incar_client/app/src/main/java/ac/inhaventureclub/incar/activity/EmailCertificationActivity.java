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
import ac.inhaventureclub.incar.databinding.ActivityEmailCertificationBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.EmailObject;
import ac.inhaventureclub.incar.object.Result;
import ac.inhaventureclub.incar.object.UserObject;

public class EmailCertificationActivity extends AppCompatActivity {
    private ActivityEmailCertificationBinding emailCertificationBinding;
    public String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // [Binding]
        emailCertificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_certification);
        emailCertificationBinding.setActivity(this);
        // <<getIntent>> : id(value = etEmailSignUp)
        // refactoring-dasol: change getString->etEmailSignUp
        String getString = getIntent().getStringExtra("id");

        // 앱 권한 요청
        // refactoring-dasol: requestCode를 static으로 선언하여 관리
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }

        // [emailSendBtn/ onClickListener] : 인증번호 발송 버튼
        // * 클릭시, URL/email 주소에게 json(=getString)을 전송 (POST)
        emailCertificationBinding.emailSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Object, Object, Object>() { // DoIn실행하기 전에, 후에 뭘 할거냐를 정해주기위해 param Ob(전),Ob(중),Ob(후)가 필요해. 근데 우린 필요없 지금.
                    @Override
                    protected Object doInBackground(Object[] objects) { //doInBackground:"나는 background에서 동작할거야"
                        Log.i("[TAG/EmailCertificationActivity/emailSendBtn/onClick] ", "doInBackground() start!");
                        Log.i("[TAG/EmailCertificationActivity/emailSendBtn/onClick]", "post : address=/email; json="+getString);
                        result = HttpManager.postData(getString, "/email"); // null: error, !null: ok
                        Log.i("[TAG/EmailCertificationActivity/emailSendBtn/onClick]", "post : result="+result);
                        return null;
                    }
                }.execute(); // 실행시, 서버는 인증번호가 담긴 메일을 json(=getString)에게 전송하여, 응답으로 result(인증번호)를 받아옴

                Toast.makeText(getApplicationContext(), "인증번호를 전송하였습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        // [emailAuthBtn/ onClickListener] : 인증번호 확인 버튼
        // * 인증번호를 입력하지 않은 경우 → Toast
        // * 인증번호를 입력한 경우
        //    * 인증번호가 result와 일치하는 경우 → setResult(3)
        //    * 인증번호가 result와 다른 경우 → Toast
        emailCertificationBinding.emailAuthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("[TAG/EmailCertificationActivity/emailAuthBtn/onClick] ", "onClick() start!");

                if (emailCertificationBinding.emailAuthNumber.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "인증번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!result.isEmpty()) {
                        if (emailCertificationBinding.emailAuthNumber.getText().toString().equals(result)) {
                            Intent intent = new Intent(EmailCertificationActivity.this, SignUpActivity.class);
                            setResult(3, intent);
                            Log.i("[TAG/EmailCertificationActivity/emailAuthBtn/onClick] ", "Intent: EmailCertificationActivity → SignUpActivity (resultCode:3)");
                            finish(); // finish() 결과로, EmailCertificationActivity가 종료되어 아래에 위치하던 SignUpActivity가 나타남.
                        } else {
                            Toast.makeText(getApplicationContext(), "인증번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }
        });
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

