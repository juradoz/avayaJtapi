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

	public static CSTARouteEndRequest decode(InputStream in) {
		CSTARouteEndRequest _this = new CSTARouteEndRequest();
		_this.doDecode(in);

		return _this;
	}

	public CSTARouteEndRequest() {
	}

	public CSTARouteEndRequest(int _routeRegisterReqID, int _routingCrossRefID,
			short _errorValue) {
		routeRegisterReqID = _routeRegisterReqID;
		routingCrossRefID = _routingCrossRefID;
		errorValue = _errorValue;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
		routingCrossRefID = ASNInteger.decode(memberStream);
		errorValue = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();
		lines.add("CSTARouteEndRequest ::=");
		lines.add("{");

		String indent = "  ";

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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTARouteEndRequest JD-Core Version: 0.5.4
 */