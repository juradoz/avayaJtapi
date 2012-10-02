package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV5GetAPICapsConfEvent extends LucentGetAPICapsConfEvent {
	boolean singleStepConference;
	boolean selectiveListeningHold;
	boolean selectiveListeningRetrieve;
	boolean setBillingRate;
	boolean queryUCID;
	boolean chargeAdviceEvent;
	static final int PDU = 97;

	static LucentGetAPICapsConfEvent decode(InputStream in) {
		LucentV5GetAPICapsConfEvent _this = new LucentV5GetAPICapsConfEvent();
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
		this.singleStepConference = ASNBoolean.decode(memberStream);
		this.selectiveListeningHold = ASNBoolean.decode(memberStream);
		this.selectiveListeningRetrieve = ASNBoolean.decode(memberStream);
		this.setBillingRate = ASNBoolean.decode(memberStream);
		this.queryUCID = ASNBoolean.decode(memberStream);
		this.chargeAdviceEvent = ASNBoolean.decode(memberStream);
		this.reserved1 = ASNBoolean.decode(memberStream);
		this.reserved2 = ASNBoolean.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(this.switchVersion, memberStream);
		ASNBoolean.encode(this.sendDTMFTone, memberStream);
		ASNBoolean.encode(this.enteredDigitsEvent, memberStream);
		ASNBoolean.encode(this.queryDeviceName, memberStream);
		ASNBoolean.encode(this.queryAgentMeas, memberStream);
		ASNBoolean.encode(this.querySplitSkillMeas, memberStream);
		ASNBoolean.encode(this.queryTrunkGroupMeas, memberStream);
		ASNBoolean.encode(this.queryVdnMeas, memberStream);
		ASNBoolean.encode(this.singleStepConference, memberStream);
		ASNBoolean.encode(this.selectiveListeningHold, memberStream);
		ASNBoolean.encode(this.selectiveListeningRetrieve, memberStream);
		ASNBoolean.encode(this.setBillingRate, memberStream);
		ASNBoolean.encode(this.queryUCID, memberStream);
		ASNBoolean.encode(this.chargeAdviceEvent, memberStream);
		ASNBoolean.encode(this.reserved1, memberStream);
		ASNBoolean.encode(this.reserved2, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5GetAPICapsConfEvent ::=");
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
		lines.addAll(ASNBoolean.print(this.singleStepConference,
				"singleStepConference", indent));

		lines.addAll(ASNBoolean.print(this.selectiveListeningHold,
				"selectiveListeningHold", indent));

		lines.addAll(ASNBoolean.print(this.selectiveListeningRetrieve,
				"selectiveListeningRetrieve", indent));

		lines.addAll(ASNBoolean.print(this.setBillingRate, "setBillingRate",
				indent));
		lines.addAll(ASNBoolean.print(this.queryUCID, "queryUCID", indent));
		lines.addAll(ASNBoolean.print(this.chargeAdviceEvent,
				"chargeAdviceEvent", indent));

		lines.addAll(ASNBoolean.print(this.reserved1, "reserved1", indent));
		lines.addAll(ASNBoolean.print(this.reserved2, "reserved2", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 97;
	}
}