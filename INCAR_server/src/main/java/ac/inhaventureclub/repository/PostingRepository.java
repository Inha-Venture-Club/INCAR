package ac.inhaventureclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ac.inhaventureclub.vo.Posting;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long>{
	
	/* select */
	// Select All Data
	@Query("select p from Posting p")
	List<Posting> findAll();
	
	// Select posting By posting.POSTING_INDEX
	@Query("select p from Posting p where p.POSTING_INDEX = :postingindex ")
	Posting findPostingWithIndex(@Param("postingindex") int postingindex);

	// Select posting By posting.DEPARTURE_IDX
	@Query("select p from Posting p where p.DEPARTURE_IDX = :departureIdx ")
	List<Posting> findPostingWithDepartIdx(@Param("departureIdx") int departureIdx);
	// Select posting By posting.ARRIVE_IDX
	@Query("select p from Posting p where p.ARRIVE_IDX = :arriveIdx ")
	List<Posting> findPostingWithArriveIdx(@Param("arriveIdx") int arriveIdx);
	
	// Select posting By posting.IS_GUEST
	@Query("select p from Posting p where p.IS_GUEST = :isGuest ")
	List<Posting> findPostingWithIsGuest(@Param("isGuest") int isGuest);

	// Select posting By posting.FLAG
	@Query("select p from Posting p where p.FLAG = :flag ")
	List<Posting> findPostingWithFlag(@Param("flag") int flag);

	// Select posting By posting.USER_ID
	@Query("select p from Posting p where p.USER_ID = :userId ")
	List<Posting> findPostingWithUserId(@Param("userId") String userId);
	
	

	

	/* Insert */
	// Insert or Update Posting data
	Posting saveAndFlush(Posting posting);// update + insert
	
	
	/* Posting and Request */
	// Select posting By request.USER_ID
	@Query("select p from Posting p join Request r on p.POSTING_INDEX=r.POSTING_IDX where r.USER_ID=:userid")
	List<Posting> findPostingsWithRequestUserid(@Param("userid") String userid);

	// Select posting By user.USER_ID
	@Query("select p from Posting p join User u on u.ID=p.USER_ID")
	List<Posting> findPostingsAndUserid(@Param("userid") String userid);
	
	
}
