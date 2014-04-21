package com.bridgearchive.bridgedata.filter;

import java.util.Set;

import com.bridgearchive.bridgedata.Call;
import com.bridgearchive.bridgedata.Result;
import com.bridgearchive.bridgedata.Stake;
import com.bridgearchive.bridgedata.Strain;

public abstract class BiddingFilter extends Filter {

	private Call minCall = Call.valueOf(Stake.PASS);

	private Call maxCall = Call.valueOf(7, Strain.NO_TRUMP);

	public BiddingFilter(Set<Result> results) {
		super(results);
	}

	public BiddingFilter setBid(Call call) {
		minCall = call;
		maxCall = call;
		return this;
	}

	public BiddingFilter setBid(Call minCall, Call maxCall) {
		this.minCall = minCall;
		this.maxCall = maxCall;
		return this;
	}

	@Override
	protected boolean isRemaining(Result result) {
		Call call = getCall(result);
		if (call.getValue() < minCall.getValue() || call.getValue() > maxCall.getValue()) {
			return false;
		} else {
			return true;
		}
	}

	protected abstract Call getCall(Result result);

}
