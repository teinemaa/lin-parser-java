package com.bridgearchive.statistics;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Summary implements Comparable<Summary> {

	public final long n;

	public final double mean;

	public final double tStatistic;

	public final double percentage;

	public final String description;

	public Summary() {
		this(0, 0, 0, 0, "");
	}

	public Summary(DescriptiveStatistics stats, double tStatistic, double percentage, String description) {
		this(stats.getN(), stats.getMean(), tStatistic, percentage, description);
	}

	public Summary(long n, double mean, double tStatistic, double percentage, String description) {
		this.n = n;
		this.mean = mean;
		this.tStatistic = tStatistic;
		this.percentage = percentage;
		this.description = description;

	}

	@Override
	public int compareTo(Summary o) {
		if (tStatistic == o.tStatistic)
			return 0;
		return tStatistic < o.tStatistic ? -1 : 1;
	}

	@Override
	public String toString() {
		return DebuggerUtlis.formatDouble(tStatistic) + "\t" + DebuggerUtlis.formatPercentage(percentage) + "\t" + n + "\t" + DebuggerUtlis.formatIMPs(mean)
				+ "\t" + description;
	}

	public static String getLabel() {
		return "T-stat\tBetter\tCount\tIMP\tCondition";
	}
}
