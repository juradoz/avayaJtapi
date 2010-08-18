package com.avaya.jtapi.tsapi;

public abstract interface OriginalCallInfo {
	public static final short OR_NONE = 0;
	public static final short OR_CONSULTATION = 1;
	public static final short OR_CONFERENCED = 2;
	public static final short OR_TRANSFERRED = 3;
	public static final short OR_NEW_CALL = 4;

	public abstract ITsapiAddress getCalledDevice();

	public abstract ITsapiAddress getCallingDevice();

	public abstract LookaheadInfo getLookaheadInfo();

	public abstract short getReason();

	public abstract TsapiTrunk getTrunk();

	public abstract UserEnteredCode getUserEnteredCode();

	public abstract UserToUserInfo getUserToUserInfo();
}

