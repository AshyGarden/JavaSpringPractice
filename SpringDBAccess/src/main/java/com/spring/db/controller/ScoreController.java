package com.spring.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.db.model.ScoreVO;
import com.spring.db.service.IScoreService;

@Controller
@RequestMapping("/score")
public class ScoreController {
	
	//컨트롤러와 서비스 계층사이의 의존성 자동 주입을 위해 변수 선언
	//@Autowired는 스프링 컨테이너에 등록되어있는 번들중 
	//해당 변수 타입에 맞는 객체를 자동으로 변수에 주입해 주는 스프링 아노테이션
	//필드, 생성자, setter 매서드에 붙일수 있다. 
	@Autowired // 필드에 선언한 타입을 자동 연동 - dependancy injectissson
	private IScoreService service;
	
	//점수 등록화면을 열어주는 매서드
	@GetMapping("/register")
	public String register() {
		System.out.println("/score/register: GET");
		return "score/write-form";
	}
	
	//점수 등록 요청을  처리할 매서드
	@PostMapping("/register")
	public String register(ScoreVO vo) {
		System.out.println("/score/register: POST");
		System.out.println("vo: " + vo );
		service.insertScore(vo);
		return "score/write-result";
	}
	
	//점수 전체 조회를 처리하는 요청 매서드
	@GetMapping("/list")
	public void list(Model model) {
		System.out.println("/score/list: GET");
		//List<ScoreVO> list = service.selectAllScores();
		//model.addAttribute("sList",list);
		model.addAttribute("sList",service.selectAllScores());
	}
	
	@GetMapping("/delete")
	public String delete(int stuId, RedirectAttributes ra) {
		System.out.println("삭제할 학번: "+stuId);
		service.deleteScore(stuId);
		ra.addFlashAttribute("msg", "delSuccess");
		
		return "redirect:/score/list";
	}
	
	@GetMapping("/search")
	public void search() {
		System.out.println("/score/search: GET");
	}
	
	//점수 개별조회 매서드
	@GetMapping("/selectOne")
	public String selectOne(int stuId, Model model, RedirectAttributes ra) {
//		model.addAttribute("stu",service.SelectOne(stuId));
//		return "score/search-result";
		
		ScoreVO vo = service.SelectOne(stuId);
		if(vo == null) {
			ra.addFlashAttribute("msg","학번 정보가 없습니다.");
			return "redirect:/score/search";
		} else {
			model.addAttribute("stu",service.SelectOne(stuId));
			return "score/search-result";
		}
	}
}
