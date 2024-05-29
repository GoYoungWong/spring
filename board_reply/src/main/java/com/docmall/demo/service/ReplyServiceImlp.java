package com.docmall.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.mapper.ReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImlp implements ReplyService {
	
	private final ReplyMapper replyMapper;

	@Override
	public List<ReplyVO> getListPaging(Criteria cri, Long bno) {
		// TODO Auto-generated method stub
		return replyMapper.getListPaging(cri, bno);
	}

	@Override
	public int getCountByBno(Long bno) {
		// TODO Auto-generated method stub
		return replyMapper.getCountByBno(bno);
	}

	@Override
	public void insert(ReplyVO vo) {
		// TODO Auto-generated method stub
		replyMapper.insert(vo);
	}

	@Override
	public void update(ReplyVO vo) {
		// TODO Auto-generated method stub
		replyMapper.update(vo);
	}
}
