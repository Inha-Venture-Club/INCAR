package ac.inhaventureclub.websocket.service;

import org.springframework.stereotype.Service;

import ac.inhaventureclub.vo.Chatmessage;

@Service
public interface ChatmessageService {
	/* find */
	public String findMessagesWithRoomidx(int roomIdx);
	public String findMessagesWithLastTimeAndRoomIdx(String lastTime, int roomIdx);
	public Chatmessage findLastMessageWithSenderId(String senderId);
	
	/* save */
	public Chatmessage saveChatmessage(Chatmessage chatmessage);

}
