package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAMonitorDevice extends CSTARequest {
	String deviceID;
	CSTAMonitorFilter monitorFilter;
	public static final int PDU = 111;

	public CSTAMonitorDevice(String _deviceID, CSTAMonitorFilter _monitorFilter) {
		this.deviceID = _deviceID;
		this.monitorFilter = _monitorFilter;
	}

	public CSTAMonitorDevice() {
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.deviceID, memberStream);
		CSTAMonitorFilter.encode(this.monitorFilter, memberStream);
	}

	public static CSTAMonitorDevice decode(InputStream in) {
		CSTAMonitorDevice _this = new CSTAMonitorDevice();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.deviceID = DeviceID.decode(memberStream);
		this.monitorFilter = CSTAMonitorFilter.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTAMonitorDevice ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.deviceID, "deviceID", indent));
		lines.addAll(CSTAMonitorFilter.print(this.monitorFilter,
				"monitorFilter", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 111;
	}

	public String getDeviceID() {
		return this.deviceID;
	}

	public CSTAMonitorFilter getMonitorFilter() {
		return this.monitorFilter;
	}
}