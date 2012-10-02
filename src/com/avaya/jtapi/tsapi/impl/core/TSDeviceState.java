package com.avaya.jtapi.tsapi.impl.core;

abstract class TSDeviceState {
	abstract void recreate(TSDevice paramTSDevice);

	abstract boolean wasDeleteDone();
}