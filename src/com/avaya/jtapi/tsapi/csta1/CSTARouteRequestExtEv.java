package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

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

	@Override
	public void decodeMembers(InputStream memberStream) {
		routeRegisterReqID = ASNInteger.decode(memberStream);
		routingCrossRefID = ASNInteger.decode(memberStream);
		currentRoute = CSTAExtendedDeviceID.decode(memberStream);
		callingDevice = CSTAExtendedDeviceID.decode(memberStream);
		routedCall = CSTAConnectionID.decode(memberStream);
		routedSelAlgorithm = ASNEnumerated.decode(memberStream);
		priority = ASNBoolean.decode(memberStream);
		setupInformation = ASNOctetString.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(routeRegisterReqID, memberStream);
		ASNInteger.encode(routingCrossRefID, memberStream);
		ASNSequence.encode(currentRoute, memberStream);
		ASNSequence.encode(callingDevice, memberStream);
		CSTAConnectionID.encode(routedCall, memberStream);
		ASNEnumerated.encode(routedSelAlgorithm, memberStream);
		ASNBoolean.encode(priority, memberStream);
		ASNOctetString.encode(setupInformation, memberStream);
	}

	public CSTAExtendedDeviceID getCallingDevice() {
		return callingDevice;
	}

	public CSTAExtendedDeviceID getCurrentRoute() {
		return currentRoute;
	}

	@Override
	public int getPDU() {
		return 130;
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
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRequestExtEv ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(routeRegisterReqID, "routeRegisterReqID",
				indent));
		lines.addAll(ASNInteger.print(routingCrossRefID, "routingCrossRefID",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(currentRoute, "currentRoute",
				indent));
		lines.addAll(CSTAExtendedDeviceID.print(callingDevice, "callingDevice",
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTARouteRequestExtEv JD-Core Version: 0.5.4
 */