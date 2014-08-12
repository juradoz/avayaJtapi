package com.avaya.jtapi.tsapi;

public class NetworkProgressInfo {
	public static final short PL_NONE = -1;
	public static final short PL_USER = 0;
	public static final short PL_PUB_LOCAL = 1;
	public static final short PL_PUB_REMOTE = 4;
	public static final short PL_PRIV_REMOTE = 5;
	public static final short PD_NONE = -1;
	public static final short PD_CALL_OFF_ISDN = 1;
	public static final short PD_DEST_NOT_ISDN = 2;
	public static final short PD_ORIG_NOT_ISDN = 3;
	public static final short PD_CALL_ON_ISDN = 4;
	public static final short PD_INBAND = 8;
	public short progressLocation;
	public short progressDescription;

	NetworkProgressInfo(short location, short description) {
		this.progressLocation = location;
		this.progressDescription = description;
	}

	public NetworkProgressInfo() {
	}
}