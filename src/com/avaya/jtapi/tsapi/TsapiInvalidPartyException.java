package com.avaya.jtapi.tsapi;

import javax.telephony.InvalidPartyException;

public final class TsapiInvalidPartyException extends InvalidPartyException
		implements ITsapiException {
	private static final long serialVersionUID = 1L;
	int errorType = 0;
	int errorCode = 0;

	public TsapiInvalidPartyException(int _errorType, int _errorCode, int type) {
		super(type);
		errorType = _errorType;
		errorCode = _errorCode;
	}

	public TsapiInvalidPartyException(int _errorType, int _errorCode, int type,
			String s) {
		super(type, s);
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

