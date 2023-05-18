package com.spring.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.spring.db.model.BoardVO;
import com.spring.db.repository.IBoardDAO;
import com.spring.db.repository.IBoardMapper;

@Service
public class BoardService implements IBoardService{

//	@Autowired
//	@Qualifier("boardDAO") // 타입이 겹칠때 강제로 타입을 할당
//	private IBoardDAO dao;
	
	@Autowired
	private IBoardMapper mapper;
	
	@Override
	public void write(BoardVO vo) {
		mapper.insertArticle(vo);		
	}

	@Override
	public List<BoardVO> list() { //getArticles()
		return mapper.getArticles();
	}

	@Override
	public BoardVO selectContent(int bno) { //getArticle()
		return mapper.getArticle(bno);
	}

	@Override
	public void modifyContent(BoardVO vo) {	
		mapper.updateArticle(vo);
	}

	@Override
	public void deleteContent(int bno) {
		mapper.deleteArticle(bno);	
	}

}
