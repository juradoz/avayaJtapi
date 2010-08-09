package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ParticipationType extends ASNEnumerated {
	public static final short PT_ACTIVE = 1;
	public static final short PT_SILENT = 0;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 1:
			str = "PT_ACTIVE";
			break;
		case 0:
			str = "PT_SILENT";
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
 * com.avaya.jtapi.tsapi.csta1.ParticipationType JD-Core Version: 0.5.4
 */