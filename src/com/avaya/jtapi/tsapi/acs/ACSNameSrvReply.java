package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public final class ACSNameSrvReply extends ACSConfirmation {
	public static ACSNameSrvReply decode(InputStream in) {
		ACSNameSrvReply _this = new ACSNameSrvReply();
		_this.doDecode(in);

		return _this;
	}

	boolean more;
	ACSNameAddr[] list;

	public static final int PDU = 11;

	public ACSNameSrvReply() {
	}

	public ACSNameSrvReply(boolean _more, ACSNameAddr[] _list) {
		more = _more;
		list = _list;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		more = ASNBoolean.decode(memberStream);
		list = ACSNameAddrList.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();
		lines.add("ACSNameSrvReply ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean.print(more, "more", indent));
		lines.addAll(ACSNameAddrList.print(list, "list", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSNameSrvReply JD-Core Version: 0.5.4
 */