package com.avaya.jtapi.tsapi.tsapiInterface;

public class TsapiAlternateTlinkPropertyNameSyntaxException extends
		TsapiPropertySyntaxException {
	private static final long serialVersionUID = 1L;

	public TsapiAlternateTlinkPropertyNameSyntaxException() {
		super(
				"Syntax error.  Alternate Tlink property name is not properly formatted.");
	}

	public TsapiAlternateTlinkPropertyNameSyntaxException(String s) {
		super("Alternate Tlink property name is not properly formatted; " + s);
	}
}