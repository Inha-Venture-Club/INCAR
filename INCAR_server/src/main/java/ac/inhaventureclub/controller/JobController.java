package ac.inhaventureclub.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ac.inhaventureclub.service.JobService;
import ac.inhaventureclub.vo.Job;

@RestController
public class JobController {
	@Resource(name = "jobservice")
	JobService jobService;
	
	/* find */
	
	// Find Job
	@ResponseBody
	@GetMapping("/job")
	public String getJobs() {
		return jobService.findAll();
	}
	
	// Find job.NAME: with job.INDEX ={JOB_IDX;}
	@ResponseBody
	@PostMapping("/jobWithIndex")
		public String getJobNameWithIndex(@RequestBody String vo) {
		Gson gson = new Gson();
		Job job = gson.fromJson(vo, Job.class);
		return jobService.findJobNameWithIndex(job.INDEX);
	}
		

}
