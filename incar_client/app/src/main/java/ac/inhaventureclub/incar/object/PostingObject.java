package ac.inhaventureclub.incar.object;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostingObject implements Comparable<PostingObject> {

    public int POSTING_INDEX;
    public String WHEN_GO;
    public int DEPARTURE_IDX;
    public String DEPARTURE_DETAIL;
    public String DEPARTURE_X;
    public String DEPARTURE_Y;
    public int ARRIVE_IDX;
    public String ARRIVE_DETAIL;
    public String ARRIVE_X;
    public String ARRIVE_Y;
    public int IS_GUEST;
    public int PRICE;
    public String CAR_TYPE;
    public String EXPLANATION;
    public int FLAG;
    public String USER_ID;
    public String REG_TIME;
    public String REMARK;

    public static boolean ASC = true;
    @Override
    public int compareTo(PostingObject o) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
        int p_date = 0;
        int o_date = 0;
        int compare;
        if (this.WHEN_GO != null && o !=null && o.WHEN_GO !=null) {
            Date date = null;//시간형태로 가져와줌
            try {
                date = simpleDateFormat.parse(this.WHEN_GO);
                Date objDate = simpleDateFormat.parse(o.WHEN_GO);
                compare = date.compareTo(objDate); //compare ==1 this가 나중, compare == -1이면 this가 먼저
                Log.i("compareTo", "compareTo: "+this.WHEN_GO+", "+o.WHEN_GO+", "+compare);
                if (ASC){
                    return compare;
                }else {
                    return (compare==1? -1:compare ==0? 0:1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return 1;
            }
        }else {
            return 1;
        }
    }
}
