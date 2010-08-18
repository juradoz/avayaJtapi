package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentQueryAcdSplitConfEvent extends LucentPrivateData {
	int availableAgents;
	int callsInQueue;
	int agentsLoggedIn;
	public static final int PDU = 12;

	public static LucentQueryAcdSplitConfEvent decode(final InputStream in) {
		final LucentQueryAcdSplitConfEvent _this = new LucentQueryAcdSplitConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		availableAgents = ASNInteger.decode(memberStream);
		callsInQueue = ASNInteger.decode(memberStream);
		agentsLoggedIn = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(availableAgents, memberStream);
		ASNInteger.encode(callsInQueue, memberStream);
		ASNInteger.encode(agentsLoggedIn, memberStream);
	}

	public int getAgentsLoggedIn() {
		return agentsLoggedIn;
	}

	public int getAvailableAgents() {
		return availableAgents;
	}

	public int getCallsInQueue() {
		return callsInQueue;
	}

	@Override
	public int getPDU() {
		return 12;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAcdSplitConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(availableAgents, "availableAgents",
				indent));
		lines.addAll(ASNInteger.print(callsInQueue, "callsInQueue", indent));
		lines
				.addAll(ASNInteger.print(agentsLoggedIn, "agentsLoggedIn",
						indent));

		lines.add("}");
		return lines;
	}

	public void setAgentsLoggedIn(final int agentsLoggedIn) {
		this.agentsLoggedIn = agentsLoggedIn;
	}

	public void setAvailableAgents(final int availableAgents) {
		this.availableAgents = availableAgents;
	}

	public void setCallsInQueue(final int callsInQueue) {
		this.callsInQueue = callsInQueue;
	}
}
