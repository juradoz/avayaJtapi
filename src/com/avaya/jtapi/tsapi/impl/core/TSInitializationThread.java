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
		provider = _provider;
	}

	@Override
	public void run() {
		try {
			Vector<TSEvent> eventList = new Vector<TSEvent>();
			provider.setState(1, eventList);
			if (eventList.size() > 0) {
				Vector<?> observers = provider.getMonitors();

				for (int j = 0; j < observers.size(); ++j) {
					TsapiProviderMonitor callback = (TsapiProviderMonitor) observers
							.elementAt(j);
					callback.deliverEvents(eventList, false);
				}

			}

			List<String> monitorableDevices = provider.getMonitorableDevices();
			if ((monitorableDevices != null)
					&& (monitorableDevices.size() != 0)) {
				provider.tsMonitorableDevices.addAll(monitorableDevices);
			}

			provider.setRouteDevices();

			eventList = new Vector<TSEvent>();
			provider.setState(2, eventList);
			if (eventList.size() > 0) {
				Vector<TsapiProviderMonitor> observers = provider.getMonitors();

				for (int j = 0; j < observers.size(); ++j) {
					TsapiProviderMonitor callback = (TsapiProviderMonitor) observers
							.elementAt(j);
					callback.deliverEvents(eventList, false);
				}
			}

			synchronized (this) {
				super.notify();
			}
		} catch (Exception e) {
			log.error("INIT Thread Exception - shutting down provider "
					+ provider);
			log.error(e.getMessage(), e);
			try {
				provider.shutdown();
			} catch (Exception e1) {
				try {
					provider.tsapi.shutdown();
				} catch (Exception e2) {
				}
			}
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSInitializationThread JD-Core Version: 0.5.4
 */