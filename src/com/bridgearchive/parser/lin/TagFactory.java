package com.bridgearchive.parser.lin;

import com.bridgearchive.parser.lin.exception.ParseException;
import com.bridgearchive.parser.lin.tag.AhTag;
import com.bridgearchive.parser.lin.tag.AnTag;
import com.bridgearchive.parser.lin.tag.AtTag;
import com.bridgearchive.parser.lin.tag.BtTag;
import com.bridgearchive.parser.lin.tag.HtTag;
import com.bridgearchive.parser.lin.tag.MbTag;
import com.bridgearchive.parser.lin.tag.McTag;
import com.bridgearchive.parser.lin.tag.MdTag;
import com.bridgearchive.parser.lin.tag.MnTag;
import com.bridgearchive.parser.lin.tag.MpTag;
import com.bridgearchive.parser.lin.tag.NtTag;
import com.bridgearchive.parser.lin.tag.ObTag;
import com.bridgearchive.parser.lin.tag.PcTag;
import com.bridgearchive.parser.lin.tag.PfTag;
import com.bridgearchive.parser.lin.tag.PgTag;
import com.bridgearchive.parser.lin.tag.PnTag;
import com.bridgearchive.parser.lin.tag.PwTag;
import com.bridgearchive.parser.lin.tag.QxTag;
import com.bridgearchive.parser.lin.tag.RhTag;
import com.bridgearchive.parser.lin.tag.RsTag;
import com.bridgearchive.parser.lin.tag.SaTag;
import com.bridgearchive.parser.lin.tag.StTag;
import com.bridgearchive.parser.lin.tag.SvTag;
import com.bridgearchive.parser.lin.tag.Tag;
import com.bridgearchive.parser.lin.tag.UnknownTag;
import com.bridgearchive.parser.lin.tag.VgTag;

public class TagFactory {

	public static Tag createTag(String key, String value) throws ParseException {

		key = key.trim().toLowerCase();
		if (key.length() != 2) {
			throw new ParseException("invalid tag length", key.length() + " key='" + key + "' value='" + value + "'");
		}
		switch (key) {
		case "vg":
			return new VgTag(value);
		case "rs":
			return new RsTag(value);
		case "pn":
			return new PnTag(value);
		case "pg":
			return new PgTag(value);
		case "qx":
			return new QxTag(value);
		case "md":
			return new MdTag(value);
		case "sv":
			return new SvTag(value);
		case "mb":
			return new MbTag(value);
		case "pc":
			return new PcTag(value);
		case "mc":
			return new McTag(value);
		case "ob":
			return new ObTag(value);
		case "an":
			return new AnTag(value);
		case "st":
			return new StTag(value);
		case "nt":
			return new NtTag(value);
		case "mn":
			return new MnTag(value);
		case "rh":
			return new RhTag(value);
		case "ah":
			return new AhTag(value);
		case "sa":
			return new SaTag(value);
		case "pw":
			return new PwTag(value);
		case "bt":
			return new BtTag(value);
		case "pf":
			return new PfTag(value);
		case "mp":
			return new MpTag(value);
		case "ht":
			return new HtTag(value);
		case "at":
			return new AtTag(value);

		default:
			return new UnknownTag(key, value);
		}
	}

}
