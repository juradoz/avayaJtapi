package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentEnteredDigitsEvent extends LucentPrivateData {
	CSTAConnectionID connection_asn;
	String digits;
	short localConnectionInfo;
	short cause;
	static final int PDU = 38;

	public static LucentEnteredDigitsEvent decode(InputStream in) {
		LucentEnteredDigitsEvent _this = new LucentEnteredDigitsEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.connection_asn = CSTAConnectionID.decode(memberStream);
		this.digits = ASNIA5String.decode(memberStream);
		this.localConnectionInfo = LocalConnectionState.decode(memberStream);
		this.cause = CSTAEventCause.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.connection_asn, memberStream);
		ASNIA5String.encode(this.digits, memberStream);
		LocalConnectionState.encode(this.localConnectionInfo, memberStream);
		CSTAEventCause.encode(this.cause, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentEnteredDigitsEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.connection_asn, "connection",
				indent));
		lines.addAll(ASNIA5String.print(this.digits, "digits", indent));
		lines.addAll(LocalConnectionState.print(this.localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 38;
	}

	public short getCause() {
		return this.cause;
	}

	public CSTAConnectionID getConnection_asn() {
		return this.connection_asn;
	}

	public String getDigits() {
		return this.digits;
	}

	public short getLocalConnectionInfo() {
		return this.localConnectionInfo;
	}

	public void setCause(short cause) {
		this.cause = cause;
	}

	public void setConnection_asn(CSTAConnectionID connection_asn) {
		this.connection_asn = connection_asn;
	}

	public void setDigits(String digits) {
		this.digits = digits;
	}

	public void setLocalConnectionInfo(short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}
}