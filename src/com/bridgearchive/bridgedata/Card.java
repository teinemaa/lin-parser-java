package com.bridgearchive.bridgedata;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Card {

	private static final Map<Suit, Map<Rank, Card>> CARDS =
			new EnumMap<Suit, Map<Rank, Card>>(Suit.class);
	static {
		for (Suit suit : Suit.values()) {
			Map<Rank, Card> suitTable = new EnumMap<Rank, Card>(Rank.class);
			for (Rank rank : Rank.values())
				suitTable.put(rank, new Card(rank, suit));
			CARDS.put(suit, suitTable);
		}
	}

	public static Card valueOf(Rank rank, Suit suit) {
		return CARDS.get(suit).get(rank);
	}

	private static final Set<Card> PROTO_DECK = new HashSet<Card>();
	static {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				PROTO_DECK.add(Card.valueOf(rank, suit));
			}
		}
	}

	private final Rank rank;
	private final Suit suit;

	private Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Rank rank() {
		return rank;
	}

	public Suit suit() {
		return suit;
	}

	@Override
	public String toString() {
		return "" + suit.getOneLetterName() + rank.getOneLetterName();
	}

	public static Set<Card> newDeck() {
		return new HashSet<Card>(PROTO_DECK);
	}

	public int getHcp() {
		return rank.getHcp();
	}

	public int getControls() {
		return rank.getControls();
	}

}
