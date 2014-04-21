package com.bridgearchive.statistics;

import java.util.Set;

import com.bridgearchive.bridgedata.Call;
import com.bridgearchive.bridgedata.Result;
import com.bridgearchive.bridgedata.Strain;
import com.bridgearchive.bridgedata.Suit;
import com.bridgearchive.bridgedata.filter.HcpComponent;
import com.bridgearchive.bridgedata.filter.OpenerFilter;
import com.bridgearchive.bridgedata.filter.OpeningFilter;
import com.bridgearchive.bridgedata.filter.RemoveDuplicates;
import com.bridgearchive.bridgedata.filter.SuitComponent;

public class StrongNT extends Calculator {

	@Override
	public void calculate(Set<Result> results) {
		results = new OpenerFilter(results)
				.addComponent(new HcpComponent().setHcp(20, 21))
				.addComponent(new SuitComponent().setSuit(Suit.SPADE).setLength(2, 5))
				.addComponent(new SuitComponent().setSuit(Suit.HEARTH).setLength(2, 5))
				.addComponent(new SuitComponent().setSuit(Suit.DIAMOND).setLength(2, 6))
				.addComponent(new SuitComponent().setSuit(Suit.CLUB).setLength(2, 6))
				.getRemaining();
		results = new OpeningFilter(results).setBid(Call.valueOf(2, Strain.NO_TRUMP)).getRemaining();
		results = new RemoveDuplicates(results).getRemaining();

		DebuggerUtlis.printResults(results, "strong2NT");

	}
}
