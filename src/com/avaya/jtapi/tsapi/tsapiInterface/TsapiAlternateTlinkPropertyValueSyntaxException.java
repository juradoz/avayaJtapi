package com.avaya.jtapi.tsapi.tsapiInterface;

public class TsapiAlternateTlinkPropertyValueSyntaxException extends
		TsapiPropertySyntaxException {
	private static final long serialVersionUID = 1L;

	public TsapiAlternateTlinkPropertyValueSyntaxException() {
		super(
				"Syntax error.  Alternate Tlink property value is not properly formatted.");
	}

	public TsapiAlternateTlinkPropertyValueSyntaxException(String s) {
		super("Alternate Tlink property value is not properly formatted; " + s);
	}
}

