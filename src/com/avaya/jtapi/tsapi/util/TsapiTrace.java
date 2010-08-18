package com.avaya.jtapi.tsapi.util;

import org.apache.log4j.Logger;

public class TsapiTrace {
	public static int TRACELEVEL = 1;
	protected static int callDepth = 0;

	private static String getIndent() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < callDepth; ++i) {
			buffer.append("  ");
		}
		return buffer.toString();
	}

	@SuppressWarnings("unchecked")
	private static String print(Object o) {
		if (o != null) {
			if (o instanceof Class) {
				return ((Class) o).getName();
			}
			try {
				return o.toString();
			} catch (Exception e) {
				return o.getClass().getName();
			}
		}

		return "";
	}

	private static void printEntering(String str, Object o) {
		if (o != null) {
			Logger log = Logger.getLogger(o.getClass());
			log.trace(getIndent() + "--> " + str + print(o));
		}
	}

	private static void printExiting(String str, Object o) {
		if (o != null) {
			Logger log = Logger.getLogger(o.getClass());
			log.trace(getIndent() + "<-- " + str + print(o));
		}
	}

	public static void traceConstruction(Object obj, Class<?> clazz) {
		if (obj.getClass().equals(clazz)) {
			Logger log = Logger.getLogger(obj.getClass());
			log.trace(getIndent() + obj + " constructed.");
		}
	}

	public static void traceDestruction(Object obj, Class<?> clazz) {
		if (obj.getClass().equals(clazz)) {
			Logger log = Logger.getLogger(obj.getClass());
			log.trace(getIndent() + obj + " destroyed.");
		}
	}

	public static void traceEntry(String str, Object o) {
		if (TRACELEVEL == 0) {
			return;
		}
		if (TRACELEVEL == 2) {
			callDepth += 1;
		}
		printEntering(str + ": ", o);
	}

	public static void traceExit(String str, Object o) {
		if (TRACELEVEL == 0) {
			return;
		}
		printExiting(str + ": ", o);
		if (TRACELEVEL != 2) {
			return;
		}
		callDepth -= 1;
	}
}

