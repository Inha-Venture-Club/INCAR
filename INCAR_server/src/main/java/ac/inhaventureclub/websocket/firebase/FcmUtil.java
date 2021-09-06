package ac.inhaventureclub.websocket.firebase;

import java.io.FileInputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Component
public class FcmUtil {
	
	public void send_FCM(String tokenId, String title, String content, String clientMessage) {
		try {
			// 본인의 json 파일 경로 입력
			FileInputStream refreshToken = new FileInputStream("C:\\Users\\dazol\\eclipse-workspace\\incar_server\\src\\main\\resources\\fcm\\incar-948ab-firebase-adminsdk-90jn3-dfcb31cce1.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.setDatabaseUrl("https://incar-948ab.firebaseio.com")
					.build();
			
			// Firebase 처음 호출시에만 initializing 처리
			if(FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
			// String registrationToken = 안드로이드 토큰 입력;
			String registrationToken = tokenId;
			System.out.println("FcmUtil - tokenId : "+tokenId);
			
			// message 작성
			Message msg = Message.builder()
					.putData("clientMessage", clientMessage)
					.setAndroidConfig(AndroidConfig.builder()
							.setTtl(3600*1000) // 1 hour in milliseconds
							.setPriority(AndroidConfig.Priority.NORMAL)
							.setNotification(AndroidNotification.builder()
									.setTitle(title)
									.setBody(content)
									.setIcon("stock_ticker_update")
									.setColor("#00C8E0")
									.build())
							.build())
					.setToken(registrationToken)
					.build();
			
			// 메시지를 FirebaseMessaging에 보내기
			String respone = FirebaseMessaging.getInstance().send(msg);
			
			// 결과 출력
			System.out.println("Successfully sent message: "+respone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 여러 데이터를 client에게만 전달
//	public void send_FCM_List(String tokenId, String chatMessageList, String chattingRoom) {
//		try {
//			// 본인의 json 파일 경로 입력
//			FileInputStream refreshToken = new FileInputStream("C:\\Users\\dazol\\eclipse-workspace\\incar_server\\src\\main\\resources\\fcm\\incar-948ab-firebase-adminsdk-90jn3-dfcb31cce1.json");
//			
//			FirebaseOptions options = new FirebaseOptions.Builder()
//					.setCredentials(GoogleCredentials.fromStream(refreshToken))
//					.setDatabaseUrl("https://incar-948ab.firebaseio.com")
//					.build();
//			
//			// Firebase 처음 호출시에만 initializing 처리
//			if(FirebaseApp.getApps().isEmpty()) {
//				FirebaseApp.initializeApp(options);
//			}
//			// String registrationToken = 안드로이드 토큰 입력;
//			String registrationToken = tokenId;
//			
//			// message 작성
//			Message msg = Message.builder()
//					.putData("chatMessageList", chatMessageList)
//					.setAndroidConfig(AndroidConfig.builder()
//							.setTtl(3600*1000) // 1 hour in milliseconds
//							.setPriority(AndroidConfig.Priority.NORMAL)
//							.build())
//					.setToken(registrationToken)
//					.build();
//			
//			// 메시지를 FirebaseMessaging에 보내기
//			String respone = FirebaseMessaging.getInstance().send(msg);
//			
//			// 결과 출력
//			System.out.println("Successfully sent message: "+respone);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}
