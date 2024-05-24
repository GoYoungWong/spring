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
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Controller
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("write")
	public void write() {
		log.info("called write...");
	}
	
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
	// 1)Model : list에 데이터를 .jsp에서 사용하기 위해 사용
	// 2)Model : 데이터의 공유 동일한데이터를 다른 jsp에서 사용할 수 있음.
	@GetMapping("list")
	public void list(Criteria cri, Model model) {
		List<BoardVO> list = boardService.listwithPaging(cri);
		log.info("게시물 목록데이터: " + list);
		
		// 1) 게시물 목록10건
		model.addAttribute("list", list);
		
		// 전체데이터 개수
		int total = boardService.getTotalCount(cri);
		PageDTO pageDTO = new PageDTO(cri, total);
		
		log.info("페이징 기능데이터: " + pageDTO);
		model.addAttribute("pageMaker", pageDTO);
		
	}
	
	// 게시물 조회, 게시물 수정
		@GetMapping(value = {"get","modify"})
		public void get(Long bno,@ModelAttribute("cri") Criteria cri ,Model model) {
			
			log.info("게시물번호: " + bno);
			
			BoardVO boardVO = boardService.get(bno);
			model.addAttribute("boardVO", boardVO);
		}
		
		// 게시물 수정
		@PostMapping("modify")
		public String modify(BoardVO vo, Criteria cri) {
			
			log.info("수정데이터: " + vo);
			boardService.modify(vo);
			
			return "redirect:/board/list" + cri.getListLink();
		}
		
		@GetMapping("delete")
		public String delete(Long bno, Criteria cri) {
			log.info("삭제 글번호: " + bno);
			
			boardService.delete(bno);
			
			return "redirect:/board/list" + cri.getListLink();
			
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
