package ac.inhaventureclub.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ac.inhaventureclub.repository.PostingRepository;
import ac.inhaventureclub.service.PostingService;
import ac.inhaventureclub.util.Result;
import ac.inhaventureclub.vo.Posting;

@RestController
public class PostingController {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	PostingRepository PostingRepository;
	
	@Resource(name="postingservice")
	PostingService postingService;
	
	/* find */
	// Find Postings
	@ResponseBody
	@GetMapping("/posting")
	public String getPostings() {
		return postingService.findAll();
	}
	// Find Posting: with INDEX ={ 게시글; }
	@ResponseBody
	@PostMapping("/postingWithIndex")
	public String postPostingWithIndex(@RequestBody String vo) {
		Gson gson = new Gson();
		Posting posting = gson.fromJson(vo, Posting.class);
		return postingService.findPostingWithIndex(posting.POSTING_INDEX);
	}
	
	// Find Posting: with DEPARTURE_IDX ={ 출발지 선택; }
	@ResponseBody
	@PostMapping("/postingsWithDepartIdx")
	public String postPostingsWithDepartIdx(@RequestBody String vo) {
		Gson gson = new Gson();
		Posting posting = gson.fromJson(vo, Posting.class);
		return postingService.findPostingWithDepartIdx(posting.DEPARTURE_IDX);
	}
	// Find Posting: with ARRIVE_IDX ={ 도착지 선택; }
	@ResponseBody
	@PostMapping("/postingsWithArriveIdx")
	public String postPostingsWithArriveIdx(@RequestBody String vo) {
		Gson gson = new Gson();
		Posting posting = gson.fromJson(vo, Posting.class);
		return postingService.findPostingWithArriveIdx(posting.ARRIVE_IDX);
	}
	
	
	// Find Posting: with IS_GUEST ={ 탈래요/타세요; }
	@ResponseBody
	@PostMapping("/postingsWithIsGuest")
	public String postPostingsWithIsGuest(@RequestBody String vo) {
		Gson gson = new Gson();
		Posting posting = gson.fromJson(vo, Posting.class);
		return postingService.findPostingWithIsGuest(posting.IS_GUEST);
	}
	
	// Find Posting: with FLAG ={ 게시글 삭제 여부; }
	@ResponseBody
	@PostMapping("/postingsWithFlag")
	public String postPostingsWithFlag(@RequestBody String vo) {
		Gson gson = new Gson();
		Posting posting = gson.fromJson(vo, Posting.class);
		return postingService.findPostingWithFlag(posting.FLAG);
	}
	
	// Find Posting: with USER_ID ={ 내가 쓴 글 보기; }
	@ResponseBody
	@PostMapping("/postingsWithUserId")
	public String postPostingsWithUserId(@RequestBody String vo) {
		Gson gson = new Gson();
		Posting posting = gson.fromJson(vo, Posting.class);
		return postingService.findPostingWithUserId(posting.USER_ID);
	}
	
	/* INSERT */
	
	// INSERT AND UPDATE: posting ={ 게시글 작성; 게시글 수정;}  
//	@ResponseBody
//	@PostMapping("/postingService")
//	public String setPostingService(@RequestBody String vo) {
//		Gson gson = new Gson();
//		int result = -1;
//		try {
//			Posting posting = gson.fromJson(vo, Posting.class);
//			System.out.println(vo);
//			result = postingService.savePosting(posting);
//			// if SUCCESS result=1, else FAIL result=0;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Result obj = result > 0 ? new Result(true, "SUCCESS") : new Result(false, "FAILED");
//		return gson.toJson(obj);
//	}
	
	   // INSERT AND UPDATE: posting ={ 게시글 작성; 게시글 수정;}  
	   @ResponseBody
	   @PostMapping("/postingServices")
	   public String setPostingServices(@RequestBody String vo) {
	      Gson gson = new Gson();
	      int result = -1;
	      int savingCount = 0;
	      try {
	         ArrayList<Posting> postingList = gson.fromJson(vo, new TypeToken<ArrayList<Posting>>() {}.getType());
	         System.out.println(vo);
	         result = postingService.savePosting((ArrayList<Posting>) postingList);
	         savingCount = result;
	         // if SUCCESS result=1, else FAIL result=0;
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      Result obj = result > 0 ? new Result(true, String.valueOf(savingCount)) : new Result(false, "FAILED");
	      return gson.toJson(obj);
	   }
	   
	   
		/* Posting and Request */
		// Find Postings: with Request.USER_ID ={운전자가 받은 요청;}
			@ResponseBody
			@PostMapping("/postingsWithRequestUserid")
			public String postPostingsWithRequestUserid(@RequestBody String userid) {
				return postingService.findPostingsWithRequestUserid(userid);
			}
	
}


