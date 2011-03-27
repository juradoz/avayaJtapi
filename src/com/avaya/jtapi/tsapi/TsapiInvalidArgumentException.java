package com.avaya.jtapi.tsapi;

import javax.telephony.InvalidArgumentException;

public final class TsapiInvalidArgumentException extends
		InvalidArgumentException implements ITsapiException {
	private static final long serialVersionUID = 1L;
	int errorType = 0;
	int errorCode = 0;

	public TsapiInvalidArgumentException(final int _errorType,
			final int _errorCode) {
		errorType = _errorType;
		errorCode = _errorCode;
	}

	public TsapiInvalidArgumentException(final int _errorType,
			final int _errorCode, final String s) {
		super(s);
		errorType = _errorType;
		errorCode = _errorCode;
	}

	@Override
	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public int getErrorType() {
		return errorType;
	}
}
