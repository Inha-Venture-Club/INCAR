package ac.inhaventureclub;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ac.inhaventureclub.websocket.message.MessageHandler;
import ac.inhaventureclub.websocket.repository.ChatmessageRepository;

@SpringBootApplication
public class IncarServerApplication{
	
	@Autowired
	ChatmessageRepository chatmessageRepository;

	@Autowired
	MessageHandler messageHandler;
	
	public static void main(String[] args) {
		SpringApplication.run(IncarServerApplication.class, args);
	}

}
