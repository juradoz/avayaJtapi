package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentQueryAgentLoginConfEvent extends LucentPrivateData {
	int privEventCrossRefID;
	public static final int PDU = 14;

	public static LucentQueryAgentLoginConfEvent decode(InputStream in) {
		LucentQueryAgentLoginConfEvent _this = new LucentQueryAgentLoginConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		privEventCrossRefID = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(privEventCrossRefID, memberStream);
	}

	@Override
	public int getPDU() {
		return 14;
	}

	public int getPrivEventCrossRefID() {
		return privEventCrossRefID;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentLoginConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(privEventCrossRefID,
				"privEventCrossRefID", indent));

		lines.add("}");
		return lines;
	}

	public void setPrivEventCrossRefID(int privEventCrossRefID) {
		this.privEventCrossRefID = privEventCrossRefID;
	}
}
