package com.docmall.demo.mapper;

import com.docmall.demo.domain.UserInfoVO;

// UserInfoMapper.xml mapper파일 구성 정상
// 매개변수 이름은 같을 필요는 없다. 값이 들어와서 사용되기 때문에
// 인터페이스는 객체생성이 불가능하다.
// 스프링에서는 Mapper인터페이스르ㅡ구현한 프록시클래스가 내부적으로 bean으로 자동생성되어 의존성 주입을 하게된다.
public interface UserInfoMapper {
	
	String idCheck(String u_id);
	
	// 회원가입
	void join(UserInfoVO vo);
	
	// 로그인
	UserInfoVO login(String u_id);

}
