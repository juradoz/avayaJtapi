package com.avaya.jtapi.tsapi.impl.core;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;

final class TSAuditThread extends Thread {
	private static Logger log = Logger.getLogger(TSAuditThread.class);
	TSProviderImpl provider;
	boolean keepRunning;
	boolean isSleeping;
	Hashtable<Integer, SavedCall> saveCallHash;
	Hashtable<CSTAConnectionID, SavedConn> saveConnHash;
	Hashtable<TSAgentKey, SavedAgent> saveAgentHash;
	static int SLEEP_TIME = 10000;
	static final long DIFF_TIME = 5000L;

	TSAuditThread(final TSProviderImpl _provider) {
		super("AuditThread");
		provider = _provider;
		keepRunning = true;
		saveConnHash = new Hashtable<CSTAConnectionID, SavedConn>(20);
		saveCallHash = new Hashtable<Integer, SavedCall>(10);
		saveAgentHash = new Hashtable<TSAgentKey, SavedAgent>(10);
		isSleeping = false;
	}

	void dump(final String indent) {
		TSAuditThread.log.trace(indent + "***** AUDIT DUMP *****");
		TSAuditThread.log.trace(indent + "TSAuditThread: " + this);
		TSAuditThread.log.trace(indent + "TSAuditThread calls: ");
		final Enumeration<SavedCall> callEnum = saveCallHash.elements();

		while (callEnum.hasMoreElements()) {
			SavedCall call;
			try {
				call = callEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSAuditThread.log.error(e.getMessage(), e);
				continue;
			}

			call.call.dump(indent + " ");
		}
		TSAuditThread.log.trace(indent + "TSAuditThread conns: ");
		final Enumeration<SavedConn> connEnum = saveConnHash.elements();

		while (connEnum.hasMoreElements()) {
			SavedConn conn;
			try {
				conn = connEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSAuditThread.log.error(e.getMessage(), e);
				continue;
			}

			conn.conn.dump(indent + " ");
		}
		TSAuditThread.log.trace(indent + "TSAuditThread agents: ");
		final Enumeration<SavedAgent> agentEnum = saveAgentHash.elements();

		while (agentEnum.hasMoreElements()) {
			SavedAgent agent;
			try {
				agent = agentEnum.nextElement();
			} catch (final NoSuchElementException e) {
				TSAuditThread.log.error(e.getMessage(), e);
				continue;
			}

			agent.agent.dump(indent + " ");
		}
		TSAuditThread.log.trace(indent
				+ "TSAuditThread Referenced/NotReferenced dump for TSDevice");
		if (TSDevice.g_RefCnt != TSDevice.g_CreationCnt) {
			TSAuditThread.log.trace(indent
					+ "Reference dump: TSDevice Created Count "
					+ TSDevice.g_CreationCnt);
			TSAuditThread.log.trace(indent
					+ "Reference dump: TSDevice Referenced Count "
					+ TSDevice.g_RefCnt);
			TSAuditThread.log.trace(indent
					+ "Reference dump: TSDevice Registered Count "
					+ provider.xrefHash.size());
		}
		TSAuditThread.log.trace(indent + "***** AUDIT DUMP END *****");
	}

	void dumpAgent(final TSAgentKey agentKey) {
		final SavedAgent sAgent = saveAgentHash.remove(agentKey);
		if (sAgent != null)
			TSAuditThread.log.info("AUDIT (dumpAgent): removing agent "
					+ sAgent.agent + " for " + provider);
	}

	void dumpCall(final int callID) {
		final SavedCall sCall = saveCallHash.remove(new Integer(callID));
		if (sCall != null)
			TSAuditThread.log.info("AUDIT (dumpCall): removing call "
					+ sCall.call + " for " + provider);
		final Hashtable<CSTAConnectionID, SavedConn> keepHash = new Hashtable<CSTAConnectionID, SavedConn>(
				20);

		synchronized (saveConnHash) {
			final Enumeration<SavedConn> my_enum = saveConnHash.elements();

			while (my_enum.hasMoreElements()) {
				SavedConn sConn;
				try {
					sConn = my_enum.nextElement();
				} catch (final NoSuchElementException e) {
					TSAuditThread.log.error(e.getMessage(), e);
					continue;
				}
				try {
					if (sConn.conn.getConnID().getCallID() != callID) {
						final Object oldObj = keepHash.put(
								sConn.conn.getConnID(), sConn);
						if (oldObj != null)
							TSAuditThread.log
									.info("NOTICE: keepHash.put() replaced "
											+ oldObj + " for " + provider);
					} else
						TSAuditThread.log
								.info("AUDIT (dumpCall): removing conn "
										+ sConn.conn + " for " + provider);
				} catch (final TsapiPlatformException e) {
					TSAuditThread.log.error("Ignoring exception: " + e);
					TSAuditThread.log.info("AUDIT (dumpCall): removing conn "
							+ sConn.conn + " for " + provider);
				}
			}
			saveConnHash = keepHash;
		}
	}

	void dumpConn(final CSTAConnectionID connID) {
		final SavedConn sConn = saveConnHash.remove(connID);
		if (sConn != null)
			TSAuditThread.log.info("AUDIT (dumpConn): removing conn "
					+ sConn.conn + " for " + provider);
	}

	TSAgent getAgent(final TSAgentKey agentKey) {
		final SavedAgent sAgent = saveAgentHash.get(agentKey);
		if (sAgent != null) {
			TSAuditThread.log.info("Found agent in audit hash: " + sAgent.agent
					+ " for " + provider);
			return sAgent.agent;
		}

		return null;
	}

	TSCall getCall(final int callID) {
		final SavedCall sCall = saveCallHash.get(new Integer(callID));
		if (sCall != null) {
			TSAuditThread.log.info("Found call in audit hash: " + sCall.call
					+ " for " + provider);
			return sCall.call;
		}

		return null;
	}

	TSConnection getConn(final CSTAConnectionID connID) {
		final SavedConn sConn = saveConnHash.get(connID);
		if (sConn != null) {
			TSAuditThread.log.info("Found conn in audit hash: " + sConn.conn
					+ " for " + provider);
			return sConn.conn;
		}

		return null;
	}

	void putAgent(final TSAgent agent) {
		final TSAgentKey agentKey = agent.getAgentKey();
		if (agentKey == null)
			return;
		final Object oldObj = saveAgentHash
				.put(agentKey, new SavedAgent(agent));
		if (oldObj != null)
			TSAuditThread.log.info("NOTICE: saveAgentHash.put() replaced "
					+ oldObj + " for " + provider);
	}

	void putCall(final TSCall call) {
		final Object oldObj = saveCallHash.put(new Integer(call.getCallID()),
				new SavedCall(call));
		if (oldObj != null)
			TSAuditThread.log.info("NOTICE: saveCallHash.put() replaced "
					+ oldObj + " for " + provider);
	}

	void putConn(final TSConnection conn) {
		try {
			final CSTAConnectionID connID = conn.getConnID();
			if (connID != null) {
				final Object oldObj = saveConnHash.put(connID, new SavedConn(
						conn));
				if (oldObj != null)
					TSAuditThread.log
							.info("NOTICE: saveConnHash.put() replaced "
									+ oldObj + " for " + provider);
			}
		} catch (final TsapiPlatformException e) {
			TSAuditThread.log.error("Ignoring exception: " + e);
			TSAuditThread.log.info("AUDIT: removing conn " + conn + " for "
					+ provider);
		}
	}

	@Override
	public void run() {
		int secondsSinceLastCallCleanup = 0;
		int secondsSinceLastProviderDump = 0;
		try {
			while (keepRunning) {
				try {
					isSleeping = true;
					Thread.sleep(TSAuditThread.SLEEP_TIME);
				} catch (final InterruptedException e) {
				}
				isSleeping = false;

				if (!keepRunning)
					break;

				final long curTime = System.currentTimeMillis();

				synchronized (saveCallHash) {
					final Hashtable<Integer, SavedCall> keepHash = new Hashtable<Integer, SavedCall>(
							20);
					final Enumeration<SavedCall> my_enum = saveCallHash
							.elements();
					while (my_enum.hasMoreElements()) {
						SavedCall sCall;
						try {
							sCall = my_enum.nextElement();

							if (!sCall.call.needsSnapshot()
									&& curTime - sCall.saveTime > 3600000L) {
								TSAuditThread.log
										.info("Setting NeedSnapshot for LONG call: "
												+ sCall.call);
								sCall.call.setNeedSnapshot(true);
							}
						} catch (final NoSuchElementException e) {
							TSAuditThread.log.error(e.getMessage(), e);
							continue;
						}

						if (curTime - sCall.saveTime < 5000L) {
							final Object oldObj = keepHash.put(new Integer(
									sCall.call.getCallID()), sCall);
							if (oldObj != null)
								TSAuditThread.log
										.info("NOTICE: keepHash.put() replaced "
												+ oldObj + " for " + provider);
						}
						TSAuditThread.log.info("AUDIT: removing call "
								+ sCall.call + " for " + provider);
					}

					saveCallHash = keepHash;
				}

				synchronized (saveConnHash) {
					final Hashtable<CSTAConnectionID, SavedConn> keepHash = new Hashtable<CSTAConnectionID, SavedConn>(
							20);
					final Enumeration<SavedConn> my_enum = saveConnHash
							.elements();
					while (my_enum.hasMoreElements()) {
						SavedConn sConn;
						try {
							sConn = my_enum.nextElement();
						} catch (final NoSuchElementException e) {
							TSAuditThread.log.error(e.getMessage(), e);
							continue;
						}

						if (curTime - sConn.saveTime < 5000L)
							try {
								final Object oldObj = keepHash.put(
										sConn.conn.getConnID(), sConn);
								if (oldObj != null)
									TSAuditThread.log
											.info("NOTICE: keepHash.put() replaced "
													+ oldObj
													+ " for "
													+ provider);
							} catch (final TsapiPlatformException e) {
								TSAuditThread.log.error("Ignoring exception: "
										+ e);
								TSAuditThread.log.info("AUDIT: removing conn "
										+ sConn.conn + " for " + provider);
							}
						TSAuditThread.log.info("AUDIT: removing conn "
								+ sConn.conn + " for " + provider);
					}

					saveConnHash = keepHash;
				}

				synchronized (saveAgentHash) {
					final Hashtable<TSAgentKey, SavedAgent> keepHash = new Hashtable<TSAgentKey, SavedAgent>(
							20);
					final Enumeration<SavedAgent> my_enum = saveAgentHash
							.elements();
					while (my_enum.hasMoreElements()) {
						SavedAgent sAgent;
						try {
							sAgent = my_enum.nextElement();
						} catch (final NoSuchElementException e) {
							TSAuditThread.log.error(e.getMessage(), e);
							continue;
						}

						if (curTime - sAgent.saveTime < 5000L) {
							final Object oldObj = keepHash.put(
									sAgent.agent.getAgentKey(), sAgent);
							if (oldObj != null)
								TSAuditThread.log
										.info("NOTICE: keepHash.put() replaced "
												+ oldObj + " for " + provider);
						}
						TSAuditThread.log.info("AUDIT: removing agent "
								+ sAgent.agent + " for " + provider);
					}

					saveAgentHash = keepHash;
				}

				secondsSinceLastCallCleanup += TSAuditThread.SLEEP_TIME / 1000;
				if (secondsSinceLastCallCleanup >= Tsapi.getCallCleanupRate()) {
					provider.callCleanup();
					secondsSinceLastCallCleanup = 0;
				}

				secondsSinceLastProviderDump += TSAuditThread.SLEEP_TIME / 1000;
				if (secondsSinceLastProviderDump / 60 < Tsapi
						.getAuditDumpInterval())
					continue;

				if (TSAuditThread.log.isTraceEnabled()
						&& Tsapi.isEnableAuditDump()) {
					TSAuditThread.log.trace("STARTING DUMPS, date = "
							+ new Date() + " for " + provider);
					provider.dump("");
				}
				secondsSinceLastProviderDump = 0;
			}

		} catch (final Exception e) {
			TSAuditThread.log.info("AUDIT Thread Exception - on provider "
					+ provider);
			TSAuditThread.log.error(e.getMessage(), e);
		}
	}

	void stopRunning() {
		keepRunning = false;
		if (!isSleeping)
			return;
		interrupt();
	}
}
