package ac.inhaventureclub.incar.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.activity.MainActivity;
import ac.inhaventureclub.incar.databinding.FragmentCreateRatingBinding;
import ac.inhaventureclub.incar.object.RequestObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRatingFragment extends Fragment {

    public RequestObject requestObject;

    private Context context;


    private FragmentCreateRatingBinding createRatingBinding;

    public CreateRatingFragment() {
        // Required empty public constructor
    }

    class Listener implements RatingBar.OnRatingBarChangeListener{

        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            Toast.makeText(getContext(), "입력된 rating 값 : "+rating, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        createRatingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_rating, container,false);

        RatingBar ratingBar = createRatingBinding.createRatingRatingBar;

        ratingBar.setOnRatingBarChangeListener(new Listener());

        // Inflate the layout for this fragment


        Button ratingCancel = createRatingBinding.createRatingCancel;

        ratingCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onFragmentSelected(5,null);
                Log.i("calcel", "setOnclick");
            }
        });

        Button ratingComplete = createRatingBinding.createRatingComplete;

        ratingComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        requestObject = new RequestObject();
                        //requestObject.RATING

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        ((MainActivity)getActivity()).requestObject = requestObject;
                        ((MainActivity)getActivity()).onFragmentSelected(6, savedInstanceState);
                        Toast.makeText(getContext(), "리뷰 작성 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                }.execute();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().remove(CreateRatingFragment.this).commit();
//                fragmentManager.popBackStack();
            }
        });

        return createRatingBinding.getRoot();

    }




}
