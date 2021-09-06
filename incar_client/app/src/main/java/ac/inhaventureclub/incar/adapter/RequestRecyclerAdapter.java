package ac.inhaventureclub.incar.adapter;


import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.object.PostingObject;

public class RequestRecyclerAdapter extends RecyclerView.Adapter<RequestRecyclerAdapter.Holder> {

    private Context context;
    private List<PostingObject>list = new ArrayList<>();
    private GestureDetector gestureDetector;
    private FragmentManager fragmentManager;

    private ItemClick itemClick;
    public interface ItemClick{
        public void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }


    public RequestRecyclerAdapter(Context context, List<PostingObject> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestmanagement_item, parent, false);
        Holder holder = new Holder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(Holder holder, int position){
        int itemposition = position;

        if(list.get(itemposition) != null){
            try{
                holder.onBind(list.get(itemposition));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (itemClick != null){
                    itemClick.onClick(v, position);
                }
            }
        });
    }


    @Override
    public int getItemCount(){
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        public TextView departureText;
        public TextView destinationText;
        public TextView user_nameText;
        public TextView whenGoText;
        public TextView timeText;
        public Button reviewBtn;
        public PostingObject data;


        public Holder(View view) {
            super(view);
            departureText = view.findViewById(R.id.request_departure);
            destinationText = (TextView) view.findViewById(R.id.request_destination);
            user_nameText = (TextView) view.findViewById(R.id.request_name);
            whenGoText = (TextView) view.findViewById(R.id.request_date);
            timeText = view.findViewById(R.id.request_time);
            reviewBtn = view.findViewById(R.id.request_reviewBtn);



        }

        public void onBind(PostingObject data)throws ParseException{
            this.data = data;
            departureText.setText("출발지 : "+data.DEPARTURE_DETAIL);
            destinationText.setText("도착지 : "+data.ARRIVE_DETAIL);
            user_nameText.setText(data.USER_ID);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
            if (data.WHEN_GO != null){
                Date date = simpleDateFormat.parse(data.WHEN_GO);//시간형태로 가져와줌
                SimpleDateFormat viewFormat = new SimpleDateFormat("날짜 : yyyy/MM/dd", Locale.KOREA);
                String stringDate = viewFormat.format(date);
                Date date2 = simpleDateFormat.parse(data.WHEN_GO);
                SimpleDateFormat viewFormat2 = new SimpleDateFormat("시간 : a hh:mm", Locale.KOREA);
                String stringTime = viewFormat2.format(date2);
                Log.d("라라라라라라라라ㅏㅏ라라", "onBind: "+stringDate);
                whenGoText.setText(stringDate);
                timeText.setText(stringTime);
            }

            reviewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO 리뷰입력 서비스 준비중입니다.
//                ((MainActivity)context).postingObject= data;
//                ((MainActivity)context).onFragmentSelected(4, null);

                //여기에 팝업 띄워야 함. 어뎁터에 팝업 띄울줄 몰라서 아직 못 함.
            }
        });
        }
        }
    }
