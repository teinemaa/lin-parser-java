package com.bridgearchive.parser.lin.tag;

import com.bridgearchive.bridgedata.Call;
import com.bridgearchive.bridgedata.Stake;
import com.bridgearchive.bridgedata.Strain;
import com.bridgearchive.parser.lin.exception.ParseException;

public class MbTag extends Tag {

	private final Call call;

	public MbTag(String value) throws ParseException {
		super(value);
		int length;
		boolean bid = Character.isDigit(value.charAt(0));
		if (bid) {
			length = 2;
			int level = Integer.parseInt(value.substring(0, 1));
			if (level < 1 || level > 7) {
				throw new ParseException("mb tag", "unknown level " + value);

			}
			Strain strain;
			switch (Character.toUpperCase(value.charAt(1))) {
			case 'C':
				strain = Strain.CLUB;
				break;
			case 'D':
				strain = Strain.DIAMOND;
				break;
			case 'H':
				strain = Strain.HEARTH;
				break;
			case 'S':
				strain = Strain.SPADE;
				break;
			case 'N':
				strain = Strain.NO_TRUMP;
				break;

			default:
				throw new ParseException("mb tag", "unknown strain " + value);
			}

			call = Call.valueOf(level, strain);
		} else {
			length = 1;
			Stake stake;
			switch (Character.toLowerCase(value.charAt(0))) {
			case 'p':
				stake = Stake.PASS;
				break;
			case 'd':
				stake = Stake.DOUBLE;
				break;
			case 'r':
				stake = Stake.REDOUBLE;
				break;

			default:
				throw new ParseException("mb tag", "unknown stake " + value);
			}
			call = Call.valueOf(stake);
		}
		if (length != value.length()) {
			if (!(length + 1 == value.length() && value.charAt(length) == '!')) {

				throw new ParseException("mb tag", "unknown length " + value);
			}
		}

	}

	public Call getCall() {
		return call;
	}

}
