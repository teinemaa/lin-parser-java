package com.bridgearchive.model;

public enum Suit {
	CLUB,
	DIAMOND,
	HEARTH,
	SPADE;

	public char getOneLetterName() {
		return Character.toLowerCase(this.toString().charAt(0));
	}

	public static Suit valueOf(char name) {
		name = Character.toLowerCase(name);
		for (Suit suit : Suit.values()) {
			if (suit.getOneLetterName() == name) {
				return suit;
			}
		}
		return null;
	}

	public static Suit valueOf(Strain strain) {
		switch (strain) {
		case CLUB:
			return CLUB;
		case DIAMOND:
			return DIAMOND;
		case HEARTH:
			return HEARTH;
		case SPADE:
			return SPADE;
		default:
			return null;
		}
	}

}
