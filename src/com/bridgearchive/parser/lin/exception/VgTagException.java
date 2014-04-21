package com.bridgearchive.parser.lin.exception;

public class VgTagException extends ParseException {

	public VgTagException(String vgTagValue) {
		super("vg tag", vgTagValue);
	}

}
