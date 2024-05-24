package com.docmall.demo.service;

import org.springframework.stereotype.Service;

import com.docmall.demo.mapper.UserInfoMapper;

import lombok.RequiredArgsConstructor;

// bean생성: userInfoServiceImpl
@Service
@RequiredArgsConstructor  // 생성자를 대신 만들어준다.
public class UserInfoServiceImpl implements UserInfoService {

	private final UserInfoMapper userInfoMapper;

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
	
	
}
