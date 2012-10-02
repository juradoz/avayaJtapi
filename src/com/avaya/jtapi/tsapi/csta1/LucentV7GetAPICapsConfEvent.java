package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

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

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.maxDeviceHistoryCount = ASNInteger.decode(memberStream);
		this.administeredSwitchSoftwareVersion = ASNIA5String
				.decode(memberStream);
		this.switchSoftwareVersion = ASNIA5String.decode(memberStream);
		this.offerType = ASNIA5String.decode(memberStream);
		this.serverType = ASNIA5String.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNInteger.encode(this.maxDeviceHistoryCount, memberStream);
		ASNIA5String.encode(this.administeredSwitchSoftwareVersion,
				memberStream);
		ASNIA5String.encode(this.switchSoftwareVersion, memberStream);
		ASNIA5String.encode(this.offerType, memberStream);
		ASNIA5String.encode(this.serverType, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV7GetAPICapsConfEvent ::=");
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

		lines.addAll(ASNBoolean.print(this.reserved1,
				"reserved1(singleStepTransfer)", indent));
		lines.addAll(ASNBoolean.print(this.reserved2,
				"reserved2(monitorCallsViaDevice)", indent));
		lines.addAll(ASNInteger.print(this.maxDeviceHistoryCount,
				"maxDeviceHistoryCount", indent));

		lines.addAll(ASNIA5String.print(this.administeredSwitchSoftwareVersion,
				"administeredSwitchSoftwareVersion", indent));

		lines.addAll(ASNIA5String.print(this.switchSoftwareVersion,
				"switchSoftwareVersion", indent));

		lines.addAll(ASNIA5String.print(this.offerType, "offerType", indent));
		lines.addAll(ASNIA5String.print(this.serverType, "serverType", indent));

		lines.add("}");
		return lines;
	}

	public String getOfferType() {
		return this.offerType;
	}

	public String getServerType() {
		return this.serverType;
	}

	public String getSwitchSoftwareVersion() {
		return this.switchSoftwareVersion;
	}

	public String getAdministeredSwitchSoftwareVersion() {
		return this.administeredSwitchSoftwareVersion;
	}

	public boolean getSingleStepTransfer() {
		return this.reserved1;
	}

	public boolean getMonitorCallsViaDevice() {
		return this.reserved2;
	}

	public int getPDU() {
		return 127;
	}

	public int getMaxDeviceHistoryCount() {
		return this.maxDeviceHistoryCount;
	}

	public void setMaxDeviceHistoryCount(int maxDeviceHistoryCount) {
		this.maxDeviceHistoryCount = maxDeviceHistoryCount;
	}

	public void setAdministeredSwitchSoftwareVersion(
			String administeredSwitchSoftwareVersion) {
		this.administeredSwitchSoftwareVersion = administeredSwitchSoftwareVersion;
	}

	public void setSwitchSoftwareVersion(String switchSoftwareVersion) {
		this.switchSoftwareVersion = switchSoftwareVersion;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
}