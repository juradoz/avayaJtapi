package com.avaya.jtapi.tsapi.impl.events.termConn;

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

public class LucentCallControlTerminalConnectionEvent extends
		CallControlTerminalConnectionEventImpl implements LucentCallInfo {
	public LucentCallControlTerminalConnectionEvent(CallEventParams params,
			MetaEvent event, int eventId) {
		super(params, event, eventId);
	}

	public ACDAddress getDeliveringACDAddress() {
		return callEventParams.getSplit();
	}

	public CallCenterAddress getDistributingAddress() {
		return callEventParams.getDistributingDevice();
	}

	public LookaheadInfo getLookaheadInfo() {
		return callEventParams.getLookaheadInfo();
	}

	public OriginalCallInfo getOriginalCallInfo() {
		return callEventParams.getOriginalCallInfo();
	}

	public short getReason() {
		return callEventParams.getReason();
	}

	public CallCenterTrunk getTrunk() {
		return callEventParams.getTrunk();
	}

	public UserEnteredCode getUserEnteredCode() {
		return callEventParams.getUserEnteredCode();
	}

	public UserToUserInfo getUserToUserInfo() {
		return callEventParams.getUserToUserInfo();
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com
 * .avaya.jtapi.tsapi.impl.events.termConn.LucentCallControlTerminalConnectionEvent
 * JD-Core Version: 0.5.4
 */