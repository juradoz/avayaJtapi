package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.MetaEvent;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.CallCenterTrunk;

import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.LucentCallInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class LucentCallCenterConnectionEvent extends
		CallCenterConnectionEventImpl implements LucentCallInfo {
	public LucentCallCenterConnectionEvent(final CallEventParams params,
			final MetaEvent event, final int eventId) {
		super(params, event, eventId);
	}

	@Override
	public ACDAddress getDeliveringACDAddress() {
		return callEventParams.getSplit();
	}

	@Override
	public CallCenterAddress getDistributingAddress() {
		return callEventParams.getDistributingDevice();
	}

	@Override
	public LookaheadInfo getLookaheadInfo() {
		return callEventParams.getLookaheadInfo();
	}

	@Override
	public OriginalCallInfo getOriginalCallInfo() {
		return callEventParams.getOriginalCallInfo();
	}

	@Override
	public short getReason() {
		return callEventParams.getReason();
	}

	@Override
	public CallCenterTrunk getTrunk() {
		return callEventParams.getTrunk();
	}

	@Override
	public UserEnteredCode getUserEnteredCode() {
		return callEventParams.getUserEnteredCode();
	}

	@Override
	public UserToUserInfo getUserToUserInfo() {
		return callEventParams.getUserToUserInfo();
	}
}
