package ac.inhaventureclub.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	public String getRandomAndSendEmail(String address);
}
