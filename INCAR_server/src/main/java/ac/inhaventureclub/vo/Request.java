package ac.inhaventureclub.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Request {
	@Id
	public int REQUEST_INDEX;
	public int POSTING_IDX;
	public String USER_ID;
	public String REQ_TIME;
	public String DRIVER_RATING;
	public String DRIVER_OPINION;
	public String DRIVER_REG_TIME;
	public String GUEST_RATING;
	public String GUEST_OPINION;
	public String GUEST_REG_TIME;
//	
//	@ManyToOne
//	@JoinColumn(name = "POSTING")
//	private Posting posting;
//	
	
//	@ManyToOne(targetEntity = Posting.class, fetch = FetchType.EAGER)
//	@JoinColumn(name = "POSTING_REQ", nullable = true)
//	private Posting posting_req;

}
