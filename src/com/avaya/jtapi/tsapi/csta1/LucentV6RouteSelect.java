package com.avaya.jtapi.tsapi.csta1;

import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentV6RouteSelect extends LucentRouteSelect {
	static final int PDU = 116;

	public LucentV6RouteSelect(final String _callingDevice,
			final String _directAgentCallSplit, final boolean _priorityCalling,
			final String _destRoute, final LucentUserCollectCode _collectCode,
			final LucentUserProvidedCode _userProvidedCode,
			final LucentUserToUserInfo _userInfo) {
		super(_callingDevice, _directAgentCallSplit, _priorityCalling,
				_destRoute, _collectCode, _userProvidedCode, _userInfo);
	}

	@Override
	public int getPDU() {
		return 116;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6RouteSelect ::=");
		lines.add("{");

		final String indent = "  ";

		lines
				.addAll(ASNIA5String.print(callingDevice, "callingDevice",
						indent));
		lines.addAll(ASNIA5String.print(directAgentCallSplit,
				"directAgentCallSplit", indent));
		lines.addAll(ASNBoolean.print(priorityCalling, "priorityCalling",
				indent));
		lines.addAll(ASNIA5String.print(destRoute, "destRoute", indent));
		lines.addAll(LucentUserCollectCode.print(collectCode, "collectCode",
				indent));
		lines.addAll(LucentUserProvidedCode.print(userProvidedCode,
				"userProvidedCode", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
