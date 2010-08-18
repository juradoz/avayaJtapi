package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentQueryAgentLoginResp extends LucentPrivateData {
	int privEventCrossRefID;
	String[] devices;
	public static final int PDU = 15;

	public static LucentQueryAgentLoginResp decode(final InputStream in) {
		final LucentQueryAgentLoginResp _this = new LucentQueryAgentLoginResp();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		privEventCrossRefID = ASNInteger.decode(memberStream);
		devices = LucentQueryAgentLoginRespList.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNInteger.encode(privEventCrossRefID, memberStream);
		LucentQueryAgentLoginRespList.encode(devices, memberStream);
	}

	public String[] getDevices() {
		return devices;
	}

	@Override
	public int getPDU() {
		return 15;
	}

	public int getPrivEventCrossRefID() {
		return privEventCrossRefID;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentLoginResp ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(privEventCrossRefID,
				"privEventCrossRefID", indent));
		lines.addAll(LucentQueryAgentLoginRespList.print(devices, "devices",
				indent));

		lines.add("}");
		return lines;
	}

	public void setDevices(final String[] devices) {
		this.devices = devices;
	}

	public void setPrivEventCrossRefID(final int privEventCrossRefID) {
		this.privEventCrossRefID = privEventCrossRefID;
	}
}
