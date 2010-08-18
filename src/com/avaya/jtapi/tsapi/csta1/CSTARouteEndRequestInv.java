package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTARouteEndRequestInv extends CSTARequest {
	int routeRegisterReqID;
	int routingCrossRefID;
	short errorValue;
	public static final int PDU = 133;

	public static CSTARouteEndRequestInv decode(final InputStream in) {
		final CSTARouteEndRequestInv _this = new CSTARouteEndRequestInv();
		_this.doDecode(in);

		return _this;
	}

	public CSTARouteEndRequestInv() {
	}

	public CSTARouteEndRequestInv(final int _routeRegisterReqID,
			final int _routingCrossRefID, final short _errorValue) {
		routeRegisterReqID = _routeRegisterReqID;
		routingCrossRefID = _routingCrossRefID;
		errorValue = _errorValue;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
		routingCrossRefID = ASNInteger.decode(memberStream);
		errorValue = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(routeRegisterReqID, memberStream);
		ASNInteger.encode(routingCrossRefID, memberStream);
		ASNEnumerated.encode(errorValue, memberStream);
	}

	public short getErrorValue() {
		return errorValue;
	}

	@Override
	public int getPDU() {
		return 133;
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
		lines.add("CSTARouteEndRequestInv ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));
		lines.addAll(ASNInteger.print(routingCrossRefID, "routingCrossRefID",
				indent));
		lines.addAll(CSTAUniversalFailure.print(errorValue, "errorValue",
				indent));

		lines.add("}");
		return lines;
	}
}
