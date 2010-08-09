package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentV6ConferencedEvent extends LucentV5ConferencedEvent
		implements LucentTrunkConnectionMapping {
	CSTATrunkInfo[] lucentTrunkInfo;
	static final int PDU = 107;

	public static LucentConferencedEvent decode(InputStream in) {
		LucentV6ConferencedEvent _this = new LucentV6ConferencedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		lucentTrunkInfo = LucentTrunkInfoList.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		LucentTrunkInfoList.encode(lucentTrunkInfo, memberStream);
	}

	public CSTATrunkInfo[] getLucentTrunkInfo() {
		return lucentTrunkInfo;
	}

	@Override
	public int getPDU() {
		return 107;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("LucentV6ConferencedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentOriginalCallInfo.print(originalCallInfo,
				"originalCallInfo", indent));

		lines.addAll(CSTAExtendedDeviceID.print(distributingDevice_asn,
				"distributingDevice", indent));

		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));
		lines.addAll(LucentTrunkInfoList.print(lucentTrunkInfo,
				"lucentTrunkInfo", indent));

		lines.add("}");
		return lines;
	}

	public void setLucentTrunkInfo(CSTATrunkInfo[] _trunkList) {
		lucentTrunkInfo = _trunkList;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV6ConferencedEvent JD-Core Version: 0.5.4
 */