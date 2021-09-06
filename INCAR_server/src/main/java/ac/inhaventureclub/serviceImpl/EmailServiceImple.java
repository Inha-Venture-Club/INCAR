package ac.inhaventureclub.serviceImpl;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;

import org.springframework.stereotype.Service;
import ac.inhaventureclub.service.EmailService;

@Service("emailservice")
public class EmailServiceImple implements EmailService{
	final String id = ""; //발신자의 이메일 아이디를 입력
	final String pw = ""; //발신자 이메일의 패스워드를 입력
	@Override
	public String getRandomAndSendEmail(String address) {
		String host = "smtp.gmail.com"; //smtp.gmail.com
		int port = 465; //465:SSL // 587:STARTTLS or TLS
		String randomValue = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		
		//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(id, pw);
			}
		});
		session.setDebug(true);
		
		MimeMessage mimeMessage = new MimeMessage(session);
		
		try {
			Random random = new Random();
			int dice = random.nextInt(4589362)+49311;
			randomValue = Integer.toString(dice);
			
			
			mimeMessage.setFrom(new InternetAddress(id));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
			//수신자셋팅 
			//.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음 
			mimeMessage.setSubject("[INCAR] 회원가입 인증 이메일"); 
			//제목셋팅 
			
			String content = 
					System.getProperty("line.separator")+					
					System.getProperty("line.separator")+
					"안녕하세요. 인하대학교 카풀앱 INCAR입니다."+
					System.getProperty("line.separator")+
					"회원가입을 위해 아래의 인증코드를 입력해주세요."+
					System.getProperty("line.separator")+
					System.getProperty("line.separator")+
					"인증번호는 ["+ randomValue +"]입니다."+
					System.getProperty("line.separator")+
					"인증번호를 입력해야만 회원가입이 완료됩니다.";
			
			mimeMessage.setText(content); 
			//내용셋팅 
			Transport.send(mimeMessage); 
		
			//javax.mail.Transport.send() 이용
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return randomValue;
		
	  }

}
