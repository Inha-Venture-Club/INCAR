package ac.inhaventureclub.service;

import org.springframework.stereotype.Service;

import ac.inhaventureclub.vo.Posting;
import ac.inhaventureclub.vo.Request;

@Service
public interface RequestService {
	/* find */ 
	public String findAll();
	public String findRequestWithUserid(String userid);
	public String findRequestWithPostingidx(int postingIdx);
	
	/* save */
	public int saveRequest(Request request);

	
	/* Request and Posting */
	public String findRequestsWithPostingUserid(String userid);
	
	
}
