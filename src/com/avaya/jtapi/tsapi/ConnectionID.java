package com.avaya.jtapi.tsapi;

public final class ConnectionID {
	public static final short STATIC_ID = 0;
	public static final short DYNAMIC_ID = 1;
	int callID;
	String deviceID;
	short devIDType;

	ConnectionID() {
	}

	public ConnectionID(int _callID, String _deviceID, short _devIDType) {
		callID = _callID;
		deviceID = _deviceID;
		devIDType = _devIDType;
	}

	@Override
	public boolean equals(Object anObject) {
		if (anObject instanceof ConnectionID) {
			ConnectionID anotherConnID = (ConnectionID) anObject;
			if (deviceID == null) {
				return (callID == anotherConnID.callID)
						&& (devIDType == anotherConnID.devIDType)
						&& (anotherConnID.deviceID == null);
			}

			return (callID == anotherConnID.callID)
					&& (devIDType == anotherConnID.devIDType)
					&& (deviceID.equals(anotherConnID.deviceID));
		}

		return false;
	}

	public int getCallID() {
		return callID;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public int getDevIDType() {
		return devIDType;
	}

	@Override
	public int hashCode() {
		if (deviceID == null) {
			return callID + (devIDType << 31);
		}

		return callID + deviceID.hashCode() + (devIDType << 31);
	}

	@Override
	public String toString() {
		return "ConnectionID(" + callID + "," + deviceID + "," + devIDType
				+ ")";
	}
}

