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
		chargeType = _chargeType;
		charge = _charge;
		call = _call;
		calledDevice = _calledDevice;
		chargingDevice = _chargingDevice;
		error = _error;
		trunk = _trunk;
	}

	public final LucentCall getCall() {
		return call;
	}

	public final LucentAddress getCalledAddress() {
		return (LucentAddress) calledDevice;
	}

	public final int getCharge() {
		return charge;
	}

	public final short getChargeError() {
		return error;
	}

	public final short getChargeType() {
		return chargeType;
	}

	public final LucentAddress getChargingAddress() {
		return (LucentAddress) chargingDevice;
	}

	public final TsapiTrunk getTrunk() {
		return trunk;
	}
}
