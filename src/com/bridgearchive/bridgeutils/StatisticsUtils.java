package com.bridgearchive.bridgeutils;

import java.util.Set;

import com.bridgearchive.bridgedata.Result;

public class StatisticsUtils {

	public static double getImpsPerBoard(Set<Result> results) {
		return 1.0 * getImps(results) / results.size();
	}

	public static int getImps(Set<Result> results) {
		int imps = 0;
		for (Result result : results) {
			imps += result.getImps();
		}
		return imps;
	}

}
