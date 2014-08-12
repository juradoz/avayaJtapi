package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentTransferCallConfEvent extends LucentPrivateData
		implements HasUCID {
	String ucid;
	static final int PDU = 91;

	public static LucentTransferCallConfEvent decode(InputStream in) {
		LucentTransferCallConfEvent _this = new LucentTransferCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.ucid = UCID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		UCID.encode(this.ucid, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentTransferCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(UCID.print(this.ucid, "ucid", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 91;
	}

	public String getUcid() {
		return this.ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
}