package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTASnapshotDeviceResponseInfo extends ASNSequence {
	public static CSTASnapshotDeviceResponseInfo decode(InputStream in) {
		CSTASnapshotDeviceResponseInfo _this = new CSTASnapshotDeviceResponseInfo();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(
			CSTASnapshotDeviceResponseInfo _this, String name, String _indent) {
		Collection lines = new ArrayList();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		lines.addAll(CSTAConnectionID.print(_this.callIdentifier,
				"callIdentifier", indent));
		lines.addAll(CSTACallState.print(_this.localCallState,
				"localCallState", indent));

		lines.add(_indent + "}");
		return lines;
	}

	CSTAConnectionID callIdentifier;

	short[] localCallState;

	public CSTASnapshotDeviceResponseInfo() {
	}

	public CSTASnapshotDeviceResponseInfo(CSTAConnectionID _callIdentifier,
			short[] _localCallState) {
		callIdentifier = _callIdentifier;
		localCallState = _localCallState;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		callIdentifier = CSTAConnectionID.decode(memberStream);
		localCallState = CSTACallState.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(callIdentifier, memberStream);
		CSTACallState.encode(localCallState, memberStream);
	}

	public CSTAConnectionID getCallIdentifier() {
		return callIdentifier;
	}

	public short[] getLocalCallState() {
		return localCallState;
	}

	public void setCallIdentifier(CSTAConnectionID callIdentifier) {
		this.callIdentifier = callIdentifier;
	}

	public void setLocalCallState(short[] localCallState) {
		this.localCallState = new short[localCallState.length];
		System.arraycopy(localCallState, 0, this.localCallState, 0,
				localCallState.length);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceResponseInfo JD-Core Version:
 * 0.5.4
 */