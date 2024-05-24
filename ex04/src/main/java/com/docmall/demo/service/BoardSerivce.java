package com.docmall.demo.service;

import java.util.List;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;

public interface BoardSerivce {

	// 글쓰기 저장
	void write(BoardVO vo);
	
	// 글목록
	List<BoardVO> list();
	
	// 페이징
	List<BoardVO> listwithPaging(Criteria cri);
	
	// 페이징
	// 총 데이터 수 
	int getTotalCount(Criteria cri);
	
	// 게시글 조회
	BoardVO get(Long bno);
	
	// 글수정하기
	void modify(BoardVO vo);
	
	// 글삭제하기
	void delete(Long bno);
}
