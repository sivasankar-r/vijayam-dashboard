package com.kanchutech.mitra.managed;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.kanchutech.mitra.model.Client;

@ManagedBean(name="userMBean")
@SessionScoped
public class UserMBean implements Serializable{

	private static final long serialVersionUID = -1670860734020604646L;
	private Client client;
	
	@PostConstruct
	public void init(){
		if(client==null){
			client = new Client();
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
}
