package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.MetaEvent;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.CallCenterTrunk;

import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.LucentCall;
import com.avaya.jtapi.tsapi.LucentCallInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class LucentCallControlConnectionNetworkReachedEvent extends
		CallControlConnectionNetworkReachedEventImpl implements LucentCallInfo {
	public LucentCallControlConnectionNetworkReachedEvent(
			final CallEventParams params, final MetaEvent event,
			final int eventId, final int numInQueue, final String digits) {
		super(params, event, eventId, numInQueue, digits);
	}

	@Override
	public ACDAddress getDeliveringACDAddress() {
		ACDAddress acdAddr = null;
		if (callEventParams.getSplit() != null)
			acdAddr = callEventParams.getSplit();
		else if (getCall() instanceof LucentCall)
			acdAddr = ((LucentCall) getCall()).getDeliveringACDAddress();
		return acdAddr;
	}

	@Override
	public CallCenterAddress getDistributingAddress() {
		CallCenterAddress cca = null;
		if (callEventParams.getDistributingDevice() != null)
			cca = callEventParams.getDistributingDevice();
		else if (getCall() instanceof LucentCall)
			cca = ((LucentCall) getCall()).getDistributingAddress();
		return cca;
	}

	@Override
	public LookaheadInfo getLookaheadInfo() {
		LookaheadInfo lai = null;
		if (callEventParams.getLookaheadInfo() != null)
			lai = callEventParams.getLookaheadInfo();
		else if (getCall() instanceof LucentCall)
			lai = ((LucentCall) getCall()).getLookaheadInfo();
		return lai;
	}

	@Override
	public OriginalCallInfo getOriginalCallInfo() {
		OriginalCallInfo oci = null;
		if (callEventParams.getOriginalCallInfo() != null)
			oci = callEventParams.getOriginalCallInfo();
		else if (getCall() instanceof LucentCall)
			oci = ((LucentCall) getCall()).getOriginalCallInfo();
		return oci;
	}

	@Override
	public short getReason() {
		return callEventParams.getReason();
	}

	@Override
	public CallCenterTrunk getTrunk() {
		CallCenterTrunk trunk = null;
		if (callEventParams.getTrunk() != null)
			trunk = callEventParams.getTrunk();
		else if (getCall() instanceof LucentCall)
			trunk = ((LucentCall) getCall()).getTrunk();
		return trunk;
	}

	@Override
	public UserEnteredCode getUserEnteredCode() {
		UserEnteredCode uec = null;
		if (callEventParams.getUserEnteredCode() != null)
			uec = callEventParams.getUserEnteredCode();
		else if (getCall() instanceof LucentCall)
			uec = ((LucentCall) getCall()).getUserEnteredCode();
		return uec;
	}

	@Override
	public UserToUserInfo getUserToUserInfo() {
		UserToUserInfo uui = null;
		if (callEventParams.getUserToUserInfo() != null)
			uui = callEventParams.getUserToUserInfo();
		else if (getCall() instanceof LucentCall)
			uui = ((LucentCall) getCall()).getUserToUserInfo();
		return uui;
	}
}
