package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentUseUnifiedDesktopLicense extends LucentPrivateData {
	public static final int PDU = 139;

	public static LucentUseUnifiedDesktopLicense decode(InputStream in) {
		LucentUseUnifiedDesktopLicense _this = new LucentUseUnifiedDesktopLicense();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public int getPDU() {
		return 139;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("LucentUseUnifiedDesktopLicense ::=");
		lines.add("{");
		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentUseUnifiedDesktopLicense JD-Core Version:
 * 0.5.4
 */