package ac.inhaventureclub.websocket.service;


import java.util.List;

import org.springframework.stereotype.Service;

import ac.inhaventureclub.vo.Chattingroom;

@Service
public interface ChattingroomService {
	
	/* find */
	public String findAll();
	public int findRoomIdxWithYourAndMyId(String myId, String yourId);
	public int findRoomIdxWithDriverAndGuest(String driver, String guest);
	public List<Chattingroom> findChattingroomWithDriver(String driver);
	public List<Chattingroom> findChattingroomWithGuest(String guest);
	public Chattingroom findChattingroomWithRoomIdx(int roomIdx);
	/* save */ 
	public String saveChattingroom(Chattingroom chattingroom);
	
	/* Chattingroom and Chatmessage */
	public String findYourIdWithRoomIdx(int roomIdx, String myId);
}
