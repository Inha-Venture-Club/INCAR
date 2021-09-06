package ac.inhaventureclub.incar;/*package ac.inhaventureclub.incar;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {
    ArrayList<AddressData> datas=new ArrayList<AddressData>();

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        setContentView(R.layout.activity_selectaddress);

        datas.add(new AddressData("서울특별시", "양천구"));
        datas.add(new AddressData("서울특별시", "영등포구"));

        listView=(ListView)findViewById(R.id.listview_address_gu);
        listView=(ListView)findViewById(R.id.listview_address_region);

        AddressDataAdapter adapter = new AddressDataAdapter(getLayoutInflater(), datas);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public Toolbar getSupportActionBar() {
        return supportActionBar;
    }
}
*/