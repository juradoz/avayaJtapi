package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.HandleConfOnCurrentThread;

final class AsyncQueryDeviceInfoConfHandler implements ConfHandler,
		HandleConfOnCurrentThread {
	TSDevice device;

	AsyncQueryDeviceInfoConfHandler(final TSDevice _device) {
		device = _device;
	}

	@Override
	public void handleConf(final CSTAEvent event) {
		try {
			if (event == null
					|| !(event.getEvent() instanceof CSTAQueryDeviceInfoConfEvent)) {
				device.setIsATerminal(false);
				device.setIsExternal(true);
				return;
			}
			final CSTAQueryDeviceInfoConfEvent devInfoConf = (CSTAQueryDeviceInfoConfEvent) event
					.getEvent();

			if (devInfoConf.getDeviceType() != 0
					&& devInfoConf.getDeviceType() != 2
					&& devInfoConf.getDeviceType() != 5
					&& devInfoConf.getDeviceType() != 18
					&& devInfoConf.getDeviceType() != 1)
				if (devInfoConf.getDeviceType() != 255) {
					device.setIsATerminal(false);

					final Object replyPriv = event.getPrivData();

					if (replyPriv instanceof LucentQueryDeviceInfoConfEvent) {
						if (((LucentQueryDeviceInfoConfEvent) replyPriv)
								.getExtensionClass() == 0)
							device.setDeviceType((short) 1);
						else if (((LucentQueryDeviceInfoConfEvent) replyPriv)
								.getExtensionClass() == 1)
							device.setDeviceType((short) 2);

						if (replyPriv instanceof LucentV5QueryDeviceInfoConfEvent) {
							device.setAssociatedDevice(((LucentV5QueryDeviceInfoConfEvent) replyPriv)
									.getAssociatedDevice());
							device.setAssociatedClass(((LucentV5QueryDeviceInfoConfEvent) replyPriv)
									.getAssociatedClass());
						}
					}
				}

		} catch (final Exception e) {
			device.setIsATerminal(false);
			device.setIsExternal(true);
		} finally {
			device.notifyAsyncInitializationComplete();
		}
	}
}
