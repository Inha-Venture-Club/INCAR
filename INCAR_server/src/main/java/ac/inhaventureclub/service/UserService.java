package ac.inhaventureclub.service;

import org.springframework.stereotype.Service;

import ac.inhaventureclub.vo.User;

@Service
public interface UserService {
	/* find */
	public String findAll();
	public String findUserWithId(String id);
	public String findUserPwWithId(String id);
	public String findUserRejoinabledateWithId(String id);
	public String findUserWithIdAndPw(String id, String pw);
	public String findUserTokenidWithId(String id);

	
	/* save */
	public int saveUser(User user);
	public String saveUserPwWithId(String pw, String id);
	public String saveUserIntroWithId(String introduction, String id);
	
	
}