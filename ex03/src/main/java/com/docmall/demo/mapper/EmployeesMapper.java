package com.docmall.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.docmall.demo.domain.EmoloyeeVO;

@Mapper
public interface EmployeesMapper {
	
	// Employees 테이블의 데이터 10건을 불러오는 기능의 추상메서드
	// List<EmoloyeeVO> : Employees 테이블의 읽어 온 데이터를 List컬렉션으로 저장하고자 한다.
	// 목록형태(여러개)의 데이터는 List컬렉션 저장소를 사용. 예>회원목록, 상품목록, 주문목록, 게시판목록, 등등
	// 메서드가 하는 역할에 따라 리턴타입을 달리한다.
	List<EmoloyeeVO> emp_list();
	
	
}
