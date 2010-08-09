package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAOutOfServiceEvent extends CSTAUnsolicited {
	String device;
	short cause;
	static final int PDU = 97;

	static CSTAOutOfServiceEvent decode(InputStream in) {
		CSTAOutOfServiceEvent _this = new CSTAOutOfServiceEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	public short getCause() {
		return cause;
	}

	public String getDevice() {
		return device;
	}

	@Override
	public int getPDU() {
		return 97;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("CSTAOutOfServiceEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(ASNIA5String.print(device, "device", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAOutOfServiceEvent JD-Core Version: 0.5.4
 */