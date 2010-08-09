 package com.avaya.jtapi.tsapi.impl.core;
 
 import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.CallClassifierInfo;
import com.avaya.jtapi.tsapi.ITsapiException;
import com.avaya.jtapi.tsapi.TSProvider;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiProviderUnavailableException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.TsapiUnableToSendException;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import com.avaya.jtapi.tsapi.csta1.CSTAGetAPICapsConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAGetDeviceListConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryCallMonitorConfEvent;
import com.avaya.jtapi.tsapi.csta1.HasUCID;
import com.avaya.jtapi.tsapi.csta1.LucentCallClassifierInfo;
import com.avaya.jtapi.tsapi.csta1.LucentConferencedEvent;
import com.avaya.jtapi.tsapi.csta1.LucentGetAPICapsConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentPrivateData;
import com.avaya.jtapi.tsapi.csta1.LucentQueryCallClassifier;
import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryTg;
import com.avaya.jtapi.tsapi.csta1.LucentQueryTod;
import com.avaya.jtapi.tsapi.csta1.LucentQueryTodConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentSetAdviceOfCharge;
import com.avaya.jtapi.tsapi.csta1.LucentTransferredEvent;
import com.avaya.jtapi.tsapi.csta1.LucentTrunkGroupInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV5GetAPICapsConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV7GetAPICapsConfEvent;
import com.avaya.jtapi.tsapi.impl.TsapiAddressCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiCallCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiConnCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiProviderCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiTermConnCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiTerminalCapabilities;
import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiInServiceEvent;
import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiInitializingEvent;
import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiOutOfServiceEvent;
import com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiShutdownEvent;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.ITsapiHeartbeatTimeoutListener;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiFactory;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiSession;
import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
import com.avaya.jtapi.tsapi.util.JtapiUtils;
 
 public final class TSProviderImpl extends TSProvider
   implements IDomainTracker, IDomainContainer, ITsapiHeartbeatTimeoutListener
 {
   private static Logger log = Logger.getLogger(TSProviderImpl.class);
   Tsapi tsapi;
   private final Object obsSync;
   private final Vector<TsapiProviderMonitor> monitors;
   private final Vector<TsapiProviderMonitor> providerMonitorThreads;
   private final Vector<TsapiAddressMonitor> addressMonitorThreads;
   private final Vector<TsapiTerminalMonitor> terminalMonitorThreads;
   private final Vector<TsapiCallMonitor> callMonitorThreads;
   private final Vector<TsapiRouteMonitor> routeMonitorThreads;
   private final ConnectStringData connectStringData;
   int state;
   Object replyPriv = null;
   TSEventHandler tsEHandler;
   TSAuditThread auditor;
   Timer timerThread;
   private final Hashtable<String, TSDevice> devHash;
   private final Hashtable<String, TSTrunk> trkHash;
   private final Hashtable<TSAgentKey, TSAgent> agentHash;
   boolean lucent = false;
 
   private int lucentPDV = -1;
 
   final int LUCENT_PDV_UNINITIALIZED = -1;
   private final Hashtable<Integer, TSCall> callHash;
   private final Hashtable<Integer, TSCall> nonCallHash;
   private final Hashtable<CSTAConnectionID, TSConnection> connHash;
   final Hashtable<Integer, Object> xrefHash;
   final Hashtable<Integer, Object> routeRegHash;
   private final Hashtable<Integer, Object> privXrefHash;
   TSCapabilities tsCaps;
   Vector<String> tsMonitorableDevices;
   Vector<String> tsRouteDevices;
   boolean callMonitoring;
   int[] nonCallIDArray;
   int nonCallID = 0;
 
   static int NOT_IN_USE = 0;
 
   static int IN_USE = 1;
 
   static int CSTA_HOME_WORK_TOP = 1;
 
   static int CSTA_AWAY_WORK_TOP = 2;
 
   static int CSTA_DEVICE_DEVICE_MONITOR = 3;
 
   static int CSTA_ROUTING = 4;
 
   static int GET_DEVICE_INITIAL_INDEX = -1;
 
   static int GET_DEVICE_NO_MORE_INDEX = -1;
 
   static int TSAPI_RESPONSE_TIME = 30000;
 
   static int DEFAULT_TIMEOUT = 180000;
   TSInitializationThread initThread;
   boolean securityOn = true;
 
   private String administeredSwitchSoftwareVersion = "";
 
   private String switchSoftwareVersion = "";
 
   private String offerType = "";
 
   private String serverType = "";
 
   private boolean monitorCallsViaDevice = false;
 
   private boolean enableTsapiHeartbeat = false;
 
   private static int g_instanceNumber = 0;
 
   private static Object g_lock = new Object();
 
   private int m_instanceNumber = 0;
   static int provider_count = 0;
 
   private static final Object provider_count_lock = new Object();
 
   private final Object shutdown_single_thread_lock = new Object();
 
   static int CREATEAGENT_ACCEPT_DELETED = 1;
 
   static int CREATEAGENT_REFUSE_DELETED = 2;
 
   IDomainTracker m_providerTracker = new TSDomainTracker(this);
 
   public TSProviderImpl(String _url, Vector<TsapiVendor> vendors)
   {
     setInstanceNumber();
 
     log.info("TSProvider: version '" + getProviderVersionDetails() + "', for " + this);
 
     this.state = 0;
 
     this.devHash = new Hashtable(10);
     this.trkHash = new Hashtable(10);
     this.agentHash = new Hashtable(10);
 
     this.connHash = new Hashtable(20);
 
     TtConnHash("ctor", "NO OBJECT", "NO CONNID");
     this.callHash = new Hashtable(10);
     this.nonCallHash = new Hashtable(10);
     this.xrefHash = new Hashtable(3);
 
     TtXrefHash("ctor", 0, "NO OBJECT");
     this.routeRegHash = new Hashtable(3);
     this.privXrefHash = new Hashtable(3);
     this.tsMonitorableDevices = new Vector();
     this.tsRouteDevices = new Vector();
     this.monitors = new Vector();
     this.providerMonitorThreads = new Vector();
     this.addressMonitorThreads = new Vector();
     this.terminalMonitorThreads = new Vector();
     this.callMonitorThreads = new Vector();
     this.routeMonitorThreads = new Vector();
 
     this.obsSync = new Object();
     this.nonCallIDArray = new int[100];
     this.callMonitoring = false;
 
     this.connectStringData = parseURL(_url);
     if (this.connectStringData.telephonyServers != null) {
       for (InetSocketAddress telephonyServer : this.connectStringData.telephonyServers) {
         Tsapi.addServer(telephonyServer);
       }
     }
     this.tsEHandler = new TSEventHandler(this);
 
     log.info("TSProvider: calling acsOpenStream serverID=" + this.connectStringData.serverId + " loginID=" + this.connectStringData.loginId + " passwd=******* for " + this);
 
     this.tsapi = TsapiFactory.getTsapi(this.connectStringData.serverId, this.connectStringData.loginId, this.connectStringData.password, vendors, this.tsEHandler);
 
     this.lucent = LucentPrivateData.isAvayaVendor(getVendor());
 
     this.auditor = new TSAuditThread(this);
     this.auditor.start();
 
     this.timerThread = new Timer("TsapiProReader", true);
 
     int timeInterval = Tsapi.getRefreshIntervalForTsapiPro() * 1000;
     this.timerThread.schedule(new TsapiProReaderTask(), timeInterval, timeInterval);
 
     if (this.enableTsapiHeartbeat) {
       this.tsapi.enableHeartbeat();
       this.tsapi.setHeartbeatTimeoutListener(this);
       this.enableTsapiHeartbeat = false;
     }
 
     setCapabilities(getCaps());
     setCallMonitor(getCallMonitor());
     if (this.tsCaps.sysStatStart != 0) {
       SysStatHandler handler = new SysStatHandler();
       try {
         this.tsapi.startSystemStatusMonitoring(null, handler);
       }
       catch (Exception e)
       {
         if (e instanceof ITsapiException) {
           throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "startSystemStatusMonitoring() failure: " + e.getMessage());
         }
 
         throw new TsapiPlatformException(4, 0, "startSystemStatusMonitoring() failure: " + e.getMessage());
       }
 
     }
 
     JtapiEventThreadManager.initialize();
 
     this.initThread = new TSInitializationThread(this);
     this.initThread.start();
 
     initNewProvider();
   }
 
   private ConnectStringData parseURL(String _url) {
     String serverID = _url;
     String loginID = "";
     String passwd = "";
     Collection telephonyServers = new LinkedHashSet();
     int firstSemiColon_index = _url.indexOf(';');
     serverID = _url.substring(0, firstSemiColon_index);
     if (firstSemiColon_index >= 0) {
       StringTokenizer params = new StringTokenizer(_url.substring(firstSemiColon_index + 1), ";");
 
       while (params.hasMoreTokens()) {
         StringTokenizer param = new StringTokenizer(params.nextToken(), "=");
 
         if (!param.hasMoreTokens()) {
           continue;
         }
         String key = param.nextToken();
         if (!param.hasMoreTokens()) {
           continue;
         }
         String value = param.nextToken();
 
         if ((key.equals("login")) || (key.equals("loginID"))) {
           loginID = value;
         }
         else if (key.equals("passwd")) {
           passwd = value;
         }
         else if (key.equals("servers")) {
           telephonyServers = JtapiUtils.parseTelephonyServerEntry(value, 450);
         }
       }
     }
 
     if (loginID.length() > 48) {
       throw new TsapiPlatformException(4, 0, "Username provided is more than 48 characters in length. Login ID=" + loginID);
     }
 
     if (passwd.length() > 47) {
       throw new TsapiPlatformException(4, 0, "Password provided is more than 47 characters in length. Password length=" + passwd.length());
     }
 
     return new ConnectStringData(serverID, loginID, passwd, telephonyServers, _url);
   }
 
   void dump(String indent) {
     log.trace(indent + "***** PROVIDER DUMP *****");
     log.trace(indent + "TSProvider: " + this);
 
     log.trace(indent + "TSProvider: " + this.connectStringData.serverId + ";login=" + this.connectStringData.loginId + ";passwd=*******");
 
     log.trace(indent + "TSProvider state: " + this.state);
     log.trace(indent + "TSProvider version details: " + getProviderVersionDetails());
 
     log.trace(indent + "TSProvider calls: ");
     Enumeration callEnum = this.callHash.elements();
 
     while (callEnum.hasMoreElements()) {
       TSCall call;
       try { call = (TSCall)callEnum.nextElement();
       } catch (NoSuchElementException e) {
         log.error(e.getMessage(), e);
continue;
       }
 
       call.dump(indent + " ");
     }
     log.trace(indent + "TSProvider non calls: ");
     Enumeration nonCallEnum = this.nonCallHash.elements();
 
     while (nonCallEnum.hasMoreElements()) {
       TSCall nonCall;
       try { nonCall = (TSCall)nonCallEnum.nextElement();
       } catch (NoSuchElementException e) {
         log.error(e.getMessage(), e);
continue;
       }
 
       nonCall.dump(indent + " ");
     }
 
     log.trace(indent + "TSProvider VDN Calls-to-VDN Domain Mapping: ");
 
     dumpDomainData(indent);
 
     log.trace(indent + "TSProvider devices: ");
     Enumeration deviceEnum = this.devHash.elements();
 
     while (deviceEnum.hasMoreElements()) {
       TSDevice device;
       try { device = (TSDevice)deviceEnum.nextElement();
       } catch (NoSuchElementException e) {
         log.error(e.getMessage(), e);
continue;
       }
 
       device.dump(indent + " ");
     }
     log.trace(indent + "TSProvider conns: ");
     Enumeration connEnum = this.connHash.elements();
 
     while (connEnum.hasMoreElements()) {
       TSConnection conn;
       try { conn = (TSConnection)connEnum.nextElement();
       } catch (NoSuchElementException e) {
         log.error(e.getMessage(), e);
continue;
       }
 
       conn.dump(indent + " ");
     }
     log.trace(indent + "TSProvider agents: ");
     Enumeration agentEnum = this.agentHash.elements();
 
     while (agentEnum.hasMoreElements()) {
       TSAgent agent;
       try { agent = (TSAgent)agentEnum.nextElement();
       } catch (NoSuchElementException e) {
         log.error(e.getMessage(), e);
continue;
       }
 
       agent.dump(indent + " ");
     }
     log.trace(indent + "TSProvider trunks: ");
     Enumeration trkEnum = this.trkHash.elements();
 
     while (trkEnum.hasMoreElements()) {
       TSTrunk trk;
       try { trk = (TSTrunk)trkEnum.nextElement();
       } catch (NoSuchElementException e) {
         log.error(e.getMessage(), e);
continue;
       }
 
       trk.dump(indent + " ");
     }
     log.trace(indent + "TSProvider xrefs: ");
     Enumeration xrefEnum = this.xrefHash.elements();
     while (xrefEnum.hasMoreElements()) {
       try {
         log.trace(indent + "xref object: " + xrefEnum.nextElement());
       }
       catch (NoSuchElementException e) {
         log.error(e.getMessage(), e);
       }
     }
 
     log.trace(indent + "TSProvider audits: ");
     this.auditor.dump(indent + " ");
     log.trace(indent + "***** PROVIDER DUMP END *****");
   }
 
   void TtXrefHash(String s, int monitorCrossRefID, Object observed)
   {
     Tt.println("#X=" + this.xrefHash.size() + " R=" + monitorCrossRefID + " O=" + observed + " //" + s);
   }
 
   void TtConnHash(String s, Object connection, Object connID)
   {
     Tt.println("#C=" + this.connHash.size() + " I=" + connID.toString() + " C=" + connection.toString() + " //" + s);
   }
 
   String getProviderVersionDetails()
   {
     String std_string = "production build";
 
     String stdver = "5.2.0.483";
 
     String customver = "production build";
 
     return stdver + " [" + customver + "]";
   }
 
   public void initNewProvider()
   {
     synchronized (provider_count_lock) {
       provider_count += 1;
     }
   }
 
   public void finalizeOldProvider()
   {
     synchronized (provider_count_lock) {
       if (provider_count > 0) {
         provider_count -= 1;
         if (provider_count == 0)
         {
           JtapiEventThreadManager.drainThreads();
         }
       }
     }
   }
 
   public int getState()
   {
     switch (this.state)
     {
     case 0:
     case 1:
     default:
       return 17;
     case 2:
       int jtapiState = 16;
 
       if (this.tsCaps.sysStatReq != 0) {
         Vector eventList = new Vector();
         SysStatHandler handler = new SysStatHandler();
         try {
           this.tsapi.requestSystemStatus(null, handler);
         }
         catch (Exception e) {
           log.warn("Failed to get system status. Returning OUT_OF_SERVICE to be safe");
           setState(0, eventList, true);
           jtapiState = 17;
         }
         if ((handler.getSystemStatus() != 1) && (handler.getSystemStatus() != 2)) {
           setState(0, eventList, true);
           jtapiState = 17;
         }
         if (eventList.size() > 0) {
           Vector observers = getMonitors();
           for (int j = 0; j < observers.size(); ++j) {
             TsapiProviderMonitor callback = (TsapiProviderMonitor)observers.elementAt(j);
 
             callback.deliverEvents(eventList, false);
           }
         }
       }
       return jtapiState;
     case 3:
     }
     return 18;
   }
 
   public int getTsapiState()
   {
     return this.state;
   }
 
   public String getName()
   {
     return this.connectStringData.url;
   }
 
   public boolean isLucent() {
     return this.lucent;
   }
 
   public int getLucentPDV()
   {
     if (this.lucent) {
       if (this.lucentPDV == -1) {
         byte[] version = this.tsapi.getVendorVersion();
 
         if ((version.length == 0) || (version[0] != 0) || (version[(version.length - 1)] != 0))
         {
           log.info("Version bytes with no data, or missing discriminator byte or trailing NULL byte, found while decoding TSAPI private version string");
 
           this.lucentPDV = 0;
         }
         else
         {
           try
           {
             this.lucentPDV = Integer.parseInt(new String(version, 1, version.length - 2, "US-ASCII"));
           }
           catch (Exception e)
           {
             log.info("Exception occurred decoding TSAPI private version string: " + e);
 
             this.lucentPDV = 0;
           }
         }
       }
       return this.lucentPDV;
     }
     return 0;
   }
 
   public boolean isLucentV5()
   {
     return getLucentPDV() >= 5;
   }
 
   public boolean isLucentV6()
   {
     return getLucentPDV() >= 6;
   }
 
   public boolean isLucentV7()
   {
     return getLucentPDV() >= 7;
   }
 
   public boolean isLucentV8()
   {
     return getLucentPDV() >= 8;
   }
 
   public LucentTrunkGroupInfo getTrunkGroupInfo(String trunkAccessCode) throws TsapiMethodNotSupportedException
   {
     if (!isLucent()) {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     try
     {
       LucentQueryTg qtg = new LucentQueryTg(trunkAccessCode);
       Object result = sendPrivateData(qtg.makeTsapiPrivate());
 
       if (result instanceof LucentTrunkGroupInfo) {
         return (LucentTrunkGroupInfo)result;
       }
       return null;
     } catch (TsapiPlatformException e) {
       throw e;
     } catch (Exception e) {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), " service failure");
       }
 
       throw new TsapiPlatformException(4, 0, " service failure");
     }
   }
 
   public CallClassifierInfo getCallClassifierInfo()
     throws TsapiMethodNotSupportedException
   {
     if (!isLucent()) {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     try
     {
       LucentQueryCallClassifier qcc = new LucentQueryCallClassifier();
       Object result = sendPrivateData(qcc.makeTsapiPrivate());
 
       if (result instanceof LucentCallClassifierInfo) {
         return new CallClassifierInfo(((LucentCallClassifierInfo)result).numAvailPorts, ((LucentCallClassifierInfo)result).numInUsePorts);
       }
 
       return null;
     } catch (TsapiPlatformException e) {
       throw e;
     } catch (Exception e) {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), " service failure");
       }
 
       throw new TsapiPlatformException(4, 0, " service failure");
     }
   }
 
   public Date getSwitchDateAndTime() throws TsapiMethodNotSupportedException
   {
     if (!isLucent()) {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     try
     {
       LucentQueryTod qtod = new LucentQueryTod();
       Object result = sendPrivateData(qtod.makeTsapiPrivate(), null, true);
 
       if (result instanceof LucentQueryTodConfEvent) {
         LucentQueryTodConfEvent tod = (LucentQueryTodConfEvent)result;
         if (tod.getYear() < 97) {
           tod.setYear(tod.getYear() + 100);
         }
         Calendar cal = Calendar.getInstance();
         cal.set(tod.getYear(), tod.getMonth() - 1, tod.getDay(), tod.getHour(), tod.getMinute(), tod.getSecond());
 
         return cal.getTime();
       }
       return null;
     } catch (TsapiPlatformException e) {
       throw e;
     } catch (Exception e) {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), " service failure");
       }
 
       throw new TsapiPlatformException(4, 0, " service failure");
     }
   }
 
   public void setAdviceOfCharge(boolean flag)
     throws TsapiMethodNotSupportedException
   {
     if (!isLucentV5()) {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     try
     {
       LucentSetAdviceOfCharge aoc = new LucentSetAdviceOfCharge(flag);
       sendPrivateData(aoc.makeTsapiPrivate());
     } catch (TsapiPlatformException e) {
       throw e;
     } catch (Exception e) {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), " service failure");
       }
 
       throw new TsapiPlatformException(4, 0, " service failure");
     }
   }
 
   public String getVendor()
   {
     return this.tsapi.getVendor();
   }
 
   public byte[] getVendorVersion() {
     return this.tsapi.getVendorVersion();
   }
 
   public void updateAddresses()
   {
     List monitorableDevices = getMonitorableDevices();
     if ((monitorableDevices != null) && (monitorableDevices.size() != 0))
     {
       synchronized (this.tsMonitorableDevices)
       {
         for (Object element : monitorableDevices)
         {
           if (!this.tsMonitorableDevices.contains(element)) {
             this.tsMonitorableDevices.add((String) element);
           }
         }
         this.tsMonitorableDevices.retainAll(monitorableDevices);
       }
     }
     monitorableDevices = null;
   }
 
   public void setDebugPrinting(boolean enable)
   {
     boolean traceLoggingEnabled = JTAPILoggingAdapter.isTraceLoggingEnabled();
     boolean errorLoggingEnabled = Logger.getLogger("com.avaya.jtapi.tsapi").isEnabledFor(Level.ERROR);
     boolean isLog4jLoggingEnabled = JtapiUtils.isLog4jConfigured();
 
     if ((!traceLoggingEnabled) && 
       (isLog4jLoggingEnabled))
     {
       traceLoggingEnabled = true;
     }
 
     if (enable)
     {
       if (traceLoggingEnabled)
       {
         Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.TRACE);
       }
       else
       {
         JTAPILoggingAdapter.setTraceLoggerLevel("7");
         JTAPILoggingAdapter.initializeLogging();
       }
 
     }
     else
     {
       if (!traceLoggingEnabled)
         return;
       if (errorLoggingEnabled)
       {
         Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.ERROR);
       }
       else
       {
         Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.OFF);
       }
     }
   }
 
   public boolean heartbeatIsEnabled()
   {
     if (this.tsapi != null) {
       return this.tsapi.heartbeatIsEnabled();
     }
 
     return false;
   }
 
   public void enableHeartbeat()
   {
     if (this.tsapi != null) {
       this.tsapi.enableHeartbeat();
     }
     else
     {
       this.enableTsapiHeartbeat = true;
     }
   }
 
   public void disableHeartbeat()
   {
     if (this.tsapi != null)
       this.tsapi.disableHeartbeat();
   }
 
   public void setHeartbeatInterval(short heartbeatInterval)
     throws TsapiInvalidArgumentException
   {
     ConfHandler handler = new SetHeartbeatIntervalConfHandler(this);
     try {
       this.tsapi.setHeartbeatInterval(heartbeatInterval, null, handler);
     } catch (TsapiInvalidArgumentException e) {
       throw e;
     } catch (Exception e) {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setHeartbeatInterval() failure: " + e.getMessage());
       }
 
       throw new TsapiPlatformException(4, 0, "setHeartbeatInterval() failure: " + e.getMessage());
     }
   }
 
   void setClientHeartbeatInterval(short heartbeatInterval)
   {
     this.tsapi.setClientHeartbeatInterval(heartbeatInterval);
   }
 
   public void heartbeatTimeout() {
     log.info("*** Heartbeat timer expired.  Shutting down Provider. ***");
 
     shutdown();
   }
 
   public String getServerID()
   {
     return this.tsapi.getServerID();
   }
 
   public Object getPrivateData() {
     if (this.replyPriv instanceof CSTAPrivate) {
       return this.replyPriv;
     }
     return null;
   }
 
   public void setPrivateData(Object o) {
     if (o instanceof CSTAPrivate)
       this.replyPriv = o;
   }
 
   public Object sendPrivateData(CSTAPrivate data)
     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     return sendPrivateData(data, null, false);
   }
 
   Object sendPrivateData(CSTAPrivate data, ConfHandler extraHandler)
     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     return sendPrivateData(data, extraHandler, false);
   }
 
   Object sendPrivateData(CSTAPrivate data, ConfHandler extraHandler, boolean priority)
     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     if (data.tsType == 89)
     {
       ConfHandler handler;
       if (priority)
         handler = new PriorityEscapeConfHandler(this, extraHandler);
       else {
         handler = new EscapeConfHandler(this, extraHandler);
       }
       this.tsapi.CSTAEscapeService(data, handler);
       return ((EscapeConfHandler)handler).getPrivateData();
     }
     if (data.tsType == 95) {
       this.tsapi.CSTASendPrivateEvent(data);
       return null;
     }
     throw new TsapiPlatformException(3, 0, "unknown  data type [" + data.tsType + "]");
   }
 
   Vector<TSCall> doCallSnapshot(String device)
   {
     if (this.tsCaps.getSnapshotDeviceReq() == 0) {
       return null;
     }
 
     ProviderSnapshotDeviceConfHandler handler = new ProviderSnapshotDeviceConfHandler(this);
     try
     {
       this.tsapi.snapshotDevice(device, null, handler);
     }
     catch (TsapiUnableToSendException tue)
     {
       throw tue;
     }
     catch (Exception e) {
       log.error(e.getMessage(), e);
       return null;
     }
 
     return handler.cv;
   }
 
   void waitToInitialize()
   {
     if (this.state == 2) return;
     try {
       synchronized (this.initThread) {
         this.initThread.wait(DEFAULT_TIMEOUT);
       }
     } catch (InterruptedException e) {
       throw new TsapiPlatformException(4, 0, "init time-out");
     }
   }
 
   public void setSessionTimeout(int timeout)
   {
     TsapiSession.setTimeout(timeout);
   }
 
   public int getCurrentStateOfCallFromTelephonyServer(int callId)
   {
     TSCall currentCall = null;
 
     if (callId < 1) {
       throw new TsapiPlatformException(3, 0, "Please pass a Call ID value that is greater than 0.");
     }
 
     currentCall = createTSCall(callId);
 
     return getCurrentStateOfCallFromTelephonyServer(currentCall);
   }
 
   public int getCurrentStateOfCallFromTelephonyServer(TSCall call)
   {
     if (call == null) {
       throw new TsapiPlatformException(3, 0, "Call object passed in is null.");
     }
 
     log.info("Forcing a query on telephony server to check state of call - " + call);
     return call.getStateFromServer();
   }
 
   public Vector<TSCall> getTSCalls()
   {
     Vector tsCallVector = new Vector();
     Vector tsDevCallVector = null;
 
     waitToInitialize();
 
     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
       tsDevCallVector = doCallSnapshot((String)this.tsMonitorableDevices.elementAt(i));
 
       if (tsDevCallVector != null)
         for (int j = 0; j < tsDevCallVector.size(); ++j)
           if (!tsCallVector.contains(tsDevCallVector.elementAt(j)))
             tsCallVector.addElement(tsDevCallVector.elementAt(j));
     }
     Enumeration callEnum;
     synchronized (this.nonCallHash) {
       callEnum = this.nonCallHash.elements();
       while (callEnum.hasMoreElements()) {
         try {
           tsCallVector.addElement(callEnum.nextElement());
         } catch (NoSuchElementException e) {
           log.error(e.getMessage(), e);
         }
 
       }
 
     }
 
     synchronized (this.callHash) {
       callEnum = this.callHash.elements();
       while (callEnum.hasMoreElements()) {
         TSCall callVar;
         try { callVar = (TSCall)callEnum.nextElement();
         } catch (NoSuchElementException e) {
           log.error(e.getMessage(), e);
continue;
         }
 
         if (!tsCallVector.contains(callVar));
         tsCallVector.addElement(callVar);
       }
 
     }
 
     return tsCallVector;
   }
 
   public Vector<TSDevice> getTSAddressDevices()
   {
     if (!this.securityOn) {
       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Addesses can be accessed.");
     }
 
     Vector tsDeviceVector = new Vector();
 
     waitToInitialize();
 
     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
       TSDevice device = createDevice((String)this.tsMonitorableDevices.elementAt(i));
       if (device != null)
         tsDeviceVector.addElement(device);
     }
     TSDevice device = createDevice("AllRouteAddress");
     if (device != null)
       tsDeviceVector.addElement(device);
     return tsDeviceVector;
   }
 
   public Vector<TSDevice> getTSTerminalDevices()
   {
     if (!this.securityOn) {
       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Terminals can be accessed.");
     }
 
     Vector tsDeviceVector = new Vector();
 
     waitToInitialize();
 
     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
       String devName = (String)this.tsMonitorableDevices.elementAt(i);
       TSDevice device = createDevice(devName);
       if ((device != null) && (device.isTerminal()))
         tsDeviceVector.addElement(device);
     }
     return tsDeviceVector;
   }
 
   public void shutdown()
   {
     shutdown(null);
   }
 
   public void shutdown(Object privateData)
   {
     log.info("TSProvider.shutdown - attempting shutdown");
     if (this.timerThread != null) {
       this.timerThread.cancel();
     }
     this.timerThread = null;
 
     synchronized (this.shutdown_single_thread_lock)
     {
       if (this.state == 3) {
         log.info("TSProvider.shutdown - already in shutdown, redundant call, returning.");
         return;
       }
 
       log.info("TSProvider.shutdown - Starting");
       if (this.tsCaps.sysStatStop != 0) {
         SysStatHandler handler = new SysStatHandler();
         try {
           this.tsapi.stopSystemStatusMonitoring(null, handler);
         } catch (Exception e) {
           log.error("stopSystemStatusMonitoring() failure: " + e.getMessage());
         }
       }
 
       Vector eventList = new Vector();
       synchronized (eventList) {
         setState(3, eventList);
 
         if (privateData != null)
         {
           for (int i = 0; i < eventList.size(); ++i) {
             TSEvent ev = (TSEvent)eventList.elementAt(i);
             if (ev.getPrivateData() == null) {
               ev.setPrivateData(privateData);
             }
           }
           if (!isLucent()) {
             eventList.addElement(new TSEvent(9999, this, privateData));
           }
 
         }
 
         if (eventList.size() > 0) {
           Vector observers = getMonitors();
           for (int j = 0; j < observers.size(); ++j) {
             TsapiProviderMonitor callback = (TsapiProviderMonitor)observers.elementAt(j);
 
             callback.deliverEvents(eventList, false);
           }
         }
       }
       removeMonitors(100, null);
 
       finalizeOldProvider();
 
       log.info("TSProvider.shutdown - Done");
     }
   }
 
   void setState(int tsapi_shutdown, Vector<TSEvent> eventList)
   {
     setState(tsapi_shutdown, eventList, false);
   }
 
   void sendSnapshot(TsapiProviderMonitor callback)
   {
     if (callback == null) {
       return;
     }
 
     Vector eventList = new Vector();
 
     switch (this.state)
     {
     case 2:
       eventList.addElement(new TSEvent(1, this));
 
       eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiInServiceEvent()));
 
       break;
     case 1:
       eventList.addElement(new TSEvent(2, this));
 
       eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiInitializingEvent()));
 
       break;
     case 0:
       eventList.addElement(new TSEvent(2, this));
 
       eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiOutOfServiceEvent()));
 
       break;
     case 3:
       eventList.addElement(new TSEvent(3, this));
 
       eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiShutdownEvent()));
     }
 
     if (eventList.size() <= 0) {
       return;
     }
     callback.deliverEvents(eventList, true);
   }
 
   public TsapiProviderCapabilities getTsapiProviderCapabilities()
   {
     return new TsapiProviderCapabilities(this.tsCaps);
   }
 
   public TsapiAddressCapabilities getTsapiAddressCapabilities()
   {
     return new TsapiAddressCapabilities(this.tsCaps);
   }
 
   public TsapiTerminalCapabilities getTsapiTerminalCapabilities()
   {
     return new TsapiTerminalCapabilities(this.tsCaps);
   }
 
   public TsapiCallCapabilities getTsapiCallCapabilities()
   {
     return new TsapiCallCapabilities(this.tsCaps);
   }
 
   public TsapiConnCapabilities getTsapiConnCapabilities()
   {
     return new TsapiConnCapabilities(this.tsCaps);
   }
 
   public TsapiTermConnCapabilities getTsapiTermConnCapabilities()
   {
     return new TsapiTermConnCapabilities(this.tsCaps);
   }
 
   public Vector<TSDevice> getTSRouteDevices()
   {
     if (!this.securityOn) {
       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Route addresses can be accessed.");
     }
 
     Vector tsDeviceVector = new Vector();
 
     waitToInitialize();
 
     for (int i = 0; i < this.tsRouteDevices.size(); ++i) {
       TSDevice device = createDevice((String)this.tsRouteDevices.elementAt(i));
       if (device != null)
         tsDeviceVector.addElement(device);
     }
     TSDevice device = createDevice("AllRouteAddress");
     if (device != null)
       tsDeviceVector.addElement(device);
     return tsDeviceVector;
   }
 
   short getDeviceExt(String deviceID)
   {
     if (this.tsCaps.getQueryDeviceInfo() == 0) {
       return 0;
     }
     CSTAEvent event;
     try
     {
       event = this.tsapi.queryDeviceInfo(deviceID, null);
     }
     catch (TsapiUnableToSendException tue)
     {
       throw tue;
     }
     catch (Exception e) {
       log.error(e.getMessage(), e);
       return 0;
     }
 
     Object replyPriv = event.getPrivData();
     if (replyPriv instanceof LucentQueryDeviceInfoConfEvent) {
       if (((LucentQueryDeviceInfoConfEvent)replyPriv).getExtensionClass() == 0)
       {
         return 1;
       }if (((LucentQueryDeviceInfoConfEvent)replyPriv).getExtensionClass() == 1)
       {
         return 2;
       }
 
       if (!(replyPriv instanceof LucentV5QueryDeviceInfoConfEvent));
     }
 
     return 0;
   }
 
   public Vector<TSDevice> getTSACDDevices()
     throws TsapiMethodNotSupportedException
   {
     if (!isLucent()) {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     if (!this.securityOn) {
       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered ACD addresses can be accessed.");
     }
 
     Vector tsDeviceVector = new Vector();
 
     waitToInitialize();
 
     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
       if (getDeviceExt((String)this.tsMonitorableDevices.elementAt(i)) == 2) {
         TSDevice device = createDevice((String)this.tsMonitorableDevices.elementAt(i));
 
         if (device != null)
           tsDeviceVector.addElement(device);
       }
     }
     return tsDeviceVector;
   }
 
   public Vector<TSDevice> getTSACDManagerDevices()
     throws TsapiMethodNotSupportedException
   {
     if (!isLucent()) {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     if (!this.securityOn) {
       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered ACD Manager addresses can be accessed.");
     }
 
     Vector tsDeviceVector = new Vector();
 
     waitToInitialize();
 
     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
       if (getDeviceExt((String)this.tsMonitorableDevices.elementAt(i)) == 1) {
         TSDevice device = createDevice((String)this.tsMonitorableDevices.elementAt(i));
 
         if (device != null)
           tsDeviceVector.addElement(device);
       }
     }
     return tsDeviceVector;
   }
 
   public TSCall createTSCall(int callID)
   {
     TSCall call = createCall(callID);
     call.updateObject();
     return call;
   }
 
   public TSDevice createDevice(String name, boolean checkValidity) throws TsapiInvalidArgumentException
   {
     if (name == null) {
       return null;
     }
 
     return createDevice(new CSTAExtendedDeviceID(name, (short)0, (short)0), checkValidity);
   }
 
   public TSDevice createDevice(CSTAExtendedDeviceID deviceID, boolean checkValidity)
     throws TsapiInvalidArgumentException
   {
     if ((deviceID == null) || (deviceID.getDeviceIDStatus() != 0) || (deviceID.getDeviceID() == null))
     {
       return null;
     }
     if (checkValidity)
     {
       if (deviceID.getDeviceID().equals("AllRouteAddress")) {
         return createDevice(deviceID);
       }
 
       if ((this.state == 2) && (this.securityOn) && 
         (!this.tsMonitorableDevices.contains(deviceID.getDeviceID()))) {
         throw new TsapiInvalidArgumentException(0, 0, "not in provider's domain");
       }
 
     }
 
     return createDevice(deviceID);
   }
 
   public TSConnection createTSConnection(CSTAConnectionID connID, TSDevice device) {
     return createConnection(connID, device, null);
   }
 
   void addMonitor(int monitorCrossRefID, Object observed)
   {
     synchronized (this.xrefHash)
     {
       Object oldObj = this.xrefHash.put(new Integer(monitorCrossRefID), observed);
 
       TtXrefHash("addMon", monitorCrossRefID, observed);
       if (oldObj != null)
         log.info("NOTICE: xrefHash.put() replaced " + oldObj + " for " + this);
     }
   }
 
   void deleteMonitor(int monitorCrossRefID)
   {
     this.xrefHash.remove(new Integer(monitorCrossRefID));
 
     TtXrefHash("delMon", monitorCrossRefID, "GONE");
   }
 
   void addRoute(int routeRegisterID, TSDevice tsDevice) {
     synchronized (this.routeRegHash)
     {
       Object oldObj = this.routeRegHash.put(new Integer(routeRegisterID), tsDevice);
 
       if (oldObj != null)
         log.info("NOTICE: routeRegHash.put() replaced " + oldObj + " for " + this);
     }
   }
 
   void deleteRoute(int routeRegisterID)
   {
     this.routeRegHash.remove(new Integer(routeRegisterID));
   }
 
   void addPrivateXref(int xrefID, TSDevice tsDevice) {
     synchronized (this.privXrefHash)
     {
       Object oldObj = this.privXrefHash.put(new Integer(xrefID), tsDevice);
       if (oldObj != null)
         log.info("NOTICE: privXrefHash.put() replaced " + oldObj + " for " + this);
     }
   }
 
   void deletePrivateXref(int xrefID)
   {
     synchronized (this.privXrefHash)
     {
       this.privXrefHash.remove(new Integer(xrefID));
     }
   }
 
   TSDevice findACDDevice(int xrefID) {
     return (TSDevice)this.privXrefHash.get(new Integer(xrefID));
   }
 
   void addNonCallToHash(TSCall call) {
     synchronized (this.nonCallHash) {
       Object oldObj = this.nonCallHash.put(new Integer(call.getNonCallID()), call);
 
       if (oldObj != null)
         log.info("NOTICE: nonCallHash.put() replaced " + oldObj + " for " + this);
     }
   }
 
   void deleteNonCallFromHash(int nonCallId)
   {
     this.nonCallHash.remove(new Integer(nonCallId));
   }
 
   void dumpAgent(TSAgentKey agentKey) {
     this.auditor.dumpAgent(agentKey);
   }
 
   void dumpCall(int callID) {
     this.auditor.dumpCall(callID);
   }
 
   void dumpConn(CSTAConnectionID connID) {
     this.auditor.dumpConn(connID);
   }
 
   void callCleanup() {
     Enumeration callEnum = this.callHash.elements();
 
     while (callEnum.hasMoreElements()) {
       TSCall call;
       try { call = (TSCall)callEnum.nextElement();
       } catch (NoSuchElementException e) {
         log.error(e.getMessage(), e);
continue;
       }
 
       if (call == null)
       {
         log.error("callCleanup: handled AuditThread null call reference race condition for " + this);
       }
 
       if (call.hasReceivedCallClearedTransfer())
       {
         if (System.currentTimeMillis() - call.getCallClearedTransferReceiptTime() < 3000L) {
           continue;
         }
         Vector eventList = new Vector();
 
         call.setState(34, eventList);
 
         int jtapiCause = 212;
 
         this.tsEHandler.doCallMonitors(call, eventList, jtapiCause, null);
       }
 
       if (call.checkForMonitors()) {
         continue;
       }
       boolean is_confirmed_that_call_is_gone = false;
 
       boolean lucent_tactics_get_an_answer = false;
 
       if (isLucentV5())
       {
         try
         {
           String old_ucid = call.getUCID();
           String new_ucid = call.queryUCID();
 
           if ((old_ucid != null) && (new_ucid != null) && (old_ucid.compareTo(new_ucid) != 0))
           {
             is_confirmed_that_call_is_gone = true;
           }
           else is_confirmed_that_call_is_gone = false;
 
           lucent_tactics_get_an_answer = true;
         }
         catch (TsapiUnableToSendException tue)
         {
           throw tue;
         }
         catch (TsapiPlatformException e)
         {
           if ((e.getErrorType() == 2) && (((e.getErrorCode() == 24) || (e.getErrorCode() == 11))))
           {
             is_confirmed_that_call_is_gone = true;
 
             lucent_tactics_get_an_answer = true;
           } else if ((e.getErrorType() == 2) && (e.getErrorCode() == 15))
           {
             log.info("Error: UCID not enabled on switch - interferes with JTAPI Call Auditing");
           }
 
         }
         catch (Exception e)
         {
           log.error(e.getMessage(), e);
         }
 
       }
 
       if (((lucent_tactics_get_an_answer) && (is_confirmed_that_call_is_gone)) || ((!lucent_tactics_get_an_answer) && (!call.updateObject())))
       {
         if (call.getTSState() == 34) {
           if (this.callHash.get(new Integer(call.getCallID())) == null)
           {
             log.info("Benign race condition: call (callid " + call.getCallID() + ") went INVALID while being audited");
           }
           else
           {
             log.info("ERROR: removing call (callid " + call.getCallID() + ") from Provider's records - Audit indicates call had ended");
 
             call.delete();
           }
 
         }
 
         call.setState(34, null);
       }
     }
   }
 
   TSCall addCallToHash(TSCall call)
   {
     synchronized (this.callHash) {
       Object oldObj = this.callHash.put(new Integer(call.getCallID()), call);
       if (oldObj != null) {
         log.info("NOTICE: callHash.put() replaced " + oldObj + " for " + this);
       }
       return (TSCall)oldObj;
     }
   }
 
   void addCallToSaveHash(TSCall call) {
     this.auditor.putCall(call);
   }
 
   void deleteCallFromHash(int callID) {
     this.callHash.remove(new Integer(callID));
   }
 
   TSConnection addConnectionToHash(TSConnection connection) {
     synchronized (this.connHash) {
       Object oldObj = null;
       CSTAConnectionID connID = connection.getConnID();
       if (connID != null) {
         oldObj = this.connHash.put(connID, connection);
 
         TtConnHash("addConn", connection, connID);
 
         log.info("NOTICE: connHash.put() replaced " + oldObj + " with " + connection + " for " + this);
       }
 
       return (TSConnection)oldObj;
     }
   }
 
   void addConnectionToSaveHash(TSConnection connection) {
     this.auditor.putConn(connection);
   }
 
   void deleteConnectionFromHash(CSTAConnectionID connID) {
     if (connID != null) {
       this.connHash.remove(connID);
 
       TtConnHash("delConn", "NO OBJECT", connID);
     }
   }
 
   void addDeviceToHash(TSDevice device) {
     addDeviceToHash(device.getName(), device);
   }
 
   void addDeviceToHash(String deviceID, TSDevice device) {
     synchronized (this.devHash) {
       if (deviceID != null) {
         Object oldObj = this.devHash.put(deviceID, device);
         if (oldObj != null)
           log.info("NOTICE: devHash.put() replaced " + oldObj + " for " + this);
       }
     }
   }
 
   void deleteDeviceFromHash(TSDevice device)
   {
     synchronized (this.devHash) {
       Vector keys = device.getKeys();
       for (int i = 0; i < keys.size(); ++i) {
         String key = ((CSTAExtendedDeviceID)keys.elementAt(i)).getDeviceID();
         Object removedObj = this.devHash.remove(key);
         log.info("NOTICE: devHash.remove() removed " + removedObj + " for device name(" + i + ") " + key);
       }
     }
   }
 
   void deleteDeviceFromHash(String _deviceID)
   {
     synchronized (this.devHash) {
       Object removedObj = this.devHash.remove(_deviceID);
       log.info("NOTICE: devHash.remove() removed " + removedObj + " by device name " + _deviceID);
     }
   }
 
   private void addDeviceNameToPrintingBuffer(StringBuffer aBuffer, String aName)
   {
     if (aBuffer.length() > 0) {
       aBuffer.append(", ");
     }
     aBuffer.append(aName);
   }
 
   void deleteInstanceOfDeviceFromHash(TSDevice _soughtObj)
   {
     int keys_not_in_hash = 0;
     int keys_pointing_elsewhere = 0;
     Hashtable keys_pointing_at = new Hashtable();
 
     StringBuffer alias_names = new StringBuffer();
 
     StringBuffer elsewhere_names = new StringBuffer();
 
     StringBuffer not_in_hash_names = new StringBuffer();
 
     synchronized (this.devHash) {
       Vector keys = _soughtObj.getKeys();
       for (int i = 0; i < keys.size(); ++i) {
         String key = ((CSTAExtendedDeviceID)keys.elementAt(i)).getDeviceID();
         Object foundObj = this.devHash.get(key);
         boolean foundAny = foundObj != null;
         boolean foundThatOne = foundObj == _soughtObj;
 
         if (foundThatOne)
         {
           this.devHash.remove(key);
         }
 
         if (foundThatOne) {
           addDeviceNameToPrintingBuffer(alias_names, key);
         } else if (foundAny)
         {
           ++keys_pointing_elsewhere;
           keys_pointing_at.put(key, foundObj);
           addDeviceNameToPrintingBuffer(elsewhere_names, key);
         }
         else
         {
           ++keys_not_in_hash;
           addDeviceNameToPrintingBuffer(not_in_hash_names, key);
         }
 
       }
 
     }
 
     if (keys_pointing_elsewhere + keys_not_in_hash == 0) {
       log.info("NOTICE: devHash.remove() expected, found and removed " + _soughtObj + " by device name(s) [" + alias_names + "]");
     }
     else
     {
       if (keys_pointing_elsewhere > 0) {
         Iterator key_iter = keys_pointing_at.keySet().iterator();
 
         while (key_iter.hasNext()) {
           String d = (String)key_iter.next();
           TSDevice t = (TSDevice)keys_pointing_at.get(d);
           log.info("NOTICE: devHash.remove() expected " + _soughtObj + " but found " + t + " by device name " + d + " - race condition - left latter TSDevice in hash");
         }
 
       }
 
       if (keys_not_in_hash > 0)
         log.info("NOTICE: attempted to devHash.remove() " + _soughtObj + " by device name(s) [" + not_in_hash_names + "] but no TSDevice there by those name(s)");
     }
   }
 
   void addTrunkToHash(String name, TSTrunk trunk)
   {
     synchronized (this.trkHash) {
       if (name != null) {
         Object oldObj = this.trkHash.put(name, trunk);
         if (oldObj != null)
           log.info("NOTICE: trkHash.put() replaced " + oldObj + " for " + this);
       }
     }
   }
 
   void deleteTrunkFromHash(String name)
   {
     synchronized (this.trkHash) {
       while (this.trkHash.remove(name) != null);
     }
   }
 
   void addAgentToHash(TSAgent agent)
   {
     synchronized (this.agentHash) {
       TSAgentKey agentKey = agent.getAgentKey();
       if (agentKey != null) {
         Object oldObj = this.agentHash.put(agentKey, agent);
         if (oldObj != null)
           log.info("NOTICE: agentHash.put() replaced " + oldObj + " for " + this);
       }
     }
   }
 
   void addAgentToSaveHash(TSAgent agent)
   {
     this.auditor.putAgent(agent);
   }
 
   void deleteAgentFromHash(TSAgentKey agentKey) {
     if (agentKey != null)
       this.agentHash.remove(agentKey);
   }
 
   TSAgent findAgent(TSAgentKey agentKey)
   {
     return (TSAgent)this.agentHash.get(agentKey);
   }
 
   TSAgent createAgent(TSAgentKey agentKey, String agentID, String password)
   {
     return createAgent(agentKey, agentID, password, CREATEAGENT_ACCEPT_DELETED);
   }
 
   TSAgent createAgent(TSAgentKey agentKey, String agentID, String password, int deletedAgentSearchPolicy)
   {
     TSAgent agent = null;
 
     boolean newObject = false;
     boolean auditObject = false;
 
     synchronized (this.agentHash) {
       agent = (TSAgent)this.agentHash.get(agentKey);
       if (agent == null)
       {
         if (deletedAgentSearchPolicy == CREATEAGENT_ACCEPT_DELETED) {
           agent = this.auditor.getAgent(agentKey);
         }
 
         if (agent == null) {
           newObject = true;
           agent = new TSAgent(this, agentKey, password);
           addAgentToHash(agent);
         } else {
           auditObject = true;
         }
       }
     }
 
     if (newObject) {
       agent.finishConstruction();
     } else {
       agent.waitForConstruction();
 
       if ((agent.getACDDeviceID() == null) && (agentKey.acdDeviceID != null) && (!auditObject))
       {
         agent.addToSkillsVector(agentKey.acdDeviceID);
       }
     }
     return agent;
   }
 
   TSCall findCall(int callID) {
     synchronized (this.callHash) {
       TSCall call = null;
 
       if (callID != 0)
       {
         call = (TSCall)this.callHash.get(new Integer(callID));
         if (call != null) {
           return call;
         }
 
         call = this.auditor.getCall(callID);
         if (call != null) {
           return call;
         }
 
       }
 
       return null;
     }
   }
 
   TSCall createCall(int callID) {
     synchronized (this.callHash) {
       TSCall call = null;
 
       if (callID != 0) {
         call = findCall(callID);
         if (call != null)
         {
           return call;
         }
 
         call = new TSCall(this, callID);
       }
       else {
         call = new TSCall(this, callID);
       }
 
       return call;
     }
   }
 
   TSCall createCall(int callID, Object privateData)
   {
     TSCall call = createCall(callID);
     return validateCall(privateData, call, callID);
   }
 
   TSConnection createConnection(CSTAConnectionID connID, TSDevice device, Vector<TSEvent> eventList)
   {
     TSConnection conn = null;
 
     boolean newObject = false;
 
     synchronized (this.connHash) {
       if (connID != null) {
         conn = (TSConnection)this.connHash.get(connID);
       }
       if (conn == null) {
         if (connID != null) {
           conn = this.auditor.getConn(connID);
         }
         if (conn == null) {
           newObject = true;
           conn = new TSConnection(this, connID, device, false);
           if (connID != null) {
             addConnectionToHash(conn);
           }
         }
       }
     }
     if (newObject)
       conn.finishConstruction(null, eventList);
     else {
       conn.waitForConstruction();
     }
 
     return conn.getTSConn();
   }
 
   public TSConnection getConnection(CSTAConnectionID connID)
   {
     TSConnection conn = null;
 
     synchronized (this.connHash) {
       if (connID != null) {
         conn = (TSConnection)this.connHash.get(connID);
       }
       if ((conn == null) && 
         (connID != null)) {
         conn = this.auditor.getConn(connID);
       }
     }
 
     if (conn == null) {
       return null;
     }
 
     conn.waitForConstruction();
 
     return conn.getTSConn();
   }
 
   boolean isConnInActiveHash(CSTAConnectionID connID)
   {
     return this.connHash.get(connID) != null;
   }
 
   boolean isConnInSaveHash(CSTAConnectionID connID)
   {
     return this.auditor.getConn(connID) != null;
   }
 
   boolean isConnInAnyHash(CSTAConnectionID connID)
   {
     return (isConnInActiveHash(connID)) || (isConnInSaveHash(connID));
   }
 
   boolean isConnInDisconnectedHash(CSTAConnectionID connID)
   {
     return this.auditor.getConn(connID) != null;
   }
 
   TSConnection createTerminalConnection(CSTAConnectionID connID, TSDevice termConnDevice, Vector<TSEvent> eventList, TSDevice connDevice)
   {
     TSConnection conn = null;
 
     boolean newObject = false;
     boolean auditObject = false;
 
     synchronized (this.connHash) {
       conn = (TSConnection)this.connHash.get(connID);
 
       if (conn == null) {
         conn = this.auditor.getConn(connID);
 
         if (conn == null) {
           newObject = true;
           conn = new TSConnection(this, connID, termConnDevice, true);
           addConnectionToHash(conn);
         } else {
           auditObject = true;
         }
       }
     }
 
     if (newObject)
       conn.finishConstruction(connDevice, eventList);
     else {
       conn.waitForConstruction();
     }
 
     if ((termConnDevice.isTerminal()) && (!conn.isTerminalConnection())) {
       if (isLucent())
       {
         if (!auditObject) {
           deleteConnectionFromHash(connID);
           conn = createTerminalConnection(connID, termConnDevice, eventList, connDevice);
         }
       }
       else {
         conn.setTerminalConnection();
       }
     }
 
     return conn;
   }
 
   TSDevice createDevice(String deviceID) {
     return createDevice(deviceID, null);
   }
 
   TSDevice createDevice(String deviceID, CSTAConnectionID connID) {
     if (deviceID == null) {
       return null;
     }
     return createDevice(new CSTAExtendedDeviceID(deviceID, (short)0, (short)0), connID);
   }
 
   public TSDevice createDevice(CSTAExtendedDeviceID deviceID)
   {
     if ((deviceID == null) || (deviceID.getDeviceIDStatus() != 0) || (deviceID.getDeviceID() == null))
     {
       return null;
     }
 
     TSDevice device = null;
 
     synchronized (this.devHash) {
       device = (TSDevice)this.devHash.get(deviceID.getDeviceID());
 
       if (device == null) {
         device = new TSDevice(this, deviceID);
         addDeviceToHash(device);
       }
     }
 
     return device;
   }
 
   TSDevice findDevice(String name) {
     synchronized (this.devHash) {
       return (TSDevice)this.devHash.get(name);
     }
   }
 
   TSDevice createDevice(CSTAExtendedDeviceID deviceID, CSTAConnectionID connID)
   {
     if ((deviceID == null) || (deviceID.getDeviceIDStatus() != 0) || (deviceID.getDeviceID() == null))
     {
       return null;
     }
 
     TSDevice device = null;
 
     device = (TSDevice)this.devHash.get(deviceID.getDeviceID());
 
     if (device == null) {
       if (connID != null)
       {
         TSConnection conn = (TSConnection)this.connHash.get(connID);
         if (conn == null) {
           conn = this.auditor.getConn(connID);
         }
         if (conn != null)
         {
           device = conn.getTSDevice();
 
           synchronized (device)
           {
             device.addName(deviceID);
             synchronized (this.devHash)
             {
               TSDevice tmpDev = (TSDevice)this.devHash.get(deviceID.getDeviceID());
 
               if (tmpDev == null)
                 addDeviceToHash(device);
               else {
                 device = tmpDev;
               }
             }
           }
         }
       }
 
       boolean notFound = false;
       synchronized (this.devHash)
       {
         if ((device == null) && ((device = (TSDevice)this.devHash.get(deviceID.getDeviceID())) == null))
         {
           notFound = true;
         }
 
       }
 
       if (notFound)
       {
         device = new TSDevice(this, deviceID);
         synchronized (device)
         {
           addDeviceToHash(device);
         }
       }
 
     }
 
     return device;
   }
 
   public TSTrunk createTSTrunk(String trkName)
   {
     TSTrunk trunk = createTrunk(trkName, 1);
     return trunk;
   }
 
   TSTrunk createTrunk(String trkName, int type) {
     if (trkName == null) {
       return null;
     }
 
     synchronized (this.trkHash) {
       TSTrunk trunk = null;
 
       trunk = (TSTrunk)this.trkHash.get(trkName);
 
       if (trunk != null) {
         return trunk;
       }
 
       trunk = new TSTrunk(this, trkName, type);
 
       return trunk;
     }
   }
 
   private void setState(int _state, Vector<TSEvent> eventList, boolean ignoreOldState)
   {
     int oldCoreState = 16;
     if (!ignoreOldState) {
       oldCoreState = getState();
     }
     synchronized (this)
     {
       if (this.state == _state) {
         return;
       }
 
       this.state = _state;
     }
 
     switch (this.state)
     {
     case 2:
       synchronized (eventList) {
         if (eventList != null)
         {
           if ((ignoreOldState) || (oldCoreState != 16)) {
             eventList.addElement(new TSEvent(1, this));
           }
 
           eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiInServiceEvent()));
         }
       }
 
       break;
     case 1:
       synchronized (eventList) {
         if (eventList != null)
         {
           if ((ignoreOldState) || (oldCoreState != 17)) {
             eventList.addElement(new TSEvent(2, this));
           }
 
           eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiInitializingEvent()));
         }
       }
 
       break;
     case 0:
       synchronized (eventList) {
         if (eventList != null)
         {
           if ((ignoreOldState) || (oldCoreState != 17)) {
             eventList.addElement(new TSEvent(2, this));
           }
 
           eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiOutOfServiceEvent()));
         }
       }
 
       break;
     case 3:
       synchronized (eventList) {
         if (eventList != null)
         {
           if ((ignoreOldState) || (oldCoreState != 18)) {
             eventList.addElement(new TSEvent(3, this));
           }
 
           eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiShutdownEvent()));
         }
 
       }
 
       Enumeration xrefEnum = this.xrefHash.elements();
       Object monitored = null;
 
       while (xrefEnum.hasMoreElements()) {
         try {
           monitored = xrefEnum.nextElement();
         } catch (NoSuchElementException e) {
           log.error(e.getMessage(), e);
continue;
         }
 
         if (monitored == null) {
           continue;
         }
 
         if (monitored instanceof TSDevice) {
           ((TSDevice)monitored).removeObservers(100, null, 0);
         }
         if (monitored instanceof TSCall);
         ((TSCall)monitored).removeObservers(100, null, 0);
       }
 
       if (this.tsapi != null) {
         this.tsapi.shutdown();
       }
 
       this.devHash.clear();
       this.trkHash.clear();
       this.agentHash.clear();
       this.connHash.clear();
 
       TtConnHash("dtor", "NO OBJECT", "NO CONNID");
       this.callHash.clear();
       this.xrefHash.clear();
 
       TtXrefHash("dtor", 0, "NO OBJECT");
       this.routeRegHash.clear();
       this.privXrefHash.clear();
       this.providerMonitorThreads.removeAllElements();
       this.addressMonitorThreads.removeAllElements();
       this.terminalMonitorThreads.removeAllElements();
       this.callMonitorThreads.removeAllElements();
       this.routeMonitorThreads.removeAllElements();
 
       if (this.auditor != null) {
         this.auditor.stopRunning();
       }
 
       disableHeartbeat();
     }
   }
 
   boolean isDeviceMonitorable(String name)
   {
     if ((this.state == 2) && (this.securityOn)) {
       if (name == null) {
         return false;
       }
       return this.tsMonitorableDevices.contains(name);
     }
 
     return true;
   }
 
   boolean allowCallMonitoring() {
     return this.callMonitoring;
   }
 
   List<String> getMonitorableDevices()
   {
     short[] level = { 1, 2, 3 };
 
     List listOfMonitorableDevices = new ArrayList();
     for (int i = 0; i < level.length; ++i) {
       int index = GET_DEVICE_INITIAL_INDEX;
       do
       {
         CSTAEvent event;
         try
         {
           event = this.tsapi.getDeviceList(index, level[i]);
         } catch (Exception e) {
//           break label164:
	break;
         }
         if (event != null) {
           CSTAGetDeviceListConfEvent getDeviceListConf = (CSTAGetDeviceListConfEvent)event.getEvent();
 
           if ((getDeviceListConf.getDriverSdbLevel() == 1) || (getDeviceListConf.getDriverSdbLevel() == -1))
           {
             setSecurity(false);
             return listOfMonitorableDevices;
           }
           for (int j = 0; j < getDeviceListConf.getDevList().length; ++j) {
             String device = getDeviceListConf.getDevList()[j];
 
             if (!listOfMonitorableDevices.contains(device)) {
               listOfMonitorableDevices.add(device);
             }
           }
           label164: index = getDeviceListConf.getIndex();
         }
       }
       while (index != GET_DEVICE_NO_MORE_INDEX);
     }
 
     return listOfMonitorableDevices;
   }
 
   void setRouteDevices()
   {
     int index = GET_DEVICE_INITIAL_INDEX;
     do
     {
       CSTAEvent event;
       try
       {
         event = this.tsapi.getDeviceList(index, (short) 6);
       } catch (Exception e) {
         return;
       }
 
       CSTAGetDeviceListConfEvent getDeviceListConf = (CSTAGetDeviceListConfEvent)event.getEvent();
 
       for (int j = 0; j < getDeviceListConf.getDevList().length; ++j) {
         String device = getDeviceListConf.getDevList()[j];
 
         if (!this.tsRouteDevices.contains(device))
           this.tsRouteDevices.addElement(device);
       }
       index = getDeviceListConf.getIndex();
     }
     while (index != GET_DEVICE_NO_MORE_INDEX);
   }
 
   void setCallMonitor(boolean _callMonitoring)
   {
     this.callMonitoring = _callMonitoring;
   }
 
   void setCapabilities(TSCapabilities _tsCaps)
   {
     this.tsCaps = _tsCaps;
   }
 
   TSCapabilities getCapabilities() {
     return this.tsCaps;
   }
 
   void setSecurity(boolean _securityOn)
   {
     this.securityOn = _securityOn;
   }
 
   synchronized int getNonCallID()
   {
     int[] start = { this.nonCallID, 0 };
     for (int j = 0; j < 1; ++j)
     {
       for (int i = start[j]; i < 100; ++i) {
         if (this.nonCallIDArray[i] == NOT_IN_USE) {
           this.nonCallID = i;
           this.nonCallIDArray[i] = IN_USE;
           return this.nonCallID;
         }
       }
     }
     return -1;
   }
 
   synchronized void releaseNonCallID(int nonCallId)
   {
     this.nonCallIDArray[nonCallId] = NOT_IN_USE;
   }
 
   TSCapabilities getCaps()
   {
     TSCapabilities tsCaps = new TSCapabilities();
 
     if (isLucent()) {
       tsCaps.setLucent(getLucentPDV());
     }
 
     try
     {
       CSTAEvent event = this.tsapi.getApiCaps();
       if (event.getEvent() == null)
       {
         log.info("Init Capabilities: Conf event null, enable all Capabilities for " + this);
 
         tsCaps.setAll();
         return tsCaps;
       }
       if (event.getEvent() instanceof CSTAGetAPICapsConfEvent) {
         CSTAGetAPICapsConfEvent getAPICapsConf = (CSTAGetAPICapsConfEvent)event.getEvent();
 
         if (isLucentV5()) {
           tsCaps.setAddParty(1);
         }
         tsCaps.setAlternateCall(getAPICapsConf.getAlternateCall());
         tsCaps.setAnswerCall(getAPICapsConf.getAnswerCall());
         tsCaps.setCallCompletion(getAPICapsConf.getCallCompletion());
 
         tsCaps.setClearCall(getAPICapsConf.getClearCall());
         tsCaps.setClearConnection(getAPICapsConf.getClearConnection());
 
         tsCaps.setConferenceCall(getAPICapsConf.getConferenceCall());
 
         tsCaps.setConsultationCall(getAPICapsConf.getConsultationCall());
 
         tsCaps.setDeflectCall(getAPICapsConf.getDeflectCall());
         tsCaps.setPickupCall(getAPICapsConf.getPickupCall());
         tsCaps.setGroupPickupCall(getAPICapsConf.getGroupPickupCall());
 
         tsCaps.setHoldCall(getAPICapsConf.getHoldCall());
         tsCaps.setMakeCall(getAPICapsConf.getMakeCall());
         tsCaps.setMakePredictiveCall(getAPICapsConf.getMakePredictiveCall());
 
         tsCaps.setQueryMwi(getAPICapsConf.getQueryMwi());
         tsCaps.setQueryDnd(getAPICapsConf.getQueryDnd());
         tsCaps.setQueryFwd(getAPICapsConf.getQueryFwd());
         tsCaps.setQueryAgentState(getAPICapsConf.getQueryAgentState());
 
         tsCaps.setQueryLastNumber(getAPICapsConf.getQueryLastNumber());
 
         tsCaps.setQueryDeviceInfo(getAPICapsConf.getQueryDeviceInfo());
 
         tsCaps.setReconnectCall(getAPICapsConf.getReconnectCall());
         tsCaps.setRetrieveCall(getAPICapsConf.getRetrieveCall());
         tsCaps.setSetMwi(getAPICapsConf.getSetMwi());
         tsCaps.setSetDnd(getAPICapsConf.getSetDnd());
         tsCaps.setSetFwd(getAPICapsConf.getSetFwd());
         tsCaps.setSetAgentState(getAPICapsConf.getSetAgentState());
         tsCaps.setTransferCall(getAPICapsConf.getTransferCall());
         tsCaps.setEventReport(getAPICapsConf.getEventReport());
         tsCaps.setCallClearedEvent(getAPICapsConf.getCallClearedEvent());
 
         tsCaps.setConferencedEvent(getAPICapsConf.getConferencedEvent());
 
         tsCaps.setConnectionClearedEvent(getAPICapsConf.getConnectionClearedEvent());
 
         tsCaps.setDeliveredEvent(getAPICapsConf.getDeliveredEvent());
 
         tsCaps.setDivertedEvent(getAPICapsConf.getDivertedEvent());
         tsCaps.setEstablishedEvent(getAPICapsConf.getEstablishedEvent());
 
         tsCaps.setFailedEvent(getAPICapsConf.getFailedEvent());
         tsCaps.setHeldEvent(getAPICapsConf.getHeldEvent());
         tsCaps.setNetworkReachedEvent(getAPICapsConf.getNetworkReachedEvent());
 
         tsCaps.setOriginatedEvent(getAPICapsConf.getOriginatedEvent());
 
         tsCaps.setQueuedEvent(getAPICapsConf.getQueuedEvent());
         tsCaps.setRetrievedEvent(getAPICapsConf.getRetrievedEvent());
 
         tsCaps.setServiceInitiatedEvent(getAPICapsConf.getServiceInitiatedEvent());
 
         tsCaps.setTransferredEvent(getAPICapsConf.getTransferredEvent());
 
         tsCaps.setCallInformationEvent(getAPICapsConf.getCallInformationEvent());
 
         tsCaps.setDoNotDisturbEvent(getAPICapsConf.getDoNotDisturbEvent());
 
         tsCaps.setForwardingEvent(getAPICapsConf.getForwardingEvent());
 
         tsCaps.setMessageWaitingEvent(getAPICapsConf.getMessageWaitingEvent());
 
         tsCaps.setLoggedOnEvent(getAPICapsConf.getLoggedOnEvent());
         tsCaps.setLoggedOffEvent(getAPICapsConf.getLoggedOffEvent());
 
         tsCaps.setNotReadyEvent(getAPICapsConf.getNotReadyEvent());
         tsCaps.setReadyEvent(getAPICapsConf.getReadyEvent());
         tsCaps.setWorkNotReadyEvent(getAPICapsConf.getWorkNotReadyEvent());
 
         tsCaps.setWorkReadyEvent(getAPICapsConf.getWorkReadyEvent());
 
         tsCaps.setBackInServiceEvent(getAPICapsConf.getBackInServiceEvent());
 
         tsCaps.setOutOfServiceEvent(getAPICapsConf.getOutOfServiceEvent());
 
         tsCaps.setPrivateEvent(getAPICapsConf.getPrivateEvent());
         tsCaps.setRouteRequestEvent(getAPICapsConf.getRouteRequestEvent());
 
         tsCaps.setReRoute(getAPICapsConf.getReRoute());
         tsCaps.setRouteSelect(getAPICapsConf.getRouteSelect());
         tsCaps.setRouteUsedEvent(getAPICapsConf.getRouteUsedEvent());
 
         tsCaps.setRouteEndEvent(getAPICapsConf.getRouteEndEvent());
         tsCaps.setMonitorDevice(getAPICapsConf.getMonitorDevice());
         tsCaps.setMonitorCall(getAPICapsConf.getMonitorCall());
         tsCaps.setMonitorCallsViaDevice(getAPICapsConf.getMonitorCallsViaDevice());
 
         tsCaps.setChangeMonitorFilter(getAPICapsConf.getChangeMonitorFilter());
 
         tsCaps.setMonitorStop(getAPICapsConf.getMonitorStop());
         tsCaps.setMonitorEnded(getAPICapsConf.getMonitorEnded());
         tsCaps.setSnapshotDeviceReq(getAPICapsConf.getSnapshotDeviceReq());
 
         tsCaps.setSnapshotCallReq(getAPICapsConf.getSnapshotCallReq());
 
         tsCaps.setEscapeService(getAPICapsConf.getEscapeService());
         tsCaps.setPrivateStatusEvent(getAPICapsConf.getPrivateStatusEvent());
 
         tsCaps.setEscapeServiceEvent(getAPICapsConf.getEscapeServiceEvent());
 
         tsCaps.setEscapeServiceConf(getAPICapsConf.getEscapeServiceConf());
 
         tsCaps.setSendPrivateEvent(getAPICapsConf.getSendPrivateEvent());
 
         tsCaps.setSysStatReq(getAPICapsConf.getSysStatReq());
         tsCaps.setSysStatStart(getAPICapsConf.getSysStatStart());
         tsCaps.setSysStatStop(getAPICapsConf.getSysStatStop());
         tsCaps.setChangeSysStatFilter(getAPICapsConf.getChangeSysStatFilter());
 
         tsCaps.setSysStatReqEvent(getAPICapsConf.getSysStatReqEvent());
 
         tsCaps.setSysStatReqConf(getAPICapsConf.getSysStatReqConf());
 
         tsCaps.setSysStatEvent(getAPICapsConf.getSysStatEvent());
 
         Object replyPriv = event.getPrivData();
         if ((replyPriv instanceof LucentGetAPICapsConfEvent) && 
           (replyPriv instanceof LucentV5GetAPICapsConfEvent) && 
           (replyPriv instanceof LucentV7GetAPICapsConfEvent))
         {
           LucentV7GetAPICapsConfEvent cf = (LucentV7GetAPICapsConfEvent)replyPriv;
 
           setAdministeredSwitchSoftwareVersion(cf.getAdministeredSwitchSoftwareVersion());
 
           setSwitchSoftwareVersion(cf.getSwitchSoftwareVersion());
 
           setOfferType(cf.getOfferType());
           setServerType(cf.getServerType());
           setMonitorCallsViaDevice(cf.getMonitorCallsViaDevice());
         }
 
       }
       else
       {
         log.info("Init Capabilities: expected conf event with pduType 125,received conf event with pduType " + event.getEvent().getPDU() + ", enable all Capabilities" + " for " + this);
 
         tsCaps.setAll();
         return tsCaps;
       }
     } catch (Exception e) {
       log.error("Init Capabilities: Exception, enable all Capabilities - Exception: " + e + " for " + this);
 
       tsCaps.setAll();
     }
 
     return tsCaps;
   }
 
   boolean getCallMonitor()
   {
     CSTAEvent event;
     try {
       event = this.tsapi.CSTAQueryCallMonitor();
     }
     catch (TsapiUnableToSendException tue)
     {
       throw tue;
     }
     catch (Exception e) {
       log.error(e.getMessage(), e);
       return false;
     }
     CSTAQueryCallMonitorConfEvent qcmConf = (CSTAQueryCallMonitorConfEvent)event.getEvent();
 
     return qcmConf.isCallMonitor();
   }
 
   public void addMonitor(TsapiProviderMonitor monitor)
     throws TsapiResourceUnavailableException
   {
     synchronized (this.obsSync)
     {
       if (this.monitors.contains(monitor)) {
         return;
       }
 
       this.monitors.addElement(monitor);
 
       monitor.addReference();
     }
 
     sendSnapshot(monitor);
   }
 
   public Vector<TsapiProviderMonitor> getMonitors()
   {
     return new Vector(this.monitors);
   }
 
   void removeMonitors(int cause, Object privateData)
   {
     Vector obs = new Vector(this.monitors);
     for (int i = 0; i < obs.size(); ++i)
       removeMonitor((TsapiProviderMonitor)obs.elementAt(i), cause, privateData);
   }
 
   public void removeMonitor(TsapiProviderMonitor monitor)
   {
     removeMonitor(monitor, 100, null);
   }
 
   void removeMonitor(TsapiProviderMonitor monitor, int cause, Object privateData) {
     if (this.monitors.removeElement(monitor))
       monitor.deleteReference(cause, privateData);
   }
 
   public Vector<TsapiProviderMonitor> getProviderMonitorThreads() {
     return this.providerMonitorThreads;
   }
 
   public void addProviderMonitorThread(TsapiProviderMonitor obs)
   {
     if (this.providerMonitorThreads.contains(obs)) {
       return;
     }
 
     this.providerMonitorThreads.addElement(obs);
   }
 
   public void removeProviderMonitorThread(TsapiProviderMonitor obs) {
     this.providerMonitorThreads.removeElement(obs);
   }
 
   public Vector<TsapiAddressMonitor> getAddressMonitorThreads() {
     return this.addressMonitorThreads;
   }
 
   public void addAddressMonitorThread(TsapiAddressMonitor obs) {
     if (this.addressMonitorThreads.contains(obs)) {
       return;
     }
 
     this.addressMonitorThreads.addElement(obs);
   }
 
   public void removeAddressMonitorThread(TsapiAddressMonitor obs) {
     this.addressMonitorThreads.removeElement(obs);
   }
 
   public Vector<TsapiRouteMonitor> getRouteMonitorThreads() {
     return this.routeMonitorThreads;
   }
 
   public void addRouteMonitorThread(TsapiRouteMonitor obs) {
     if (this.routeMonitorThreads.contains(obs)) {
       return;
     }
 
     this.routeMonitorThreads.addElement(obs);
   }
 
   public void removeRouteMonitorThread(TsapiRouteMonitor obs) {
     this.routeMonitorThreads.removeElement(obs);
   }
 
   public Vector<TsapiTerminalMonitor> getTerminalMonitorThreads() {
     return this.terminalMonitorThreads;
   }
 
   public void addTerminalMonitorThread(TsapiTerminalMonitor obs) {
     if (this.terminalMonitorThreads.contains(obs)) {
       return;
     }
 
     this.terminalMonitorThreads.addElement(obs);
   }
 
   public void removeTerminalMonitorThread(TsapiTerminalMonitor obs) {
     this.terminalMonitorThreads.removeElement(obs);
   }
 
   public Vector<TsapiCallMonitor> getCallMonitorThreads() {
     return this.callMonitorThreads;
   }
 
   public void addCallMonitorThread(TsapiCallMonitor obs) {
     if (this.callMonitorThreads.contains(obs)) {
       return;
     }
 
     this.callMonitorThreads.addElement(obs);
   }
 
   public void removeCallMonitorThread(TsapiCallMonitor obs) {
     this.callMonitorThreads.removeElement(obs);
   }
 
   private void setInstanceNumber()
   {
     synchronized (g_lock) {
       this.m_instanceNumber = (++g_instanceNumber);
     }
   }
 
   public int getInstanceNumber()
   {
     return this.m_instanceNumber;
   }
 
   public TSEventHandler getTsEHandler()
   {
     return this.tsEHandler;
   }
 
   public String getAdministeredSwitchSoftwareVersion()
   {
     return this.administeredSwitchSoftwareVersion;
   }
 
   void setAdministeredSwitchSoftwareVersion(String administeredSwitchSoftwareVersion)
   {
     this.administeredSwitchSoftwareVersion = administeredSwitchSoftwareVersion;
   }
 
   public String getOfferType()
   {
     return this.offerType;
   }
 
   void setOfferType(String offerType)
   {
     this.offerType = offerType;
   }
 
   public String getServerType()
   {
     return this.serverType;
   }
 
   void setServerType(String serverType)
   {
     this.serverType = serverType;
   }
 
   public String getSwitchSoftwareVersion()
   {
     return this.switchSoftwareVersion;
   }
 
   void setSwitchSoftwareVersion(String switchSoftwareVersion)
   {
     this.switchSoftwareVersion = switchSoftwareVersion;
   }
 
   public boolean getMonitorCallsViaDevice()
   {
     return this.monitorCallsViaDevice;
   }
 
   public String requestPrivileges()
     throws TsapiInvalidArgumentException
   {
     RequestPrivilegesConfHandler handler = new RequestPrivilegesConfHandler(this);
 
     boolean request_failed = true;
     try {
       this.tsapi.requestPrivileges(null, handler);
       request_failed = false;
       String str = handler.get_nonce();
 
       return str;
     }
     catch (TsapiPlatformException e)
     {
       switch (e.getErrorType())
       {
       case 120:
       case 126:
       case 127:
       case 128:
       case 121:
       case 122:
       case 123:
       case 124:
       case 125:
       }
 
       throw new TsapiPlatformException(e.getErrorType(), e.getErrorCode(), "Unexpected requestPrivileges TSAPI failure: " + e);
     }
     finally
     {
       if (request_failed)
         shutdown();
     }
   }
 
   void setMonitorCallsViaDevice(boolean monitorCallsViaDevice)
   {
     this.monitorCallsViaDevice = monitorCallsViaDevice;
   }
 
   public void setPrivileges(String xmlData)
     throws TsapiInvalidArgumentException
   {
     ConfHandler handler = new SetPrivilegesConfHandler(this);
     try {
       this.tsapi.setPrivileges(xmlData, null, handler);
       return;
     } catch (TsapiInvalidArgumentException e) {
       shutdown();
       throw e;
     } catch (TsapiPlatformException e) {
       shutdown();
       throw new TsapiPlatformException(e.getErrorType(), e.getErrorCode(), "setPrivileges TSAPI failure: " + e);
     }
     catch (Exception e)
     {
       shutdown();
       throw new TsapiPlatformException(4, 0, "Unexpected setPrivileges TSAPI failure: " + e);
     }
   }
 
   public IDomainDevice addCallToDomain(IDomainDevice d, IDomainCall c)
   {
     return this.m_providerTracker.addCallToDomain(d, c);
   }
 
   public void changeCallIDInDomain(int old_callid, int new_callid)
   {
     this.m_providerTracker.changeCallIDInDomain(old_callid, new_callid);
   }
 
   public IDomainDevice getDomainCallIsIn(IDomainCall c)
   {
     return this.m_providerTracker.getDomainCallIsIn(c);
   }
 
   public void removeCallFromDomain(IDomainCall c)
   {
     this.m_providerTracker.removeCallFromDomain(c);
   }
 
   public void removeAllCallsForDomain(IDomainDevice d)
   {
     this.m_providerTracker.removeAllCallsForDomain(d);
   }
 
   public boolean isCallInAnyDomain(IDomainCall c)
   {
     return this.m_providerTracker.isCallInAnyDomain(c);
   }
 
   public void dumpDomainData(String indent)
   {
     this.m_providerTracker.dumpDomainData(indent);
   }
 
   public IDomainCall getDomainCall(int callid)
   {
     return findCall(callid);
   }
 
   public IDomainDevice getDomainDevice(String name)
   {
     return findDevice(name);
   }
 
   public void logln(String s)
   {
     if (log.isInfoEnabled())
       log.info(s);
   }
 
   Object getMonitoredObject(int xrefID)
   {
     return this.xrefHash.get(new Integer(xrefID));
   }
 
   public String toString()
   {
     return "TSProvider[#" + getInstanceNumber() + "]@" + Integer.toHexString(super.hashCode());
   }
 
   TSCall validateCall(Object privateData, TSCall call, int callID)
   {
     if (call == null) {
       return call;
     }
 
     if ((privateData instanceof LucentTransferredEvent) || (privateData instanceof LucentConferencedEvent))
     {
       return call;
     }
     if (privateData instanceof HasUCID) {
       if (((HasUCID)privateData).getUcid() == null) {
         return call;
       }
       if (call.ucid == null) {
         return call;
       }
       if (((HasUCID)privateData).getUcid().compareTo(call.ucid) != 0)
       {
         log.info("Mismatched UCID for validateCall removing stale call obj " + call);
 
         log.info("UCID for validateCall for the new call is " + ((HasUCID)privateData).getUcid());
 
         call.setState(34, null);
         dumpCall(callID);
         TSCall newCall = createCall(callID);
         return newCall;
       }
       return call;
     }
 
     return call;
   }
 
   public String getMonitoredObjects()
   {
     StringBuffer buffer = new StringBuffer();
     for (Map.Entry entry : this.xrefHash.entrySet()) {
       buffer.append(entry.getKey() + ":" + entry.getValue() + "\n");
     }
     return buffer.toString();
   }
 
   class TsapiProReaderTask extends TimerTask
   {
     TsapiProReaderTask()
     {
     }
 
     public void run()
     {
       Tsapi.updateVolatileConfigurationValues();
       if (!Tsapi.isRefreshPeriodChanged())
         return;
       TSProviderImpl.this.timerThread.cancel();
       int interval = Tsapi.getRefreshIntervalForTsapiPro() * 1000;
       TSProviderImpl.this.timerThread = new Timer("TsapiProReader");
       TSProviderImpl.this.timerThread.schedule(new TsapiProReaderTask(), interval, interval);
     }
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSProviderImpl
 * JD-Core Version:    0.5.4
 */