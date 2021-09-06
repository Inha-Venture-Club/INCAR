package ac.inhaventureclub.incar.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ac.inhaventureclub.incar.fragment.AssessmentGetInFragment;
import ac.inhaventureclub.incar.fragment.AssessmentGiveMeaRideFragment;

public class AssessmentPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public AssessmentPagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    //@NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new AssessmentGetInFragment();
            case 1:
                return new AssessmentGiveMeaRideFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
