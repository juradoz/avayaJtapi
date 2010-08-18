package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAForwardingInfo extends ASNSequence {
	public static CSTAForwardingInfo decode(InputStream in) {
		CSTAForwardingInfo _this = new CSTAForwardingInfo();
		_this.doDecode(in);

		return _this;
	}

	public static Collection<String> print(CSTAForwardingInfo _this,
			String name, String _indent) {
		Collection<String> lines = new ArrayList<String>();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		lines.addAll(ForwardingType.print(_this.forwardingType,
				"forwardingType", indent));
		lines.addAll(ASNBoolean.print(_this.forwardingOn, "forwardingOn",
				indent));
		lines.addAll(ASNIA5String.print(_this.forwardDN, "forwardDN", indent));

		lines.add(_indent + "}");
		return lines;
	}

	short forwardingType;

	boolean forwardingOn;

	String forwardDN;

	public CSTAForwardingInfo() {
	}

	public CSTAForwardingInfo(short _forwardingType, boolean _forwardingOn,
			String _forwardDN) {
		forwardingType = _forwardingType;
		forwardingOn = _forwardingOn;
		forwardDN = _forwardDN;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		forwardingType = ASNEnumerated.decode(memberStream);
		forwardingOn = ASNBoolean.decode(memberStream);
		forwardDN = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(forwardingType, memberStream);
		ASNBoolean.encode(forwardingOn, memberStream);
		ASNIA5String.encode(forwardDN, memberStream);
	}

	public String getForwardDN() {
		return forwardDN;
	}

	public short getForwardingType() {
		return forwardingType;
	}

	public boolean isForwardingOn() {
		return forwardingOn;
	}
}

