package com.docmall.demo.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


// rno, bno, retext, replyer, replydate, updatedate
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
