package com.avaya.jtapi.tsapi;

public class TsapiSocketException extends TsapiPlatformException {
	private static final long serialVersionUID = 1L;

	public TsapiSocketException(final int _errorType, final int _errorCode,
			final String s) {
		super(_errorType, _errorCode, s);
	}
}
