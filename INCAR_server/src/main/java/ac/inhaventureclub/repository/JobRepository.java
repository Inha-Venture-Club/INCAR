package ac.inhaventureclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ac.inhaventureclub.vo.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
	/* select */
	
	// Select All Data
	@Query("select j from Job j")
	List<Job> findAll();
	
	// Select job.NAME By job.INDEX
	@Query("select j.NAME from Job j where j.INDEX =:index")
	String findJobNameWithIndex(@Param("index") int index);
}
