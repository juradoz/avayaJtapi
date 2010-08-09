package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

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

	@Override
	public void decodeMembers(InputStream memberStream) {
		switchVersion = ASNIA5String.decode(memberStream);
		sendDTMFTone = ASNBoolean.decode(memberStream);
		enteredDigitsEvent = ASNBoolean.decode(memberStream);
		queryDeviceName = ASNBoolean.decode(memberStream);
		queryAgentMeas = ASNBoolean.decode(memberStream);
		querySplitSkillMeas = ASNBoolean.decode(memberStream);
		queryTrunkGroupMeas = ASNBoolean.decode(memberStream);
		queryVdnMeas = ASNBoolean.decode(memberStream);
		singleStepConference = ASNBoolean.decode(memberStream);
		selectiveListeningHold = ASNBoolean.decode(memberStream);
		selectiveListeningRetrieve = ASNBoolean.decode(memberStream);
		setBillingRate = ASNBoolean.decode(memberStream);
		queryUCID = ASNBoolean.decode(memberStream);
		chargeAdviceEvent = ASNBoolean.decode(memberStream);
		reserved1 = ASNBoolean.decode(memberStream);
		reserved2 = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(switchVersion, memberStream);
		ASNBoolean.encode(sendDTMFTone, memberStream);
		ASNBoolean.encode(enteredDigitsEvent, memberStream);
		ASNBoolean.encode(queryDeviceName, memberStream);
		ASNBoolean.encode(queryAgentMeas, memberStream);
		ASNBoolean.encode(querySplitSkillMeas, memberStream);
		ASNBoolean.encode(queryTrunkGroupMeas, memberStream);
		ASNBoolean.encode(queryVdnMeas, memberStream);
		ASNBoolean.encode(singleStepConference, memberStream);
		ASNBoolean.encode(selectiveListeningHold, memberStream);
		ASNBoolean.encode(selectiveListeningRetrieve, memberStream);
		ASNBoolean.encode(setBillingRate, memberStream);
		ASNBoolean.encode(queryUCID, memberStream);
		ASNBoolean.encode(chargeAdviceEvent, memberStream);
		ASNBoolean.encode(reserved1, memberStream);
		ASNBoolean.encode(reserved2, memberStream);
	}

	@Override
	public int getPDU() {
		return 97;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentV5GetAPICapsConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines
				.addAll(ASNIA5String.print(switchVersion, "switchVersion",
						indent));
		lines.addAll(ASNBoolean.print(sendDTMFTone, "sendDTMFTone", indent));
		lines.addAll(ASNBoolean.print(enteredDigitsEvent, "enteredDigitsEvent",
				indent));

		lines.addAll(ASNBoolean.print(queryDeviceName, "queryDeviceName",
				indent));
		lines
				.addAll(ASNBoolean.print(queryAgentMeas, "queryAgentMeas",
						indent));
		lines.addAll(ASNBoolean.print(querySplitSkillMeas,
				"querySplitSkillMeas", indent));

		lines.addAll(ASNBoolean.print(queryTrunkGroupMeas,
				"queryTrunkGroupMeas", indent));

		lines.addAll(ASNBoolean.print(queryVdnMeas, "queryVdnMeas", indent));
		lines.addAll(ASNBoolean.print(singleStepConference,
				"singleStepConference", indent));

		lines.addAll(ASNBoolean.print(selectiveListeningHold,
				"selectiveListeningHold", indent));

		lines.addAll(ASNBoolean.print(selectiveListeningRetrieve,
				"selectiveListeningRetrieve", indent));

		lines
				.addAll(ASNBoolean.print(setBillingRate, "setBillingRate",
						indent));
		lines.addAll(ASNBoolean.print(queryUCID, "queryUCID", indent));
		lines.addAll(ASNBoolean.print(chargeAdviceEvent, "chargeAdviceEvent",
				indent));

		lines.addAll(ASNBoolean.print(reserved1, "reserved1", indent));
		lines.addAll(ASNBoolean.print(reserved2, "reserved2", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV5GetAPICapsConfEvent JD-Core Version:
 * 0.5.4
 */