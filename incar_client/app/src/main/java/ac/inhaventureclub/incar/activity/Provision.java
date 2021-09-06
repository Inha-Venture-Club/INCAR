package ac.inhaventureclub.incar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.databinding.ActivityProvisionBinding;

public class Provision extends AppCompatActivity {
    private ActivityProvisionBinding provisionBinding;
    private CheckBox cb1 = null;
    private CheckBox cb2 = null;
    private CheckBox cb3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provisionBinding = DataBindingUtil.setContentView(this, R.layout.activity_provision);
        provisionBinding.setActivity(this);
        final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1);
        final CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb1.isChecked()) {
                    cb2.setChecked(true);
                    cb3.setChecked(true);
                } else {
                    cb2.setChecked(false);
                    cb3.setChecked(false);

                }
            }
        });

        provisionBinding.scrollview2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    provisionBinding.scrollview1.requestDisallowInterceptTouchEvent(false);
                } else {
                    provisionBinding.scrollview1.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

        provisionBinding.scrollview3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    provisionBinding.scrollview1.requestDisallowInterceptTouchEvent(false);
                } else {
                    provisionBinding.scrollview1.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

        provisionBinding.btnCancelProvision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();

            }
        });

        provisionBinding.btnCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb1.isChecked() == true) {
                    setResult(1);
                    finish();
                } else if (cb2.isChecked() == true && cb3.isChecked() == true) {
                    setResult(1);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "약관에 모두 동의하지 않으면 회원가입을 할 수 없습니다.", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private View.OnClickListener onCheckBoxClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (isAllChecked()) {
                cb1.setChecked(true);

            } else {
                cb1.setChecked(false);
            }

        }
    };


    private boolean isAllChecked() {
        return (cb2.isChecked() && cb3.isChecked()) ? true : false;

    }
}
