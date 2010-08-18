package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.Terminal;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.privatedata.events.PrivateCallEv;

import com.avaya.jtapi.tsapi.ITsapiCallEvent;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;

@SuppressWarnings("deprecation")
public abstract class TsapiCallEvent extends TsapiObserverEvent implements
		PrivateCallEv, ITsapiCallEvent {
	protected CallEventParams params = null;

	public TsapiCallEvent(final CallEventParams params) {
		this(params, 0);
	}

	public TsapiCallEvent(final CallEventParams params, final int _eventPackage) {
		super(params.getCause(), params.getMetaCode(), params.getPrivateData(),
				_eventPackage);
		this.params = params;
	}

	public final boolean canSetBillRate() {
		return params.isFlexibleBilling();
	}

	public final Call getCall() {
		return params.getCall();
	}

	public final Address getCalledAddress() {
		return params.getCalledAddress();
	}

	public final Address getCallingAddress() {
		return params.getCallingAddress();
	}

	public final Terminal getCallingTerminal() {
		return params.getCallingTerminal();
	}

	public final int getCallOriginatorType() {
		return params.getCallOriginatorType();
	}

	public short getCSTACause() {
		return params.getCstaCause();
	}

	public final ACDAddress getDeliveringACDAddress() {
		return params.getSplit();
	}

	public final CallCenterAddress getDistributingAddress() {
		return params.getDistributingDevice();
	}

	public final Address getLastRedirectedAddress() {
		return params.getLastRedirectionAddress();
	}

	public final LookaheadInfo getLookaheadInfo() {
		return params.getLookaheadInfo();
	}

	public final OriginalCallInfo getOriginalCallInfo() {
		return params.getOriginalCallInfo();
	}

	public final short getReason() {
		return params.getReason();
	}

	public CallCenterTrunk getTrunk() {
		return params.getTrunk();
	}

	public CallCenterTrunk[] getTrunks() {
		return params.getTrunks();
	}

	public final String getUCID() {
		return params.getUcid();
	}

	public final UserEnteredCode getUserEnteredCode() {
		return params.getUserEnteredCode();
	}

	public final UserToUserInfo getUserToUserInfo() {
		return params.getUserToUserInfo();
	}

	public final boolean hasCallOriginatorType() {
		return params.hasCallOriginatorType();
	}
}
