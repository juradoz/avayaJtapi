package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public class LucentLinkStatusEvent extends LucentPrivateData {
	private CSTALinkStatus linkStatus;
	static final int PDU = 73;

	public static LucentLinkStatusEvent decode(final InputStream in) {
		final LucentLinkStatusEvent _this = new LucentLinkStatusEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		linkStatus = CSTALinkStatus.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNSequence.encode(linkStatus, memberStream);
	}

	public CSTALinkStatus getLinkStatus() {
		return linkStatus;
	}

	@Override
	public int getPDU() {
		return 73;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentLinkStatusEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.addAll(CSTALinkStatus.print(linkStatus, "linkStatus", indent));

		lines.add("}");
		return lines;
	}

	public void setLinkStatus(final CSTALinkStatus linkStatus) {
		this.linkStatus = linkStatus;
	}
}
