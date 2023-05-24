package com.spring.myweb.reply.mapper;

import java.util.List;
import java.util.Map;

import com.spring.myweb.command.ReplyVO;

public interface IReplyMapper {
	
	void replyResist(ReplyVO vo);//댓글등록	
	List<ReplyVO> getList(Map<String, Object> data);//목록요청	
	int getTotal(int bno);//댓글갯수(페이징)
	
	String pwCheck(int rno);//비밀번호확인	
	void update(ReplyVO vo);//댓글수정	
	void delete(int rno);//댓글삭제
	
}
