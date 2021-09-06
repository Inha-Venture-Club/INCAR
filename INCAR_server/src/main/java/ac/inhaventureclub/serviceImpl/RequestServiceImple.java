package ac.inhaventureclub.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.RequestRepository;
import ac.inhaventureclub.service.RequestService;
import ac.inhaventureclub.vo.Posting;
import ac.inhaventureclub.vo.Request;

@Service("requestservice")
public class RequestServiceImple implements RequestService{
	
	@Autowired
	RequestRepository requestRepository;

	/* find */ 
	@Override
	public String findAll() {
		return new Gson().toJson(requestRepository.findAll());
	}
	@Override
	public String findRequestWithUserid(String Userid) {
		return new Gson().toJson(requestRepository.findRequestsWithUserid(Userid));
	}
	@Override
	public String findRequestWithPostingidx(int postingIdx) {
		return new Gson().toJson(requestRepository.findRequestsWithPostingidx(postingIdx));
	}
	
	/* save */
	@Override
	public int saveRequest(Request request) {
		Request resultRequest = requestRepository.saveAndFlush(request);
		// if SUCCESS = Post.class; else FAIL = null;
		return resultRequest == null ? 0 : 1;
	}
	
	/* Request and Posting */
	@Override
	public String findRequestsWithPostingUserid(String userid) {
		return new Gson().toJson(requestRepository.findRequestsWithPostingUserid(userid));
	}
	
	

}
