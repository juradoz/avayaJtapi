package com.avaya.jtapi.tsapi.impl.beans;

import com.avaya.jtapi.tsapi.V5OriginalCallInfo;

public class V5OriginalCallInfoImpl extends OriginalCallInfoImpl implements
		V5OriginalCallInfo {
	private String ucid;
	int callOriginatorType;
	private boolean hasCallOriginatorType;
	private boolean flexibleBilling;

	public V5OriginalCallInfoImpl() {
		callOriginatorType = -1;
		hasCallOriginatorType = false;
		flexibleBilling = false;
	}

	public boolean canSetBillRate() {
		return flexibleBilling;
	}

	public int getCallOriginatorType() {
		return callOriginatorType;
	}

	public String getUCID() {
		return ucid;
	}

	public boolean hasCallOriginatorType() {
		return hasCallOriginatorType;
	}

	public void setCallOriginatorType(final int callOriginatorType) {
		this.callOriginatorType = callOriginatorType;
	}

	public void setFlexibleBilling(final boolean _flexibleBilling) {
		flexibleBilling = _flexibleBilling;
	}

	public void setHasCallOriginatorType(final boolean hasCallOriginatorType) {
		this.hasCallOriginatorType = hasCallOriginatorType;
	}

	public void setUCID(final String ucid) {
		this.ucid = ucid;
	}
}
