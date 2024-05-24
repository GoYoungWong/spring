package com.docmall.demo.mapper;

import java.util.List;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;

public interface BoardMapper {
	
	// 글쓰기 저장
	// 사용자가 입력한 데이터↓
	void write(BoardVO vo);
	
	// 페이징
	// 검색조건이 추가된 목록
	List<BoardVO> listwithPaging(Criteria cri);
	
	// 페이징
	// 검색조건이 추가된 총 데이터개수
	int getTotalCount(Criteria cri);
	
	// 게시물 조회
	BoardVO get(Long bno);
	
	// 조회수 증가
	void readCount(Long bno);
	
	// 글수정
	void modify(BoardVO vo);
	
	// 글삭제
	void delete(Long bno);

}
