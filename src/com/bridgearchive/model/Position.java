package com.bridgearchive.model;

public enum Position {
	NORTH,
	SOUTH,
	EAST,
	WEST;

	public Line getLine() {
		if (this == NORTH || this == SOUTH) {
			return Line.NS;
		} else {
			return Line.EW;
		}

	}

	public boolean isNS() {
		return getLine() == Line.NS;
	}

	public Position getNext() {
		switch (this) {
		default:
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		}
	}

	public char getOneLetterName() {
		return this.toString().charAt(0);
	}

	public Position getShifted(int shift) {
		if (shift < 0) {
			throw new IllegalArgumentException("shift must be positive");
		}
		Position shifted = this;
		for (int i = 0; i < shift; i++) {
			shifted = shifted.getNext();
		}
		return shifted;
	}

	public boolean isLine(Line line) {
		if (this.getLine() == line) {
			return true;
		} else {
			return false;

		}
	}
}
