package com.avaya.jtapi.tsapi.impl.events.route;

import com.avaya.jtapi.tsapi.ITsapiRouteUsedEvent;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.RouteSession;

public class TsapiRouteUsedEvent extends TsapiRouteSessionEvent implements
		ITsapiRouteUsedEvent {
	private Address routeUsedAddress;
	private Terminal routeUsedTerminal;
	private Terminal callingTerminal;
	private Address callingAddress;
	private boolean outOfDomain;

	public Terminal getRouteUsed() {
		if (this.routeUsedTerminal == null) {
			throw new TsapiPlatformException(3, 0,
					"Could not return a Terminal for RouteUsed ("
							+ this.routeUsedAddress.getName()
							+ ") - was probably off-switch");
		}

		return this.routeUsedTerminal;
	}

	public Terminal getCallingTerminal() {
		return this.callingTerminal;
	}

	public Address getCallingAddress() {
		return this.callingAddress;
	}

	public boolean getDomain() {
		return this.outOfDomain;
	}

	public Address getRouteUsedAddress() {
		return this.routeUsedAddress;
	}

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
}