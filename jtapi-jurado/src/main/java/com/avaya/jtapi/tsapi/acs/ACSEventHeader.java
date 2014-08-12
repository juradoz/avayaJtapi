package com.avaya.jtapi.tsapi.acs;

public final class ACSEventHeader {
	public static final int ACSREQUEST = 0;
	public static final int ACSUNSOLICITED = 1;
	public static final int ACSCONFIRMATION = 2;
	public static final int CSTAREQUEST = 3;
	public static final int CSTAUNSOLICITED = 4;
	public static final int CSTACONFIRMATION = 5;
	public static final int CSTAEVENTREPORT = 6;
	private int eventClass;
	private int eventType;

	public int getEventClass() {
		return this.eventClass;
	}

	public int getEventType() {
		return this.eventType;
	}

	public ACSEventHeader(int _eventClass, int _eventType) {
		this.eventClass = _eventClass;
		this.eventType = _eventType;
	}
}