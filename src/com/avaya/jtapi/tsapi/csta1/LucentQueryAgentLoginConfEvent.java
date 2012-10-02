package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentQueryAgentLoginConfEvent extends LucentPrivateData {
	int privEventCrossRefID;
	public static final int PDU = 14;

	public static LucentQueryAgentLoginConfEvent decode(InputStream in) {
		LucentQueryAgentLoginConfEvent _this = new LucentQueryAgentLoginConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.privEventCrossRefID = LucentPrivEventCrossRefID
				.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		LucentPrivEventCrossRefID
				.encode(this.privEventCrossRefID, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentLoginConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentPrivEventCrossRefID.print(this.privEventCrossRefID,
				"privEventCrossRefID", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 14;
	}

	public int getPrivEventCrossRefID() {
		return this.privEventCrossRefID;
	}

	public void setPrivEventCrossRefID(int privEventCrossRefID) {
		this.privEventCrossRefID = privEventCrossRefID;
	}
}