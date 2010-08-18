package com.avaya.jtapi.tsapi.impl.core;

import java.util.Date;

import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;

class TSCallObjectAge {
	static final String OLD_AGE_DUMP_ANNOTATION = "{AgeThresholdExceeded}";
	private final long _gettime_stamp_milliseconds;

	TSCallObjectAge() {
		_gettime_stamp_milliseconds = new Date().getTime();
	}

	long getAgeMinutes() {
		return (new Date().getTime() - _gettime_stamp_milliseconds) / 60000L;
	}

	boolean isOld() {
		return getAgeMinutes() > Tsapi.getAuditObjectAgeThreshold();
	}

	@Override
	public String toString() {
		return getAgeMinutes()
				+ (isOld() ? " minutes {AgeThresholdExceeded}" : " minutes");
	}
}
