package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTARouteRegisterReq extends CSTARequest {
	String routingDevice;
	public static final int PDU = 78;

	public static CSTARouteRegisterReq decode(final InputStream in) {
		final CSTARouteRegisterReq _this = new CSTARouteRegisterReq();
		_this.doDecode(in);

		return _this;
	}

	public CSTARouteRegisterReq() {
	}

	public CSTARouteRegisterReq(final String _routingDevice) {
		routingDevice = _routingDevice;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		routingDevice = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(routingDevice, memberStream);
	}

	@Override
	public int getPDU() {
		return 78;
	}

	public String getRoutingDevice() {
		return routingDevice;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRegisterReq ::=");
		lines.add("{");

		final String indent = "  ";

		lines
				.addAll(ASNIA5String.print(routingDevice, "routingDevice",
						indent));

		lines.add("}");
		return lines;
	}
}
