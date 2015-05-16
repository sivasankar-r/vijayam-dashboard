package com.kanchutech.mitra.managed;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.kanchutech.mitra.model.Exam;
import com.kanchutech.mitra.service.ExamService;

@ManagedBean(name = "examsMBean")
@SessionScoped
public class ExamsMBean implements Serializable{
	
	private static final long serialVersionUID = 3485937020246662440L;
	
	@ManagedProperty(value="#{examService}")
	private ExamService examService;
	
	@ManagedProperty(value="#{userMBean}")
	private UserMBean userMBean;
	
	private List<Exam> examList;

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	public ExamService getExamService() {
		return examService;
	}

	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}

	public List<Exam> getExamList() {
		return examList;
	}
	
	public String loadExams(){
		int clientId = userMBean.getClient().getClientId();
//		String clientId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("clientId");
		examList = examService.fetchExamsByClientId(clientId);
		return "showExams";
	}
}
