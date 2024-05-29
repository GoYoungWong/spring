package com.docmall.demo.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.demo.domain.UserInfoVO;
import com.docmall.demo.dto.EmailDTO;
import com.docmall.demo.service.EmailService;
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
	
	private final EmailService emailService;
	
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
		// 사용자가 입력한 아이디를 비교해서 있으면 null이 아니여서 true 사용불가
		// 사용자가 입력한 아이디를 비교해서 없으면 null이기 떄문에 false 사용가능
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
		
		return "redirect:/userinfo/login";
	}
	
	// 로그인 폼 화면
	@GetMapping("/login")
	public void loginForm() {
		
	}
	
	// 로그인 폼
	@PostMapping("/login")  // 파라미터 1)LoginDTO dto  2)String u_id, String u_pwd
	public String login(String u_id, String u_pwd, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		// u_id를 이용해 데이터베이스에서 사용자 정보를 가져옵니다. userInfoService.login(u_id) 메서드는 u_id에 해당하는 사용자 정보를 반환합니다.
		UserInfoVO vo = userInfoService.login(u_id);
		
		String msg = "";  // 사용자에게 전달할 메시지를 초기화합니다.
		String url = "/"; // 로그인 성공 시 리디렉션할 기본 URL을 설정합니다. 기본적으로 메인 주소로 설정합니다.
		
		if(vo != null) { // 사용자가 입력한 아이디에 해당하는 사용자 정보가 존재하는지 확인합니다.
			// 비밀번호 비교            
			if(passwordEncoder.matches(u_pwd, vo.getU_pwd())) { // 사용자가 입력한 비밀번호(u_pwd)가 데이터베이스에 저장된 암호화된 비밀번호(vo.getU_pwd())와 일치하는지 확인합니다.
				session.setAttribute("login_status", vo); // 비밀번호가 일치하면 사용자 정보를 세션에 login_status라는 이름으로 저장합니다.
			
			}else { // 비밀번호가 존재하지 않을경우  
				msg = "failPW"; // 비밀번호가 일치하지 않으면 실패 메시지를 설정합니다.
				url = "/userinfo/login";  // 로그인 폼 주소로 리디렉션할 URL을 설정합니다.
			}
		}else {          // 아이디가 존재하지 않을 경우
			msg = "failID"; // 아이디가 존재하지 않음을 나타내는 실패 메시지를 설정합니다.
			url = "/userinfo/login";  // 로그인 폼 주소
		}
		
		rttr.addFlashAttribute("msg",msg); // jsp에서 msg변수를 사용목적
		
		return "redirect:" + url;  // 로그인 폼 주소로 리디렉션할 URL을 설정합니다.
		
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate(); // 세션형태로 관리되는 모든 메모리는 소멸.
		
		return "redirect:/";
	}
	
	// 마이페이지 구성   Model model: 여기서 만든 정보를 jsp에서 사용하기 위해
	@GetMapping("/mypage") // 인증된 사용자만 사용가능
	public void mypage(HttpSession session, Model model) throws Exception {
		
		// 세션에서 login_status라는 이름으로 저장된 사용자 정보를 가져옵니다. 
		// 이 정보는 UserInfoVO 객체로 캐스팅된 후, 사용자 아이디(u_id)를 추출합니다.
		String u_id = ((UserInfoVO) session.getAttribute("login_status")).getU_id();
		
		// u_id를 이용해 데이터베이스에서 사용자 정보를 가져옵니다.
		// userInfoService.login(u_id) 메서드는 u_id에 해당하는 사용자 정보를 반환합니다.
		UserInfoVO vo = userInfoService.login(u_id);
		
		// 사용자 정보를 userinfo라는 이름으로 모델에 추가합니다. 이렇게 추가된 정보는 JSP 페이지에서 사용할 수 있습니다.
		model.addAttribute("userinfo", vo);
	}
	
	// 마이페이지 수정          HttpSession session: 인증이나 수정에 필요   RedirectAttributes rttr: 메세지를 출력하기위해
	@PostMapping("/modify")
	public String modify(UserInfoVO vo,HttpSession session, RedirectAttributes rttr) throws Exception{
		
		// 인터셉터는 나중에 보충설명
		// 사용자가 로그인 상태인지 확인합니다. 로그인하지 않은 경우 로그인 페이지로 리다이렉트합니다.
		if(session.getAttribute("login_status") == null) return "redirect:/userinfo/login"; 
		
		// 세션에서 login_status라는 이름으로 저장된 사용자 정보를 가져와 UserInfoVO 객체로 캐스팅한 후, 사용자 아이디(u_id)를 추출합니다.
		String u_id = ((UserInfoVO) session.getAttribute("login_status")).getU_id();
		
		log.info("수정데이터: " + vo);
		
		// UserInfoService를 통해 사용자 정보를 업데이트합니다. vo 객체에 수정된 사용자 정보가 담겨 있습니다.
		userInfoService.modify(vo);
		
		// 리다이렉트 후에도 유지되는 플래시 속성에 성공 메시지를 추가합니다.
		rttr.addFlashAttribute("msg", "success");
		
		// 마이페이지로 리다이렉트합니다. 사용자가 자신의 수정된 정보를 확인할 수 있도록 합니다.
		return "redirect:/userinfo/mypage";
	}
	
	// 비밀번호변경 화면
	@GetMapping("/changepw")
	public void changepw() {
		
	}
	
	// 비밀번호 변경
	@PostMapping("/changepw")
	public String changepw(String cur_pwd, String new_pwd, HttpSession session, RedirectAttributes rttr) {
		
		// 세션에서 login_status라는 이름으로 저장된 사용자 정보를 가져와 UserInfoVO 객체로 캐스팅한 후, 사용자 아이디(u_id)를 추출합니다.
		String u_id = ((UserInfoVO) session.getAttribute("login_status")).getU_id();
		
		// 사용자 아이디를 이용해 데이터베이스에서 사용자 정보를 가져옵니다.
		UserInfoVO vo = userInfoService.login(u_id);
		
		String msg = "";  // 실패 메시지를 초기화합니다.
		
		if(vo != null) { // 아이디가 존재하는 경우(사용자가 존재하는지 확인합니다.)
			
			// 비밀번호 비교             
			// 사용자가 입력한 현재 비밀번호(cur_pwd)가 데이터베이스에 저장된 암호화된 비밀번호(vo.getU_pwd())와 일치하는지 확인합니다.
			if(passwordEncoder.matches(cur_pwd, vo.getU_pwd())) {  
				
				// 신규비밀번호 변경작업
				// 새 비밀번호를 암호화합니다.
				String u_pwd = passwordEncoder.encode(new_pwd);
				
				// 암호화된 새 비밀번호를 데이터베이스에 업데이트합니다.
				userInfoService.changePw(u_id, u_pwd);
				msg = "success"; // 성공 메시지를 설정합니다.
			
			}else {  // 사용자가 입력한 현재 비밀번호가 일치하지 않는 경우, 실패 메시지를 설정합니다.
				msg = "failPW";
			}
		}
		// 리다이렉트 후에도 유지되는 플래시 속성에 메시지를 추가합니다.
		rttr.addFlashAttribute("msg",msg); // jsp에서 msg변수를 사용목적
		
		// 비밀번호 변경 페이지로 리다이렉트합니다. 사용자가 메시지를 확인할 수 있도록 합니다.
		return "redirect:/userinfo/changepw";
		
	}
	
	// 회원탈퇴 화면
	@GetMapping("/delete")
	public void delete() {
		
	}
	
	// 회원탈퇴 
	@PostMapping("/delete")
	public String delete(String u_pwd,HttpSession session, RedirectAttributes rttr) throws Exception {
		
		// 세션에서 login_status라는 이름으로 저장된 사용자 정보를 가져와 UserInfoVO 객체로 캐스팅한 후, 사용자 아이디(u_id)를 추출합니다.
		String u_id = ((UserInfoVO) session.getAttribute("login_status")).getU_id();
		
		// 사용자 아이디를 이용해 데이터베이스에서 사용자 정보를 가져옵니다.
		UserInfoVO vo = userInfoService.login(u_id);
		
		String msg = "";  // 실패 메시지를 초기화합니다.
		String url = "";  // 리다이렉트할 URL을 초기화합니다.
		
		if(vo != null) { // 아이디가 존재하는 경우(사용자가 존재하는지 확인합니다.)
			
			// 비밀번호 비교           
			// 사용자가 입력한 비밀번호(u_pwd)가 데이터베이스에 저장된 암호화된 비밀번호(vo.getU_pwd())와 일치하는지 확인합니다.
			if(passwordEncoder.matches(u_pwd, vo.getU_pwd())) { 
			
				// 사용자의 계정을 삭제합니다.
				userInfoService.delete(u_id);  // 사용자가 입력한 비번이 Encdoer에서 매번바뀌기 때문에 매개변수로 사용하면 안됨
				// 세션을 삭제합니다.(세션을 무효화하여 사용자 로그아웃을 처리합니다.)
				session.invalidate();
				msg = "success"; // 성공 메시지를 설정합니다.
				url = "/"; // 리다이렉트할 URL을 메인 페이지로 설정합니다.
				
			}else {  // 사용자가 입력한 비번이 암호화된 형태에 해당하지 않는 것이라면 false.
				msg = "failPW"; // 사용자가 입력한 비밀번호가 일치하지 않는 경우, 실패 메시지를 설정합니다.
				url = "/userinfo/delete";  // 리다이렉트할 URL을 계정 삭제 페이지로 설정합니다.
			}
		}
		// 리다이렉트 후에도 유지되는 플래시 속성에 메시지를 추가합니다.
		rttr.addFlashAttribute("msg",msg); // jsp에서 msg변수를 사용목적
		
		return "redirect:" + url; // 설정된 URL로 리다이렉트합니다.
	}
	
	// 아이디찾기 화면
	@GetMapping("/idfind")
	public void idfind() {
		
	}
	// 아이디 찾기
	@PostMapping("/idfind")
	// 사용자가 입력한 이름(u_name), 이메일(u_email), 인증 코드(authcode), 세션 객체, 리다이렉트 속성 객체를 매개변수로 받는 메서드입니다.
	public String idfind(String u_name, String u_email,String authcode, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String url = ""; // 리다이렉트할 URL을 초기화합니다.
		String msg = ""; // 메시지를 초기화합니다.
		
		// 인증코드 확인
		// 사용자가 입력한 인증 코드(authcode)가 세션에 저장된 인증 코드와 일치하는지 확인합니다.
		if(authcode.equals(session.getAttribute("authcode"))) {

			// 이름(u_name)과 이메일(u_email)을 사용하여 데이터베이스에서 아이디(u_id)를 찾습니다.
			String u_id = userInfoService.idfind(u_name, u_email);
			if(u_id != null) { // 아이디가 존재하는지 확인합니다.
				
				// 아이디를 내용으로 메일발송작업(이메일 제목을 설정합니다)
				String subject = "DocMail 아이디를 보냅니다.";
				
				// "DocMail" 보낸사람 이름 또는 이메일계정, "DocMail" 실제 보낸 사람 이메일, u_email 받는 사람이메일, subject 이메일제목, u_id 사용자의 ID 또는 기타추가정보
				// 이메일 발송에 필요한 정보를 담은 EmailDTO 객체를 생성합니다.
				EmailDTO dto = new EmailDTO("DocMail","DocMail", u_email, subject, u_id);
				
				// 이메일을 발송합니다.
				emailService.sendMail("emailIDResuit", dto, u_id);
				
				// 인증코드값 메모리에서 삭제
				session.removeAttribute("authcode");
				
				msg = "success"; // 성공 메시지를 설정합니다.
				url = "/userinfo/login"; // 리다이렉트할 URL을 로그인 페이지로 설정합니다.
				rttr.addFlashAttribute("msg", msg); // 리다이렉트 후에도 유지되는 플래시 속성에 메시지를 추가합니다.
				
			}else {
				msg = "idfail"; // 아이디를 찾지 못한 경우 실패 메시지를 설정합니다.
				url = "/userinfo/idfind"; // 리다이렉트할 URL을 아이디 찾기 페이지로 설정합니다.
			}
			
			
		}else {  // 인증코드가 틀렸을때
			msg = "failAuthCode"; // 인증 코드가 틀린 경우 실패 메시지를 설정합니다.
			url = "/userinfo/findid";// 리다이렉트할 URL을 아이디 찾기 페이지로 설정합니다.
		}

		rttr.addFlashAttribute("msg", msg); // 리다이렉트 후에도 유지되는 플래시 속성에 메시지를 추가합니다.
		
		return "redirect:" + url; // 설정된 URL로 리다이렉트합니다.
	}
	
	// 비밀번호 재설정(비밀번호 찾기) 화면
	@GetMapping("/pwfind")
	public void pwfind() {
		
	}
	
	// 비밀번호 재설정(비밀번호 찾기) 
	@PostMapping("/pwfind")
	// 사용자가 입력한 아이디(u_id), 이름(u_name), 이메일(u_email), 인증 코드(authcode), 세션 객체, 리다이렉트 속성 객체를 매개변수로 받는 메서드입니다. 
	public String pwfind(String u_id, String u_name, String u_email,String authcode, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String url = ""; // 리다이렉트할 URL을 초기화합니다.
		String msg = ""; // 메세지를 초기화 합니다.
		
		// 인증코드 확인
		// 사용자가 입력한 인증 코드(authcode)가 세션에 저장된 인증 코드와 일치하는지 확인합니다.
		if(authcode.equals(session.getAttribute("authcode"))) { 
			
			// 사용자가 입력한 아이디(u_id), 이름(u_name), 이메일(u_email)을 조건으로 데이터베이스에서 이메일(d_email)을 가져옵니다.
			String d_email = userInfoService.pwfind(u_id, u_name, u_email);
			if(d_email != null) { // 이메일이 존재하는지 확인합니다.
				
				// 임시 비밀번호 생성(UUID 이용)
				// UUID를 이용하여 임시 비밀번호를 생성하고, 하이픈(-)을 제거합니다.
				String tempPw = UUID.randomUUID().toString().replaceAll("-", "");  // - 를 제거
				// 임시 비밀번호를 10자리로 제한합니다.
				tempPw = tempPw.substring(0, 10);
				
				// 암호화된 비밀번호(임시 비밀번호를 암호화합니다.)
				String enc_tempPw = passwordEncoder.encode(tempPw);
				
				// 암호화된 임시 비밀번호를 데이터베이스에 업데이트합니다.
				userInfoService.tempPwUpdate(u_id, enc_tempPw);
				
				// 이메일 발송에 필요한 정보를 담은 EmailDTO 객체를 생성합니다.
				EmailDTO dto = new EmailDTO("DocMail", "DocMail", d_email, "DocMail에서 임시비밀번호를 보냅니다.", tempPw);
				
				// 임시 비밀번호를 이메일로 발송합니다.
				emailService.sendMail("emailPwResuit", dto, tempPw);  // "타임리프파일명"
				
				// 인증 코드를 세션에서 삭제합니다.
				session.removeAttribute("authcode"); 
				
				url = "/userinfo/login"; // 리다이렉트할 URL을 로그인 페이지로 설정합니다.
			
			}else {
				url = "/userinfo/pwfind"; // 이메일이 존재하지 않을 경우 리다이렉트할 URL을 비밀번호 찾기 페이지로 설정합니다.
				msg = "failInput"; // 실패 메시지를 설정합니다.
			}
			
		}else {
			url = "/userinfo/pwfind"; // 인증 코드가 일치하지 않을 경우 리다이렉트할 URL을 비밀번호 찾기 페이지로 설정합니다.
			msg = "failAuth"; // 인증 실패 메시지를 설정합니다.
		}
		rttr.addFlashAttribute("msg", msg); // 리다이렉트 후에도 유지되는 플래시 속성에 메시지를 추가합니다.
		
		return "redirect:" + url; // 설정된 URL로 리다이렉트합니다.
	}
	
}
