package com.docmall.demo.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/* -- u_id, u_pwd, u_name, u_email, u_zip_code, u_addr, u_addrdetail, u_phone, u_regdate*/ 

@Getter
@Setter
@ToString
public class UserInfoVO {

	private String u_id;
	private String u_pwd;
	private String u_name;
	private String u_email;
	private String u_zip_code;
	private String u_addr;
	private String u_addrdetail;
	private String u_phone;
	private Date   u_regdate;     // 날짜관련 클래스 Calendar, LocalDate, LocalDateTime, LocalTime
	
}
