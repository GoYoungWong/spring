package com.docmall.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.demo.dto.EmailDTO;
import com.docmall.demo.service.EmailService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController     // jsp사용 안함
@RequestMapping("/email/*")
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;
	
	// 백엔드는 한번사용된 변수는 모두 소멸하기 때문에 인증코드가 남아있지 않아 제대로 인증이 되지 않는다.
	// 그래서 발송한 인증코드를 세션객체를 이용하여 메모리에 저장하기 위해 HttpSession session 파라미터를 넣어준다.
	
	// 스프링이 밑에 작업을 자동으로 처리해준다.
	// EmailDTO dto = new EmailDTO();
	// dto.setReceiverMail("입력한 메일주소");
	@GetMapping("/authcode")                            
	public ResponseEntity<String> authcode(EmailDTO dto, HttpSession session) {
		
		
		log.info("dto: ", dto);  // dto.toString() 호출
		ResponseEntity<String> entity = null;
		
		// 인증코드작업
		String authcode ="";
		for(int i=0; i <6; i++) {
			authcode += String.valueOf((int)(Math.random() * 10));
		}
		
		log.info("인증코드: " + authcode);
		
		// 사용자가 자신의 메일에서 발급받은 인증코드를 읽고, 회원가입시 인증확인란에 입력을 하게되면, 서버에서 비교목적으로 세션방식으로 인증코드를 메모리에 저장해두어애 한다.
		session.setAttribute("authcode", authcode); // tomcat서버내장 세션기본시간 30분. 30분이 지나면 세션 메모리에 저장된 인증코드가 사라짐
		
		try {
			// 메일발송.
			emailService.sendMail(dto, authcode);
			entity = new ResponseEntity<String>("success", HttpStatus.OK); // 200
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
		
		return entity;
	}
	
	// 메일인증코드확인
	@GetMapping("/confirm_authcode")
	public ResponseEntity<String> confirm_authcode(String authcode, HttpSession session) {
		ResponseEntity<String> entity = null;
		
		// session.setAttribute("authcode", authcode); - 세션에 저장되는 인증코드값을 authcode로 저장함
		// 세션이 유지되고 있는 동안
		if(session.getAttribute("authcode") != null) {
		
			if(authcode.equals(session.getAttribute("authcode"))) {
				entity = new ResponseEntity<String>("success", HttpStatus.OK); // 서버측에 인증코드가 일치한다는 뜻
				session.removeAttribute("authcode");  // 서버의 메모리를 초기화. 인증을 완료하고 필요없어진 세션메모리를 지워준다.
			}else {
				entity = new ResponseEntity<String>("fail", HttpStatus.OK); // 일치하지 않다는 뜻
			}
			
		}else {  // 세션이 소멸되었을 경우
			entity = new ResponseEntity<String>("request", HttpStatus.OK); // 인증코드를 다시 요청하게 하기위해
		}
		
		return entity;
	}
}











