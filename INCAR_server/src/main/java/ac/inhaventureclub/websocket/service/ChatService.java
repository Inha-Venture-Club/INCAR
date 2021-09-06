package ac.inhaventureclub.websocket.service;
//package ac.inhaventureclub.service;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sun.org.apache.regexp.internal.recompile;
//
//import ac.inhaventureclub.vo.Chattingroom;
//
//@Service
//public interface ChatService {
//	private final ObjectMapper objectMapper;
//	private Map<String, Chattingroom> chatRooms;
//	
//	@PostConstruct
//	private void init() {
//		chatRooms = new LinkedHashMap<String, Chattingroom>();
//	}
//	
//	public List<Chattingroom> findAllRoom() {
//		return new ArrayList<Chattingroom>(chatRooms.values());
//	}
//	
//	public Chattingroom findRoomById(String roomId) {
//		return chatRooms.get(roomId);
//	}
//	
//	
//	
//	
//}
