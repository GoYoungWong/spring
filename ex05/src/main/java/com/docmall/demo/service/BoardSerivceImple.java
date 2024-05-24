package com.docmall.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.mapper.BoardMapper;

// 구현클래스
@Service
public class BoardSerivceImple implements BoardSerivce{

	// 의존성 주입 : 필드주입방식
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public void write(BoardVO vo) {
		boardMapper.write(vo);
		
	}

	@Override
	public List<BoardVO> list() {
		// TODO Auto-generated method stub
		return boardMapper.list();
	}

	@Override
	public BoardVO get(Long bno) {
		
		// 조회수증가
		boardMapper.readCount(bno);
		
		return boardMapper.get(bno);
	}

	@Override
	public void modify(BoardVO vo) {
		boardMapper.modify(vo);
		
	}

	@Override
	public void delete(Long bno) {
		// TODO Auto-generated method stub
		boardMapper.delete(bno);
	}
	
	// 페이징
	@Override
	public List<BoardVO> listwithPaging(Criteria cri) {
		// TODO Auto-generated method stub
		return boardMapper.listwithPaging(cri);
	}
	// 페이징
	@Override
	public int getTotalCount(Criteria cri) {
		// TODO Auto-generated method stub
		return boardMapper.getTotalCount(cri);
	}
}
