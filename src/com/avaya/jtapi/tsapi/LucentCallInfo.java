package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.CallCenterTrunk;

public abstract interface LucentCallInfo extends ITsapiCallInfo {
	public static final short AR_NONE = 0;
	public static final short AR_ANSWER_NORMAL = 1;
	public static final short AR_ANSWER_TIMED = 2;
	public static final short AR_ANSWER_VOICE_ENERGY = 3;
	public static final short AR_ANSWER_MACHINE_DETECTED = 4;
	public static final short AR_SIT_REORDER = 5;
	public static final short AR_SIT_NO_CIRCUIT = 6;
	public static final short AR_SIT_INTERCEPT = 7;
	public static final short AR_SIT_VACANT_CODE = 8;
	public static final short AR_SIT_INEFFECTIVE_OTHER = 9;
	public static final short AR_SIT_UNKNOWN = 10;
	public static final short AR_IN_QUEUE = 11;

	public abstract ACDAddress getDeliveringACDAddress();

	public abstract CallCenterAddress getDistributingAddress();

	public abstract LookaheadInfo getLookaheadInfo();

	public abstract OriginalCallInfo getOriginalCallInfo();

	public abstract short getReason();

	public abstract CallCenterTrunk getTrunk();

	public abstract UserEnteredCode getUserEnteredCode();

	public abstract UserToUserInfo getUserToUserInfo();
}

