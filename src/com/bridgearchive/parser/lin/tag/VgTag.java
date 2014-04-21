package com.bridgearchive.parser.lin.tag;

import com.bridgearchive.parser.lin.exception.ParseException;
import com.bridgearchive.parser.lin.exception.VgTagException;

public class VgTag extends Tag {

	enum EventType {
		IMPS
	}

	private final String eventName;
	private final String segment;
	private final EventType eventType;
	private final int firstBoard;
	private final int lastBoard;
	private final String homeTeam;
	private final int homeTeamPreviousScore;
	private final String awayTeam;
	private final int awayTeamPreviousScore;

	public VgTag(String value) throws ParseException {
		super(value);
		String[] parts = value.split(",");
		eventName = parts[0];
		segment = parts[1];
		switch (parts[2]) {
		case "I":
			eventType = EventType.IMPS;
			break;
		case "P":
		case "B":

			throw new ParseException("unsupported event type", parts[2]);
		default:
			throw new ParseException("invalid event type", parts[2]);

		}
		try {

			firstBoard = Integer.parseInt(parts[3].trim());
			lastBoard = Integer.parseInt(parts[4].trim());
			homeTeam = parts[5];
			homeTeamPreviousScore = Integer.parseInt(parts[6].trim());
			awayTeam = parts[7];
			awayTeamPreviousScore = Integer.parseInt(parts[8].trim());
		} catch (NumberFormatException e) {
			throw new VgTagException(value);
		}
	}

	public int getFirstBoard() {
		return firstBoard;
	}

	public int getLastBoard() {
		return lastBoard;
	}

}
