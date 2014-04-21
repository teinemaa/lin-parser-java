package com.bridgearchive.parser.lin;

import java.util.List;

import com.bridgearchive.parser.lin.tag.QxTag;
import com.bridgearchive.parser.lin.tag.Tag;

public class TagReader {

	private List<Tag> tags;

	int currentBoard = -1;
	int currentTag = -1;

	public TagReader(List<Tag> tags) {
		this.tags = tags;
	}

	public QxTag nextBoard() {
		int index = currentBoard + 1;

		while (index < tags.size()) {
			Tag tag = tags.get(index);
			if (tag instanceof QxTag) {
				currentBoard = index;
				currentTag = index;
				QxTag qx = (QxTag) tag;
				return qx;
			}
			index++;
		}

		return null;
	}

	public <T extends Tag> T nextTag(Class<T> clazz) {

		int index = currentTag + 1;

		while (index < tags.size()) {
			Tag tag = tags.get(index);
			if (tag instanceof QxTag) {
				return null;
			}
			if (clazz.isInstance(tag)) {
				currentTag = index;
				return (T) tag;
			}
			index++;
		}

		return null;
	}

}
