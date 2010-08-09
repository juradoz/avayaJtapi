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

	public ACSEventHeader(int _eventClass, int _eventType) {
		eventClass = _eventClass;
		eventType = _eventType;
	}

	public int getEventClass() {
		return eventClass;
	}

	public int getEventType() {
		return eventType;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSEventHeader JD-Core Version: 0.5.4
 */