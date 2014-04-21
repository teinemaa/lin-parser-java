package com.bridgearchive.model;

import java.util.List;

import com.bridgearchive.model.utils.BridgeUtils;

public class Play {

	private final Bidding bidding;
	private final List<Card> cardPlays;
	private final int tricksTaken;

	public Play(Bidding bidding, List<Card> cardPlays, Integer tricksTaken) {
		this.bidding = bidding;
		this.cardPlays = cardPlays;
		if (tricksTaken == null) {
			this.tricksTaken = BridgeUtils.calculateTricks(cardPlays, bidding.getContract().getStrain());
		} else {

			this.tricksTaken = tricksTaken;
		}
	}

	public Bidding getBidding() {
		return bidding;
	}

	public int getContractScore(Line line) {
		if (line.isNS()) {
			return getContractScoreNS();
		} else {
			return -getContractScoreNS();
		}
	}

	private int getContractScoreNS() {
		if (bidding.getContract().getDeclarer().isNS()) {
			return getContractScoreDeclarer();
		} else {
			return -getContractScoreDeclarer();
		}
	}

	private int getContractScoreDeclarer() {
		Contract contract = bidding.getContract();
		if (contract.isPassout()) {
			return 0;
		}
		int expectedTricks = 6 + contract.getLevel();
		boolean contractMade = tricksTaken >= expectedTricks;
		if (contractMade) {
			int contractPoints = contract.getContractPoints();
			int overtricks = tricksTaken - expectedTricks;
			int overtrickPoints = overtricks * contract.getOvertickValue();
			int stakeBonus = contract.getStake().getStakeMakingBonus();
			int gamePoints = contract.getGameBonus();
			int slamBonus = contract.getSlamBonus();
			int score = contractPoints + overtrickPoints + stakeBonus + gamePoints + slamBonus;
			return score;
		} else {
			int undertrickValue = contract.isDeclarerVulnerable() ? -100 : -50;
			int undertricks = expectedTricks - tricksTaken;
			int multiplayer = contract.getStake().getStakeMultiplier();
			int penalty = multiplayer * undertricks * undertrickValue;
			if (!contract.getStake().isPass()) {
				if (undertricks > 1) {
					penalty += -(undertricks - 1) * multiplayer * 50;
				}
				if (!contract.isDeclarerVulnerable() && undertricks > 3) {
					penalty += -(undertricks - 3) * multiplayer * 50;
				}
			}
			return penalty;
		}
	}

	public int getTricks() {
		return tricksTaken;
	}

	@Override
	public String toString() {
		String toReturn = "";
		toReturn += "Lead\t2nd\t3rd\t4th";
		int index = 0;
		for (Card card : cardPlays) {

			toReturn += (index % 4 == 0 ? "\r\n" : "") + card + (index % 4 != 3 ? "\t" : "");

			index++;
		}
		toReturn += "\r\n";
		return toReturn;
	}
}
