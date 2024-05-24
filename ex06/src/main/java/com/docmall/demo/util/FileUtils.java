package com.docmall.demo.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

// bean으로 동작하게 할수있는 어노테이션
@Component // 스프링에서 클래스를 자동관리.
public class FileUtils {
	
	// 기능? 현재폴더를 운영체제에 맞게 문자열로 반환하는 기능
	public static String getDateFolder() {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd"); // 날짜포맷형식
		Date date = new Date();	// 오늘날짜정보
		
		String str = sdf.format(date);   // "2024-05-16" 폴더명 문자열
		
		// File.separator : 이 코드를 실행하는 운영체제별로 파일의 경로구분자를 리턴.
		/*
		 - 유닉스,맥,리눅스 구분자 : /   예> "2024-05-16"   ->  "2024/05/16"
		 - 윈도우즈 구분자 : \         예> "2024-05-16"   ->  "2024\05\16"
		 */ 
		
		return str.replace("-", File.separator); // - 이거를 \로 바꿔준다.
	}
	
	// 기능? 파일 업로드기능
	/*
	 - String uploadFolder : 업로드 폴더명.  C:\\Dev\\upload\\pds
	 - String dateFolder : 날짜폴더명        "2024\\05\\16"
	 - MultipartFile uploadFile : 클라이언트에서 전송한 파일
	 */
	public static String uploadFile(String uploadFolder, String dateFolder, MultipartFile uploadFile) {
		
		String realUploadFileName = ""; // 실제 업로드한 파일명. 예> 사용자가 A란 파일을 올렸을때 서버에 중복되는 파일이 되지 않게 바꿔서 올려주는 기능
		
		// File클래스 : JDK제공. 파일과폴더관련 기능을 제공.
		/*
		 File file = new File(파일또는 폴더정보구성);  file.명령어(속성과메서드)
		 - 파일또는 폴더 존재여부확인
		 - 존재하지 않으면 파일또는 폴더 생성
		 - 존재하면 파일또는 폴더 속성확인
		 */
		
	// 업로드 할 폴더 File객체 // uploadFolder   // dateFolder
		File file = new File(uploadFolder, dateFolder);   // 예> C:/Dev/upload/pds  "2024/05/16"
		
		// "2024/05/16" 이 폴더가 존재하지 않으면, 폴더생성
		// 새로운 날짜에 첫 파일업로드가 진행이되면, 폴더생성되고, 두번째 파일업로드 부터는 폴더가 생성되지 않게된다.
		// exists : 파일의 존재여부를 확인함
		// mkdirs : 지정한 경로에 폴더가 없는 경우, 부모 폴더를 생성해준다.
		if(file.exists() == false) {
			// mkdirs, mkdir 이렇게 두개가 있는데 mkdirs 파일이 한개여도 그냥 mkdirs이거 씀
			file.mkdirs();  // C:/Dev/upload/pds/2024/05/16 없으면 다 생성 
		}
		
		// 클라이언트에서 보낸 파일명
		String clientFileName = uploadFile.getOriginalFilename(); // abc.png
		// https://dpdpwl.tistory.com/76 UUID 참고자료
		// UUID.ranodmUUID : 범용 고유 식별자(universally unique identifier)로써 2f48f241-9d64-4d16-bf56-70b9d4e0e79a 이와같은 형태로 고유한 값을 생성해줍니다.
		UUID uuid = UUID.randomUUID(); // 2f48f241-9d64-4d16-bf56-70b9d4e0e79a

		// 2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
		realUploadFileName = uuid.toString() + "_" + clientFileName;
		
		// 예외처리작업
		try {
			File saveFile = new File(file, realUploadFileName);
			uploadFile.transferTo(saveFile); // 파일복사(원본)
			
			// Thumnail 작업(원본파일에서 해상도크기를 줄여 썸네일이미지 생성하기)
			// checkImageType  : 프로그래밍에서 이미지 파일의 유형(타입)을 확인하는 기능을 하는 메서드나 함수의 이름으로 사용됩니다.
			if(checkImageType(saveFile)) {
				// Thumnail 파일명 : s_2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png 생성
				// thumnailFile객체: "C:/Dev/upload/pds/2024/05/16" "s_2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png"
				File thumnailFile = new File(file, "s_" + realUploadFileName);
				
				// saveFile객체: 업로드 된 파일정보
				// 이 코드는 이미지 파일을 메모리에 로드하여 프로그램 내에서 이미지 처리 작업을 수행할 수 있도록 준비하는 단계.
				// 예를 들어, 이미지의 픽셀을 조작하거나, 크기를 변경하거나, 다른 형식으로 저장하는 등의 작업을 할 수 있습니다.
				BufferedImage bo_img = ImageIO.read(saveFile);
				
				// 알맞게 크기를 줄이는 작업
				double ratio = 3;
				int width = (int) (bo_img.getWidth() / ratio);
				int height = (int) (bo_img.getHeight() / ratio);
				
				Thumbnails.of(saveFile) // 원본파일
						  .size(width, height) // 조정한 크기
						  .toFile(thumnailFile); // 최종적으로 들어오는 파일
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return realUploadFileName; // 실제 업로드되는 파일명: 2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
		
	}

	// 기능? 업로드파일의 MIME타입 확인. 즉 이미지파일 또는 일반파일 여부를 체크.
	public static boolean checkImageType(File saveFile) {
		
		boolean isImageType = false;
		
		try {
			// MIME : text/html, text/plain, text/jpeg, image/jpg, image/png .....
			// 클라이언트에서 전송한 파일의 MIME정보 추출
			// 파일이 어떤 형식인지를 판별함.
			String contentType = Files.probeContentType(saveFile.toPath());
			
			// contentType변수의 내용이 "image"문자열 시작여부 boolean값 반환
			// 이 코드는 파일의 MIME 타입이 이미지인지 아닌지를 판별하는 데 사용됩니다.
			isImageType = contentType.startsWith("image");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return isImageType;
	}
	
	// 이미지파일을 웹브라우저 화면에 보이는 작업.
	// <img src="abc.gif">  <img src="매핑주소"> 매핑주소를 통한 서버측에서 받아오는 바이트배열을 이용하여, 브라우저가 이미지를 표시한다.
	/* 
	 String uploadPath: 서버 업로드폴더 예> "C:/Dev/upload/pds"
	 String fileName: 이미지 파일명(날짜폴더명 포함).
	 ResponseEntity<byte[]> : 모든 파일을 btye별로 보낸다.
	 */
	// 파일업로드되는 폴더가 프로젝트 외부에 존재하여, 보안적인 이슈가 있으므로, 업로드 파일들을 바이트배열로 읽어서 클라이언트로 보낸다.
	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception {
		ResponseEntity<byte[]> entity = null;
		
		File file = new File(uploadPath,fileName); // 실제 파일이 존재해야함 존재하지 않으면 에러남.
		
		if(!file.exists()) {   // 파일이 있는지 확인
			return entity;
		}
		
		HttpHeaders headers = new HttpHeaders();
		// Files.probeContentType(file.toPath()) : MIME TYPE 정보 예> image/jpeg
		// 파일의 MIME 타입을 알아내어 HTTP 응답 헤더에 추가하는 코드.
		// 이 코드는 클라이언트가 파일의 타입을 알 수 있도록 응답에 MIME 타입 정보를 포함시킵니다.
		headers.add("Content-Type", Files.probeContentType(file.toPath())); 
		
		// FileCopyUtils.copyToByteArray(file) : File객체로 부터 byte배열단위로 이미지파일을 추출함
		entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		
		return entity;
	}
	
	// 기능? (일반파일도 상관없음)이미지파일 삭제
	/*
	 String uploadPath : 서버업로드 폴더
	 String folderName : 날짜폴더명
	 String fileName : 파일명(날짜폴더명 포함)
	 */
	public static void delete(String uploadPath,String dateFolderName,String fileName, String type) {
		
		// 1) thumnail파일    예> "C:/Dev/upload/pds" "2024/05/16" s_2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
		File file1 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName).replace('\\', File.separatorChar));
		if(file1.exists()) file1.delete();
		
		if(type.equals("image")) {
			// 2) 원본 파일.  fileName.substring(2) -> s_를 빼고 원본파일명 2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
			File file2 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName.substring(2)).replace('\\', File.separatorChar));
			if(file2.exists()) file2.delete();
	}
  }
	
}
