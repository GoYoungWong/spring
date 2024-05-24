package com.docmall.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.dto.PageDTO;
import com.docmall.demo.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/replies/*")
@RequiredArgsConstructor // 의존성 주입
public class ReplyController {
	
	private final ReplyService replyService;
	
	@GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Map<String, Object>> getList(@PathVariable("bno") Long bno,@PathVariable("page") int page) {
		
		// ResponseEntity 객체를 선언하지만 초기에는 null로 설정합니다.
		// 이 객체는 HTTP 응답의 상태 코드, 헤더, 및 본문을 포함합니다.
		ResponseEntity<Map<String, Object>> entity = null;
		// HashMap 객체를 생성합니다. 이 맵은 응답 본문에 담길 데이터를 키-값 쌍의 형태로 저장합니다.
		Map<String, Object> map = new HashMap<>();
		
		// 1) 댓글목록 작업
		// Criteria 객체는 페이지 번호와 페이지 당 항목 수를 나타내는 두 개의 매개변수를 받아 생성됩니다.
		// page 변수와 한 페이지에 5개의 항목을 표시하도록 설정한 5가 사용됩니다.
		Criteria cri = new Criteria(page,5);
		// 메서드를 호출하여 특정 게시물(bno)에 대한 페이징된 댓글 목록을 가져옵니다.
		// Criteria 객체와 게시물 번호(bno)를 매개변수로 받습니다.
		// ReplyVO 객체들의 리스트로 반환됩니다.
		List<ReplyVO> list = replyService.getListPaging(cri, bno);
		// 가져온 댓글 목록을 map 객체에 저장합니다.
		// map 객체는 이후에 ResponseEntity를 통해 클라이언트에 반환될 것입니다.
		// map 객체는 최종적으로 클라이언트에게 JSON 또는 XML 형식으로 반환됩니다.
		map.put("list",list);
		
		// 2) 페이징 작업
		// replyService.getCountByBno(bno) 메서드를 호출하여 특정 게시물(bno)에 대한 총 댓글 개수를 가져옵니다.
		// 이 값은 페이지 번호와 페이지 당 항목 수를 기반으로 페이징 처리를 하기 위해 사용됩니다.
		int total =  replyService.getCountByBno(bno);
		
		// PageDTO 객체를 생성하여 페이징 정보를 담습니다.
		// cri는 Criteria 객체로, 페이지 번호와 페이지 당 항목 수를 담고 있습니다.
		// total은 전체 댓글 수입니다.
		PageDTO pageDTO = new PageDTO(cri, total);
		
		// 생성된 pageDTO 객체를 map에 pageMaker라는 키로 저장합니다.
		// 이 map 객체는 나중에 클라이언트에게 반환될 때 JSON 또는 XML 형식으로 변환됩니다.
		map.put("pageMaker", pageDTO);
		
		
		// ResponseEntity 객체를 생성하여, map 객체와 HTTP 상태 코드 HttpStatus.OK를 포함합니다.
		// HttpStatus.OK : HTTP 상태 코드 200을 의미하며, 요청이 성공적으로 처리되었음을 나타냅니다.
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		return entity;
		
	}
		// 댓글저장
	    // consumes = "application/json" : 클라이언트에서 보내는 데이터는 json 이어야 한다. 라고 명시
		// produces = {MediaType.TEXT_PLAIN_VALUE} : 서버에서 클라이언트로 보내는 응답데이터는 text 이다 라는 설정. ResponseEntity<String> 이게 String 이여서 text 인것이다.
		// @RequestBody : JSON데이터를 ReplyVO vo로 변환해주는 기능. 더불어서 jackson-databind-2.15.4.jar 라이브러리도 실제 json관련 작업을 함.
		@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
		public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
			ResponseEntity<String> entity = null;
			
			log.info("댓글데이터: " + vo);
			
			replyService.insert(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
			
			return entity;
		}
}
