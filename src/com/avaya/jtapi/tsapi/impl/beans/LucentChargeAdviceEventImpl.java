package com.avaya.jtapi.tsapi.impl.beans;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentCall;
import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
import com.avaya.jtapi.tsapi.TsapiTrunk;

public class LucentChargeAdviceEventImpl implements LucentChargeAdviceEvent {
	private final short chargeType;
	private final int charge;
	private final short error;
	private final LucentCall call;
	private final ITsapiAddress calledDevice;
	private final ITsapiAddress chargingDevice;
	private final TsapiTrunk trunk;

	public LucentChargeAdviceEventImpl(final short _chargeType,
			final int _charge, final LucentCall _call,
			final ITsapiAddress _calledDevice,
			final ITsapiAddress _chargingDevice, final short _error,
			final TsapiTrunk _trunk) {
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
