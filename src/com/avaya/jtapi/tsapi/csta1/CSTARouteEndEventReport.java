package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTARouteEndEventReport extends CSTAEventReport {
	int routeRegisterReqID;
	int routingCrossRefID;
	short cause;
	public static final int PDU = 87;

	public static CSTARouteEndEventReport decode(InputStream in) {
		CSTARouteEndEventReport _this = new CSTARouteEndEventReport();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
		this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
		this.cause = CSTAUniversalFailure.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
		RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
		CSTAUniversalFailure.encode(this.cause, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteEndEventReport ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID,
				"routeRegisterReqID", indent));
		lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID,
				"routingCrossRefID", indent));
		lines.addAll(CSTAUniversalFailure.print(this.cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 87;
	}

	public short getCause() {
		return this.cause;
	}

	public int getRouteRegisterReqID() {
		return this.routeRegisterReqID;
	}

	public int getRoutingCrossRefID() {
		return this.routingCrossRefID;
	}

	public void setCause(short cause) {
		this.cause = cause;
	}

	public void setRouteRegisterReqID(int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}

	public void setRoutingCrossRefID(int routingCrossRefID) {
		this.routingCrossRefID = routingCrossRefID;
	}
}