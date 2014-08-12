package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV6RouteSelect extends LucentRouteSelect {
	static final int PDU = 116;

	public LucentV6RouteSelect(String _callingDevice,
			String _directAgentCallSplit, boolean _priorityCalling,
			String _destRoute, LucentUserCollectCode _collectCode,
			LucentUserProvidedCode _userProvidedCode,
			LucentUserToUserInfo _userInfo) {
		super(_callingDevice, _directAgentCallSplit, _priorityCalling,
				_destRoute, _collectCode, _userProvidedCode, _userInfo);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6RouteSelect ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID
				.print(this.callingDevice, "callingDevice", indent));
		lines.addAll(DeviceID.print(this.directAgentCallSplit,
				"directAgentCallSplit", indent));
		lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling",
				indent));
		lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
		lines.addAll(LucentUserCollectCode.print(this.collectCode,
				"collectCode", indent));
		lines.addAll(LucentUserProvidedCode.print(this.userProvidedCode,
				"userProvidedCode", indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 116;
	}
}