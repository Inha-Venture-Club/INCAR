package ac.inhaventureclub.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {
	@Id
	public int ADDRESS_INDEX;
	public String REGION;
	public String GU;
}
