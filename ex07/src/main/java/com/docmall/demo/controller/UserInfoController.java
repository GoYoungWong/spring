package com.docmall.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.demo.domain.UserInfoVO;
import com.docmall.demo.service.UserInfoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// bean생성: userInfoController
@RequestMapping("/userinfo/*")
@Slf4j
@RequiredArgsConstructor

//메핑주소관리하는 클래스를 Controller클래스 라고 하고 @Controller를 달아야한다.
@Controller  // userInfoController bean 자동생성
public class UserInfoController {
	
	// UserInfoServiceImpl 클래스를 사용하지 않고
	// UserInfoService 인터페이스를 사용한 이유는 다형성목적
	private final UserInfoService userInfoService; 
	
	private final PasswordEncoder passwordEncoder;
	
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
	
	// insert, update, dlelet, 작업을 하고, 다른 주소로 이동을 할 경우에는 메서드의 리턴타입은 String 으로 해야한다.
	// 이유는? redirect: 사용해야 하므로.
	// 데이터베이스와 관련있는 메서드는 throws Exception 예외처리를 해준다.
	// 회원가입 저장
	@PostMapping("/join")
	public String join(UserInfoVO vo) throws Exception {
		
		
//		log.info("비밀번호: " + passwordEncoder.encode(vo.getU_pwd())); // 암호화 길이: 60바이트
//		log.info("암호화길이: " + "$2a$10$6BJg0c1LhdPBlbVby/BxwOlAywg/woJ8odQHhEh49frlLijDb1cZu".length());
		log.info("회원정보: " + vo);
		
		userInfoService.join(vo);
		
		return "redirect:/list";
	}
	
	// 로그인 폼
	@GetMapping("/login")
	public void loginForm() {
		
	}
	
	@PostMapping("/login")  // 파라미터 1)LoginDTO dto  2)String u_id, String u_pwd
	public String login(String u_id, String u_pwd, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		UserInfoVO vo = userInfoService.login(u_id);
		
		String msg = "";  // 아이디나 비번이 하나라도 틀리면 나오는 실패메세지
		String url = "/"; // "/" 메인주소
		
		if(vo != null) { // 아이디가 존재하는 경우
			// 비밀번호 비교            u_pwd: 사용자가 입력한 비번   vo.getU_pwd(): 암호화된 비번 
			if(passwordEncoder.matches(u_pwd, vo.getU_pwd())) { // 사용자가 입력한 비번이 암호화된 형태에 해당하는 것이라면 true.
				session.setAttribute("login_status", vo);
			}else {  // 사용자가 입력한 비번이 암호화된 형태에 해당하지 않는 것이라면 false.
				msg = "failPW";
				url = "userinfo/login";  // 로그인 폼 주소
			}
		}else {          // 아이디가 존재하지 않을 경우
			msg = "failID";
			url = "userinfo/login";  // 로그인 폼 주소
		}
		
		rttr.addAttribute("msg",msg); // jsp에서 msg변수를 사용목적
		
		return "redirect:" + url;  // 메인으로 이동.
		
	}
	
	
	
}
