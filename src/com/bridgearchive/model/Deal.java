package com.bridgearchive.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bridgearchive.model.utils.BridgeUtils;

public class Deal {

	private final Play play;
	private final Deal otherTableDeal;
	private final int linNr;
	private Integer impScoreNS = null;
	private final Result resultNS;
	private final Result resultEW;

	public static Collection<Deal> createDeals(Play open, Play closed, int linNr) {
		Deal openDeal = new Deal(open, closed, linNr);
		Deal closedDeal = openDeal.getOtherTableDeal();
		List<Deal> toReturn = new ArrayList<Deal>();
		toReturn.add(openDeal);
		toReturn.add(closedDeal);
		return toReturn;
	}

	private Deal(Play open, Play closed, int linNr) {
		play = open;
		otherTableDeal = new Deal(closed, this, linNr);
		this.linNr = linNr;
		resultNS = new Result(this, Line.NS);
		resultEW = new Result(this, Line.EW);
	}

	private Deal(Play closed, Deal open, int linNr) {
		play = closed;
		otherTableDeal = open;
		this.linNr = linNr;
		resultNS = new Result(this, Line.NS);
		resultEW = new Result(this, Line.EW);
	}

	public Play getPlay() {
		return play;
	}

	public int getImpScore(Line line) {
		if (impScoreNS == null) {
			int thisTableScore = play.getContractScore(Line.NS);
			int otherTableScore = otherTableDeal.play.getContractScore(Line.NS);
			int scoreDifference = thisTableScore - otherTableScore;
			impScoreNS = BridgeUtils.calculateImps(scoreDifference);
		}
		return line == Line.NS ? impScoreNS : -impScoreNS;
	}

	public Deal getOtherTableDeal() {
		return otherTableDeal;
	}

	@Override
	public String toString() {
		return toString(Line.NS, "", "");

	}

	public String toString(Line line, String focusDescription, String otherTableDescription) {
		String linNrString = linNr + ".lin\r\n";
		String boardString = play.getBidding().getBoard().toString();
		String focusPlay = getPlayAndBiddingToString(play, line, focusDescription);
		String otherTablePlay = getPlayAndBiddingToString(otherTableDeal.getPlay(), line, otherTableDescription);
		String impScore = "IMP score for " + focusDescription + ": " + getImpScore(line) + "\r\n";
		String separator = "----------------------------------------" + "\r\n";
		String scoreFocusTable = "Result in " + focusDescription + " table for " + line + ": " + getFormatedResult(play, line) + "\r\n";
		String scoreOtherTable = "Result in " + otherTableDescription + " table for " + line + ": " + getFormatedResult(otherTableDeal.getPlay(), line)
				+ "\r\n";
		String toReturn = linNrString + boardString + "\r\n" + focusPlay + "\r\n" + otherTablePlay + "\r\n" + scoreFocusTable + scoreOtherTable + impScore
				+ "\r\n"
				+ separator;
		return toReturn;
	}

	private String getPlayAndBiddingToString(Play play, Line line, String description) {
		String label = "Action in " + description + " table:\r\n";
		String biddingString = play.getBidding().toString();
		String playString = play.toString();
		String toReturn = label + "\r\n" + biddingString + "\r\n" + playString;
		return toReturn;

	}

	private static String getFormatedResult(Play play, Line line) {
		int overtricks = play.getTricks() - play.getBidding().getContract().getExpectedTricks();
		String overtricksString = "=";
		if (overtricks < 0) {
			overtricksString = "" + overtricks;
		}
		if (overtricks > 0) {
			overtricksString = "+" + overtricks;
		}
		return play.getContractScore(line) + " " + play.getBidding().getContract().toString() + overtricksString + " by "
				+ play.getBidding().getContract().getDeclarer();
	}

	public Result getResult(Line line) {
		return (line == Line.NS) ? resultNS : resultEW;
	}
}
