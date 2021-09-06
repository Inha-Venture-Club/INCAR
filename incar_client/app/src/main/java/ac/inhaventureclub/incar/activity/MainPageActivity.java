package ac.inhaventureclub.incar.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.databinding.ActivityMainPageBinding;

public class MainPageActivity extends AppCompatActivity {
    private ActivityMainPageBinding mainPageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPageBinding = DataBindingUtil.setContentView(this,R.layout.activity_main_page);
    }
}