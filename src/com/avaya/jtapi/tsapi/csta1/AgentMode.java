package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public final class AgentMode extends ASNEnumerated {
	public static final short AM_LOG_IN = 0;
	public static final short AM_LOG_OUT = 1;
	public static final short AM_NOT_READY = 2;
	public static final short AM_READY = 3;
	public static final short AM_WORK_NOT_READY = 4;
	public static final short AM_WORK_READY = 5;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 0:
			str = "AM_LOG_IN";
			break;
		case 1:
			str = "AM_LOG_OUT";
			break;
		case 2:
			str = "AM_NOT_READY";
			break;
		case 3:
			str = "AM_READY";
			break;
		case 4:
			str = "AM_WORK_NOT_READY";
			break;
		case 5:
			str = "AM_WORK_READY";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}