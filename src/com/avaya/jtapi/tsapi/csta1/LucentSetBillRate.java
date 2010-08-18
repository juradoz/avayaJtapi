package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNReal;

public class LucentSetBillRate extends LucentPrivateData {
	CSTAConnectionID call;
	short billType;
	float billRate;
	static final int PDU = 74;

	public LucentSetBillRate(CSTAConnectionID _call, short _billType,
			float _billRate) {
		call = _call;
		billType = _billType;
		billRate = _billRate;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTAConnectionID.encode(call, memberStream);
		ASNEnumerated.encode(billType, memberStream);
		ASNReal.encode(billRate, memberStream);
	}

	@Override
	public int getPDU() {
		return 74;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSetBillRate ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAConnectionID.print(call, "call", indent));
		lines.addAll(BillType.print(billType, "billType", indent));
		lines.addAll(ASNReal.print(billRate, "billRate", indent));

		lines.add("}");
		return lines;
	}
}

