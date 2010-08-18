package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentTrunkGroupInfo extends LucentPrivateData {
	public int idleTrunks;
	public int usedTrunks;
	static final int PDU = 27;

	static LucentTrunkGroupInfo decode(final InputStream in) {
		final LucentTrunkGroupInfo _this = new LucentTrunkGroupInfo();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		idleTrunks = ASNInteger.decode(memberStream);
		usedTrunks = ASNInteger.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 27;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("TrunkGroupInfo ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(idleTrunks, "idleTrunks", indent));
		lines.addAll(ASNInteger.print(usedTrunks, "usedTrunks", indent));

		lines.add("}");
		return lines;
	}

	public void setIdleTrunks(final int idleTrunks) {
		this.idleTrunks = idleTrunks;
	}

	public void setUsedTrunks(final int usedTrunks) {
		this.usedTrunks = usedTrunks;
	}
}
