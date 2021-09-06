package ac.inhaventureclub.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ac.inhaventureclub.vo.Posting;

@Service
public interface PostingService {
	/* find */
	public String findAll();
	public String findPostingWithIndex(int postingindex);
	public String findPostingWithDepartIdx(int departureIdx);
	public String findPostingWithArriveIdx(int arriveIdx);
	public String findPostingWithIsGuest(int isGuest);
	public String findPostingWithFlag(int flag);
	public String findPostingWithUserId(String userId);
	
	/* save */
	public int savePosting(ArrayList<Posting> postings);

	
	/* Posting and Request */
	public String findPostingsWithRequestUserid(String userid);
	
	/* Posting and User */
	public List<Posting> findPostingsAndUserid(String userId);
	


}
