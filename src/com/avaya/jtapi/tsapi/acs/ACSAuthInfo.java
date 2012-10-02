package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSAuthInfo extends ASNSequence {
	short authType;
	String authLoginID;

	ACSAuthInfo() {
	}

	public ACSAuthInfo(short _authType, String _authLoginID) {
		this.authType = _authType;
		this.authLoginID = _authLoginID;
	}

	static ACSAuthInfo decode(InputStream in) {
		ACSAuthInfo _this = new ACSAuthInfo();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.authType = ACSAuthType.decode(memberStream);
		this.authLoginID = LoginID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		ACSAuthType.encode(this.authType, memberStream);
		LoginID.encode(this.authLoginID, memberStream);
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
		lines.addAll(LoginID.print(_this.authLoginID, "authLoginID", indent));

		lines.add(_indent + "}");
		return lines;
	}
}