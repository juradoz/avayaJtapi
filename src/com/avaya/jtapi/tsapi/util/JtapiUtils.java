package com.avaya.jtapi.tsapi.util;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class JtapiUtils {
	private static Logger log = Logger.getLogger(JtapiUtils.class);

	@SuppressWarnings({ "rawtypes" })
	public static boolean isLog4jConfigured() {
		final Enumeration appenders = Logger.getRootLogger().getAllAppenders();
		if (appenders.hasMoreElements())
			return true;

		final Enumeration loggers = LogManager.getCurrentLoggers();
		while (loggers.hasMoreElements()) {
			final Logger c = (Logger) loggers.nextElement();
			if (c.getAllAppenders().hasMoreElements())
				return true;
		}
		return false;
	}

	public static Collection<InetSocketAddress> parseTelephonyServerEntry(
			final String value, final int defaultPort) {
		final Collection<InetSocketAddress> telephonyServers = new LinkedHashSet<InetSocketAddress>();
		final String[] entries = value.split(",");
		for (final String entry : entries) {
			final String[] tokens = entry.split(":");
			if (tokens.length > 0) {
				int port = defaultPort;
				if (tokens.length > 1)
					try {
						port = Integer.parseInt(tokens[1]);
					} catch (final NumberFormatException e) {
						JtapiUtils.log.error(
								"Error getting port from string fragment'"
										+ value + "':" + e.getMessage(), e);
					}

				String server = tokens[0];
				server = server != null ? server.trim() : server;
				telephonyServers.add(new InetSocketAddress(server, port));
			}
		}
		return telephonyServers;
	}
}
