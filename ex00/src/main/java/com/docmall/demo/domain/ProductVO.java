package com.docmall.demo.domain;

// 오라클 데이터베이스에 product 테이블에 생성하고 구조를 정의한 클래스
public class ProductVO {
	
	private String name;  // 상품이름
	private double price; // 상품가격
	

	public ProductVO(String name, double price) { 
        //super();   // 컴파일 과정에서 자동생성됨
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductVO [name=" + name + ", price=" + price + "]";
	}
	
	
	
}
