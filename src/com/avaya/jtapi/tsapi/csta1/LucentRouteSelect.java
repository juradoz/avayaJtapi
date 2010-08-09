package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentRouteSelect extends LucentPrivateData {
	String callingDevice;
	String directAgentCallSplit;
	boolean priorityCalling;
	String destRoute;
	LucentUserCollectCode collectCode;
	LucentUserProvidedCode userProvidedCode;
	LucentUserToUserInfo userInfo;
	static final int PDU = 43;

	public LucentRouteSelect() {
	}

	public LucentRouteSelect(String _callingDevice,
			String _directAgentCallSplit, boolean _priorityCalling,
			String _destRoute, LucentUserCollectCode _collectCode,
			LucentUserProvidedCode _userProvidedCode,
			LucentUserToUserInfo _userInfo) {
		callingDevice = _callingDevice;
		directAgentCallSplit = _directAgentCallSplit;
		priorityCalling = _priorityCalling;
		destRoute = _destRoute;
		collectCode = _collectCode;
		userProvidedCode = _userProvidedCode;
		userInfo = _userInfo;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(callingDevice, memberStream);
		ASNIA5String.encode(directAgentCallSplit, memberStream);
		ASNBoolean.encode(priorityCalling, memberStream);
		ASNIA5String.encode(destRoute, memberStream);
		LucentUserCollectCode.encode(collectCode, memberStream);
		LucentUserProvidedCode.encode(userProvidedCode, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 43;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentRouteSelect ::=");
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
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentRouteSelect JD-Core Version: 0.5.4
 */