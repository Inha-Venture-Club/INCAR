package ac.inhaventureclub.incar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.object.RequestObject;

public class RatingReceiveRecyclerAdapter extends RecyclerView.Adapter<RatingReceiveRecyclerAdapter.Holder> {

    private Context context;
    private List<RequestObject>list = new ArrayList<>();

    public RatingReceiveRecyclerAdapter(Context context, List<RequestObject> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public  Holder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(Holder holder, int position){
        int itemposition = position;
        if (list.get(itemposition) != null) {
            try {
                holder.onBind(list.get(itemposition));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
    public class Holder extends RecyclerView.ViewHolder{

        public TextView nameText;
        public TextView opinionText;

        public Holder(View view){
            super(view);
            nameText = (TextView) view.findViewById(R.id.rating_name);
            opinionText = (TextView) view.findViewById(R.id.rating_comment);

        }

        public void onBind(RequestObject data) throws ParseException{
            nameText.setText(data.USER_ID);
            if (incar.IS_GUEST==0){//운전자
                opinionText.setText(data.GUEST_OPINION);
            }else if (incar.IS_GUEST==1){
                opinionText.setText(data.DRIVER_OPINION);
            }
        }

    }
}





