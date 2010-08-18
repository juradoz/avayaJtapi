package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentSingleStepConferenceCall extends LucentPrivateData {
	public static LucentSingleStepConferenceCall decode(final InputStream in) {
		final LucentSingleStepConferenceCall _this = new LucentSingleStepConferenceCall();

		_this.doDecode(in);

		return _this;
	}

	CSTAConnectionID activeCall;
	String deviceToBeJoin;
	short participationType;
	boolean alertDestination;

	public static final int PDU = 65;

	public LucentSingleStepConferenceCall() {
	}

	public LucentSingleStepConferenceCall(final CSTAConnectionID _activeCall,
			final String _deviceToBeJoin, final short _participationType,
			final boolean _alertDestination) {
		activeCall = _activeCall;
		deviceToBeJoin = _deviceToBeJoin;
		participationType = _participationType;
		alertDestination = _alertDestination;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		activeCall = CSTAConnectionID.decode(memberStream);
		deviceToBeJoin = ASNIA5String.decode(memberStream);
		participationType = ASNEnumerated.decode(memberStream);
		alertDestination = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		CSTAConnectionID.encode(activeCall, memberStream);
		ASNIA5String.encode(deviceToBeJoin, memberStream);
		ASNEnumerated.encode(participationType, memberStream);
		ASNBoolean.encode(alertDestination, memberStream);
	}

	public CSTAConnectionID getActiveCall() {
		return activeCall;
	}

	public String getDeviceToBeJoin() {
		return deviceToBeJoin;
	}

	public short getParticipationType() {
		return participationType;
	}

	@Override
	public int getPDU() {
		return 65;
	}

	public boolean isAlertDestination() {
		return alertDestination;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSingleStepConferenceCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAConnectionID.print(activeCall, "activeCall", indent));
		lines.addAll(ASNIA5String.print(deviceToBeJoin, "deviceToBeJoin",
				indent));
		lines.addAll(ParticipationType.print(participationType,
				"participationType", indent));
		lines.addAll(ASNBoolean.print(alertDestination, "alertDestination",
				indent));

		lines.add("}");
		return lines;
	}
}
