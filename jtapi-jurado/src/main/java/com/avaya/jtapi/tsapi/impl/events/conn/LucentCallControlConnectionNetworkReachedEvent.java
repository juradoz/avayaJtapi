package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.LucentCall;
import com.avaya.jtapi.tsapi.LucentCallInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import javax.telephony.MetaEvent;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.CallCenterTrunk;

public class LucentCallControlConnectionNetworkReachedEvent extends
		CallControlConnectionNetworkReachedEventImpl implements LucentCallInfo {
	public LucentCallControlConnectionNetworkReachedEvent(
			CallEventParams params, MetaEvent event, int eventId,
			int numInQueue, String digits) {
		super(params, event, eventId, numInQueue, digits);
	}

	public ACDAddress getDeliveringACDAddress() {
		ACDAddress acdAddr = null;
		if (this.callEventParams.getSplit() != null) {
			acdAddr = this.callEventParams.getSplit();
		} else if ((getCall() instanceof LucentCall)) {
			acdAddr = ((LucentCall) getCall()).getDeliveringACDAddress();
		}
		return acdAddr;
	}

	public CallCenterAddress getDistributingAddress() {
		CallCenterAddress cca = null;
		if (this.callEventParams.getDistributingDevice() != null) {
			cca = this.callEventParams.getDistributingDevice();
		} else if ((getCall() instanceof LucentCall)) {
			cca = ((LucentCall) getCall()).getDistributingAddress();
		}
		return cca;
	}

	public LookaheadInfo getLookaheadInfo() {
		LookaheadInfo lai = null;
		if (this.callEventParams.getLookaheadInfo() != null) {
			lai = this.callEventParams.getLookaheadInfo();
		} else if ((getCall() instanceof LucentCall)) {
			lai = ((LucentCall) getCall()).getLookaheadInfo();
		}
		return lai;
	}

	public OriginalCallInfo getOriginalCallInfo() {
		OriginalCallInfo oci = null;
		if (this.callEventParams.getOriginalCallInfo() != null) {
			oci = this.callEventParams.getOriginalCallInfo();
		} else if ((getCall() instanceof LucentCall)) {
			oci = ((LucentCall) getCall()).getOriginalCallInfo();
		}
		return oci;
	}

	public short getReason() {
		return this.callEventParams.getReason();
	}

	public CallCenterTrunk getTrunk() {
		CallCenterTrunk trunk = null;
		if (this.callEventParams.getTrunk() != null) {
			trunk = this.callEventParams.getTrunk();
		} else if ((getCall() instanceof LucentCall)) {
			trunk = ((LucentCall) getCall()).getTrunk();
		}
		return trunk;
	}

	public UserEnteredCode getUserEnteredCode() {
		UserEnteredCode uec = null;
		if (this.callEventParams.getUserEnteredCode() != null) {
			uec = this.callEventParams.getUserEnteredCode();
		} else if ((getCall() instanceof LucentCall)) {
			uec = ((LucentCall) getCall()).getUserEnteredCode();
		}
		return uec;
	}

	public UserToUserInfo getUserToUserInfo() {
		UserToUserInfo uui = null;
		if (this.callEventParams.getUserToUserInfo() != null) {
			uui = this.callEventParams.getUserToUserInfo();
		} else if ((getCall() instanceof LucentCall)) {
			uui = ((LucentCall) getCall()).getUserToUserInfo();
		}
		return uui;
	}
}