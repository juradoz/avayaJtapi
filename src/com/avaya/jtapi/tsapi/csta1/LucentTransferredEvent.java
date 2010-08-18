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

	public static LucentTransferredEvent decode(final InputStream in) {
		final LucentTransferredEvent _this = new LucentTransferredEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		originalCallInfo = decodeOCI(memberStream);
		distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentTransferredEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentOriginalCallInfo.print(originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(distributingDevice_asn,
				"distributingDevice", indent));

		lines.add("}");
		return lines;
	}

	public void setDistributingDevice_asn(
			final CSTAExtendedDeviceID _distributingDevice_asn) {
		distributingDevice_asn = _distributingDevice_asn;
	}

	public void setOriginalCallInfo(final LucentOriginalCallInfo _info) {
		originalCallInfo = _info;
	}
}
