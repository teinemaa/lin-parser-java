package com.bridgearchive.bridgedata.filter;

public interface FilterComponent<T> {

	boolean isRemaining(T input);

}
