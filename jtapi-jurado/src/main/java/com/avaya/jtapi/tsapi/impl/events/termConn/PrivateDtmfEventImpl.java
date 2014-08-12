package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.PrivateDtmfEvent;

public class PrivateDtmfEventImpl implements PrivateDtmfEvent {
	private char dtmfDigit;

	public PrivateDtmfEventImpl(char digit) {
		this.dtmfDigit = digit;
	}

	public int hashCode() {
		int result = 1;
		result = 31 * result + this.dtmfDigit;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PrivateDtmfEventImpl))
			return false;
		PrivateDtmfEventImpl other = (PrivateDtmfEventImpl) obj;
		if (this.dtmfDigit != other.dtmfDigit)
			return false;
		return true;
	}

	public char getDtmfDigit() {
		return this.dtmfDigit;
	}
}