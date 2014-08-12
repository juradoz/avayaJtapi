package com.avaya.jtapi.tsapi;

import javax.telephony.InvalidArgumentException;

public final class TsapiInvalidArgumentException extends
		InvalidArgumentException implements ITsapiException {
	private static final long serialVersionUID = 1L;
	int errorType = 0;
	int errorCode = 0;

	public int getErrorType() {
		return this.errorType;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public TsapiInvalidArgumentException(int _errorType, int _errorCode) {
		this.errorType = _errorType;
		this.errorCode = _errorCode;
	}

	public TsapiInvalidArgumentException(int _errorType, int _errorCode,
			String s) {
		super(s);
		this.errorType = _errorType;
		this.errorCode = _errorCode;
	}
}