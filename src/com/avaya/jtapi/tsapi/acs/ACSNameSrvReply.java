package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class ACSNameSrvReply extends ACSConfirmation {
	public static ACSNameSrvReply decode(final InputStream in) {
		final ACSNameSrvReply _this = new ACSNameSrvReply();
		_this.doDecode(in);

		return _this;
	}

	boolean more;
	ACSNameAddr[] list;

	public static final int PDU = 11;

	public ACSNameSrvReply() {
	}

	public ACSNameSrvReply(final boolean _more, final ACSNameAddr[] _list) {
		more = _more;
		list = _list;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		more = ASNBoolean.decode(memberStream);
		list = ACSNameAddrList.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNBoolean.encode(more, memberStream);
		ACSNameAddrList.encode(list, memberStream);
	}

	public ACSNameAddr[] getList() {
		return list;
	}

	@Override
	public int getPDU() {
		return 11;
	}

	public boolean isMore() {
		return more;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("ACSNameSrvReply ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNBoolean.print(more, "more", indent));
		lines.addAll(ACSNameAddrList.print(list, "list", indent));

		lines.add("}");
		return lines;
	}
}
