package com.kanchutech.mitra.model;

public class Option {
	private int optionId;
	private int questionId;
	private String content;
	private boolean correct;

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public int getOptionId() {
		return optionId;
	}

	public Option() {

	}
	
	public Option(int optionId){
		this.optionId = optionId;
	}
	
	public Option(int questionId, int optionId, String content){
		this.questionId = questionId;
		this.optionId =  optionId;
		this.content = content;
	}

	public Option(int questionId, String content) {
		this.questionId =  questionId;
		this.content = content;
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

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	@Override
	public boolean equals(Object otherOption) {
		if ((otherOption instanceof Option)){
			boolean sameQuestionId = ((Option)otherOption).getQuestionId() == this.getQuestionId();
			boolean sameOptionId = ((Option)otherOption).getOptionId() == this.getOptionId();
			if(sameQuestionId && sameOptionId) {
				return true;
			}else{
				return false;
			}
		} else{
			return false;
		}
	}
	
	@Override
	public int hashCode()
	{
	    return content!=null? this.getClass().hashCode()+content.hashCode() : super.hashCode();
	}

	@Override
	public String toString()
	{
	    return questionId+":::"+optionId+":::"+content;
	}
}
