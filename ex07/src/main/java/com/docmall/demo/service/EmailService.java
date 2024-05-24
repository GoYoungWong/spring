package com.docmall.demo.service;

import com.docmall.demo.dto.EmailDTO;

public interface EmailService {

	// 메일을 보내는 추상메서드
	void sendMail(EmailDTO dto, String message);
}
