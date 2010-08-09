package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

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

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		pendingWorkMode = ASNEnumerated.decode(memberStream);
		pendingReasonCode = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNEnumerated.encode(pendingWorkMode, memberStream);
		ASNInteger.encode(pendingReasonCode, memberStream);
	}

	@Override
	public int getPDU() {
		return 104;
	}

	public int getPendingReasonCode() {
		return pendingReasonCode;
	}

	public short getPendingWorkMode() {
		return pendingWorkMode;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentV6QueryAgentStateConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(workMode, "workMode", indent));
		lines.addAll(LucentTalkState.print(talkState, "talkState", indent));
		lines.addAll(ASNInteger.print(reasonCode, "reasonCode", indent));
		lines.addAll(LucentWorkMode.print(pendingWorkMode, "pendingWorkMode",
				indent));
		lines.addAll(ASNInteger.print(pendingReasonCode, "pendingReasonCode",
				indent));

		lines.add("}");
		return lines;
	}

	public void setPendingReasonCode(int pendingReasonCode) {
		this.pendingReasonCode = pendingReasonCode;
	}

	public void setPendingWorkMode(short pendingWorkMode) {
		this.pendingWorkMode = pendingWorkMode;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV6QueryAgentStateConfEvent JD-Core Version:
 * 0.5.4
 */