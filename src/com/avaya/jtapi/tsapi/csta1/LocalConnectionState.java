package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class LocalConnectionState extends ASNEnumerated {
	public static final short CS_NONE = -1;
	public static final short CS_NULL = 0;
	public static final short CS_INITIATE = 1;
	public static final short CS_ALERTING = 2;
	public static final short CS_CONNECT = 3;
	public static final short CS_HOLD = 4;
	public static final short CS_QUEUED = 5;
	public static final short CS_FAIL = 6;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case -1:
			str = "CS_NONE";
			break;
		case 0:
			str = "CS_NULL";
			break;
		case 1:
			str = "CS_INITIATE";
			break;
		case 2:
			str = "CS_ALERTING";
			break;
		case 3:
			str = "CS_CONNECT";
			break;
		case 4:
			str = "CS_HOLD";
			break;
		case 5:
			str = "CS_QUEUED";
			break;
		case 6:
			str = "CS_FAIL";
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
 * com.avaya.jtapi.tsapi.csta1.LocalConnectionState JD-Core Version: 0.5.4
 */