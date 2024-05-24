package com.docmall.demo.service;

import java.util.List;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;

public interface ReplyService {

		// 댓글목록
		List<ReplyVO> getListPaging(Criteria cri, Long bno);
		
		// 페이징작업
		int getCountByBno(Long bno);
}
