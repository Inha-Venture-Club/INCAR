package ac.inhaventureclub.incar.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ac.inhaventureclub.incar.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AssessmentGiveMeaRideFragment extends Fragment {


    public AssessmentGiveMeaRideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assessment_give_mea_ride, container, false);
    }

}
