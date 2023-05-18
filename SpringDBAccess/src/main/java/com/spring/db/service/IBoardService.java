package com.spring.db.service;

import java.util.List;

import com.spring.db.model.BoardVO;

public interface IBoardService {
	
	//글쓰기 기능
	void write(BoardVO vo);
	
	//리스트(list)
	List<BoardVO> list();
	
	//상세보기
	BoardVO selectContent(int bno);
	
	//수정하기
	void modifyContent(BoardVO vo);
	
	//삭제하기
	void deleteContent(int bno);
}
