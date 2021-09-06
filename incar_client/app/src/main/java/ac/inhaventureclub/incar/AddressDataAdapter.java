package ac.inhaventureclub.incar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AddressDataAdapter extends BaseAdapter {

    ArrayList<AddressData> datas;
    LayoutInflater inflater;

    public AddressDataAdapter(LayoutInflater inflater, ArrayList<AddressData> datas){
        this.datas = datas;
        this.inflater = inflater;
    }

    //AddressDataAdapter 객체가 만들어 낼 View의 개수를 리턴하는 메소드
    // ListVIew에 나열되는 목록의 개수를 의미함
    // 즉, datas라는 ArrayList의 개수를 지칭
    // 특별한 경우가 아니라면 보통 datas의 size를 리턴
    @Override
    public int getCount() {
        return datas.size();
    }

    //AddressDataAdapter의 특정위치(position)에 해당하는 Data를 리턴하는 메소드
    // datas의 특정 위치에 해당하는 AddressData 객체를 리턴
    // ListView의 position은 가장 위에 목록부터(0,1,2,3...)으로 지정됨
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    //특정위치(position)의 data(AddressData)를 지칭하는 아이디를 리턴하는 메소드
    // 특별한 경우가 아니라면 보통은 data의 위치를 아이디로 사용하므로
    // 전달받은 position을 그대로 리턴함
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 이 커스텀 Adapter 클래스 설계에서 가장 중요한 메소드로서
    // ListView에서 한 항목의 View 모양과 값을 설정하는 메소드
    // AdapterView의 일종인 ListView는 설정된 Adapter(즉 AddressDataAdapter)에게
    // 대량의 데이터들(datas: AddressData 객체 배열)을 보여주기에 적절한 View로 만들고
    // 해당 데이터의 값으로 만들어 내는 핵심 메소드로서 ListView를 위에 만든 getCount()메소르의 리턴값만큼
    // getView를 요구하여 목록에 나열함
    // 즉, 이 메소드의 리턴값인 View가 ListView의 한 항목을 의미한다.
    // 우리는 리턴될 View의 모양을 res>>layout>>list_row.xml팡리을 만들어서 설계한다.
    // 첫번째 파라미터 position: ListView에 놓여질 목록의 위치
    // 두번째 파라미너 convertview: 리턴될 View로서 List의 한 항목의 View객체
    // 세번째 파라미너 parent: 이 Adapter객체가 설정된 AdapterView객체(즉, ListView)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1. New View: ListView의 목록 하나의 모양을 담당하는 View 객체를 만들어 낸다.
        // data가 5개인데 굳이 100개를 만들 필요는 없으므로, convertview가 null이면 새로 만들어야 한다.
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row,null);
        }

        //2. Bind View: 만들어진 View에 해당하는 Data를 연결한다.
        // 이제 converView는 View 객체 상태임
        TextView text_region = (TextView)convertView.findViewById(R.id.text_region);
        TextView text_gu = (TextView)convertView.findViewById(R.id.text_gu);

        text_region.setText(datas.get(position).getRegion());
        text_gu.setText(datas.get(position).getGu());

        return convertView;
    }
}
