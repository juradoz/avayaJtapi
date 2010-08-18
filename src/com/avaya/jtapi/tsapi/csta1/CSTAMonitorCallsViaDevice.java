package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAMonitorCallsViaDevice extends CSTARequest {
	public static CSTAMonitorCallsViaDevice decode(InputStream in) {
		CSTAMonitorCallsViaDevice _this = new CSTAMonitorCallsViaDevice();
		_this.doDecode(in);

		return _this;
	}

	String deviceID;
	CSTAMonitorFilter monitorFilter;

	public static final int PDU = 113;

	public CSTAMonitorCallsViaDevice() {
	}

	public CSTAMonitorCallsViaDevice(String _deviceID,
			CSTAMonitorFilter _monitorFilter) {
		deviceID = _deviceID;
		monitorFilter = _monitorFilter;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		deviceID = ASNIA5String.decode(memberStream);
		monitorFilter = CSTAMonitorFilter.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(deviceID, memberStream);
		ASNSequence.encode(monitorFilter, memberStream);
	}

	public String getDeviceID() {
		return deviceID;
	}

	public CSTAMonitorFilter getMonitorFilter() {
		return monitorFilter;
	}

	@Override
	public int getPDU() {
		return 113;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAMonitorCallsViaDevice ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(deviceID, "deviceID", indent));
		lines.addAll(CSTAMonitorFilter.print(monitorFilter, "monitorFilter",
				indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAMonitorCallsViaDevice JD-Core Version: 0.5.4
 */