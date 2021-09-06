package ac.inhaventureclub.incar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import ac.inhaventureclub.incar.adapter.NoticeRecyclerAdapter;
import ac.inhaventureclub.incar.databinding.FragmentNoticeBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.NoticeObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {
    private RecyclerView recyclerView;
    private NoticeRecyclerAdapter adapter;
    private FragmentNoticeBinding noticeBinding;

    NoticeObject noticeObject = new NoticeObject();
    public String result;

    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);


        /* Binding */
        noticeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notice, container, false);
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        // Inflate the layout for this fragment

        recyclerView = view.findViewById(R.id.notice_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }


        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                result = HttpManager.getData("/notice"); // null: error, !null: ok
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (result != null) {
                    Gson gson = new Gson();
                    ArrayList<NoticeObject> noticeObjectList = gson.fromJson(result, new TypeToken<ArrayList<NoticeObject>>() {
                    }.getType());
                    adapter = new NoticeRecyclerAdapter(getActivity(), noticeObjectList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "null 입니다", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.INTERNET},
                    123);
        }

        return view;
    }
    //화면에 터치하는걸 click이라고 하는데, click하면 여기 안에있는 함수에 event를 던져주는거야.


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //requestCode==123:: 호출된거
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }
}