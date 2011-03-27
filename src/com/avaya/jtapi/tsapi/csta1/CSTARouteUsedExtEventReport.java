package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTARouteUsedExtEventReport extends CSTAEventReport {
	int routeRegisterReqID;
	int routingCrossRefID;
	CSTAExtendedDeviceID routeUsed;
	CSTAExtendedDeviceID callingDevice;
	boolean domain;
	public static final int PDU = 131;

	public static CSTARouteUsedExtEventReport decode(final InputStream in) {
		final CSTARouteUsedExtEventReport _this = new CSTARouteUsedExtEventReport();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
		routingCrossRefID = ASNInteger.decode(memberStream);
		routeUsed = CSTAExtendedDeviceID.decode(memberStream);
		callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		domain = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(routeRegisterReqID, memberStream);
		ASNInteger.encode(routingCrossRefID, memberStream);
		ASNSequence.encode(routeUsed, memberStream);
		ASNSequence.encode(callingDevice, memberStream);
		ASNBoolean.encode(domain, memberStream);
	}

	public CSTAExtendedDeviceID getCallingDevice() {
		return callingDevice;
	}

	@Override
	public int getPDU() {
		return 131;
	}

	public int getRouteRegisterReqID() {
		return routeRegisterReqID;
	}

	public CSTAExtendedDeviceID getRouteUsed() {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteUsedExtEventReport ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));
		lines.addAll(ASNInteger.print(routingCrossRefID, "routingCrossRefID",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(routeUsed, "routeUsed", indent));
		lines.addAll(CSTAExtendedDeviceID.print(callingDevice, "callingDevice",
				indent));
		lines.addAll(ASNBoolean.print(domain, "domain", indent));

		lines.add("}");
		return lines;
	}

	public void setCallingDevice(final CSTAExtendedDeviceID callingDevice) {
		this.callingDevice = callingDevice;
	}

	public void setDomain(final boolean domain) {
		this.domain = domain;
	}

	public void setRouteRegisterReqID(final int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}

	public void setRouteUsed(final CSTAExtendedDeviceID routeUsed) {
		this.routeUsed = routeUsed;
	}

	public void setRoutingCrossRefID(final int routingCrossRefID) {
		this.routingCrossRefID = routingCrossRefID;
	}
}
