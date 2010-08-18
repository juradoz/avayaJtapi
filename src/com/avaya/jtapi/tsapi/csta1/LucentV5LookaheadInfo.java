package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;

public final class LucentV5LookaheadInfo extends LucentLookaheadInfo {
	public static LucentLookaheadInfo decode(InputStream in) {
		LucentV5LookaheadInfo _this = new LucentV5LookaheadInfo();
		_this.doDecode(in);
		if (_this.getType() == -1) {
			return null;
		}
		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);

		sourceVDN = UnicodeDeviceID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		UnicodeDeviceID.encode(sourceVDN, memberStream);
	}
}

