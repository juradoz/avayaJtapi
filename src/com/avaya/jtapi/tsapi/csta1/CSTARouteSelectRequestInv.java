package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

public final class CSTARouteSelectRequestInv extends CSTARequest {
	int routeRegisterReqID;
	int routingCrossRefID;
	String routeSelected;
	int remainRetry;
	byte[] setupInformation;
	boolean routeUsedReq;
	public static final int PDU = 132;

	public static CSTARouteSelectRequestInv decode(InputStream in) {
		CSTARouteSelectRequestInv _this = new CSTARouteSelectRequestInv();
		_this.doDecode(in);

		return _this;
	}

	public CSTARouteSelectRequestInv() {
	}

	public CSTARouteSelectRequestInv(int _routeRegisterReqID,
			int _routingCrossRefID, String _routeSelected, int _remainRetry,
			byte[] _setupInformation, boolean _routeUsedReq) {
		routeRegisterReqID = _routeRegisterReqID;
		routingCrossRefID = _routingCrossRefID;
		routeSelected = _routeSelected;
		remainRetry = _remainRetry;
		setupInformation = _setupInformation;
		routeUsedReq = _routeUsedReq;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
		routingCrossRefID = ASNInteger.decode(memberStream);
		routeSelected = ASNIA5String.decode(memberStream);
		remainRetry = ASNInteger.decode(memberStream);
		setupInformation = ASNOctetString.decode(memberStream);
		routeUsedReq = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(routeRegisterReqID, memberStream);
		ASNInteger.encode(routingCrossRefID, memberStream);
		ASNIA5String.encode(routeSelected, memberStream);
		ASNInteger.encode(remainRetry, memberStream);
		ASNOctetString.encode(setupInformation, memberStream);
		ASNBoolean.encode(routeUsedReq, memberStream);
	}

	@Override
	public int getPDU() {
		return 132;
	}

	public int getRemainRetry() {
		return remainRetry;
	}

	public int getRouteRegisterReqID() {
		return routeRegisterReqID;
	}

	public String getRouteSelected() {
		return routeSelected;
	}

	public int getRoutingCrossRefID() {
		return routingCrossRefID;
	}

	public byte[] getSetupInformation() {
		return setupInformation;
	}

	public boolean isRouteUsedReq() {
		return routeUsedReq;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("CSTARouteSelectRequestInv ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));
		lines.addAll(ASNInteger.print(routingCrossRefID, "routingCrossRefID",
				indent));
		lines
				.addAll(ASNIA5String.print(routeSelected, "routeSelected",
						indent));
		lines.addAll(RetryValue.print(remainRetry, "remainRetry", indent));
		lines.addAll(ASNOctetString.print(setupInformation, "setupInformation",
				indent));
		lines.addAll(ASNBoolean.print(routeUsedReq, "routeUsedReq", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTARouteSelectRequestInv JD-Core Version: 0.5.4
 */