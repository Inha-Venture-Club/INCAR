package ac.inhaventureclub.incar;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ac.inhaventureclub.incar.databinding.ActivityProgrammerIntroductionBinding;
import ac.inhaventureclub.incar.databinding.FragmentProgrammerIntroductionBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class Programmer_IntroductionFragment extends Fragment {
    private FragmentProgrammerIntroductionBinding programmerIntroductionBinding;

    public Programmer_IntroductionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        programmerIntroductionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_programmer__introduction, container, false);
        View view = inflater.inflate(R.layout.fragment_programmer__introduction, container, false);
        // Inflate the layout for this fragment
        return view;
    }
}
