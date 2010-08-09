package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LinkState extends ASNEnumerated {
	public static final short LS_LINK_UNAVAIL = 0;
	public static final short LS_LINK_UP = 1;
	public static final short LS_LINK_DOWN = 2;

	public static Collection<String> print(short value, String name,
			String indent) {
		String str;
		switch (value) {
		case 0:
			str = "LS_LINK_UNAVAIL";
			break;
		case 1:
			str = "LS_LINK_UP";
			break;
		case 2:
			str = "LS_LINK_DOWN";
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
 * com.avaya.jtapi.tsapi.csta1.LinkState JD-Core Version: 0.5.4
 */