package com.bridgearchive.bridgedata.filter;

import java.util.Set;

import com.bridgearchive.bridgedata.Call;
import com.bridgearchive.bridgedata.Position;
import com.bridgearchive.bridgedata.Result;

public class OpeningFilter extends BiddingFilter {

	private int minPosition = 1;
	private int maxPosition = 4;

	public OpeningFilter(Set<Result> results) {
		super(results);
	}

	public OpeningFilter setPosition(int minPosition, int maxPosition) {
		this.minPosition = minPosition;
		this.maxPosition = maxPosition;
		return this;
	}

	public OpeningFilter setPosition(int position) {
		return setPosition(position, position);
	}

	@Override
	protected boolean isRemaining(Result result) {
		Position opener = result.getBidding().getOpener();
		if (opener != null) {
			if (result.getLine().isPosition(opener)) {
				return false;
			}
			Position currentPosition = result.getBoard().getDealer();
			int currentPositionIndex = 1;
			while (currentPosition != opener) {
				currentPosition = currentPosition.getNext();
				currentPositionIndex++;
			}
			if (currentPositionIndex >= minPosition && currentPositionIndex <= maxPosition) {

				return super.isRemaining(result);
			}
		}
		return false;
	}

	@Override
	protected Call getCall(Result result) {
		return result.getBidding().getOpening();
	}

}
