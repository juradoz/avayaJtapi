package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV6ConferencedEvent extends LucentV5ConferencedEvent
		implements LucentTrunkConnectionMapping {
	CSTATrunkInfo[] lucentTrunkInfo;
	static final int PDU = 107;

	public static LucentConferencedEvent decode(InputStream in) {
		LucentV6ConferencedEvent _this = new LucentV6ConferencedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		LucentTrunkInfoList.encode(this.lucentTrunkInfo, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.lucentTrunkInfo = LucentTrunkInfoList.decode(memberStream);
	}

	public CSTATrunkInfo[] getLucentTrunkInfo() {
		return this.lucentTrunkInfo;
	}

	public void setLucentTrunkInfo(CSTATrunkInfo[] _trunkList) {
		this.lucentTrunkInfo = _trunkList;
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("LucentV6ConferencedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentV5OriginalCallInfo.print(this.originalCallInfo,
				"originalCallInfo", indent));

		lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn,
				"distributingDevice", indent));

		lines.addAll(UCID.print(this.ucid, "ucid", indent));
		lines.addAll(LucentTrunkInfoList.print(this.lucentTrunkInfo,
				"lucentTrunkInfo", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 107;
	}
}