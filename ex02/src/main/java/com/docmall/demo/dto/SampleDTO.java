package com.docmall.demo.dto;

public class SampleDTO {

	private String name;
	private int age;
	
	// 서버가 호출되면 데이터가 들어오기 전에 생성자가 먼저 호출된다.
	// 스프링은 생성자를 자동으로 생성된다.
	public SampleDTO() {
		System.out.println("SampleDTO 생성자 호출");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		System.out.println("setName 호출");
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		System.out.println("setage 호출");
		this.age = age;
	}

	@Override
	public String toString() {
		return "SampleDTO [name=" + name + ", age=" + age + "]";
	}
	
}
