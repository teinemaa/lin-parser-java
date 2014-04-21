package com.bridgearchive.statistics;

import java.util.Set;

import com.bridgearchive.bridgedata.Call;
import com.bridgearchive.bridgedata.Result;
import com.bridgearchive.bridgedata.Strain;
import com.bridgearchive.bridgedata.filter.HcpComponent;
import com.bridgearchive.bridgedata.filter.OpenerFilter;
import com.bridgearchive.bridgedata.filter.OpeningFilter;
import com.bridgearchive.bridgedata.filter.RemoveDuplicates;
import com.bridgearchive.bridgedata.filter.SuitComponent;

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
