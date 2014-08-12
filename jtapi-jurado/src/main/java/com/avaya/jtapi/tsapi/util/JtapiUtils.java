package com.avaya.jtapi.tsapi.util;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class JtapiUtils {
	private static Logger log = Logger.getLogger(JtapiUtils.class);

	public static Collection<InetSocketAddress> parseTelephonyServerEntry(
			String value, int defaultPort) {
		Collection<InetSocketAddress> telephonyServers = new LinkedHashSet<InetSocketAddress>();
		String[] entries = value.split(",");
		for (int i = 0; i < entries.length; i++) {
			String[] tokens = getTokens(entries[i], ":");
			if (tokens != null) {
				int port = defaultPort;
				if (tokens[1] != null) {
					try {
						port = Integer.parseInt(tokens[1]);
					} catch (NumberFormatException e) {
						log.error("Error getting port from string fragment'"
								+ value + "':" + e.getMessage(), e);
					}
				}

				String server = tokens[0];
				server = server != null ? server.trim() : server;
				telephonyServers.add(new InetSocketAddress(server, port));
			}
		}
		return telephonyServers;
	}

	public static String[] getTokens(String entry, String delimiter) {
		String[] tokens = null;
		int index = entry.indexOf("[");
		if (index == 0) {
			int indexOfEndingBracket = entry.lastIndexOf("]");
			int indexOfColon = entry.indexOf(":");
			if ((indexOfEndingBracket > 0) && (indexOfColon > 0)) {
				tokens = new String[2];
				tokens[0] = entry.substring(1, indexOfEndingBracket);

				index = entry.indexOf(delimiter, indexOfEndingBracket);
				if (index > 0) {
					tokens[1] = entry.substring(index + 1, entry.length());
				}
			}
		} else {
			tokens = new String[2];
			index = entry.lastIndexOf(delimiter);
			if (index > 0) {
				tokens[0] = entry.substring(0, index);
				tokens[1] = entry.substring(index + 1, entry.length());

				int tempIndex = tokens[1].indexOf(":");
				if (tempIndex != -1) {
					if (tokens[1].charAt(tempIndex - 1) == '\\') {
						String before = tokens[1].substring(0, tempIndex - 1);
						String after = tokens[1].substring(tempIndex);
						tokens[1] = before.concat(after);
					}
				}
			} else {
				tokens[0] = entry.substring(0);
			}

		}

		return tokens;
	}

	public static boolean isLog4jConfigured() {
		Enumeration<?> appenders = Logger.getRootLogger().getAllAppenders();
		if (appenders.hasMoreElements()) {
			return true;
		}
		Enumeration<?> loggers = LogManager.getCurrentLoggers();
		while (loggers.hasMoreElements()) {
			Logger c = (Logger) loggers.nextElement();
			if (c.getAllAppenders().hasMoreElements()) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String[] temp = getTokens("altTraceFile=c\\:\\myLogs.txt", "=");

		if (temp != null) {
			for (String my : temp) {
				System.out.println(my);
			}
		}

		temp = getTokens("altTraceFile=/root/myLogs.txt", "=");

		if (temp != null)
			for (String my : temp) {
				System.out.println(my);
			}
	}
}