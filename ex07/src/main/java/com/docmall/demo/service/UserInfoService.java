package com.docmall.demo.service;

import com.docmall.demo.domain.UserInfoVO;

public interface UserInfoService {

	// 아이디중복체크
	String idCheck(String u_id);
	
	// 비밀번호 암호화
	void join(UserInfoVO vo);
	
	// 로그인
	UserInfoVO login(String u_id);
}
