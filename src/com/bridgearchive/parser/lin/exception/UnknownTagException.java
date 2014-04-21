package com.bridgearchive.parser.lin.exception;

public class UnknownTagException extends ParseException {

	public UnknownTagException(String tagKey) {
		super("unknown tag", "'" + tagKey + "'");
	}
}
