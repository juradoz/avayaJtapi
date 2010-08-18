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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.termConn.PrivateDtmfEventImpl JD-Core
 * Version: 0.5.4
 */