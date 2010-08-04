/*      */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*      */ 
/*      */ import java.io.IOException;
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
/*      */ 
/*      */ public class Tsapi
/*      */ {
/*      */   private static Logger log;
/*      */   private TsapiUnsolicitedHandler evtHandler;
/*      */   protected boolean inService;
/*  109 */   private static Properties saveJtapiProperties = new Properties();
/*      */ 
/*  111 */   private TsapiSession session = null;
/*  112 */   private static TsapiSessionFactory sessionFac = null;
/*      */   private static int refreshIntervalForTsapiPro;
/*  120 */   private static boolean refreshPeriodChanged = false;
/*      */   private static boolean useTLinkIP;
/*      */   private static String alternateTraceFile;
/*      */   private static int maxTcpSocketWait;
/*  128 */   private static int getServicesTimeout = Integer.parseInt("10") * 1000;
/*      */ 
/*  130 */   private static int callCleanupRate = Integer.parseInt("100");
/*      */ 
/*  133 */   private static int callCompletionTimeout = Integer.parseInt("15") * 1000;
/*      */ 
/*  135 */   private static boolean isEnableAuditDump = false;
/*      */ 
/*  139 */   private static int auditDumpInterval = Integer.parseInt("3");
/*      */ 
/*  145 */   private static int auditObjectAgeThreshold = Integer.parseInt("60");
/*      */ 
/*  151 */   private static boolean tsDevicePerformanceOptimization = Boolean.valueOf("false").booleanValue();
/*      */ 
/*  158 */   private static boolean patch_enable_PreserveRedirectedVDNAsUNKNOWN = false;
/*      */   protected static Vector<InetSocketAddress> servers;
/*  171 */   private static int maxThreadPoolSize = Integer.parseInt("20");
/*      */ 
/*      */   protected Tsapi()
/*      */   {
/*      */   }
/*      */ 
/*      */   public Tsapi(String tlink, String login, String passwd, Vector<TsapiVendor> vendors, TsapiUnsolicitedHandler handler)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void init(String tlink, String login, String passwd, Vector<TsapiVendor> vendors, TsapiUnsolicitedHandler handler)
/*      */   {
/*  183 */     this.evtHandler = handler;
/*      */     try
/*      */     {
/*  187 */       sessionFac.setDebugID(this.evtHandler.toString());
/*  188 */       validate(servers);
/*  189 */       ACSNameAddr nameAddr = sessionFac.findTlink(tlink, servers, useTLinkIP);
/*      */ 
/*  195 */       tlink = nameAddr.getServerName();
/*  196 */       InetSocketAddress addr = nameAddr.createInetSocketAddress();
/*      */ 
/*  198 */       this.session = sessionFac.getTsapiSession(addr, tlink);
/*  199 */       this.session.setHandler(this.evtHandler);
/*      */ 
/*  201 */       this.session.startSession(tlink, login, passwd, vendors, 10000);
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  205 */       log.error("Tsapi<init>: " + e);
/*      */ 
/*  207 */       if (this.session != null) {
/*  208 */         this.session.close();
/*      */       }
/*      */ 
/*  211 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  215 */       log.error("Tsapi<init>: " + e);
/*      */ 
/*  217 */       if (this.session != null) {
/*  218 */         this.session.close();
/*      */       }
/*      */ 
/*  221 */       throw new TsapiPlatformException(4, 0, "initialization failed");
/*      */     }
/*      */ 
/*  225 */     this.inService = true;
/*      */   }
/*      */   private static void validate(Vector<InetSocketAddress> tServers) {
/*  228 */     if ((tServers == null) || (tServers.size() == 0))
/*  229 */       throw new TsapiPlatformException(4, 0, "Unable to find target telephony server(s). Please either \n1. create a tsapi.pro file with one or more valid telephony server entries in the system classpath or at location '" + System.getProperty("user.dir") + "' or \n" + "2. pass valid server(s) via the providerString when invoking JtapiPeer.getProvider()\n" + "3. pass the server(s) via the " + "com.avaya.jtapi.tsapi.servers" + " system property");
/*      */   }
/*      */ 
/*      */   public static void addServer(InetSocketAddress address)
/*      */   {
/*  238 */     servers.add(address);
/*      */   }
/*      */ 
/*      */   public String getVendor() {
/*  242 */     return this.session.getTheVendor();
/*      */   }
/*      */ 
/*      */   public byte[] getVendorVersion()
/*      */   {
/*  247 */     return this.session.getVendorVersion();
/*      */   }
/*      */ 
/*      */   public String getServerID()
/*      */   {
/*  255 */     return this.session.getServerID();
/*      */   }
/*      */ 
/*      */   public boolean heartbeatIsEnabled()
/*      */   {
/*  264 */     return this.session.heartbeatIsEnabled();
/*      */   }
/*      */ 
/*      */   public void enableHeartbeat()
/*      */   {
/*  272 */     this.session.enableHeartbeat();
/*      */   }
/*      */ 
/*      */   public void disableHeartbeat()
/*      */   {
/*  280 */     this.session.disableHeartbeat();
/*      */   }
/*      */ 
/*      */   public void setHeartbeatTimeoutListener(ITsapiHeartbeatTimeoutListener listener)
/*      */   {
/*  291 */     this.session.setHeartbeatTimeoutListener(listener);
/*      */   }
/*      */ 
/*      */   static void showImplementationVersion()
/*      */   {
/*  299 */     log.info("JTAPI Package Version: 5.2.0.483");
/*      */   }
/*      */ 
/*      */   private static void prePopulateJtapiProperties()
/*      */   {
/*  412 */     saveJtapiProperties.put("altTraceFile", "");
/*  413 */     saveJtapiProperties.put("traceFileCount", Integer.valueOf(10));
/*  414 */     saveJtapiProperties.put("traceFileSize", Integer.valueOf(50));
/*  415 */     saveJtapiProperties.put("errorFile", "");
/*  416 */     saveJtapiProperties.put("errorFileCount", Integer.valueOf(10));
/*  417 */     saveJtapiProperties.put("errorFileSize", Integer.valueOf(50));
/*  418 */     saveJtapiProperties.put("debugLevel", "0");
/*  419 */     saveJtapiProperties.put("maxWaitForSocket", "20");
/*  420 */     saveJtapiProperties.put("propertyRefreshRate", Integer.valueOf(100));
/*      */ 
/*  422 */     String value = "";
/*  423 */     ClassLoader loader = ClassLoader.getSystemClassLoader();
/*  424 */     if (loader != null)
/*      */     {
/*  426 */       URL url = loader.getResource("avayaprca.jks");
/*  427 */       if (url != null)
/*  428 */         value = url.getFile();
/*      */     }
/*  430 */     saveJtapiProperties.put("trustStoreLocation", value);
/*      */ 
/*  432 */     saveJtapiProperties.put("trustStorePassword", "password");
/*  433 */     saveJtapiProperties.put("verifyServerCertificate", "false");
/*  434 */     saveJtapiProperties.put("useTlinkIP", "0");
/*  435 */     saveJtapiProperties.put("tsDevicePerformanceOptimization", "false");
/*  436 */     saveJtapiProperties.put("maxThreadPoolSize", "20");
/*  437 */     saveJtapiProperties.put("callCleanupRate", "100");
/*  438 */     saveJtapiProperties.put("enableAuditDump", "false");
/*  439 */     saveJtapiProperties.put("getServicesTimeout", "10");
/*  440 */     saveJtapiProperties.put("callCompletionTimeout", "15");
/*      */   }
/*      */ 
/*      */   private static void initClass(InetSocketAddress address, Properties prop) throws IOException
/*      */   {
/*  445 */     Enumeration eprop = prop.propertyNames();
/*      */ 
/*  447 */     while (eprop.hasMoreElements())
/*      */     {
/*  449 */       String tsapiProperty = (String)eprop.nextElement();
/*      */ 
/*  451 */       if (tsapiProperty.startsWith("["))
/*      */       {
/*  454 */         prop.remove(tsapiProperty);
/*      */       }
/*  456 */       else if (!handleVolatileConfigurationUpdate(tsapiProperty, prop))
/*      */       {
/*  460 */         if (tsapiProperty.equalsIgnoreCase("useTlinkIP")) {
/*      */           try
/*      */           {
/*  463 */             int temp = Integer.parseInt(prop.getProperty(tsapiProperty, "0"));
/*  464 */             if (temp == 1) {
/*  465 */               useTLinkIP = true;
/*      */             }
/*      */ 
/*      */           }
/*      */           catch (Exception re)
/*      */           {
/*      */           }
/*      */ 
/*      */         }
/*  475 */         else if (tsapiProperty.equalsIgnoreCase("enable_PreserveRedirectedVDNs")) {
/*      */           try
/*      */           {
/*  478 */             int temp = Integer.parseInt(prop.getProperty(tsapiProperty, "0"));
/*  479 */             patch_enable_PreserveRedirectedVDNAsUNKNOWN = temp > 0;
/*      */           }
/*      */           catch (Exception re)
/*      */           {
/*  483 */             patch_enable_PreserveRedirectedVDNAsUNKNOWN = false;
/*      */           }
/*      */ 
/*      */         }
/*  492 */         else if (tsapiProperty.equalsIgnoreCase("maxWaitForSocket")) {
/*      */           try
/*      */           {
/*  495 */             maxTcpSocketWait = Integer.parseInt(prop.getProperty(tsapiProperty, "0"));
/*      */           }
/*      */           catch (Exception re) {
/*  498 */             maxTcpSocketWait = 0;
/*      */           }
/*      */         }
/*  501 */         else if ((!tsapiProperty.equalsIgnoreCase("trustStoreLocation")) && (!tsapiProperty.equalsIgnoreCase("trustStorePassword"))) if (!tsapiProperty.equalsIgnoreCase("verifyServerCertificate"))
/*      */           {
/*  508 */             if (tsapiProperty.regionMatches(true, 0, "Alternates", 0, 10))
/*      */             {
/*  510 */               String valueString = prop.getProperty(tsapiProperty);
/*  511 */               if (valueString == null)
/*      */               {
/*  515 */                 if (JtapiUtils.isLog4jConfigured())
/*  516 */                   log.info("Error parsing property \"" + tsapiProperty + "\"; " + "could not read property value.");
/*      */                 else
/*  518 */                   System.out.println("Error parsing property \"" + tsapiProperty + "\"; " + "could not read property value.");
/*      */               }
/*      */               else
/*      */               {
/*  522 */                 TsapiAlternateTlinkEntriesList alternateTlinkEntriesList = TsapiAlternateTlinkEntriesList.Instance();
/*      */                 try
/*      */                 {
/*  526 */                   alternateTlinkEntriesList.addAlternateTlinkEntry(tsapiProperty, valueString);
/*      */                 }
/*      */                 catch (TsapiPropertiesException e)
/*      */                 {
/*  532 */                   if (JtapiUtils.isLog4jConfigured())
/*  533 */                     log.error(e.getMessage(), e);
/*      */                   else {
/*  535 */                     System.out.println(e.getMessage());
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/*      */               String hostname;
/*      */               try
/*      */               {
/*  545 */                 hostname = tsapiProperty.trim();
/*      */               }
/*      */               catch (NoSuchElementException e)
/*      */               {
/*  551 */                 if (JtapiUtils.isLog4jConfigured())
/*  552 */                   log.error(e.getMessage(), e);
/*      */                 else
/*  554 */                   System.out.println(e.getMessage());
continue;
/*      */               }
/*      */ 
/*  555 */               
/*      */               int port;
/*      */               try
/*      */               {
/*  561 */                 port = Integer.parseInt(prop.getProperty(tsapiProperty, Integer.toString(450)).trim());
/*      */               }
/*      */               catch (Exception re2)
/*      */               {
/*  566 */                 if (JtapiUtils.isLog4jConfigured()) {
/*  567 */                   log.error("Invalid name value pair in : " + tsapiProperty);
/*      */                 }
/*      */                 else {
/*  570 */                   System.out.println("Invalid name value pair in : " + tsapiProperty);
/*      */                 }
/*  572 */                 port = 450;
/*      */               }
/*      */ 
/*  575 */               InetSocketAddress addr = new InetSocketAddress(hostname, port);
/*  576 */               servers.addElement(addr);
/*      */             }
/*      */           } 
/*      */       }
/*      */     }
/*  580 */     JTAPILoggingAdapter.initializeLogging();
/*      */ 
/*  582 */     log = Logger.getLogger(Tsapi.class);
/*      */ 
/*  584 */     sessionFac = TsapiSessionFactory.getTsapiSessionFactory(prop);
/*      */   }
/*      */ 
/*      */   private static void displayProperties(Properties systemProperties, Properties jtapiProperties)
/*      */   {
/*  590 */     Set list = null;
/*  591 */     if (systemProperties != null)
/*      */     {
/*  593 */       list = systemProperties.entrySet();
/*  594 */       log.info("System properties dump ");
/*  595 */       for (Object entry : list)
/*      */       {
/*  597 */         String key = (String)((Entry) entry).getKey();
/*      */ 
/*  599 */         if (!key.startsWith("com.avaya.jtapi.tsapi."))
/*      */         {
/*  601 */           print(key, (String)((Entry) entry).getValue());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  606 */     log.info("Jtapi properties dump ");
/*  607 */     list = jtapiProperties.entrySet();
/*  608 */     for (Object entry : list)
/*      */     {
/*  610 */       Object key = ((Entry) entry).getKey();
/*  611 */       Object value = ((Entry) entry).getValue();
/*  612 */       if ((key.equals("traceFileSize")) || (key.equals("errorFileSize"))) {
/*  613 */         value = value + "M";
/*      */       }
/*  615 */       else if ((key.equals("maxWaitForSocket")) || (key.equals("propertyRefreshRate")) || (key.equals("callCompletionTimeout")) || (key.equals("callCleanupRate")))
/*      */       {
/*  617 */         value = value + " seconds";
/*      */       }
/*  619 */       else if (key.equals("debugLevel")) {
/*      */         try {
/*  621 */           int intValue = Integer.parseInt((String)value);
/*  622 */           if ((intValue < 0) || (intValue > 7)) {
/*  623 */             value = "<INVALID>";
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */       }
/*  630 */       print((String)key, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void print(String key, Object value) {
/*  635 */     if (key.toLowerCase().indexOf("password") == -1) {
/*  636 */       log.info(key + "=" + value);
/*      */     }
/*      */     else
/*  639 */       log.info(key + "=****");
/*      */   }
/*      */ 
/*      */   private static int getIntegerProperty(String tsapiProperty, Properties props, String defaultValue, int currentValue)
/*      */   {
/*  647 */     int returnValue = currentValue;
/*      */ 
/*  649 */     String propertyValue = props.getProperty(tsapiProperty, defaultValue);
/*      */     try
/*      */     {
/*  653 */       returnValue = Integer.parseInt(propertyValue);
/*  654 */       if (returnValue != currentValue)
/*      */       {
/*  656 */         log.info("Property \"" + tsapiProperty + "\" set to " + returnValue);
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException ee)
/*      */     {
/*  661 */       if (JtapiUtils.isLog4jConfigured()) {
/*  662 */         log.error("Invalid integer value " + propertyValue + " given for property " + tsapiProperty);
/*  663 */         log.error(ee.getMessage(), ee);
/*      */       } else {
/*  665 */         System.out.println("Invalid integer value " + propertyValue + " given for property " + tsapiProperty);
/*  666 */         ee.printStackTrace();
/*      */       }
/*      */     }
/*      */ 
/*  670 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private static boolean handleVolatileConfigurationUpdate(String tsapiProperty, Properties prop)
/*      */     throws IOException
/*      */   {
/*  700 */     if (tsapiProperty.equalsIgnoreCase("debugLevel"))
/*      */     {
/*  702 */       JTAPILoggingAdapter.setTraceLoggerLevel(prop.getProperty(tsapiProperty));
/*      */     }
/*  704 */     else if (tsapiProperty.equalsIgnoreCase("getServicesTimeout")) {
/*  705 */       int value = getIntegerProperty("getServicesTimeout", prop, "10", getServicesTimeout);
/*      */ 
/*  707 */       getServicesTimeout = value * 1000;
/*      */     }
/*  709 */     else if (tsapiProperty.equalsIgnoreCase("callCleanupRate")) {
/*  710 */       int value = getIntegerProperty("callCleanupRate", prop, "100", callCleanupRate);
/*  711 */       int roundedOfValue = 0;
/*  712 */       if (value < 10) {
/*  713 */         roundedOfValue = 10;
/*  714 */         if (JtapiUtils.isLog4jConfigured())
/*  715 */           log.info("value specified for property: callCleanupRate is " + value + ". Rounding up to multiple of 10. Final value = " + roundedOfValue);
/*      */         else
/*  717 */           System.out.println("value specified for property: callCleanupRate is " + value + ". Rounding up to multiple of 10. Final value = " + roundedOfValue);
/*      */       }
/*  719 */       else if (value % 10 != 0) {
/*  720 */         if (value % 10 < 5)
/*  721 */           roundedOfValue = value - value % 10;
/*      */         else
/*  723 */           roundedOfValue = value - value % 10 + 10;
/*  724 */         if (JtapiUtils.isLog4jConfigured())
/*  725 */           log.info("value specified for property: callCleanupRate is " + value + ". Rounding up to multiple of 10. Final value = " + roundedOfValue);
/*      */         else
/*  727 */           System.out.println("value specified for property: callCleanupRate is " + value + ". Rounding up to multiple of 10. Final value = " + roundedOfValue);
/*      */       } else {
/*  729 */         roundedOfValue = value;
/*      */       }
/*  731 */       if (value != roundedOfValue)
/*  732 */         prop.setProperty(tsapiProperty, Integer.toString(roundedOfValue));
/*  733 */       callCleanupRate = roundedOfValue;
/*  734 */     } else if (tsapiProperty.equalsIgnoreCase("callCompletionTimeout")) {
/*  735 */       int value = getIntegerProperty("callCompletionTimeout", prop, "15", callCompletionTimeout / 1000);
/*  736 */       callCompletionTimeout = value * 1000;
/*      */     }
/*  738 */     else if (tsapiProperty.equalsIgnoreCase("enableAuditDump"))
/*      */     {
/*  740 */       String propertyValue = prop.getProperty("enableAuditDump");
/*  741 */       if ((propertyValue == null) || ((!propertyValue.equalsIgnoreCase(Boolean.FALSE.toString())) && (!propertyValue.equalsIgnoreCase(Boolean.TRUE.toString()))))
/*      */       {
/*  743 */         if (JtapiUtils.isLog4jConfigured())
/*  744 */           log.error("Need to provide either \"true\" or \"false\" value for property: enableAuditDump");
/*      */         else
/*  746 */           System.out.println("Need to provide either \"true\" or \"false\" value for property: enableAuditDump");
/*      */       }
/*  748 */       else isEnableAuditDump = Boolean.parseBoolean(propertyValue);
/*      */ 
/*      */     }
/*  752 */     else if (tsapiProperty.equalsIgnoreCase("auditDumpInterval"))
/*      */     {
/*  754 */       auditDumpInterval = getIntegerProperty("auditDumpInterval", prop, "3", auditDumpInterval);
/*      */     }
/*  758 */     else if (tsapiProperty.equalsIgnoreCase("auditObjectAgeThreshold"))
/*      */     {
/*  760 */       auditObjectAgeThreshold = getIntegerProperty("auditObjectAgeThreshold", prop, "60", auditObjectAgeThreshold);
/*      */     }
/*  765 */     else if (tsapiProperty.equalsIgnoreCase("propertyRefreshRate"))
/*      */     {
/*  767 */       int newValue = Integer.parseInt(prop.getProperty(tsapiProperty, "100"));
/*  768 */       if (refreshIntervalForTsapiPro != newValue)
/*      */       {
/*  771 */         refreshIntervalForTsapiPro = newValue;
/*  772 */         refreshPeriodChanged = true;
/*      */       }
/*      */       else {
/*  775 */         refreshPeriodChanged = false;
/*      */       }
/*      */ 
/*      */     }
/*  782 */     else if (tsapiProperty.equalsIgnoreCase("altTraceFile"))
/*      */     {
/*  784 */       JTAPILoggingAdapter.setAltTraceFile(prop.getProperty(tsapiProperty));
/*      */ 
/*  786 */       String newTraceFile = alternateTraceFile;
/*      */       try
/*      */       {
/*  789 */         newTraceFile = prop.getProperty(tsapiProperty, "");
/*      */       }
/*      */       catch (Exception re)
/*      */       {
/*      */       }
/*      */ 
/*  795 */       if (!newTraceFile.equals(alternateTraceFile))
/*      */       {
/*  798 */         alternateTraceFile = newTraceFile;
/*      */       }
/*  800 */     } else if (tsapiProperty.equalsIgnoreCase("traceFileCount")) {
/*  801 */       JTAPILoggingAdapter.setTraceFileCount(prop.getProperty(tsapiProperty));
/*  802 */     } else if (tsapiProperty.equalsIgnoreCase("traceFileSize")) {
/*  803 */       JTAPILoggingAdapter.setTraceFileSize(prop.getProperty(tsapiProperty));
/*  804 */     } else if (tsapiProperty.equalsIgnoreCase("errorFile")) {
/*  805 */       JTAPILoggingAdapter.setErrorFile(prop.getProperty(tsapiProperty));
/*  806 */     } else if (tsapiProperty.equalsIgnoreCase("errorFileCount")) {
/*  807 */       JTAPILoggingAdapter.setErrorFileCount(prop.getProperty(tsapiProperty));
/*  808 */     } else if (tsapiProperty.equalsIgnoreCase("errorFileSize")) {
/*  809 */       JTAPILoggingAdapter.setErrorFileSize(prop.getProperty(tsapiProperty));
/*  810 */     } else if (tsapiProperty.equalsIgnoreCase("perfFile")) {
/*  811 */       JTAPILoggingAdapter.setPerfFile(prop.getProperty(tsapiProperty));
/*  812 */     } else if (tsapiProperty.equalsIgnoreCase("perfFileCount")) {
/*  813 */       JTAPILoggingAdapter.setPerfFileCount(prop.getProperty(tsapiProperty));
/*  814 */     } else if (tsapiProperty.equalsIgnoreCase("perfFileSize")) {
/*  815 */       JTAPILoggingAdapter.setPerfFileSize(prop.getProperty(tsapiProperty));
/*  816 */     } else if (tsapiProperty.equalsIgnoreCase("performanceWindow")) {
/*  817 */       PerfStatisticsCollector.setPerformanceWindow(Integer.parseInt(prop.getProperty(tsapiProperty)));
/*  818 */     } else if (tsapiProperty.equalsIgnoreCase("unsolicitedHandlingTimeThreshold")) {
/*  819 */       PerfStatisticsCollector.setUnsolicitedHandlingTimeThreshold(Long.parseLong(prop.getProperty(tsapiProperty)));
/*  820 */     } else if (tsapiProperty.equalsIgnoreCase("serviceRequestTurnaroundTimeThreshold")) {
/*  821 */       PerfStatisticsCollector.setServiceRequestTurnaroundTimeThreshold(Long.parseLong(prop.getProperty(tsapiProperty)));
/*  822 */     } else if (tsapiProperty.equalsIgnoreCase("queueLengthThreshold")) {
/*  823 */       PerfStatisticsCollector.setQueueLengthThreshold(Long.parseLong(prop.getProperty(tsapiProperty)));
/*  824 */     } else if (tsapiProperty.equalsIgnoreCase("messageLatencyThreshold")) {
/*  825 */       PerfStatisticsCollector.setMessageLatencyThreshold(Long.parseLong(prop.getProperty(tsapiProperty)));
/*  826 */     } else if (tsapiProperty.equalsIgnoreCase("maxThreadPoolSize")) {
/*      */       try {
/*  828 */         maxThreadPoolSize = Integer.parseInt(prop.getProperty(tsapiProperty, "20"));
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  832 */         if (JtapiUtils.isLog4jConfigured())
/*  833 */           log.error("Invalid value encountered for " + tsapiProperty + ". Setting to default value", e);
/*      */         else {
/*  835 */           System.out.println("Invalid value encountered for " + tsapiProperty + ". Setting to default value." + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*  839 */     else if (tsapiProperty.equalsIgnoreCase("tsDevicePerformanceOptimization"))
/*      */     {
/*  841 */       String propertyValue = prop.getProperty(tsapiProperty, "false");
/*      */ 
/*  844 */       tsDevicePerformanceOptimization = Boolean.valueOf(propertyValue).booleanValue();
/*      */     }
/*  846 */     else if (tsapiProperty.equalsIgnoreCase("handleCstaEventTimeThreshold"))
/*      */     {
/*      */       int threshold;
/*      */       try
/*      */       {
/*  852 */         threshold = Integer.parseInt(prop.getProperty(tsapiProperty, "250"));
/*      */       }
/*      */       catch (Exception re)
/*      */       {
/*  857 */         threshold = Integer.parseInt("250");
/*      */       }
/*      */ 
/*  860 */       if (threshold < 0)
/*      */       {
/*  864 */         if (JtapiUtils.isLog4jConfigured())
/*  865 */           log.info("Requested setting for property \"handleCstaEventTimeThreshold\" (" + threshold + ") is invalid.");
/*      */         else {
/*  867 */           System.out.println("Requested setting for property \"handleCstaEventTimeThreshold\" (" + threshold + ") is invalid.");
/*      */         }
/*  869 */         threshold = Integer.parseInt("250");
/*      */       }
/*      */ 
/*  872 */       CSTATSProvider.setHandleCSTAEventTimeThreshold(threshold);
/*      */     }
/*      */     else
/*      */     {
/*  877 */       return false;
/*      */     }
/*      */ 
/*  882 */     return true;
/*      */   }
/*      */ 
/*      */   public static void updateVolatileConfigurationValues()
/*      */   {
/*      */     try
/*      */     {
/*  889 */       Properties prop = new Properties();
/*      */ 
/*  891 */       InputStream in = TsapiChannelOio.getProperties();
/*      */ 
/*  894 */       if (in == null) {
/*  895 */         return;
/*      */       }
/*  897 */       prop.load(in);
/*  898 */       in.close();
/*      */ 
/*  904 */       if (prop.equals(saveJtapiProperties))
/*      */       {
/*  906 */         return;
/*      */       }
/*      */ 
/*  910 */       prePopulateJtapiProperties();
/*      */ 
/*  912 */       Enumeration eprop = prop.propertyNames();
/*      */ 
/*  914 */       isEnableAuditDump = false;
/*      */ 
/*  917 */       getServicesTimeout = Integer.parseInt("10") * 1000;
/*      */ 
/*  920 */       callCleanupRate = Integer.parseInt("100");
/*      */ 
/*  922 */       callCompletionTimeout = Integer.parseInt("15") * 1000;
/*      */ 
/*  924 */       while (eprop.hasMoreElements())
/*      */       {
/*  926 */         handleVolatileConfigurationUpdate((String)eprop.nextElement(), prop);
/*      */       }
/*  928 */       saveJtapiProperties.putAll(prop);
/*      */ 
/*  930 */       JTAPILoggingAdapter.updateLoggingProperties();
/*      */ 
/*  933 */       displayProperties(null, saveJtapiProperties);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  937 */       log.error(e.getMessage(), e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void shutdown()
/*      */   {
/*  943 */     log.info("tsapi.shutdown() called (inService = " + this.inService + ")" + " for " + this.evtHandler);
/*      */ 
/*  945 */     if (!this.inService) {
/*  946 */       return;
/*      */     }
/*  948 */     this.inService = false;
/*  949 */     this.session.close();
/*      */   }
/*      */ 
/*      */   public static int getRefreshIntervalForTsapiPro()
/*      */   {
/*  955 */     return refreshIntervalForTsapiPro;
/*      */   }
/*      */ 
/*      */   public static String[] getServices()
/*      */   {
/*  960 */     String[] services = new String[0];
/*  961 */     if (sessionFac != null) {
/*  962 */       validate(servers);
/*  963 */       Vector serv = sessionFac.enumServices(servers, useTLinkIP);
/*  964 */       services = new String[serv.size()];
/*      */ 
/*  966 */       for (int i = 0; i < serv.size(); ++i) {
/*  967 */         services[i] = ((ACSNameAddr)serv.elementAt(i)).getServerName();
/*      */       }
/*      */     }
/*  970 */     return services;
/*      */   }
/*      */ 
/*      */   public CSTAEvent getApiCaps()
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  981 */     TsapiRequest req = new CSTAGetAPICaps();
/*  982 */     return this.session.send(req, null);
/*      */   }
/*      */ 
/*      */   public CSTAEvent CSTAQueryCallMonitor()
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/*  993 */     TsapiRequest req = new CSTAQueryCallMonitor();
/*  994 */     return this.session.send(req, null);
/*      */   }
/*      */ 
/*      */   public CSTAEvent getDeviceList(int index, short level)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1005 */     TsapiRequest req = new CSTAGetDeviceList(index, level);
/* 1006 */     return this.session.send(req, null);
/*      */   }
/*      */ 
/*      */   public CSTAEvent queryDeviceInfo(String device, CSTAPrivate priv)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1017 */     TsapiRequest req = new CSTAQueryDeviceInfo(device);
/* 1018 */     return this.session.send(req, priv);
/*      */   }
/*      */ 
/*      */   public void queryDeviceInfoAsync(String device, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1029 */     TsapiRequest req = new CSTAQueryDeviceInfo(device);
/* 1030 */     this.session.sendAsync(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public CSTAEvent monitorCall(CSTAConnectionID call, CSTAMonitorFilter monitorFilter, CSTAPrivate priv)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1041 */     TsapiRequest req = new CSTAMonitorCall(call, monitorFilter);
/* 1042 */     return this.session.send(req, priv);
/*      */   }
/*      */ 
/*      */   public CSTAEvent monitorDevice(String deviceID, CSTAMonitorFilter monitorFilter, CSTAPrivate priv)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1053 */     TsapiRequest req = new CSTAMonitorDevice(deviceID, monitorFilter);
/* 1054 */     return this.session.send(req, priv);
/*      */   }
/*      */ 
/*      */   public CSTAEvent monitorCallsViaDevice(String deviceID, CSTAMonitorFilter monitorFilter, CSTAPrivate priv)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1065 */     TsapiRequest req = new CSTAMonitorCallsViaDevice(deviceID, monitorFilter);
/* 1066 */     return this.session.send(req, priv);
/*      */   }
/*      */ 
/*      */   public void monitorStop(int monitorCrossRefID, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1077 */     TsapiRequest req = new CSTAMonitorStop(monitorCrossRefID);
/* 1078 */     if (handler != null)
/* 1079 */       this.session.sendAsync(req, priv, handler);
/*      */     else
/* 1081 */       this.session.send(req, priv);
/*      */   }
/*      */ 
/*      */   public void answerCall(CSTAConnectionID alertingCall, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1092 */     TsapiRequest req = new CSTAAnswerCall(alertingCall);
/* 1093 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void clearCall(CSTAConnectionID call, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1104 */     TsapiRequest req = new CSTAClearCall(call);
/* 1105 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void clearConnection(CSTAConnectionID connection, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1116 */     TsapiRequest req = new CSTAClearConnection(connection);
/* 1117 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void conferenceCall(CSTAConnectionID heldCall, CSTAConnectionID activeCall, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1128 */     TsapiRequest req = new CSTAConferenceCall(heldCall, activeCall);
/* 1129 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void holdCall(CSTAConnectionID activeCall, boolean reservation, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1140 */     TsapiRequest req = new CSTAHoldCall(activeCall, reservation);
/* 1141 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void makeCall(String callingDevice, String calledDevice, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1152 */     TsapiRequest req = new CSTAMakeCall(callingDevice, calledDevice);
/* 1153 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void retrieveCall(CSTAConnectionID heldCall, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1164 */     TsapiRequest req = new CSTARetrieveCall(heldCall);
/* 1165 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void setAgentState(String agentDevice, short agentMode, String agentID, String acdGroup, String agentPassword, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1177 */     TsapiRequest req = new CSTASetAgentState(agentDevice, agentMode, agentID, acdGroup, agentPassword);
/* 1178 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void queryAgentState(String agentDevice, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1189 */     TsapiRequest req = new CSTAQueryAgentState(agentDevice);
/* 1190 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void registerRouteCallback(String routingDevice, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1201 */     TsapiRequest req = new CSTARouteRegisterReq(routingDevice);
/* 1202 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void cancelRouteCallback(int routeRegisterReqID, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1213 */     TsapiRequest req = new CSTARouteRegisterCancel(routeRegisterReqID);
/* 1214 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void queryFwd(String device, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1225 */     TsapiRequest req = new CSTAQueryFwd(device);
/* 1226 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void queryDoNotDisturb(String device, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1237 */     TsapiRequest req = new CSTAQueryDnd(device);
/* 1238 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void queryMsgWaitingInd(String device, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1249 */     TsapiRequest req = new CSTAQueryMwi(device);
/* 1250 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void setFwd(String device, short forwardingType, boolean forwardingOn, String forwardingDestination, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1262 */     CSTAForwardingInfo forward = new CSTAForwardingInfo(forwardingType, forwardingOn, forwardingDestination);
/* 1263 */     TsapiRequest req = new CSTASetFwd(device, forward);
/* 1264 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void setDnd(String device, boolean doNotDisturb, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1275 */     TsapiRequest req = new CSTASetDnd(device, doNotDisturb);
/* 1276 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void setMsgWaitingInd(String device, boolean messages, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1287 */     TsapiRequest req = new CSTASetMwi(device, messages);
/* 1288 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void callCompletion(short feature, CSTAConnectionID call, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1299 */     TsapiRequest req = new CSTACallCompletion(feature, call);
/* 1300 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void transferCall(CSTAConnectionID heldCall, CSTAConnectionID activeCall, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1311 */     TsapiRequest req = new CSTATransferCall(heldCall, activeCall);
/* 1312 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void reconnectCall(CSTAConnectionID activeCall, CSTAConnectionID heldCall, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1323 */     TsapiRequest req = new CSTAReconnectCall(activeCall, heldCall);
/* 1324 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void pickupCall(CSTAConnectionID deflectCall, String calledDevice, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1335 */     TsapiRequest req = new CSTAPickupCall(deflectCall, calledDevice);
/* 1336 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void makePredictiveCall(String callingDevice, String calledDevice, short allocationState, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1347 */     TsapiRequest req = new CSTAMakePredictiveCall(callingDevice, calledDevice, allocationState);
/*      */ 
/* 1349 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void groupPickupCall(String pickupDevice, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1360 */     TsapiRequest req = new CSTAGroupPickupCall(null, pickupDevice);
/* 1361 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void deflectCall(CSTAConnectionID deflectCall, String calledDevice, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1372 */     TsapiRequest req = new CSTADeflectCall(deflectCall, calledDevice);
/* 1373 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void consultationCall(CSTAConnectionID activeCall, String calledDevice, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1384 */     TsapiRequest req = new CSTAConsultationCall(activeCall, calledDevice);
/* 1385 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void alternateCall(CSTAConnectionID activeCall, CSTAConnectionID otherCall, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1396 */     TsapiRequest req = new CSTAAlternateCall(activeCall, otherCall);
/* 1397 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void CSTAEscapeService(CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1408 */     TsapiRequest req = new CSTAEscapeSvc();
/* 1409 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void snapshotDevice(String snapshotObj, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1420 */     TsapiRequest req = new CSTASnapshotDevice(snapshotObj);
/* 1421 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void snapshotCall(CSTAConnectionID snapshotObj, CSTAPrivate priv, ConfHandler handler)
/*      */   {
/* 1426 */     TsapiRequest req = new CSTASnapshotCall(snapshotObj);
/*      */ 
/* 1429 */     this.session.sendAsync(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void selectRoute(int routeRegisterReqID, int routingCrossRefID, String routeSelected, int remainRetry, String isdnSetupMessage, boolean routeUsedReq, CSTAPrivate priv)
/*      */   {
/* 1435 */     byte[] isdnBuf = isdnSetupMessage.getBytes();
/*      */     TsapiRequest req;
/* 1437 */     if (this.session.getApiVersion().equals("1"))
/*      */     {
/* 1439 */       req = new CSTARouteSelectRequest(routeRegisterReqID, routingCrossRefID, routeSelected, remainRetry, isdnBuf, routeUsedReq);
/*      */     }
/*      */     else
/*      */     {
/* 1445 */       req = new CSTARouteSelectRequestInv(routeRegisterReqID, routingCrossRefID, routeSelected, remainRetry, isdnBuf, routeUsedReq);
/*      */     }
/*      */ 
/* 1453 */     this.session.sendAsync(req, priv);
/*      */   }
/*      */ 
/*      */   public void routeEnd(int routeRegisterReqID, int routingCrossRefID, short errorValue, CSTAPrivate priv)
/*      */   {
/*      */     TsapiRequest req;
/* 1459 */     if (this.session.getApiVersion().equals("1"))
/*      */     {
/* 1461 */       req = new CSTARouteEndRequest(routeRegisterReqID, routingCrossRefID, errorValue);
/*      */     }
/*      */     else
/*      */     {
/* 1466 */       req = new CSTARouteEndRequestInv(routeRegisterReqID, routingCrossRefID, errorValue);
/*      */     }
/*      */ 
/* 1472 */     this.session.sendAsync(req, priv);
/*      */   }
/*      */ 
/*      */   public void CSTASendPrivateEvent(CSTAPrivate priv)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1483 */     TsapiRequest req = new CSTASendPrivateEv();
/*      */ 
/* 1488 */     this.session.sendAsync(req, priv);
/*      */   }
/*      */ 
/*      */   public void setHeartbeatInterval(short heartbeatInterval, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiProviderUnavailableException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1506 */     TsapiRequest req = new ACSSetHeartbeatInterval(heartbeatInterval);
/*      */ 
/* 1509 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void setClientHeartbeatInterval(short heartbeatInterval)
/*      */   {
/* 1520 */     this.session.setClientHeartbeatInterval(heartbeatInterval);
/*      */   }
/*      */ 
/*      */   public static boolean isPatch_enable_PreserveRedirectedVDNAsUNKNOWN()
/*      */   {
/* 1527 */     return patch_enable_PreserveRedirectedVDNAsUNKNOWN;
/*      */   }
/*      */ 
/*      */   public static int getMaxTcpSocketWait()
/*      */   {
/* 1534 */     return maxTcpSocketWait;
/*      */   }
/*      */ 
/*      */   public boolean isInService()
/*      */   {
/* 1541 */     return this.inService;
/*      */   }
/*      */ 
/*      */   public static int getGetServicesTimeout()
/*      */   {
/* 1548 */     return getServicesTimeout;
/*      */   }
/*      */ 
/*      */   public static int getCallCleanupRate()
/*      */   {
/* 1555 */     return callCleanupRate;
/*      */   }
/*      */ 
/*      */   public static void setCallCleanupRate(int callCleanupRate)
/*      */   {
/* 1563 */     callCleanupRate = callCleanupRate;
/*      */   }
/*      */ 
/*      */   public static int getCallCompletionTimeout()
/*      */   {
/* 1570 */     return callCompletionTimeout;
/*      */   }
/*      */ 
/*      */   public static void setCallCompletionTimeout(int callCompletionTimeout)
/*      */   {
/* 1578 */     callCompletionTimeout = callCompletionTimeout;
/*      */   }
/*      */ 
/*      */   public static boolean isEnableAuditDump()
/*      */   {
/* 1585 */     return isEnableAuditDump;
/*      */   }
/*      */ 
/*      */   public static int getAuditDumpInterval() {
/* 1589 */     return auditDumpInterval;
/*      */   }
/*      */ 
/*      */   public static int getAuditObjectAgeThreshold() {
/* 1593 */     return auditObjectAgeThreshold;
/*      */   }
/*      */ 
/*      */   public static boolean getTSDevicePerformanceOptimization()
/*      */   {
/* 1600 */     return tsDevicePerformanceOptimization;
/*      */   }
/*      */ 
/*      */   public void requestPrivileges(CSTAPrivate priv, ConfHandler handler)
/*      */   {
/* 1610 */     TsapiRequest req = new ACSRequestPrivileges();
/*      */     try
/*      */     {
/* 1616 */       this.session.send(req, priv, handler);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1621 */       if (e instanceof ITsapiException)
/*      */       {
/* 1626 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "requestPrivileges failure: " + e);
/*      */       }
/*      */ 
/* 1632 */       throw new TsapiPlatformException(4, 0, "request privileges unexpected exception " + e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setPrivileges(String xmlData, CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/*      */     try
/*      */     {
/* 1649 */       TsapiRequest req = new ACSSetPrivileges(xmlData);
/*      */ 
/* 1653 */       this.session.send(req, priv, handler);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1657 */       if (e instanceof TsapiInvalidArgumentException)
/*      */       {
/* 1659 */         throw new TsapiInvalidArgumentException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setPrivileges failure: " + e);
/*      */       }
/*      */ 
/* 1663 */       if (!(e instanceof ITsapiException))
/*      */         return;
/* 1665 */       throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setPrivileges unexpected exception: " + e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean isRefreshPeriodChanged()
/*      */   {
/* 1677 */     return refreshPeriodChanged;
/*      */   }
/*      */ 
/*      */   public static int getMaxThreadPoolSize() {
/* 1681 */     return maxThreadPoolSize;
/*      */   }
/*      */ 
/*      */   public void startSystemStatusMonitoring(CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1692 */     TsapiRequest req = new CSTASysStatStart();
/* 1693 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void startSystemStatusMonitoring(CSTAPrivate priv, ConfHandler handler, int filter)
/*      */     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1703 */     TsapiRequest req = new CSTASysStatStart(filter);
/* 1704 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void stopSystemStatusMonitoring(CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1715 */     TsapiRequest req = new CSTASysStatStop();
/* 1716 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   public void requestSystemStatus(CSTAPrivate priv, ConfHandler handler)
/*      */     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1726 */     TsapiRequest req = new CSTASysStatReq();
/* 1727 */     this.session.send(req, priv, handler);
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  304 */     log = Logger.getLogger(Tsapi.class);
/*  305 */     useTLinkIP = false;
/*  306 */     maxTcpSocketWait = 20;
/*  307 */     alternateTraceFile = null;
/*  308 */     refreshIntervalForTsapiPro = 100;
/*      */ 
/*  310 */     prePopulateJtapiProperties();
/*      */ 
/*  312 */     servers = new Vector();
/*      */     try
/*      */     {
/*  316 */       if (System.getProperty("com.avaya.jtapi.tsapi.servers") != null) {
/*  317 */         Collection serverEntries = JtapiUtils.parseTelephonyServerEntry(System.getProperty("com.avaya.jtapi.tsapi.servers").trim(), 450);
/*      */ 
/*  321 */         for (Object server : serverEntries) {
/*  322 */           addServer((InetSocketAddress) server);
/*      */         }
/*      */       }
/*      */ 
/*  326 */       Properties prop = new Properties();
/*      */ 
/*  328 */       InputStream in = TsapiChannelOio.getProperties();
/*      */ 
/*  330 */       prop.load(in);
/*      */ 
/*  332 */       initClass(null, prop);
/*      */ 
/*  335 */       saveJtapiProperties.putAll(prop);
/*      */ 
/*  337 */       displayProperties(System.getProperties(), saveJtapiProperties);
/*      */ 
/*  339 */       in.close();
/*      */ 
/*  341 */       TsapiChannelOio.getBrowser().setStartUp(false);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       try
/*      */       {
/*  354 */         if (JtapiUtils.isLog4jConfigured())
/*      */         {
/*  356 */           log.error("failed to find/open tsapi.pro file:");
/*  357 */           log.error(e.getMessage(), e);
/*      */         }
/*      */         else {
/*  360 */           System.out.println("failed to find/open tsapi.pro file:" + e.getMessage());
/*  361 */           e.printStackTrace();
/*      */         }
/*  363 */         String hostname = TsapiChannelOio.getBrowser().getCodeBaseServer();
/*      */ 
/*  365 */         InetSocketAddress addr = new InetSocketAddress(hostname, 450);
/*  366 */         Properties prop = new Properties();
/*  367 */         initClass(addr, prop);
/*      */       }
/*      */       catch (Exception e1)
/*      */       {
/*      */         try
/*      */         {
/*  376 */           if (JtapiUtils.isLog4jConfigured()) {
/*  377 */             log.error("failed to find codebase server:");
/*  378 */             log.error(e1.getMessage(), e1);
/*      */           } else {
/*  380 */             System.out.println("failed to find codebase server:" + e.getMessage());
/*  381 */             e.printStackTrace();
/*      */           }
/*      */ 
/*  384 */           String hostname = InetAddress.getLocalHost().getHostName();
/*  385 */           InetSocketAddress addr = new InetSocketAddress(hostname, 450);
/*  386 */           Properties prop = new Properties();
/*  387 */           initClass(addr, prop);
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/*  394 */           if (JtapiUtils.isLog4jConfigured())
/*  395 */             log.error("enumServers: " + e2);
/*      */           else {
/*  397 */             System.out.println("enumServers: " + e2);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  402 */     showImplementationVersion();
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.Tsapi
 * JD-Core Version:    0.5.4
 */