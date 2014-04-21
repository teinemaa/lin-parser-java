package com.bridgearchive.draft;

import java.util.Set;

import com.bridgearchive.model.Call;
import com.bridgearchive.model.Result;
import com.bridgearchive.model.Strain;
import com.bridgearchive.model.Suit;
import com.bridgearchive.model.filters.HcpComponent;
import com.bridgearchive.model.filters.OpenerFilter;
import com.bridgearchive.model.filters.OpeningFilter;
import com.bridgearchive.model.filters.RemoveDuplicates;
import com.bridgearchive.model.filters.SuitComponent;

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
