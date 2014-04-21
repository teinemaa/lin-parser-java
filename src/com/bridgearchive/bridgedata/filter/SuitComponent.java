package com.bridgearchive.bridgedata.filter;

import java.util.Set;

import com.bridgearchive.bridgedata.Card;
import com.bridgearchive.bridgedata.CardUtils;
import com.bridgearchive.bridgedata.Suit;

public class SuitComponent implements CardComponent {

	private Suit minSuit = Suit.CLUB;
	private Suit maxSuit = Suit.SPADE;

	private int minLenght = 0;
	private int maxLenght = 13;

	private int minHcp = 0;
	private int maxHcp = 10;

	private int minControls = 0;
	private int maxControls = 3;

	@Override
	public boolean isRemaining(Set<Card> input) {
		for (Suit suit : Suit.values()) {
			if (suit.ordinal() >= minSuit.ordinal() && suit.ordinal() <= maxSuit.ordinal()) {
				Set<Card> suitCards = CardUtils.getSuit(input, suit);
				int length = suitCards.size();
				if (length >= minLenght && length <= maxLenght) {
					int hcp = CardUtils.getHcp(suitCards);
					if (hcp >= minHcp && hcp <= maxHcp) {
						int controls = CardUtils.getControls(suitCards);
						if (controls >= minControls && controls <= maxControls) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public SuitComponent setLength(int minLenght, int maxLenght) {
		this.minLenght = minLenght;
		this.maxLenght = maxLenght;
		return this;
	}

	public SuitComponent setLength(int lenght) {
		return setLength(lenght, lenght);
	}

	public SuitComponent setHcp(int minHcp, int maxHcp) {
		this.minHcp = minHcp;
		this.maxHcp = maxHcp;
		return this;
	}

	public SuitComponent setControls(int minControls, int maxControls) {
		this.minControls = minControls;
		this.maxControls = maxControls;
		return this;
	}

	public SuitComponent setControls(int controls) {
		return setControls(controls, controls);
	}

	public SuitComponent setHcp(int hcp) {
		return setHcp(hcp, hcp);
	}

	public SuitComponent setSuit(Suit minSuit, Suit maxSuit) {
		this.minSuit = minSuit;
		this.maxSuit = maxSuit;
		return this;
	}

	public SuitComponent setSuit(Suit suit) {
		return setSuit(suit, suit);
	}

	public SuitComponent setMinors() {
		return setSuit(Suit.CLUB, Suit.DIAMOND);
	}

	public SuitComponent setMajors() {
		return setSuit(Suit.HEARTH, Suit.SPADE);
	}

}
