package com.spring.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.db.model.ScoreVO;
import com.spring.db.repository.IScoreDAO;
import com.spring.db.repository.IScoreMapper;

@Service //서비스 계층 등록
public class ScoreService implements IScoreService {

	@Autowired
	//private IScoreDAO dao;
	private IScoreMapper mapper;
	
	@Override
	public void insertScore(ScoreVO vo) {
		vo.calcData();
		System.out.println("service: " + vo);
		mapper.insertScore(vo);
	}

	@Override
	public List<ScoreVO> selectAllScores() {	
		return mapper.selectAllScores();
	}

	@Override
	public void deleteScore(int num) {
		mapper.deleteScore(num);
	}

	@Override
	public ScoreVO SelectOne(int num) {
		return mapper.SelectOne(num);
	}

}
