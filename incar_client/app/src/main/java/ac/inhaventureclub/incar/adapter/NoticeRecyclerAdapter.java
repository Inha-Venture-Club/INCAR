package ac.inhaventureclub.incar.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.object.NoticeObject;

import static ac.inhaventureclub.incar.R.layout.noticeboard_item;

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.Holder> {

    private Context context;
    private ArrayList<NoticeObject> list = new ArrayList<>();

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public NoticeRecyclerAdapter(Context context, ArrayList<NoticeObject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(noticeboard_item, parent, false);
        Holder holder = new Holder(view);
        return holder;

    }


    public void setList(ArrayList<NoticeObject> list) {
        this.list = list;
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
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        else return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private boolean visible = false;
        public TextView title_Text;
        public TextView dateText;
        public TextView descriptionText;
        public View descriptionSreen2;
        public ImageView look_description1;


        private int position;

        Holder(View view) {
            super(view);
            title_Text = (TextView) view.findViewById(R.id.notice_title);
            dateText = (TextView) view.findViewById(R.id.notice_date);
            descriptionText = (TextView) view.findViewById(R.id.notice_description);
            descriptionSreen2 = view.findViewById(R.id.description);
            look_description1 = view.findViewById(R.id.look_description);

        }

        public void onBind(NoticeObject data) throws ParseException { //데이터 입힘
            title_Text.setText(data.TITLE);
            descriptionText.setText(data.DESCRIPTION);
            this.position = position;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            if (data.DATE != null) {
                Date date = simpleDateFormat.parse(data.DATE);//시간형태로 가져와줌
                SimpleDateFormat viewFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                String stringDate = viewFormat.format(date);
                dateText.setText(stringDate);
            }

            look_description1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    visible = !visible;
                    descriptionSreen2.setVisibility(visible? View.VISIBLE : View.GONE);
                }
            });
        }
    }
}
