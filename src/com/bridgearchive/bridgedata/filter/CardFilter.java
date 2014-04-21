package com.bridgearchive.bridgedata.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bridgearchive.bridgedata.Card;
import com.bridgearchive.bridgedata.Result;

public abstract class CardFilter extends Filter {

	private List<CardComponent> components = new ArrayList<>();

	public CardFilter(Set<Result> results) {
		super(results);
	}

	@Override
	protected boolean isRemaining(Result result) {
		Set<Card> cards = getCards(result);
		for (CardComponent component : components) {
			if (!component.isRemaining(cards)) {
				return false;
			}
		}
		return true;
	}

	public CardFilter addComponent(CardComponent component) {
		components.add(component);
		return this;
	}

	protected abstract Set<Card> getCards(Result result);

}
