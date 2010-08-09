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

	public static LucentConferencedEvent decode(InputStream in) {
		LucentV5ConferencedEvent _this = new LucentV5ConferencedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		ucid = ASNIA5String.decode(memberStream);
	}

	@Override
	public LucentOriginalCallInfo decodeOCI(InputStream memberStream) {
		return LucentV5OriginalCallInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNIA5String.encode(ucid, memberStream);
	}

	@Override
	public void encodeOCI(LucentOriginalCallInfo callInfo,
			OutputStream memberStream) {
		ASNSequence.encode(callInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 78;
	}

	public String getUcid() {
		return ucid;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentV5ConferencedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentV5OriginalCallInfo.print(
				(LucentV5OriginalCallInfo) originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(distributingDevice_asn,
				"distributingDevice", indent));
		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));

		lines.add("}");
		return lines;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV5ConferencedEvent JD-Core Version: 0.5.4
 */