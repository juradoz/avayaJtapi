package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.ArrayList;
import java.util.Collection;

public final class StreamType extends ASNEnumerated {
	public static final short ST_CSTA = 1;
	public static final short ST_OAM = 2;
	public static final short ST_DIRECTORY = 3;
	public static final short ST_NMSRV = 4;
	public static final short ST_CSTA_TRUSTED_APP = 5;

	static Collection<String> print(short value, String name, String indent) {
		Collection<String> lines = new ArrayList<String>();
		String str;
		switch (value) {
		case 1:
			str = "ST_CSTA";
			break;
		case 2:
			str = "ST_OAM";
			break;
		case 3:
			str = "ST_DIRECTORY";
			break;
		case 4:
			str = "ST_NMSRV";
			break;
		case 5:
			str = "ST_CSTA_TRUSTED_APP";
			break;
		default:
			str = "?? " + value + " ??";
		}

		lines.addAll(print(value, str, name, indent));
		return lines;
	}
}