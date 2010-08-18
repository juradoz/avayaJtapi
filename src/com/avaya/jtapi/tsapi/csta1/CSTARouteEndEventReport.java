package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTARouteEndEventReport extends CSTAEventReport {
	int routeRegisterReqID;
	int routingCrossRefID;
	short cause;
	public static final int PDU = 87;

	public static CSTARouteEndEventReport decode(final InputStream in) {
		final CSTARouteEndEventReport _this = new CSTARouteEndEventReport();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
		routingCrossRefID = ASNInteger.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(routeRegisterReqID, memberStream);
		ASNInteger.encode(routingCrossRefID, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public short getCause() {
		return cause;
	}

	@Override
	public int getPDU() {
		return 87;
	}

	public int getRouteRegisterReqID() {
		return routeRegisterReqID;
	}

	public int getRoutingCrossRefID() {
		return routingCrossRefID;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteEndEventReport ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));
		lines.addAll(ASNInteger.print(routingCrossRefID, "routingCrossRefID",
				indent));
		lines.addAll(CSTAUniversalFailure.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCause(final short cause) {
		this.cause = cause;
	}

	public void setRouteRegisterReqID(final int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}

	public void setRoutingCrossRefID(final int routingCrossRefID) {
		this.routingCrossRefID = routingCrossRefID;
	}
}
