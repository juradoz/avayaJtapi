package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.util.Collection;

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