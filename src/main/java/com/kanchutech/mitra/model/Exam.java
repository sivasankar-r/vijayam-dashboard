package com.kanchutech.mitra.model;

import java.io.Serializable;
import java.util.List;

public class Exam implements Serializable{
	
	private static final long serialVersionUID = -8939207206768820590L;
	private int examId;
	private String title;
	private String description;
	private String passCode;
	private int duration;
	private int questionCount;
	private List<Question> questionList;

	public Exam(){
		
	}
	
	public Exam(int examId, String title){
		this.examId=examId;
		this.title=title;
	}
	
	public Exam(int examId, String title, String description, String passCode){
		this(examId, title);
		this.description=description;
		this.passCode=passCode;
	}
	
	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassCode() {
		return passCode;
	}

	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}
}
