package com.docmall.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.dto.PageDTO;
import com.docmall.demo.service.BoardSerivce;

import lombok.extern.slf4j.Slf4j;



@Slf4j    // log 객체지원
@RequestMapping("/board/*")
@Controller
public class BoardController {
	
		// 로그객체
		private static final Logger logger = LoggerFactory.getLogger(BoardController.class);


		// 의존성 주입
		@Autowired
		private BoardSerivce boardSerivce;
		
		// 글쓰기 폼
		@GetMapping("write")
		public void write() {
			log.info("called write...");
		}
		
		// 글쓰기 저장
		@PostMapping("write")
		public String write(BoardVO vo) {
			
			log.info("게시판 입력데이터: " + vo);
			
			//db저장.
			boardSerivce.write(vo);
			
			return "redirect:/board/list";
		}
		
		// 글목록
		/*
		@GetMapping("list")
		public void list(Model model) {
			
			// 데이터 소스(list)를 jsp에서 사용할 경우에는 파라미터를 Model 을 사용한다.
			
			List<BoardVO> list = boardSerivce.list();
			model.addAttribute("list" ,list);
			
			logger.info("리스트");
		}
		*/
		
		// 페이징이 들어간 list
		// 메서드의 파라미터를 Criteria cri 를 사용한 이유?
		// 클라이언트로부터 pageNum,amount,type,Keyword 필드의 값을 받기위한 목적
		// 처음에는 클라이언트로부터 받은 필드값이 없다. 기본생성자가 호출되어 필드값이 사용된다.
		 // 객체를 자동으로 생성함
		@GetMapping("list")
		public void list(Criteria cri,Model model) {
			
			// 데이터 소스(list)를 jsp에서 사용할 경우에는 파라미터를 Model 을 사용한다.
			
			
			List<BoardVO> list = boardSerivce.listwithPaging(cri);
			
			log.info("게시물 목록데이터: " + list);
			
			// 1)게시물 목록 10건
			model.addAttribute("list", list);
			
			// 전체 데이터 개수
			int total = boardSerivce.getTotalCount(cri);
			PageDTO pageDTO = new PageDTO(cri, total);
			
			log.info("페이징 기능데이터: " + pageDTO);
			
			// 2)페이징기능  1 2 3 4 5 6 7 8 9 10 [next]
			model.addAttribute("pageMaker", pageDTO);
		}
		
		
		// 게시물 조회, 게시물수정
		//@ModelAttribute : 메소드의 파라미터 Criteria cri 를 "cri"를 통해서 jsp에서 사용하기 위해 사용
		@GetMapping(value = { "get","modify"})
		public void get(Long bno, @ModelAttribute("cri") Criteria cri ,Model model) {
			
			log.info("게시물번호: " + bno);
			
			// 게시물정보 읽어오기(조회수증가 작업포함)
			BoardVO boardVO = boardSerivce.get(bno);
			model.addAttribute("boardVO",boardVO);
		}
		
		// 게시물 수정
		@PostMapping("modify")
		public String modify(BoardVO vo, Criteria cri) {
			
			log.info("수정데이터: " + vo);
			
			boardSerivce.modify(vo);
			
			return "redirect:/board/list" + cri.getListLink();
		}
		// insert,update,delete는 Strnig을 사용
		// 게시물 삭제하기
		// RedirectAttributes : rttr에 있는 파라미터의 값을 주소뒤에 자동으로 붙여주는 명령어
		@GetMapping("delete")
		public String delete(Long bno, Criteria cri /*, RedirectAttributes rttr*/) {
			
			log.info("삭제 클번호: " + bno);
			
			boardSerivce.delete(bno);
			
			// 삭제후 보고있던 페이지로 돌아가게 하는 명령어
			/*
			rttr.addAttribute("pageNum", cri.getPageNum());
			rttr.addAttribute("aomunt", cri.getAmount());
			rttr.addAttribute("type", cri.getType());
			rttr.addAttribute("keyword", cri.getKeyword());
			*/
			
			// redirect:/board/list?pageNum=2&amount=10&type=T&keyword=사과
			return "redirect:/board/list" + cri.getListLink();
		}
}
