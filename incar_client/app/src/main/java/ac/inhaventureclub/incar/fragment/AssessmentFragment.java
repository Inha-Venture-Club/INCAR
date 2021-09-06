package ac.inhaventureclub.incar.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import ac.inhaventureclub.incar.adapter.AssessmentPagerAdapter;
import ac.inhaventureclub.incar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssessmentFragment extends Fragment {



    public AssessmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setRetainInstance(true); //@@

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_assessment,container,false);
        TabLayout tabLayout = (TabLayout)v.findViewById(R.id.tablayout_assessment);

        TabItem tabAssessmentGetIn = (TabItem)v.findViewById(R.id.tabAssessmentGetIn);
        TabItem tabAssessmentGiveMeaRide = (TabItem)v.findViewById(R.id.tabAssessmentGiveMeaRide);

        ViewPager viewPager = (ViewPager)v.findViewById(R.id.assessmentViewPager);


        AssessmentPagerAdapter assessmentPagerAdapter= new AssessmentPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(assessmentPagerAdapter);


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

        return v;
    }



}
