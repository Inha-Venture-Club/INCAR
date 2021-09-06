package ac.inhaventureclub.incar.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ac.inhaventureclub.incar.R;

public class PopUpUtilActivity extends AppCompatActivity {

    public void popUpMake(String _message, Class activityClass) { // 1.메세지내용 2.이동할 액티비티 Class

        AlertDialog.Builder checkBuilder = new AlertDialog.Builder(this, R.style.mainPopUpDialog);

        checkBuilder.setMessage("\n" + _message)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Dialog", "확인");

                        Intent intent = new Intent(PopUpUtilActivity.this, activityClass);
                        startActivity(intent);
                    }
                })

                .setCancelable(false); // 백버튼으로 팝업창이 닫히지 않도록 한다.

        AlertDialog checkDialog = checkBuilder.show();

        //checkBuilder.setMessage한 것 가운데 정렬 방법
        TextView messageText = (TextView) checkDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);

        Button btnPositive = checkDialog.getButton(Dialog.BUTTON_POSITIVE);
        btnPositive.setTextSize(14);

        checkDialog.show();

        //디스플레이 해상도를 가져와서
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //비율에 맞게 다이얼로그 크기를 지정
        Window window = checkDialog.getWindow();
        int x = (int) (size.x * 0.7f);
        int y = (int) (size.y * 0.17f);
        window.setLayout(x, y);

    }

    public void popUpMakeWithoutIntent(String _message) { // 1.메세지내용 2.이동할 액티비티 Class

        AlertDialog.Builder checkBuilder = new AlertDialog.Builder(this, R.style.mainPopUpDialog);

        checkBuilder.setMessage("\n" + _message)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Dialog", "확인");

                    }
                })

                .setCancelable(false); // 백버튼으로 팝업창이 닫히지 않도록 한다.

        AlertDialog checkDialog = checkBuilder.show();

        //checkBuilder.setMessage한 것 가운데 정렬 방법
        TextView messageText = (TextView) checkDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);

        Button btnPositive = checkDialog.getButton(Dialog.BUTTON_POSITIVE);
        btnPositive.setTextSize(14);

        checkDialog.show();

        //디스플레이 해상도를 가져와서
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //비율에 맞게 다이얼로그 크기를 지정
        Window window = checkDialog.getWindow();
        int x = (int) (size.x * 0.7f);
        int y = (int) (size.y * 0.17f);
        window.setLayout(x, y);
    }

    public void popUpMakeWithoutIntentWithFinish(String _message) { // 1.메세지내용 2.이동할 액티비티 Class

        AlertDialog.Builder checkBuilder = new AlertDialog.Builder(this, R.style.mainPopUpDialog);

        checkBuilder.setMessage("\n" + _message)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Dialog", "확인");
                        finish();
                    }
                })

                .setCancelable(false); // 백버튼으로 팝업창이 닫히지 않도록 한다.

        AlertDialog checkDialog = checkBuilder.show();

        //checkBuilder.setMessage한 것 가운데 정렬 방법
        TextView messageText = (TextView) checkDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);

        Button btnPositive = checkDialog.getButton(Dialog.BUTTON_POSITIVE);
        btnPositive.setTextSize(14);

        checkDialog.show();

        //디스플레이 해상도를 가져와서
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //비율에 맞게 다이얼로그 크기를 지정
        Window window = checkDialog.getWindow();
        int x = (int) (size.x * 0.7f);
        int y = (int) (size.y * 0.17f);
        window.setLayout(x, y);
    }

    public void popUpMakeWithoutIntentWithVertical(String _message) { // 1.메세지내용 2.이동할 액티비티 Class

        AlertDialog.Builder checkBuilder = new AlertDialog.Builder(this, R.style.mainPopUpDialog);

        checkBuilder.setMessage("\n" + _message)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Dialog", "확인");

                    }
                })

                .setCancelable(false); // 백버튼으로 팝업창이 닫히지 않도록 한다.

        AlertDialog checkDialog = checkBuilder.show();

        //checkBuilder.setMessage한 것 가운데 정렬 방법
        TextView messageText = (TextView) checkDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER_VERTICAL);

        Button btnPositive = checkDialog.getButton(Dialog.BUTTON_POSITIVE);
        btnPositive.setTextSize(14);

        checkDialog.show();

        //디스플레이 해상도를 가져와서
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);

        //비율에 맞게 다이얼로그 크기를 지정
//        Window window = checkDialog.getWindow();
//        int x = (int) (size.x * 0.7f);
//        int y = (int) (size.y * 0.17f);
//        window.setLayout(x, y);
    }
}