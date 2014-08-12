package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentMakeCall extends LucentPrivateData {
	String destRoute;
	boolean priorityCalling;
	LucentUserToUserInfo userInfo;
	static final int PDU = 3;

	public LucentMakeCall() {
	}

	public LucentMakeCall(String _destRoute, boolean _priorityCalling,
			LucentUserToUserInfo _userInfo) {
		this.destRoute = _destRoute;
		this.priorityCalling = _priorityCalling;
		this.userInfo = _userInfo;
	}

	public static LucentMakeCall decode(InputStream in) {
		LucentMakeCall _this = new LucentMakeCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.destRoute = DeviceID.decode(memberStream);
		this.priorityCalling = ASNBoolean.decode(memberStream);
		this.userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.destRoute, memberStream);
		ASNBoolean.encode(this.priorityCalling, memberStream);
		LucentUserToUserInfo.encode(this.userInfo, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMakeCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
		lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling",
				indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 3;
	}
}