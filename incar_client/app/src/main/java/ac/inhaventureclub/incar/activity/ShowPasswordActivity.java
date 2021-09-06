package ac.inhaventureclub.incar.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.databinding.ActivityShowPasswordBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.UserObject;

public class ShowPasswordActivity extends AppCompatActivity {
    public String spw, spw_chk;
    public String result;

    private ActivityShowPasswordBinding ShowPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_password);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }

        //기존에 있던 비밀번호를 지우고 새로 비밀번호를 입력하는 코드가 필요함.
        ShowPasswordBinding.btnCancelFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ShowPasswordBinding.authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserObject userObject = new UserObject();
                spw = ShowPasswordBinding.etShowPassword.getText().toString();
                spw_chk = ShowPasswordBinding.etShowPassword1.getText().toString();

                if (spw.matches("")) {
                    Toast.makeText(getApplicationContext(), "변경하고자 하는 비밀번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (spw_chk.matches("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else if (!spw_chk.equals(spw)) {
                    Toast.makeText(getApplicationContext(), "비밀번호와 비밀번호 확인이 서로 다릅니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    userObject.PW = ShowPasswordBinding.etShowPassword.getText().toString();

                    new AsyncTask<Object, Object, Object>() { // DoIn실행하기 전에, 후에 뭘 할거냐를 정해주기위해 param Ob(전),Ob(중),Ob(후)가 필요해. 근데 우린 필요없어 지금.

                        @Override
                        protected Object doInBackground(Object[] objects) { //doInBackground:"나는 background에서 동작할거야"
                            result = HttpManager.postData(new Gson().toJson(userObject), "/user"); // null: error, !null: ok
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);
                            if (result != null) {
                                Intent intent = new Intent(ShowPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "서버연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute(); //실행
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
