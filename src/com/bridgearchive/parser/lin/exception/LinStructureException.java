package com.bridgearchive.parser.lin.exception;

public class LinStructureException extends ParseException {

	public LinStructureException(String message) {
		super("invalid lin structure", message);
	}

}
