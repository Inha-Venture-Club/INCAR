package ac.inhaventureclub.websocket.serviceImple;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ac.inhaventureclub.vo.Chattingroom;
import ac.inhaventureclub.websocket.repository.ChattingroomRepository;
import ac.inhaventureclub.websocket.service.ChattingroomService;

@Service("chattingroomservice")
public class ChattingroomServiceImple implements ChattingroomService{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	ChattingroomRepository chattingroomRepository;

	@Override
	public String findAll() {
		return new Gson().toJson(chattingroomRepository.findAll()); 
	}
	
	
	@Override
	public String saveChattingroom(Chattingroom chattingroom) {
		Chattingroom resultChattingroom = chattingroomRepository.saveAndFlush(chattingroom);
		return new Gson().toJson(resultChattingroom);
	}

	/* Chattingroom and Chatmessage */
	@Override
	public String findYourIdWithRoomIdx(int roomIdx, String myId) {
		System.out.println("findYourIdWithRoomIdx- myId: "+myId);
		Chattingroom chattingroom = chattingroomRepository.findChattingroomWithRoomIdx(roomIdx);
		String driver = chattingroom.DRIVER;
		System.out.println("findYourIdWithRoomIdx- driver: "+driver);
		String guest = chattingroom.GUEST;
		System.out.println("findYourIdWithRoomIdx- guest: "+guest);
		if(myId == driver) return guest;
		else if(myId == guest) return driver;
		else return null;
	}

	@Override
	public int findRoomIdxWithYourAndMyId(String myId, String yourId) {
		Chattingroom cr = chattingroomRepository.findChattingroomWithDriverAndGuest(myId, yourId);
		if(cr==null) {
			cr = chattingroomRepository.findChattingroomWithDriverAndGuest(yourId, myId);
			if(cr == null) {
				return -1;
			}
			else return cr.ROOM_INDEX;
		}else return cr.ROOM_INDEX;
	}

	@Override
	public int findRoomIdxWithDriverAndGuest(String driver, String guest) {
		Chattingroom cr = chattingroomRepository.findChattingroomWithDriverAndGuest(driver, guest);
		if(cr==null) {
			return -1;
		}else return cr.ROOM_INDEX;
	}


	@Override
	public Chattingroom findChattingroomWithRoomIdx(int roomIdx) {
		Chattingroom chattingroom = new Chattingroom();
		chattingroom = chattingroomRepository.findChattingroomWithRoomIdx(roomIdx);
		return chattingroom;
	}


	@Override
	public List<Chattingroom> findChattingroomWithDriver(String driver) {
		return chattingroomRepository.findChattingroomWithDriver(driver);
	}


	@Override
	public List<Chattingroom> findChattingroomWithGuest(String guest) {
		return chattingroomRepository.findChattingroomWithGuest(guest);
	}
	
	
}
