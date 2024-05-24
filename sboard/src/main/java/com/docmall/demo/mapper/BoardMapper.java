package com.docmall.demo.mapper;

import java.util.List;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;

public interface BoardMapper {

	// 글 저장
	void write(BoardVO vo);
	
	// 글목록
	// 페이징
	// 검색조건이 추가되는 목록
	List<BoardVO> listwithPaging(Criteria cri);
	
	// 페이징
	// 검색조건이 추가된 총 데이터개수
	int getTotalCount(Criteria cri);
	
	// 게시물 조회
	BoardVO get(Long bno); // 데이터 하나만 참조할 경우
	
	// 조회수증가
	void readCount(Long bno);
	
	// 게시글 수정 <insert,update,delete 는 리턴값을 보통 void를 사용한다.>
	void modify(BoardVO vo);
	
	// 글삭제
	void delete(Long bno);
	
}
