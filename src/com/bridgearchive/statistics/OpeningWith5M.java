package com.bridgearchive.statistics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.bridgearchive.bridgedata.Call;
import com.bridgearchive.bridgedata.Result;
import com.bridgearchive.bridgedata.Strain;
import com.bridgearchive.bridgedata.filter.HcpComponent;
import com.bridgearchive.bridgedata.filter.OpenerFilter;
import com.bridgearchive.bridgedata.filter.OpeningFilter;
import com.bridgearchive.bridgedata.filter.SuitComponent;

public class OpeningWith5M extends Calculator {

	@Override
	public void calculate(Set<Result> results) {
		List<Summary> summarys = new ArrayList<Summary>();
		for (int k = 0; k < 2; k++) {
			for (int i = 10; i < 18; i++) {
				for (int j = i; j < 18; j++) {

					summarys.add(calculate(results, i, j, k));
				}
			}
		}

		Collections.sort(summarys);
		String s = Summary.getLabel() + "\r\n";
		for (Summary summary : summarys) {
			s += summary + "\r\n";
		}
		File analysisSubdirectory = new File(DebuggerUtlis.ANALYSIS_DIRECTORY, "5-card major");
		File analysisFile = new File(analysisSubdirectory, "_summary.txt");

		try {
			FileUtils.writeStringToFile(analysisFile, s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Summary calculate(Set<Result> results, int fromHcp, int toHcp, int spades) {
		results = new OpenerFilter(results)
				.addComponent(new HcpComponent().setHcp(fromHcp, toHcp))
				.addComponent(new SuitComponent().setMajors().setLength(5))
				//.addComponent(new SuitComponent().setMajors().setLength(2, 3))
				//.addComponent(new SuitComponent().setSuit(Suit.DIAMOND).setLength(2, 3))
				//.addComponent(new SuitComponent().setSuit(Suit.CLUB).setLength(2, 3))
				.getRemaining();
		results = new OpeningFilter(results).setBid(Call.valueOf(1, Strain.NO_TRUMP)).getRemaining();
		results = new OpeningFilter(results).setBid(Call.valueOf(1, spades == 1 ? Strain.SPADE : Strain.HEARTH)).setFilterOtherTable(true).getRemaining();

		//results = new RemoveDuplicates(results).getRemaining();
		String range = fromHcp == toHcp ? (fromHcp + "") : (fromHcp + "-" + toHcp);
		return DebuggerUtlis
				.printResults(results, "5major-open-1nt-" + range + "hcp-" + (spades == 1 ? "spades" : "hearts"),
						"Hands with " + range
								+ " HCP and 5-card major, which are opened 1NT in one room and 1-major in other room.", "1NT opening",
						"1-major opening", "5-card major");

	}
}
