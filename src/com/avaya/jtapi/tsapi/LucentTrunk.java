package com.avaya.jtapi.tsapi;

import javax.telephony.Connection;

public abstract interface LucentTrunk extends ITsapiTrunk {
	public abstract Connection getConnection();

	public abstract String getGroupName();

	public abstract String getMemberName();
}
