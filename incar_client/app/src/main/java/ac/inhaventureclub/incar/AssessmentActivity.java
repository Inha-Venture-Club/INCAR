package ac.inhaventureclub.incar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class AssessmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_assessment);

        TabLayout tabLayout = findViewById(R.id.tablayout_assessment);

        TabItem tabAssessmentGetIn = findViewById(R.id.tabAssessmentGetIn);
        TabItem tabAssessmentGiveMeaRide = findViewById(R.id.tabAssessmentGiveMeaRide);

        ViewPager viewPager = findViewById(R.id.assessmentViewPager);

        PostManagementPagerAdapter postManagementPagerAdapter = new PostManagementPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(postManagementPagerAdapter);

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


    }
}
