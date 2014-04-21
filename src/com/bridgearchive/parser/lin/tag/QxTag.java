package com.bridgearchive.parser.lin.tag;

import com.bridgearchive.parser.lin.exception.ParseException;

public class QxTag extends Tag {

	private final int boardNr;
	private final boolean isOpenRoom;

	public QxTag(String value) throws ParseException {
		super(value);
		if (value.charAt(0) == 'o') {
			isOpenRoom = true;
		} else if (value.charAt(0) == 'c') {
			isOpenRoom = false;
		} else {

			throw new ParseException("qx tag", "unknown room " + value);
		}
		int endIndex = value.indexOf(",BOARD");
		if (endIndex == -1) {
			endIndex = value.length();
		}
		String boardNrString = value.substring(1, endIndex);
		try {
			boardNr = Integer.parseInt(boardNrString);
			if (boardNr <= 0) {
				throw new ParseException("qx tag", "nonpositive board nr " + value);
			}
		} catch (NumberFormatException e) {
			throw new ParseException("qx tag", "unknown board nr " + value);

		}

	}

	public int getBoardNr() {
		return boardNr;
	}

	public boolean isOpenRoom() {
		return isOpenRoom;
	}

}
