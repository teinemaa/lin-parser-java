package com.bridgearchive.model;

import java.util.ArrayList;
import java.util.Collection;

public enum Line {

	NS,
	EW;

	//	private static final Position[] NS_POSITIONS = { Position.NORTH, Position.SOUTH };
	//	private static final Position[] EW_POSITIONS = { Position.EAST, Position.WEST };

	public boolean isNS() {
		return this == NS;
	}

	public Collection<Position> getPositions() {
		Collection<Position> positions = new ArrayList<Position>();
		for (Position position : Position.values()) {
			if (position.getLine() == this) {
				positions.add(position);
			}
		}
		return positions;
	}

	public boolean isPosition(Position position) {
		return position.isLine(this);
	}

	public Line getOpposite() {
		if (this == NS) {
			return EW;
		} else {
			return NS;
		}
	}

}
