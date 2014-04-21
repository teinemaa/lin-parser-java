package com.bridgearchive.bridgedata;

import java.util.List;

public class Bidding {

	private final Board board;
	private final List<Call> bidding;
	private Contract contract;
	private boolean openRoom;

	public Bidding(Board board, List<Call> bidding, boolean openRoom) {
		this.board = board;
		this.bidding = bidding;
		this.openRoom = openRoom;
		contract = new Contract(board, bidding);

	}

	public Contract getContract() {
		return contract;
	}

	public Board getBoard() {
		return board;
	}

	public List<Call> getBidding() {
		return bidding;
	}

	public boolean isOpenRoom() {
		return openRoom;
	}

	@Override
	public String toString() {
		Position current = Position.WEST;
		String toReturn = "";
		for (int i = 0; i < 4; i++) {
			toReturn += current + (current == Position.SOUTH ? "" : "\t");
			current = current.getNext();
		}
		while (current != getBoard().getDealer()) {
			toReturn += (current == Position.WEST ? "\r\n" : "") + "\t";
			current = current.getNext();
		}
		for (Call call : bidding) {

			toReturn += (current == Position.WEST ? "\r\n" : "") + call + (current == Position.SOUTH ? "" : "\t");
			current = current.getNext();

		}
		toReturn += "\r\n";
		return toReturn;
	}

	public Position getOpener() {
		Position current = getBoard().getDealer();
		for (Call call : getBidding()) {
			if (call.isBid()) {
				return current;
			}
			current = current.getNext();
		}
		return null;
	}

	public Call getOpening() {
		Position current = getBoard().getDealer();
		for (Call call : getBidding()) {
			if (call.isBid()) {
				return call;
			}
			current = current.getNext();
		}
		return null;
	}

}
