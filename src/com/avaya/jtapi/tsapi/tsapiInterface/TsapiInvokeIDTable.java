package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class TsapiInvokeIDTable {
	private static Logger log = Logger.getLogger(TsapiInvokeIDTable.class);
	private Hashtable<Integer, TSInvokeID> invokeIDTbl;
	private int nextInvokeID = 1;
	private String debugID;

	public TsapiInvokeIDTable(String _debugID) {
		debugID = _debugID;
		invokeIDTbl = new Hashtable<Integer, TSInvokeID>();
	}

	public TSInvokeID allocTSInvokeID(ConfHandler handler) {
		synchronized (invokeIDTbl) {
			TSInvokeID invokeID = new TSInvokeID(nextInvokeID, handler, debugID);
			Object oldObj = invokeIDTbl
					.put(new Integer(nextInvokeID), invokeID);
			if (oldObj != null) {
				log.info("NOTICE: invokeIDTbl.put() replaced " + oldObj
						+ " for " + debugID);
			}
			nextInvokeID += 1;

			return invokeID;
		}
	}

	public void deallocTSInvokeID(TSInvokeID invokeID) {
		if (invokeIDTbl.remove(new Integer(invokeID.getValue())) != null) {
			return;
		}
		log.info("couldn't dealloc invokeID " + invokeID.getValue() + " for "
				+ debugID);
	}

	public TSInvokeID getTSInvokeID(int value) {
		TSInvokeID invokeID = invokeIDTbl.get(new Integer(value));
		if ((invokeID == null) || (invokeID.getValue() != value)) {
			log.info("couldn't find invokeID " + value + " for " + debugID);
		}
		return invokeID;
	}

	public void requestTimeOut(ConfHandler handler) {
		Iterator<TSInvokeID> ids = invokeIDTbl.values().iterator();
		while (ids.hasNext()) {
			TSInvokeID id = ids.next();
			if (id.getConfHandler().equals(handler)) {
				deallocTSInvokeID(id);
				return;
			}
		}
	}

	public void shutdown() {
		Iterator<TSInvokeID> ids = invokeIDTbl.values().iterator();
		while (ids.hasNext()) {
			TSInvokeID id = ids.next();
			id.setConf(null);
		}
	}
}

