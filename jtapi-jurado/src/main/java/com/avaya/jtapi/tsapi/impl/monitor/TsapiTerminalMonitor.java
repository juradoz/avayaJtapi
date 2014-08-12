package com.avaya.jtapi.tsapi.impl.monitor;

import com.avaya.jtapi.tsapi.ITsapiEvent;
import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
import com.avaya.jtapi.tsapi.impl.core.JtapiEventThreadManager;
import com.avaya.jtapi.tsapi.impl.core.TSAgent;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSEvent;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.events.PrivateDataEventImpl;
import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
import com.avaya.jtapi.tsapi.impl.events.terminal.AgentTerminalEventImpl;
import com.avaya.jtapi.tsapi.impl.events.terminal.CallControlTerminalEventImpl;
import com.avaya.jtapi.tsapi.impl.events.terminal.TerminalEventImpl;
import com.avaya.jtapi.tsapi.impl.events.terminal.TerminalEventParams;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiPrivateTerminalEvent;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermBusyEv;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermLogOffEv;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermLogOnEv;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermNotReadyEv;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermObservationEndedEvent;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermReadyEv;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermWorkNotReadyEv;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermWorkReadyEv;
import com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTerminalDNDEvent;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import java.util.BitSet;
import java.util.Vector;
import javax.telephony.Event;
import javax.telephony.Terminal;
import javax.telephony.TerminalEvent;
import javax.telephony.TerminalListener;
import javax.telephony.TerminalObserver;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.AgentTerminalEvent;
import javax.telephony.callcenter.AgentTerminalListener;
import javax.telephony.callcenter.AgentTerminalObserver;
import javax.telephony.callcontrol.CallControlTerminalEvent;
import javax.telephony.callcontrol.CallControlTerminalListener;
import javax.telephony.callcontrol.CallControlTerminalObserver;
import javax.telephony.events.TermEv;
import javax.telephony.phone.PhoneTerminalObserver;
import javax.telephony.privatedata.PrivateDataEvent;
import javax.telephony.privatedata.PrivateDataTerminalListener;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
public final class TsapiTerminalMonitor implements TsapiMonitor {
	private static Logger log = Logger.getLogger(TsapiTerminalMonitor.class);
	TSProviderImpl provider;
	TerminalObserver observer = null;
	TerminalListener terminalListener = null;
	private final Vector<TermEv> eventList = new Vector<TermEv>();
	private final Vector<Event> listenerEventList = new Vector<Event>();

	long reference = 0L;
	BitSet observerType = new BitSet(8);

	Object syncObject = new Object();

	public void dump(String indent) {
		log.trace(indent + "***** TsapiTerminalMonitor DUMP *****");
		log.trace(indent + "TsapiTerminalMonitor: " + this);
		if (this.observer != null)
			log.trace(indent + "observer: " + this.observer);
		else
			log.trace(indent + "listener: " + this.terminalListener);
		log.trace(indent + "***** TsapiTerminalMonitor DUMP END *****");
	}

	public TsapiTerminalMonitor(TSProviderImpl _provider,
			TerminalObserver _observer) {
		this.provider = _provider;
		this.observer = _observer;

		this.provider.addTerminalMonitorThread(this);

		this.observerType.set(0);
		if ((this.observer instanceof CallControlTerminalObserver)) {
			this.observerType.set(1);
		}
		if ((this.observer instanceof AgentTerminalObserver)) {
			this.observerType.set(2);
		}
		if ((this.observer instanceof PhoneTerminalObserver)) {
			this.observerType.set(4);
		}

		this.observerType.set(5);

		deliverEvents(null, false);
	}

	public TsapiTerminalMonitor(TSProviderImpl _provider,
			TerminalListener _listener) {
		this.provider = _provider;
		this.terminalListener = _listener;

		this.provider.addTerminalMonitorThread(this);

		deliverEvents(null, false);
	}

	public TerminalObserver getObserver() {
		return this.observer;
	}

	public TerminalListener getListener() {
		return this.terminalListener;
	}

	public synchronized void addReference() {
		this.reference += 1L;
	}

	public void deleteReference(TSDevice device, int cause, Object privateData) {
		if (this.observer != null)
			log.debug("Getting TsapiTerminalMonitor lock to deliver deleteReference events for observer "
					+ this.observer);
		else
			log.debug("Getting TsapiTerminalMonitor lock to deliver deleteReference events for listener "
					+ this.terminalListener);
		deleteReferenceInternal(device, cause, privateData);
	}

	private synchronized void deleteReferenceInternal(TSDevice device,
			int cause, Object privateData) {
		String tsEventLog = null;
		this.reference -= 1L;

		if (this.observer != null) {
			log.debug("meta event BEGIN: cause (" + cause + ") metaCode ("
					+ 136 + ")" + " for " + this.observer);
			tsEventLog = "OBSERVATIONENDEDEVENT for " + device;
			synchronized (this.eventList) {
				int nextMetaEventIndex = this.eventList.size();

				addObserverEvent(new TsapiTermObservationEndedEvent(
						createTerminal(device), cause, privateData), tsEventLog);

				((TsapiObserverEvent) this.eventList
						.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
			}
			if (privateData != null) {
				tsEventLog = "PRIVATEEVENT for " + device;
				addObserverEvent(new TsapiPrivateTerminalEvent(
						createTerminal(device), cause, 136, privateData),
						tsEventLog);
			}

			log.debug("meta event END for " + this.observer
					+ " eventList size=" + this.eventList.size());
		} else {
			log.debug("meta event BEGIN: cause (" + cause + ") for "
					+ this.terminalListener);
			tsEventLog = "OBSERVATIONENDEDEVENT for " + device;

			this.listenerEventList.addElement(new TerminalEventImpl(121, cause,
					createTerminal(device), privateData));

			log.debug(tsEventLog + " for listener " + this.terminalListener);
			if (privateData != null) {
				tsEventLog = "PRIVATEEVENT for " + device;
				addPrivateDataEvent(603, cause, createTerminal(device),
						privateData, tsEventLog);
			}
		}

		if (this.reference <= 0L) {
			this.provider.removeTerminalMonitorThread(this);
		}

		JtapiEventThreadManager.execute(this);
	}

	void addObserverEvent(TermEv event, String tsEventLog) {
		if ((this.observerType.get(0))
				&& (((ITsapiEvent) event).getEventPackage() == 0)) {
			log.debug(tsEventLog + " for observer " + this.observer);

			this.eventList.addElement(event);
		} else if ((this.observerType.get(1))
				&& (((ITsapiEvent) event).getEventPackage() == 1)) {
			log.debug(tsEventLog + " for observer " + this.observer);

			this.eventList.addElement(event);
		} else if ((this.observerType.get(2))
				&& (((ITsapiEvent) event).getEventPackage() == 2)) {
			log.debug(tsEventLog + " for observer " + this.observer);

			this.eventList.addElement(event);
		} else if ((this.observerType.get(4))
				&& (((ITsapiEvent) event).getEventPackage() == 4)) {
			log.debug(tsEventLog + " for observer " + this.observer);

			this.eventList.addElement(event);
		} else if ((this.observerType.get(5))
				&& (((ITsapiEvent) event).getEventPackage() == 5)) {
			log.debug(tsEventLog + " for observer " + this.observer);

			this.eventList.addElement(event);
		} else {
			log.debug(tsEventLog + " ignored");
		}
	}

	private void addAgentTerminalEvent(TerminalEventParams terminalEventParams,
			Agent agent, String tsEventLog) {
		if ((this.terminalListener instanceof AgentTerminalListener)) {
			this.listenerEventList.addElement(new AgentTerminalEventImpl(
					terminalEventParams, agent));
			log.debug(tsEventLog + " for listener " + this.terminalListener);
		} else {
			log.debug(tsEventLog + " ignored since listener "
					+ this.terminalListener
					+ " is not of type AgentTerminalListener");
		}
	}

	private void addCallControlTerminalEvent(
			TerminalEventParams terminalEventParams, boolean state,
			String tsEventLog) {
		if ((this.terminalListener instanceof CallControlTerminalListener)) {
			this.listenerEventList.addElement(new CallControlTerminalEventImpl(
					terminalEventParams, state));
			log.debug(tsEventLog + " for listener " + this.terminalListener);
		} else {
			log.debug(tsEventLog + " ignored since listener "
					+ this.terminalListener
					+ " is not of type CallControlTerminalListener");
		}
	}

	private void addPrivateDataEvent(int eventId, int cause, Object source,
			Object privateData, String tsEventLog) {
		if ((this.terminalListener instanceof PrivateDataTerminalListener)) {
			this.listenerEventList.addElement(new PrivateDataEventImpl(eventId,
					cause, null, source, privateData));
			log.debug(tsEventLog + " for listener " + this.terminalListener);
		} else {
			log.debug(tsEventLog + " ignored since listener "
					+ this.terminalListener
					+ " is not of type PrivateDataTerminalListener");
		}
	}

	public void deliverEvents(Vector<TSEvent> _eventList, boolean snapshot) {
		if (this.observer != null)
			log.debug("Getting TsapiTerminalMonitor lock to deliver events for observer "
					+ this.observer);
		else
			log.debug("Getting TsapiTerminalMonitor lock to deliver events for listener "
					+ this.terminalListener);
		if ((_eventList == null) || (_eventList.size() == 0)) {
			return;
		}
		synchronized (_eventList) {
			deliverEventsInternal(_eventList, snapshot);
		}
	}

	private synchronized void deliverEventsInternal(Vector<TSEvent> _eventList,
			boolean snapshot) {
		if (this.observer != null) {
			createObserverEvents(_eventList, snapshot);
			if (this.eventList.size() != 0)
				;
		} else {
			createListenerEvents(_eventList, snapshot);
			if (this.listenerEventList.size() == 0)
				return;
		}
		JtapiEventThreadManager.execute(this);
	}

	private void createObserverEvents(Vector<TSEvent> _eventList,
			boolean snapshot) {
		String tsEventLog = null;
		if (_eventList == null)
			return;
		int cause;
		int metaCode;
		if (snapshot) {
			metaCode = 135;
			cause = 110;
		} else {
			metaCode = 136;
			cause = 100;
		}

		int nextMetaEventIndex = this.eventList.size();

		TSEvent ev = null;
		Object tsTarget = null;
		TSDevice target = null;
		TSAgent agent = null;
		Object privateData = null;
		log.debug("meta event BEGIN: cause (" + cause + ") metaCode ("
				+ metaCode + ")" + " for " + this.observer);
		for (int i = 0; i < _eventList.size(); i++) {
			ev = (TSEvent) _eventList.elementAt(i);
			tsTarget = ev.getEventTarget();
			if ((tsTarget instanceof TSDevice)) {
				target = (TSDevice) tsTarget;
			} else if ((tsTarget instanceof TSAgent)) {
				agent = (TSAgent) tsTarget;
				target = agent.getTSAgentDevice();
			}

			privateData = ev.getPrivateData();

			switch (ev.getEventType()) {
			case 59:
				tsEventLog = "TERMINALDONOTDISTURBEVENT for " + target;

				addObserverEvent(new TsapiTerminalDNDEvent(
						createTerminal(target), target.dndState, cause,
						metaCode, privateData), tsEventLog);

				break;
			case 47:
				tsEventLog = "TERMINALLOGGEDONEVENT for " + agent;

				addObserverEvent(new TsapiTermLogOnEv(createTerminal(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 48:
				tsEventLog = "TERMINALLOGGEDOFFEVENT for " + agent;

				addObserverEvent(new TsapiTermLogOffEv(createTerminal(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 49:
				tsEventLog = "TERMINALREADYEVENT for " + agent;

				addObserverEvent(new TsapiTermReadyEv(createTerminal(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 50:
				tsEventLog = "TERMINALNOTREADYEVENT for " + agent;

				addObserverEvent(new TsapiTermNotReadyEv(
						createTerminal(target), createAgent(agent), cause,
						metaCode, privateData), tsEventLog);

				break;
			case 51:
				tsEventLog = "TERMINALWORKREADYEVENT for " + agent;

				addObserverEvent(new TsapiTermWorkReadyEv(
						createTerminal(target), createAgent(agent), cause,
						metaCode, privateData), tsEventLog);

				break;
			case 52:
				tsEventLog = "TERMINALWORKNOTREADYEVENT for " + agent;

				addObserverEvent(new TsapiTermWorkNotReadyEv(
						createTerminal(target), createAgent(agent), cause,
						metaCode, privateData), tsEventLog);

				break;
			case 53:
				tsEventLog = "TERMINALBUSYEVENT for " + agent;

				addObserverEvent(new TsapiTermBusyEv(createTerminal(target),
						createAgent(agent), cause, metaCode, privateData),
						tsEventLog);

				break;
			case 9999:
				tsEventLog = "PRIVATEEVENT for " + target;

				addObserverEvent(new TsapiPrivateTerminalEvent(
						createTerminal(target), cause, metaCode, privateData),
						tsEventLog);
			}

		}

		synchronized (this.eventList) {
			log.debug("meta event END for " + this.observer
					+ " eventList size=" + this.eventList.size());

			if (this.eventList.size() == 0) {
				log.debug("no events to send to " + this.observer);
				return;
			}

			if (nextMetaEventIndex < this.eventList.size()) {
				((TsapiObserverEvent) this.eventList
						.elementAt(nextMetaEventIndex)).setNewMetaEventFlag();
			}
		}
	}

	private void createListenerEvents(Vector<TSEvent> _eventList,
			boolean snapshot) {
		String tsEventLog = null;
		if (_eventList == null)
			return;
		int cause;
		if (snapshot)
			cause = 110;
		else {
			cause = 100;
		}

		TSEvent ev = null;
		Object tsTarget = null;
		TSDevice target = null;
		TSAgent targetAgent = null;
		Object privateData = null;
		Object prevPrivateData = null;
		Terminal terminal = null;
		Agent agent = null;
		Object source = null;
		log.debug("meta event BEGIN: cause (" + cause + ")  for "
				+ this.terminalListener);
		for (int i = 0; i < _eventList.size(); i++) {
			ev = (TSEvent) _eventList.elementAt(i);
			tsTarget = ev.getEventTarget();
			if ((tsTarget instanceof TSDevice)) {
				target = (TSDevice) tsTarget;
				terminal = createTerminal(target);
				source = terminal;
			} else if ((tsTarget instanceof TSAgent)) {
				targetAgent = (TSAgent) tsTarget;
				target = targetAgent.getTSAgentDevice();
				agent = createAgent(targetAgent);
				terminal = createTerminal(target);
				source = agent;
			}

			privateData = ev.getPrivateData();

			if ((privateData != null) && (ev.getEventType() != 9999)) {
				if ((prevPrivateData != null)
						&& (!privateData.equals(prevPrivateData))) {
					prevPrivateData = privateData;
					tsEventLog = "PRIVATEEVENT for " + target;
					addPrivateDataEvent(603, cause, target, privateData,
							tsEventLog);
				}
			} else
				prevPrivateData = null;

			TerminalEventParams terminalEventParams = new TerminalEventParams();
			terminalEventParams.setCause(cause);
			terminalEventParams.setTerminal(terminal);
			terminalEventParams.setPrivateData(privateData);
			terminalEventParams.setSource(source);
			switch (ev.getEventType()) {
			case 59:
				tsEventLog = "TERMINALDONOTDISTURBEVENT for " + target;

				terminalEventParams.setEventId(371);
				addCallControlTerminalEvent(terminalEventParams,
						target.dndState, tsEventLog);
				break;
			case 47:
				tsEventLog = "TERMINALLOGGEDONEVENT for " + targetAgent;

				terminalEventParams.setEventId(310);
				addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
				break;
			case 48:
				tsEventLog = "TERMINALLOGGEDOFFEVENT for " + targetAgent;

				terminalEventParams.setEventId(309);
				addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
				break;
			case 49:
				tsEventLog = "TERMINALREADYEVENT for " + targetAgent;

				terminalEventParams.setEventId(312);
				addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
				break;
			case 50:
				tsEventLog = "TERMINALNOTREADYEVENT for " + targetAgent;

				terminalEventParams.setEventId(311);
				addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
				break;
			case 51:
				tsEventLog = "TERMINALWORKREADYEVENT for " + targetAgent;

				terminalEventParams.setEventId(315);
				addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
				break;
			case 52:
				tsEventLog = "TERMINALWORKNOTREADYEVENT for " + targetAgent;

				terminalEventParams.setEventId(314);
				addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
				break;
			case 53:
				tsEventLog = "TERMINALBUSYEVENT for " + targetAgent;

				terminalEventParams.setEventId(308);
				addAgentTerminalEvent(terminalEventParams, agent, tsEventLog);
				break;
			case 9999:
				tsEventLog = "PRIVATEEVENT for " + target;

				addPrivateDataEvent(603, cause, terminal, privateData,
						tsEventLog);
			}
		}

		log.debug("meta event END for " + this.terminalListener
				+ " eventList size=" + this.listenerEventList.size());

		if (this.listenerEventList.size() == 0) {
			log.debug("no events to send to " + this.terminalListener);
			return;
		}
	}

	Terminal createTerminal(TSDevice device) {
		if (device == null) {
			return null;
		}
		return (Terminal) TsapiCreateObject.getTsapiObject(device, false);
	}

	Agent createAgent(TSAgent agent) {
		if (agent == null) {
			return null;
		}
		return (Agent) TsapiCreateObject.getTsapiObject(agent, false);
	}

	public void run() {
		TsapiTrace.traceEntry("run[]", this);
		synchronized (this.syncObject) {
			if (this.observer != null)
				log.debug("Got syncObject for TerminalObserver - "
						+ this.observer);
			else
				log.debug("Got syncObject for TerminalListener - "
						+ this.terminalListener);
			TermEv[] eventArray = null;
			Event[] listenerEventArray = null;
			synchronized (this) {
				if (this.observer != null)
					synchronized (this.eventList) {
						if (this.eventList.size() == 0) {
							log.debug("TsapiTerminalMonitor: events delivered by previous thread; no events to deliver in this thread");
							TsapiTrace.traceExit("run[]", this);
							return;
						}
						log.debug("Got this for TerminalObserver - "
								+ this.observer);
						eventArray = new TermEv[this.eventList.size()];
						this.eventList.copyInto(eventArray);
						this.eventList.clear();
					}
				else {
					synchronized (this.listenerEventList) {
						if (this.listenerEventList.size() == 0) {
							log.debug("TsapiTerminalMonitor: events delivered by previous thread; no events to deliver in this thread");
							TsapiTrace.traceExit("run[]", this);
							return;
						}
						log.debug("Got this for TerminalListener - "
								+ this.terminalListener);
						listenerEventArray = new Event[this.listenerEventList
								.size()];
						this.listenerEventList.copyInto(listenerEventArray);
						this.listenerEventList.clear();
					}
				}
			}

			if (this.observer != null) {
				log.debug("calling terminalChangedEvent in " + this.observer);
				try {
					this.observer.terminalChangedEvent(eventArray);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by terminalChangedEvent in "
							+ this.observer + " - " + e.getMessage(), e);
				}
				log.debug("returned from terminalChangedEvent in "
						+ this.observer);
			} else {
				deliverListenerEvents(listenerEventArray);
			}
		}
		TsapiTrace.traceExit("run[]", this);
	}

	private void deliverListenerEvents(Event[] listenerEventArray) {
		for (Event event : listenerEventArray)
			switch (event.getID()) {
			case 121:
				log.debug("calling terminalListenerEnded in "
						+ this.terminalListener);
				try {
					this.terminalListener
							.terminalListenerEnded((TerminalEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by terminalListenerEnded in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from terminalListenerEnded in "
						+ this.terminalListener);
				break;
			case 308:
				log.debug("calling agentTerminalBusy in "
						+ this.terminalListener);
				try {
					((AgentTerminalListener) this.terminalListener)
							.agentTerminalBusy((AgentTerminalEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by agentTerminalBusy in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from agentTerminalBusy in "
						+ this.terminalListener);
				break;
			case 309:
				log.debug("calling agentTerminalLoggedOff in "
						+ this.terminalListener);
				try {
					((AgentTerminalListener) this.terminalListener)
							.agentTerminalLoggedOff((AgentTerminalEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by agentTerminalLoggedOff in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from agentTerminalLoggedOff in "
						+ this.terminalListener);
				break;
			case 310:
				log.debug("calling agentTerminalLoggedOn in "
						+ this.terminalListener);
				try {
					((AgentTerminalListener) this.terminalListener)
							.agentTerminalLoggedOn((AgentTerminalEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by agentTerminalLoggedOn in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from agentTerminalLoggedOn in "
						+ this.terminalListener);
				break;
			case 311:
				log.debug("calling agentTerminalNotReady in "
						+ this.terminalListener);
				try {
					((AgentTerminalListener) this.terminalListener)
							.agentTerminalNotReady((AgentTerminalEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by agentTerminalNotReady in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from agentTerminalNotReady in "
						+ this.terminalListener);
				break;
			case 312:
				log.debug("calling agentTerminalReady in "
						+ this.terminalListener);
				try {
					((AgentTerminalListener) this.terminalListener)
							.agentTerminalReady((AgentTerminalEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by agentTerminalReady in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from agentTerminalReady in "
						+ this.terminalListener);
				break;
			case 313:
				log.debug("calling agentTerminalUnknown in "
						+ this.terminalListener);
				try {
					((AgentTerminalListener) this.terminalListener)
							.agentTerminalUnknown((AgentTerminalEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by agentTerminalUnknown in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from agentTerminalUnknown in "
						+ this.terminalListener);
				break;
			case 314:
				log.debug("calling agentTerminalWorkNotReady in "
						+ this.terminalListener);
				try {
					((AgentTerminalListener) this.terminalListener)
							.agentTerminalWorkNotReady((AgentTerminalEvent) event);
				} catch (Exception e) {
					log.error(
							"EXCEPTION thrown by agentTerminalWorkNotReady in "
									+ this.terminalListener + " - "
									+ e.getMessage(), e);
				}
				log.debug("returned from agentTerminalWorkNotReady in "
						+ this.terminalListener);
				break;
			case 315:
				log.debug("calling agentTerminalWorkReady in "
						+ this.terminalListener);
				try {
					((AgentTerminalListener) this.terminalListener)
							.agentTerminalWorkReady((AgentTerminalEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by agentTerminalWorkReady in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from agentTerminalWorkReady in "
						+ this.terminalListener);
				break;
			case 371:
				log.debug("calling terminalDoNotDisturb in "
						+ this.terminalListener);
				try {
					((CallControlTerminalListener) this.terminalListener)
							.terminalDoNotDisturb((CallControlTerminalEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by terminalDoNotDisturb in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from terminalDoNotDisturb in "
						+ this.terminalListener);
				break;
			case 603:
				log.debug("calling terminalPrivateData in "
						+ this.terminalListener);
				try {
					((PrivateDataTerminalListener) this.terminalListener)
							.terminalPrivateData((PrivateDataEvent) event);
				} catch (Exception e) {
					log.error("EXCEPTION thrown by terminalPrivateData in "
							+ this.terminalListener + " - " + e.getMessage(), e);
				}
				log.debug("returned from terminalPrivateData in "
						+ this.terminalListener);
			}
	}
}