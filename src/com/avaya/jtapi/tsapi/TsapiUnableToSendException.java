package com.avaya.jtapi.tsapi;

public class TsapiUnableToSendException extends TsapiPlatformException {
	private static final long serialVersionUID = 1L;

	public TsapiUnableToSendException(final int type, final int code,
			final String s) {
		super(type, code, s);
	}
}
