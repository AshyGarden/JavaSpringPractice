package com.spring.myweb.reply.service;

import java.util.List;
import java.util.Map;

import com.spring.myweb.command.ReplyVO;

public interface IReplyService {
	
	void replyResist(ReplyVO vo);//댓글등록	
	List<ReplyVO> getList(int bno, int pageNum);//목록요청	
	int getTotal(int bno);//댓글갯수(페이징)
	
	boolean pwCheck(ReplyVO vo);//비밀번호확인	
	void update(ReplyVO vo);//댓글수정	
	void delete(int rno);//댓글삭제
}
