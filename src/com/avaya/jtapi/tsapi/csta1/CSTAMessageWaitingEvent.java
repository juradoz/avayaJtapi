package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAMessageWaitingEvent extends CSTAUnsolicited {
	CSTAExtendedDeviceID deviceForMessage;
	CSTAExtendedDeviceID invokingDevice;
	boolean messageWaitingOn;
	public static final int PDU = 71;

	public static CSTAMessageWaitingEvent decode(final InputStream in) {
		final CSTAMessageWaitingEvent _this = new CSTAMessageWaitingEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		deviceForMessage = CSTAExtendedDeviceID.decode(memberStream);
		invokingDevice = CSTAExtendedDeviceID.decode(memberStream);
		messageWaitingOn = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNSequence.encode(deviceForMessage, memberStream);
		ASNSequence.encode(invokingDevice, memberStream);
		ASNBoolean.encode(messageWaitingOn, memberStream);
	}

	public CSTAExtendedDeviceID getDeviceForMessage() {
		return deviceForMessage;
	}

	public CSTAExtendedDeviceID getInvokingDevice() {
		return invokingDevice;
	}

	@Override
	public int getPDU() {
		return 71;
	}

	public boolean isMessageWaitingOn() {
		return messageWaitingOn;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAMessageWaitingEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAExtendedDeviceID.print(deviceForMessage,
				"deviceForMessage", indent));

		lines.addAll(CSTAExtendedDeviceID.print(invokingDevice,
				"invokingDevice", indent));

		lines.addAll(ASNBoolean.print(messageWaitingOn, "messageWaitingOn",
				indent));

		lines.add("}");
		return lines;
	}

	public void setDeviceForMessage(final CSTAExtendedDeviceID deviceForMessage) {
		this.deviceForMessage = deviceForMessage;
	}

	public void setInvokingDevice(final CSTAExtendedDeviceID invokingDevice) {
		this.invokingDevice = invokingDevice;
	}

	public void setMessageWaitingOn(final boolean messageWaitingOn) {
		this.messageWaitingOn = messageWaitingOn;
	}
}
