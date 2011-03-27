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

	@Override
	public ITsapiAddress getCalledDevice() {
		return calledDevice;
	}

	@Override
	public ITsapiAddress getCallingDevice() {
		return callingDevice;
	}

	@Override
	public LookaheadInfo getLookaheadInfo() {
		return lookaheadInfo;
	}

	@Override
	public short getReason() {
		return reason;
	}

	@Override
	public TsapiTrunk getTrunk() {
		return trunk;
	}

	@Override
	public UserEnteredCode getUserEnteredCode() {
		return userEnteredCode;
	}

	@Override
	public UserToUserInfo getUserToUserInfo() {
		return userInfo;
	}

	public void setCalledDevice(final ITsapiAddress _dev) {
		calledDevice = _dev;
	}

	public void setCallingDevice(final ITsapiAddress _dev) {
		callingDevice = _dev;
	}

	public void setLookaheadInfo(final LookaheadInfo _lookaheadInfo) {
		lookaheadInfo = _lookaheadInfo;
	}

	public void setReason(final short _reason) {
		reason = _reason;
	}

	public void setTrunk(final TsapiTrunk _trunk) {
		trunk = _trunk;
	}

	public void setUserEnteredCode(final UserEnteredCode _userEnteredCode) {
		userEnteredCode = _userEnteredCode;
	}

	public void setUserInfo(final UserToUserInfo _userInfo) {
		userInfo = _userInfo;
	}
}
