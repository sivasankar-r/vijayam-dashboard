package com.kanchutech.mitra.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kanchutech.mitra.model.Client;

public class LoginDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public Client isValidUser(String username, String password) throws Exception{
		String query = "SELECT * FROM client c WHERE c.username='"+username+"' AND c.password='"+password+"'";
		Client user = null;
		try{
			user = getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper<Client>(Client.class));
		}catch(DataAccessException e){
			if(e instanceof EmptyResultDataAccessException){
				throw new Exception("Invalid Username / Password", e);
			}else{
				throw e;
			}
		}
		return user;
	}

}
