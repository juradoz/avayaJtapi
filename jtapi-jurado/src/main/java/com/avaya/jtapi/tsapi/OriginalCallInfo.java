package com.avaya.jtapi.tsapi;

public abstract interface OriginalCallInfo {
	public static final short OR_NONE = 0;
	public static final short OR_CONSULTATION = 1;
	public static final short OR_CONFERENCED = 2;
	public static final short OR_TRANSFERRED = 3;
	public static final short OR_NEW_CALL = 4;

	public abstract short getReason();

	public abstract ITsapiAddress getCallingDevice();

	public abstract ITsapiAddress getCalledDevice();

	public abstract TsapiTrunk getTrunk();

	public abstract UserToUserInfo getUserToUserInfo();

	public abstract LookaheadInfo getLookaheadInfo();

	public abstract UserEnteredCode getUserEnteredCode();
}