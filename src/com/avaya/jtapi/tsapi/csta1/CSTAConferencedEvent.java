package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAConferencedEvent extends CSTAUnsolicited {
	CSTAConnectionID primaryOldCall;
	CSTAConnectionID secondaryOldCall;
	CSTAExtendedDeviceID confController;
	CSTAExtendedDeviceID addedParty;
	CSTAConnection[] conferenceConnections;
	short localConnectionInfo;
	short cause;
	public static final int PDU = 55;

	public static CSTAConferencedEvent decode(final InputStream in) {
		final CSTAConferencedEvent _this = new CSTAConferencedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		primaryOldCall = CSTAConnectionID.decode(memberStream);
		secondaryOldCall = CSTAConnectionID.decode(memberStream);
		confController = CSTAExtendedDeviceID.decode(memberStream);
		addedParty = CSTAExtendedDeviceID.decode(memberStream);
		conferenceConnections = ConnectionList.decode(memberStream);
		localConnectionInfo = ASNEnumerated.decode(memberStream);
		cause = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(primaryOldCall, memberStream);
		CSTAConnectionID.encode(secondaryOldCall, memberStream);
		ASNSequence.encode(confController, memberStream);
		ASNSequence.encode(addedParty, memberStream);
		ConnectionList.encode(conferenceConnections, memberStream);
		ASNEnumerated.encode(localConnectionInfo, memberStream);
		ASNEnumerated.encode(cause, memberStream);
	}

	public CSTAExtendedDeviceID getAddedParty() {
		return addedParty;
	}

	public short getCause() {
		return cause;
	}

	public CSTAExtendedDeviceID getConfController() {
		return confController;
	}

	public CSTAConnection[] getConferenceConnections() {
		return conferenceConnections;
	}

	public short getLocalConnectionInfo() {
		return localConnectionInfo;
	}

	@Override
	public int getPDU() {
		return 55;
	}

	public CSTAConnectionID getPrimaryOldCall() {
		return primaryOldCall;
	}

	public CSTAConnectionID getSecondaryOldCall() {
		return secondaryOldCall;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAConferencedEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAConnectionID.print(primaryOldCall, "primaryOldCall",
				indent));
		lines.addAll(CSTAConnectionID.print(secondaryOldCall,
				"secondaryOldCall", indent));
		lines.addAll(CSTAExtendedDeviceID.print(confController,
				"confController", indent));
		lines.addAll(CSTAExtendedDeviceID.print(addedParty, "addedParty",
				indent));
		lines.addAll(ConnectionList.print(conferenceConnections,
				"conferenceConnections", indent));
		lines.addAll(LocalConnectionState.print(localConnectionInfo,
				"localConnectionInfo", indent));
		lines.addAll(CSTAEventCause.print(cause, "cause", indent));

		lines.add("}");
		return lines;
	}

	public void setAddedParty(final CSTAExtendedDeviceID _conferencedDevice) {
		addedParty = _conferencedDevice;
	}

	public void setCause(final short _cause) {
		cause = _cause;
	}

	public void setConfController(final CSTAExtendedDeviceID _conferencingDevice) {
		confController = _conferencingDevice;
	}

	public void setConferencedConnections(
			final CSTAConnection[] _conferenceConnections) {
		conferenceConnections = _conferenceConnections;
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
}
