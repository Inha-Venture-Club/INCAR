package ac.inhaventureclub.vo;

import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.List;

import javax.persistence.Entity;


//@Entity(name="user")
@Entity
public class User {
	@Id
	public String ID;
	public String PW;
	public String NAME;
	public int GENDER;
	public String BIRTH;
	public int JOB_IDX;
	public String INTRODUCTION;
	public String REJOINABLE_DATE;
	public String TOKEN_ID;
	
//	@OneToMany(mappedBy = "Posting")
//	public List<Posting> postings;
}
