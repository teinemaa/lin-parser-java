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

public class Gambling extends Calculator {

	@Override
	public void calculate(Set<Result> results) {
		Set<Result> strict = strict(results);
		Set<Result> both = offshape(results);
		Set<Result> offshape = offshape(results);
		offshape.removeAll(strict);

		DebuggerUtlis.printResults(strict, "gambling_strict");
		DebuggerUtlis.printResults(offshape, "gambling_offshape");
		DebuggerUtlis.printResults(both, "gambling_both");

	}

	private Set<Result> strict(Set<Result> results) {
		results = new OpenerFilter(results)
				.addComponent(new SuitComponent().setLength(7, 7).setMinors().setHcp(9, 10))
				.addComponent(new HcpComponent().setControls(3, 3))
				.addComponent(new SuitComponent().setSuit(Suit.SPADE).setControls(0, 0))
				.addComponent(new SuitComponent().setSuit(Suit.HEARTH).setControls(0, 0))
				.addComponent(new SuitComponent().setMinors().setControls(0, 0))
				.getRemaining();
		results = new OpeningFilter(results).setBid(Call.valueOf(3, Strain.NO_TRUMP)).getRemaining();
		results = new RemoveDuplicates(results).getRemaining();
		return results;
	}

	private Set<Result> offshape(Set<Result> results) {
		results = new OpenerFilter(results)
				.addComponent(new SuitComponent().setLength(6, 8).setMinors().setHcp(7, 10))
				.addComponent(new HcpComponent().setControls(2, 4))
				.addComponent(new SuitComponent().setSuit(Suit.SPADE).setControls(0, 1))
				.addComponent(new SuitComponent().setSuit(Suit.HEARTH).setControls(0, 1))
				.addComponent(new SuitComponent().setMinors().setControls(0, 1))
				.getRemaining();
		results = new OpeningFilter(results).setBid(Call.valueOf(3, Strain.NO_TRUMP)).getRemaining();
		results = new RemoveDuplicates(results).getRemaining();
		return results;
	}

}
