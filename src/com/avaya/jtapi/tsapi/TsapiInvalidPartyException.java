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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.TsapiInvalidPartyException JD-Core Version: 0.5.4
 */