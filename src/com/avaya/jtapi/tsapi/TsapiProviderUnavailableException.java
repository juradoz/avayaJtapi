package com.avaya.jtapi.tsapi;

import javax.telephony.ProviderUnavailableException;

public final class TsapiProviderUnavailableException extends
		ProviderUnavailableException implements ITsapiException {
	private static final long serialVersionUID = 1L;
	int errorType = 0;
	int errorCode = 0;

	public TsapiProviderUnavailableException(int _errorType, int _errorCode) {
		errorCode = _errorCode;
		errorType = _errorType;
	}

	public TsapiProviderUnavailableException(int _errorType, int _errorCode,
			String s) {
		super(s);
		errorCode = _errorCode;
		errorType = _errorType;
	}

	public TsapiProviderUnavailableException(int cause, String s) {
		super(cause, s);
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
 * com.avaya.jtapi.tsapi.TsapiProviderUnavailableException JD-Core Version:
 * 0.5.4
 */