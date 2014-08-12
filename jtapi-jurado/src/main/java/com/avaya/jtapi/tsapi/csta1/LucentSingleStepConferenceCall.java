package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentSingleStepConferenceCall extends LucentPrivateData {
	CSTAConnectionID activeCall;
	String deviceToBeJoin;
	short participationType;
	boolean alertDestination;
	public static final int PDU = 65;

	public LucentSingleStepConferenceCall(CSTAConnectionID _activeCall,
			String _deviceToBeJoin, short _participationType,
			boolean _alertDestination) {
		this.activeCall = _activeCall;
		this.deviceToBeJoin = _deviceToBeJoin;
		this.participationType = _participationType;
		this.alertDestination = _alertDestination;
	}

	public LucentSingleStepConferenceCall() {
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.activeCall, memberStream);
		DeviceID.encode(this.deviceToBeJoin, memberStream);
		ParticipationType.encode(this.participationType, memberStream);
		ASNBoolean.encode(this.alertDestination, memberStream);
	}

	public static LucentSingleStepConferenceCall decode(InputStream in) {
		LucentSingleStepConferenceCall _this = new LucentSingleStepConferenceCall();

		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.activeCall = CSTAConnectionID.decode(memberStream);
		this.deviceToBeJoin = DeviceID.decode(memberStream);
		this.participationType = ParticipationType.decode(memberStream);
		this.alertDestination = ASNBoolean.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSingleStepConferenceCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall",
				indent));
		lines.addAll(DeviceID.print(this.deviceToBeJoin, "deviceToBeJoin",
				indent));
		lines.addAll(ParticipationType.print(this.participationType,
				"participationType", indent));
		lines.addAll(ASNBoolean.print(this.alertDestination,
				"alertDestination", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 65;
	}

	public CSTAConnectionID getActiveCall() {
		return this.activeCall;
	}

	public boolean isAlertDestination() {
		return this.alertDestination;
	}

	public String getDeviceToBeJoin() {
		return this.deviceToBeJoin;
	}

	public short getParticipationType() {
		return this.participationType;
	}
}