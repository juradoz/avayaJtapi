package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTASnapshotDeviceResponseInfo extends ASNSequence {
	public static CSTASnapshotDeviceResponseInfo decode(final InputStream in) {
		final CSTASnapshotDeviceResponseInfo _this = new CSTASnapshotDeviceResponseInfo();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(
			final CSTASnapshotDeviceResponseInfo _this, final String name,
			final String _indent) {
		final Collection<String> lines = new ArrayList<String>();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null)
			lines.add(_indent + name);
		lines.add(_indent + "{");

		final String indent = _indent + "  ";

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

	public CSTASnapshotDeviceResponseInfo(
			final CSTAConnectionID _callIdentifier,
			final short[] _localCallState) {
		callIdentifier = _callIdentifier;
		localCallState = _localCallState;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		callIdentifier = CSTAConnectionID.decode(memberStream);
		localCallState = CSTACallState.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(callIdentifier, memberStream);
		CSTACallState.encode(localCallState, memberStream);
	}

	public CSTAConnectionID getCallIdentifier() {
		return callIdentifier;
	}

	public short[] getLocalCallState() {
		return localCallState;
	}

	public void setCallIdentifier(final CSTAConnectionID callIdentifier) {
		this.callIdentifier = callIdentifier;
	}

	public void setLocalCallState(final short[] localCallState) {
		this.localCallState = new short[localCallState.length];
		System.arraycopy(localCallState, 0, this.localCallState, 0,
				localCallState.length);
	}
}
