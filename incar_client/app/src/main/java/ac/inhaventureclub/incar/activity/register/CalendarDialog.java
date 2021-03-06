package ac.inhaventureclub.incar.activity.register;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.databinding.ActivityCalendarBinding;

public class CalendarDialog extends DialogFragment {

    public interface OnCalendarDialogListener{
        void setReceivedStrWhenGoDateList(ArrayList<String> _selectedStrWhenGoDateList);
        ArrayList<String> getReceivedStrWhenGoDateList();
        void renewCalenderItem();
        }

    private OnCalendarDialogListener mOnCalendarDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof OnCalendarDialogListener) {
            mOnCalendarDialogListener = (OnCalendarDialogListener) getActivity();
        }
    }

    private ActivityCalendarBinding calendarBinding;

    public ArrayList<String> selectedStrWhenGoDateList = new ArrayList<>() ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        calendarBinding = DataBindingUtil.inflate(inflater,R.layout.activity_calendar,container,false);
        calendarBinding.setActivity(this);

        calendarBinding.calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarBinding.calendarView.setCurrentDate(new Date(System.currentTimeMillis()));
        // Data?????? ??????????????? ????????? ???????????? ????????? ?????? ????????? ???????????? ?????? ??? ??????. ?????? ??????.

        //calendarBinding.calendarView.setDateSelected(new Date(System.currentTimeMillis()), false);
        // ??????????????? ????????? ????????? ?????? ??????????????? ????????????.

        //calendarBinding.calendarView.setSelectedDate(new Date(System.currentTimeMillis()));
        // ????????? ?????? ?????? ????????? ?????????!

        //calendarBinding.calendarView.removeDecorators();
        // decorators??? ???????????? ????????? ?????????.
        calendarBinding.calendarView.setSelectionMode(calendarBinding.calendarView.SELECTION_MODE_MULTIPLE);

        calendarBinding.calendarView.addDecorators( new SundayDecorator(), new SaturdayDecorator());

        calendarBinding.tvChoiceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<CalendarDay> selectedDates = calendarBinding.calendarView.getSelectedDates();

                String result="";
                int year, month, day;
                String strYYYY, strMM, strDD;
                for( int i=0; i<selectedDates.size(); i++)
                {
                    CalendarDay calendar = selectedDates.get(i);
                    year = calendar.getYear();
                    month = calendar.getMonth();
                    day = calendar.getDay();

                    // ????????? ????????? ???????????? ?????? result??? ??????
                    String day_full = year + "??? "+ (month+1)  + "??? " + day + "??? ";
                    result += (day_full + "\n");

                    strYYYY = Integer.toString(year);

                    if (month <= 8) {
                        strMM = "0";
                    }
                    else {
                        strMM = "";
                    }
                    strMM = strMM + (month + 1);

                    if (day <= 9) {
                        strDD = "0";
                    }
                    else {
                        strDD = "";
                    }
                    strDD = strDD + day;

                    String strWhenGo = strYYYY + strMM + strDD;
                    selectedStrWhenGoDateList.add(strWhenGo);
                }
                // result ????????? "\n" ????????????
                if (!result.equals("")) {
                    result = result.substring(0, result.length()-1);
                    Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                }

                /*
                for( int i=0; i<selectedStrWhenGoDateList.size(); i++) {
                    Toast.makeText(CalendarDialog.this, selectedStrWhenGoDateList.get(i), Toast.LENGTH_LONG).show();
                }
                */

                mOnCalendarDialogListener.setReceivedStrWhenGoDateList(selectedStrWhenGoDateList);
                mOnCalendarDialogListener.renewCalenderItem();

                dismiss(); // popup??? ?????????
            }
        });

        return calendarBinding.getRoot();
    }


}
