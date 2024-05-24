package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 http://thalals.tistory.com/268
 ResponseEntity 클래스 : 일반적으로 ajax기술을 이용할 때 사용.
 명시적으로 서버에서 클라이언트에게 보내는 데이터와 그 데이터 설명에 해당하는 MINE-TYPE,인코딩,상태코드를 보내서 작업하기위한 클래스이다.
 */

@RequestMapping("/sample5/*")
@Controller
public class SampleController5 {

	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(SampleController5.class);
	
	// @ResponseBody: 사용안하면 jsp파일로 인식하고 있으면 순수하게 리턴값을 그대로 보냄
	@GetMapping("doA")
	public @ResponseBody String doA() {
		
		logger.info("@ResponseBody 사용");
		String msg = "{\"name\" : \"홍길동\"}"; // json포맷의 문자열
		
		return msg;
	}
	
	@GetMapping("doB")
	public ResponseEntity<String> doB() {
		
		ResponseEntity<String> entity = null;
		
		// 1) body 서버에서 클라이언트(브라우저)에게 전송하는 데이터
		String msg = "{\"name\" : \"홍길동\"}"; // json포맷의 문자열
		
		logger.info("ResponseEntity 사용");
		
		// 2) body 데이터를 해석하기위한 MINE-TYPE, 인코딩정보를 작업.
		// application/json : MINE-TYPE
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json;charset=UTF-8");
		
		// 3) 상태코드 : HttpStatus.OK -> 서버에 프로그램이 설공적으로 실행됬다.
		entity = new ResponseEntity<String>(msg,headers,HttpStatus.OK);
		
		return entity;
	}
}
