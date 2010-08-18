package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ACSNameSrvRequest extends ACSRequest {
	public static ACSNameSrvRequest decode(final InputStream in) {
		final ACSNameSrvRequest _this = new ACSNameSrvRequest();
		_this.doDecode(in);

		return _this;
	}

	short streamType;

	public static final int PDU = 10;

	public ACSNameSrvRequest() {
	}

	public ACSNameSrvRequest(final short _streamType) {
		streamType = _streamType;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(streamType, memberStream);
	}

	@Override
	public int getPDU() {
		return 10;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSNameSrvRequest ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(StreamType.print(streamType, "streamType", indent));

		lines.add("}");
		return lines;
	}
}
