
// INCAR PLUS
/*
package ac.inhaventureclub.incar.activity;
//INCAR PLUS
/*
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.adapter.ChattingRecyclerAdapter;
import ac.inhaventureclub.incar.application.incar;
import ac.inhaventureclub.incar.databinding.ActivityChattingBinding;
import ac.inhaventureclub.incar.manager.HttpManager;
import ac.inhaventureclub.incar.object.Chatmessage;
import ac.inhaventureclub.incar.object.Chattingroom;
import ac.inhaventureclub.incar.object.ClientMessage;

public class ChattingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChattingRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ClientMessage> messages = new ArrayList<>();

    public String result;

    private ActivityChattingBinding chattingBinding;

    public final static int DRIVER = 0;
    public final static int GUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//
//        chattingBinding = DataBindingUtil.setContentView(this, R.layout.activity_chatting);
//
//        recyclerView = chattingBinding.messageRecycler;
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
//        int userType = (incar.IS_GUEST);
//
//        switch (userType){
//            case DRIVER:
//                new AsyncTask() {
//                    @Override
//                    protected Object doInBackground(Object[] objects) {
//                        HttpManager.connectWebSocket();
//                        ClientMessage clientMessage = new ClientMessage();
//                        clientMessage.code=ClientMessage.JOIN_ROOM;
//                        clientMessage.roomIdx = null;
//                        clientMessage.id = (incar.USER.ID);
//                        clientMessage.name = (incar.USER.NAME);
//                        clientMessage.message = null;
//                        clientMessage.posting_idx = -1;
//                        HttpManager.sendMsg(new Gson().toJson(clientMessage));
//                        result = HttpManager.postData("","chatmessagesWithRoomIdx");
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Object o) {
//                        super.onPostExecute(o);
//                        if (result != null) {
//                            Gson gson = new Gson();
//                            List<Chatmessage> chatmessages = gson.fromJson(result, new TypeToken<ArrayList<Chatmessage>>() {
//                            }.getType());
//                            adapter = new ChattingRecyclerAdapter(getApplicationContext(), chatmessages);
//                            recyclerView.setAdapter(adapter);
//                        } else {
//                            Toast.makeText(ChattingActivity.this, "result == null 입니다.", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//
//                }.execute();
//                break;
//            case GUEST:
//                new AsyncTask() {
//                    @Override
//                    protected Object doInBackground(Object[] objects) {
//                        HttpManager.connectWebSocket();
//                        ClientMessage clientMessage = new ClientMessage();
//                        clientMessage.code=ClientMessage.JOIN_ROOM;
//                        clientMessage.roomIdx = null;
//                        clientMessage.id = (incar.USER.ID);
//                        clientMessage.name = (incar.USER.NAME);
//                        clientMessage.message = null;
//                        clientMessage.posting_idx = -1;
//                        HttpManager.sendMsg(new Gson().toJson(clientMessage));
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Object o) {
//                        super.onPostExecute(o);
//                        if (result != null) {
//                            Gson gson = new Gson();
//                            ArrayList<ClientMessage> messagesList = gson.fromJson(result, new TypeToken<ArrayList<ClientMessage>>() {
//                            }.getType());
//                            adapter = new ChattingRecyclerAdapter(getApplicationContext(), messagesList);
//                            recyclerView.setAdapter(adapter);
//                        } else {
//                            Toast.makeText(ChattingActivity.this, "result == null 입니다.", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//
//                }.execute();
//                break;
//        }
//
//        new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                HttpManager.connectWebSocket();
//                ClientMessage clientMessage = new ClientMessage();
//                clientMessage.code=ClientMessage.JOIN_ROOM;
//                clientMessage.roomIdx = null;
//                clientMessage.id = (incar.USER.ID);
//                clientMessage.name = (incar.USER.NAME);
//                clientMessage.message = null;
//                clientMessage.posting_idx = -1;
//                HttpManager.sendMsg(new Gson().toJson(clientMessage));
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//                if (result != null) {
//                    Gson gson = new Gson();
//                    ArrayList<ClientMessage> messagesList = gson.fromJson(result, new TypeToken<ArrayList<ClientMessage>>() {
//                    }.getType());
//                    adapter = new ChattingRecyclerAdapter(getApplicationContext(), messagesList);
//                    recyclerView.setAdapter(adapter);
//                } else {
//                    Toast.makeText(ChattingActivity.this, "result == null 입니다.", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//        }.execute();
//
//        if()

    }
}
*/