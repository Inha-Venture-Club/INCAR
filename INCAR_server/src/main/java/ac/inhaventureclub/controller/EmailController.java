package ac.inhaventureclub.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ac.inhaventureclub.service.EmailService;

@RestController
public class EmailController {
	@Resource(name="emailservice")
	EmailService emailService;
	
	@ResponseBody
	@PostMapping("/email")
	public String getRandomAndSendEmail(@RequestBody String address) {
		return emailService.getRandomAndSendEmail(address);
	}
	

}
