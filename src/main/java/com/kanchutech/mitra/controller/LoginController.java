package com.kanchutech.mitra.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.kanchutech.mitra.managed.ExamsMBean;
import com.kanchutech.mitra.managed.UserMBean;
import com.kanchutech.mitra.model.Client;
import com.kanchutech.mitra.service.LoginService;

@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController {
	
	@ManagedProperty(value="#{userMBean}")
	private UserMBean userMBean;
	
	@ManagedProperty(value="#{loginService}")
	private LoginService loginService;

	@ManagedProperty(value="#{examsMBean}")
	private ExamsMBean examsMBean;

	private String loginMessage;	
	
	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}
	
	public ExamsMBean getExamsMBean() {
		return examsMBean;
	}

	public void setExamsMBean(ExamsMBean examsMBean) {
		this.examsMBean = examsMBean;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public String login(){
		String toPage = null;
		Client user = userMBean.getClient();
		Client client = null;
		try {
			client = loginService.isValidUser(user.getUsername(), user.getPassword());
		} catch (Exception e) {
			setLoginMessage(e.getMessage());
		}
		if(client!=null){
			user.setClientId(client.getClientId());
			user.setName(client.getName());
			user.setUsername(client.getUsername());
			toPage = examsMBean.loadExams();
		}		
		return toPage;
	}
}
