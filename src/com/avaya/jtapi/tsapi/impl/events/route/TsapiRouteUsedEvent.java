package com.avaya.jtapi.tsapi.impl.events.route;

import javax.telephony.Address;
import javax.telephony.Terminal;
import javax.telephony.callcenter.RouteSession;

import com.avaya.jtapi.tsapi.ITsapiRouteUsedEvent;
import com.avaya.jtapi.tsapi.TsapiPlatformException;

public class TsapiRouteUsedEvent extends TsapiRouteSessionEvent implements
		ITsapiRouteUsedEvent {
	private final Address routeUsedAddress;
	private final Terminal routeUsedTerminal;
	private final Terminal callingTerminal;
	private final Address callingAddress;
	private boolean outOfDomain;

	public TsapiRouteUsedEvent(final RouteSession routeSession,
			final Address routeUsedAddress, final Terminal routeUsedTerminal,
			final Address callingAddress, final Terminal callingTerminal,
			final boolean outOfDomain) {
		super(routeSession);
		this.routeUsedAddress = routeUsedAddress;
		this.routeUsedTerminal = routeUsedTerminal;
		this.callingAddress = callingAddress;
		this.callingTerminal = callingTerminal;
	}

	@Override
	public Address getCallingAddress() {
		return callingAddress;
	}

	@Override
	public Terminal getCallingTerminal() {
		return callingTerminal;
	}

	@Override
	public boolean getDomain() {
		return outOfDomain;
	}

	@Override
	public Terminal getRouteUsed() {
		if (routeUsedTerminal == null)
			throw new TsapiPlatformException(3, 0,
					"Could not return a Terminal for RouteUsed ("
							+ routeUsedAddress.getName()
							+ ") - was probably off-switch");

		return routeUsedTerminal;
	}

	public Address getRouteUsedAddress() {
		return routeUsedAddress;
	}
}
