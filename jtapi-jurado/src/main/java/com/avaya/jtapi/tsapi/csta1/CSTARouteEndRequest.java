package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTARouteEndRequest extends CSTARequest {
	int routeRegisterReqID;
	int routingCrossRefID;
	short errorValue;
	public static final int PDU = 88;

	public CSTARouteEndRequest() {
	}

	public CSTARouteEndRequest(int _routeRegisterReqID, int _routingCrossRefID,
			short _errorValue) {
		this.routeRegisterReqID = _routeRegisterReqID;
		this.routingCrossRefID = _routingCrossRefID;
		this.errorValue = _errorValue;
	}

	public static CSTARouteEndRequest decode(InputStream in) {
		CSTARouteEndRequest _this = new CSTARouteEndRequest();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
		this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
		this.errorValue = CSTAUniversalFailure.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
		RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
		CSTAUniversalFailure.encode(this.errorValue, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTARouteEndRequest ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID,
				"routeRegisterReqID", indent));
		lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID,
				"routingCrossRefID", indent));
		lines.addAll(CSTAUniversalFailure.print(this.errorValue, "errorValue",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 88;
	}

	public short getErrorValue() {
		return this.errorValue;
	}

	public int getRouteRegisterReqID() {
		return this.routeRegisterReqID;
	}

	public int getRoutingCrossRefID() {
		return this.routingCrossRefID;
	}
}