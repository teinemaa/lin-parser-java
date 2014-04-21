package com.bridgearchive.parser.lin.tag;

import com.bridgearchive.bridgedata.Card;
import com.bridgearchive.bridgedata.Rank;
import com.bridgearchive.bridgedata.Suit;
import com.bridgearchive.parser.lin.exception.ParseException;

public class PcTag extends Tag {

	private final Card card;

	public PcTag(String value) throws ParseException {
		super(value);
		if (value.length() != 2) {
			throw new ParseException("pc tag", "invalid card " + value);
		}
		Rank rank = Rank.valueOf(value.charAt(1));
		Suit suit = Suit.valueOf(value.charAt(0));
		if (rank == null || suit == null) {
			throw new ParseException("pc tag", "invalid card " + value);
		}
		card = Card.valueOf(rank, suit);
	}

	public Card getCard() {
		return card;
	}

}
