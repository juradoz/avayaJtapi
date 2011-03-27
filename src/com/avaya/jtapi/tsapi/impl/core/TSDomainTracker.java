package com.avaya.jtapi.tsapi.impl.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

class TSDomainTracker implements IDomainTracker {
	private static Logger log = Logger.getLogger(TSDomainTracker.class);
	private final IDomainContainer m_container;
	private final Map<Integer, IDomainDevice> m_callsToDomainDevices = new HashMap<Integer, IDomainDevice>();

	TSDomainTracker(final IDomainContainer container) {
		m_container = container;
	}

	@Override
	public synchronized IDomainDevice addCallToDomain(final IDomainDevice d,
			final IDomainCall c) {
		final Integer cid = new Integer(c.getDomainCallID());

		IDomainDevice prior_domain = null;

		if (m_callsToDomainDevices.containsKey(cid)) {
			final IDomainDevice oldd = m_callsToDomainDevices.get(cid);
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

	@Override
	public synchronized void changeCallIDInDomain(final int old_callid,
			final int new_callid) {
		final Integer oldcid = new Integer(old_callid);
		final Integer newcid = new Integer(new_callid);

		if (m_callsToDomainDevices.containsKey(oldcid)) {
			final IDomainDevice domain = m_callsToDomainDevices.remove(oldcid);
			m_callsToDomainDevices.put(newcid, domain);
			internallyLog("changeCallIDInDomain: old cid " + old_callid
					+ " was under domain " + domain
					+ " - now recorded under new cid " + new_callid);
		} else
			internallyLog("changeCallIDInDomain: old call was not in any domain, oldcid "
					+ old_callid
					+ " newcid "
					+ new_callid
					+ " - no action taken");
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public void dumpDomainData(final String indent) {
		HashMap<Integer, IDomainDevice> dup = null;

		int found_count = 0;

		synchronized (this) {
			dup = new HashMap<Integer, IDomainDevice>(m_callsToDomainDevices);
		}

		final Set<?> entries = dup.entrySet();
		final Iterator<?> current = entries.iterator();
		TSDomainTracker.log.trace("DomainTracker begins:");
		while (current.hasNext()) {
			final Map.Entry an_entry = (Map.Entry) current.next();

			final IDomainDevice d = (IDomainDevice) an_entry.getValue();

			final int cid = ((Integer) an_entry.getKey()).intValue();
			final IDomainCall call = m_container.getDomainCall(cid);

			++found_count;

			TSDomainTracker.log.trace(indent + found_count + ": Call(cid) "
					+ call + "(" + cid + ") --> VDNDevice(" + d + ")");
		}

		TSDomainTracker.log.trace("DomainTracker ends(" + found_count
				+ " entries)");
	}

	@Override
	public synchronized IDomainDevice getDomainCallIsIn(final IDomainCall c) {
		final Integer cid = new Integer(c.getDomainCallID());

		if (m_callsToDomainDevices.containsKey(cid))
			return m_callsToDomainDevices.get(cid);

		return null;
	}

	private void internallyLog(final String s) {
		m_container.logln("TSDomainTracker/" + m_container + ": " + s);
	}

	@Override
	public boolean isCallInAnyDomain(final IDomainCall c) {
		return getDomainCallIsIn(c) != null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public synchronized void removeAllCallsForDomain(final IDomainDevice d) {
		int found_count = 0;

		internallyLog("removeAllCallsForDomain: to clean calls for domain " + d);

		if (m_callsToDomainDevices.containsValue(d)) {
			final Set<?> entries = m_callsToDomainDevices.entrySet();
			final Iterator<?> current = entries.iterator();
			while (current.hasNext()) {
				final Map.Entry an_entry = (Map.Entry) current.next();

				if (an_entry.getValue() == d) {
					current.remove();

					final int cid = ((Integer) an_entry.getKey()).intValue();
					final IDomainCall call = m_container.getDomainCall(cid);
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

	@Override
	public synchronized void removeCallFromDomain(final IDomainCall c) {
		final Integer cid = new Integer(c.getDomainCallID());

		if (m_callsToDomainDevices.containsKey(cid)) {
			final IDomainDevice domain = m_callsToDomainDevices.remove(cid);
			c.notifyCallRemoved(domain);
			internallyLog("removeCallFromDomain: cid " + cid
					+ " was under domain " + domain);
		} else
			internallyLog("removeCallFromDomain: cid " + cid
					+ " was not in any domain - no action taken");
	}
}
