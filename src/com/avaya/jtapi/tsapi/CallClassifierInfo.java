package com.avaya.jtapi.tsapi;

public final class CallClassifierInfo {
	public int numAvailPorts;
	public int numInUsePorts;

	public CallClassifierInfo(int _numAvailPorts, int _numInUsePorts) {
		numAvailPorts = _numAvailPorts;
		numInUsePorts = _numInUsePorts;
	}
}

