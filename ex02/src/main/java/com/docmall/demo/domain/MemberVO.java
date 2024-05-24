package com.docmall.demo.domain;

import java.util.Date;

/*
 CREATE TABLE t_member (
    id  VARCHAR2(10)    PRIMARY KEY,
    pwd VARCHAR2(10)    NOT NULL,
    name VARCHAR2(50)   NOT NULL,
    email VARCHAR2(50)  NOT NULL,
    joinDate  DATE DEFAULT SYSDATE
);
 */

public class MemberVO {

	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date   joinDate;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", joinDate=" + joinDate
				+ "]";
	}
	
	
}
