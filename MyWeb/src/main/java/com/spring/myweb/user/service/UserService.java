package com.spring.myweb.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.myweb.command.UserVO;
import com.spring.myweb.user.mapper.IUserMapper;
import com.spring.myweb.util.PageVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements IUserService {

	//DI ->dependency injection(의존성 주입), IOC->Inversion of control(제어의 역전)
	@Autowired
	private IUserMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public int idCheck(String id) {	
		return mapper.idCheck(id);
	}

	@Override
	public void join(UserVO vo) {
		//회원 비밀번호 암호화 인코딩
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		log.info("암호화 전 비밀번호: " + vo.getUserPw());
		
		//비밀번호를 암호화해서 vo객체에 다시 저장하기
		String securePw = encoder.encode(vo.getUserPw());
		log.info("암호화 후 비밀번호: " + securePw);
		vo.setUserPw(securePw);
		mapper.join(vo);
	}

	@Override
	public String login(String id, String pw) {
		//db에서 id정보를 기반으로 회원의 정보를 조회
		String dbPw = mapper.login(id);
		if(dbPw != null) {
			//String dbPw = vo.getUserPw(); //db에서 가져온 암호화된 pw
			if(encoder.matches(pw, dbPw)) {
				//return vo.getUserId();
				return id;
			}
		}	
		return null;
	}

	@Override
	public UserVO getInfo(String id, PageVO vo) {
		return mapper.getInfo(id, vo);
	}

	@Override
	public void updateUser(UserVO vo) {
		

	}

}
