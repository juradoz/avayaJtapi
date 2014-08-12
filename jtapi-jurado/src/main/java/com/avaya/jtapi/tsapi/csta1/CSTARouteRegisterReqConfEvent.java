package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTARouteRegisterReqConfEvent extends CSTAConfirmation {
	int registerReqID;
	public static final int PDU = 79;

	public static CSTARouteRegisterReqConfEvent decode(InputStream in) {
		CSTARouteRegisterReqConfEvent _this = new CSTARouteRegisterReqConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.registerReqID = RouteRegisterReqID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		RouteRegisterReqID.encode(this.registerReqID, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRegisterReqConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(RouteRegisterReqID.print(this.registerReqID,
				"registerReqID", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 79;
	}

	public int getRegisterReqID() {
		return this.registerReqID;
	}

	public void setRegisterReqID(int registerReqID) {
		this.registerReqID = registerReqID;
	}
}