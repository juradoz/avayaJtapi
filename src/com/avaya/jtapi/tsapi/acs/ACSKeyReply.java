package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

public final class ACSKeyReply extends ACSConfirmation {
	int objectID;
	byte[] key;
	public static final int PDU = 9;

	public static ACSKeyReply decode(InputStream in) {
		ACSKeyReply _this = new ACSKeyReply();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		objectID = ASNInteger.decode(memberStream);
		key = ASNOctetString.decode(memberStream);
	}

	public byte[] getKey() {
		return key;
	}

	public int getObjectID() {
		return objectID;
	}

	@Override
	public int getPDU() {
		return 9;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSKeyReply ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(objectID, "objectID", indent));
		lines.addAll(ASNOctetString.print(key, "key", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSKeyReply JD-Core Version: 0.5.4
 */