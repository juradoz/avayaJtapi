package javax.telephony.callcenter.events;

import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.RouteAddress;

public abstract interface RouteEvent extends RouteSessionEvent {
	public static final int SELECT_NORMAL = 0;
	public static final int SELECT_LEAST_COST = 1;
	public static final int SELECT_EMERGENCY = 2;
	public static final int SELECT_ACD = 3;
	public static final int SELECT_USER_DEFINED = 4;

	public abstract Address getCallingAddress();

	public abstract Terminal getCallingTerminal();

	public abstract RouteAddress getCurrentRouteAddress();

	public abstract int getRouteSelectAlgorithm();

	public abstract String getSetupInformation();
}

