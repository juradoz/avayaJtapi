package com.avaya.jtapi.tsapi;

public class LookaheadInfo {
	public static final short LAI_ALL_INTERFLOW = 0;
	public static final short LAI_THRESHOLD_INTERFLOW = 1;
	public static final short LAI_VECTORING_INTERFLOW = 2;
	public static final short LAI_NOT_IN_QUEUE = 0;
	public static final short LAI_LOW = 1;
	public static final short LAI_MEDIUM = 2;
	public static final short LAI_HIGH = 3;
	public static final short LAI_TOP = 4;
	short type;
	short priority;
	int hours;
	int minutes;
	int seconds;
	String sourceVDN;

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public short getPriority() {
		return priority;
	}

	public int getSeconds() {
		return seconds;
	}

	public String getSourceVDN() {
		return sourceVDN;
	}

	public short getType() {
		return type;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public void setSourceVDN(String sourceVDN) {
		this.sourceVDN = sourceVDN;
	}

	public void setType(short type) {
		this.type = type;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LookaheadInfo JD-Core Version: 0.5.4
 */