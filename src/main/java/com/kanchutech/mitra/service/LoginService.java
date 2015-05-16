package com.kanchutech.mitra.service;

import org.springframework.dao.DataAccessException;

import com.kanchutech.mitra.dao.LoginDao;
import com.kanchutech.mitra.model.Client;

public class LoginService {
	private LoginDao loginDao;

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public Client isValidUser(String username, String password) throws Exception{
		Client client = null;
		try {
			client = loginDao.isValidUser(username, password);
		} catch (DataAccessException e) {
			throw new Exception("Unknown Exception Occurred. Contact Administrator.", e);
		} catch (Exception e) {
			throw e;
		} 
		return client;
	}
	
	
}
