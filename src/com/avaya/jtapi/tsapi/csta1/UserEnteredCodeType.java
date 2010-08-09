package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class UserEnteredCodeType extends ASNEnumerated {
	static final short UE_NONE = -1;
	public static final short UE_ANY = 0;
	public static final short UE_LOGIN_DIGITS = 2;
	public static final short UE_CALL_PROMPTER = 5;
	public static final short UE_DATA_BASE_PROVIDED = 17;
	public static final short UE_TONE_DETECTOR = 32;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case -1:
			str = "UE_NONE";
			break;
		case 0:
			str = "UE_ANY";
			break;
		case 2:
			str = "UE_LOGIN_DIGITS";
			break;
		case 5:
			str = "UE_CALL_PROMPTER";
			break;
		case 17:
			str = "UE_DATA_BASE_PROVIDED";
			break;
		case 32:
			str = "UE_TONE_DETECTOR";
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
 * com.avaya.jtapi.tsapi.csta1.UserEnteredCodeType JD-Core Version: 0.5.4
 */