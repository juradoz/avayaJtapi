package com.avaya.jtapi.tsapi.csta1;

public final class CSTATSProvider {
	private static long handleCSTAEventTimeThreshold = Integer.parseInt("250");

	public static long getHandleCSTAEventTimeThreshold() {
		return CSTATSProvider.handleCSTAEventTimeThreshold;
	}

	public static void setHandleCSTAEventTimeThreshold(
			final long handleCSTAEventTimeThreshold) {
		CSTATSProvider.handleCSTAEventTimeThreshold = handleCSTAEventTimeThreshold;
	}
}
