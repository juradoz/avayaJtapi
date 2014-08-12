package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.Q931UserToUserInfo;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.csta1.LucentQ931UserToUserInfo;
import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;

class JtapiUserToUserInfoFactory {
	static UserToUserInfo createUserToUserInfo(LucentUserToUserInfo csta_obj) {
		if (csta_obj == null) {
			return null;
		}

		if ((csta_obj instanceof LucentQ931UserToUserInfo)) {
			return new Q931UserToUserInfo(csta_obj.getBytes());
		}

		return new UserToUserInfo(csta_obj.getBytes(), csta_obj.getType());
	}

	static LucentUserToUserInfo createUserToUserInfo(UserToUserInfo uui) {
		if (uui == null) {
			return null;
		}
		if ((uui instanceof Q931UserToUserInfo)) {
			return new LucentQ931UserToUserInfo(uui.getBytes());
		}

		return new LucentUserToUserInfo(uui.getBytes(), uui.getType());
	}
}