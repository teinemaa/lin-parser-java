package com.bridgearchive.bridgedata.filter;

import java.util.Set;

import com.bridgearchive.bridgedata.Card;
import com.bridgearchive.bridgedata.Position;
import com.bridgearchive.bridgedata.Result;

public class ResponderFilter extends CardFilter {

	public ResponderFilter(Set<Result> results) {
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
		Position responder = result.getBidding().getOpener().getNext().getNext();
		return result.getHand(responder).getCards();
	}

}
