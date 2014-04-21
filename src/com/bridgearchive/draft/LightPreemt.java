package com.bridgearchive.draft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.bridgearchive.model.Call;
import com.bridgearchive.model.Result;
import com.bridgearchive.model.Strain;
import com.bridgearchive.model.filters.OpeningFilter;

public class LightPreemt extends Calculator {

	@Override
	public void calculate(Set<Result> results) {
		List<Summary> summarys = new ArrayList<Summary>();
		for (int i = 15; i < 22; i++) {
			summarys.add(calculate(results, i));
		}
		Collections.sort(summarys);
		String s = Summary.getLabel() + "\r\n";
		for (Summary summary : summarys) {
			s += summary + "\r\n";
		}
		File analysisSubdirectory = new File(DebuggerUtlis.ANALYSIS_DIRECTORY, "light preempt");
		File analysisFile = new File(analysisSubdirectory, "_summary.txt");

		try {
			FileUtils.writeStringToFile(analysisFile, s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Summary calculate(Set<Result> results, int fromHcp) {

		results = new OpeningFilter(results).setPosition(1).setBid(Call.valueOf(3, Strain.CLUB), Call.valueOf(3, Strain.SPADE)).getRemaining();
		results = new OpeningFilter(results).setPosition(2, 4).setBid(Call.valueOf(1, Strain.CLUB), Call.valueOf(7, Strain.NO_TRUMP)).setFilterOtherTable(true)
				.getRemaining();

		//results = new RemoveDuplicates(results).getRemaining();
		return DebuggerUtlis
				.printResults(results, "light-preempt-" + fromHcp + "hcp",
						"Hands with at least " + fromHcp
								+ " HCP, which are opened 1C in one room and 2C in other room.", "preempt",
						"pass", "light preempt");

	}
}
