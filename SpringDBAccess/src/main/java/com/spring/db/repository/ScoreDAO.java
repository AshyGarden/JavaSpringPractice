package com.spring.db.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.spring.db.common.ScoreMapper;
import com.spring.db.model.ScoreVO;

@Repository
public class ScoreDAO implements IScoreDAO {

	//내부(중첩) 클래스(inner class)
	//두 클래스가 굉장히 긴밀한관계가 있을때 주로선언
	//해당 클래스 안에서만 사용할 클래스를 굳이 새 파일로 선언하지 않고 만들수 있다.
	class ScoreMapper implements RowMapper<ScoreVO>{

		@Override
		public ScoreVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.printf("Maprow Activate! - ");
			System.out.println("row: "+ rowNum);
			ScoreVO vo = new ScoreVO(
					rs.getInt("stu_id"),
					rs.getString("stu_name"),
					rs.getInt("kor"),
					rs.getInt("eng"),
					rs.getInt("math"),
					rs.getInt("total"),
					rs.getDouble("average")
					);		
			return vo;
		}

	}
	
	//spring-jdbc 방식의 처리: jdbctemplate 활용
	@Autowired
	private JdbcTemplate template;
	
	@Override
	public void insertScore(ScoreVO vo) {
		String sql = "INSERT INTO scores (stu_name, kor, eng, math, total, average) "
				+ "VALUES(?,?,?,?,?,?)";
		template.update(sql,vo.getStuName(),vo.getKor(), vo.getEng(),
				vo.getMath(),vo.getTotal(),vo.getAverage());
	}

	@Override
	public List<ScoreVO> selectAllScores() { //조회결과가 다수!
		String sql = "SELECT * FROM scores ORDER BY stu_id ASC";
		return template.query(sql, new ScoreMapper());
	}

	@Override
	public void deleteScore(int num) {
		String sql = "DELETE FROM scores WHERE stu_id=?";
		template.update(sql,num);
	}

	@Override
	public ScoreVO SelectOne(int num) { //조회 결과가 1개!
		String sql = "SELECT * FROM scores WHERE stu_id=?";
		try {
			return template.queryForObject(sql, new ScoreMapper(), num);
		} catch (Exception e) {
			return null;
		}
	}

}
