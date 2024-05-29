package com.docmall.demo.mapper;

import org.apache.ibatis.annotations.Param;

import com.docmall.demo.domain.UserInfoVO;

// UserInfoMapper.xml mapper파일 구성 정상
// 매개변수 이름은 같을 필요는 없다. 값이 들어와서 사용되기 때문에
// 인터페이스는 객체생성이 불가능하다.
// 스프링에서는 Mapper인터페이스르ㅡ구현한 프록시클래스가 내부적으로 bean으로 자동생성되어 의존성 주입을 하게된다.
public interface UserInfoMapper {
	
	// 아이디 중복체크
	String idCheck(String u_id);
	
	// 회원가입
	void join(UserInfoVO vo);
	
	// 로그인
	UserInfoVO login(String u_id);
	
	// 마이페이지수정
	void modify(UserInfoVO vo);
	
	// 신규비밀번호
	void changePw(@Param("u_id") String u_id,@Param("new_pwd") String new_pwd); // 파라미터 두개있을떄 @Param
		
	// 회원탈퇴
	void delete(String u_id);
	
	// 아이디 찾기
	String idfind(@Param("u_name") String u_name, @Param("u_email") String u_email);
	
	// 임시비밀번호
	String pwfind(@Param("u_id")String u_id,@Param("u_name") String u_name,@Param("u_email") String u_email);
	
	// 임시비밀번호업데이트
	void tempPwUpdate(@Param("u_id")String u_id,@Param("u_pwd") String u_pwd);

}
