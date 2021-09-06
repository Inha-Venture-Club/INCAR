package ac.inhaventureclub.websocket.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ac.inhaventureclub.vo.Chattingroom;
import ac.inhaventureclub.websocket.repository.ChattingroomRepository;
import ac.inhaventureclub.websocket.service.ChattingroomService;

@RestController
public class ChattingroomController {
	@Autowired
	ChattingroomRepository chattingroomRepository;
	@Resource(name = "chattingroomservice")
	ChattingroomService chattingroomService;
	
	@ResponseBody
	@PostMapping("/chattingrooomsWithDriver")
	public String postChattingroomWithDriver(@RequestBody String driver) {
		return new Gson().toJson(chattingroomService.findChattingroomWithDriver(driver));
	}

	@ResponseBody
	@PostMapping("/chattingrooomsWithGuest")
	public String postChattingroomWithGuest(@RequestBody String guest) {
		return new Gson().toJson(chattingroomService.findChattingroomWithGuest(guest));
	}
	
	
}
