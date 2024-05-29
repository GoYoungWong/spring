package com.docmall.demo.service;

import com.docmall.demo.dto.EmailDTO;

public interface EmailService {

	// 메일을 보내는 추상메서드
	void sendMail(String type,EmailDTO dto, String message);
}
