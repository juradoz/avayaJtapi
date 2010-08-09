package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class SystemStatus extends ASNEnumerated {
	public static final short SS_INITIALIZING = 0;
	public static final short SS_ENABLED = 1;
	public static final short SS_NORMAL = 2;
	public static final short SS_MESSAGES_LOST = 3;
	public static final short SS_DISABLED = 4;
	public static final short SS_OVERLOAD_IMMINENT = 5;
	public static final short SS_OVERLOAD_REACHED = 6;
	public static final short SS_OVERLOAD_RELIEVED = 7;

	public static Collection<String> print(short value, String name,
			String indent) {
		String str;
		switch (value) {
		case 0:
			str = "SS_INITIALIZING";
			break;
		case 1:
			str = "SS_ENABLED";
			break;
		case 2:
			str = "SS_NORMAL";
			break;
		case 3:
			str = "SS_MESSAGES_LOST";
			break;
		case 4:
			str = "SS_DISABLED";
			break;
		case 5:
			str = "SS_OVERLOAD_IMMINENT";
			break;
		case 6:
			str = "SS_OVERLOAD_REACHED";
			break;
		case 7:
			str = "SS_OVERLOAD_RELIEVED";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.SystemStatus JD-Core Version: 0.5.4
 */