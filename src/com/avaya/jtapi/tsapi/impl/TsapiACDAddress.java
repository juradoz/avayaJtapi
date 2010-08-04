package com.avaya.jtapi.tsapi.impl;

import java.util.Vector;

import javax.telephony.Call;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.ACDManagerAddress;
import javax.telephony.callcenter.Agent;

import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

class TsapiACDAddress extends TsapiAddress implements ACDAddress {
	public final Agent[] getLoggedOnAgents() {
		TsapiTrace.traceEntry("getLoggedOnAgents[]", this);
		try {
			Vector tsAgents = this.tsDevice.getTSAgentsForACDAddr();
			Object localObject1;
			if (tsAgents == null) {
				TsapiTrace.traceExit("getLoggedOnAgents[]", this);
				localObject1 = null;

				this.privData = null;
			}
			synchronized (tsAgents) {
				if (tsAgents.size() == 0) {
					TsapiTrace.traceExit("getLoggedOnAgents[]", this);

					this.privData = null;
					return null;
				}
				Agent[] tsapiAgent = new Agent[tsAgents.size()];
				for (int i = 0; i < tsAgents.size(); ++i) {
					tsapiAgent[i] = ((Agent) TsapiCreateObject.getTsapiObject(
							(TSAgent) tsAgents.elementAt(i), false));
				}
				TsapiTrace.traceExit("getLoggedOnAgents[]", this);

				this.privData = null;
				return tsapiAgent;
			}
		} finally {
			this.privData = null;
		}

	}

	public final int getAgentsLoggedIn()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getAgentsLoggedIn[]", this);
		try {
			int agentsLoggedIn = this.tsDevice.getTSAgentsLoggedIn();
			TsapiTrace.traceExit("getAgentsLoggedIn[]", this);
			return agentsLoggedIn;
		} finally {
			this.privData = null;
		}
	}

	public final int getAgentsAvailable()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getAgentsAvailable[]", this);
		try {
			int agentsAvailable = this.tsDevice.getTSAgentsAvailable();
			TsapiTrace.traceExit("getAgentsAvailable[]", this);
			return agentsAvailable;
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
			Call localCall = null;

			return localCall;
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
			int i = relativeQueuedLoad;

			return i;
		} finally {
			this.privData = null;
		}

	}

	public final int getQueueWaitTime() throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getQueueWaitTime[]", this);
		try {
			int queueWaitTime = this.tsDevice.getTSQueueWaitTime();
			TsapiTrace.traceExit("getQueueWaitTime[]", this);
			int i = queueWaitTime;

			return i;
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
			if (acdManagerDevices != null) {
				int number = acdManagerDevices.length;
				ACDManagerAddress[] acdManagerAddresses = new ACDManagerAddress[number];
				for (int i = 0; i < number; ++i) {
					acdManagerAddresses[i] = new TsapiACDManagerAddress(
							acdManagerDevices[i]);
				}
				ACDManagerAddress mgr = acdManagerAddresses[0];
				TsapiTrace.traceExit("getACDManagerAddress[]", this);
				ACDManagerAddress localACDManagerAddress1 = mgr;
				return localACDManagerAddress1;
			}
			TsapiTrace.traceExit("getACDManagerAddress[]", this);

			return null;
		} finally {
			this.privData = null;
		}

	}

	public boolean equals(Object obj) {
		if (obj instanceof TsapiACDAddress) {
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiACDAddress JD-Core Version: 0.5.4
 */