// INCAR PLUS
/*
package ac.inhaventureclub.incar.object;

public class ClientMessage {
	public final static int CONNECT = 1;
	public final static int JOIN_ROOM = 2;
	public final static int MESSAGE = 3;

	public int code = 0;
	public String roomIdx;
	public String id;
	public String name;
	public String message;
	public String time;
	public int posting_idx = -1;


	public final static int MINE = 1;
	public final static int YOURS = 2;
	public final static int POSTING = 3;

	public int getClientMessageType(ClientMessage clientMessage, String myId){
		if(clientMessage.id.equals(myId)) return MINE; // 내 메시지
		else{
			if(clientMessage.posting_idx<0) return YOURS; // 니 메시지
			else return POSTING; // 포스팅 정보
		}
	}

}
*/