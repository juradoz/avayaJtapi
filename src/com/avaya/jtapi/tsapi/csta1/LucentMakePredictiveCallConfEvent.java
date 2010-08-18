package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentMakePredictiveCallConfEvent extends LucentPrivateData
		implements HasUCID {
	String ucid;
	static final int PDU = 86;

	public static LucentMakePredictiveCallConfEvent decode(InputStream in) {
		LucentMakePredictiveCallConfEvent _this = new LucentMakePredictiveCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		ucid = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(ucid, memberStream);
	}

	@Override
	public int getPDU() {
		return 86;
	}

	public String getUcid() {
		return ucid;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMakePredictiveCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));

		lines.add("}");
		return lines;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
}
