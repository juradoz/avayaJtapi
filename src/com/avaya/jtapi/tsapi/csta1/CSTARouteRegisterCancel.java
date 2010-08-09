package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTARouteRegisterCancel extends CSTARequest {
	int routeRegisterReqID;
	public static final int PDU = 80;

	public static CSTARouteRegisterCancel decode(InputStream in) {
		CSTARouteRegisterCancel _this = new CSTARouteRegisterCancel();
		_this.doDecode(in);

		return _this;
	}

	public CSTARouteRegisterCancel() {
	}

	public CSTARouteRegisterCancel(int _routeRegisterReqID) {
		routeRegisterReqID = _routeRegisterReqID;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(routeRegisterReqID, memberStream);
	}

	@Override
	public int getPDU() {
		return 80;
	}

	public int getRouteRegisterReqID() {
		return routeRegisterReqID;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("CSTARouteRegisterCancel ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));

		lines.add("}");
		return lines;
	}

	public void setRouteRegisterReqID(int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterCancel JD-Core Version: 0.5.4
 */