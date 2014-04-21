package com.bridgearchive.draft;

import java.util.Set;

import com.bridgearchive.loader.DealReader;
import com.bridgearchive.model.Result;

public class Main {

	public static void main(String[] args) {
		Set<Result> results = DealReader.readAllDeals();
		Calculator calculator = new OpeningWith5M();
		calculator.calculate(results);
	}
}
