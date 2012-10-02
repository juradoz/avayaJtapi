package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.ITsapiACDManagerAddress;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.csta1.LucentMonitorCallsViaDevice;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import java.util.Vector;
import javax.telephony.CallListener;
import javax.telephony.CallObserver;
import javax.telephony.callcenter.ACDAddress;

@SuppressWarnings("deprecation")
class TsapiACDManagerAddress extends TsapiCallCenterAddress implements
		ITsapiACDManagerAddress {
	public final ACDAddress[] getACDAddresses()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getACDAddresses[]", this);
		try {
			Vector<?> tsACDDevices = this.tsDevice.getTSACDDevices();

			if (tsACDDevices == null) {
				TsapiTrace.traceExit("getACDAddresses[]", this);
				return null;
			}

			synchronized (tsACDDevices) {
				if (tsACDDevices.size() == 0) {
					TsapiTrace.traceExit("getACDAddresses[]", this);
					return null;
				}

				ACDAddress[] tsapiAddress = new ACDAddress[tsACDDevices.size()];
				for (int i = 0; i < tsACDDevices.size(); i++) {
					tsapiAddress[i] = ((ACDAddress) TsapiCreateObject
							.getTsapiObject(
									(TSDevice) tsACDDevices.elementAt(i), true));
				}
				TsapiTrace.traceExit("getACDAddresses[]", this);
				return tsapiAddress;
			}
		} finally {
			this.privData = null;
		}
	}

	private LucentMonitorCallsViaDevice createLucentMonitorCallsViaDevice() {
		TsapiTrace.traceEntry("createLucentMonitorCallsViaDevice[]", this);
		TSProviderImpl TSProviderImpl = this.tsDevice.getTSProviderImpl();

		if (TSProviderImpl != null) {
			if ((TSProviderImpl.isLucentV7())
					&& (TSProviderImpl.getMonitorCallsViaDevice())) {
				LucentMonitorCallsViaDevice lmcvd = new LucentMonitorCallsViaDevice(
						true, 0);
				TsapiTrace.traceExit("createLucentMonitorCallsViaDevice[]",
						this);
				return lmcvd;
			}
		}
		TsapiTrace.traceExit("createLucentMonitorCallsViaDevice[]", this);
		return null;
	}

	private void addPredictiveCallEventMonitor(CallObserver observer,
			CallListener listener) throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException {
		TsapiTrace
				.traceEntry(
						"addPredictiveCallEventMonitor[CallObserver observer, CallListener listener]",
						this);
		try {
			TSProviderImpl prov = this.tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<?> observers = prov.getCallMonitorThreads();

			TsapiCallMonitor obs = null;
			TsapiCallMonitor obsToUse = null;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); i++) {
					obs = (TsapiCallMonitor) observers.elementAt(i);
					if (observer != null) {
						if (obs.getObserver() == observer) {
							obsToUse = obs;
							break;
						}
					} else if (listener != null) {
						if (obs.getListener() == listener) {
							obsToUse = obs;
							break;
						}
					}
				}

				if (obsToUse == null) {
					if (observer != null)
						obsToUse = new TsapiCallMonitor(prov, observer);
					else if (listener != null)
						obsToUse = new TsapiCallMonitor(prov, listener);
					if (obsToUse == null) {
						throw new TsapiPlatformException(4, 0,
								"could not allocate Monitor wrapper");
					}

				}

			}

			LucentMonitorCallsViaDevice lmcvd = createLucentMonitorCallsViaDevice();
			if (lmcvd != null)
				this.privData = lmcvd.makeTsapiPrivate();
			this.tsDevice.addAddressCallMonitor(obsToUse, true, true,
					this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace
				.traceExit(
						"addPredictiveCallEventMonitor(CallObserver observer, CallListener listener)",
						this);
	}

	public final void addPredictiveCallObserver(CallObserver observer)
			throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"addPredictiveCallObserver[CallObserver observer]", this);
		addPredictiveCallEventMonitor(observer, null);
		TsapiTrace.traceExit(
				"addPredictiveCallObserver[CallObserver observer]", this);
	}

	public final void addPredictiveCallListener(CallListener listener)
			throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"addPredictiveCallListener[CallListener listener]", this);
		addPredictiveCallEventMonitor(null, listener);
		TsapiTrace.traceExit(
				"addPredictiveCallListener[CallListener listener]", this);
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiACDManagerAddress)) {
			return this.tsDevice
					.equals(((TsapiACDManagerAddress) obj).tsDevice);
		}

		return false;
	}

	TsapiACDManagerAddress(TSProviderImpl TSProviderImpl, String number)
			throws TsapiInvalidArgumentException {
		super(TSProviderImpl, number);
		TsapiTrace.traceConstruction(this, TsapiACDManagerAddress.class);
	}

	TsapiACDManagerAddress(TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, TsapiACDManagerAddress.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDManagerAddress.class);
	}
}