package com.bridgearchive.parser.lin.tag;

import com.bridgearchive.parser.lin.exception.ParseException;

public class McTag extends Tag {

	private final int tricks;

	public McTag(String value) throws ParseException {
		super(value);
		tricks = Integer.parseInt(value);
		if (tricks < 0 || tricks > 13) {

			throw new ParseException("mc tag", "illegal nr of tricks " + value);
		}
	}

	public Integer getClaimedTrickCount() {
		return tricks;
	}

}
