package com.bridgearchive.parser.lin.tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.bridgearchive.model.Card;
import com.bridgearchive.model.Position;
import com.bridgearchive.model.Rank;
import com.bridgearchive.model.Suit;
import com.bridgearchive.parser.lin.exception.ParseException;

public class MdTag extends Tag {

	private Position dealer;
	private Map<Position, Set<Card>> hands;

	public MdTag(String value) throws ParseException {
		super(value);
		if (value.isEmpty()) {

			throw new ParseException("md tag", "empty");
		}
		if (!Character.isDigit(value.charAt(0))) {

			throw new ParseException("md tag", "first char not dealer digit " + value);
		}
		int dealerPositionFromSouth = Integer.parseInt(value.substring(0, 1));
		dealer = Position.SOUTH.getShifted(dealerPositionFromSouth - 1);
		Set<Card> deck = Card.newDeck();
		hands = new HashMap<Position, Set<Card>>();
		for (Position position : Position.values()) {
			hands.put(position, new HashSet<Card>());
		}
		Position currentPosition = Position.SOUTH;
		Suit currentSuit = null;
		for (int i = 1; i < value.length(); i++) {
			char c = Character.toUpperCase(value.charAt(i));
			switch (c) {
			case ',':
				currentPosition = currentPosition.getNext();
				break;
			case 'C':
			case 'D':
			case 'H':
			case 'S':
				currentSuit = Suit.valueOf(c);
				break;
			default:
				Rank rank = Rank.valueOf(c);
				if (rank == null || currentSuit == null) {

					throw new ParseException("md tag", "unknown char " + c + " " + value);
				}
				Card card = Card.valueOf(rank, currentSuit);
				if (deck.contains(card)) {
					hands.get(currentPosition).add(card);
					deck.remove(card);
				} else {
					throw new ParseException("md tag", "card " + card + " appears twice " + value);
				}
				break;
			}
		}
		hands.get(Position.EAST).addAll(deck);
		for (Set<Card> hand : hands.values()) {
			if (hand.size() != 13) {
				throw new ParseException("md tag", "hand doesn't contain 13 cards " + value);

			}
		}

	}

	public Position getDealer() {
		return dealer;
	}

	public Map<Position, Set<Card>> getHands() {
		return hands;
	}
}
