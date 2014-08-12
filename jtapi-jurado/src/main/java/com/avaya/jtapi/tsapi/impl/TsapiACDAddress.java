package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import java.util.Vector;
import javax.telephony.Call;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.ACDManagerAddress;
import javax.telephony.callcenter.Agent;

class TsapiACDAddress extends TsapiCallCenterAddress implements ACDAddress {
	public final Agent[] getLoggedOnAgents() {
		TsapiTrace.traceEntry("getLoggedOnAgents[]", this);
		try {
			Vector<?> tsAgents = this.tsDevice.getTSAgentsForACDAddr();

			if (tsAgents == null) {
				TsapiTrace.traceExit("getLoggedOnAgents[]", this);
				return null;
			}

			synchronized (tsAgents) {
				if (tsAgents.size() == 0) {
					TsapiTrace.traceExit("getLoggedOnAgents[]", this);
					return null;
				}

				Agent[] tsapiAgent = new Agent[tsAgents.size()];
				for (int i = 0; i < tsAgents.size(); i++) {
					tsapiAgent[i] = ((Agent) TsapiCreateObject.getTsapiObject(
							(TSAgent) tsAgents.elementAt(i), false));
				}
				TsapiTrace.traceExit("getLoggedOnAgents[]", this);
				return tsapiAgent;
			}
		} finally {
			this.privData = null;
		}
	}

	public final int getNumberQueued() throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getNumberQueued[]", this);
		try {
			int numberQueued = this.tsDevice.getTSNumberQueued();
			TsapiTrace.traceExit("getNumberQueued[]", this);
			return numberQueued;
		} finally {
			this.privData = null;
		}
	}

	public final Call getOldestCallQueued()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getOldestCallQueued[]", this);
		try {
			TSCall tsCall = this.tsDevice.getTSOldestCallQueued();
			if (tsCall != null) {
				TsapiTrace.traceExit("getOldestCallQueued[]", this);
				return (Call) TsapiCreateObject.getTsapiObject(tsCall, false);
			}

			TsapiTrace.traceExit("getOldestCallQueued[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final int getRelativeQueueLoad()
			throws TsapiMethodNotSupportedException {
		try {
			TsapiTrace.traceEntry("getRelativeQueueLoad[]", this);
			int relativeQueuedLoad = this.tsDevice.getTSRelativeQueueLoad();
			TsapiTrace.traceExit("getRelativeQueueLoad[]", this);
			return relativeQueuedLoad;
		} finally {
			this.privData = null;
		}
	}

	public final int getQueueWaitTime() throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getQueueWaitTime[]", this);
		try {
			int queueWaitTime = this.tsDevice.getTSQueueWaitTime();
			TsapiTrace.traceExit("getQueueWaitTime[]", this);
			return queueWaitTime;
		} finally {
			this.privData = null;
		}
	}

	public final ACDManagerAddress getACDManagerAddress()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getACDManagerAddress[]", this);
		try {
			TSDevice[] acdManagerDevices = this.tsDevice
					.getTSACDManagerDevice();
			int number;
			if (acdManagerDevices != null) {
				number = acdManagerDevices.length;
				ACDManagerAddress[] acdManagerAddresses = new ACDManagerAddress[number];
				for (int i = 0; i < number; i++) {
					acdManagerAddresses[i] = new TsapiACDManagerAddress(
							acdManagerDevices[i]);
				}
				ACDManagerAddress mgr = acdManagerAddresses[0];
				TsapiTrace.traceExit("getACDManagerAddress[]", this);
				return mgr;
			}

			TsapiTrace.traceExit("getACDManagerAddress[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiACDAddress)) {
			return this.tsDevice.equals(((TsapiACDAddress) obj).tsDevice);
		}

		return false;
	}

	TsapiACDAddress(TSProviderImpl tsProvider, String number)
			throws TsapiInvalidArgumentException {
		super(tsProvider, number);
		TsapiTrace.traceConstruction(this, TsapiACDAddress.class);
	}

	TsapiACDAddress(TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, TsapiACDAddress.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDAddress.class);
	}
}