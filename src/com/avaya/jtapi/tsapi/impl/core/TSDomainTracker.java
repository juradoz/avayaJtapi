package com.avaya.jtapi.tsapi.impl.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

class TSDomainTracker implements IDomainTracker {
	private static Logger log = Logger.getLogger(TSDomainTracker.class);
	private IDomainContainer m_container;
	private final Map<Integer, IDomainDevice> m_callsToDomainDevices = new HashMap<Integer, IDomainDevice>();

	TSDomainTracker(IDomainContainer container) {
		m_container = container;
	}

	public synchronized IDomainDevice addCallToDomain(IDomainDevice d,
			IDomainCall c) {
		Integer cid = new Integer(c.getDomainCallID());

		IDomainDevice prior_domain = null;

		if (m_callsToDomainDevices.containsKey(cid)) {
			IDomainDevice oldd = m_callsToDomainDevices.get(cid);
			if (oldd == d) {
				internallyLog("addCallToDomain: " + c.getDomainCallID()
						+ " to domain " + d.getDomainName()
						+ " - was already in that domain - done");
				prior_domain = d;
			} else {
				m_callsToDomainDevices.remove(cid);
				c.notifyCallRemoved(oldd);
				m_callsToDomainDevices.put(cid, d);
				c.notifyCallAdded(d);
				internallyLog("addCallToDomain: " + c.getDomainCallID()
						+ " to domain " + d.getDomainName()
						+ " - overrode prior other domain "
						+ oldd.getDomainName());
				prior_domain = oldd;
			}

		} else {
			m_callsToDomainDevices.put(cid, d);
			c.notifyCallAdded(d);
			internallyLog("addCallToDomain: " + c.getDomainCallID()
					+ " to domain " + d.getDomainName()
					+ " and was under none before");
		}

		return prior_domain;
	}

	public synchronized void changeCallIDInDomain(int old_callid, int new_callid) {
		Integer oldcid = new Integer(old_callid);
		Integer newcid = new Integer(new_callid);

		if (m_callsToDomainDevices.containsKey(oldcid)) {
			IDomainDevice domain = m_callsToDomainDevices.remove(oldcid);
			m_callsToDomainDevices.put(newcid, domain);
			internallyLog("changeCallIDInDomain: old cid " + old_callid
					+ " was under domain " + domain
					+ " - now recorded under new cid " + new_callid);
		} else {
			internallyLog("changeCallIDInDomain: old call was not in any domain, oldcid "
					+ old_callid
					+ " newcid "
					+ new_callid
					+ " - no action taken");
		}
	}

	@SuppressWarnings("unchecked")
	public void dumpDomainData(String indent) {
		HashMap<Integer, IDomainDevice> dup = null;

		int found_count = 0;

		synchronized (this) {
			dup = new HashMap<Integer, IDomainDevice>(m_callsToDomainDevices);
		}

		Set<?> entries = dup.entrySet();
		Iterator<?> current = entries.iterator();
		log.trace("DomainTracker begins:");
		while (current.hasNext()) {
			Map.Entry an_entry = (Map.Entry) current.next();

			IDomainDevice d = (IDomainDevice) an_entry.getValue();

			int cid = ((Integer) an_entry.getKey()).intValue();
			IDomainCall call = m_container.getDomainCall(cid);

			++found_count;

			log.trace(indent + found_count + ": Call(cid) " + call + "(" + cid
					+ ") --> VDNDevice(" + d + ")");
		}

		log.trace("DomainTracker ends(" + found_count + " entries)");
	}

	public synchronized IDomainDevice getDomainCallIsIn(IDomainCall c) {
		Integer cid = new Integer(c.getDomainCallID());

		if (m_callsToDomainDevices.containsKey(cid)) {
			return m_callsToDomainDevices.get(cid);
		}

		return null;
	}

	private void internallyLog(String s) {
		m_container.logln("TSDomainTracker/" + m_container + ": " + s);
	}

	public boolean isCallInAnyDomain(IDomainCall c) {
		return getDomainCallIsIn(c) != null;
	}

	@SuppressWarnings("unchecked")
	public synchronized void removeAllCallsForDomain(IDomainDevice d) {
		int found_count = 0;

		internallyLog("removeAllCallsForDomain: to clean calls for domain " + d);

		if (m_callsToDomainDevices.containsValue(d)) {
			Set<?> entries = m_callsToDomainDevices.entrySet();
			Iterator<?> current = entries.iterator();
			while (current.hasNext()) {
				Map.Entry an_entry = (Map.Entry) current.next();

				if (an_entry.getValue() == d) {
					current.remove();

					int cid = ((Integer) an_entry.getKey()).intValue();
					IDomainCall call = m_container.getDomainCall(cid);
					call.notifyCallRemoved(d);

					++found_count;
					internallyLog("removeAllCallsForDomain: cid " + cid
							+ " was under domain " + d);
				}
			}

		}

		internallyLog("removeAllCallsForDomain: done, cleaned " + found_count
				+ " call(s) for domain " + d);
	}

	public synchronized void removeCallFromDomain(IDomainCall c) {
		Integer cid = new Integer(c.getDomainCallID());

		if (m_callsToDomainDevices.containsKey(cid)) {
			IDomainDevice domain = m_callsToDomainDevices.remove(cid);
			c.notifyCallRemoved(domain);
			internallyLog("removeCallFromDomain: cid " + cid
					+ " was under domain " + domain);
		} else {
			internallyLog("removeCallFromDomain: cid " + cid
					+ " was not in any domain - no action taken");
		}
	}
}

