package com.bridgearchive.model.filters;

import java.util.HashSet;
import java.util.Set;

import com.bridgearchive.model.Card;
import com.bridgearchive.model.Position;
import com.bridgearchive.model.Result;

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
