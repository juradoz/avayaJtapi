package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTARouteRegisterCancelConfEvent extends CSTAConfirmation {
	int routeRegisterReqID;
	public static final int PDU = 81;

	public static CSTARouteRegisterCancelConfEvent decode(final InputStream in) {
		final CSTARouteRegisterCancelConfEvent _this = new CSTARouteRegisterCancelConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(routeRegisterReqID, memberStream);
	}

	@Override
	public int getPDU() {
		return 81;
	}

	public int getRouteRegisterReqID() {
		return routeRegisterReqID;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRegisterCancelConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));

		lines.add("}");
		return lines;
	}

	public void setRouteRegisterReqID(final int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}
}
