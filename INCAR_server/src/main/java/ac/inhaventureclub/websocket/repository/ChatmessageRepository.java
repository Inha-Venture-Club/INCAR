package ac.inhaventureclub.websocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import ac.inhaventureclub.vo.Chatmessage;

@Repository
public interface ChatmessageRepository extends JpaRepository<Chatmessage, Long>{
	
	/* select */
	@Query("select m from Chatmessage m where m.ROOM_IDX =:roomIdx")
	List<Chatmessage> findMessagesWithRoomidx(@Param("roomIdx") int roomIdx);

	@Query("select m from Chatmessage m where m.TIME >:lastTime and m.ROOM_IDX =:roomIdx")
	List<Chatmessage> findMessagesWithLastTimeAndRoomIdx(@Param("lastTime") Long lastTime,@Param("roomIdx") int roomIdx);

	@Query("select cm from Chatmessage cm where cm.SENDER_ID =:senderId")
	List<Chatmessage> findMessagesWithSenderId(@Param("senderId") String senderId);
	
	/* inset */
	// Insert or Update Chatmessage data
	Chatmessage saveAndFlush(Chatmessage chatmessage);
	
}
