package com.bridgearchive.bridgedata;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Call {

	private static final Map<Integer, Map<Strain, Call>> BIDS = new HashMap<Integer, Map<Strain, Call>>();
	private static final Map<Stake, Call> OTHER_CALLS = new EnumMap<Stake, Call>(Stake.class);

	static {
		for (int level = 1; level <= 7; level++) {
			Map<Strain, Call> strainTable = new EnumMap<Strain, Call>(Strain.class);
			for (Strain strain : Strain.values()) {
				strainTable.put(strain, new Call(level, strain));
			}
			BIDS.put(level, strainTable);
		}
		for (Stake stake : Stake.values()) {
			OTHER_CALLS.put(stake, new Call(stake));
		}
	}

	public static Call valueOf(int level, Strain strain) {
		return BIDS.get(level).get(strain);
	}

	public static Call valueOf(Stake stake) {
		return OTHER_CALLS.get(stake);
	}

	private final Integer level;
	private final Strain strain;
	private final Stake stake;

	private Call(int level, Strain strain) {
		this.level = level;
		this.strain = strain;
		stake = null;
	}

	private Call(Stake stake) {
		level = null;
		strain = null;
		this.stake = stake;
	}

	public boolean isBid() {
		return stake == null;
	}

	public Integer getLevel() {
		return level;
	}

	public Strain getStrain() {
		return strain;

	}

	public Stake getStake() {
		return stake;
	}

	public int getValue() {
		if (isBid()) {
			return 10 * level + strain.ordinal();
		} else {
			return stake.ordinal();
		}
	}

	@Override
	public String toString() {
		if (isBid()) {

			return "" + level + strain.getShortName();
		} else {
			return stake.toString();
		}
	}

}
