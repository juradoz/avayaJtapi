package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import java.util.Date;

class TSCallObjectAge {
	static final String OLD_AGE_DUMP_ANNOTATION = "{AgeThresholdExceeded}";
	private long _gettime_stamp_milliseconds = new Date().getTime();

	long getAgeMinutes() {
		return (new Date().getTime() - this._gettime_stamp_milliseconds) / 60000L;
	}

	boolean isOld() {
		return getAgeMinutes() > Tsapi.getAuditObjectAgeThreshold();
	}

	public String toString() {
		return new StringBuilder()
				.append(getAgeMinutes())
				.append(isOld() ? " minutes {AgeThresholdExceeded}"
						: " minutes").toString();
	}
}