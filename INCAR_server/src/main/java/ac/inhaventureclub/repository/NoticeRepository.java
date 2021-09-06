package ac.inhaventureclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ac.inhaventureclub.vo.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>{
	
	/* select */ 
	// Select All Data
	@Query("select n from Notice n")
	List<Notice> findAll();
	
	// Select Notice: with INDEX
	@Query("select n from Notice n where n.NOTICE_INDEX =:notice_index")
	Notice findNoticeWithNoticeindex(@Param("notice_index") int notice_index);

	
}
