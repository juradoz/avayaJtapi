package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

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

	public void decodeMembers(InputStream memberStream) {
		this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
		this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
		this.routeUsed = DeviceID.decode(memberStream);
		this.callingDevice = DeviceID.decode(memberStream);
		this.domain = ASNBoolean.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteUsedEventReport ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID,
				"routeRegisterReqID", indent));
		lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID,
				"routingCrossRefID", indent));
		lines.addAll(DeviceID.print(this.routeUsed, "routeUsed", indent));
		lines.addAll(DeviceID
				.print(this.callingDevice, "callingDevice", indent));
		lines.addAll(ASNBoolean.print(this.domain, "domain", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 86;
	}

	public String getCallingDevice() {
		return this.callingDevice;
	}

	public boolean isDomain() {
		return this.domain;
	}

	public int getRouteRegisterReqID() {
		return this.routeRegisterReqID;
	}

	public String getRouteUsed() {
		return this.routeUsed;
	}

	public int getRoutingCrossRefID() {
		return this.routingCrossRefID;
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