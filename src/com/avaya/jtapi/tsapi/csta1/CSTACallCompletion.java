package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class CSTACallCompletion extends CSTARequest {
	short feature;
	CSTAConnectionID call;
	static final int PDU = 5;

	public CSTACallCompletion(final short _feature, final CSTAConnectionID _call) {
		feature = _feature;
		call = _call;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(feature, memberStream);
		CSTAConnectionID.encode(call, memberStream);
	}

	public CSTAConnectionID getCall() {
		return call;
	}

	public short getFeature() {
		return feature;
	}

	@Override
	public int getPDU() {
		return 5;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTACallCompletion ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(Feature.print(feature, "feature", indent));
		lines.addAll(CSTAConnectionID.print(call, "call", indent));

		lines.add("}");
		return lines;
	}
}
