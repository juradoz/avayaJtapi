package com.avaya.jtapi.tsapi.impl.events.route;

import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.RouteSession;

import com.avaya.jtapi.tsapi.ITsapiRouteUsedEvent;
import com.avaya.jtapi.tsapi.TsapiPlatformException;

public class TsapiRouteUsedEvent extends TsapiRouteSessionEvent implements
		ITsapiRouteUsedEvent {
	private Address routeUsedAddress;
	private Terminal routeUsedTerminal;
	private Terminal callingTerminal;
	private Address callingAddress;
	private boolean outOfDomain;

	public TsapiRouteUsedEvent(RouteSession routeSession,
			Address routeUsedAddress, Terminal routeUsedTerminal,
			Address callingAddress, Terminal callingTerminal,
			boolean outOfDomain) {
		super(routeSession);
		this.routeUsedAddress = routeUsedAddress;
		this.routeUsedTerminal = routeUsedTerminal;
		this.callingAddress = callingAddress;
		this.callingTerminal = callingTerminal;
	}

	public Address getCallingAddress() {
		return callingAddress;
	}

	public Terminal getCallingTerminal() {
		return callingTerminal;
	}

	public boolean getDomain() {
		return outOfDomain;
	}

	public Terminal getRouteUsed() {
		if (routeUsedTerminal == null) {
			throw new TsapiPlatformException(3, 0,
					"Could not return a Terminal for RouteUsed ("
							+ routeUsedAddress.getName()
							+ ") - was probably off-switch");
		}

		return routeUsedTerminal;
	}

	public Address getRouteUsedAddress() {
		return routeUsedAddress;
	}
}

