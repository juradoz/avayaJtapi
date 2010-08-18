package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.PrivateDtmfEvent;

public class PrivateDtmfEventImpl implements PrivateDtmfEvent {
	private final char dtmfDigit;

	public PrivateDtmfEventImpl(final char digit) {
		dtmfDigit = digit;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PrivateDtmfEventImpl))
			return false;
		final PrivateDtmfEventImpl other = (PrivateDtmfEventImpl) obj;

		return dtmfDigit == other.dtmfDigit;
	}

	public char getDtmfDigit() {
		return dtmfDigit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dtmfDigit;
		return result;
	}
}
