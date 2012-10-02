package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentTrunkGroupInfo extends LucentPrivateData {
	public int idleTrunks;
	public int usedTrunks;
	static final int PDU = 27;

	static LucentTrunkGroupInfo decode(InputStream in) {
		LucentTrunkGroupInfo _this = new LucentTrunkGroupInfo();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.idleTrunks = ASNInteger.decode(memberStream);
		this.usedTrunks = ASNInteger.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("TrunkGroupInfo ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(this.idleTrunks, "idleTrunks", indent));
		lines.addAll(ASNInteger.print(this.usedTrunks, "usedTrunks", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 27;
	}

	public void setIdleTrunks(int idleTrunks) {
		this.idleTrunks = idleTrunks;
	}

	public void setUsedTrunks(int usedTrunks) {
		this.usedTrunks = usedTrunks;
	}
}