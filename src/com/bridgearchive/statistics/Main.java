package com.bridgearchive.statistics;

import java.util.Set;

import com.bridgearchive.bridgedata.Result;
import com.bridgearchive.parser.lin.DealReader;

public class Main {

	public static void main(String[] args) {
		Set<Result> results = DealReader.readAllDeals();
		Calculator calculator = new LightPreemt();

		calculator.calculate(results);
	}
}
