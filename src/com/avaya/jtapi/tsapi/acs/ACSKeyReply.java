package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSKeyReply extends ACSConfirmation {
	int objectID;
	byte[] key;
	public static final int PDU = 9;

	public static ACSKeyReply decode(InputStream in) {
		ACSKeyReply _this = new ACSKeyReply();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.objectID = ASNInteger.decode(memberStream);
		this.key = ChallengeKey.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSKeyReply ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(this.objectID, "objectID", indent));
		lines.addAll(ChallengeKey.print(this.key, "key", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 9;
	}

	public byte[] getKey() {
		return this.key;
	}

	public int getObjectID() {
		return this.objectID;
	}
}