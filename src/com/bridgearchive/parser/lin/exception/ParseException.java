package com.bridgearchive.parser.lin.exception;

public class ParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int fileNr;

	private String type;

	private String message;

	public ParseException(String type, String message) {
		this.type = type;
		this.message = message;

	}

	public void setFileNr(int nr) {
		fileNr = nr;
	}

	public String getType() {
		return type;
	}

	@Override
	public String getMessage() {
		return fileNr + ".lin " + getType() + ": " + message;
	}

}
