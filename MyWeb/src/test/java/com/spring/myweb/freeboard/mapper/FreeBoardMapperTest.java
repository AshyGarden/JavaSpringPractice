package com.spring.myweb.freeboard.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.myweb.command.FreeBoardVO;
import com.spring.myweb.util.PageVO;

@ExtendWith(SpringExtension.class) //테스트 환경을 만들어 주는 junit5 객체로딩
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/db-config.xml"
		//,"file:src/main/webapp/WEB-INF/config/mybatis-config.xml"
})
public class FreeBoardMapperTest {
	
	@Autowired
	private IFreeBoardMapper mapper;
	
	//단위 테스트(unit test) - 가장 작은 단위의 테스트
	//테스트 시나리오 
	//-단언(Assertion) 기법
	
	@Test
	@DisplayName("Mapper 계층에 regist를 호출하면서 FreeBoardVO를 전달하면 데이터가 INSERT된다.")
	void registTest() {
		//given - when -then pattern(생략가능)
		
		//given: 테스트를 위해 주어질 데이터
		for(int i=1; i<200; i++) {		
			FreeBoardVO vo = new FreeBoardVO();
			vo.setTitle("test"+i);
			vo.setWriter("abc1234");
			vo.setContent("메롱");
			
			//when: 테스트 실제상황
			mapper.regist(vo);
		}
		
		//then: 테스트 결과 확인
		
	}
	@Test
	//@DisplayName("전체글 목록 조회후, 조회된 글 갯수를 파악했을때 하나조회")
	@DisplayName("사용자가 원하는 페이지 번호에 맞는 글 목록을 불러올 것이고, "
			+ "게시물의 개수는 사용자가 원하는 만큼의 개수를 가진다.")
	void getListTest() {
		PageVO vo = new PageVO();
		vo.setPageNum(7);
		List<FreeBoardVO> list =  mapper.getList(vo);
		/*
		 for(FreeBoardVO vo: list){
		 	System.out.println(vo);
		 } 와 밑의 내용이 같음.
		 */
		list.forEach(article->System.out.println(article)); //lamda식
		
		assertEquals(vo.getCpp(), list.size()); //ㅣlist.size()의 크기가 1이라는것을 단언
		
	}
	
	@Test
	@DisplayName("글번호가 1번인 글 조회시, 잘 나올것이다.")
	void getContentTest() {
		//given
		int bno=11;
		
		//when
		FreeBoardVO vo = mapper.getContent(bno);
		
		//then
		//assertEquals("abc1234",vo.getWriter());
		//assertEquals("안녕하세요!",vo.getContent());
		
		assertNull(vo);
	}
	
	@Test
	@DisplayName("글 번호가 1번인 글의 제목과 내용을 수정후 다시 죄회 했을때 제목이 수정한 제목으로 바뀌는것을 단언")
	void updateTest() {
	     
        //given
        FreeBoardVO vo = new FreeBoardVO();
        vo.setBno(9);
        
        vo.setTitle("수정수정 크리스탈 정수정");
        vo.setContent("크리스탈 파워");
        
        //when
        mapper.update(vo);
        
        //then
        assertEquals(vo.getTitle(), mapper.getContent(vo.getBno()).getTitle());
		
	}
	@Test
	@DisplayName("글 번호가 1번인을 삭제후 다시 조회 했을때 리스트 전체조회시 글개수는 1개이고, 2번글 조회시 null반환")
	void deleteTest() {
		//given
		int bno = 8;
		
		//when
		mapper.delete(bno);
		
		//then
//		assertEquals(0, mapper.getList().size());
		assertNull(mapper.getContent(bno));
	}
	
	
	
	
}
