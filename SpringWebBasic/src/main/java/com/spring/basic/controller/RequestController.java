package com.spring.basic.controller;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spiring.basic.model.UserVO;

//자동으로 스프링 컨테이너에 해당 클래스의 빈을 등록하는 아노테이션.
//빈을 등록해 놔야 HandlerMapping이 이 클래스의 객체를 검색할 수 있을 것이다.
@Controller  //("reqCtrl") //이름을 지정하면 이름대로, 안하면 클래스 이름으로 등록
@RequestMapping("/request") //컨트롤러릐 공통 URI미리 선언 = 밑에서 더 안적어도됨.
public class RequestController {
	
	
	public RequestController() {
		System.out.println("reqCtrl생성");
	}
	@RequestMapping("/test")
	public String testCall() {
		System.out.println("testCall");
		return "test";
	}
	
	//@RequestMapping(value = "/request/basic01", method= RequestMethod.GET)
	@GetMapping("/basic01")
	public String reqGet() {
		System.out.println("requestCall-get");
		return "request/req-ex01";
	}
	
	//@RequestMapping(value = "/request/basic01", method= RequestMethod.POST)
	@PostMapping("/basic01")//4버전 이후에는 이렇게 많이씀
	public String reqPost() {
		System.out.println("requestCall-post");
		return "request/req-ex01";
	}
	
	////////////////////////////////////////////////////////
	//컨트롤러 내의 매서드 타입을 void로 선언시
	//요청이 들어온 URL값을 View Resolver에게 전달한다.
	@GetMapping("/join") //리턴타입이 void면 mapping경로가 리턴된다!
	public void register() {
		System.out.println("/request/join: GET");	
	}
	
	//요청 URI주소가 같더라도 전송 방식에 따라 맵핑을 다르게 할수 있기때문에
	//같은 주소를 사용하는것이 가능하다(GET->화면처리, POST->입력값처리등)
	/*
	 # 스프링에서 요청과 함께 전달된 데이터를 처리하는 방식
	 1.전통적인 jsp/servlet 방법(parameter읽기 처리방법)
	 - HttpServletRequest 객체를 활용(jsp에서 사용한던 방식)
	 
	
	@PostMapping("/join")
	public void register(HttpServletRequest request) {
		System.out.println("/request/join: POST");
		System.out.println("ID: "+request.getParameter("userId"));
		System.out.println("PW: "+request.getParameter("userPw"));
		System.out.println("Hobby: "+Arrays.toString(request.getParameterValues("hobby")));
	}
	*/
	
//	@PostMapping("/join")
//	public void register(@RequestParam("userId") String id, 
//			@RequestParam("userPw") String pw,
//			@RequestParam("hobby") List<String> hobbies) {	
//		System.out.println("ID: "+id);
//		System.out.println("PW: "+pw);
//		System.out.println("Hobby: "+hobbies);
//	}
	/*
	 2 . @RequestParam annotation을 이용한 요청 파라미터 처리
	 @RequestParam("파라미터 변수명") 값을 찾아서 처라
	 
	@PostMapping("/join")
	public void register(String userId, String userPw, 
			@RequestParam(value = "hobby", required = false, defaultValue = "No Hobby Person") List<String> hobby) {	
		System.out.println("ID: "+userId);
		System.out.println("PW: "+userPw);
		System.out.println("hobby: "+hobby);
	}
	*/
	
	/*
    3. 커멘드 객체를 활용한 파라미터 처리
    - 파라미터 데이터와 연동되는 VO 클래스가 필요합니다.
    */
   @PostMapping("/join")
   public void register(UserVO vo) {
       System.out.println(vo);
   }

	
}
