package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTARouteUsedExtEventReport extends CSTAEventReport {
	int routeRegisterReqID;
	int routingCrossRefID;
	CSTAExtendedDeviceID routeUsed;
	CSTAExtendedDeviceID callingDevice;
	boolean domain;
	public static final int PDU = 131;

	public static CSTARouteUsedExtEventReport decode(InputStream in) {
		CSTARouteUsedExtEventReport _this = new CSTARouteUsedExtEventReport();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
		this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
		this.routeUsed = CSTAExtendedDeviceID.decode(memberStream);
		this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.domain = ASNBoolean.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
		RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
		CSTAExtendedDeviceID.encode(this.routeUsed, memberStream);
		CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
		ASNBoolean.encode(this.domain, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteUsedExtEventReport ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID,
				"routeRegisterReqID", indent));
		lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID,
				"routingCrossRefID", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.routeUsed, "routeUsed",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice,
				"callingDevice", indent));
		lines.addAll(ASNBoolean.print(this.domain, "domain", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 131;
	}

	public CSTAExtendedDeviceID getCallingDevice() {
		return this.callingDevice;
	}

	public boolean isDomain() {
		return this.domain;
	}

	public int getRouteRegisterReqID() {
		return this.routeRegisterReqID;
	}

	public CSTAExtendedDeviceID getRouteUsed() {
		return this.routeUsed;
	}

	public int getRoutingCrossRefID() {
		return this.routingCrossRefID;
	}

	public void setCallingDevice(CSTAExtendedDeviceID callingDevice) {
		this.callingDevice = callingDevice;
	}

	public void setDomain(boolean domain) {
		this.domain = domain;
	}

	public void setRouteRegisterReqID(int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}

	public void setRouteUsed(CSTAExtendedDeviceID routeUsed) {
		this.routeUsed = routeUsed;
	}

	public void setRoutingCrossRefID(int routingCrossRefID) {
		this.routingCrossRefID = routingCrossRefID;
	}
}