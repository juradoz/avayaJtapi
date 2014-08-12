package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

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

	public static CSTARouteRequestEv decode(InputStream in) {
		CSTARouteRequestEv _this = new CSTARouteRequestEv();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
		this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
		this.currentRoute = DeviceID.decode(memberStream);
		this.callingDevice = DeviceID.decode(memberStream);
		this.routedCall = CSTAConnectionID.decode(memberStream);
		this.routedSelAlgorithm = SelectValue.decode(memberStream);
		this.priority = ASNBoolean.decode(memberStream);
		this.setupInformation = SetUpValues.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRequestEv ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID,
				"routeRegisterReqID", indent));
		lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID,
				"routingCrossRefID", indent));
		lines.addAll(DeviceID.print(this.currentRoute, "currentRoute", indent));
		lines.addAll(DeviceID
				.print(this.callingDevice, "callingDevice", indent));
		lines.addAll(CSTAConnectionID.print(this.routedCall, "routedCall",
				indent));
		lines.addAll(SelectValue.print(this.routedSelAlgorithm,
				"routedSelAlgorithm", indent));
		lines.addAll(ASNBoolean.print(this.priority, "priority", indent));
		lines.addAll(SetUpValues.print(this.setupInformation,
				"setupInformation", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 83;
	}

	public String getCallingDevice() {
		return this.callingDevice;
	}

	public String getCurrentRoute() {
		return this.currentRoute;
	}

	public boolean isPriority() {
		return this.priority;
	}

	public CSTAConnectionID getRoutedCall() {
		return this.routedCall;
	}

	public short getRoutedSelAlgorithm() {
		return this.routedSelAlgorithm;
	}

	public int getRouteRegisterReqID() {
		return this.routeRegisterReqID;
	}

	public int getRoutingCrossRefID() {
		return this.routingCrossRefID;
	}

	public byte[] getSetupInformation() {
		return this.setupInformation;
	}

	public void setRouteRegisterReqID(int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}

	public void setRoutingCrossRefID(int routingCrossRefID) {
		this.routingCrossRefID = routingCrossRefID;
	}

	public void setCurrentRoute(String currentRoute) {
		this.currentRoute = currentRoute;
	}

	public void setRoutedCall(CSTAConnectionID routedCall) {
		this.routedCall = routedCall;
	}

	public void setRoutedSelAlgorithm(short routedSelAlgorithm) {
		this.routedSelAlgorithm = routedSelAlgorithm;
	}

	public void setPriority(boolean priority) {
		this.priority = priority;
	}

	public void setCallingDevice(String callingDevice) {
		this.callingDevice = callingDevice;
	}
}