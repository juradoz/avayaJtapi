package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;

public final class LucentV5SendDTMFTone extends LucentSendDTMFTone {
	static final int PDU = 71;

	public static LucentSendDTMFTone decode(InputStream in) {
		LucentV5SendDTMFTone _this = new LucentV5SendDTMFTone();
		_this.doDecode(in);

		return _this;
	}

	public LucentV5SendDTMFTone() {
	}

	public LucentV5SendDTMFTone(CSTAConnectionID _sender,
			CSTAConnectionID[] _receivers, String _tones, int _toneDuration,
			int _pauseDuration) {
		super(_sender, _receivers, _tones, _toneDuration, _pauseDuration);
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	@Override
	public int getPDU() {
		return 71;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV5SendDTMFTone JD-Core Version: 0.5.4
 */