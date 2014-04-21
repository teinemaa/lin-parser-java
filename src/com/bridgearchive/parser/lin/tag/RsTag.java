package com.bridgearchive.parser.lin.tag;

public class RsTag extends Tag {

	private String[] results;

	public RsTag(String value) {
		super(value);
		results = value.split(",", -1);
	}
	
	public String getResults(int index) {
		return results[index];
	}
	
	public int getResultsCount() {
		return results.length;
	}

}
