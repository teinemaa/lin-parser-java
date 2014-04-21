package com.bridgearchive.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

public class Board {

	private static final boolean[] VULNERABLE_NS = { false, true, false, true, true, false, true, false, false, true, false, true, true, false, true, false };
	private static final boolean[] VULNERABLE_EW = { false, false, true, true, false, true, true, false, true, true, false, false, true, false, false, true };
	private static final Position[] DEALER = { Position.NORTH, Position.EAST, Position.SOUTH, Position.WEST };
	private final int boardNr;

	private final Map<Position, Hand> hands = new HashMap<Position, Hand>();

	public Board(Map<Position, Set<Card>> hands, int boardNr) {
		for (Position position : Position.values()) {
			this.hands.put(position, new Hand(hands.get(position)));
		}
		this.boardNr = boardNr;
	}

	public int getBoardNr() {
		return boardNr;
	}

	public Position getDealer() {
		int index = (boardNr - 1) % 4;
		return DEALER[index];
	}

	public boolean isVulnerable(Line line) {
		int index = (boardNr - 1) % 16;

		if (line.isNS()) {
			return VULNERABLE_NS[index];
		} else {
			return VULNERABLE_EW[index];
		}
	}

	private String getVulnerability() {
		boolean ns = isVulnerable(Line.NS);
		boolean ew = isVulnerable(Line.EW);
		if (!ns && !ew) {
			return "none";
		} else if (ns && !ew) {
			return "NS";
		} else if (!ns && ew) {
			return "EW";
		} else {
			return "both";
		}
	}

	public Hand getHand(Position position) {
		return hands.get(position);
	}

	@Override
	public String toString() {
		String toReturn = "";
		toReturn += boardNr + ".\t" + positionToString(Position.NORTH) + "\r\n";
		toReturn += "D:" + getDealer() + "\t" + suitToString(Position.NORTH, Suit.SPADE) + "\r\n";
		toReturn += "V:" + getVulnerability() + "\t" + suitToString(Position.NORTH, Suit.HEARTH) + "\r\n";
		toReturn += "\t" + suitToString(Position.NORTH, Suit.DIAMOND) + "\r\n";
		toReturn += positionToString(Position.WEST) + "\t" + suitToString(Position.NORTH, Suit.CLUB) + "\t" + positionToString(Position.EAST) + "\r\n";
		toReturn += suitToString(Position.WEST, Suit.SPADE) + "\t\t" + suitToString(Position.EAST, Suit.SPADE) + "\r\n";
		toReturn += suitToString(Position.WEST, Suit.HEARTH) + "\t\t" + suitToString(Position.EAST, Suit.HEARTH) + "\r\n";
		toReturn += suitToString(Position.WEST, Suit.DIAMOND) + "\t" + positionToString(Position.SOUTH) + "\t" + suitToString(Position.EAST, Suit.DIAMOND)
				+ "\r\n";
		toReturn += suitToString(Position.WEST, Suit.CLUB) + "\t" + suitToString(Position.SOUTH, Suit.SPADE) + "\t" + suitToString(Position.EAST, Suit.CLUB)
				+ "\r\n";
		toReturn += "\t" + suitToString(Position.SOUTH, Suit.HEARTH) + "\r\n";
		toReturn += "\t" + suitToString(Position.SOUTH, Suit.DIAMOND) + "\r\n";
		toReturn += "\t" + suitToString(Position.SOUTH, Suit.CLUB) + "\r\n";
		return toReturn;
	}

	private String suitToString(Position position, Suit suit) {
		Rank[] ranks = Rank.values();
		ArrayUtils.reverse(ranks);
		String toReturn = "";
		//toReturn += Character.toUpperCase(suit.getOneLetterName()) + " ";
		for (Rank rank : ranks) {
			Card card = Card.valueOf(rank, suit);
			if (getHand(position).getCards().contains(card)) {
				toReturn += rank.getOneLetterName();
			}
		}
		if (toReturn.isEmpty()) {
			toReturn = "-";
		}
		return toReturn;
	}

	private String positionToString(Position position) {
		return position.getOneLetterName() + " (" + getHand(position).getHCP() + ")";
	}
}
