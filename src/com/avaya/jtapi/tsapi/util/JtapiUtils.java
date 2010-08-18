package com.avaya.jtapi.tsapi.util;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class JtapiUtils {
	private static Logger log = Logger.getLogger(JtapiUtils.class);

	@SuppressWarnings("unchecked")
	public static boolean isLog4jConfigured() {
		Enumeration appenders = Logger.getRootLogger().getAllAppenders();
		if (appenders.hasMoreElements()) {
			return true;
		}

		Enumeration loggers = LogManager.getCurrentLoggers();
		while (loggers.hasMoreElements()) {
			Logger c = (Logger) loggers.nextElement();
			if (c.getAllAppenders().hasMoreElements()) {
				return true;
			}
		}
		return false;
	}

	public static Collection<InetSocketAddress> parseTelephonyServerEntry(
			String value, int defaultPort) {
		Collection<InetSocketAddress> telephonyServers = new LinkedHashSet<InetSocketAddress>();
		String[] entries = value.split(",");
		for (String entry : entries) {
			String[] tokens = entry.split(":");
			if (tokens.length > 0) {
				int port = defaultPort;
				if (tokens.length > 1) {
					try {
						port = Integer.parseInt(tokens[1]);
					} catch (NumberFormatException e) {
						log.error("Error getting port from string fragment'"
								+ value + "':" + e.getMessage(), e);
					}
				}

				String server = tokens[0];
				server = (server != null) ? server.trim() : server;
				telephonyServers.add(new InetSocketAddress(server, port));
			}
		}
		return telephonyServers;
	}
}

