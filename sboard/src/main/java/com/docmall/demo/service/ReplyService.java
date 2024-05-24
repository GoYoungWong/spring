package com.docmall.demo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;

public interface ReplyService {
	
	List<ReplyVO> getListPaging(Criteria cri,Long bno);
	
	// 페이징작업
	int getCountByBno(Long bno);
	
	// 댓글저장
	void insert(ReplyVO vo);

}
