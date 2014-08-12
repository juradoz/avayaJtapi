package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

abstract interface SnapshotCallExtraConfHandler {
	public abstract Object handleConf(boolean paramBoolean,
			Vector<TSEvent> paramVector, Object paramObject);
}