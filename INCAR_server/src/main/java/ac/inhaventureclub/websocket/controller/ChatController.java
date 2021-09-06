package ac.inhaventureclub.websocket.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ac.inhaventureclub.websocket.firebase.FcmUtil;
import ac.inhaventureclub.websocket.repository.ChatmessageRepository;

@RestController
public class ChatController {
	
	@Autowired
	ChatmessageRepository chatmessageRepository;
	
	@GetMapping("/chatrooms")
	public String chatrooms() {
		return "chatrooms";
	}
	
//	@RequestMapping(value = "/fcmTest.do")
//	//public@ResponseBody String fcmTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
//	public@ResponseBody String fcmTest(String tokenId, String title, String content) throws Exception{
//		
//		tokenId = "eZv5VLL_Eg4:APA91bEr_RV2bziVO-1ccrlIdz9ft103VWpaK-xlNY0fIOCmV-TU7uIqyjz_Ez-4k16XEiZGb81JIkA8jqqI4dV9L5J9h7YY64jTxA3zwxZqrN_zwGgtxUdSh01-EmagMhLJKrTboXTn";
//		title = "제목";
//		content = "내용";
//		
//		FcmUtil fcmUtil = new FcmUtil();
//		fcmUtil.send_FCM(tokenId, title, content);
//		
//		return "test";
//	}
	
	@RequestMapping(value = "/z")
	//public@ResponseBody String fcmTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
	@ResponseBody 
	public String z(String vo){
		return chatmessageRepository.findAll().toString();
	}
}
