package com.docmall.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j  // log 객체지원
@RequestMapping("/board/*")
@Controller
public class BoardController {

	//로그객체
//	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	//의존성 주입
	@Autowired
	private BoardService boardService;
	
	//글쓰기 폼
	@GetMapping("write")
	public void write() {
		log.info("called write...");
		
	}
	
	//글쓰기 저장
	@PostMapping("write")
	public String write(BoardVO vo) {
		
		log.info("게시판 입력데이타: " + vo);
		
		//db저장.
		boardService.write(vo);
		
		return "redirect:/board/list";
	}
	
	// 글목록
	@GetMapping("list")
	public void list(Model model ) {
		
		// 데이타소스(list)를 jsp에서 사용할 경우에는 파라미터를 Model 를 사용한다.
		
		List<BoardVO> list = boardService.list();
		model.addAttribute("list", list);
		
		log.info("리스트");
	}
	
	// 게시물조회, 게시물수정
	@GetMapping(value = {"get", "modify"})
	public void get(Long bno, Model model) {
		
		log.info("게시물번호: " + bno);
		
		//게시물정보 읽어오기(조회수증가 작업포함).
		BoardVO boardVO = boardService.get(bno);
		model.addAttribute("boardVO", boardVO);
	}
	
	//게시물 수정하기
	@PostMapping("modify")
	public String modify(BoardVO vo) {
		
		
		log.info("수정데이타: " + vo);
		
		boardService.modify(vo);
		
		return "redirect:/board/list";
	}
	
	//게시물 삭제하기
	@GetMapping("delete")
	public String delete(Long bno) {
		
		log.info("삭제 글번호:" + bno);
		
		boardService.delete(bno);
		
		return "redirect:/board/list";
	}
}
