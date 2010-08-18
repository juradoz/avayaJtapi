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

	TSAuditThread(TSProviderImpl _provider) {
		super("AuditThread");
		provider = _provider;
		keepRunning = true;
		saveConnHash = new Hashtable<CSTAConnectionID, SavedConn>(20);
		saveCallHash = new Hashtable<Integer, SavedCall>(10);
		saveAgentHash = new Hashtable<TSAgentKey, SavedAgent>(10);
		isSleeping = false;
	}

	void dump(String indent) {
		log.trace(indent + "***** AUDIT DUMP *****");
		log.trace(indent + "TSAuditThread: " + this);
		log.trace(indent + "TSAuditThread calls: ");
		Enumeration<SavedCall> callEnum = saveCallHash.elements();

		while (callEnum.hasMoreElements()) {
			SavedCall call;
			try {
				call = (SavedCall) callEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			call.call.dump(indent + " ");
		}
		log.trace(indent + "TSAuditThread conns: ");
		Enumeration<SavedConn> connEnum = saveConnHash.elements();

		while (connEnum.hasMoreElements()) {
			SavedConn conn;
			try {
				conn = (SavedConn) connEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			conn.conn.dump(indent + " ");
		}
		log.trace(indent + "TSAuditThread agents: ");
		Enumeration<SavedAgent> agentEnum = saveAgentHash.elements();

		while (agentEnum.hasMoreElements()) {
			SavedAgent agent;
			try {
				agent = (SavedAgent) agentEnum.nextElement();
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
				continue;
			}

			agent.agent.dump(indent + " ");
		}
		log.trace(indent
				+ "TSAuditThread Referenced/NotReferenced dump for TSDevice");
		if (TSDevice.g_RefCnt != TSDevice.g_CreationCnt) {
			log.trace(indent + "Reference dump: TSDevice Created Count "
					+ TSDevice.g_CreationCnt);
			log.trace(indent + "Reference dump: TSDevice Referenced Count "
					+ TSDevice.g_RefCnt);
			log.trace(indent + "Reference dump: TSDevice Registered Count "
					+ provider.xrefHash.size());
		}
		log.trace(indent + "***** AUDIT DUMP END *****");
	}

	void dumpAgent(TSAgentKey agentKey) {
		SavedAgent sAgent = saveAgentHash.remove(agentKey);
		if (sAgent != null) {
			log.info("AUDIT (dumpAgent): removing agent " + sAgent.agent
					+ " for " + provider);
		}
	}

	void dumpCall(int callID) {
		SavedCall sCall = saveCallHash.remove(new Integer(callID));
		if (sCall != null) {
			log.info("AUDIT (dumpCall): removing call " + sCall.call + " for "
					+ provider);
		}
		Hashtable<CSTAConnectionID, SavedConn> keepHash = new Hashtable<CSTAConnectionID, SavedConn>(20);

		synchronized (saveConnHash) {
			Enumeration<SavedConn> my_enum = saveConnHash.elements();

			while (my_enum.hasMoreElements()) {
				SavedConn sConn;
				try {
					sConn = (SavedConn) my_enum.nextElement();
				} catch (NoSuchElementException e) {
					log.error(e.getMessage(), e);
					continue;
				}
				try {
					if (sConn.conn.getConnID().getCallID() != callID) {
						Object oldObj = keepHash.put(sConn.conn.getConnID(),
								sConn);
						if (oldObj != null) {
							log.info("NOTICE: keepHash.put() replaced "
									+ oldObj + " for " + provider);
						}
					} else {
						log.info("AUDIT (dumpCall): removing conn "
								+ sConn.conn + " for " + provider);
					}
				} catch (TsapiPlatformException e) {
					log.error("Ignoring exception: " + e);
					log.info("AUDIT (dumpCall): removing conn " + sConn.conn
							+ " for " + provider);
				}
			}
			saveConnHash = keepHash;
		}
	}

	void dumpConn(CSTAConnectionID connID) {
		SavedConn sConn = saveConnHash.remove(connID);
		if (sConn != null) {
			log.info("AUDIT (dumpConn): removing conn " + sConn.conn + " for "
					+ provider);
		}
	}TSAgent getAgent(TSAgentKey agentKey) {
		SavedAgent sAgent = saveAgentHash.get(agentKey);
		if (sAgent != null) {
			log.info("Found agent in audit hash: " + sAgent.agent + " for "
					+ provider);
			return sAgent.agent;
		}

		return null;
	}

	TSCall getCall(int callID) {
		SavedCall sCall = saveCallHash.get(new Integer(callID));
		if (sCall != null) {
			log.info("Found call in audit hash: " + sCall.call + " for "
					+ provider);
			return sCall.call;
		}

		return null;
	}

	TSConnection getConn(CSTAConnectionID connID) {
		SavedConn sConn = saveConnHash.get(connID);
		if (sConn != null) {
			log.info("Found conn in audit hash: " + sConn.conn + " for "
					+ provider);
			return sConn.conn;
		}

		return null;
	}

	void putAgent(TSAgent agent) {
		TSAgentKey agentKey = agent.getAgentKey();
		if (agentKey == null) {
			return;
		}
		Object oldObj = saveAgentHash.put(agentKey, new SavedAgent(agent));
		if (oldObj != null) {
			log.info("NOTICE: saveAgentHash.put() replaced " + oldObj + " for "
					+ provider);
		}
	}

	void putCall(TSCall call) {
		Object oldObj = saveCallHash.put(new Integer(call.getCallID()),
				new SavedCall(call));
		if (oldObj != null) {
			log.info("NOTICE: saveCallHash.put() replaced " + oldObj + " for "
					+ provider);
		}
	}

	void putConn(TSConnection conn) {
		try {
			CSTAConnectionID connID = conn.getConnID();
			if (connID != null) {
				Object oldObj = saveConnHash.put(connID, new SavedConn(conn));
				if (oldObj != null) {
					log.info("NOTICE: saveConnHash.put() replaced " + oldObj
							+ " for " + provider);
				}
			}
		} catch (TsapiPlatformException e) {
			log.error("Ignoring exception: " + e);
			log.info("AUDIT: removing conn " + conn + " for " + provider);
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
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
				}
				isSleeping = false;

				if (!keepRunning) {
					break;
				}

				long curTime = System.currentTimeMillis();

				synchronized (saveCallHash) {
					Hashtable<Integer, SavedCall> keepHash = new Hashtable<Integer, SavedCall>(20);
					Enumeration<SavedCall> my_enum = saveCallHash.elements();
					while (my_enum.hasMoreElements()) {
						SavedCall sCall;
						try {
							sCall = (SavedCall) my_enum.nextElement();

							if ((!sCall.call.needsSnapshot())
									&& (curTime - sCall.saveTime > 3600000L)) {
								log.info("Setting NeedSnapshot for LONG call: "
										+ sCall.call);
								sCall.call.setNeedSnapshot(true);
							}
						} catch (NoSuchElementException e) {
							log.error(e.getMessage(), e);
							continue;
						}

						if (curTime - sCall.saveTime < 5000L) {
							Object oldObj = keepHash.put(new Integer(sCall.call
									.getCallID()), sCall);
							if (oldObj != null) {
								log.info("NOTICE: keepHash.put() replaced "
										+ oldObj + " for " + provider);
							}
						}
						log.info("AUDIT: removing call " + sCall.call + " for "
								+ provider);
					}

					saveCallHash = keepHash;
				}

				synchronized (saveConnHash) {
					Hashtable<CSTAConnectionID, SavedConn> keepHash = new Hashtable<CSTAConnectionID, SavedConn>(20);
					Enumeration<SavedConn> my_enum = saveConnHash.elements();
					while (my_enum.hasMoreElements()) {
						SavedConn sConn;
						try {
							sConn = (SavedConn) my_enum.nextElement();
						} catch (NoSuchElementException e) {
							log.error(e.getMessage(), e);
							continue;
						}

						if (curTime - sConn.saveTime < 5000L) {
							try {
								Object oldObj = keepHash.put(sConn.conn
										.getConnID(), sConn);
								if (oldObj != null) {
									log.info("NOTICE: keepHash.put() replaced "
											+ oldObj + " for " + provider);
								}
							} catch (TsapiPlatformException e) {
								log.error("Ignoring exception: " + e);
								log.info("AUDIT: removing conn " + sConn.conn
										+ " for " + provider);
							}
						}
						log.info("AUDIT: removing conn " + sConn.conn + " for "
								+ provider);
					}

					saveConnHash = keepHash;
				}

				synchronized (saveAgentHash) {
					Hashtable<TSAgentKey, SavedAgent> keepHash = new Hashtable<TSAgentKey, SavedAgent>(20);
					Enumeration<SavedAgent> my_enum = saveAgentHash.elements();
					while (my_enum.hasMoreElements()) {
						SavedAgent sAgent;
						try {
							sAgent = (SavedAgent) my_enum.nextElement();
						} catch (NoSuchElementException e) {
							log.error(e.getMessage(), e);
							continue;
						}

						if (curTime - sAgent.saveTime < 5000L) {
							Object oldObj = keepHash.put(sAgent.agent
									.getAgentKey(), sAgent);
							if (oldObj != null) {
								log.info("NOTICE: keepHash.put() replaced "
										+ oldObj + " for " + provider);
							}
						}
						log.info("AUDIT: removing agent " + sAgent.agent
								+ " for " + provider);
					}

					saveAgentHash = keepHash;
				}

				secondsSinceLastCallCleanup += SLEEP_TIME / 1000;
				if (secondsSinceLastCallCleanup >= Tsapi.getCallCleanupRate()) {
					provider.callCleanup();
					secondsSinceLastCallCleanup = 0;
				}

				secondsSinceLastProviderDump += SLEEP_TIME / 1000;
				if (secondsSinceLastProviderDump / 60 < Tsapi
						.getAuditDumpInterval()) {
					continue;
				}

				if ((log.isTraceEnabled()) && (Tsapi.isEnableAuditDump())) {
					log.trace("STARTING DUMPS, date = " + new Date() + " for "
							+ provider);
					provider.dump("");
				}
				secondsSinceLastProviderDump = 0;
			}

		} catch (Exception e) {
			log.info("AUDIT Thread Exception - on provider " + provider);
			log.error(e.getMessage(), e);
		}
	}

	void stopRunning() {
		keepRunning = false;
		if (!isSleeping) {
			return;
		}
		interrupt();
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSAuditThread JD-Core Version: 0.5.4
 */