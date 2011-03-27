package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public class LucentV5ConferencedEvent extends LucentConferencedEvent implements
		HasUCID {
	String ucid;
	static final int PDU = 78;

	public static LucentConferencedEvent decode(final InputStream in) {
		final LucentV5ConferencedEvent _this = new LucentV5ConferencedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		ucid = ASNIA5String.decode(memberStream);
	}

	@Override
	public LucentOriginalCallInfo decodeOCI(final InputStream memberStream) {
		return LucentV5OriginalCallInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNIA5String.encode(ucid, memberStream);
	}

	@Override
	public void encodeOCI(final LucentOriginalCallInfo callInfo,
			final OutputStream memberStream) {
		ASNSequence.encode(callInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 78;
	}

	@Override
	public String getUcid() {
		return ucid;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5ConferencedEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentV5OriginalCallInfo.print(
				(LucentV5OriginalCallInfo) originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(distributingDevice_asn,
				"distributingDevice", indent));
		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));

		lines.add("}");
		return lines;
	}

	public void setUcid(final String ucid) {
		this.ucid = ucid;
	}
}
