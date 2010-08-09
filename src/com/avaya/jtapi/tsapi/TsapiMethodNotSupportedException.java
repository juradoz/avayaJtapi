package com.avaya.jtapi.tsapi;

import javax.telephony.MethodNotSupportedException;

public final class TsapiMethodNotSupportedException extends
		MethodNotSupportedException implements ITsapiException {
	int errorType = 0;
	int errorCode = 0;
	static final long serialVersionUID = 1L;

	public TsapiMethodNotSupportedException(int _errorType, int _errorCode) {
		errorType = _errorType;
		errorCode = _errorCode;
	}

	public TsapiMethodNotSupportedException(int _errorType, int _errorCode,
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException JD-Core Version: 0.5.4
 */