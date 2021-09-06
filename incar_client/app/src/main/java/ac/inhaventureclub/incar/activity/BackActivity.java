package ac.inhaventureclub.incar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.fragment.AddressFragment;
import ac.inhaventureclub.incar.fragment.PostingBoardFragment;

import static ac.inhaventureclub.incar.fragment.PostingBoardFragment.ARRIVE_STATE;
import static ac.inhaventureclub.incar.fragment.PostingBoardFragment.DEPART_STATE;

public class BackActivity extends AppCompatActivity implements AddressFragment.OnAddressFragmentListener, PostingBoardFragment.OnPostingBoardListener {

    public ArrayList<Integer> addrSelectedList = new ArrayList<Integer>();
    public int state;

    public void setState(int state){ this.state = state;}
    public int getState(){ return state;}
    public void setAddrSelectedList(ArrayList<Integer> addrSelectedList){ this.addrSelectedList = addrSelectedList;}
    public ArrayList<Integer> getAddrSelectedList(){ return addrSelectedList;}


    /* fragment */
    AddressFragment addressFragment = new AddressFragment();
    PostingBoardFragment postingboardFragment = new PostingBoardFragment();
    /* Item */
    Button logoButton;
    Toolbar toolbar;
    Button btn_address_ok;
    /* Manager */
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        /* PostingBoardFragment로 부터 data를 받음 */
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            state = extras.getInt("state");
            addrSelectedList = extras.getIntegerArrayList("addrSelectedList");
//            Log.i("BackActivity★ state: ",Integer.toString(state));
//            Log.i("BackActivity★addrSelectedList: ",addrSelectedList.toString());

        }
        /* PostingBoardFragment로 부터 받은 data들을 AddressFragment로 전송 */
        Bundle bundle = new Bundle();
        bundle.putInt("state",state);
        bundle.putIntegerArrayList("addrSelectedList",addrSelectedList);
        if( state == DEPART_STATE) addressFragment.setAddrKeep(addrSelectedList,DEPART_STATE);
        if( state == ARRIVE_STATE) addressFragment.setAddrKeep(addrSelectedList,ARRIVE_STATE);
        addressFragment.setArguments(bundle);

        /* 초기 설정 */
        getSupportFragmentManager().beginTransaction().add(R.id.layout_appbarback_content,addressFragment).commit();


        /* ActionBar */
        toolbar = findViewById(R.id.toolbar_appbarback);
        logoButton = findViewById(R.id.ib_appbarback_mainlogo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* logo button */
        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BackActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
