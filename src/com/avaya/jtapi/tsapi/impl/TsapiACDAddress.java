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
	TsapiACDAddress(final TSDevice _tsDevice) {
		super(_tsDevice);
		TsapiTrace.traceConstruction(this, TsapiACDAddress.class);
	}

	TsapiACDAddress(final TSProviderImpl tsProvider, final String number)
			throws TsapiInvalidArgumentException {
		super(tsProvider, number);
		TsapiTrace.traceConstruction(this, TsapiACDAddress.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TsapiACDAddress)
			return tsDevice.equals(((TsapiACDAddress) obj).tsDevice);

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
			final TSDevice[] acdManagerDevices = tsDevice
					.getTSACDManagerDevice();
			if (acdManagerDevices != null) {
				final int number = acdManagerDevices.length;
				final ACDManagerAddress[] acdManagerAddresses = new ACDManagerAddress[number];
				for (int i = 0; i < number; ++i)
					acdManagerAddresses[i] = new TsapiACDManagerAddress(
							acdManagerDevices[i]);
				final ACDManagerAddress mgr = acdManagerAddresses[0];
				TsapiTrace.traceExit("getACDManagerAddress[]", this);
				final ACDManagerAddress localACDManagerAddress1 = mgr;
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
			final int agentsAvailable = tsDevice.getTSAgentsAvailable();
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
			final int agentsLoggedIn = tsDevice.getTSAgentsLoggedIn();
			TsapiTrace.traceExit("getAgentsLoggedIn[]", this);
			return agentsLoggedIn;
		} finally {
			privData = null;
		}
	}

	public final Agent[] getLoggedOnAgents() {
		TsapiTrace.traceEntry("getLoggedOnAgents[]", this);
		try {
			final Vector<TSAgent> tsAgents = tsDevice.getTSAgentsForACDAddr();
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
				final Agent[] tsapiAgent = new Agent[tsAgents.size()];
				for (int i = 0; i < tsAgents.size(); ++i)
					tsapiAgent[i] = (Agent) TsapiCreateObject.getTsapiObject(
							tsAgents.elementAt(i), false);
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
			final int numberQueued = tsDevice.getTSNumberQueued();
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
			final TSCall tsCall = tsDevice.getTSOldestCallQueued();
			if (tsCall != null) {
				TsapiTrace.traceExit("getOldestCallQueued[]", this);
				return (Call) TsapiCreateObject.getTsapiObject(tsCall, false);
			}
			TsapiTrace.traceExit("getOldestCallQueued[]", this);
			final Call localCall = null;

			return localCall;
		} finally {
			privData = null;
		}

	}

	public final int getQueueWaitTime() throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("getQueueWaitTime[]", this);
		try {
			final int queueWaitTime = tsDevice.getTSQueueWaitTime();
			TsapiTrace.traceExit("getQueueWaitTime[]", this);
			final int i = queueWaitTime;

			return i;
		} finally {
			privData = null;
		}

	}

	public final int getRelativeQueueLoad()
			throws TsapiMethodNotSupportedException {
		try {
			TsapiTrace.traceEntry("getRelativeQueueLoad[]", this);
			final int relativeQueuedLoad = tsDevice.getTSRelativeQueueLoad();
			TsapiTrace.traceExit("getRelativeQueueLoad[]", this);
			final int i = relativeQueuedLoad;

			return i;
		} finally {
			privData = null;
		}

	}
}
