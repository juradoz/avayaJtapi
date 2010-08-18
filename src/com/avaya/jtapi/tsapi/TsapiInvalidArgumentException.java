package com.avaya.jtapi.tsapi;

import javax.telephony.InvalidArgumentException;

public final class TsapiInvalidArgumentException extends
		InvalidArgumentException implements ITsapiException {
	private static final long serialVersionUID = 1L;
	int errorType = 0;
	int errorCode = 0;

	public TsapiInvalidArgumentException(int _errorType, int _errorCode) {
		errorType = _errorType;
		errorCode = _errorCode;
	}

	public TsapiInvalidArgumentException(int _errorType, int _errorCode,
			String s) {
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

