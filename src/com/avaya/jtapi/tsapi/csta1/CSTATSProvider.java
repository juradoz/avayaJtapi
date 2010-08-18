package com.avaya.jtapi.tsapi.csta1;

public final class CSTATSProvider {
	private static long handleCSTAEventTimeThreshold = Integer.parseInt("250");

	public static long getHandleCSTAEventTimeThreshold() {
		return handleCSTAEventTimeThreshold;
	}

	public static void setHandleCSTAEventTimeThreshold(
			long handleCSTAEventTimeThreshold) {
		CSTATSProvider.handleCSTAEventTimeThreshold = handleCSTAEventTimeThreshold;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTATSProvider JD-Core Version: 0.5.4
 */