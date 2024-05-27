package com.docmall.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.docmall.demo.domain.UserInfoVO;
import com.docmall.demo.mapper.UserInfoMapper;

import lombok.RequiredArgsConstructor;

// bean생성: userInfoServiceImpl
@Service  // 중간계층의 비지니스로직을 목적으로 하는 클래스  userInfoServiceImpl bean 자동생성
@RequiredArgsConstructor  // 생성자를 대신 만들어준다.
public class UserInfoServiceImpl implements UserInfoService {

	private final UserInfoMapper userInfoMapper;
	
	private final PasswordEncoder passwordEncoder;

	// 롬복에서 제공하는 @RequiredArgsConstructor를 사용하지 않으면, 아래의 생성자 구문을 수동으로 작성해야 한다.
	// 생성자 주입
	/*
	- lombok이 없으면 의존성 주입시 생성자를 수동으로 만들어야함
	- 호출을 할때 mapper에서 객체를 만드는게 아니라 프록시객체가 참조해서 넘어온다.
	public UserInfoServiceImpl(UserInfoMapper userInfoMapper) {
		this.userInfoMapper = userInfoMapper;
	}
	*/
	
	@Override
	public String idCheck(String u_id) {
		// TODO Auto-generated method stub
		return userInfoMapper.idCheck(u_id);
	}

	@Override
	public void join(UserInfoVO vo) {
		
		// 비밀번호 암호화
		// vo.getU_pwd(): 사용자 객체(VO)로부터 비밀번호를 가져옵니다.
		// passwordEncoder.encode: 스프링 시큐리티의 PasswordEncoder를 사용하여 비밀번호를 암호화합니다.
		// vo.setU_pwd: 암호화된 비밀번호를 다시 vo.setU_pwd()를 사용하여 사용자 객체의 암호화된 비밀번호로 설정합니다.
		vo.setU_pwd(passwordEncoder.encode(vo.getU_pwd()));
		
		userInfoMapper.join(vo);
		
	}

	@Override
	public UserInfoVO login(String u_id) {
		// TODO Auto-generated method stub
		return userInfoMapper.login(u_id);
	}
	
	
}
