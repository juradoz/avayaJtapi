package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class CSTACallClearedEvent extends CSTAUnsolicited {
	CSTAConnectionID clearedCall;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 54;

	public static CSTACallClearedEvent decode(final InputStream in) {
		final CSTACallClearedEvent _this = new CSTACallClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		clearedCall = CSTAConnectionID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(clearedCall, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public short getCause() {
		return cause;
	}

	public CSTAConnectionID getClearedCall() {
		return clearedCall;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 54;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTACallClearedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines
				.addAll(CSTAConnectionID.print(clearedCall, "clearedCall",
						indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCause(final short cause) {
		this.cause = cause;
	}

	public void setClearedCall(final CSTAConnectionID clearedCall) {
		this.clearedCall = clearedCall;
	}

	public void setLocalConnectionInfo(final short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}
}
