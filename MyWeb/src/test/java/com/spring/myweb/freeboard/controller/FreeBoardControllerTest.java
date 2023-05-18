package com.spring.myweb.freeboard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/servlet-config.xml",
		"file:src/main/webapp/WEB-INF/config/db-config.xml"
})
@WebAppConfiguration //
public class FreeBoardControllerTest {
	/*
	- 테스트 환경에서 가상의 DispaycherServlet을 사용하기 위한 객체 자동 주입
	- WebApplicationContext는 Spring에서 제공되는 servlet들을 사욜할수 있도록 정보를 저장하는 객체
	 */
	
	@Autowired
	private WebApplicationContext ctx;
	
	//MockMvc는 WEBAPP을 서버에 배포하지 않아도 spring mvc동작을 구현할수 있는 가상의 구조를 만늠
	private MockMvc mockMvc;
	
	//private FreeBoardController controller;
	
	@BeforeEach //테스트 시작시 항상 먼저 구동되는기능들을 선언
	public void setup() {
		//테스트할 컨트롤러를 수동으로 주입. 하나의 컨트롤러 자체만 테스트를 진행 할 때 이렇게 씁니다.
        //this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		//가상 구조를 세팅
		//스프링 컨테이너에 등록된 모든 bean과 의존성 주입까지 로드해서 사용이 가능.
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	@DisplayName("/freeboard/freeList 부르기")
	void testList() throws Exception {
		//System.out.println(mockMvc);
		ModelAndView mv = mockMvc.perform(MockMvcRequestBuilders.get("/freeboard/freeList")).andReturn().getModelAndView();
		
		System.out.println("Model에 저장한 데이터"+mv.getModelMap());
		System.out.println("응답 처리를 위해 사용할 페이지"+mv.getViewName());
	}
	
	@Test
	@DisplayName("게시글 등록 요청 처리과정 테스트")
	void testInserst() throws Exception {
		String viewPage = mockMvc.perform(MockMvcRequestBuilders.post("/freeboard/freeRegist").
				param("title","테스트 제목")
				.param("content","테스트 새글")
				.param("writer","테스트 user01"))
				.andReturn().getModelAndView().getViewName();
		System.out.println("viewName"+viewPage);
	}
	
//	@Test
//	@DisplayName("3번글의 제목과 내용을 수정하는 요청을 post 방식으로 전송하면 수정이 진행되고, "
//			+ "수정된 글의 상세보기 페이지로 이동할 것이다.")
//	// /freeboard/modify -> post
//	public void testContent() throws Exception {
//		ModelAndView mv 
//		= mockMvc.perform(MockMvcRequestBuilders.get("/freeboard/content").param("bno", "9")).andReturn().getModelAndView();
//		
//		System.out.println("Model에 저장한 데이터"+mv.getModelMap());
//		assertEquals("freeboard/freeDetail", mv.getViewName());
//	}
//	
//	@Test
//    @DisplayName("3번글의 제목과 내용을 수정하는 요청을 post 방식으로 전송하면 수정이 진행되고, "
//            + "수정된 글의 상세보기 페이지로 이동할 것이다.")
//    // /freeboard/modify -> post
//    public void testModify() throws Exception {
//		String bno = "9";
//        String viewPage = mockMvc.perform(
//        		MockMvcRequestBuilders.post("/freeboard/modify").
//        		param("title","수정!").param("content","컨텐트 수정").param("bno","9"))
//        		.andReturn().getModelAndView().getViewName();
//        assertEquals("redirect:/freeboard/content?bno="+bno, viewPage);
//    }
//    

	@Test
	@DisplayName("3번 글 상세 보기 요청을 넣으면 "
			+ "컨트롤러는 DB에서 가지고 온 글 객체를 model에 담아서 jsp로 이동시킬 것이다.")
	// /freeboard/content -> get
	void testContent() throws Exception {
		ModelAndView mv = mockMvc.perform(
					MockMvcRequestBuilders.get("/freeboard/content")
					.param("bno", "6")
				).andReturn().getModelAndView();
		System.out.println("Model: " + mv.getModelMap());
		assertEquals("freeboard/freeDetail", mv.getViewName());
		
		
	}
	
	@Test
	@DisplayName("3번글의 제목과 내용을 수정하는 요청을 post 방식으로 전송하면 수정이 진행되고, "
			+ "수정된 글의 상세보기 페이지로 이동할 것이다.")
	// /freeboard/modify -> post
	public void testModify() throws Exception {
		String bno = "6";
		String viewPage = mockMvc.perform(
				MockMvcRequestBuilders.post("/freeboard/modify")
				.param("title", "무야호")
				.param("content", "수정된 테스트 글 내용")
				.param("bno", bno)
				).andReturn().getModelAndView().getViewName();
		assertEquals("redirect:/freeboard/content?bno=" + bno, viewPage);
	}
	
	@Test
	@DisplayName("3번 글을 삭제하면 목록 재요청이 발생할 것이다.")
	// /freeboard/delete -> post
	void testDelete() throws Exception {
		assertEquals("redirect:/freeboard/freeList", 
					mockMvc.perform(MockMvcRequestBuilders.post("/freeboard/delete")
							.param("bno", "6"))
					.andReturn().getModelAndView().getViewName()
				);
	}
	
	
}
