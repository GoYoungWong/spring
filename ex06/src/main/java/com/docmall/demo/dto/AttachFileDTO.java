package com.docmall.demo.dto;

import lombok.Data;

// 클라이언트가 전송한 업로드된 파일목록의 정보.

@Data // getter, setter , toString 등등 Object클래스와 관련있는거 대부분을 만들어줌
public class AttachFileDTO {
	
	private String uuid; 			// 중복되지 않는 파일명
	private String uploadPath;      // 날짜를 이용한 업로드 폴더명
	private String fileName;        // 클라이언트에서 보낸 파일명
	private boolean image;          // 이미지 파일여부.   true : 이미지파일, false : 일반파일.
}
