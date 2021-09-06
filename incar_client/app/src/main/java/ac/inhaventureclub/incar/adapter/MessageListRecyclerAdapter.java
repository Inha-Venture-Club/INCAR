
// INCAR PLUS
/*
package ac.inhaventureclub.incar.adapter;

import android.content.Context;
import android.content.Intent;
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
import ac.inhaventureclub.incar.activity.PostActivity;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.object.Chatmessage;
import ac.inhaventureclub.incar.object.Chattingroom;
import ac.inhaventureclub.incar.object.ChattingroomAndMessages;
import ac.inhaventureclub.incar.object.ChattingroomsAndLastmessage;
import ac.inhaventureclub.incar.object.PostingObject;

import static ac.inhaventureclub.incar.R.layout.message_item;

public class MessageListRecyclerAdapter extends RecyclerView.Adapter<MessageListRecyclerAdapter.Holder> {
    public final static int DRIVER = 0;
    public final static int GUEST = 1;

    // AUTO
    public final static int CONNECT = 1;

    // CHATTING ROOM
    public final static int JOIN_ROOM = 2;

    // SENDING
    public final static int MESSAGE = 3;

    private Context context;
    private ChattingroomAndMessages chattingroomAndMessages = new ChattingroomAndMessages();
    private List<ChattingroomsAndLastmessage> chattingroomsAndLastmessages = new ArrayList<>();

    private ItemClick itemClick;
    public interface ItemClick{
        public void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    public MessageListRecyclerAdapter(Context context, List<ChattingroomsAndLastmessage> chattingroomsAndLastmessages){
        this.context = context;
        this.chattingroomsAndLastmessages = chattingroomsAndLastmessages;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(message_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position){
        int itemposition = position;

        if(chattingroomsAndLastmessages.get(itemposition) != null){
            try{
                holder.onBind(chattingroomsAndLastmessages.get(itemposition));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick !=null){
                    itemClick.onClick(v, position);
                }

            }
        });
    }

    @Override
    public int getItemCount(){return chattingroomsAndLastmessages.size();}

    public void setList(List<ChattingroomsAndLastmessage> chattingroomsAndLastmessages){this.chattingroomsAndLastmessages = chattingroomsAndLastmessages;}

    public List<ChattingroomsAndLastmessage> getList() {return chattingroomsAndLastmessages;}

    public class Holder extends RecyclerView.ViewHolder{

        public TextView roomNameText;
        public TextView timeText;
        public TextView finalmessageText;
        public final View mView;

        public Holder(View view){
            super(view);
            roomNameText = view.findViewById(R.id.message_list_room_name);
            timeText = view.findViewById(R.id.message_list_time);
            finalmessageText = view.findViewById(R.id.message_list_final_message);
            mView = view;
        }

        public void onBind(ChattingroomsAndLastmessage data)throws ParseException{
            roomNameText.setText(data.room_index);
            finalmessageText.setText(data.message);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
            if (data.time != null){
                Date date = simpleDateFormat.parse(data.time);//시간형태로 가져와줌
                SimpleDateFormat viewFormat = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm", Locale.KOREA);
                String stringTime = viewFormat.format(date);
                timeText.setText(stringTime);
            }
        }
    }

}
*/