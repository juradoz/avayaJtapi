package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class RetryValue extends ASNInteger {
	public static final short noListAvailable = -1;
	public static final short noCountAvailable = -2;

	public static Collection<String> print(int value, String name, String indent) {
		String str;
		switch (value) {
		case -1:
			str = "noListAvailable";
			break;
		case -2:
			str = "noCountAvailable";
			break;
		default:
			str = String.valueOf(value);
		}

		return print(str, name, indent);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.RetryValue JD-Core Version: 0.5.4
 */