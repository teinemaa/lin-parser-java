package com.bridgearchive.bridgedata.filter;

import java.util.HashSet;
import java.util.Set;

import com.bridgearchive.bridgedata.Card;
import com.bridgearchive.bridgedata.Position;
import com.bridgearchive.bridgedata.Result;

public class LineFilter extends CardFilter {

	public LineFilter(Set<Result> results) {
		super(results);
	}

	@Override
	protected Set<Card> getCards(Result result) {
		Set<Card> toReturn = new HashSet<Card>();
		for (Position position : result.getLine().getPositions()) {
			toReturn.addAll(result.getHand(position).getCards());
		}
		return toReturn;
	}
}
