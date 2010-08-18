package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentUseUnifiedDesktopLicenseConfEvent extends
		LucentPrivateData {
	static final int PDU = 140;

	public static LucentUseUnifiedDesktopLicenseConfEvent decode(
			final InputStream in) {
		final LucentUseUnifiedDesktopLicenseConfEvent _this = new LucentUseUnifiedDesktopLicenseConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
	}

	@Override
	public int getPDU() {
		return 140;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("LucentUseUnifiedDesktopLicenseConfEvent ::=");
		lines.add("{");
		lines.add("}");
		return lines;
	}
}
