package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentMakeCallConfEvent extends LucentPrivateData implements
		HasUCID {
	String ucid;
	static final int PDU = 85;

	public static LucentMakeCallConfEvent decode(final InputStream in) {
		final LucentMakeCallConfEvent _this = new LucentMakeCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		ucid = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(ucid, memberStream);
	}

	@Override
	public int getPDU() {
		return 85;
	}

	@Override
	public String getUcid() {
		return ucid;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMakeCallConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));

		lines.add("}");
		return lines;
	}

	public void setUcid(final String ucid) {
		this.ucid = ucid;
	}
}
