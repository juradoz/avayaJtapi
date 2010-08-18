package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNOctetString;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class ACSAuthReplyTwo extends ACSConfirmation {
	public static ACSAuthReplyTwo decode(InputStream in) {
		ACSAuthReplyTwo _this = new ACSAuthReplyTwo();
		_this.doDecode(in);

		return _this;
	}

	int objectID;
	byte[] key;
	ACSAuthInfo authInfo;
	short encodeType;
	String pipe;

	public static final int PDU = 13;

	public ACSAuthReplyTwo() {
	}

	public ACSAuthReplyTwo(int _objectID, byte[] _key, ACSAuthInfo _authInfo,
			short _encodeType, String _pipe) {
		objectID = _objectID;
		key = _key;
		authInfo = _authInfo;
		encodeType = _encodeType;
		pipe = _pipe;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		objectID = ASNInteger.decode(memberStream);
		key = ASNOctetString.decode(memberStream);
		authInfo = ACSAuthInfo.decode(memberStream);
		encodeType = ASNEnumerated.decode(memberStream);
		pipe = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(objectID, memberStream);
		ASNOctetString.encode(key, memberStream);
		ASNSequence.encode(authInfo, memberStream);
		ASNEnumerated.encode(encodeType, memberStream);
		ASNIA5String.encode(pipe, memberStream);
	}

	public ACSAuthInfo getAuthInfo() {
		return authInfo;
	}

	public short getEncodeType() {
		return encodeType;
	}

	public byte[] getKey() {
		return key;
	}

	public int getObjectID() {
		return objectID;
	}

	@Override
	public int getPDU() {
		return 13;
	}

	public String getPipe() {
		return pipe;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("ACSAuthReplyTwo ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(objectID, "objectID", indent));
		lines.addAll(ASNOctetString.print(key, "key", indent));
		lines.addAll(ACSAuthInfo.print(authInfo, "authInfo", indent));
		lines.addAll(ACSEncodeType.print(encodeType, "encodeType", indent));
		lines.addAll(ASNIA5String.print(pipe, "pipe", indent));

		lines.add("}");
		return lines;
	}
}

