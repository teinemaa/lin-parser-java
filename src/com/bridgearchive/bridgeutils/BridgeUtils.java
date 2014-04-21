package com.bridgearchive.bridgeutils;

import java.util.List;

import com.bridgearchive.bridgedata.Card;
import com.bridgearchive.bridgedata.Strain;
import com.bridgearchive.bridgedata.Suit;

public class BridgeUtils {
	private static final int[] IMP_RANGES = { 20, 50, 90, 130, 170, 220, 270, 320, 370, 430, 500, 600, 750, 900, 1100, 1300, 1500, 1750, 2000, 2250, 2500,
			3000, 3500, 4000 };

	public static int calculateImps(double score) {
		double absScore = Math.abs(score);
		int imps = 0;
		for (int impRange : IMP_RANGES) {
			if (absScore >= impRange - 5) {
				imps++;
			} else {
				break;
			}
		}
		if (score < 0) {
			imps = -imps;
		}
		return imps;
	}

	public static int calculateTricks(List<Card> cardPlays, Strain trump) {
		boolean declearerTurn = false;
		int tricksCount = 0;
		for (int i = 0; i < 13; i++) {
			List<Card> trick = cardPlays.subList(4 * i, 4 * (i + 1));
			Card winningCard = findTrickWinningCard(trick, trump);
			boolean changeTurn;
			if (trick.get(1) == winningCard || trick.get(3) == winningCard) {
				changeTurn = true;
			} else {
				changeTurn = false;
			}
			if (changeTurn) {
				declearerTurn = !declearerTurn;
			}
			if (declearerTurn) {
				tricksCount++;
			}
		}
		return tricksCount;
	}

	private static Card findTrickWinningCard(List<Card> cards, Strain trump) {
		Card best = cards.get(0);
		for (Card card : cards) {
			best = isSecondCardWinning(best, card, trump);
		}
		return best;
	}

	private static Card isSecondCardWinning(Card first, Card second, Strain trump) {
		if (trump != Strain.NO_TRUMP) {
			Suit trumpSuit = Suit.valueOf(trump);
			if (second.suit() == trumpSuit && first.suit() != trumpSuit) {
				return second;
			}
		}
		if (first.suit() != second.suit()) {
			return first;
		}
		if (second.rank().compareTo(first.rank()) > 0) {
			return second;
		} else {
			return first;
		}
	}
}
