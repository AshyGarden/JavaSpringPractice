package com.spring.myweb.user.service;

import org.apache.ibatis.annotations.Param;

import com.spring.myweb.command.UserVO;

public interface IUserService {
	
	//아이디 중복확인
	int idCheck(String id);
	
	//회원가입
	void join(UserVO vo);
	
	
	//로그인
	UserVO login(String id, String pw);
	
	
	//회원 정보 얻어오기
	UserVO getInfo(String id);
	
	//회원정보수정
	void updateUser(UserVO vo);
	
	//회원탈퇴 -미구현일수도있음
}
