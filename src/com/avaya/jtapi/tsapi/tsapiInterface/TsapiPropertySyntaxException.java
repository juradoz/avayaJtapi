package com.avaya.jtapi.tsapi.tsapiInterface;

public class TsapiPropertySyntaxException extends TsapiPropertiesException {
	private static final long serialVersionUID = 1L;

	public TsapiPropertySyntaxException() {
		super("Syntax error.");
	}

	public TsapiPropertySyntaxException(String s) {
		super(s);
	}
}

