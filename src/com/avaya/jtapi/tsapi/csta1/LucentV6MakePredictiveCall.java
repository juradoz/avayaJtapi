package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentV6MakePredictiveCall extends LucentMakePredictiveCall {
	public static final int PDU = 112;

	public static LucentMakePredictiveCall decode(final InputStream in) {
		final LucentV6MakePredictiveCall _this = new LucentV6MakePredictiveCall();
		_this.doDecode(in);

		return _this;
	}

	public LucentV6MakePredictiveCall() {
	}

	public LucentV6MakePredictiveCall(final boolean _priorityCalling,
			final int _maxRings, final short _answerTreat,
			final String _destRoute, final LucentUserToUserInfo _userInfo) {
		super(_priorityCalling, _maxRings, _answerTreat, _destRoute, _userInfo);
	}

	@Override
	public int getPDU() {
		return 112;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6MakePredictiveCall ::=");
		lines.add("{");

		final String indent = "  ";

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
