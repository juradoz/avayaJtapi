package com.avaya.jtapi.tsapi;

public class TsapiSocketException extends TsapiPlatformException {
	private static final long serialVersionUID = 1L;

	public TsapiSocketException(int _errorType, int _errorCode, String s) {
		super(_errorType, _errorCode, s);
	}
}

