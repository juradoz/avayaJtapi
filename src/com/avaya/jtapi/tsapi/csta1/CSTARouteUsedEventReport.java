package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class CSTARouteUsedEventReport extends CSTAEventReport {
	int routeRegisterReqID;
	int routingCrossRefID;
	String routeUsed;
	String callingDevice;
	boolean domain;
	public static final int PDU = 86;

	public static CSTARouteUsedEventReport decode(InputStream in) {
		CSTARouteUsedEventReport _this = new CSTARouteUsedEventReport();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
		routingCrossRefID = ASNInteger.decode(memberStream);
		routeUsed = ASNIA5String.decode(memberStream);
		callingDevice = ASNIA5String.decode(memberStream);
		domain = ASNBoolean.decode(memberStream);
	}

	public String getCallingDevice() {
		return callingDevice;
	}

	@Override
	public int getPDU() {
		return 86;
	}

	public int getRouteRegisterReqID() {
		return routeRegisterReqID;
	}

	public String getRouteUsed() {
		return routeUsed;
	}

	public int getRoutingCrossRefID() {
		return routingCrossRefID;
	}

	public boolean isDomain() {
		return domain;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteUsedEventReport ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));
		lines.addAll(ASNInteger.print(routingCrossRefID, "routingCrossRefID",
				indent));
		lines.addAll(ASNIA5String.print(routeUsed, "routeUsed", indent));
		lines
				.addAll(ASNIA5String.print(callingDevice, "callingDevice",
						indent));
		lines.addAll(ASNBoolean.print(domain, "domain", indent));

		lines.add("}");
		return lines;
	}

	public void setCallingDevice(String callingDevice) {
		this.callingDevice = callingDevice;
	}

	public void setDomain(boolean domain) {
		this.domain = domain;
	}

	public void setRouteRegisterReqID(int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}

	public void setRouteUsed(String routeUsed) {
		this.routeUsed = routeUsed;
	}

	public void setRoutingCrossRefID(int routingCrossRefID) {
		this.routingCrossRefID = routingCrossRefID;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTARouteUsedEventReport JD-Core Version: 0.5.4
 */