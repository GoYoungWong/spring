package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.dto.SampleDTO;
import com.docmall.demo.dto.SampleDTOList;

/*
 SampleDTOList 예제.
 */



@RequestMapping("/sample4/*")
@Controller
public class SampleController4 {

	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(SampleController4.class);

	@GetMapping("basicForm")
	public void basicForm() {
		logger.info("basicForm called...");
	}

	// 기본생성자호출(메모리생성) -> setAge(),setName() 각각호출되어, dto객체가 가리키는 립영역의 기억장소에 입력데이터가 저장.
	@PostMapping("basicPro")
	public void basicPro(SampleDTO dto) {
		logger.info("1인 회원정보: " + dto);
	}
	
/***************************************************************/	
	
	// 회원정보를 여러개 처리할때 sampleDTOList(예제이름임 이거보고 참고하라는 뜻)로 처리해라
	@GetMapping("basicForm2")
	public void basicForm2() {
		logger.info("basicForm2 called...");
	}
	
	@PostMapping("basicPro2")
	public void basicPro2(SampleDTOList lst) {
		logger.info("다수 회원정보: " + lst);
	}

}
