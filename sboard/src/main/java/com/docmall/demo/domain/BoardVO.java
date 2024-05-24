package com.docmall.demo.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
CREATE TABLE BOARD (
   BNO      NUMBER,
   TITLE    VARCHAR2(100)  NOT NULL,
   CONTENT  VARCHAR2(1000)  NOT NULL,
   WRITER   VARCHAR2(100)   NOT NULL,
   REGDATE  DATE     DEFAULT SYSDATE,
   UPDATEDATE DATE   DEFAULT SYSDATE,
   VIEWCOUNT  NUMBER  DEFAULT 0,
   CONSTRAINT PK_BOARD PRIMARY KEY(BON)
);
*/
//bno, title, content, writer, regdate, updatedate, viewcount

// lombok
@Getter
@Setter
@ToString
public class BoardVO {

	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date   regdate;
	private Date   updatedate;
	private int    viewcount;
}
