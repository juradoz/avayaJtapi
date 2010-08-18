package com.avaya.jtapi.tsapi;

public final class TrunkGroupInfo {
	public int idleTrunks;
	public int usedTrunks;

	public TrunkGroupInfo(final int _idleTrunks, final int _usedTrunks) {
		idleTrunks = _idleTrunks;
		usedTrunks = _usedTrunks;
	}
}
