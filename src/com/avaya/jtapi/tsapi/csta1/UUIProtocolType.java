package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class UUIProtocolType extends ASNEnumerated {
	public static final short UUI_NONE = -1;
	public static final short UUI_USER_SPECIFIC = 0;
	public static final short UUI_IA5_ASCII = 4;
	public static final short UUI_Q931_CALLCTRL = 8;

	public static LucentUserToUserInfo decodeUserToUserInfo(
			InputStream memberStream, CSTATSProvider provider) {
		LucentUserToUserInfo _this = new LucentUserToUserInfo();
		_this.doDecode(memberStream);

		switch (_this.getType()) {
		case -1:
			_this = null;
			break;
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		default:
			break;
		case 8:
			LucentQ931UserToUserInfo _alternate = new LucentQ931UserToUserInfo(
					_this.getBytes());

			_this = _alternate;
		}

		return _this;
	}

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case -1:
			str = "UUI_NONE";
			break;
		case 0:
			str = "UUI_USER_SPECIFIC";
			break;
		case 4:
			str = "UUI_IA5_ASCII";
			break;
		case 8:
			str = "UUI_Q931_CALLCTRL";
			break;
		case 1:
		case 2:
		case 3:
		case 5:
		case 6:
		case 7:
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.UUIProtocolType JD-Core Version: 0.5.4
 */