package com.docmall.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

// 생성자 의존성 주입
// 이걸로 만들지 않으면 생성자를 수동으로 만들어야 한다.
@RequiredArgsConstructor // 생성자를 자동으로 만들어줌
@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;

	@Override
	public void write(BoardVO vo) {
		// TODO Auto-generated method stub
		boardMapper.write(vo);
	}

	@Override
	public List<BoardVO> listwithPaging(Criteria cri) {
		// TODO Auto-generated method stub
		return boardMapper.listwithPaging(cri);
	}

	@Override
	public int getTotalCount(Criteria cri) {
		// TODO Auto-generated method stub
		return boardMapper.getTotalCount(cri);
	}

	@Override
	public BoardVO get(Long bno) {
		
		boardMapper.readCount(bno);
		return boardMapper.get(bno);
	}

	@Override
	public void modify(BoardVO vo) {
		// TODO Auto-generated method stub
		boardMapper.modify(vo);
	}

	@Override
	public void delete(Long bno) {
		// TODO Auto-generated method stub
		boardMapper.delete(bno);
	}
}
