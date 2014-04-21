package com.bridgearchive.model;

import java.util.List;

public class Contract {

	private final int level;
	private final Strain strain;
	private final Stake stake;
	private final Position declarer;
	private final boolean declarerVulnerable;

	public Contract(Board board, List<Call> bidding) {
		int level = 0;
		Strain strain = null;
		Stake stake = Stake.PASS;
		Position biddingWinner = null;
		Position current = board.getDealer();
		for (Call call : bidding) {
			if (call.isBid()) {
				level = call.getLevel();
				strain = call.getStrain();
				stake = Stake.PASS;
				biddingWinner = current;
			} else {
				if (!call.getStake().isPass()) {
					stake = call.getStake();
				}
			}
			current = current.getNext();
		}
		this.level = level;
		this.strain = strain;
		this.stake = stake;
		Position declarer = null;
		if (isPassout()) {

			declarer = Position.NORTH;
		} else {
			current = board.getDealer();
			for (Call call : bidding) {
				if (call.isBid() && call.getStrain() == strain && current.getLine() == biddingWinner.getLine()) {
					declarer = current;

					break;
				}
				current = current.getNext();
			}
		}
		this.declarer = declarer;
		Line line = declarer.getLine();
		declarerVulnerable = board.isVulnerable(line);

	}

	public boolean isPassout() {
		return level == 0;
	}

	public int getLevel() {
		return level;
	}

	public int getContractPoints() {
		return getStake().getStakeMultiplier() * (getLevel() * getStrain().getBaseTrickValue() + getStrain().getFirstTrickBonus());
	}

	public boolean isGame() {
		return getContractPoints() >= 100;
	}

	public boolean isDeclarerVulnerable() {
		return declarerVulnerable;
	}

	public Strain getStrain() {
		return strain;
	}

	public Stake getStake() {
		return stake;
	}

	public int getOvertickValue() {
		if (getStake().isPass()) {
			return getStrain().getBaseTrickValue();
		} else {
			return getStake().getStakeMultiplier() * (isDeclarerVulnerable() ? 100 : 50);
		}
	}

	public int getGameBonus() {
		if (isGame()) {
			return isDeclarerVulnerable() ? 500 : 300;
		} else {
			return 50;
		}
	}

	public int getSlamBonus() {
		if (isSmallSlam()) {
			return isDeclarerVulnerable() ? 750 : 500;
		} else if (isGrandSlam()) {
			return isDeclarerVulnerable() ? 1500 : 1000;
		} else {
			return 0;

		}
	}

	private boolean isGrandSlam() {
		return getLevel() == 7;
	}

	private boolean isSmallSlam() {
		return getLevel() == 6;
	}

	public Position getDeclarer() {
		return declarer;

	}

	@Override
	public String toString() {
		if (isPassout()) {
			return "PASSOUT";
		} else {
			return "" + level + strain.getShortName() + stake.getExtension();
		}
	}

	public int getExpectedTricks() {
		return getLevel() + 6;
	}
}
