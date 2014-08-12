package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

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

	static LucentGetAPICapsConfEvent decode(InputStream in) {
		LucentGetAPICapsConfEvent _this = new LucentGetAPICapsConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.switchVersion = ASNIA5String.decode(memberStream);
		this.sendDTMFTone = ASNBoolean.decode(memberStream);
		this.enteredDigitsEvent = ASNBoolean.decode(memberStream);
		this.queryDeviceName = ASNBoolean.decode(memberStream);
		this.queryAgentMeas = ASNBoolean.decode(memberStream);
		this.querySplitSkillMeas = ASNBoolean.decode(memberStream);
		this.queryTrunkGroupMeas = ASNBoolean.decode(memberStream);
		this.queryVdnMeas = ASNBoolean.decode(memberStream);
		this.reserved1 = ASNBoolean.decode(memberStream);
		this.reserved2 = ASNBoolean.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentGetAPICapsConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(this.switchVersion, "switchVersion",
				indent));
		lines.addAll(ASNBoolean
				.print(this.sendDTMFTone, "sendDTMFTone", indent));
		lines.addAll(ASNBoolean.print(this.enteredDigitsEvent,
				"enteredDigitsEvent", indent));
		lines.addAll(ASNBoolean.print(this.queryDeviceName, "queryDeviceName",
				indent));
		lines.addAll(ASNBoolean.print(this.queryAgentMeas, "queryAgentMeas",
				indent));
		lines.addAll(ASNBoolean.print(this.querySplitSkillMeas,
				"querySplitSkillMeas", indent));
		lines.addAll(ASNBoolean.print(this.queryTrunkGroupMeas,
				"queryTrunkGroupMeas", indent));
		lines.addAll(ASNBoolean
				.print(this.queryVdnMeas, "queryVdnMeas", indent));
		lines.addAll(ASNBoolean.print(this.reserved1, "reserved1", indent));
		lines.addAll(ASNBoolean.print(this.reserved2, "reserved2", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 64;
	}

	public void setMonitorCallsViaDevice(boolean monitorCallsViaDevice) {
		this.reserved2 = monitorCallsViaDevice;
	}
}