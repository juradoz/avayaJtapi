package com.avaya.jtapi.tsapi.impl.events.route;

import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.RouteAddress;
import javax.telephony.callcenter.RouteSession;
import javax.telephony.callcenter.events.RouteEvent;

public final class TsapiRouteEvent extends TsapiRouteSessionEvent implements
		RouteEvent {
	private final RouteAddress currentRouteAddress;
	private final Address callingAddress;
	private final Terminal callingTerminal;
	private final int routeSelectAlgorithm;
	private final String isdnSetupMessage;

	public TsapiRouteEvent(final RouteSession routeSession,
			final RouteAddress currentRouteAddress,
			final Address callingAddress, final Terminal callingTerminal,
			final int routeSelectAlgorithm, final String isdnSetupMessage) {
		super(routeSession);
		this.currentRouteAddress = currentRouteAddress;
		this.callingAddress = callingAddress;
		this.callingTerminal = callingTerminal;
		this.routeSelectAlgorithm = routeSelectAlgorithm;
		this.isdnSetupMessage = isdnSetupMessage;
	}

	public Address getCallingAddress() {
		return callingAddress;
	}

	public Terminal getCallingTerminal() {
		return callingTerminal;
	}

	public RouteAddress getCurrentRouteAddress() {
		return currentRouteAddress;
	}

	public int getRouteSelectAlgorithm() {
		return routeSelectAlgorithm;
	}

	public String getSetupInformation() {
		return isdnSetupMessage;
	}
}
