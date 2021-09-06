package ac.inhaventureclub.incar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.object.PostingObject;

import static ac.inhaventureclub.incar.R.layout.postingboard_item;


public class PostingManagementMyPostingRecyclerAdapter extends RecyclerView.Adapter<PostingManagementMyPostingRecyclerAdapter.Holder> {

    private Context context;
    private ArrayList<PostingObject> list = new ArrayList<>();
    private ItemClick itemClick;
    public interface ItemClick{
        public void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    public PostingManagementMyPostingRecyclerAdapter(Context context, ArrayList<PostingObject> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(postingboard_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int itemposition = position;

        if (list.get(itemposition) != null) {
            try {
                holder.onBind(list.get(itemposition));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(itemClick != null){
                    itemClick.onClick(v, position);
                }

            }
        });
    }


    @Override
    public int getItemCount(){
        return list.size();
    }

    public void setList(ArrayList<PostingObject> list) {
        this.list = list;
    }

    public ArrayList<PostingObject> getList() {
        return list;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView departureText;
        public TextView destinationText;
        public TextView user_nameText;
        public TextView whenGoText;
        public TextView timeText;
        public final View mView;

        public Holder(View view) {
            super(view);
            departureText = view.findViewById(R.id.postingboard_departure);
            destinationText = (TextView) view.findViewById(R.id.postingboard_destination);
            user_nameText = (TextView) view.findViewById(R.id.postingboard_name);
            whenGoText = (TextView) view.findViewById(R.id.postingboard_date);
            timeText = view.findViewById(R.id.postingboard_time);
            mView = view;
        }

        public void onBind(PostingObject data)throws ParseException{
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
        }
    }

}
