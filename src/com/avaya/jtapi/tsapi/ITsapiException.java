package com.avaya.jtapi.tsapi;

public abstract interface ITsapiException {
	public static final int NORMAL = 0;
	public static final int ACS = 1;
	public static final int CSTA = 2;
	public static final int JTAPI = 3;
	public static final int INTERNAL = 4;
	public static final int EC_NORMAL = 0;
	public static final int EC_INVALID_CONF = 1;
	public static final int EC_PROVIDER_OUT_OF_SERVICE = 2;

	public abstract int getErrorCode();

	public abstract int getErrorType();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.ITsapiException JD-Core Version: 0.5.4
 */