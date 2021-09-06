package ac.inhaventureclub.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.JobRepository;
import ac.inhaventureclub.service.JobService;

@Service("jobservice")
public class JobServiceImple implements JobService{
	@Autowired
	JobRepository jobRepository;

	/* find */

	@Override
	public String findAll() {
		return new Gson().toJson(jobRepository.findAll());
	}
	
	@Override
	public String findJobNameWithIndex(int index) {
		return jobRepository.findJobNameWithIndex(index);
	}
}
