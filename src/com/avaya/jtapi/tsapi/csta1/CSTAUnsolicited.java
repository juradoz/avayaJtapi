package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.TsapiPDU;

public abstract class CSTAUnsolicited extends TsapiPDU {
	int monitorCrossRefID;

	public int getMonitorCrossRefID() {
		return monitorCrossRefID;
	}

	@Override
	public int getPDUClass() {
		return 4;
	}

	public void setMonitorCrossRefID(final int monitorCrossRefID) {
		this.monitorCrossRefID = monitorCrossRefID;
	}
}
