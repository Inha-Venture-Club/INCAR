package ac.inhaventureclub.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

@Entity
public class Posting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
//	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "USER_ID", insertable = false, updatable = false, referencedColumnName = "ID")
//	public User user;
}
