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

	public static LucentQueryAgentLoginResp decode(InputStream in) {
		LucentQueryAgentLoginResp _this = new LucentQueryAgentLoginResp();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		privEventCrossRefID = ASNInteger.decode(memberStream);
		devices = LucentQueryAgentLoginRespList.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentLoginResp ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(privEventCrossRefID,
				"privEventCrossRefID", indent));
		lines.addAll(LucentQueryAgentLoginRespList.print(devices, "devices",
				indent));

		lines.add("}");
		return lines;
	}

	public void setDevices(String[] devices) {
		this.devices = devices;
	}

	public void setPrivEventCrossRefID(int privEventCrossRefID) {
		this.privEventCrossRefID = privEventCrossRefID;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginResp JD-Core Version: 0.5.4
 */