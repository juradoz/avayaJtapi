package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTARouteRegisterReq extends CSTARequest {
	String routingDevice;
	public static final int PDU = 78;

	public static CSTARouteRegisterReq decode(InputStream in) {
		CSTARouteRegisterReq _this = new CSTARouteRegisterReq();
		_this.doDecode(in);

		return _this;
	}

	public CSTARouteRegisterReq() {
	}

	public CSTARouteRegisterReq(String _routingDevice) {
		routingDevice = _routingDevice;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		routingDevice = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTARouteRegisterReq ::=");
		lines.add("{");

		String indent = "  ";

		lines
				.addAll(ASNIA5String.print(routingDevice, "routingDevice",
						indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterReq JD-Core Version: 0.5.4
 */