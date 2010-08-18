package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

public final class ACSAuthReply extends ACSConfirmation {
	public static ACSAuthReply decode(InputStream in) {
		ACSAuthReply _this = new ACSAuthReply();
		_this.doDecode(in);

		return _this;
	}

	int objectID;
	byte[] key;
	ACSAuthInfo authInfo;

	public static final int PDU = 12;

	@Override
	public void decodeMembers(InputStream memberStream) {
		objectID = ASNInteger.decode(memberStream);
		key = ASNOctetString.decode(memberStream);
		authInfo = ACSAuthInfo.decode(memberStream);
	}

	public ACSAuthInfo getAuthInfo() {
		return authInfo;
	}

	public byte[] getKey() {
		return key;
	}

	public int getObjectID() {
		return objectID;
	}

	@Override
	public int getPDU() {
		return 12;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSAuthReply ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(objectID, "objectID", indent));
		lines.addAll(ASNOctetString.print(key, "key", indent));
		lines.addAll(ACSAuthInfo.print(authInfo, "authInfo", indent));

		lines.add("}");
		return lines;
	}
}

