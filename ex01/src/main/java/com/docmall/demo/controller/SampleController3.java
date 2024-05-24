package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController3 {
	
	// 클래스명의 이 클래스가 호출될 떄 로그를 logger 이 이름으로 기록한다는 의미
	private static final Logger logger = LoggerFactory.getLogger(SampleController3.class);
	
	
	 // ?(물음표) 뒤에 문자열을 쿼리스트링(Query String)
	//1) http://localhost:9090/doG?name=abc&age=100    출력 : name=abc age=100
	//2) http://localhost:9090/doG?age=100          출력 : name=null age=100  참조타입은 null로 처리가되어 에러발생 안됨.
	//3) http://localhost:9090/doG?name=abc         출력 : 에러발생 원인? 파라미터가 기본데이터타입이면, 반드시 값을 제공해야 한다.
	 @RequestMapping("/doG")
	 public String doG(String name, int age) {
		 
		 logger.info("이름은? " + name);
		 logger.info("나이는? " + age);
		 return "testG";
	 }
}
