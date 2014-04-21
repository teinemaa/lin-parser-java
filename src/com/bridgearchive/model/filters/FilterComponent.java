package com.bridgearchive.model.filters;

public interface FilterComponent<T> {

	boolean isRemaining(T input);

}
