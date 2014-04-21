package com.bridgearchive.parser.lin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bridgearchive.bridgedata.Deal;
import com.bridgearchive.parser.lin.exception.ParseException;
import com.bridgearchive.parser.lin.tag.Tag;

public class LinParser {

	private int ignoredDeals;

	public Collection<Deal> parseLinToBridgeData(String linString, int linNr)
			throws ParseException {
		linString = linString.replaceAll("\\p{Cc}", "");

		if (linString.startsWith("% PBN 1.0")) {
			throw new UnsupportedFileFormatException("PBN");
		}

		if (linString.startsWith("Fetch failed (2)")) {
			throw new ParseException("invalid bbo error", "Fetch failed (2)");
		}

		List<Tag> tags = readTags(linString);

		if (tags.isEmpty()) {
			System.out.println("------");
			System.out.println(linString);
			throw new RuntimeException("Network error.");
		}

		LinStructure lin = new LinStructure(tags);

		lin.getRsTag();

		int dealsCount = 2 * (lin.getVgTag().getLastBoard() - lin.getVgTag().getFirstBoard() + 1);
		int resultsCount = lin.getRsTag().getResultsCount();
		if (resultsCount == dealsCount + 1) {
			//throw new ParseException("invalid one result more than expected", "expected=" + dealsCount + " actual=" + resultsCount);
		}
		if (resultsCount < dealsCount) {
			//throw new ParseException("invalid less results than expected", "expected=" + dealsCount + " actual=" + resultsCount);
		}
		if (resultsCount > dealsCount) {
			//throw new ParseException("invalid more results than expected", "expected=" + dealsCount + " actual=" + resultsCount);
		}
		DealBuilder dealBuilder = new DealBuilder(tags, linNr);
		Collection<Deal> deals = dealBuilder.buildDeals();
		ignoredDeals = dealBuilder.getIgnoredDeals();

		return deals;
	}

	public int getIgnoredDeals() {
		return ignoredDeals;
	}

	private List<Tag> readTags(String lin)
			throws ParseException {
		int separators = StringUtils.countMatches(lin, "|");
		if (separators % 2 != 0) {
			throw new ParseException("invalid nr of separators", separators + "");
		}

		List<Tag> tags = new ArrayList<Tag>();

		String[] entries = lin.split("\\|", -1);
		for (int j = 0; j < entries.length / 2; j++) {
			tags.add(TagFactory.createTag(entries[2 * j], entries[2 * j + 1]));
		}
		return tags;
	}

}
