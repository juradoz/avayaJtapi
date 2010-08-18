package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTACallInformationEvent extends CSTAUnsolicited {
	CSTAConnectionID connection;
	CSTAExtendedDeviceID device;
	String accountInfo;
	String authorisationCode;
	static final int PDU = 68;

	public static CSTACallInformationEvent decode(final InputStream in) {
		final CSTACallInformationEvent _this = new CSTACallInformationEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		connection = CSTAConnectionID.decode(memberStream);
		device = CSTAExtendedDeviceID.decode(memberStream);
		accountInfo = ASNIA5String.decode(memberStream);
		authorisationCode = ASNIA5String.decode(memberStream);
	}

	public String getAccountInfo() {
		return accountInfo;
	}

	public String getAuthorisationCode() {
		return authorisationCode;
	}

	public CSTAConnectionID getConnection() {
		return connection;
	}

	public CSTAExtendedDeviceID getDevice() {
		return device;
	}

	@Override
	public int getPDU() {
		return 68;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTACallInformationEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(connection, "connection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(device, "device", indent));
		lines.addAll(ASNIA5String.print(accountInfo, "accountInfo", indent));
		lines.addAll(ASNIA5String.print(authorisationCode, "authorisationCode",
				indent));

		lines.add("}");
		return lines;
	}
}
