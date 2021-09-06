package ac.inhaventureclub.websocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ac.inhaventureclub.vo.Chattingroom;
import ac.inhaventureclub.vo.Posting;

@Repository
public interface ChattingroomRepository extends JpaRepository<Chattingroom, Long>{
	
	/* select */
	// Select All Data
	@Query("select c from Chattingroom c")
	List<Chattingroom> findAll();
	
	@Query("select cr from Chattingroom cr where cr.DRIVER=:driver and cr.GUEST=:guest")
	Chattingroom findChattingroomWithDriverAndGuest(@Param("driver") String driver, @Param("guest") String guest);

	// Select Chattingroom.By ROOM_INDEX
	@Query("select cr from Chattingroom cr where cr.ROOM_INDEX=:roomIdx")
	Chattingroom findChattingroomWithRoomIdx(@Param("roomIdx") int roomIdx);

	// Select Chattingroom.By DRIVER
	@Query("select cr from Chattingroom cr where cr.DRIVER=:driver")
	List<Chattingroom> findChattingroomWithDriver(@Param("driver") String driver);

	// Select Chattingroom.By GUEST
	@Query("select cr from Chattingroom cr where cr.GUEST=:guest")
	List<Chattingroom> findChattingroomWithGuest(@Param("guest") String guest);
	
	/* insert */
	// Insert or Update Chattingroom data
	Chattingroom saveAndFlush(Chattingroom chattingroom);
}
