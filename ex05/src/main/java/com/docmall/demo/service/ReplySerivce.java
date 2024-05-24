package com.docmall.demo.service;

import java.util.List;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;

public interface ReplySerivce {
	
	List<ReplyVO> getListPaging(Criteria cri,Long bno);
	
	// 페이징작업
	int getCountByBno(Long bno);
	
	// 댓글저장
	void insert(ReplyVO vo);
	
	// 글수정
	void update(ReplyVO vo);
	
	// 글삭제
	void delete(Integer rno);
}
