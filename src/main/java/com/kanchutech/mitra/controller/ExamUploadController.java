package com.kanchutech.mitra.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import com.kanchutech.mitra.common.Constant;
import com.kanchutech.mitra.managed.UserMBean;
import com.kanchutech.mitra.service.FileUploadService;

@ManagedBean(name = "examUploadController")
@ViewScoped
public class ExamUploadController implements Serializable{

	private static final long serialVersionUID = 8060341947789490132L;
	
	@ManagedProperty(value="#{fileUploadService}")
	private FileUploadService fileUploadService;
	
	@ManagedProperty(value="#{userMBean}")
	private UserMBean userMBean;
	
	private String uploadErrorMessage;
	private String uploadSuccessMessage;
	private boolean uploadSuccess;
	private int examId;
	
	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}
	
	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getExamId() {
		return examId;
	}

	public void fileUploadListener(FileUploadEvent event) throws IOException {
		clearMessages();
		UploadedFile item = event.getUploadedFile();
		try {
			examId = fileUploadService.uploadFile(item.getInputStream());
		} catch (Exception e) {
			uploadErrorMessage = e.getMessage();
		}
		if(examId>0){
			fileUploadService.mapExamToClient(examId, userMBean.getClient().getClientId());
			uploadSuccess = true;
			uploadSuccessMessage = "ExamId : "+examId+ " uploaded successfully";
		}
	}

	private void clearMessages() {
		uploadSuccessMessage = "";
		uploadErrorMessage = "";
	}

	/**
	 * Template excel download.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void templateExcelDownload() throws IOException {
		ByteArrayOutputStream streamObj = null;
		BufferedInputStream buffInStream = null;
		ServletOutputStream outputStream = null;
		try {
			streamObj = new ByteArrayOutputStream();
			HSSFWorkbook templateWB = fileUploadService.createExcelTemplate(Constant.TEMPLATE_FILENAME);
			templateWB.write(streamObj);
			buffInStream = new BufferedInputStream(new ByteArrayInputStream(streamObj.toByteArray()));
			outputStream = getOutputStream(Constant.EXCEL_CONTENTTYPE, Constant.TEMPLATE_FILENAME);
			writeStreamData(outputStream, buffInStream);
		} catch (IOException exc) {
			exc.printStackTrace();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (buffInStream != null) {
				buffInStream.close();
			}
			if (streamObj != null) {
				streamObj.close();
			}
		}
	}
	
	/**
	 * Write stream data.
	 * 
	 * @param outputStream the output stream
	 * @param inputStream the input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeStreamData(OutputStream outputStream, InputStream inputStream) throws IOException {
		int writeData = 0;
		while ((writeData = inputStream.read()) != -1) {
			outputStream.write(writeData);
		}
	}
	
	public ServletOutputStream getOutputStream(final String contentType, final String fileName) throws IOException {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType(contentType);
		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		FacesContext.getCurrentInstance().responseComplete();
		return response.getOutputStream();
	}

	public void setUploadErrorMessage(String uploadErrorMessage) {
		this.uploadErrorMessage = uploadErrorMessage;
	}

	public String getUploadErrorMessage() {
		return uploadErrorMessage;
	}

	public void setUploadSuccessMessage(String uploadSuccessMessage) {
		this.uploadSuccessMessage = uploadSuccessMessage;
	}

	public String getUploadSuccessMessage() {
		return uploadSuccessMessage;
	}

	public void setUploadSuccess(boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}

	public boolean isUploadSuccess() {
		return uploadSuccess;
	}
}
