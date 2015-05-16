package com.kanchutech.mitra.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kanchutech.mitra.dao.ExamDao;
import com.kanchutech.mitra.dao.QuestionDao;
import com.kanchutech.mitra.model.Exam;

public class ExamService {
	private ExamDao examDao;
	private QuestionDao questionDao;
	public static final Logger logger = Logger.getLogger("ExamService");
	
	public void setExamDao(ExamDao examDao) {
		this.examDao = examDao;
	}

	public ExamDao getExamDao() {
		return examDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public List<Exam> fetchAllExams() {
		List<Exam> examList = null;
		examList = examDao.getAllExams();
		return examList;
	}
	
	public List<Exam> fetchExamsByClientId(int clientId) {
		return examDao.getExamsByClientId(clientId);
	}
	
	public Exam fetchExamDetailById(int examId) {
		Exam exam = null;
		exam = examDao.getExamDetailById(examId);
		if(exam!=null){
			exam.setQuestionCount(questionDao.fetchQuestionCount(exam.getExamId()));
		}
		return exam;
	}

	public Exam fetchExamById(int examId) {
		Exam exam = fetchExamDetailById(examId);
		if(exam!=null){
			exam.setQuestionList(questionDao.fetchQuestions(exam.getExamId()));
		}
		return exam;
	}

	public int persistExam(Exam exam) throws Exception {
		int examId = 0;
		if(exam!=null){
			try {
				examId = examDao.createAndReturnExamId(exam);
				logger.log(Level.INFO, "ExamId : "+examId+" added successfully");	
			} catch (SQLException e) {
				throw new Exception("Exception occurred in creating exam. Verify the Exam sheet in the uploaded workbook", e);
			}
		}
		return examId;
	}

	public void mapExamToClient(int examId, int clientId) {
		int rowsUpdated = 0;
		try {
			rowsUpdated = examDao.mapExamToClient(examId, clientId);
			if(rowsUpdated<=0){
				throw new Exception("Mapping Exam to Client failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
