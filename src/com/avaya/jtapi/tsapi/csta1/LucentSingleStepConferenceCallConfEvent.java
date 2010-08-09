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

	public static LucentSingleStepConferenceCallConfEvent decode(InputStream in) {
		LucentSingleStepConferenceCallConfEvent _this = new LucentSingleStepConferenceCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public LucentSingleStepConferenceCallConfEvent() {
	}

	public LucentSingleStepConferenceCallConfEvent(CSTAConnectionID _newCall,
			CSTAConnection[] _connList, String _ucid) {
		newCall = _newCall;
		connList = _connList;
		ucid = _ucid;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		newCall = CSTAConnectionID.decode(memberStream);
		connList = ConnectionList.decode(memberStream);
		ucid = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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

	public String getUcid() {
		return ucid;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentSingleStepConferenceCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(newCall, "newCall", indent));
		lines.addAll(ConnectionList.print(connList, "connList", indent));
		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCallConfEvent JD-Core
 * Version: 0.5.4
 */