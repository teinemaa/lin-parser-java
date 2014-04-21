package com.bridgearchive.model.filters;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.bridgearchive.model.Board;
import com.bridgearchive.model.Result;

public class RemoveDuplicates extends Filter {

	private Map<Board, Integer> sameBoardCount = new HashMap<Board, Integer>();

	public RemoveDuplicates(Set<Result> results) {
		super(results);
		for (Result result : results) {
			Board board = result.getBoard();
			if (sameBoardCount.containsKey(board)) {
				sameBoardCount.put(board, sameBoardCount.get(board) + 1);
			} else {
				sameBoardCount.put(board, 1);
			}
		}
	}

	@Override
	protected boolean isRemaining(Result result) {
		if (sameBoardCount.get(result.getBoard()) == 1) {
			return true;
		} else {
			return false;

		}
	}

}
