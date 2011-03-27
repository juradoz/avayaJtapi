package com.avaya.jtapi.tsapi.util;

import org.apache.log4j.Logger;

public class TsapiTrace {
	public static int TRACELEVEL = 1;
	protected static int callDepth = 0;

	private static String getIndent() {
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < TsapiTrace.callDepth; ++i)
			buffer.append("  ");
		return buffer.toString();
	}

	@SuppressWarnings("rawtypes")
	private static String print(final Object o) {
		if (o != null) {
			if (o instanceof Class)
				return ((Class) o).getName();
			try {
				return o.toString();
			} catch (final Exception e) {
				return o.getClass().getName();
			}
		}

		return "";
	}

	private static void printEntering(final String str, final Object o) {
		if (o != null) {
			final Logger log = Logger.getLogger(o.getClass());
			log.trace(TsapiTrace.getIndent() + "--> " + str
					+ TsapiTrace.print(o));
		}
	}

	private static void printExiting(final String str, final Object o) {
		if (o != null) {
			final Logger log = Logger.getLogger(o.getClass());
			log.trace(TsapiTrace.getIndent() + "<-- " + str
					+ TsapiTrace.print(o));
		}
	}

	public static void traceConstruction(final Object obj, final Class<?> clazz) {
		if (obj.getClass().equals(clazz)) {
			final Logger log = Logger.getLogger(obj.getClass());
			log.trace(TsapiTrace.getIndent() + obj + " constructed.");
		}
	}

	public static void traceDestruction(final Object obj, final Class<?> clazz) {
		if (obj.getClass().equals(clazz)) {
			final Logger log = Logger.getLogger(obj.getClass());
			log.trace(TsapiTrace.getIndent() + obj + " destroyed.");
		}
	}

	public static void traceEntry(final String str, final Object o) {
		if (TsapiTrace.TRACELEVEL == 0)
			return;
		if (TsapiTrace.TRACELEVEL == 2)
			TsapiTrace.callDepth += 1;
		TsapiTrace.printEntering(str + ": ", o);
	}

	public static void traceExit(final String str, final Object o) {
		if (TsapiTrace.TRACELEVEL == 0)
			return;
		TsapiTrace.printExiting(str + ": ", o);
		if (TsapiTrace.TRACELEVEL != 2)
			return;
		TsapiTrace.callDepth -= 1;
	}
}
