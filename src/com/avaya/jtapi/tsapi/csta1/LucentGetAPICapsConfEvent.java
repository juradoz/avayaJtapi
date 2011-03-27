package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentGetAPICapsConfEvent extends LucentPrivateData {
	String switchVersion;
	boolean sendDTMFTone;
	boolean enteredDigitsEvent;
	boolean queryDeviceName;
	boolean queryAgentMeas;
	boolean querySplitSkillMeas;
	boolean queryTrunkGroupMeas;
	boolean queryVdnMeas;
	boolean reserved1;
	boolean reserved2;
	static final int PDU = 64;

	static LucentGetAPICapsConfEvent decode(final InputStream in) {
		final LucentGetAPICapsConfEvent _this = new LucentGetAPICapsConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		switchVersion = ASNIA5String.decode(memberStream);
		sendDTMFTone = ASNBoolean.decode(memberStream);
		enteredDigitsEvent = ASNBoolean.decode(memberStream);
		queryDeviceName = ASNBoolean.decode(memberStream);
		queryAgentMeas = ASNBoolean.decode(memberStream);
		querySplitSkillMeas = ASNBoolean.decode(memberStream);
		queryTrunkGroupMeas = ASNBoolean.decode(memberStream);
		queryVdnMeas = ASNBoolean.decode(memberStream);
		reserved1 = ASNBoolean.decode(memberStream);
		reserved2 = ASNBoolean.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 64;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentGetAPICapsConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(switchVersion, "switchVersion", indent));
		lines.addAll(ASNBoolean.print(sendDTMFTone, "sendDTMFTone", indent));
		lines.addAll(ASNBoolean.print(enteredDigitsEvent, "enteredDigitsEvent",
				indent));
		lines.addAll(ASNBoolean.print(queryDeviceName, "queryDeviceName",
				indent));
		lines.addAll(ASNBoolean.print(queryAgentMeas, "queryAgentMeas", indent));
		lines.addAll(ASNBoolean.print(querySplitSkillMeas,
				"querySplitSkillMeas", indent));
		lines.addAll(ASNBoolean.print(queryTrunkGroupMeas,
				"queryTrunkGroupMeas", indent));
		lines.addAll(ASNBoolean.print(queryVdnMeas, "queryVdnMeas", indent));
		lines.addAll(ASNBoolean.print(reserved1, "reserved1", indent));
		lines.addAll(ASNBoolean.print(reserved2, "reserved2", indent));

		lines.add("}");
		return lines;
	}
}
