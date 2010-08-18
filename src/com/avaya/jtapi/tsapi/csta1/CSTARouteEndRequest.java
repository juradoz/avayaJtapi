package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTARouteEndRequest extends CSTARequest {
	int routeRegisterReqID;
	int routingCrossRefID;
	short errorValue;
	public static final int PDU = 88;

	public static CSTARouteEndRequest decode(final InputStream in) {
		final CSTARouteEndRequest _this = new CSTARouteEndRequest();
		_this.doDecode(in);

		return _this;
	}

	public CSTARouteEndRequest() {
	}

	public CSTARouteEndRequest(final int _routeRegisterReqID,
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
		return 88;
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
		lines.add("CSTARouteEndRequest ::=");
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
