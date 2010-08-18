package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAMakePredictiveCall extends CSTARequest {
	String callingDevice;
	String calledDevice;
	short allocationState;
	public static final int PDU = 25;

	public static CSTAMakePredictiveCall decode(final InputStream in) {
		final CSTAMakePredictiveCall _this = new CSTAMakePredictiveCall();
		_this.doDecode(in);

		return _this;
	}

	public CSTAMakePredictiveCall() {
	}

	public CSTAMakePredictiveCall(final String _callingDevice,
			final String _calledDevice, final short _allocationState) {
		callingDevice = _callingDevice;
		calledDevice = _calledDevice;
		allocationState = _allocationState;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		callingDevice = ASNIA5String.decode(memberStream);
		calledDevice = ASNIA5String.decode(memberStream);
		allocationState = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(callingDevice, memberStream);
		ASNIA5String.encode(calledDevice, memberStream);
		ASNEnumerated.encode(allocationState, memberStream);
	}

	public short getAllocationState() {
		return allocationState;
	}

	public String getCalledDevice() {
		return calledDevice;
	}

	public String getCallingDevice() {
		return callingDevice;
	}

	@Override
	public int getPDU() {
		return 25;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAMakePredictiveCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines
				.addAll(ASNIA5String.print(callingDevice, "callingDevice",
						indent));
		lines.addAll(ASNIA5String.print(calledDevice, "calledDevice", indent));
		lines.addAll(AllocationState.print(allocationState, "allocationState",
				indent));

		lines.add("}");
		return lines;
	}
}
