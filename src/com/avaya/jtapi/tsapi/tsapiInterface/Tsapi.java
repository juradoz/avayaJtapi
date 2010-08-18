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
		log = Logger.getLogger(Tsapi.class);
		useTLinkIP = false;
		maxTcpSocketWait = 20;
		alternateTraceFile = null;
		refreshIntervalForTsapiPro = 100;

		prePopulateJtapiProperties();

		servers = new Vector<InetSocketAddress>();
		try {
			if (System.getProperty("com.avaya.jtapi.tsapi.servers") != null) {
				Collection<InetSocketAddress> serverEntries = JtapiUtils
						.parseTelephonyServerEntry(System.getProperty(
								"com.avaya.jtapi.tsapi.servers").trim(), 450);

				for (Object server : serverEntries) {
					addServer((InetSocketAddress) server);
				}
			}

			Properties prop = new Properties();

			InputStream in = TsapiChannelOio.getProperties();

			prop.load(in);

			initClass(null, prop);

			saveJtapiProperties.putAll(prop);

			displayProperties(System.getProperties(), saveJtapiProperties);

			in.close();

			TsapiChannelOio.getBrowser().setStartUp(false);
		} catch (Exception e) {
			try {
				if (JtapiUtils.isLog4jConfigured()) {
					log.error("failed to find/open tsapi.pro file:");
					log.error(e.getMessage(), e);
				} else {
					System.out.println("failed to find/open tsapi.pro file:"
							+ e.getMessage());
					e.printStackTrace();
				}
				String hostname = TsapiChannelOio.getBrowser()
						.getCodeBaseServer();

				InetSocketAddress addr = new InetSocketAddress(hostname, 450);
				Properties prop = new Properties();
				initClass(addr, prop);
			} catch (Exception e1) {
				try {
					if (JtapiUtils.isLog4jConfigured()) {
						log.error("failed to find codebase server:");
						log.error(e1.getMessage(), e1);
					} else {
						System.out.println("failed to find codebase server:"
								+ e.getMessage());
						e.printStackTrace();
					}

					String hostname = InetAddress.getLocalHost().getHostName();
					InetSocketAddress addr = new InetSocketAddress(hostname,
							450);
					Properties prop = new Properties();
					initClass(addr, prop);
				} catch (Exception e2) {
					if (JtapiUtils.isLog4jConfigured()) {
						log.error("enumServers: " + e2);
					} else {
						System.out.println("enumServers: " + e2);
					}
				}
			}
		}
		showImplementationVersion();
	}

	@SuppressWarnings("unchecked")
	private static void displayProperties(Properties systemProperties,
			Properties jtapiProperties) {
		Set list = null;
		if (systemProperties != null) {
			list = systemProperties.entrySet();
			log.info("System properties dump ");
			for (Object entry : list) {
				String key = (String) ((Entry) entry).getKey();

				if (!key.startsWith("com.avaya.jtapi.tsapi.")) {
					print(key, (String) ((Entry) entry).getValue());
				}
			}
		}

		log.info("Jtapi properties dump ");
		list = jtapiProperties.entrySet();
		for (Object entry : list) {
			Object key = ((Entry) entry).getKey();
			Object value = ((Entry) entry).getValue();
			if ((key.equals("traceFileSize")) || (key.equals("errorFileSize"))) {
				value = value + "M";
			} else if ((key.equals("maxWaitForSocket"))
					|| (key.equals("propertyRefreshRate"))
					|| (key.equals("callCompletionTimeout"))
					|| (key.equals("callCleanupRate"))) {
				value = value + " seconds";
			} else if (key.equals("debugLevel")) {
				try {
					int intValue = Integer.parseInt((String) value);
					if ((intValue < 0) || (intValue > 7)) {
						value = "<INVALID>";
					}
				} catch (Exception e) {
				}
			}
			print((String) key, value);
		}
	}

	public static int getAuditDumpInterval() {
		return auditDumpInterval;
	}

	public static int getAuditObjectAgeThreshold() {
		return auditObjectAgeThreshold;
	}

	public static int getCallCleanupRate() {
		return callCleanupRate;
	}

	public static int getCallCompletionTimeout() {
		return callCompletionTimeout;
	}

	public static int getGetServicesTimeout() {
		return getServicesTimeout;
	}

	private static int getIntegerProperty(String tsapiProperty,
			Properties props, String defaultValue, int currentValue) {
		int returnValue = currentValue;

		String propertyValue = props.getProperty(tsapiProperty, defaultValue);
		try {
			returnValue = Integer.parseInt(propertyValue);
			if (returnValue != currentValue) {
				log.info("Property \"" + tsapiProperty + "\" set to "
						+ returnValue);
			}
		} catch (NumberFormatException ee) {
			if (JtapiUtils.isLog4jConfigured()) {
				log.error("Invalid integer value " + propertyValue
						+ " given for property " + tsapiProperty);
				log.error(ee.getMessage(), ee);
			} else {
				System.out.println("Invalid integer value " + propertyValue
						+ " given for property " + tsapiProperty);
				ee.printStackTrace();
			}
		}

		return returnValue;
	}

	public static int getMaxTcpSocketWait() {
		return maxTcpSocketWait;
	}

	public static int getMaxThreadPoolSize() {
		return maxThreadPoolSize;
	}

	public static int getRefreshIntervalForTsapiPro() {
		return refreshIntervalForTsapiPro;
	}

	public static String[] getServices() {
		String[] services = new String[0];
		if (sessionFac != null) {
			validate(servers);
			Vector<ACSNameAddr> serv = sessionFac.enumServices(servers,
					useTLinkIP);
			services = new String[serv.size()];

			for (int i = 0; i < serv.size(); ++i) {
				services[i] = ((ACSNameAddr) serv.elementAt(i)).getServerName();
			}
		}
		return services;
	}

	public static boolean getTSDevicePerformanceOptimization() {
		return tsDevicePerformanceOptimization;
	}

	private static boolean handleVolatileConfigurationUpdate(
			String tsapiProperty, Properties prop) throws IOException {
		if (tsapiProperty.equalsIgnoreCase("debugLevel")) {
			JTAPILoggingAdapter.setTraceLoggerLevel(prop
					.getProperty(tsapiProperty));
		} else if (tsapiProperty.equalsIgnoreCase("getServicesTimeout")) {
			int value = getIntegerProperty("getServicesTimeout", prop, "10",
					getServicesTimeout);

			getServicesTimeout = value * 1000;
		} else if (tsapiProperty.equalsIgnoreCase("callCleanupRate")) {
			int value = getIntegerProperty("callCleanupRate", prop, "100",
					callCleanupRate);
			int roundedOfValue = 0;
			if (value < 10) {
				roundedOfValue = 10;
				if (JtapiUtils.isLog4jConfigured()) {
					log
							.info("value specified for property: callCleanupRate is "
									+ value
									+ ". Rounding up to multiple of 10. Final value = "
									+ roundedOfValue);
				} else {
					System.out
							.println("value specified for property: callCleanupRate is "
									+ value
									+ ". Rounding up to multiple of 10. Final value = "
									+ roundedOfValue);
				}
			} else if (value % 10 != 0) {
				if (value % 10 < 5) {
					roundedOfValue = value - value % 10;
				} else {
					roundedOfValue = value - value % 10 + 10;
				}
				if (JtapiUtils.isLog4jConfigured()) {
					log
							.info("value specified for property: callCleanupRate is "
									+ value
									+ ". Rounding up to multiple of 10. Final value = "
									+ roundedOfValue);
				} else {
					System.out
							.println("value specified for property: callCleanupRate is "
									+ value
									+ ". Rounding up to multiple of 10. Final value = "
									+ roundedOfValue);
				}
			} else {
				roundedOfValue = value;
			}
			if (value != roundedOfValue) {
				prop.setProperty(tsapiProperty, Integer
						.toString(roundedOfValue));
			}
			callCleanupRate = roundedOfValue;
		} else if (tsapiProperty.equalsIgnoreCase("callCompletionTimeout")) {
			int value = getIntegerProperty("callCompletionTimeout", prop, "15",
					callCompletionTimeout / 1000);
			callCompletionTimeout = value * 1000;
		} else if (tsapiProperty.equalsIgnoreCase("enableAuditDump")) {
			String propertyValue = prop.getProperty("enableAuditDump");
			if ((propertyValue == null)
					|| ((!propertyValue.equalsIgnoreCase(Boolean.FALSE
							.toString())) && (!propertyValue
							.equalsIgnoreCase(Boolean.TRUE.toString())))) {
				if (JtapiUtils.isLog4jConfigured()) {
					log
							.error("Need to provide either \"true\" or \"false\" value for property: enableAuditDump");
				} else {
					System.out
							.println("Need to provide either \"true\" or \"false\" value for property: enableAuditDump");
				}
			} else {
				isEnableAuditDump = Boolean.parseBoolean(propertyValue);
			}

		} else if (tsapiProperty.equalsIgnoreCase("auditDumpInterval")) {
			auditDumpInterval = getIntegerProperty("auditDumpInterval", prop,
					"3", auditDumpInterval);
		} else if (tsapiProperty.equalsIgnoreCase("auditObjectAgeThreshold")) {
			auditObjectAgeThreshold = getIntegerProperty(
					"auditObjectAgeThreshold", prop, "60",
					auditObjectAgeThreshold);
		} else if (tsapiProperty.equalsIgnoreCase("propertyRefreshRate")) {
			int newValue = Integer.parseInt(prop.getProperty(tsapiProperty,
					"100"));
			if (refreshIntervalForTsapiPro != newValue) {
				refreshIntervalForTsapiPro = newValue;
				refreshPeriodChanged = true;
			} else {
				refreshPeriodChanged = false;
			}

		} else if (tsapiProperty.equalsIgnoreCase("altTraceFile")) {
			JTAPILoggingAdapter
					.setAltTraceFile(prop.getProperty(tsapiProperty));

			String newTraceFile = alternateTraceFile;
			try {
				newTraceFile = prop.getProperty(tsapiProperty, "");
			} catch (Exception re) {
			}

			if (!newTraceFile.equals(alternateTraceFile)) {
				alternateTraceFile = newTraceFile;
			}
		} else if (tsapiProperty.equalsIgnoreCase("traceFileCount")) {
			JTAPILoggingAdapter.setTraceFileCount(prop
					.getProperty(tsapiProperty));
		} else if (tsapiProperty.equalsIgnoreCase("traceFileSize")) {
			JTAPILoggingAdapter.setTraceFileSize(prop
					.getProperty(tsapiProperty));
		} else if (tsapiProperty.equalsIgnoreCase("errorFile")) {
			JTAPILoggingAdapter.setErrorFile(prop.getProperty(tsapiProperty));
		} else if (tsapiProperty.equalsIgnoreCase("errorFileCount")) {
			JTAPILoggingAdapter.setErrorFileCount(prop
					.getProperty(tsapiProperty));
		} else if (tsapiProperty.equalsIgnoreCase("errorFileSize")) {
			JTAPILoggingAdapter.setErrorFileSize(prop
					.getProperty(tsapiProperty));
		} else if (tsapiProperty.equalsIgnoreCase("perfFile")) {
			JTAPILoggingAdapter.setPerfFile(prop.getProperty(tsapiProperty));
		} else if (tsapiProperty.equalsIgnoreCase("perfFileCount")) {
			JTAPILoggingAdapter.setPerfFileCount(prop
					.getProperty(tsapiProperty));
		} else if (tsapiProperty.equalsIgnoreCase("perfFileSize")) {
			JTAPILoggingAdapter
					.setPerfFileSize(prop.getProperty(tsapiProperty));
		} else if (tsapiProperty.equalsIgnoreCase("performanceWindow")) {
			PerfStatisticsCollector.setPerformanceWindow(Integer.parseInt(prop
					.getProperty(tsapiProperty)));
		} else if (tsapiProperty
				.equalsIgnoreCase("unsolicitedHandlingTimeThreshold")) {
			PerfStatisticsCollector.setUnsolicitedHandlingTimeThreshold(Long
					.parseLong(prop.getProperty(tsapiProperty)));
		} else if (tsapiProperty
				.equalsIgnoreCase("serviceRequestTurnaroundTimeThreshold")) {
			PerfStatisticsCollector
					.setServiceRequestTurnaroundTimeThreshold(Long
							.parseLong(prop.getProperty(tsapiProperty)));
		} else if (tsapiProperty.equalsIgnoreCase("queueLengthThreshold")) {
			PerfStatisticsCollector.setQueueLengthThreshold(Long.parseLong(prop
					.getProperty(tsapiProperty)));
		} else if (tsapiProperty.equalsIgnoreCase("messageLatencyThreshold")) {
			PerfStatisticsCollector.setMessageLatencyThreshold(Long
					.parseLong(prop.getProperty(tsapiProperty)));
		} else if (tsapiProperty.equalsIgnoreCase("maxThreadPoolSize")) {
			try {
				maxThreadPoolSize = Integer.parseInt(prop.getProperty(
						tsapiProperty, "20"));
			} catch (Exception e) {
				if (JtapiUtils.isLog4jConfigured()) {
					log.error("Invalid value encountered for " + tsapiProperty
							+ ". Setting to default value", e);
				} else {
					System.out.println("Invalid value encountered for "
							+ tsapiProperty + ". Setting to default value."
							+ e.getMessage());
				}
			}
		} else if (tsapiProperty
				.equalsIgnoreCase("tsDevicePerformanceOptimization")) {
			String propertyValue = prop.getProperty(tsapiProperty, "false");

			tsDevicePerformanceOptimization = Boolean.valueOf(propertyValue)
					.booleanValue();
		} else if (tsapiProperty
				.equalsIgnoreCase("handleCstaEventTimeThreshold")) {
			int threshold;
			try {
				threshold = Integer.parseInt(prop.getProperty(tsapiProperty,
						"250"));
			} catch (Exception re) {
				threshold = Integer.parseInt("250");
			}

			if (threshold < 0) {
				if (JtapiUtils.isLog4jConfigured()) {
					log
							.info("Requested setting for property \"handleCstaEventTimeThreshold\" ("
									+ threshold + ") is invalid.");
				} else {
					System.out
							.println("Requested setting for property \"handleCstaEventTimeThreshold\" ("
									+ threshold + ") is invalid.");
				}
				threshold = Integer.parseInt("250");
			}

			CSTATSProvider.setHandleCSTAEventTimeThreshold(threshold);
		} else {
			return false;
		}

		return true;
	}

	private static void initClass(InetSocketAddress address, Properties prop)
			throws IOException {
		Enumeration<?> eprop = prop.propertyNames();

		while (eprop.hasMoreElements()) {
			String tsapiProperty = (String) eprop.nextElement();

			if (tsapiProperty.startsWith("[")) {
				prop.remove(tsapiProperty);
			} else if (!handleVolatileConfigurationUpdate(tsapiProperty, prop)) {
				if (tsapiProperty.equalsIgnoreCase("useTlinkIP")) {
					try {
						int temp = Integer.parseInt(prop.getProperty(
								tsapiProperty, "0"));
						if (temp == 1) {
							useTLinkIP = true;
						}

					} catch (Exception re) {
					}

				} else if (tsapiProperty
						.equalsIgnoreCase("enable_PreserveRedirectedVDNs")) {
					try {
						int temp = Integer.parseInt(prop.getProperty(
								tsapiProperty, "0"));
						patch_enable_PreserveRedirectedVDNAsUNKNOWN = temp > 0;
					} catch (Exception re) {
						patch_enable_PreserveRedirectedVDNAsUNKNOWN = false;
					}

				} else if (tsapiProperty.equalsIgnoreCase("maxWaitForSocket")) {
					try {
						maxTcpSocketWait = Integer.parseInt(prop.getProperty(
								tsapiProperty, "0"));
					} catch (Exception re) {
						maxTcpSocketWait = 0;
					}
				} else if ((!tsapiProperty
						.equalsIgnoreCase("trustStoreLocation"))
						&& (!tsapiProperty
								.equalsIgnoreCase("trustStorePassword"))) {
					if (!tsapiProperty
							.equalsIgnoreCase("verifyServerCertificate")) {
						if (tsapiProperty.regionMatches(true, 0, "Alternates",
								0, 10)) {
							String valueString = prop
									.getProperty(tsapiProperty);
							if (valueString == null) {
								if (JtapiUtils.isLog4jConfigured()) {
									log.info("Error parsing property \""
											+ tsapiProperty + "\"; "
											+ "could not read property value.");
								} else {
									System.out
											.println("Error parsing property \""
													+ tsapiProperty
													+ "\"; "
													+ "could not read property value.");
								}
							} else {
								TsapiAlternateTlinkEntriesList alternateTlinkEntriesList = TsapiAlternateTlinkEntriesList
										.Instance();
								try {
									alternateTlinkEntriesList
											.addAlternateTlinkEntry(
													tsapiProperty, valueString);
								} catch (TsapiPropertiesException e) {
									if (JtapiUtils.isLog4jConfigured()) {
										log.error(e.getMessage(), e);
									} else {
										System.out.println(e.getMessage());
									}
								}
							}
						} else {
							String hostname;
							try {
								hostname = tsapiProperty.trim();
							} catch (NoSuchElementException e) {
								if (JtapiUtils.isLog4jConfigured()) {
									log.error(e.getMessage(), e);
								} else {
									System.out.println(e.getMessage());
								}
								continue;
							}

							int port;
							try {
								port = Integer.parseInt(prop.getProperty(
										tsapiProperty, Integer.toString(450))
										.trim());
							} catch (Exception re2) {
								if (JtapiUtils.isLog4jConfigured()) {
									log.error("Invalid name value pair in : "
											+ tsapiProperty);
								} else {
									System.out
											.println("Invalid name value pair in : "
													+ tsapiProperty);
								}
								port = 450;
							}

							InetSocketAddress addr = new InetSocketAddress(
									hostname, port);
							servers.addElement(addr);
						}
					}
				}
			}
		}
		JTAPILoggingAdapter.initializeLogging();

		log = Logger.getLogger(Tsapi.class);

		sessionFac = TsapiSessionFactory.getTsapiSessionFactory(prop);
	}

	public static boolean isEnableAuditDump() {
		return isEnableAuditDump;
	}

	public static boolean isPatch_enable_PreserveRedirectedVDNAsUNKNOWN() {
		return patch_enable_PreserveRedirectedVDNAsUNKNOWN;
	}

	public static boolean isRefreshPeriodChanged() {
		return refreshPeriodChanged;
	}

	private static void prePopulateJtapiProperties() {
		saveJtapiProperties.put("altTraceFile", "");
		saveJtapiProperties.put("traceFileCount", Integer.valueOf(10));
		saveJtapiProperties.put("traceFileSize", Integer.valueOf(50));
		saveJtapiProperties.put("errorFile", "");
		saveJtapiProperties.put("errorFileCount", Integer.valueOf(10));
		saveJtapiProperties.put("errorFileSize", Integer.valueOf(50));
		saveJtapiProperties.put("debugLevel", "0");
		saveJtapiProperties.put("maxWaitForSocket", "20");
		saveJtapiProperties.put("propertyRefreshRate", Integer.valueOf(100));

		String value = "";
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		if (loader != null) {
			URL url = loader.getResource("avayaprca.jks");
			if (url != null) {
				value = url.getFile();
			}
		}
		saveJtapiProperties.put("trustStoreLocation", value);

		saveJtapiProperties.put("trustStorePassword", "password");
		saveJtapiProperties.put("verifyServerCertificate", "false");
		saveJtapiProperties.put("useTlinkIP", "0");
		saveJtapiProperties.put("tsDevicePerformanceOptimization", "false");
		saveJtapiProperties.put("maxThreadPoolSize", "20");
		saveJtapiProperties.put("callCleanupRate", "100");
		saveJtapiProperties.put("enableAuditDump", "false");
		saveJtapiProperties.put("getServicesTimeout", "10");
		saveJtapiProperties.put("callCompletionTimeout", "15");
	}

	private static void print(String key, Object value) {
		if (key.toLowerCase().indexOf("password") == -1) {
			log.info(key + "=" + value);
		} else {
			log.info(key + "=****");
		}
	}

	public static void setCallCleanupRate(int callCleanupRate) {
		Tsapi.callCleanupRate = callCleanupRate;
	}

	public static void setCallCompletionTimeout(int callCompletionTimeout) {
		Tsapi.callCompletionTimeout = callCompletionTimeout;
	}

	static void showImplementationVersion() {
		log.info("JTAPI Package Version: 5.2.0.483");
	}

	public static void updateVolatileConfigurationValues() {
		try {
			Properties prop = new Properties();

			InputStream in = TsapiChannelOio.getProperties();

			if (in == null) {
				return;
			}
			prop.load(in);
			in.close();

			if (prop.equals(saveJtapiProperties)) {
				return;
			}

			prePopulateJtapiProperties();

			Enumeration<?> eprop = prop.propertyNames();

			isEnableAuditDump = false;

			getServicesTimeout = Integer.parseInt("10") * 1000;

			callCleanupRate = Integer.parseInt("100");

			callCompletionTimeout = Integer.parseInt("15") * 1000;

			while (eprop.hasMoreElements()) {
				handleVolatileConfigurationUpdate((String) eprop.nextElement(),
						prop);
			}
			saveJtapiProperties.putAll(prop);

			JTAPILoggingAdapter.updateLoggingProperties();

			displayProperties(null, saveJtapiProperties);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
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

	public static void addServer(InetSocketAddress address) {
		servers.add(address);
	}

	private static void validate(Vector<InetSocketAddress> tServers) {
		if ((tServers == null) || (tServers.size() == 0)) {
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
	}

	protected Tsapi() {
	}

	public Tsapi(String tlink, String login, String passwd,
			Vector<TsapiVendor> vendors, TsapiUnsolicitedHandler handler) {
	}

	public void alternateCall(CSTAConnectionID activeCall,
			CSTAConnectionID otherCall, CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAAlternateCall(activeCall, otherCall);
		session.send(req, priv, handler);
	}

	public void answerCall(CSTAConnectionID alertingCall, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAAnswerCall(alertingCall);
		session.send(req, priv, handler);
	}

	public void callCompletion(short feature, CSTAConnectionID call,
			CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTACallCompletion(feature, call);
		session.send(req, priv, handler);
	}

	public void cancelRouteCallback(int routeRegisterReqID, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTARouteRegisterCancel(routeRegisterReqID);
		session.send(req, priv, handler);
	}

	public void clearCall(CSTAConnectionID call, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAClearCall(call);
		session.send(req, priv, handler);
	}

	public void clearConnection(CSTAConnectionID connection, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAClearConnection(connection);
		session.send(req, priv, handler);
	}

	public void conferenceCall(CSTAConnectionID heldCall,
			CSTAConnectionID activeCall, CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAConferenceCall(heldCall, activeCall);
		session.send(req, priv, handler);
	}

	public void consultationCall(CSTAConnectionID activeCall,
			String calledDevice, CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAConsultationCall(activeCall, calledDevice);
		session.send(req, priv, handler);
	}

	public void CSTAEscapeService(CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAEscapeSvc();
		session.send(req, priv, handler);
	}

	public CSTAEvent CSTAQueryCallMonitor() throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAQueryCallMonitor();
		return session.send(req, null);
	}

	public void CSTASendPrivateEvent(CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTASendPrivateEv();

		session.sendAsync(req, priv);
	}

	public void deflectCall(CSTAConnectionID deflectCall, String calledDevice,
			CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTADeflectCall(deflectCall, calledDevice);
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
		TsapiRequest req = new CSTAGetAPICaps();
		return session.send(req, null);
	}

	public CSTAEvent getDeviceList(int index, short level)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAGetDeviceList(index, level);
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

	public void groupPickupCall(String pickupDevice, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAGroupPickupCall(null, pickupDevice);
		session.send(req, priv, handler);
	}

	public boolean heartbeatIsEnabled() {
		return session.heartbeatIsEnabled();
	}

	public void holdCall(CSTAConnectionID activeCall, boolean reservation,
			CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAHoldCall(activeCall, reservation);
		session.send(req, priv, handler);
	}

	public void init(String tlink, String login, String passwd,
			Vector<TsapiVendor> vendors, TsapiUnsolicitedHandler handler) {
		evtHandler = handler;
		try {
			sessionFac.setDebugID(evtHandler.toString());
			validate(servers);
			ACSNameAddr nameAddr = sessionFac.findTlink(tlink, servers,
					useTLinkIP);

			tlink = nameAddr.getServerName();
			InetSocketAddress addr = nameAddr.createInetSocketAddress();

			session = sessionFac.getTsapiSession(addr, tlink);
			session.setHandler(evtHandler);

			session.startSession(tlink, login, passwd, vendors, 10000);
		} catch (TsapiPlatformException e) {
			log.error("Tsapi<init>: " + e);

			if (session != null) {
				session.close();
			}

			throw e;
		} catch (Exception e) {
			log.error("Tsapi<init>: " + e);

			if (session != null) {
				session.close();
			}

			throw new TsapiPlatformException(4, 0, "initialization failed");
		}

		inService = true;
	}

	public boolean isInService() {
		return inService;
	}

	public void makeCall(String callingDevice, String calledDevice,
			CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAMakeCall(callingDevice, calledDevice);
		session.send(req, priv, handler);
	}

	public void makePredictiveCall(String callingDevice, String calledDevice,
			short allocationState, CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAMakePredictiveCall(callingDevice,
				calledDevice, allocationState);

		session.send(req, priv, handler);
	}

	public CSTAEvent monitorCall(CSTAConnectionID call,
			CSTAMonitorFilter monitorFilter, CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAMonitorCall(call, monitorFilter);
		return session.send(req, priv);
	}

	public CSTAEvent monitorCallsViaDevice(String deviceID,
			CSTAMonitorFilter monitorFilter, CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAMonitorCallsViaDevice(deviceID,
				monitorFilter);
		return session.send(req, priv);
	}

	public CSTAEvent monitorDevice(String deviceID,
			CSTAMonitorFilter monitorFilter, CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAMonitorDevice(deviceID, monitorFilter);
		return session.send(req, priv);
	}

	public void monitorStop(int monitorCrossRefID, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAMonitorStop(monitorCrossRefID);
		if (handler != null) {
			session.sendAsync(req, priv, handler);
		} else {
			session.send(req, priv);
		}
	}

	public void pickupCall(CSTAConnectionID deflectCall, String calledDevice,
			CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAPickupCall(deflectCall, calledDevice);
		session.send(req, priv, handler);
	}

	public void queryAgentState(String agentDevice, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAQueryAgentState(agentDevice);
		session.send(req, priv, handler);
	}

	public CSTAEvent queryDeviceInfo(String device, CSTAPrivate priv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAQueryDeviceInfo(device);
		return session.send(req, priv);
	}

	public void queryDeviceInfoAsync(String device, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAQueryDeviceInfo(device);
		session.sendAsync(req, priv, handler);
	}

	public void queryDoNotDisturb(String device, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAQueryDnd(device);
		session.send(req, priv, handler);
	}

	public void queryFwd(String device, CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAQueryFwd(device);
		session.send(req, priv, handler);
	}

	public void queryMsgWaitingInd(String device, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAQueryMwi(device);
		session.send(req, priv, handler);
	}

	public void reconnectCall(CSTAConnectionID activeCall,
			CSTAConnectionID heldCall, CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTAReconnectCall(activeCall, heldCall);
		session.send(req, priv, handler);
	}

	public void registerRouteCallback(String routingDevice, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTARouteRegisterReq(routingDevice);
		session.send(req, priv, handler);
	}

	public void requestPrivileges(CSTAPrivate priv, ConfHandler handler) {
		TsapiRequest req = new ACSRequestPrivileges();
		try {
			session.send(req, priv, handler);
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"requestPrivileges failure: " + e);
			}

			throw new TsapiPlatformException(4, 0,
					"request privileges unexpected exception " + e);
		}
	}

	public void requestSystemStatus(CSTAPrivate priv, ConfHandler handler)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTASysStatReq();
		session.send(req, priv, handler);
	}

	public void retrieveCall(CSTAConnectionID heldCall, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTARetrieveCall(heldCall);
		session.send(req, priv, handler);
	}

	public void routeEnd(int routeRegisterReqID, int routingCrossRefID,
			short errorValue, CSTAPrivate priv) {
		TsapiRequest req;
		if (session.getApiVersion().equals("1")) {
			req = new CSTARouteEndRequest(routeRegisterReqID,
					routingCrossRefID, errorValue);
		} else {
			req = new CSTARouteEndRequestInv(routeRegisterReqID,
					routingCrossRefID, errorValue);
		}

		session.sendAsync(req, priv);
	}

	public void selectRoute(int routeRegisterReqID, int routingCrossRefID,
			String routeSelected, int remainRetry, String isdnSetupMessage,
			boolean routeUsedReq, CSTAPrivate priv) {
		byte[] isdnBuf = isdnSetupMessage.getBytes();
		TsapiRequest req;
		if (session.getApiVersion().equals("1")) {
			req = new CSTARouteSelectRequest(routeRegisterReqID,
					routingCrossRefID, routeSelected, remainRetry, isdnBuf,
					routeUsedReq);
		} else {
			req = new CSTARouteSelectRequestInv(routeRegisterReqID,
					routingCrossRefID, routeSelected, remainRetry, isdnBuf,
					routeUsedReq);
		}

		session.sendAsync(req, priv);
	}

	public void setAgentState(String agentDevice, short agentMode,
			String agentID, String acdGroup, String agentPassword,
			CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTASetAgentState(agentDevice, agentMode,
				agentID, acdGroup, agentPassword);
		session.send(req, priv, handler);
	}

	public void setClientHeartbeatInterval(short heartbeatInterval) {
		session.setClientHeartbeatInterval(heartbeatInterval);
	}

	public void setDnd(String device, boolean doNotDisturb, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTASetDnd(device, doNotDisturb);
		session.send(req, priv, handler);
	}

	public void setFwd(String device, short forwardingType,
			boolean forwardingOn, String forwardingDestination,
			CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		CSTAForwardingInfo forward = new CSTAForwardingInfo(forwardingType,
				forwardingOn, forwardingDestination);
		TsapiRequest req = new CSTASetFwd(device, forward);
		session.send(req, priv, handler);
	}

	public void setHeartbeatInterval(short heartbeatInterval, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new ACSSetHeartbeatInterval(heartbeatInterval);

		session.send(req, priv, handler);
	}

	public void setHeartbeatTimeoutListener(
			ITsapiHeartbeatTimeoutListener listener) {
		session.setHeartbeatTimeoutListener(listener);
	}

	public void setMsgWaitingInd(String device, boolean messages,
			CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTASetMwi(device, messages);
		session.send(req, priv, handler);
	}

	public void setPrivileges(String xmlData, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidArgumentException {
		try {
			TsapiRequest req = new ACSSetPrivileges(xmlData);

			session.send(req, priv, handler);
		} catch (Exception e) {
			if (e instanceof TsapiInvalidArgumentException) {
				throw new TsapiInvalidArgumentException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"setPrivileges failure: " + e);
			}

			if (!(e instanceof ITsapiException)) {
				return;
			}
			throw new TsapiPlatformException(((ITsapiException) e)
					.getErrorType(), ((ITsapiException) e).getErrorCode(),
					"setPrivileges unexpected exception: " + e);
		}
	}

	public synchronized void shutdown() {
		log.info("tsapi.shutdown() called (inService = " + inService + ")"
				+ " for " + evtHandler);

		if (!inService) {
			return;
		}
		inService = false;
		session.close();
	}

	public void snapshotCall(CSTAConnectionID snapshotObj, CSTAPrivate priv,
			ConfHandler handler) {
		TsapiRequest req = new CSTASnapshotCall(snapshotObj);

		session.sendAsync(req, priv, handler);
	}

	public void snapshotDevice(String snapshotObj, CSTAPrivate priv,
			ConfHandler handler) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiProviderUnavailableException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTASnapshotDevice(snapshotObj);
		session.send(req, priv, handler);
	}

	public void startSystemStatusMonitoring(CSTAPrivate priv,
			ConfHandler handler) throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTASysStatStart();
		session.send(req, priv, handler);
	}

	public void startSystemStatusMonitoring(CSTAPrivate priv,
			ConfHandler handler, int filter)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTASysStatStart(filter);
		session.send(req, priv, handler);
	}

	public void stopSystemStatusMonitoring(CSTAPrivate priv, ConfHandler handler)
			throws TsapiProviderUnavailableException,
			TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiInvalidPartyException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		TsapiRequest req = new CSTASysStatStop();
		session.send(req, priv, handler);
	}

	public void transferCall(CSTAConnectionID heldCall,
			CSTAConnectionID activeCall, CSTAPrivate priv, ConfHandler handler)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiProviderUnavailableException, TsapiInvalidPartyException,
			TsapiPrivilegeViolationException, TsapiResourceUnavailableException {
		TsapiRequest req = new CSTATransferCall(heldCall, activeCall);
		session.send(req, priv, handler);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.Tsapi JD-Core Version: 0.5.4
 */