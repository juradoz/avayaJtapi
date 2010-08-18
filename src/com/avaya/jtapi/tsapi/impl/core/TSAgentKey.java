package com.avaya.jtapi.tsapi.impl.core;

final class TSAgentKey {
	String agentDeviceID;
	String acdDeviceID;
	String agentID;

	TSAgentKey(String _agentDeviceID, String _acdDeviceID, String _agentID) {
		agentDeviceID = _agentDeviceID;
		acdDeviceID = _acdDeviceID;
		agentID = _agentID;
	}

	@Override
	public boolean equals(Object anObject) {
		if (anObject instanceof TSAgentKey) {
			TSAgentKey anotherAgentKey = (TSAgentKey) anObject;

			boolean acdDeviceIDMatch = true;
			boolean agentIDMatch = true;
			if ((acdDeviceID != null) && (anotherAgentKey.acdDeviceID != null)) {
				acdDeviceIDMatch = acdDeviceID
						.equals(anotherAgentKey.acdDeviceID);
			}
			if ((agentID != null) && (anotherAgentKey.agentID != null)) {
				agentIDMatch = agentID.equals(anotherAgentKey.agentID);
			}
			return (agentDeviceID.equals(anotherAgentKey.agentDeviceID))
					&& (acdDeviceIDMatch) && (agentIDMatch);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return agentDeviceID.hashCode();
	}

	@Override
	public String toString() {
		return "TSAgentKey(" + agentDeviceID + "," + acdDeviceID + ","
				+ agentID + ")";
	}
}

