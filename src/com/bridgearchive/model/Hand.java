package com.bridgearchive.model;

import java.util.Set;

import com.bridgearchive.model.utils.CardUtils;

public class Hand {

	private final Set<Card> cards;

	public Hand(Set<Card> cards) {
		this.cards = cards;

	}

	public Set<Card> getCards() {
		return cards;
	}

	public int getHCP() {
		return CardUtils.getHcp(cards);
	}

}
