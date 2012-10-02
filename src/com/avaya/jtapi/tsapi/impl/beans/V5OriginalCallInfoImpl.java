package com.avaya.jtapi.tsapi.impl.beans;

import com.avaya.jtapi.tsapi.V5OriginalCallInfo;

public class V5OriginalCallInfoImpl extends OriginalCallInfoImpl implements
		V5OriginalCallInfo {
	private String ucid;
	int callOriginatorType = -1;
	private boolean hasCallOriginatorType = false;
	private boolean flexibleBilling = false;

	public String getUCID() {
		return this.ucid;
	}

	public void setCallOriginatorType(int callOriginatorType) {
		this.callOriginatorType = callOriginatorType;
	}

	public int getCallOriginatorType() {
		return this.callOriginatorType;
	}

	public void setHasCallOriginatorType(boolean hasCallOriginatorType) {
		this.hasCallOriginatorType = hasCallOriginatorType;
	}

	public boolean hasCallOriginatorType() {
		return this.hasCallOriginatorType;
	}

	public boolean canSetBillRate() {
		return this.flexibleBilling;
	}

	public void setUCID(String ucid) {
		this.ucid = ucid;
	}

	public void setFlexibleBilling(boolean _flexibleBilling) {
		this.flexibleBilling = _flexibleBilling;
	}
}