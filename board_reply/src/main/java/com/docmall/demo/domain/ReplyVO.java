package com.docmall.demo.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 CREATE TABLE REPLY (
    rno     NUMBER   CONSTRAINT pk_reply primary key,  -- 댓글 일련번호
    bno     NUMBER   NULL,                             -- 게시판(board)의 bno컬럼의 참조글번호
    retext  VARCHAR2(100)   NOT NULL,                  -- 댓글내용
    replyer VARCHAR2(100)   NOT NULL,                  -- 댓글작성자
    replydate  DATE  DEFAULT SYSDATE,                  -- 뎃글 등록일
    updatedate DATE  DEFAULT SYSDATE                   -- 댓글 수정일
    
);
 */

@Getter
@Setter
@ToString
public class ReplyVO {
	
	private Integer rno;   // Integer 는 참조타입이라 기본값이 null 이다. 
	private Long    bno;
	private String  retext;
	private String  replyer;
	private Date    replydate;
	private Date    updatedate;
	
	
}

