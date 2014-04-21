package com.bridgearchive.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.bridgearchive.model.Deal;
import com.bridgearchive.model.Line;
import com.bridgearchive.model.Result;
import com.bridgearchive.parser.lin.LinParser;
import com.bridgearchive.parser.lin.exception.ParseException;

public class DealReader {
	public static final File ROOT_DIRECTORY = new File(System.getProperty("user.dir"));

	public static Set<Result> readDeals(int start, int end) {
		try {
			int exceptionCount = 0;
			int ignoredDeals = 0;
			Map<String, Integer> exceptionCounts = new HashMap<String, Integer>();
			File exeptionsDirectory = new File(ROOT_DIRECTORY, "exceptions");
			FileUtils.deleteDirectory(exeptionsDirectory);
			File dealsDirectory = new File(ROOT_DIRECTORY, "deals");
			File lastLin = new File(dealsDirectory, "last_lin.txt");
			int lastLinIndex;
			if (lastLin.exists()) {
				lastLinIndex = Integer.parseInt(FileUtils.readFileToString(lastLin));
			} else {
				lastLinIndex = 0;
			}
			boolean parseMore = true;
			Set<Deal> allDeals = new HashSet<Deal>();
			while (parseMore) {
				File destination = new File(dealsDirectory, start + ".lin");
				if (!(destination.exists() && lastLinIndex >= start)) {
					URL source = new URL("http://www.bridgebase.com/tools/vugraph_linfetch.php?id=" + start);
					FileUtils.copyURLToFile(source, destination);
					FileUtils.writeStringToFile(lastLin, start + "");

					System.out.print(".");
				}
				String lin = FileUtils.readFileToString(destination, "UTF-8");
				try {
					LinParser linParser = new LinParser();
					Collection<Deal> deals = linParser.parseLinToBridgeData(lin, start);
					ignoredDeals += linParser.getIgnoredDeals();
					for (Deal deal : deals) {
						//System.out.println(deal);
					}
					allDeals.addAll(deals);
				} catch (ParseException e) {
					e.setFileNr(start);
					exceptionCount++;
					String exceptionType = e.getType();
					if (exceptionCounts.containsKey(exceptionType)) {
						exceptionCounts.put(exceptionType, exceptionCounts.get(exceptionType) + 1);
					} else {
						exceptionCounts.put(exceptionType, 1);
					}

					File exceptionLogFile = new File(exeptionsDirectory, e.getType() + ".txt");

					FileUtils.writeStringToFile(exceptionLogFile, e.getMessage() + "\n", true);

				}

				if (start == end) {
					parseMore = false;
				}

				if (start % 1000 == 0 || !parseMore) {
					System.out.println();
					System.out.println(start);
					DecimalFormat format = new DecimalFormat("0.00%");
					for (Entry<String, Integer> parseExceptionCount : exceptionCounts.entrySet()) {

						System.out.println(format.format(1.0 * parseExceptionCount.getValue() / start) + " " + parseExceptionCount.getKey());
					}
					System.out.println("TotalExceptions " + format.format(1.0 * exceptionCount / start));
					System.out.println("Deals " + allDeals.size() + " (" + ignoredDeals + " ignored)");

				}

				start++;
			}
			return copyResults(allDeals);
		} catch (IOException e) {
			e.printStackTrace();
			return new HashSet<Result>();
		}

	}

	public static Set<Result> copyResults(Set<Deal> deals) {
		Set<Result> toReturn = new HashSet<Result>();
		for (Deal deal : deals) {
			toReturn.add(deal.getResult(Line.NS));
			toReturn.add(deal.getResult(Line.EW));
		}
		return toReturn;
	}

	public static Set<Result> readAllDeals() {
		return readDeals(1, 31762);
	}

	public static Set<Result> readSampleDeals() {
		return readDeals(20001, 22000);
	}
}
