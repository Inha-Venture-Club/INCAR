package ac.inhaventureclub.incar.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import ac.inhaventureclub.incar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicePreparingRotateFragment extends Fragment {


    public ServicePreparingRotateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_service_preparing_rotate, container,false);
        ImageView imageView = view.findViewById(R.id.service_preparing_rotate_image);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        imageView.startAnimation(animation);
        return view;
    }

}
