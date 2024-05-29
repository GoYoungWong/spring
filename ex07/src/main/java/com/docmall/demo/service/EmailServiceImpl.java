package com.docmall.demo.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.docmall.demo.dto.EmailDTO;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	// EmilConfig.java파일의 JavaMailSender()메서드가 스프링시스템에서 실행되어, 리턴되는 타입의 객체
	// 즉 bean을 생성 및 등록작업을 하고, 아래 객체에 주입을 해준다.
	private final JavaMailSender mailSender;
	
	// 타임리프 템플릿객체가 자동으로 내부적으로 처리해줌
	private final SpringTemplateEngine templateEngine; // 매개변수 생성자
	
	@Override
	public void sendMail(String type,EmailDTO dto, String authcode) {
		
		// 메일구성정보 담당(받는사람, 보내는사람, 받는사람 메일주소, 본문내용)
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			// 받는사람의 메일주소
//			mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(dto.getReceiverMail()));
//			// 보내는 사람(메일, 이름)
//			mimeMessage.addFrom(new InternetAddress[] {new InternetAddress(dto.getSenderMail(), dto.getSenderName())});
//			// 제목
//			mimeMessage.setSubject(dto.getSubject(), "utf-8");
//			// 본문내용
//			mimeMessage.setText(authcode, "utf-8");
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo(dto.getReceiverMail()); // 메일수신자
			mimeMessageHelper.setFrom(new InternetAddress(dto.getSenderMail(), dto.getSenderName()));
			mimeMessageHelper.setSubject(dto.getSubject()); // apdlfwpahr
			mimeMessageHelper.setText(setContext(authcode, type), true); // 메일 본문 내용, true: HTML 내용을 사용함
														// email.html파일을 가리킴
			// 메일발송기능
			mailSender.send(mimeMessage);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	// thymeleaf를 통한 html 적용
	// String authcode : 인증코드.  String type : email.html 확장자만 빠지고 들어옴
	public String setContext(String authcode,String type) {
		Context context = new Context();
		context.setVariable("authcode", authcode);
		return templateEngine.process(type, context);
	
	}

 }

