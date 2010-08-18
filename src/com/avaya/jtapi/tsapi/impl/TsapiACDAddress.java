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
	TsapiACDAddress(TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, TsapiACDAddress.class);
	}

	TsapiACDAddress(TSProviderImpl tsProvider, String number)
			throws TsapiInvalidArgumentException {
		super(tsProvider, number);
		TsapiTrace.traceConstruction(this, TsapiACDAddress.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TsapiACDAddress) {
			return tsDevice.equals(((TsapiACDAddress) obj).tsDevice);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiACDAddress.class);
	}

	public final ACDManagerAddress getACDManagerAddress()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getACDManagerAddress[]", this);
		try {
			TSDevice[] acdManagerDevices = tsDevice.getTSACDManagerDevice();
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
			privData = null;
		}

	}

	public final int getAgentsAvailable()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getAgentsAvailable[]", this);
		try {
			int agentsAvailable = tsDevice.getTSAgentsAvailable();
			TsapiTrace.traceExit("getAgentsAvailable[]", this);
			return agentsAvailable;
		} finally {
			privData = null;
		}
	}

	public final int getAgentsLoggedIn()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getAgentsLoggedIn[]", this);
		try {
			int agentsLoggedIn = tsDevice.getTSAgentsLoggedIn();
			TsapiTrace.traceExit("getAgentsLoggedIn[]", this);
			return agentsLoggedIn;
		} finally {
			privData = null;
		}
	}

	public final Agent[] getLoggedOnAgents() {
		TsapiTrace.traceEntry("getLoggedOnAgents[]", this);
		try {
			Vector<TSAgent> tsAgents = tsDevice.getTSAgentsForACDAddr();
			if (tsAgents == null) {
				TsapiTrace.traceExit("getLoggedOnAgents[]", this);
				privData = null;
			}
			synchronized (tsAgents) {
				if (tsAgents.size() == 0) {
					TsapiTrace.traceExit("getLoggedOnAgents[]", this);

					privData = null;
					return null;
				}
				Agent[] tsapiAgent = new Agent[tsAgents.size()];
				for (int i = 0; i < tsAgents.size(); ++i) {
					tsapiAgent[i] = ((Agent) TsapiCreateObject.getTsapiObject(
							tsAgents.elementAt(i), false));
				}
				TsapiTrace.traceExit("getLoggedOnAgents[]", this);

				privData = null;
				return tsapiAgent;
			}
		} finally {
			privData = null;
		}

	}

	public final int getNumberQueued() throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getNumberQueued[]", this);
		try {
			int numberQueued = tsDevice.getTSNumberQueued();
			TsapiTrace.traceExit("getNumberQueued[]", this);
			return numberQueued;
		} finally {
			privData = null;
		}

	}

	public final Call getOldestCallQueued()
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getOldestCallQueued[]", this);
		try {
			TSCall tsCall = tsDevice.getTSOldestCallQueued();
			if (tsCall != null) {
				TsapiTrace.traceExit("getOldestCallQueued[]", this);
				return (Call) TsapiCreateObject.getTsapiObject(tsCall, false);
			}
			TsapiTrace.traceExit("getOldestCallQueued[]", this);
			Call localCall = null;

			return localCall;
		} finally {
			privData = null;
		}

	}

	public final int getQueueWaitTime() throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getQueueWaitTime[]", this);
		try {
			int queueWaitTime = tsDevice.getTSQueueWaitTime();
			TsapiTrace.traceExit("getQueueWaitTime[]", this);
			int i = queueWaitTime;

			return i;
		} finally {
			privData = null;
		}

	}

	public final int getRelativeQueueLoad()
			throws TsapiMethodNotSupportedException {
		try {
			TsapiTrace.traceEntry("getRelativeQueueLoad[]", this);
			int relativeQueuedLoad = tsDevice.getTSRelativeQueueLoad();
			TsapiTrace.traceExit("getRelativeQueueLoad[]", this);
			int i = relativeQueuedLoad;

			return i;
		} finally {
			privData = null;
		}

	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiACDAddress JD-Core Version: 0.5.4
 */