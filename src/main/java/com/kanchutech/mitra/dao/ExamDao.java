package com.kanchutech.mitra.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.kanchutech.mitra.common.NoResultException;
import com.kanchutech.mitra.model.Exam;

public class ExamDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public List<Exam> getAllExams() {
		String query = "SELECT * FROM exam ORDER BY examId";
		return getJdbcTemplate().query(query, new BeanPropertyRowMapper<Exam>(Exam.class));
	}
	
	public Exam getExamDetailById(int examId){
		String query = "SELECT * FROM exam e WHERE e.examId="+examId;
		Exam exam = null;
		try {
			exam  = getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper<Exam>(Exam.class));
		} catch (DataAccessException e) {
			if(e instanceof EmptyResultDataAccessException){
				throw new NoResultException("No Results Found");
			}else{
				throw e;
			}
		}
		return exam;
	}

	public List<Exam> getExamsByClientId(int clientId) {
		String query = "SELECT e.examId, e.title, e.description, e.passCode, e.duration, count(eq.questionId) as questionCount FROM exam e INNER JOIN exam_question eq ON eq.examId = e.examId INNER JOIN client_exam ce ON e.examId = ce.examId WHERE ce.clientId =" + clientId +" group by ce.examId";
		return getJdbcTemplate().query(query, new BeanPropertyRowMapper<Exam>(Exam.class));
	}

	public int createAndReturnExamId(Exam exam) throws SQLException{
		int examId = 0;
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		jdbcInsert.withTableName("exam");
		jdbcInsert.setGeneratedKeyName("examId");
		Number key = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(exam));
		if (key != null){
			examId = key.intValue();
		} 
		return examId;
	}
	
	public int mapExamToClient(int examId, int clientId){
		int rowsUpdated = 0;
		if(examId>0 && clientId >0){
			rowsUpdated = getJdbcTemplate().update("INSERT INTO client_exam (clientId, examId) VALUES(?,?)", new Object[] {clientId, examId });
		}
		return rowsUpdated;
	}
}
