package com.bridgearchive.draft;

import java.util.Set;

import com.bridgearchive.model.Call;
import com.bridgearchive.model.Contract;
import com.bridgearchive.model.Result;
import com.bridgearchive.model.Strain;
import com.bridgearchive.model.Suit;
import com.bridgearchive.model.filters.Filter;
import com.bridgearchive.model.filters.HcpComponent;
import com.bridgearchive.model.filters.OpenerFilter;
import com.bridgearchive.model.filters.OpeningFilter;
import com.bridgearchive.model.filters.RemoveDuplicates;
import com.bridgearchive.model.filters.ResponderFilter;
import com.bridgearchive.model.filters.SuitComponent;

public class InvTo4M extends Calculator {

	@Override
	public void calculate(Set<Result> results) {
		calculate(results, true, true);

	}

	public void calculate(Set<Result> results, boolean minor, boolean strong) {
		Set<Result> both = findForOne(results, Suit.HEARTH);
		both.addAll(findForOne(results, Suit.SPADE));
		DebuggerUtlis.printResults(both, "invTo4M");

	}

	private Set<Result> findForOne(Set<Result> results, Suit suit) {
		results = new OpenerFilter(results)
				.addComponent(new HcpComponent().setHcp(10, 13))
				.addComponent(new SuitComponent().setSuit(suit).setLength(5, 6))
				.addComponent(new SuitComponent().setLength(3))
				.addComponent(new SuitComponent().setLength(1).setHcp(0))
				.getRemaining();
		results = new ResponderFilter(results)
				.addComponent(new HcpComponent().setHcp(6, 11))
				.addComponent(new SuitComponent().setSuit(suit).setLength(4))
				/*				.addComponent(new SuitComponent().setSuit(Suit.CLUB).setLength(2, 4))
								.addComponent(new SuitComponent().setSuit(Suit.DIAMOND).setLength(2, 4))
								.addComponent(new SuitComponent().setSuit(Suit.HEARTH).setLength(2, 4))
								.addComponent(new SuitComponent().setSuit(Suit.SPADE).setLength(2, 4))*/
				.getRemaining();
		/*		results = new LineFilter(results)
						.addComponent(new HcpComponent().setControls(6, 7))
						.getRemaining();*/
		results = new CustomFilter(results, suit)
				.getRemaining();
		results = new OpeningFilter(results).setBid(Call.valueOf(1, Strain.valueOf(suit))).getRemaining();
		results = new RemoveDuplicates(results).getRemaining();
		return results;
	}

	class CustomFilter extends Filter {

		private Suit suit;

		public CustomFilter(Set<Result> results, Suit suit) {
			super(results);
			this.suit = suit;
		}

		@Override
		protected boolean isRemaining(Result result) {
			Contract contractThisRoom = result.getContract();
			Contract contractOtherRoom = result.getDeal().getOtherTableDeal().getPlay().getBidding().getContract();
			if (contractThisRoom.getLevel() == 4 && contractThisRoom.getStrain() == Strain.valueOf(suit)) {
				if (contractOtherRoom.getLevel() < 4 && contractOtherRoom.getStrain() == Strain.valueOf(suit)) {
					return true;
				}
			}
			return false;
		}

	}
}
