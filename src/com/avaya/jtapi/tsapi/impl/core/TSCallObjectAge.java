package com.avaya.jtapi.tsapi.impl.core;

import java.util.Date;

import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;

class TSCallObjectAge {
	static final String OLD_AGE_DUMP_ANNOTATION = "{AgeThresholdExceeded}";
	private long _gettime_stamp_milliseconds;

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
				+ ((isOld()) ? " minutes {AgeThresholdExceeded}" : " minutes");
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSCallObjectAge JD-Core Version: 0.5.4
 */