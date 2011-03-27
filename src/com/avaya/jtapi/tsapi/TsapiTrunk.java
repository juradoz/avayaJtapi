package com.avaya.jtapi.tsapi;

import javax.telephony.Call;
import javax.telephony.Connection;
import javax.telephony.callcenter.CallCenterTrunk;

public abstract class TsapiTrunk implements CallCenterTrunk {
	public static String makeTrunkName(final String groupName,
			final String memberName) {
		if (groupName == null)
			return null;
		if (memberName == null)
			return groupName + ":0";
		return groupName + ":" + memberName;
	}

	@Override
	public abstract boolean equals(Object paramObject);

	@Override
	public abstract Call getCall();

	public abstract Connection getConnection();

	public abstract String getGroupName();

	public abstract String getMemberName();

	@Override
	public abstract String getName();

	@Override
	public abstract int getState();

	@Override
	public abstract int getType();

	@Override
	public abstract int hashCode();
}
