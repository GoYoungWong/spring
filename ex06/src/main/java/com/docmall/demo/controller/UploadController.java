package com.docmall.demo.controller;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.docmall.demo.dto.AttachFileDTO;
import com.docmall.demo.service.UploadService;
import com.docmall.demo.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/upload/*")
@RequiredArgsConstructor // 생성자 주읩
@Controller                          
public class UploadController {
	
	private final UploadService uploadService;
	
	private FileUtils fileUtils;
	
	// application.properties같은 외부에 있는 코드를 참조하는 어노테이션
	@Value("${org.docmall.upload.path}") // C:\\Dev\\upload\\pds
	private String uploadFolder; 
	
	
	// 업로드방식 1)<form>태그를 이용한 방식. 결과를 보내고 끝
	@GetMapping("uploadForm")
	public void uploadForm() {
		
	}
	// com.docmall.demo.config 패키지의 MultipartConfig클래스안에 multipartResolver bean이 업로드파일을 참조하여
	// MultipartFile[] uploadFile 이 파라미터로 사용하게 해준다.
	@PostMapping("uploadFormAction")
	// 파일이 복수로 선택되기 때문에 배열[]로 함. 오른쪽은 jsp파일에 name을 사용
	public void uploadFormPost(MultipartFile[] uploadFile) {
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("---------------------");
			log.info("파일이름: " + multipartFile.getOriginalFilename());
			log.info("파일크기: " + multipartFile.getSize());
			
			// 생성자
			// new File(파일 또는 폴더경로)
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile); // multipartFile: 사용자가 업로드한 파일자체를 가지고있는 객체. transferTo: multipartFile에 있는 파일을 svaeFile에 복사(보냄).
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	// 업로드방식 2)ajax를 이용한 방식.
	@GetMapping("uploadAjax")
	public void uploadAjax() {
		
		
	}
	// ajax요청방식: 결과를 보내고 다시 받아온다.
	// ResponseEntity : 보내는 정보가 정상적이라는 상태코드, 보내는 정보가 MIMN타입이 무엇인지 알려주는 것. ResponseEntity사용하지 않아도 작동은 된다.
	// ajax요청으로 파일업로드 작업을 진행하고, 업로드된 파일목록 정보를 리턴해주는 기능.
	// 리턴값은 <List<AttachFileDTO>> -> JSON으로 변환되어 클라이언트로 전송된다.(jackson-databind라이브러리가 작업)
	// 전부가 아닌 일부만 ajax요청을 사용하면 그냥 @Controller를 사용하고 필요한 부분에 @ResponseBody를 수동으로 작성한다.
	// MultipartFile[] == MultipartConfig 이 두개는 연관되서 봐야한다.
	@ResponseBody // ajax요청받는 매핑주소는 @ResponseBody이 어노테이션을 달아야 한다.
	@PostMapping(value = "uploadAjaxAction", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxAction(MultipartFile[] uploadFile) {
		ResponseEntity<List<AttachFileDTO>> entity = null;
		
		// 업로드한 파일정보 목록
		List<AttachFileDTO> list = new ArrayList<>();
		
		// 업로드하는 날짜를 이용한 폴더
		String uploadDateFolder = fileUtils.getDateFolder(); // 날짜폴더를 받아오는 코드
		
		for(MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachFileDTO = new AttachFileDTO();
			
			// 1) 클라이언트 파일이름
			String clientFileName = multipartFile.getOriginalFilename();
			attachFileDTO.setFileName(clientFileName);
			
			// 2) 실제업로드한 파일명
			attachFileDTO.setUuid(fileUtils.uploadFile(uploadFolder, uploadDateFolder, multipartFile));
			
			// 3) 날짜폴더명
			attachFileDTO.setUploadPath(uploadDateFolder);
			
			File saveFile = new File(uploadFolder, clientFileName);
			
			if(fileUtils.checkImageType(saveFile)) {
				// 4) 이미지파일 여부
				attachFileDTO.setImage(true);
			}
			list.add(attachFileDTO);
		}
						// list에 List<AttachFileDTO>정보가 들어있고 리턴타입으로 json으로 변환되어 result(uploadAjax.jsp)에 넘어감
						// result[i]에 AttachFileDTO에 정보가 [0],[1],[2] 이런식으로 정보가 인덱스 하나에 들어있다.
		entity = new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
		
		return entity;
	}
	
	// <img sec="매핑주소">
	@GetMapping("display")
	public ResponseEntity<byte[]> getFile(String fileName) {
				
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = fileUtils.getFile(uploadFolder, fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return entity;
	}
	
	// 파일삭제
	// 파일삭제시 파일명과 날짜폴더명은 따로 받아야한다.
	@PostMapping(value = "deleteFile")
	@ResponseBody         
	public ResponseEntity<String> deleteFile(String dateFolderName, String fileName, String type) {
		
		
		try {
			// 클라이언트에서 \문자 데이터를 인코딩으로 받아, 서버에서는 디코딩처리 함.
			// 2024%5C05%5C20 -> 2024\05\20
			dateFolderName = URLDecoder.decode(dateFolderName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		log.info("dateFolderName: " + dateFolderName);
		log.info("fileName: " + fileName);
		log.info("type: " + type);
		
		
		
		ResponseEntity<String> entity = null;
		
		fileUtils.delete(uploadFolder,dateFolderName ,fileName, type);
		
		entity = new ResponseEntity<String>("sucess", HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
	
	
	
	
}
