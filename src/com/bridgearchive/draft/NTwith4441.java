package com.bridgearchive.draft;

import java.util.Set;

import com.bridgearchive.model.Call;
import com.bridgearchive.model.Result;
import com.bridgearchive.model.Strain;
import com.bridgearchive.model.filters.HcpComponent;
import com.bridgearchive.model.filters.OpenerFilter;
import com.bridgearchive.model.filters.OpeningFilter;
import com.bridgearchive.model.filters.RemoveDuplicates;
import com.bridgearchive.model.filters.SuitComponent;

public class NTwith4441 extends Calculator {

	@Override
	public void calculate(Set<Result> results) {
		calculate(results, true, true);

	}

	public void calculate(Set<Result> results, boolean minor, boolean strong) {
		results = new OpenerFilter(results)
				.addComponent(new HcpComponent().setHcp(11, 12))
				.addComponent(new SuitComponent().setLength(4))
				.addComponent(new SuitComponent().setLength(3))
				.addComponent(new SuitComponent().setMajors().setLength(5))
				.addComponent(new SuitComponent().setLength(1).setHcp(0))
				.getRemaining();
		results = new OpeningFilter(results).setBid(Call.valueOf(1, Strain.HEARTH), Call.valueOf(1, Strain.SPADE)).getRemaining();
		results = new OpenerFilter(results)
				.addComponent(new HcpComponent().setHcp(11, 12))
				.addComponent(new SuitComponent().setLength(4))
				.addComponent(new SuitComponent().setLength(3))
				.addComponent(new SuitComponent().setMajors().setLength(5))
				.addComponent(new SuitComponent().setLength(1).setHcp(0))
				.getRemaining();

		results = new RemoveDuplicates(results).getRemaining();

		DebuggerUtlis.printResults(results, "4major");

	}
}
