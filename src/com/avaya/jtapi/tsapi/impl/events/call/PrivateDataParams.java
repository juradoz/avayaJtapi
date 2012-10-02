package com.avaya.jtapi.tsapi.impl.events.call;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterTrunk;

public class PrivateDataParams {
	private CallCenterTrunk trunk = null;
	private short reason = 0;
	private UserToUserInfo userToUserInfo = null;
	private LookaheadInfo lookaheadInfo = null;
	private UserEnteredCode userEnteredCode = null;
	private OriginalCallInfo originalCallInfo = null;
	private String ucid = null;
	private int callOriginatorType = -1;
	private boolean hasCallOriginatorType = false;
	private ACDAddress split = null;
	private ITsapiAddress distributingDevice = null;
	private boolean flexibleBilling = false;

	public CallCenterTrunk getTrunk() {
		return this.trunk;
	}

	public void setTrunk(CallCenterTrunk trunk) {
		this.trunk = trunk;
	}

	public short getReason() {
		return this.reason;
	}

	public void setReason(short reason) {
		this.reason = reason;
	}

	public int getCallOriginatorType() {
		return this.callOriginatorType;
	}

	public void setCallOriginatorType(int callOriginatorType) {
		this.callOriginatorType = callOriginatorType;
	}

	public boolean hasCallOriginatorType() {
		return this.hasCallOriginatorType;
	}

	public void setHasCallOriginatorType(boolean hasCallOriginatorType) {
		this.hasCallOriginatorType = hasCallOriginatorType;
	}

	public UserToUserInfo getUserToUserInfo() {
		return this.userToUserInfo;
	}

	public void setUserToUserInfo(UserToUserInfo userToUserInfo) {
		this.userToUserInfo = userToUserInfo;
	}

	public LookaheadInfo getLookaheadInfo() {
		return this.lookaheadInfo;
	}

	public void setLookaheadInfo(LookaheadInfo lookaheadInfo) {
		this.lookaheadInfo = lookaheadInfo;
	}

	public UserEnteredCode getUserEnteredCode() {
		return this.userEnteredCode;
	}

	public void setUserEnteredCode(UserEnteredCode userEnteredCode) {
		this.userEnteredCode = userEnteredCode;
	}

	public OriginalCallInfo getOriginalCallInfo() {
		return this.originalCallInfo;
	}

	public void setOriginalCallInfo(OriginalCallInfo originalCallInfo) {
		this.originalCallInfo = originalCallInfo;
	}

	public String getUcid() {
		return this.ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	public ACDAddress getSplit() {
		return this.split;
	}

	public void setSplit(ACDAddress split) {
		this.split = split;
	}

	public ITsapiAddress getDistributingDevice() {
		return this.distributingDevice;
	}

	public void setDistributingDevice(ITsapiAddress distributingDevice) {
		this.distributingDevice = distributingDevice;
	}

	public boolean isFlexibleBilling() {
		return this.flexibleBilling;
	}

	public void setFlexibleBilling(boolean flexibleBilling) {
		this.flexibleBilling = flexibleBilling;
	}
}