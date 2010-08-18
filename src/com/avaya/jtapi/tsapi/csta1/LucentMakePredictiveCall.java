package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentMakePredictiveCall extends LucentPrivateData {
	public static LucentMakePredictiveCall decode(InputStream in) {
		LucentMakePredictiveCall _this = new LucentMakePredictiveCall();
		_this.doDecode(in);

		return _this;
	}

	boolean priorityCalling;
	int maxRings;
	short answerTreat;
	String destRoute;
	LucentUserToUserInfo userInfo;

	static final int PDU = 5;

	public LucentMakePredictiveCall() {
	}

	public LucentMakePredictiveCall(boolean _priorityCalling, int _maxRings,
			short _answerTreat, String _destRoute,
			LucentUserToUserInfo _userInfo) {
		priorityCalling = _priorityCalling;
		maxRings = _maxRings;
		answerTreat = _answerTreat;
		destRoute = _destRoute;
		userInfo = _userInfo;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		priorityCalling = ASNBoolean.decode(memberStream);
		maxRings = ASNInteger.decode(memberStream);
		answerTreat = ASNEnumerated.decode(memberStream);
		destRoute = ASNIA5String.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNBoolean.encode(priorityCalling, memberStream);
		ASNInteger.encode(maxRings, memberStream);
		ASNEnumerated.encode(answerTreat, memberStream);
		ASNIA5String.encode(destRoute, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	public short getAnswerTreat() {
		return answerTreat;
	}

	@Override
	public int getPDU() {
		return 5;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMakePredictiveCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNBoolean.print(priorityCalling, "priorityCalling",
				indent));
		lines.addAll(ASNInteger.print(maxRings, "maxRings", indent));
		lines.addAll(LucentAnswerTreat
				.print(answerTreat, "answerTreat", indent));
		lines.addAll(ASNIA5String.print(destRoute, "destRoute", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}

