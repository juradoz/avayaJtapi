 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.ITsapiException;
 import com.avaya.jtapi.tsapi.LookaheadInfo;
 import com.avaya.jtapi.tsapi.OriginalCallInfo;
 import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
 import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
 import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
 import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
 import com.avaya.jtapi.tsapi.TsapiPlatformException;
 import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
 import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
 import com.avaya.jtapi.tsapi.TsapiUnableToSendException;
 import com.avaya.jtapi.tsapi.UserEnteredCode;
 import com.avaya.jtapi.tsapi.UserToUserInfo;
 import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
 import com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo;
 import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTAMonitorFilter;
 import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
 import com.avaya.jtapi.tsapi.csta1.HasUCID;
 import com.avaya.jtapi.tsapi.csta1.LucentDeviceHistoryEntry;
 import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
 import com.avaya.jtapi.tsapi.csta1.LucentMakePredictiveCall;
 import com.avaya.jtapi.tsapi.csta1.LucentOriginalCallInfo;
 import com.avaya.jtapi.tsapi.csta1.LucentQueryUcid;
 import com.avaya.jtapi.tsapi.csta1.LucentQueryUcidConfEvent;
 import com.avaya.jtapi.tsapi.csta1.LucentSetBillRate;
 import com.avaya.jtapi.tsapi.csta1.LucentSingleStepConferenceCall;
 import com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCall;
 import com.avaya.jtapi.tsapi.csta1.LucentUserEnteredCode;
 import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
 import com.avaya.jtapi.tsapi.csta1.LucentV6MakePredictiveCall;
 import com.avaya.jtapi.tsapi.impl.TsapiAddress;
 import com.avaya.jtapi.tsapi.impl.TsapiCallCapabilities;
 import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
 import com.avaya.jtapi.tsapi.util.JTAPILoggingAdapter;
 import com.avaya.jtapi.tsapi.util.PerfStatisticsCollector;
 import java.util.Vector;
 import org.apache.log4j.Logger;
 
 public final class TSCall
   implements IDomainCall
 {
   private static Logger log = Logger.getLogger(TSCall.class);
   Object obsSync;
   TSProviderImpl provider;
   int state;
   int callID;
   int nonCallID = -1;
   private final Vector<TSConnection> connections;
   private final Vector<TSConnection> staleConnections;
   TSDevice internalDeviceMonitor = null;
   TSConnection confController;
   TSConnection xferController;
   boolean confEnable = true;
   boolean xferEnable = true;
   boolean delayVDNremoveCallFromDomain = false;
 
   boolean receivedCallClearedTransfer = false;
 
   long callClearedTransferReceiptTime = 0L;
   private final Vector<TsapiCallMonitor> monitorThreads;
   boolean needSnapshot = true;
 
   int monitorCrossRefID = 0;
 
   boolean wasEverMonitoredByCallsViaDevice = false;
 
   boolean monitorPending = false;
 
   Object replyPriv = null;
 
   TSDevice calledDevice = null;
 
   TSDevice callingAddress = null;
   TSDevice callingTerminal = null;
 
   TSDevice lastRedirectionDevice = null;
 
   TSDevice distributingDevice = null;
   TSDevice deliveringACDDevice = null;
 
   TSDevice distributingVDN = null;
   private final Vector<DeviceObs> deviceObsVector;
   private final Vector<TsapiCallMonitor> staleObsVector;
   UserToUserInfo uui = null;
   LookaheadInfo lai = null;
   UserEnteredCode uec = null;
   OriginalCallInfo oci = null;
   short reason = 0;
 
   String ucid = null;
   CSTACallOriginatorInfo callOriginatorInfo;
   boolean flexibleBilling;
   V7DeviceHistoryEntry[] deviceHistory = null;
   private final Vector<TSTrunk> trkVector;
   TSCall handOffCall = null;
   final Vector<CallbackAndType> callbackAndTypeVector;
   SnapshotCallConfHandler currentSnapshotHandler = null;
   SnapshotCallConfHandler futureAsynchronousSnapshotHandler = null;
   String digits;
   private short cstaCause = -1;
 
   private boolean snapshotCallConfPending = false;
 
   private boolean needRedoSnapshotCall = false;
 
   int refCount = 0;
   int connection_wait_limit;
   private TSCallObjectAge my_age = new TSCallObjectAge();
 
   TsapiAddress refVDN = null;
 
   TSCall(TSProviderImpl _provider, int _callID)
   {
     this.provider = _provider;
     this.state = 32;
     this.connections = new Vector();
     this.staleConnections = new Vector();
     this.trkVector = new Vector();
     this.monitorThreads = new Vector();
     this.deviceObsVector = new Vector();
     this.staleObsVector = new Vector();
     this.callbackAndTypeVector = new Vector();
     this.obsSync = new Object();
     setCallID(_callID);
     setConnection_wait_limit(2);
     log.info("Constructing call " + this + " with ID " + _callID + " for " + this.provider);
     if (JTAPILoggingAdapter.isPerformanceLoggingEnabled()) {
       log.debug("Updating call count in the statistics collector.");
       PerfStatisticsCollector.updateCallCount();
     }
   }
 
   void dump(String indent)
   {
     log.trace(indent + "***** CALL DUMP *****");
     log.trace(indent + "TSCall: " + this);
     log.trace(indent + "TSCall ID: " + this.callID);
     log.trace(indent + "TSCall UCID: " + this.ucid);
     log.trace(indent + "TSCall non-Call ID: " + this.nonCallID);
     log.trace(indent + "TSCall state: " + this.state);
     log.trace(indent + "TSCall needSnapshot: " + this.needSnapshot);
     log.trace(indent + "TSCall age: " + this.my_age);
     log.trace(indent + "TSCall connections: ");
     synchronized (this.connections)
     {
       for (int i = 0; i < this.connections.size(); ++i)
       {
         TSConnection conn = (TSConnection)this.connections.elementAt(i);
         conn.dump(indent + " ");
       }
     }
     log.trace(indent + "TSCall trunks: ");
     synchronized (this.trkVector)
     {
       for (int i = 0; i < this.trkVector.size(); ++i)
       {
         TSTrunk trk = (TSTrunk)this.trkVector.elementAt(i);
         trk.dump(indent + " ");
       }
     }
     log.trace(indent + "TSCall handOffCall: " + this.handOffCall);
     log.trace(indent + "TSCall stale connections: ");
     synchronized (this.staleConnections)
     {
       for (int i = 0; i < this.staleConnections.size(); ++i)
       {
         TSConnection conn = (TSConnection)this.staleConnections.elementAt(i);
         conn.dump(indent + " ");
       }
     }
     log.trace(indent + "TSCall trunks: ");
     log.trace(indent + "TSCall Monitor Threads: ");
     synchronized (this.monitorThreads)
     {
       for (int i = 0; i < this.monitorThreads.size(); ++i)
       {
         TsapiCallMonitor oThreads = (TsapiCallMonitor)this.monitorThreads.elementAt(i);
         oThreads.dump(indent + " ");
       }
     }
     log.trace(indent + "TSCall Device Monitor Threads: ");
     synchronized (this.deviceObsVector)
     {
       for (int i = 0; i < this.deviceObsVector.size(); ++i)
       {
         TsapiCallMonitor oThreads = ((DeviceObs)this.deviceObsVector.elementAt(i)).callback;
         oThreads.dump(indent + " ");
       }
     }
     log.trace(indent + "TSCall Stale Monitor Threads: ");
     synchronized (this.staleObsVector)
     {
       for (int i = 0; i < this.staleObsVector.size(); ++i)
       {
         TsapiCallMonitor oThreads = (TsapiCallMonitor)this.staleObsVector.elementAt(i);
         oThreads.dump(indent + " ");
       }
     }
     log.trace(indent + "TSCall CallbackAndType Monitor Threads: ");
     synchronized (this.callbackAndTypeVector)
     {
       CallbackAndType cbAndType = null;
       int i;
       for (i = 0; i < this.callbackAndTypeVector.size(); ++i)
       {
         cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
         TsapiCallMonitor oThreads = cbAndType.callback;
         oThreads.dump(indent + " ");
       }
 
     }
 
     int i = 0;
     for (String str : LucentUserToUserInfo.print(TsapiPromoter.demoteUserToUserInfo(getUUI()), "CallUUI", indent + " "))
     {
       if (i == 0) {
         log.trace(indent + "TSCALL UUI" + str);
       }
       else {
         log.trace(str);
       }
       ++i;
     }
     log.trace(indent + "***** CALL DUMP END *****");
   }
 
   public TSProviderImpl getTSProviderImpl()
   {
     return this.provider;
   }
 
   public int getState()
   {
     updateObject();
 
     return this.state;
   }
 
   int getTSState()
   {
     return this.state;
   }
 
   int getStateFromServer()
   {
     if (!updateSuspiciousObject()) {
       throw new TsapiPlatformException(4, 0, "Could not get state from the telephony server. [CallId=" + getCallID() + "]");
     }
     return this.state;
   }
 
   public Object getPrivateData()
   {
     if (this.replyPriv instanceof CSTAPrivate)
       return this.replyPriv;
     return null;
   }
 
   public Object sendPrivateData(CSTAPrivate data)
   {
     try
     {
       return this.provider.sendPrivateData(data);
     }
     catch (Exception e)
     {
       throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
     }
   }
 
   public Vector<TSConnection> getTSConnections()
   {
     updateObject();
 
     Vector tsConnections = new Vector();
 
     synchronized (this.connections)
     {
       for (int i = 0; i < this.connections.size(); ++i)
       {
         TSConnection tsConn = (TSConnection)this.connections.elementAt(i);
         if ((tsConn.getTSDevice().getDeviceType() == 2) && (tsConn.getACDManagerConn() != null)) {
           continue;
         }
         tsConnections.addElement(tsConn);
       }
 
     }
 
     return tsConnections;
   }
 
   Vector<TSConnection> getConnections()
   {
     return this.connections;
   }
 
   public Vector<TSConnection> connect(TSDevice device, String destAddress, CSTAPrivate reqPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getMakeCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     if (this.state != 32)
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not idle");
     }
 
     String devName = device.getName();
 
     if ((((this.provider.getCapabilities().getSnapshotCallReq() == 0) || (this.monitorPending))) && (this.internalDeviceMonitor == null))
     {
       try
       {
         this.internalDeviceMonitor = device.setInternalMonitor(this);
       }
       catch (TsapiUnableToSendException tue)
       {
         throw tue;
       }
       catch (Exception e) {
         log.error(e.getMessage(), e);
       }
     }
 
     MakeCallConfHandler handler = new MakeCallConfHandler(this, device, destAddress, 24);
     try
     {
       if (this.provider.getDeviceExt(destAddress) == 1)
       {
         recordVDNDomainEntry(destAddress);
 
         TSDevice tsDevice = this.provider.createDevice(destAddress);
         tsDevice.setMonitor(false, true);
       }
       this.provider.tsapi.makeCall(devName, destAddress, reqPriv, handler);
       log.info("TSCall.connect: finished makeCall for Call ID " + this.callID);
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiInvalidPartyException e)
     {
       throw e;
     }
     catch (TsapiInvalidArgumentException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "makeCall failure");
       }
       throw new TsapiPlatformException(4, 0, "makeCall failure");
     }
 
     log.info("TSCall.connect: about to call connectFinish() for callID " + this.callID);
     if (this.handOffCall != null)
     {
       return this.handOffCall.connectFinish(device, destAddress, handler.newCall);
     }
 
     return connectFinish(device, destAddress, handler.newCall);
   }
 
   Vector<TSConnection> connectFinish(TSDevice device, String destAddress, CSTAConnectionID newCall)
   {
     if (device.isMonitorSet())
     {
       this.needSnapshot = false;
     }
     else
     {
       log.info("TSCall.connect: calling doSnapshot() for callID " + this.callID);
 
       doSnapshot(newCall, null, false);
     }
 
     log.info("TSCall.connect: about to wait for 2 connections for callID " + this.callID);
 
     synchronized (this.connections)
     {
       if (this.connections.size() < 2)
       {
         setConnection_wait_limit(2);
         if ((!waitForConnections("connect", 2)) && (this.state != 34))
         {
           log.info("failed to get 2 connections for call ID " + this.callID);
 
           throw new TsapiPlatformException(4, 0, "Could not meet post-conditions of connect()");
         }
       }
     }
 
     return this.connections;
   }
 
   private LucentMakePredictiveCall createLucentMakePredictiveCall(boolean priorityCall, int maxRings, short answerTreat, String destRoute, UserToUserInfo userInfo)
   {
     LucentUserToUserInfo asn_uui = TsapiPromoter.demoteUserToUserInfo(userInfo);
 
     if (this.provider.isLucentV6()) {
       return new LucentV6MakePredictiveCall(priorityCall, maxRings, answerTreat, destRoute, asn_uui);
     }
     return new LucentMakePredictiveCall(priorityCall, maxRings, answerTreat, destRoute, asn_uui);
   }
 
   public Vector<TSConnection> connectPredictive(TSDevice device, String dialedDigits, int connectionState, int maxRings, int answeringTreatment, int answeringEndpointType, String destRoute, boolean priorityCall, UserToUserInfo userInfo, CSTAPrivate reqPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getMakePredictiveCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     if (this.state != 32)
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not idle");
     }
 
     String devName = device.getName();
     short allocState;
     switch (connectionState)
     {
     case 50:
       allocState = 0;
       break;
     case 51:
       allocState = 1;
       break;
     default:
       throw new TsapiInvalidArgumentException(0, 0, "invalid connectionState");
     }
     if (this.provider.isLucent())
     {
       short answerTreat;
       switch (answeringTreatment)
       {
       case 1:
         answerTreat = 1;
         break;
       case 2:
         answerTreat = 2;
         break;
       case 3:
         answerTreat = 3;
         break;
       case 4:
         answerTreat = 0;
         break;
       default:
         throw new TsapiInvalidArgumentException(0, 0, "invalid answeringTreatment");
       }
       LucentMakePredictiveCall lmc = createLucentMakePredictiveCall(priorityCall, maxRings, answerTreat, destRoute, userInfo);
 
       reqPriv = lmc.makeTsapiPrivate();
     }
 
     MakeCallConfHandler handler = new MakeCallConfHandler(this, device, dialedDigits, 26);
     try
     {
       this.provider.tsapi.makePredictiveCall(devName, dialedDigits, allocState, reqPriv, handler);
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiInvalidPartyException e)
     {
       throw e;
     }
     catch (TsapiInvalidArgumentException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "makePredictiveCall failure");
       }
       throw new TsapiPlatformException(4, 0, "makePredictiveCall failure");
     }
 
     boolean hasPredictiveCallMonitor = device.isPredictiveCallsViaDeviceMonitorSet();
 
     if (this.handOffCall != null)
     {
       return this.handOffCall.connectPredictiveFinish(handler.newCall, hasPredictiveCallMonitor);
     }
 
     return connectPredictiveFinish(handler.newCall, hasPredictiveCallMonitor);
   }
 
   boolean waitForConnections(String forWhom, int requiredMinNumberOfConnections)
   {
     if (this.connections.size() >= requiredMinNumberOfConnections)
     {
       log.info("waitForConnections: called for " + forWhom + ": had right# of connections on entry, for call ID " + this.callID);
 
       return true;
     }
 
     if (this.state == 34)
     {
       log.info("waitForConnections: called for " + forWhom + ": call was INVALID on entry, for call ID " + this.callID);
 
       return false;
     }
 
     long startTime = System.currentTimeMillis();
     long currentTime = startTime;
 
     long endTime = currentTime + Tsapi.getCallCompletionTimeout();
     while (true)
     {
       try
       {
         this.connections.wait(endTime - currentTime);
       }
       catch (InterruptedException e)
       {
       }
       currentTime = System.currentTimeMillis();
 
       if (this.connections.size() >= requiredMinNumberOfConnections)
       {
         log.info("waitForConnections: waiting for " + forWhom + ": succeeded after waiting a total of " + (currentTime - startTime) / 1000L + " seconds, to get " + requiredMinNumberOfConnections + " connections for call ID " + this.callID);
 
         return true;
       }
 
       if (currentTime >= endTime)
       {
         log.info("waitForConnections: waited for " + forWhom + ": Failed.  After waiting a total of " + (currentTime - startTime) / 1000L + " seconds, did not get " + requiredMinNumberOfConnections + " connections for call ID " + this.callID);
 
         return false;
       }
 
       if (this.state == 34)
       {
         log.info("waitForConnections: waited for " + forWhom + ": Failed.  After waiting a total of " + (currentTime - startTime) / 1000L + " seconds, the call went INVALID (ended) - call ID " + this.callID);
 
         return false;
       }
 
       log.info("waitForConnections: waiting for " + forWhom + ": woke up after waiting a total of " + (currentTime - startTime) / 1000L + " seconds, and failed to see " + requiredMinNumberOfConnections + " connections for call ID " + this.callID + " - #connections=" + this.connections.size() + " - waiting again");
     }
   }
 
   Vector<TSConnection> connectPredictiveFinish(CSTAConnectionID connID, boolean hasPredictiveCallMonitor)
   {
     if (!hasPredictiveCallMonitor)
     {
       try
       {
         monitorCall(connID, true);
       }
       catch (TsapiUnableToSendException tue)
       {
         throw tue;
       }
       catch (Exception e) {
         log.error(e.getMessage(), e);
       }
 
     }
 
     synchronized (this.connections)
     {
       if ((this.connections.size() < 2) && 
         (!waitForConnections("connectPredictive", 2)) && (this.state != 34))
       {
         log.error("failed to get 2 connections for call ID " + this.callID);
 
         throw new TsapiPlatformException(4, 0, "Could not meet post-conditions of connectPredictive()");
       }
     }
 
     return this.connections;
   }
 
   public void addCallMonitor(TsapiCallMonitor obs)
     throws TsapiResourceUnavailableException
   {
     if (this.provider.getCapabilities().getMonitorCall() == 0)
     {
       throw new TsapiResourceUnavailableException(0, 0, 0, "unsupported by driver");
     }
 
     if (!this.provider.allowCallMonitoring())
     {
       throw new TsapiResourceUnavailableException(0, 0, 0, "no permissions");
     }
 
     switch (this.state)
     {
     case 32:
       if (this.monitorThreads.contains(obs))
       {
         return;
       }
 
       synchronized (this)
       {
         this.monitorPending = true;
         this.callbackAndTypeVector.addElement(new CallbackAndType(obs, null));
       }
       break;
     case 34:
       if (this.monitorThreads.contains(obs))
       {
         return;
       }
 
       obs.addReference();
       obs.deleteReference(this, false, 100, null);
       break;
     default:
       synchronized (this.obsSync)
       {
         if (this.monitorThreads.contains(obs))
         {
           return;
         }
         int i = 0;
         synchronized (this.deviceObsVector)
         {
           for (i = 0; i < this.deviceObsVector.size(); ++i)
           {
             if (((DeviceObs)this.deviceObsVector.elementAt(i)).callback != obs)
               continue;
             i = 1;
 
             this.monitorThreads.addElement(((DeviceObs)this.deviceObsVector.elementAt(i)).callback);
             this.deviceObsVector.removeElementAt(i);
 
             break;
           }
 
         }
 
         if (i != 0)
         {
           setMonitor(true);
           return;
         }
 
         if (this.staleObsVector.removeElement(obs))
         {
           setMonitor(true);
 
           this.monitorThreads.addElement(obs);
 
           return;
         }
 
         setMonitor(true);
 
         this.monitorThreads.addElement(obs);
       }
 
       obs.addReference();
 
       sendSnapshot(obs, null, true);
     }
   }
 
   public void removeCallMonitor(TsapiCallMonitor obs)
   {
     removeCallMonitor(obs, 100, null);
   }
 
   protected void removeCallMonitor(TsapiCallMonitor obs, int cause, Object privateData)
   {
     CallbackAndType cbAndType = null;
     synchronized (this.callbackAndTypeVector)
     {
       for (int i = 0; i < this.callbackAndTypeVector.size(); ++i)
       {
         cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
         if (cbAndType.callback != obs)
           continue;
         if (cbAndType.devWithType == null)
         {
           synchronized (this.monitorThreads)
           {
             if (!this.monitorThreads.contains(cbAndType.callback))
             {
               this.monitorThreads.addElement(cbAndType.callback);
               cbAndType.callback.addReference();
             }
           }
         }
         else
         {
           boolean found = false;
           DeviceObs devObs = null;
           synchronized (this.deviceObsVector)
           {
             for (int j = 0; j < this.deviceObsVector.size(); ++j)
             {
               devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
               if (devObs.callback != cbAndType.callback)
                 continue;
               found = true;
               break;
             }
 
             if (!found)
             {
               devObs = new DeviceObs(cbAndType.callback);
               devObs.devWithTypeVector.addElement(cbAndType.devWithType);
               this.deviceObsVector.addElement(devObs);
               cbAndType.callback.addReference();
             }
           }
         }
         this.callbackAndTypeVector.removeElement(cbAndType);
       }
 
     }
 
     synchronized (this.monitorThreads)
     {
       if (this.monitorThreads.removeElement(obs))
       {
         obs.deleteReference(this, false, cause, privateData);
         if ((this.monitorThreads.isEmpty()) && 
           (this.monitorCrossRefID != 0))
         {
           this.provider.deleteMonitor(this.monitorCrossRefID);
           if (this.provider.getCapabilities().getMonitorStop() != 0)
           {
             try
             {
               this.provider.tsapi.monitorStop(this.monitorCrossRefID, null, null);
             }
             catch (TsapiUnableToSendException tue)
             {
               throw tue;
             }
             catch (Exception e) {
               log.error(e.getMessage(), e);
             }
 
           }
 
           this.monitorCrossRefID = 0;
 
           if (!checkForMonitors())
           {
             setNeedSnapshot(true);
           }
         }
 
       }
       else
       {
         synchronized (this.deviceObsVector)
         {
           for (int i = 0; i < this.deviceObsVector.size(); ++i)
           {
             if (((DeviceObs)this.deviceObsVector.elementAt(i)).callback != obs)
               continue;
             this.deviceObsVector.removeElementAt(i);
             obs.deleteReference(this, false, cause, privateData);
             return;
           }
 
         }
 
         if (this.staleObsVector.removeElement(obs))
         {
           obs.deleteReference(this, false, cause, privateData);
           return;
         }
       }
     }
   }
 
   public TsapiCallCapabilities getTsapiCallCapabilities()
   {
     return this.provider.getTsapiCallCapabilities();
   }
 
   public TSDevice getCalledDevice()
   {
     return this.calledDevice;
   }
 
   public TSDevice getCallingAddress()
   {
     return this.callingAddress;
   }
 
   public TSDevice getCallingTerminal()
   {
     return this.callingTerminal;
   }
 
   public TSDevice getLastRedirectionDevice()
   {
     return this.lastRedirectionDevice;
   }
 
   public void drop(CSTAPrivate reqPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getClearCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if ((updateObject()) && 
       (this.state != 33))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
     }
 
     CSTAConnectionID clearConnID = null;
     synchronized (this.connections)
     {
       if (this.connections.size() > 0)
       {
         clearConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
       }
     }
     if (clearConnID == null)
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active (no connections)");
     }
 
     ConfHandler handler = new ClearCallConfHandler(this);
     try
     {
       this.provider.tsapi.clearCall(clearConnID, reqPriv, handler);
     }
     catch (TsapiInvalidStateException e)
     {
       setState(34, null);
       endCVDObservers(100, null);
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "clearCall failure");
       }
       throw new TsapiPlatformException(4, 0, "clearCall failure");
     }
   }
 
   public void conference(TSCall otherCall, CSTAPrivate reqPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getConferenceCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     boolean thisCallUpdate = updateObject();
     boolean otherCallUpdate = otherCall.updateObject();
 
     if ((thisCallUpdate) && (this.state != 33))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
     }
 
     if ((!this.confEnable) || (!otherCall.confEnable))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "conferencing disabled");
     }
 
     TSConnection activeTermConn = this.confController;
     TSConnection heldTermConn = otherCall.confController;
     if (activeTermConn == null)
     {
       if (heldTermConn == null)
       {
         TSConnection conn = null;
         TSConnection termConn = null;
         boolean found = false;
         synchronized (this.connections)
         {
           for (int i = 0; i < this.connections.size(); ++i)
           {
             conn = (TSConnection)this.connections.elementAt(i);
             Vector termConns = conn.getTermConns();
             if (termConns == null)
               continue;
             Vector tcs = new Vector(termConns);
             for (int j = 0; j < tcs.size(); ++j)
             {
               termConn = (TSConnection)tcs.elementAt(j);
               if ((termConn.getCallControlTermConnState() != 98) && (termConn.getCallControlTermConnState() != 103)) {
                 continue;
               }
               activeTermConn = termConn;
 
               heldTermConn = otherCall.findHeldTermConnection(activeTermConn.getTSDevice());
               if (heldTermConn == null) {
                 continue;
               }
               found = true;
               break;
             }
 
             if (found) {
               break;
             }
           }
         }
         if (activeTermConn == null)
         {
           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no active terminal connection found");
         }
 
         if (heldTermConn == null)
         {
           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no held terminal connection found");
         }
 
       }
       else
       {
         if ((otherCallUpdate) && (heldTermConn.getCallControlTermConnState() != 99) && (heldTermConn.getCallControlTermConnState() != 103))
         {
           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection not held");
         }
 
         activeTermConn = findActiveTermConnection(heldTermConn.getTSDevice());
         if (activeTermConn == null)
         {
           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no active terminal connection found");
         }
 
       }
 
     }
     else if (heldTermConn == null)
     {
       if ((thisCallUpdate) && (activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "Terminal connection not active. It's state is " + activeTermConn.getCallControlTermConnState());
       }
 
       heldTermConn = otherCall.findHeldTermConnection(activeTermConn.getTSDevice());
       if (heldTermConn == null)
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no held terminal connection found");
       }
 
     }
     else if ((thisCallUpdate) && (otherCallUpdate))
     {
       if ((activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the state of the active terminal connection is not TALKING or UNKNOWN; its state is " + activeTermConn.getCallControlTermConnState());
       }
 
       if ((heldTermConn.getCallControlTermConnState() != 99) && (heldTermConn.getCallControlTermConnState() != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the state of the held terminal connection is not HELD or UNKNOWN; its state is " + heldTermConn.getCallControlTermConnState());
       }
 
       if (!activeTermConn.getTSDevice().getTermConns().contains(heldTermConn))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the held terminal connection is not associated with the device (" + activeTermConn.getTSDevice() + ") of the active terminal connection");
       }
 
     }
 
     ConfHandler handler = new ConfXferConfHandler(this, otherCall, 12);
     try
     {
       this.provider.tsapi.conferenceCall(heldTermConn.getConnID(), activeTermConn.getConnID(), reqPriv, handler);
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiInvalidArgumentException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "conferenceCall failure");
       }
       throw new TsapiPlatformException(4, 0, "conferenceCall failure");
     }
   }
 
   private CSTAConnectionID selectConnectionIdForAddParty()
   {
     synchronized (this.connections)
     {
       for (int i = 0; i < this.connections.size(); ++i)
       {
         CSTAConnectionID connID = ((TSConnection)this.connections.elementAt(i)).getConnID();
 
         if (connID == null)
         {
           continue;
         }
 
         if (connID.getDevIDType() == 0)
         {
           return connID;
         }
 
         if (connID.getDevIDType() != 1)
         {
           continue;
         }
 
         String deviceID = connID.getDeviceID();
         if (deviceID.regionMatches(true, 0, "T", 0, 1))
         {
           return connID;
         }
 
       }
 
       if (this.connections.size() > 0)
       {
         return ((TSConnection)this.connections.elementAt(0)).getConnID();
       }
 
       return null;
     }
   }
 
   public TSConnection addParty(String newParty, boolean active)
     throws TsapiInvalidStateException, TsapiInvalidPartyException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException
   {
     if (!this.provider.isLucentV5()) {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     if ((updateObject()) && 
       (this.state != 33))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
     }
 
     CSTAConnectionID connID = selectConnectionIdForAddParty();
 
     if (connID == null)
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active (no connections)");
     }
 
     LucentSingleStepConferenceCall ssc = new LucentSingleStepConferenceCall(connID, newParty, (short) ((active) ? 1 : 0), false);
 
     ConfHandler handler = new ConfXferConfHandler(this, null, 90);
     try
     {
       this.provider.sendPrivateData(ssc.makeTsapiPrivate(), handler);
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiInvalidPartyException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException)
       {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "addParty failure, exception: " + e);
       }
 
       throw new TsapiPlatformException(3, 0, "addParty failure, exception: " + e);
     }
 
     return ((ConfXferConfHandler)handler).newConn;
   }
 
   public void transfer(TSCall otherCall, CSTAPrivate reqPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getTransferCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     boolean thisCallUpdate = updateObject();
     boolean otherCallUpdate = otherCall.updateObject();
 
     if ((thisCallUpdate) && (this.state != 33))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not active");
     }
 
     if ((!this.xferEnable) || (!otherCall.xferEnable))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "transfer disabled");
     }
 
     TSConnection activeTermConn = this.xferController;
     TSConnection heldTermConn = otherCall.xferController;
     if (activeTermConn == null)
     {
       if (heldTermConn == null)
       {
         TSConnection conn = null;
         TSConnection termConn = null;
         boolean found = false;
         synchronized (this.connections)
         {
           for (int i = 0; i < this.connections.size(); ++i)
           {
             conn = (TSConnection)this.connections.elementAt(i);
             Vector termConns = conn.getTermConns();
             if (termConns == null)
               continue;
             Vector tcs = new Vector(termConns);
             for (int j = 0; j < tcs.size(); ++j)
             {
               termConn = (TSConnection)tcs.elementAt(j);
               if ((termConn.getCallControlTermConnState() != 98) && (termConn.getCallControlTermConnState() != 103)) {
                 continue;
               }
               activeTermConn = termConn;
 
               heldTermConn = otherCall.findHeldTermConnection(activeTermConn.getTSDevice());
               if (heldTermConn == null) {
                 continue;
               }
               found = true;
               break;
             }
 
             if (found) {
               break;
             }
           }
         }
         if (activeTermConn == null)
         {
           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no active terminal connection found");
         }
 
         if (heldTermConn == null)
         {
           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no held terminal connection found");
         }
 
       }
       else
       {
         if ((otherCallUpdate) && (heldTermConn.getCallControlTermConnState() != 99) && (heldTermConn.getCallControlTermConnState() != 103))
         {
           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection not held");
         }
 
         activeTermConn = findActiveTermConnection(heldTermConn.getTSDevice());
         if (activeTermConn == null)
         {
           throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no active terminal connection found");
         }
 
       }
 
     }
     else if (heldTermConn == null)
     {
       if ((thisCallUpdate) && (activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection not active. It's state is " + activeTermConn.getCallControlTermConnState());
       }
 
       heldTermConn = otherCall.findHeldTermConnection(activeTermConn.getTSDevice());
       if (heldTermConn == null)
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "no held terminal connection found");
       }
 
     }
     else if ((thisCallUpdate) && (otherCallUpdate))
     {
       if ((activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the state of the active terminal connection is not TALKING or UNKNOWN; its state is " + activeTermConn.getCallControlTermConnState());
       }
 
       if ((heldTermConn.getCallControlTermConnState() != 99) && (heldTermConn.getCallControlTermConnState() != 103))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the state of the held terminal connection is not HELD or UNKNOWN; its state is " + heldTermConn.getCallControlTermConnState());
       }
 
       if (!activeTermConn.getTSDevice().getTermConns().contains(heldTermConn))
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "the held terminal connection is not associated with the device (" + activeTermConn.getTSDevice() + ") of the active terminal connection");
       }
 
     }
 
     ConfHandler handler = new ConfXferConfHandler(this, otherCall, 52);
     try
     {
       this.provider.tsapi.transferCall(heldTermConn.getConnID(), activeTermConn.getConnID(), reqPriv, handler);
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiInvalidPartyException e)
     {
       throw e;
     }
     catch (TsapiInvalidArgumentException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "transferCall failure");
       }
       throw new TsapiPlatformException(4, 0, "transferCall failure");
     }
   }
 
   public TSConnection transfer(String xferDestAddress, CSTAPrivate reqPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getTransferCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "transfer unsupported by driver");
     }
 
     if (!this.provider.getTsapiCallCapabilities().canTransfer((String)null))
     {
       throw new TsapiMethodNotSupportedException(4, 0, "transfer(String) unsupported by driver");
     }
 
     boolean thisCallUpdate = updateObject();
 
     if ((thisCallUpdate) && (this.state != 33))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not active");
     }
 
     if (!this.xferEnable)
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "transfer disabled");
     }
 
     TSConnection activeTermConn = this.xferController;
 
     if (activeTermConn == null)
     {
       throw new TsapiInvalidArgumentException(3, 0, "transfer(String) with null TransferController not supported");
     }
 
     if ((thisCallUpdate) && (activeTermConn.getCallControlTermConnState() != 98) && (activeTermConn.getCallControlTermConnState() != 103))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 2, this.state, "transfer controller terminal connection not in valid state (TALKING or UNKNOWN)");
     }
 
     setConnection_wait_limit(1);
 
     LucentSingleStepTransferCall sst = new LucentSingleStepTransferCall(activeTermConn.getConnID(), xferDestAddress);
 
     ConfXferConfHandler handler = new ConfXferConfHandler(this, null, 90);
     try
     {
       this.provider.sendPrivateData(sst.makeTsapiPrivate(), handler);
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiInvalidPartyException e)
     {
       throw e;
     }
     catch (TsapiInvalidArgumentException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "transfer(String) failure");
       }
       throw new TsapiPlatformException(4, 0, "transfer(String) failure: " + e);
     }
 
     return this.provider.getConnection(handler.newCall);
   }
 
   public Vector<TSConnection> consult(TSConnection termconn, String address, CSTAPrivate reqPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getConsultationCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     if (this.state != 32)
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not idle");
     }
 
     boolean otherCallUpdate = termconn.getTSCall().updateObject();
     if ((otherCallUpdate) && (termconn.getTSCall().state != 33))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(termconn.getTSCall(), false), 1, this.state, "other call not active");
     }
 
     if ((otherCallUpdate) && (termconn.getCallControlTermConnState() != 98) && (termconn.getCallControlTermConnState() != 103))
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(termconn, false), 5, this.state, "terminal connection not talking");
     }
 
     if ((((this.provider.getCapabilities().getSnapshotCallReq() == 0) || (this.monitorPending))) && (this.internalDeviceMonitor == null))
     {
       try
       {
         this.internalDeviceMonitor = termconn.getTSDevice().setInternalMonitor(this);
       }
       catch (TsapiUnableToSendException tue)
       {
         throw tue;
       }
       catch (Exception e) {
         log.error(e.getMessage(), e);
       }
 
     }
 
     MakeCallConfHandler handler = new MakeCallConfHandler(this, termconn.getTSDevice(), address, 14);
     try
     {
       this.provider.tsapi.consultationCall(termconn.getConnID(), address, reqPriv, handler);
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiInvalidArgumentException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException) {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "consultationCall failure");
       }
       throw new TsapiPlatformException(4, 0, "consultationCall failure");
     }
 
     if (this.handOffCall != null)
     {
       return this.handOffCall.consultFinish(termconn, address, handler.newCall);
     }
 
     return consultFinish(termconn, address, handler.newCall);
   }
 
   Vector<TSConnection> consultFinish(TSConnection termconn, String address, CSTAConnectionID newCall)
   {
     Vector eventList = new Vector();
     termconn.setTermConnState(99, eventList);
     if (eventList.size() > 0)
     {
       Vector observers = termconn.getTSCall().getObservers();
       for (int j = 0; j < observers.size(); ++j)
       {
         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
         callback.deliverEvents(eventList, 100, false);
       }
 
     }
 
     if (termconn.getTSDevice().isMonitorSet())
     {
       this.needSnapshot = false;
     }
     else
     {
       doSnapshot(newCall, null, false);
     }
 
     synchronized (this.connections)
     {
       if (this.connections.size() < 2)
       {
         try
         {
           this.connections.wait(Tsapi.getCallCompletionTimeout());
         } catch (InterruptedException e) {
         }
         if ((this.connections.size() < 2) && (this.state != 34))
         {
           log.error("failed to get 2 connections for call ID " + this.callID);
 
           throw new TsapiPlatformException(4, 0, "Could not meet post-conditions of consult()");
         }
       }
     }
     return this.connections;
   }
 
   public void setConfController(TSConnection termconn)
     throws TsapiInvalidArgumentException, TsapiInvalidStateException
   {
     if (updateObject())
     {
       if (this.state != 33)
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
       }
 
       if (termconn.getCallControlTermConnState() == 102)
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(termconn, false), 5, this.state, "terminal connection is dropped");
       }
 
       boolean contains = false;
 
       synchronized (this.connections)
       {
         for (int i = 0; i < this.connections.size(); ++i)
         {
           TSConnection conn = (TSConnection)this.connections.elementAt(i);
           Vector termConns = conn.getTermConns();
           if ((termConns == null) || (!termConns.contains(termconn)))
             continue;
           contains = true;
           break;
         }
       }
 
       if (!contains)
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection is not in this call");
       }
 
     }
 
     this.confController = termconn;
   }
 
   public TSConnection getConfController()
   {
     return this.confController;
   }
 
   public void setXferController(TSConnection termconn)
     throws TsapiInvalidArgumentException, TsapiInvalidStateException
   {
     if (updateObject())
     {
       if (this.state != 33)
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call is not active");
       }
 
       if (termconn.getCallControlTermConnState() == 102)
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(termconn, false), 5, 102, "terminal connection is dropped");
       }
 
       boolean contains = false;
 
       synchronized (this.connections)
       {
         for (int i = 0; i < this.connections.size(); ++i)
         {
           TSConnection conn = (TSConnection)this.connections.elementAt(i);
           Vector termConns = conn.getTermConns();
           if ((termConns == null) || (!termConns.contains(termconn)))
             continue;
           contains = true;
           break;
         }
       }
 
       if (!contains)
       {
         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "terminal connection is not in this call");
       }
 
     }
 
     this.xferController = termconn;
   }
 
   public TSConnection getXferController()
   {
     return this.xferController;
   }
 
   public void setConfEnable(boolean enable)
   {
     this.confEnable = enable;
   }
 
   public boolean getConfEnable()
   {
     return this.confEnable;
   }
 
   public void setXferEnable(boolean enable)
   {
     this.xferEnable = enable;
   }
 
   public boolean getXferEnable()
   {
     return this.xferEnable;
   }
 
   public Vector<TSTrunk> getTSTrunks()
   {
     return this.trkVector;
   }
 
   public UserToUserInfo getUUI()
   {
     return this.uui;
   }
 
   public LookaheadInfo getLAI()
   {
     return this.lai;
   }
 
   public UserEnteredCode getUEC()
   {
     return this.uec;
   }
 
   public OriginalCallInfo getOCI()
   {
     return this.oci;
   }
 
   public short getReason()
   {
     return this.reason;
   }
 
   public String getUCID()
   {
     if (this.ucid == null)
     {
       setUCID(queryUCID());
     }
 
     return this.ucid;
   }
 
   public LucentQueryUcidConfEvent getQueryUCIDConf()
   {
     Object lquConf = null;
     try
     {
       LucentQueryUcid lqu = new LucentQueryUcid(new CSTAConnectionID(this.callID, "", (short) 0));
       lquConf = this.provider.sendPrivateData(lqu.makeTsapiPrivate());
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException)
       {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "getUCIDConf failure, exception: " + e);
       }
 
       throw new TsapiPlatformException(4, 0, "getUCIDConf failure, exception: " + e);
     }
 
     return (LucentQueryUcidConfEvent)lquConf;
   }
 
   String queryUCID()
   {
     if (!this.provider.isLucentV5()) {
       return null;
     }
     LucentQueryUcidConfEvent lquConf = getQueryUCIDConf();
 
     if (lquConf != null) {
       return lquConf.getUcid();
     }
     return null;
   }
 
   public int getCallOriginatorType()
   {
     if (hasCallOriginatorType()) {
       return this.callOriginatorInfo.getCallOriginatorType();
     }
     return -1;
   }
 
   public boolean hasCallOriginatorType()
   {
     return this.callOriginatorInfo != null;
   }
 
   public boolean canSetBillRate()
   {
     return this.flexibleBilling;
   }
 
   public void setBillRate(short billType, float billRate)
     throws TsapiInvalidArgumentException, TsapiMethodNotSupportedException, TsapiResourceUnavailableException
   {
     if (!this.provider.isLucentV5())
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
     switch (billType)
     {
     case 16:
     case 17:
     case 18:
     case 19:
     case 24:
       break;
     case 20:
     case 21:
     case 22:
     case 23:
     default:
       throw new TsapiInvalidArgumentException(0, 0, "invalid billType");
     }
     try
     {
       CSTAConnectionID connID = new CSTAConnectionID(this.callID, "", (short) 0);
 
       LucentSetBillRate sbr = new LucentSetBillRate(connID, billType, billRate);
       this.provider.sendPrivateData(sbr.makeTsapiPrivate());
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException)
       {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setBillRate failure, exception: " + e);
       }
 
       throw new TsapiPlatformException(4, 0, "setBillRate failure, exception: " + e);
     }
   }
 
   public TSDevice getDistributingDevice()
   {
     return this.distributingDevice;
   }
 
   public TSDevice getDistributingVDN()
   {
     return this.distributingVDN;
   }
 
   public TSDevice getDeliveringACDDevice()
   {
     return this.deliveringACDDevice;
   }
 
   void setDistributingDevice(TSDevice _distributingDevice)
   {
     if (_distributingDevice == null)
       return;
     this.distributingDevice = _distributingDevice;
   }
 
   void setDistributingVDN(TSDevice _distributingVDN)
   {
     if (_distributingVDN == null)
       return;
     this.distributingVDN = _distributingVDN;
   }
 
   void setDeliveringACDDevice(TSDevice _deliveringACDDevice)
   {
     if (_deliveringACDDevice == null)
       return;
     this.deliveringACDDevice = _deliveringACDDevice;
   }
 
   void setCalledDevice(TSDevice _calledDevice)
   {
     if (_calledDevice == null)
       return;
     this.calledDevice = _calledDevice;
   }
 
   void setCallingDevices(TSDevice _callingDevice)
   {
     if (_callingDevice == null)
       return;
     if (_callingDevice.isTerminal())
     {
       this.callingTerminal = _callingDevice;
     }
     else
     {
       this.callingTerminal = null;
     }
     this.callingAddress = _callingDevice;
   }
 
   void setLastRedirectionDevice(TSDevice _lastRedirectionDevice)
   {
     if (_lastRedirectionDevice == null)
       return;
     this.lastRedirectionDevice = _lastRedirectionDevice;
   }
 
   void setUUI(LucentUserToUserInfo _uui)
   {
     if (_uui == null)
       return;
     this.uui = TsapiPromoter.promoteUserToUserInfo(_uui);
   }
 
   void setLAI(LucentLookaheadInfo _lai)
   {
     if (_lai == null)
       return;
     this.lai = TsapiPromoter.promoteLookaheadInfo(_lai);
   }
 
   void setUEC(LucentUserEnteredCode _uec)
   {
     if (_uec == null)
       return;
     this.uec = TsapiPromoter.promoteUserEnteredCode(this.provider, _uec);
   }
 
   void setOCI(LucentOriginalCallInfo _oci)
   {
     if (_oci == null)
       return;
     this.oci = TsapiPromoter.promoteOriginalCallInfo(this.provider, _oci);
   }
 
   void setUUI(UserToUserInfo _uui)
   {
     if (_uui == null)
       return;
     this.uui = _uui;
   }
 
   void setLAI(LookaheadInfo _lai)
   {
     if (_lai == null)
       return;
     this.lai = _lai;
   }
 
   void setUEC(UserEnteredCode _uec)
   {
     if (_uec == null)
       return;
     this.uec = _uec;
   }
 
   void setOCI(OriginalCallInfo _oci)
   {
     if (_oci == null)
       return;
     this.oci = _oci;
   }
 
   void setReason(short _reason)
   {
     this.reason = _reason;
   }
 
   void setUCID(String _ucid)
   {
     if (_ucid == null)
       return;
     this.ucid = _ucid;
   }
 
   void setCallOriginatorInfo(CSTACallOriginatorInfo _callOriginatorInfo)
   {
     this.callOriginatorInfo = _callOriginatorInfo;
   }
 
   void setFlexibleBilling(boolean _flexibleBilling)
   {
     this.flexibleBilling = _flexibleBilling;
   }
 
   public void addTrunk(TSTrunk trunk, Vector<TSEvent> eventList)
   {
     synchronized (this.trkVector)
     {
       if (this.trkVector.contains(trunk))
       {
         return;
       }
 
       if (trunk.setCall(this, eventList))
         this.trkVector.addElement(trunk);
     }
   }
 
   void removeTrunk(TSTrunk trunk, Vector<TSEvent> eventList)
   {
     if (!this.trkVector.removeElement(trunk))
       return;
     trunk.unsetCall(eventList);
   }
 
   boolean cleanUCIDInCall()
   {
     CallUCIDStatus callsttype = CallUCIDStatus.OK;
     try
     {
       Object ucidConf = getQueryUCIDConf();
       log.debug("UCID obtained from the switch is " + ((HasUCID)ucidConf).getUcid());
 
       if ((getUCID() != null) && (!getUCID().contentEquals(((HasUCID)ucidConf).getUcid())))
       {
         callsttype = CallUCIDStatus.UCIDMISMATCH;
         log.info("ERROR: mismatched ucid, for call: " + this + " - setting call state to INVALID.");
       }
     }
     catch (TsapiPlatformException e)
     {
       int i;
       if ((e.getErrorType() == 2) && (e.getErrorCode() == 24))
       {
         callsttype = CallUCIDStatus.NONEXISTING;
         log.info("ERROR: Attempted cleanUCIDInCall() but no active call: " + this + " - setting call state to INVALID.");
       }
       else
       {
         callsttype = CallUCIDStatus.OK;
         log.info("ERROR: Saw & ignored exception (TsapiPlatformException)  for cleanUCIDsInCallsInConnections(), for call " + this + " - Perhaps UCID queries are disabled on the switch. " + e);
       }
     }
     catch (Exception e)
     {
       int i;
       callsttype = CallUCIDStatus.OK;
       log.info("ERROR: Saw & ignored unexpected exception  for cleanUCIDsInCallsInConnections(), for call " + this + " - Perhaps UCID queries are disabled on the switch. " + e);
     }
     finally
     {
       int i;
       if (callsttype != CallUCIDStatus.OK)
       {
         i = getCallID();
         setState(34, null);
         this.provider.dumpCall(i);
       }
 
     }
 
     return callsttype == CallUCIDStatus.OK;
   }
 
   boolean updateObject()
   {
     if ((isMonitorSet() == true) || ((this.state == 32) && (this.callID == 0)) || (this.state == 34))
     {
       return true;
     }
 
     CSTAConnectionID snapConnID = null;
     synchronized (this.connections)
     {
       for (int i = 0; i < this.connections.size(); ++i)
       {
         TSConnection conn = (TSConnection)this.connections.elementAt(i);
         if (conn.getTSDevice().isMonitorSet())
         {
           return true;
         }
         Vector termConns = conn.getTermConns();
         if (termConns == null)
           continue;
         synchronized (termConns)
         {
           for (int j = 0; j < termConns.size(); ++j)
           {
             TSConnection termconn = (TSConnection)termConns.elementAt(j);
             if (!termconn.getTSDevice().isMonitorSet())
               continue;
             return true;
           }
 
         }
 
       }
 
       if (this.connections.size() > 0)
       {
         try
         {
           snapConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
         }
         catch (TsapiPlatformException e)
         {
           log.error("Ignoring exception: " + e);
           if (this.callID != 0)
           {
             snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
           }
         }
       }
     }
     if (snapConnID != null)
     {
       return doSnapshot(snapConnID, null, true);
     }
     if (this.callID != 0)
     {
       snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
       return doSnapshot(snapConnID, null, true);
     }
 
     return false;
   }
 
   boolean updateSuspiciousObject()
   {
     if (this.callID != 0)
     {
       log.info("Mark " + this + " (callID " + this.callID + ") for immediate and future snapshots for " + this.provider);
 
       this.needSnapshot = true;
 
       CSTAConnectionID snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
       return doSnapshot(snapConnID, null, true);
     }
 
     return false;
   }
 
   void addConnection(TSConnection tsConn, Vector<TSEvent> eventList)
   {
     synchronized (this.connections)
     {
       if (this.connections.contains(tsConn))
       {
         return;
       }
 
       this.connections.addElement(tsConn);
 
       if (this.connections.size() > 0)
       {
         setState(33, eventList);
       }
 
       if (this.connections.size() >= getConnection_wait_limit())
       {
         this.connections.notify();
       }
     }
   }
 
   void removeConnection(TSConnection tsConn, Vector<TSEvent> eventList)
   {
     if (!this.connections.removeElement(tsConn))
     {
       return;
     }
 
     if (this.confController == tsConn)
     {
       this.confController = null;
     }
     if (this.xferController == tsConn)
     {
       this.xferController = null;
     }
 
     synchronized (this.staleConnections)
     {
       if (!this.staleConnections.contains(tsConn))
       {
         this.staleConnections.addElement(tsConn);
       }
     }
     if (checkForMonitors())
       return;
     setNeedSnapshot(true);
   }
 
   public boolean checkForMonitors()
   {
     if (isMonitorSet() == true)
     {
       return true;
     }
 
     synchronized (this.connections)
     {
       for (int i = 0; i < this.connections.size(); ++i)
       {
         TSConnection conn = (TSConnection)this.connections.elementAt(i);
         if (conn.getTSDevice().isMonitorSet())
         {
           return true;
         }
         Vector termConns = conn.getTermConns();
         if (termConns == null)
           continue;
         synchronized (termConns)
         {
           for (int j = 0; j < termConns.size(); ++j)
           {
             TSConnection termconn = (TSConnection)termConns.elementAt(j);
             if (!termconn.getTSDevice().isMonitorSet())
               continue;
             return true;
           }
 
         }
 
       }
 
     }
 
     return false;
   }
 
   void endCVDObservers(int cause, Object privateData)
   {
     for (int i = 0; i < this.staleConnections.size(); ++i)
     {
       TSDevice dev = ((TSConnection)this.staleConnections.elementAt(i)).getTSDevice();
       if (dev == null)
         continue;
       Vector cvd = dev.getCVDObservers();
       for (int j = 0; j < cvd.size(); ++j)
       {
         TsapiCallMonitor obs = (TsapiCallMonitor)cvd.elementAt(j);
         removeCallMonitor(obs, cause, privateData);
       }
 
       dev.testDelete();
     }
   }
 
   void removeObservers(int cause, Object privateData, int xrefID)
   {
     if ((xrefID != 0) && (this.monitorCrossRefID == xrefID))
     {
       this.provider.deleteMonitor(this.monitorCrossRefID);
       this.monitorCrossRefID = 0;
     }
 
     CallbackAndType cbAndType = null;
     synchronized (this.callbackAndTypeVector)
     {
       try
       {
         for (int i = 0; i < this.callbackAndTypeVector.size(); ++i)
         {
           cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
           if (cbAndType.devWithType == null)
           {
             synchronized (this.monitorThreads)
             {
               if (!this.monitorThreads.contains(cbAndType.callback))
               {
                 this.monitorThreads.addElement(cbAndType.callback);
                 cbAndType.callback.addReference();
               }
             }
           }
           else
           {
             boolean found = false;
             DeviceObs devObs = null;
             synchronized (this.deviceObsVector)
             {
               for (int j = 0; j < this.deviceObsVector.size(); ++j)
               {
                 devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
                 if (devObs.callback != cbAndType.callback)
                   continue;
                 found = true;
                 break;
               }
 
               if (!found)
               {
                 devObs = new DeviceObs(cbAndType.callback);
                 devObs.devWithTypeVector.addElement(cbAndType.devWithType);
                 this.deviceObsVector.addElement(devObs);
                 cbAndType.callback.addReference();
               }
             }
 
           }
 
         }
 
       }
       finally
       {
         this.callbackAndTypeVector.removeAllElements();
       }
     }
 
     Vector observers = getCallObservers();
     for (int i = 0; i < observers.size(); ++i)
     {
       removeCallMonitor((TsapiCallMonitor)observers.elementAt(i), cause, privateData);
     }
 
     if (this.monitorCrossRefID == 0)
       return;
     this.provider.deleteMonitor(this.monitorCrossRefID);
     if (this.provider.getCapabilities().getMonitorStop() != 0)
     {
       try
       {
         this.provider.tsapi.monitorStop(this.monitorCrossRefID, null, null);
       }
       catch (TsapiUnableToSendException tue)
       {
         throw tue;
       }
       catch (Exception e) {
         log.error(e.getMessage(), e);
       }
 
     }
 
     this.monitorCrossRefID = 0;
 
     if (checkForMonitors())
       return;
     setNeedSnapshot(true);
   }
 
   void considerAddingVDNMonitorCallObservers(Object monitored)
   {
     if (!(monitored instanceof TSDevice))
       return;
     TSDevice monitoredTSDevice = (TSDevice)monitored;
     if (!monitoredTSDevice.isPredictiveCallsViaDeviceMonitorSet()) {
       return;
     }
     addDeviceObservers(monitoredTSDevice, null, null, monitoredTSDevice.callsViaAddressMonitorThreads, true);
   }
 
   void addDeviceObservers(TSDevice tsDevice, Vector<TsapiCallMonitor> _terminalObservers, Vector<TsapiCallMonitor> _addressObservers, Vector<TsapiCallMonitor> _cvdObservers, boolean sendSnapshotEvents)
   {
     TsapiCallMonitor callback = null;
 
     boolean found = false;
 
     CSTAConnectionID snapConnID = null;
     synchronized (this.connections)
     {
       if (this.connections.size() > 0)
       {
         try
         {
           snapConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
         }
         catch (TsapiPlatformException e)
         {
           log.error("Ignoring exception: " + e);
           if (this.callID != 0)
           {
             snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
           }
         }
       }
       else if (this.callID != 0)
       {
         snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
       }
     }
 
     if (_cvdObservers != null)
     {
       Vector cvdObservers = new Vector(_cvdObservers);
       for (int i = 0; i < cvdObservers.size(); ++i)
       {
         found = false;
         callback = (TsapiCallMonitor)cvdObservers.elementAt(i);
         synchronized (this.monitorThreads)
         {
           if (this.monitorThreads.contains(callback))
           {
             found = true;
           }
 
           if (!found)
           {
             synchronized (this.staleObsVector)
             {
               for (int j = 0; j < this.staleObsVector.size(); ++j)
               {
                 if (this.staleObsVector.elementAt(j) != callback)
                   continue;
                 this.monitorThreads.addElement(callback);
                 this.staleObsVector.removeElementAt(j);
 
                 found = true;
                 break;
               }
             }
 
             if (!found)
             {
               synchronized (this.deviceObsVector)
               {
                 for (int j = 0; j < this.deviceObsVector.size(); ++j)
                 {
                   if (((DeviceObs)this.deviceObsVector.elementAt(j)).callback != callback)
                     continue;
                   this.monitorThreads.addElement(callback);
                   this.deviceObsVector.removeElementAt(j);
 
                   found = true;
                   break;
                 }
               }
             }
 
           }
 
           if ((!found) && (((snapConnID == null) || (!sendSnapshotEvents))))
           {
             this.monitorThreads.addElement(callback);
             callback.addReference();
             if (sendSnapshotEvents)
               sendSnapshot(callback, null, false);
           }
         }
         if ((found) || (snapConnID == null) || (!sendSnapshotEvents))
           continue;
         this.callbackAndTypeVector.addElement(new CallbackAndType(callback, null));
       }
 
     }
 
     DeviceObs devObs = null;
     DevWithType devWithType = null;
 
     if (_addressObservers != null)
     {
       found = false;
 
       devWithType = new DevWithType(tsDevice, false);
 
       Vector addressObservers = new Vector(_addressObservers);
 
       for (int i = 0; i < addressObservers.size(); ++i)
       {
         callback = (TsapiCallMonitor)addressObservers.elementAt(i);
         found = false;
         if (this.monitorThreads.contains(callback))
         {
           found = true;
         }
 
         if (!found)
         {
           synchronized (this.deviceObsVector)
           {
             synchronized (this.staleObsVector)
             {
               int k;
               for (k = 0; k < this.staleObsVector.size(); ++k)
               {
                 if (this.staleObsVector.elementAt(k) != callback)
                   continue;
                 found = true;
                 devObs = new DeviceObs(callback);
                 devObs.devWithTypeVector.addElement(devWithType);
 
                 this.deviceObsVector.addElement(devObs);
                 this.staleObsVector.removeElementAt(k);
                 break;
               }
             }
 
             if (!found)
             {
               for ( i = 0; i < this.deviceObsVector.size(); ++i)
               {
                 devObs = (DeviceObs)this.deviceObsVector.elementAt(i);
                 if (devObs.callback != callback)
                   continue;
                 found = true;
                 synchronized (devObs.devWithTypeVector)
                 {
                   if (!devObs.devWithTypeVector.contains(devWithType))
                   {
                     devObs.devWithTypeVector.addElement(devWithType);
                   }
                 }
                 break;
               }
 
               if ((!found) && (((snapConnID == null) || (!sendSnapshotEvents))))
               {
                 devObs = new DeviceObs(callback);
                 devObs.devWithTypeVector.addElement(devWithType);
                 this.deviceObsVector.addElement(devObs);
                 callback.addReference();
                 if (sendSnapshotEvents)
                   sendSnapshot(callback, null, false);
               }
             }
           }
         }
         if ((found) || (snapConnID == null) || (!sendSnapshotEvents))
           continue;
         this.callbackAndTypeVector.addElement(new CallbackAndType(callback, devWithType));
       }
     }
 
     if (_terminalObservers == null)
       return;
     found = false;
 
     devWithType = new DevWithType(tsDevice, true);
 
     Vector terminalObservers = new Vector(_terminalObservers);
 
     for (int i = 0; i < terminalObservers.size(); ++i)
     {
       callback = (TsapiCallMonitor)terminalObservers.elementAt(i);
       found = false;
       if (this.monitorThreads.contains(callback))
       {
         found = true;
       }
       if (!found)
       {
         synchronized (this.deviceObsVector)
         {
           synchronized (this.staleObsVector)
           {
             int l;
             for (l = 0; l < this.staleObsVector.size(); ++l)
             {
               if (this.staleObsVector.elementAt(l) != callback)
                 continue;
               found = true;
               devObs = new DeviceObs(callback);
               devObs.devWithTypeVector.addElement(devWithType);
 
               this.deviceObsVector.addElement(devObs);
               this.staleObsVector.removeElementAt(l);
               break;
             }
           }
 
           if (!found)
           {
             int j;
             for (j = 0; j < this.deviceObsVector.size(); ++j)
             {
               devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
               if (devObs.callback != callback)
                 continue;
               found = true;
               synchronized (devObs.devWithTypeVector)
               {
                 if (!devObs.devWithTypeVector.contains(devWithType))
                 {
                   devObs.devWithTypeVector.addElement(devWithType);
                 }
               }
               break;
             }
 
             if ((!found) && (((snapConnID == null) || (!sendSnapshotEvents))))
             {
               devObs = new DeviceObs(callback);
               devObs.devWithTypeVector.addElement(devWithType);
               this.deviceObsVector.addElement(devObs);
               callback.addReference();
               if (sendSnapshotEvents)
                 sendSnapshot(callback, null, false);
             }
           }
         }
       }
       if ((found) || (snapConnID == null) || (!sendSnapshotEvents))
         continue;
       this.callbackAndTypeVector.addElement(new CallbackAndType(callback, devWithType));
     }
   }
 
   void processCallbackSnapshots(int cause)
   {
     boolean doDoSnapshot = false;
     synchronized (this.callbackAndTypeVector)
     {
       if ((this.callbackAndTypeVector.size() > 0) && (this.currentSnapshotHandler == null))
       {
         doDoSnapshot = true;
       }
     }
     if (!doDoSnapshot)
       return;
     CSTAConnectionID snapConnID = null;
     synchronized (this.connections)
     {
       if (this.connections.size() > 0)
       {
         try
         {
           snapConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
         }
         catch (TsapiPlatformException e)
         {
           log.error("Ignoring exception: " + e);
           if (this.callID != 0)
           {
             snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
           }
         }
       }
       else if (this.callID != 0)
       {
         snapConnID = new CSTAConnectionID(this.callID, "", (short) 0);
       }
     }
     if (snapConnID != null)
       doSnapshot(snapConnID, null, false, cause);
   }
 
   void doCallbackSnapshots(Vector<TSEvent> eventList, int cause)
   {
     CallbackAndType cbAndType = null;
     synchronized (this.callbackAndTypeVector)
     {
       try
       {
         for (int i = 0; i < this.callbackAndTypeVector.size(); ++i)
         {
           cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
           if (cbAndType.devWithType == null)
           {
             synchronized (this.monitorThreads)
             {
               if (!this.monitorThreads.contains(cbAndType.callback))
               {
                 this.monitorThreads.addElement(cbAndType.callback);
                 cbAndType.callback.addReference();
                 sendSnapshot(cbAndType.callback, eventList, false, cause);
               }
             }
           }
           else
           {
             boolean found = false;
             DeviceObs devObs = null;
             synchronized (this.deviceObsVector)
             {
               for (int j = 0; j < this.deviceObsVector.size(); ++j)
               {
                 devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
                 if (devObs.callback != cbAndType.callback)
                   continue;
                 found = true;
                 break;
               }
 
               if (!found)
               {
                 devObs = new DeviceObs(cbAndType.callback);
                 devObs.devWithTypeVector.addElement(cbAndType.devWithType);
                 this.deviceObsVector.addElement(devObs);
                 cbAndType.callback.addReference();
                 sendSnapshot(cbAndType.callback, eventList, false, cause);
               }
             }
 
           }
 
         }
 
       }
       finally
       {
         this.callbackAndTypeVector.removeAllElements();
       }
     }
     if (!checkForMonitors())
       return;
     this.needSnapshot = false;
   }
 
   void removeDefaultDeviceObservers(TSDevice tsDevice, boolean isTerminal)
   {
     Vector cbKeepVector = new Vector();
     DeviceObs devObs = null;
     DevWithType devWithType = new DevWithType(tsDevice, isTerminal);
 
     synchronized (this.deviceObsVector)
     {
       for (int j = 0; j < this.deviceObsVector.size(); ++j)
       {
         devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
         synchronized (devObs.devWithTypeVector)
         {
           if (devObs.devWithTypeVector.removeElement(devWithType))
           {
             if ((devObs.devWithTypeVector.isEmpty()) && (!devObs.callback.isVDN()))
             {
               this.staleObsVector.addElement(devObs.callback);
             }
             else
             {
               cbKeepVector.addElement(devObs);
             }
 
           }
           else {
             cbKeepVector.addElement(devObs);
           }
         }
       }
       this.deviceObsVector.clear();
       this.deviceObsVector.addAll(cbKeepVector);
     }
 
     if ((this.internalDeviceMonitor == null) || (!this.internalDeviceMonitor.equals(tsDevice)))
       return;
     this.internalDeviceMonitor.removeInternalMonitor(this);
     this.internalDeviceMonitor = null;
     if (checkForMonitors())
       return;
     setNeedSnapshot(true);
   }
 
   void staleObsCleanup(int cause)
   {
     synchronized (this.staleObsVector)
     {
       for (int i = 0; i < this.staleObsVector.size(); ++i)
       {
         ((TsapiCallMonitor)this.staleObsVector.elementAt(i)).deleteReference(this, false, cause, null);
       }
 
       this.staleObsVector.removeAllElements();
     }
   }
 
   boolean doHeldTalkingMatch(TSCall otherCall)
   {
     TSConnection conn = null;
     TSConnection termConn = null;
     synchronized (this.connections)
     {
       for (int i = 0; i < this.connections.size(); ++i)
       {
         conn = (TSConnection)this.connections.elementAt(i);
         Vector termConns = conn.getTermConns();
         if (termConns == null)
           continue;
         synchronized (termConns)
         {
           for (int j = 0; j < termConns.size(); ++j)
           {
             termConn = (TSConnection)termConns.elementAt(j);
             if ((termConn.getCallControlTermConnState() != 99) || 
               (otherCall.findActiveTermConnection(termConn.getTSDevice()) == null)) {
               continue;
             }
             return true;
           }
         }
 
       }
 
     }
 
     return false;
   }
 
   TSConnection findHeldTermConnection(TSDevice matchDevice)
   {
     TSConnection conn = null;
     TSConnection termConn = null;
 
     synchronized (this.connections)
     {
       for (int i = 0; i < this.connections.size(); ++i)
       {
         conn = (TSConnection)this.connections.elementAt(i);
         Vector termConns = conn.getTermConns();
         if (termConns == null)
           continue;
         synchronized (termConns)
         {
           for (int j = 0; j < termConns.size(); ++j)
           {
             termConn = (TSConnection)termConns.elementAt(j);
             if (((termConn.getCallControlTermConnState() != 99) && (termConn.getCallControlTermConnState() != 103)) || (termConn.getTSDevice() != matchDevice)) {
               continue;
             }
 
             return termConn;
           }
         }
 
       }
 
     }
 
     return null;
   }
 
   TSConnection findActiveTermConnection(TSDevice matchDevice)
   {
     TSConnection conn = null;
     TSConnection termConn = null;
 
     synchronized (this.connections)
     {
       for (int i = 0; i < this.connections.size(); ++i)
       {
         conn = (TSConnection)this.connections.elementAt(i);
         Vector termConns = conn.getTermConns();
         if (termConns == null)
           continue;
         synchronized (termConns)
         {
           for (int j = 0; j < termConns.size(); ++j)
           {
             termConn = (TSConnection)termConns.elementAt(j);
             if (((termConn.getCallControlTermConnState() != 98) && (termConn.getCallControlTermConnState() != 103)) || (termConn.getTSDevice() != matchDevice)) {
               continue;
             }
 
             return termConn;
           }
         }
       }
 
     }
 
     return null;
   }
 
   protected void moveInternalStuff(TSCall otherCall)
   {
     TsapiCallMonitor callback = null;
 
     synchronized (otherCall.monitorThreads)
     {
       for (int i = 0; i < otherCall.monitorThreads.size(); ++i)
       {
         callback = (TsapiCallMonitor)otherCall.monitorThreads.elementAt(i);
         synchronized (this.monitorThreads)
         {
           if (!this.monitorThreads.contains(callback))
           {
             this.monitorThreads.addElement(callback);
           }
         }
       }
     }
     DeviceObs devObs = null;
     DeviceObs otherDevObs = null;
     DevWithType devWithType = null;
 
     synchronized (otherCall.deviceObsVector)
     {
       for (int i = 0; i < otherCall.deviceObsVector.size(); ++i)
       {
         otherDevObs = (DeviceObs)otherCall.deviceObsVector.elementAt(i);
         i = 0;
         synchronized (this.deviceObsVector)
         {
           for (int j = 0; j < this.deviceObsVector.size(); ++j)
           {
             devObs = (DeviceObs)this.deviceObsVector.elementAt(j);
             if (otherDevObs.callback != devObs.callback)
               continue;
             i = 1;
             synchronized (otherDevObs.devWithTypeVector)
             {
               for (int k = 0; k < otherDevObs.devWithTypeVector.size(); ++k)
               {
                 devWithType = (DevWithType)otherDevObs.devWithTypeVector.elementAt(k);
                 synchronized (devObs.devWithTypeVector)
                 {
                   if (!devObs.devWithTypeVector.contains(devWithType))
                   {
                     devObs.devWithTypeVector.addElement(devWithType);
                   }
                 }
               }
             }
             break;
           }
 
           if (i == 0)
           {
             this.deviceObsVector.addElement(otherDevObs);
           }
         }
       }
     }
 
     CallbackAndType otherCbAndType = null;
     CallbackAndType cbAndType = null;
     synchronized (otherCall.callbackAndTypeVector)
     {
       for (int i = 0; i < otherCall.callbackAndTypeVector.size(); ++i)
       {
         otherCbAndType = (CallbackAndType)otherCall.callbackAndTypeVector.elementAt(i);
         int j = 0;
         synchronized (this.callbackAndTypeVector)
         {
           for (j = 0; j < this.callbackAndTypeVector.size(); ++j)
           {
             cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(j);
             if ((!otherCbAndType.callback.equals(cbAndType.callback)) || (!otherCbAndType.devWithType.equals(cbAndType.devWithType))) {
               continue;
             }
             j = 1;
             break;
           }
 
           if (j == 0)
           {
             this.callbackAndTypeVector.addElement(otherCbAndType);
           }
         }
       }
     }
 
     synchronized (otherCall)
     {
       synchronized (this)
       {
         if (!this.monitorPending)
         {
           this.monitorPending = otherCall.monitorPending;
         }
         if (this.internalDeviceMonitor == null)
         {
           this.internalDeviceMonitor = otherCall.internalDeviceMonitor;
         }
       }
     }
 
     moveStuff(otherCall);
   }
 
   void copyStuff(TSCall otherCall)
   {
     if (!callIsInVDNDomain(otherCall)) {
       return;
     }
     Vector observers = otherCall.getCallObservers();
 
     for (int i = 0; i < observers.size(); ++i)
     {
       if ((((TsapiCallMonitor)observers.elementAt(i)).isVDN()) && (!this.monitorThreads.contains(observers.elementAt(i))))
       {
         this.monitorThreads.addElement((TsapiCallMonitor) observers.elementAt(i));
         ((TsapiCallMonitor)observers.elementAt(i)).addReference();
       }
       else
       {
         otherCall.removeCallMonitor((TsapiCallMonitor)observers.elementAt(i));
       }
     }
   }
 
   void moveStuff(TSCall otherCall)
   {
     if (otherCall == null)
     {
       return;
     }
 
     if (callIsInVDNDomain(otherCall))
     {
       Vector observers = otherCall.getCallObservers();
 
       for (int i = 0; i < observers.size(); ++i)
       {
         if ((((TsapiCallMonitor)observers.elementAt(i)).isVDN()) && (!this.monitorThreads.contains(observers.elementAt(i))))
         {
           this.monitorThreads.addElement((TsapiCallMonitor) observers.elementAt(i));
           ((TsapiCallMonitor)observers.elementAt(i)).addReference();
         }
         else
         {
           otherCall.removeCallMonitor((TsapiCallMonitor)observers.elementAt(i));
         }
       }
     }
     else
     {
       otherCall.removeObservers(100, null, 0);
     }
 
     if (this.callingAddress == null)
       this.callingAddress = otherCall.callingAddress;
     if (this.callingTerminal == null)
       this.callingTerminal = otherCall.callingTerminal;
     if (this.calledDevice == null)
       this.calledDevice = otherCall.calledDevice;
     if (this.lastRedirectionDevice == null)
       this.lastRedirectionDevice = otherCall.lastRedirectionDevice;
     if (this.confController == null)
       this.confController = otherCall.confController;
     if (this.xferController == null)
       this.xferController = otherCall.xferController;
     if (this.uui == null)
       this.uui = otherCall.uui;
     if (this.lai == null)
       this.lai = otherCall.lai;
     if (this.uec == null)
       this.uec = otherCall.uec;
     if (this.oci == null)
       this.oci = otherCall.oci;
     if (getDeviceHistory() == null) {
       setDeviceHistory(otherCall.getDeviceHistory());
     }
 
     this.replyPriv = otherCall.replyPriv;
     this.confEnable = otherCall.confEnable;
     this.xferEnable = otherCall.xferEnable;
 
     TSTrunk trk = null;
     synchronized (otherCall.trkVector)
     {
       for (int i = 0; i < otherCall.trkVector.size(); ++i)
       {
         trk = (TSTrunk)otherCall.trkVector.elementAt(i);
         trk.setCall(this, null);
         synchronized (this.trkVector)
         {
           if (!this.trkVector.contains(trk))
           {
             this.trkVector.addElement(trk);
           }
         }
       }
     }
 
     if (checkForMonitors())
       return;
     setNeedSnapshot(true);
   }
 
   public boolean callIsInVDNDomain(TSCall callToCheck)
   {
     return callToCheck.refVDN != null;
   }
 
   Vector<TsapiCallMonitor> getObservers()
   {
     Vector allObservers = new Vector(this.monitorThreads);
     synchronized (this.deviceObsVector)
     {
       for (int i = 0; i < this.deviceObsVector.size(); ++i)
       {
         if (!allObservers.contains(((DeviceObs)this.deviceObsVector.elementAt(i)).callback))
           allObservers.addElement(((DeviceObs)this.deviceObsVector.elementAt(i)).callback);
       }
     }
     synchronized (this.staleObsVector)
     {
       for (int i = 0; i < this.staleObsVector.size(); ++i)
       {
         if (!allObservers.contains(this.staleObsVector.elementAt(i)))
           allObservers.addElement(this.staleObsVector.elementAt(i));
       }
     }
     return allObservers;
   }
 
   public Vector<TsapiCallMonitor> getCallObservers()
   {
     Vector allObservers = getObservers();
     synchronized (this.callbackAndTypeVector)
     {
       CallbackAndType cbAndType = null;
       for (int i = 0; i < this.callbackAndTypeVector.size(); ++i)
       {
         cbAndType = (CallbackAndType)this.callbackAndTypeVector.elementAt(i);
         TsapiCallMonitor obs = cbAndType.callback;
         if (allObservers.contains(obs))
           continue;
         allObservers.addElement(obs);
       }
     }
 
     return allObservers;
   }
 
   public TSCall getHandOff()
   {
     if (this.handOffCall != null)
     {
       return this.handOffCall;
     }
 
     return this;
   }
 
   synchronized void setCallID(int newCallID)
   {
     if (newCallID == 0)
     {
       int rc = this.provider.getNonCallID();
       if (rc != -1)
       {
         this.nonCallID = rc;
         this.provider.addNonCallToHash(this);
       }
     }
     else if (this.callID == 0)
     {
       this.callID = newCallID;
       if (this.nonCallID != -1)
       {
         this.provider.deleteNonCallFromHash(this.nonCallID);
         this.provider.releaseNonCallID(this.nonCallID);
         this.nonCallID = -1;
       }
 
       TSCall tmpCall = this.provider.addCallToHash(this);
       if (tmpCall != null)
       {
         if ((this.ucid != null) && (tmpCall.ucid != null) && (this.ucid.compareTo(tmpCall.ucid) == 0))
         {
           this.handOffCall = tmpCall;
 
           this.provider.addCallToHash(this.handOffCall);
 
           synchronized (this.connections)
           {
             for (int i = 0; i < this.connections.size(); ++i)
             {
               TSConnection conn = (TSConnection)this.connections.elementAt(i);
               conn.setCall(this.handOffCall);
               this.handOffCall.addConnection(conn, null);
             }
           }
 
           this.handOffCall.moveInternalStuff(this);
         }
         else
         {
           tmpCall.setState(34, null);
           this.provider.dumpCall(tmpCall.getCallID());
           this.handOffCall = null;
 
           log.info("Mismatched UCID for setCallID removing stale call obj " + tmpCall);
           log.info("UCID for setCallID for the new call is " + this.ucid);
 
           this.provider.addCallToHash(this);
         }
       }
     } else {
       if (newCallID == this.callID) {
         return;
       }
       this.provider.changeCallIDInDomain(this.callID, newCallID);
 
       this.provider.deleteCallFromHash(this.callID);
 
       int saveCallID = this.callID;
       this.callID = newCallID;
 
       TSCall saveCall = this.provider.addCallToHash(this);
 
       if (saveCall == null)
         return;
       saveCall.callID = saveCallID;
       this.provider.addCallToHash(saveCall);
 
       Vector conns = new Vector(saveCall.connections);
       for (int i = 0; i < conns.size(); ++i)
       {
         TSConnection conn = (TSConnection)conns.elementAt(i);
         Vector cv = conn.getTermConns();
         if ((cv != null) && (cv.size() > 0))
         {
           Vector termConns = new Vector(cv);
           for (int j = 0; j < termConns.size(); ++j)
           {
             TSConnection tc = (TSConnection)termConns.elementAt(j);
             CSTAConnectionID connID = tc.getConnID();
             connID.setCallID(saveCallID);
             tc.setConnID(connID);
           }
         }
         else
         {
           CSTAConnectionID connID = conn.getConnID();
           connID.setCallID(saveCallID);
           conn.setConnID(connID);
         }
       }
       moveInternalStuff(saveCall);
     }
   }
 
   void setState(int _state, Vector<TSEvent> eventList)
   {
     synchronized (this)
     {
       if ((this.state == _state) || (this.state == 34))
       {
         return;
       }
 
       this.state = _state;
     }
 
     switch (this.state)
     {
     case 33:
       if (eventList != null)
       {
         eventList.addElement(new TSEvent(4, this));
       }
 
       boolean tryMonitor = false;
       synchronized (this)
       {
         if (this.monitorPending)
         {
           tryMonitor = true;
           this.monitorPending = false;
         }
       }
       if (!tryMonitor)
         return;
       try
       {
         setMonitor(false);
       }
       catch (TsapiUnableToSendException tue)
       {
         throw tue;
       }
       catch (Exception e)
       {
         log.error(e.getMessage(), e);
         removeObservers(107, null, 0);
       }break;
     case 34:
       Vector trkVectorClone = (Vector)this.trkVector.clone();
       int i;
       for (i = 0; i < trkVectorClone.size(); ++i)
       {
         TSTrunk tmpTrunk = (TSTrunk)trkVectorClone.elementAt(i);
 
         synchronized (tmpTrunk) {
           if (this.trkVector.removeElement(tmpTrunk)) {
             tmpTrunk.setState(1, eventList);
           }
         }
         tmpTrunk = null;
       }
       trkVectorClone.removeAllElements();
       trkVectorClone = null;
 
       Vector conn = new Vector(this.connections);
       for (i = 0; i < conn.size(); ++i)
       {
         ((TSConnection)conn.elementAt(i)).setConnectionState(89, eventList);
       }
       if (eventList != null)
       {
         eventList.addElement(new TSEvent(5, this));
       }
 
       this.needSnapshot = false;
 
       synchronized (this.connections)
       {
         this.connections.notify();
       }
 
       delete();
     }
   }
 
   void setStateForVDN()
   {
     this.provider.removeCallFromDomain(this);
   }
 
   boolean needsSnapshot()
   {
     return this.needSnapshot;
   }
 
   boolean doSnapshot(CSTAConnectionID connID, SnapshotCallExtraConfHandler extraHandler, boolean waitForSnapshotConf)
   {
     return doSnapshot(connID, extraHandler, waitForSnapshotConf, 110);
   }
 
   boolean doSnapshot(CSTAConnectionID connID, SnapshotCallExtraConfHandler extraHandler, boolean waitForSnapshotConf, int cause)
   {
     if (!this.needSnapshot)
     {
       try
       {
         if (extraHandler != null)
         {
           extraHandler.handleConf(true, null, null);
         }
       }
       finally
       {
         doCallbackSnapshots(null, cause);
       }
       return true;
     }
 
     if (this.provider.getCapabilities().getSnapshotCallReq() == 0)
     {
       try
       {
         if (extraHandler != null)
         {
           extraHandler.handleConf(false, null, null);
         }
       }
       finally
       {
         doCallbackSnapshots(null, cause);
       }
       return false;
     }
 
     if (!waitForSnapshotConf)
     {
       synchronized (this.callbackAndTypeVector)
       {
         if (this.futureAsynchronousSnapshotHandler != null)
         {
           this.futureAsynchronousSnapshotHandler.addExtraHandler(extraHandler);
           return true;
         }
       }
 
     }
 
     SnapshotCallConfHandler handler = new SnapshotCallConfHandler(this, extraHandler, waitForSnapshotConf);
 
     synchronized (handler)
     {
       this.snapshotCallConfPending = true;
       this.provider.tsapi.snapshotCall(connID, null, handler);
 
       if (waitForSnapshotConf)
       {
         try
         {
           handler.wait(TSProviderImpl.TSAPI_RESPONSE_TIME);
         } catch (InterruptedException e) {
         }
         if (!handler.handled)
         {
           try
           {
             if (extraHandler != null)
             {
               extraHandler.handleConf(false, null, null);
             }
           }
           finally
           {
             doCallbackSnapshots(null, cause);
           }
           return false;
         }
         return handler.rc;
       }
     }
 
     return true;
   }
 
   void getSnapshot(Vector<TSEvent> eventList)
   {
     if (eventList == null)
     {
       return;
     }
 
     switch (this.state)
     {
     case 33:
       eventList.addElement(new TSEvent(4, this));
       break;
     case 34:
       eventList.addElement(new TSEvent(5, this));
     }
 
     synchronized (this.connections)
     {
       for (int i = 0; i < this.connections.size(); ++i)
       {
         ((TSConnection)this.connections.elementAt(i)).getSnapshot(eventList);
       }
     }
   }
 
   void sendSnapshot(TsapiCallMonitor callback, Vector<TSEvent> _eventList, boolean snapMetaCode)
   {
     sendSnapshot(callback, _eventList, snapMetaCode, 110);
   }
 
   void sendSnapshot(TsapiCallMonitor callback, Vector<TSEvent> _eventList, boolean snapMetaCode, int cause) {
     if (callback == null)
     {
       return;
     }
 
     Vector eventList = null;
 
     if (_eventList == null)
     {
       eventList = new Vector();
       getSnapshot(eventList);
     }
     else
     {
       eventList = _eventList;
     }
 
     setCSTACause((short) -1);
 
     if (eventList.size() <= 0) {
       return;
     }
     callback.deliverEvents(eventList, cause, snapMetaCode);
   }
 
   public synchronized void setNeedSnapshot(boolean flag)
   {
     this.needSnapshot = flag;
   }
 
   boolean isMonitorSet()
   {
     return (this.monitorCrossRefID != 0) || (this.provider.isCallInAnyDomain(this));
   }
 
   void setMonitor(boolean waitForSnapshotConf)
     throws TsapiResourceUnavailableException
   {
     CSTAConnectionID monConnID = null;
     if (!isMonitorSet())
     {
       synchronized (this.connections)
       {
         if (this.connections.size() > 0)
         {
           try
           {
             monConnID = ((TSConnection)this.connections.elementAt(0)).getConnID();
           }
           catch (TsapiPlatformException e)
           {
             log.error("Ignoring exception: " + e);
             if (this.callID != 0)
             {
               monConnID = new CSTAConnectionID(this.callID, "", (short) 0);
             }
           }
         }
         else if (this.callID != 0)
         {
           monConnID = new CSTAConnectionID(this.callID, "", (short) 0);
         }
       }
     }
     if (monConnID != null)
       monitorCall(monConnID, waitForSnapshotConf);
   }
 
   void monitorCall(CSTAConnectionID connID, boolean waitForSnapshotConf)
     throws TsapiResourceUnavailableException
   {
     if ((this.provider.getCapabilities().getMonitorCall() == 0) || (connID == null))
     {
       throw new TsapiResourceUnavailableException(0, 0, 0, "no capability to monitor call");
     }
 
     if (this.monitorCrossRefID != 0)
       return;
     CSTAEvent event;
     try
     {
       event = this.provider.tsapi.monitorCall(connID, new CSTAMonitorFilter(), null);
     }
     catch (TsapiUnableToSendException tue)
     {
       throw tue;
     }
     catch (Exception e)
     {
       log.error("MonitorCall request failed - retrying");
       try
       {
         event = this.provider.tsapi.monitorCall(connID, new CSTAMonitorFilter(), null);
       }
       catch (TsapiResourceUnavailableException e1)
       {
         throw e1;
       }
       catch (Exception e1)
       {
         throw new TsapiResourceUnavailableException(0, 0, 0, "monitor call failure");
       }
     }
 
     CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent)event.getEvent();
 
     SnapshotCallExtraConfHandler handler = new AddCallMonitorHandler(this, monitorConf.getMonitorCrossRefID());
     if (this.needSnapshot)
     {
       doSnapshot(connID, handler, waitForSnapshotConf);
     }
     else
     {
       handler.handleConf(true, null, null);
     }
   }
 
   void replaceConnections(Vector<TSConnection> newConnections, Vector<TSEvent> eventList)
   {
     for (int i = 0; i < newConnections.size(); ++i)
     {
       TSConnection conn = (TSConnection)newConnections.elementAt(i);
       if (conn.isTerminalConnection())
       {
         conn = conn.getTSConn();
         newConnections.setElementAt(conn, i);
       }
 
       addConnection(conn, null);
     }
 
     Vector conns = new Vector(this.connections);
     for (int i = 0; i < conns.size(); ++i)
     {
       TSConnection conn = (TSConnection)conns.elementAt(i);
       if (newConnections.contains(conn))
         continue;
       conn.setConnectionState(89, eventList);
     }
   }
 
   TSConnection getConnAtDevice(TSDevice matchDevice)
   {
     synchronized (this.connections)
     {
       TSConnection conn = null;
       for (int i = 0; i < this.connections.size(); ++i)
       {
         conn = (TSConnection)this.connections.elementAt(i);
         if (conn.getTSDevice() == matchDevice)
         {
           return conn;
         }
       }
       return null;
     }
   }
 
   TSConnection findOtherConnection(TSConnection conn)
   {
     synchronized (this.connections)
     {
       int size = this.connections.size();
       if (size != 2)
       {
         return null;
       }
       TSConnection otherConn = null;
       for (int i = 0; i < size; ++i)
       {
         otherConn = (TSConnection)this.connections.elementAt(i);
         if (conn != otherConn)
         {
           return otherConn;
         }
       }
       return null;
     }
   }
 
   void setDigits(String _digits)
   {
     this.digits = _digits;
   }
 
   public String getDigits()
   {
     return this.digits;
   }
 
   synchronized void delete()
   {
     log.info("Call object= " + this + " being deleted" + " for " + this.provider);
 
     if (!this.delayVDNremoveCallFromDomain)
     {
       this.provider.removeCallFromDomain(this);
     }
 
     if (this.internalDeviceMonitor != null)
     {
       this.internalDeviceMonitor.removeInternalMonitor(this);
       this.internalDeviceMonitor = null;
     }
 
     if (this.nonCallID != -1)
     {
       this.provider.deleteNonCallFromHash(this.nonCallID);
     }
 
     if (this.callID == 0)
       return;
     this.provider.deleteCallFromHash(this.callID);
     this.provider.addCallToSaveHash(this);
     synchronized (this.staleConnections)
     {
       for (int i = 0; i < this.staleConnections.size(); ++i)
       {
         ((TSConnection)this.staleConnections.elementAt(i)).delete();
       }
     }
   }
 
   public int getCallID()
   {
     return this.callID;
   }
 
   int getNonCallID()
   {
     return this.nonCallID;
   }
 
   public void referenced()
   {
     this.refCount += 1;
   }
 
   public void unreferenced()
   {
     this.refCount -= 1;
     if ((this.refCount > 0) || (this.callID != 0))
       return;
     setState(34, null);
     removeObservers(100, null, 0);
   }
 
   void endNonCVDObservers(int cause)
   {
     if ((this.monitorCrossRefID != 0) && (this.wasEverMonitoredByCallsViaDevice != true))
       return;
     removeObservers(cause, null, 0);
   }
 
   public V7DeviceHistoryEntry[] getDeviceHistory()
   {
     return this.deviceHistory;
   }
 
   void setDeviceHistory(V7DeviceHistoryEntry[] deviceHistory)
   {
     this.deviceHistory = deviceHistory;
   }
 
   void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory)
   {
     this.deviceHistory = TsapiPromoter.promoteDeviceHistory(deviceHistory);
   }
 
   public Vector<TSConnection> fastConnect(TSDevice device, String destAddress, CSTAPrivate reqPriv)
     throws TsapiPrivilegeViolationException, TsapiInvalidArgumentException, TsapiInvalidPartyException, TsapiInvalidStateException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
   {
     if (this.provider.getCapabilities().getMakeCall() == 0)
     {
       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
     }
 
     if (this.state != 32)
     {
       throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(this, false), 1, this.state, "call not idle");
     }
 
     String devName = device.getName();
 
     if ((((this.provider.getCapabilities().getSnapshotCallReq() == 0) || (this.monitorPending))) && (this.internalDeviceMonitor == null))
     {
       try
       {
         this.internalDeviceMonitor = device.setInternalMonitor(this);
       }
       catch (Exception e)
       {
       }
 
     }
 
     setConnection_wait_limit(1);
 
     MakeCallConfHandler handler = new MakeCallConfHandler(this, device, destAddress, 24);
     try
     {
       this.provider.tsapi.makeCall(devName, destAddress, reqPriv, handler);
     }
     catch (TsapiInvalidStateException e)
     {
       throw e;
     }
     catch (TsapiInvalidPartyException e)
     {
       throw e;
     }
     catch (TsapiInvalidArgumentException e)
     {
       throw e;
     }
     catch (TsapiPrivilegeViolationException e)
     {
       throw e;
     }
     catch (TsapiResourceUnavailableException e)
     {
       throw e;
     }
     catch (TsapiPlatformException e)
     {
       throw e;
     }
     catch (Exception e)
     {
       if (e instanceof ITsapiException)
       {
         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "makeCall failure");
       }
 
       throw new TsapiPlatformException(4, 0, "makeCall failure");
     }
 
     synchronized (this.connections)
     {
       if (this.handOffCall != null)
       {
         return this.handOffCall.fastConnectFinish(device, destAddress, handler.newCall);
       }
 
       return fastConnectFinish(device, destAddress, handler.newCall);
     }
   }
 
   Vector<TSConnection> fastConnectFinish(TSDevice device, String destAddress, CSTAConnectionID newCall)
   {
     if (device.isMonitorSet())
     {
       this.needSnapshot = false;
     }
     else
     {
       doSnapshot(newCall, null, false);
     }
 
     synchronized (this.connections)
     {
       if (this.connections.size() < 1)
       {
         try
         {
           this.connections.wait(Tsapi.getCallCompletionTimeout());
         }
         catch (InterruptedException e)
         {
         }
 
         if ((this.connections.size() < 1) && (this.state != 34))
         {
           log.info("after succesfully initiating, fastConnect returns null since found a DISCONNECTED originating Connection for call ID " + this.callID);
           return null;
         }
       }
     }
 
     return this.connections;
   }
 
   private int getConnection_wait_limit()
   {
     return this.connection_wait_limit;
   }
 
   private void setConnection_wait_limit(int connection_wait_limit)
   {
     this.connection_wait_limit = connection_wait_limit;
   }
 
   public String toString()
   {
     return "TSCall" + getMyCustomString() + "@" + Integer.toHexString(super.hashCode());
   }
 
   private String getMyCustomString()
   {
     if (this.callID == 0)
     {
       return "[0(nonCallID=" + this.nonCallID + ")]";
     }
 
     return "[" + this.callID + "]";
   }
 
   TSConnection findTSConnectionForDevice(TSDevice device)
   {
     Vector clonedConnsToCheck = new Vector(this.connections);
 
     for (int j = 0; j < clonedConnsToCheck.size(); ++j)
     {
       TSConnection conn = (TSConnection)clonedConnsToCheck.elementAt(j);
       Vector cv = conn.getTermConns();
       if ((cv != null) && (cv.size() > 0))
       {
         Vector termConns = new Vector(cv);
         for (int k = 0; k < termConns.size(); ++k)
         {
           TSConnection tc = (TSConnection)termConns.elementAt(k);
           if (tc.getTSDevice() == device)
           {
             return tc;
           }
 
         }
 
       }
       else if (conn.getTSDevice() == device)
       {
         return conn;
       }
 
     }
 
     return null;
   }
 
   void updateConnectionCallIDs(int newCallID)
   {
     Vector clonedConnectionsToUpdate = new Vector(this.connections);
 
     for (int j = 0; j < clonedConnectionsToUpdate.size(); ++j)
     {
       TSConnection conn = (TSConnection)clonedConnectionsToUpdate.elementAt(j);
       Vector cv = conn.getTermConns();
       if ((cv != null) && (cv.size() > 0))
       {
         Vector termConns = new Vector(cv);
         for (int k = 0; k < termConns.size(); ++k)
         {
           TSConnection tsc = (TSConnection)termConns.elementAt(k);
           tsc.updateConnIDCallID(newCallID);
         }
 
       }
       else
       {
         conn.updateConnIDCallID(newCallID);
       }
     }
   }
 
   boolean getSnapshotCallConfPending()
   {
     return this.snapshotCallConfPending;
   }
 
   void setSnapshotCallConfPending(boolean flag)
   {
     this.snapshotCallConfPending = flag;
   }
 
   boolean getNeedRedoSnapshotCall()
   {
     return this.needRedoSnapshotCall;
   }
 
   void setNeedRedoSnapshotCall(boolean flag)
   {
     this.needRedoSnapshotCall = flag;
   }
 
   public short getCSTACause()
   {
     return this.cstaCause;
   }
 
   void setCSTACause(short cause)
   {
     this.cstaCause = cause;
   }
 
   public void notifyCallAdded(IDomainDevice d)
   {
     recordVDNDomainEntry(d.getDomainName());
   }
 
   public void notifyCallRemoved(IDomainDevice d)
   {
     recordVDNDomainExit();
   }
 
   public int getDomainCallID()
   {
     return getCallID();
   }
 
   void recordVDNDomainEntry(String vdn_domain_we_are_entering)
   {
     log.info("recordVDNDomainEntry: -- entering VDN domain for Address " + vdn_domain_we_are_entering + " - wasEverMonitoredByCallsViaDevice=" + this.wasEverMonitoredByCallsViaDevice + " refVDN=" + this.refVDN);
 
     String found_name = null;
 
     this.wasEverMonitoredByCallsViaDevice = true;
 
     if (this.refVDN != null)
     {
       found_name = this.refVDN.getName();
       if (!found_name.equals(vdn_domain_we_are_entering))
       {
         recordVDNDomainExit();
       }
 
     }
 
     if (this.refVDN != null)
     {
       return;
     }
 
     TSDevice tsDevice = this.provider.createDevice(vdn_domain_we_are_entering);
     this.refVDN = ((TsapiAddress)TsapiCreateObject.getTsapiObject(tsDevice, true));
   }
 
   void recordVDNDomainExit()
   {
     log.info("recordVDNDomainExit: -- leaving VDN domain for Address " + this.refVDN);
 
     this.refVDN = null;
   }
   public boolean hasReceivedCallClearedTransfer() {
     return this.receivedCallClearedTransfer;
   }
 
   public void setReceivedCallClearedTransfer(boolean receivedCallClearedTransfer) {
     this.receivedCallClearedTransfer = receivedCallClearedTransfer;
     this.callClearedTransferReceiptTime = System.currentTimeMillis();
   }
 
   public long getCallClearedTransferReceiptTime() {
     return this.callClearedTransferReceiptTime;
   }
 
   static enum CallUCIDStatus
   {
     OK, 
     UCIDMISMATCH, 
     NONEXISTING;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSCall
 * JD-Core Version:    0.5.4
 */