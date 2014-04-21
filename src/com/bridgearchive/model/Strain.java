package com.bridgearchive.model;

public enum Strain {
	CLUB,
	DIAMOND,
	HEARTH,
	SPADE,
	NO_TRUMP;

	public int getFirstTrickBonus() {
		return this == NO_TRUMP ? 10 : 0;
	}

	public int getBaseTrickValue() {
		return this == CLUB || this == DIAMOND ? 20 : 30;
	}

	public char getOneLetterName() {
		return this.toString().charAt(0);
	}

	public String getShortName() {
		switch (this) {
		default:
		case CLUB:
			return "C";
		case DIAMOND:
			return "D";
		case HEARTH:
			return "H";
		case SPADE:
			return "S";
		case NO_TRUMP:
			return "NT";

		}
	}

	public static Strain valueOf(Suit suit) {
		switch (suit) {
		default:
		case CLUB:
			return CLUB;
		case DIAMOND:
			return DIAMOND;
		case HEARTH:
			return HEARTH;
		case SPADE:
			return SPADE;
		}
	}
}
