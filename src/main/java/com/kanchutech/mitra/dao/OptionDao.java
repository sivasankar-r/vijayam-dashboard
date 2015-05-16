package com.kanchutech.mitra.dao;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.kanchutech.mitra.model.Option;

public class OptionDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public int persistOptions(int questionId, Option option) throws SQLException{
		int rowsUpdated = 0;
		if(questionId > 0 && option!=null && !option.getContent().trim().isEmpty()){
			rowsUpdated = getJdbcTemplate().update("INSERT INTO options (questionId, optionId, content, correct ) VALUES(?,?,?,?)", new Object[] { questionId, option.getOptionId(), option.getContent(), option.isCorrect() });	
		}
		return rowsUpdated;
	}
}
