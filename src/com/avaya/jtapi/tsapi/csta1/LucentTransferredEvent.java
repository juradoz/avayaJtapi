package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public class LucentTransferredEvent extends LucentPrivateData {
	LucentOriginalCallInfo originalCallInfo;
	CSTAExtendedDeviceID distributingDevice_asn;
	static final int PDU = 62;

	public static LucentTransferredEvent decode(InputStream in) {
		LucentTransferredEvent _this = new LucentTransferredEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		originalCallInfo = decodeOCI(memberStream);
		distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		encodeOCI(originalCallInfo, memberStream);
		ASNSequence.encode(distributingDevice_asn, memberStream);
	}

	public CSTAExtendedDeviceID getDistributingDevice_asn() {
		return distributingDevice_asn;
	}

	public LucentOriginalCallInfo getOriginalCallInfo() {
		return originalCallInfo;
	}

	@Override
	public int getPDU() {
		return 62;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentTransferredEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentOriginalCallInfo.print(originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(distributingDevice_asn,
				"distributingDevice", indent));

		lines.add("}");
		return lines;
	}

	public void setDistributingDevice_asn(
			CSTAExtendedDeviceID _distributingDevice_asn) {
		distributingDevice_asn = _distributingDevice_asn;
	}

	public void setOriginalCallInfo(LucentOriginalCallInfo _info) {
		originalCallInfo = _info;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentTransferredEvent JD-Core Version: 0.5.4
 */