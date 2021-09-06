package ac.inhaventureclub.incar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.activity.MainActivity;

import static ac.inhaventureclub.incar.fragment.PostingBoardFragment.ARRIVE_STATE;
import static ac.inhaventureclub.incar.fragment.PostingBoardFragment.DEPART_STATE;

public class AddressFragment extends Fragment {
    //    public int state;
    public ArrayList<Integer> addrSelectedList = new ArrayList<Integer>();

    public ArrayList<Integer> addrDepartKeep = new ArrayList<Integer>();
    public ArrayList<Integer> addrArriveKeep = new ArrayList<Integer>();

    public interface OnAddressFragmentListener {
        void setState(int state);

        int getState();

        void setAddrSelectedList(ArrayList<Integer> addrSelectedList);

        ArrayList<Integer> getAddrSelectedList();
    }

    /* Listener */
    private OnAddressFragmentListener onAddressFragmentListener;
    private PostingBoardFragment.OnPostingBoardListener onPostingBoardListener;

    public static final int DEPART_OK = 101;
    public static final int ARRIVE_OK = 201;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof OnAddressFragmentListener) {
            onAddressFragmentListener = (OnAddressFragmentListener) getActivity();
        }
//        if(getActivity() != null && getActivity() instanceof MainActivity){
//            state = ((MainActivity)getActivity()).getState();
//        }
    }

    /* Address Array Group */
    private static String[] regionGroup;
    private static String[] gu0Group, gu1Group, gu2Group, gu3Group, gu4Group;
    private static String[] gu5Group, gu6Group, gu7Group, gu8Group, gu9Group;
    private static String[] gu10Group, gu11Group, gu12Group, gu13Group, gu14Group;
    private static String[] gu15Group, gu16Group;
    private static String[] guSelected;

    /* Fragment */
    private AddressContentFragment addressContentFragment;

    /* Constant */
    private static final int COLUMNS = 3;

    /* Variables */
    private int intRegionSelected;

    /* View */
    private RecyclerView recyclerView_address_gu;
    private ListView listView_address_region;


    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setRetainInstance(true); //@@

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* Fragment */
        addressContentFragment = new AddressContentFragment();

        /* Find View By Id */
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        // View
        listView_address_region = view.findViewById(R.id.listView_address_region);
        recyclerView_address_gu = view.findViewById(R.id.rv_address_gu);
        // Button
        Button btn_ok = view.findViewById(R.id.btn_address_ok);
        // Array
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
        String[][] guArray = {gu0Group, gu1Group, gu2Group, gu3Group, gu4Group
                , gu5Group, gu6Group, gu7Group, gu8Group, gu9Group
                , gu10Group, gu11Group, gu12Group, gu13Group, gu14Group
                , gu15Group, gu16Group};
        /* PostingBoardFragment의 data를 받은 BackAcitivy부터 data를 받기 */
        Bundle extra = this.getArguments();
        if (extra != null) {
//            extra = getArguments();
            onAddressFragmentListener.setState(extra.getInt("state"));
            Log.i("\nAddressFragment★postingBoardFragment-state->", Integer.toString(onAddressFragmentListener.getState()));
        }


        /* 초반 설정 */
        getChildFragmentManager().beginTransaction().replace(R.id.include_address_gu, addressContentFragment).commit();
        setOnClickListnerRegion(guArray, intRegionSelected);



        /* region 설정 */
        // 초반 settings
        ArrayAdapter<String> adapter_region = new ArrayAdapter<String>(getActivity(), R.layout.item_address_region, regionGroup);
        listView_address_region.setAdapter(adapter_region);
        guSelected = getGugroupWithRegionnum(guArray, intRegionSelected);

        // Click Event 처리
        listView_address_region.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intRegionSelected = position;
                Log.i("\nAddressFragment★\nintRegionSelected ", Integer.toString(intRegionSelected));
                setOnClickListnerRegion(guArray, intRegionSelected);

                listView_address_region.setAdapter(adapter_region);

            }
        });




        /* Ok Click Action */
        setOnClickListnerOk(btn_ok);

        return view;
    }


    private String[] getGugroupWithRegionnum(String[][] guArray, int regionNum) {
        Log.i("\nAddressFragment★\ngetGugroupWithRegionnum ", "SUCCESS");
        return guArray[regionNum];
    }

    /* Region */
    private void setOnClickListnerRegion(String[][] guArray, int intRegionSelected) {
        //setButtonBackgrounds(intRegionSelected,btnGroup_region);
        guSelected = getGugroupWithRegionnum(guArray, intRegionSelected);
        GridAdapter adapter = new GridAdapter(getActivity(), guSelected);
        recyclerView_address_gu.setLayoutManager(new GridLayoutManager(getActivity(), COLUMNS));
        recyclerView_address_gu.setAdapter(adapter);
        recyclerView_address_gu.setHasFixedSize(true);
    }

    /* Gu */
    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.RecyclerViewHolder> {
        Context context;
        LayoutInflater inflater;
        String[] items;

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public RecyclerViewHolder(View view) {
                super(view);
                textView = view.findViewById(R.id.tv_address_gu);
            }
        }

        public GridAdapter(Context context, String[] items) {
            this.context = context;
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public GridAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_address_gu, parent, false);
            GridAdapter.RecyclerViewHolder viewHolder = new GridAdapter.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            final String text = items[position];
            holder.textView.setText(text);

            for(int i=0; i<addrSelectedList.size(); i++){
                if(position==addrSelectedList.get(i)){
                    holder.textView.setTag(R.drawable.drawable_gu_selected);
                    holder.textView.setBackground(getResources().getDrawable(R.drawable.drawable_gu_selected));
                }
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int addressInt = 10000; // abcde: a==1; bc==region; de==gu;
                    int regionInt = intRegionSelected;
                    addressInt = addressInt + regionInt * 100 + position;
                    renewAddressSelectedList(addressInt);

                }
            });
        }


        private ArrayList<Integer> getRegionFromAddresslist(ArrayList<Integer> addrDepartSelectedList) {
            int region;
            ArrayList<Integer> regionList = new ArrayList<Integer>();
            for (int i = 0; i < addrDepartSelectedList.size(); i++) {
                region = (addrDepartSelectedList.get(i) - 10000) / 100;
                regionList.add(i, region);
//            Log.i("PostingBoardFragment\n★\nregion",Integer.toString(region));
            }
            return regionList;
        }

        private ArrayList<Integer> getGuFromAddresslist(ArrayList<Integer> addrDepartSelectedList) {
            int gu;
            ArrayList<Integer> guList = new ArrayList<Integer>();
            for (int i = 0; i < addrDepartSelectedList.size(); i++) {
                gu = addrDepartSelectedList.get(i) % 100;
                guList.add(i, gu);
//            Log.i("PostingBoardFragment\n★\ngu",Integer.toString(gu));
            }
            return guList;
        }

        @Override
        public int getItemCount() {
            if (items != null) return items.length;
            else {
                Log.i("\nAddressFragment★\n★\n★\n★\n★\nitems ", "ㅠㅠ");
                return 0;
            }
        }

        private void renewAddressSelectedList(int addressInt) {

            if (onAddressFragmentListener.getState() == DEPART_STATE) {
                addrSelectedList = getAddrKeep(DEPART_STATE);
            }
            if (onAddressFragmentListener.getState() == ARRIVE_STATE) {
                addrSelectedList = getAddrKeep(ARRIVE_STATE);
            }

            if (addrSelectedList != null) {
                for (int i = 0; i < addrSelectedList.size(); i++) {
                    if (addrSelectedList.get(i) == addressInt) {
                        addrSelectedList.remove(i);
                        return;
                    }
                }
                addrSelectedList.add(addressInt);
            } else {
                addrSelectedList.add(addressInt);
            }
        }

        private void firstBackgroundSetting() {
            ArrayList<Integer> regionList = getRegionFromAddresslist(addrDepartKeep);
            ArrayList<Integer> guList = getGuFromAddresslist(addrDepartKeep);

            /* TO_DO ★★★★★★★★*/
            //guArray[regionList.get(i)][guList.get(i)];
//            for(int i=0; i<addrDepartKeep.size(); i++){
//
//            }
//            Log.i("PostingBoardFragment★DEPART",arrayList.toString());
        }
    }


    /* OK */
    private void setOnClickListnerOk(Button btnSelected) {
        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressFragmentListener.setAddrSelectedList(addrSelectedList);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                int state = onAddressFragmentListener.getState();
                if (state == DEPART_STATE) {
                    intent.putExtra("addrSelectedList", onAddressFragmentListener.getAddrSelectedList());
                    intent.putExtra("state", DEPART_STATE);
                    getActivity().setResult(DEPART_OK, intent);
                    getActivity().finish();
                    Log.i("\nAddressFragment★OK-state ", Integer.toString(onAddressFragmentListener.getState()));
                } else if (state == ARRIVE_STATE) {
                    intent.putExtra("addrSelectedList", onAddressFragmentListener.getAddrSelectedList());
                    intent.putExtra("state", ARRIVE_STATE);
                    getActivity().setResult(ARRIVE_OK, intent);
                    getActivity().finish();
                    Log.i("\nAddressFragment★OK-state ", Integer.toString(onAddressFragmentListener.getState()));
                } else {
                    getActivity().finish();
                    Log.i("\nAddressFragment★OK ", "ㅠㅠ");

                }

            }
        });
    }

    public void setAddrKeep(ArrayList<Integer> arrayList, int state) {
        if (state == DEPART_STATE) this.addrDepartKeep = arrayList;
        if (state == ARRIVE_STATE) this.addrArriveKeep = arrayList;
    }

    public ArrayList<Integer> getAddrKeep(int state) {
        if (state == DEPART_STATE) return this.addrDepartKeep;
        else if (state == ARRIVE_STATE) return this.addrArriveKeep;
        else return null;
    }


}
