package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTARouteRegisterReq extends CSTARequest {
	String routingDevice;
	public static final int PDU = 78;

	public CSTARouteRegisterReq() {
	}

	public CSTARouteRegisterReq(String _routingDevice) {
		this.routingDevice = _routingDevice;
	}

	public static CSTARouteRegisterReq decode(InputStream in) {
		CSTARouteRegisterReq _this = new CSTARouteRegisterReq();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.routingDevice = DeviceID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.routingDevice, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRegisterReq ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID
				.print(this.routingDevice, "routingDevice", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 78;
	}

	public String getRoutingDevice() {
		return this.routingDevice;
	}
}