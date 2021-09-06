package ac.inhaventureclub.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.RequestRepository;
import ac.inhaventureclub.service.RequestService;
import ac.inhaventureclub.util.Result;
import ac.inhaventureclub.vo.Posting;
import ac.inhaventureclub.vo.Request;

@RestController
public class RequestController {
	@Autowired
	RequestRepository requestRepository;
	
	@Resource(name="requestservice")
	RequestService requestService;
	
	
	/* find */
	// Find Requests
	@ResponseBody
	@GetMapping("/request")
	public String getRequests() {
		return requestService.findAll();
	}
	// Find Requests: with USER_ID ={내가 쓴 리뷰 관리;}
	@ResponseBody
	@PostMapping("/requestWithUserid")
	public String postRequestWithUserid(@RequestBody String vo) {
		Gson gson = new Gson();
		Request request= gson.fromJson(vo, Request.class);
		return requestService.findRequestWithUserid(request.USER_ID);
	}
	
	// Find Requests: with POSTING_IDX ={나에게 달린 리뷰 보기;}
	@ResponseBody
	@PostMapping("/requestWithPostingidx")
	public String postRequestWithPostingidx(@RequestBody String vo) {
		Gson gson = new Gson();
		Request request= gson.fromJson(vo, Request.class);
		return requestService.findRequestWithPostingidx(request.POSTING_IDX);
	}
	
	
	/* Insert */
	// INSERT AND UPDATE: request ={ 요청하기; 리뷰달기; 리뷰수정;}  
	@ResponseBody
	@PostMapping("/requestService")
	public String setRequestService(@RequestBody String vo) {
		Gson gson = new Gson();
		int result = -1;
		try {
			Request request= gson.fromJson(vo, Request.class);
			System.out.println(vo);
			result = requestService.saveRequest(request);
			// if SUCCESS result=1, else FAIL result=0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Result obj = result > 0 ? new Result(true, "SUCCESS") : new Result(false, "FAILED");
		return gson.toJson(obj);
	}
	
	/* Request and Posting */
	// Find Requests: with Posting.USER_ID ={운전자가 받은 요청;}
		@ResponseBody
		@PostMapping("/requestsWithPostingUserid")
		public String postRequestsWithPostingUserid(@RequestBody String userid) {
			return requestService.findRequestsWithPostingUserid(userid);
		}
}
