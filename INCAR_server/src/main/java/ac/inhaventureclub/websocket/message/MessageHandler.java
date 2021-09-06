package ac.inhaventureclub.websocket.message;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ac.inhaventureclub.repository.PostingRepository;
import ac.inhaventureclub.repository.UserRepository;
import ac.inhaventureclub.service.PostingService;
import ac.inhaventureclub.service.UserService;
import ac.inhaventureclub.vo.Chatmessage;
import ac.inhaventureclub.vo.Chattingroom;
import ac.inhaventureclub.vo.Posting;
import ac.inhaventureclub.websocket.controller.ChatController;
import ac.inhaventureclub.websocket.firebase.FcmUtil;
import ac.inhaventureclub.websocket.repository.ChatmessageRepository;
import ac.inhaventureclub.websocket.repository.ChattingroomRepository;
import ac.inhaventureclub.websocket.service.ChatmessageService;
import ac.inhaventureclub.websocket.service.ChattingroomService;
import ac.inhaventureclub.websocket.serviceImple.ChatmessageServiceImple;
import ac.inhaventureclub.websocket.serviceImple.ChattingroomServiceImple;
import ac.inhaventureclub.websocket.vo.ClientMessage;
import ac.inhaventureclub.websocket.vo.ClientMessageRequest;
import ac.inhaventureclub.websocket.vo.MsgForAndroid;
import javassist.bytecode.stackmap.BasicBlock.Catch;

@Component
@Resource
public class MessageHandler extends TextWebSocketHandler{
	
	ObjectMapper objectMapper = new ObjectMapper();
    List<CheckingSendObject> list = Collections.synchronizedList(new ArrayList<>());
    
	@Resource
	ChattingroomRepository chattingroomRepository;
	@Resource
	ChatmessageRepository chatmessageRepository;
	@Resource
	UserRepository userRepository;
	@Resource
	PostingRepository postingRepository;

	@Resource(name = "chatmessageservice")
	ChatmessageService chatmessageService;
	@Resource(name = "chattingroomservice")
	ChattingroomService chattingroomService;
	@Resource(name = "userservice")
	UserService userService;
	@Resource(name = "postingservice")
	PostingService postingService;

    // 웹 소켓에 연결될 때 호출되는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("접속===========================");
        System.out.println(session.getId());
        System.out.println("접속===========================");
        CheckingSendObject sendObj = new CheckingSendObject();
        sendObj.webSocketSession = session;
        list.add(sendObj);
    }

    
    // 메시지를 전송받았을때 호출되는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	
    	// Variables
    	String tokenId = null;
    	
    	// System out
    	System.out.println("메시지가 왔어요...");
        System.out.println(session.getId() + ", " + message.getPayload());
        System.out.println("메시지가 왔어요...");

		// CheckingSendObject 생성 및 필요한 내용 저장 // session과 clientMessage
        CheckingSendObject sendObj = new CheckingSendObject();
		sendObj.webSocketSession = session; 
        ClientMessage clientMessage = objectMapper.readValue(message.getPayload(), ClientMessage.class);
		sendObj.clientMessage = clientMessage;
		sendObj = list.get(list.indexOf(sendObj));

		// clientMessage Code별 상황
		Chatmessage chatmessage = new Chatmessage();
		chatmessage.CM_CODE= clientMessage.code;				
		if(clientMessage.roomIdx==null) chatmessage.ROOM_IDX = -1;
		else chatmessage.ROOM_IDX = Integer.parseInt(clientMessage.roomIdx);				
		chatmessage.SENDER_ID = clientMessage.id;
		chatmessage.SENDER_NAME = clientMessage.name;
		chatmessage.MESSAGE = clientMessage.message;
		chatmessage.TIME= Long.parseLong(clientMessage.time);
		chatmessage.POSTING_IDX= clientMessage.posting_idx;
		
		switch (clientMessage.code) {
			case ClientMessage.CONNECT: // 1: 로그인 했을 때 
				if(new Gson().toJson(sendObj.clientMessage)!=null) { 
					// DB에 저장
					chatmessage = chatmessageService.saveChatmessage(chatmessage);
					// 안드로이드에게 응답
					System.out.println(new Gson().toJson(clientMessage));
					responseToAndroid(MsgForAndroid.SUCCESS, "환영합니다.", new Gson().toJson(clientMessage), session);
				}else {
					clientMessageIsNull("UNCONNECT", clientMessage, session);
				}
				break;
			case ClientMessage.JOIN_ROOM: // 2 : 채팅방에 접속하거나 요청하기를 눌렀을 경우
				if(new Gson().toJson(sendObj.clientMessage)!=null) { 
					int postingIdx = chatmessage.POSTING_IDX;
					// 채팅방에서 접속했을 때 
					if(postingIdx<0) { 
						String lastTime = chatmessage.MESSAGE;
						// 알고 있던 방일 때 
						if(lastTime !=null) {
							// lastTime 기준으로 이후 채팅 내용 불러오기
							int roomIdx = chatmessage.ROOM_IDX;
							String chatmessages = chatmessageService.findMessagesWithLastTimeAndRoomIdx(lastTime, roomIdx);
							// 안드로이드에게 응답
							responseToAndroid(MsgForAndroid.SUCCESS, "메시지를 불러왔습니다.", chatmessages, session);
						}
						// 처음 보는 방 일때 : lastTime == null
						else {
							// 채팅 내용 모두 불러오기
							int roomIdx = chatmessage.ROOM_IDX;
							String chatmessages = chatmessageService.findMessagesWithRoomidx(roomIdx);
							// 안드로이드에게 응답
							responseToAndroid(MsgForAndroid.SUCCESS, "메시지를 불러왔습니다.", chatmessages, session);
						}
					}
					// 요청하기를 눌렀을 때: POSTING_IDX값이 존재한다.
					else { 
						Posting posting = new Gson().fromJson(postingService.findPostingWithIndex(clientMessage.posting_idx), Posting.class);
						if(posting==null) { // 그런 posting이 없다면 [ EXCEPTION ]
							// 안드로이드에게 응답
							responseToAndroid(MsgForAndroid.EXCEPTION, "해당 게시글이 존재하지 않습니다.", new Gson().toJson(clientMessage),session);
						}
						else {	// 해당 posting이 존재하면
							String driver = posting.USER_ID;
							String guest = chatmessage.SENDER_ID;
							// 기존에 없던 채팅방이면
							int roomIdx =chattingroomService.findRoomIdxWithDriverAndGuest(driver, guest); 
							if(roomIdx<0) { // = -1
								// 채팅방 생성
								Chattingroom chattingroom = new Chattingroom();
								chattingroom.GUEST = guest;  
								chattingroom.DRIVER = driver;
								chattingroomService.saveChattingroom(chattingroom);
								// 안드로이드에게  응답
								clientMessage.roomIdx = Integer.toString(chattingroomService.findRoomIdxWithDriverAndGuest(driver, guest));
								chatmessage.ROOM_IDX = roomIdx;
								responseToAndroid(MsgForAndroid.SUCCESS, "성공적으로 요청하였습니다.", new Gson().toJson(clientMessage),session);
							}
							// 기존에 있던 채팅방
							else {
								// roomIdx값을 넣어줌
								chatmessage.ROOM_IDX = roomIdx;
								clientMessage.roomIdx = Integer.toString(roomIdx);
								// 안드로이드에게 응답
								clientMessage.roomIdx = Integer.toString(roomIdx);
								chatmessage.ROOM_IDX = roomIdx;
								responseToAndroid(MsgForAndroid.SUCCESS, "성공적으로 요청하였습니다.", new Gson().toJson(clientMessage),session);
							}
						}
					}
					// DB에 저장
					chatmessageService.saveChatmessage(chatmessage);
				}
				// [ FAILED ]
				else {
					clientMessageIsNull("JOIN_ROOM", clientMessage ,session);
				}

				break;
			case ClientMessage.MESSAGE: // 3
				if(new Gson().toJson(sendObj.clientMessage)!=null) { 
					String roomIdx = clientMessage.roomIdx;
					System.out.println("roomIdx: "+ roomIdx);
					// 상대방의 정보를 가져온다.
					Chattingroom chattingroom = chattingroomService.findChattingroomWithRoomIdx(Integer.parseInt(roomIdx));
					if(chattingroom ==null) {
						clientMessageIsNull("존재하지 않는 채팅방입니다. ", clientMessage, session);
						break;
					}
					String myId = clientMessage.id;
					String receiverId = yourIdWithChattingroomWithMyId(chattingroom, myId);
					System.out.println("상대방 id: "+receiverId);
					// 채팅방에서 메시지를 보내는 경우
					if(clientMessage.posting_idx < 0) {
						if(roomIdx!=null) {
							// 상대방의 상태에 따른 메시지 전송 방식
							Chatmessage lastYourChatmessage = chatmessageService.findLastMessageWithSenderId(receiverId);
							if(lastYourChatmessage == null) {
								// 푸시를 보낸다.
								sendPushUsingFcm(receiverId, chatmessage, clientMessage);
								// 안드로이드에게 응답
								responseToAndroid(MsgForAndroid.SUCCESS, "메시지가 전송되었습니다.", new Gson().toJson(clientMessage),session);
								// DB에 저장
								chatmessageService.saveChatmessage(chatmessage);
							}
							else {
							switch(lastYourChatmessage.CM_CODE) {
							case ClientMessage.CONNECT:
								if(new Gson().toJson(sendObj.clientMessage)!=null) {
									// 푸시를 보낸다.
									sendPushUsingFcm(receiverId, chatmessage, clientMessage);
									// 안드로이드에게 응답
									responseToAndroid(MsgForAndroid.SUCCESS, "메시지가 전송되었습니다.", new Gson().toJson(clientMessage),session);
									// DB에 저장
									chatmessageService.saveChatmessage(chatmessage);
								}else {
									clientMessageIsNull("CONNECT - MESSAGE", clientMessage,session);
								}
								break;
							case ClientMessage.MESSAGE:
								// 요청하기를 통한 메시지 전송
								if(lastYourChatmessage.POSTING_IDX>=0){
									if(new Gson().toJson(sendObj.clientMessage)!=null) {
										// 푸시를 보낸다.
										sendPushUsingFcm(receiverId, chatmessage, clientMessage);
										// 안드로이드에게 응답
										responseToAndroid(MsgForAndroid.SUCCESS, "메시지가 전송되었습니다.", new Gson().toJson(clientMessage),session);
										// DB에 저장
										chatmessageService.saveChatmessage(chatmessage);
									}else {
										// 로그 찍기
										System.out.println("MESSAGE: sendObj.clientMessage is null");
										// 안드로이드에게 응답
										responseToAndroid(MsgForAndroid.FAILED, "접속에 실패하였습니다.", new Gson().toJson(clientMessage),session);
									}
								}
								// 채팅방을 통한 메시지 전송이었다면
								else {
									// 내 채팅방에 접속이라면 
									if(lastYourChatmessage.ROOM_IDX== Integer.parseInt(clientMessage.roomIdx)) {
										if(new Gson().toJson(sendObj.clientMessage)!=null) {
											// DB에 저장
											chatmessageService.saveChatmessage(chatmessage);
											// 안드로이드에게 응답
											responseToAndroid(MsgForAndroid.SUCCESS, "메시지가 전송되었습니다.", new Gson().toJson(clientMessage),session);
										}else {
											// 로그 찍기
											System.out.println("MESSAGE: sendObj.clientMessage is null");
											// 안드로이드에게 응답
											responseToAndroid(MsgForAndroid.FAILED, "접속에 실패하였습니다.", new Gson().toJson(clientMessage),session);
										}
									}
									// 남의 채팅방에 접속이라면
									else {
										if(new Gson().toJson(sendObj.clientMessage)!=null) {
											// 푸시를 보낸다.
											tokenId = userService.findUserTokenidWithId(receiverId);
											FcmUtil fcmUtil = new FcmUtil();
											String title = chatmessage.SENDER_ID;
											String content = chatmessage.MESSAGE;
											fcmUtil.send_FCM(tokenId, title, content, new Gson().toJson(clientMessage));
											// 안드로이드에게 응답
											responseToAndroid(MsgForAndroid.SUCCESS, "메시지가 전송되었습니다.", new Gson().toJson(clientMessage),session);
											CheckingSendObject wss = new CheckingSendObject();
											// DB에 저장
											chatmessageService.saveChatmessage(chatmessage);
										}else {
											// 로그 찍기
											System.out.println("MESSAGE: sendObj.clientMessage is null");
											// 안드로이드에게 응답
											responseToAndroid(MsgForAndroid.FAILED, "접속에 실패하였습니다.", new Gson().toJson(clientMessage),session);
										}
									}
								}
							case ClientMessage.JOIN_ROOM:
								// 내 채팅방에 접속이라면 
								if(lastYourChatmessage.ROOM_IDX== Integer.parseInt(clientMessage.roomIdx)) {
									if(new Gson().toJson(sendObj.clientMessage)!=null) {
										// DB에 저장
										chatmessageService.saveChatmessage(chatmessage);
										// 안드로이드에게 응답
										responseToAndroid(MsgForAndroid.SUCCESS, "메시지가 전송되었습니다.", new Gson().toJson(clientMessage),session);
									}else {
										// 로그 찍기
										System.out.println("MESSAGE: sendObj.clientMessage is null");
										// 안드로이드에게 응답
										responseToAndroid(MsgForAndroid.FAILED, "접속에 실패하였습니다.", new Gson().toJson(clientMessage),session);
									}
								}
								// 남의 채팅방에 접속이라면
								else {
									if(new Gson().toJson(sendObj.clientMessage)!=null) {
										// 푸시를 보낸다.
										sendPushUsingFcm(receiverId, chatmessage, clientMessage);
										// 안드로이드에게 응답
										responseToAndroid(MsgForAndroid.SUCCESS, "메시지가 전송되었습니다.", new Gson().toJson(clientMessage),session);
										// DB에 저장
										chatmessageService.saveChatmessage(chatmessage);
									}else {
										clientMessageIsNull("남의 채팅방에 접속이라면 - MESSAGE", clientMessage,session);
									}
								}
							}
							}
						}else {
							// 안드로이드에게 응답
							responseToAndroid(MsgForAndroid.EXCEPTION, "존재하지 않는 채팅방입니다.", new Gson().toJson(clientMessage),session);
						}
					}
					// 요청을 한 경우
					else {
						if(new Gson().toJson(sendObj.clientMessage)!=null) {
							// 푸시를 보낸다.
							sendPushUsingFcm(receiverId, chatmessage, clientMessage);
							// 안드로이드에게 응답
							responseToAndroid(MsgForAndroid.SUCCESS, "메시지가 전송되었습니다.", new Gson().toJson(clientMessage),session);
							// DB에 저장
							chatmessageService.saveChatmessage(chatmessage);
						}else {
							clientMessageIsNull("요청을 한 경우 - MESSAGE", clientMessage,session);
						}
					}
				}
				// [ FAILED ]
				else {
					clientMessageIsNull("MESSAGE", clientMessage,session);
				}
				break;
			default:
				// 로그 찍기
				System.out.println("Default");
				// 안드로이드에게 응답
				responseToAndroid(MsgForAndroid.EXCEPTION, "알수없는 접근입니다.", new Gson().toJson(clientMessage),session);
        		break;
		}
    }

    // 웹 소켓이 연결이 클로즈될때 호출되는 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("접속 close===========================");
        System.out.println(session.getId());
        System.out.println("접속 close===========================");
        CheckingSendObject sendObj = new CheckingSendObject();
		sendObj.webSocketSession = session;
        list.remove(sendObj);
    }
    
    private class CheckingSendObject{
    	public WebSocketSession webSocketSession;
    	public ClientMessage clientMessage;
    	
    	@Override
    	public boolean equals(Object obj) {
    		if(!super.equals(obj)) {
    			if(obj!=null) {
    				if(obj instanceof CheckingSendObject) {
    					if(!((CheckingSendObject) obj).webSocketSession.getId().equals(webSocketSession.getId())) {
    						return ((CheckingSendObject) obj).clientMessage.equals(clientMessage);
    					}else return true; 
    				}
    			}
			}
    		return false;
    	}
    }
    
    private String yourIdWithChattingroomWithMyId(Chattingroom chattingroom, String myId) {
    	System.out.println("yourIdWithChattingroomWithMyId - myId: "+myId);
    	System.out.println("yourIdWithChattingroomWithMyId - DRIVER: "+chattingroom.DRIVER);
    	System.out.println("yourIdWithChattingroomWithMyId - guest: "+chattingroom.GUEST);
    	String driver = chattingroom.DRIVER;
    	String guest = chattingroom.GUEST;
    	if(myId.equals(driver)) return chattingroom.GUEST;
    	else if(myId.equals(guest)) return chattingroom.DRIVER;
    	else return null;
	}
    
    // 안드로이드에게 응답
    private void responseToAndroid(int code, String code_message, String clientMessage, WebSocketSession webSocketSession) throws IOException {
    	CheckingSendObject wss = new CheckingSendObject();
		MsgForAndroid msgForAndroid = new MsgForAndroid();
		msgForAndroid.code = code;
		msgForAndroid.code_message = code_message;
		msgForAndroid.clientMessage = clientMessage;
		wss.webSocketSession = webSocketSession; 
		wss.webSocketSession.sendMessage
			(new TextMessage (new Gson().toJson(msgForAndroid)));
    }
    
    private void clientMessageIsNull(String code ,ClientMessage clientMessage, WebSocketSession webSocketSession) throws IOException {
    	// 로그 찍기
		System.out.println(code+": sendObj.clientMessage is null");
		// 안드로이드에게 응답
		responseToAndroid(MsgForAndroid.FAILED, "접속에 실패하였습니다.", new Gson().toJson(clientMessage),webSocketSession);
    }
    
    private void sendPushUsingFcm(String receiverId, Chatmessage chatmessage, ClientMessage clientMessage) {
		// 푸시를 보낸다.
		String tokenId = userService.findUserTokenidWithId(receiverId);
		System.out.println("sendPushUsingFcm - tokenId: "+tokenId);
		FcmUtil fcmUtil = new FcmUtil();
		String title = chatmessage.SENDER_ID;
		String content = chatmessage.MESSAGE;
		fcmUtil.send_FCM(tokenId, title, content, new Gson().toJson(clientMessage));
	}

}
