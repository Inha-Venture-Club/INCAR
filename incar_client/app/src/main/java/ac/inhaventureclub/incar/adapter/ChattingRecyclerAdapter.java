package ac.inhaventureclub.incar.adapter;
//INCAR PLUS
/*
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.object.Chatmessage;

public class ChattingRecyclerAdapter extends RecyclerView.Adapter<ChattingRecyclerAdapter.Holder> {

    private Context context;
    private ArrayList<Chatmessage> messages = new ArrayList<>();

    public ChattingRecyclerAdapter(Context context, ArrayList<Chatmessage> messages){
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        Holder holder = new Holder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int itemposition = position;
        if(messages.get(itemposition) != null){
            try{
                holder.onBind(messages.get(itemposition));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setList(ArrayList<Chatmessage> messages){
        this.messages = messages;
    }

    public ArrayList<Chatmessage> getList(){
        return messages;
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView messageText;
        public TextView timeText;
        public LinearLayout layoutBackground;

        public Holder(View view){
            super(view);
            messageText = view.findViewById(R.id.chat_message);
            timeText = view.findViewById(R.id.chat_time);
            layoutBackground = view.findViewById(R.id.layout_background);
        }

        public void onBind(Chatmessage data)throws ParseException{
            if(data.SENDER_ID.equals(incar.USER.ID)){
                layoutBackground.setBackgroundResource(R.drawable.round_corner_skyblue);
                layoutBackground.setGravity(Gravity.END);
                messageText.setGravity(Gravity.START);
                timeText.setGravity(Gravity.END);
            }else{
                layoutBackground.setBackgroundResource(R.drawable.round_corner);
                layoutBackground.setGravity(Gravity.START);
                messageText.setGravity(Gravity.END);
                timeText.setGravity(Gravity.START);
            }
            messageText.setText(data.MESSAGE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
            if (data.TIME != null){
                Date date = simpleDateFormat.parse(Long.toString(data.TIME));//시간형태로 가져와줌
                SimpleDateFormat viewFormat = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm", Locale.KOREA);
                String stringTime = viewFormat.format(date);
                timeText.setText(stringTime);
            }
        }

    }
}
*/