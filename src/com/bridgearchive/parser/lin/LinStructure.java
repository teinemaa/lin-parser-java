package com.bridgearchive.parser.lin;

import java.util.Iterator;
import java.util.List;

import com.bridgearchive.parser.lin.exception.LinStructureException;
import com.bridgearchive.parser.lin.exception.ParseException;
import com.bridgearchive.parser.lin.tag.PfTag;
import com.bridgearchive.parser.lin.tag.RsTag;
import com.bridgearchive.parser.lin.tag.Tag;
import com.bridgearchive.parser.lin.tag.VgTag;

public class LinStructure {

	private VgTag vgTag;
	private RsTag rsTag;

	public LinStructure(List<Tag> tags) throws ParseException {
		Iterator<Tag> iter = tags.iterator();
		Tag nextTag;

		nextTag = iter.next();
		if (nextTag instanceof PfTag) {
			nextTag = iter.next();
		}
		if (nextTag instanceof VgTag) {
			vgTag = (VgTag) nextTag;
		} else {
			throw new LinStructureException("vg tag not found");
		}

		nextTag = iter.next();
		if (nextTag instanceof PfTag) {
			nextTag = iter.next();
		}
		if (nextTag instanceof RsTag) {
			rsTag = (RsTag) nextTag;
		} else {
			throw new LinStructureException("rs tag not found");
		}

	}

	public VgTag getVgTag() {
		return vgTag;
	}

	public RsTag getRsTag() {
		return rsTag;
	}

}
