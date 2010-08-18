package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.CallCenterTrunk;

public class V5NetworkProgressInfo extends NetworkProgressInfo {
	String trunkGroup;
	String trunkMember;
	public CallCenterTrunk trunk;

	public V5NetworkProgressInfo() {
	}

	V5NetworkProgressInfo(final String _trunkGroup, final String _trunkMember,
			final TsapiTrunk _trunk, final short progressLocation,
			final short progressDescription) {
		super(progressLocation, progressDescription);
		trunkGroup = _trunkGroup;
		trunkMember = _trunkMember;
		trunk = _trunk;
	}

	String getTrunkGroup() {
		return trunkGroup;
	}

	String getTrunkMember() {
		return trunkMember;
	}

	public void setTrunkGroup(final String trunkGroup) {
		this.trunkGroup = trunkGroup;
	}

	public void setTrunkMember(final String trunkMember) {
		this.trunkMember = trunkMember;
	}

	public void setTsapiTrunk(final TsapiTrunk trunk) {
		this.trunk = trunk;
	}
}
