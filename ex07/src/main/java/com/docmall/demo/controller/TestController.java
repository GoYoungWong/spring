package com.docmall.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/Thymeleaf/*")
public class TestController {
	
	@GetMapping("/test2")
	public String test() {
		log.info("called test...");
		
		return "/thymeleaf/test2"; // 뷰이름(view name)이 jsp or Thymeleaf 둘중 어떤걸로 해석되는 나?
	}

}
