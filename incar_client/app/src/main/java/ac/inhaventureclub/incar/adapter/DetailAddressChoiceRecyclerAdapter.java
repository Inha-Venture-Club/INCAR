package ac.inhaventureclub.incar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.object.IncarMapAddress;

public class DetailAddressChoiceRecyclerAdapter extends RecyclerView.Adapter<DetailAddressChoiceRecyclerAdapter.Holder>{

    private Context context;
    private ArrayList<IncarMapAddress> incarMapAddressesArrayList = new ArrayList<>();

    private ItemClick itemClick;
    public interface ItemClick{
        public void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }
    public DetailAddressChoiceRecyclerAdapter(Context context, ArrayList<IncarMapAddress> incarMapAddressesArrayList){
        this.context = context;
        this.incarMapAddressesArrayList = incarMapAddressesArrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_address_choice_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int itemposition = position;

        if(incarMapAddressesArrayList.get(itemposition) != null){
            try{
                if (getItemCount()!=0){
                    holder.onBind(incarMapAddressesArrayList.get(itemposition));
                }else {
                    //holder.onBind();
                }

            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v, position);
                }
            }
        });

    }

    @NonNull
    @Override

    public int getItemCount(){
        return incarMapAddressesArrayList.size();
    }

    public  void setList(ArrayList<IncarMapAddress> incarMapAddressesArrayList){
        this.incarMapAddressesArrayList = incarMapAddressesArrayList;
    }
    public class Holder extends RecyclerView.ViewHolder{
        public TextView detailAddress;
        public TextView detailDistance;

        public Holder(View view){
            super(view);
            detailAddress = view.findViewById(R.id.detail_address_choice_item_text);
            detailDistance = view.findViewById(R.id.detail_address_choice_item_distance);
        }

        public void onBind(IncarMapAddress data)throws ParseException{

            // //TODO recycleView 변수 바뀌면 바꾸면 됨
            detailAddress.setText(data.place_name);
            detailDistance.setText(data.distance+"m");
        }
    }


}
