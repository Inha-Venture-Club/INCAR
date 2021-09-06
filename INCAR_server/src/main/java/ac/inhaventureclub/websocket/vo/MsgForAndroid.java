package ac.inhaventureclub.websocket.vo;

public class MsgForAndroid {
	
	public final static int SUCCESS = 1;
	public final static int FAILED = -1;
	public final static int EXCEPTION = 0;

	public int code;
	public String code_message;
	public String clientMessage;
}
