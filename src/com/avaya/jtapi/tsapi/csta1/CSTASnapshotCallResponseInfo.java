package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTASnapshotCallResponseInfo extends ASNSequence {
	public static CSTASnapshotCallResponseInfo decode(InputStream in) {
		CSTASnapshotCallResponseInfo _this = new CSTASnapshotCallResponseInfo();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(CSTASnapshotCallResponseInfo _this,
			String name, String _indent) {
		Collection<String> lines = new ArrayList<String>();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

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

	public CSTASnapshotCallResponseInfo(CSTAExtendedDeviceID _deviceOnCall,
			CSTAConnectionID _callIdentifier, short _localConnectionState) {
		deviceOnCall = _deviceOnCall;
		callIdentifier = _callIdentifier;
		localConnectionState = _localConnectionState;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		deviceOnCall = CSTAExtendedDeviceID.decode(memberStream);
		callIdentifier = CSTAConnectionID.decode(memberStream);
		localConnectionState = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASnapshotCallResponseInfo JD-Core Version:
 * 0.5.4
 */