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

	public static LucentQueryAcdSplitConfEvent decode(InputStream in) {
		LucentQueryAcdSplitConfEvent _this = new LucentQueryAcdSplitConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		availableAgents = ASNInteger.decode(memberStream);
		callsInQueue = ASNInteger.decode(memberStream);
		agentsLoggedIn = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("LucentQueryAcdSplitConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(availableAgents, "availableAgents",
				indent));
		lines.addAll(ASNInteger.print(callsInQueue, "callsInQueue", indent));
		lines
				.addAll(ASNInteger.print(agentsLoggedIn, "agentsLoggedIn",
						indent));

		lines.add("}");
		return lines;
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplitConfEvent JD-Core Version:
 * 0.5.4
 */