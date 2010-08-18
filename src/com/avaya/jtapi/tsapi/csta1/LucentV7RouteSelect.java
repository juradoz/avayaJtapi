package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV7RouteSelect extends LucentRouteSelect {
	public static LucentV7RouteSelect decode(InputStream in) {
		LucentV7RouteSelect _this = new LucentV7RouteSelect();
		_this.doDecode(in);

		return _this;
	}

	short useNCR;

	public static final int PDU = 126;

	public LucentV7RouteSelect() {
	}

	public LucentV7RouteSelect(String _callingDevice,
			String _directAgentCallSplit, boolean _priorityCalling,
			String _destRoute, LucentUserCollectCode _collectCode,
			LucentUserProvidedCode _userProvidedCode,
			LucentUserToUserInfo _userInfo, short _useNCR) {
		super(_callingDevice, _directAgentCallSplit, _priorityCalling,
				_destRoute, _collectCode, _userProvidedCode, _userInfo);
		useNCR = _useNCR;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		useNCR = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNEnumerated.encode(useNCR, memberStream);
	}

	@Override
	public int getPDU() {
		return 126;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV7RouteSelect ::=");
		lines.add("{");

		String indent = "  ";

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
		lines.addAll(LucentRedirectType.print(useNCR, "useNCR", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));
		lines.add("}");
		return lines;
	}
}

