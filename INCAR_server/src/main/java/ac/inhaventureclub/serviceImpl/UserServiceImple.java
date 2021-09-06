package ac.inhaventureclub.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.UserRepository;
import ac.inhaventureclub.service.UserService;
import ac.inhaventureclub.util.Result;
import ac.inhaventureclub.vo.User;

@Service("userservice")
public class UserServiceImple implements UserService {
	@Autowired
	UserRepository userRepository;

	/* find */
	
	@Override
	public String findAll() {
		return new Gson().toJson(userRepository.findAll());
	}
	
	@Override
	public String findUserWithId(String id) {
		return new Gson().toJson(userRepository.findUserWithId(id));
	}
	
	@Override
	public String findUserPwWithId(String id) {
		Gson gson = new Gson();
		User user = userRepository.findUserWithId(id);
		return user.PW;
	}
		
	@Override
	public String findUserRejoinabledateWithId(String id) {
		Gson gson = new Gson();
		User user = userRepository.findUserWithId(id);
		return user.REJOINABLE_DATE;
	}
	
	@Override
	public String findUserWithIdAndPw(String id, String pw) {
		User user = userRepository.findUserWithIdAndPw(id);
		Result result;
		if(user == null) {
			result = new Result(false, "존재하지 않는 ID입니다.");
		}else if(!user.PW.equals(pw)){
			result = new Result(false, "비밀번호가 틀립니다.");
		}else {
			return new Gson().toJson(user);
		}
		return new Gson().toJson(result);
	}

	@Override
	public String findUserTokenidWithId(String id) {
		return userRepository.findUserTokenidWithId(id);
	}

	
	
	/* save */

	@Override
	public int saveUser(User user) {
		User resultuser = userRepository.saveAndFlush(user);
		// if SUCCESS = User.class; else FAIL = null;
		return resultuser == null ? 0 : 1;
	}

	@Override
	public String saveUserPwWithId(String pw, String id) {
		Gson gson = new Gson();
		int result = -1;
		try {
			result = userRepository.saveUserPwWithId(pw, id);
			//if SUCCESS result=1; else if FAIL result=0; 
		}catch (Exception e) {
			e.printStackTrace();
		}
		Result obj = result > 0 ? new Result(true, "ID:"+id+", PW:"+pw) : new Result(false, "FAILED");
		return gson.toJson(obj);
	}
	
	@Override
	public String saveUserIntroWithId(String introduction, String id) {
		Gson gson = new Gson();
		int result = -1;
		try {
			result = userRepository.saveUserIntroWithId(introduction, id);
			//if SUCCESS result=1; else if FAIL result=0; 
		}catch (Exception e) {
			e.printStackTrace();
		}
		Result obj = result > 0 ? new Result(true, "SUCCESS") : new Result(false, "FAILED");
		return gson.toJson(obj);
	}
	
	
	



	

}
