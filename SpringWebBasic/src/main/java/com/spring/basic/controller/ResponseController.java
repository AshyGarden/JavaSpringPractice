package com.spring.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spiring.basic.model.UserVO;

@Controller
@RequestMapping("/response")
public class ResponseController {
	
	@GetMapping("/res-ex01")
	public void resEx01() {}
	
//	@GetMapping("/test")
//	public void test(@RequestParam("age") int age, Model model) {
//		model.addAttribute("age",age);
//		model.addAttribute("nick","닉네임"); //패킹해놓으면 전달이 알아서됨
//	}
	
	 
    //2. @ModelAttribute를 사용한 화면에 데이터 전송 처리
    //@RequestParam + model.addAttribute처럼 동작
    @GetMapping("/test")
    public void test(@ModelAttribute("age") int age, Model model) {
        //model.addAttribute("age" , age); 할 필요 x
        model.addAttribute("nick", "멍멍이");
    }
    
    @GetMapping("/test2")
    public void test2(@ModelAttribute("info") UserVO vo) {
    	System.out.println("Method 내의 콘솔 출력:" + vo);
    }
    
    //3.ModelAndView 객체를 활용한 처리
    @GetMapping("/test3")
    public ModelAndView test3() {
    	ModelAndView mv = new ModelAndView();
    	mv.addObject("userName","김철수");
    	mv.addObject("userAge",30);
    	mv.setViewName("/response/test3"); 
    	
    	return mv;
    }
    
    //////////////////////////////////////////
    
    //Redirect처리
    @GetMapping("/login")
    public String login() {
    	System.out.println("로그인 GET 요청");
    	return "response/res-redirect-form";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam("userId") String id,
    		@RequestParam("userPw") String pw,
    		@RequestParam("userPwChk") String pwChk,
    		RedirectAttributes ra) {
    	
    	System.out.println("로그인 POST 요청");
    	System.out.println("ID: "+id);
    	System.out.println("PW: "+pw);
    	System.out.println("Chk: "+pwChk);
    	if(id.equals("")) {
    		/*
    		 redirect 상황에서 model객체를 사용시 model 내부의 데이터가 재요청이 들어올 때 
    		 데이터가 파라미터 값으로 붙어서 url주소뒤에 ?와 함께 노출되어 전달된다.
    		 model.addAttribute("msg","아이디는 필수값입니다.");
    		 */
    		
    		//redirect상황에서 일회성으로 데이터를 전송할때 사용하는 매서드
    		//url뒤에 데이터가 붙지 않는다. 1번 사용 후 소멸!
    		ra.addFlashAttribute("msg","아이디는 필수값입니다.");
    	}
    	return "redirect:/response/login"; //view resolver에게 전달X, 바로 선언한 주소로(위의Getmapping) 이동

    }
    
	
}
