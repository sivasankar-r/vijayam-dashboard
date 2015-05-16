package com.kanchutech.mitra.model;

import java.io.Serializable;

public class Client implements Serializable{

	private static final long serialVersionUID = -5928842318010424718L;
	private int clientId;
	private String name;
	private String username;
	private String password;
	
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
