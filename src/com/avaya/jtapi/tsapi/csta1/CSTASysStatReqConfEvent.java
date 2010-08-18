package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class CSTASysStatReqConfEvent extends CSTAConfirmation {
	public static final int PDU = 99;

	public static CSTASysStatReqConfEvent decode(final InputStream in) {
		final CSTASysStatReqConfEvent _this = new CSTASysStatReqConfEvent();
		_this.doDecode(in);

		return _this;
	}

	private short systemStatus;

	@Override
	public void decodeMembers(final InputStream memberStream) {
		systemStatus = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(systemStatus, memberStream);
	}

	@Override
	public int getPDU() {
		return 99;
	}

	public short getSystemStatus() {
		return systemStatus;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTASysStatReqConfEvent ::=");
		lines.add("{");
		final String indent = "  ";
		lines.addAll(SystemStatus.print(systemStatus, "systemStatus", indent));
		lines.add("}");
		return lines;
	}

	public void setSystemStatus(final short systemStatus) {
		this.systemStatus = systemStatus;
	}
}
