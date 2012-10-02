package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.ITsapiAddress;
import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import java.util.ArrayList;
import java.util.Vector;
import javax.telephony.AddressListener;
import javax.telephony.AddressObserver;
import javax.telephony.CallListener;
import javax.telephony.CallObserver;
import javax.telephony.Connection;
import javax.telephony.InvalidArgumentException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PlatformException;
import javax.telephony.Provider;
import javax.telephony.ResourceUnavailableException;
import javax.telephony.Terminal;
import javax.telephony.callcenter.RouteCallback;
import javax.telephony.callcenter.RouteSession;
import javax.telephony.callcontrol.CallControlForwarding;
import javax.telephony.capabilities.AddressCapabilities;
import javax.telephony.privatedata.PrivateData;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
public class TsapiAddress implements ITsapiAddress, PrivateData, LucentAddress {
	private static Logger log = Logger.getLogger(TsapiAddress.class);
	TSDevice tsDevice;
	CSTAPrivate privData = null;
	String name;

	TsapiAddress(TSDevice _tsDevice) {
		this.tsDevice = _tsDevice;

		this.name = this.tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiAddress.class);
	}

	TsapiAddress(TSProviderImpl TSProviderImpl, String number)
			throws TsapiInvalidArgumentException {
		this.tsDevice = TSProviderImpl.createDevice(number, false);
		if (this.tsDevice == null) {
			throw new TsapiPlatformException(4, 0, "could not create");
		}
		this.name = this.tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiAddress.class);
	}

	TsapiAddress(TSProviderImpl TSProviderImpl, CSTAExtendedDeviceID deviceID)
			throws TsapiInvalidArgumentException {
		this.tsDevice = TSProviderImpl.createDevice(deviceID, false);
		if (this.tsDevice == null) {
			throw new TsapiPlatformException(4, 0, "could not create");
		}
		this.name = this.tsDevice.referenced();
		TsapiTrace.traceConstruction(this, TsapiAddress.class);
	}

	public final String getName() {
		TsapiTrace.traceEntry("getName[]", this);
		try {
			TsapiTrace.traceExit("getName[]", this);
			return this.name;
		} finally {
			this.privData = null;
		}
	}

	public final String getDirectoryName() {
		TsapiTrace.traceEntry("getDirectoryName[]", this);
		String name = this.tsDevice.getDirectoryName();
		TsapiTrace.traceExit("getDirectoryName[]", this);
		return name;
	}

	public final Provider getProvider() {
		TsapiTrace.traceEntry("getProvider[]", this);
		try {
			TSProviderImpl TSProviderImpl = this.tsDevice.getTSProviderImpl();
			if (TSProviderImpl != null) {
				Provider provider = (Provider) TsapiCreateObject
						.getTsapiObject(TSProviderImpl, false);
				TsapiTrace.traceExit("getProvider[]", this);
				return provider;
			}

			throw new TsapiPlatformException(4, 0, "could not locate provider");
		} finally {
			this.privData = null;
		}
	}

	public final Terminal[] getTerminals() {
		TsapiTrace.traceEntry("getTerminals[]", this);
		try {
			Vector<?> tsTermDevices = this.tsDevice.getTSTerminalDevices();

			if (tsTermDevices == null) {
				TsapiTrace.traceExit("getTerminals[]", this);
				return null;
			}

			synchronized (tsTermDevices) {
				if (tsTermDevices.size() == 0) {
					TsapiTrace.traceExit("getTerminals[]", this);
					return null;
				}

				Terminal[] tsapiTerm = new Terminal[tsTermDevices.size()];
				for (int i = 0; i < tsTermDevices.size(); i++) {
					tsapiTerm[i] = ((Terminal) TsapiCreateObject
							.getTsapiObject(
									(TSDevice) tsTermDevices.elementAt(i),
									false));
				}
				TsapiTrace.traceExit("getTerminals[]", this);
				return tsapiTerm;
			}
		} finally {
			this.privData = null;
		}
	}

	public final Connection[] getConnections() {
		TsapiTrace.traceEntry("getConnections[]", this);
		try {
			log.info("API CALL BEGIN: Address.getConnections() for " + this);
			try {
				Vector<?> tsconn = this.tsDevice.getTSConnections();

				if (tsconn == null) {
					TsapiTrace.traceExit("getConnections[]", this);
					Connection[] arrayOfConnection1 = null;

					this.privData = null;

					return arrayOfConnection1;
				}
				synchronized (tsconn) {
					if (tsconn.size() == 0) {
						TsapiTrace.traceExit("getConnections[]", this);
						Connection[] arrayOfConnection2 = null;

						this.privData = null;

						return arrayOfConnection2;
					}
					Connection[] tsapiConn = new Connection[tsconn.size()];
					for (int i = 0; i < tsconn.size(); i++) {
						tsapiConn[i] = ((Connection) TsapiCreateObject
								.getTsapiObject(
										(TSConnection) tsconn.elementAt(i),
										true));
					}
					TsapiTrace.traceExit("getConnections[]", this);

					this.privData = null;

					return tsapiConn;
				}
			} finally {
				this.privData = null;
			}

		} finally {
			log.info("API CALL END: Address.getConnections() for " + this);
		}
	}

	public final void addObserver(AddressObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addObserver[AddressObserver observer]", this);
		try {
			TSProviderImpl prov = null;
			prov = this.tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<?> observers = prov.getAddressMonitorThreads();

			TsapiAddressMonitor obs = null;

			boolean found = false;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); i++) {
					obs = (TsapiAddressMonitor) observers.elementAt(i);
					if (obs.getObserver() == observer) {
						found = true;
						break;
					}
				}
				if (!found) {
					obs = new TsapiAddressMonitor(prov, observer);
				}

			}

			try {
				this.tsDevice.addAddressMonitor(obs);
			} catch (TsapiResourceUnavailableException e) {
				if ((!found) && (obs != null)) {
					prov.removeAddressMonitorThread(obs);
				}
				throw e;
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("addObserver[AddressObserver observer]", this);
	}

	public final AddressObserver[] getObservers() {
		TsapiTrace.traceEntry("getObservers[]", this);
		try {
			Vector<?> tsapiAddressObservers = this.tsDevice.getAddressObservers();

			if ((tsapiAddressObservers == null)
					|| (tsapiAddressObservers.size() == 0)) {
				TsapiTrace.traceExit("getObservers[]", this);
				return null;
			}

			ArrayList<AddressObserver> observers = new ArrayList<AddressObserver>();
			TsapiAddressMonitor obs;
			AddressObserver addressObserver;
			for (int i = 0; i < tsapiAddressObservers.size(); i++) {
				obs = (TsapiAddressMonitor) tsapiAddressObservers.elementAt(i);
				addressObserver = obs.getObserver();
				if (addressObserver != null) {
					observers.add(addressObserver);
				}
			}
			int size = observers.size();
			if (size == 0) {
				TsapiTrace.traceExit("getObservers[]", this);
				return null;
			}
			AddressObserver[] arrayOfObservers = new AddressObserver[size];
			observers.toArray(arrayOfObservers);
			TsapiTrace.traceExit("getObservers[]", this);
			return arrayOfObservers;
		} finally {
			this.privData = null;
		}
	}

	public final void removeObserver(AddressObserver observer) {
		TsapiTrace.traceEntry("removeObserver[AddressObserver observer]", this);
		try {
			Vector<TsapiAddressMonitor> tsapiAddressObservers = this.tsDevice.getAddressObservers();

			if ((tsapiAddressObservers == null)
					|| (tsapiAddressObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeObserver[AddressObserver observer]", this);
				return;
			}
			for (int i = 0; i < tsapiAddressObservers.size();) {
				TsapiAddressMonitor obs = (TsapiAddressMonitor) tsapiAddressObservers
						.elementAt(i);
				if (obs.getObserver() == observer) {
					this.tsDevice.removeAddressMonitor(obs);
					TsapiTrace.traceExit(
							"removeObserver[AddressObserver observer]", this);
					return;
				}
				i++;
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("removeObserver[AddressObserver observer]", this);
	}

	public final void addCallObserver(CallObserver observer)
			throws TsapiResourceUnavailableException {
		TsapiTrace.traceEntry("addCallObserver[CallObserver observer]", this);
		try {
			addCallObserver(observer, false);
		} catch (TsapiMethodNotSupportedException e) {
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("addCallObserver[CallObserver observer]", this);
	}

	public final CallObserver[] getCallObservers() {
		try {
			TsapiTrace.traceEntry("getCallObservers[]", this);
			Vector<TsapiCallMonitor> tsapiAddressCallObservers = this.tsDevice
					.getAddressCallObservers();

			if ((tsapiAddressCallObservers == null)
					|| (tsapiAddressCallObservers.size() == 0)) {
				TsapiTrace.traceExit("getCallObservers[]", this);
				return null;
			}

			ArrayList<CallObserver> observerList = new ArrayList<CallObserver>();
			CallObserver[] observers = null;

			for (TsapiCallMonitor obs : tsapiAddressCallObservers) {
				CallObserver observer = obs.getObserver();
				if (observer != null)
					observerList.add(observer);
			}
			observers = new CallObserver[observerList.size()];
			TsapiTrace.traceExit("getCallObservers[]", this);
			return (CallObserver[]) observerList.toArray(observers);
		} finally {
			this.privData = null;
		}
	}

	public final void removeCallObserver(CallObserver observer) {
		TsapiTrace
				.traceEntry("removeCallObserver[CallObserver observer]", this);
		try {
			Vector<TsapiCallMonitor> tsapiAddressCallObservers = this.tsDevice.getAddressCallObservers();

			if ((tsapiAddressCallObservers == null)
					|| (tsapiAddressCallObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeCallObserver[CallObserver observer]", this);
				return;
			}
			for (int i = 0; i < tsapiAddressCallObservers.size();) {
				TsapiCallMonitor obs = (TsapiCallMonitor) tsapiAddressCallObservers
						.elementAt(i);
				if (obs.getObserver() == observer) {
					this.tsDevice.removeAddressCallMonitor(obs);
					TsapiTrace.traceExit(
							"removeCallObserver[CallObserver observer]", this);
					return;
				}
				i++;
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("removeCallObserver[CallObserver observer]", this);
	}

	public final AddressCapabilities getCapabilities() {
		TsapiTrace.traceEntry("getCapabilities[]", this);
		try {
			AddressCapabilities caps = this.tsDevice
					.getTsapiAddressCapabilities();
			TsapiTrace.traceExit("getCapabilities[]", this);
			return caps;
		} finally {
			this.privData = null;
		}
	}

	public final AddressCapabilities getAddressCapabilities(Terminal terminal)
			throws InvalidArgumentException, PlatformException {
		TsapiTrace
				.traceEntry("getAddressCapabilities[Terminal terminal]", this);
		AddressCapabilities addressCapabilities = getCapabilities();
		TsapiTrace.traceExit("getAddressCapabilities[Terminal terminal]", this);
		return addressCapabilities;
	}

	public final void setForwarding(CallControlForwarding[] instructions)
			throws TsapiMethodNotSupportedException,
			TsapiInvalidArgumentException, TsapiInvalidStateException {
		TsapiTrace.traceEntry(
				"setForwarding[CallControlForwarding[] instructions]", this);
		try {
			Vector<TsapiCallControlForwarding> fwdVector = new Vector<TsapiCallControlForwarding>();
			TsapiCallControlForwarding fwd = null;
			for (int i = 0; i < instructions.length; i++) {
				if (instructions[i].getFilter() == 4) {
					throw new TsapiInvalidArgumentException(3, 0,
							"forwarding from a specific address unsupported");
				}
				if ((instructions[i].getType() == 1)
						&& (instructions[i].getFilter() != 1)) {
					throw new TsapiInvalidArgumentException(3, 0,
							"only unconditional forwarding supported for all calls");
				}
				if (instructions[i].getFilter() == 1) {
					fwd = new TsapiCallControlForwarding(
							instructions[i].getDestinationAddress(),
							instructions[i].getType());
				} else {
					fwd = new TsapiCallControlForwarding(
							instructions[i].getDestinationAddress(),
							instructions[i].getType(),
							instructions[i].getFilter() == 2);
				}

				fwdVector.addElement(fwd);
			}

			this.tsDevice.setForwarding(fwdVector, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit(
				"setForwarding[CallControlForwarding[] instructions]", this);
	}

	public final CallControlForwarding[] getForwarding()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getForwarding[]", this);
		try {
			Vector<?> fwdVector = this.tsDevice.getForwarding();

			if ((fwdVector == null) || (fwdVector.size() == 0)) {
				TsapiTrace.traceExit("getForwarding[]", this);
				return null;
			}

			TsapiCallControlForwarding[] tsapiInstructions = new TsapiCallControlForwarding[fwdVector
					.size()];
			CallControlForwarding[] instructions = new CallControlForwarding[fwdVector
					.size()];
			fwdVector.copyInto(tsapiInstructions);
			for (int i = 0; i < tsapiInstructions.length; i++) {
				if (tsapiInstructions[i].getFilter() == 1) {
					instructions[i] = new CallControlForwarding(
							tsapiInstructions[i].getDestinationAddress(),
							tsapiInstructions[i].getType());
				} else {
					instructions[i] = new CallControlForwarding(
							tsapiInstructions[i].getDestinationAddress(),
							tsapiInstructions[i].getType(),
							tsapiInstructions[i].getFilter() == 2);
				}

			}

			TsapiTrace.traceExit("getForwarding[]", this);
			return instructions;
		} finally {
			this.privData = null;
		}
	}

	public final void cancelForwarding()
			throws TsapiMethodNotSupportedException, TsapiInvalidStateException {
		TsapiTrace.traceEntry("cancelForwarding[]", this);
		try {
			this.tsDevice.cancelForwarding(this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("cancelForwarding[]", this);
	}

	public final boolean getDoNotDisturb()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getDoNotDisturb[]", this);
		try {
			boolean dont = this.tsDevice.getDoNotDisturb();
			TsapiTrace.traceExit("getDoNotDisturb[]", this);
			return dont;
		} finally {
			this.privData = null;
		}
	}

	public final void setDoNotDisturb(boolean enable)
			throws TsapiMethodNotSupportedException, TsapiInvalidStateException {
		TsapiTrace.traceEntry("setDoNotDisturb[boolean enable]", this);
		try {
			this.tsDevice.setDoNotDisturb(enable, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("setDoNotDisturb[boolean enable]", this);
	}

	public final boolean getMessageWaiting()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getMessageWaiting[]", this);
		try {
			boolean msgWaiting = this.tsDevice.getMessageWaitingBits() != 0;
			TsapiTrace.traceExit("getMessageWaiting[]", this);
			return msgWaiting;
		} finally {
			this.privData = null;
		}
	}

	public final int getMessageWaitingBits()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getMessageWaitingBits[]", this);
		try {
			int i = this.tsDevice.getMessageWaitingBits();
			TsapiTrace.traceExit("getMessageWaitingBits[]", this);
			return i;
		} finally {
			this.privData = null;
		}
	}

	public final void setMessageWaiting(boolean enable)
			throws TsapiMethodNotSupportedException, TsapiInvalidStateException {
		TsapiTrace.traceEntry("setMessageWaiting[boolean enable]", this);
		try {
			this.tsDevice.setMessageWaiting(enable, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("setMessageWaiting[boolean enable]", this);
	}

	public final void addCallObserver(CallObserver observer, boolean remain)
			throws TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry(
				"addCallObserver[CallObserver observer, boolean remain]", this);
		addCallEventMonitor(observer, remain, null);
		TsapiTrace.traceExit(
				"addCallObserver[CallObserver observer, boolean remain]", this);
	}

	protected void addCallEventMonitor(CallObserver observer, boolean remain,
			CallListener listener) throws TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"addCallEventMonitor(CallObserver observer, boolean remain,CallListener listener)",
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
					} else if ((listener != null)
							&& (obs.getListener() == listener)) {
						obsToUse = obs;

						break;
					}

				}

				if (obsToUse == null) {
					if (observer != null) {
						obsToUse = new TsapiCallMonitor(prov, observer);
					} else if (listener != null) {
						obsToUse = new TsapiCallMonitor(prov, listener);
					}
					if (obsToUse == null) {
						throw new TsapiPlatformException(4, 0,
								"could not allocate Monitor wrapper");
					}

				}

				if (this.tsDevice.getDeviceType() == 1)
					obsToUse.setVDN(true);
				else {
					obsToUse.setVDN(false);
				}

			}

			this.tsDevice.addAddressCallMonitor(obsToUse, remain);
		} finally {
			this.privData = null;
		}
		TsapiTrace
				.traceExit(
						"addCallEventMonitor(CallObserver observer, boolean remain,CallListener listener)",
						this);
	}

	public final void setPrivateData(Object data) {
		TsapiTrace.traceEntry("setPrivateData[Object data]", this);
		try {
			this.privData = TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data);
		} catch (ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}

		TsapiTrace.traceExit("setPrivateData[Object data]", this);
	}

	public final Object getPrivateData() {
		TsapiTrace.traceEntry("getPrivateData[]", this);
		Object priv = this.tsDevice.getAddrPrivateData();
		Object obj = null;
		if (priv != null)
			obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate) priv);
		TsapiTrace.traceExit("getPrivateData[]", this);
		return obj;
	}

	public final Object sendPrivateData(Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		try {
			Object obj = this.tsDevice.sendPrivateData(TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data));
			TsapiTrace.traceExit("sendPrivateData[Object data]", this);
			return obj;
		} catch (ClassCastException e) {
		}
		throw new TsapiPlatformException(3, 0,
				"data is not a TsapiPrivate object");
	}

	public final void registerRouteCallback(RouteCallback routeCallback)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry(
				"registerRouteCallback[RouteCallback routeCallback]", this);
		try {
			TSProviderImpl prov = null;
			prov = this.tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<?> observers = prov.getRouteMonitorThreads();

			TsapiRouteMonitor obs = null;

			synchronized (observers) {
				for (int i = 0; i < observers.size();) {
					obs = (TsapiRouteMonitor) observers.elementAt(i);
					if (obs.getObserver() == routeCallback) {
						this.tsDevice.addRouteMonitor(obs);
						TsapiTrace
								.traceExit(
										"registerRouteCallback[RouteCallback routeCallback]",
										this);
						return;
					}
					i++;
				}

				obs = new TsapiRouteMonitor(prov, routeCallback);
			}

			this.tsDevice.addRouteMonitor(obs);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit(
				"registerRouteCallback[RouteCallback routeCallback]", this);
	}

	public final void cancelRouteCallback(RouteCallback routeCallback) {
		TsapiTrace.traceEntry(
				"cancelRouteCallback[RouteCallback routeCallback]", this);
		try {
			Vector<?> tsapiRouteObservers = this.tsDevice.getRouteObservers();

			if ((tsapiRouteObservers == null)
					|| (tsapiRouteObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"cancelRouteCallback[RouteCallback routeCallback]",
						this);
				return;
			}
			int i;
			synchronized (tsapiRouteObservers) {
				for (i = 0; i < tsapiRouteObservers.size();) {
					TsapiRouteMonitor obs = (TsapiRouteMonitor) tsapiRouteObservers
							.elementAt(i);
					if (obs.getObserver() == routeCallback) {
						this.tsDevice.removeRouteMonitor(obs);
						TsapiTrace
								.traceExit(
										"cancelRouteCallback[RouteCallback routeCallback]",
										this);
						return;
					}
					i++;
				}

			}

		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit(
				"cancelRouteCallback[RouteCallback routeCallback]", this);
	}

	public final RouteCallback[] getRouteCallback() {
		TsapiTrace.traceEntry("getRouteCallback[]", this);
		try {
			Vector<?> tsapiRouteObservers = this.tsDevice.getRouteObservers();

			if ((tsapiRouteObservers == null)
					|| (tsapiRouteObservers.size() == 0)) {
				TsapiTrace.traceExit("getRouteCallback[]", this);
				return null;
			}

			synchronized (tsapiRouteObservers) {
				RouteCallback[] observers = new RouteCallback[tsapiRouteObservers
						.size()];

				for (int i = 0; i < tsapiRouteObservers.size(); i++) {
					TsapiRouteMonitor obs = (TsapiRouteMonitor) tsapiRouteObservers
							.elementAt(i);
					observers[i] = obs.getObserver();
				}
				TsapiTrace.traceExit("getRouteCallback[]", this);
				return observers;
			}
		} finally {
			this.privData = null;
		}
	}

	public final RouteSession[] getActiveRouteSessions() {
		TsapiTrace.traceEntry("getActiveRouteSessions[]", this);
		try {
			Vector<?> tsSession = this.tsDevice.getTSRouteSessions();

			if (tsSession == null) {
				TsapiTrace.traceExit("getActiveRouteSessions[]", this);
				return null;
			}

			synchronized (tsSession) {
				if (tsSession.size() == 0) {
					TsapiTrace.traceExit("getActiveRouteSessions[]", this);
					return null;
				}

				RouteSession[] tsapiSessions = new RouteSession[tsSession
						.size()];
				for (int i = 0; i < tsSession.size(); i++) {
					tsapiSessions[i] = ((RouteSession) TsapiCreateObject
							.getTsapiObject(
									(TSRouteSession) tsSession.elementAt(i),
									false));
				}
				TsapiTrace.traceExit("getActiveRouteSessions[]", this);
				return tsapiSessions;
			}
		} finally {
			this.privData = null;
		}
	}

	public final int hashCode() {
		return this.tsDevice.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiAddress)) {
			return this.tsDevice.equals(((TsapiAddress) obj).tsDevice);
		}

		return false;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		if (this.tsDevice != null) {
			this.tsDevice.unreferenced();
			this.tsDevice = null;
		}
		TsapiTrace.traceDestruction(this, TsapiAddress.class);
	}

	public final TSDevice getTSDevice() {
		TsapiTrace.traceEntry("getTSDevice[]", this);
		TsapiTrace.traceExit("getTSDevice[]", this);
		return this.tsDevice;
	}

	public void addAddressListener(AddressListener listener)
			throws ResourceUnavailableException {
		TsapiTrace.traceEntry("addAddressListener[AddressListener listener]",
				this);
		try {
			TSProviderImpl prov = null;
			prov = this.tsDevice.getTSProviderImpl();

			if (prov == null) {
				throw new TsapiPlatformException(4, 0,
						"could not locate provider");
			}

			Vector<?> observers = prov.getAddressMonitorThreads();

			TsapiAddressMonitor obs = null;

			boolean found = false;

			synchronized (observers) {
				for (int i = 0; i < observers.size(); i++) {
					obs = (TsapiAddressMonitor) observers.elementAt(i);
					if (obs.getAddressListener() == listener) {
						found = true;
						break;
					}
				}
				if (!found) {
					obs = new TsapiAddressMonitor(prov, listener);
				}

			}

			try {
				this.tsDevice.addAddressMonitor(obs);
			} catch (TsapiResourceUnavailableException e) {
				if ((!found) && (obs != null)) {
					prov.removeAddressMonitorThread(obs);
				}
				throw e;
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("addAddressListener[AddressListener listener]",
				this);
	}

	public AddressListener[] getAddressListeners() {
		TsapiTrace.traceEntry("getAddressListeners[]", this);
		try {
			Vector<?> tsapiAddressObservers = this.tsDevice.getAddressObservers();

			if ((tsapiAddressObservers == null)
					|| (tsapiAddressObservers.size() == 0)) {
				TsapiTrace.traceExit("getAddressListeners[]", this);
				return null;
			}

			ArrayList<AddressListener> listeners = new ArrayList<AddressListener>();
			TsapiAddressMonitor obs;
			AddressListener addressListener;
			for (int i = 0; i < tsapiAddressObservers.size(); i++) {
				obs = (TsapiAddressMonitor) tsapiAddressObservers.elementAt(i);
				addressListener = obs.getAddressListener();
				if (addressListener != null) {
					listeners.add(addressListener);
				}
			}
			int size = listeners.size();
			if (size == 0) {
				TsapiTrace.traceExit("getAddressListeners[]", this);
				return null;
			}
			AddressListener[] arrayOfListeners = new AddressListener[size];
			listeners.toArray(arrayOfListeners);
			TsapiTrace.traceExit("getAddressListeners[]", this);
			return arrayOfListeners;
		} finally {
			this.privData = null;
		}
	}

	public void removeAddressListener(AddressListener listener) {
		TsapiTrace.traceEntry(
				"removeAddressListener[AddressListener listener]", this);
		try {
			Vector<TsapiAddressMonitor> tsapiAddressObservers = this.tsDevice.getAddressObservers();

			if ((tsapiAddressObservers == null)
					|| (tsapiAddressObservers.size() == 0)) {
				TsapiTrace
						.traceExit(
								"removeAddressListener[AddressListener listener]",
								this);
				return;
			}
			for (int i = 0; i < tsapiAddressObservers.size();) {
				TsapiAddressMonitor obs = (TsapiAddressMonitor) tsapiAddressObservers
						.elementAt(i);
				if (obs.getAddressListener() == listener) {
					this.tsDevice.removeAddressMonitor(obs);
					TsapiTrace.traceExit(
							"removeAddressListener[AddressListener listener]",
							this);
					return;
				}
				i++;
			}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("removeAddressListener[AddressListener listener]",
				this);
	}

	public void addCallListener(CallListener listener)
			throws ResourceUnavailableException, MethodNotSupportedException {
		TsapiTrace.traceEntry("addCallListener[CallListener listener]", this);
		addCallEventMonitor(null, false, listener);
		TsapiTrace.traceExit("addCallListener[CallListener listener]", this);
	}

	public CallListener[] getCallListeners() {
		TsapiTrace.traceEntry("getCallListeners[]", this);
		try {
			Vector<TsapiCallMonitor> tsapiAddressCallObservers = this.tsDevice
					.getAddressCallObservers();

			if ((tsapiAddressCallObservers == null)
					|| (tsapiAddressCallObservers.size() == 0)) {
				TsapiTrace.traceExit("getCallListeners[]", this);
				return null;
			}

			ArrayList<CallListener> listenerList = new ArrayList<CallListener>();
			CallListener[] listeners = null;

			synchronized (tsapiAddressCallObservers) {
				for (TsapiCallMonitor obs : tsapiAddressCallObservers) {
					CallListener listener = obs.getListener();
					if (listener != null)
						listenerList.add(listener);
				}
			}
			listeners = new CallListener[listenerList.size()];
			TsapiTrace.traceExit("getCallListeners[]", this);
			return (CallListener[]) listenerList.toArray(listeners);
		} finally {
			this.privData = null;
		}
	}

	public void removeCallListener(CallListener listener) {
		TsapiTrace
				.traceEntry("removeCallListener[CallListener listener]", this);
		try {
			Vector<TsapiCallMonitor> tsapiAddressCallObservers = this.tsDevice
					.getAddressCallObservers();

			if ((tsapiAddressCallObservers == null)
					|| (tsapiAddressCallObservers.size() == 0)) {
				TsapiTrace.traceExit(
						"removeCallListener[CallListener listener]", this);
				return;
			}

			for (TsapiCallMonitor obs : tsapiAddressCallObservers)
				if (obs.getListener() == listener) {
					this.tsDevice.removeAddressCallMonitor(obs);
					TsapiTrace.traceExit(
							"removeCallListener[CallListener listener]", this);
					return;
				}
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
	}

	public boolean isOnSwitch() {
		TsapiTrace.traceEntry("isOnSwitch[]", this);
		boolean result = ((CSTAExtendedDeviceID) this.tsDevice
				.getDevNameVector().lastElement()).hasPrivateDeviceIDType();
		TsapiTrace.traceExit("isOnSwitch[]", this);
		return result;
	}
}