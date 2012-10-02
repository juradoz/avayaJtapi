package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentQueryAcdSplitConfEvent extends LucentPrivateData {
	int availableAgents;
	int callsInQueue;
	int agentsLoggedIn;
	public static final int PDU = 12;

	public static LucentQueryAcdSplitConfEvent decode(InputStream in) {
		LucentQueryAcdSplitConfEvent _this = new LucentQueryAcdSplitConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.availableAgents = ASNInteger.decode(memberStream);
		this.callsInQueue = ASNInteger.decode(memberStream);
		this.agentsLoggedIn = ASNInteger.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(this.availableAgents, memberStream);
		ASNInteger.encode(this.callsInQueue, memberStream);
		ASNInteger.encode(this.agentsLoggedIn, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAcdSplitConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(this.availableAgents, "availableAgents",
				indent));
		lines.addAll(ASNInteger
				.print(this.callsInQueue, "callsInQueue", indent));
		lines.addAll(ASNInteger.print(this.agentsLoggedIn, "agentsLoggedIn",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 12;
	}

	public int getAgentsLoggedIn() {
		return this.agentsLoggedIn;
	}

	public int getAvailableAgents() {
		return this.availableAgents;
	}

	public int getCallsInQueue() {
		return this.callsInQueue;
	}

	public void setAgentsLoggedIn(int agentsLoggedIn) {
		this.agentsLoggedIn = agentsLoggedIn;
	}

	public void setAvailableAgents(int availableAgents) {
		this.availableAgents = availableAgents;
	}

	public void setCallsInQueue(int callsInQueue) {
		this.callsInQueue = callsInQueue;
	}
}