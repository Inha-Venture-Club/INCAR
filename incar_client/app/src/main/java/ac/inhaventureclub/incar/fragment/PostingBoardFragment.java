package ac.inhaventureclub.incar.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.activity.BackActivity;
import ac.inhaventureclub.incar.activity.MainActivity;
import ac.inhaventureclub.incar.activity.PostActivity;
import ac.inhaventureclub.incar.adapter.PostingBoardRecyclerAdapter;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.PostingObject;

public class PostingBoardFragment extends Fragment implements TextWatcher {

    public int state;
    public ArrayList<Integer> addrSelectedList = new ArrayList<Integer>();
    public ArrayList<Integer> addrDepartKeep = new ArrayList<Integer>();
    public ArrayList<Integer> addrArriveKeep = new ArrayList<Integer>();


    //TODO 검색 기능
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
       //adapter.getFilter().filter(s);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public interface OnPostingBoardListener{
        void setState(int state);
        int getState();
        void setAddrSelectedList(ArrayList<Integer> addrSelectedList);
        ArrayList<Integer> getAddrSelectedList();
    }

    private OnPostingBoardListener onPostingBoardListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof OnPostingBoardListener) {
            onPostingBoardListener = (OnPostingBoardListener) getActivity();
        }
    }
    ArrayList<String> gu;

    /* address array */
    public static String[] regionGroup;
    public static String[] gu0Group, gu1Group,gu2Group,gu3Group,gu4Group;
    public static String[] gu5Group,gu6Group,gu7Group,gu8Group,gu9Group;
    public static String[] gu10Group,gu11Group,gu12Group,gu13Group,gu14Group;
    public static String[] gu15Group,gu16Group;
    public static String[] guSelected;

    /* View */
    private RecyclerView recyclerView;
    private PostingBoardRecyclerAdapter adapter;
    private String result;
    private RecyclerView.LayoutManager layoutManager;
    //new ArrayList<>();
    private HorizontalScrollView hsv_depart;
    private HorizontalScrollView hsv_arrive;
    private LinearLayout layout_hsv_depart;
    private LinearLayout layout_hsv_arrive;
    private String[][]  guArrayStatic;




    /* REQUEST CODE */
    public static final int REQ_DEPART = 100;
    public static final int REQ_ARRIVE = 200;
    public static final int DEPART_STATE = 10;
    public static final int ARRIVE_STATE = 20;



    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        /* Binding */
        //postingboardBinding = DataBindingUtil.
        View view = inflater.inflate(R.layout.fragment_postingboard, container, false);

        ImageButton search = view.findViewById(R.id.btn_searchingbox);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).popUpMakeWithoutIntentWithVertical(" 아직 검색 기능이 구현되지 않았습니다.");
            }
        });

        /* Find View By Id */
        // 상단
        hsv_depart = view.findViewById(R.id.hsv_depart);
        hsv_arrive = view.findViewById(R.id.hsv_arrive);
        layout_hsv_depart = view.findViewById(R.id.layout_hsv_depart);
        layout_hsv_arrive = view.findViewById(R.id.layout_hsv_arrive);
        hsv_depart.setClickable(true);
        hsv_arrive.setClickable(true);
        // 하단
        recyclerView = (RecyclerView)view.findViewById(R.id.postingboard_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        TextView textView = view.findViewById(R.id.postingboard_text);
        // array
        regionGroup = getResources().getStringArray(R.array.region);
        gu0Group = getResources().getStringArray(R.array.gu0);
        gu1Group = getResources().getStringArray(R.array.gu1);
        gu2Group = getResources().getStringArray(R.array.gu2);
        gu3Group = getResources().getStringArray(R.array.gu3);
        gu4Group = getResources().getStringArray(R.array.gu4);
        gu5Group = getResources().getStringArray(R.array.gu5);
        gu6Group = getResources().getStringArray(R.array.gu6);
        gu7Group = getResources().getStringArray(R.array.gu7);
        gu8Group = getResources().getStringArray(R.array.gu8);
        gu9Group = getResources().getStringArray(R.array.gu9);
        gu10Group = getResources().getStringArray(R.array.gu10);
        gu11Group = getResources().getStringArray(R.array.gu11);
        gu12Group = getResources().getStringArray(R.array.gu12);
        gu13Group = getResources().getStringArray(R.array.gu13);
        gu14Group = getResources().getStringArray(R.array.gu14);
        gu15Group = getResources().getStringArray(R.array.gu15);
        gu16Group = getResources().getStringArray(R.array.gu16);
        String[][]  guArray = {gu0Group, gu1Group, gu2Group, gu3Group, gu4Group
                , gu5Group, gu6Group, gu7Group, gu8Group, gu9Group
                , gu10Group, gu11Group, gu12Group, gu13Group, gu14Group
                , gu15Group, gu16Group};
        setGuArray(guArray);


        recyclerView = view.findViewById(R.id.postingboard_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // permission
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }

        Spinner spinner = view.findViewById(R.id.postingboard_spinner);
        //PostingObject postingObject = new PostingObject();

        new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                result = HttpManager.getData( "/posting");
                //result = HttpManager.getData("/posting"); // null: error, !null: ok
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Gson gson = new Gson();
                if (result != null){
                    recyclerView.setVisibility(View.VISIBLE);
                    ArrayList<PostingObject> postingObjectList = gson.fromJson(result, new TypeToken<ArrayList<PostingObject>>() {
                    }.getType());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
                    Date date = new Date();
                    ArrayList<PostingObject> postingClone = (ArrayList<PostingObject>) postingObjectList.clone();
                    for (PostingObject p: postingClone) {
                        try {
                            if (date.after(simpleDateFormat.parse(p.WHEN_GO)))
                                postingObjectList.remove(p);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                adapter = new PostingBoardRecyclerAdapter(getActivity(), postingObjectList);
//                Collections.sort(postingObjectList);
//                adapter.notifyDataSetChanged();
                //TODO spinner sorting
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spinner.getSelectedItem().toString().equals("빠른출발순")) {
                            PostingObject.ASC = true;
                            Collections.sort(postingObjectList);
                            adapter.notifyDataSetChanged();
                        } else if (spinner.getSelectedItem().toString().equals("느린출발순")) {
                            PostingObject.ASC = false;
                            Collections.sort(postingObjectList);
//                            Collections.sort(postingObjectList, new Comparator<PostingObject>() {
//                                @Override
//                                public int compare(PostingObject o1, PostingObject o2) {
//                                    return 0;
//                                }
//                            });
                            adapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        PostingObject.ASC = true;
                        Collections.sort(postingObjectList);
                        adapter.notifyDataSetChanged();

                    }
                });
//                spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String time = (String) spinner.getSelectedItem();//time.equals("")
////                        if(spinner.getSelectedItem().toString().equals(" ")){
////                            Collections.sort(postingObjectList);
////                            adapter.notifyDataSetChanged();
////                        }
//                        if(spinner.getSelectedItem().toString().equals("빠른출발순")){
//                            Collections.sort(postingObjectList);
//                            adapter.notifyDataSetChanged();
//                        }else  if(spinner.getSelectedItem().toString().equals("느린출발순")){
//                            PostingObject.ASC = false;
//                            Collections.sort(postingObjectList);
////                            Collections.sort(postingObjectList, new Comparator<PostingObject>() {
////                                @Override
////                                public int compare(PostingObject o1, PostingObject o2) {
////                                    return 0;
////                                }
////                            });
//                            adapter.notifyDataSetChanged();
//
//                        }
//                    }
//                });


                recyclerView.setAdapter(adapter);
                adapter.setItemClick(new PostingBoardRecyclerAdapter.ItemClick() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), PostActivity.class);
                        intent.putExtra("postingDetail", new Gson().toJson(postingObjectList.get(position)));
                        startActivity(intent);
                        Toast.makeText(getContext(), "현재 선택된 아이템 : " + position, Toast.LENGTH_LONG).show();
                    }
                });
//                ClientMessage clientMessage = new ClientMessage();
//                clientMessage.message = "hi";
//                clientMessage.name = "id";
                //HttpManager.sendMsg(new Gson().toJson(clientMessage));
//                if(result!= null) {
//                    Gson gson = new Gson();
//                    ArrayList<PostingObject> postingObjectList = gson.fromJson(result, new TypeToken<ArrayList<PostingObject>>(){}.getType());
//                    adapter = new PostingBoardRecyclerAdapter(getActivity(), postingObjectList);
//                    recyclerView.setAdapter(adapter);
//                }else{
//                    Toast.makeText(getContext(), "null 입니다", Toast.LENGTH_SHORT).show();
//                }
            }else{
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }
            }
        }.execute();

//                /* AddressFragment의 data를 받은 MainAcitivy부터 data를 받기 */
//        Bundle extra = this.getArguments();
//        if(extra!=null){
//            extra = getArguments();
//            addrSelectedList = extra.getIntegerArrayList("addrSelectedList");
//
//            if(isAddrArriveSelected ==1){
//                //gu = new ArrayList<String>();
//                addrArriveSelectedList = extra.getIntegerArrayList("addrArriveSelectedList");
//                Log.i("\nPostingBoardFragment★\naddrArriveSelectedList ",addrArriveSelectedList.toString());
//                Log.i("\nPostingBoardFragment★\nisAddrDepartSelected ",Integer.toString(isAddrDepartSelected));
//                Log.i("\nPostingBoardFragment★\nisAddrArriveSelected ",Integer.toString(isAddrArriveSelected));
//
//                ArrayList<Integer> regionList = getRegionFromAddresslist(addrArriveSelectedList);
//                ArrayList<Integer> guList = getGuFromAddresslist(addrArriveSelectedList);
//                for(int i=0; i<addrArriveSelectedList.size(); i++){
//                    addTextViewToLayout(layout_hsv_arrive,guArray[regionList.get(i)][guList.get(i)]);
//                    Log.i("\nPostingBoardFragment★\nfor ",guArray[regionList.get(i)][guList.get(i)]);
//                }
//            }
//        }


        /* 검색 box 선택 */
        hsv_depart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action==MotionEvent.ACTION_DOWN){
                    Intent intent = new Intent(getActivity(), BackActivity.class);
                    if(addrDepartKeep!=null){
                        intent.putExtra("addrSelectedList", addrDepartKeep);
                        Log.i("\nPostingBoardFragment★\naddrSelectedList ",addrDepartKeep.toString());
                    }
                    intent.putExtra("state",DEPART_STATE);
                    Log.i("\nPostingBoardFragment★\nstate ",Integer.toString(onPostingBoardListener.getState()));
                    getActivity().startActivityForResult(intent,REQ_DEPART);
                }
                return false;
            }
        });
        hsv_arrive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_MOVE){
                    return true;
                }
                else if(action==MotionEvent.ACTION_UP){
                    Intent intent = new Intent(getActivity(), BackActivity.class);
                    if(addrArriveKeep!=null){
                        intent.putExtra("addrSelectedList", addrArriveKeep);
                        Log.i("\nPostingBoardFragment★\naddrSelectedList ",addrArriveKeep.toString());
                    }
                    intent.putExtra("state",ARRIVE_STATE);
                    Log.i("\nPostingBoardFragment★\nstate ",Integer.toString(onPostingBoardListener.getState()));
                    getActivity().startActivityForResult(intent,REQ_ARRIVE  );
                    return false;
                }
                return false;
            }


        });
        hsv_arrive.setOnDragListener(new View.OnDragListener(){
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });





        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //requestCode==123:: 호출된거
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private ArrayList<String> convertIntegerToString(ArrayList<Integer> integerArrayList){
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for(int i=0; i<integerArrayList.size(); i++){
            stringArrayList.add(i,integerArrayList.toString());
        }
        return stringArrayList;
    }

    private ArrayList<Integer> getRegionFromAddresslist(ArrayList<Integer> addrDepartSelectedList){
        int region;
        ArrayList<Integer> regionList = new ArrayList<Integer>();
        for(int i =0; i<addrDepartSelectedList.size(); i++){
            region = (addrDepartSelectedList.get(i)-10000)/100;
            regionList.add(i,region);
//            Log.i("PostingBoardFragment\n★\nregion",Integer.toString(region));
        }
        return regionList;
    }
    private ArrayList<Integer> getGuFromAddresslist(ArrayList<Integer> addrDepartSelectedList){
        int gu;
        ArrayList<Integer> guList = new ArrayList<Integer>();
        for(int i =0; i<addrDepartSelectedList.size(); i++){
            gu = addrDepartSelectedList.get(i)%100;
            guList.add(i,gu);
//            Log.i("PostingBoardFragment\n★\ngu",Integer.toString(gu));
        }
        return guList;
    }


    private void addTextViewToLayout(LinearLayout linearLayout, String address){
        TextView textView = new TextView(getActivity());
        textView.setBackground(getResources().getDrawable(R.drawable.shape_postingboard_addressitem));
        textView.setText(address);
        textView.setTextSize(15);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setPadding(10,5,10,5);
        LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewParam.setMargins(10,5,10,5);
        linearLayout.addView(textView,textViewParam);
    }


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
//        setContentView(R.layout.fragment_postingboard);
//        recyclerView = (RecyclerView) findViewById(R.id.postingboard_recycler);
//
//        list = new ArrayList<WordItemData>();
//
//        list.add(new WordItemData(new String("우리집"), new String("인하대 하이테크"), new String("김진우"), new String("2020/03/01"), new String("08:00")));
//        list.add(new WordItemData(new String("서울집"), new String("인하대 5호관"), new String("박주원"), new String("2020/03/02"), new String("09:00")));
//        list.add(new WordItemData(new String("인천집"), new String("인하대 60주년"), new String("조민경"), new String("2020/03/03"), new String("09:30")));
//        list.add(new WordItemData(new String("경기집"), new String("인하대 6호관"), new String("홍다솔"), new String("2020/03/04"), new String("10:00")));
//        list.add(new WordItemData(new String("대전집"), new String("인하대 2호관"), new String("이민혁"), new String("2020/03/05"), new String("08:00")));
//

    }

    private void setGuArray(String[][] guArrayStatic){ this.guArrayStatic = guArrayStatic; }
    private String[][] getGuArray(){ return this.guArrayStatic; }

    public void receiveData(int state, ArrayList<Integer> arrayList){

        ArrayList<Integer> regionList = getRegionFromAddresslist(arrayList);
        ArrayList<Integer> guList = getGuFromAddresslist(arrayList);

        if(state == DEPART_STATE){
            layout_hsv_depart.removeAllViews();
            for(int i=0; i<arrayList.size(); i++){
                this.addTextViewToLayout(layout_hsv_depart,guArrayStatic[regionList.get(i)][guList.get(i)]);
            }
            Log.i("PostingBoardFragment★DEPART",arrayList.toString());
        }
        if(state == ARRIVE_STATE){
            layout_hsv_arrive.removeAllViews();
            for(int i=0; i<arrayList.size(); i++){
                this.addTextViewToLayout(layout_hsv_arrive,guArrayStatic[regionList.get(i)][guList.get(i)]);
            }
            Log.i("PostingBoardFragment★ARRIVE",arrayList.toString());
        }
    }

    public void setAddrKeep(ArrayList<Integer> arrayList, int state){
        if(state==DEPART_STATE) this.addrDepartKeep = arrayList;
        if(state==ARRIVE_STATE) this.addrArriveKeep = arrayList;
    }
    public ArrayList<Integer> getAddrKeep(int state){
        if(state==DEPART_STATE) return this.addrDepartKeep;
        else if(state==ARRIVE_STATE) return this.addrArriveKeep;
        else return null;
    }



}