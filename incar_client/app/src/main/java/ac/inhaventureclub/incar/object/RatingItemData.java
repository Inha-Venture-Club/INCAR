package ac.inhaventureclub.incar.object;

import java.util.ArrayList;

public class RatingItemData {
    public String name;
    public String conmment;

    public RatingItemData(String name, String conmment){
        this.name = name;
        this.conmment = conmment;
    }

    public static ArrayList<RatingItemData> createContactList(int numContacts){
        ArrayList<RatingItemData> contacts = new ArrayList<RatingItemData>();

        for(int i = 1; i<= numContacts; i++){
            contacts.add(new RatingItemData("김 XX", "친절해요"));
        }

        return contacts;
    }
}
