package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

public final class CSTARouteRequestEv extends CSTARequest {
	int routeRegisterReqID;
	int routingCrossRefID;
	String currentRoute;
	String callingDevice;
	CSTAConnectionID routedCall;
	short routedSelAlgorithm;
	boolean priority;
	byte[] setupInformation;
	public static final int PDU = 83;

	public static CSTARouteRequestEv decode(final InputStream in) {
		final CSTARouteRequestEv _this = new CSTARouteRequestEv();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
		routingCrossRefID = ASNInteger.decode(memberStream);
		currentRoute = ASNIA5String.decode(memberStream);
		callingDevice = ASNIA5String.decode(memberStream);
		routedCall = CSTAConnectionID.decode(memberStream);
		routedSelAlgorithm = ASNEnumerated.decode(memberStream);
		priority = ASNBoolean.decode(memberStream);
		setupInformation = ASNOctetString.decode(memberStream);
	}

	public String getCallingDevice() {
		return callingDevice;
	}

	public String getCurrentRoute() {
		return currentRoute;
	}

	@Override
	public int getPDU() {
		return 83;
	}

	public CSTAConnectionID getRoutedCall() {
		return routedCall;
	}

	public short getRoutedSelAlgorithm() {
		return routedSelAlgorithm;
	}

	public int getRouteRegisterReqID() {
		return routeRegisterReqID;
	}

	public int getRoutingCrossRefID() {
		return routingCrossRefID;
	}

	public byte[] getSetupInformation() {
		return setupInformation;
	}

	public boolean isPriority() {
		return priority;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRequestEv ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));
		lines.addAll(ASNInteger.print(routingCrossRefID, "routingCrossRefID",
				indent));
		lines.addAll(ASNIA5String.print(currentRoute, "currentRoute", indent));
		lines
				.addAll(ASNIA5String.print(callingDevice, "callingDevice",
						indent));
		lines.addAll(CSTAConnectionID.print(routedCall, "routedCall", indent));
		lines.addAll(SelectValue.print(routedSelAlgorithm,
				"routedSelAlgorithm", indent));
		lines.addAll(ASNBoolean.print(priority, "priority", indent));
		lines.addAll(ASNOctetString.print(setupInformation, "setupInformation",
				indent));

		lines.add("}");
		return lines;
	}

	public void setCallingDevice(final String callingDevice) {
		this.callingDevice = callingDevice;
	}

	public void setCurrentRoute(final String currentRoute) {
		this.currentRoute = currentRoute;
	}

	public void setPriority(final boolean priority) {
		this.priority = priority;
	}

	public void setRoutedCall(final CSTAConnectionID routedCall) {
		this.routedCall = routedCall;
	}

	public void setRoutedSelAlgorithm(final short routedSelAlgorithm) {
		this.routedSelAlgorithm = routedSelAlgorithm;
	}

	public void setRouteRegisterReqID(final int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}

	public void setRoutingCrossRefID(final int routingCrossRefID) {
		this.routingCrossRefID = routingCrossRefID;
	}
}
