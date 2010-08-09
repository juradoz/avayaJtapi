package com.avaya.jtapi.tsapi;

import javax.telephony.ResourceUnavailableException;

public final class TsapiResourceUnavailableException extends
		ResourceUnavailableException implements ITsapiException {
	private static final long serialVersionUID = 1L;
	int errorType = 0;
	int errorCode = 0;

	public TsapiResourceUnavailableException(int _errorType, int _errorCode,
			int type) {
		super(type);
		errorType = _errorType;
		errorCode = _errorCode;
	}

	public TsapiResourceUnavailableException(int _errorType, int _errorCode,
			int type, String s) {
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.TsapiResourceUnavailableException JD-Core Version:
 * 0.5.4
 */