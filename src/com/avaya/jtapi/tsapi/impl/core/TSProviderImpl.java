/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import java.net.InetSocketAddress;
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
/*      */ 
/*      */ public final class TSProviderImpl extends TSProvider
/*      */   implements IDomainTracker, IDomainContainer, ITsapiHeartbeatTimeoutListener
/*      */ {
/*  119 */   private static Logger log = Logger.getLogger(TSProviderImpl.class);
/*      */   Tsapi tsapi;
/*      */   private final Object obsSync;
/*      */   private final Vector<TsapiProviderMonitor> monitors;
/*      */   private final Vector<TsapiProviderMonitor> providerMonitorThreads;
/*      */   private final Vector<TsapiAddressMonitor> addressMonitorThreads;
/*      */   private final Vector<TsapiTerminalMonitor> terminalMonitorThreads;
/*      */   private final Vector<TsapiCallMonitor> callMonitorThreads;
/*      */   private final Vector<TsapiRouteMonitor> routeMonitorThreads;
/*      */   private final ConnectStringData connectStringData;
/*      */   int state;
/*  148 */   Object replyPriv = null;
/*      */   TSEventHandler tsEHandler;
/*      */   TSAuditThread auditor;
/*      */   Timer timerThread;
/*      */   private final Hashtable<String, TSDevice> devHash;
/*      */   private final Hashtable<String, TSTrunk> trkHash;
/*      */   private final Hashtable<TSAgentKey, TSAgent> agentHash;
/*  171 */   boolean lucent = false;
/*      */ 
/*  174 */   private int lucentPDV = -1;
/*      */ 
/*  177 */   final int LUCENT_PDV_UNINITIALIZED = -1;
/*      */   private final Hashtable<Integer, TSCall> callHash;
/*      */   private final Hashtable<Integer, TSCall> nonCallHash;
/*      */   private final Hashtable<CSTAConnectionID, TSConnection> connHash;
/*      */   final Hashtable<Integer, Object> xrefHash;
/*      */   final Hashtable<Integer, Object> routeRegHash;
/*      */   private final Hashtable<Integer, Object> privXrefHash;
/*      */   TSCapabilities tsCaps;
/*      */   Vector<String> tsMonitorableDevices;
/*      */   Vector<String> tsRouteDevices;
/*      */   boolean callMonitoring;
/*      */   int[] nonCallIDArray;
/*  216 */   int nonCallID = 0;
/*      */ 
/*  219 */   static int NOT_IN_USE = 0;
/*      */ 
/*  222 */   static int IN_USE = 1;
/*      */ 
/*  225 */   static int CSTA_HOME_WORK_TOP = 1;
/*      */ 
/*  228 */   static int CSTA_AWAY_WORK_TOP = 2;
/*      */ 
/*  231 */   static int CSTA_DEVICE_DEVICE_MONITOR = 3;
/*      */ 
/*  234 */   static int CSTA_ROUTING = 4;
/*      */ 
/*  237 */   static int GET_DEVICE_INITIAL_INDEX = -1;
/*      */ 
/*  243 */   static int GET_DEVICE_NO_MORE_INDEX = -1;
/*      */ 
/*  246 */   static int TSAPI_RESPONSE_TIME = 30000;
/*      */ 
/*  251 */   static int DEFAULT_TIMEOUT = 180000;
/*      */   TSInitializationThread initThread;
/*  258 */   boolean securityOn = true;
/*      */ 
/*  261 */   private String administeredSwitchSoftwareVersion = "";
/*      */ 
/*  263 */   private String switchSoftwareVersion = "";
/*      */ 
/*  265 */   private String offerType = "";
/*      */ 
/*  267 */   private String serverType = "";
/*      */ 
/*  271 */   private boolean monitorCallsViaDevice = false;
/*      */ 
/*  273 */   private boolean enableTsapiHeartbeat = false;
/*      */ 
/*  276 */   private static int g_instanceNumber = 0;
/*      */ 
/*  278 */   private static Object g_lock = new Object();
/*      */ 
/*  281 */   private int m_instanceNumber = 0;
/*  282 */   static int provider_count = 0;
/*      */ 
/*  284 */   private static final Object provider_count_lock = new Object();
/*      */ 
/*  293 */   private final Object shutdown_single_thread_lock = new Object();
/*      */ 
/*  297 */   static int CREATEAGENT_ACCEPT_DELETED = 1;
/*      */ 
/*  300 */   static int CREATEAGENT_REFUSE_DELETED = 2;
/*      */ 
/* 3320 */   IDomainTracker m_providerTracker = new TSDomainTracker(this);
/*      */ 
/*      */   public TSProviderImpl(String _url, Vector<TsapiVendor> vendors)
/*      */   {
/*  305 */     setInstanceNumber();
/*      */ 
/*  308 */     log.info("TSProvider: version '" + getProviderVersionDetails() + "', for " + this);
/*      */ 
/*  315 */     this.state = 0;
/*      */ 
/*  317 */     this.devHash = new Hashtable(10);
/*  318 */     this.trkHash = new Hashtable(10);
/*  319 */     this.agentHash = new Hashtable(10);
/*      */ 
/*  321 */     this.connHash = new Hashtable(20);
/*      */ 
/*  323 */     TtConnHash("ctor", "NO OBJECT", "NO CONNID");
/*  324 */     this.callHash = new Hashtable(10);
/*  325 */     this.nonCallHash = new Hashtable(10);
/*  326 */     this.xrefHash = new Hashtable(3);
/*      */ 
/*  328 */     TtXrefHash("ctor", 0, "NO OBJECT");
/*  329 */     this.routeRegHash = new Hashtable(3);
/*  330 */     this.privXrefHash = new Hashtable(3);
/*  331 */     this.tsMonitorableDevices = new Vector();
/*  332 */     this.tsRouteDevices = new Vector();
/*  333 */     this.monitors = new Vector();
/*  334 */     this.providerMonitorThreads = new Vector();
/*  335 */     this.addressMonitorThreads = new Vector();
/*  336 */     this.terminalMonitorThreads = new Vector();
/*  337 */     this.callMonitorThreads = new Vector();
/*  338 */     this.routeMonitorThreads = new Vector();
/*      */ 
/*  340 */     this.obsSync = new Object();
/*  341 */     this.nonCallIDArray = new int[100];
/*  342 */     this.callMonitoring = false;
/*      */ 
/*  345 */     this.connectStringData = parseURL(_url);
/*  346 */     if (this.connectStringData.telephonyServers != null) {
/*  347 */       for (InetSocketAddress telephonyServer : this.connectStringData.telephonyServers) {
/*  348 */         Tsapi.addServer(telephonyServer);
/*      */       }
/*      */     }
/*  351 */     this.tsEHandler = new TSEventHandler(this);
/*      */ 
/*  355 */     log.info("TSProvider: calling acsOpenStream serverID=" + this.connectStringData.serverId + " loginID=" + this.connectStringData.loginId + " passwd=******* for " + this);
/*      */ 
/*  359 */     this.tsapi = TsapiFactory.getTsapi(this.connectStringData.serverId, this.connectStringData.loginId, this.connectStringData.password, vendors, this.tsEHandler);
/*      */ 
/*  362 */     this.lucent = LucentPrivateData.isAvayaVendor(getVendor());
/*      */ 
/*  365 */     this.auditor = new TSAuditThread(this);
/*  366 */     this.auditor.start();
/*      */ 
/*  368 */     this.timerThread = new Timer("TsapiProReader", true);
/*      */ 
/*  370 */     int timeInterval = Tsapi.getRefreshIntervalForTsapiPro() * 1000;
/*  371 */     this.timerThread.schedule(new TsapiProReaderTask(), timeInterval, timeInterval);
/*      */ 
/*  376 */     if (this.enableTsapiHeartbeat) {
/*  377 */       this.tsapi.enableHeartbeat();
/*  378 */       this.tsapi.setHeartbeatTimeoutListener(this);
/*  379 */       this.enableTsapiHeartbeat = false;
/*      */     }
/*      */ 
/*  382 */     setCapabilities(getCaps());
/*  383 */     setCallMonitor(getCallMonitor());
/*  384 */     if (this.tsCaps.sysStatStart != 0) {
/*  385 */       SysStatHandler handler = new SysStatHandler();
/*      */       try {
/*  387 */         this.tsapi.startSystemStatusMonitoring(null, handler);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  391 */         if (e instanceof ITsapiException) {
/*  392 */           throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "startSystemStatusMonitoring() failure: " + e.getMessage());
/*      */         }
/*      */ 
/*  396 */         throw new TsapiPlatformException(4, 0, "startSystemStatusMonitoring() failure: " + e.getMessage());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  403 */     JtapiEventThreadManager.initialize();
/*      */ 
/*  407 */     this.initThread = new TSInitializationThread(this);
/*  408 */     this.initThread.start();
/*      */ 
/*  410 */     initNewProvider();
/*      */   }
/*      */ 
/*      */   private ConnectStringData parseURL(String _url) {
/*  414 */     String serverID = _url;
/*  415 */     String loginID = "";
/*  416 */     String passwd = "";
/*  417 */     Collection telephonyServers = new LinkedHashSet();
/*  418 */     int firstSemiColon_index = _url.indexOf(';');
/*  419 */     serverID = _url.substring(0, firstSemiColon_index);
/*  420 */     if (firstSemiColon_index >= 0) {
/*  421 */       StringTokenizer params = new StringTokenizer(_url.substring(firstSemiColon_index + 1), ";");
/*      */ 
/*  423 */       while (params.hasMoreTokens()) {
/*  424 */         StringTokenizer param = new StringTokenizer(params.nextToken(), "=");
/*      */ 
/*  426 */         if (!param.hasMoreTokens()) {
/*      */           continue;
/*      */         }
/*  429 */         String key = param.nextToken();
/*  430 */         if (!param.hasMoreTokens()) {
/*      */           continue;
/*      */         }
/*  433 */         String value = param.nextToken();
/*      */ 
/*  437 */         if ((key.equals("login")) || (key.equals("loginID"))) {
/*  438 */           loginID = value;
/*      */         }
/*  440 */         else if (key.equals("passwd")) {
/*  441 */           passwd = value;
/*      */         }
/*  443 */         else if (key.equals("servers")) {
/*  444 */           telephonyServers = JtapiUtils.parseTelephonyServerEntry(value, 450);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  449 */     if (loginID.length() > 48) {
/*  450 */       throw new TsapiPlatformException(4, 0, "Username provided is more than 48 characters in length. Login ID=" + loginID);
/*      */     }
/*      */ 
/*  456 */     if (passwd.length() > 47) {
/*  457 */       throw new TsapiPlatformException(4, 0, "Password provided is more than 47 characters in length. Password length=" + passwd.length());
/*      */     }
/*      */ 
/*  462 */     return new ConnectStringData(serverID, loginID, passwd, telephonyServers, _url);
/*      */   }
/*      */ 
/*      */   void dump(String indent) {
/*  466 */     log.trace(indent + "***** PROVIDER DUMP *****");
/*  467 */     log.trace(indent + "TSProvider: " + this);
/*      */ 
/*  470 */     log.trace(indent + "TSProvider: " + this.connectStringData.serverId + ";login=" + this.connectStringData.loginId + ";passwd=*******");
/*      */ 
/*  472 */     log.trace(indent + "TSProvider state: " + this.state);
/*  473 */     log.trace(indent + "TSProvider version details: " + getProviderVersionDetails());
/*      */ 
/*  477 */     log.trace(indent + "TSProvider calls: ");
/*  478 */     Enumeration callEnum = this.callHash.elements();
/*      */ 
/*  480 */     while (callEnum.hasMoreElements()) {
/*      */       TSCall call;
/*      */       try { call = (TSCall)callEnum.nextElement();
/*      */       } catch (NoSuchElementException e) {
/*  484 */         log.error(e.getMessage(), e);
continue;
/*  485 */       }
/*      */ 
/*  487 */       call.dump(indent + " ");
/*      */     }
/*  489 */     log.trace(indent + "TSProvider non calls: ");
/*  490 */     Enumeration nonCallEnum = this.nonCallHash.elements();
/*      */ 
/*  492 */     while (nonCallEnum.hasMoreElements()) {
/*      */       TSCall nonCall;
/*      */       try { nonCall = (TSCall)nonCallEnum.nextElement();
/*      */       } catch (NoSuchElementException e) {
/*  496 */         log.error(e.getMessage(), e);
continue;
/*  497 */       }
/*      */ 
/*  499 */       nonCall.dump(indent + " ");
/*      */     }
/*      */ 
/*  502 */     log.trace(indent + "TSProvider VDN Calls-to-VDN Domain Mapping: ");
/*      */ 
/*  504 */     dumpDomainData(indent);
/*      */ 
/*  506 */     log.trace(indent + "TSProvider devices: ");
/*  507 */     Enumeration deviceEnum = this.devHash.elements();
/*      */ 
/*  509 */     while (deviceEnum.hasMoreElements()) {
/*      */       TSDevice device;
/*      */       try { device = (TSDevice)deviceEnum.nextElement();
/*      */       } catch (NoSuchElementException e) {
/*  513 */         log.error(e.getMessage(), e);
continue;
/*  514 */       }
/*      */ 
/*  516 */       device.dump(indent + " ");
/*      */     }
/*  518 */     log.trace(indent + "TSProvider conns: ");
/*  519 */     Enumeration connEnum = this.connHash.elements();
/*      */ 
/*  521 */     while (connEnum.hasMoreElements()) {
/*      */       TSConnection conn;
/*      */       try { conn = (TSConnection)connEnum.nextElement();
/*      */       } catch (NoSuchElementException e) {
/*  525 */         log.error(e.getMessage(), e);
continue;
/*  526 */       }
/*      */ 
/*  528 */       conn.dump(indent + " ");
/*      */     }
/*  530 */     log.trace(indent + "TSProvider agents: ");
/*  531 */     Enumeration agentEnum = this.agentHash.elements();
/*      */ 
/*  533 */     while (agentEnum.hasMoreElements()) {
/*      */       TSAgent agent;
/*      */       try { agent = (TSAgent)agentEnum.nextElement();
/*      */       } catch (NoSuchElementException e) {
/*  537 */         log.error(e.getMessage(), e);
continue;
/*  538 */       }
/*      */ 
/*  540 */       agent.dump(indent + " ");
/*      */     }
/*  542 */     log.trace(indent + "TSProvider trunks: ");
/*  543 */     Enumeration trkEnum = this.trkHash.elements();
/*      */ 
/*  545 */     while (trkEnum.hasMoreElements()) {
/*      */       TSTrunk trk;
/*      */       try { trk = (TSTrunk)trkEnum.nextElement();
/*      */       } catch (NoSuchElementException e) {
/*  549 */         log.error(e.getMessage(), e);
continue;
/*  550 */       }
/*      */ 
/*  552 */       trk.dump(indent + " ");
/*      */     }
/*  554 */     log.trace(indent + "TSProvider xrefs: ");
/*  555 */     Enumeration xrefEnum = this.xrefHash.elements();
/*  556 */     while (xrefEnum.hasMoreElements()) {
/*      */       try {
/*  558 */         log.trace(indent + "xref object: " + xrefEnum.nextElement());
/*      */       }
/*      */       catch (NoSuchElementException e) {
/*  561 */         log.error(e.getMessage(), e);
/*      */       }
/*      */     }
/*      */ 
/*  565 */     log.trace(indent + "TSProvider audits: ");
/*  566 */     this.auditor.dump(indent + " ");
/*  567 */     log.trace(indent + "***** PROVIDER DUMP END *****");
/*      */   }
/*      */ 
/*      */   void TtXrefHash(String s, int monitorCrossRefID, Object observed)
/*      */   {
/*  578 */     Tt.println("#X=" + this.xrefHash.size() + " R=" + monitorCrossRefID + " O=" + observed + " //" + s);
/*      */   }
/*      */ 
/*      */   void TtConnHash(String s, Object connection, Object connID)
/*      */   {
/*  585 */     Tt.println("#C=" + this.connHash.size() + " I=" + connID.toString() + " C=" + connection.toString() + " //" + s);
/*      */   }
/*      */ 
/*      */   String getProviderVersionDetails()
/*      */   {
/*  603 */     String std_string = "production build";
/*      */ 
/*  605 */     String stdver = "5.2.0.483";
/*      */ 
/*  612 */     String customver = "production build";
/*      */ 
/*  614 */     return stdver + " [" + customver + "]";
/*      */   }
/*      */ 
/*      */   public void initNewProvider()
/*      */   {
/*  624 */     synchronized (provider_count_lock) {
/*  625 */       provider_count += 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void finalizeOldProvider()
/*      */   {
/*  633 */     synchronized (provider_count_lock) {
/*  634 */       if (provider_count > 0) {
/*  635 */         provider_count -= 1;
/*  636 */         if (provider_count == 0)
/*      */         {
/*  638 */           JtapiEventThreadManager.drainThreads();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getState()
/*      */   {
/*  651 */     switch (this.state)
/*      */     {
/*      */     case 0:
/*      */     case 1:
/*      */     default:
/*  655 */       return 17;
/*      */     case 2:
/*  657 */       int jtapiState = 16;
/*      */ 
/*  659 */       if (this.tsCaps.sysStatReq != 0) {
/*  660 */         Vector eventList = new Vector();
/*  661 */         SysStatHandler handler = new SysStatHandler();
/*      */         try {
/*  663 */           this.tsapi.requestSystemStatus(null, handler);
/*      */         }
/*      */         catch (Exception e) {
/*  666 */           log.warn("Failed to get system status. Returning OUT_OF_SERVICE to be safe");
/*  667 */           setState(0, eventList, true);
/*  668 */           jtapiState = 17;
/*      */         }
/*  670 */         if ((handler.getSystemStatus() != 1) && (handler.getSystemStatus() != 2)) {
/*  671 */           setState(0, eventList, true);
/*  672 */           jtapiState = 17;
/*      */         }
/*  674 */         if (eventList.size() > 0) {
/*  675 */           Vector observers = getMonitors();
/*  676 */           for (int j = 0; j < observers.size(); ++j) {
/*  677 */             TsapiProviderMonitor callback = (TsapiProviderMonitor)observers.elementAt(j);
/*      */ 
/*  679 */             callback.deliverEvents(eventList, false);
/*      */           }
/*      */         }
/*      */       }
/*  683 */       return jtapiState;
/*      */     case 3:
/*      */     }
/*  685 */     return 18;
/*      */   }
/*      */ 
/*      */   public int getTsapiState()
/*      */   {
/*  691 */     return this.state;
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/*  696 */     return this.connectStringData.url;
/*      */   }
/*      */ 
/*      */   public boolean isLucent() {
/*  700 */     return this.lucent;
/*      */   }
/*      */ 
/*      */   public int getLucentPDV()
/*      */   {
/*  708 */     if (this.lucent) {
/*  709 */       if (this.lucentPDV == -1) {
/*  710 */         byte[] version = this.tsapi.getVendorVersion();
/*      */ 
/*  712 */         if ((version.length == 0) || (version[0] != 0) || (version[(version.length - 1)] != 0))
/*      */         {
/*  719 */           log.info("Version bytes with no data, or missing discriminator byte or trailing NULL byte, found while decoding TSAPI private version string");
/*      */ 
/*  722 */           this.lucentPDV = 0;
/*      */         }
/*      */         else
/*      */         {
/*      */           try
/*      */           {
/*  728 */             this.lucentPDV = Integer.parseInt(new String(version, 1, version.length - 2, "US-ASCII"));
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  733 */             log.info("Exception occurred decoding TSAPI private version string: " + e);
/*      */ 
/*  735 */             this.lucentPDV = 0;
/*      */           }
/*      */         }
/*      */       }
/*  739 */       return this.lucentPDV;
/*      */     }
/*  741 */     return 0;
/*      */   }
/*      */ 
/*      */   public boolean isLucentV5()
/*      */   {
/*  748 */     return getLucentPDV() >= 5;
/*      */   }
/*      */ 
/*      */   public boolean isLucentV6()
/*      */   {
/*  754 */     return getLucentPDV() >= 6;
/*      */   }
/*      */ 
/*      */   public boolean isLucentV7()
/*      */   {
/*  760 */     return getLucentPDV() >= 7;
/*      */   }
/*      */ 
/*      */   public boolean isLucentV8()
/*      */   {
/*  766 */     return getLucentPDV() >= 8;
/*      */   }
/*      */ 
/*      */   public LucentTrunkGroupInfo getTrunkGroupInfo(String trunkAccessCode) throws TsapiMethodNotSupportedException
/*      */   {
/*  771 */     if (!isLucent()) {
/*  772 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  777 */       LucentQueryTg qtg = new LucentQueryTg(trunkAccessCode);
/*  778 */       Object result = sendPrivateData(qtg.makeTsapiPrivate());
/*      */ 
/*  780 */       if (result instanceof LucentTrunkGroupInfo) {
/*  781 */         return (LucentTrunkGroupInfo)result;
/*      */       }
/*  783 */       return null;
/*      */     } catch (TsapiPlatformException e) {
/*  785 */       throw e;
/*      */     } catch (Exception e) {
/*  787 */       if (e instanceof ITsapiException) {
/*  788 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), " service failure");
/*      */       }
/*      */ 
/*  792 */       throw new TsapiPlatformException(4, 0, " service failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public CallClassifierInfo getCallClassifierInfo()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  799 */     if (!isLucent()) {
/*  800 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  805 */       LucentQueryCallClassifier qcc = new LucentQueryCallClassifier();
/*  806 */       Object result = sendPrivateData(qcc.makeTsapiPrivate());
/*      */ 
/*  808 */       if (result instanceof LucentCallClassifierInfo) {
/*  809 */         return new CallClassifierInfo(((LucentCallClassifierInfo)result).numAvailPorts, ((LucentCallClassifierInfo)result).numInUsePorts);
/*      */       }
/*      */ 
/*  813 */       return null;
/*      */     } catch (TsapiPlatformException e) {
/*  815 */       throw e;
/*      */     } catch (Exception e) {
/*  817 */       if (e instanceof ITsapiException) {
/*  818 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), " service failure");
/*      */       }
/*      */ 
/*  822 */       throw new TsapiPlatformException(4, 0, " service failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public Date getSwitchDateAndTime() throws TsapiMethodNotSupportedException
/*      */   {
/*  828 */     if (!isLucent()) {
/*  829 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  834 */       LucentQueryTod qtod = new LucentQueryTod();
/*  835 */       Object result = sendPrivateData(qtod.makeTsapiPrivate(), null, true);
/*      */ 
/*  838 */       if (result instanceof LucentQueryTodConfEvent) {
/*  839 */         LucentQueryTodConfEvent tod = (LucentQueryTodConfEvent)result;
/*  840 */         if (tod.getYear() < 97) {
/*  841 */           tod.setYear(tod.getYear() + 100);
/*      */         }
/*  843 */         Calendar cal = Calendar.getInstance();
/*  844 */         cal.set(tod.getYear(), tod.getMonth() - 1, tod.getDay(), tod.getHour(), tod.getMinute(), tod.getSecond());
/*      */ 
/*  847 */         return cal.getTime();
/*      */       }
/*  849 */       return null;
/*      */     } catch (TsapiPlatformException e) {
/*  851 */       throw e;
/*      */     } catch (Exception e) {
/*  853 */       if (e instanceof ITsapiException) {
/*  854 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), " service failure");
/*      */       }
/*      */ 
/*  858 */       throw new TsapiPlatformException(4, 0, " service failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAdviceOfCharge(boolean flag)
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  865 */     if (!isLucentV5()) {
/*  866 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  871 */       LucentSetAdviceOfCharge aoc = new LucentSetAdviceOfCharge(flag);
/*  872 */       sendPrivateData(aoc.makeTsapiPrivate());
/*      */     } catch (TsapiPlatformException e) {
/*  874 */       throw e;
/*      */     } catch (Exception e) {
/*  876 */       if (e instanceof ITsapiException) {
/*  877 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), " service failure");
/*      */       }
/*      */ 
/*  881 */       throw new TsapiPlatformException(4, 0, " service failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getVendor()
/*      */   {
/*  887 */     return this.tsapi.getVendor();
/*      */   }
/*      */ 
/*      */   public byte[] getVendorVersion() {
/*  891 */     return this.tsapi.getVendorVersion();
/*      */   }
/*      */ 
/*      */   public void updateAddresses()
/*      */   {
/*  897 */     List monitorableDevices = getMonitorableDevices();
/*  898 */     if ((monitorableDevices != null) && (monitorableDevices.size() != 0))
/*      */     {
/*  900 */       synchronized (this.tsMonitorableDevices)
/*      */       {
/*  903 */         for (Object element : monitorableDevices)
/*      */         {
/*  905 */           if (!this.tsMonitorableDevices.contains(element)) {
/*  906 */             this.tsMonitorableDevices.add((String) element);
/*      */           }
/*      */         }
/*  909 */         this.tsMonitorableDevices.retainAll(monitorableDevices);
/*      */       }
/*      */     }
/*  912 */     monitorableDevices = null;
/*      */   }
/*      */ 
/*      */   public void setDebugPrinting(boolean enable)
/*      */   {
/*  917 */     boolean traceLoggingEnabled = JTAPILoggingAdapter.isTraceLoggingEnabled();
/*  918 */     boolean errorLoggingEnabled = Logger.getLogger("com.avaya.jtapi.tsapi").isEnabledFor(Level.ERROR);
/*  919 */     boolean isLog4jLoggingEnabled = JtapiUtils.isLog4jConfigured();
/*      */ 
/*  921 */     if ((!traceLoggingEnabled) && 
/*  923 */       (isLog4jLoggingEnabled))
/*      */     {
/*  926 */       traceLoggingEnabled = true;
/*      */     }
/*      */ 
/*  929 */     if (enable)
/*      */     {
/*  931 */       if (traceLoggingEnabled)
/*      */       {
/*  933 */         Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.TRACE);
/*      */       }
/*      */       else
/*      */       {
/*  941 */         JTAPILoggingAdapter.setTraceLoggerLevel("7");
/*  942 */         JTAPILoggingAdapter.initializeLogging();
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  948 */       if (!traceLoggingEnabled)
/*      */         return;
/*  950 */       if (errorLoggingEnabled)
/*      */       {
/*  952 */         Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.ERROR);
/*      */       }
/*      */       else
/*      */       {
/*  956 */         Logger.getLogger("com.avaya.jtapi.tsapi").setLevel(Level.OFF);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean heartbeatIsEnabled()
/*      */   {
/*  972 */     if (this.tsapi != null) {
/*  973 */       return this.tsapi.heartbeatIsEnabled();
/*      */     }
/*      */ 
/*  976 */     return false;
/*      */   }
/*      */ 
/*      */   public void enableHeartbeat()
/*      */   {
/*  986 */     if (this.tsapi != null) {
/*  987 */       this.tsapi.enableHeartbeat();
/*      */     }
/*      */     else
/*      */     {
/*  993 */       this.enableTsapiHeartbeat = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void disableHeartbeat()
/*      */   {
/* 1003 */     if (this.tsapi != null)
/* 1004 */       this.tsapi.disableHeartbeat();
/*      */   }
/*      */ 
/*      */   public void setHeartbeatInterval(short heartbeatInterval)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/* 1019 */     ConfHandler handler = new SetHeartbeatIntervalConfHandler(this);
/*      */     try {
/* 1021 */       this.tsapi.setHeartbeatInterval(heartbeatInterval, null, handler);
/*      */     } catch (TsapiInvalidArgumentException e) {
/* 1023 */       throw e;
/*      */     } catch (Exception e) {
/* 1025 */       if (e instanceof ITsapiException) {
/* 1026 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setHeartbeatInterval() failure: " + e.getMessage());
/*      */       }
/*      */ 
/* 1030 */       throw new TsapiPlatformException(4, 0, "setHeartbeatInterval() failure: " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   void setClientHeartbeatInterval(short heartbeatInterval)
/*      */   {
/* 1045 */     this.tsapi.setClientHeartbeatInterval(heartbeatInterval);
/*      */   }
/*      */ 
/*      */   public void heartbeatTimeout() {
/* 1049 */     log.info("*** Heartbeat timer expired.  Shutting down Provider. ***");
/*      */ 
/* 1052 */     shutdown();
/*      */   }
/*      */ 
/*      */   public String getServerID()
/*      */   {
/* 1059 */     return this.tsapi.getServerID();
/*      */   }
/*      */ 
/*      */   public Object getPrivateData() {
/* 1063 */     if (this.replyPriv instanceof CSTAPrivate) {
/* 1064 */       return this.replyPriv;
/*      */     }
/* 1066 */     return null;
/*      */   }
/*      */ 
/*      */   public void setPrivateData(Object o) {
/* 1070 */     if (o instanceof CSTAPrivate)
/* 1071 */       this.replyPriv = o;
/*      */   }
/*      */ 
/*      */   public Object sendPrivateData(CSTAPrivate data)
/*      */     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1079 */     return sendPrivateData(data, null, false);
/*      */   }
/*      */ 
/*      */   Object sendPrivateData(CSTAPrivate data, ConfHandler extraHandler)
/*      */     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1087 */     return sendPrivateData(data, extraHandler, false);
/*      */   }
/*      */ 
/*      */   Object sendPrivateData(CSTAPrivate data, ConfHandler extraHandler, boolean priority)
/*      */     throws TsapiProviderUnavailableException, TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1095 */     if (data.tsType == 89)
/*      */     {
/*      */       ConfHandler handler;
/* 1097 */       if (priority)
/* 1098 */         handler = new PriorityEscapeConfHandler(this, extraHandler);
/*      */       else {
/* 1100 */         handler = new EscapeConfHandler(this, extraHandler);
/*      */       }
/* 1102 */       this.tsapi.CSTAEscapeService(data, handler);
/* 1103 */       return ((EscapeConfHandler)handler).getPrivateData();
/*      */     }
/* 1105 */     if (data.tsType == 95) {
/* 1106 */       this.tsapi.CSTASendPrivateEvent(data);
/* 1107 */       return null;
/*      */     }
/* 1109 */     throw new TsapiPlatformException(3, 0, "unknown  data type [" + data.tsType + "]");
/*      */   }
/*      */ 
/*      */   Vector<TSCall> doCallSnapshot(String device)
/*      */   {
/* 1116 */     if (this.tsCaps.getSnapshotDeviceReq() == 0) {
/* 1117 */       return null;
/*      */     }
/*      */ 
/* 1121 */     ProviderSnapshotDeviceConfHandler handler = new ProviderSnapshotDeviceConfHandler(this);
/*      */     try
/*      */     {
/* 1124 */       this.tsapi.snapshotDevice(device, null, handler);
/*      */     }
/*      */     catch (TsapiUnableToSendException tue)
/*      */     {
/* 1128 */       throw tue;
/*      */     }
/*      */     catch (Exception e) {
/* 1131 */       log.error(e.getMessage(), e);
/* 1132 */       return null;
/*      */     }
/*      */ 
/* 1135 */     return handler.cv;
/*      */   }
/*      */ 
/*      */   void waitToInitialize()
/*      */   {
/* 1140 */     if (this.state == 2) return;
/*      */     try {
/* 1142 */       synchronized (this.initThread) {
/* 1143 */         this.initThread.wait(DEFAULT_TIMEOUT);
/*      */       }
/*      */     } catch (InterruptedException e) {
/* 1146 */       throw new TsapiPlatformException(4, 0, "init time-out");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setSessionTimeout(int timeout)
/*      */   {
/* 1154 */     TsapiSession.setTimeout(timeout);
/*      */   }
/*      */ 
/*      */   public int getCurrentStateOfCallFromTelephonyServer(int callId)
/*      */   {
/* 1159 */     TSCall currentCall = null;
/*      */ 
/* 1162 */     if (callId < 1) {
/* 1163 */       throw new TsapiPlatformException(3, 0, "Please pass a Call ID value that is greater than 0.");
/*      */     }
/*      */ 
/* 1166 */     currentCall = createTSCall(callId);
/*      */ 
/* 1169 */     return getCurrentStateOfCallFromTelephonyServer(currentCall);
/*      */   }
/*      */ 
/*      */   public int getCurrentStateOfCallFromTelephonyServer(TSCall call)
/*      */   {
/* 1174 */     if (call == null) {
/* 1175 */       throw new TsapiPlatformException(3, 0, "Call object passed in is null.");
/*      */     }
/*      */ 
/* 1178 */     log.info("Forcing a query on telephony server to check state of call - " + call);
/* 1179 */     return call.getStateFromServer();
/*      */   }
/*      */ 
/*      */   public Vector<TSCall> getTSCalls()
/*      */   {
/* 1204 */     Vector tsCallVector = new Vector();
/* 1205 */     Vector tsDevCallVector = null;
/*      */ 
/* 1208 */     waitToInitialize();
/*      */ 
/* 1210 */     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
/* 1211 */       tsDevCallVector = doCallSnapshot((String)this.tsMonitorableDevices.elementAt(i));
/*      */ 
/* 1213 */       if (tsDevCallVector != null)
/* 1214 */         for (int j = 0; j < tsDevCallVector.size(); ++j)
/* 1215 */           if (!tsCallVector.contains(tsDevCallVector.elementAt(j)))
/* 1216 */             tsCallVector.addElement(tsDevCallVector.elementAt(j));
/*      */     }
/*      */     Enumeration callEnum;
/* 1226 */     synchronized (this.nonCallHash) {
/* 1227 */       callEnum = this.nonCallHash.elements();
/* 1228 */       while (callEnum.hasMoreElements()) {
/*      */         try {
/* 1230 */           tsCallVector.addElement(callEnum.nextElement());
/*      */         } catch (NoSuchElementException e) {
/* 1232 */           log.error(e.getMessage(), e);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1239 */     synchronized (this.callHash) {
/* 1240 */       callEnum = this.callHash.elements();
/* 1241 */       while (callEnum.hasMoreElements()) {
/*      */         TSCall callVar;
/*      */         try { callVar = (TSCall)callEnum.nextElement();
/*      */         } catch (NoSuchElementException e) {
/* 1245 */           log.error(e.getMessage(), e);
continue;
/* 1246 */         }
/*      */ 
/* 1248 */         if (!tsCallVector.contains(callVar));
/* 1249 */         tsCallVector.addElement(callVar);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1254 */     return tsCallVector;
/*      */   }
/*      */ 
/*      */   public Vector<TSDevice> getTSAddressDevices()
/*      */   {
/* 1264 */     if (!this.securityOn) {
/* 1265 */       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Addesses can be accessed.");
/*      */     }
/*      */ 
/* 1270 */     Vector tsDeviceVector = new Vector();
/*      */ 
/* 1273 */     waitToInitialize();
/*      */ 
/* 1276 */     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
/* 1277 */       TSDevice device = createDevice((String)this.tsMonitorableDevices.elementAt(i));
/* 1278 */       if (device != null)
/* 1279 */         tsDeviceVector.addElement(device);
/*      */     }
/* 1281 */     TSDevice device = createDevice("AllRouteAddress");
/* 1282 */     if (device != null)
/* 1283 */       tsDeviceVector.addElement(device);
/* 1284 */     return tsDeviceVector;
/*      */   }
/*      */ 
/*      */   public Vector<TSDevice> getTSTerminalDevices()
/*      */   {
/* 1290 */     if (!this.securityOn) {
/* 1291 */       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Terminals can be accessed.");
/*      */     }
/*      */ 
/* 1296 */     Vector tsDeviceVector = new Vector();
/*      */ 
/* 1299 */     waitToInitialize();
/*      */ 
/* 1302 */     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
/* 1303 */       String devName = (String)this.tsMonitorableDevices.elementAt(i);
/* 1304 */       TSDevice device = createDevice(devName);
/* 1305 */       if ((device != null) && (device.isTerminal()))
/* 1306 */         tsDeviceVector.addElement(device);
/*      */     }
/* 1308 */     return tsDeviceVector;
/*      */   }
/*      */ 
/*      */   public void shutdown()
/*      */   {
/* 1315 */     shutdown(null);
/*      */   }
/*      */ 
/*      */   public void shutdown(Object privateData)
/*      */   {
/* 1323 */     log.info("TSProvider.shutdown - attempting shutdown");
/* 1324 */     if (this.timerThread != null) {
/* 1325 */       this.timerThread.cancel();
/*      */     }
/* 1327 */     this.timerThread = null;
/*      */ 
/* 1329 */     synchronized (this.shutdown_single_thread_lock)
/*      */     {
/* 1332 */       if (this.state == 3) {
/* 1333 */         log.info("TSProvider.shutdown - already in shutdown, redundant call, returning.");
/* 1334 */         return;
/*      */       }
/*      */ 
/* 1337 */       log.info("TSProvider.shutdown - Starting");
/* 1338 */       if (this.tsCaps.sysStatStop != 0) {
/* 1339 */         SysStatHandler handler = new SysStatHandler();
/*      */         try {
/* 1341 */           this.tsapi.stopSystemStatusMonitoring(null, handler);
/*      */         } catch (Exception e) {
/* 1343 */           log.error("stopSystemStatusMonitoring() failure: " + e.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1347 */       Vector eventList = new Vector();
/* 1348 */       synchronized (eventList) {
/* 1349 */         setState(3, eventList);
/*      */ 
/* 1352 */         if (privateData != null)
/*      */         {
/* 1354 */           for (int i = 0; i < eventList.size(); ++i) {
/* 1355 */             TSEvent ev = (TSEvent)eventList.elementAt(i);
/* 1356 */             if (ev.getPrivateData() == null) {
/* 1357 */               ev.setPrivateData(privateData);
/*      */             }
/*      */           }
/* 1360 */           if (!isLucent()) {
/* 1361 */             eventList.addElement(new TSEvent(9999, this, privateData));
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1366 */         if (eventList.size() > 0) {
/* 1367 */           Vector observers = getMonitors();
/* 1368 */           for (int j = 0; j < observers.size(); ++j) {
/* 1369 */             TsapiProviderMonitor callback = (TsapiProviderMonitor)observers.elementAt(j);
/*      */ 
/* 1371 */             callback.deliverEvents(eventList, false);
/*      */           }
/*      */         }
/*      */       }
/* 1375 */       removeMonitors(100, null);
/*      */ 
/* 1377 */       finalizeOldProvider();
/*      */ 
/* 1379 */       log.info("TSProvider.shutdown - Done");
/*      */     }
/*      */   }
/*      */ 
/*      */   void setState(int tsapi_shutdown, Vector<TSEvent> eventList)
/*      */   {
/* 1385 */     setState(tsapi_shutdown, eventList, false);
/*      */   }
/*      */ 
/*      */   void sendSnapshot(TsapiProviderMonitor callback)
/*      */   {
/* 1392 */     if (callback == null) {
/* 1393 */       return;
/*      */     }
/*      */ 
/* 1396 */     Vector eventList = new Vector();
/*      */ 
/* 1398 */     switch (this.state)
/*      */     {
/*      */     case 2:
/* 1400 */       eventList.addElement(new TSEvent(1, this));
/*      */ 
/* 1402 */       eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiInServiceEvent()));
/*      */ 
/* 1404 */       break;
/*      */     case 1:
/* 1406 */       eventList.addElement(new TSEvent(2, this));
/*      */ 
/* 1408 */       eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiInitializingEvent()));
/*      */ 
/* 1410 */       break;
/*      */     case 0:
/* 1412 */       eventList.addElement(new TSEvent(2, this));
/*      */ 
/* 1414 */       eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiOutOfServiceEvent()));
/*      */ 
/* 1416 */       break;
/*      */     case 3:
/* 1418 */       eventList.addElement(new TSEvent(3, this));
/*      */ 
/* 1420 */       eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiShutdownEvent()));
/*      */     }
/*      */ 
/* 1427 */     if (eventList.size() <= 0) {
/*      */       return;
/*      */     }
/* 1430 */     callback.deliverEvents(eventList, true);
/*      */   }
/*      */ 
/*      */   public TsapiProviderCapabilities getTsapiProviderCapabilities()
/*      */   {
/* 1439 */     return new TsapiProviderCapabilities(this.tsCaps);
/*      */   }
/*      */ 
/*      */   public TsapiAddressCapabilities getTsapiAddressCapabilities()
/*      */   {
/* 1447 */     return new TsapiAddressCapabilities(this.tsCaps);
/*      */   }
/*      */ 
/*      */   public TsapiTerminalCapabilities getTsapiTerminalCapabilities()
/*      */   {
/* 1455 */     return new TsapiTerminalCapabilities(this.tsCaps);
/*      */   }
/*      */ 
/*      */   public TsapiCallCapabilities getTsapiCallCapabilities()
/*      */   {
/* 1460 */     return new TsapiCallCapabilities(this.tsCaps);
/*      */   }
/*      */ 
/*      */   public TsapiConnCapabilities getTsapiConnCapabilities()
/*      */   {
/* 1465 */     return new TsapiConnCapabilities(this.tsCaps);
/*      */   }
/*      */ 
/*      */   public TsapiTermConnCapabilities getTsapiTermConnCapabilities()
/*      */   {
/* 1473 */     return new TsapiTermConnCapabilities(this.tsCaps);
/*      */   }
/*      */ 
/*      */   public Vector<TSDevice> getTSRouteDevices()
/*      */   {
/* 1486 */     if (!this.securityOn) {
/* 1487 */       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered Route addresses can be accessed.");
/*      */     }
/*      */ 
/* 1492 */     Vector tsDeviceVector = new Vector();
/*      */ 
/* 1495 */     waitToInitialize();
/*      */ 
/* 1498 */     for (int i = 0; i < this.tsRouteDevices.size(); ++i) {
/* 1499 */       TSDevice device = createDevice((String)this.tsRouteDevices.elementAt(i));
/* 1500 */       if (device != null)
/* 1501 */         tsDeviceVector.addElement(device);
/*      */     }
/* 1503 */     TSDevice device = createDevice("AllRouteAddress");
/* 1504 */     if (device != null)
/* 1505 */       tsDeviceVector.addElement(device);
/* 1506 */     return tsDeviceVector;
/*      */   }
/*      */ 
/*      */   short getDeviceExt(String deviceID)
/*      */   {
/* 1512 */     if (this.tsCaps.getQueryDeviceInfo() == 0) {
/* 1513 */       return 0;
/*      */     }
/*      */     CSTAEvent event;
/*      */     try
/*      */     {
/* 1518 */       event = this.tsapi.queryDeviceInfo(deviceID, null);
/*      */     }
/*      */     catch (TsapiUnableToSendException tue)
/*      */     {
/* 1522 */       throw tue;
/*      */     }
/*      */     catch (Exception e) {
/* 1525 */       log.error(e.getMessage(), e);
/* 1526 */       return 0;
/*      */     }
/*      */ 
/* 1529 */     Object replyPriv = event.getPrivData();
/* 1530 */     if (replyPriv instanceof LucentQueryDeviceInfoConfEvent) {
/* 1531 */       if (((LucentQueryDeviceInfoConfEvent)replyPriv).getExtensionClass() == 0)
/*      */       {
/* 1533 */         return 1;
/* 1534 */       }if (((LucentQueryDeviceInfoConfEvent)replyPriv).getExtensionClass() == 1)
/*      */       {
/* 1536 */         return 2;
/*      */       }
/*      */ 
/* 1538 */       if (!(replyPriv instanceof LucentV5QueryDeviceInfoConfEvent));
/*      */     }
/*      */ 
/* 1542 */     return 0;
/*      */   }
/*      */ 
/*      */   public Vector<TSDevice> getTSACDDevices()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 1551 */     if (!isLucent()) {
/* 1552 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1557 */     if (!this.securityOn) {
/* 1558 */       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered ACD addresses can be accessed.");
/*      */     }
/*      */ 
/* 1563 */     Vector tsDeviceVector = new Vector();
/*      */ 
/* 1566 */     waitToInitialize();
/*      */ 
/* 1569 */     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
/* 1570 */       if (getDeviceExt((String)this.tsMonitorableDevices.elementAt(i)) == 2) {
/* 1571 */         TSDevice device = createDevice((String)this.tsMonitorableDevices.elementAt(i));
/*      */ 
/* 1573 */         if (device != null)
/* 1574 */           tsDeviceVector.addElement(device);
/*      */       }
/*      */     }
/* 1577 */     return tsDeviceVector;
/*      */   }
/*      */ 
/*      */   public Vector<TSDevice> getTSACDManagerDevices()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 1586 */     if (!isLucent()) {
/* 1587 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1593 */     if (!this.securityOn) {
/* 1594 */       throw new TsapiPlatformException(4, 0, "Either the security database is turned off or the user has an unrestricted access, No List will be returned but any administered ACD Manager addresses can be accessed.");
/*      */     }
/*      */ 
/* 1599 */     Vector tsDeviceVector = new Vector();
/*      */ 
/* 1602 */     waitToInitialize();
/*      */ 
/* 1605 */     for (int i = 0; i < this.tsMonitorableDevices.size(); ++i) {
/* 1606 */       if (getDeviceExt((String)this.tsMonitorableDevices.elementAt(i)) == 1) {
/* 1607 */         TSDevice device = createDevice((String)this.tsMonitorableDevices.elementAt(i));
/*      */ 
/* 1609 */         if (device != null)
/* 1610 */           tsDeviceVector.addElement(device);
/*      */       }
/*      */     }
/* 1613 */     return tsDeviceVector;
/*      */   }
/*      */ 
/*      */   public TSCall createTSCall(int callID)
/*      */   {
/* 1619 */     TSCall call = createCall(callID);
/* 1620 */     call.updateObject();
/* 1621 */     return call;
/*      */   }
/*      */ 
/*      */   public TSDevice createDevice(String name, boolean checkValidity) throws TsapiInvalidArgumentException
/*      */   {
/* 1626 */     if (name == null) {
/* 1627 */       return null;
/*      */     }
/*      */ 
/* 1630 */     return createDevice(new CSTAExtendedDeviceID(name, (short)0, (short)0), checkValidity);
/*      */   }
/*      */ 
/*      */   public TSDevice createDevice(CSTAExtendedDeviceID deviceID, boolean checkValidity)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/* 1637 */     if ((deviceID == null) || (deviceID.getDeviceIDStatus() != 0) || (deviceID.getDeviceID() == null))
/*      */     {
/* 1640 */       return null;
/*      */     }
/* 1642 */     if (checkValidity)
/*      */     {
/* 1644 */       if (deviceID.getDeviceID().equals("AllRouteAddress")) {
/* 1645 */         return createDevice(deviceID);
/*      */       }
/*      */ 
/* 1648 */       if ((this.state == 2) && (this.securityOn) && 
/* 1649 */         (!this.tsMonitorableDevices.contains(deviceID.getDeviceID()))) {
/* 1650 */         throw new TsapiInvalidArgumentException(0, 0, "not in provider's domain");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1656 */     return createDevice(deviceID);
/*      */   }
/*      */ 
/*      */   public TSConnection createTSConnection(CSTAConnectionID connID, TSDevice device) {
/* 1660 */     return createConnection(connID, device, null);
/*      */   }
/*      */ 
/*      */   void addMonitor(int monitorCrossRefID, Object observed)
/*      */   {
/* 1666 */     synchronized (this.xrefHash)
/*      */     {
/* 1668 */       Object oldObj = this.xrefHash.put(new Integer(monitorCrossRefID), observed);
/*      */ 
/* 1671 */       TtXrefHash("addMon", monitorCrossRefID, observed);
/* 1672 */       if (oldObj != null)
/* 1673 */         log.info("NOTICE: xrefHash.put() replaced " + oldObj + " for " + this);
/*      */     }
/*      */   }
/*      */ 
/*      */   void deleteMonitor(int monitorCrossRefID)
/*      */   {
/* 1680 */     this.xrefHash.remove(new Integer(monitorCrossRefID));
/*      */ 
/* 1682 */     TtXrefHash("delMon", monitorCrossRefID, "GONE");
/*      */   }
/*      */ 
/*      */   void addRoute(int routeRegisterID, TSDevice tsDevice) {
/* 1686 */     synchronized (this.routeRegHash)
/*      */     {
/* 1688 */       Object oldObj = this.routeRegHash.put(new Integer(routeRegisterID), tsDevice);
/*      */ 
/* 1690 */       if (oldObj != null)
/* 1691 */         log.info("NOTICE: routeRegHash.put() replaced " + oldObj + " for " + this);
/*      */     }
/*      */   }
/*      */ 
/*      */   void deleteRoute(int routeRegisterID)
/*      */   {
/* 1698 */     this.routeRegHash.remove(new Integer(routeRegisterID));
/*      */   }
/*      */ 
/*      */   void addPrivateXref(int xrefID, TSDevice tsDevice) {
/* 1702 */     synchronized (this.privXrefHash)
/*      */     {
/* 1704 */       Object oldObj = this.privXrefHash.put(new Integer(xrefID), tsDevice);
/* 1705 */       if (oldObj != null)
/* 1706 */         log.info("NOTICE: privXrefHash.put() replaced " + oldObj + " for " + this);
/*      */     }
/*      */   }
/*      */ 
/*      */   void deletePrivateXref(int xrefID)
/*      */   {
/* 1712 */     synchronized (this.privXrefHash)
/*      */     {
/* 1714 */       this.privXrefHash.remove(new Integer(xrefID));
/*      */     }
/*      */   }
/*      */ 
/*      */   TSDevice findACDDevice(int xrefID) {
/* 1719 */     return (TSDevice)this.privXrefHash.get(new Integer(xrefID));
/*      */   }
/*      */ 
/*      */   void addNonCallToHash(TSCall call) {
/* 1723 */     synchronized (this.nonCallHash) {
/* 1724 */       Object oldObj = this.nonCallHash.put(new Integer(call.getNonCallID()), call);
/*      */ 
/* 1726 */       if (oldObj != null)
/* 1727 */         log.info("NOTICE: nonCallHash.put() replaced " + oldObj + " for " + this);
/*      */     }
/*      */   }
/*      */ 
/*      */   void deleteNonCallFromHash(int nonCallId)
/*      */   {
/* 1733 */     this.nonCallHash.remove(new Integer(nonCallId));
/*      */   }
/*      */ 
/*      */   void dumpAgent(TSAgentKey agentKey) {
/* 1737 */     this.auditor.dumpAgent(agentKey);
/*      */   }
/*      */ 
/*      */   void dumpCall(int callID) {
/* 1741 */     this.auditor.dumpCall(callID);
/*      */   }
/*      */ 
/*      */   void dumpConn(CSTAConnectionID connID) {
/* 1745 */     this.auditor.dumpConn(connID);
/*      */   }
/*      */ 
/*      */   void callCleanup() {
/* 1749 */     Enumeration callEnum = this.callHash.elements();
/*      */ 
/* 1754 */     while (callEnum.hasMoreElements()) {
/*      */       TSCall call;
/*      */       try { call = (TSCall)callEnum.nextElement();
/*      */       } catch (NoSuchElementException e) {
/* 1758 */         log.error(e.getMessage(), e);
continue;
/* 1759 */       }
/*      */ 
/* 1762 */       if (call == null)
/*      */       {
/* 1773 */         log.error("callCleanup: handled AuditThread null call reference race condition for " + this);
/*      */       }
/*      */ 
/* 1777 */       if (call.hasReceivedCallClearedTransfer())
/*      */       {
/* 1785 */         if (System.currentTimeMillis() - call.getCallClearedTransferReceiptTime() < 3000L) {
/*      */           continue;
/*      */         }
/* 1788 */         Vector eventList = new Vector();
/*      */ 
/* 1790 */         call.setState(34, eventList);
/*      */ 
/* 1792 */         int jtapiCause = 212;
/*      */ 
/* 1794 */         this.tsEHandler.doCallMonitors(call, eventList, jtapiCause, null);
/*      */       }
/*      */ 
/* 1800 */       if (call.checkForMonitors()) {
/*      */         continue;
/*      */       }
/* 1803 */       boolean is_confirmed_that_call_is_gone = false;
/*      */ 
/* 1807 */       boolean lucent_tactics_get_an_answer = false;
/*      */ 
/* 1818 */       if (isLucentV5())
/*      */       {
/*      */         try
/*      */         {
/* 1826 */           String old_ucid = call.getUCID();
/* 1827 */           String new_ucid = call.queryUCID();
/*      */ 
/* 1829 */           if ((old_ucid != null) && (new_ucid != null) && (old_ucid.compareTo(new_ucid) != 0))
/*      */           {
/* 1836 */             is_confirmed_that_call_is_gone = true;
/*      */           }
/* 1838 */           else is_confirmed_that_call_is_gone = false;
/*      */ 
/* 1842 */           lucent_tactics_get_an_answer = true;
/*      */         }
/*      */         catch (TsapiUnableToSendException tue)
/*      */         {
/* 1846 */           throw tue;
/*      */         }
/*      */         catch (TsapiPlatformException e)
/*      */         {
/* 1854 */           if ((e.getErrorType() == 2) && (((e.getErrorCode() == 24) || (e.getErrorCode() == 11))))
/*      */           {
/* 1861 */             is_confirmed_that_call_is_gone = true;
/*      */ 
/* 1864 */             lucent_tactics_get_an_answer = true;
/* 1865 */           } else if ((e.getErrorType() == 2) && (e.getErrorCode() == 15))
/*      */           {
/* 1877 */             log.info("Error: UCID not enabled on switch - interferes with JTAPI Call Auditing");
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1888 */           log.error(e.getMessage(), e);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1902 */       if (((lucent_tactics_get_an_answer) && (is_confirmed_that_call_is_gone)) || ((!lucent_tactics_get_an_answer) && (!call.updateObject())))
/*      */       {
/* 1934 */         if (call.getTSState() == 34) {
/* 1935 */           if (this.callHash.get(new Integer(call.getCallID())) == null)
/*      */           {
/* 1937 */             log.info("Benign race condition: call (callid " + call.getCallID() + ") went INVALID while being audited");
/*      */           }
/*      */           else
/*      */           {
/* 1944 */             log.info("ERROR: removing call (callid " + call.getCallID() + ") from Provider's records - Audit indicates call had ended");
/*      */ 
/* 1947 */             call.delete();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1962 */         call.setState(34, null);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   TSCall addCallToHash(TSCall call)
/*      */   {
/* 1970 */     synchronized (this.callHash) {
/* 1971 */       Object oldObj = this.callHash.put(new Integer(call.getCallID()), call);
/* 1972 */       if (oldObj != null) {
/* 1973 */         log.info("NOTICE: callHash.put() replaced " + oldObj + " for " + this);
/*      */       }
/* 1975 */       return (TSCall)oldObj;
/*      */     }
/*      */   }
/*      */ 
/*      */   void addCallToSaveHash(TSCall call) {
/* 1980 */     this.auditor.putCall(call);
/*      */   }
/*      */ 
/*      */   void deleteCallFromHash(int callID) {
/* 1984 */     this.callHash.remove(new Integer(callID));
/*      */   }
/*      */ 
/*      */   TSConnection addConnectionToHash(TSConnection connection) {
/* 1988 */     synchronized (this.connHash) {
/* 1989 */       Object oldObj = null;
/* 1990 */       CSTAConnectionID connID = connection.getConnID();
/* 1991 */       if (connID != null) {
/* 1992 */         oldObj = this.connHash.put(connID, connection);
/*      */ 
/* 1995 */         TtConnHash("addConn", connection, connID);
/*      */ 
/* 1997 */         log.info("NOTICE: connHash.put() replaced " + oldObj + " with " + connection + " for " + this);
/*      */       }
/*      */ 
/* 2000 */       return (TSConnection)oldObj;
/*      */     }
/*      */   }
/*      */ 
/*      */   void addConnectionToSaveHash(TSConnection connection) {
/* 2005 */     this.auditor.putConn(connection);
/*      */   }
/*      */ 
/*      */   void deleteConnectionFromHash(CSTAConnectionID connID) {
/* 2009 */     if (connID != null) {
/* 2010 */       this.connHash.remove(connID);
/*      */ 
/* 2012 */       TtConnHash("delConn", "NO OBJECT", connID);
/*      */     }
/*      */   }
/*      */ 
/*      */   void addDeviceToHash(TSDevice device) {
/* 2017 */     addDeviceToHash(device.getName(), device);
/*      */   }
/*      */ 
/*      */   void addDeviceToHash(String deviceID, TSDevice device) {
/* 2021 */     synchronized (this.devHash) {
/* 2022 */       if (deviceID != null) {
/* 2023 */         Object oldObj = this.devHash.put(deviceID, device);
/* 2024 */         if (oldObj != null)
/* 2025 */           log.info("NOTICE: devHash.put() replaced " + oldObj + " for " + this);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void deleteDeviceFromHash(TSDevice device)
/*      */   {
/* 2032 */     synchronized (this.devHash) {
/* 2033 */       Vector keys = device.getKeys();
/* 2034 */       for (int i = 0; i < keys.size(); ++i) {
/* 2035 */         String key = ((CSTAExtendedDeviceID)keys.elementAt(i)).getDeviceID();
/* 2036 */         Object removedObj = this.devHash.remove(key);
/* 2037 */         log.info("NOTICE: devHash.remove() removed " + removedObj + " for device name(" + i + ") " + key);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void deleteDeviceFromHash(String _deviceID)
/*      */   {
/* 2045 */     synchronized (this.devHash) {
/* 2046 */       Object removedObj = this.devHash.remove(_deviceID);
/* 2047 */       log.info("NOTICE: devHash.remove() removed " + removedObj + " by device name " + _deviceID);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addDeviceNameToPrintingBuffer(StringBuffer aBuffer, String aName)
/*      */   {
/* 2054 */     if (aBuffer.length() > 0) {
/* 2055 */       aBuffer.append(", ");
/*      */     }
/* 2057 */     aBuffer.append(aName);
/*      */   }
/*      */ 
/*      */   void deleteInstanceOfDeviceFromHash(TSDevice _soughtObj)
/*      */   {
/* 2069 */     int keys_not_in_hash = 0;
/* 2070 */     int keys_pointing_elsewhere = 0;
/* 2071 */     Hashtable keys_pointing_at = new Hashtable();
/*      */ 
/* 2074 */     StringBuffer alias_names = new StringBuffer();
/*      */ 
/* 2078 */     StringBuffer elsewhere_names = new StringBuffer();
/*      */ 
/* 2081 */     StringBuffer not_in_hash_names = new StringBuffer();
/*      */ 
/* 2083 */     synchronized (this.devHash) {
/* 2084 */       Vector keys = _soughtObj.getKeys();
/* 2085 */       for (int i = 0; i < keys.size(); ++i) {
/* 2086 */         String key = ((CSTAExtendedDeviceID)keys.elementAt(i)).getDeviceID();
/* 2087 */         Object foundObj = this.devHash.get(key);
/* 2088 */         boolean foundAny = foundObj != null;
/* 2089 */         boolean foundThatOne = foundObj == _soughtObj;
/*      */ 
/* 2091 */         if (foundThatOne)
/*      */         {
/* 2093 */           this.devHash.remove(key);
/*      */         }
/*      */ 
/* 2096 */         if (foundThatOne) {
/* 2097 */           addDeviceNameToPrintingBuffer(alias_names, key);
/* 2098 */         } else if (foundAny)
/*      */         {
/* 2101 */           ++keys_pointing_elsewhere;
/* 2102 */           keys_pointing_at.put(key, foundObj);
/* 2103 */           addDeviceNameToPrintingBuffer(elsewhere_names, key);
/*      */         }
/*      */         else
/*      */         {
/* 2107 */           ++keys_not_in_hash;
/* 2108 */           addDeviceNameToPrintingBuffer(not_in_hash_names, key);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2115 */     if (keys_pointing_elsewhere + keys_not_in_hash == 0) {
/* 2116 */       log.info("NOTICE: devHash.remove() expected, found and removed " + _soughtObj + " by device name(s) [" + alias_names + "]");
/*      */     }
/*      */     else
/*      */     {
/* 2120 */       if (keys_pointing_elsewhere > 0) {
/* 2121 */         Iterator key_iter = keys_pointing_at.keySet().iterator();
/*      */ 
/* 2123 */         while (key_iter.hasNext()) {
/* 2124 */           String d = (String)key_iter.next();
/* 2125 */           TSDevice t = (TSDevice)keys_pointing_at.get(d);
/* 2126 */           log.info("NOTICE: devHash.remove() expected " + _soughtObj + " but found " + t + " by device name " + d + " - race condition - left latter TSDevice in hash");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2136 */       if (keys_not_in_hash > 0)
/* 2137 */         log.info("NOTICE: attempted to devHash.remove() " + _soughtObj + " by device name(s) [" + not_in_hash_names + "] but no TSDevice there by those name(s)");
/*      */     }
/*      */   }
/*      */ 
/*      */   void addTrunkToHash(String name, TSTrunk trunk)
/*      */   {
/* 2146 */     synchronized (this.trkHash) {
/* 2147 */       if (name != null) {
/* 2148 */         Object oldObj = this.trkHash.put(name, trunk);
/* 2149 */         if (oldObj != null)
/* 2150 */           log.info("NOTICE: trkHash.put() replaced " + oldObj + " for " + this);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void deleteTrunkFromHash(String name)
/*      */   {
/* 2157 */     synchronized (this.trkHash) {
/* 2158 */       while (this.trkHash.remove(name) != null);
/*      */     }
/*      */   }
/*      */ 
/*      */   void addAgentToHash(TSAgent agent)
/*      */   {
/* 2164 */     synchronized (this.agentHash) {
/* 2165 */       TSAgentKey agentKey = agent.getAgentKey();
/* 2166 */       if (agentKey != null) {
/* 2167 */         Object oldObj = this.agentHash.put(agentKey, agent);
/* 2168 */         if (oldObj != null)
/* 2169 */           log.info("NOTICE: agentHash.put() replaced " + oldObj + " for " + this);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void addAgentToSaveHash(TSAgent agent)
/*      */   {
/* 2176 */     this.auditor.putAgent(agent);
/*      */   }
/*      */ 
/*      */   void deleteAgentFromHash(TSAgentKey agentKey) {
/* 2180 */     if (agentKey != null)
/* 2181 */       this.agentHash.remove(agentKey);
/*      */   }
/*      */ 
/*      */   TSAgent findAgent(TSAgentKey agentKey)
/*      */   {
/* 2186 */     return (TSAgent)this.agentHash.get(agentKey);
/*      */   }
/*      */ 
/*      */   TSAgent createAgent(TSAgentKey agentKey, String agentID, String password)
/*      */   {
/* 2193 */     return createAgent(agentKey, agentID, password, CREATEAGENT_ACCEPT_DELETED);
/*      */   }
/*      */ 
/*      */   TSAgent createAgent(TSAgentKey agentKey, String agentID, String password, int deletedAgentSearchPolicy)
/*      */   {
/* 2202 */     TSAgent agent = null;
/*      */ 
/* 2204 */     boolean newObject = false;
/* 2205 */     boolean auditObject = false;
/*      */ 
/* 2207 */     synchronized (this.agentHash) {
/* 2208 */       agent = (TSAgent)this.agentHash.get(agentKey);
/* 2209 */       if (agent == null)
/*      */       {
/* 2212 */         if (deletedAgentSearchPolicy == CREATEAGENT_ACCEPT_DELETED) {
/* 2213 */           agent = this.auditor.getAgent(agentKey);
/*      */         }
/*      */ 
/* 2218 */         if (agent == null) {
/* 2219 */           newObject = true;
/* 2220 */           agent = new TSAgent(this, agentKey, password);
/* 2221 */           addAgentToHash(agent);
/*      */         } else {
/* 2223 */           auditObject = true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2228 */     if (newObject) {
/* 2229 */       agent.finishConstruction();
/*      */     } else {
/* 2231 */       agent.waitForConstruction();
/*      */ 
/* 2235 */       if ((agent.getACDDeviceID() == null) && (agentKey.acdDeviceID != null) && (!auditObject))
/*      */       {
/* 2237 */         agent.addToSkillsVector(agentKey.acdDeviceID);
/*      */       }
/*      */     }
/* 2240 */     return agent;
/*      */   }
/*      */ 
/*      */   TSCall findCall(int callID) {
/* 2244 */     synchronized (this.callHash) {
/* 2245 */       TSCall call = null;
/*      */ 
/* 2248 */       if (callID != 0)
/*      */       {
/* 2250 */         call = (TSCall)this.callHash.get(new Integer(callID));
/* 2251 */         if (call != null) {
/* 2252 */           return call;
/*      */         }
/*      */ 
/* 2255 */         call = this.auditor.getCall(callID);
/* 2256 */         if (call != null) {
/* 2257 */           return call;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2262 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   TSCall createCall(int callID) {
/* 2267 */     synchronized (this.callHash) {
/* 2268 */       TSCall call = null;
/*      */ 
/* 2271 */       if (callID != 0) {
/* 2272 */         call = findCall(callID);
/* 2273 */         if (call != null)
/*      */         {
/* 2275 */           return call;
/*      */         }
/*      */ 
/* 2278 */         call = new TSCall(this, callID);
/*      */       }
/*      */       else {
/* 2281 */         call = new TSCall(this, callID);
/*      */       }
/*      */ 
/* 2284 */       return call;
/*      */     }
/*      */   }
/*      */ 
/*      */   TSCall createCall(int callID, Object privateData)
/*      */   {
/* 2290 */     TSCall call = createCall(callID);
/* 2291 */     return validateCall(privateData, call, callID);
/*      */   }
/*      */ 
/*      */   TSConnection createConnection(CSTAConnectionID connID, TSDevice device, Vector<TSEvent> eventList)
/*      */   {
/* 2299 */     TSConnection conn = null;
/*      */ 
/* 2301 */     boolean newObject = false;
/*      */ 
/* 2303 */     synchronized (this.connHash) {
/* 2304 */       if (connID != null) {
/* 2305 */         conn = (TSConnection)this.connHash.get(connID);
/*      */       }
/* 2307 */       if (conn == null) {
/* 2308 */         if (connID != null) {
/* 2309 */           conn = this.auditor.getConn(connID);
/*      */         }
/* 2311 */         if (conn == null) {
/* 2312 */           newObject = true;
/* 2313 */           conn = new TSConnection(this, connID, device, false);
/* 2314 */           if (connID != null) {
/* 2315 */             addConnectionToHash(conn);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2320 */     if (newObject)
/* 2321 */       conn.finishConstruction(null, eventList);
/*      */     else {
/* 2323 */       conn.waitForConstruction();
/*      */     }
/*      */ 
/* 2326 */     return conn.getTSConn();
/*      */   }
/*      */ 
/*      */   public TSConnection getConnection(CSTAConnectionID connID)
/*      */   {
/* 2336 */     TSConnection conn = null;
/*      */ 
/* 2338 */     synchronized (this.connHash) {
/* 2339 */       if (connID != null) {
/* 2340 */         conn = (TSConnection)this.connHash.get(connID);
/*      */       }
/* 2342 */       if ((conn == null) && 
/* 2343 */         (connID != null)) {
/* 2344 */         conn = this.auditor.getConn(connID);
/*      */       }
/*      */     }
/*      */ 
/* 2348 */     if (conn == null) {
/* 2349 */       return null;
/*      */     }
/*      */ 
/* 2353 */     conn.waitForConstruction();
/*      */ 
/* 2355 */     return conn.getTSConn();
/*      */   }
/*      */ 
/*      */   boolean isConnInActiveHash(CSTAConnectionID connID)
/*      */   {
/* 2374 */     return this.connHash.get(connID) != null;
/*      */   }
/*      */ 
/*      */   boolean isConnInSaveHash(CSTAConnectionID connID)
/*      */   {
/* 2387 */     return this.auditor.getConn(connID) != null;
/*      */   }
/*      */ 
/*      */   boolean isConnInAnyHash(CSTAConnectionID connID)
/*      */   {
/* 2401 */     return (isConnInActiveHash(connID)) || (isConnInSaveHash(connID));
/*      */   }
/*      */ 
/*      */   boolean isConnInDisconnectedHash(CSTAConnectionID connID)
/*      */   {
/* 2412 */     return this.auditor.getConn(connID) != null;
/*      */   }
/*      */ 
/*      */   TSConnection createTerminalConnection(CSTAConnectionID connID, TSDevice termConnDevice, Vector<TSEvent> eventList, TSDevice connDevice)
/*      */   {
/* 2418 */     TSConnection conn = null;
/*      */ 
/* 2420 */     boolean newObject = false;
/* 2421 */     boolean auditObject = false;
/*      */ 
/* 2423 */     synchronized (this.connHash) {
/* 2424 */       conn = (TSConnection)this.connHash.get(connID);
/*      */ 
/* 2426 */       if (conn == null) {
/* 2427 */         conn = this.auditor.getConn(connID);
/*      */ 
/* 2429 */         if (conn == null) {
/* 2430 */           newObject = true;
/* 2431 */           conn = new TSConnection(this, connID, termConnDevice, true);
/* 2432 */           addConnectionToHash(conn);
/*      */         } else {
/* 2434 */           auditObject = true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2439 */     if (newObject)
/* 2440 */       conn.finishConstruction(connDevice, eventList);
/*      */     else {
/* 2442 */       conn.waitForConstruction();
/*      */     }
/*      */ 
/* 2447 */     if ((termConnDevice.isTerminal()) && (!conn.isTerminalConnection())) {
/* 2448 */       if (isLucent())
/*      */       {
/* 2452 */         if (!auditObject) {
/* 2453 */           deleteConnectionFromHash(connID);
/* 2454 */           conn = createTerminalConnection(connID, termConnDevice, eventList, connDevice);
/*      */         }
/*      */       }
/*      */       else {
/* 2458 */         conn.setTerminalConnection();
/*      */       }
/*      */     }
/*      */ 
/* 2462 */     return conn;
/*      */   }
/*      */ 
/*      */   TSDevice createDevice(String deviceID) {
/* 2466 */     return createDevice(deviceID, null);
/*      */   }
/*      */ 
/*      */   TSDevice createDevice(String deviceID, CSTAConnectionID connID) {
/* 2470 */     if (deviceID == null) {
/* 2471 */       return null;
/*      */     }
/* 2473 */     return createDevice(new CSTAExtendedDeviceID(deviceID, (short)0, (short)0), connID);
/*      */   }
/*      */ 
/*      */   public TSDevice createDevice(CSTAExtendedDeviceID deviceID)
/*      */   {
/* 2479 */     if ((deviceID == null) || (deviceID.getDeviceIDStatus() != 0) || (deviceID.getDeviceID() == null))
/*      */     {
/* 2482 */       return null;
/*      */     }
/*      */ 
/* 2485 */     TSDevice device = null;
/*      */ 
/* 2487 */     synchronized (this.devHash) {
/* 2488 */       device = (TSDevice)this.devHash.get(deviceID.getDeviceID());
/*      */ 
/* 2490 */       if (device == null) {
/* 2491 */         device = new TSDevice(this, deviceID);
/* 2492 */         addDeviceToHash(device);
/*      */       }
/*      */     }
/*      */ 
/* 2496 */     return device;
/*      */   }
/*      */ 
/*      */   TSDevice findDevice(String name) {
/* 2500 */     synchronized (this.devHash) {
/* 2501 */       return (TSDevice)this.devHash.get(name);
/*      */     }
/*      */   }
/*      */ 
/*      */   TSDevice createDevice(CSTAExtendedDeviceID deviceID, CSTAConnectionID connID)
/*      */   {
/* 2508 */     if ((deviceID == null) || (deviceID.getDeviceIDStatus() != 0) || (deviceID.getDeviceID() == null))
/*      */     {
/* 2511 */       return null;
/*      */     }
/*      */ 
/* 2514 */     TSDevice device = null;
/*      */ 
/* 2516 */     device = (TSDevice)this.devHash.get(deviceID.getDeviceID());
/*      */ 
/* 2519 */     if (device == null) {
/* 2520 */       if (connID != null)
/*      */       {
/* 2523 */         TSConnection conn = (TSConnection)this.connHash.get(connID);
/* 2524 */         if (conn == null) {
/* 2525 */           conn = this.auditor.getConn(connID);
/*      */         }
/* 2527 */         if (conn != null)
/*      */         {
/* 2530 */           device = conn.getTSDevice();
/*      */ 
/* 2535 */           synchronized (device)
/*      */           {
/* 2540 */             device.addName(deviceID);
/* 2541 */             synchronized (this.devHash)
/*      */             {
/* 2550 */               TSDevice tmpDev = (TSDevice)this.devHash.get(deviceID.getDeviceID());
/*      */ 
/* 2552 */               if (tmpDev == null)
/* 2553 */                 addDeviceToHash(device);
/*      */               else {
/* 2555 */                 device = tmpDev;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 2562 */       boolean notFound = false;
/* 2563 */       synchronized (this.devHash)
/*      */       {
/* 2572 */         if ((device == null) && ((device = (TSDevice)this.devHash.get(deviceID.getDeviceID())) == null))
/*      */         {
/* 2576 */           notFound = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2583 */       if (notFound)
/*      */       {
/* 2585 */         device = new TSDevice(this, deviceID);
/* 2586 */         synchronized (device)
/*      */         {
/* 2588 */           addDeviceToHash(device);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2594 */     return device;
/*      */   }
/*      */ 
/*      */   public TSTrunk createTSTrunk(String trkName)
/*      */   {
/* 2600 */     TSTrunk trunk = createTrunk(trkName, 1);
/* 2601 */     return trunk;
/*      */   }
/*      */ 
/*      */   TSTrunk createTrunk(String trkName, int type) {
/* 2605 */     if (trkName == null) {
/* 2606 */       return null;
/*      */     }
/*      */ 
/* 2609 */     synchronized (this.trkHash) {
/* 2610 */       TSTrunk trunk = null;
/*      */ 
/* 2612 */       trunk = (TSTrunk)this.trkHash.get(trkName);
/*      */ 
/* 2614 */       if (trunk != null) {
/* 2615 */         return trunk;
/*      */       }
/*      */ 
/* 2618 */       trunk = new TSTrunk(this, trkName, type);
/*      */ 
/* 2620 */       return trunk;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setState(int _state, Vector<TSEvent> eventList, boolean ignoreOldState)
/*      */   {
/* 2628 */     int oldCoreState = 16;
/* 2629 */     if (!ignoreOldState) {
/* 2630 */       oldCoreState = getState();
/*      */     }
/* 2632 */     synchronized (this)
/*      */     {
/* 2634 */       if (this.state == _state) {
/* 2635 */         return;
/*      */       }
/*      */ 
/* 2638 */       this.state = _state;
/*      */     }
/*      */ 
/* 2641 */     switch (this.state)
/*      */     {
/*      */     case 2:
/* 2643 */       synchronized (eventList) {
/* 2644 */         if (eventList != null)
/*      */         {
/* 2646 */           if ((ignoreOldState) || (oldCoreState != 16)) {
/* 2647 */             eventList.addElement(new TSEvent(1, this));
/*      */           }
/*      */ 
/* 2651 */           eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiInServiceEvent()));
/*      */         }
/*      */       }
/*      */ 
/* 2655 */       break;
/*      */     case 1:
/* 2657 */       synchronized (eventList) {
/* 2658 */         if (eventList != null)
/*      */         {
/* 2660 */           if ((ignoreOldState) || (oldCoreState != 17)) {
/* 2661 */             eventList.addElement(new TSEvent(2, this));
/*      */           }
/*      */ 
/* 2664 */           eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiInitializingEvent()));
/*      */         }
/*      */       }
/*      */ 
/* 2668 */       break;
/*      */     case 0:
/* 2670 */       synchronized (eventList) {
/* 2671 */         if (eventList != null)
/*      */         {
/* 2673 */           if ((ignoreOldState) || (oldCoreState != 17)) {
/* 2674 */             eventList.addElement(new TSEvent(2, this));
/*      */           }
/*      */ 
/* 2677 */           eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiOutOfServiceEvent()));
/*      */         }
/*      */       }
/*      */ 
/* 2681 */       break;
/*      */     case 3:
/* 2683 */       synchronized (eventList) {
/* 2684 */         if (eventList != null)
/*      */         {
/* 2686 */           if ((ignoreOldState) || (oldCoreState != 18)) {
/* 2687 */             eventList.addElement(new TSEvent(3, this));
/*      */           }
/*      */ 
/* 2690 */           eventList.addElement(new TSEvent(9999, this, new TsapiProviderTsapiShutdownEvent()));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2732 */       Enumeration xrefEnum = this.xrefHash.elements();
/* 2733 */       Object monitored = null;
/*      */ 
/* 2735 */       while (xrefEnum.hasMoreElements()) {
/*      */         try {
/* 2737 */           monitored = xrefEnum.nextElement();
/*      */         } catch (NoSuchElementException e) {
/* 2739 */           log.error(e.getMessage(), e);
continue;
/* 2740 */         }
/*      */ 
/* 2743 */         if (monitored == null) {
/*      */           continue;
/*      */         }
/*      */ 
/* 2747 */         if (monitored instanceof TSDevice) {
/* 2748 */           ((TSDevice)monitored).removeObservers(100, null, 0);
/*      */         }
/* 2750 */         if (monitored instanceof TSCall);
/* 2751 */         ((TSCall)monitored).removeObservers(100, null, 0);
/*      */       }
/*      */ 
/* 2755 */       if (this.tsapi != null) {
/* 2756 */         this.tsapi.shutdown();
/*      */       }
/*      */ 
/* 2759 */       this.devHash.clear();
/* 2760 */       this.trkHash.clear();
/* 2761 */       this.agentHash.clear();
/* 2762 */       this.connHash.clear();
/*      */ 
/* 2764 */       TtConnHash("dtor", "NO OBJECT", "NO CONNID");
/* 2765 */       this.callHash.clear();
/* 2766 */       this.xrefHash.clear();
/*      */ 
/* 2768 */       TtXrefHash("dtor", 0, "NO OBJECT");
/* 2769 */       this.routeRegHash.clear();
/* 2770 */       this.privXrefHash.clear();
/* 2771 */       this.providerMonitorThreads.removeAllElements();
/* 2772 */       this.addressMonitorThreads.removeAllElements();
/* 2773 */       this.terminalMonitorThreads.removeAllElements();
/* 2774 */       this.callMonitorThreads.removeAllElements();
/* 2775 */       this.routeMonitorThreads.removeAllElements();
/*      */ 
/* 2783 */       if (this.auditor != null) {
/* 2784 */         this.auditor.stopRunning();
/*      */       }
/*      */ 
/* 2787 */       disableHeartbeat();
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean isDeviceMonitorable(String name)
/*      */   {
/* 2795 */     if ((this.state == 2) && (this.securityOn)) {
/* 2796 */       if (name == null) {
/* 2797 */         return false;
/*      */       }
/* 2799 */       return this.tsMonitorableDevices.contains(name);
/*      */     }
/*      */ 
/* 2802 */     return true;
/*      */   }
/*      */ 
/*      */   boolean allowCallMonitoring() {
/* 2806 */     return this.callMonitoring;
/*      */   }
/*      */ 
/*      */   List<String> getMonitorableDevices()
/*      */   {
/* 2812 */     short[] level = { 1, 2, 3 };
/*      */ 
/* 2815 */     List listOfMonitorableDevices = new ArrayList();
/* 2816 */     for (int i = 0; i < level.length; ++i) {
/* 2817 */       int index = GET_DEVICE_INITIAL_INDEX;
/*      */       do
/*      */       {
/*      */         CSTAEvent event;
/*      */         try
/*      */         {
/* 2823 */           event = this.tsapi.getDeviceList(index, level[i]);
/*      */         } catch (Exception e) {
///* 2825 */           break label164:
	break;
/*      */         }
/* 2827 */         if (event != null) {
/* 2828 */           CSTAGetDeviceListConfEvent getDeviceListConf = (CSTAGetDeviceListConfEvent)event.getEvent();
/*      */ 
/* 2848 */           if ((getDeviceListConf.getDriverSdbLevel() == 1) || (getDeviceListConf.getDriverSdbLevel() == -1))
/*      */           {
/* 2850 */             setSecurity(false);
/* 2851 */             return listOfMonitorableDevices;
/*      */           }
/* 2853 */           for (int j = 0; j < getDeviceListConf.getDevList().length; ++j) {
/* 2854 */             String device = getDeviceListConf.getDevList()[j];
/*      */ 
/* 2856 */             if (!listOfMonitorableDevices.contains(device)) {
/* 2857 */               listOfMonitorableDevices.add(device);
/*      */             }
/*      */           }
/* 2860 */           label164: index = getDeviceListConf.getIndex();
/*      */         }
/*      */       }
/* 2862 */       while (index != GET_DEVICE_NO_MORE_INDEX);
/*      */     }
/*      */ 
/* 2865 */     return listOfMonitorableDevices;
/*      */   }
/*      */ 
/*      */   void setRouteDevices()
/*      */   {
/* 2871 */     int index = GET_DEVICE_INITIAL_INDEX;
/*      */     do
/*      */     {
/*      */       CSTAEvent event;
/*      */       try
/*      */       {
/* 2877 */         event = this.tsapi.getDeviceList(index, (short) 6);
/*      */       } catch (Exception e) {
/* 2879 */         return;
/*      */       }
/*      */ 
/* 2882 */       CSTAGetDeviceListConfEvent getDeviceListConf = (CSTAGetDeviceListConfEvent)event.getEvent();
/*      */ 
/* 2885 */       for (int j = 0; j < getDeviceListConf.getDevList().length; ++j) {
/* 2886 */         String device = getDeviceListConf.getDevList()[j];
/*      */ 
/* 2889 */         if (!this.tsRouteDevices.contains(device))
/* 2890 */           this.tsRouteDevices.addElement(device);
/*      */       }
/* 2892 */       index = getDeviceListConf.getIndex();
/*      */     }
/* 2894 */     while (index != GET_DEVICE_NO_MORE_INDEX);
/*      */   }
/*      */ 
/*      */   void setCallMonitor(boolean _callMonitoring)
/*      */   {
/* 2899 */     this.callMonitoring = _callMonitoring;
/*      */   }
/*      */ 
/*      */   void setCapabilities(TSCapabilities _tsCaps)
/*      */   {
/* 2904 */     this.tsCaps = _tsCaps;
/*      */   }
/*      */ 
/*      */   TSCapabilities getCapabilities() {
/* 2908 */     return this.tsCaps;
/*      */   }
/*      */ 
/*      */   void setSecurity(boolean _securityOn)
/*      */   {
/* 2913 */     this.securityOn = _securityOn;
/*      */   }
/*      */ 
/*      */   synchronized int getNonCallID()
/*      */   {
/* 2922 */     int[] start = { this.nonCallID, 0 };
/* 2923 */     for (int j = 0; j < 1; ++j)
/*      */     {
/* 2925 */       for (int i = start[j]; i < 100; ++i) {
/* 2926 */         if (this.nonCallIDArray[i] == NOT_IN_USE) {
/* 2927 */           this.nonCallID = i;
/* 2928 */           this.nonCallIDArray[i] = IN_USE;
/* 2929 */           return this.nonCallID;
/*      */         }
/*      */       }
/*      */     }
/* 2933 */     return -1;
/*      */   }
/*      */ 
/*      */   synchronized void releaseNonCallID(int nonCallId)
/*      */   {
/* 2941 */     this.nonCallIDArray[nonCallId] = NOT_IN_USE;
/*      */   }
/*      */ 
/*      */   TSCapabilities getCaps()
/*      */   {
/* 2949 */     TSCapabilities tsCaps = new TSCapabilities();
/*      */ 
/* 2951 */     if (isLucent()) {
/* 2952 */       tsCaps.setLucent(getLucentPDV());
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2958 */       CSTAEvent event = this.tsapi.getApiCaps();
/* 2959 */       if (event.getEvent() == null)
/*      */       {
/* 2961 */         log.info("Init Capabilities: Conf event null, enable all Capabilities for " + this);
/*      */ 
/* 2963 */         tsCaps.setAll();
/* 2964 */         return tsCaps;
/*      */       }
/* 2966 */       if (event.getEvent() instanceof CSTAGetAPICapsConfEvent) {
/* 2967 */         CSTAGetAPICapsConfEvent getAPICapsConf = (CSTAGetAPICapsConfEvent)event.getEvent();
/*      */ 
/* 2973 */         if (isLucentV5()) {
/* 2974 */           tsCaps.setAddParty(1);
/*      */         }
/* 2976 */         tsCaps.setAlternateCall(getAPICapsConf.getAlternateCall());
/* 2977 */         tsCaps.setAnswerCall(getAPICapsConf.getAnswerCall());
/* 2978 */         tsCaps.setCallCompletion(getAPICapsConf.getCallCompletion());
/*      */ 
/* 2981 */         tsCaps.setClearCall(getAPICapsConf.getClearCall());
/* 2982 */         tsCaps.setClearConnection(getAPICapsConf.getClearConnection());
/*      */ 
/* 2984 */         tsCaps.setConferenceCall(getAPICapsConf.getConferenceCall());
/*      */ 
/* 2987 */         tsCaps.setConsultationCall(getAPICapsConf.getConsultationCall());
/*      */ 
/* 2989 */         tsCaps.setDeflectCall(getAPICapsConf.getDeflectCall());
/* 2990 */         tsCaps.setPickupCall(getAPICapsConf.getPickupCall());
/* 2991 */         tsCaps.setGroupPickupCall(getAPICapsConf.getGroupPickupCall());
/*      */ 
/* 2993 */         tsCaps.setHoldCall(getAPICapsConf.getHoldCall());
/* 2994 */         tsCaps.setMakeCall(getAPICapsConf.getMakeCall());
/* 2995 */         tsCaps.setMakePredictiveCall(getAPICapsConf.getMakePredictiveCall());
/*      */ 
/* 2997 */         tsCaps.setQueryMwi(getAPICapsConf.getQueryMwi());
/* 2998 */         tsCaps.setQueryDnd(getAPICapsConf.getQueryDnd());
/* 2999 */         tsCaps.setQueryFwd(getAPICapsConf.getQueryFwd());
/* 3000 */         tsCaps.setQueryAgentState(getAPICapsConf.getQueryAgentState());
/*      */ 
/* 3002 */         tsCaps.setQueryLastNumber(getAPICapsConf.getQueryLastNumber());
/*      */ 
/* 3004 */         tsCaps.setQueryDeviceInfo(getAPICapsConf.getQueryDeviceInfo());
/*      */ 
/* 3006 */         tsCaps.setReconnectCall(getAPICapsConf.getReconnectCall());
/* 3007 */         tsCaps.setRetrieveCall(getAPICapsConf.getRetrieveCall());
/* 3008 */         tsCaps.setSetMwi(getAPICapsConf.getSetMwi());
/* 3009 */         tsCaps.setSetDnd(getAPICapsConf.getSetDnd());
/* 3010 */         tsCaps.setSetFwd(getAPICapsConf.getSetFwd());
/* 3011 */         tsCaps.setSetAgentState(getAPICapsConf.getSetAgentState());
/* 3012 */         tsCaps.setTransferCall(getAPICapsConf.getTransferCall());
/* 3013 */         tsCaps.setEventReport(getAPICapsConf.getEventReport());
/* 3014 */         tsCaps.setCallClearedEvent(getAPICapsConf.getCallClearedEvent());
/*      */ 
/* 3016 */         tsCaps.setConferencedEvent(getAPICapsConf.getConferencedEvent());
/*      */ 
/* 3018 */         tsCaps.setConnectionClearedEvent(getAPICapsConf.getConnectionClearedEvent());
/*      */ 
/* 3020 */         tsCaps.setDeliveredEvent(getAPICapsConf.getDeliveredEvent());
/*      */ 
/* 3023 */         tsCaps.setDivertedEvent(getAPICapsConf.getDivertedEvent());
/* 3024 */         tsCaps.setEstablishedEvent(getAPICapsConf.getEstablishedEvent());
/*      */ 
/* 3026 */         tsCaps.setFailedEvent(getAPICapsConf.getFailedEvent());
/* 3027 */         tsCaps.setHeldEvent(getAPICapsConf.getHeldEvent());
/* 3028 */         tsCaps.setNetworkReachedEvent(getAPICapsConf.getNetworkReachedEvent());
/*      */ 
/* 3030 */         tsCaps.setOriginatedEvent(getAPICapsConf.getOriginatedEvent());
/*      */ 
/* 3032 */         tsCaps.setQueuedEvent(getAPICapsConf.getQueuedEvent());
/* 3033 */         tsCaps.setRetrievedEvent(getAPICapsConf.getRetrievedEvent());
/*      */ 
/* 3036 */         tsCaps.setServiceInitiatedEvent(getAPICapsConf.getServiceInitiatedEvent());
/*      */ 
/* 3039 */         tsCaps.setTransferredEvent(getAPICapsConf.getTransferredEvent());
/*      */ 
/* 3041 */         tsCaps.setCallInformationEvent(getAPICapsConf.getCallInformationEvent());
/*      */ 
/* 3043 */         tsCaps.setDoNotDisturbEvent(getAPICapsConf.getDoNotDisturbEvent());
/*      */ 
/* 3045 */         tsCaps.setForwardingEvent(getAPICapsConf.getForwardingEvent());
/*      */ 
/* 3047 */         tsCaps.setMessageWaitingEvent(getAPICapsConf.getMessageWaitingEvent());
/*      */ 
/* 3049 */         tsCaps.setLoggedOnEvent(getAPICapsConf.getLoggedOnEvent());
/* 3050 */         tsCaps.setLoggedOffEvent(getAPICapsConf.getLoggedOffEvent());
/*      */ 
/* 3053 */         tsCaps.setNotReadyEvent(getAPICapsConf.getNotReadyEvent());
/* 3054 */         tsCaps.setReadyEvent(getAPICapsConf.getReadyEvent());
/* 3055 */         tsCaps.setWorkNotReadyEvent(getAPICapsConf.getWorkNotReadyEvent());
/*      */ 
/* 3057 */         tsCaps.setWorkReadyEvent(getAPICapsConf.getWorkReadyEvent());
/*      */ 
/* 3060 */         tsCaps.setBackInServiceEvent(getAPICapsConf.getBackInServiceEvent());
/*      */ 
/* 3062 */         tsCaps.setOutOfServiceEvent(getAPICapsConf.getOutOfServiceEvent());
/*      */ 
/* 3064 */         tsCaps.setPrivateEvent(getAPICapsConf.getPrivateEvent());
/* 3065 */         tsCaps.setRouteRequestEvent(getAPICapsConf.getRouteRequestEvent());
/*      */ 
/* 3067 */         tsCaps.setReRoute(getAPICapsConf.getReRoute());
/* 3068 */         tsCaps.setRouteSelect(getAPICapsConf.getRouteSelect());
/* 3069 */         tsCaps.setRouteUsedEvent(getAPICapsConf.getRouteUsedEvent());
/*      */ 
/* 3072 */         tsCaps.setRouteEndEvent(getAPICapsConf.getRouteEndEvent());
/* 3073 */         tsCaps.setMonitorDevice(getAPICapsConf.getMonitorDevice());
/* 3074 */         tsCaps.setMonitorCall(getAPICapsConf.getMonitorCall());
/* 3075 */         tsCaps.setMonitorCallsViaDevice(getAPICapsConf.getMonitorCallsViaDevice());
/*      */ 
/* 3077 */         tsCaps.setChangeMonitorFilter(getAPICapsConf.getChangeMonitorFilter());
/*      */ 
/* 3079 */         tsCaps.setMonitorStop(getAPICapsConf.getMonitorStop());
/* 3080 */         tsCaps.setMonitorEnded(getAPICapsConf.getMonitorEnded());
/* 3081 */         tsCaps.setSnapshotDeviceReq(getAPICapsConf.getSnapshotDeviceReq());
/*      */ 
/* 3083 */         tsCaps.setSnapshotCallReq(getAPICapsConf.getSnapshotCallReq());
/*      */ 
/* 3085 */         tsCaps.setEscapeService(getAPICapsConf.getEscapeService());
/* 3086 */         tsCaps.setPrivateStatusEvent(getAPICapsConf.getPrivateStatusEvent());
/*      */ 
/* 3088 */         tsCaps.setEscapeServiceEvent(getAPICapsConf.getEscapeServiceEvent());
/*      */ 
/* 3090 */         tsCaps.setEscapeServiceConf(getAPICapsConf.getEscapeServiceConf());
/*      */ 
/* 3092 */         tsCaps.setSendPrivateEvent(getAPICapsConf.getSendPrivateEvent());
/*      */ 
/* 3094 */         tsCaps.setSysStatReq(getAPICapsConf.getSysStatReq());
/* 3095 */         tsCaps.setSysStatStart(getAPICapsConf.getSysStatStart());
/* 3096 */         tsCaps.setSysStatStop(getAPICapsConf.getSysStatStop());
/* 3097 */         tsCaps.setChangeSysStatFilter(getAPICapsConf.getChangeSysStatFilter());
/*      */ 
/* 3099 */         tsCaps.setSysStatReqEvent(getAPICapsConf.getSysStatReqEvent());
/*      */ 
/* 3101 */         tsCaps.setSysStatReqConf(getAPICapsConf.getSysStatReqConf());
/*      */ 
/* 3104 */         tsCaps.setSysStatEvent(getAPICapsConf.getSysStatEvent());
/*      */ 
/* 3110 */         Object replyPriv = event.getPrivData();
/* 3111 */         if ((replyPriv instanceof LucentGetAPICapsConfEvent) && 
/* 3112 */           (replyPriv instanceof LucentV5GetAPICapsConfEvent) && 
/* 3113 */           (replyPriv instanceof LucentV7GetAPICapsConfEvent))
/*      */         {
/* 3115 */           LucentV7GetAPICapsConfEvent cf = (LucentV7GetAPICapsConfEvent)replyPriv;
/*      */ 
/* 3117 */           setAdministeredSwitchSoftwareVersion(cf.getAdministeredSwitchSoftwareVersion());
/*      */ 
/* 3119 */           setSwitchSoftwareVersion(cf.getSwitchSoftwareVersion());
/*      */ 
/* 3121 */           setOfferType(cf.getOfferType());
/* 3122 */           setServerType(cf.getServerType());
/* 3123 */           setMonitorCallsViaDevice(cf.getMonitorCallsViaDevice());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 3135 */         log.info("Init Capabilities: expected conf event with pduType 125,received conf event with pduType " + event.getEvent().getPDU() + ", enable all Capabilities" + " for " + this);
/*      */ 
/* 3137 */         tsCaps.setAll();
/* 3138 */         return tsCaps;
/*      */       }
/*      */     } catch (Exception e) {
/* 3141 */       log.error("Init Capabilities: Exception, enable all Capabilities - Exception: " + e + " for " + this);
/*      */ 
/* 3143 */       tsCaps.setAll();
/*      */     }
/*      */ 
/* 3146 */     return tsCaps;
/*      */   }
/*      */ 
/*      */   boolean getCallMonitor()
/*      */   {
/*      */     CSTAEvent event;
/*      */     try {
/* 3153 */       event = this.tsapi.CSTAQueryCallMonitor();
/*      */     }
/*      */     catch (TsapiUnableToSendException tue)
/*      */     {
/* 3160 */       throw tue;
/*      */     }
/*      */     catch (Exception e) {
/* 3163 */       log.error(e.getMessage(), e);
/* 3164 */       return false;
/*      */     }
/* 3166 */     CSTAQueryCallMonitorConfEvent qcmConf = (CSTAQueryCallMonitorConfEvent)event.getEvent();
/*      */ 
/* 3168 */     return qcmConf.isCallMonitor();
/*      */   }
/*      */ 
/*      */   public void addMonitor(TsapiProviderMonitor monitor)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/* 3174 */     synchronized (this.obsSync)
/*      */     {
/* 3176 */       if (this.monitors.contains(monitor)) {
/* 3177 */         return;
/*      */       }
/*      */ 
/* 3181 */       this.monitors.addElement(monitor);
/*      */ 
/* 3184 */       monitor.addReference();
/*      */     }
/*      */ 
/* 3187 */     sendSnapshot(monitor);
/*      */   }
/*      */ 
/*      */   public Vector<TsapiProviderMonitor> getMonitors()
/*      */   {
/* 3193 */     return new Vector(this.monitors);
/*      */   }
/*      */ 
/*      */   void removeMonitors(int cause, Object privateData)
/*      */   {
/* 3199 */     Vector obs = new Vector(this.monitors);
/* 3200 */     for (int i = 0; i < obs.size(); ++i)
/* 3201 */       removeMonitor((TsapiProviderMonitor)obs.elementAt(i), cause, privateData);
/*      */   }
/*      */ 
/*      */   public void removeMonitor(TsapiProviderMonitor monitor)
/*      */   {
/* 3208 */     removeMonitor(monitor, 100, null);
/*      */   }
/*      */ 
/*      */   void removeMonitor(TsapiProviderMonitor monitor, int cause, Object privateData) {
/* 3212 */     if (this.monitors.removeElement(monitor))
/* 3213 */       monitor.deleteReference(cause, privateData);
/*      */   }
/*      */ 
/*      */   public Vector<TsapiProviderMonitor> getProviderMonitorThreads() {
/* 3217 */     return this.providerMonitorThreads;
/*      */   }
/*      */ 
/*      */   public void addProviderMonitorThread(TsapiProviderMonitor obs)
/*      */   {
/* 3223 */     if (this.providerMonitorThreads.contains(obs)) {
/* 3224 */       return;
/*      */     }
/*      */ 
/* 3227 */     this.providerMonitorThreads.addElement(obs);
/*      */   }
/*      */ 
/*      */   public void removeProviderMonitorThread(TsapiProviderMonitor obs) {
/* 3231 */     this.providerMonitorThreads.removeElement(obs);
/*      */   }
/*      */ 
/*      */   public Vector<TsapiAddressMonitor> getAddressMonitorThreads() {
/* 3235 */     return this.addressMonitorThreads;
/*      */   }
/*      */ 
/*      */   public void addAddressMonitorThread(TsapiAddressMonitor obs) {
/* 3239 */     if (this.addressMonitorThreads.contains(obs)) {
/* 3240 */       return;
/*      */     }
/*      */ 
/* 3243 */     this.addressMonitorThreads.addElement(obs);
/*      */   }
/*      */ 
/*      */   public void removeAddressMonitorThread(TsapiAddressMonitor obs) {
/* 3247 */     this.addressMonitorThreads.removeElement(obs);
/*      */   }
/*      */ 
/*      */   public Vector<TsapiRouteMonitor> getRouteMonitorThreads() {
/* 3251 */     return this.routeMonitorThreads;
/*      */   }
/*      */ 
/*      */   public void addRouteMonitorThread(TsapiRouteMonitor obs) {
/* 3255 */     if (this.routeMonitorThreads.contains(obs)) {
/* 3256 */       return;
/*      */     }
/*      */ 
/* 3259 */     this.routeMonitorThreads.addElement(obs);
/*      */   }
/*      */ 
/*      */   public void removeRouteMonitorThread(TsapiRouteMonitor obs) {
/* 3263 */     this.routeMonitorThreads.removeElement(obs);
/*      */   }
/*      */ 
/*      */   public Vector<TsapiTerminalMonitor> getTerminalMonitorThreads() {
/* 3267 */     return this.terminalMonitorThreads;
/*      */   }
/*      */ 
/*      */   public void addTerminalMonitorThread(TsapiTerminalMonitor obs) {
/* 3271 */     if (this.terminalMonitorThreads.contains(obs)) {
/* 3272 */       return;
/*      */     }
/*      */ 
/* 3275 */     this.terminalMonitorThreads.addElement(obs);
/*      */   }
/*      */ 
/*      */   public void removeTerminalMonitorThread(TsapiTerminalMonitor obs) {
/* 3279 */     this.terminalMonitorThreads.removeElement(obs);
/*      */   }
/*      */ 
/*      */   public Vector<TsapiCallMonitor> getCallMonitorThreads() {
/* 3283 */     return this.callMonitorThreads;
/*      */   }
/*      */ 
/*      */   public void addCallMonitorThread(TsapiCallMonitor obs) {
/* 3287 */     if (this.callMonitorThreads.contains(obs)) {
/* 3288 */       return;
/*      */     }
/*      */ 
/* 3291 */     this.callMonitorThreads.addElement(obs);
/*      */   }
/*      */ 
/*      */   public void removeCallMonitorThread(TsapiCallMonitor obs) {
/* 3295 */     this.callMonitorThreads.removeElement(obs);
/*      */   }
/*      */ 
/*      */   private void setInstanceNumber()
/*      */   {
/* 3302 */     synchronized (g_lock) {
/* 3303 */       this.m_instanceNumber = (++g_instanceNumber);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getInstanceNumber()
/*      */   {
/* 3312 */     return this.m_instanceNumber;
/*      */   }
/*      */ 
/*      */   public TSEventHandler getTsEHandler()
/*      */   {
/* 3326 */     return this.tsEHandler;
/*      */   }
/*      */ 
/*      */   public String getAdministeredSwitchSoftwareVersion()
/*      */   {
/* 3333 */     return this.administeredSwitchSoftwareVersion;
/*      */   }
/*      */ 
/*      */   void setAdministeredSwitchSoftwareVersion(String administeredSwitchSoftwareVersion)
/*      */   {
/* 3342 */     this.administeredSwitchSoftwareVersion = administeredSwitchSoftwareVersion;
/*      */   }
/*      */ 
/*      */   public String getOfferType()
/*      */   {
/* 3349 */     return this.offerType;
/*      */   }
/*      */ 
/*      */   void setOfferType(String offerType)
/*      */   {
/* 3357 */     this.offerType = offerType;
/*      */   }
/*      */ 
/*      */   public String getServerType()
/*      */   {
/* 3364 */     return this.serverType;
/*      */   }
/*      */ 
/*      */   void setServerType(String serverType)
/*      */   {
/* 3372 */     this.serverType = serverType;
/*      */   }
/*      */ 
/*      */   public String getSwitchSoftwareVersion()
/*      */   {
/* 3379 */     return this.switchSoftwareVersion;
/*      */   }
/*      */ 
/*      */   void setSwitchSoftwareVersion(String switchSoftwareVersion)
/*      */   {
/* 3387 */     this.switchSoftwareVersion = switchSoftwareVersion;
/*      */   }
/*      */ 
/*      */   public boolean getMonitorCallsViaDevice()
/*      */   {
/* 3394 */     return this.monitorCallsViaDevice;
/*      */   }
/*      */ 
/*      */   public String requestPrivileges()
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/* 3402 */     RequestPrivilegesConfHandler handler = new RequestPrivilegesConfHandler(this);
/*      */ 
/* 3404 */     boolean request_failed = true;
/*      */     try {
/* 3406 */       this.tsapi.requestPrivileges(null, handler);
/* 3407 */       request_failed = false;
/* 3408 */       String str = handler.get_nonce();
/*      */ 
/* 3435 */       return str;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 3412 */       switch (e.getErrorType())
/*      */       {
/*      */       case 120:
/*      */       case 126:
/*      */       case 127:
/*      */       case 128:
/*      */       case 121:
/*      */       case 122:
/*      */       case 123:
/*      */       case 124:
/*      */       case 125:
/*      */       }
/*      */ 
/* 3427 */       throw new TsapiPlatformException(e.getErrorType(), e.getErrorCode(), "Unexpected requestPrivileges TSAPI failure: " + e);
/*      */     }
/*      */     finally
/*      */     {
/* 3434 */       if (request_failed)
/* 3435 */         shutdown();
/*      */     }
/*      */   }
/*      */ 
/*      */   void setMonitorCallsViaDevice(boolean monitorCallsViaDevice)
/*      */   {
/* 3445 */     this.monitorCallsViaDevice = monitorCallsViaDevice;
/*      */   }
/*      */ 
/*      */   public void setPrivileges(String xmlData)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/* 3454 */     ConfHandler handler = new SetPrivilegesConfHandler(this);
/*      */     try {
/* 3456 */       this.tsapi.setPrivileges(xmlData, null, handler);
/* 3457 */       return;
/*      */     } catch (TsapiInvalidArgumentException e) {
/* 3459 */       shutdown();
/* 3460 */       throw e;
/*      */     } catch (TsapiPlatformException e) {
/* 3462 */       shutdown();
/* 3463 */       throw new TsapiPlatformException(e.getErrorType(), e.getErrorCode(), "setPrivileges TSAPI failure: " + e);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3467 */       shutdown();
/* 3468 */       throw new TsapiPlatformException(4, 0, "Unexpected setPrivileges TSAPI failure: " + e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public IDomainDevice addCallToDomain(IDomainDevice d, IDomainCall c)
/*      */   {
/* 3481 */     return this.m_providerTracker.addCallToDomain(d, c);
/*      */   }
/*      */ 
/*      */   public void changeCallIDInDomain(int old_callid, int new_callid)
/*      */   {
/* 3486 */     this.m_providerTracker.changeCallIDInDomain(old_callid, new_callid);
/*      */   }
/*      */ 
/*      */   public IDomainDevice getDomainCallIsIn(IDomainCall c)
/*      */   {
/* 3491 */     return this.m_providerTracker.getDomainCallIsIn(c);
/*      */   }
/*      */ 
/*      */   public void removeCallFromDomain(IDomainCall c)
/*      */   {
/* 3496 */     this.m_providerTracker.removeCallFromDomain(c);
/*      */   }
/*      */ 
/*      */   public void removeAllCallsForDomain(IDomainDevice d)
/*      */   {
/* 3501 */     this.m_providerTracker.removeAllCallsForDomain(d);
/*      */   }
/*      */ 
/*      */   public boolean isCallInAnyDomain(IDomainCall c)
/*      */   {
/* 3506 */     return this.m_providerTracker.isCallInAnyDomain(c);
/*      */   }
/*      */ 
/*      */   public void dumpDomainData(String indent)
/*      */   {
/* 3511 */     this.m_providerTracker.dumpDomainData(indent);
/*      */   }
/*      */ 
/*      */   public IDomainCall getDomainCall(int callid)
/*      */   {
/* 3520 */     return findCall(callid);
/*      */   }
/*      */ 
/*      */   public IDomainDevice getDomainDevice(String name)
/*      */   {
/* 3529 */     return findDevice(name);
/*      */   }
/*      */ 
/*      */   public void logln(String s)
/*      */   {
/* 3537 */     if (log.isInfoEnabled())
/* 3538 */       log.info(s);
/*      */   }
/*      */ 
/*      */   Object getMonitoredObject(int xrefID)
/*      */   {
/* 3549 */     return this.xrefHash.get(new Integer(xrefID));
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 3554 */     return "TSProvider[#" + getInstanceNumber() + "]@" + Integer.toHexString(super.hashCode());
/*      */   }
/*      */ 
/*      */   TSCall validateCall(Object privateData, TSCall call, int callID)
/*      */   {
/* 3565 */     if (call == null) {
/* 3566 */       return call;
/*      */     }
/*      */ 
/* 3574 */     if ((privateData instanceof LucentTransferredEvent) || (privateData instanceof LucentConferencedEvent))
/*      */     {
/* 3576 */       return call;
/*      */     }
/* 3578 */     if (privateData instanceof HasUCID) {
/* 3579 */       if (((HasUCID)privateData).getUcid() == null) {
/* 3580 */         return call;
/*      */       }
/* 3582 */       if (call.ucid == null) {
/* 3583 */         return call;
/*      */       }
/* 3585 */       if (((HasUCID)privateData).getUcid().compareTo(call.ucid) != 0)
/*      */       {
/* 3587 */         log.info("Mismatched UCID for validateCall removing stale call obj " + call);
/*      */ 
/* 3589 */         log.info("UCID for validateCall for the new call is " + ((HasUCID)privateData).getUcid());
/*      */ 
/* 3598 */         call.setState(34, null);
/* 3599 */         dumpCall(callID);
/* 3600 */         TSCall newCall = createCall(callID);
/* 3601 */         return newCall;
/*      */       }
/* 3603 */       return call;
/*      */     }
/*      */ 
/* 3606 */     return call;
/*      */   }
/*      */ 
/*      */   public String getMonitoredObjects()
/*      */   {
/* 3611 */     StringBuffer buffer = new StringBuffer();
/* 3612 */     for (Map.Entry entry : this.xrefHash.entrySet()) {
/* 3613 */       buffer.append(entry.getKey() + ":" + entry.getValue() + "\n");
/*      */     }
/* 3615 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */   class TsapiProReaderTask extends TimerTask
/*      */   {
/*      */     TsapiProReaderTask()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void run()
/*      */     {
/* 1188 */       Tsapi.updateVolatileConfigurationValues();
/* 1189 */       if (!Tsapi.isRefreshPeriodChanged())
/*      */         return;
/* 1191 */       TSProviderImpl.this.timerThread.cancel();
/* 1192 */       int interval = Tsapi.getRefreshIntervalForTsapiPro() * 1000;
/* 1193 */       TSProviderImpl.this.timerThread = new Timer("TsapiProReader");
/* 1194 */       TSProviderImpl.this.timerThread.schedule(new TsapiProReaderTask(), interval, interval);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSProviderImpl
 * JD-Core Version:    0.5.4
 */