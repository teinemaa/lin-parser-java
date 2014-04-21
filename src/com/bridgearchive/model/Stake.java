package com.bridgearchive.model;

public enum Stake {

	PASS,
	DOUBLE,
	REDOUBLE;

	public int getStakeMultiplier() {
		switch (this) {
		default:
		case PASS:
			return 1;
		case DOUBLE:
			return 2;
		case REDOUBLE:
			return 4;
		}
	}

	public int getStakeMakingBonus() {
		switch (this) {
		default:
		case PASS:
			return 0;
		case DOUBLE:
			return 50;
		case REDOUBLE:
			return 100;
		}
	}

	public boolean isPass() {
		return this == PASS;
	}

	public String getExtension() {
		switch (this) {
		default:
		case PASS:
			return "";
		case DOUBLE:
			return "x";
		case REDOUBLE:
			return "xx";
		}
	}

	@Override
	public String toString() {
		if (this == REDOUBLE) {
			return "rdbl";
		} else {

			return super.toString().toLowerCase();
		}
	}
}
