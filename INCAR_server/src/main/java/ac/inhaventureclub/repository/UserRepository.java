package ac.inhaventureclub.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ac.inhaventureclub.vo.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	/* select */
	
	// Select All Data 
	@Query("select u from User u")
	List<User> findAll();

	// Select Data By user.ID
	@Query("select u from User u where u.ID =:id")
	User findUserWithId(@Param("id") String id);

	// Select user.REJOINABLE_DATE By user.ID
	@Query("select u.REJOINABLE_DATE from User u where u.id = :id ")
	User findUserRejoinabledateWithId(@Param("id") String id);

	// Select User By user.ID and user.PW
	@Query("select u from User u where u.id = :id")
	User findUserWithIdAndPw(@Param("id") String id);

	// Select User.TOKEN_ID By user.ID
	@Query("select u.TOKEN_ID from User u where u.id = :id")
	String findUserTokenidWithId(@Param("id") String id);

	
	
	/* insert and update */
	
	// Insert or Update User Data
	User saveAndFlush(User user);// update + insert
	
	// Update user.PW By user.ID
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.PW = :pw WHERE u.ID =:id")
	int saveUserPwWithId(@Param("pw") String pw, @Param("id") String id);
	
	// Update user.INTRODUCTION By user.ID
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.INTRODUCTION = :introduction WHERE u.ID =:id")
	int saveUserIntroWithId(@Param("introduction") String introduction, @Param("id") String id);
	
	
	

}
