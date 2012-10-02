package com.avaya.jtapi.tsapi.impl.beans;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentCall;
import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
import com.avaya.jtapi.tsapi.TsapiTrunk;

public class LucentChargeAdviceEventImpl implements LucentChargeAdviceEvent {
	private short chargeType;
	private int charge;
	private short error;
	private LucentCall call;
	private ITsapiAddress calledDevice;
	private ITsapiAddress chargingDevice;
	private TsapiTrunk trunk;

	public LucentChargeAdviceEventImpl(short _chargeType, int _charge,
			LucentCall _call, ITsapiAddress _calledDevice,
			ITsapiAddress _chargingDevice, short _error, TsapiTrunk _trunk) {
		this.chargeType = _chargeType;
		this.charge = _charge;
		this.call = _call;
		this.calledDevice = _calledDevice;
		this.chargingDevice = _chargingDevice;
		this.error = _error;
		this.trunk = _trunk;
	}

	public final LucentCall getCall() {
		return this.call;
	}

	public final LucentAddress getCalledAddress() {
		return (LucentAddress) this.calledDevice;
	}

	public final LucentAddress getChargingAddress() {
		return (LucentAddress) this.chargingDevice;
	}

	public final TsapiTrunk getTrunk() {
		return this.trunk;
	}

	public final int getCharge() {
		return this.charge;
	}

	public final short getChargeType() {
		return this.chargeType;
	}

	public final short getChargeError() {
		return this.error;
	}
}