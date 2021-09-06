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
import ac.inhaventureclub.incar.databinding.ActivityPasswordEmailCertificationBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.EmailObject;
import ac.inhaventureclub.incar.object.Result;
import ac.inhaventureclub.incar.object.UserObject;

public class PasswordEmailCertificationActivity extends AppCompatActivity {
    private ActivityPasswordEmailCertificationBinding passwordEmailCertificationBinding;
    public String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordEmailCertificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_password_email_certification);
        passwordEmailCertificationBinding.setActivity(this);
        String getString = getIntent().getStringExtra("id");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }
        passwordEmailCertificationBinding.emailSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Object, Object, Object>() { // DoIn실행하기 전에, 후에 뭘 할거냐를 정해주기위해 param Ob(전),Ob(중),Ob(후)가 필요해. 근데 우린 필요없 지금.

                    @Override
                    protected Object doInBackground(Object[] objects) { //doInBackground:"나는 background에서 동작할거야"
                        result = HttpManager.postData(getString, "/email"); // null: error, !null: ok
                        Log.i("TAG", "**************************");//id를 onClick이라는 event에 받아온거야.
                        return null;
                    }
                }.execute();
                Toast.makeText(getApplicationContext(), "인증번호를 전송하였습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        passwordEmailCertificationBinding.emailAuthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordEmailCertificationBinding.emailAuthNumber.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "인증번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!result.isEmpty()) {
                        if (passwordEmailCertificationBinding.emailAuthNumber.getText().toString().equals(result)) {
                            setResult(3);
                            finish();
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

