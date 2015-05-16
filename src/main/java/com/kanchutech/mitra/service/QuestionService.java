package com.kanchutech.mitra.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kanchutech.mitra.dao.OptionDao;
import com.kanchutech.mitra.dao.QuestionDao;
import com.kanchutech.mitra.model.Option;
import com.kanchutech.mitra.model.Question;

public class QuestionService {
	public static final Logger logger = Logger.getLogger("QuestionService");
	private QuestionDao questionDao;
	private OptionDao optionDao;

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setOptionDao(OptionDao optionDao) {
		this.optionDao = optionDao;
	}

	public OptionDao getOptionDao() {
		return optionDao;
	}

	/*public int createQuestion(Question question) {
		int rowsUpdated = 0;
		if(question!=null){
			rowsUpdated = questionDao.createQuestion(question);
		}
		return rowsUpdated;
	}*/
	
	public int persistQuestion(Question question) throws Exception {
		int questionId = 0;
		if(question!=null && !question.getContent().trim().isEmpty()){
			try {
				questionId = questionDao.createAndReturnQuestionId(question);
				logger.log(Level.INFO, "QuestionId : "+questionId+" added successfully");	
			} catch (SQLException e) {
				throw new Exception("Exception occurred in creating question with question content \""+question.getContent()+"\"", e);
			}
			if(questionId >0){
				for(Option option : question.getOptionList()){
					int rowsUpdated = 0;
					try {
						rowsUpdated = optionDao.persistOptions(questionId, option);
					} catch (SQLException e) {
						throw new Exception("Exception occurred in persisting optionId : " + option.getOptionId()+ "for the question \""+question.getContent()+"\"", e);
					}
					if(rowsUpdated>0){
						logger.log(Level.INFO, "Persisted Question ID: "+questionId+ ", optionId: "+option.getOptionId());
					}else{
						logger.log(Level.SEVERE, "Failed to Persist Question ID: "+questionId+ ", optionId: "+option.getOptionId());
					}
				}
			}
		}
		return questionId;
	}
	
	public List<Question> fetchQuestions(int examId){
		return questionDao.fetchQuestionsWithTypeDesc(examId);
	}

	public void update(Question question) {
		if(question!=null){
			try {
				questionDao.update(question);
				int optionId = 1;
				for (Option option : question.getOptionList()) {
					option.setOptionId(optionId++);
					questionDao.persistOptions(question.getQuestionId(), option);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
