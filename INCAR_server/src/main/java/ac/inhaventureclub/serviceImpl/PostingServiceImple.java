package ac.inhaventureclub.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.PostingRepository;
import ac.inhaventureclub.service.PostingService;
import ac.inhaventureclub.vo.Posting;
import ac.inhaventureclub.vo.User;

@Service("postingservice")
public class PostingServiceImple implements PostingService{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	PostingRepository postingRepository;
	
	
	/* find */
	@Override
	public String findAll() {
		return new Gson().toJson(postingRepository.findAll());
	}

	@Override
	public String findPostingWithIndex(int postingindex) {
		return new Gson().toJson(postingRepository.findPostingWithIndex(postingindex));
	}
	
	@Override
	public String findPostingWithDepartIdx(int departureIdx) {
		return new Gson().toJson(postingRepository.findPostingWithDepartIdx(departureIdx));
	}
	@Override
	public String findPostingWithArriveIdx(int arriveIdx) {
		return new Gson().toJson(postingRepository.findPostingWithArriveIdx(arriveIdx));
	}
	
	@Override
	public String findPostingWithIsGuest(int isGuest) {
		return new Gson().toJson(postingRepository.findPostingWithIsGuest(isGuest));
	}
	
	@Override
	public String findPostingWithFlag(int flag) {
		return new Gson().toJson(postingRepository.findPostingWithFlag(flag));
	}
	
	@Override
	public String findPostingWithUserId(String userId) {
		return new Gson().toJson(postingRepository.findPostingWithUserId(userId));
	}
	
	/* save */
	
	@Override
	public int savePosting(ArrayList<Posting> postings) {
		int ret = 0;
		for(int i=0;i<postings.size();i++) {
			Posting result = postingRepository.saveAndFlush(postings.get(i));
			if(result!=null) ret++;
		}
		// if SUCCESS = Post.class; else FAIL = null;
		return ret;
	}
	
	/* Posting and Request */
	@Override
	public String findPostingsWithRequestUserid(String userid) {
		return new Gson().toJson(postingRepository.findPostingsWithRequestUserid(userid));
	}

	@Override
	public List<Posting> findPostingsAndUserid(String userId) {
		return postingRepository.findPostingsAndUserid(userId);
	}
	
}
