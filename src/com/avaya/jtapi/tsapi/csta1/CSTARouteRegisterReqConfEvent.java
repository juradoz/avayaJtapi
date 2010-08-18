package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTARouteRegisterReqConfEvent extends CSTAConfirmation {
	int registerReqID;
	public static final int PDU = 79;

	public static CSTARouteRegisterReqConfEvent decode(final InputStream in) {
		final CSTARouteRegisterReqConfEvent _this = new CSTARouteRegisterReqConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		registerReqID = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(registerReqID, memberStream);
	}

	@Override
	public int getPDU() {
		return 79;
	}

	public int getRegisterReqID() {
		return registerReqID;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRegisterReqConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(registerReqID, "registerReqID", indent));

		lines.add("}");
		return lines;
	}

	public void setRegisterReqID(final int registerReqID) {
		this.registerReqID = registerReqID;
	}
}
