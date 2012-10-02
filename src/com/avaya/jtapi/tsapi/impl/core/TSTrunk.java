package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;
import org.apache.log4j.Logger;

public final class TSTrunk {
	private static Logger log = Logger.getLogger(TSTrunk.class);
	TSProviderImpl provider;
	String name;
	String groupName;
	String memberName;
	TSCall call;
	int state;
	int type;
	TSConnection connection;

	void dump(String indent) {
		log.trace(indent + "***** TRUNK DUMP *****");
		log.trace(indent + "TSTrunk: " + this);
		log.trace(indent + "TSTrunk name: " + this.name);
		log.trace(indent + "TSTrunk state: " + this.state);
		log.trace(indent + "TSTrunk call: " + this.call);
		log.trace(indent + "TSTrunk groupName: " + this.groupName);
		log.trace(indent + "TSTrunk memberName: " + this.memberName);
		log.trace(indent + "***** TRUNK DUMP END *****");
	}

	public String getName() {
		return this.name;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setGroupName(String _name) {
		if ((_name != null) && (_name != "")) {
			this.groupName = _name;
		}
	}

	public void setMemberName(String _name) {
		if ((_name != null) && (_name != "")) {
			this.memberName = _name;
		}
	}

	public int getState() {
		return this.state;
	}

	public int getType() {
		return this.type;
	}

	public synchronized TSCall getTSCall() {
		return this.call;
	}

	public TSProviderImpl getTSProviderImpl() {
		return this.provider;
	}

	boolean setCall(TSCall _call, Vector<TSEvent> eventList) {
		synchronized (this) {
			if (this.call == _call) {
				return false;
			}

			if (this.call != null) {
				this.call.removeTrunk(this, null);
			}
			this.call = _call;
		}
		setState(2, eventList);

		this.provider.addTrunkToHash(this.name, this);

		return true;
	}

	void unsetCall(Vector<TSEvent> eventList) {
		setState(1, eventList);
	}

	TSTrunk(TSProviderImpl _provider, String _name, int _type) {
		this.provider = _provider;
		this.name = _name;
		this.type = _type;
		this.state = 0;

		if (this.name == null) {
			this.groupName = (this.memberName = null);
		} else {
			int colonPos = this.name.indexOf(':');
			if ((colonPos <= 0) || (colonPos >= this.name.length() - 1)) {
				this.groupName = (this.memberName = null);
			} else {
				this.groupName = this.name.substring(0, colonPos - 1);
				this.memberName = this.name.substring(colonPos + 1);
			}
		}
		this.provider.addTrunkToHash(this.name, this);
		log.info("Trunk object= " + this + " being created with name "
				+ this.name + " (group:member = " + getGroupAndMember()
				+ ") for " + this.provider);
	}

	String getGroupAndMember() {
		String g_m;
		if (this.groupName == null) {
			g_m = "-:";
		} else {
			g_m = this.groupName + ":";
		}
		if (this.memberName == null) {
			g_m = g_m + "-";
		} else {
			g_m = g_m + this.memberName;
		}
		return g_m;
	}

	void setState(int _state, Vector<TSEvent> eventList) {
		synchronized (this) {
			if (this.state == _state) {
				return;
			}

			this.state = _state;
		}

		switch (this.state) {
		case 2:
			if (eventList != null) {
				eventList.addElement(new TSEvent(54, this));
			}
			break;
		case 1:
			if (eventList != null) {
				eventList.addElement(new TSEvent(55, this));
			}

			delete();
			break;
		}
	}

	void setType(int _type) {
		this.type = _type;
	}

	synchronized void delete() {
		log.info("Trunk object= " + this + " being deleted" + " for "
				+ this.provider);

		this.provider.deleteTrunkFromHash(this.name);
	}

	public void setTSConnection(TSConnection _conn) {
		this.connection = _conn;
	}

	public TSConnection getTSConnection() {
		return this.connection;
	}
}