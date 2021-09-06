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
        // Data형을 파라미터로 받아서 캘린더를 띄울때 현재 날짜를 기본으로 띄울 수 있다. 근데 안됨.

        //calendarBinding.calendarView.setDateSelected(new Date(System.currentTimeMillis()), false);
        // 파라미터로 넣어준 날짜에 대한 선택여부를 결정한다.

        //calendarBinding.calendarView.setSelectedDate(new Date(System.currentTimeMillis()));
        // 이대로 키면 현재 날짜를 띄워줌!

        //calendarBinding.calendarView.removeDecorators();
        // decorators를 제거하여 초기화 해주고.
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

                    // 토스트 메세지 띄워주기 위해 result에 저장
                    String day_full = year + "년 "+ (month+1)  + "월 " + day + "일 ";
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
                // result 마지막 "\n" 없애주기
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

                dismiss(); // popup이 삭제됨
            }
        });

        return calendarBinding.getRoot();
    }


}
