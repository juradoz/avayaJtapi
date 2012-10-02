package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSAuthReply extends ACSConfirmation {
	int objectID;
	byte[] key;
	ACSAuthInfo authInfo;
	public static final int PDU = 12;

	public ACSAuthInfo getAuthInfo() {
		return this.authInfo;
	}

	public byte[] getKey() {
		return this.key;
	}

	public int getObjectID() {
		return this.objectID;
	}

	public static ACSAuthReply decode(InputStream in) {
		ACSAuthReply _this = new ACSAuthReply();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.objectID = ASNInteger.decode(memberStream);
		this.key = ChallengeKey.decode(memberStream);
		this.authInfo = ACSAuthInfo.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSAuthReply ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(this.objectID, "objectID", indent));
		lines.addAll(ChallengeKey.print(this.key, "key", indent));
		lines.addAll(ACSAuthInfo.print(this.authInfo, "authInfo", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 12;
	}
}