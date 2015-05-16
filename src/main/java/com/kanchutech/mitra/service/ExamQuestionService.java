package com.kanchutech.mitra.service;

import java.sql.SQLException;

import com.kanchutech.mitra.dao.ExamQuestionDao;

public class ExamQuestionService{
	private ExamQuestionDao examQuestionDao;

	public void setExamQuestionDao(ExamQuestionDao examQuestionDao) {
		this.examQuestionDao = examQuestionDao;
	}

	public ExamQuestionDao getExamQuestionDao() {
		return examQuestionDao;
	}

	public void persistExamQuestion(int examId, int questionId, int row) throws Exception {
		try {
			examQuestionDao.persistExamQuestion(examId, questionId, row);
		} catch (SQLException e) {
			throw new Exception("Exception occurred in mapping question to exam", e);
		}
	}
}
