package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV6QueryAgentStateConfEvent extends
		LucentV5QueryAgentStateConfEvent {
	short pendingWorkMode;
	int pendingReasonCode;
	public static final int PDU = 104;

	public static LucentQueryAgentStateConfEvent decode(InputStream in) {
		LucentV6QueryAgentStateConfEvent _this = new LucentV6QueryAgentStateConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.pendingWorkMode = LucentWorkMode.decode(memberStream);
		this.pendingReasonCode = ASNInteger.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		LucentWorkMode.encode(this.pendingWorkMode, memberStream);
		ASNInteger.encode(this.pendingReasonCode, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6QueryAgentStateConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
		lines.addAll(LucentTalkState.print(this.talkState, "talkState", indent));
		lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));
		lines.addAll(LucentWorkMode.print(this.pendingWorkMode,
				"pendingWorkMode", indent));
		lines.addAll(ASNInteger.print(this.pendingReasonCode,
				"pendingReasonCode", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 104;
	}

	public int getPendingReasonCode() {
		return this.pendingReasonCode;
	}

	public short getPendingWorkMode() {
		return this.pendingWorkMode;
	}

	public void setPendingReasonCode(int pendingReasonCode) {
		this.pendingReasonCode = pendingReasonCode;
	}

	public void setPendingWorkMode(short pendingWorkMode) {
		this.pendingWorkMode = pendingWorkMode;
	}
}