package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTANetworkReachedEvent extends CSTAUnsolicited {
	CSTAConnectionID connection;
	CSTAExtendedDeviceID trunkUsed;
	CSTAExtendedDeviceID calledDevice;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 62;

	public static CSTANetworkReachedEvent decode(final InputStream in) {
		final CSTANetworkReachedEvent _this = new CSTANetworkReachedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		connection = CSTAConnectionID.decode(memberStream);
		trunkUsed = CSTAExtendedDeviceID.decode(memberStream);
		calledDevice = CSTAExtendedDeviceID.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(connection, memberStream);
		ASNSequence.encode(trunkUsed, memberStream);
		ASNSequence.encode(calledDevice, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public CSTAExtendedDeviceID getCalledDevice() {
		return calledDevice;
	}

	public short getCause() {
		return cause;
	}

	public CSTAConnectionID getConnection() {
		return connection;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 62;
	}

	public CSTAExtendedDeviceID getTrunkUsed() {
		return trunkUsed;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTANetworkReachedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(connection, "connection", indent));
		lines.addAll(CSTAExtendedDeviceID.print(trunkUsed, "trunkUsed", indent));
		lines.addAll(CSTAExtendedDeviceID.print(calledDevice, "calledDevice",
				indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setCalledDevice(final CSTAExtendedDeviceID calledDevice) {
		this.calledDevice = calledDevice;
	}

	public void setCause(final short cause) {
		this.cause = cause;
	}

	public void setConnection(final CSTAConnectionID connection) {
		this.connection = connection;
	}

	public void setLocalConnectionInfo(final short localConnectionInfo) {
		this.localConnectionInfo = localConnectionInfo;
	}

	public void setTrunkUsed(final CSTAExtendedDeviceID trunkUsed) {
		this.trunkUsed = trunkUsed;
	}
}
