package ac.inhaventureclub.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding.Use;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ac.inhaventureclub.repository.PostingRepository;
import ac.inhaventureclub.repository.RequestRepository;
import ac.inhaventureclub.repository.UserRepository;
import ac.inhaventureclub.service.PostingService;
import ac.inhaventureclub.service.RequestService;
import ac.inhaventureclub.service.UserService;
import ac.inhaventureclub.vo.Posting;
import ac.inhaventureclub.vo.PostingAndRequestAndUser;
import ac.inhaventureclub.vo.Request;
import ac.inhaventureclub.vo.User;

@RestController
public class PostingAndRequestAndUserController {
	@Autowired
	PostingRepository postingRepository;
	@Autowired
	RequestRepository requestRepository;
	@Autowired
	UserRepository userRepository;
	
	@Resource(name = "postingservice")
	PostingService postingService;
	@Resource(name = "requestservice")
	RequestService requestService;
	@Resource(name = "userservice")
	UserService userService;
	
	/* find */ 
	@ResponseBody
	@PostMapping("/postingAndRequestAndUserWithPostingIdx")
	public String getPostingAndRequestAndUserWithPostingIdx(@RequestBody String postingIdx) {
		System.out.println("postingIdx: " + postingIdx);
		int postingIndex = Integer.parseInt(postingIdx);
		System.out.println("postingIndex:" + Integer.toString(postingIndex));
		Posting posting = new Posting();
		posting = new Gson().fromJson(postingService.findPostingWithIndex(postingIndex), Posting.class);

		List<Request> requests = new ArrayList<Request>();
		requests = new Gson().fromJson(requestService.findRequestWithPostingidx(postingIndex), new TypeToken<ArrayList<Request>>() {}.getType());
		
		User user = new User();
		user = new Gson().fromJson(userService.findUserWithId(posting.USER_ID), User.class);
		
		PostingAndRequestAndUser postingAndRequestAndUser = new PostingAndRequestAndUser();
		postingAndRequestAndUser.posting = posting;
		postingAndRequestAndUser.requests = requests;
		postingAndRequestAndUser.user = user;
		return new Gson().toJson(postingAndRequestAndUser);
		
	}
}
