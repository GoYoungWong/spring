package com.docmall.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.dto.PageDTO;
import com.docmall.demo.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//jsp를 사용하지 않음
//댓글기능과 관련된 매핑주소를 관리하는 클래스
//게시판 get.jsp에서 작업에 필요한 내용.
//RESP API 개발방법
//웹페이지가 필요하지 않음 그래서 폴더를 생성하지 않음(검색이기 떄문에 따로 jsp가 필요하지 않음)
//이 클래스에 모든 매핑주소들이 전부 ajax로 요청을 받는다면 @RestController 어노테이션을 사용하여 @ResponseBody를 수동으로 사용하지 않는다.


@Slf4j
@RequiredArgsConstructor
@RestController // @Controller + @ResponseBody.  @RestController를 사용하는 클래스의 메서드들은 모두 @ResponseBody이 적용되어 있다.
@RequestMapping("/replies/*")
public class ReplyController {

	private final ReplyService replyService;
	
	// value = "/pages/{bno}/{page}" -> replies/pages/511/1
	// {bno}와 {page}는 경로 변수(Path Variable)로, 요청 시 값이 동적으로 바인딩됩니다.
	// produces : 메서드가 생성하는 응답의 MIME 타입을 지정합니다. JSON 또는 XML 형식의 응답을 받을 수 있음을 의미합니다.
	@GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	
	// ResponseEntity는 HTTP 응답의 상태 코드, 헤더, 바디를 포함하는 전체 응답을 나타냅니다.
	// Map<String, Object>입니다. 이 맵은 다양한 키-값 쌍을 포함할 수 있어 유연한 데이터 구조를 제공합니다.
	// @PathVariable("bno"): URL 경로의 {bno} 부분을 메서드 매개변수 bno에 바인딩합니다.
	// @PathVariable("page") : 위랑 같음
	public ResponseEntity<Map<String, Object>> getList(@PathVariable("bno") Long bno,@PathVariable("page") int page) {
		
		// ResponseEntity: 객체를 선언하고 초기화합니다.
		// entity는 나중에 데이터를 설정하고 HTTP 응답으로 반환하기 위해 사용됩니다.
		ResponseEntity<Map<String, Object>> entity = null;
		
		// 응답 데이터 맵을 생성합니다.
		// Map<String, Object>는 키-값 쌍으로 데이터를 저장할 수 있는 자료구조입니다.
		// HashMap은 Map 인터페이스의 구현체로, 키-값 쌍을 해시 테이블에 저장합니다.
		// map은 나중에 여러 종류의 데이터를 담아 ResponseEntity에 포함시키기 위해 사용됩니다.
		Map<String, Object> map = new HashMap<>(); 
		
		// 1)댓글목록 작업
		// 페이징 처리를 위한 Criteria 객체를 생성합니다.
		// new Criteria(page, 5)를 통해
		// page 번호와 한 페이지당 5개의 항목을 설정한 Criteria 객체가 생성됩니다.
		Criteria cri = new Criteria(page,5);
		
		// 댓글 목록을 페이징 처리하여 조회합니다.
		// 메서드를 호출하여 특정 게시물(bno)의 댓글 목록을 페이징 처리하여 가져옵니다.
		List<ReplyVO> list = replyService.getListPaging(cri, bno);
		
		// 조회한 댓글 목록을 맵에 추가합니다.
		// map은 응답 데이터를 담는 Map<String, Object> 객체입니다.
		// list는 조회한 댓글 목록을 담고 있습니다.
		// map.put("list", list)를 통해 list라는 키로 댓글 목록을 map에 추가합니다.
		// map은 나중에 ResponseEntity로 반환되어 클라이언트에게 전달됩니다.
		map.put("list", list);
		
		//2) 페이징작업
		// 게시물 번호(bno)를 기준으로 해당 게시물의 댓글 총 개수를 조회합니다.
		// 이는 페이징 처리를 위해 필요한 데이터입니다.
		int total = replyService.getCountByBno(bno);
		
		// 페이지 정보를 담고 있는 PageDTO 객체를 생성합니다.
		// cri: Criteria 객체를 이용하여 현재 페이지 번호와 페이지당 댓글 수를 설정합니다.
		// total: 댓글 총 개수를 전달하여 총 페이지 수 등의 정보를 계산합니다.
		PageDTO pageDTO = new PageDTO(cri, total);
		
		// 페이지 정보를 담고 있는 PageDTO 객체를 맵에 "pageMaker"라는 키로 추가합니다. 
		// 이렇게 함으로써 뷰에서 해당 정보를 활용할 수 있습니다.
		map.put("pageMaker", pageDTO);
		
		// 데이터 준비: map 객체에 필요한 데이터를 추가합니다. 여기서는 댓글 리스트와 페이지 정보를 추가하고 있습니다.
		// ResponseEntity 생성: new ResponseEntity<>(map, HttpStatus.OK)을 통해 ResponseEntity 객체를 생성합니다.
		// 이 객체는 map 데이터를 본문으로 하고, HTTP 상태 코드로 HttpStatus.OK (200 OK)를 설정합니다.
		// 응답 반환: 컨트롤러 메서드에서 이 ResponseEntity 객체를 반환하면,
		// 클라이언트는 map에 담긴 데이터를 JSON 형식으로 받게 되며, HTTP 상태 코드 200을 확인할 수 있습니다.
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
