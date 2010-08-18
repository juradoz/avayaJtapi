package com.avaya.jtapi.tsapi;

import javax.telephony.Connection;
import javax.telephony.callcenter.ACDAddress;

public abstract interface LucentCall extends ITsapiCall, LucentCallInfo {
	public abstract Connection[] connect(LucentTerminal paramLucentTerminal,
			LucentAddress paramLucentAddress, String paramString,
			boolean paramBoolean, UserToUserInfo paramUserToUserInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException;

	public abstract Connection[] connectDirectAgent(
			LucentTerminal paramLucentTerminal,
			LucentAddress paramLucentAddress, LucentAgent paramLucentAgent,
			boolean paramBoolean, UserToUserInfo paramUserToUserInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException;

	public abstract Connection[] connectPredictive(
			LucentTerminal paramLucentTerminal,
			LucentAddress paramLucentAddress, String paramString,
			int paramInt1, int paramInt2, int paramInt3, int paramInt4,
			boolean paramBoolean, UserToUserInfo paramUserToUserInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException;

	public abstract Connection[] connectSupervisorAssist(
			LucentAgent paramLucentAgent, String paramString,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException;

	public abstract Connection[] consult(
			LucentTerminalConnection paramLucentTerminalConnection,
			String paramString, boolean paramBoolean,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException;

	public abstract Connection[] consultDirectAgent(
			LucentTerminalConnection paramLucentTerminalConnection,
			LucentAgent paramLucentAgent, boolean paramBoolean,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException;

	public abstract Connection[] consultSupervisorAssist(
			LucentTerminalConnection paramLucentTerminalConnection,
			ACDAddress paramACDAddress, String paramString,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException;
}

