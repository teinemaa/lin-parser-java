package com.bridgearchive.parser.lin.tag;

public class UnknownTag extends Tag {

	private String key;

	public UnknownTag(String key, String value) {
		super(value);
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}

}
