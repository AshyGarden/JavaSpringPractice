package com.spring.myweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/fileupload")
@Slf4j
public class UploadController {
	@GetMapping("/upload")
	public void form() {}
	
	@PostMapping("/upload_ok") 
	public void upload(MultipartFile file) {
		String fileRealName = file.getOriginalFilename();
		long size = file.getSize();
		
		log.info ("파일명:  "+fileRealName);
		log.info("파일크기: "+ size + "bytes");
		
		/*
		 사용자가 첨부한 파일은 DB에 저장하는 것은 선호하지 않음
		 db용량을 파일첨부에 사용하는것은 비용적으로도 좋지 않다.
		 
		 실제 상용되는 서비스들이 파일을 처리하는 방법은 따로 파일 전용서버를 구축하여 저장
		 db에는 파일의 저장경로를 지정하는 것이 일반적
		 
		 파일 업로드시 파일명이 동일한 파일이 이미존재 할수도 있고,
		 사용자가 업로드 하는 파일명이 영어 이외의 언어로 되어있을수 있음.
		 타 언어를 지원하지 않는 환경에서는 정상 동작이 되지않을수도있음(리눅스)
		 고유한 랜덤 문자를 통해 db와 서버에 저장할 파일명을 새롭게  만들어줌
		 */
		UUID uuid = UUID.randomUUID();
		log.info("uuid: "+ uuid.toString());
		String[] uuids = uuid.toString().split("-");
		log.info("생성된 고유 문자열: "+ uuids[0]);
		
		String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
		log.info("확장자명: "+ fileExtension);
		
		//db에는 파일경로를 저장, 실제 파일은 서버 컴퓨터 로컬 경로에 지정하는 방식.
		String uploadFolder = "C:/test/upload";
		
		File f = new File(uploadFolder);
		if(!f.exists()) {
			log.info("폴더가 존재하지 않습니다!");
			f.mkdir(); //폴더가 존재하지 않으면 생성하라는뜻
		}
		
		File saveFile = new File(uploadFolder+"/"+uuids[0]+fileExtension);
		
		try {
			file.transferTo(saveFile); //매개값으로 받은 첨부 파일을 지정한 로컬 경로에 보내는 매서드
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@PostMapping("/upload_ok2")
	public String upload2(MultipartHttpServletRequest files) {
		//첨부파일이 여러개 전달
		String uploadFolder = "C:/test/upload";
		
		List<MultipartFile> list = files.getFiles("files");
		
		for(MultipartFile m : list) {
			try {
				String fileRealName = m.getOriginalFilename();
				long size = m.getSize();
				
				File saveFile = new File(uploadFolder+"/"+fileRealName);
				m.transferTo(saveFile);			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "fileupload/upload_ok";
		
	}
	
	@PostMapping("/upload_ok3")
	public String upload3(@RequestParam("file") List<MultipartFile> list) {
		
		String uploadFolder = "C:/test/upload";
			
		for(MultipartFile m : list) {
			try {
				if(m.getSize()==0) break;
				
				String fileRealName = m.getOriginalFilename();
				long size = m.getSize();
				
				File saveFile = new File(uploadFolder+"/"+fileRealName);
				m.transferTo(saveFile);			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "fileupload/upload_ok";
	}
	
	
}
