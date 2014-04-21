package com.bridgearchive.bridgedata;

public class Result implements Comparable<Result> {

	private final Deal deal;
	private final Line line;

	Result(Deal deal, Line line) {
		this.deal = deal;
		this.line = line;
	}

	public Deal getDeal() {
		return deal;
	}

	public Play getPlay() {
		return getDeal().getPlay();
	}

	public Bidding getBidding() {
		return getPlay().getBidding();
	}

	public Contract getContract() {
		return getBidding().getContract();
	}

	public Board getBoard() {
		return getBidding().getBoard();
	}

	public Hand getHand(Position position) {
		return getBoard().getHand(position);
	}

	public Line getLine() {
		return line;
	}

	public String toString(String focusDescription, String otherTableDescription) {
		return getDeal().toString(line, focusDescription, otherTableDescription);
	}

	public int getImps() {
		return deal.getImpScore(line);
	}

	@Override
	public int compareTo(Result o) {
		return this.getImps() - o.getImps();
	}

	public Result getOtherTableResult() {
		return deal.getOtherTableDeal().getResult(line);
	}

}
