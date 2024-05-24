package com.docmall.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.demo.domain.EmoloyeeVO;
import com.docmall.demo.mapper.EmployeesMapper;

// 구현클래스 : 인터페이스를 상속받는 클래스
@Service // 구현클래스에 만들어야함 상속하는 인터페이스에 만들지 말고
public class EmployeesServiceImpl implements EmployeesService {

	/*
	 스프링에서는 다른 클래스또는 인터페이스를 사용하기위하여
	 아래와 같은 객체생성작업을 하지 않는다.
	 EmployeesMapper emp = new EmployeesMapper();
	 emp.emp_list();
	 */
	
	// 스프링이 내부적으로 EmployeesMapper 인터페이스를 구현한 프록시클래스를 이용하여 객체를 생성하고
	// 그리고, 아래 객체에 주입(대입). 즉 참조를 해준다. (의존성 주입:Dependency Injection -> DI)
	
	@Autowired // 주입기능을 제공
	private EmployeesMapper employeesMapper;

	@Override
	public List<EmoloyeeVO> emp_list() {
		// TODO Auto-generated method stub
		return employeesMapper.emp_list();
	}
	
}
