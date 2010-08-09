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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com
 * .avaya.jtapi.tsapi.tsapiInterface.TsapiAlternateTlinkPropertyNameSyntaxException
 * JD-Core Version: 0.5.4
 */