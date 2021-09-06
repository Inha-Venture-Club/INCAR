package ac.inhaventureclub.websocket.vo;

public class ClientMessage {
	
	// AUTO
	public final static int CONNECT = 1;
	
	// CHATTING ROOM
	public final static int JOIN_ROOM = 2;
	
	// SENDING
	public final static int MESSAGE = 3;

	

	public int code = 0;
	
	public String roomIdx;
	public String id;
	public String name;
	public String message;
	public String time;
	public int posting_idx = -1;
	
	@Override
	public boolean equals(Object obj) {
		if(!super.equals(obj)) {
			if(obj!=null) {
				if(obj instanceof ClientMessage) {
					return (roomIdx!=null
							&&((ClientMessage) obj).roomIdx!=null
							&&((ClientMessage) obj).roomIdx.equals(roomIdx)); 
				}else return false;
			}else
				return false;
		}else
			return true;
	}
}
