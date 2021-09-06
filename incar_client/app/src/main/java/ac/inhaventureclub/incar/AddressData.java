package ac.inhaventureclub.incar;

// The class to save address data
public class AddressData {
    String region;
    String gu;

    public AddressData(String region, String gu){
        this.region = region;
        this.gu = gu;
    }

    public void setRegion(String region){this.region=region;}
    public void setFu(String gu){this.gu=gu;}
    public String getRegion(){return region;}
    public String getGu(){return gu;}

}
