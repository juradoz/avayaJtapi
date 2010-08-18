package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class CSTACallCompletion extends CSTARequest {
	short feature;
	CSTAConnectionID call;
	static final int PDU = 5;

	public CSTACallCompletion(short _feature, CSTAConnectionID _call) {
		feature = _feature;
		call = _call;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTACallCompletion ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(Feature.print(feature, "feature", indent));
		lines.addAll(CSTAConnectionID.print(call, "call", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTACallCompletion JD-Core Version: 0.5.4
 */