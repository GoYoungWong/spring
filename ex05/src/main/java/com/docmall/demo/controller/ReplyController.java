package com.docmall.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.docmall.demo.service.ReplySerivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// jsp를 사용하지 않음
// 댓글기능과 관련된 매핑주소를 관리하는 클래스
// 게시판 get.jsp에서 작업에 필요한 내용.
// RESP API 개발방법
// 웹페이지가 필요하지 않음 그래서 폴더를 생성하지 않음(검색이기 떄문에 따로 jsp가 필요하지 않음)
// 이 클래스에 모든 매핑주소들이 전부 ajax로 요청을 받는다면 @RestController 어노테이션을 사용하여 @ResponseBody를 수동으로 사용하지 않는다.

@Slf4j  // log객체가 지원
@RestController // @Controller + @ResponseBody.  @RestController를 사용하는 클래스의 메서드들은 모두 @ResponseBody이 적용되어 있다.
@RequestMapping("/replies/*") // 구분하기 위해서 만들었음
@RequiredArgsConstructor  // 의존성 주입
public class ReplyController {

	// 의존성 주입
	private final ReplySerivce replySerivce; 
	 
	// page : 페이지 번호값 / bno : 글번호
	// /replies/pages/511/1
	// 요청주소 : 1) /replies/pages/511/1 -> REST API 기술이론에서 권장하는 주소형태.  2) /replies/pages?page=1&bno=511 -> 전통적인 주소
	// /replies/pages/511/1 주소(경로)에 존재하는 값을 사용할 떄 구분되는 위치에 {이름}을 사용
	// produces : JSON 또는 XML로 보낼것이다.
	// APPLICATION_JSON_UTF8_VALUE : 버전업 되면서 언제 없어질지 모름
	@GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Map<String, Object>> getList(@PathVariable("bno") Long bno, @PathVariable("page") int page) {
		       // 서로 다른 데이터타입을 추가하기 위해 map을 사용
		
		// 일부의 데이터만 보내주면 되기 떄문에 Model을 사용하지 않는다.
		// ResponseEntity : 클라이언트에게 이 데이터는 어떤 성격인가도 포함해서 보내줌 
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<>();
		
		// 1) 댓글목록 작업
		Criteria cri = new Criteria(page, 5);
		List<ReplyVO> list = replySerivce.getListPaging(cri, bno);
		map.put("list",list);
		
		// 2) 페이징 작업
		int total = replySerivce.getCountByBno(bno);
		PageDTO pageDTO = new PageDTO(cri, total);
		map.put("pageMaker", pageDTO);
		
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
		
		replySerivce.insert(vo);

		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;
	}
	
	// 댓글수정.  put, patch
	@PutMapping(value = "/modify",consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;
		
		log.info("댓글수정데이터: " + vo);
		
		// 댓글수정작업
		replySerivce.update(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 댓글삭제. delete		일반적인 주소 : /delete?rno=500  rest api 경로형태 주소 /delete/500
	@DeleteMapping(value = "/delete/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> delete(@PathVariable("rno") Integer rno) {
		ResponseEntity<String> entity = null;
		
		// 댓글삭제작업
		replySerivce.delete(rno);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	
}
