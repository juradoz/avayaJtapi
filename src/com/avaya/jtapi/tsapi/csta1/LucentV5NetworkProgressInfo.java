package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentV5NetworkProgressInfo extends LucentNetworkProgressInfo {
	String trunkGroup;
	String trunkMember;
	public static final int PDU = 101;

	public static LucentNetworkProgressInfo decode(InputStream in) {
		LucentV5NetworkProgressInfo _this = new LucentV5NetworkProgressInfo();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		trunkGroup = ASNIA5String.decode(memberStream);
		trunkMember = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNIA5String.encode(trunkGroup, memberStream);
		ASNIA5String.encode(trunkMember, memberStream);
	}

	@Override
	public int getPDU() {
		return 101;
	}

	public String getTrunkGroup() {
		return trunkGroup;
	}

	public String getTrunkMember() {
		return trunkMember;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("V5NetworkProgressInfo ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ProgressLocation.print(progressLocation,
				"progressLocation", indent));

		lines.addAll(ProgressDescription.print(progressDescription,
				"progressDescription", indent));

		lines.addAll(ASNIA5String.print(trunkGroup, "trunkGroup", indent));
		lines.addAll(ASNIA5String.print(trunkMember, "trunkMember", indent));

		lines.add("}");
		return lines;
	}

	public void setTrunkGroup(String trunkGroup) {
		this.trunkGroup = trunkGroup;
	}

	public void setTrunkMember(String trunkMember) {
		this.trunkMember = trunkMember;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV5NetworkProgressInfo JD-Core Version:
 * 0.5.4
 */