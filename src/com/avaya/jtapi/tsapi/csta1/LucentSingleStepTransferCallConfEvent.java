package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentSingleStepTransferCallConfEvent extends LucentPrivateData {
	CSTAConnectionID transferredCall;
	static final int PDU = 143;

	public static LucentSingleStepTransferCallConfEvent decode(
			final InputStream in) {
		final LucentSingleStepTransferCallConfEvent _this = new LucentSingleStepTransferCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public LucentSingleStepTransferCallConfEvent() {
	}

	public LucentSingleStepTransferCallConfEvent(
			final CSTAConnectionID _transferredCall) {
		transferredCall = _transferredCall;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		transferredCall = CSTAConnectionID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(transferredCall, memberStream);
	}

	@Override
	public int getPDU() {
		return 143;
	}

	public CSTAConnectionID getTransferredCall() {
		return transferredCall;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSingleStepTransferCallConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(transferredCall, "transferredCall",
				indent));

		lines.add("}");
		return lines;
	}
}
