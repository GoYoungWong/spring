package com.docmall.demo.service;

import java.util.List;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;

/*
 - Service를 만드는 이유
 비즈니스 로직을 캡슐화하고 재사용성을 높이며, 유지 보수와 테스트를 용이하게 하기 위함입니다.
서비스 계층을 통해 트랜잭션을 관리하고, 컨트롤러와 DAO 사이의 명확한 역할 분리를 할 수 있습니다.
 */


public interface BoardService {
	
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
	
	// 글수정
	void modify(BoardVO vo);
	
	// 글삭제
	void delete(Long bno);
		


}
