// INCAR PLUS
/*

package ac.inhaventureclub.incar.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.activity.ChattingActivity;
import ac.inhaventureclub.incar.activity.PostActivity;
import ac.inhaventureclub.incar.adapter.MessageListRecyclerAdapter;
import ac.inhaventureclub.incar.adapter.PostingManagementMyPostingRecyclerAdapter;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.interfaceIncar.MessageInterface;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.Chatmessage;
import ac.inhaventureclub.incar.object.Chattingroom;
import ac.inhaventureclub.incar.object.ChattingroomAndMessages;
import ac.inhaventureclub.incar.object.ChattingroomsAndLastmessage;
import ac.inhaventureclub.incar.object.ClientMessage;
import ac.inhaventureclub.incar.object.PostingObject;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


 //A simple {@link Fragment} subclass.



public class MessageListFragment extends Fragment {
    public final static int DRIVER = 0;
    public final static int GUEST = 1;
    private RecyclerView recyclerView;
    private MessageListRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostingObject> list = new ArrayList<>();

    public String result;
    private incar incar;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        Log.i(TAG,"onCreateView");
        recyclerView = view.findViewById(R.id.message_list_recycler);
        recyclerView.setHasFixedSize(true);
        HttpManager.createClient();
        incar  =  (incar)getActivity().getApplicationContext();
        Log.i(TAG,"incar: "+incar);



        // Inflate the layout for this fragment

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.d("MessageListFragment: ","doInBackground");
                int userType = (incar.IS_GUEST);
                switch (userType){
                    case DRIVER:
                        result = HttpManager.postData(incar.USER.ID,"chattingroomsAndLastmessageWithDriver");
                        break;
                    case GUEST:
                        result = HttpManager.postData(incar.USER.ID,"chattingroomsAndLastmessageWithGuest");
                        break;
                }

                // 본인의 상태를 전송
                ClientMessage clientMessage = new ClientMessage();
                clientMessage.code=ClientMessage.JOIN_ROOM;
                clientMessage.roomIdx = null;
                clientMessage.id = (incar.USER.ID);
                clientMessage.name = (incar.USER.NAME);
                clientMessage.message = null;
                clientMessage.posting_idx = -1;
                HttpManager.sendMsg(new Gson().toJson(clientMessage));

//                // 채팅 리스트를 가져옴
//                result = HttpManager.postData((incar.USER.ID),"chattingrooomsWithDriver");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.d("MessageListFragment: ","onPostExecute");
                if (result != null) {
                    Gson gson = new Gson();
                    Log.i("[MessageistFragment]","onPostExecute-result: ");
                    List<ChattingroomsAndLastmessage> chattingroomsAndLastmessage =  gson.fromJson(result, new TypeToken<ArrayList<ChattingroomsAndLastmessage>>() {}.getType());
                    adapter = new MessageListRecyclerAdapter(getActivity().getApplicationContext(), chattingroomsAndLastmessage );
                    recyclerView.setAdapter(adapter);

                    adapter.setItemClick(new MessageListRecyclerAdapter.ItemClick() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), ChattingActivity.class);
                            intent.putExtra("roomIdx", chattingroomsAndLastmessage.get(position).room_index);
                            startActivity(intent);
                            Toast.makeText(getContext(), "현재 선택된 아이템 : "+position, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "result == null 입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();


        return view;
    }

    private final String TAG = MessageListFragment.class.getSimpleName();


    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
        incar.setMessageInterface(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        MessageInterface messageInterface = new MessageInterface() {
            @Override
            public void onMessage(String message) {
//                List<ChattingroomsAndLastmessage> chattingroomsAndLastmessage =  new Gson().fromJson(message, new TypeToken<ArrayList<ChattingroomsAndLastmessage>>() {}.getType());
//                adapter = new MessageListRecyclerAdapter(getActivity().getApplicationContext(), chattingroomsAndLastmessage );
//                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onClosed() {

            }

            @Override
            public void onConnect() {

            }
        };
        incar.setMessageInterface(messageInterface);
    }
}
*/