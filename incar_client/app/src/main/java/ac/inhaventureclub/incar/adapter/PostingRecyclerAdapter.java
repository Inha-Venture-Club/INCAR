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
import java.util.List;
import java.util.Locale;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.object.PostingObject;

import static ac.inhaventureclub.incar.R.layout.postingmanagement_item;

public class PostingRecyclerAdapter extends RecyclerView.Adapter<PostingRecyclerAdapter.Holder> {

    private Context context;
    private List<PostingObject> list = new ArrayList<>();

    public PostingRecyclerAdapter(Context context, List<PostingObject> list) {
        this.context = context;
        this.list = list;
    }

    private ItemClick itemClick;
    public interface ItemClick{
        public void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(postingmanagement_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
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
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView departureText;
        public TextView destinationText;
        public TextView user_nameText;
        public TextView whenGoText;
        public final View mView;
        //public TextView timeText;

        public Holder(View view) {
            super(view);
            departureText = (TextView) view.findViewById(R.id.postingboard_departure);
            destinationText = (TextView) view.findViewById(R.id.postingboard_destination);
            user_nameText = (TextView) view.findViewById(R.id.postingboard_name);
            whenGoText = (TextView) view.findViewById(R.id.postingboard_date);
            mView = view;
        }

        public void onBind(PostingObject data) throws ParseException { //데이터 입힘
            departureText.setText(data.DEPARTURE_DETAIL);
            destinationText.setText(data.ARRIVE_DETAIL);
            user_nameText.setText(data.USER_ID);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDhhmm");
            if (data.WHEN_GO != null) {
                Date date = simpleDateFormat.parse(data.WHEN_GO);//시간형태로 가져와줌
                SimpleDateFormat viewFormat = new SimpleDateFormat("YYYY/MM/DD \n a hh:mm", Locale.KOREA);
                String stringDate = viewFormat.format(date);
                Log.d("라라라라라라라라ㅏㅏ라라", "onBind: " + stringDate);
                whenGoText.setText(stringDate);
            }

        }
    }
}