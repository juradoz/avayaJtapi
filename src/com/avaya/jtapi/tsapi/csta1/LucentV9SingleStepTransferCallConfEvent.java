package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV9SingleStepTransferCallConfEvent extends
		LucentSingleStepTransferCallConfEvent {
	static final int PDU = 148;
	private String ucid;

	public static LucentV9SingleStepTransferCallConfEvent decode(InputStream in) {
		LucentV9SingleStepTransferCallConfEvent lucentV9SingleStepTransferCallConfEvent = new LucentV9SingleStepTransferCallConfEvent();
		lucentV9SingleStepTransferCallConfEvent.doDecode(in);
		return lucentV9SingleStepTransferCallConfEvent;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.ucid = UCID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		UCID.encode(this.ucid, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV9SingleStepTransferCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.transferredCall,
				"transferredCall", indent));

		lines.addAll(UCID.print(this.ucid, "ucid", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 148;
	}

	public String getUcid() {
		return this.ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
}