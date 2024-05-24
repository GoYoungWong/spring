package com.docmall.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.service.UserInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// bean생성: userInfoController
@RequestMapping("/userinfo/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class UserInfoController {
	
	// UserInfoServiceImpl 클래스를 사용하지 않고
	// UserInfoService 인터페이스를 사용한 이유는 다형성목적
	private final UserInfoService userInfoService; 
	
	// 회원가입폼  /userinfo/join
	@GetMapping("/join")
	public void joinForm() {
		log.info("called join...");
	}
	
	// 아이디중복체크
	// String 이유? 아이디가 중복된다 아니다 라는 문자열을 클라이언트에게 보내기위해
	// 문제가 발생할수 있으니 throws Exception 예외처리를 해놓는게 좋다. 없어도 동작은 됨
	@GetMapping("/idCheck")
	public ResponseEntity<String> idCheck(String u_id) throws Exception{
		log.info("아이디: " + u_id);
		
		ResponseEntity<String> entity = null;
		
		String idUse = "";
		if(userInfoService.idCheck(u_id) != null) {
			idUse = "no"; // 사용불가능
		}else {
			idUse = "yes"; // 사용가능
		}
		
		// HttpStatus.OK: 상태코드 200번 
		entity = new ResponseEntity<String>(idUse, HttpStatus.OK);
		
		return entity;
		
	}
	
	
	
	
	
}
