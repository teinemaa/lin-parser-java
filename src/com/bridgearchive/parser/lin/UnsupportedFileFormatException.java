package com.bridgearchive.parser.lin;

import com.bridgearchive.parser.lin.exception.ParseException;

public class UnsupportedFileFormatException extends ParseException {

	public UnsupportedFileFormatException(String fileFormat) {
		super("unsupported file format", fileFormat);
	}

}
