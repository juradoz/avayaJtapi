package com.avaya.jtapi.tsapi;

public final class CallClassifierInfo {
	public int numAvailPorts;
	public int numInUsePorts;

	public CallClassifierInfo(int _numAvailPorts, int _numInUsePorts) {
		this.numAvailPorts = _numAvailPorts;
		this.numInUsePorts = _numInUsePorts;
	}
}