package com.avaya.jtapi.tsapi.acs;

import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ACSEncodeType extends ASNEnumerated {
	static final short CAN_USE_BINDERY_ENCRYPTION = 1;
	static final short NDS_AUTH_CONNID = 2;
	static final short WIN_NT_LOCAL = 3;
	static final short WIN_NT_NAMED_PIPE = 4;
	static final short WIN_NT_WRITE_DATA = 5;

	static Collection<String> print(short value, String name, String indent) {
		Collection<String> lines = new ArrayList<String>();
		String str;
		switch (value) {
		case 1:
			str = "CAN_USE_BINDERY_ENCRYPTION";
			break;
		case 2:
			str = "NDS_AUTH_CONNID";
			break;
		case 3:
			str = "WIN_NT_LOCAL";
			break;
		case 4:
			str = "WIN_NT_NAMED_PIPE";
			break;
		case 5:
			str = "WIN_NT_WRITE_DATA";
			break;
		default:
			str = "?? " + value + " ??";
		}

		lines.addAll(print(value, str, name, indent));
		return lines;
	}
}

