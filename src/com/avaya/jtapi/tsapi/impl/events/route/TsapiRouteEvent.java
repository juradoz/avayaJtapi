package com.avaya.jtapi.tsapi.impl.events.route;

import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.RouteAddress;
import javax.telephony.callcenter.RouteSession;
import javax.telephony.callcenter.events.RouteEvent;

public final class TsapiRouteEvent extends TsapiRouteSessionEvent implements
		RouteEvent {
	private RouteAddress currentRouteAddress;
	private Address callingAddress;
	private Terminal callingTerminal;
	private int routeSelectAlgorithm;
	private String isdnSetupMessage;

	public RouteAddress getCurrentRouteAddress() {
		return this.currentRouteAddress;
	}

	public Address getCallingAddress() {
		return this.callingAddress;
	}

	public Terminal getCallingTerminal() {
		return this.callingTerminal;
	}

	public int getRouteSelectAlgorithm() {
		return this.routeSelectAlgorithm;
	}

	public String getSetupInformation() {
		return this.isdnSetupMessage;
	}

	public TsapiRouteEvent(RouteSession routeSession,
			RouteAddress currentRouteAddress, Address callingAddress,
			Terminal callingTerminal, int routeSelectAlgorithm,
			String isdnSetupMessage) {
		super(routeSession);
		this.currentRouteAddress = currentRouteAddress;
		this.callingAddress = callingAddress;
		this.callingTerminal = callingTerminal;
		this.routeSelectAlgorithm = routeSelectAlgorithm;
		this.isdnSetupMessage = isdnSetupMessage;
	}
}