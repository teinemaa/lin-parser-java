package com.bridgearchive.draft;

import java.util.Set;

import com.bridgearchive.model.Result;

public class All extends Calculator {

	@Override
	public void calculate(Set<Result> results) {
		DebuggerUtlis.printResults(results, "all");

	}

}
