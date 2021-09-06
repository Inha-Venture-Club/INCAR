package ac.inhaventureclub.websocket.serviceImple;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ac.inhaventureclub.vo.Chatmessage;
import ac.inhaventureclub.websocket.repository.ChatmessageRepository;
import ac.inhaventureclub.websocket.service.ChatmessageService;

@Service("chatmessageservice")
public class ChatmessageServiceImple implements ChatmessageService{
	
	@Autowired
	ChatmessageRepository chatmessageRepository;
	
	
	@Override
	public String findMessagesWithRoomidx(int roomId) {
		return new Gson().toJson(chatmessageRepository.findMessagesWithRoomidx(roomId));
	}
	
	@Override
	public String findMessagesWithLastTimeAndRoomIdx(String lastTime, int roomIdx) {
		return new Gson().toJson(chatmessageRepository.findMessagesWithLastTimeAndRoomIdx(Long.parseLong(lastTime), roomIdx));
	}
	
	@Override
	public Chatmessage saveChatmessage(Chatmessage chatmessage) {
		return chatmessageRepository.saveAndFlush(chatmessage);
		
	}

	@Override
	public Chatmessage findLastMessageWithSenderId(String senderId) {
		List<Chatmessage> chatmessages = new ArrayList<Chatmessage>();
		chatmessages = chatmessageRepository.findMessagesWithSenderId(senderId);
		if(chatmessages.isEmpty()) {
			return null;
		}else {
			System.out.println("LastMessage: "+chatmessages);
			int listSize = chatmessages.size();
			if(listSize <0 ) return null;
			else return chatmessages.get(listSize);
		}
		
	}
	
	
}
