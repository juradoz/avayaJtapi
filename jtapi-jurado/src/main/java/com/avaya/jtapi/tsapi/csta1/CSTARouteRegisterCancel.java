package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTARouteRegisterCancel extends CSTARequest {
	int routeRegisterReqID;
	public static final int PDU = 80;

	public CSTARouteRegisterCancel() {
	}

	public CSTARouteRegisterCancel(int _routeRegisterReqID) {
		this.routeRegisterReqID = _routeRegisterReqID;
	}

	public static CSTARouteRegisterCancel decode(InputStream in) {
		CSTARouteRegisterCancel _this = new CSTARouteRegisterCancel();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRegisterCancel ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID,
				"routeRegisterReqID", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 80;
	}

	public int getRouteRegisterReqID() {
		return this.routeRegisterReqID;
	}

	public void setRouteRegisterReqID(int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}
}