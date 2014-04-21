package com.bridgearchive.draft;

import java.util.Set;

import com.bridgearchive.model.Call;
import com.bridgearchive.model.Result;
import com.bridgearchive.model.Strain;
import com.bridgearchive.model.filters.HcpComponent;
import com.bridgearchive.model.filters.OpenerFilter;
import com.bridgearchive.model.filters.OpeningFilter;
import com.bridgearchive.model.filters.RemoveDuplicates;

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
