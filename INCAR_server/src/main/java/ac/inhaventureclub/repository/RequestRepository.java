package ac.inhaventureclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ac.inhaventureclub.vo.Posting;
import ac.inhaventureclub.vo.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{
	
	/* select */
	// Select All Data
	@Query("select r from Request r")
	List<Request> findAll();
	
	// Select request By request.USER_ID
	@Query("select r from Request r where r.USER_ID=:userid")
	List<Request> findRequestsWithUserid(@Param("userid") String userid);
	
	// Select request By request.POSTING_IDX
	@Query("select r from Request r where r.POSTING_IDX=:postingIdx")
	List<Request> findRequestsWithPostingidx(@Param("postingIdx") int postingIdx);
	
	/* Insert */
	// Insert or Update Request data
	Request saveAndFlush(Request request);// update + insert
	
	
	/* Request and Posting */
	// Select request By posting.USER_ID
	@Query("select r from Request r join Posting p on p.POSTING_INDEX=r.POSTING_IDX where p.USER_ID=:userid")
	List<Request> findRequestsWithPostingUserid(@Param("userid") String userid);
	

}
