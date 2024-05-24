package com.docmall.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;

public interface ReplyMapper {

	// mapper인터페이스를 구현한 프록시객체에서 주입을 해줌
	
	// Mapper인터페이스 메서드 파라미터가 2개이상 일 경우에는 @Param("이름") 어노테이션 사용해야 한다.
	// Criteria - pageNum, amount, (type,keyword 제외)
	// bno - 게시물 번호 
	List<ReplyVO> getListPaging(@Param("cri") Criteria cri,@Param("bno") Long bno);
	
	// 페이징작업
	int getCountByBno(Long bno);
	
	// 댓글저장
	// insert : insert이 실행이 되면 자동으로 mapper.xml에 insert에서 자동으로 1이라는 값이 생성되는데 그 값을 사용하고 싶으면 int를 사용
	void insert(ReplyVO vo);
	
	// 글수정
	void update(ReplyVO vo);
	
	// 글삭제
	void delete(Integer rno);
}

