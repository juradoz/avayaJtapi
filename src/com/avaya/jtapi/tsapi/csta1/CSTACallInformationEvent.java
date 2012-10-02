package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTACallInformationEvent extends CSTAUnsolicited {
	CSTAConnectionID connection;
	CSTAExtendedDeviceID device;
	String accountInfo;
	String authorisationCode;
	static final int PDU = 68;

	public static CSTACallInformationEvent decode(InputStream in) {
		CSTACallInformationEvent _this = new CSTACallInformationEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.connection = CSTAConnectionID.decode(memberStream);
		this.device = CSTAExtendedDeviceID.decode(memberStream);
		this.accountInfo = AccountInfo.decode(memberStream);
		this.authorisationCode = AuthCode.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTACallInformationEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(this.connection, "connection",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.device, "device", indent));
		lines.addAll(AccountInfo.print(this.accountInfo, "accountInfo", indent));
		lines.addAll(AuthCode.print(this.authorisationCode,
				"authorisationCode", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 68;
	}

	public String getAccountInfo() {
		return this.accountInfo;
	}

	public String getAuthorisationCode() {
		return this.authorisationCode;
	}

	public CSTAConnectionID getConnection() {
		return this.connection;
	}

	public CSTAExtendedDeviceID getDevice() {
		return this.device;
	}
}