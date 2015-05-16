package com.kanchutech.mitra.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private int questionId;
	private String content;
	private int type = 1;
	private String typeDesc;
	private int points = 1;
	private int difficulty = 3;
	private int sortOrder;
	private List<Option> optionList = new ArrayList<Option>();
	
	public Question(){
		
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public List<Option> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}
	
	public void addOption(Option option){
		this.optionList.add(option);
	}

	public void removeOption(Option optionToRemove) {
		this.optionList.remove(optionToRemove);
	}

}
