package com.docmall.demo.mapper;

import java.util.List;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;

// @Mapper
public interface BoardMapper {
	// mapper인터페이스를 구현한 프록시에서 주입을 해줌
	
	
	// 글쓰기 저장
	// 사용자가 입력한 데이터
	void write(BoardVO vo);
	
	// 글목록. 사용안함
	// 테이블의 데이터를 읽어오는 거(select)
	List<BoardVO> list();
	
	// 페이징
	// 검색조건이 추가되는 목록
	List<BoardVO> listwithPaging(Criteria cri); // 데이터 여러개 참조할 경우
	
	// 페이징
	// 검색조건이 추가된 총 데이터개수
	int getTotalCount(Criteria cri);
	
	// 게시물조회
	BoardVO get(Long bno); // 데이터 하나만 참조할 경우
	
	//조회수증가
	void readCount(Long bno);
	
	// 글수정하기 <insert,update,delete 는 리턴값을 보통 void를 사용한다.>
	void modify(BoardVO vo);
	
	// 글삭제하기
	void delete(Long bno);
	
	
	
}
