package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentV7GetAPICapsConfEvent extends
		LucentV5GetAPICapsConfEvent {
	private int maxDeviceHistoryCount;
	private String administeredSwitchSoftwareVersion;
	private String switchSoftwareVersion;
	private String offerType;
	private String serverType;
	static final int PDU = 127;

	static LucentGetAPICapsConfEvent decode(InputStream in) {
		LucentV7GetAPICapsConfEvent _this = new LucentV7GetAPICapsConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		maxDeviceHistoryCount = ASNInteger.decode(memberStream);
		administeredSwitchSoftwareVersion = ASNIA5String.decode(memberStream);
		switchSoftwareVersion = ASNIA5String.decode(memberStream);
		offerType = ASNIA5String.decode(memberStream);
		serverType = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNInteger.encode(maxDeviceHistoryCount, memberStream);
		ASNIA5String.encode(administeredSwitchSoftwareVersion, memberStream);
		ASNIA5String.encode(switchSoftwareVersion, memberStream);
		ASNIA5String.encode(offerType, memberStream);
		ASNIA5String.encode(serverType, memberStream);
	}

	public String getAdministeredSwitchSoftwareVersion() {
		return administeredSwitchSoftwareVersion;
	}

	public int getMaxDeviceHistoryCount() {
		return maxDeviceHistoryCount;
	}

	public boolean getMonitorCallsViaDevice() {
		return reserved2;
	}

	public String getOfferType() {
		return offerType;
	}

	@Override
	public int getPDU() {
		return 127;
	}

	public String getServerType() {
		return serverType;
	}

	public boolean getSingleStepTransfer() {
		return reserved1;
	}

	public String getSwitchSoftwareVersion() {
		return switchSoftwareVersion;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentV7GetAPICapsConfEvent ::=");
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

		lines.addAll(ASNBoolean.print(reserved1,
				"reserved1(singleStepTransfer)", indent));
		lines.addAll(ASNBoolean.print(reserved2,
				"reserved2(monitorCallsViaDevice)", indent));
		lines.addAll(ASNInteger.print(maxDeviceHistoryCount,
				"maxDeviceHistoryCount", indent));

		lines.addAll(ASNIA5String.print(administeredSwitchSoftwareVersion,
				"administeredSwitchSoftwareVersion", indent));

		lines.addAll(ASNIA5String.print(switchSoftwareVersion,
				"switchSoftwareVersion", indent));

		lines.addAll(ASNIA5String.print(offerType, "offerType", indent));
		lines.addAll(ASNIA5String.print(serverType, "serverType", indent));

		lines.add("}");
		return lines;
	}

	public void setAdministeredSwitchSoftwareVersion(
			String administeredSwitchSoftwareVersion) {
		this.administeredSwitchSoftwareVersion = administeredSwitchSoftwareVersion;
	}

	public void setMaxDeviceHistoryCount(int maxDeviceHistoryCount) {
		this.maxDeviceHistoryCount = maxDeviceHistoryCount;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public void setSwitchSoftwareVersion(String switchSoftwareVersion) {
		this.switchSoftwareVersion = switchSoftwareVersion;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV7GetAPICapsConfEvent JD-Core Version:
 * 0.5.4
 */