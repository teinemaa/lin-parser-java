package com.bridgearchive.model;

public enum Rank {
	DEUCE, THREE, FOUR, FIVE, SIX,
	SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

	public char getOneLetterName() {

		switch (this) {
		default:
		case DEUCE:
			return '2';
		case THREE:
			return '3';
		case FOUR:
			return '4';
		case FIVE:
			return '5';
		case SIX:
			return '6';
		case SEVEN:
			return '7';
		case EIGHT:
			return '8';
		case NINE:
			return '9';
		case TEN:
			return 'T';
		case JACK:
			return 'J';
		case QUEEN:
			return 'Q';
		case KING:
			return 'K';
		case ACE:
			return 'A';

		}
	}

	public static Rank valueOf(char name) {
		name = Character.toUpperCase(name);
		for (Rank rank : Rank.values()) {
			if (rank.getOneLetterName() == name) {
				return rank;
			}
		}
		return null;
	}

	public int getHcp() {
		switch (this) {
		case ACE:
			return 4;
		case KING:
			return 3;
		case QUEEN:
			return 2;
		case JACK:
			return 1;
		default:
			return 0;
		}
	}

	public int getControls() {
		switch (this) {
		case ACE:
			return 2;
		case KING:
			return 1;
		default:
			return 0;
		}
	}

}
