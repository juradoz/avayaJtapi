package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.PrivateDtmfEvent;

public class PrivateDtmfEventImpl implements PrivateDtmfEvent {
	private char dtmfDigit;

	public PrivateDtmfEventImpl(char digit) {
		dtmfDigit = digit;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PrivateDtmfEventImpl)) {
			return false;
		}
		PrivateDtmfEventImpl other = (PrivateDtmfEventImpl) obj;

		return dtmfDigit == other.dtmfDigit;
	}

	public char getDtmfDigit() {
		return dtmfDigit;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + dtmfDigit;
		return result;
	}
}

