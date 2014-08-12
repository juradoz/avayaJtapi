package com.avaya.jtapi.tsapi;

public final class TrunkGroupInfo {
	public int idleTrunks;
	public int usedTrunks;

	public TrunkGroupInfo(int _idleTrunks, int _usedTrunks) {
		this.idleTrunks = _idleTrunks;
		this.usedTrunks = _usedTrunks;
	}
}