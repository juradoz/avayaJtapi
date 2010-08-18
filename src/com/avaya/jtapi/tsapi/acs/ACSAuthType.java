package com.avaya.jtapi.tsapi.acs;

import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ACSAuthType extends ASNEnumerated {
	static final short REQUIRES_EXTERNAL_AUTH = -1;
	static final short AUTH_LOGIN_ID_ONLY = 0;
	static final short AUTH_LOGIN_ID_IS_DEFAULT = 1;
	static final short NEED_LOGIN_ID_AND_PASSWD = 2;
	static final short ANY_LOGIN_ID = 3;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		final Collection<String> lines = new ArrayList<String>();
		String str;
		switch (value) {
		case -1:
			str = "REQUIRES_EXTERNAL_AUTH";
			break;
		case 0:
			str = "AUTH_LOGIN_ID_ONLY";
			break;
		case 1:
			str = "AUTH_LOGIN_ID_IS_DEFAULT";
			break;
		case 2:
			str = "NEED_LOGIN_ID_AND_PASSWD";
			break;
		case 3:
			str = "ANY_LOGIN_ID";
			break;
		default:
			str = "?? " + value + " ??";
		}

		lines.addAll(ASNEnumerated.print(value, str, name, indent));
		return lines;
	}
}
