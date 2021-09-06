package ac.inhaventureclub.service;

import org.springframework.stereotype.Service;

@Service
public interface JobService {
	/* find */
	public String findAll();
	public String findJobNameWithIndex(int index);
	/* save */
}
