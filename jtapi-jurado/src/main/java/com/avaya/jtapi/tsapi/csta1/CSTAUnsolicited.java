package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.TsapiPDU;

public abstract class CSTAUnsolicited extends TsapiPDU {
	int monitorCrossRefID;

	public int getPDUClass() {
		return 4;
	}

	public int getMonitorCrossRefID() {
		return this.monitorCrossRefID;
	}

	public void setMonitorCrossRefID(int monitorCrossRefID) {
		this.monitorCrossRefID = monitorCrossRefID;
	}
}