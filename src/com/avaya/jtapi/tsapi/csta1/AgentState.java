package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class AgentState extends ASNEnumerated {
	public static final short AG_NOT_READY = 0;
	public static final short AG_NULL = 1;
	public static final short AG_READY = 2;
	public static final short AG_WORK_NOT_READY = 3;
	public static final short AG_WORK_READY = 4;
	public static final short AG_NOT_INITIALIZED = -1;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "AG_NOT_READY";
			break;
		case 1:
			str = "AG_NULL";
			break;
		case 2:
			str = "AG_READY";
			break;
		case 3:
			str = "AG_WORK_NOT_READY";
			break;
		case 4:
			str = "AG_WORK_READY";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
