package com.spring.db.common;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.spring.db.model.ScoreVO;

//jdbcTemplate에서 SELECT쿼리를 위한 RESULTSET사용을 원활하게 하기 위한 클래스 생성
//RowMapper 인터페이스 구현
public class ScoreMapper implements RowMapper<ScoreVO>{

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
