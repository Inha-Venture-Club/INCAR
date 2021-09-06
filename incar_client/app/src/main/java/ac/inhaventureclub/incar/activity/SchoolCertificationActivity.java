package ac.inhaventureclub.incar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.databinding.ActivitySchoolCertificationBinding;


public class SchoolCertificationActivity extends AppCompatActivity {
    private ActivitySchoolCertificationBinding schoolCertificationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("[TAG/SchoolCertificationActivity/onCreate] ", "start!");

        // [Binding]
        schoolCertificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_school_certification);
        schoolCertificationBinding.setActivity(this);

        // [btnCertification/onClickListener] : 안내사항에 동의하는지 여부 확인
        // * 동의하지 않는 경우 → Toast
        // * 동의하는 경우 → setResult(2) & finish()
        schoolCertificationBinding.btnCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("[TAG/SchoolCertificationActivity/onCreate/btnCertification/onClick] ", "start!");

                if (!schoolCertificationBinding.checkBox2.isChecked()) {
                    Toast.makeText(getApplicationContext(), "안내사항에 동의하지 않으면 회원가입을 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    // Intent intent = new Intent(SchoolCertificationActivity.this, SignUpActivity.class);
                    // setResult(2, intent); // setResult(2);
                    // setResult(2);

                    Intent intent = new Intent();
                    setResult(2, intent);
                    Log.i("[TAG/SchoolCertificationActivity/onCreate/btnCertification/onClick] ", "setResult(2)");
                    finish();
                }
            }
        });
    }

}
