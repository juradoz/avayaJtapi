package com.avaya.jtapi.tsapi;

public abstract interface ITsapiEvent {
	public static final int CORE_EVENT = 0;
	public static final int CALLCONTROL_EVENT = 1;
	public static final int CALLCENTER_EVENT = 2;
	public static final int MEDIA_EVENT = 3;
	public static final int PHONE_EVENT = 4;
	public static final int PRIVATE_EVENT = 5;
	public static final int NUM_PACKAGES = 8;

	public abstract int getCallCenterCause();

	public abstract int getCallControlCause();

	public abstract int getCause();

	public abstract int getEventPackage();

	public abstract int getMediaCause();

	public abstract Object getPrivateData();
}

