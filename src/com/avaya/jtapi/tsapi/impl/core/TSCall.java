/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.ITsapiException;
/*      */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*      */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*      */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
/*      */ import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
/*      */ import com.avaya.jtapi.tsapi.TsapiUnableToSendException;
/*      */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*      */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*      */ import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMonitorFilter;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*      */ import com.avaya.jtapi.tsapi.csta1.HasUCID;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentDeviceHistoryEntry;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentMakePredictiveCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentOriginalCallInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryUcid;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryUcidConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSetBillRate;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCall;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentUserEnteredCode;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV6MakePredictiveCall;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiAddress;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiCallCapabilities;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*      */ import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
/*      */ import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;
/*      */ import java.util.Vector;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public final class TSCall
/*      */   implements IDomainCall
/*      */ {
/*  100 */   private static Logger log = Logger.getLogger(TSCall.class);
/*      */   Object obsSync;
/*      */   TSProviderImpl provider;
/*      */   int state;
/*      */   int callID;
/*  116 */   int nonCallID = -1;
/*      */   private final Vector<TSConnection> connections;
/*      */   private final Vector<TSConnection> staleConnections;
/*  125 */   TSDevice internalDeviceMonitor = null;
/*      */   TSConnection confController;
/*      */   TSConnection xferController;
/*  130 */   boolean confEnable = true;
/*  131 */   boolean xferEnable = true;
/*  132 */   boolean delayVDNremoveCallFromDomain = false;
/*      */ 
/*  149 */   boolean receivedCallClearedTransfer = false;
/*      */ 
/*  151 */   long callClearedTransferReceiptTime = 0L;
/*      */   private final Vector<TsapiCallMonitor> monitorThreads;
/*  156 */   boolean needSnapshot = true;
/*      */ 
/*  159 */   int monitorCrossRefID = 0;
/*      */ 
/*  166 */   boolean wasEverMonitoredByCallsViaDevice = false;
/*      */ 
/*  171 */   boolean monitorPending = false;
/*      */ 
/*  180 */   Object replyPriv = null;
/*      */ 
/*  183 */   TSDevice calledDevice = null;
/*      */ 
/*  186 */   TSDevice callingAddress = null;
/*  187 */   TSDevice callingTerminal = null;
/*      */ 
/*  190 */   TSDevice lastRedirectionDevice = null;
/*      */ 
/*  192 */   TSDevice distributingDevice = null;
/*  193 */   TSDevice deliveringACDDevice = null;
/*      */ 
/*  195 */   TSDevice distributingVDN = null;
/*      */   private final Vector<DeviceObs> deviceObsVector;
/*      */   private final Vector<TsapiCallMonitor> staleObsVector;
/*  200 */   UserToUserInfo uui = null;
/*  201 */   LookaheadInfo lai = null;
/*  202 */   UserEnteredCode uec = null;
/*  203 */   OriginalCallInfo oci = null;
/*  204 */   short reason = 0;
/*      */ 
/*  207 */   String ucid = null;
/*      */   CSTACallOriginatorInfo callOriginatorInfo;
/*      */   boolean flexibleBilling;
/*  212 */   V7DeviceHistoryEntry[] deviceHistory = null;
/*      */   private final Vector<TSTrunk> trkVector;
/*  216 */   TSCall handOffCall = null;
/*      */   final Vector<CallbackAndType> callbackAndTypeVector;
/*  219 */   SnapshotCallConfHandler currentSnapshotHandler = null;
/*  220 */   SnapshotCallConfHandler futureAsynchronousSnapshotHandler = null;
/*      */   String digits;
/*  238 */   private short cstaCause = -1;
/*      */ 
/*  244 */   private boolean snapshotCallConfPending = false;
/*      */ 
/*  249 */   private boolean needRedoSnapshotCall = false;
/*      */ 
/*  251 */   int refCount = 0;
/*      */   int connection_wait_limit;
/*  270 */   private TSCallObjectAge my_age = new TSCallObjectAge();
/*      */ 
/*  274 */   TsapiAddress refVDN = null;
/*      */ 
/*      */   TSCall(TSProviderImpl _provider, int _callID)
/*      */   {
/*  281 */     this.provider = _provider;
/*  282 */     this.state = 32;
/*  283 */     this.connections = new Vector();
/*  284 */     this.staleConnections = new Vector();
/*  285 */     this.trkVector = new Vector();
/*  286 */     this.monitorThreads = new Vector();
/*  287 */     this.deviceObsVector = new Vector();
/*  288 */     this.staleObsVector = new Vector();
/*  289 */     this.callbackAndTypeVector = new Vector();
/*  290 */     this.obsSync = new Object();
/*  291 */     setCallID(_callID);
/*  292 */     setConnection_wait_limit(2);
/*  293 */     log.info("Constructing call " + this + " with ID " + _callID + " for " + this.provider);
/*  294 */     if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
/*  295 */       log.debug("Updating call count in the statistics collector.");
/*  296 */       PerfStatisticsCollector.updateCallCount();
/*      */     }
/*      */   }
/*      */ 
/*      */   void dump(String indent)
/*      */   {
/*  302 */     log.trace(indent + "***** CALL DUMP *****");
/*  303 */     log.trace(indent + "TSCall: " + this);
/*  304 */     log.trace(indent + "TSCall ID: " + this.callID);
/*  305 */     log.trace(indent + "TSCall UCID: " + this.ucid);
/*  306 */     log.trace(indent + "TSCall non-Call ID: " + this.nonCallID);
/*  307 */     log.trace(indent + "TSCall state: " + this.state);
/*  308 */     log.trace(indent + "TSCall needSnapshot: " + this.needSnapshot);
/*  309 */     log.trace(indent + "TSCall age: " + this.my_age);
/*  310 */     log.trace(indent + "TSCall connections: ");
/*  311 */     synchronized (this.connections)
/*      */     {
/*  314 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/*  316 */         TSConnection conn = (TSConnection)this.connections.elementAt(i);
/*  317 */         conn.dump(indent + " ");
/*      */       }
/*      */     }
/*  320 */     log.trace(indent + "TSCall trunks: ");
/*  321 */     synchronized (this.trkVector)
/*      */     {
/*  324 */       for (int i = 0; i < this.trkVector.size(); ++i)
/*      */       {
/*  326 */         TSTrunk trk = (TSTrunk)this.trkVector.elementAt(i);
/*  327 */         trk.dump(indent + " ");
/*      */       }
/*      */     }
/*  330 */     log.trace(indent + "TSCall handOffCall: " + this.handOffCall);
/*  331 */     log.trace(indent + "TSCall stale connections: ");
/*  332 */     synchronized (this.staleConnections)
/*      */     {
/*  335 */       for (int i = 0; i < this.staleConnections.size(); ++i)
/*      */       {
/*  337 */         TSConnection conn = (TSConnection)this.staleConnections.elementAt(i);
/*  338 */         conn.dump(indent + " ");
/*      */       }
/*      */     }
/*  341 */     log.trace(indent + "TSCall trunks: ");
/*  342 */     log.trace(indent + "TSCall Monitor Threads: ");
/*  343 */     synchronized (this.monitorThreads)
/*      */     {
/*  346 */       for (int i = 0; i < this.monitorThreads.size(); ++i)
/*      */       {
/*  348 */         TsapiCallMonitor oThreads = (TsapiCallMonitor)this.monitorThreads.elementAt(i);
/*  349 */         oThreads.dump(indent + " ");
/*      */       }
/*      */     }
/*  352 */     log.trace(indent + "TSCall Device Monitor Threads: ");
/*  353 */     synchronized (this.deviceObsVector)
/*      */     {
/*  356 */       for (int i = 0; i < this.deviceObsVector.size(); ++i)
/*      */       {
/*  358 */         TsapiCallMonitor oThreads = ((DeviceObs)this.deviceObsVector.elementAt(i)).callback;
/*  359 */         oThreads.dump(indent + " ");
/*      */       }
/*      */     }
/*  362 */     log.trace(indent + "TSCall Stale Monitor Threads: ");
/*  363 */     synchronized (this.staleObsVector)
/*      */     {
/*  366 */       for (int i = 0; i < this.staleObsVector.size(); ++i)
/*      */       {
/*  368 */         TsapiCallMonitor oThreads = (TsapiCallMonitor)this.staleObsVector.elementAt(i);
/*  369 */         oThreads.dump(indent + " ");
/*      */       }
/*      */     }
/*  372 */     log.trace(indent + "TSCall CallbackAndType Monitor Threads: ");
/*  373 */     synchronized (this.callbackAndTypeVector)
/*      */     {
/*  375 */       CallbackAndType cbAndType = null;
/*      */       int i;
/*  377 */       for (i = 0; i < this.callbackAndTypeVector.size(); ++i)
/*      */       {
/*  379 */         cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
/*  380 */         TsapiCallMonitor oThreads = cbAndType.callback;
/*  381 */         oThreads.dump(indent + " ");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  388 */     int i = 0;
/*  389 */     for (String str : LucentUserToUserInfo.print(TsapiPromoter.demoteUserToUserInfo(getUUI()), "CallUUI", indent + " "))
/*      */     {
/*  392 */       if (i == 0) {
/*  393 */         log.trace(indent + "TSCALL UUI" + str);
/*      */       }
/*      */       else {
/*  396 */         log.trace(str);
/*      */       }
/*  398 */       ++i;
/*      */     }
/*  400 */     log.trace(indent + "***** CALL DUMP END *****");
/*      */   }
/*      */ 
/*      */   public TSProviderImpl getTSProviderImpl()
/*      */   {
/*  408 */     return this.provider;
/*      */   }
/*      */ 
/*      */   public int getState()
/*      */   {
/*  414 */     updateObject();
/*      */ 
/*  416 */     return this.state;
/*      */   }
/*      */ 
/*      */   int getTSState()
/*      */   {
/*  421 */     return this.state;
/*      */   }
/*      */ 
/*      */   int getStateFromServer()
/*      */   {
/*  427 */     if (!updateSuspiciousObject()) {
/*  428 */       throw new TsapiPlatformException(4, 0, "Could not get state from the telephony server. [CallId=" + getCallID() + "]");
/*      */     }
/*  430 */     return this.state;
/*      */   }
/*      */ 
/*      */   public Object getPrivateData()
/*      */   {
/*  438 */     if (this.replyPriv instanceof CSTAPrivate)
/*  439 */       return this.replyPriv;
/*  440 */     return null;
/*      */   }
/*      */ 
/*      */   public Object sendPrivateData(CSTAPrivate data)
/*      */   {
/*      */     try
/*      */     {
/*  448 */       return this.provider.sendPrivateData(data);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  452 */       throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<TSConnection> getTSConnections()
/*      */   {
/*  459 */     updateObject();
/*      */ 
/*  461 */     Vector tsConnections = new Vector();
/*      */ 
/*  464 */     synchronized (this.connections)
/*      */     {
/*  471 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/*  473 */         TSConnection tsConn = (TSConnection)this.connections.elementAt(i);
/*  474 */         if ((tsConn.getTSDevice().getDeviceType() == 2) && (tsConn.getACDManagerConn() != null)) {
/*      */           continue;
/*      */         }
/*  477 */         tsConnections.addElement(tsConn);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  482 */     return tsConnections;
/*      */   }
/*      */ 
/*      */   Vector<TSConnection> getConnections()
/*      */   {
/*  490 */     return this.connections;
/*      */   }
/*      */ 
/*      */   public Vector<TSConnection> connect(TSDevice device, String destAddress, CSTAPrivate reqPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  500 */     if (this.provider.getCapabilities().getMakeCall() == 0)
/*      */     {
/*  502 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*  505 */     if (this.state != 32)
/*      */     {
/*  507 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not idle");
/*      */     }
/*      */ 
/*  515 */     String devName = device.getName();
/*      */ 
/*  520 */     if ((((this.provider.getCapabilities().getSnapshotCallReq() == 0) || (this.monitorPending))) && (this.internalDeviceMonitor == null))
/*      */     {
/*      */       try
/*      */       {
/*  525 */         this.internalDeviceMonitor = device.setInternalMonitor(this);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/*  529 */         throw tue;
/*      */       }
/*      */       catch (Exception e) {
/*  532 */         log.error(e.getMessage(), e);
/*      */       }
/*      */     }
/*      */ 
/*  536 */     MakeCallConfHandler handler = new MakeCallConfHandler(this, device, destAddress, 24);
/*      */     try
/*      */     {
/*  543 */       if (this.provider.getDeviceExt(destAddress) == 1)
/*      */       {
/*  552 */         recordVDNDomainEntry(destAddress);
/*      */ 
/*  556 */         TSDevice tsDevice = this.provider.createDevice(destAddress);
/*  557 */         tsDevice.setMonitor(false, true);
/*      */       }
/*  559 */       this.provider.tsapi.makeCall(devName, destAddress, reqPriv, handler);
/*  560 */       log.info("TSCall.connect: finished makeCall for Call ID " + this.callID);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/*  565 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidPartyException e)
/*      */     {
/*  569 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/*  573 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  577 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  581 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  585 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  589 */       if (e instanceof ITsapiException) {
/*  590 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "makeCall failure");
/*      */       }
/*  592 */       throw new TsapiPlatformException(4, 0, "makeCall failure");
/*      */     }
/*      */ 
/*  595 */     log.info("TSCall.connect: about to call connectFinish() for callID " + this.callID);
/*  596 */     if (this.handOffCall != null)
/*      */     {
/*  598 */       return this.handOffCall.connectFinish(device, destAddress, handler.newCall);
/*      */     }
/*      */ 
/*  602 */     return connectFinish(device, destAddress, handler.newCall);
/*      */   }
/*      */ 
/*      */   Vector<TSConnection> connectFinish(TSDevice device, String destAddress, CSTAConnectionID newCall)
/*      */   {
/*  610 */     if (device.isMonitorSet())
/*      */     {
/*  614 */       this.needSnapshot = false;
/*      */     }
/*      */     else
/*      */     {
/*  618 */       log.info("TSCall.connect: calling doSnapshot() for callID " + this.callID);
/*      */ 
/*  620 */       doSnapshot(newCall, null, false);
/*      */     }
/*      */ 
/*  623 */     log.info("TSCall.connect: about to wait for 2 connections for callID " + this.callID);
/*      */ 
/*  626 */     synchronized (this.connections)
/*      */     {
/*  628 */       if (this.connections.size() < 2)
/*      */       {
/*  631 */         setConnection_wait_limit(2);
/*  632 */         if ((!waitForConnections("connect", 2)) && (this.state != 34))
/*      */         {
/*  634 */           log.info("failed to get 2 connections for call ID " + this.callID);
/*      */ 
/*  636 */           throw new TsapiPlatformException(4, 0, "Could not meet post-conditions of connect()");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  641 */     return this.connections;
/*      */   }
/*      */ 
/*      */   private LucentMakePredictiveCall createLucentMakePredictiveCall(boolean priorityCall, int maxRings, short answerTreat, String destRoute, UserToUserInfo userInfo)
/*      */   {
/*  652 */     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
/*      */ 
/*  655 */     if (this.provider.isLucentV6()) {
/*  656 */       return new LucentV6MakePredictiveCall(priorityCall, maxRings, answerTreat, destRoute, asn_uui);
/*      */     }
/*  658 */     return new LucentMakePredictiveCall(priorityCall, maxRings, answerTreat, destRoute, asn_uui);
/*      */   }
/*      */ 
/*      */   public Vector<TSConnection> connectPredictive(TSDevice device, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType, String destRoute, boolean priorityCall, UserToUserInfo userInfo, CSTAPrivate reqPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  672 */     if (this.provider.getCapabilities().getMakePredictiveCall() == 0)
/*      */     {
/*  674 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*  677 */     if (this.state != 32)
/*      */     {
/*  679 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not idle");
/*      */     }
/*      */ 
/*  687 */     String devName = device.getName();
/*      */     short allocState;
/*  691 */     switch (connectionState)
/*      */     {
/*      */     case 50:
/*  694 */       allocState = 0;
/*  695 */       break;
/*      */     case 51:
/*  697 */       allocState = 1;
/*  698 */       break;
/*      */     default:
/*  700 */       throw new TsapiInvalidArgumentException(0, 0, "invalid connectionState");
/*      */     }
/*  702 */     if (this.provider.isLucent())
/*      */     {
/*      */       short answerTreat;
/*  707 */       switch (answeringTreatment)
/*      */       {
/*      */       case 1:
/*  711 */         answerTreat = 1;
/*  712 */         break;
/*      */       case 2:
/*  716 */         answerTreat = 2;
/*  717 */         break;
/*      */       case 3:
/*  721 */         answerTreat = 3;
/*  722 */         break;
/*      */       case 4:
/*  726 */         answerTreat = 0;
/*  727 */         break;
/*      */       default:
/*  730 */         throw new TsapiInvalidArgumentException(0, 0, "invalid answeringTreatment");
/*      */       }
/*  732 */       LucentMakePredictiveCall lmc = createLucentMakePredictiveCall(priorityCall, maxRings, answerTreat, destRoute, userInfo);
/*      */ 
/*  737 */       reqPriv = lmc.makeTsapiPrivate();
/*      */     }
/*      */ 
/*  740 */     MakeCallConfHandler handler = new MakeCallConfHandler(this, device, dialedDigits, 26);
/*      */     try
/*      */     {
/*  743 */       this.provider.tsapi.makePredictiveCall(devName, dialedDigits, allocState, reqPriv, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/*  747 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidPartyException e)
/*      */     {
/*  751 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/*  755 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/*  759 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/*  763 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  767 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  771 */       if (e instanceof ITsapiException) {
/*  772 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "makePredictiveCall failure");
/*      */       }
/*  774 */       throw new TsapiPlatformException(4, 0, "makePredictiveCall failure");
/*      */     }
/*      */ 
/*  777 */     boolean hasPredictiveCallMonitor = device.isPredictiveCallsViaDeviceMonitorSet();
/*      */ 
/*  779 */     if (this.handOffCall != null)
/*      */     {
/*  781 */       return this.handOffCall.connectPredictiveFinish(handler.newCall, hasPredictiveCallMonitor);
/*      */     }
/*      */ 
/*  785 */     return connectPredictiveFinish(handler.newCall, hasPredictiveCallMonitor);
/*      */   }
/*      */ 
/*      */   boolean waitForConnections(String forWhom, int requiredMinNumberOfConnections)
/*      */   {
/*  816 */     if (this.connections.size() >= requiredMinNumberOfConnections)
/*      */     {
/*  818 */       log.info("waitForConnections: called for " + forWhom + ": had right# of connections on entry, for call ID " + this.callID);
/*      */ 
/*  825 */       return true;
/*      */     }
/*      */ 
/*  828 */     if (this.state == 34)
/*      */     {
/*  831 */       log.info("waitForConnections: called for " + forWhom + ": call was INVALID on entry, for call ID " + this.callID);
/*      */ 
/*  838 */       return false;
/*      */     }
/*      */ 
/*  848 */     long startTime = System.currentTimeMillis();
/*  849 */     long currentTime = startTime;
/*      */ 
/*  851 */     long endTime = currentTime + Tsapi.getCallCompletionTimeout();
/*      */     while (true)
/*      */     {
/*      */       try
/*      */       {
/*  858 */         this.connections.wait(endTime - currentTime);
/*      */       }
/*      */       catch (InterruptedException e)
/*      */       {
/*      */       }
/*  863 */       currentTime = System.currentTimeMillis();
/*      */ 
/*  865 */       if (this.connections.size() >= requiredMinNumberOfConnections)
/*      */       {
/*  867 */         log.info("waitForConnections: waiting for " + forWhom + ": succeeded after waiting a total of " + (currentTime - startTime) / 1000L + " seconds, to get " + requiredMinNumberOfConnections + " connections for call ID " + this.callID);
/*      */ 
/*  877 */         return true;
/*      */       }
/*      */ 
/*  880 */       if (currentTime >= endTime)
/*      */       {
/*  886 */         log.info("waitForConnections: waited for " + forWhom + ": Failed.  After waiting a total of " + (currentTime - startTime) / 1000L + " seconds, did not get " + requiredMinNumberOfConnections + " connections for call ID " + this.callID);
/*      */ 
/*  896 */         return false;
/*      */       }
/*      */ 
/*  899 */       if (this.state == 34)
/*      */       {
/*  905 */         log.info("waitForConnections: waited for " + forWhom + ": Failed.  After waiting a total of " + (currentTime - startTime) / 1000L + " seconds, the call went INVALID (ended) - call ID " + this.callID);
/*      */ 
/*  913 */         return false;
/*      */       }
/*      */ 
/*  916 */       log.info("waitForConnections: waiting for " + forWhom + ": woke up after waiting a total of " + (currentTime - startTime) / 1000L + " seconds, and failed to see " + requiredMinNumberOfConnections + " connections for call ID " + this.callID + " - #connections=" + this.connections.size() + " - waiting again");
/*      */     }
/*      */   }
/*      */ 
/*      */   Vector<TSConnection> connectPredictiveFinish(CSTAConnectionID connID, boolean hasPredictiveCallMonitor)
/*      */   {
/*  934 */     if (!hasPredictiveCallMonitor)
/*      */     {
/*      */       try
/*      */       {
/*  938 */         monitorCall(connID, true);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/*  942 */         throw tue;
/*      */       }
/*      */       catch (Exception e) {
/*  945 */         log.error(e.getMessage(), e);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  956 */     synchronized (this.connections)
/*      */     {
/*  958 */       if ((this.connections.size() < 2) && 
/*  960 */         (!waitForConnections("connectPredictive", 2)) && (this.state != 34))
/*      */       {
/*  962 */         log.error("failed to get 2 connections for call ID " + this.callID);
/*      */ 
/*  964 */         throw new TsapiPlatformException(4, 0, "Could not meet post-conditions of connectPredictive()");
/*      */       }
/*      */     }
/*      */ 
/*  968 */     return this.connections;
/*      */   }
/*      */ 
/*      */   public void addCallMonitor(TsapiCallMonitor obs)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*  977 */     if (this.provider.getCapabilities().getMonitorCall() == 0)
/*      */     {
/*  979 */       throw new TsapiResourceUnavailableException(0, 0, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*  984 */     if (!this.provider.allowCallMonitoring())
/*      */     {
/*  986 */       throw new TsapiResourceUnavailableException(0, 0, 0, "no permissions");
/*      */     }
/*      */ 
/*  990 */     switch (this.state)
/*      */     {
/*      */     case 32:
/*  994 */       if (this.monitorThreads.contains(obs))
/*      */       {
/*  996 */         return;
/*      */       }
/*      */ 
/* 1000 */       synchronized (this)
/*      */       {
/* 1002 */         this.monitorPending = true;
/* 1003 */         this.callbackAndTypeVector.addElement(new CallbackAndType(obs, null));
/*      */       }
/* 1005 */       break;
/*      */     case 34:
/* 1008 */       if (this.monitorThreads.contains(obs))
/*      */       {
/* 1010 */         return;
/*      */       }
/*      */ 
/* 1014 */       obs.addReference();
/* 1015 */       obs.deleteReference(this, false, 100, null);
/* 1016 */       break;
/*      */     default:
/* 1018 */       synchronized (this.obsSync)
/*      */       {
/* 1021 */         if (this.monitorThreads.contains(obs))
/*      */         {
/* 1023 */           return;
/*      */         }
/* 1026 */         int i = 0;
/* 1027 */         synchronized (this.deviceObsVector)
/*      */         {
/* 1031 */           for (i = 0; i < this.deviceObsVector.size(); ++i)
/*      */           {
/* 1033 */             if (((DeviceObs)this.deviceObsVector.elementAt(i)).callback != obs)
/*      */               continue;
/* 1035 */             i = 1;
/*      */ 
/* 1037 */             this.monitorThreads.addElement(((DeviceObs)this.deviceObsVector.elementAt(i)).callback);
/* 1038 */             this.deviceObsVector.removeElementAt(i);
/*      */ 
/* 1041 */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1049 */         if (i != 0)
/*      */         {
/* 1051 */           setMonitor(true);
/* 1052 */           return;
/*      */         }
/*      */ 
/* 1056 */         if (this.staleObsVector.removeElement(obs))
/*      */         {
/* 1058 */           setMonitor(true);
/*      */ 
/* 1060 */           this.monitorThreads.addElement(obs);
/*      */ 
/* 1063 */           return;
/*      */         }
/*      */ 
/* 1066 */         setMonitor(true);
/*      */ 
/* 1069 */         this.monitorThreads.addElement(obs);
/*      */       }
/*      */ 
/* 1073 */       obs.addReference();
/*      */ 
/* 1075 */       sendSnapshot(obs, null, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeCallMonitor(TsapiCallMonitor obs)
/*      */   {
/* 1085 */     removeCallMonitor(obs, 100, null);
/*      */   }
/*      */ 
/*      */   protected void removeCallMonitor(TsapiCallMonitor obs, int cause, Object privateData)
/*      */   {
/* 1095 */     CallbackAndType cbAndType = null;
/* 1096 */     synchronized (this.callbackAndTypeVector)
/*      */     {
/* 1098 */       for (int i = 0; i < this.callbackAndTypeVector.size(); ++i)
/*      */       {
/* 1100 */         cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
/* 1101 */         if (cbAndType.callback != obs)
/*      */           continue;
/* 1103 */         if (cbAndType.devWithType == null)
/*      */         {
/* 1105 */           synchronized (this.monitorThreads)
/*      */           {
/* 1107 */             if (!this.monitorThreads.contains(cbAndType.callback))
/*      */             {
/* 1109 */               this.monitorThreads.addElement(cbAndType.callback);
/* 1110 */               cbAndType.callback.addReference();
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1116 */           boolean found = false;
/* 1117 */           DeviceObs devObs = null;
/* 1118 */           synchronized (this.deviceObsVector)
/*      */           {
/* 1120 */             for (int j = 0; j < this.deviceObsVector.size(); ++j)
/*      */             {
/* 1122 */               devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
/* 1123 */               if (devObs.callback != cbAndType.callback)
/*      */                 continue;
/* 1125 */               found = true;
/* 1126 */               break;
/*      */             }
/*      */ 
/* 1129 */             if (!found)
/*      */             {
/* 1131 */               devObs = new DeviceObs(cbAndType.callback);
/* 1132 */               devObs.devWithTypeVector.addElement(cbAndType.devWithType);
/* 1133 */               this.deviceObsVector.addElement(devObs);
/* 1134 */               cbAndType.callback.addReference();
/*      */             }
/*      */           }
/*      */         }
/* 1138 */         this.callbackAndTypeVector.removeElement(cbAndType);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1143 */     synchronized (this.monitorThreads)
/*      */     {
/* 1145 */       if (this.monitorThreads.removeElement(obs))
/*      */       {
/* 1147 */         obs.deleteReference(this, false, cause, privateData);
/* 1148 */         if ((this.monitorThreads.isEmpty()) && 
/* 1152 */           (this.monitorCrossRefID != 0))
/*      */         {
/* 1154 */           this.provider.deleteMonitor(this.monitorCrossRefID);
/* 1155 */           if (this.provider.getCapabilities().getMonitorStop() != 0)
/*      */           {
/*      */             try
/*      */             {
/* 1160 */               this.provider.tsapi.monitorStop(this.monitorCrossRefID, null, null);
/*      */             }
/*      */             catch (TsapiUnableToSendException tue)
/*      */             {
/* 1164 */               throw tue;
/*      */             }
/*      */             catch (Exception e) {
/* 1167 */               log.error(e.getMessage(), e);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1177 */           this.monitorCrossRefID = 0;
/*      */ 
/* 1179 */           if (!checkForMonitors())
/*      */           {
/* 1181 */             setNeedSnapshot(true);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1188 */         synchronized (this.deviceObsVector)
/*      */         {
/* 1191 */           for (int i = 0; i < this.deviceObsVector.size(); ++i)
/*      */           {
/* 1193 */             if (((DeviceObs)this.deviceObsVector.elementAt(i)).callback != obs)
/*      */               continue;
/* 1195 */             this.deviceObsVector.removeElementAt(i);
/* 1196 */             obs.deleteReference(this, false, cause, privateData);
/* 1197 */             return;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1203 */         if (this.staleObsVector.removeElement(obs))
/*      */         {
/* 1205 */           obs.deleteReference(this, false, cause, privateData);
/* 1206 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public TsapiCallCapabilities getTsapiCallCapabilities()
/*      */   {
/* 1217 */     return this.provider.getTsapiCallCapabilities();
/*      */   }
/*      */ 
/*      */   public TSDevice getCalledDevice()
/*      */   {
/* 1224 */     return this.calledDevice;
/*      */   }
/*      */ 
/*      */   public TSDevice getCallingAddress()
/*      */   {
/* 1229 */     return this.callingAddress;
/*      */   }
/*      */ 
/*      */   public TSDevice getCallingTerminal()
/*      */   {
/* 1234 */     return this.callingTerminal;
/*      */   }
/*      */ 
/*      */   public TSDevice getLastRedirectionDevice()
/*      */   {
/* 1239 */     return this.lastRedirectionDevice;
/*      */   }
/*      */ 
/*      */   public void drop(CSTAPrivate reqPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/* 1250 */     if (this.provider.getCapabilities().getClearCall() == 0)
/*      */     {
/* 1252 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/* 1254 */     if ((updateObject()) && 
/* 1256 */       (this.state != 33))
/*      */     {
/* 1258 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
/*      */     }
/*      */ 
/* 1266 */     CSTAConnectionID clearConnID = null;
/* 1267 */     synchronized (this.connections)
/*      */     {
/* 1269 */       if (this.connections.size() > 0)
/*      */       {
/* 1271 */         clearConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
/*      */       }
/*      */     }
/* 1274 */     if (clearConnID == null)
/*      */     {
/* 1276 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active (no connections)");
/*      */     }
/*      */ 
/* 1283 */     ConfHandler handler = new ClearCallConfHandler(this);
/*      */     try
/*      */     {
/* 1286 */       this.provider.tsapi.clearCall(clearConnID, reqPriv, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1290 */       setState(34, null);
/* 1291 */       endCVDObservers(100, null);
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/* 1295 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 1299 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 1303 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1307 */       if (e instanceof ITsapiException) {
/* 1308 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "clearCall failure");
/*      */       }
/* 1310 */       throw new TsapiPlatformException(4, 0, "clearCall failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void conference(TSCall otherCall, CSTAPrivate reqPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/* 1321 */     if (this.provider.getCapabilities().getConferenceCall() == 0)
/*      */     {
/* 1323 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1326 */     boolean thisCallUpdate = updateObject();
/* 1327 */     boolean otherCallUpdate = otherCall.updateObject();
/*      */ 
/* 1329 */     if ((thisCallUpdate) && (this.state != 33))
/*      */     {
/* 1331 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
/*      */     }
/*      */ 
/* 1337 */     if ((!this.confEnable) || (!otherCall.confEnable))
/*      */     {
/* 1339 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "conferencing disabled");
/*      */     }
/*      */ 
/* 1347 */     TSConnection activeTermConn = this.confController;
/* 1348 */     TSConnection heldTermConn = otherCall.confController;
/* 1349 */     if (activeTermConn == null)
/*      */     {
/* 1351 */       if (heldTermConn == null)
/*      */       {
/* 1353 */         TSConnection conn = null;
/* 1354 */         TSConnection termConn = null;
/* 1355 */         boolean found = false;
/* 1356 */         synchronized (this.connections)
/*      */         {
/* 1359 */           for (int i = 0; i < this.connections.size(); ++i)
/*      */           {
/* 1361 */             conn = (TSConnection)this.connections.elementAt(i);
/* 1362 */             Vector termConns = conn.getTermConns();
/* 1363 */             if (termConns == null)
/*      */               continue;
/* 1365 */             Vector tcs = new Vector(termConns);
/* 1366 */             for (int j = 0; j < tcs.size(); ++j)
/*      */             {
/* 1368 */               termConn = (TSConnection)tcs.elementAt(j);
/* 1369 */               if ((termConn.getCallControlTermConnState() != 98) && (termConn.getCallControlTermConnState() != 103)) {
/*      */                 continue;
/*      */               }
/* 1372 */               activeTermConn = termConn;
/*      */ 
/* 1374 */               heldTermConn = otherCall.findHeldTermConnection(activeTermConn.getTSDevice());
/* 1375 */               if (heldTermConn == null) {
/*      */                 continue;
/*      */               }
/* 1378 */               found = true;
/* 1379 */               break;
/*      */             }
/*      */ 
/* 1383 */             if (found) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1388 */         if (activeTermConn == null)
/*      */         {
/* 1390 */           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no active terminal connection found");
/*      */         }
/*      */ 
/* 1396 */         if (heldTermConn == null)
/*      */         {
/* 1398 */           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no held terminal connection found");
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1407 */         if ((otherCallUpdate) && (heldTermConn.getCallControlTermConnState() != 99) && (heldTermConn.getCallControlTermConnState() != 103))
/*      */         {
/* 1410 */           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection not held");
/*      */         }
/*      */ 
/* 1418 */         activeTermConn = findActiveTermConnection(heldTermConn.getTSDevice());
/* 1419 */         if (activeTermConn == null)
/*      */         {
/* 1421 */           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no active terminal connection found");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/* 1429 */     else if (heldTermConn == null)
/*      */     {
/* 1431 */       if ((thisCallUpdate) && (activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
/*      */       {
/* 1434 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "Terminal connection not active. It's state is " + activeTermConn.getCallControlTermConnState());
/*      */       }
/*      */ 
/* 1442 */       heldTermConn = otherCall.findHeldTermConnection(activeTermConn.getTSDevice());
/* 1443 */       if (heldTermConn == null)
/*      */       {
/* 1445 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no held terminal connection found");
/*      */       }
/*      */ 
/*      */     }
/* 1452 */     else if ((thisCallUpdate) && (otherCallUpdate))
/*      */     {
/* 1454 */       if ((activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
/*      */       {
/* 1457 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the state of the active terminal connection is not TALKING or UNKNOWN; its state is " + activeTermConn.getCallControlTermConnState());
/*      */       }
/*      */ 
/* 1464 */       if ((heldTermConn.getCallControlTermConnState() != 99) && (heldTermConn.getCallControlTermConnState() != 103))
/*      */       {
/* 1467 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the state of the held terminal connection is not HELD or UNKNOWN; its state is " + heldTermConn.getCallControlTermConnState());
/*      */       }
/*      */ 
/* 1474 */       if (!activeTermConn.getTSDevice().getTermConns().contains(heldTermConn))
/*      */       {
/* 1476 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the held terminal connection is not associated with the device (" + activeTermConn.getTSDevice() + ") of the active terminal connection");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1486 */     ConfHandler handler = new ConfXferConfHandler(this, otherCall, 12);
/*      */     try
/*      */     {
/* 1489 */       this.provider.tsapi.conferenceCall(heldTermConn.getConnID(), activeTermConn.getConnID(), reqPriv, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1494 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/* 1498 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/* 1502 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 1506 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 1510 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1514 */       if (e instanceof ITsapiException) {
/* 1515 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "conferenceCall failure");
/*      */       }
/* 1517 */       throw new TsapiPlatformException(4, 0, "conferenceCall failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   private CSTAConnectionID selectConnectionIdForAddParty()
/*      */   {
/* 1537 */     synchronized (this.connections)
/*      */     {
/* 1541 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/* 1543 */         CSTAConnectionID connID = ((TSConnection)this.connections.elementAt(i)).getConnID();
/*      */ 
/* 1545 */         if (connID == null)
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/* 1550 */         if (connID.getDevIDType() == 0)
/*      */         {
/* 1554 */           return connID;
/*      */         }
/*      */ 
/* 1557 */         if (connID.getDevIDType() != 1)
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/* 1563 */         String deviceID = connID.getDeviceID();
/* 1564 */         if (deviceID.regionMatches(true, 0, "T", 0, 1))
/*      */         {
/* 1568 */           return connID;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1576 */       if (this.connections.size() > 0)
/*      */       {
/* 1578 */         return ((TSConnection)this.connections.elementAt(0)).getConnID();
/*      */       }
/*      */ 
/* 1581 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public TSConnection addParty(String newParty, boolean active)
/*      */     throws TsapiInvalidStateException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
/*      */   {
/* 1608 */     if (!this.provider.isLucentV5()) {
/* 1609 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1612 */     if ((updateObject()) && 
/* 1614 */       (this.state != 33))
/*      */     {
/* 1616 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
/*      */     }
/*      */ 
/* 1625 */     CSTAConnectionID connID = selectConnectionIdForAddParty();
/*      */ 
/* 1627 */     if (connID == null)
/*      */     {
/* 1629 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active (no connections)");
/*      */     }
/*      */ 
/* 1636 */     LucentSingleStepConferenceCall ssc = new LucentSingleStepConferenceCall(connID, newParty, (short) ((active) ? 1 : 0), false);
/*      */ 
/* 1640 */     ConfHandler handler = new ConfXferConfHandler(this, null, 90);
/*      */     try
/*      */     {
/* 1643 */       this.provider.sendPrivateData(ssc.makeTsapiPrivate(), handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1647 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidPartyException e)
/*      */     {
/* 1651 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/* 1655 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 1659 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1663 */       if (e instanceof ITsapiException)
/*      */       {
/* 1665 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "addParty failure, exception: " + e);
/*      */       }
/*      */ 
/* 1669 */       throw new TsapiPlatformException(3, 0, "addParty failure, exception: " + e);
/*      */     }
/*      */ 
/* 1672 */     return ((ConfXferConfHandler)handler).newConn;
/*      */   }
/*      */ 
/*      */   public void transfer(TSCall otherCall, CSTAPrivate reqPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/* 1682 */     if (this.provider.getCapabilities().getTransferCall() == 0)
/*      */     {
/* 1684 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1687 */     boolean thisCallUpdate = updateObject();
/* 1688 */     boolean otherCallUpdate = otherCall.updateObject();
/*      */ 
/* 1690 */     if ((thisCallUpdate) && (this.state != 33))
/*      */     {
/* 1692 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not active");
/*      */     }
/*      */ 
/* 1698 */     if ((!this.xferEnable) || (!otherCall.xferEnable))
/*      */     {
/* 1700 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "transfer disabled");
/*      */     }
/*      */ 
/* 1708 */     TSConnection activeTermConn = this.xferController;
/* 1709 */     TSConnection heldTermConn = otherCall.xferController;
/* 1710 */     if (activeTermConn == null)
/*      */     {
/* 1712 */       if (heldTermConn == null)
/*      */       {
/* 1714 */         TSConnection conn = null;
/* 1715 */         TSConnection termConn = null;
/* 1716 */         boolean found = false;
/* 1717 */         synchronized (this.connections)
/*      */         {
/* 1720 */           for (int i = 0; i < this.connections.size(); ++i)
/*      */           {
/* 1722 */             conn = (TSConnection)this.connections.elementAt(i);
/* 1723 */             Vector termConns = conn.getTermConns();
/* 1724 */             if (termConns == null)
/*      */               continue;
/* 1726 */             Vector tcs = new Vector(termConns);
/* 1727 */             for (int j = 0; j < tcs.size(); ++j)
/*      */             {
/* 1729 */               termConn = (TSConnection)tcs.elementAt(j);
/* 1730 */               if ((termConn.getCallControlTermConnState() != 98) && (termConn.getCallControlTermConnState() != 103)) {
/*      */                 continue;
/*      */               }
/* 1733 */               activeTermConn = termConn;
/*      */ 
/* 1735 */               heldTermConn = otherCall.findHeldTermConnection(activeTermConn.getTSDevice());
/* 1736 */               if (heldTermConn == null) {
/*      */                 continue;
/*      */               }
/* 1739 */               found = true;
/* 1740 */               break;
/*      */             }
/*      */ 
/* 1744 */             if (found) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1749 */         if (activeTermConn == null)
/*      */         {
/* 1751 */           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no active terminal connection found");
/*      */         }
/*      */ 
/* 1757 */         if (heldTermConn == null)
/*      */         {
/* 1759 */           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no held terminal connection found");
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1768 */         if ((otherCallUpdate) && (heldTermConn.getCallControlTermConnState() != 99) && (heldTermConn.getCallControlTermConnState() != 103))
/*      */         {
/* 1771 */           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection not held");
/*      */         }
/*      */ 
/* 1779 */         activeTermConn = findActiveTermConnection(heldTermConn.getTSDevice());
/* 1780 */         if (activeTermConn == null)
/*      */         {
/* 1782 */           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no active terminal connection found");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/* 1790 */     else if (heldTermConn == null)
/*      */     {
/* 1792 */       if ((thisCallUpdate) && (activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
/*      */       {
/* 1795 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection not active. It's state is " + activeTermConn.getCallControlTermConnState());
/*      */       }
/*      */ 
/* 1803 */       heldTermConn = otherCall.findHeldTermConnection(activeTermConn.getTSDevice());
/* 1804 */       if (heldTermConn == null)
/*      */       {
/* 1806 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no held terminal connection found");
/*      */       }
/*      */ 
/*      */     }
/* 1813 */     else if ((thisCallUpdate) && (otherCallUpdate))
/*      */     {
/* 1815 */       if ((activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
/*      */       {
/* 1818 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the state of the active terminal connection is not TALKING or UNKNOWN; its state is " + activeTermConn.getCallControlTermConnState());
/*      */       }
/*      */ 
/* 1825 */       if ((heldTermConn.getCallControlTermConnState() != 99) && (heldTermConn.getCallControlTermConnState() != 103))
/*      */       {
/* 1828 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the state of the held terminal connection is not HELD or UNKNOWN; its state is " + heldTermConn.getCallControlTermConnState());
/*      */       }
/*      */ 
/* 1835 */       if (!activeTermConn.getTSDevice().getTermConns().contains(heldTermConn))
/*      */       {
/* 1837 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the held terminal connection is not associated with the device (" + activeTermConn.getTSDevice() + ") of the active terminal connection");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1847 */     ConfHandler handler = new ConfXferConfHandler(this, otherCall, 52);
/*      */     try
/*      */     {
/* 1850 */       this.provider.tsapi.transferCall(heldTermConn.getConnID(), activeTermConn.getConnID(), reqPriv, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1855 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidPartyException e)
/*      */     {
/* 1859 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/* 1863 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/* 1867 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 1871 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 1875 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1879 */       if (e instanceof ITsapiException) {
/* 1880 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "transferCall failure");
/*      */       }
/* 1882 */       throw new TsapiPlatformException(4, 0, "transferCall failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public TSConnection transfer(String xferDestAddress, CSTAPrivate reqPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/* 1893 */     if (this.provider.getCapabilities().getTransferCall() == 0)
/*      */     {
/* 1895 */       throw new TsapiMethodNotSupportedException(4, 0, "transfer unsupported by driver");
/*      */     }
/*      */ 
/* 1899 */     if (!this.provider.getTsapiCallCapabilities().canTransfer((String)null))
/*      */     {
/* 1901 */       throw new TsapiMethodNotSupportedException(4, 0, "transfer(String) unsupported by driver");
/*      */     }
/*      */ 
/* 1905 */     boolean thisCallUpdate = updateObject();
/*      */ 
/* 1908 */     if ((thisCallUpdate) && (this.state != 33))
/*      */     {
/* 1910 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not active");
/*      */     }
/*      */ 
/* 1916 */     if (!this.xferEnable)
/*      */     {
/* 1918 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "transfer disabled");
/*      */     }
/*      */ 
/* 1942 */     TSConnection activeTermConn = this.xferController;
/*      */ 
/* 1945 */     if (activeTermConn == null)
/*      */     {
/* 1947 */       throw new TsapiInvalidArgumentException(3, 0, "transfer(String) with null TransferController not supported");
/*      */     }
/*      */ 
/* 1954 */     if ((thisCallUpdate) && (activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
/*      */     {
/* 1957 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 2, this.state, "transfer controller terminal connection not in valid state (TALKING or UNKNOWN)");
/*      */     }
/*      */ 
/* 1973 */     setConnection_wait_limit(1);
/*      */ 
/* 1978 */     LucentSingleStepTransferCall sst = new LucentSingleStepTransferCall(activeTermConn.getConnID(), xferDestAddress);
/*      */ 
/* 1980 */     ConfXferConfHandler handler = new ConfXferConfHandler(this, null, 90);
/*      */     try
/*      */     {
/* 1983 */       this.provider.sendPrivateData(sst.makeTsapiPrivate(), handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1987 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidPartyException e)
/*      */     {
/* 1991 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/* 1995 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/* 1999 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 2003 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 2007 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2011 */       if (e instanceof ITsapiException) {
/* 2012 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "transfer(String) failure");
/*      */       }
/* 2014 */       throw new TsapiPlatformException(4, 0, "transfer(String) failure: " + e);
/*      */     }
/*      */ 
/* 2033 */     return this.provider.getConnection(handler.newCall);
/*      */   }
/*      */ 
/*      */   public Vector<TSConnection> consult(TSConnection termconn, String address, CSTAPrivate reqPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/* 2045 */     if (this.provider.getCapabilities().getConsultationCall() == 0)
/*      */     {
/* 2047 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/* 2049 */     if (this.state != 32)
/*      */     {
/* 2051 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not idle");
/*      */     }
/*      */ 
/* 2058 */     boolean otherCallUpdate = termconn.getTSCall().updateObject();
/* 2059 */     if ((otherCallUpdate) && (termconn.getTSCall().state != 33))
/*      */     {
/* 2061 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(termconn.getTSCall(), false), 1, this.state, "other call not active");
/*      */     }
/*      */ 
/* 2067 */     if ((otherCallUpdate) && (termconn.getCallControlTermConnState() != 98) && (termconn.getCallControlTermConnState() != 103))
/*      */     {
/* 2070 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(termconn, false), 5, this.state, "terminal connection not talking");
/*      */     }
/*      */ 
/* 2080 */     if ((((this.provider.getCapabilities().getSnapshotCallReq() == 0) || (this.monitorPending))) && (this.internalDeviceMonitor == null))
/*      */     {
/*      */       try
/*      */       {
/* 2085 */         this.internalDeviceMonitor = termconn.getTSDevice().setInternalMonitor(this);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/* 2089 */         throw tue;
/*      */       }
/*      */       catch (Exception e) {
/* 2092 */         log.error(e.getMessage(), e);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2097 */     MakeCallConfHandler handler = new MakeCallConfHandler(this, termconn.getTSDevice(), address, 14);
/*      */     try
/*      */     {
/* 2100 */       this.provider.tsapi.consultationCall(termconn.getConnID(), address, reqPriv, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 2104 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/* 2108 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/* 2112 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 2116 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 2120 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2124 */       if (e instanceof ITsapiException) {
/* 2125 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "consultationCall failure");
/*      */       }
/* 2127 */       throw new TsapiPlatformException(4, 0, "consultationCall failure");
/*      */     }
/*      */ 
/* 2130 */     if (this.handOffCall != null)
/*      */     {
/* 2132 */       return this.handOffCall.consultFinish(termconn, address, handler.newCall);
/*      */     }
/*      */ 
/* 2136 */     return consultFinish(termconn, address, handler.newCall);
/*      */   }
/*      */ 
/*      */   Vector<TSConnection> consultFinish(TSConnection termconn, String address, CSTAConnectionID newCall)
/*      */   {
/* 2145 */     Vector eventList = new Vector();
/* 2146 */     termconn.setTermConnState(99, eventList);
/* 2147 */     if (eventList.size() > 0)
/*      */     {
/* 2149 */       Vector observers = termconn.getTSCall().getObservers();
/* 2150 */       for (int j = 0; j < observers.size(); ++j)
/*      */       {
/* 2152 */         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 2153 */         callback.deliverEvents(eventList, 100, false);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2158 */     if (termconn.getTSDevice().isMonitorSet())
/*      */     {
/* 2161 */       this.needSnapshot = false;
/*      */     }
/*      */     else
/*      */     {
/* 2167 */       doSnapshot(newCall, null, false);
/*      */     }
/*      */ 
/* 2171 */     synchronized (this.connections)
/*      */     {
/* 2173 */       if (this.connections.size() < 2)
/*      */       {
/*      */         try
/*      */         {
/* 2178 */           this.connections.wait(Tsapi.getCallCompletionTimeout());
/*      */         } catch (InterruptedException e) {
/*      */         }
/* 2181 */         if ((this.connections.size() < 2) && (this.state != 34))
/*      */         {
/* 2183 */           log.error("failed to get 2 connections for call ID " + this.callID);
/*      */ 
/* 2185 */           throw new TsapiPlatformException(4, 0, "Could not meet post-conditions of consult()");
/*      */         }
/*      */       }
/*      */     }
/* 2189 */     return this.connections;
/*      */   }
/*      */ 
/*      */   public void setConfController(TSConnection termconn)
/*      */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*      */   {
/* 2198 */     if (updateObject())
/*      */     {
/* 2200 */       if (this.state != 33)
/*      */       {
/* 2202 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
/*      */       }
/*      */ 
/* 2208 */       if (termconn.getCallControlTermConnState() == 102)
/*      */       {
/* 2210 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(termconn, false), 5, this.state, "terminal connection is dropped");
/*      */       }
/*      */ 
/* 2217 */       boolean contains = false;
/*      */ 
/* 2219 */       synchronized (this.connections)
/*      */       {
/* 2221 */         for (int i = 0; i < this.connections.size(); ++i)
/*      */         {
/* 2223 */           TSConnection conn = (TSConnection)this.connections.elementAt(i);
/* 2224 */           Vector termConns = conn.getTermConns();
/* 2225 */           if ((termConns == null) || (!termConns.contains(termconn)))
/*      */             continue;
/* 2227 */           contains = true;
/* 2228 */           break;
/*      */         }
/*      */       }
/*      */ 
/* 2232 */       if (!contains)
/*      */       {
/* 2234 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection is not in this call");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2242 */     this.confController = termconn;
/*      */   }
/*      */ 
/*      */   public TSConnection getConfController()
/*      */   {
/* 2248 */     return this.confController;
/*      */   }
/*      */ 
/*      */   public void setXferController(TSConnection termconn)
/*      */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*      */   {
/* 2257 */     if (updateObject())
/*      */     {
/* 2259 */       if (this.state != 33)
/*      */       {
/* 2261 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
/*      */       }
/*      */ 
/* 2267 */       if (termconn.getCallControlTermConnState() == 102)
/*      */       {
/* 2269 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(termconn, false), 5, 102, "terminal connection is dropped");
/*      */       }
/*      */ 
/* 2277 */       boolean contains = false;
/*      */ 
/* 2279 */       synchronized (this.connections)
/*      */       {
/* 2281 */         for (int i = 0; i < this.connections.size(); ++i)
/*      */         {
/* 2283 */           TSConnection conn = (TSConnection)this.connections.elementAt(i);
/* 2284 */           Vector termConns = conn.getTermConns();
/* 2285 */           if ((termConns == null) || (!termConns.contains(termconn)))
/*      */             continue;
/* 2287 */           contains = true;
/* 2288 */           break;
/*      */         }
/*      */       }
/*      */ 
/* 2292 */       if (!contains)
/*      */       {
/* 2294 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection is not in this call");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2302 */     this.xferController = termconn;
/*      */   }
/*      */ 
/*      */   public TSConnection getXferController()
/*      */   {
/* 2308 */     return this.xferController;
/*      */   }
/*      */ 
/*      */   public void setConfEnable(boolean enable)
/*      */   {
/* 2314 */     this.confEnable = enable;
/*      */   }
/*      */ 
/*      */   public boolean getConfEnable()
/*      */   {
/* 2320 */     return this.confEnable;
/*      */   }
/*      */ 
/*      */   public void setXferEnable(boolean enable)
/*      */   {
/* 2326 */     this.xferEnable = enable;
/*      */   }
/*      */ 
/*      */   public boolean getXferEnable()
/*      */   {
/* 2332 */     return this.xferEnable;
/*      */   }
/*      */ 
/*      */   public Vector<TSTrunk> getTSTrunks()
/*      */   {
/* 2338 */     return this.trkVector;
/*      */   }
/*      */ 
/*      */   public UserToUserInfo getUUI()
/*      */   {
/* 2344 */     return this.uui;
/*      */   }
/*      */ 
/*      */   public LookaheadInfo getLAI()
/*      */   {
/* 2350 */     return this.lai;
/*      */   }
/*      */ 
/*      */   public UserEnteredCode getUEC()
/*      */   {
/* 2356 */     return this.uec;
/*      */   }
/*      */ 
/*      */   public OriginalCallInfo getOCI()
/*      */   {
/* 2362 */     return this.oci;
/*      */   }
/*      */ 
/*      */   public short getReason()
/*      */   {
/* 2368 */     return this.reason;
/*      */   }
/*      */ 
/*      */   public String getUCID()
/*      */   {
/* 2374 */     if (this.ucid == null)
/*      */     {
/* 2376 */       setUCID(queryUCID());
/*      */     }
/*      */ 
/* 2379 */     return this.ucid;
/*      */   }
/*      */ 
/*      */   public LucentQueryUcidConfEvent getQueryUCIDConf()
/*      */   {
/* 2392 */     Object lquConf = null;
/*      */     try
/*      */     {
/* 2396 */       LucentQueryUcid lqu = new LucentQueryUcid(new CSTAConnectionID(this.callID, "", (short) 0));
/* 2397 */       lquConf = this.provider.sendPrivateData(lqu.makeTsapiPrivate());
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 2401 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2405 */       if (e instanceof ITsapiException)
/*      */       {
/* 2407 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "getUCIDConf failure, exception: " + e);
/*      */       }
/*      */ 
/* 2411 */       throw new TsapiPlatformException(4, 0, "getUCIDConf failure, exception: " + e);
/*      */     }
/*      */ 
/* 2415 */     return (LucentQueryUcidConfEvent)lquConf;
/*      */   }
/*      */ 
/*      */   String queryUCID()
/*      */   {
/* 2421 */     if (!this.provider.isLucentV5()) {
/* 2422 */       return null;
/*      */     }
/* 2424 */     LucentQueryUcidConfEvent lquConf = getQueryUCIDConf();
/*      */ 
/* 2428 */     if (lquConf != null) {
/* 2429 */       return lquConf.getUcid();
/*      */     }
/* 2431 */     return null;
/*      */   }
/*      */ 
/*      */   public int getCallOriginatorType()
/*      */   {
/* 2437 */     if (hasCallOriginatorType()) {
/* 2438 */       return this.callOriginatorInfo.getCallOriginatorType();
/*      */     }
/* 2440 */     return -1;
/*      */   }
/*      */ 
/*      */   public boolean hasCallOriginatorType()
/*      */   {
/* 2446 */     return this.callOriginatorInfo != null;
/*      */   }
/*      */ 
/*      */   public boolean canSetBillRate()
/*      */   {
/* 2452 */     return this.flexibleBilling;
/*      */   }
/*      */ 
/*      */   public void setBillRate(short billType, float billRate)
/*      */     throws TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException
/*      */   {
/* 2458 */     if (!this.provider.isLucentV5())
/*      */     {
/* 2460 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/* 2462 */     switch (billType)
/*      */     {
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 24:
/* 2469 */       break;
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     default:
/* 2471 */       throw new TsapiInvalidArgumentException(0, 0, "invalid billType");
/*      */     }
/*      */     try
/*      */     {
/* 2475 */       CSTAConnectionID connID = new CSTAConnectionID(this.callID, "", (short) 0);
/*      */ 
/* 2477 */       LucentSetBillRate sbr = new LucentSetBillRate(connID, billType, billRate);
/* 2478 */       this.provider.sendPrivateData(sbr.makeTsapiPrivate());
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 2482 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 2486 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2490 */       if (e instanceof ITsapiException)
/*      */       {
/* 2492 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setBillRate failure, exception: " + e);
/*      */       }
/*      */ 
/* 2496 */       throw new TsapiPlatformException(4, 0, "setBillRate failure, exception: " + e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public TSDevice getDistributingDevice()
/*      */   {
/* 2504 */     return this.distributingDevice;
/*      */   }
/*      */ 
/*      */   public TSDevice getDistributingVDN()
/*      */   {
/* 2510 */     return this.distributingVDN;
/*      */   }
/*      */ 
/*      */   public TSDevice getDeliveringACDDevice()
/*      */   {
/* 2516 */     return this.deliveringACDDevice;
/*      */   }
/*      */ 
/*      */   void setDistributingDevice(TSDevice _distributingDevice)
/*      */   {
/* 2524 */     if (_distributingDevice == null)
/*      */       return;
/* 2526 */     this.distributingDevice = _distributingDevice;
/*      */   }
/*      */ 
/*      */   void setDistributingVDN(TSDevice _distributingVDN)
/*      */   {
/* 2533 */     if (_distributingVDN == null)
/*      */       return;
/* 2535 */     this.distributingVDN = _distributingVDN;
/*      */   }
/*      */ 
/*      */   void setDeliveringACDDevice(TSDevice _deliveringACDDevice)
/*      */   {
/* 2542 */     if (_deliveringACDDevice == null)
/*      */       return;
/* 2544 */     this.deliveringACDDevice = _deliveringACDDevice;
/*      */   }
/*      */ 
/*      */   void setCalledDevice(TSDevice _calledDevice)
/*      */   {
/* 2551 */     if (_calledDevice == null)
/*      */       return;
/* 2553 */     this.calledDevice = _calledDevice;
/*      */   }
/*      */ 
/*      */   void setCallingDevices(TSDevice _callingDevice)
/*      */   {
/* 2559 */     if (_callingDevice == null)
/*      */       return;
/* 2561 */     if (_callingDevice.isTerminal())
/*      */     {
/* 2563 */       this.callingTerminal = _callingDevice;
/*      */     }
/*      */     else
/*      */     {
/* 2567 */       this.callingTerminal = null;
/*      */     }
/* 2569 */     this.callingAddress = _callingDevice;
/*      */   }
/*      */ 
/*      */   void setLastRedirectionDevice(TSDevice _lastRedirectionDevice)
/*      */   {
/* 2575 */     if (_lastRedirectionDevice == null)
/*      */       return;
/* 2577 */     this.lastRedirectionDevice = _lastRedirectionDevice;
/*      */   }
/*      */ 
/*      */   void setUUI(LucentUserToUserInfo _uui)
/*      */   {
/* 2589 */     if (_uui == null)
/*      */       return;
/* 2591 */     this.uui = TsapiPromoter.promoteUserToUserInfo(_uui);
/*      */   }
/*      */ 
/*      */   void setLAI(LucentLookaheadInfo _lai)
/*      */   {
/* 2602 */     if (_lai == null)
/*      */       return;
/* 2604 */     this.lai = TsapiPromoter.promoteLookaheadInfo(_lai);
/*      */   }
/*      */ 
/*      */   void setUEC(LucentUserEnteredCode _uec)
/*      */   {
/* 2615 */     if (_uec == null)
/*      */       return;
/* 2617 */     this.uec = TsapiPromoter.promoteUserEnteredCode(this.provider, _uec);
/*      */   }
/*      */ 
/*      */   void setOCI(LucentOriginalCallInfo _oci)
/*      */   {
/* 2628 */     if (_oci == null)
/*      */       return;
/* 2630 */     this.oci = TsapiPromoter.promoteOriginalCallInfo(this.provider, _oci);
/*      */   }
/*      */ 
/*      */   void setUUI(UserToUserInfo _uui)
/*      */   {
/* 2640 */     if (_uui == null)
/*      */       return;
/* 2642 */     this.uui = _uui;
/*      */   }
/*      */ 
/*      */   void setLAI(LookaheadInfo _lai)
/*      */   {
/* 2648 */     if (_lai == null)
/*      */       return;
/* 2650 */     this.lai = _lai;
/*      */   }
/*      */ 
/*      */   void setUEC(UserEnteredCode _uec)
/*      */   {
/* 2656 */     if (_uec == null)
/*      */       return;
/* 2658 */     this.uec = _uec;
/*      */   }
/*      */ 
/*      */   void setOCI(OriginalCallInfo _oci)
/*      */   {
/* 2664 */     if (_oci == null)
/*      */       return;
/* 2666 */     this.oci = _oci;
/*      */   }
/*      */ 
/*      */   void setReason(short _reason)
/*      */   {
/* 2672 */     this.reason = _reason;
/*      */   }
/*      */ 
/*      */   void setUCID(String _ucid)
/*      */   {
/* 2677 */     if (_ucid == null)
/*      */       return;
/* 2679 */     this.ucid = _ucid;
/*      */   }
/*      */ 
/*      */   void setCallOriginatorInfo(CSTACallOriginatorInfo _callOriginatorInfo)
/*      */   {
/* 2685 */     this.callOriginatorInfo = _callOriginatorInfo;
/*      */   }
/*      */ 
/*      */   void setFlexibleBilling(boolean _flexibleBilling)
/*      */   {
/* 2690 */     this.flexibleBilling = _flexibleBilling;
/*      */   }
/*      */ 
/*      */   public void addTrunk(TSTrunk trunk, Vector<TSEvent> eventList)
/*      */   {
/* 2699 */     synchronized (this.trkVector)
/*      */     {
/* 2701 */       if (this.trkVector.contains(trunk))
/*      */       {
/* 2703 */         return;
/*      */       }
/*      */ 
/* 2706 */       if (trunk.setCall(this, eventList))
/* 2707 */         this.trkVector.addElement(trunk);
/*      */     }
/*      */   }
/*      */ 
/*      */   void removeTrunk(TSTrunk trunk, Vector<TSEvent> eventList)
/*      */   {
/* 2716 */     if (!this.trkVector.removeElement(trunk))
/*      */       return;
/* 2718 */     trunk.unsetCall(eventList);
/*      */   }
/*      */ 
/*      */   boolean cleanUCIDInCall()
/*      */   {
/* 2734 */     CallUCIDStatus callsttype = CallUCIDStatus.OK;
/*      */     try
/*      */     {
/* 2744 */       Object ucidConf = getQueryUCIDConf();
/* 2745 */       log.debug("UCID obtained from the switch is " + ((HasUCID)ucidConf).getUcid());
/*      */ 
/* 2747 */       if ((getUCID() != null) && (!getUCID().contentEquals(((HasUCID)ucidConf).getUcid())))
/*      */       {
/* 2750 */         callsttype = CallUCIDStatus.UCIDMISMATCH;
/* 2751 */         log.info("ERROR: mismatched ucid, for call: " + this + " - setting call state to INVALID.");
/*      */       }
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*      */       int i;
/* 2762 */       if ((e.getErrorType() == 2) && (e.getErrorCode() == 24))
/*      */       {
/* 2768 */         callsttype = CallUCIDStatus.NONEXISTING;
/* 2769 */         log.info("ERROR: Attempted cleanUCIDInCall() but no active call: " + this + " - setting call state to INVALID.");
/*      */       }
/*      */       else
/*      */       {
/* 2779 */         callsttype = CallUCIDStatus.OK;
/* 2780 */         log.info("ERROR: Saw & ignored exception (TsapiPlatformException)  for cleanUCIDsInCallsInConnections(), for call " + this + " - Perhaps UCID queries are disabled on the switch. " + e);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       int i;
/* 2793 */       callsttype = CallUCIDStatus.OK;
/* 2794 */       log.info("ERROR: Saw & ignored unexpected exception  for cleanUCIDsInCallsInConnections(), for call " + this + " - Perhaps UCID queries are disabled on the switch. " + e);
/*      */     }
/*      */     finally
/*      */     {
/*      */       int i;
/* 2805 */       if (callsttype != CallUCIDStatus.OK)
/*      */       {
/* 2807 */         i = getCallID();
/* 2808 */         setState(34, null);
/* 2809 */         this.provider.dumpCall(i);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2814 */     return callsttype == CallUCIDStatus.OK;
/*      */   }
/*      */ 
/*      */   boolean updateObject()
/*      */   {
/* 2830 */     if ((isMonitorSet() == true) || ((this.state == 32) && (this.callID == 0)) || (this.state == 34))
/*      */     {
/* 2835 */       return true;
/*      */     }
/*      */ 
/* 2844 */     CSTAConnectionID snapConnID = null;
/* 2845 */     synchronized (this.connections)
/*      */     {
/* 2847 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/* 2849 */         TSConnection conn = (TSConnection)this.connections.elementAt(i);
/* 2850 */         if (conn.getTSDevice().isMonitorSet())
/*      */         {
/* 2852 */           return true;
/*      */         }
/* 2854 */         Vector termConns = conn.getTermConns();
/* 2855 */         if (termConns == null)
/*      */           continue;
/* 2857 */         synchronized (termConns)
/*      */         {
/* 2859 */           for (int j = 0; j < termConns.size(); ++j)
/*      */           {
/* 2861 */             TSConnection termconn = (TSConnection)termConns.elementAt(j);
/* 2862 */             if (!termconn.getTSDevice().isMonitorSet())
/*      */               continue;
/* 2864 */             return true;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2871 */       if (this.connections.size() > 0)
/*      */       {
/*      */         try
/*      */         {
/* 2877 */           snapConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
/*      */         }
/*      */         catch (TsapiPlatformException e)
/*      */         {
/* 2881 */           log.error("Ignoring exception: " + e);
/* 2882 */           if (this.callID != 0)
/*      */           {
/* 2884 */             snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2889 */     if (snapConnID != null)
/*      */     {
/* 2891 */       return doSnapshot(snapConnID, null, true);
/*      */     }
/* 2893 */     if (this.callID != 0)
/*      */     {
/* 2895 */       snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
/* 2896 */       return doSnapshot(snapConnID, null, true);
/*      */     }
/*      */ 
/* 2899 */     return false;
/*      */   }
/*      */ 
/*      */   boolean updateSuspiciousObject()
/*      */   {
/* 2925 */     if (this.callID != 0)
/*      */     {
/* 2927 */       log.info("Mark " + this + " (callID " + this.callID + ") for immediate and future snapshots for " + this.provider);
/*      */ 
/* 2931 */       this.needSnapshot = true;
/*      */ 
/* 2934 */       CSTAConnectionID snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
/* 2935 */       return doSnapshot(snapConnID, null, true);
/*      */     }
/*      */ 
/* 2939 */     return false;
/*      */   }
/*      */ 
/*      */   void addConnection(TSConnection tsConn, Vector<TSEvent> eventList)
/*      */   {
/* 2950 */     synchronized (this.connections)
/*      */     {
/* 2952 */       if (this.connections.contains(tsConn))
/*      */       {
/* 2954 */         return;
/*      */       }
/*      */ 
/* 2957 */       this.connections.addElement(tsConn);
/*      */ 
/* 2960 */       if (this.connections.size() > 0)
/*      */       {
/* 2962 */         setState(33, eventList);
/*      */       }
/*      */ 
/* 2966 */       if (this.connections.size() >= getConnection_wait_limit())
/*      */       {
/* 2968 */         this.connections.notify();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void removeConnection(TSConnection tsConn, Vector<TSEvent> eventList)
/*      */   {
/* 2987 */     if (!this.connections.removeElement(tsConn))
/*      */     {
/* 2989 */       return;
/*      */     }
/*      */ 
/* 2992 */     if (this.confController == tsConn)
/*      */     {
/* 2994 */       this.confController = null;
/*      */     }
/* 2996 */     if (this.xferController == tsConn)
/*      */     {
/* 2998 */       this.xferController = null;
/*      */     }
/*      */ 
/* 3001 */     synchronized (this.staleConnections)
/*      */     {
/* 3003 */       if (!this.staleConnections.contains(tsConn))
/*      */       {
/* 3005 */         this.staleConnections.addElement(tsConn);
/*      */       }
/*      */     }
/* 3008 */     if (checkForMonitors())
/*      */       return;
/* 3010 */     setNeedSnapshot(true);
/*      */   }
/*      */ 
/*      */   public boolean checkForMonitors()
/*      */   {
/* 3021 */     if (isMonitorSet() == true)
/*      */     {
/* 3023 */       return true;
/*      */     }
/*      */ 
/* 3031 */     synchronized (this.connections)
/*      */     {
/* 3033 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/* 3035 */         TSConnection conn = (TSConnection)this.connections.elementAt(i);
/* 3036 */         if (conn.getTSDevice().isMonitorSet())
/*      */         {
/* 3038 */           return true;
/*      */         }
/* 3040 */         Vector termConns = conn.getTermConns();
/* 3041 */         if (termConns == null)
/*      */           continue;
/* 3043 */         synchronized (termConns)
/*      */         {
/* 3045 */           for (int j = 0; j < termConns.size(); ++j)
/*      */           {
/* 3047 */             TSConnection termconn = (TSConnection)termConns.elementAt(j);
/* 3048 */             if (!termconn.getTSDevice().isMonitorSet())
/*      */               continue;
/* 3050 */             return true;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3059 */     return false;
/*      */   }
/*      */ 
/*      */   void endCVDObservers(int cause, Object privateData)
/*      */   {
/* 3067 */     for (int i = 0; i < this.staleConnections.size(); ++i)
/*      */     {
/* 3069 */       TSDevice dev = ((TSConnection)this.staleConnections.elementAt(i)).getTSDevice();
/* 3070 */       if (dev == null)
/*      */         continue;
/* 3072 */       Vector cvd = dev.getCVDObservers();
/* 3073 */       for (int j = 0; j < cvd.size(); ++j)
/*      */       {
/* 3075 */         TsapiCallMonitor obs = (TsapiCallMonitor)cvd.elementAt(j);
/* 3076 */         removeCallMonitor(obs, cause, privateData);
/*      */       }
/*      */ 
/* 3079 */       dev.testDelete();
/*      */     }
/*      */   }
/*      */ 
/*      */   void removeObservers(int cause, Object privateData, int xrefID)
/*      */   {
/* 3089 */     if ((xrefID != 0) && (this.monitorCrossRefID == xrefID))
/*      */     {
/* 3091 */       this.provider.deleteMonitor(this.monitorCrossRefID);
/* 3092 */       this.monitorCrossRefID = 0;
/*      */     }
/*      */ 
/* 3097 */     CallbackAndType cbAndType = null;
/* 3098 */     synchronized (this.callbackAndTypeVector)
/*      */     {
/*      */       try
/*      */       {
/* 3102 */         for (int i = 0; i < this.callbackAndTypeVector.size(); ++i)
/*      */         {
/* 3104 */           cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
/* 3105 */           if (cbAndType.devWithType == null)
/*      */           {
/* 3107 */             synchronized (this.monitorThreads)
/*      */             {
/* 3109 */               if (!this.monitorThreads.contains(cbAndType.callback))
/*      */               {
/* 3111 */                 this.monitorThreads.addElement(cbAndType.callback);
/* 3112 */                 cbAndType.callback.addReference();
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3118 */             boolean found = false;
/* 3119 */             DeviceObs devObs = null;
/* 3120 */             synchronized (this.deviceObsVector)
/*      */             {
/* 3122 */               for (int j = 0; j < this.deviceObsVector.size(); ++j)
/*      */               {
/* 3124 */                 devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
/* 3125 */                 if (devObs.callback != cbAndType.callback)
/*      */                   continue;
/* 3127 */                 found = true;
/* 3128 */                 break;
/*      */               }
/*      */ 
/* 3131 */               if (!found)
/*      */               {
/* 3133 */                 devObs = new DeviceObs(cbAndType.callback);
/* 3134 */                 devObs.devWithTypeVector.addElement(cbAndType.devWithType);
/* 3135 */                 this.deviceObsVector.addElement(devObs);
/* 3136 */                 cbAndType.callback.addReference();
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/* 3147 */         this.callbackAndTypeVector.removeAllElements();
/*      */       }
/*      */     }
/*      */ 
/* 3151 */     Vector observers = getCallObservers();
/* 3152 */     for (int i = 0; i < observers.size(); ++i)
/*      */     {
/* 3154 */       removeCallMonitor((TsapiCallMonitor)observers.elementAt(i), cause, privateData);
/*      */     }
/*      */ 
/* 3158 */     if (this.monitorCrossRefID == 0)
/*      */       return;
/* 3160 */     this.provider.deleteMonitor(this.monitorCrossRefID);
/* 3161 */     if (this.provider.getCapabilities().getMonitorStop() != 0)
/*      */     {
/*      */       try
/*      */       {
/* 3166 */         this.provider.tsapi.monitorStop(this.monitorCrossRefID, null, null);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/* 3170 */         throw tue;
/*      */       }
/*      */       catch (Exception e) {
/* 3173 */         log.error(e.getMessage(), e);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3183 */     this.monitorCrossRefID = 0;
/*      */ 
/* 3185 */     if (checkForMonitors())
/*      */       return;
/* 3187 */     setNeedSnapshot(true);
/*      */   }
/*      */ 
/*      */   void considerAddingVDNMonitorCallObservers(Object monitored)
/*      */   {
/* 3232 */     if (!(monitored instanceof TSDevice))
/*      */       return;
/* 3234 */     TSDevice monitoredTSDevice = (TSDevice)monitored;
/* 3235 */     if (!monitoredTSDevice.isPredictiveCallsViaDeviceMonitorSet()) {
/*      */       return;
/*      */     }
/* 3238 */     addDeviceObservers(monitoredTSDevice, null, null, monitoredTSDevice.callsViaAddressMonitorThreads, true);
/*      */   }
/*      */ 
/*      */   void addDeviceObservers(TSDevice tsDevice, Vector<TsapiCallMonitor> _terminalObservers, Vector<TsapiCallMonitor> _addressObservers, Vector<TsapiCallMonitor> _cvdObservers, boolean sendSnapshotEvents)
/*      */   {
/* 3261 */     TsapiCallMonitor callback = null;
/*      */ 
/* 3263 */     boolean found = false;
/*      */ 
/* 3265 */     CSTAConnectionID snapConnID = null;
/* 3266 */     synchronized (this.connections)
/*      */     {
/* 3268 */       if (this.connections.size() > 0)
/*      */       {
/*      */         try
/*      */         {
/* 3272 */           snapConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
/*      */         }
/*      */         catch (TsapiPlatformException e)
/*      */         {
/* 3276 */           log.error("Ignoring exception: " + e);
/* 3277 */           if (this.callID != 0)
/*      */           {
/* 3279 */             snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
/*      */           }
/*      */         }
/*      */       }
/* 3283 */       else if (this.callID != 0)
/*      */       {
/* 3285 */         snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
/*      */       }
/*      */     }
/*      */ 
/* 3289 */     if (_cvdObservers != null)
/*      */     {
/* 3291 */       Vector cvdObservers = new Vector(_cvdObservers);
/* 3292 */       for (int i = 0; i < cvdObservers.size(); ++i)
/*      */       {
/* 3294 */         found = false;
/* 3295 */         callback = (TsapiCallMonitor)cvdObservers.elementAt(i);
/* 3296 */         synchronized (this.monitorThreads)
/*      */         {
/* 3298 */           if (this.monitorThreads.contains(callback))
/*      */           {
/* 3300 */             found = true;
/*      */           }
/*      */ 
/* 3303 */           if (!found)
/*      */           {
/* 3305 */             synchronized (this.staleObsVector)
/*      */             {
/* 3309 */               for (int j = 0; j < this.staleObsVector.size(); ++j)
/*      */               {
/* 3311 */                 if (this.staleObsVector.elementAt(j) != callback)
/*      */                   continue;
/* 3313 */                 this.monitorThreads.addElement(callback);
/* 3314 */                 this.staleObsVector.removeElementAt(j);
/*      */ 
/* 3317 */                 found = true;
/* 3318 */                 break;
/*      */               }
/*      */             }
/*      */ 
/* 3322 */             if (!found)
/*      */             {
/* 3324 */               synchronized (this.deviceObsVector)
/*      */               {
/* 3328 */                 for (int j = 0; j < this.deviceObsVector.size(); ++j)
/*      */                 {
/* 3330 */                   if (((DeviceObs)this.deviceObsVector.elementAt(j)).callback != callback)
/*      */                     continue;
/* 3332 */                   this.monitorThreads.addElement(callback);
/* 3333 */                   this.deviceObsVector.removeElementAt(j);
/*      */ 
/* 3336 */                   found = true;
/* 3337 */                   break;
/*      */                 }
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 3344 */           if ((!found) && (((snapConnID == null) || (!sendSnapshotEvents))))
/*      */           {
/* 3346 */             this.monitorThreads.addElement(callback);
/* 3347 */             callback.addReference();
/* 3348 */             if (sendSnapshotEvents)
/* 3349 */               sendSnapshot(callback, null, false);
/*      */           }
/*      */         }
/* 3352 */         if ((found) || (snapConnID == null) || (!sendSnapshotEvents))
/*      */           continue;
/* 3354 */         this.callbackAndTypeVector.addElement(new CallbackAndType(callback, null));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3359 */     DeviceObs devObs = null;
/* 3360 */     DevWithType devWithType = null;
/*      */ 
/* 3362 */     if (_addressObservers != null)
/*      */     {
/* 3364 */       found = false;
/*      */ 
/* 3366 */       devWithType = new DevWithType(tsDevice, false);
/*      */ 
/* 3368 */       Vector addressObservers = new Vector(_addressObservers);
/*      */ 
/* 3370 */       for (int i = 0; i < addressObservers.size(); ++i)
/*      */       {
/* 3372 */         callback = (TsapiCallMonitor)addressObservers.elementAt(i);
/* 3373 */         found = false;
/* 3374 */         if (this.monitorThreads.contains(callback))
/*      */         {
/* 3376 */           found = true;
/*      */         }
/*      */ 
/* 3379 */         if (!found)
/*      */         {
/* 3381 */           synchronized (this.deviceObsVector)
/*      */           {
/* 3383 */             synchronized (this.staleObsVector)
/*      */             {
/*      */               int k;
/* 3385 */               for (k = 0; k < this.staleObsVector.size(); ++k)
/*      */               {
/* 3387 */                 if (this.staleObsVector.elementAt(k) != callback)
/*      */                   continue;
/* 3389 */                 found = true;
/* 3390 */                 devObs = new DeviceObs(callback);
/* 3391 */                 devObs.devWithTypeVector.addElement(devWithType);
/*      */ 
/* 3394 */                 this.deviceObsVector.addElement(devObs);
/* 3395 */                 this.staleObsVector.removeElementAt(k);
/* 3396 */                 break;
/*      */               }
/*      */             }
/*      */ 
/* 3400 */             if (!found)
/*      */             {
/* 3402 */               for ( i = 0; i < this.deviceObsVector.size(); ++i)
/*      */               {
/* 3404 */                 devObs = (DeviceObs)this.deviceObsVector.elementAt(i);
/* 3405 */                 if (devObs.callback != callback)
/*      */                   continue;
/* 3407 */                 found = true;
/* 3408 */                 synchronized (devObs.devWithTypeVector)
/*      */                 {
/* 3410 */                   if (!devObs.devWithTypeVector.contains(devWithType))
/*      */                   {
/* 3412 */                     devObs.devWithTypeVector.addElement(devWithType);
/*      */                   }
/*      */                 }
/* 3415 */                 break;
/*      */               }
/*      */ 
/* 3418 */               if ((!found) && (((snapConnID == null) || (!sendSnapshotEvents))))
/*      */               {
/* 3420 */                 devObs = new DeviceObs(callback);
/* 3421 */                 devObs.devWithTypeVector.addElement(devWithType);
/* 3422 */                 this.deviceObsVector.addElement(devObs);
/* 3423 */                 callback.addReference();
/* 3424 */                 if (sendSnapshotEvents)
/* 3425 */                   sendSnapshot(callback, null, false);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 3430 */         if ((found) || (snapConnID == null) || (!sendSnapshotEvents))
/*      */           continue;
/* 3432 */         this.callbackAndTypeVector.addElement(new CallbackAndType(callback, devWithType));
/*      */       }
/*      */     }
/*      */ 
/* 3436 */     if (_terminalObservers == null)
/*      */       return;
/* 3438 */     found = false;
/*      */ 
/* 3440 */     devWithType = new DevWithType(tsDevice, true);
/*      */ 
/* 3442 */     Vector terminalObservers = new Vector(_terminalObservers);
/*      */ 
/* 3444 */     for (int i = 0; i < terminalObservers.size(); ++i)
/*      */     {
/* 3446 */       callback = (TsapiCallMonitor)terminalObservers.elementAt(i);
/* 3447 */       found = false;
/* 3448 */       if (this.monitorThreads.contains(callback))
/*      */       {
/* 3450 */         found = true;
/*      */       }
/* 3452 */       if (!found)
/*      */       {
/* 3454 */         synchronized (this.deviceObsVector)
/*      */         {
/* 3456 */           synchronized (this.staleObsVector)
/*      */           {
/*      */             int l;
/* 3458 */             for (l = 0; l < this.staleObsVector.size(); ++l)
/*      */             {
/* 3460 */               if (this.staleObsVector.elementAt(l) != callback)
/*      */                 continue;
/* 3462 */               found = true;
/* 3463 */               devObs = new DeviceObs(callback);
/* 3464 */               devObs.devWithTypeVector.addElement(devWithType);
/*      */ 
/* 3467 */               this.deviceObsVector.addElement(devObs);
/* 3468 */               this.staleObsVector.removeElementAt(l);
/* 3469 */               break;
/*      */             }
/*      */           }
/*      */ 
/* 3473 */           if (!found)
/*      */           {
/*      */             int j;
/* 3475 */             for (j = 0; j < this.deviceObsVector.size(); ++j)
/*      */             {
/* 3477 */               devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
/* 3478 */               if (devObs.callback != callback)
/*      */                 continue;
/* 3480 */               found = true;
/* 3481 */               synchronized (devObs.devWithTypeVector)
/*      */               {
/* 3483 */                 if (!devObs.devWithTypeVector.contains(devWithType))
/*      */                 {
/* 3485 */                   devObs.devWithTypeVector.addElement(devWithType);
/*      */                 }
/*      */               }
/* 3488 */               break;
/*      */             }
/*      */ 
/* 3491 */             if ((!found) && (((snapConnID == null) || (!sendSnapshotEvents))))
/*      */             {
/* 3493 */               devObs = new DeviceObs(callback);
/* 3494 */               devObs.devWithTypeVector.addElement(devWithType);
/* 3495 */               this.deviceObsVector.addElement(devObs);
/* 3496 */               callback.addReference();
/* 3497 */               if (sendSnapshotEvents)
/* 3498 */                 sendSnapshot(callback, null, false);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 3503 */       if ((found) || (snapConnID == null) || (!sendSnapshotEvents))
/*      */         continue;
/* 3505 */       this.callbackAndTypeVector.addElement(new CallbackAndType(callback, devWithType));
/*      */     }
/*      */   }
/*      */ 
/*      */   void processCallbackSnapshots(int cause)
/*      */   {
/* 3516 */     boolean doDoSnapshot = false;
/* 3517 */     synchronized (this.callbackAndTypeVector)
/*      */     {
/* 3519 */       if ((this.callbackAndTypeVector.size() > 0) && (this.currentSnapshotHandler == null))
/*      */       {
/* 3521 */         doDoSnapshot = true;
/*      */       }
/*      */     }
/* 3524 */     if (!doDoSnapshot)
/*      */       return;
/* 3526 */     CSTAConnectionID snapConnID = null;
/* 3527 */     synchronized (this.connections)
/*      */     {
/* 3529 */       if (this.connections.size() > 0)
/*      */       {
/*      */         try
/*      */         {
/* 3533 */           snapConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
/*      */         }
/*      */         catch (TsapiPlatformException e)
/*      */         {
/* 3537 */           log.error("Ignoring exception: " + e);
/* 3538 */           if (this.callID != 0)
/*      */           {
/* 3540 */             snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
/*      */           }
/*      */         }
/*      */       }
/* 3544 */       else if (this.callID != 0)
/*      */       {
/* 3546 */         snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
/*      */       }
/*      */     }
/* 3549 */     if (snapConnID != null)
/* 3550 */       doSnapshot(snapConnID, null, false, cause);
/*      */   }
/*      */ 
/*      */   void doCallbackSnapshots(Vector<TSEvent> eventList, int cause)
/*      */   {
/* 3556 */     CallbackAndType cbAndType = null;
/* 3557 */     synchronized (this.callbackAndTypeVector)
/*      */     {
/*      */       try
/*      */       {
/* 3561 */         for (int i = 0; i < this.callbackAndTypeVector.size(); ++i)
/*      */         {
/* 3563 */           cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
/* 3564 */           if (cbAndType.devWithType == null)
/*      */           {
/* 3566 */             synchronized (this.monitorThreads)
/*      */             {
/* 3568 */               if (!this.monitorThreads.contains(cbAndType.callback))
/*      */               {
/* 3570 */                 this.monitorThreads.addElement(cbAndType.callback);
/* 3571 */                 cbAndType.callback.addReference();
/* 3572 */                 sendSnapshot(cbAndType.callback, eventList, false, cause);
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3578 */             boolean found = false;
/* 3579 */             DeviceObs devObs = null;
/* 3580 */             synchronized (this.deviceObsVector)
/*      */             {
/* 3582 */               for (int j = 0; j < this.deviceObsVector.size(); ++j)
/*      */               {
/* 3584 */                 devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
/* 3585 */                 if (devObs.callback != cbAndType.callback)
/*      */                   continue;
/* 3587 */                 found = true;
/* 3588 */                 break;
/*      */               }
/*      */ 
/* 3591 */               if (!found)
/*      */               {
/* 3593 */                 devObs = new DeviceObs(cbAndType.callback);
/* 3594 */                 devObs.devWithTypeVector.addElement(cbAndType.devWithType);
/* 3595 */                 this.deviceObsVector.addElement(devObs);
/* 3596 */                 cbAndType.callback.addReference();
/* 3597 */                 sendSnapshot(cbAndType.callback, eventList, false, cause);
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/* 3608 */         this.callbackAndTypeVector.removeAllElements();
/*      */       }
/*      */     }
/* 3611 */     if (!checkForMonitors())
/*      */       return;
/* 3613 */     this.needSnapshot = false;
/*      */   }
/*      */ 
/*      */   void removeDefaultDeviceObservers(TSDevice tsDevice, boolean isTerminal)
/*      */   {
/* 3630 */     Vector cbKeepVector = new Vector();
/* 3631 */     DeviceObs devObs = null;
/* 3632 */     DevWithType devWithType = new DevWithType(tsDevice, isTerminal);
/*      */ 
/* 3634 */     synchronized (this.deviceObsVector)
/*      */     {
/* 3636 */       for (int j = 0; j < this.deviceObsVector.size(); ++j)
/*      */       {
/* 3638 */         devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
/* 3639 */         synchronized (devObs.devWithTypeVector)
/*      */         {
/* 3641 */           if (devObs.devWithTypeVector.removeElement(devWithType))
/*      */           {
/* 3643 */             if ((devObs.devWithTypeVector.isEmpty()) && (!devObs.callback.isVDN()))
/*      */             {
/* 3647 */               this.staleObsVector.addElement(devObs.callback);
/*      */             }
/*      */             else
/*      */             {
/* 3651 */               cbKeepVector.addElement(devObs);
/*      */             }
/*      */ 
/*      */           }
/*      */           else {
/* 3656 */             cbKeepVector.addElement(devObs);
/*      */           }
/*      */         }
/*      */       }
/* 3660 */       this.deviceObsVector.clear();
/* 3661 */       this.deviceObsVector.addAll(cbKeepVector);
/*      */     }
/*      */ 
/* 3664 */     if ((this.internalDeviceMonitor == null) || (!this.internalDeviceMonitor.equals(tsDevice)))
/*      */       return;
/* 3666 */     this.internalDeviceMonitor.removeInternalMonitor(this);
/* 3667 */     this.internalDeviceMonitor = null;
/* 3668 */     if (checkForMonitors())
/*      */       return;
/* 3670 */     setNeedSnapshot(true);
/*      */   }
/*      */ 
/*      */   void staleObsCleanup(int cause)
/*      */   {
/* 3679 */     synchronized (this.staleObsVector)
/*      */     {
/* 3681 */       for (int i = 0; i < this.staleObsVector.size(); ++i)
/*      */       {
/* 3683 */         ((TsapiCallMonitor)this.staleObsVector.elementAt(i)).deleteReference(this, false, cause, null);
/*      */       }
/*      */ 
/* 3686 */       this.staleObsVector.removeAllElements();
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean doHeldTalkingMatch(TSCall otherCall)
/*      */   {
/* 3695 */     TSConnection conn = null;
/* 3696 */     TSConnection termConn = null;
/* 3697 */     synchronized (this.connections)
/*      */     {
/* 3699 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/* 3701 */         conn = (TSConnection)this.connections.elementAt(i);
/* 3702 */         Vector termConns = conn.getTermConns();
/* 3703 */         if (termConns == null)
/*      */           continue;
/* 3705 */         synchronized (termConns)
/*      */         {
/* 3707 */           for (int j = 0; j < termConns.size(); ++j)
/*      */           {
/* 3709 */             termConn = (TSConnection)termConns.elementAt(j);
/* 3710 */             if ((termConn.getCallControlTermConnState() != 99) || 
/* 3713 */               (otherCall.findActiveTermConnection(termConn.getTSDevice()) == null)) {
/*      */               continue;
/*      */             }
/* 3716 */             return true;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3724 */     return false;
/*      */   }
/*      */ 
/*      */   TSConnection findHeldTermConnection(TSDevice matchDevice)
/*      */   {
/* 3732 */     TSConnection conn = null;
/* 3733 */     TSConnection termConn = null;
/*      */ 
/* 3735 */     synchronized (this.connections)
/*      */     {
/* 3737 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/* 3739 */         conn = (TSConnection)this.connections.elementAt(i);
/* 3740 */         Vector termConns = conn.getTermConns();
/* 3741 */         if (termConns == null)
/*      */           continue;
/* 3743 */         synchronized (termConns)
/*      */         {
/* 3745 */           for (int j = 0; j < termConns.size(); ++j)
/*      */           {
/* 3747 */             termConn = (TSConnection)termConns.elementAt(j);
/* 3748 */             if (((termConn.getCallControlTermConnState() != 99) && (termConn.getCallControlTermConnState() != 103)) || (termConn.getTSDevice() != matchDevice)) {
/*      */               continue;
/*      */             }
/*      */ 
/* 3752 */             return termConn;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3760 */     return null;
/*      */   }
/*      */ 
/*      */   TSConnection findActiveTermConnection(TSDevice matchDevice)
/*      */   {
/* 3768 */     TSConnection conn = null;
/* 3769 */     TSConnection termConn = null;
/*      */ 
/* 3771 */     synchronized (this.connections)
/*      */     {
/* 3773 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/* 3775 */         conn = (TSConnection)this.connections.elementAt(i);
/* 3776 */         Vector termConns = conn.getTermConns();
/* 3777 */         if (termConns == null)
/*      */           continue;
/* 3779 */         synchronized (termConns)
/*      */         {
/* 3781 */           for (int j = 0; j < termConns.size(); ++j)
/*      */           {
/* 3783 */             termConn = (TSConnection)termConns.elementAt(j);
/* 3784 */             if (((termConn.getCallControlTermConnState() != 98) && (termConn.getCallControlTermConnState() != 103)) || (termConn.getTSDevice() != matchDevice)) {
/*      */               continue;
/*      */             }
/*      */ 
/* 3788 */             return termConn;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3795 */     return null;
/*      */   }
/*      */ 
/*      */   protected void moveInternalStuff(TSCall otherCall)
/*      */   {
/* 3805 */     TsapiCallMonitor callback = null;
/*      */ 
/* 3807 */     synchronized (otherCall.monitorThreads)
/*      */     {
/* 3809 */       for (int i = 0; i < otherCall.monitorThreads.size(); ++i)
/*      */       {
/* 3811 */         callback = (TsapiCallMonitor)otherCall.monitorThreads.elementAt(i);
/* 3812 */         synchronized (this.monitorThreads)
/*      */         {
/* 3814 */           if (!this.monitorThreads.contains(callback))
/*      */           {
/* 3816 */             this.monitorThreads.addElement(callback);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 3821 */     DeviceObs devObs = null;
/* 3822 */     DeviceObs otherDevObs = null;
/* 3823 */     DevWithType devWithType = null;
/*      */ 
/* 3825 */     synchronized (otherCall.deviceObsVector)
/*      */     {
/* 3827 */       for (int i = 0; i < otherCall.deviceObsVector.size(); ++i)
/*      */       {
/* 3829 */         otherDevObs = (DeviceObs)otherCall.deviceObsVector.elementAt(i);
/* 3830 */         i = 0;
/* 3831 */         synchronized (this.deviceObsVector)
/*      */         {
/* 3833 */           for (int j = 0; j < this.deviceObsVector.size(); ++j)
/*      */           {
/* 3835 */             devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
/* 3836 */             if (otherDevObs.callback != devObs.callback)
/*      */               continue;
/* 3838 */             i = 1;
/* 3839 */             synchronized (otherDevObs.devWithTypeVector)
/*      */             {
/* 3841 */               for (int k = 0; k < otherDevObs.devWithTypeVector.size(); ++k)
/*      */               {
/* 3843 */                 devWithType = (DevWithType)otherDevObs.devWithTypeVector.elementAt(k);
/* 3844 */                 synchronized (devObs.devWithTypeVector)
/*      */                 {
/* 3846 */                   if (!devObs.devWithTypeVector.contains(devWithType))
/*      */                   {
/* 3848 */                     devObs.devWithTypeVector.addElement(devWithType);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 3853 */             break;
/*      */           }
/*      */ 
/* 3856 */           if (i == 0)
/*      */           {
/* 3858 */             this.deviceObsVector.addElement(otherDevObs);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3864 */     CallbackAndType otherCbAndType = null;
/* 3865 */     CallbackAndType cbAndType = null;
/* 3866 */     synchronized (otherCall.callbackAndTypeVector)
/*      */     {
/* 3868 */       for (int i = 0; i < otherCall.callbackAndTypeVector.size(); ++i)
/*      */       {
/* 3870 */         otherCbAndType = (CallbackAndType)otherCall.callbackAndTypeVector.elementAt(i);
/* 3871 */         int j = 0;
/* 3872 */         synchronized (this.callbackAndTypeVector)
/*      */         {
/* 3874 */           for (j = 0; j < this.callbackAndTypeVector.size(); ++j)
/*      */           {
/* 3876 */             cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(j);
/* 3877 */             if ((!otherCbAndType.callback.equals(cbAndType.callback)) || (!otherCbAndType.devWithType.equals(cbAndType.devWithType))) {
/*      */               continue;
/*      */             }
/* 3880 */             j = 1;
/* 3881 */             break;
/*      */           }
/*      */ 
/* 3884 */           if (j == 0)
/*      */           {
/* 3886 */             this.callbackAndTypeVector.addElement(otherCbAndType);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3892 */     synchronized (otherCall)
/*      */     {
/* 3894 */       synchronized (this)
/*      */       {
/* 3896 */         if (!this.monitorPending)
/*      */         {
/* 3898 */           this.monitorPending = otherCall.monitorPending;
/*      */         }
/* 3900 */         if (this.internalDeviceMonitor == null)
/*      */         {
/* 3902 */           this.internalDeviceMonitor = otherCall.internalDeviceMonitor;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3907 */     moveStuff(otherCall);
/*      */   }
/*      */ 
/*      */   void copyStuff(TSCall otherCall)
/*      */   {
/* 3916 */     if (!callIsInVDNDomain(otherCall)) {
/*      */       return;
/*      */     }
/* 3919 */     Vector observers = otherCall.getCallObservers();
/*      */ 
/* 3921 */     for (int i = 0; i < observers.size(); ++i)
/*      */     {
/* 3923 */       if ((((TsapiCallMonitor)observers.elementAt(i)).isVDN()) && (!this.monitorThreads.contains(observers.elementAt(i))))
/*      */       {
/* 3925 */         this.monitorThreads.addElement((TsapiCallMonitor) observers.elementAt(i));
/* 3926 */         ((TsapiCallMonitor)observers.elementAt(i)).addReference();
/*      */       }
/*      */       else
/*      */       {
/* 3930 */         otherCall.removeCallMonitor((TsapiCallMonitor)observers.elementAt(i));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void moveStuff(TSCall otherCall)
/*      */   {
/* 3942 */     if (otherCall == null)
/*      */     {
/* 3944 */       return;
/*      */     }
/*      */ 
/* 3950 */     if (callIsInVDNDomain(otherCall))
/*      */     {
/* 3953 */       Vector observers = otherCall.getCallObservers();
/*      */ 
/* 3955 */       for (int i = 0; i < observers.size(); ++i)
/*      */       {
/* 3957 */         if ((((TsapiCallMonitor)observers.elementAt(i)).isVDN()) && (!this.monitorThreads.contains(observers.elementAt(i))))
/*      */         {
/* 3959 */           this.monitorThreads.addElement((TsapiCallMonitor) observers.elementAt(i));
/* 3960 */           ((TsapiCallMonitor)observers.elementAt(i)).addReference();
/*      */         }
/*      */         else
/*      */         {
/* 3964 */           otherCall.removeCallMonitor((TsapiCallMonitor)observers.elementAt(i));
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 3970 */       otherCall.removeObservers(100, null, 0);
/*      */     }
/*      */ 
/* 3976 */     if (this.callingAddress == null)
/* 3977 */       this.callingAddress = otherCall.callingAddress;
/* 3978 */     if (this.callingTerminal == null)
/* 3979 */       this.callingTerminal = otherCall.callingTerminal;
/* 3980 */     if (this.calledDevice == null)
/* 3981 */       this.calledDevice = otherCall.calledDevice;
/* 3982 */     if (this.lastRedirectionDevice == null)
/* 3983 */       this.lastRedirectionDevice = otherCall.lastRedirectionDevice;
/* 3984 */     if (this.confController == null)
/* 3985 */       this.confController = otherCall.confController;
/* 3986 */     if (this.xferController == null)
/* 3987 */       this.xferController = otherCall.xferController;
/* 3988 */     if (this.uui == null)
/* 3989 */       this.uui = otherCall.uui;
/* 3990 */     if (this.lai == null)
/* 3991 */       this.lai = otherCall.lai;
/* 3992 */     if (this.uec == null)
/* 3993 */       this.uec = otherCall.uec;
/* 3994 */     if (this.oci == null)
/* 3995 */       this.oci = otherCall.oci;
/* 3996 */     if (getDeviceHistory() == null) {
/* 3997 */       setDeviceHistory(otherCall.getDeviceHistory());
/*      */     }
/*      */ 
/* 4000 */     this.replyPriv = otherCall.replyPriv;
/* 4001 */     this.confEnable = otherCall.confEnable;
/* 4002 */     this.xferEnable = otherCall.xferEnable;
/*      */ 
/* 4005 */     TSTrunk trk = null;
/* 4006 */     synchronized (otherCall.trkVector)
/*      */     {
/* 4008 */       for (int i = 0; i < otherCall.trkVector.size(); ++i)
/*      */       {
/* 4010 */         trk = (TSTrunk)otherCall.trkVector.elementAt(i);
/* 4011 */         trk.setCall(this, null);
/* 4012 */         synchronized (this.trkVector)
/*      */         {
/* 4014 */           if (!this.trkVector.contains(trk))
/*      */           {
/* 4016 */             this.trkVector.addElement(trk);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4022 */     if (checkForMonitors())
/*      */       return;
/* 4024 */     setNeedSnapshot(true);
/*      */   }
/*      */ 
/*      */   public boolean callIsInVDNDomain(TSCall callToCheck)
/*      */   {
/* 4036 */     return callToCheck.refVDN != null;
/*      */   }
/*      */ 
/*      */   Vector<TsapiCallMonitor> getObservers()
/*      */   {
/* 4047 */     Vector allObservers = new Vector(this.monitorThreads);
/* 4048 */     synchronized (this.deviceObsVector)
/*      */     {
/* 4050 */       for (int i = 0; i < this.deviceObsVector.size(); ++i)
/*      */       {
/* 4052 */         if (!allObservers.contains(((DeviceObs)this.deviceObsVector.elementAt(i)).callback))
/* 4053 */           allObservers.addElement(((DeviceObs)this.deviceObsVector.elementAt(i)).callback);
/*      */       }
/*      */     }
/* 4056 */     synchronized (this.staleObsVector)
/*      */     {
/* 4058 */       for (int i = 0; i < this.staleObsVector.size(); ++i)
/*      */       {
/* 4060 */         if (!allObservers.contains(this.staleObsVector.elementAt(i)))
/* 4061 */           allObservers.addElement(this.staleObsVector.elementAt(i));
/*      */       }
/*      */     }
/* 4064 */     return allObservers;
/*      */   }
/*      */ 
/*      */   public Vector<TsapiCallMonitor> getCallObservers()
/*      */   {
/* 4072 */     Vector allObservers = getObservers();
/* 4073 */     synchronized (this.callbackAndTypeVector)
/*      */     {
/* 4075 */       CallbackAndType cbAndType = null;
/* 4076 */       for (int i = 0; i < this.callbackAndTypeVector.size(); ++i)
/*      */       {
/* 4078 */         cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
/* 4079 */         TsapiCallMonitor obs = cbAndType.callback;
/* 4080 */         if (allObservers.contains(obs))
/*      */           continue;
/* 4082 */         allObservers.addElement(obs);
/*      */       }
/*      */     }
/*      */ 
/* 4086 */     return allObservers;
/*      */   }
/*      */ 
/*      */   public TSCall getHandOff()
/*      */   {
/* 4094 */     if (this.handOffCall != null)
/*      */     {
/* 4096 */       return this.handOffCall;
/*      */     }
/*      */ 
/* 4099 */     return this;
/*      */   }
/*      */ 
/*      */   synchronized void setCallID(int newCallID)
/*      */   {
/* 4113 */     if (newCallID == 0)
/*      */     {
/* 4116 */       int rc = this.provider.getNonCallID();
/* 4117 */       if (rc != -1)
/*      */       {
/* 4119 */         this.nonCallID = rc;
/* 4120 */         this.provider.addNonCallToHash(this);
/*      */       }
/*      */     }
/* 4123 */     else if (this.callID == 0)
/*      */     {
/* 4128 */       this.callID = newCallID;
/* 4129 */       if (this.nonCallID != -1)
/*      */       {
/* 4131 */         this.provider.deleteNonCallFromHash(this.nonCallID);
/* 4132 */         this.provider.releaseNonCallID(this.nonCallID);
/* 4133 */         this.nonCallID = -1;
/*      */       }
/*      */ 
/* 4136 */       TSCall tmpCall = this.provider.addCallToHash(this);
/* 4137 */       if (tmpCall != null)
/*      */       {
/* 4140 */         if ((this.ucid != null) && (tmpCall.ucid != null) && (this.ucid.compareTo(tmpCall.ucid) == 0))
/*      */         {
/* 4146 */           this.handOffCall = tmpCall;
/*      */ 
/* 4152 */           this.provider.addCallToHash(this.handOffCall);
/*      */ 
/* 4155 */           synchronized (this.connections)
/*      */           {
/* 4157 */             for (int i = 0; i < this.connections.size(); ++i)
/*      */             {
/* 4159 */               TSConnection conn = (TSConnection)this.connections.elementAt(i);
/* 4160 */               conn.setCall(this.handOffCall);
/* 4161 */               this.handOffCall.addConnection(conn, null);
/*      */             }
/*      */           }
/*      */ 
/* 4165 */           this.handOffCall.moveInternalStuff(this);
/*      */         }
/*      */         else
/*      */         {
/* 4169 */           tmpCall.setState(34, null);
/* 4170 */           this.provider.dumpCall(tmpCall.getCallID());
/* 4171 */           this.handOffCall = null;
/*      */ 
/* 4173 */           log.info("Mismatched UCID for setCallID removing stale call obj " + tmpCall);
/* 4174 */           log.info("UCID for setCallID for the new call is " + this.ucid);
/*      */ 
/* 4180 */           this.provider.addCallToHash(this);
/*      */         }
/*      */       }
/*      */     } else {
/* 4184 */       if (newCallID == this.callID) {
/*      */         return;
/*      */       }
/* 4187 */       this.provider.changeCallIDInDomain(this.callID, newCallID);
/*      */ 
/* 4190 */       this.provider.deleteCallFromHash(this.callID);
/*      */ 
/* 4192 */       int saveCallID = this.callID;
/* 4193 */       this.callID = newCallID;
/*      */ 
/* 4196 */       TSCall saveCall = this.provider.addCallToHash(this);
/*      */ 
/* 4211 */       if (saveCall == null)
/*      */         return;
/* 4213 */       saveCall.callID = saveCallID;
/* 4214 */       this.provider.addCallToHash(saveCall);
/*      */ 
/* 4218 */       Vector conns = new Vector(saveCall.connections);
/* 4219 */       for (int i = 0; i < conns.size(); ++i)
/*      */       {
/* 4221 */         TSConnection conn = (TSConnection)conns.elementAt(i);
/* 4222 */         Vector cv = conn.getTermConns();
/* 4223 */         if ((cv != null) && (cv.size() > 0))
/*      */         {
/* 4225 */           Vector termConns = new Vector(cv);
/* 4226 */           for (int j = 0; j < termConns.size(); ++j)
/*      */           {
/* 4228 */             TSConnection tc = (TSConnection)termConns.elementAt(j);
/* 4229 */             CSTAConnectionID connID = tc.getConnID();
/* 4230 */             connID.setCallID(saveCallID);
/* 4231 */             tc.setConnID(connID);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 4236 */           CSTAConnectionID connID = conn.getConnID();
/* 4237 */           connID.setCallID(saveCallID);
/* 4238 */           conn.setConnID(connID);
/*      */         }
/*      */       }
/* 4241 */       moveInternalStuff(saveCall);
/*      */     }
/*      */   }
/*      */ 
/*      */   void setState(int _state, Vector<TSEvent> eventList)
/*      */   {
/* 4255 */     synchronized (this)
/*      */     {
/* 4259 */       if ((this.state == _state) || (this.state == 34))
/*      */       {
/* 4261 */         return;
/*      */       }
/*      */ 
/* 4264 */       this.state = _state;
/*      */     }
/*      */ 
/* 4267 */     switch (this.state)
/*      */     {
/*      */     case 33:
/* 4270 */       if (eventList != null)
/*      */       {
/* 4272 */         eventList.addElement(new TSEvent(4, this));
/*      */       }
/*      */ 
/* 4275 */       boolean tryMonitor = false;
/* 4276 */       synchronized (this)
/*      */       {
/* 4278 */         if (this.monitorPending)
/*      */         {
/* 4280 */           tryMonitor = true;
/* 4281 */           this.monitorPending = false;
/*      */         }
/*      */       }
/* 4284 */       if (!tryMonitor)
/*      */         return;
/*      */       try
/*      */       {
/* 4288 */         setMonitor(false);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/* 4292 */         throw tue;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 4296 */         log.error(e.getMessage(), e);
/* 4297 */         removeObservers(107, null, 0);
/* 4298 */       }break;
/*      */     case 34:
/* 4304 */       Vector trkVectorClone = (Vector)this.trkVector.clone();
/*      */       int i;
/* 4305 */       for (i = 0; i < trkVectorClone.size(); ++i)
/*      */       {
/* 4307 */         TSTrunk tmpTrunk = (TSTrunk)trkVectorClone.elementAt(i);
/*      */ 
/* 4309 */         synchronized (tmpTrunk) {
/* 4310 */           if (this.trkVector.removeElement(tmpTrunk)) {
/* 4311 */             tmpTrunk.setState(1, eventList);
/*      */           }
/*      */         }
/* 4314 */         tmpTrunk = null;
/*      */       }
/* 4316 */       trkVectorClone.removeAllElements();
/* 4317 */       trkVectorClone = null;
/*      */ 
/* 4321 */       Vector conn = new Vector(this.connections);
/* 4322 */       for (i = 0; i < conn.size(); ++i)
/*      */       {
/* 4324 */         ((TSConnection)conn.elementAt(i)).setConnectionState(89, eventList);
/*      */       }
/* 4326 */       if (eventList != null)
/*      */       {
/* 4328 */         eventList.addElement(new TSEvent(5, this));
/*      */       }
/*      */ 
/* 4336 */       this.needSnapshot = false;
/*      */ 
/* 4339 */       synchronized (this.connections)
/*      */       {
/* 4341 */         this.connections.notify();
/*      */       }
/*      */ 
/* 4344 */       delete();
/*      */     }
/*      */   }
/*      */ 
/*      */   void setStateForVDN()
/*      */   {
/* 4364 */     this.provider.removeCallFromDomain(this);
/*      */   }
/*      */ 
/*      */   boolean needsSnapshot()
/*      */   {
/* 4371 */     return this.needSnapshot;
/*      */   }
/*      */ 
/*      */   boolean doSnapshot(CSTAConnectionID connID, SnapshotCallExtraConfHandler extraHandler, boolean waitForSnapshotConf)
/*      */   {
/* 4382 */     return doSnapshot(connID, extraHandler, waitForSnapshotConf, 110);
/*      */   }
/*      */ 
/*      */   boolean doSnapshot(CSTAConnectionID connID, SnapshotCallExtraConfHandler extraHandler, boolean waitForSnapshotConf, int cause)
/*      */   {
/* 4387 */     if (!this.needSnapshot)
/*      */     {
/*      */       try
/*      */       {
/* 4392 */         if (extraHandler != null)
/*      */         {
/* 4394 */           extraHandler.handleConf(true, null, null);
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/* 4399 */         doCallbackSnapshots(null, cause);
/*      */       }
/* 4401 */       return true;
/*      */     }
/*      */ 
/* 4404 */     if (this.provider.getCapabilities().getSnapshotCallReq() == 0)
/*      */     {
/*      */       try
/*      */       {
/* 4409 */         if (extraHandler != null)
/*      */         {
/* 4411 */           extraHandler.handleConf(false, null, null);
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/* 4416 */         doCallbackSnapshots(null, cause);
/*      */       }
/* 4418 */       return false;
/*      */     }
/*      */ 
/* 4421 */     if (!waitForSnapshotConf)
/*      */     {
/* 4423 */       synchronized (this.callbackAndTypeVector)
/*      */       {
/* 4425 */         if (this.futureAsynchronousSnapshotHandler != null)
/*      */         {
/* 4427 */           this.futureAsynchronousSnapshotHandler.addExtraHandler(extraHandler);
/* 4428 */           return true;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4434 */     SnapshotCallConfHandler handler = new SnapshotCallConfHandler(this, extraHandler, waitForSnapshotConf);
/*      */ 
/* 4436 */     synchronized (handler)
/*      */     {
/* 4438 */       this.snapshotCallConfPending = true;
/* 4439 */       this.provider.tsapi.snapshotCall(connID, null, handler);
/*      */ 
/* 4441 */       if (waitForSnapshotConf)
/*      */       {
/*      */         try
/*      */         {
/* 4445 */           handler.wait(TSProviderImpl.TSAPI_RESPONSE_TIME);
/*      */         } catch (InterruptedException e) {
/*      */         }
/* 4448 */         if (!handler.handled)
/*      */         {
/*      */           try
/*      */           {
/* 4453 */             if (extraHandler != null)
/*      */             {
/* 4455 */               extraHandler.handleConf(false, null, null);
/*      */             }
/*      */           }
/*      */           finally
/*      */           {
/* 4460 */             doCallbackSnapshots(null, cause);
/*      */           }
/* 4462 */           return false;
/*      */         }
/* 4464 */         return handler.rc;
/*      */       }
/*      */     }
/*      */ 
/* 4468 */     return true;
/*      */   }
/*      */ 
/*      */   void getSnapshot(Vector<TSEvent> eventList)
/*      */   {
/* 4473 */     if (eventList == null)
/*      */     {
/* 4475 */       return;
/*      */     }
/*      */ 
/* 4478 */     switch (this.state)
/*      */     {
/*      */     case 33:
/* 4481 */       eventList.addElement(new TSEvent(4, this));
/* 4482 */       break;
/*      */     case 34:
/* 4484 */       eventList.addElement(new TSEvent(5, this));
/*      */     }
/*      */ 
/* 4490 */     synchronized (this.connections)
/*      */     {
/* 4492 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/* 4494 */         ((TSConnection)this.connections.elementAt(i)).getSnapshot(eventList);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void sendSnapshot(TsapiCallMonitor callback, Vector<TSEvent> _eventList, boolean snapMetaCode)
/*      */   {
/* 4505 */     sendSnapshot(callback, _eventList, snapMetaCode, 110);
/*      */   }
/*      */ 
/*      */   void sendSnapshot(TsapiCallMonitor callback, Vector<TSEvent> _eventList, boolean snapMetaCode, int cause) {
/* 4509 */     if (callback == null)
/*      */     {
/* 4511 */       return;
/*      */     }
/*      */ 
/* 4514 */     Vector eventList = null;
/*      */ 
/* 4516 */     if (_eventList == null)
/*      */     {
/* 4518 */       eventList = new Vector();
/* 4519 */       getSnapshot(eventList);
/*      */     }
/*      */     else
/*      */     {
/* 4523 */       eventList = _eventList;
/*      */     }
/*      */ 
/* 4565 */     setCSTACause((short) -1);
/*      */ 
/* 4567 */     if (eventList.size() <= 0) {
/*      */       return;
/*      */     }
/* 4570 */     callback.deliverEvents(eventList, cause, snapMetaCode);
/*      */   }
/*      */ 
/*      */   public synchronized void setNeedSnapshot(boolean flag)
/*      */   {
/* 4580 */     this.needSnapshot = flag;
/*      */   }
/*      */ 
/*      */   boolean isMonitorSet()
/*      */   {
/* 4588 */     return (this.monitorCrossRefID != 0) || (this.provider.isCallInAnyDomain(this));
/*      */   }
/*      */ 
/*      */   void setMonitor(boolean waitForSnapshotConf)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/* 4600 */     CSTAConnectionID monConnID = null;
/* 4601 */     if (!isMonitorSet())
/*      */     {
/* 4603 */       synchronized (this.connections)
/*      */       {
/* 4605 */         if (this.connections.size() > 0)
/*      */         {
/*      */           try
/*      */           {
/* 4609 */             monConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
/*      */           }
/*      */           catch (TsapiPlatformException e)
/*      */           {
/* 4613 */             log.error("Ignoring exception: " + e);
/* 4614 */             if (this.callID != 0)
/*      */             {
/* 4616 */               monConnID = new CSTAConnectionID(this.callID, "", (short) 0);
/*      */             }
/*      */           }
/*      */         }
/* 4620 */         else if (this.callID != 0)
/*      */         {
/* 4622 */           monConnID = new CSTAConnectionID(this.callID, "", (short) 0);
/*      */         }
/*      */       }
/*      */     }
/* 4626 */     if (monConnID != null)
/* 4627 */       monitorCall(monConnID, waitForSnapshotConf);
/*      */   }
/*      */ 
/*      */   void monitorCall(CSTAConnectionID connID, boolean waitForSnapshotConf)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/* 4639 */     if ((this.provider.getCapabilities().getMonitorCall() == 0) || (connID == null))
/*      */     {
/* 4642 */       throw new TsapiResourceUnavailableException(0, 0, 0, "no capability to monitor call");
/*      */     }
/*      */ 
/* 4646 */     if (this.monitorCrossRefID != 0)
/* 4647 */       return;
/*      */     CSTAEvent event;
/*      */     try
/*      */     {
/* 4651 */       event = this.provider.tsapi.monitorCall(connID, new CSTAMonitorFilter(), null);
/*      */     }
/*      */     catch (TsapiUnableToSendException tue)
/*      */     {
/* 4655 */       throw tue;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4659 */       log.error("MonitorCall request failed - retrying");
/*      */       try
/*      */       {
/* 4664 */         event = this.provider.tsapi.monitorCall(connID, new CSTAMonitorFilter(), null);
/*      */       }
/*      */       catch (TsapiResourceUnavailableException e1)
/*      */       {
/* 4668 */         throw e1;
/*      */       }
/*      */       catch (Exception e1)
/*      */       {
/* 4672 */         throw new TsapiResourceUnavailableException(0, 0, 0, "monitor call failure");
/*      */       }
/*      */     }
/*      */ 
/* 4676 */     CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent)event.getEvent();
/*      */ 
/* 4786 */     SnapshotCallExtraConfHandler handler = new AddCallMonitorHandler(this, monitorConf.getMonitorCrossRefID());
/* 4787 */     if (this.needSnapshot)
/*      */     {
/* 4789 */       doSnapshot(connID, handler, waitForSnapshotConf);
/*      */     }
/*      */     else
/*      */     {
/* 4794 */       handler.handleConf(true, null, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   void replaceConnections(Vector<TSConnection> newConnections, Vector<TSEvent> eventList)
/*      */   {
/* 4809 */     for (int i = 0; i < newConnections.size(); ++i)
/*      */     {
/* 4811 */       TSConnection conn = (TSConnection)newConnections.elementAt(i);
/* 4812 */       if (conn.isTerminalConnection())
/*      */       {
/* 4814 */         conn = conn.getTSConn();
/* 4815 */         newConnections.setElementAt(conn, i);
/*      */       }
/*      */ 
/* 4818 */       addConnection(conn, null);
/*      */     }
/*      */ 
/* 4821 */     Vector conns = new Vector(this.connections);
/* 4822 */     for (int i = 0; i < conns.size(); ++i)
/*      */     {
/* 4824 */       TSConnection conn = (TSConnection)conns.elementAt(i);
/* 4825 */       if (newConnections.contains(conn))
/*      */         continue;
/* 4827 */       conn.setConnectionState(89, eventList);
/*      */     }
/*      */   }
/*      */ 
/*      */   TSConnection getConnAtDevice(TSDevice matchDevice)
/*      */   {
/* 4837 */     synchronized (this.connections)
/*      */     {
/* 4839 */       TSConnection conn = null;
/* 4840 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/* 4842 */         conn = (TSConnection)this.connections.elementAt(i);
/* 4843 */         if (conn.getTSDevice() == matchDevice)
/*      */         {
/* 4845 */           return conn;
/*      */         }
/*      */       }
/* 4848 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   TSConnection findOtherConnection(TSConnection conn)
/*      */   {
/* 4858 */     synchronized (this.connections)
/*      */     {
/* 4860 */       int size = this.connections.size();
/* 4861 */       if (size != 2)
/*      */       {
/* 4863 */         return null;
/*      */       }
/* 4865 */       TSConnection otherConn = null;
/* 4866 */       for (int i = 0; i < size; ++i)
/*      */       {
/* 4868 */         otherConn = (TSConnection)this.connections.elementAt(i);
/* 4869 */         if (conn != otherConn)
/*      */         {
/* 4871 */           return otherConn;
/*      */         }
/*      */       }
/* 4874 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   void setDigits(String _digits)
/*      */   {
/* 4883 */     this.digits = _digits;
/*      */   }
/*      */ 
/*      */   public String getDigits()
/*      */   {
/* 4888 */     return this.digits;
/*      */   }
/*      */ 
/*      */   synchronized void delete()
/*      */   {
/* 4898 */     log.info("Call object= " + this + " being deleted" + " for " + this.provider);
/*      */ 
/* 4902 */     if (!this.delayVDNremoveCallFromDomain)
/*      */     {
/* 4904 */       this.provider.removeCallFromDomain(this);
/*      */     }
/*      */ 
/* 4909 */     if (this.internalDeviceMonitor != null)
/*      */     {
/* 4911 */       this.internalDeviceMonitor.removeInternalMonitor(this);
/* 4912 */       this.internalDeviceMonitor = null;
/*      */     }
/*      */ 
/* 4933 */     if (this.nonCallID != -1)
/*      */     {
/* 4935 */       this.provider.deleteNonCallFromHash(this.nonCallID);
/*      */     }
/*      */ 
/* 4939 */     if (this.callID == 0)
/*      */       return;
/* 4941 */     this.provider.deleteCallFromHash(this.callID);
/* 4942 */     this.provider.addCallToSaveHash(this);
/* 4943 */     synchronized (this.staleConnections)
/*      */     {
/* 4945 */       for (int i = 0; i < this.staleConnections.size(); ++i)
/*      */       {
/* 4947 */         ((TSConnection)this.staleConnections.elementAt(i)).delete();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getCallID()
/*      */   {
/* 4955 */     return this.callID;
/*      */   }
/*      */ 
/*      */   int getNonCallID()
/*      */   {
/* 4960 */     return this.nonCallID;
/*      */   }
/*      */ 
/*      */   public void referenced()
/*      */   {
/* 4968 */     this.refCount += 1;
/*      */   }
/*      */ 
/*      */   public void unreferenced()
/*      */   {
/* 4976 */     this.refCount -= 1;
/* 4977 */     if ((this.refCount > 0) || (this.callID != 0))
/*      */       return;
/* 4979 */     setState(34, null);
/* 4980 */     removeObservers(100, null, 0);
/*      */   }
/*      */ 
/*      */   void endNonCVDObservers(int cause)
/*      */   {
/* 5006 */     if ((this.monitorCrossRefID != 0) && (this.wasEverMonitoredByCallsViaDevice != true))
/*      */       return;
/* 5008 */     removeObservers(cause, null, 0);
/*      */   }
/*      */ 
/*      */   public V7DeviceHistoryEntry[] getDeviceHistory()
/*      */   {
/* 5016 */     return this.deviceHistory;
/*      */   }
/*      */ 
/*      */   void setDeviceHistory(V7DeviceHistoryEntry[] deviceHistory)
/*      */   {
/* 5023 */     this.deviceHistory = deviceHistory;
/*      */   }
/*      */ 
/*      */   void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory)
/*      */   {
/* 5033 */     this.deviceHistory = TsapiPromoter.promoteDeviceHistory(deviceHistory);
/*      */   }
/*      */ 
/*      */   public Vector<TSConnection> fastConnect(TSDevice device, String destAddress, CSTAPrivate reqPriv)
/*      */     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/* 5045 */     if (this.provider.getCapabilities().getMakeCall() == 0)
/*      */     {
/* 5047 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 5051 */     if (this.state != 32)
/*      */     {
/* 5053 */       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not idle");
/*      */     }
/*      */ 
/* 5061 */     String devName = device.getName();
/*      */ 
/* 5067 */     if ((((this.provider.getCapabilities().getSnapshotCallReq() == 0) || (this.monitorPending))) && (this.internalDeviceMonitor == null))
/*      */     {
/*      */       try
/*      */       {
/* 5072 */         this.internalDeviceMonitor = device.setInternalMonitor(this);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 5085 */     setConnection_wait_limit(1);
/*      */ 
/* 5087 */     MakeCallConfHandler handler = new MakeCallConfHandler(this, device, destAddress, 24);
/*      */     try
/*      */     {
/* 5091 */       this.provider.tsapi.makeCall(devName, destAddress, reqPriv, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 5095 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidPartyException e)
/*      */     {
/* 5099 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/* 5103 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/* 5107 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 5111 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 5115 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5119 */       if (e instanceof ITsapiException)
/*      */       {
/* 5121 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "makeCall failure");
/*      */       }
/*      */ 
/* 5126 */       throw new TsapiPlatformException(4, 0, "makeCall failure");
/*      */     }
/*      */ 
/* 5130 */     synchronized (this.connections)
/*      */     {
/* 5132 */       if (this.handOffCall != null)
/*      */       {
/* 5134 */         return this.handOffCall.fastConnectFinish(device, destAddress, handler.newCall);
/*      */       }
/*      */ 
/* 5139 */       return fastConnectFinish(device, destAddress, handler.newCall);
/*      */     }
/*      */   }
/*      */ 
/*      */   Vector<TSConnection> fastConnectFinish(TSDevice device, String destAddress, CSTAConnectionID newCall)
/*      */   {
/* 5149 */     if (device.isMonitorSet())
/*      */     {
/* 5153 */       this.needSnapshot = false;
/*      */     }
/*      */     else
/*      */     {
/* 5158 */       doSnapshot(newCall, null, false);
/*      */     }
/*      */ 
/* 5162 */     synchronized (this.connections)
/*      */     {
/* 5164 */       if (this.connections.size() < 1)
/*      */       {
/*      */         try
/*      */         {
/* 5169 */           this.connections.wait(Tsapi.getCallCompletionTimeout());
/*      */         }
/*      */         catch (InterruptedException e)
/*      */         {
/*      */         }
/*      */ 
/* 5188 */         if ((this.connections.size() < 1) && (this.state != 34))
/*      */         {
/* 5190 */           log.info("after succesfully initiating, fastConnect returns null since found a DISCONNECTED originating Connection for call ID " + this.callID);
/* 5191 */           return null;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 5196 */     return this.connections;
/*      */   }
/*      */ 
/*      */   private int getConnection_wait_limit()
/*      */   {
/* 5204 */     return this.connection_wait_limit;
/*      */   }
/*      */ 
/*      */   private void setConnection_wait_limit(int connection_wait_limit)
/*      */   {
/* 5212 */     this.connection_wait_limit = connection_wait_limit;
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 5219 */     return "TSCall" + getMyCustomString() + "@" + Integer.toHexString(super.hashCode());
/*      */   }
/*      */ 
/*      */   private String getMyCustomString()
/*      */   {
/* 5226 */     if (this.callID == 0)
/*      */     {
/* 5228 */       return "[0(nonCallID=" + this.nonCallID + ")]";
/*      */     }
/*      */ 
/* 5232 */     return "[" + this.callID + "]";
/*      */   }
/*      */ 
/*      */   TSConnection findTSConnectionForDevice(TSDevice device)
/*      */   {
/* 5256 */     Vector clonedConnsToCheck = new Vector(this.connections);
/*      */ 
/* 5264 */     for (int j = 0; j < clonedConnsToCheck.size(); ++j)
/*      */     {
/* 5266 */       TSConnection conn = (TSConnection)clonedConnsToCheck.elementAt(j);
/* 5267 */       Vector cv = conn.getTermConns();
/* 5268 */       if ((cv != null) && (cv.size() > 0))
/*      */       {
/* 5270 */         Vector termConns = new Vector(cv);
/* 5271 */         for (int k = 0; k < termConns.size(); ++k)
/*      */         {
/* 5273 */           TSConnection tc = (TSConnection)termConns.elementAt(k);
/* 5274 */           if (tc.getTSDevice() == device)
/*      */           {
/* 5276 */             return tc;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/* 5282 */       else if (conn.getTSDevice() == device)
/*      */       {
/* 5284 */         return conn;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 5292 */     return null;
/*      */   }
/*      */ 
/*      */   void updateConnectionCallIDs(int newCallID)
/*      */   {
/* 5320 */     Vector clonedConnectionsToUpdate = new Vector(this.connections);
/*      */ 
/* 5329 */     for (int j = 0; j < clonedConnectionsToUpdate.size(); ++j)
/*      */     {
/* 5331 */       TSConnection conn = (TSConnection)clonedConnectionsToUpdate.elementAt(j);
/* 5332 */       Vector cv = conn.getTermConns();
/* 5333 */       if ((cv != null) && (cv.size() > 0))
/*      */       {
/* 5335 */         Vector termConns = new Vector(cv);
/* 5336 */         for (int k = 0; k < termConns.size(); ++k)
/*      */         {
/* 5338 */           TSConnection tsc = (TSConnection)termConns.elementAt(k);
/* 5339 */           tsc.updateConnIDCallID(newCallID);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 5345 */         conn.updateConnIDCallID(newCallID);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean getSnapshotCallConfPending()
/*      */   {
/* 5352 */     return this.snapshotCallConfPending;
/*      */   }
/*      */ 
/*      */   void setSnapshotCallConfPending(boolean flag)
/*      */   {
/* 5357 */     this.snapshotCallConfPending = flag;
/*      */   }
/*      */ 
/*      */   boolean getNeedRedoSnapshotCall()
/*      */   {
/* 5362 */     return this.needRedoSnapshotCall;
/*      */   }
/*      */ 
/*      */   void setNeedRedoSnapshotCall(boolean flag)
/*      */   {
/* 5367 */     this.needRedoSnapshotCall = flag;
/*      */   }
/*      */ 
/*      */   public short getCSTACause()
/*      */   {
/* 5374 */     return this.cstaCause;
/*      */   }
/*      */ 
/*      */   void setCSTACause(short cause)
/*      */   {
/* 5380 */     this.cstaCause = cause;
/*      */   }
/*      */ 
/*      */   public void notifyCallAdded(IDomainDevice d)
/*      */   {
/* 5397 */     recordVDNDomainEntry(d.getDomainName());
/*      */   }
/*      */ 
/*      */   public void notifyCallRemoved(IDomainDevice d)
/*      */   {
/* 5407 */     recordVDNDomainExit();
/*      */   }
/*      */ 
/*      */   public int getDomainCallID()
/*      */   {
/* 5414 */     return getCallID();
/*      */   }
/*      */ 
/*      */   void recordVDNDomainEntry(String vdn_domain_we_are_entering)
/*      */   {
/* 5423 */     log.info("recordVDNDomainEntry: -- entering VDN domain for Address " + vdn_domain_we_are_entering + " - wasEverMonitoredByCallsViaDevice=" + this.wasEverMonitoredByCallsViaDevice + " refVDN=" + this.refVDN);
/*      */ 
/* 5429 */     String found_name = null;
/*      */ 
/* 5436 */     this.wasEverMonitoredByCallsViaDevice = true;
/*      */ 
/* 5439 */     if (this.refVDN != null)
/*      */     {
/* 5441 */       found_name = this.refVDN.getName();
/* 5442 */       if (!found_name.equals(vdn_domain_we_are_entering))
/*      */       {
/* 5450 */         recordVDNDomainExit();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 5455 */     if (this.refVDN != null)
/*      */     {
/*      */       return;
/*      */     }
/*      */ 
/* 5479 */     TSDevice tsDevice = this.provider.createDevice(vdn_domain_we_are_entering);
/* 5480 */     this.refVDN = ((TsapiAddress)TsapiCreateObject.getTsapiObject(tsDevice, true));
/*      */   }
/*      */ 
/*      */   void recordVDNDomainExit()
/*      */   {
/* 5491 */     log.info("recordVDNDomainExit: -- leaving VDN domain for Address " + this.refVDN);
/*      */ 
/* 5494 */     this.refVDN = null;
/*      */   }
/*      */   public boolean hasReceivedCallClearedTransfer() {
/* 5497 */     return this.receivedCallClearedTransfer;
/*      */   }
/*      */ 
/*      */   public void setReceivedCallClearedTransfer(boolean receivedCallClearedTransfer) {
/* 5501 */     this.receivedCallClearedTransfer = receivedCallClearedTransfer;
/* 5502 */     this.callClearedTransferReceiptTime = System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */   public long getCallClearedTransferReceiptTime() {
/* 5506 */     return this.callClearedTransferReceiptTime;
/*      */   }
/*      */ 
/*      */   static enum CallUCIDStatus
/*      */   {
/* 2725 */     OK, 
/* 2726 */     UCIDMISMATCH, 
/* 2727 */     NONEXISTING;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSCall
 * JD-Core Version:    0.5.4
 */