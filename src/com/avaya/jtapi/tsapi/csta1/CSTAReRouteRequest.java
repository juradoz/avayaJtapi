package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTAReRouteRequest extends CSTARequest {
	int routeRegisterReqID;
	int routingCrossRefID;
	public static final int PDU = 85;

	public static CSTAReRouteRequest decode(final InputStream in) {
		final CSTAReRouteRequest _this = new CSTAReRouteRequest();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
		routingCrossRefID = ASNInteger.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 85;
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

		lines.add("CSTAReRouteRequest ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));
		lines.addAll(ASNInteger.print(routingCrossRefID, "routingCrossRefID",
				indent));

		lines.add("}");
		return lines;
	}

	public void setRouteRegisterReqID(final int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}

	public void setRoutingCrossRefID(final int routingCrossRefID) {
		this.routingCrossRefID = routingCrossRefID;
	}
}
