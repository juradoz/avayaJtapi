package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentPrivateRouteUsedEvent extends LucentPrivateData {
	String destRoute_asn;
	public static final int PDU = 44;

	static LucentPrivateRouteUsedEvent decode(final InputStream in) {
		final LucentPrivateRouteUsedEvent _this = new LucentPrivateRouteUsedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		destRoute_asn = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(destRoute_asn, memberStream);
	}

	public String getDestRoute_asn() {
		return destRoute_asn;
	}

	@Override
	public int getPDU() {
		return 44;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("LucentPrivateRouteUsedEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(destRoute_asn, "destRoute", indent));

		lines.add("}");
		return lines;
	}

	public void setDestRoute_asn(final String destRoute_asn) {
		this.destRoute_asn = destRoute_asn;
	}
}
