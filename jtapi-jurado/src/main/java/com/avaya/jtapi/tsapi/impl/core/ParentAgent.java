package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.Logger;

public class ParentAgent {
	private static Logger log = Logger.getLogger(ParentAgent.class);
	private TSProviderImpl provider;
	private TSAgentKey agentKey;
	private Vector<TSDevice> skillsVector;
	private int state;
	private int lucentWorkMode;
	private int workMode;
	private int reasonCode;
	private int pendingState;
	private int pendingReasonCode;

	public ParentAgent(TSProviderImpl provider, TSAgentKey agentKey) {
		this.provider = provider;
		this.agentKey = agentKey;
		this.skillsVector = new Vector<TSDevice>();
	}

	public TSAgentKey getAgentKey() {
		return this.agentKey;
	}

	public void setAgentKey(TSAgentKey agentKey) {
		this.agentKey = agentKey;
	}

	public Vector<TSDevice> getSkillsVector() {
		return this.skillsVector;
	}

	public void setSkillsVector(Vector<TSDevice> skillsVector) {
		this.skillsVector = skillsVector;
	}

	public int getState() {
		return this.state;
	}

	public int getLucentWorkMode() {
		return this.lucentWorkMode;
	}

	public int getWorkMode() {
		return this.workMode;
	}

	public int getReasonCode() {
		return this.reasonCode;
	}

	public int getPendingState() {
		return this.pendingState;
	}

	public int getPendingReasonCode() {
		return this.pendingReasonCode;
	}

	public void addSkill(TSDevice skillToAdd) {
		synchronized (this.skillsVector) {
			if (!this.skillsVector.contains(skillToAdd))
				this.skillsVector.add(skillToAdd);
		}
	}

	public void deleteSkill(TSDevice skillToDelete) {
		synchronized (this.skillsVector) {
			this.skillsVector.remove(skillToDelete);
			if (this.skillsVector.size() == 0) {
				log.info("ParentAgent object " + this.agentKey
						+ " being deleted for provider " + this.provider);
				this.provider.deleteParentAgentFromHash(this.agentKey);
			}
		}
	}

	public void populateSkillsVector() {
		ArrayList<?> skillsArray = this.provider.getParentAgentSkills(
				this.agentKey.agentDeviceID, this.agentKey.agentID);
		Iterator<?> iterator = skillsArray.iterator();
		while (iterator.hasNext())
			addSkill(this.provider.createDevice((String) iterator.next()));
	}

	public void clearSkillsVector() {
		synchronized (this.skillsVector) {
			this.skillsVector.clear();
		}
	}

	public void deliverEventsToOtherSkills(TSAgent agent,
			Vector<TSEvent> eventList, TSDevice skillToSkip) {
		synchronized (this.skillsVector) {
			if ((agent.getInternalState() != 1)
					&& (agent.getInternalState() != 2)
					&& (this.skillsVector.size() > 1)) {
				Iterator<TSDevice> iterator = this.skillsVector.iterator();
				while (iterator.hasNext()) {
					TSDevice skillDevice = (TSDevice) iterator.next();
					if ((skillDevice != null) && (skillDevice != skillToSkip)) {
						TSAgentKey agentKey = new TSAgentKey(agent
								.getTSAgentDevice().getName(),
								skillDevice.getName(), agent.getAgentID());
						TSAgent eventTarget = this.provider.createAgent(
								agentKey, agent.getAgentID(), null,
								TSProviderImpl.CREATEAGENT_REFUSE_DELETED);
						if ((eventTarget.getAgentID() != null)
								&& (!eventTarget.getAgentID().equals(""))
								&& (eventTarget.getACDDeviceID() != null)) {
							Iterator<TSEvent> iterator1 = eventList.iterator();
							while (iterator1.hasNext()) {
								TSEvent event = (TSEvent) iterator1.next();
								event.setEventTarget(eventTarget);
								eventTarget.copyAgentState(agent);
							}

							Vector<?> observers = null;
							observers = skillDevice.getAddressObservers();
							for (int j = 0; j < observers.size(); j++) {
								TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
										.elementAt(j);
								log.info("Delivering events of multiskilled agent: "
										+ agentKey
										+ " to ACD: "
										+ skillDevice.getName());

								callback.deliverEvents(eventList, false);
							}

							Vector<?> tObservers = eventTarget
									.getTerminalObservers();
							for (int j = 0; j < tObservers.size(); j++) {
								TsapiTerminalMonitor callback = (TsapiTerminalMonitor) tObservers
										.elementAt(j);
								log.debug("Delivering events of multiskilled agent: "
										+ agentKey
										+ " to AgentTerminal: "
										+ agent.agentDevice.getName());

								callback.deliverEvents(eventList, false);
							}
						}
					}
				}
			}
			this.state = agent.getInternalState();
			this.lucentWorkMode = agent.getLucentWorkMode();
			this.workMode = agent.getWorkMode();
			this.reasonCode = agent.getReasonCode();
			this.pendingState = agent.getPendingState();
			this.pendingReasonCode = agent.getPendingReasonCode();
		}
	}
}