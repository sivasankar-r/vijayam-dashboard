package com.kanchutech.mitra.managed;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.kanchutech.mitra.model.Option;
import com.kanchutech.mitra.model.Question;

@ManagedBean(name = "questionEditMBean")
@SessionScoped
public class QuestionEditMBean implements Serializable{

	private static final long serialVersionUID = 7233033751687697794L;
	private Question selQuestion;
	private Option optionToRemove;
	private String newOptionText;
	private Option optionSelected;
		
	public void setSelQuestion(Question selQuestion) {
		this.selQuestion = selQuestion;
	}

	public Question getSelQuestion() {
		return selQuestion;
	}

	public void setOptionToRemove(Option optionToRemove) {
		this.optionToRemove = optionToRemove;
	}

	public Option getOptionToRemove() {
		return optionToRemove;
	}

	public void setNewOptionText(String newOptionText) {
		this.newOptionText = newOptionText;
	}

	public String getNewOptionText() {
		return newOptionText;
	}

	public void setOptionSelected(Option optionSelected) {
		this.optionSelected = optionSelected;
	}

	public Option getOptionSelected() {
		return optionSelected;
	}
}