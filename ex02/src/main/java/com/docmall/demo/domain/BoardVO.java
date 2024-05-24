package com.docmall.demo.domain;

import java.util.Date;

// 데이터베이스의 게시판테이블을 참조하는 클래스
/*
 CREATE TABLE BOARD (
    IDX     NUMBER    PRIMARY KEY, -- 글번호           시퀀스생성및사용
    TITLE    VARCHAR2(100)   NOT NULL, -- 제목
    CONTENT   VARCHAR2(1000)   NOT NULL, -- 내용
    WRITER  VARCHAR2(50)   NOT NULL, -- 작성자
    REGDATE   DATE   DEFAULT SYSDATE -- 등록일
);
 */
/*
 1)테이블명(BOARD)와 클래스명(boardVO) 이름이 상관이없다.
 2)테이블의 컬렴명과 클래스의 필드명은 동일해야 하고, 데이터타입은 호환이 되어야 한다.
 3)필드는 private 접근자사용. getter/setter 메서드 pubilc, toString()메서드 재정의
 */
public class BoardVO {
	
	private long idx;
	private String title;
	private String content;
	private String writer;
	private Date   regdate;
	
	
	public long getIdx() {
		return idx;
	}


	public void setIdx(long idx) {
		this.idx = idx;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public Date getRegdate() {
		return regdate;
	}


	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}


	// 필드들을 통한 데이터확인
	@Override
	public String toString() {
		return "BoardVO [idx=" + idx + ", title=" + title + ", content=" + content + ", writer=" + writer + ", regdate="
				+ regdate + "]";
	}
	
	
}
