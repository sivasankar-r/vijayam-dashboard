package com.kanchutech.mitra.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.dao.DataAccessException;

import com.kanchutech.mitra.common.InternalServerException;
import com.kanchutech.mitra.common.NoResultException;
import com.kanchutech.mitra.model.Exam;
import com.kanchutech.mitra.service.ExamService;

@Path("/exams")
public class ExamRestService {
	private ExamService examService;

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	public ExamService getExamService() {
		return examService;
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Exam> fetchExams() {
		List<Exam> examList = null;
		try{
			examList = examService.fetchAllExams();
		}catch(DataAccessException exception){
			throw new InternalServerException("Unknown Exception Occurred");
		}
		return examList;
	}
	
	@GET
	@Path("/clientId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Exam> fetchExamsByClientId(@PathParam("id") int clientId) {
		List<Exam> examList = null;
		try{
			examList = examService.fetchExamsByClientId(clientId);
		} catch(DataAccessException exception){
			throw new InternalServerException("Unknown Exception Occurred");
		}
		return examList;
	}
	
	@GET
	@Path("/examDetail/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Exam fetchExamDetailById(@PathParam("id") int examId) {
		Exam exam = null;
		try {
			exam = examService.fetchExamDetailById(examId);
		} catch(NoResultException nre){
			throw nre;
		} catch (DataAccessException e) {
			throw new InternalServerException("Unknown Exception Occurred");
		}
		return exam;
	}
	
	@GET
	@Path("/examId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Exam fetchExamById(@PathParam("id") int examId) {
		Exam exam = null;
		try {
			exam = examService.fetchExamById(examId);
		} catch(NoResultException nre){
			throw nre;
		} catch (DataAccessException e) {
			throw new InternalServerException("Unknown Exception Occurred");
		}
		return exam;
	}
	
}
