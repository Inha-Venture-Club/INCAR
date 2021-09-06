package ac.inhaventureclub.incar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PostManagementPagerAdapter extends FragmentPagerAdapter {
    //@NonNull

    private int numOfTabs;

    public PostManagementPagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new PostedWritingFragment();
           case 1:
                return new SentRequestFragment();
            case 2:
                return new ReceivedRequestFragment();
            case 3:
                return new AssessmentFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
