package com.avaya.jtapi.tsapi.impl;

import java.util.Vector;

import javax.telephony.CallObserver;
import javax.telephony.callcenter.ACDAddress;

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

@SuppressWarnings("deprecation")
class TsapiACDManagerAddress extends TsapiAddress implements
		ITsapiACDManagerAddress {
	TsapiACDManagerAddress(final TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, TsapiACDManagerAddress.class);
	}

	TsapiACDManagerAddress(final TSProviderImpl TSProviderImpl,
			final String number) throws TsapiInvalidArgumentException {
		super(TSProviderImpl, number);
		TsapiTrace.traceConstruction(this, TsapiACDManagerAddress.class);
	}

	public final void addPredictiveCallObserver(final CallObserver observer)
			throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException {
		TsapiTrace.traceEntry(
				"addPredictiveCallObserver[CallObserver observer]", this);
		try {
			final TSProviderImpl prov = tsDevice.getTSProviderImpl();

			if (prov == null)
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");

			final Vector<TsapiCallMonitor> observers = prov
					.getCallMonitorThreads();

			TsapiCallMonitor obs = null;
			TsapiCallMonitor obsToUse = null;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); ++i) {
					obs = observers.elementAt(i);
					if (obs.getObserver() != observer)
						continue;
					obsToUse = obs;
					break;
				}

				if (obsToUse == null) {
					obsToUse = new TsapiCallMonitor(prov, observer);
					if (obsToUse == null)
						throw new TsapiPlatformException(4, 0,
								"could not allocate Monitor wrapper");

				}

			}

			final LucentMonitorCallsViaDevice lmcvd = createLucentMonitorCallsViaDevice();
			privData = lmcvd.makeTsapiPrivate();
			tsDevice.addAddressCallMonitor(obsToUse, true, true, privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit(
				"addPredictiveCallObserver[CallObserver observer]", this);
	}

	private LucentMonitorCallsViaDevice createLucentMonitorCallsViaDevice() {
		TsapiTrace.traceEntry("createLucentMonitorCallsViaDevice[]", this);
		final TSProviderImpl TSProviderImpl = tsDevice.getTSProviderImpl();

		if (TSProviderImpl != null && TSProviderImpl.isLucentV7()
				&& TSProviderImpl.getMonitorCallsViaDevice()) {
			final LucentMonitorCallsViaDevice lmcvd = new LucentMonitorCallsViaDevice(
					true, 0);
			TsapiTrace.traceExit("createLucentMonitorCallsViaDevice[]", this);
			return lmcvd;
		}

		TsapiTrace.traceExit("createLucentMonitorCallsViaDevice[]", this);
		return null;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TsapiACDManagerAddress)
			return tsDevice.equals(((TsapiACDManagerAddress) obj).tsDevice);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDManagerAddress.class);
	}

	@Override
	public final javax.telephony.callcenter.ACDAddress[] getACDAddresses()
			throws TsapiMethodNotSupportedException {
		try {
			final Vector<TSDevice> tsACDDevices = tsDevice.getTSACDDevices();
			if (tsACDDevices == null)
				privData = null;
			synchronized (tsACDDevices) {
				if (tsACDDevices.size() == 0) {
					privData = null;
					return null;
				}
				final ACDAddress[] tsapiAddress = new ACDAddress[tsACDDevices
						.size()];
				for (int i = 0; i < tsACDDevices.size(); ++i)
					tsapiAddress[i] = (ACDAddress) TsapiCreateObject
							.getTsapiObject(tsACDDevices.elementAt(i), true);

				privData = null;
				return tsapiAddress;
			}
		} finally {
			privData = null;
		}
	}
}
