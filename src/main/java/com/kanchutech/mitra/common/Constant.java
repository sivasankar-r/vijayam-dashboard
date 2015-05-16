package com.kanchutech.mitra.common;

public class Constant {
	public static final String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static final String DB_SUB_PROTOCOL = "jdbc:mysql";
	public static final String DB_HOST = "10.105.5.17";
	public static final String DB_PORT = "3306";
	public static final String DB_SCHEMA_NAME = "mitra_panther";
	public static final String DB_USERNAME="mitra";
	public static final String DB_PASSWORD = "mitra";
	public static final String DB_CONNECTION_STRING = DB_SUB_PROTOCOL+"://"+DB_HOST+":"+DB_PORT+"/"+DB_SCHEMA_NAME;
	
	/** The Constant EXCELHEADING. */
	public static final String EXCELHEADING = "STATUS";
	
	/** The Constant EXCEL_CONTENTTYPE. */
	public static final String EXCEL_CONTENTTYPE = "application/vnd.ms-excel";
	
	/** The Constant FILENAME. */
	public static final String FILENAME = "STATUS.xls";

	/** The Constant TEMPLATE_FILENAME. */
	public static final String TEMPLATE_FILENAME = "Questions_Upload_Template.xls";

	/** The Constant HEADER_FIELDSTYLE. */
	public static final String HEADER_FIELDSTYLE = "headerfield";

	/** The Constant STATUS_HEADER_FIELDSTYLE. */
	public static final String STATUS_HEADER_FIELDSTYLE = "statusheaderfield";

	/** The Constant STATUS_DATA_FIELDSTYLE. */
	public static final String STATUS_DATA_FIELDSTYLE = "statusdatafield";

	public static final String EXAM_ID = "ExamID";
	public static final String QUESTION_NUMBER="Question Number";
	public static final String QUESTION_CONTENT = "Question";
	public static final String QUESTION_TYPE = "Question Type";
	public static final String QUESTION_POINTS = "Question Points";
	public static final String INVALID_TEMPLATE_MSG = "The Uploaded file is not in valid format. Please download the template";
	public static final String HERE_AFTER_OPTIONS = "Options From This Column";
	public static final String EXAM_TITLE = "Exam Title";
	public static final String EXAM_DESC = "Exam Description";
	public static final String EXAM_PASS_CODE = "Exam Pass Code";
	public static final String EXAM_DURATION = "Exam Duration";

	
}
