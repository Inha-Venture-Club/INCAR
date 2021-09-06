package ac.inhaventureclub.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chattingroom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int ROOM_INDEX;
	public String DRIVER;
	public String GUEST;
}
