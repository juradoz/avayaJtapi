package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentSingleStepTransferCall extends LucentPrivateData {
	public static LucentSingleStepTransferCall decode(InputStream in) {
		LucentSingleStepTransferCall _this = new LucentSingleStepTransferCall();

		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID activeCall;
	String transferredTo;

	public static final int PDU = 142;

	public LucentSingleStepTransferCall() {
	}

	public LucentSingleStepTransferCall(CSTAConnectionID _activeCall,
			String _transferredTo) {
		activeCall = _activeCall;
		transferredTo = _transferredTo;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		activeCall = CSTAConnectionID.decode(memberStream);
		transferredTo = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(activeCall, memberStream);
		ASNIA5String.encode(transferredTo, memberStream);
	}

	public CSTAConnectionID getActiveCall() {
		return activeCall;
	}

	@Override
	public int getPDU() {
		return 142;
	}

	public String getTransferredTo() {
		return transferredTo;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSingleStepTransferCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));
		lines
				.addAll(ASNIA5String.print(transferredTo, "transferredTo",
						indent));

		lines.add("}");
		return lines;
	}
}
