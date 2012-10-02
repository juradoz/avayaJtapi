package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;

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

	void dump(String indent) {
		log.trace(indent + "***** AUDIT DUMP *****");
		log.trace(indent + "TSAuditThread: " + this);
		log.trace(indent + "TSAuditThread calls: ");
		Enumeration<SavedCall> callEnum = this.saveCallHash.elements();

		while (callEnum.hasMoreElements()) {
			SavedCall call;
			try {
				call = (SavedCall) callEnum.nextElement();
				call.call.dump(indent + " ");
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
			}
			continue;

		}
		log.trace(indent + "TSAuditThread conns: ");
		Enumeration<SavedConn> connEnum = this.saveConnHash.elements();

		while (connEnum.hasMoreElements()) {
			SavedConn conn;
			try {
				conn = (SavedConn) connEnum.nextElement();
				conn.conn.dump(indent + " ");
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
			}
			continue;

		}
		log.trace(indent + "TSAuditThread agents: ");
		Enumeration<SavedAgent> agentEnum = this.saveAgentHash.elements();

		while (agentEnum.hasMoreElements()) {
			SavedAgent agent;
			try {
				agent = (SavedAgent) agentEnum.nextElement();
				agent.agent.dump(indent + " ");
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
			}
			continue;

		}
		log.trace(indent
				+ "TSAuditThread Referenced/NotReferenced dump for TSDevice");
		if (TSDevice.g_RefCnt != TSDevice.g_CreationCnt) {
			log.trace(indent + "Reference dump: TSDevice Created Count "
					+ TSDevice.g_CreationCnt);
			log.trace(indent + "Reference dump: TSDevice Referenced Count "
					+ TSDevice.g_RefCnt);
			log.trace(indent + "Reference dump: TSDevice Registered Count "
					+ this.provider.xrefHash.size());
		}
		log.trace(indent + "***** AUDIT DUMP END *****");
	}

	TSAuditThread(TSProviderImpl _provider) {
		super("AuditThread");
		this.provider = _provider;
		this.keepRunning = true;
		this.saveConnHash = new Hashtable<CSTAConnectionID, SavedConn>(20);
		this.saveCallHash = new Hashtable<Integer, SavedCall>(10);
		this.saveAgentHash = new Hashtable<TSAgentKey, SavedAgent>(10);
		this.isSleeping = false;
	}

	void stopRunning() {
		this.keepRunning = false;
		if (this.isSleeping) {
			interrupt();
		}
	}

	public void run() {
		int secondsSinceLastCallCleanup = 0;
		int secondsSinceLastProviderDump = 0;
		try {
			while (this.keepRunning) {
				try {
					this.isSleeping = true;
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
				}
				this.isSleeping = false;

				if (!this.keepRunning) {
					break;
				}

				long curTime = System.currentTimeMillis();

				synchronized (this.saveCallHash) {
					Hashtable<Integer, SavedCall> keepHash = new Hashtable<Integer, SavedCall>(20);
					Enumeration<SavedCall> my_enum = this.saveCallHash.elements();
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
							if (curTime - sCall.saveTime < 5000L) {
								Object oldObj = keepHash.put(
										new Integer(sCall.call.getCallID()), sCall);
								if (oldObj != null)
									log.info("NOTICE: keepHash.put() replaced "
											+ oldObj + " for " + this.provider);
							} else {
								log.info("AUDIT: removing call " + sCall.call
										+ " for " + this.provider);
							}
						} catch (NoSuchElementException e) {
							log.error(e.getMessage(), e);
						}
						continue;

					}
					this.saveCallHash = keepHash;
				}

				synchronized (this.saveConnHash) {
					Hashtable<CSTAConnectionID, SavedConn> keepHash = new Hashtable<CSTAConnectionID, SavedConn>(20);
					Enumeration<SavedConn> my_enum = this.saveConnHash.elements();
					while (my_enum.hasMoreElements()) {
						SavedConn sConn;
						try {
							sConn = (SavedConn) my_enum.nextElement();
							if (curTime - sConn.saveTime < 5000L) {
								try {
									Object oldObj = keepHash.put(
											sConn.conn.getConnID(), sConn);
									if (oldObj != null)
										log.info("NOTICE: keepHash.put() replaced "
												+ oldObj + " for " + this.provider);
								} catch (TsapiPlatformException e) {
									log.error("Ignoring exception: " + e);
									log.info("AUDIT: removing conn " + sConn.conn
											+ " for " + this.provider);
								}
							} else
								log.info("AUDIT: removing conn " + sConn.conn
										+ " for " + this.provider);
						} catch (NoSuchElementException e) {
							log.error(e.getMessage(), e);
						}
						continue;

					}

					this.saveConnHash = keepHash;
				}

				synchronized (this.saveAgentHash) {
					Hashtable<TSAgentKey, SavedAgent> keepHash = new Hashtable<TSAgentKey, SavedAgent>(20);
					Enumeration<SavedAgent> my_enum = this.saveAgentHash.elements();
					while (my_enum.hasMoreElements()) {
						SavedAgent sAgent;
						try {
							sAgent = (SavedAgent) my_enum.nextElement();
							if (curTime - sAgent.saveTime < 5000L) {
								Object oldObj = keepHash.put(
										sAgent.agent.getAgentKey(), sAgent);
								if (oldObj != null)
									log.info("NOTICE: keepHash.put() replaced "
											+ oldObj + " for " + this.provider);
							} else {
								log.info("AUDIT: removing agent " + sAgent.agent
										+ " for " + this.provider);
							}
						} catch (NoSuchElementException e) {
							log.error(e.getMessage(), e);
						}
						continue;

					}
					this.saveAgentHash = keepHash;
				}

				secondsSinceLastCallCleanup += SLEEP_TIME / 1000;
				if (secondsSinceLastCallCleanup >= Tsapi.getCallCleanupRate()) {
					this.provider.callCleanup();
					secondsSinceLastCallCleanup = 0;
				}

				secondsSinceLastProviderDump += SLEEP_TIME / 1000;
				if (secondsSinceLastProviderDump / 60 >= Tsapi
						.getAuditDumpInterval()) {
					if ((log.isTraceEnabled()) && (Tsapi.isEnableAuditDump())) {
						log.trace("STARTING DUMPS, date = " + new Date()
								+ " for " + this.provider);
						this.provider.dump("");
					}
					secondsSinceLastProviderDump = 0;
				}
			}
		} catch (Exception e) {
			log.info("AUDIT Thread Exception - on provider " + this.provider);
			log.error(e.getMessage(), e);
		}
	}

	void dumpCall(int callID) {
		SavedCall sCall = (SavedCall) this.saveCallHash.remove(new Integer(
				callID));
		if (sCall != null) {
			log.info("AUDIT (dumpCall): removing call " + sCall.call + " for "
					+ this.provider);
		}
		Hashtable<CSTAConnectionID, SavedConn> keepHash = new Hashtable<CSTAConnectionID, SavedConn>(20);

		synchronized (this.saveConnHash) {
			Enumeration<SavedConn> my_enum = this.saveConnHash.elements();

			while (my_enum.hasMoreElements()) {
				SavedConn sConn;
				try {
					sConn = (SavedConn) my_enum.nextElement();
					try {
						if (sConn.conn.getConnID().getCallID() != callID) {
							Object oldObj = keepHash.put(sConn.conn.getConnID(),
									sConn);
							if (oldObj != null)
								log.info("NOTICE: keepHash.put() replaced "
										+ oldObj + " for " + this.provider);
						} else {
							log.info("AUDIT (dumpCall): removing conn "
									+ sConn.conn + " for " + this.provider);
						}
					} catch (TsapiPlatformException e) {
						log.error("Ignoring exception: " + e);
						log.info("AUDIT (dumpCall): removing conn " + sConn.conn
								+ " for " + this.provider);
					}
				} catch (NoSuchElementException e) {
					log.error(e.getMessage(), e);
				}
				continue;
			}
			this.saveConnHash = keepHash;
		}
	}

	void putCall(TSCall call) {
		Object oldObj = this.saveCallHash.put(new Integer(call.getCallID()),
				new SavedCall(call));
		if (oldObj != null)
			log.info("NOTICE: saveCallHash.put() replaced " + oldObj + " for "
					+ this.provider);
	}

	TSCall getCall(int callID) {
		SavedCall sCall = (SavedCall) this.saveCallHash
				.get(new Integer(callID));
		if (sCall != null) {
			log.info("Found call in audit hash: " + sCall.call + " for "
					+ this.provider);
			return sCall.call;
		}

		return null;
	}

	void dumpConn(CSTAConnectionID connID) {
		SavedConn sConn = (SavedConn) this.saveConnHash.remove(connID);
		if (sConn != null)
			log.info("AUDIT (dumpConn): removing conn " + sConn.conn + " for "
					+ this.provider);
	}

	void putConn(TSConnection conn) {
		try {
			CSTAConnectionID connID = conn.getConnID();
			if (connID != null) {
				Object oldObj = this.saveConnHash.put(connID, new SavedConn(
						conn));
				if (oldObj != null)
					log.info("NOTICE: saveConnHash.put() replaced " + oldObj
							+ " for " + this.provider);
			}
		} catch (TsapiPlatformException e) {
			log.error("Ignoring exception: " + e);
			log.info("AUDIT: removing conn " + conn + " for " + this.provider);
		}
	}

	TSConnection getConn(CSTAConnectionID connID) {
		SavedConn sConn = (SavedConn) this.saveConnHash.get(connID);
		if (sConn != null) {
			log.info("Found conn in audit hash: " + sConn.conn + " for "
					+ this.provider);
			return sConn.conn;
		}

		return null;
	}

	void dumpAgent(TSAgentKey agentKey) {
		SavedAgent sAgent = (SavedAgent) this.saveAgentHash.remove(agentKey);
		if (sAgent != null)
			log.info("AUDIT (dumpAgent): removing agent " + sAgent.agent
					+ " for " + this.provider);
	}

	void putAgent(TSAgent agent) {
		TSAgentKey agentKey = agent.getAgentKey();
		if (agentKey != null) {
			Object oldObj = this.saveAgentHash.put(agentKey, new SavedAgent(
					agent));
			if (oldObj != null)
				log.info("NOTICE: saveAgentHash.put() replaced " + oldObj
						+ " for " + this.provider);
		}
	}

	TSAgent getAgent(TSAgentKey agentKey) {
		SavedAgent sAgent = (SavedAgent) this.saveAgentHash.get(agentKey);
		if (sAgent != null) {
			log.info("Found agent in audit hash: " + sAgent.agent + " for "
					+ this.provider);
			return sAgent.agent;
		}

		return null;
	}
}