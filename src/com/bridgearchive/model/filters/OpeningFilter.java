package com.bridgearchive.model.filters;

import java.util.Set;

import com.bridgearchive.model.Call;
import com.bridgearchive.model.Position;
import com.bridgearchive.model.Result;

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
