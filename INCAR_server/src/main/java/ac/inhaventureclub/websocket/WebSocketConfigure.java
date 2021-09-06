package ac.inhaventureclub.websocket;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import ac.inhaventureclub.websocket.message.MessageHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfigure implements WebSocketConfigurer{
	
	@Autowired
	MessageHandler messageHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(messageHandler, "/chat").setAllowedOrigins("*");
	}

}
