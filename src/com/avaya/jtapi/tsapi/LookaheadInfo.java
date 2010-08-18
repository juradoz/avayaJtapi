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

	public void setHours(final int hours) {
		this.hours = hours;
	}

	public void setMinutes(final int minutes) {
		this.minutes = minutes;
	}

	public void setPriority(final short priority) {
		this.priority = priority;
	}

	public void setSeconds(final int seconds) {
		this.seconds = seconds;
	}

	public void setSourceVDN(final String sourceVDN) {
		this.sourceVDN = sourceVDN;
	}

	public void setType(final short type) {
		this.type = type;
	}
}
