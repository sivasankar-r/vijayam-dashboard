package com.kanchutech.mitra.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.kanchutech.mitra.managed.QuestionMBean;
import com.kanchutech.mitra.service.QuestionService;

@ManagedBean(name = "questionController")
@RequestScoped
public class QuestionController {
	@ManagedProperty(value="#{questionService}")
	private QuestionService questionService;
	
	@ManagedProperty(value="#{questionMBean}")
	private QuestionMBean questionMBean;
	
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionMBean(QuestionMBean questionMBean) {
		this.questionMBean = questionMBean;
	}

	public QuestionMBean getQuestionMBean() {
		return questionMBean;
	}

	public String loadExamQuestions(){
		setExamIdToSession();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String examId = (String) request.getSession(false).getAttribute("examId");
		questionMBean.setQuestionList(questionService.fetchQuestions(Integer.parseInt(examId)));
		return "showQuestions";
	}
	
	private void setExamIdToSession(){
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String examId = context.getRequestParameterMap().get("examId");
		HttpServletRequest request = (HttpServletRequest)context.getRequest();
		request.getSession(false).setAttribute("examId", examId);
	}
}