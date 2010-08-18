package com.avaya.jtapi.tsapi.tsapiInterface;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.ITsapiException;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiProviderUnavailableException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.acs.ACSNameAddr;
import com.avaya.jtapi.tsapi.acs.ACSRequestPrivileges;
import com.avaya.jtapi.tsapi.acs.ACSSetHeartbeatInterval;
import com.avaya.jtapi.tsapi.acs.ACSSetPrivileges;
import com.avaya.jtapi.tsapi.asn1.TsapiRequest;
import com.avaya.jtapi.tsapi.csta1.CSTAAlternateCall;
import com.avaya.jtapi.tsapi.csta1.CSTAAnswerCall;
import com.avaya.jtapi.tsapi.csta1.CSTACallCompletion;
import com.avaya.jtapi.tsapi.csta1.CSTAClearCall;
import com.avaya.jtapi.tsapi.csta1.CSTAClearConnection;
import com.avaya.jtapi.tsapi.csta1.CSTAConferenceCall;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAConsultationCall;
import com.avaya.jtapi.tsapi.csta1.CSTADeflectCall;
import com.avaya.jtapi.tsapi.csta1.CSTAEscapeSvc;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAForwardingInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAGetAPICaps;
import com.avaya.jtapi.tsapi.csta1.CSTAGetDeviceList;
import com.avaya.jtapi.tsapi.csta1.CSTAGroupPickupCall;
import com.avaya.jtapi.tsapi.csta1.CSTAHoldCall;
import com.avaya.jtapi.tsapi.csta1.CSTAMakeCall;
import com.avaya.jtapi.tsapi.csta1.CSTAMakePredictiveCall;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorCall;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorCallsViaDevice;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorDevice;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorFilter;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorStop;
import com.avaya.jtapi.tsapi.csta1.CSTAPickupCall;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryAgentState;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryCallMonitor;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryDnd;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryFwd;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryMwi;
import com.avaya.jtapi.tsapi.csta1.CSTAReconnectCall;
import com.avaya.jtapi.tsapi.csta1.CSTARetrieveCall;
import com.avaya.jtapi.tsapi.csta1.CSTARouteEndRequest;
import com.avaya.jtapi.tsapi.csta1.CSTARouteEndRequestInv;
import com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterCancel;
import com.avaya.jtapi.tsapi.csta1.CSTARouteRegisterReq;
import com.avaya.jtapi.tsapi.csta1.CSTARouteSelectRequest;
import com.avaya.jtapi.tsapi.csta1.CSTARouteSelectRequestInv;
import com.avaya.jtapi.tsapi.csta1.CSTASendPrivateEv;
import com.avaya.jtapi.tsapi.csta1.CSTASetAgentState;
import com.avaya.jtapi.tsapi.csta1.CSTASetDnd;
import com.avaya.jtapi.tsapi.csta1.CSTASetFwd;
import com.avaya.jtapi.tsapi.csta1.CSTASetMwi;
import com.avaya.jtapi.tsapi.csta1.CSTASnapshotCall;
import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDevice;
import com.avaya.jtapi.tsapi.csta1.CSTASysStatReq;
import com.avaya.jtapi.tsapi.csta1.CSTASysStatStart;
import com.avaya.jtapi.tsapi.csta1.CSTASysStatStop;
import com.avaya.jtapi.tsapi.csta1.CSTATSProvider;
import com.avaya.jtapi.tsapi.csta1.CSTATransferCall;
import com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiChannelOio;
import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
import com.avaya.jtapi.tsapi.util.JtapiUtils;
import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;

public class Tsapi {
	private static Logger log;
	private static Properties saveJtapiProperties = new Properties();
	static {
		Tsapi.log = Logger.getLogger(Tsapi.class);
		Tsapi.useTLinkIP = false;
		Tsapi.maxTcpSocketWait = 20;
		Tsapi.alternateTraceFile = null;
		Tsapi.refreshIntervalForTsapiPro = 100;

		Tsapi.prePopulateJtapiProperties();

		Tsapi.servers = new Vector<InetSocketAddress>();
		try {
			if (System.getProperty("com.avaya.jtapi.tsapi.servers") != null) {
				final Collection<InetSocketAddress> serverEntries = JtapiUtils
						.parseTelephonyServerEntry(System.getProperty(
								"com.avaya.jtapi.tsapi.servers").trim(), 450);

				for (final Object server : serverEntries)
					Tsapi.addServer((InetSocketAddress) server);
			}

			final Properties prop = new Properties();

			final InputStream in = TsapiChannelOio.getProperties();

			prop.load(in);

			Tsapi.initClass(null, prop);

			Tsapi.saveJtapiProperties.putAll(prop);

			Tsapi.displayProperties(System.getProperties(),
					Tsapi.saveJtapiProperties);

			in.close();

			TsapiChannelOio.getBrowser().setStartUp(false);
		} catch (final Exception e) {
			try {
				if (JtapiUtils.isLog4jConfigured()) {
					Tsapi.log.error("failed to find/open tsapi.pro file:");
					Tsapi.log.error(e.getMessage(), e);
				} else {
					System.out.println("failed to find/open tsapi.pro file:"
							+ e.getMessage());
					e.printStackTrace();
				}
				final String hostname = TsapiChannelOio.getBrowser()
						.getCodeBaseServer();

				final InetSocketAddress addr = new InetSocketAddress(hostname,
						450);
				final Properties prop = new Properties();
				Tsapi.initClass(addr, prop);
			} catch (final Exception e1) {
				try {
					if (JtapiUtils.isLog4jConfigured()) {
						Tsapi.log.error("failed to find codebase server:");
						Tsapi.log.error(e1.getMessage(), e1);
					} else {
						System.out.println("failed to find codebase server:"
								+ e.getMessage());
						e.printStackTrace();
					}

					final String hostname = InetAddress.getLocalHost()
							.getHostName();
					final InetSocketAddress addr = new InetSocketAddress(
							hostname, 450);
					final Properties prop = new Properties();
					Tsapi.initClass(addr, prop);
				} catch (final Exception e2) {
					if (JtapiUtils.isLog4jConfigured())
						Tsapi.log.error("enumServers: " + e2);
					else
						System.out.println("enumServers: " + e2);
				}
			}
		}
		Tsapi.showImplementationVersion();
	}

	@SuppressWarnings("unchecked")
	private static void displayProperties(final Properties systemProperties,
			final Properties jtapiProperties) {
		Set list = null;
		if (systemProperties != null) {
			list = systemProperties.entrySet();
			Tsapi.log.info("System properties dump ");
			for (final Object entry : list) {
				final String key = (String) ((Entry) entry).getKey();

				if (!key.startsWith("com.avaya.jtapi.tsapi."))
					Tsapi.print(key, (String) ((Entry) entry).getValue());
			}
		}

		Tsapi.log.info("Jtapi properties dump ");
		list = jtapiProperties.entrySet();
		for (final Object entry : list) {
			final Object key = ((Entry) entry).getKey();
			Object value = ((Entry) entry).getValue();
			if (key.equals("traceFileSize") || key.equals("errorFileSize"))
				value = value + "M";
			else if (key.equals("maxWaitForSocket")
					|| key.equals("propertyRefreshRate")
					|| key.equals("callCompletionTimeout")
					|| key.equals("callCleanupRate"))
				value = value + " seconds";
			else if (key.equals("debugLevel"))
				try {
					final int intValue = Integer.parseInt((String) value);
					if (intValue < 0 || intValue > 7)
						value = "<INVALID>";
				} catch (final Exception e) {
				}
			Tsapi.print((String) key, value);
		}
	}

	public static int getAuditDumpInterval() {
		return Tsapi.auditDumpInterval;
	}

	public static int getAuditObjectAgeThreshold() {
		return Tsapi.auditObjectAgeThreshold;
	}

	public static int getCallCleanupRate() {
		return Tsapi.callCleanupRate;
	}

	public static int getCallCompletionTimeout() {
		return Tsapi.callCompletionTimeout;
	}

	public static int getGetServicesTimeout() {
		return Tsapi.getServicesTimeout;
	}

	private static int getIntegerProperty(final String tsapiProperty,
			final Properties props, final String defaultValue,
			final int currentValue) {
		int returnValue = currentValue;

		final String propertyValue = props.getProperty(tsapiProperty,
				defaultValue);
		try {
			returnValue = Integer.parseInt(propertyValue);
			if (returnValue != currentValue)
				Tsapi.log.info("Property \"" + tsapiProperty + "\" set to "
						+ returnValue);
		} catch (final NumberFormatException ee) {
			if (JtapiUtils.isLog4jConfigured()) {
				Tsapi.log.error("Invalid integer value " + propertyValue
						+ " given for property " + tsapiProperty);
				Tsapi.log.error(ee.getMessage(), ee);
			} else {
				System.out.println("Invalid integer value " + propertyValue
						+ " given for property " + tsapiProperty);
				ee.printStackTrace();
			}
		}

		return returnValue;
	}

	public static int getMaxTcpSocketWait() {
		return Tsapi.maxTcpSocketWait;
	}

	public static int getMaxThreadPoolSize() {
		return Tsapi.maxThreadPoolSize;
	}

	public static int getRefreshIntervalForTsapiPro() {
		return Tsapi.refreshIntervalForTsapiPro;
	}

	public static String[] getServices() {
		String[] services = new String[0];
		if (Tsapi.sessionFac != null) {
			Tsapi.validate(Tsapi.servers);
			final Vector<ACSNameAddr> serv = Tsapi.sessionFac.enumServices(
					Tsapi.servers, Tsapi.useTLinkIP);
			services = new String[serv.size()];

			for (int i = 0; i < serv.size(); ++i)
				services[i] = ((ACSNameAddr) serv.elementAt(i)).getServerName();
		}
		return services;
	}

	public static boolean getTSDevicePerformanceOptimization() {
		return Tsapi.tsDevicePerformanceOptimization;
	}

	private static boolean handleVolatileConfigurationUpdate(
			final String tsapiProperty, final Properties prop)
			throws IOException {
		if (tsapiProperty.equalsIgnoreCase("debugLevel"))
			JTAPILoggingAdapter.setTraceLoggerLevel(prop
					.getProperty(tsapiProperty));
		else if (tsapiProperty.equalsIgnoreCase("getServicesTimeout")) {
			final int value = Tsapi.getIntegerProperty("getServicesTimeout",
					prop, "10", Tsapi.getServicesTimeout);

			Tsapi.getServicesTimeout = value * 1000;
		} else if (tsapiProperty.equalsIgnoreCase("callCleanupRate")) {
			final int value = Tsapi.getIntegerProperty("callCleanupRate", prop,
					"100", Tsapi.callCleanupRate);
			int roundedOfValue = 0;
			if (value < 10) {
				roundedOfValue = 10;
				if (JtapiUtils.isLog4jConfigured())
					Tsapi.log
							.info("value specified for property: callCleanupRate is "
									+ value
									+ ". Rounding up to multiple of 10. Final value = "
									+ roundedOfValue);
				else
					System.out
							.println("value specified for property: callCleanupRate is "
									+ value
									+ ". Rounding up to multiple of 10. Final value = "
									+ roundedOfValue);
			} else if (value % 10 != 0) {
				if (value % 10 < 5)
					roundedOfValue = value - value % 10;
				else
					roundedOfValue = value - value % 10 + 10;
				if (JtapiUtils.isLog4jConfigured())
					Tsapi.log
							.info("value specified for property: callCleanupRate is "
									+ value
									+ ". Rounding up to multiple of 10. Final value = "
									+ roundedOfValue);
				else
					System.out
							.println("value specified for property: callCleanupRate is "
									+ value
									+ ". Rounding up to multiple of 10. Final value = "
									+ roundedOfValue);
			} else
				roundedOfValue = value;
			if (value != roundedOfValue)
				prop.setProperty(tsapiProperty, Integer
						.toString(roundedOfValue));
			Tsapi.callCleanupRate = roundedOfValue;
		} else if (tsapiProperty.equalsIgnoreCase("callCompletionTimeout")) {
			final int value = Tsapi.getIntegerProperty("callCompletionTimeout",
					prop, "15", Tsapi.callCompletionTimeout / 1000);
			Tsapi.callCompletionTimeout = value * 1000;
		} else if (tsapiProperty.equalsIgnoreCase("enableAuditDump")) {
			final String propertyValue = prop.getProperty("enableAuditDump");
			if (propertyValue == null
					|| !propertyValue
							.equalsIgnoreCase(Boolean.FALSE.toString())
					&& !propertyValue.equalsIgnoreCase(Boolean.TRUE.toString())) {
				if (JtapiUtils.isLog4jConfigured())
					Tsapi.log
							.error("Need to provide either \"true\" or \"false\" value for property: enableAuditDump");
				else
					System.out
							.println("Need to provide either \"true\" or \"false\" value for property: enableAuditDump");
			} else
				Tsapi.isEnableAuditDump = Boolean.parseBoolean(propertyValue);

		} else if (tsapiProperty.equalsIgnoreCase("auditDumpInterval"))
			Tsapi.auditDumpInterval = Tsapi.getIntegerProperty(
					"auditDumpInterval", prop, "3", Tsapi.auditDumpInterval);
		else if (tsapiProperty.equalsIgnoreCase("auditObjectAgeThreshold"))
			Tsapi.auditObjectAgeThreshold = Tsapi.getIntegerProperty(
					"auditObjectAgeThreshold", prop, "60",
					Tsapi.auditObjectAgeThreshold);
		else if (tsapiProperty.equalsIgnoreCase("propertyRefreshRate")) {
			final int newValue = Integer.parseInt(prop.getProperty(
					tsapiProperty, "100"));
			if (Tsapi.refreshIntervalForTsapiPro != newValue) {
				Tsapi.refreshIntervalForTsapiPro = newValue;
				Tsapi.refreshPeriodChanged = true;
			} else
				Tsapi.refreshPeriodChanged = false;

		} else if (tsapiProperty.equalsIgnoreCase("altTraceFile")) {
			JTAPILoggingAdapter
					.setAltTraceFile(prop.getProperty(tsapiProperty));

			String newTraceFile = Tsapi.alternateTraceFile;
			try {
				newTraceFile = prop.getProperty(tsapiProperty, "");
			} catch (final Exception re) {
			}

			if (!newTraceFile.equals(Tsapi.alternateTraceFile))
				Tsapi.alternateTraceFile = newTraceFile;
		} else if (tsapiProperty.equalsIgnoreCase("traceFileCount"))
			JTAPILoggingAdapter.setTraceFileCount(prop
					.getProperty(tsapiProperty));
		else if (tsapiProperty.equalsIgnoreCase("traceFileSize"))
			JTAPILoggingAdapter.setTraceFileSize(prop
					.getProperty(tsapiProperty));
		else if (tsapiProperty.equalsIgnoreCase("errorFile"))
			JTAPILoggingAdapter.setErrorFile(prop.getProperty(tsapiProperty));
		else if (tsapiProperty.equalsIgnoreCase("errorFileCount"))
			JTAPILoggingAdapter.setErrorFileCount(prop
					.getProperty(tsapiProperty));
		else if (tsapiProperty.equalsIgnoreCase("errorFileSize"))
			JTAPILoggingAdapter.setErrorFileSize(prop
					.getProperty(tsapiProperty));
		else if (tsapiProperty.equalsIgnoreCase("perfFile"))
			JTAPILoggingAdapter.setPerfFile(prop.getProperty(tsapiProperty));
		else if (tsapiProperty.equalsIgnoreCase("perfFileCount"))
			JTAPILoggingAdapter.setPerfFileCount(prop
					.getProperty(tsapiProperty));
		else if (tsapiProperty.equalsIgnoreCase("perfFileSize"))
			JTAPILoggingAdapter
					.setPerfFileSize(prop.getProperty(tsapiProperty));
		else if (tsapiProperty.equalsIgnoreCase("performanceWindow"))
			PerfStatisticsCollector.setPerformanceWindow(Integer.parseInt(prop
					.getProperty(tsapiProperty)));
		else if (tsapiProperty
				.equalsIgnoreCase("unsolicitedHandlingTimeThreshold"))
			PerfStatisticsCollector.setUnsolicitedHandlingTimeThreshold(Long
					.parseLong(prop.getProperty(tsapiProperty)));
		else if (tsapiProperty
				.equalsIgnoreCase("serviceRequestTurnaroundTimeThreshold"))
			PerfStatisticsCollector
					.setServiceRequestTurnaroundTimeThreshold(Long
							.parseLong(prop.getProperty(tsapiProperty)));
		else if (tsapiProperty.equalsIgnoreCase("queueLengthThreshold"))
			PerfStatisticsCollector.setQueueLengthThreshold(Long.parseLong(prop
					.getProperty(tsapiProperty)));
		else if (tsapiProperty.equalsIgnoreCase("messageLatencyThreshold"))
			PerfStatisticsCollector.setMessageLatencyThreshold(Long
					.parseLong(prop.getProperty(tsapiProperty)));
		else if (tsapiProperty.equalsIgnoreCase("maxThreadPoolSize"))
			try {
				Tsapi.maxThreadPoolSize = Integer.parseInt(prop.getProperty(
						tsapiProperty, "20"));
			} catch (final Exception e) {
				if (JtapiUtils.isLog4jConfigured())
					Tsapi.log.error("Invalid value encountered for "
							+ tsapiProperty + ". Setting to default value", e);
				else
					System.out.println("Invalid value encountered for "
							+ tsapiProperty + ". Setting to default value."
							+ e.getMessage());
			}
		else if (tsapiProperty
				.equalsIgnoreCase("tsDevicePerformanceOptimization")) {
			final String propertyValue = prop.getProperty(tsapiProperty,
					"false");

			Tsapi.tsDevicePerformanceOptimization = Boolean.valueOf(
					propertyValue).booleanValue();
		} else if (tsapiProperty
				.equalsIgnoreCase("handleCstaEventTimeThreshold")) {
			int threshold;
			try {
				threshold = Integer.parseInt(prop.getProperty(tsapiProperty,
						"250"));
			} catch (final Exception re) {
				threshold = Integer.parseInt("250");
			}

			if (threshold < 0) {
				if (JtapiUtils.isLog4jConfigured())
					Tsapi.log
							.info("Requested setting for property \"handleCstaEventTimeThreshold\" ("
									+ threshold + ") is invalid.");
				else
					System.out
							.println("Requested setting for property \"handleCstaEventTimeThreshold\" ("
									+ threshold + ") is invalid.");
				threshold = Integer.parseInt("250");
			}

			CSTATSProvider.setHandleCSTAEventTimeThreshold(threshold);
		} else
			return false;

		return true;
	}

	private static void initClass(final InetSocketAddress address,
			final Properties prop) throws IOException {
		final Enumeration<?> eprop = prop.propertyNames();

		while (eprop.hasMoreElements()) {
			final String tsapiProperty = (String) eprop.nextElement();

			if (tsapiProperty.startsWith("["))
				prop.remove(tsapiProperty);
			else if (!Tsapi.handleVolatileConfigurationUpdate(tsapiProperty,
					prop))
				if (tsapiProperty.equalsIgnoreCase("useTlinkIP"))
					try {
						final int temp = Integer.parseInt(prop.getProperty(
								tsapiProperty, "0"));
						if (temp == 1)
							Tsapi.useTLinkIP = true;

					} catch (final Exception re) {
					}
				else if (tsapiProperty
						.equalsIgnoreCase("enable_PreserveRedirectedVDNs"))
					try {
						final int temp = Integer.parseInt(prop.getProperty(
								tsapiProperty, "0"));
						Tsapi.patch_enable_PreserveRedirectedVDNAsUNKNOWN = temp > 0;
					} catch (final Exception re) {
						Tsapi.patch_enable_PreserveRedirectedVDNAsUNKNOWN = false;
					}
				else if (tsapiProperty.equalsIgnoreCase("maxWaitForSocket"))
					try {
						Tsapi.maxTcpSocketWait = Integer.parseInt(prop
								.getProperty(tsapiProperty, "0"));
					} catch (final Exception re) {
						Tsapi.maxTcpSocketWait = 0;
					}
				else if (!tsapiProperty.equalsIgnoreCase("trustStoreLocation")
						&& !tsapiProperty
								.equalsIgnoreCase("trustStorePassword"))
					if (!tsapiProperty
							.equalsIgnoreCase("verifyServerCertificate"))
						if (tsapiProperty.regionMatches(true, 0, "Alternates",
								0, 10)) {
							final String valueString = prop
									.getProperty(tsapiProperty);
							if (valueString == null) {
								if (JtapiUtils.isLog4jConfigured())
									Tsapi.log.info("Error parsing property \""
											+ tsapiProperty + "\"; "
											+ "could not read property value.");
								else
									System.out
											.println("Error parsing property \""
													+ tsapiProperty
													+ "\"; "
													+ "could not read property value.");
							} else {
								final TsapiAlternateTlinkEntriesList alternateTlinkEntriesList = TsapiAlternateTlinkEntriesList
										.Instance();
								try {
									alternateTlinkEntriesList
											.addAlternateTlinkEntry(
													tsapiProperty, valueString);
								} catch (final TsapiPropertiesException e) {
									if (JtapiUtils.isLog4jConfigured())
										Tsapi.log.error(e.getMessage(), e);
									else
										System.out.println(e.getMessage());
								}
							}
						} else {
							String hostname;
							try {
								hostname = tsapiProperty.trim();
							} catch (final NoSuchElementException e) {
								if (JtapiUtils.isLog4jConfigured())
									Tsapi.log.error(e.getMessage(), e);
								else
									System.out.println(e.getMessage());
								continue;
							}

							int port;
							try {
								port = Integer.parseInt(prop.getProperty(
										tsapiProperty, Integer.toString(450))
										.trim());
							} catch (final Exception re2) {
								if (JtapiUtils.isLog4jConfigured())
									Tsapi.log
											.error("Invalid name value pair in : "
													+ tsapiProperty);
								else
									System.out
											.println("Invalid name value pair in : "
													+ tsapiProperty);
								port = 450;
							}

							final InetSocketAddress addr = new InetSocketAddress(
									hostname, port);
							Tsapi.servers.addElement(addr);
						}
		}
		JTAPILoggingAdapter.initializeLogging();

		Tsapi.log = Logger.getLogger(Tsapi.class);

		Tsapi.sessionFac = TsapiSessionFactory.getTsapiSessionFactory(prop);
	}

	public static boolean isEnableAuditDump() {
		return Tsapi.isEnableAuditDump;
	}

	public static boolean isPatch_enable_PreserveRedirectedVDNAsUNKNOWN() {
		return Tsapi.patch_enable_PreserveRedirectedVDNAsUNKNOWN;
	}

	public static boolean isRefreshPeriodChanged() {
		return Tsapi.refreshPeriodChanged;
	}

	private static void prePopulateJtapiProperties() {
		Tsapi.saveJtapiProperties.put("altTraceFile", "");
		Tsapi.saveJtapiProperties.put("traceFileCount", Integer.valueOf(10));
		Tsapi.saveJtapiProperties.put("traceFileSize", Integer.valueOf(50));
		Tsapi.saveJtapiProperties.put("errorFile", "");
		Tsapi.saveJtapiProperties.put("errorFileCount", Integer.valueOf(10));
		Tsapi.saveJtapiProperties.put("errorFileSize", Integer.valueOf(50));
		Tsapi.saveJtapiProperties.put("debugLevel", "0");
		Tsapi.saveJtapiProperties.put("maxWaitForSocket", "20");
		Tsapi.saveJtapiProperties.put("propertyRefreshRate", Integer
				.valueOf(100));

		String value = "";
		final ClassLoader loader = ClassLoader.getSystemClassLoader();
		if (loader != null) {
			final URL url = loader.getResource("avayaprca.jks");
			if (url != null)
				value = url.getFile();
		}
		Tsapi.saveJtapiProperties.put("trustStoreLocation", value);

		Tsapi.saveJtapiProperties.put("trustStorePassword", "password");
		Tsapi.saveJtapiProperties.put("verifyServerCertificate", "false");
		Tsapi.saveJtapiProperties.put("useTlinkIP", "0");
		Tsapi.saveJtapiProperties.put("tsDevicePerformanceOptimization",
				"false");
		Tsapi.saveJtapiProperties.put("maxThreadPoolSize", "20");
		Tsapi.saveJtapiProperties.put("callCleanupRate", "100");
		Tsapi.saveJtapiProperties.put("enableAuditDump", "false");
		Tsapi.saveJtapiProperties.put("getServicesTimeout", "10");
		Tsapi.saveJtapiProperties.put("callCompletionTimeout", "15");
	}

	private static void print(final String key, final Object value) {
		if (key.toLowerCase().indexOf("password") == -1)
			Tsapi.log.info(key + "=" + value);
		else
			Tsapi.log.info(key + "=****");
	}

	public static void setCallCleanupRate(final int callCleanupRate) {
		Tsapi.callCleanupRate = callCleanupRate;
	}

	public static void setCallCompletionTimeout(final int callCompletionTimeout) {
		Tsapi.callCompletionTimeout = callCompletionTimeout;
	}

	static void showImplementationVersion() {
		Tsapi.log.info("JTAPI Package Version: 5.2.0.483");
	}

	public static void updateVolatileConfigurationValues() {
		try {
			final Properties prop = new Properties();

			final InputStream in = TsapiChannelOio.getProperties();

			if (in == null)
				return;
			prop.load(in);
			in.close();

			if (prop.equals(Tsapi.saveJtapiProperties))
				return;

			Tsapi.prePopulateJtapiProperties();

			final Enumeration<?> eprop = prop.propertyNames();

			Tsapi.isEnableAuditDump = false;

			Tsapi.getServicesTimeout = Integer.parseInt("10") * 1000;

			Tsapi.callCleanupRate = Integer.parseInt("100");

			Tsapi.callCompletionTimeout = Integer.parseInt("15") * 1000;

			while (eprop.hasMoreElements())
				Tsapi.handleVolatileConfigurationUpdate((String) eprop
						.nextElement(), prop);
			Tsapi.saveJtapiProperties.putAll(prop);

			JTAPILoggingAdapter.updateLoggingProperties();

			Tsapi.displayProperties(null, Tsapi.saveJtapiProperties);
		} catch (final Exception e) {
			Tsapi.log.error(e.getMessage(), e);
		}
	}

	private TsapiUnsolicitedHandler evtHandler;

	protected boolean inService;

	private TsapiSession session = null;

	private static TsapiSessionFactory sessionFac;

	private static int refreshIntervalForTsapiPro;

	private static boolean refreshPeriodChanged = false;

	private static boolean useTLinkIP;

	private static String alternateTraceFile;

	private static int maxTcpSocketWait;

	private static int getServicesTimeout = Integer.parseInt("10") * 1000;

	private static int callCleanupRate = Integer.parseInt("100");

	private static int callCompletionTimeout = Integer.parseInt("15") * 1000;

	private static boolean isEnableAuditDump = false;

	private static int auditDumpInterval = Integer.parseInt("3");

	private static int auditObjectAgeThreshold = Integer.parseInt("60");

	private static boolean tsDevicePerformanceOptimization = Boolean.valueOf(
			"false").booleanValue();

	private static boolean patch_enable_PreserveRedirectedVDNAsUNKNOWN = false;

	protected static Vector<InetSocketAddress> servers;

	private static int maxThreadPoolSize = Integer.parseInt("20");

	public static void addServer(final InetSocketAddress address) {
		Tsapi.servers.add(address);
	}

	private static void validate(final Vector<InetSocketAddress> tServers) {
		if (tServers == null || tServers.size() == 0)
			throw new TsapiPlatformException(
					4,
					0,
					"Unable to find target telephony server(s). Please either \n1. create a tsapi.pro file with one or more valid telephony server entries in the system classpath or at location '"
							+ System.getProperty("user.dir")
							+ "' or \n"
							+ "2. pass valid server(s) via the providerString when invoking JtapiPeer.getProvider()\n"
							+ "3. pass the server(s) via the "
							+ "com.avaya.jtapi.tsapi.servers"
							+ " system property");
	}

	protected Tsapi() {
	}

	public Tsapi(final String tlink, final String login, final String passwd,
			final Vector<TsapiVendor> vendors,
			final TsapiUnsolicitedHandler handler) {
	}

	public void alternateCall(final CSTAConnectionID activeCall,
			final CSTAConnectionID otherCall, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAAlternateCall(activeCall, otherCall);
		session.send(req, priv, handler);
	}

	public void answerCall(final CSTAConnectionID alertingCall,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAAnswerCall(alertingCall);
		session.send(req, priv, handler);
	}

	public void callCompletion(final short feature,
			final CSTAConnectionID call, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTACallCompletion(feature, call);
		session.send(req, priv, handler);
	}

	public void cancelRouteCallback(final int routeRegisterReqID,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTARouteRegisterCancel(routeRegisterReqID);
		session.send(req, priv, handler);
	}

	public void clearCall(final CSTAConnectionID call, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAClearCall(call);
		session.send(req, priv, handler);
	}

	public void clearConnection(final CSTAConnectionID connection,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAClearConnection(connection);
		session.send(req, priv, handler);
	}

	public void conferenceCall(final CSTAConnectionID heldCall,
			final CSTAConnectionID activeCall, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAConferenceCall(heldCall, activeCall);
		session.send(req, priv, handler);
	}

	public void consultationCall(final CSTAConnectionID activeCall,
			final String calledDevice, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAConsultationCall(activeCall,
				calledDevice);
		session.send(req, priv, handler);
	}

	public void CSTAEscapeService(final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAEscapeSvc();
		session.send(req, priv, handler);
	}

	public CSTAEvent CSTAQueryCallMonitor() throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAQueryCallMonitor();
		return session.send(req, null);
	}

	public void CSTASendPrivateEvent(final CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTASendPrivateEv();

		session.sendAsync(req, priv);
	}

	public void deflectCall(final CSTAConnectionID deflectCall,
			final String calledDevice, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTADeflectCall(deflectCall, calledDevice);
		session.send(req, priv, handler);
	}

	public void disableHeartbeat() {
		session.disableHeartbeat();
	}

	public void enableHeartbeat() {
		session.enableHeartbeat();
	}

	public CSTAEvent getApiCaps() throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAGetAPICaps();
		return session.send(req, null);
	}

	public CSTAEvent getDeviceList(final int index, final short level)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAGetDeviceList(index, level);
		return session.send(req, null);
	}

	public String getServerID() {
		return session.getServerID();
	}

	public String getVendor() {
		return session.getTheVendor();
	}

	public byte[] getVendorVersion() {
		return session.getVendorVersion();
	}

	public void groupPickupCall(final String pickupDevice,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAGroupPickupCall(null, pickupDevice);
		session.send(req, priv, handler);
	}

	public boolean heartbeatIsEnabled() {
		return session.heartbeatIsEnabled();
	}

	public void holdCall(final CSTAConnectionID activeCall,
			final boolean reservation, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAHoldCall(activeCall, reservation);
		session.send(req, priv, handler);
	}

	public void init(String tlink, final String login, final String passwd,
			final Vector<TsapiVendor> vendors,
			final TsapiUnsolicitedHandler handler) {
		evtHandler = handler;
		try {
			Tsapi.sessionFac.setDebugID(evtHandler.toString());
			Tsapi.validate(Tsapi.servers);
			final ACSNameAddr nameAddr = Tsapi.sessionFac.findTlink(tlink,
					Tsapi.servers, Tsapi.useTLinkIP);

			tlink = nameAddr.getServerName();
			final InetSocketAddress addr = nameAddr.createInetSocketAddress();

			session = Tsapi.sessionFac.getTsapiSession(addr, tlink);
			session.setHandler(evtHandler);

			session.startSession(tlink, login, passwd, vendors, 10000);
		} catch (final TsapiPlatformException e) {
			Tsapi.log.error("Tsapi<init>: " + e);

			if (session != null)
				session.close();

			throw e;
		} catch (final Exception e) {
			Tsapi.log.error("Tsapi<init>: " + e);

			if (session != null)
				session.close();

			throw new TsapiPlatformException(4, 0, "initialization failed");
		}

		inService = true;
	}

	public boolean isInService() {
		return inService;
	}

	public void makeCall(final String callingDevice, final String calledDevice,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAMakeCall(callingDevice, calledDevice);
		session.send(req, priv, handler);
	}

	public void makePredictiveCall(final String callingDevice,
			final String calledDevice, final short allocationState,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAMakePredictiveCall(callingDevice,
				calledDevice, allocationState);

		session.send(req, priv, handler);
	}

	public CSTAEvent monitorCall(final CSTAConnectionID call,
			final CSTAMonitorFilter monitorFilter, final CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAMonitorCall(call, monitorFilter);
		return session.send(req, priv);
	}

	public CSTAEvent monitorCallsViaDevice(final String deviceID,
			final CSTAMonitorFilter monitorFilter, final CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAMonitorCallsViaDevice(deviceID,
				monitorFilter);
		return session.send(req, priv);
	}

	public CSTAEvent monitorDevice(final String deviceID,
			final CSTAMonitorFilter monitorFilter, final CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAMonitorDevice(deviceID, monitorFilter);
		return session.send(req, priv);
	}

	public void monitorStop(final int monitorCrossRefID,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAMonitorStop(monitorCrossRefID);
		if (handler != null)
			session.sendAsync(req, priv, handler);
		else
			session.send(req, priv);
	}

	public void pickupCall(final CSTAConnectionID deflectCall,
			final String calledDevice, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAPickupCall(deflectCall, calledDevice);
		session.send(req, priv, handler);
	}

	public void queryAgentState(final String agentDevice,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAQueryAgentState(agentDevice);
		session.send(req, priv, handler);
	}

	public CSTAEvent queryDeviceInfo(final String device, final CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAQueryDeviceInfo(device);
		return session.send(req, priv);
	}

	public void queryDeviceInfoAsync(final String device,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAQueryDeviceInfo(device);
		session.sendAsync(req, priv, handler);
	}

	public void queryDoNotDisturb(final String device, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAQueryDnd(device);
		session.send(req, priv, handler);
	}

	public void queryFwd(final String device, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAQueryFwd(device);
		session.send(req, priv, handler);
	}

	public void queryMsgWaitingInd(final String device, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAQueryMwi(device);
		session.send(req, priv, handler);
	}

	public void reconnectCall(final CSTAConnectionID activeCall,
			final CSTAConnectionID heldCall, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTAReconnectCall(activeCall, heldCall);
		session.send(req, priv, handler);
	}

	public void registerRouteCallback(final String routingDevice,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTARouteRegisterReq(routingDevice);
		session.send(req, priv, handler);
	}

	public void requestPrivileges(final CSTAPrivate priv,
			final ConfHandler handler) {
		final TsapiRequest req = new ACSRequestPrivileges();
		try {
			session.send(req, priv, handler);
		} catch (final Exception e) {
			if (e instanceof ITsapiException)
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"requestPrivileges failure: " + e);

			throw new TsapiPlatformException(4, 0,
					"request privileges unexpected exception " + e);
		}
	}

	public void requestSystemStatus(final CSTAPrivate priv,
			final ConfHandler handler)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTASysStatReq();
		session.send(req, priv, handler);
	}

	public void retrieveCall(final CSTAConnectionID heldCall,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTARetrieveCall(heldCall);
		session.send(req, priv, handler);
	}

	public void routeEnd(final int routeRegisterReqID,
			final int routingCrossRefID, final short errorValue,
			final CSTAPrivate priv) {
		TsapiRequest req;
		if (session.getApiVersion().equals("1"))
			req = new CSTARouteEndRequest(routeRegisterReqID,
					routingCrossRefID, errorValue);
		else
			req = new CSTARouteEndRequestInv(routeRegisterReqID,
					routingCrossRefID, errorValue);

		session.sendAsync(req, priv);
	}

	public void selectRoute(final int routeRegisterReqID,
			final int routingCrossRefID, final String routeSelected,
			final int remainRetry, final String isdnSetupMessage,
			final boolean routeUsedReq, final CSTAPrivate priv) {
		final byte[] isdnBuf = isdnSetupMessage.getBytes();
		TsapiRequest req;
		if (session.getApiVersion().equals("1"))
			req = new CSTARouteSelectRequest(routeRegisterReqID,
					routingCrossRefID, routeSelected, remainRetry, isdnBuf,
					routeUsedReq);
		else
			req = new CSTARouteSelectRequestInv(routeRegisterReqID,
					routingCrossRefID, routeSelected, remainRetry, isdnBuf,
					routeUsedReq);

		session.sendAsync(req, priv);
	}

	public void setAgentState(final String agentDevice, final short agentMode,
			final String agentID, final String acdGroup,
			final String agentPassword, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTASetAgentState(agentDevice, agentMode,
				agentID, acdGroup, agentPassword);
		session.send(req, priv, handler);
	}

	public void setClientHeartbeatInterval(final short heartbeatInterval) {
		session.setClientHeartbeatInterval(heartbeatInterval);
	}

	public void setDnd(final String device, final boolean doNotDisturb,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTASetDnd(device, doNotDisturb);
		session.send(req, priv, handler);
	}

	public void setFwd(final String device, final short forwardingType,
			final boolean forwardingOn, final String forwardingDestination,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final CSTAForwardingInfo forward = new CSTAForwardingInfo(
				forwardingType, forwardingOn, forwardingDestination);
		final TsapiRequest req = new CSTASetFwd(device, forward);
		session.send(req, priv, handler);
	}

	public void setHeartbeatInterval(final short heartbeatInterval,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new ACSSetHeartbeatInterval(heartbeatInterval);

		session.send(req, priv, handler);
	}

	public void setHeartbeatTimeoutListener(
			final ITsapiHeartbeatTimeoutListener listener) {
		session.setHeartbeatTimeoutListener(listener);
	}

	public void setMsgWaitingInd(final String device, final boolean messages,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTASetMwi(device, messages);
		session.send(req, priv, handler);
	}

	public void setPrivileges(final String xmlData, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidArgumentException {
		try {
			final TsapiRequest req = new ACSSetPrivileges(xmlData);

			session.send(req, priv, handler);
		} catch (final Exception e) {
			if (e instanceof TsapiInvalidArgumentException)
				throw new TsapiInvalidArgumentException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"setPrivileges failure: " + e);

			if (!(e instanceof ITsapiException))
				return;
			throw new TsapiPlatformException(((ITsapiException) e)
					.getErrorType(), ((ITsapiException) e).getErrorCode(),
					"setPrivileges unexpected exception: " + e);
		}
	}

	public synchronized void shutdown() {
		Tsapi.log.info("tsapi.shutdown() called (inService = " + inService
				+ ")" + " for " + evtHandler);

		if (!inService)
			return;
		inService = false;
		session.close();
	}

	public void snapshotCall(final CSTAConnectionID snapshotObj,
			final CSTAPrivate priv, final ConfHandler handler) {
		final TsapiRequest req = new CSTASnapshotCall(snapshotObj);

		session.sendAsync(req, priv, handler);
	}

	public void snapshotDevice(final String snapshotObj,
			final CSTAPrivate priv, final ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTASnapshotDevice(snapshotObj);
		session.send(req, priv, handler);
	}

	public void startSystemStatusMonitoring(final CSTAPrivate priv,
			final ConfHandler handler)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTASysStatStart();
		session.send(req, priv, handler);
	}

	public void startSystemStatusMonitoring(final CSTAPrivate priv,
			final ConfHandler handler, final int filter)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTASysStatStart(filter);
		session.send(req, priv, handler);
	}

	public void stopSystemStatusMonitoring(final CSTAPrivate priv,
			final ConfHandler handler)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTASysStatStop();
		session.send(req, priv, handler);
	}

	public void transferCall(final CSTAConnectionID heldCall,
			final CSTAConnectionID activeCall, final CSTAPrivate priv,
			final ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		final TsapiRequest req = new CSTATransferCall(heldCall, activeCall);
		session.send(req, priv, handler);
	}
}
