package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class ACSAuthInfo extends ASNSequence {
	static ACSAuthInfo decode(InputStream in) {
		ACSAuthInfo _this = new ACSAuthInfo();
		_this.doDecode(in);

		return _this;
	}

	static Collection<String> print(ACSAuthInfo _this, String name,
			String _indent) {
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

		lines.addAll(ACSAuthType.print(_this.authType, "authType", indent));
		lines.addAll(ASNIA5String.print(_this.authLoginID, "authLoginID",
				indent));

		lines.add(_indent + "}");
		return lines;
	}

	short authType;

	String authLoginID;

	ACSAuthInfo() {
	}

	public ACSAuthInfo(short _authType, String _authLoginID) {
		authType = _authType;
		authLoginID = _authLoginID;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		authType = ASNEnumerated.decode(memberStream);
		authLoginID = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(authType, memberStream);
		ASNIA5String.encode(authLoginID, memberStream);
	}
}

