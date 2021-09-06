package ac.inhaventureclub.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Job {
	@Id
	public int INDEX;
	public String NAME;
}
