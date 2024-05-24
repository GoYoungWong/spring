package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.MemberVO;

@RequestMapping("/member/*")
@Controller
public class MemberController {
	
	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	// 회원가입폼
	@GetMapping("join")
	public void join() {
		logger.info("회원가입 폼");
	}
	
	// 회원저장
	@PostMapping("join") // DB의 테이블에 insert, update, delete 작업을하는 매핑주소는 String 으로 해야함
	public String join(MemberVO vo) {    // 내부에 setter가 동작한다.
		logger.info("회원가입 데이터" + vo);
		
		return "redirect:/member/list";
	}
	// 회원리스트
	@GetMapping("list")
	public void list() {
		logger.info("회원목록");
	}
}
