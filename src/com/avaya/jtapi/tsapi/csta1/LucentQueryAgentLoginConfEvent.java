package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentQueryAgentLoginConfEvent extends LucentPrivateData {
	int privEventCrossRefID;
	public static final int PDU = 14;

	public static LucentQueryAgentLoginConfEvent decode(final InputStream in) {
		final LucentQueryAgentLoginConfEvent _this = new LucentQueryAgentLoginConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		privEventCrossRefID = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentLoginConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(privEventCrossRefID,
				"privEventCrossRefID", indent));

		lines.add("}");
		return lines;
	}

	public void setPrivEventCrossRefID(final int privEventCrossRefID) {
		this.privEventCrossRefID = privEventCrossRefID;
	}
}
