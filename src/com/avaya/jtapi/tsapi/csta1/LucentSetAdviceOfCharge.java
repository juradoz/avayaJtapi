package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;

public class LucentSetAdviceOfCharge extends LucentPrivateData {
	boolean featureFlag;
	static final int PDU = 99;

	public LucentSetAdviceOfCharge(boolean _featureFlag) {
		featureFlag = _featureFlag;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNBoolean.encode(featureFlag, memberStream);
	}

	@Override
	public int getPDU() {
		return 99;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSetAdviceOfCharge ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean.print(featureFlag, "featureFlag", indent));

		lines.add("}");
		return lines;
	}
}

