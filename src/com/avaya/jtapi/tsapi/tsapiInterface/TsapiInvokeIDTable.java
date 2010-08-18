package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class TsapiInvokeIDTable {
	private static Logger log = Logger.getLogger(TsapiInvokeIDTable.class);
	private final Hashtable<Integer, TSInvokeID> invokeIDTbl;
	private int nextInvokeID = 1;
	private final String debugID;

	public TsapiInvokeIDTable(final String _debugID) {
		debugID = _debugID;
		invokeIDTbl = new Hashtable<Integer, TSInvokeID>();
	}

	public TSInvokeID allocTSInvokeID(final ConfHandler handler) {
		synchronized (invokeIDTbl) {
			final TSInvokeID invokeID = new TSInvokeID(nextInvokeID, handler,
					debugID);
			final Object oldObj = invokeIDTbl.put(new Integer(nextInvokeID),
					invokeID);
			if (oldObj != null)
				TsapiInvokeIDTable.log
						.info("NOTICE: invokeIDTbl.put() replaced " + oldObj
								+ " for " + debugID);
			nextInvokeID += 1;

			return invokeID;
		}
	}

	public void deallocTSInvokeID(final TSInvokeID invokeID) {
		if (invokeIDTbl.remove(new Integer(invokeID.getValue())) != null)
			return;
		TsapiInvokeIDTable.log.info("couldn't dealloc invokeID "
				+ invokeID.getValue() + " for " + debugID);
	}

	public TSInvokeID getTSInvokeID(final int value) {
		final TSInvokeID invokeID = invokeIDTbl.get(new Integer(value));
		if (invokeID == null || invokeID.getValue() != value)
			TsapiInvokeIDTable.log.info("couldn't find invokeID " + value
					+ " for " + debugID);
		return invokeID;
	}

	public void requestTimeOut(final ConfHandler handler) {
		final Iterator<TSInvokeID> ids = invokeIDTbl.values().iterator();
		while (ids.hasNext()) {
			final TSInvokeID id = ids.next();
			if (id.getConfHandler().equals(handler)) {
				deallocTSInvokeID(id);
				return;
			}
		}
	}

	public void shutdown() {
		final Iterator<TSInvokeID> ids = invokeIDTbl.values().iterator();
		while (ids.hasNext()) {
			final TSInvokeID id = ids.next();
			id.setConf(null);
		}
	}
}
