package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;

public final class LucentV5SendDTMFTone extends LucentSendDTMFTone {
	static final int PDU = 71;

	public static LucentSendDTMFTone decode(final InputStream in) {
		final LucentV5SendDTMFTone _this = new LucentV5SendDTMFTone();
		_this.doDecode(in);

		return _this;
	}

	public LucentV5SendDTMFTone() {
	}

	public LucentV5SendDTMFTone(final CSTAConnectionID _sender,
			final CSTAConnectionID[] _receivers, final String _tones,
			final int _toneDuration, final int _pauseDuration) {
		super(_sender, _receivers, _tones, _toneDuration, _pauseDuration);
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	@Override
	public int getPDU() {
		return 71;
	}
}
