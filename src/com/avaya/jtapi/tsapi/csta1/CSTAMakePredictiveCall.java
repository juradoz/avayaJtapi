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

	public static CSTAMakePredictiveCall decode(InputStream in) {
		CSTAMakePredictiveCall _this = new CSTAMakePredictiveCall();
		_this.doDecode(in);

		return _this;
	}

	public CSTAMakePredictiveCall() {
	}

	public CSTAMakePredictiveCall(String _callingDevice, String _calledDevice,
			short _allocationState) {
		callingDevice = _callingDevice;
		calledDevice = _calledDevice;
		allocationState = _allocationState;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		callingDevice = ASNIA5String.decode(memberStream);
		calledDevice = ASNIA5String.decode(memberStream);
		allocationState = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();
		lines.add("CSTAMakePredictiveCall ::=");
		lines.add("{");

		String indent = "  ";

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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAMakePredictiveCall JD-Core Version: 0.5.4
 */