package com.spring.myweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.myweb.command.FreeBoardVO;
import com.spring.myweb.freeboard.service.IFreeBoardService;
import com.spring.myweb.util.PageCreator;
import com.spring.myweb.util.PageVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/freeboard")
@Slf4j
public class FreeBoardController {

	@Autowired
	private IFreeBoardService service;
	
	//목록 화면
	@GetMapping("/freeList")
	public void freeList(PageVO vo, Model model){
		PageCreator pc = new PageCreator(vo, service.getTotal(vo));
		//System.out.println(pc.toString());
		log.info(pc.toString());
		
		model.addAttribute("boardList", service.getList(vo));
		model.addAttribute("pc",pc);
	}
	
	//글쓰기 페이지 열어주는 메서드
	@GetMapping("/regist")
	public String regist() {
		return "freeboard/freeRegist";
	}
	
	//글 등록 처리
	@PostMapping("/regist")
	public String regist(FreeBoardVO vo) {
		service.regist(vo);
		return "redirect:/freeboard/freeList";
	}
	
//	//글 상세 보기 처리
//	@GetMapping("/content/{bno}")//중괄호 영역을 넣어줌(생략시 {}안 그대로로 인식)
//	public String getContent(@PathVariable int bno, Model model) {
//		model.addAttribute("article", service.getContent(bno));
//		return "freeboard/freeDetail";
//	}
	
	
	/*
	 @PathVariable은 URL경로에 변수를 포함시켜주는 방식
	 null이나 공백이 들어갈수 있는 파라미터라면 적용하지 않는것을 추천
	 
	 파라미터값에 .이 포함되면 .뒤에 값이 잘림
	 
	 {}안에 변수명을 넣고, @PathVariable괄호안에 경로를 지정
	 */
	//글 상세 보기 처리
	@GetMapping("/content/{bno}")
	public String getContent(@PathVariable int bno, 
			@ModelAttribute("p") PageVO vo, Model model) {
		model.addAttribute("article", service.getContent(bno));
		return "freeboard/freeDetail";
	}
	
	
	//글 수정 페이지 이동 처리
	@PostMapping("/modify")
	public String modify(@ModelAttribute("article") FreeBoardVO vo) {
		return "freeboard/freeModify";
	}
	
	//글 수정 처리
	@PostMapping("/update")
	public String update(FreeBoardVO vo) {
		service.update(vo);
		return "redirect:/freeboard/content/" + vo.getBno();
	}
	
	//글 삭제 처리
	@PostMapping("/delete")
	public String delete(int bno) {
		service.delete(bno);
		return "redirect:/freeboard/freeList";
	}
	
	
}
