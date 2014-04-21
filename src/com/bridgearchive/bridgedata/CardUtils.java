package com.bridgearchive.bridgedata;

import java.util.HashSet;
import java.util.Set;

public class CardUtils {

	public static int getHcp(Set<Card> cards) {
		int toReturn = 0;
		for (Card card : cards) {
			toReturn += card.getHcp();
		}
		return toReturn;
	}

	public static int getControls(Set<Card> cards) {
		int toReturn = 0;
		for (Card card : cards) {
			toReturn += card.getControls();
		}
		return toReturn;
	}

	public static Set<Card> getSuit(Set<Card> cards, Suit suit) {
		Set<Card> toReturn = new HashSet<Card>();
		for (Card card : cards) {
			if (card.suit() == suit) {
				toReturn.add(card);
			}
		}
		return toReturn;
	}
}
