package com.docmall.demo.service;

import org.springframework.stereotype.Service;

import com.docmall.demo.mapper.UploadMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 주읩
@Service
public class UploadServiceImpl implements UploadService{

	private final UploadMapper uploadMapper; 
}
