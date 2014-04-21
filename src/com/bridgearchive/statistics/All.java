package com.bridgearchive.statistics;

import java.util.Set;

import com.bridgearchive.bridgedata.Result;

public class All extends Calculator {

	@Override
	public void calculate(Set<Result> results) {
		DebuggerUtlis.printResults(results, "all");

	}

}
