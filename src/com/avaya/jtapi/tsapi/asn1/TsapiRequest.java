package com.avaya.jtapi.tsapi.asn1;

public abstract class TsapiRequest extends TsapiPDU {
	int invokeID;

	public int getInvokeID() {
		return invokeID;
	}

	public void setInvokeID(int invokeID) {
		this.invokeID = invokeID;
	}
}
