package com.kanchutech.mitra.dao;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

public class ExamQuestionDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public int persistExamQuestion(int examId, int questionId, int rowNum) throws SQLException{
		int rowsUpdated = 0;
		if(questionId > 0 && examId > 0){
			rowsUpdated = getJdbcTemplate().update("INSERT INTO exam_question (examId, questionId, sortOrder) VALUES(?,?,?)", new Object[] { examId, questionId, rowNum});	
		}
		return rowsUpdated;
	}
}
