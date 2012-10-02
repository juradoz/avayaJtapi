package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNReal;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentSetBillRate extends LucentPrivateData {
	CSTAConnectionID call;
	short billType;
	float billRate;
	static final int PDU = 74;

	public LucentSetBillRate(CSTAConnectionID _call, short _billType,
			float _billRate) {
		this.call = _call;
		this.billType = _billType;
		this.billRate = _billRate;
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(this.call, memberStream);
		BillType.encode(this.billType, memberStream);
		ASNReal.encode(this.billRate, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSetBillRate ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
		lines.addAll(BillType.print(this.billType, "billType", indent));
		lines.addAll(ASNReal.print(this.billRate, "billRate", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 74;
	}
}