package ac.inhaventureclub.incar.activity.register;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.adapter.DetailAddressChoiceRecyclerAdapter;
import ac.inhaventureclub.incar.databinding.ActivityDetailAddressChoiceBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.CacaoMapAddress;
import ac.inhaventureclub.incar.object.IncarMapAddress;

public class DetailAddressChoiceActivity extends AppCompatActivity {

    private static final String TAG = "DetailAddressChoiceActivity2";

    public double currentX;
    public double currentY;
    public double mapCenterX;
    public double mapCenterY;
    public String strCurrentX;
    public String strCurrentY;
    public String strMapCenterX;
    public String strMapCenterY;

    //TODO recyclerView
    private RecyclerView recyclerView;
    private DetailAddressChoiceRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public String strAddress;
    public String result;

    private ActivityDetailAddressChoiceBinding detailAddressChoiceBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_address_choice);

        detailAddressChoiceBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_address_choice);
        detailAddressChoiceBinding.setActivity(this);

        Intent intent = getIntent();
        currentX = intent.getExtras().getDouble("currentX");
        currentY = intent.getExtras().getDouble("currentY");
        mapCenterX = intent.getExtras().getDouble("mapCenterX");
        mapCenterY = intent.getExtras().getDouble("mapCenterY");

        strCurrentX = String.valueOf(currentX);
        strCurrentY = String.valueOf(currentY);
        strMapCenterX = String.valueOf(mapCenterX);
        strMapCenterY = String.valueOf(mapCenterY);

//        Log.i(TAG, "onCreate: " + currentX);
//        Log.i(TAG, "onCreate: " + currentY);
//        Log.i(TAG, "onCreate: " + mapCenterX);
//        Log.i(TAG, "onCreate: " + mapCenterY);

        //TODO recyclerView
        recyclerView = detailAddressChoiceBinding.tvRecyclerview;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        detailAddressChoiceBinding.imageViewBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("내 위치 중심");
        arrayList.add("지도 위치 중심");

        SpinnerAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayList);
        detailAddressChoiceBinding.spinnerPosition.setAdapter(spinnerAdapter);

        detailAddressChoiceBinding.spinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strAddress = detailAddressChoiceBinding.etDetailAdressSearch.getText().toString();

                if (!strAddress.equals("")) {

                    if ( position == 0) {

                        cacaoMapToIncarMapInBackGround(0, strAddress, strCurrentX, strCurrentY);

                    }else if (position ==1) {

                        cacaoMapToIncarMapInBackGround(1, strAddress, strMapCenterX, strMapCenterY);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        detailAddressChoiceBinding.imageViewSearchBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // 텍스트 내용 서버에 담아서 던지기
                // 리퀘스트 받기
                strAddress = detailAddressChoiceBinding.etDetailAdressSearch.getText().toString();
                //Log.i(TAG, "strAddress: " + strAddress);

                if (strAddress.equals("")) {
                    Toast.makeText(getApplicationContext(), "검색어를 입력해주세요.", Toast.LENGTH_LONG).show();
                }

                if ((detailAddressChoiceBinding.spinnerPosition.getSelectedItem().toString().equals("내 위치 중심")) && (!strAddress.equals(""))) {

//                    Log.i(TAG, "strCurrentX: " + strCurrentX);
//                    Log.i(TAG, "strCurrentY: " + strCurrentY);

                    cacaoMapToIncarMapInBackGround(0, strAddress, strCurrentX, strCurrentY);

                } else if ((detailAddressChoiceBinding.spinnerPosition.getSelectedItem().toString().equals("지도 위치 중심")) && (!strAddress.equals(""))) {

                    cacaoMapToIncarMapInBackGround(1, strAddress, strMapCenterX, strMapCenterY);
                }


            }
        });

    }

 public void cacaoMapToIncarMapInBackGround(int checkNum, String address, String X, String Y) {

     new  AsyncTask() {
         @Override
         protected Object doInBackground(Object[] objects) {

             if ( checkNum == 0) {
                 result = HttpManager.getCacaoMapAddressByMyPositon(address, X, Y); // null: error, !null: ok
                 return null;
             } else if (checkNum == 1) {
                 result = HttpManager.getCacaoMapAddressByMapPosition(address, X, Y); // null: error, !null: ok
                 return null;

             }
             return null;
         }

         @Override
         protected void onPostExecute(Object o) {
             super.onPostExecute(o);

//                            Log.i(TAG, "onPostExecute: ");
             if (result != null ) {
                 // ---------------- 여기 아래에도 body에 객체 비었는지, 안 비었는지 확인해야함 ----------------

//                                Log.i(TAG, "result != null");
                 Gson gson = new Gson();
                 CacaoMapAddress cacaoMapAddress = gson.fromJson(result, CacaoMapAddress.class);

                            /*
                            //HashMap Class를 통해서 이런식으로 변환하여 사용할 수도 있음.
                            HashMap<String, String> hashMap = gson.fromJson(result, HashMap.class);
                            String string = hashMap.get(key);
                             */
//                            Log.i(TAG, "result != null: " + cacaoMapAddress);

                 ArrayList<IncarMapAddress> incarMapAddressArrayList = new ArrayList<>();

                 for (int i = 0; i < cacaoMapAddress.documents.size(); i++) {

                     IncarMapAddress incarMapAddress = new IncarMapAddress();

                     incarMapAddress.place_name = cacaoMapAddress.documents.get(i).place_name;
                     incarMapAddress.address_name = cacaoMapAddress.documents.get(i).address_name;
                     incarMapAddress.x = cacaoMapAddress.documents.get(i).x;
                     incarMapAddress.y = cacaoMapAddress.documents.get(i).y;
                     incarMapAddress.distance = cacaoMapAddress.documents.get(i).distance;
                     incarMapAddressArrayList.add(incarMapAddress);

//                                    Log.i(TAG, "result != null: " + incarMapAddress.place_name);
                 }

                 //TODO recyclerView
                 adapter = new DetailAddressChoiceRecyclerAdapter(getApplicationContext(), incarMapAddressArrayList);
//                                Log.i("incarMapAddressArrayList", result);
                 recyclerView.setAdapter(adapter);

                 adapter.setItemClick(new DetailAddressChoiceRecyclerAdapter.ItemClick() {
                     @Override
                     public void onClick(View view, int position) {
                         //TODO 프래그먼트랑 달라서 변수값을 임의로 집어넣음. 안돌아갈수도 있음
                         //TODO 아이템이 클릭된 후 요청사항을 적으면 됨
                         Intent intent = new Intent(getApplicationContext(), CacaoMapActivity.class);
                         Log.i(TAG, "onClick: " + new Gson().toJson(incarMapAddressArrayList.get(position)));
                         intent.putExtra("incarMapAddress", new Gson().toJson(incarMapAddressArrayList.get(position)));
                         setResult(001, intent);
                         finish();
                         Toast.makeText(getApplicationContext(), incarMapAddressArrayList.get(position).place_name, Toast.LENGTH_LONG).show();
                     }
                 });

             }
             else if (result == null){
                 Toast.makeText(getApplicationContext(), "result == null", Toast.LENGTH_LONG).show();
             }

         }
     }.execute();
    }
}
