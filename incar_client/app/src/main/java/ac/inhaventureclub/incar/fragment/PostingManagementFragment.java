package ac.inhaventureclub.incar.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.application.incar;

public class PostingManagementFragment extends Fragment {

    private Context context;

    public void textChange(){
        if(getView()!=null){
            TabLayout tabLayout = getView().findViewById(R.id.postingmanagement_tablayout);
            if (tabLayout!=null){
                if(incar.IS_GUEST==0){
                    tabLayout.getTabAt(1).setText("받은 요청");
                }else if (incar.IS_GUEST==1){
                    tabLayout.getTabAt(1).setText("보낸 요청");
                }
            }
        }
    }

    public PostingManagementFragment() {
    }


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        super.onCreateView(inflater, container, savedInstanceState);

        View viewPosting = inflater.inflate(R.layout.fragment_postingmanagement_main, container,false);

        getChildFragmentManager().beginTransaction().replace(R.id.postingmanagement_blank, new MypostingFragment()).commit();

        TabLayout tabLayout = viewPosting.findViewById(R.id.postingmanagement_tablayout);

        TabItem tabItem_request = viewPosting.findViewById(R.id.postingmanagement_tab_myrequest);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.postingmanagement_blank, new MypostingFragment())
                                .commit();
                        break;
                    case 1:
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.postingmanagement_blank, new RequestFragment())
                                .commit();
                        break;
                    case 2:
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.postingmanagement_blank, new RatingsFragment())
                                .commit();
                        break;

                        case 3:
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.postingmanagement_blank, new ServicePreparingRotateFragment())
                                .commit();
                        break;


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }
        return viewPosting;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //requestCode==123:: 호출된거
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}