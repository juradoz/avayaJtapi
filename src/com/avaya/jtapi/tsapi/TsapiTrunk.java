package com.avaya.jtapi.tsapi;

import javax.telephony.Call;
import javax.telephony.Connection;
import javax.telephony.callcenter.CallCenterTrunk;

public abstract class TsapiTrunk implements CallCenterTrunk {
	public static String makeTrunkName(String groupName, String memberName) {
		if (groupName == null) {
			return null;
		}
		if (memberName == null) {
			return groupName + ":0";
		}
		return groupName + ":" + memberName;
	}

	@Override
	public abstract boolean equals(Object paramObject);

	public abstract Call getCall();

	public abstract Connection getConnection();

	public abstract String getGroupName();

	public abstract String getMemberName();

	public abstract String getName();

	public abstract int getState();

	public abstract int getType();

	@Override
	public abstract int hashCode();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.TsapiTrunk JD-Core Version: 0.5.4
 */