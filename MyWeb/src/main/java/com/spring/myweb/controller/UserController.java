package com.spring.myweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.myweb.command.UserVO;
import com.spring.myweb.freeboard.service.IFreeBoardService;
import com.spring.myweb.user.service.IUserService;
import com.spring.myweb.util.MailSendService;
import com.spring.myweb.util.PageCreator;
import com.spring.myweb.util.PageVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private IUserService service;
	
	@Autowired
	private IFreeBoardService boardService;
	
	@Autowired
	private MailSendService mailService;
	
	//회원가입 페이지로 이동(순수하게 페이지 이동만을 위한 method) - 동기방식이 좋음
	@GetMapping("/userJoin")
	public void userJoin() {}
	
	//아이디 중복 확인(비동기)
	@ResponseBody
	@PostMapping("/idCheck")
	public String idCheck(@RequestBody String userId) {
		log.info("전달된 id: "+userId);
		
		int result = service.idCheck(userId);
		if(result==1) return "duplicated";
		else return "ok";
	}
	
	//이메일 인증
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheck(String email) {
		log.info("이메일 인증 요청: " + email);
		
		//서버에게 이메일주소를 받아 인증번호를 보내는 로직
		return mailService.joinEmail(email); 
	}
	
	//회원가입처리
	@PostMapping("/join")
	public String join(UserVO vo, RedirectAttributes ra) {
		log.info("param: {}",vo.toString());
		service.join(vo);
		ra.addFlashAttribute("msg","JoinSuccess");
		return "redirect:/user/userLogin";
	}
	
	//로그인 페이지로 이동
	@GetMapping("/userLogin")
	public void login() {}
	
	//로그인 요청
	@PostMapping("/userLogin")
	public void login(String userId, String userPw, Model model) {
		log.info("userLogin activate");
		model.addAttribute("user",service.login(userId, userPw));
	}
	
	
	@GetMapping("/userMypage")
	public void userMypage(HttpSession session, Model model, PageVO vo) {
		//세션 데이터에서 id를 뽑아야 sql을 돌림
		String id = (String) session.getAttribute("login");
		vo.setLoginId(id);
		PageCreator pc = new PageCreator(vo, boardService.getTotal(vo));
		model.addAttribute("userInfo", service.getInfo(id, vo));
		model.addAttribute("pc",pc);
		
	}
}
