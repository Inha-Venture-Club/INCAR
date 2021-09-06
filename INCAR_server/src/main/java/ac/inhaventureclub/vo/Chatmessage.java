package ac.inhaventureclub.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chatmessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int CHAT_INDEX;
	public int CM_CODE;
	public int ROOM_IDX;
	public String SENDER_ID;
	public String SENDER_NAME;
	public String MESSAGE;
	public Long TIME;
	public int POSTING_IDX;
}
