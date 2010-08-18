package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTATransferredEvent extends CSTAUnsolicited {
	CSTAConnectionID primaryOldCall;
	CSTAConnectionID secondaryOldCall;
	CSTAExtendedDeviceID transferringDevice;
	CSTAExtendedDeviceID transferredDevice;
	CSTAConnection[] transferredConnections;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 67;

	public static CSTATransferredEvent decode(final InputStream in) {
		final CSTATransferredEvent _this = new CSTATransferredEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		primaryOldCall = CSTAConnectionID.decode(memberStream);
		secondaryOldCall = CSTAConnectionID.decode(memberStream);
		transferringDevice = CSTAExtendedDeviceID.decode(memberStream);
		transferredDevice = CSTAExtendedDeviceID.decode(memberStream);
		transferredConnections = ConnectionList.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(primaryOldCall, memberStream);
		CSTAConnectionID.encode(secondaryOldCall, memberStream);
		ASNSequence.encode(transferringDevice, memberStream);
		ASNSequence.encode(transferredDevice, memberStream);
		ConnectionList.encode(transferredConnections, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public short getCause() {
		return cause;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 67;
	}

	public CSTAConnectionID getPrimaryOldCall() {
		return primaryOldCall;
	}

	public CSTAConnectionID getSecondaryOldCall() {
		return secondaryOldCall;
	}

	public CSTAConnection[] getTransferredConnections() {
		return transferredConnections;
	}

	public CSTAExtendedDeviceID getTransferredDevice() {
		return transferredDevice;
	}

	public CSTAExtendedDeviceID getTransferringDevice() {
		return transferringDevice;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTATransferredEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(primaryOldCall, "primaryOldCall",
				indent));
		lines.addAll(CSTAConnectionID.print(secondaryOldCall,
				"secondaryOldCall", indent));
		lines.addAll(CSTAExtendedDeviceID.print(transferringDevice,
				"transferringDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(transferredDevice,
				"transferredDevice", indent));
		lines.addAll(ConnectionList.print(transferredConnections,
				"transferredConnections", indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCause(final short _cause) {
		cause = _cause;
	}

	public void setLocalConnectionInfo(final short _localConnectionInfo) {
		localConnectionInfo = _localConnectionInfo;
	}

	public void setPrimaryOldCall(final CSTAConnectionID _primaryOldCall) {
		primaryOldCall = _primaryOldCall;
	}

	public void setSecondaryOldCall(final CSTAConnectionID _secondaryOldCall) {
		secondaryOldCall = _secondaryOldCall;
	}

	public void setTransferredConnections(
			final CSTAConnection[] _transferredConnections) {
		transferredConnections = _transferredConnections;
	}

	public void setTransferredDevice(
			final CSTAExtendedDeviceID _transferredDevice) {
		transferredDevice = _transferredDevice;
	}

	public void setTransferringDevice(
			final CSTAExtendedDeviceID _transferringDevice) {
		transferringDevice = _transferringDevice;
	}
}
