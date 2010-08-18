package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentV6TransferredEvent extends LucentV5TransferredEvent
		implements LucentTrunkConnectionMapping {
	CSTATrunkInfo[] lucentTrunkInfo;
	static final int PDU = 106;

	public static LucentTransferredEvent decode(final InputStream in) {
		final LucentV6TransferredEvent _this = new LucentV6TransferredEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		lucentTrunkInfo = LucentTrunkInfoList.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		super.encodeMembers(memberStream);
		LucentTrunkInfoList.encode(lucentTrunkInfo, memberStream);
	}

	public CSTATrunkInfo[] getLucentTrunkInfo() {
		return lucentTrunkInfo;
	}

	@Override
	public int getPDU() {
		return 106;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("LucentV6TransferredEvent ::=");
		lines.add("{");

		final String indent = "  ";

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

	public void setLucentTrunkInfo(final CSTATrunkInfo[] _trunkList) {
		lucentTrunkInfo = _trunkList;
	}
}
