package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentUseUnifiedDesktopLicense extends LucentPrivateData {
	public static final int PDU = 139;

	public static LucentUseUnifiedDesktopLicense decode(final InputStream in) {
		final LucentUseUnifiedDesktopLicense _this = new LucentUseUnifiedDesktopLicense();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public int getPDU() {
		return 139;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("LucentUseUnifiedDesktopLicense ::=");
		lines.add("{");
		lines.add("}");
		return lines;
	}
}
