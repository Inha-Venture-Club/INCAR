package ac.inhaventureclub.incar.util;

import java.util.ArrayList;

public class WordItemData {
    public String departure;
    public String destination;
    public String date;
    public String time;

    public WordItemData(String departure, String destination, String date, String time){
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.time = time;
    }

    public static ArrayList<WordItemData> createContactList(int numContacts){
        ArrayList<WordItemData> contacts = new ArrayList<WordItemData>();

        for(int i=1; i<= numContacts; i++){
            contacts.add(new WordItemData("Inha University", "Juan Station",
                    "2020/04/03", "18:00"));
        }
        return contacts;
    }
}
