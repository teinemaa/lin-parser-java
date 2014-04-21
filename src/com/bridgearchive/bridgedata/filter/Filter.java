package com.bridgearchive.bridgedata.filter;

import java.util.HashSet;
import java.util.Set;

import com.bridgearchive.bridgedata.Result;

public abstract class Filter {
	private Set<Result> results;

	private boolean filterOtherTable = false;

	public Filter(Set<Result> results) {
		this.results = new HashSet<Result>(results);
	}

	public Set<Result> getRemaining() {
		HashSet<Result> toReturn = new HashSet<Result>();
		for (Result result : results) {
			Result filterBase = filterOtherTable ? result.getOtherTableResult() : result;
			if (isRemaining(filterBase)) {
				toReturn.add(result);
			}
		}
		return toReturn;
	}

	public Filter setFilterOtherTable(boolean filterOtherTable) {
		this.filterOtherTable = filterOtherTable;
		return this;
	}

	protected abstract boolean isRemaining(Result result);
}
