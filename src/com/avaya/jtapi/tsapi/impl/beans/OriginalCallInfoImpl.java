package com.avaya.jtapi.tsapi.impl.beans;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.TsapiTrunk;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;

public class OriginalCallInfoImpl implements OriginalCallInfo {
	private short reason;
	private LookaheadInfo lookaheadInfo;
	private UserEnteredCode userEnteredCode;
	private UserToUserInfo userInfo;
	private ITsapiAddress callingDevice;
	private ITsapiAddress calledDevice;
	private TsapiTrunk trunk;

	public short getReason() {
		return this.reason;
	}

	public ITsapiAddress getCallingDevice() {
		return this.callingDevice;
	}

	public ITsapiAddress getCalledDevice() {
		return this.calledDevice;
	}

	public TsapiTrunk getTrunk() {
		return this.trunk;
	}

	public UserToUserInfo getUserToUserInfo() {
		return this.userInfo;
	}

	public LookaheadInfo getLookaheadInfo() {
		return this.lookaheadInfo;
	}

	public UserEnteredCode getUserEnteredCode() {
		return this.userEnteredCode;
	}

	public void setReason(short _reason) {
		this.reason = _reason;
	}

	public void setLookaheadInfo(LookaheadInfo _lookaheadInfo) {
		this.lookaheadInfo = _lookaheadInfo;
	}

	public void setUserEnteredCode(UserEnteredCode _userEnteredCode) {
		this.userEnteredCode = _userEnteredCode;
	}

	public void setUserInfo(UserToUserInfo _userInfo) {
		this.userInfo = _userInfo;
	}

	public void setCallingDevice(ITsapiAddress _dev) {
		this.callingDevice = _dev;
	}

	public void setCalledDevice(ITsapiAddress _dev) {
		this.calledDevice = _dev;
	}

	public void setTrunk(TsapiTrunk _trunk) {
		this.trunk = _trunk;
	}
}