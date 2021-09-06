package ac.inhaventureclub.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.UserRepository;
import ac.inhaventureclub.service.UserService;
import ac.inhaventureclub.util.Result;
import ac.inhaventureclub.vo.User;

@RestController
public class UserController {
	@Resource(name = "userservice")
	UserService userService;

	/* find */
	
	// Find User
	@ResponseBody
	@GetMapping("/user")
	public String getUsers() {
		return userService.findAll();
	}

	// Find User: with ID ={ USER_ID; 해당 user의 모든 정보 가져오기(ex.프로필); } 
	@ResponseBody
	@GetMapping("/userWithId/{id}")
	public String getUserWithId(@PathVariable String id) {
		return userService.findUserWithId(id);
	}
	
	// Find user.PW: with ID ={ 비밀번호 찾기; }
	@ResponseBody
	@GetMapping("/userPwWithId/{id}")
	public String getUserPwWithId(@PathVariable String id) {
		return userService.findUserPwWithId(id);
	}
	
	// Find user.REJOINABLE_DATE: with ID ={ 재가입 가능 여부; }
	@ResponseBody
	@GetMapping("/userRejoinableWithId/{id}")
	public String getUserRejoinabledateWithId(@PathVariable String id) {
		return userService.findUserRejoinabledateWithId(id);
	}
	
	// Find: user: with ID and PW ={ 로그인; }
	@ResponseBody
	@PostMapping("/userWithIdAndPw")
	public String postUserWithIdAndPw(@RequestBody String vo) {
		Gson gson = new Gson();
		User user = gson.fromJson(vo, User.class);
		return userService.findUserWithIdAndPw(user.ID, user.PW);
	}
	
	
	/* INSERT */
	
	// INSERT: user ={ 회원가입; }  
	@ResponseBody
	@PostMapping("/user")
	public String setUserService(@RequestBody String vo) {
		Gson gson = new Gson();
		int result = -1;
		String id = null;
		String pw = null;
		try {
			User user = gson.fromJson(vo, User.class);
			System.out.println(vo);
			result = userService.saveUser(user);
			// if SUCCESS result=1, else FAIL result=0;
			id = user.ID;
			pw = user.PW;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Result obj = result > 0 ? new Result(true, "ID:"+id+", PW:"+pw) : new Result(false, "FAILED");
		return gson.toJson(obj);
	}
	
	
	/* UPDATE */
	
	// Update: user.PW with user.ID ={ 비밀번호 변경 } 
	@ResponseBody
	@PostMapping("/userPwWithId")
	public String postUserPwWithId(@RequestBody String vo) {
		Gson gson = new Gson();
		User user = gson.fromJson(vo, User.class);
		String id = user.ID;
		String pw = user.PW;
		return userService.saveUserPwWithId(pw, id);
	}
		
	// Update: user.INTRODUCTION with user.ID ={ 소개인사말 변경 } 
	@ResponseBody
	@PostMapping("/userIntroWithId")
	public String postUserIntroWithId(@RequestBody String vo) {
		Gson gson = new Gson();  
		User user = gson.fromJson(vo, User.class);
		String id = user.ID;
		String introduction = user.INTRODUCTION;
		return userService.saveUserIntroWithId(introduction, id);
	}
	

}
//