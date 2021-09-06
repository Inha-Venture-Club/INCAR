package ac.inhaventureclub.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Notice {
	@Id
	public int NOTICE_INDEX;
	public String TITLE;
	public String DESCRIPTION;
	public String DATE;
}
