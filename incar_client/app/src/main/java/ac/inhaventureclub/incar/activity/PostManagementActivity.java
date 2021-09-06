package ac.inhaventureclub.incar.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import ac.inhaventureclub.incar.PostManagementPagerAdapter;
import ac.inhaventureclub.incar.R;

import static ac.inhaventureclub.incar.R.layout.activity_postingmanagement_main;

public class PostManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(activity_postingmanagement_main);

        TabLayout tabLayout = findViewById(R.id.postManagementTab);
        TabItem tabPostedWriting = findViewById(R.id.tabPostedWriting);
        TabItem tabSentRequest = findViewById(R.id.tabSentRequest);
        TabItem tabReceivedRequest = findViewById(R.id.tabReceivedRequest);
        TabItem tabAssessment = findViewById(R.id.tabAssessment);
        //TabItem tabGetIn = findViewById(R.id.tabGetIn);
        //TabItem tabGiveMeaRide = findViewById(R.id.tabGiveMeaRide);
        //TabItem tabAssessmentGetIn =  findViewById(R.id.tabAssessmentGetIn);
        //TabItem tabAssessmentGiveMeaRide = findViewById(R.id.tabAssessmentGiveMeaRide);
        ViewPager viewPager = findViewById(R.id.postManagementViewPager);
        //ViewPager viewPager1 = findViewById(R.id.assessmentViewPager);



        PostManagementPagerAdapter postManagementPagerAdapter = new PostManagementPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        //AssessmentPagerAdapter assessmentPagerAdapter = new AssessmentPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(postManagementPagerAdapter);
        //viewPager1.setAdapter(assessmentPagerAdapter);

        //One last step is to change the tabs view when the tab is selected or clicked
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager1.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }
}
