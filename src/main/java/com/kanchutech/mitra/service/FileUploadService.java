package com.kanchutech.mitra.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.kanchutech.mitra.common.Constant;
import com.kanchutech.mitra.model.Exam;
import com.kanchutech.mitra.model.Option;
import com.kanchutech.mitra.model.Question;

public class FileUploadService implements Serializable{

	private static final long serialVersionUID = -3601759030045570596L;
	public static final Logger logger = Logger.getLogger("FileUploadService");
	public static List<String> questionSheetHeaderList = new ArrayList<String>();
	public static List<String> examSheetHeaderList = new ArrayList<String>();
	private static String[] questionTypes = new String[]{"Multiple Choice", "Multiple Response", "True/False", "Fill in the Blank"};
	private static int maxPoints = 10;
	
	static {
		questionSheetHeaderList.add(Constant.QUESTION_CONTENT);
		questionSheetHeaderList.add(Constant.QUESTION_TYPE);
		questionSheetHeaderList.add(Constant.QUESTION_POINTS);
		questionSheetHeaderList.add(Constant.HERE_AFTER_OPTIONS);
		examSheetHeaderList.add(Constant.EXAM_TITLE);
		examSheetHeaderList.add(Constant.EXAM_DESC);
		examSheetHeaderList.add(Constant.EXAM_PASS_CODE);
		examSheetHeaderList.add(Constant.EXAM_DURATION);
	}
	
	private QuestionService questionService;
	private ExamService examService;
	private ExamQuestionService examQuestionService;
	
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	public ExamService getExamService() {
		return examService;
	}

	public void setExamQuestionService(ExamQuestionService examQuestionService) {
		this.examQuestionService = examQuestionService;
	}

	public ExamQuestionService getExamQuestionService() {
		return examQuestionService;
	}

	public HSSFWorkbook createExcelTemplate(String sheetName) throws IOException {
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFCellStyle style = createExcelStyle(workBook, Constant.HEADER_FIELDSTYLE);
		HSSFCellStyle defaultstyle = createExcelStyle(workBook, "defaultstyle");
		
		int rowIndex=0;
		int cellIndex=0;
		HSSFSheet examSheet = workBook.createSheet("Exam");
		examSheet.setColumnWidth(cellIndex, 8000);
		for(String headerValue:examSheetHeaderList){
			HSSFRow examHeaderRow = examSheet.createRow(rowIndex);
			setCellValue(cellIndex, headerValue, examHeaderRow, style);
			rowIndex++;
		}
		
		HSSFSheet questionSheet = workBook.createSheet("Questions");
		HSSFRow questionHeaderRow = questionSheet.createRow(0);
		cellIndex = 0;
		for (String headerValue : questionSheetHeaderList) {
			questionSheet.setColumnWidth(cellIndex, 8000);
			questionSheet.setDefaultColumnStyle((short) cellIndex, defaultstyle);
			setCellValue(cellIndex, headerValue, questionHeaderRow, style);
			cellIndex++;
		}
		
		HSSFSheet questionTypeConfig = workBook.createSheet("typeConfig");
		for (int i = 0, length = questionTypes.length; i < length; i++) {
			String name = questionTypes[i];
			HSSFRow row = questionTypeConfig.createRow(i);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(new HSSFRichTextString(name));
		}
		Name questionTypeNamedCell = workBook.createName();
		questionTypeNamedCell.setNameName("questionTypeName");
		questionTypeNamedCell.setRefersToFormula("typeConfig!$A$1:$A$" + questionTypes.length);
		DVConstraint questionTypeConstraint = DVConstraint.createFormulaListConstraint("questionTypeName");
		CellRangeAddressList questionTypeAddressList = new CellRangeAddressList(1, 10, 1, 1);
		HSSFDataValidation typeValidation = new HSSFDataValidation(questionTypeAddressList, questionTypeConstraint);
		
		HSSFSheet pointsconfigSheet = workBook.createSheet("pointConfig");
		for (int i = 0; i < maxPoints; i++) {
			HSSFRow row = pointsconfigSheet.createRow(i);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(new HSSFRichTextString(String.valueOf(i+1)));
		}
		Name questionPointNamedCell = workBook.createName();
		questionPointNamedCell.setNameName("questionPointName");
		questionPointNamedCell.setRefersToFormula("pointConfig!$A$1:$A$" + maxPoints);
		DVConstraint questionPointConstraint = DVConstraint.createFormulaListConstraint("questionPointName");
		CellRangeAddressList questionPointAddressList = new CellRangeAddressList(1, 10, 2, 2);
		HSSFDataValidation pointValidation = new HSSFDataValidation(questionPointAddressList, questionPointConstraint);
		
		questionSheet.addValidationData(typeValidation);
		questionSheet.addValidationData(pointValidation);
		workBook.setSheetHidden(2, true);
		workBook.setSheetHidden(3, true);
		return workBook;
	}
	
	private void setCellValue(int cellIndex, String cellValue, HSSFRow row, HSSFCellStyle cellStyle) {
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(cellValue));

	}
	
	public HSSFCellStyle createExcelStyle(HSSFWorkbook workBook, String styleField) {
		HSSFFont font = null;
		HSSFCellStyle style = workBook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		style.setWrapText(true);
		if (styleField.equals(Constant.HEADER_FIELDSTYLE)) {
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
			font = workBook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.ROYAL_BLUE.index);
			font.setFontHeight((short) 200);
		} else if (styleField.equals(Constant.STATUS_HEADER_FIELDSTYLE)) {
			font = workBook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.RED.index);
			font.setFontHeight((short) 300);
		} else if (styleField.equals(Constant.STATUS_DATA_FIELDSTYLE)) {
			font = workBook.createFont();
			font.setColor(HSSFColor.RED.index);
		}
		if (font != null) {
			style.setFont(font);
		}
		return style;
	}

	public boolean validateHorizontalHeaderTemplate(HSSFWorkbook workBook, HSSFSheet dataSheet, List<String> headerList) throws Exception {
		boolean validateTemplate = true;
		try {
			HSSFRow rowData = dataSheet.getRow(0);
			HSSFCellStyle cellStyle = createExcelStyle(workBook, "defaultstyle");
			int headerListSize = headerList.size();
			for (int cellIndex = 0; cellIndex < headerListSize; cellIndex++) {
				validateTemplate = headerList.get(cellIndex).equalsIgnoreCase(readCell(rowData, cellIndex)) ? true : false;
				if (!validateTemplate) {
					break;
				}
				dataSheet.setDefaultColumnStyle((short) cellIndex, cellStyle);
			}
		} catch (Exception e) {
			throw new Exception("Invalid Question Sheet. Please download the template", e);
		}
		return validateTemplate;
	}
	
	public boolean validateVerticalHeaderTemplate(HSSFWorkbook workbook, HSSFSheet sheet, List<String> headerList) throws Exception{
		boolean validateTemplate = true;
		try {
			Iterator<Row> rows = sheet.rowIterator();
			while(rows.hasNext()){
				HSSFRow row = (HSSFRow) rows.next();
				int rowNum = row.getRowNum();
				if(!headerList.get(rowNum).equalsIgnoreCase(readCell(row, 0))){
					validateTemplate = false;
				}
			}
		} catch (Exception e) {
			throw new Exception("Invalid Exam Sheet. Please download the template", e);
		}
		return validateTemplate;
	}
	
	private Exam prepareExamFromSheet(HSSFSheet examSheet) {
		Exam exam = null;
		if(examSheet!=null){
			exam = new Exam();
			for(int i=0; i<examSheetHeaderList.size(); i++){
				HSSFRow row = examSheet.getRow(i);
				HSSFCell cell = row.getCell(1);
				if(cell!=null && !"".equals(cell.toString())){
					int cellType = cell.getCellType();
					String cellValue = null;
					if(cellType == Cell.CELL_TYPE_NUMERIC){
						int value = (int) cell.getNumericCellValue();
						cellValue = Integer.toString(value);
					}else{
						cellValue = cell.toString();
					}
					
					if(i == 0){
						exam.setTitle(cellValue);			
					}else if(i==1){
						exam.setDescription(cellValue);
					}else if(i==2){
						exam.setPassCode(cellValue);
					}else if(i==3){
						exam.setDuration(Integer.valueOf(cellValue));
					}
				}
			}
		}
		return exam;
	}
	
	public static int getQuestionSheetHeaderListSize() {
		return questionSheetHeaderList.size();
	}
	
	private String readCell(HSSFRow rowData, int cellIndex) {
		return rowData.getCell(cellIndex) != null ? rowData.getCell(cellIndex).toString().trim() : null;
	}

	public int uploadFile(InputStream inputStream) throws Exception {
		int examId = 0;
		try {
			HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
			HSSFSheet examSheet = workBook.getSheetAt(0);
			HSSFSheet questionSheet = workBook.getSheetAt(1);
			boolean validExamTemplate = validateVerticalHeaderTemplate(workBook, examSheet, FileUploadService.examSheetHeaderList);
			boolean validQuestionTemplate = validateHorizontalHeaderTemplate(workBook, questionSheet, FileUploadService.questionSheetHeaderList);
			
			if (validQuestionTemplate && validExamTemplate) {
				Exam exam = prepareExamFromSheet(examSheet);
				examId = examService.persistExam(exam);
				HSSFRow rowData;
				int lastRowNum = questionSheet.getLastRowNum();
				for (int row = 1; row <= lastRowNum; row++) {
					rowData = questionSheet.getRow(row);
					Question question = prepareQuestionFromRow(rowData, workBook);
					int questionId = questionService.persistQuestion(question);
					examQuestionService.persistExamQuestion(examId, questionId, row);
				}
			}
		} catch (Exception e) {
			throw new Exception("Invalid Exam Sheet. Please download the template", e);
		}
		return examId;
	}

	private Question prepareQuestionFromRow(HSSFRow row, HSSFWorkbook workBook) {
		Question question = null;
		if(row!=null && workBook!=null){
			int lastCellNum = row.getLastCellNum();
			List<Option> optionList = new ArrayList<Option>();
			for(int currIndex = row.getFirstCellNum(); currIndex < lastCellNum; currIndex++){
				HSSFCell currCell = row.getCell(currIndex);
				if(currIndex == 0){
					if(currCell == null || "".equals(currCell.toString())){
						break;
					}else{
						question = new Question();
						question.setContent(currCell.toString());
					}
				}else if(currIndex > 0){
					if(currCell == null || "".equals(currCell.toString())){
						continue;
					}else if(currIndex == 1){
						question.setType(getType(currCell.toString()));
					}else if(currIndex == 2){
						question.setPoints(Integer.valueOf(currCell.toString()));
					}else if(currIndex >= 3){
						Option option = new Option(currIndex-2);
						option.setContent(currCell.toString());
						if(currCell.getCellStyle().getFont(workBook).getBoldweight() == Font.BOLDWEIGHT_BOLD){
							option.setCorrect(true);
						}else{
							option.setCorrect(false);
						}
						optionList.add(option);
					}
				}
			}
			if(question!=null){
				question.setOptionList(optionList);
			}
		}
		return question;
	}
	
	private int getType(String stringCellValue) {
		int type = 1;
		if(stringCellValue!=null){
			if(stringCellValue.equalsIgnoreCase("Multiple Response")){
				type = 2;
			}else if(stringCellValue.equalsIgnoreCase("True/False")){
				type=3;
			}else if(stringCellValue.equalsIgnoreCase("Fill in the Blank")){
				type=4;
			}
		}
		return type;
	}

	public void mapExamToClient(int examId, int clientId) {
		examService.mapExamToClient(examId, clientId);
	}

}
