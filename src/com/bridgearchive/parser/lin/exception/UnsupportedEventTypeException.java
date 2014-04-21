package com.bridgearchive.parser.lin.exception;

public class UnsupportedEventTypeException extends ParseException {

	public UnsupportedEventTypeException(String eventType) {
		super("event type not supported", eventType);
	}

}
