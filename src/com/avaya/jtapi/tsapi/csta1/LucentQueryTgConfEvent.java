package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentQueryTgConfEvent extends LucentPrivateData {
	int idleTrunks;
	int usedTrunks;
	static final int PDU = 27;

	static LucentQueryTgConfEvent decode(InputStream in) {
		LucentQueryTgConfEvent _this = new LucentQueryTgConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		idleTrunks = ASNInteger.decode(memberStream);
		usedTrunks = ASNInteger.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 27;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentQueryTgConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(idleTrunks, "idleTrunks", indent));
		lines.addAll(ASNInteger.print(usedTrunks, "usedTrunks", indent));

		lines.add("}");
		return lines;
	}

	public void setIdleTrunks(int idleTrunks) {
		this.idleTrunks = idleTrunks;
	}

	public void setUsedTrunks(int usedTrunks) {
		this.usedTrunks = usedTrunks;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQueryTgConfEvent JD-Core Version: 0.5.4
 */