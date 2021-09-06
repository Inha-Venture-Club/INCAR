package ac.inhaventureclub.incar.object;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.ArrayList;

import ac.inhaventureclub.incar.R;

public class WordItemData {
    private static PostingObject PostingObject;
    public String departure;
    public String destination;
    public String user_name;
    public String WhenGo;
    //public Drawable profile;

//    public WordItemData(String departure, String destination,String user_name, String date, String time){
//        this.departure = departure;
//        this.destination = destination;
//        this.user_name = user_name;
//        this.date = date;
//        this.time = time;
//        //this.profile = profile;
//    }

    public String getDeparture(){
        return departure;
    }

    public String getDestination(){
        return destination;
    }

    public String getUser_name(){
        return user_name;
    }

    public String getWhenGo(){ return WhenGo;}
    public static ArrayList<WordItemData> contacts = new ArrayList<WordItemData>();


//    public Drawable getProfile(){
//        return profile;
//    }

    public static ArrayList<WordItemData> createContactList(int numContacts){


        for(int i=1; i<= numContacts; i++){
            contacts.add(new WordItemData());
//            contacts.add(new WordItemData("Inha University", "Juan Station",
//                    "그래놀라","2020/04/03", "18:00"));
//            contacts.add(new WordItemData("서울", "INHA University", "홍다솔", "2020/05/14","08:30"));
//            contacts.add(new WordItemData("수원", "INHA University", "박주원", "2020/04/14","09:30"));
//            contacts.add(new WordItemData("송도", "INHA University", "김진우", "2020/04/17","10:00"));
//            contacts.add(new WordItemData("인천시 부평구 삼산동", "INHA University", "조민경", "2020/04/29","09:00"));


//            String depart = dasdjald();
//            .
//            .
//
//
//
//            setRa(depart,d .... );
        }
        return contacts;
    }

//    private void setRa(String departure, String destination, String user_name, String date, String time){
//        contacts.add(departure,destination,user_name,date,time);
//    }
}
