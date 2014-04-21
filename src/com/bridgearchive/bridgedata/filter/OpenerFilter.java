package com.bridgearchive.bridgedata.filter;

import java.util.Set;

import com.bridgearchive.bridgedata.Card;
import com.bridgearchive.bridgedata.Position;
import com.bridgearchive.bridgedata.Result;

public class OpenerFilter extends CardFilter {

	public OpenerFilter(Set<Result> results) {
		super(results);
	}

	@Override
	protected boolean isRemaining(Result result) {
		Position opener = result.getBidding().getOpener();
		if (opener == null) {
			return false;
		}
		if (result.getLine().isPosition(opener)) {
			return super.isRemaining(result);
		} else {
			return false;
		}
	}

	@Override
	protected Set<Card> getCards(Result result) {
		Position opener = result.getBidding().getOpener();
		return result.getHand(opener).getCards();
	}

}
