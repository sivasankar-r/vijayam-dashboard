package com.kanchutech.mitra.managed;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.kanchutech.mitra.model.Question;

@ManagedBean(name = "questionMBean")
@SessionScoped
public class QuestionMBean implements Serializable{
	
	private static final long serialVersionUID = 4671695153857560699L;
	private List<Question> questionList;
	
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

}