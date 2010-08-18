package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterTrunk;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;

public class PrivateDataParams {
	private CallCenterTrunk trunk;
	private short reason;
	private UserToUserInfo userToUserInfo;
	private LookaheadInfo lookaheadInfo;
	private UserEnteredCode userEnteredCode;
	private OriginalCallInfo originalCallInfo;
	private String ucid;
	private int callOriginatorType;
	private boolean hasCallOriginatorType;
	private ACDAddress split;
	private ITsapiAddress distributingDevice;
	private boolean flexibleBilling;

	public PrivateDataParams() {
		trunk = null;
		reason = 0;
		userToUserInfo = null;
		lookaheadInfo = null;
		userEnteredCode = null;
		originalCallInfo = null;
		ucid = null;
		callOriginatorType = -1;
		hasCallOriginatorType = false;
		split = null;
		distributingDevice = null;
		flexibleBilling = false;
	}

	public int getCallOriginatorType() {
		return callOriginatorType;
	}

	public ITsapiAddress getDistributingDevice() {
		return distributingDevice;
	}

	public LookaheadInfo getLookaheadInfo() {
		return lookaheadInfo;
	}

	public OriginalCallInfo getOriginalCallInfo() {
		return originalCallInfo;
	}

	public short getReason() {
		return reason;
	}

	public ACDAddress getSplit() {
		return split;
	}

	public CallCenterTrunk getTrunk() {
		return trunk;
	}

	public String getUcid() {
		return ucid;
	}

	public UserEnteredCode getUserEnteredCode() {
		return userEnteredCode;
	}

	public UserToUserInfo getUserToUserInfo() {
		return userToUserInfo;
	}

	public boolean hasCallOriginatorType() {
		return hasCallOriginatorType;
	}

	public boolean isFlexibleBilling() {
		return flexibleBilling;
	}

	public void setCallOriginatorType(int callOriginatorType) {
		this.callOriginatorType = callOriginatorType;
	}

	public void setDistributingDevice(ITsapiAddress distributingDevice) {
		this.distributingDevice = distributingDevice;
	}

	public void setFlexibleBilling(boolean flexibleBilling) {
		this.flexibleBilling = flexibleBilling;
	}

	public void setHasCallOriginatorType(boolean hasCallOriginatorType) {
		this.hasCallOriginatorType = hasCallOriginatorType;
	}

	public void setLookaheadInfo(LookaheadInfo lookaheadInfo) {
		this.lookaheadInfo = lookaheadInfo;
	}

	public void setOriginalCallInfo(OriginalCallInfo originalCallInfo) {
		this.originalCallInfo = originalCallInfo;
	}

	public void setReason(short reason) {
		this.reason = reason;
	}

	public void setSplit(ACDAddress split) {
		this.split = split;
	}

	public void setTrunk(CallCenterTrunk trunk) {
		this.trunk = trunk;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	public void setUserEnteredCode(UserEnteredCode userEnteredCode) {
		this.userEnteredCode = userEnteredCode;
	}

	public void setUserToUserInfo(UserToUserInfo userToUserInfo) {
		this.userToUserInfo = userToUserInfo;
	}
}
