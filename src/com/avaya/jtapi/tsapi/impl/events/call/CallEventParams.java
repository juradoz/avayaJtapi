package com.avaya.jtapi.tsapi.impl.events.call;

import java.util.ArrayList;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.Terminal;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterTrunk;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.LucentCall;
import com.avaya.jtapi.tsapi.LucentV5Call;
import com.avaya.jtapi.tsapi.LucentV5CallInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;

public class CallEventParams {
	private Call call;
	private CallCenterTrunk trunk;
	private CallCenterTrunk[] trunks;
	private Address callingAddress;
	private Terminal callingTerminal;
	private Address calledAddress;
	private Address lastRedirectionAddress;
	private ArrayList<Call> oldCalls;
	private int cause;
	private int metaCode;
	private short cstaCause;
	private Object privateData;
	private PrivateDataParams privDataParams;

	public CallEventParams() {
		call = null;

		trunk = null;
		trunks = null;
		callingAddress = null;
		callingTerminal = null;
		calledAddress = null;
		lastRedirectionAddress = null;

		cause = 0;
		metaCode = 0;

		cstaCause = -1;
		privateData = null;
	}

	public Call getCall() {
		return call;
	}

	public Address getCalledAddress() {
		return calledAddress;
	}

	public Address getCallingAddress() {
		return callingAddress;
	}

	public Terminal getCallingTerminal() {
		return callingTerminal;
	}

	public int getCallOriginatorType() {
		int cot = 0;
		if (privDataParams != null) {
			cot = privDataParams.getCallOriginatorType();
		} else if (call instanceof LucentV5CallInfo) {
			cot = ((LucentV5CallInfo) call).getCallOriginatorType();
		}
		return cot;
	}

	public int getCause() {
		return cause;
	}

	public short getCstaCause() {
		return cstaCause;
	}

	public ITsapiAddress getDistributingDevice() {
		ITsapiAddress address = null;
		if (privDataParams != null) {
			address = privDataParams.getDistributingDevice();
		} else {
			address = (ITsapiAddress) ((LucentCall) call)
					.getDistributingAddress();
		}
		return address;
	}

	public Address getLastRedirectionAddress() {
		return lastRedirectionAddress;
	}

	public LookaheadInfo getLookaheadInfo() {
		LookaheadInfo lai = null;
		if (privDataParams != null) {
			lai = privDataParams.getLookaheadInfo();
		} else {
			lai = ((LucentCall) call).getLookaheadInfo();
		}
		return lai;
	}

	public int getMetaCode() {
		return metaCode;
	}

	public ArrayList<Call> getOldCalls() {
		return oldCalls;
	}

	public OriginalCallInfo getOriginalCallInfo() {
		OriginalCallInfo originalCallInfo = null;
		if (privDataParams != null) {
			originalCallInfo = privDataParams.getOriginalCallInfo();
		} else {
			originalCallInfo = ((LucentCall) call).getOriginalCallInfo();
		}
		return originalCallInfo;
	}

	public Object getPrivateData() {
		return privateData;
	}

	public short getReason() {
		short reason = 0;
		if (privDataParams != null) {
			reason = privDataParams.getReason();
		} else {
			reason = ((LucentCall) call).getReason();
		}
		return reason;
	}

	public ACDAddress getSplit() {
		ACDAddress address = null;
		if (privDataParams != null) {
			address = privDataParams.getSplit();
		} else {
			address = ((LucentCall) call).getDeliveringACDAddress();
		}
		return address;
	}

	public CallCenterTrunk getTrunk() {
		return trunk;
	}

	public CallCenterTrunk[] getTrunks() {
		return trunks;
	}

	public String getUcid() {
		String ucid = null;
		if (privDataParams != null) {
			ucid = privDataParams.getUcid();
		} else if (call instanceof LucentV5Call) {
			ucid = ((LucentV5Call) call).getUCID();
		}
		return ucid;
	}

	public UserEnteredCode getUserEnteredCode() {
		UserEnteredCode uec = null;
		if (privDataParams != null) {
			uec = privDataParams.getUserEnteredCode();
		} else {
			uec = ((LucentCall) call).getUserEnteredCode();
		}
		return uec;
	}

	public UserToUserInfo getUserToUserInfo() {
		UserToUserInfo uui = null;
		if (privDataParams != null) {
			uui = privDataParams.getUserToUserInfo();
		} else {
			uui = ((LucentCall) call).getUserToUserInfo();
		}
		return uui;
	}

	public boolean hasCallOriginatorType() {
		boolean has = false;
		if (privDataParams != null) {
			has = privDataParams.hasCallOriginatorType();
		} else if (call instanceof LucentV5CallInfo) {
			has = ((LucentV5CallInfo) call).hasCallOriginatorType();
		}
		return has;
	}

	public boolean isFlexibleBilling() {
		boolean flexible = false;
		if (privDataParams != null) {
			flexible = privDataParams.isFlexibleBilling();
		}
		return flexible;
	}

	public void setCall(Call call) {
		this.call = call;
	}

	public void setCalledAddress(Address calledAddress) {
		this.calledAddress = calledAddress;
	}

	public void setCallingAddress(Address callingAddress) {
		this.callingAddress = callingAddress;
	}

	public void setCallingTerminal(Terminal callingTerminal) {
		this.callingTerminal = callingTerminal;
	}

	public void setCause(int cause) {
		this.cause = cause;
	}

	public void setCstaCause(short cstaCause) {
		this.cstaCause = cstaCause;
	}

	public void setLastRedirectionAddress(Address lastRedirectionAddress) {
		this.lastRedirectionAddress = lastRedirectionAddress;
	}

	public void setMetaCode(int metaCode) {
		this.metaCode = metaCode;
	}

	public void setOldCalls(ArrayList<Call> oldCalls) {
		this.oldCalls = oldCalls;
	}

	public void setPrivateData(Object privateData) {
		this.privateData = privateData;
		if (privateData instanceof PrivateDataParams) {
			privDataParams = ((PrivateDataParams) privateData);
			if (privDataParams.getTrunk() != null) {
				trunk = privDataParams.getTrunk();
			}
		}
	}

	public void setTrunk(CallCenterTrunk trunk) {
		this.trunk = trunk;
	}

	public void setTrunks(CallCenterTrunk[] _trunks) {
		trunks = _trunks;
	}
}

