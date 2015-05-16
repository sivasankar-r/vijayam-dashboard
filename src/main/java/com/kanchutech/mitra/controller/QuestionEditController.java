package com.kanchutech.mitra.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

import com.kanchutech.mitra.managed.QuestionEditMBean;
import com.kanchutech.mitra.model.Option;
import com.kanchutech.mitra.model.Question;
import com.kanchutech.mitra.service.QuestionService;

@ManagedBean(name="questionEditController")
@RequestScoped
public class QuestionEditController {

	@ManagedProperty(value="#{questionEditMBean}")
	private QuestionEditMBean questionEditMBean;
	
	@ManagedProperty(value="#{questionService}")
	private QuestionService questionService;
	
	public QuestionEditMBean getQuestionEditMBean() {
		return questionEditMBean;
	}

	public void setQuestionEditMBean(QuestionEditMBean questionEditMBean) {
		this.questionEditMBean = questionEditMBean;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	public String updateQuestion(){
		for(Option option : questionEditMBean.getSelQuestion().getOptionList()){
			if(questionEditMBean.getOptionSelected().getContent().equals(option.getContent())){
				option.setCorrect(true);
			}else{
				option.setCorrect(false);
			}
		}
		questionService.update(questionEditMBean.getSelQuestion());
		return "showQuestions";
	}
	
	public String loadEditQuestion(){
		Question selectedQues = questionEditMBean.getSelQuestion();
		for(Option option : selectedQues.getOptionList()){
			if(option.isCorrect()){
				questionEditMBean.setOptionSelected(option);
			}
		}
		return "editQuestion";
	}
	
	public void addOption(AjaxBehaviorEvent event){
		Option option = new Option(questionEditMBean.getSelQuestion().getQuestionId(), questionEditMBean.getNewOptionText());
		questionEditMBean.getSelQuestion().addOption(option);
	}
	
	public void removeOption(){
		questionEditMBean.getSelQuestion().removeOption(questionEditMBean.getOptionToRemove());
	}
}
