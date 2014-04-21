package com.bridgearchive.parser.lin.tag;

public abstract class Tag {

	private final String value;
	
	public Tag(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return getKey()+"|"+getValue()+"|";
	}
	
	private String getValue() {
		return value;
	}

	public String getKey() {
		return getClass().getSimpleName().substring(0, 2).toLowerCase();
	}
	
}
