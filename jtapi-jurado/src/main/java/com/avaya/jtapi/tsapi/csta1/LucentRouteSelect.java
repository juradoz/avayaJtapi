package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

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
		this.callingDevice = _callingDevice;
		this.directAgentCallSplit = _directAgentCallSplit;
		this.priorityCalling = _priorityCalling;
		this.destRoute = _destRoute;
		this.collectCode = _collectCode;
		this.userProvidedCode = _userProvidedCode;
		this.userInfo = _userInfo;
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.callingDevice, memberStream);
		DeviceID.encode(this.directAgentCallSplit, memberStream);
		ASNBoolean.encode(this.priorityCalling, memberStream);
		DeviceID.encode(this.destRoute, memberStream);
		LucentUserCollectCode.encode(this.collectCode, memberStream);
		LucentUserProvidedCode.encode(this.userProvidedCode, memberStream);
		LucentUserToUserInfo.encode(this.userInfo, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentRouteSelect ::=");
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
		return 43;
	}
}