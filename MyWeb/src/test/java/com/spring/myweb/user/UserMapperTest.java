package com.spring.myweb.user;

import static org.junit.jupiter.api.Assertions.*;

//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.myweb.command.UserVO;
import com.spring.myweb.user.mapper.IUserMapper;
import com.spring.myweb.util.PageVO;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/config/db-config.xml")
@Slf4j
public class UserMapperTest {
	
	@Autowired
	private IUserMapper mapper;
	
	@Test
	@DisplayName("회원가입시 가입에 성공해야함")
	void registTest() {
		UserVO vo = new UserVO();
		vo.setUserId("abc1234");
		vo.setUserPw("aaa111");
		vo.setUserName("에이비씨");
		
		mapper.join(vo);
	}
	
	
	 
	@Test   
	@DisplayName("존재하는 회원 아이디를 조회했을 시 1이 리턴이 되어야 한다.") //idCheck
	void checkIdTest() {
	   String id = "abc1234";
	   mapper.idCheck(id);
	    
	   assertEquals(1, mapper.idCheck(id));
	}
	    
	@Test
	@DisplayName("존재하는 회원 아이디와 올바른 비밀번호를 입력했을 시 "
	        + "회원의 정보가 리턴되어야 한다.")
	void loginTest() {
		 String id = "abc1234";
		 String pw = "aaa111"; 
	
		 assertNotNull( mapper.login(null));
		
		//case1
//		Map<String, String> data = new HashMap<>();
//		data.put("id", "abc1234");
//		data.put("pw", "aaa111"); + 매개변수 map을 받는거로 바꾸기
//		assertNotNull(mapper.login(null));
		
	}
	    
	@Test
	@DisplayName("존재하지 않는 회원의 아이디를 입력하면 null이 올 것이다.")
	void getInfoTest() {
		PageVO paging = new PageVO();
		UserVO vo = mapper.getInfo("abc1234", paging);
		log.info(vo.toString());
//		assertNotNull(mapper.getInfo("abc1234"));
	}
	    
	@Test
	@DisplayName("id를 제외한 회원의 정보를 수정할 수 있다.")
	void updateTest() {
	      UserVO vo = new UserVO();
	      vo.setUserId("abc1234"); //id는 primarykey ->바꿀수없음
	      vo.setUserPw("asd123");
	      vo.setUserName("에이비씨팔");
	      vo.setUserEmail1("abc1234");
	      vo.setUserEmail2("@gmail.com");
	      mapper.updateUser(vo);
//	      assertEquals(mapper.getInfo("abc1234").getUserName(), vo.getUserName());
	}

}
