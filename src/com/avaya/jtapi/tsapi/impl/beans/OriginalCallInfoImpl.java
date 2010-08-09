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

	public ITsapiAddress getCalledDevice() {
		return calledDevice;
	}

	public ITsapiAddress getCallingDevice() {
		return callingDevice;
	}

	public LookaheadInfo getLookaheadInfo() {
		return lookaheadInfo;
	}

	public short getReason() {
		return reason;
	}

	public TsapiTrunk getTrunk() {
		return trunk;
	}

	public UserEnteredCode getUserEnteredCode() {
		return userEnteredCode;
	}

	public UserToUserInfo getUserToUserInfo() {
		return userInfo;
	}

	public void setCalledDevice(ITsapiAddress _dev) {
		calledDevice = _dev;
	}

	public void setCallingDevice(ITsapiAddress _dev) {
		callingDevice = _dev;
	}

	public void setLookaheadInfo(LookaheadInfo _lookaheadInfo) {
		lookaheadInfo = _lookaheadInfo;
	}

	public void setReason(short _reason) {
		reason = _reason;
	}

	public void setTrunk(TsapiTrunk _trunk) {
		trunk = _trunk;
	}

	public void setUserEnteredCode(UserEnteredCode _userEnteredCode) {
		userEnteredCode = _userEnteredCode;
	}

	public void setUserInfo(UserToUserInfo _userInfo) {
		userInfo = _userInfo;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.beans.OriginalCallInfoImpl JD-Core Version: 0.5.4
 */