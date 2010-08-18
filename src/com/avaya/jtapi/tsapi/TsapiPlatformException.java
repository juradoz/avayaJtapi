package com.avaya.jtapi.tsapi;

import javax.telephony.PlatformException;

public class TsapiPlatformException extends PlatformException implements
		ITsapiException {
	private static final long serialVersionUID = 1L;
	int errorType = 0;
	int errorCode = 0;

	public TsapiPlatformException(final int _errorType, final int _errorCode,
			final String s) {
		super(s);
		errorType = _errorType;
		errorCode = _errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public int getErrorType() {
		return errorType;
	}
}
