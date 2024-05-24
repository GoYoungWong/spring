package com.docmall.demo.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/*
 스프링부트 2.7 에서는 multipart가 기본 bean으로 등록되어있다.
 스프링부트 3 이상부터는 multipart 설정 클래스를 생성하여 bean으로 등록해야 한다.
 
 빈(bean) : 스프링(Spring) 컨테이너가 관리하는 자바 객체를 빈(Bean)이라 한다. 
           객체의 생성 및 제어권을 사용자가 아닌 스프링에게 맡기는 것이다. 
           스프링에 의하여 관리당하는 자바 객체를 사용한다. 이 객체를 '빈(bean)'이라 한다.
 */

// config 어노테이션 : 환경설정 클래스
@Configuration
public class MultipartConfig {

	// 스프링에서 자동으로 관리. 리턴타입 multipartResolver bean등록및관리. 
	// 클라언트가 입력한(.jsp)값이 cnotroller(호출된주소)로 갈떄 중간에 처리해주는 기능
	@Bean // 라이브러리에서 제공하는 클래스를 스프링에서 관리.
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	/* application.properties에 설정되어있는 파일업로드 설정과 같은것임
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(null);
		factory.setMaxRequestSize(null);
		factory.setMaxFileSize(null);
		
		return factory.createMultipartConfig();
	}
	*/
}
