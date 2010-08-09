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

	public static CSTAMessageWaitingEvent decode(InputStream in) {
		CSTAMessageWaitingEvent _this = new CSTAMessageWaitingEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		deviceForMessage = CSTAExtendedDeviceID.decode(memberStream);
		invokingDevice = CSTAExtendedDeviceID.decode(memberStream);
		messageWaitingOn = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("CSTAMessageWaitingEvent ::=");
		lines.add("{");

		String indent = "  ";
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

	public void setDeviceForMessage(CSTAExtendedDeviceID deviceForMessage) {
		this.deviceForMessage = deviceForMessage;
	}

	public void setInvokingDevice(CSTAExtendedDeviceID invokingDevice) {
		this.invokingDevice = invokingDevice;
	}

	public void setMessageWaitingOn(boolean messageWaitingOn) {
		this.messageWaitingOn = messageWaitingOn;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAMessageWaitingEvent JD-Core Version: 0.5.4
 */