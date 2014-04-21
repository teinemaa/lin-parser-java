package com.bridgearchive.statistics;

import java.util.Set;

import com.bridgearchive.bridgedata.Call;
import com.bridgearchive.bridgedata.Result;
import com.bridgearchive.bridgedata.Strain;
import com.bridgearchive.bridgedata.filter.HcpComponent;
import com.bridgearchive.bridgedata.filter.OpenerFilter;
import com.bridgearchive.bridgedata.filter.OpeningFilter;
import com.bridgearchive.bridgedata.filter.RemoveDuplicates;

public class OpenLight extends Calculator {

	public void calculate(Set<Result> results, int pos, int minHcp, int maxHcp) {
		results = new OpenerFilter(results)
				.addComponent(new HcpComponent().setHcp(minHcp, maxHcp))
				.getRemaining();
		results = new OpeningFilter(results).setPosition(pos, pos)
				.setBid(Call.valueOf(1, Strain.CLUB), Call.valueOf(1, Strain.NO_TRUMP))
				.getRemaining();
		results = new RemoveDuplicates(results).getRemaining();

		DebuggerUtlis.printResults(results, "light_opening_pos" + pos + "_hcp" + minHcp + "-" + maxHcp);

	}

	@Override
	public void calculate(Set<Result> results) {
		calculate(results, 3, 7, 10);
	}
}
