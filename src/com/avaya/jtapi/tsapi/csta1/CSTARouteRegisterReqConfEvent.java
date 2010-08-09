package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTARouteRegisterReqConfEvent extends CSTAConfirmation {
	int registerReqID;
	public static final int PDU = 79;

	public static CSTARouteRegisterReqConfEvent decode(InputStream in) {
		CSTARouteRegisterReqConfEvent _this = new CSTARouteRegisterReqConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		registerReqID = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("CSTARouteRegisterReqConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(registerReqID, "registerReqID", indent));

		lines.add("}");
		return lines;
	}

	public void setRegisterReqID(int registerReqID) {
		this.registerReqID = registerReqID;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterReqConfEvent JD-Core Version:
 * 0.5.4
 */