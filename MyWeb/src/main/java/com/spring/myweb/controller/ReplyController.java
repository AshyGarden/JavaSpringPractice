package com.spring.myweb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.myweb.command.ReplyVO;
import com.spring.myweb.reply.service.IReplyService;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	private IReplyService service;
	
	//댓글등록
	@PostMapping("/regist")
	public String replyRegist(@RequestBody ReplyVO vo) {
		service.replyResist(vo);
		return "regSuccess";
	}
	
	//목록요청
	@GetMapping("/getList/{bno}/{pageNum}")
	public Map<String, Object> getList(@PathVariable int bno, @PathVariable int pageNum) {
		/*
		 1. getList 메서드가 글번호 페이지번호를 경로에서 떼어옴
		 2. Mapper 인터페이스에 복수의 값을 전달하기위해 Map또는 @param선택
		 3. ReplyMapper.xml에 sql문을 페이징쿼리로 작성
		 4. 클라이언트 측으로 DB에서 조회한 댓글목록을 보낼때,
		 페이징을 위한 댓글의 총 개수도 함께 보내줘야한다.
		 복수개의 값을 리턴하기위해, 리턴타입을 Map또는 VO형식으로 줄지를 결정
		 댓글 목록 리스트와 전체 댓글개수를 함께 전달예정
		 */
			
		List<ReplyVO> list = service.getList(bno, pageNum);
		int total = service.getTotal(bno);
		return null;
	}
	
}
