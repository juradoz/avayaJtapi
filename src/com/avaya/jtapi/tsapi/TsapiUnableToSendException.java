package com.avaya.jtapi.tsapi;

public class TsapiUnableToSendException extends TsapiPlatformException {
	private static final long serialVersionUID = 1L;

	public TsapiUnableToSendException(int type, int code, String s) {
		super(type, code, s);
	}
}

