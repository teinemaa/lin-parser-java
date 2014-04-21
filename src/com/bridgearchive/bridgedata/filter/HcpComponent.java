package com.bridgearchive.bridgedata.filter;

import java.util.Set;

import com.bridgearchive.bridgedata.Card;
import com.bridgearchive.bridgedata.CardUtils;

public class HcpComponent implements CardComponent {

	private int minHcp = 0;
	private int maxHcp = 40;
	private int minControls = 0;
	private int maxControls = 12;

	public HcpComponent setHcp(int hcp) {
		minHcp = hcp;
		maxHcp = hcp;
		return this;
	}

	public HcpComponent setHcp(int minHcp, int maxHcp) {
		this.minHcp = minHcp;
		this.maxHcp = maxHcp;
		return this;
	}

	@Override
	public boolean isRemaining(Set<Card> cards) {
		int hcp = CardUtils.getHcp(cards);
		if (hcp >= minHcp && hcp <= maxHcp) {
			int controls = CardUtils.getControls(cards);
			if (controls >= minControls && controls <= maxControls) {
				return true;
			}
		}
		return false;

	}

	public HcpComponent setControls(int minControls, int maxControls) {
		this.minControls = minControls;
		this.maxControls = maxControls;
		return this;
	}

}
