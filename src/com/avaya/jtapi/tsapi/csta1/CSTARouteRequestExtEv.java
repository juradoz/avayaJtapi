package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTARouteRequestExtEv extends CSTARequest {
	int routeRegisterReqID;
	int routingCrossRefID;
	CSTAExtendedDeviceID currentRoute;
	CSTAExtendedDeviceID callingDevice;
	CSTAConnectionID routedCall;
	short routedSelAlgorithm;
	boolean priority;
	byte[] setupInformation;
	public static final int PDU = 130;

	public static CSTARouteRequestExtEv decode(InputStream in) {
		CSTARouteRequestExtEv _this = new CSTARouteRequestExtEv();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.routeRegisterReqID = RouteRegisterReqID.decode(memberStream);
		this.routingCrossRefID = RoutingCrossRefID.decode(memberStream);
		this.currentRoute = CSTAExtendedDeviceID.decode(memberStream);
		this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		this.routedCall = CSTAConnectionID.decode(memberStream);
		this.routedSelAlgorithm = SelectValue.decode(memberStream);
		this.priority = ASNBoolean.decode(memberStream);
		this.setupInformation = SetUpValues.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		RouteRegisterReqID.encode(this.routeRegisterReqID, memberStream);
		RoutingCrossRefID.encode(this.routingCrossRefID, memberStream);
		CSTAExtendedDeviceID.encode(this.currentRoute, memberStream);
		CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
		CSTAConnectionID.encode(this.routedCall, memberStream);
		SelectValue.encode(this.routedSelAlgorithm, memberStream);
		ASNBoolean.encode(this.priority, memberStream);
		SetUpValues.encode(this.setupInformation, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRequestExtEv ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(RouteRegisterReqID.print(this.routeRegisterReqID,
				"routeRegisterReqID", indent));
		lines.addAll(RoutingCrossRefID.print(this.routingCrossRefID,
				"routingCrossRefID", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.currentRoute,
				"currentRoute", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice,
				"callingDevice", indent));
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
		return 130;
	}

	public CSTAExtendedDeviceID getCallingDevice() {
		return this.callingDevice;
	}

	public CSTAExtendedDeviceID getCurrentRoute() {
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

	public void setCallingDevice(CSTAExtendedDeviceID callingDevice) {
		this.callingDevice = callingDevice;
	}

	public void setCurrentRoute(CSTAExtendedDeviceID currentRoute) {
		this.currentRoute = currentRoute;
	}

	public void setPriority(boolean priority) {
		this.priority = priority;
	}

	public void setRoutedCall(CSTAConnectionID routedCall) {
		this.routedCall = routedCall;
	}

	public void setRoutedSelAlgorithm(short routedSelAlgorithm) {
		this.routedSelAlgorithm = routedSelAlgorithm;
	}

	public void setRouteRegisterReqID(int routeRegisterReqID) {
		this.routeRegisterReqID = routeRegisterReqID;
	}

	public void setRoutingCrossRefID(int routingCrossRefID) {
		this.routingCrossRefID = routingCrossRefID;
	}

	public void setSetupInformation(byte[] setupInformation) {
		this.setupInformation = setupInformation;
	}
}