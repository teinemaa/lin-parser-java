package com.bridgearchive.draft;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.StatisticalSummary;
import org.apache.commons.math3.stat.inference.TTest;

import com.bridgearchive.loader.DealReader;
import com.bridgearchive.model.Result;

public class DebuggerUtlis {

	static final File ANALYSIS_DIRECTORY = new File(DealReader.ROOT_DIRECTORY, "analysis");
	private static double s1 = 0.682689492137086;
	private static double s2 = 0.954499736103642;
	private static double s3 = 0.997300203936740;

	private static final String separator = "========================================";

	public static void printResults(Set<Result> results, String analysisName) {
		printResults(results, analysisName, "", "focus", "other table", "");

	}

	private static double getConfidenceIntervalWidth(StatisticalSummary summaryStatistics, double significance) {
		TDistribution tDist = new TDistribution(summaryStatistics.getN() - 1);
		double a = tDist.inverseCumulativeProbability(1.0 - significance / 2);
		return a * summaryStatistics.getStandardDeviation() / Math.sqrt(summaryStatistics.getN());
	}

	public static String formatIMPs(double imps) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(2);
		return (imps > 0 ? "+" : "") + format.format(imps);
	}

	public static Summary printResults(Set<Result> results, String analysisName, String description, String focusDescription, String otherTableDescription,
			String situation) {
		try {
			if (results.size() < 3) {
				return new Summary();
			}

			StringBuilder info = new StringBuilder();

			DescriptiveStatistics stats = new DescriptiveStatistics();
			Frequency frequency = new Frequency();
			for (Result result : results) {
				stats.addValue(result.getImps());
				frequency.addValue(result.getImps());
			}
			TTest tTest = new TTest();
			double pValue;
			double tStatistic;
			try {

				pValue = tTest.tTest(0, stats);
				tStatistic = tTest.t(0, stats);
			} catch (NumberIsTooSmallException e) {
				pValue = 1;
				tStatistic = 0;
			}

			double effectiveMean = Math.abs(stats.getMean());

			double halfwidth;
			halfwidth = getConfidenceIntervalWidth(stats, 1 - s1);
			double s1_1 = effectiveMean - halfwidth;
			double s1_2 = effectiveMean + halfwidth;
			halfwidth = getConfidenceIntervalWidth(stats, 1 - s2);
			double s2_1 = effectiveMean - halfwidth;
			double s2_2 = effectiveMean + halfwidth;
			halfwidth = getConfidenceIntervalWidth(stats, 1 - s3);
			double s3_1 = effectiveMean - halfwidth;
			double s3_2 = effectiveMean + halfwidth;
			String effective = tStatistic > 0 ? focusDescription : otherTableDescription;
			String ineffective = tStatistic > 0 ? otherTableDescription : focusDescription;
			double prob = pValue / 2;
			double probEffective = (1 - prob);
			double probIneffective = prob;
			int boundry = (int) Math.round(Math.max(Math.abs(stats.getMin()), Math.abs(stats.getMax())));

			info.append(separator + "\r\n");
			info.append("CONDITION" + "\r\n");
			info.append(separator + "\r\n");
			info.append("\r\n");
			info.append(description + "\r\n");
			info.append("\r\n");
			info.append("All boards are from IMP teams tournaments vugraphed by Bridge Base Online.\r\n");
			info.append("\r\n");

			info.append(separator + "\r\n");
			info.append("SUMMARY" + "\r\n");
			info.append(separator + "\r\n");
			info.append("\r\n");
			info.append(stats.getN() + " boards matched the condition.\r\n");
			info.append("For these boards, it was " + formatIMPs(effectiveMean) + " IMP per board in favor to " + effective + ".\r\n");
			info.append("\r\n");
			info.append("Using mathematical statistics (t-test):\r\n");
			info.append("It is " + formatPercentage(probEffective) + " sure that " + effective + " is better than " + ineffective + " with " + situation
					+ ".\r\n");
			info.append("Chance for this being coincidence is " + formatPercentage(probIneffective) + ".\r\n");
			info.append("\r\n");
			info.append("It is " + formatPercentage(s1) + " sure that " + effective + " results between " + formatIMPs(s1_1) + " and " + formatIMPs(s1_2)
					+ " IMP per board in the long run. (1-sigma)\r\n");
			info.append("It is " + formatPercentage(s2) + " sure that " + effective + " results between " + formatIMPs(s2_1) + " and " + formatIMPs(s2_2)
					+ " IMP per board in the long run. (2-sigma)\r\n");
			info.append("It is " + formatPercentage(s3) + " sure that " + effective + " results between " + formatIMPs(s3_1) + " and " + formatIMPs(s3_2)
					+ " IMP per board in the long run. (3-sigma)\r\n");

			info.append("\r\n");
			info.append("Distribution of IMPs from the 1NT opening point of view:" + "\r\n");
			info.append("IMPs\t");
			for (int i = -boundry; i <= boundry; i++) {
				info.append(formatIMPs(i) + "\t");
			}
			info.append("\r\n");
			info.append("Count\t");
			for (int i = -boundry; i <= boundry; i++) {
				info.append(frequency.getCount(i) + "\t");
			}
			info.append("\r\n");

			info.append("\r\n");
			info.append(stats.toString().replaceAll("\n", "\r\n"));
			info.append("p-value: " + pValue + "\r\n");
			info.append("t statistic: " + tStatistic + "\r\n" + "\r\n");
			info.append(separator + "\r\n");
			info.append("MATCHED BOARDS" + "\r\n");
			info.append(separator + "\r\n");
			info.append("\r\n");
			File analysisSubdirectory = new File(ANALYSIS_DIRECTORY, situation);
			File analysisFile = new File(analysisSubdirectory, analysisName + ".txt");
			FileUtils.writeStringToFile(analysisFile, info.toString());

			List<Result> orderedResults = new ArrayList<Result>(results);
			Collections.sort(orderedResults);
			for (Result result : orderedResults) {
				FileUtils.writeStringToFile(analysisFile, result.toString(focusDescription, otherTableDescription) + "\r\n", true);

			}
			return new Summary(stats, tStatistic, tStatistic > 0 ? probEffective : probIneffective, analysisName);
		} catch (IOException e) {
			e.printStackTrace();
			return new Summary();
		}

	}

	public static String formatDouble(double d) {
		NumberFormat defaultFormat = NumberFormat.getNumberInstance();
		defaultFormat.setMinimumFractionDigits(2);
		return defaultFormat.format(d);
	}

	public static String formatPercentage(double percentage) {
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(2);
		return defaultFormat.format(percentage);
	}
}
