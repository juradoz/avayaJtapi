package com.avaya.jtapi.tsapi;

public final class ExtendedDeviceID {
	String deviceID;
	short deviceIDType;
	short deviceIDStatus;
	public static final short DEVICE_IDENTIFIER = 0;
	public static final short IMPLICIT_PUBLIC = 20;
	public static final short EXPLICIT_PUBLIC_UNKNOWN = 30;
	public static final short EXPLICIT_PUBLIC_INTERNATIONAL = 31;
	public static final short EXPLICIT_PUBLIC_NATIONAL = 32;
	public static final short EXPLICIT_PUBLIC_NETWORK_SPECIFIC = 33;
	public static final short EXPLICIT_PUBLIC_SUBSCRIBER = 34;
	public static final short EXPLICIT_PUBLIC_ABBREVIATED = 35;
	public static final short IMPLICIT_PRIVATE = 40;
	public static final short EXPLICIT_PRIVATE_UNKNOWN = 50;
	public static final short EXPLICIT_PRIVATE_LEVEL3_REGIONAL_NUMBER = 51;
	public static final short EXPLICIT_PRIVATE_LEVEL2_REGIONAL_NUMBER = 52;
	public static final short EXPLICIT_PRIVATE_LEVEL1_REGIONAL_NUMBER = 53;
	public static final short EXPLICIT_PRIVATE_PTN_SPECIFIC_NUMBER = 54;
	public static final short EXPLICIT_PRIVATE_LOCAL_NUMBER = 55;
	public static final short EXPLICIT_PRIVATE_ABBREVIATED = 56;
	public static final short OTHER_PLAN = 60;
	public static final short TRUNK_IDENTIFIER = 70;
	public static final short TRUNK_GROUP_IDENTIFIER = 71;
	public static final short ID_PROVIDED = 0;
	public static final short ID_NOT_KNOWN = 1;
	public static final short ID_NOT_REQUIRED = 2;

	ExtendedDeviceID() {
	}

	public ExtendedDeviceID(String _deviceID, short _deviceIDType,
			short _deviceIDStatus) {
		deviceID = _deviceID;
		deviceIDType = _deviceIDType;
		deviceIDStatus = _deviceIDStatus;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public short getDeviceIDStatus() {
		return deviceIDStatus;
	}

	public short getDeviceIDType() {
		return deviceIDType;
	}

	@Override
	public String toString() {
		return "ExtendedDeviceID(" + deviceID + "," + deviceIDType + ","
				+ deviceIDStatus + ")";
	}
}

