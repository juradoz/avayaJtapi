package com.avaya.jtapi.tsapi.csta1;

public abstract interface LucentConnectionID {
	public abstract int getCallID();

	public abstract String getDeviceID();

	public abstract int getDevIDType();
}

