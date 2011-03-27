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

	static LucentGetAPICapsConfEvent decode(final InputStream in) {
		final LucentV7GetAPICapsConfEvent _this = new LucentV7GetAPICapsConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		maxDeviceHistoryCount = ASNInteger.decode(memberStream);
		administeredSwitchSoftwareVersion = ASNIA5String.decode(memberStream);
		switchSoftwareVersion = ASNIA5String.decode(memberStream);
		offerType = ASNIA5String.decode(memberStream);
		serverType = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV7GetAPICapsConfEvent ::=");
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
		lines.addAll(ASNBoolean.print(singleStepConference,
				"singleStepConference", indent));

		lines.addAll(ASNBoolean.print(selectiveListeningHold,
				"selectiveListeningHold", indent));

		lines.addAll(ASNBoolean.print(selectiveListeningRetrieve,
				"selectiveListeningRetrieve", indent));

		lines.addAll(ASNBoolean.print(setBillingRate, "setBillingRate", indent));
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
			final String administeredSwitchSoftwareVersion) {
		this.administeredSwitchSoftwareVersion = administeredSwitchSoftwareVersion;
	}

	public void setMaxDeviceHistoryCount(final int maxDeviceHistoryCount) {
		this.maxDeviceHistoryCount = maxDeviceHistoryCount;
	}

	public void setOfferType(final String offerType) {
		this.offerType = offerType;
	}

	public void setServerType(final String serverType) {
		this.serverType = serverType;
	}

	public void setSwitchSoftwareVersion(final String switchSoftwareVersion) {
		this.switchSoftwareVersion = switchSoftwareVersion;
	}
}
