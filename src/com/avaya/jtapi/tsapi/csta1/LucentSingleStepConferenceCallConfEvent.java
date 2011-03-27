package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentSingleStepConferenceCallConfEvent extends
		LucentPrivateData implements HasUCID {
	CSTAConnectionID newCall;
	CSTAConnection[] connList;
	String ucid;
	static final int PDU = 66;

	public static LucentSingleStepConferenceCallConfEvent decode(
			final InputStream in) {
		final LucentSingleStepConferenceCallConfEvent _this = new LucentSingleStepConferenceCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public LucentSingleStepConferenceCallConfEvent() {
	}

	public LucentSingleStepConferenceCallConfEvent(
			final CSTAConnectionID _newCall, final CSTAConnection[] _connList,
			final String _ucid) {
		newCall = _newCall;
		connList = _connList;
		ucid = _ucid;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		newCall = CSTAConnectionID.decode(memberStream);
		connList = ConnectionList.decode(memberStream);
		ucid = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(newCall, memberStream);
		ConnectionList.encode(connList, memberStream);
		ASNIA5String.encode(ucid, memberStream);
	}

	public CSTAConnection[] getConnList() {
		return connList;
	}

	public CSTAConnectionID getNewCall() {
		return newCall;
	}

	@Override
	public int getPDU() {
		return 66;
	}

	@Override
	public String getUcid() {
		return ucid;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSingleStepConferenceCallConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(newCall, "newCall", indent));
		lines.addAll(ConnectionList.print(connList, "connList", indent));
		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));

		lines.add("}");
		return lines;
	}
}
