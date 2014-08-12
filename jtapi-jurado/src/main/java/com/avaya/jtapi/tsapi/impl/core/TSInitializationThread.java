package com.avaya.jtapi.tsapi.impl.core;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor;

final class TSInitializationThread extends Thread {
	private static Logger log = Logger.getLogger(TSInitializationThread.class);
	TSProviderImpl provider;

	TSInitializationThread(TSProviderImpl _provider) {
		super("ProviderInitialization");
		this.provider = _provider;
	}

	public void run() {
		try {
			Vector<TSEvent> eventList = new Vector<TSEvent>();
			this.provider.setState(1, eventList);
			if (eventList.size() > 0) {
				Vector<?> observers = this.provider.getMonitors();

				for (int j = 0; j < observers.size(); j++) {
					TsapiProviderMonitor callback = (TsapiProviderMonitor) observers
							.elementAt(j);
					callback.deliverEvents(eventList, false);
				}

			}

			List<String> monitorableDevices = this.provider.getMonitorableDevices();
			if ((monitorableDevices != null)
					&& (monitorableDevices.size() != 0)) {
				this.provider.tsMonitorableDevices.addAll(monitorableDevices);
			}

			this.provider.setRouteDevices();

			eventList = new Vector<TSEvent>();
			this.provider.setState(2, eventList);
			if (eventList.size() > 0) {
				Vector<?> observers = this.provider.getMonitors();

				for (int j = 0; j < observers.size(); j++) {
					TsapiProviderMonitor callback = (TsapiProviderMonitor) observers
							.elementAt(j);
					callback.deliverEvents(eventList, false);
				}
			}

			synchronized (this) {
				notify();
			}
		} catch (Exception e) {
			log.error("INIT Thread Exception - shutting down provider "
					+ this.provider);
			log.error(e.getMessage(), e);
			try {
				this.provider.shutdown();
			} catch (Exception e1) {
				try {
					this.provider.tsapi.shutdown();
				} catch (Exception e2) {
				}
			}
		}
	}
}