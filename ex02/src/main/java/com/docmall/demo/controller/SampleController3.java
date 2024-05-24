package com.docmall.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 @GetMapping 예제.
 */


@RequestMapping("/sample3/*")
@Controller
public class SampleController3 {

	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(SampleController3.class);
	
	// 클라이언트에서 사용하는 파라미터명과 스프링의 메서드 파라미터명이 일치하지 않을떄 중간에 @RequestParam("클라이언트 파라미터명") 사용
	// 매핑주소?  localhost:9090/sample3/doA?uname=홍길동&uage=100
	@GetMapping("doA")
	public void doA(@RequestParam("uname") String name,@RequestParam("uage") int age) {
		
		logger.info("이름: " + name);
		logger.info("나이는? " + age);
	}
	
	// 매핑주소?  localhost:9090/sample3/doB?age=100
	// 매핑주소?  localhost:9090/sample3/doB?name=홍길동&age=100
	// (@RequestParam(value = "클라이언트 파라미터명",required = 꼭 있어야 하는지 물어봄 true,false, defaultValue = "값이 없을때 출력할 값" )
	@GetMapping("doB")
	public void doB(@RequestParam(value = "name",required = false, defaultValue = "이름없음" ) String name, int age) {
		logger.info("이름: " + name);
		logger.info("나이는? " + age);
	}
	
	// 매핑주소?  localhost:9090/sample3/doC?age=100  에러발생
	// 매핑주소?  localhost:9090/sample3/doC?name=홍길동&age=100  
	@GetMapping("doC") // @RequestParam(value = "name",required = true)
	public void doC(@RequestParam(value = "name",required = true) String name, int age) {
		logger.info("이름: " + name);
		logger.info("나이는? " + age);
	}
	
	// 동일한 파라미터명으로 여러개의 값을 받을떄.
	// 매핑주소? localhost:9090/sample3/doD?num=1&num=2&num=3
	@GetMapping("doD")
	public void doD(@RequestParam("num") ArrayList<Integer> idx) {
		
		logger.info("idx: " + idx);
	}
	
	// 동일한 파라미터명으로 여러개의 값을 받을떄.
	// 매핑주소? localhost:9090/sample3/doE?num=1&num=2&num=3
	@GetMapping("doE")
	public void doE(@RequestParam("num") int[] idx) {
		
		logger.info("idx: " + Arrays.toString(idx));
	}
	
	// 동일한 파라미터명으로 여러개의 값을 받을떄.
	// 매핑주소? localhost:9090/sample3/doF?userid=user01&userid=user02&userid=user03
	@GetMapping("doF")
	public void doF(@RequestParam("userid") ArrayList<String> userid) {
		
		logger.info("idx: " + userid);
	}
}
