package com.avaya.jtapi.tsapi;

public final class CallClassifierInfo {
	public int numAvailPorts;
	public int numInUsePorts;

	public CallClassifierInfo(final int _numAvailPorts, final int _numInUsePorts) {
		numAvailPorts = _numAvailPorts;
		numInUsePorts = _numInUsePorts;
	}
}
