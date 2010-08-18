package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTASnapshotCallResponseInfo extends ASNSequence {
	public static CSTASnapshotCallResponseInfo decode(final InputStream in) {
		final CSTASnapshotCallResponseInfo _this = new CSTASnapshotCallResponseInfo();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(
			final CSTASnapshotCallResponseInfo _this, final String name,
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

		lines.addAll(CSTAExtendedDeviceID.print(_this.deviceOnCall,
				"deviceOnCall", indent));
		lines.addAll(CSTAConnectionID.print(_this.callIdentifier,
				"callIdentifier", indent));
		lines.addAll(LocalConnectionState.print(_this.localConnectionState,
				"localConnectionState", indent));

		lines.add(_indent + "}");

		return lines;
	}

	CSTAExtendedDeviceID deviceOnCall;

	CSTAConnectionID callIdentifier;

	short localConnectionState;

	public CSTASnapshotCallResponseInfo() {
	}

	public CSTASnapshotCallResponseInfo(
			final CSTAExtendedDeviceID _deviceOnCall,
			final CSTAConnectionID _callIdentifier,
			final short _localConnectionState) {
		deviceOnCall = _deviceOnCall;
		callIdentifier = _callIdentifier;
		localConnectionState = _localConnectionState;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		deviceOnCall = CSTAExtendedDeviceID.decode(memberStream);
		callIdentifier = CSTAConnectionID.decode(memberStream);
		localConnectionState = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNSequence.encode(deviceOnCall, memberStream);
		CSTAConnectionID.encode(callIdentifier, memberStream);
		ASNEnumerated.encode(localConnectionState, memberStream);
	}

	public CSTAConnectionID getCallIdentifier() {
		return callIdentifier;
	}

	public CSTAExtendedDeviceID getDeviceOnCall() {
		return deviceOnCall;
	}

	public short getLocalConnectionState() {
		return localConnectionState;
	}
}
