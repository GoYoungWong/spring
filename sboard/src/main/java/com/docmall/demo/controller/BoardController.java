package com.docmall.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.dto.PageDTO;
import com.docmall.demo.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 주입방식
@RequestMapping("/board/*")
@Controller
public class BoardController {

	// 생성자 주입방식
	private final BoardService boardService; 
	
	// 글쓰기 폼
	@GetMapping("write")
	public void write() {
		
		log.info("called write...");
	}
	
	// 글저장
	@PostMapping("write")
	public String write(BoardVO vo) {
		
		log.info("게시판 입력데이터: " + vo);
		
		boardService.write(vo);
		return "redirect:/board/list";
	}
	
	// 페이징이 들어간 list
	// 메서드의 파라미터를 Criteria cri 를 사용한 이유?
	// 클라이언트로부터 pageNum,amount,type,Keyword 필드의 값을 받기위한 목적
	// 처음에는 클라이언트로부터 받은 필드값이 없다. 기본생성자가 호출되어 필드값이 사용된다.
    // 객체를 자동으로 생성함
	@GetMapping("list")
	public void list(Criteria cri, Model model) {
		
		// 데이터 소스(list)를 jsp에서 사용할 경우에는 파라미터를 Model 을 사용한다.
		
		List<BoardVO> list = boardService.listwithPaging(cri);
		
		log.info("게시물 목록데이터: " + list);
		
		// 1)게시물 목록 10건.. jsp에서 참조하기 위해서
		model.addAttribute("list", list);
		
		// 전체 데이터 개수
		int total = boardService.getTotalCount(cri);
		PageDTO pageDTO = new PageDTO(cri, total);
		
		log.info("페이징 기능데이터: " + pageDTO);
		
		// 2)페이징기능  1 2 3 4 5 6 7 8 9 10 [next]
		// jsp에서 참조목적
		model.addAttribute("pageMaker", pageDTO);
	}
	
	// 게시물 조회, 게시물수정
	//@ModelAttribute : 파라미터로 사용하고 있는 Criteria cri 정보를 jsp에서 참조하고 싶은 경우
	// 사용자가 'get', 'modify'를 요청하든 밑에 코드가 작동되게 하는 코딩
	@GetMapping(value = {"get","modify"})
	public void get(Long bno, @ModelAttribute("cri") Criteria cri,Model model) {
		log.info("게시물번호: " + bno);
		
		// 게시물정보 읽어오기(조회수증가 작업포함)
		BoardVO boardVO = boardService.get(bno);
		model.addAttribute("boardVO", boardVO);
	}
	
	// 게시글 수정
	@PostMapping("modify")
	public String modify(BoardVO vo, Criteria cri) {
		log.info("수정데이터: " + vo);
		
		boardService.modify(vo);
		
		return "redirect:/board/list" + cri.getListLink();
			
	}
	
	@GetMapping("delete")
	public String delete(Long bno,Criteria cri) {
		log.info("삭제 게시물번호: " + bno);
		
		boardService.delete(bno);
		
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	
}
