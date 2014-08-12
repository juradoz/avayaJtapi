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

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("LucentUseUnifiedDesktopLicense ::=");
		lines.add("{");
		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 139;
	}
}