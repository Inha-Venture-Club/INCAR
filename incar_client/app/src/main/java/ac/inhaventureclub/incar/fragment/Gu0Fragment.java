package ac.inhaventureclub.incar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import ac.inhaventureclub.incar.R;

public class Gu0Fragment extends Fragment {

    private static final int BTN0 = 0;
    private static final int BTN1 = 1;
    private static final int BTN2 = 2;
    private static final int BTN_COUNT= 3;


    private int[] buttonState = {0,0,0};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_0gu, container, false);

        Button btn0_0gu = view.findViewById(R.id.btn0_0gu);
        Button btn1_0gu = view.findViewById(R.id.btn1_0gu);
        Button btn2_0gu = view.findViewById(R.id.btn2_0gu);
        Button[] btnGroup_gu = {btn0_0gu,btn1_0gu,btn2_0gu};

        setOnClickListnerGu(BTN0,btnGroup_gu);
        setOnClickListnerGu(BTN1,btnGroup_gu);
        setOnClickListnerGu(BTN2,btnGroup_gu);



        return view;
    }

    private void setOnClickListnerGu(int btnSelected,  Button[] btnGroup_gu){
        btnGroup_gu[btnSelected].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(buttonState[btnSelected]==0){
                    buttonState[btnSelected]=1;
                }else if(buttonState[btnSelected]==1){
                    buttonState[btnSelected]=0;
                }
                setButtonBackgrounds(btnSelected,btnGroup_gu);

            }
        });
    }

    private void setButtonBackgrounds(int buttonSelected, Button[] btnGroup_gu) {
        if(buttonState[buttonSelected]==0){
            btnGroup_gu[buttonSelected].setBackgroundResource(R.drawable.shape_postingmanagement_bottomtab_selected);
            btnGroup_gu[buttonSelected].setTextColor(getResources().getColor(R.color.white, null));
        }else {
            btnGroup_gu[buttonSelected].setBackgroundResource(R.drawable.shape_postingmanagement_bottomtab_normal);
            btnGroup_gu[buttonSelected].setTextColor(getResources().getColor(R.color.mainGrey, null));
        }
    }
}
