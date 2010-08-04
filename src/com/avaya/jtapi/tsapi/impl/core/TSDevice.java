/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.ITsapiException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*      */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
/*      */ import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
/*      */ import com.avaya.jtapi.tsapi.TsapiUnableToSendException;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAForwardingInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAMonitorFilter;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfoConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceResponseInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplit;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplitConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLogin;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginResp;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentState;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryCallClassifier;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceName;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceNameConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSetAgentState;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5SetAgentState;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiAddressCapabilities;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiCallControlForwarding;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
/*      */ import com.avaya.jtapi.tsapi.impl.TsapiTerminalCapabilities;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Vector;
/*      */ import javax.telephony.callcontrol.CallControlForwarding;
/*      */ import javax.telephony.capabilities.TerminalCapabilities;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public final class TSDevice
/*      */   implements IDomainDevice
/*      */ {
/*   98 */   private static Logger log = Logger.getLogger(TSDevice.class);
/*      */ 
/*  116 */   static int g_CreationCnt = 0;
/*  117 */   static int g_RefCnt = 0;
/*  118 */   private boolean wasReferenced = false;
/*      */   public static final short STATION = 0;
/*      */   public static final short VDN = 1;
/*      */   public static final short ACD = 2;
/*      */   Object obsSync;
/*      */   TSDeviceState curState;
/*      */   TSProviderImpl provider;
/* 3985 */   int refCount = 0;
/*      */   private final Vector<CSTAExtendedDeviceID> devNameVector;
/* 3994 */   int monitorDeviceXRefID = 0;
/* 3995 */   int monitorCallsViaDeviceXRefID = 0;
/*      */ 
/* 4001 */   private boolean monitorPredictiveCallsViaDevice = false;
/*      */   private final Vector<TSConnection> connections;
/*      */   private final Vector<TSConnection> terminalConnections;
/*      */   private final Vector<TSCall> internalDeviceMonitors;
/*      */   private final Vector<TsapiTerminalMonitor> terminalMonitorThreads;
/*      */   private final Vector<TsapiAddressMonitor> addressMonitorThreads;
/*      */   private final Vector<TsapiCallMonitor> callsAtAddressMonitorThreads;
/*      */   final Vector<TsapiCallMonitor> callsViaAddressMonitorThreads;
/*      */   private final Vector<TsapiCallMonitor> callsAtTerminalMonitorThreads;
/* 4022 */   TsapiRouteMonitor tsRouteCallback = null;
/*      */   int registerReqID;
/*      */   public Hashtable<Integer, TSRouteSession> sessionHash;
/*      */   int numQueued;
/*      */   private final Vector<TSAgent> tsAgentTermVector;
/*      */   private final Vector<TSAgent> tsACDVector;
/*      */   public boolean dndInitialized;
/*      */   public boolean mwiInitialized;
/*      */   public boolean forwardingInitialized;
/*      */   public boolean dndState;
/*      */   public int msgWaitingBits;
/*      */   private final Vector<TsapiCallControlForwarding> fwdVector;
/*      */   private boolean isATerminal;
/*      */   private boolean isExternal;
/* 4047 */   Object replyAddrPriv = null;
/* 4048 */   Object replyTermPriv = null;
/*      */   private short deviceType;
/* 4051 */   boolean waitingForAgents = false;
/*      */ 
/* 4068 */   private boolean asyncInitializationComplete = false;
/*      */   boolean changesWereHeldPending;
/* 4075 */   private String associatedDevice = null;
/*      */   private short associatedClass;
/*      */ 
/*      */   void dump(String indent)
/*      */   {
/*  123 */     log.trace(indent + "***** DEVICE DUMP *****");
/*  124 */     log.trace(indent + "TSDevice: " + this);
/*  125 */     log.trace(indent + "TSDevice names: ");
/*  126 */     synchronized (this.devNameVector)
/*      */     {
/*  128 */       for (int i = 0; i < this.devNameVector.size(); ++i)
/*      */       {
/*  130 */         log.trace(indent + this.devNameVector.elementAt(i));
/*      */       }
/*      */     }
/*      */ 
/*  134 */     log.trace(indent + "TSDevice connections: ");
/*  135 */     Vector connectionsClone = (Vector)this.connections.clone();
/*      */     int i;
/*  137 */     for (i = 0; i < connectionsClone.size(); ++i)
/*      */     {
/*  139 */       TSConnection conn = (TSConnection)connectionsClone.elementAt(i);
/*  140 */       conn.dump(indent + " ");
/*      */     }
/*      */ 
/*  143 */     log.trace(indent + "TSDevice terminalConnections: ");
/*  144 */     Vector terminalConnectionsClone = (Vector)this.terminalConnections.clone();
/*  145 */     TSConnection conn = null;
/*  146 */     for (i = 0; i < terminalConnectionsClone.size(); ++i)
/*      */     {
/*  148 */       conn = (TSConnection)terminalConnectionsClone.elementAt(i);
/*  149 */       conn.dump(indent + " ");
/*      */     }
/*      */ 
/*  152 */     log.trace(indent + "TSDevice ACD Agents: ");
/*  153 */     Vector tsACDVectorClone = (Vector)this.tsACDVector.clone();
/*      */ 
/*  155 */     for (i = 0; i < tsACDVectorClone.size(); ++i)
/*      */     {
/*  157 */       TSAgent agent = (TSAgent)tsACDVectorClone.elementAt(i);
/*  158 */       agent.dump(indent + " ");
/*      */     }
/*      */ 
/*  161 */     log.trace(indent + "TSDevice Terminal Agents: ");
/*  162 */     synchronized (this.tsAgentTermVector)
/*      */     {
/*  164 */       TSAgent agent = null;
/*  165 */       for (i = 0; i < this.tsAgentTermVector.size(); ++i)
/*      */       {
/*  167 */         agent = (TSAgent)this.tsAgentTermVector.elementAt(i);
/*  168 */         agent.dump(indent + " ");
/*      */       }
/*      */     }
/*  171 */     if (this.sessionHash != null)
/*      */     {
/*  173 */       log.trace(indent + "TSDevice Route Sessions: ");
/*  174 */       synchronized (this.sessionHash)
/*      */       {
/*  176 */         Enumeration sessionEnum = this.sessionHash.elements();
/*      */ 
/*  178 */         while (sessionEnum.hasMoreElements())
/*      */         {
/*      */           TSRouteSession routeSession;
/*      */           try {
/*  182 */             routeSession = (TSRouteSession)sessionEnum.nextElement();
/*      */           }
/*      */           catch (NoSuchElementException e)
/*      */           {
/*  186 */             log.error(e.getMessage(), e);
continue;
/*  187 */           }
/*      */ 
/*  189 */           routeSession.dump(indent + " ");
/*      */         }
/*      */       }
/*      */     }
/*  193 */     log.trace(indent + "TSDevice Terminal Monitor Threads: ");
/*      */ 
/*  195 */     Vector terminalMonitorThreadsClone = (Vector)this.terminalMonitorThreads.clone();
/*      */     int j;
/*  197 */     for (j = 0; j < terminalMonitorThreadsClone.size(); ++j)
/*      */     {
/*  199 */       TsapiTerminalMonitor tOThreads = (TsapiTerminalMonitor)terminalMonitorThreadsClone.elementAt(j);
/*  200 */       tOThreads.dump(indent + " ");
/*      */     }
/*      */ 
/*  203 */     log.trace(indent + "TSDevice Address Monitor Threads: ");
/*      */ 
/*  205 */     Vector addressMonitorThreadsClone = (Vector)this.addressMonitorThreads.clone();
/*      */     int k;
/*  207 */     for (k = 0; k < addressMonitorThreadsClone.size(); ++k)
/*      */     {
/*  209 */       TsapiAddressMonitor aOThreads = (TsapiAddressMonitor)addressMonitorThreadsClone.elementAt(k);
/*  210 */       aOThreads.dump(indent + " ");
/*      */     }
/*      */ 
/*  213 */     log.trace(indent + "TSDevice Calls At Address Monitor Threads: ");
/*  214 */     Vector callsAtAddressMonitorThreadsClone = (Vector)this.callsAtAddressMonitorThreads.clone();
/*      */ 
/*  216 */     for (i = 0; i < callsAtAddressMonitorThreadsClone.size(); ++i)
/*      */     {
/*  218 */       TsapiCallMonitor cAAOThreads = (TsapiCallMonitor)callsAtAddressMonitorThreadsClone.elementAt(i);
/*  219 */       cAAOThreads.dump(indent + " ");
/*      */     }
/*      */ 
/*  222 */     log.trace(indent + "TSDevice Calls Via Address Monitor Threads: ");
/*  223 */     Vector callsViaAddressMonitorThreadsClone = (Vector)this.callsViaAddressMonitorThreads.clone();
/*      */ 
/*  225 */     for (i = 0; i < callsViaAddressMonitorThreadsClone.size(); ++i)
/*      */     {
/*  227 */       TsapiCallMonitor cVAOThreads = (TsapiCallMonitor)callsViaAddressMonitorThreadsClone.elementAt(i);
/*  228 */       cVAOThreads.dump(indent + " ");
/*      */     }
/*      */ 
/*  231 */     log.trace(indent + "TSDevice Calls At Terminal Monitor Threads: ");
/*  232 */     Vector callsAtTerminalMonitorThreadsClone = (Vector)this.callsAtTerminalMonitorThreads.clone();
/*      */ 
/*  234 */     for (i = 0; i < callsAtTerminalMonitorThreadsClone.size(); ++i)
/*      */     {
/*  236 */       TsapiCallMonitor cATOThreads = (TsapiCallMonitor)callsAtTerminalMonitorThreadsClone.elementAt(i);
/*  237 */       cATOThreads.dump(indent + " ");
/*      */     }
/*  239 */     log.trace(indent + "wasReferenced is " + this.wasReferenced);
/*  240 */     log.trace(indent + "***** DEVICE DUMP END *****");
/*      */   }
/*      */ 
/*      */   synchronized void recreate()
/*      */   {
/*  248 */     this.curState.recreate(this);
/*      */   }
/*      */ 
/*      */   synchronized void internalRecreate()
/*      */   {
/*  272 */     Vector keys = new Vector(this.devNameVector);
/*  273 */     log.info("Recreating deleted device " + this);
/*  274 */     for (int i = 0; i < keys.size(); ++i)
/*      */     {
/*  276 */       String key = ((CSTAExtendedDeviceID)keys.elementAt(i)).getDeviceID();
/*  277 */       this.provider.addDeviceToHash(key, this);
/*      */     }
/*      */ 
/*  281 */     setState(new TSDeviceStateActive());
/*      */ 
/*  283 */     log.info("Device " + ((CSTAExtendedDeviceID)this.devNameVector.lastElement()).getDeviceID() + " (object= " + this + ") being re-added" + " for " + this.provider);
/*      */   }
/*      */ 
/*      */   public String getDirectoryName()
/*      */   {
/*  296 */     recreate();
/*      */     try
/*      */     {
/*  300 */       LucentQueryDeviceName lqdn = new LucentQueryDeviceName(getName());
/*  301 */       Object lqdnConf = this.provider.sendPrivateData(lqdn.makeTsapiPrivate(), null, true);
/*  302 */       if (lqdnConf instanceof LucentQueryDeviceNameConfEvent)
/*      */       {
/*  304 */         return ((LucentQueryDeviceNameConfEvent)lqdnConf).getName();
/*      */       }
/*  306 */       return null;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/*  310 */       if ((e.getErrorType() == 2) && (e.getErrorCode() == 12))
/*      */       {
/*  313 */         return null;
/*      */       }
/*      */ 
/*  317 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  322 */       if (e instanceof TsapiInvalidArgumentException)
/*      */       {
/*  324 */         return null;
/*      */       }
/*  326 */       if (e instanceof ITsapiException)
/*      */       {
/*  328 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "queryDeviceName failure");
/*      */       }
/*      */ 
/*  332 */       throw new TsapiPlatformException(4, 0, "queryDeviceName failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public TSProviderImpl getTSProviderImpl()
/*      */   {
/*  342 */     recreate();
/*      */ 
/*  344 */     return this.provider;
/*      */   }
/*      */ 
/*      */   public Vector<TSConnection> getTSConnections()
/*      */   {
/*  353 */     recreate();
/*      */ 
/*  355 */     updateObject();
/*  356 */     return getConns();
/*      */   }
/*      */ 
/*      */   Vector<TSConnection> getConns()
/*      */   {
/*  365 */     recreate();
/*      */ 
/*  367 */     return (Vector)(Vector)this.connections.clone();
/*      */   }
/*      */ 
/*      */   public Vector<TSConnection> getTSTerminalConnections()
/*      */   {
/*  376 */     recreate();
/*      */ 
/*  378 */     updateObject();
/*  379 */     return getTermConns();
/*      */   }
/*      */ 
/*      */   Vector<TSConnection> getTermConns()
/*      */   {
/*  389 */     recreate();
/*      */ 
/*  391 */     return (Vector)(Vector)this.terminalConnections.clone();
/*      */   }
/*      */ 
/*      */   public Vector<TSDevice> getTSTerminalDevices()
/*      */   {
/*  397 */     recreate();
/*      */ 
/*  399 */     Vector devVector = new Vector();
/*  400 */     if (isTerminal())
/*      */     {
/*  402 */       devVector.addElement(this);
/*      */     }
/*  404 */     return devVector;
/*      */   }
/*      */ 
/*      */   public Vector<TSDevice> getTSAddressDevices()
/*      */   {
/*  410 */     recreate();
/*      */ 
/*  412 */     Vector devVector = new Vector();
/*      */ 
/*  414 */     devVector.addElement(this);
/*  415 */     return devVector;
/*      */   }
/*      */ 
/*      */   public void addAddressMonitor(TsapiAddressMonitor obs)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*  424 */     recreate();
/*      */ 
/*  428 */     if (this.provider.getCapabilities().getMonitorDevice() == 0)
/*      */     {
/*  430 */       throw new TsapiResourceUnavailableException(0, 0, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*  434 */     synchronized (this.obsSync)
/*      */     {
/*  436 */       if (this.addressMonitorThreads.contains(obs))
/*      */       {
/*  438 */         return;
/*      */       }
/*      */ 
/*  442 */       this.addressMonitorThreads.addElement(obs);
/*      */       try
/*      */       {
/*  446 */         setMonitor(false, false);
/*      */ 
/*  449 */         obs.addReference();
/*      */       }
/*      */       catch (TsapiResourceUnavailableException e)
/*      */       {
/*  461 */         this.provider.removeAddressMonitorThread(obs);
/*  462 */         this.addressMonitorThreads.removeElement(obs);
/*  463 */         throw e;
/*      */       }
/*      */       catch (TsapiPlatformException e)
/*      */       {
/*  475 */         this.provider.removeAddressMonitorThread(obs);
/*  476 */         this.addressMonitorThreads.removeElement(obs);
/*  477 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/*  481 */     sendAddressSnapshot(obs);
/*      */   }
/*      */ 
/*      */   public void removeAddressMonitor(TsapiAddressMonitor obs)
/*      */   {
/*  489 */     recreate();
/*      */ 
/*  491 */     removeAddressMonitor(obs, 100, null);
/*      */   }
/*      */ 
/*      */   protected void removeAddressMonitor(TsapiAddressMonitor obs, int cause, Object privateData) {
/*  495 */     recreate();
/*      */ 
/*  497 */     if (!this.addressMonitorThreads.removeElement(obs))
/*      */       return;
/*  499 */     obs.deleteReference(this, cause, privateData);
/*  500 */     testDelete();
/*      */   }
/*      */ 
/*      */   public void addTerminalMonitor(TsapiTerminalMonitor obs)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*  510 */     recreate();
/*      */ 
/*  514 */     if (this.provider.getCapabilities().getMonitorDevice() == 0)
/*      */     {
/*  516 */       throw new TsapiResourceUnavailableException(0, 0, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*  520 */     synchronized (this.obsSync)
/*      */     {
/*  522 */       if (this.terminalMonitorThreads.contains(obs))
/*      */       {
/*  524 */         return;
/*      */       }
/*      */ 
/*  528 */       this.terminalMonitorThreads.addElement(obs);
/*      */       try
/*      */       {
/*  532 */         setMonitor(false, false);
/*      */ 
/*  536 */         obs.addReference();
/*      */       }
/*      */       catch (TsapiResourceUnavailableException e)
/*      */       {
/*  548 */         this.provider.removeTerminalMonitorThread(obs);
/*  549 */         this.terminalMonitorThreads.removeElement(obs);
/*  550 */         throw e;
/*      */       }
/*      */       catch (TsapiPlatformException e)
/*      */       {
/*  562 */         this.provider.removeTerminalMonitorThread(obs);
/*  563 */         this.terminalMonitorThreads.removeElement(obs);
/*  564 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/*  568 */     sendTerminalSnapshot(obs);
/*      */   }
/*      */ 
/*      */   public void removeTerminalMonitor(TsapiTerminalMonitor obs)
/*      */   {
/*  576 */     recreate();
/*      */ 
/*  578 */     removeTerminalMonitor(obs, 100, null);
/*      */   }
/*      */ 
/*      */   protected void removeTerminalMonitor(TsapiTerminalMonitor obs, int cause, Object privateData)
/*      */   {
/*  585 */     recreate();
/*      */ 
/*  587 */     if (!this.terminalMonitorThreads.removeElement(obs))
/*      */       return;
/*  589 */     obs.deleteReference(this, cause, privateData);
/*  590 */     testDelete();
/*      */   }
/*      */ 
/*      */   public void addAddressCallMonitor(TsapiCallMonitor obs, boolean followCall)
/*      */     throws TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  601 */     addAddressCallMonitor(obs, followCall, false, null);
/*      */   }
/*      */ 
/*      */   public void addAddressCallMonitor(TsapiCallMonitor obs, boolean followCall, boolean predictive, CSTAPrivate reqPriv)
/*      */     throws TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  612 */     recreate();
/*      */ 
/*  614 */     if (followCall)
/*      */     {
/*  618 */       if (this.provider.getCapabilities().getMonitorCallsViaDevice() == 0)
/*      */       {
/*  620 */         throw new TsapiMethodNotSupportedException(0, 0, "unsupported by driver");
/*      */       }
/*  622 */       if ((predictive) && (!this.provider.getMonitorCallsViaDevice()))
/*      */       {
/*  624 */         throw new TsapiMethodNotSupportedException(0, 0, "unsupported by driver");
/*      */       }
/*      */ 
/*      */     }
/*  631 */     else if (this.provider.getCapabilities().getMonitorDevice() == 0)
/*      */     {
/*  633 */       throw new TsapiResourceUnavailableException(0, 0, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*  637 */     synchronized (this.obsSync)
/*      */     {
/*  639 */       if (this.callsViaAddressMonitorThreads.contains(obs))
/*      */       {
/*  641 */         return;
/*      */       }
/*      */ 
/*  644 */       if (followCall)
/*      */       {
/*  646 */         if (this.callsAtAddressMonitorThreads.removeElement(obs))
/*      */         {
/*  649 */           this.callsViaAddressMonitorThreads.addElement(obs);
/*      */           try
/*      */           {
/*  654 */             setMonitor(true, true, predictive, reqPriv);
/*      */           }
/*      */           catch (TsapiResourceUnavailableException e)
/*      */           {
/*  669 */             this.provider.removeCallMonitorThread(obs);
/*  670 */             this.callsViaAddressMonitorThreads.removeElement(obs);
/*  671 */             throw e;
/*      */           }
/*      */           catch (TsapiPlatformException e)
/*      */           {
/*  683 */             this.provider.removeCallMonitorThread(obs);
/*  684 */             this.callsViaAddressMonitorThreads.removeElement(obs);
/*  685 */             throw e;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  691 */           this.callsViaAddressMonitorThreads.addElement(obs);
/*      */           try
/*      */           {
/*  696 */             setMonitor(true, true, predictive, reqPriv);
/*      */ 
/*  700 */             obs.addReference();
/*      */           }
/*      */           catch (TsapiResourceUnavailableException e)
/*      */           {
/*  712 */             this.provider.removeCallMonitorThread(obs);
/*  713 */             this.callsViaAddressMonitorThreads.removeElement(obs);
/*  714 */             throw e;
/*      */           }
/*      */           catch (TsapiPlatformException e)
/*      */           {
/*  726 */             this.provider.removeCallMonitorThread(obs);
/*  727 */             this.callsViaAddressMonitorThreads.removeElement(obs);
/*  728 */             throw e;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */       else {
/*  734 */         synchronized (this.obsSync)
/*      */         {
/*  736 */           if (this.callsAtAddressMonitorThreads.contains(obs))
/*      */           {
/*  738 */             return;
/*      */           }
/*      */ 
/*  742 */           this.callsAtAddressMonitorThreads.addElement(obs);
/*      */           try
/*      */           {
/*  746 */             setMonitor(false, true);
/*      */ 
/*  749 */             obs.addReference();
/*      */           }
/*      */           catch (TsapiResourceUnavailableException e)
/*      */           {
/*  761 */             this.provider.removeCallMonitorThread(obs);
/*  762 */             this.callsAtAddressMonitorThreads.removeElement(obs);
/*  763 */             throw e;
/*      */           }
/*      */           catch (TsapiPlatformException e)
/*      */           {
/*  775 */             this.provider.removeCallMonitorThread(obs);
/*  776 */             this.callsAtAddressMonitorThreads.removeElement(obs);
/*  777 */             throw e;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  784 */     sendCallSnapshot(obs, false);
/*      */   }
/*      */ 
/*      */   Vector<TsapiCallMonitor> getCVDObservers()
/*      */   {
/*  790 */     recreate();
/*      */ 
/*  792 */     Vector obs = (Vector)this.callsViaAddressMonitorThreads.clone();
/*  793 */     return obs;
/*      */   }
/*      */ 
/*      */   public Vector<TsapiCallMonitor> getAddressCallObservers()
/*      */   {
/*  799 */     recreate();
/*      */ 
/*  801 */     Vector obs = (Vector)this.callsViaAddressMonitorThreads.clone();
/*  802 */     for (int i = 0; i < this.callsAtAddressMonitorThreads.size(); ++i)
/*      */     {
/*  804 */       obs.addElement(this.callsAtAddressMonitorThreads.elementAt(i));
/*      */     }
/*  806 */     return obs;
/*      */   }
/*      */ 
/*      */   public void removeAddressCallMonitor(TsapiCallMonitor obs)
/*      */   {
/*  814 */     recreate();
/*      */ 
/*  816 */     removeAddressCallMonitor(obs, 100, null);
/*      */   }
/*      */ 
/*      */   protected void removeAddressCallMonitor(TsapiCallMonitor obs, int cause, Object privateData)
/*      */   {
/*  824 */     recreate();
/*      */ 
/*  826 */     if (!this.callsViaAddressMonitorThreads.removeElement(obs))
/*      */     {
/*  828 */       this.callsAtAddressMonitorThreads.removeElement(obs);
/*      */     }
/*  830 */     obs.deleteReference(this, false, cause, privateData);
/*      */ 
/*  832 */     testDelete();
/*      */   }
/*      */ 
/*      */   public void addTerminalCallMonitor(TsapiCallMonitor obs)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*  841 */     recreate();
/*      */ 
/*  845 */     if (this.provider.getCapabilities().getMonitorDevice() == 0)
/*      */     {
/*  847 */       throw new TsapiResourceUnavailableException(0, 0, 0, "unsupported by driver");
/*      */     }
/*      */ 
/*  850 */     synchronized (this.obsSync)
/*      */     {
/*  853 */       if (this.callsAtTerminalMonitorThreads.contains(obs))
/*      */       {
/*  855 */         return;
/*      */       }
/*      */ 
/*  858 */       this.callsAtTerminalMonitorThreads.addElement(obs);
/*      */       try
/*      */       {
/*  862 */         setMonitor(false, true);
/*      */ 
/*  865 */         obs.addReference();
/*      */       }
/*      */       catch (TsapiResourceUnavailableException e)
/*      */       {
/*  877 */         this.provider.removeCallMonitorThread(obs);
/*  878 */         this.callsAtTerminalMonitorThreads.removeElement(obs);
/*  879 */         throw e;
/*      */       }
/*      */       catch (TsapiPlatformException e)
/*      */       {
/*  891 */         this.provider.removeCallMonitorThread(obs);
/*  892 */         this.callsAtTerminalMonitorThreads.removeElement(obs);
/*  893 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/*  897 */     sendCallSnapshot(obs, true);
/*      */   }
/*      */ 
/*      */   public Vector<TsapiCallMonitor> getTerminalCallObservers()
/*      */   {
/*  903 */     recreate();
/*      */ 
/*  905 */     return (Vector)this.callsAtTerminalMonitorThreads.clone();
/*      */   }
/*      */ 
/*      */   public void removeTerminalCallMonitor(TsapiCallMonitor obs)
/*      */   {
/*  913 */     recreate();
/*      */ 
/*  915 */     removeTerminalCallMonitor(obs, 100, null);
/*      */   }
/*      */ 
/*      */   protected void removeTerminalCallMonitor(TsapiCallMonitor obs, int cause, Object privateData)
/*      */   {
/*  923 */     recreate();
/*      */ 
/*  925 */     this.callsAtTerminalMonitorThreads.removeElement(obs);
/*      */ 
/*  927 */     obs.deleteReference(this, true, cause, privateData);
/*      */ 
/*  929 */     testDelete();
/*      */   }
/*      */ 
/*      */   public TsapiAddressCapabilities getTsapiAddressCapabilities()
/*      */   {
/*  937 */     recreate();
/*      */ 
/*  939 */     return this.provider.getTsapiAddressCapabilities();
/*      */   }
/*      */ 
/*      */   public TsapiTerminalCapabilities getTsapiTerminalCapabilities()
/*      */   {
/*  947 */     recreate();
/*      */ 
/*  949 */     return this.provider.getTsapiTerminalCapabilities();
/*      */   }
/*      */ 
/*      */   TerminalCapabilities getTerminalCapabilities(TSDevice cevice)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/*  958 */     recreate();
/*      */ 
/*  960 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean getDoNotDisturb()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  968 */     recreate();
/*      */ 
/*  970 */     if (this.provider.getCapabilities().getDoNotDisturbEvent() == 0)
/*      */     {
/*  972 */       if (this.provider.getCapabilities().getQueryDnd() == 0)
/*      */       {
/*  974 */         throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */       }
/*      */ 
/*  977 */       ConfHandler handler = new DNDConfHandler(this);
/*      */       try
/*      */       {
/*  980 */         this.provider.tsapi.queryDoNotDisturb(getName(), null, handler);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/*  987 */         throw tue;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  991 */         log.error(e.getMessage(), e);
/*  992 */         return this.dndState;
/*      */       }
/*      */     }
/*  995 */     return this.dndState;
/*      */   }
/*      */ 
/*      */   public void setDoNotDisturb(boolean enable, CSTAPrivate reqPriv)
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 1002 */     recreate();
/*      */ 
/* 1004 */     if (this.provider.getCapabilities().getSetDnd() == 0)
/*      */     {
/* 1006 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1009 */     ConfHandler handler = new DNDConfHandler(this, enable);
/*      */     try
/*      */     {
/* 1012 */       this.provider.tsapi.setDnd(getName(), enable, reqPriv, handler);
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 1016 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1020 */       if (e instanceof ITsapiException) {
/* 1021 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setDnd failure");
/*      */       }
/* 1023 */       throw new TsapiPlatformException(4, 0, "setDnd failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getMessageWaitingBits()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 1030 */     recreate();
/*      */ 
/* 1032 */     if (this.provider.getCapabilities().getMessageWaitingEvent() == 0)
/*      */     {
/* 1034 */       if (this.provider.getCapabilities().getQueryMwi() == 0)
/*      */       {
/* 1036 */         throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */       }
/*      */ 
/* 1039 */       ConfHandler handler = new MWIConfHandler(this);
/*      */       try
/*      */       {
/* 1042 */         this.provider.tsapi.queryMsgWaitingInd(getName(), null, handler);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/* 1049 */         throw tue;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1053 */         log.error(e.getMessage(), e);
/* 1054 */         return this.msgWaitingBits;
/*      */       }
/*      */     }
/*      */ 
/* 1058 */     return this.msgWaitingBits;
/*      */   }
/*      */ 
/*      */   public void setMessageWaiting(boolean enable, CSTAPrivate reqPriv)
/*      */     throws TsapiInvalidStateException, TsapiMethodNotSupportedException
/*      */   {
/* 1066 */     recreate();
/*      */ 
/* 1068 */     if (this.provider.getCapabilities().getSetMwi() == 0)
/*      */     {
/* 1070 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1073 */     ConfHandler handler = new MWIConfHandler(this, this.msgWaitingBits |= 268435456);
/*      */     try
/*      */     {
/* 1076 */       this.provider.tsapi.setMsgWaitingInd(getName(), enable, reqPriv, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1080 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 1084 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1088 */       if (e instanceof ITsapiException) {
/* 1089 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setMsgWaitingInd failure");
/*      */       }
/* 1091 */       throw new TsapiPlatformException(4, 0, "setMsgWaitingInd failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setForwarding(Vector<TsapiCallControlForwarding> _fwdVector, CSTAPrivate reqPriv)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiMethodNotSupportedException
/*      */   {
/* 1100 */     recreate();
/*      */ 
/* 1102 */     if (this.provider.getCapabilities().getSetFwd() == 0)
/*      */     {
/* 1104 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1108 */     cancelForwarding(null);
/*      */ 
/* 1111 */     CSTAForwardingInfo[] fwdArray = new CSTAForwardingInfo[1];
/*      */ 
/* 1113 */     short forwardingType = 0;
/* 1114 */     boolean forwardingOn = true;
/*      */ 
/* 1116 */     synchronized (_fwdVector) {
/* 1117 */       for (int i = 0; i < _fwdVector.size(); ++i) {
/* 1118 */         TsapiCallControlForwarding fwd = (TsapiCallControlForwarding)_fwdVector.elementAt(i);
/* 1119 */         String forwardDN = fwd.getDestinationAddress();
/* 1120 */         switch (fwd.getType())
/*      */         {
/*      */         case 2:
/* 1123 */           switch (fwd.getFilter())
/*      */           {
/*      */           case 3:
/* 1126 */             forwardingType = 4;
/* 1127 */             break;
/*      */           case 2:
/* 1129 */             forwardingType = 3;
/* 1130 */             break;
/*      */           case 1:
/* 1132 */             forwardingType = 1;
/*      */           }
/*      */ 
/* 1135 */           break;
/*      */         case 3:
/* 1137 */           switch (fwd.getFilter())
/*      */           {
/*      */           case 3:
/* 1140 */             forwardingType = 6;
/* 1141 */             break;
/*      */           case 2:
/* 1143 */             forwardingType = 5;
/* 1144 */             break;
/*      */           case 1:
/* 1146 */             forwardingType = 2;
/*      */           }
/*      */ 
/* 1149 */           break;
/*      */         case 1:
/* 1151 */           forwardingType = 0;
/*      */         }
/*      */ 
/* 1155 */         fwdArray[0] = new CSTAForwardingInfo(forwardingType, forwardingOn, forwardDN);
/*      */ 
/* 1157 */         ConfHandler handler = new FwdConfHandler(this, fwdArray);
/*      */         try
/*      */         {
/* 1160 */           this.provider.tsapi.setFwd(getName(), forwardingType, forwardingOn, forwardDN, reqPriv, handler);
/*      */         }
/*      */         catch (TsapiInvalidStateException e)
/*      */         {
/* 1165 */           throw e;
/*      */         }
/*      */         catch (TsapiInvalidArgumentException e)
/*      */         {
/* 1169 */           throw e;
/*      */         }
/*      */         catch (TsapiPlatformException e)
/*      */         {
/* 1173 */           throw e;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1177 */           if (e instanceof ITsapiException) {
/* 1178 */             throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setFwd failure");
/*      */           }
/* 1180 */           throw new TsapiPlatformException(4, 0, "setFwd failure");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<TsapiCallControlForwarding> getForwarding()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 1189 */     recreate();
/*      */ 
/* 1191 */     if ((this.provider.getCapabilities().getForwardingEvent() == 0) || (this.fwdVector.size() == 0))
/*      */     {
/* 1194 */       if (this.provider.getCapabilities().getQueryFwd() == 0)
/*      */       {
/* 1196 */         throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */       }
/*      */ 
/* 1199 */       ConfHandler handler = new FwdConfHandler(this);
/*      */       try
/*      */       {
/* 1202 */         this.provider.tsapi.queryFwd(getName(), null, handler);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/* 1209 */         throw tue;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1213 */         log.error(e.getMessage(), e);
/* 1214 */         return this.fwdVector;
/*      */       }
/*      */     }
/*      */ 
/* 1218 */     return this.fwdVector;
/*      */   }
/*      */ 
/*      */   public void cancelForwarding(CSTAPrivate reqPriv)
/*      */     throws TsapiInvalidStateException, TsapiMethodNotSupportedException
/*      */   {
/* 1226 */     recreate();
/*      */ 
/* 1228 */     if (this.provider.getCapabilities().getSetFwd() == 0)
/*      */     {
/* 1230 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1234 */     getForwarding();
/*      */ 
/* 1237 */     CSTAForwardingInfo[] fwdArray = new CSTAForwardingInfo[1];
/*      */ 
/* 1239 */     short forwardingType = 0;
/* 1240 */     boolean forwardingOn = false;
/*      */ 
/* 1242 */     synchronized (this.fwdVector) {
/* 1243 */       for (int i = 0; i < this.fwdVector.size(); ++i)
/*      */       {
/* 1245 */         TsapiCallControlForwarding fwd = (TsapiCallControlForwarding)this.fwdVector.elementAt(i);
/* 1246 */         String forwardDN = fwd.getDestinationAddress();
/* 1247 */         switch (fwd.getType())
/*      */         {
/*      */         case 2:
/* 1250 */           switch (fwd.getFilter())
/*      */           {
/*      */           case 3:
/* 1253 */             forwardingType = 4;
/* 1254 */             break;
/*      */           case 2:
/* 1256 */             forwardingType = 3;
/* 1257 */             break;
/*      */           case 1:
/* 1259 */             forwardingType = 1;
/*      */           }
/*      */ 
/* 1262 */           break;
/*      */         case 3:
/* 1264 */           switch (fwd.getFilter())
/*      */           {
/*      */           case 3:
/* 1267 */             forwardingType = 6;
/* 1268 */             break;
/*      */           case 2:
/* 1270 */             forwardingType = 5;
/* 1271 */             break;
/*      */           case 1:
/* 1273 */             forwardingType = 2;
/*      */           }
/*      */ 
/* 1276 */           break;
/*      */         case 1:
/* 1278 */           forwardingType = 0;
/*      */         }
/*      */ 
/* 1281 */         fwdArray[0] = new CSTAForwardingInfo(forwardingType, forwardingOn, forwardDN);
/* 1282 */         ConfHandler handler = new FwdConfHandler(this, fwdArray);
/*      */         try
/*      */         {
/* 1285 */           this.provider.tsapi.setFwd(getName(), forwardingType, forwardingOn, forwardDN, reqPriv, handler);
/*      */         }
/*      */         catch (TsapiInvalidStateException e)
/*      */         {
/* 1290 */           throw e;
/*      */         }
/*      */         catch (TsapiPlatformException e)
/*      */         {
/* 1294 */           throw e;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1298 */           if (e instanceof ITsapiException) {
/* 1299 */             throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setFwd failure");
/*      */           }
/* 1301 */           throw new TsapiPlatformException(4, 0, "setFwd failure");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public TSConnection pickup(TSConnection pickConnection, TSDevice terminalAddress, CSTAPrivate privData)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/* 1315 */     recreate();
/*      */ 
/* 1317 */     if (this.provider.getCapabilities().getPickupCall() == 0)
/*      */     {
/* 1319 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1322 */     if (pickConnection.getTSCall().updateObject())
/*      */     {
/* 1324 */       int state = pickConnection.getTSConnState();
/* 1325 */       if ((state != 50) && (state != 49) && (state != 54))
/*      */       {
/* 1328 */         throw new TsapiInvalidStateException(3, 0, TsapiCreateObject.getTsapiObject(pickConnection, true), 2, state, "connection not alerting or in progress");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1336 */     ConfHandler handler = new PickupConfHandler(this, terminalAddress, pickConnection);
/*      */     try
/*      */     {
/* 1339 */       this.provider.tsapi.pickupCall(pickConnection.getConnID(), terminalAddress.getName(), privData, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1343 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/* 1347 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/* 1351 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 1355 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 1359 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1363 */       if (e instanceof ITsapiException) {
/* 1364 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "pickupCall failure");
/*      */       }
/* 1366 */       throw new TsapiPlatformException(4, 0, "pickupCall failure");
/*      */     }
/*      */ 
/* 1371 */     throw new TsapiPlatformException(4, 0, "Could not meet post-conditions of pickup()");
/*      */   }
/*      */ 
/*      */   public TSConnection pickup(TSDevice pickAddress, TSDevice terminalAddress, CSTAPrivate privData)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/* 1382 */     recreate();
/*      */ 
/* 1384 */     if (this.provider.getCapabilities().getPickupCall() == 0)
/*      */     {
/* 1386 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/* 1388 */     TSConnection pickConnection = null;
/*      */ 
/* 1390 */     pickAddress.updateObject();
/* 1391 */     synchronized (pickAddress.connections)
/*      */     {
/* 1393 */       for (int i = 0; i < pickAddress.connections.size(); ++i)
/*      */       {
/* 1395 */         pickConnection = (TSConnection)pickAddress.connections.elementAt(i);
/* 1396 */         if ((pickConnection.getTSConnState() == 50) || (pickConnection.getTSConnState() == 49) || (pickConnection.getTSConnState() == 54))
/*      */         {
/* 1400 */           return pickup(pickConnection, terminalAddress, privData);
/*      */         }
/*      */       }
/*      */     }
/* 1404 */     throw new TsapiInvalidArgumentException(3, 0, "no connection found to pickup");
/*      */   }
/*      */ 
/*      */   public TSConnection groupPickup(TSDevice terminalAddress, CSTAPrivate privData)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/* 1416 */     recreate();
/*      */ 
/* 1418 */     if (this.provider.getCapabilities().getGroupPickupCall() == 0)
/*      */     {
/* 1420 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */     }
/*      */ 
/* 1423 */     ConfHandler handler = new TermPrivConfHandler(this, 20);
/*      */     try
/*      */     {
/* 1426 */       this.provider.tsapi.groupPickupCall(terminalAddress.getName(), privData, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1430 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/* 1434 */       throw e;
/*      */     }
/*      */     catch (TsapiPrivilegeViolationException e)
/*      */     {
/* 1438 */       throw e;
/*      */     }
/*      */     catch (TsapiResourceUnavailableException e)
/*      */     {
/* 1442 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 1446 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1450 */       if (e instanceof ITsapiException) {
/* 1451 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "groupPickupCall failure");
/*      */       }
/* 1453 */       throw new TsapiPlatformException(4, 0, "groupPickupCall failure");
/*      */     }
/*      */ 
/* 1458 */     throw new TsapiPlatformException(4, 0, "Could not meet post-conditions of groupPickup()");
/*      */   }
/*      */ 
/*      */   void setTSAgent(String acdDeviceName, int state, String agentID, String password, CSTAPrivate reqPriv)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException
/*      */   {
/* 1472 */     recreate();
/*      */     int agentMode;
/* 1475 */     switch (state)
/*      */     {
/*      */     case 1:
/* 1478 */       agentMode = 0;
/* 1479 */       break;
/*      */     case 2:
/* 1481 */       agentMode = 1;
/* 1482 */       break;
/*      */     case 4:
/* 1484 */       agentMode = 3;
/* 1485 */       break;
/*      */     case 3:
/* 1487 */       agentMode = 2;
/* 1488 */       break;
/*      */     case 6:
/* 1490 */       agentMode = 5;
/* 1491 */       break;
/*      */     case 5:
/* 1493 */       agentMode = 4;
/* 1494 */       break;
/*      */     case 0:
/*      */     case 7:
/*      */     default:
/* 1500 */       throw new TsapiPlatformException(4, 0, "Unknown Agent state [" + state + "]");
/*      */     }
/*      */ 
/* 1504 */     ConfHandler handler = new LucentSetAgentStateConfHandler(this);
/*      */     try
/*      */     {
/* 1507 */       this.provider.tsapi.setAgentState(getName(), (short)agentMode, agentID, acdDeviceName, password, reqPriv, handler);
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1512 */       throw e;
/*      */     }
/*      */     catch (TsapiInvalidArgumentException e)
/*      */     {
/* 1516 */       throw e;
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 1520 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1524 */       if (e instanceof ITsapiException) {
/* 1525 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "setAgentState failure");
/*      */       }
/* 1527 */       throw new TsapiPlatformException(4, 0, "setAgentState failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   void addToAgentTermVector(TSAgent tsAgent)
/*      */   {
/* 1533 */     recreate();
/*      */ 
/* 1535 */     this.tsAgentTermVector.addElement(tsAgent);
/*      */   }
/*      */ 
/*      */   void addToACDVector(TSAgent tsAgent)
/*      */   {
/* 1540 */     recreate();
/*      */ 
/* 1542 */     this.tsACDVector.addElement(tsAgent);
/*      */   }
/*      */ 
/*      */   void removeFromAgentTermVector(TSAgent tsAgent)
/*      */   {
/* 1547 */     recreate();
/*      */ 
/* 1549 */     this.tsAgentTermVector.removeElement(tsAgent);
/* 1550 */     testDelete();
/*      */   }
/*      */ 
/*      */   void removeFromACDVector(TSAgent tsAgent)
/*      */   {
/* 1555 */     recreate();
/*      */ 
/* 1557 */     this.tsACDVector.removeElement(tsAgent);
/* 1558 */     testDelete();
/*      */   }
/*      */ 
/*      */   private LucentSetAgentState createLucentSetAgentState(short workMode, int reasonCode)
/*      */   {
/* 1566 */     if (this.provider.isLucentV5()) {
/* 1567 */       return new LucentV5SetAgentState(workMode, reasonCode);
/*      */     }
/* 1569 */     return new LucentSetAgentState(workMode);
/*      */   }
/*      */ 
/*      */   private LucentSetAgentState createLucentSetAgentState(short workMode)
/*      */   {
/* 1574 */     return createLucentSetAgentState(workMode, 0);
/*      */   }
/*      */ 
/*      */   public TSAgent addTSAgent(TSDevice tsACDDevice, int initialState, int workMode, int reasonCode, String agentID, String password, CSTAPrivate reqPriv)
/*      */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*      */   {
/* 1584 */     recreate();
/*      */ 
/* 1586 */     String acdName = null;
/*      */ 
/* 1588 */     if (tsACDDevice != null) {
/* 1589 */       acdName = tsACDDevice.getName();
/*      */     }
/*      */ 
/* 1592 */     TSAgentKey agentKey = null;
/* 1593 */     agentKey = new TSAgentKey(getName(), acdName, agentID);
/* 1594 */     TSAgent tsAgent = null;
/* 1595 */     tsAgent = this.provider.findAgent(agentKey);
/* 1596 */     if (tsAgent != null)
/*      */     {
/* 1598 */       return tsAgent;
/*      */     }
/*      */ 
/* 1602 */     if ((initialState != 4) && (initialState != 3) && (initialState != 1))
/*      */     {
/* 1605 */       throw new TsapiInvalidArgumentException(3, 0, "initial state not valid");
/*      */     }
/*      */ 
/* 1609 */     int state = 1;
/*      */ 
/* 1611 */     boolean reqPrivPresent = false;
/* 1612 */     if (this.provider.isLucent())
/*      */     {
/* 1616 */       if (reqPriv != null)
/*      */       {
/* 1618 */         reqPrivPresent = true;
/*      */       }
/*      */       else
/*      */       {
/* 1622 */         LucentSetAgentState lsas = null;
/* 1623 */         if (workMode == 1) {
/* 1624 */           lsas = createLucentSetAgentState((short) 3);
/* 1625 */         } else if (workMode == 2) {
/* 1626 */           lsas = createLucentSetAgentState((short) 4);
/*      */         }
/* 1629 */         else if (initialState == 4)
/*      */         {
/* 1631 */           workMode = 1;
/* 1632 */           lsas = createLucentSetAgentState((short) 3);
/*      */         }
/* 1634 */         else if ((initialState == 3) || (initialState == 1))
/*      */         {
/* 1636 */           workMode = 0;
/*      */ 
/* 1638 */           lsas = createLucentSetAgentState((short) 1, reasonCode);
/*      */         }
/*      */ 
/* 1641 */         reqPriv = lsas.makeTsapiPrivate();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1648 */       setTSAgent(acdName, state, agentID, password, reqPriv);
/*      */ 
/* 1651 */       tsAgent = this.provider.createAgent(agentKey, agentID, password);
/* 1652 */       if (tsAgent.getInternalState() == 2)
/*      */       {
/* 1655 */         this.provider.dumpAgent(agentKey);
/* 1656 */         tsAgent = this.provider.createAgent(agentKey, agentID, password);
/*      */       }
/*      */ 
/* 1665 */       if (this.provider.isLucent())
/*      */       {
/* 1667 */         if (!reqPrivPresent)
/*      */         {
/* 1669 */           tsAgent.updateState(initialState, workMode, reasonCode, null);
/*      */         }
/*      */ 
/*      */       }
/*      */       else {
/* 1674 */         tsAgent.updateState(1, 0, 0, null);
/*      */       }
/* 1676 */       tsAgent.getState();
/* 1677 */       return tsAgent;
/*      */     }
/*      */     catch (TsapiInvalidStateException e)
/*      */     {
/* 1685 */       if ((acdName != null) && (e.getErrorType() == 2) && (e.getErrorCode() == 21))
/*      */       {
/* 1689 */         snapshotAgentsInTerminal(acdName, agentID);
/*      */ 
/* 1691 */         agentKey = new TSAgentKey(getName(), acdName, null);
/* 1692 */         tsAgent = this.provider.findAgent(agentKey);
/* 1693 */         if (tsAgent != null)
/*      */         {
/* 1695 */           return tsAgent;
/*      */         }
/*      */ 
/* 1698 */         throw new TsapiPlatformException(4, 0, "add Agent failure");
/*      */       }
/* 1700 */       if ((e.getErrorType() == 2) && (e.getErrorCode() == 22))
/*      */       {
/* 1703 */         throw new TsapiPlatformException(4, 0, "Agent is already logged into another Station");
/*      */       }
/*      */ 
/* 1707 */       throw new TsapiPlatformException(4, 0, "add Agent failure");
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 1714 */       if ((acdName != null) && (e.getErrorType() == 2) && (e.getErrorCode() == 1))
/*      */       {
/* 1718 */         snapshotAgentsInTerminal(acdName, agentID);
/*      */ 
/* 1720 */         agentKey = new TSAgentKey(getName(), acdName, null);
/* 1721 */         tsAgent = this.provider.findAgent(agentKey);
/* 1722 */         if (tsAgent != null)
/*      */         {
/* 1724 */           return tsAgent;
/*      */         }
/*      */ 
/* 1727 */         throw new TsapiPlatformException(4, 0, "There is already an Agent logged on at the station");
/*      */       }
/* 1729 */       if ((acdName == null) && (e.getErrorType() == 2) && (e.getErrorCode() == 1))
/*      */       {
/* 1733 */         throw new TsapiPlatformException(4, 0, "There is already an Agent logged on at the station");
/*      */       }
/* 1735 */       if ((e.getErrorType() == 2) && (e.getErrorCode() == 0))
/*      */       {
/* 1739 */         throw new TsapiPlatformException(4, 0, "An attempt to log in an ACD agent with an incorrect password");
/*      */       }
/* 1741 */       if ((e.getErrorType() == 2) && (e.getErrorCode() == 12))
/*      */       {
/* 1745 */         throw new TsapiPlatformException(4, 0, "Invalid AgentId is specified");
/*      */       }
/*      */ 
/* 1749 */       throw e;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeTSAgent(TSAgent tsAgent, int reasonCode)
/*      */     throws TsapiInvalidArgumentException, TsapiInvalidStateException
/*      */   {
/* 1759 */     recreate();
/*      */ 
/* 1761 */     if (tsAgent == null)
/*      */     {
/* 1763 */       if (this.tsAgentTermVector.size() == 0)
/*      */       {
/* 1765 */         throw new TsapiInvalidArgumentException(3, 0, "No agents logged into specified Terminal");
/*      */       }
/*      */ 
/* 1768 */       Vector agentVector = new Vector(this.tsAgentTermVector);
/* 1769 */       for (int i = 0; i < agentVector.size(); ++i)
/*      */       {
/* 1772 */         tsAgent = (TSAgent)agentVector.elementAt(i);
/* 1773 */         if (tsAgent.getState() == 2) {
/*      */           continue;
/*      */         }
/*      */ 
/* 1777 */         tsAgent.setState(2, 0, reasonCode);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1784 */       if (tsAgent.getState() == 2)
/*      */       {
/* 1786 */         return;
/*      */       }
/*      */ 
/* 1789 */       if (!this.tsAgentTermVector.contains(tsAgent))
/*      */       {
/* 1791 */         throw new TsapiInvalidArgumentException(3, 0, "Agent not logged into specified Terminal");
/*      */       }
/*      */ 
/* 1796 */       tsAgent.setState(2, 0, reasonCode);
/*      */     }
/*      */   }
/*      */ 
/*      */   void snapshotAgentsInACD()
/*      */   {
/* 1804 */     recreate();
/*      */ 
/* 1807 */     if (!this.provider.isLucent())
/* 1808 */       return;
/*      */     try
/*      */     {
/* 1811 */       LucentQueryAgentLogin lqal = new LucentQueryAgentLogin(getName());
/* 1812 */       this.waitingForAgents = true;
/* 1813 */       ConfHandler handler = new QueryAgentLoginConfHandler(this);
/* 1814 */       Object lqalConf = this.provider.sendPrivateData(lqal.makeTsapiPrivate(), handler);
/*      */ 
/* 1816 */       if (lqalConf instanceof LucentQueryAgentLoginConfEvent)
/*      */       {
/* 1818 */         synchronized (this)
/*      */         {
/* 1820 */           if (this.waitingForAgents)
/*      */           {
/*      */             try
/*      */             {
/* 1824 */               super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
/*      */             } catch (InterruptedException e) {
/*      */             }
/* 1827 */             if (this.waitingForAgents)
/*      */             {
/* 1829 */               this.waitingForAgents = false;
/* 1830 */               throw new TsapiPlatformException(4, 0, "snapshot time-out");
/*      */             }
/*      */           }
/*      */         }
/* 1834 */         for (int i = 0; i < this.tsACDVector.size(); ++i)
/*      */         {
/* 1836 */           ((TSAgent)this.tsACDVector.elementAt(i)).getState();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void handleAgentLoginResponse(LucentQueryAgentLoginResp event)
/*      */   {
/* 1848 */     recreate();
/*      */ 
/* 1850 */     int xrefID = event.getPrivEventCrossRefID();
/* 1851 */     if ((event.getDevices() == null) || (event.getDevices().length == 0))
/*      */     {
/* 1853 */       this.provider.deletePrivateXref(xrefID);
/* 1854 */       synchronized (this)
/*      */       {
/* 1856 */         if (this.waitingForAgents)
/*      */         {
/* 1858 */           this.waitingForAgents = false;
/* 1859 */           super.notify();
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1865 */       for (int i = 0; i < event.getDevices().length; ++i)
/*      */       {
/* 1867 */         TSDevice agentDevice = this.provider.createDevice(event.getDevices()[i]);
/* 1868 */         String agentID = agentDevice.getAgentID();
/* 1869 */         TSAgentKey agentKey = new TSAgentKey(event.getDevices()[i], getName(), agentID);
/* 1870 */         TSAgent agent = this.provider.createAgent(agentKey, "", "");
/* 1871 */         if (agent.getInternalState() != 2) {
/*      */           continue;
/*      */         }
/* 1874 */         this.provider.dumpAgent(agentKey);
/* 1875 */         agent = this.provider.createAgent(agentKey, "", "");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void snapshotAgentsInTerminal(String acdName, String agentID)
/*      */   {
/* 1883 */     recreate();
/*      */ 
/* 1885 */     if (this.provider.getCapabilities().getQueryAgentState() == 0) {
/*      */       return;
/*      */     }
/* 1888 */     QueryAgentStateConfHandler2 handler = new QueryAgentStateConfHandler2(this, acdName, agentID);
/* 1889 */     CSTAPrivate priv = null;
/* 1890 */     if ((this.provider.isLucent()) && (acdName != null))
/*      */     {
/* 1892 */       LucentQueryAgentState lqas = new LucentQueryAgentState(acdName);
/* 1893 */       priv = lqas.makeTsapiPrivate();
/*      */     }
/* 1895 */     else if (this.provider.isLucent())
/*      */     {
/* 1898 */       TSAgentKey agentKey = new TSAgentKey(getName(), acdName, agentID);
/* 1899 */       TSAgent agent = null;
/* 1900 */       agent = getTSProviderImpl().findAgent(agentKey);
/* 1901 */       if (agent == null)
/*      */       {
/* 1903 */         synchronized (this.tsAgentTermVector)
/*      */         {
/* 1905 */           if (this.tsAgentTermVector.size() > 0)
/*      */           {
/* 1907 */             agent = (TSAgent)this.tsAgentTermVector.elementAt(0);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1912 */       if ((agent != null) && (agent.getACDDeviceID() != null))
/*      */       {
/* 1914 */         LucentQueryAgentState lqas = new LucentQueryAgentState(agent.getACDDeviceID().getName());
/* 1915 */         priv = lqas.makeTsapiPrivate();
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/* 1920 */       this.provider.tsapi.queryAgentState(getName(), priv, handler);
/*      */     }
/*      */     catch (TsapiUnableToSendException tue)
/*      */     {
/* 1927 */       throw tue;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1931 */       log.error(e.getMessage(), e);
/*      */     }
/*      */ 
/* 1935 */     if (acdName == null)
/*      */     {
/* 1938 */       agentID = getAgentID();
/* 1939 */       TSAgentKey currentAgentKey = new TSAgentKey(getName(), acdName, agentID);
/* 1940 */       if ((agentID != null) && 
/* 1942 */         (this.tsAgentTermVector.size() > 0))
/*      */       {
/* 1944 */         TSAgent previousAgent = (TSAgent)this.tsAgentTermVector.get(0);
/* 1945 */         TSAgentKey previousTsAgentKey = previousAgent.getAgentKey();
/* 1946 */         if (!previousTsAgentKey.equals(currentAgentKey))
/*      */         {
/* 1949 */           log.info("deleting agent " + previousAgent.getAgentID());
/* 1950 */           previousAgent.updateState(2, -1, 0, null);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1955 */     TSAgentKey agentKey = new TSAgentKey(getName(), acdName, agentID);
/* 1956 */     TSAgent agent = null;
/*      */ 
/* 1962 */     if (handler.getAgentState() == -1)
/*      */     {
/* 1964 */       return;
/*      */     }
/*      */ 
/* 1968 */     if (handler.getAgentState() == 1)
/*      */     {
/* 1970 */       agent = getTSProviderImpl().findAgent(agentKey);
/* 1971 */       if (agent == null)
/*      */       {
/* 1973 */         synchronized (this.tsAgentTermVector)
/*      */         {
/* 1975 */           if (this.tsAgentTermVector.size() > 0)
/*      */           {
/* 1977 */             agent = (TSAgent)this.tsAgentTermVector.elementAt(0);
/*      */           }
/*      */         }
/*      */       }
/* 1981 */       if (agent != null)
/*      */       {
/* 1983 */         agent.updateState(2, handler.getWorkMode(), 0, null);
/*      */       }
/* 1985 */       return;
/*      */     }
/*      */ 
/* 1989 */     agent = getTSProviderImpl().createAgent(agentKey, agentID, null);
/*      */ 
/* 1994 */     TSDevice agentDevice = agent.getTSAgentDevice();
/* 1995 */     TSDeviceState tsdeviceState = agentDevice.curState;
/* 1996 */     if (tsdeviceState.wasDeleteDone())
/*      */     {
/* 2004 */       updateAllAgentsInDeletedDeviceInstance(agentDevice.tsAgentTermVector);
/*      */     }
/* 2006 */     if (agent.getInternalState() == 2)
/*      */     {
/* 2009 */       this.provider.dumpAgent(agentKey);
/* 2010 */       agent = this.provider.createAgent(agentKey, agentID, null);
/*      */     }
/* 2013 */     int i = 0;
/* 2014 */     if (handler.getState() == 7)
/*      */     {
/* 2016 */       i = 7;
/*      */     }
/*      */     else
/*      */     {
/* 2020 */       switch (handler.getAgentState())
/*      */       {
/*      */       case 0:
/* 2023 */         i = 3;
/* 2024 */         break;
/*      */       case 1:
/* 2026 */         i = 2;
/* 2027 */         break;
/*      */       case 2:
/* 2029 */         i = 4;
/* 2030 */         break;
/*      */       case 3:
/* 2032 */         i = 5;
/* 2033 */         break;
/*      */       case 4:
/* 2035 */         i = 6;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2041 */     agent.updateState(i, handler.getWorkMode(), 0, null);
/*      */   }
/*      */ 
/*      */   private void updateAllAgentsInDeletedDeviceInstance(Vector<TSAgent> agentsForAgentTerm)
/*      */   {
/* 2048 */     for (int i = 0; i < agentsForAgentTerm.size(); ++i)
/*      */     {
/* 2050 */       TSAgent agent = (TSAgent)agentsForAgentTerm.elementAt(i);
/* 2051 */       log.info("agent represented by agentKey " + agent.getAgentKey() + " is holding reference to agentDevice which is in deleted state");
/* 2052 */       agent.agentDevice = this;
/* 2053 */       log.info("updated the agent device reference to the new agentdevice " + getName());
/* 2054 */       addToAgentTermVector(agent);
/* 2055 */       log.debug("agent has been added in the list of agents for device " + getName());
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<TSAgent> getTSAgentsForAgentTerm()
/*      */   {
/* 2068 */     recreate();
/*      */ 
/* 2070 */     if ((this.monitorDeviceXRefID == 0) || (this.provider.isLucent()))
/* 2071 */       snapshotAgentsInTerminal(null, null);
/* 2072 */     return this.tsAgentTermVector;
/*      */   }
/*      */ 
/*      */   public Vector<TSAgent> getTSAgentsForACDAddr()
/*      */   {
/* 2078 */     recreate();
/*      */ 
/* 2080 */     if (this.monitorDeviceXRefID == 0) {
/* 2081 */       snapshotAgentsInACD();
/*      */     }
/*      */ 
/* 2084 */     return this.tsACDVector;
/*      */   }
/*      */ 
/*      */   public int getTSNumberQueued()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 2091 */     recreate();
/*      */ 
/* 2093 */     if (this.provider.isLucent())
/*      */     {
/*      */       try
/*      */       {
/* 2097 */         LucentQueryAcdSplit lqas = new LucentQueryAcdSplit(getName());
/* 2098 */         Object lqasConf = this.provider.sendPrivateData(lqas.makeTsapiPrivate());
/* 2099 */         if (lqasConf instanceof LucentQueryAcdSplitConfEvent) {
/* 2100 */           return ((LucentQueryAcdSplitConfEvent)lqasConf).getCallsInQueue();
/*      */         }
/* 2102 */         throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 2106 */         throw new TsapiMethodNotSupportedException(4, 0, "unsupported by driver");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2114 */     return this.numQueued;
/*      */   }
/*      */ 
/*      */   public int getNumberQueued()
/*      */   {
/* 2121 */     recreate();
/*      */ 
/* 2123 */     return this.numQueued;
/*      */   }
/*      */ 
/*      */   void setNumberQueued(int _numQueued)
/*      */   {
/* 2129 */     recreate();
/*      */ 
/* 2131 */     this.numQueued = _numQueued;
/*      */   }
/*      */ 
/*      */   public TSCall getTSOldestCallQueued()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 2137 */     recreate();
/*      */ 
/* 2139 */     throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*      */   }
/*      */ 
/*      */   public int getTSRelativeQueueLoad()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 2145 */     recreate();
/*      */ 
/* 2147 */     throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*      */   }
/*      */ 
/*      */   public int getTSQueueWaitTime()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 2153 */     recreate();
/*      */ 
/* 2155 */     throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*      */   }
/*      */ 
/*      */   public TSDevice[] getTSACDManagerDevice()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 2161 */     recreate();
/*      */ 
/* 2164 */     throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*      */   }
/*      */ 
/*      */   public Vector<TSDevice> getTSACDDevices()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/* 2170 */     recreate();
/*      */ 
/* 2173 */     throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*      */   }
/*      */ 
/*      */   public void addRouteMonitor(TsapiRouteMonitor observer)
/*      */   {
/* 2179 */     recreate();
/*      */ 
/* 2181 */     synchronized (this.obsSync)
/*      */     {
/* 2183 */       registerRoute();
/*      */ 
/* 2188 */       this.tsRouteCallback = observer;
/* 2189 */       this.tsRouteCallback.addReference();
/*      */     }
/*      */   }
/*      */ 
/*      */   void registerRoute()
/*      */   {
/* 2195 */     recreate();
/*      */ 
/* 2197 */     ConfHandler handler = new RouteRegisterConfHandler(this);
/*      */     try
/*      */     {
/* 2200 */       if (getName().equals("AllRouteAddress"))
/* 2201 */         this.provider.tsapi.registerRouteCallback(null, null, handler);
/*      */       else
/* 2203 */         this.provider.tsapi.registerRouteCallback(getName(), null, handler);
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 2207 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2211 */       if (e instanceof ITsapiException) {
/* 2212 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "registerRouteCallback failure");
/*      */       }
/* 2214 */       throw new TsapiPlatformException(4, 0, "registerRouteCallback failure");
/*      */     }
/* 2216 */     this.provider.addRoute(this.registerReqID, this);
/* 2217 */     this.sessionHash = new Hashtable(3);
/*      */   }
/*      */ 
/*      */   public void removeRouteMonitor(TsapiRouteMonitor observer)
/*      */   {
/* 2223 */     recreate();
/*      */ 
/* 2225 */     removeRouteMonitor(observer, 100, null);
/*      */   }
/*      */ 
/*      */   protected void removeRouteMonitor(TsapiRouteMonitor observer, int cause, Object privateData)
/*      */   {
/* 2231 */     recreate();
/*      */     try
/*      */     {
/* 2236 */       this.provider.tsapi.cancelRouteCallback(this.registerReqID, null, null);
/*      */     }
/*      */     catch (TsapiPlatformException e)
/*      */     {
/* 2240 */       throw e;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2244 */       if (e instanceof ITsapiException) {
/* 2245 */         throw new TsapiPlatformException(((ITsapiException)e).getErrorType(), ((ITsapiException)e).getErrorCode(), "cancelRouteCallback failure");
/*      */       }
/* 2247 */       throw new TsapiPlatformException(4, 0, "cancelRouteCallback failure");
/*      */     }
/* 2249 */     observer.deleteReference(this);
/* 2250 */     testDelete();
/*      */   }
/*      */ 
/*      */   public Vector<TSRouteSession> getTSRouteSessions()
/*      */   {
/* 2256 */     recreate();
/*      */ 
/* 2258 */     if (this.tsRouteCallback != null) {
/* 2259 */       return getSessions();
/*      */     }
/* 2261 */     return null;
/*      */   }
/*      */ 
/*      */   int getRegisterReqID()
/*      */   {
/* 2266 */     recreate();
/*      */ 
/* 2268 */     return this.registerReqID;
/*      */   }
/*      */ 
/*      */   void addSession(int routingCrossRefID, TSRouteSession routeSession)
/*      */   {
/* 2273 */     recreate();
/*      */ 
/* 2275 */     if (this.sessionHash == null) {
/*      */       return;
/*      */     }
/* 2278 */     Object oldObj = this.sessionHash.put(new Integer(routingCrossRefID), routeSession);
/* 2279 */     if (oldObj != null)
/* 2280 */       log.info("NOTICE: sessionHash.put() replaced " + oldObj + " for " + this);
/*      */   }
/*      */ 
/*      */   void deleteSession(int routingCrossRefID)
/*      */   {
/* 2286 */     recreate();
/*      */ 
/* 2288 */     if (this.sessionHash == null) {
/*      */       return;
/*      */     }
/* 2291 */     this.sessionHash.remove(new Integer(routingCrossRefID));
/*      */   }
/*      */ 
/*      */   Vector<TSRouteSession> getSessions()
/*      */   {
/* 2297 */     recreate();
/*      */ 
/* 2299 */     if (this.sessionHash == null)
/*      */     {
/* 2301 */       return null;
/*      */     }
/*      */ 
/* 2304 */     Vector sessionVector = new Vector();
/* 2305 */     Enumeration sessionEnum = this.sessionHash.elements();
/* 2306 */     while (sessionEnum.hasMoreElements())
/*      */     {
/*      */       try
/*      */       {
/* 2310 */         sessionVector.addElement((TSRouteSession)sessionEnum.nextElement());
/*      */       }
/*      */       catch (NoSuchElementException e)
/*      */       {
/* 2314 */         log.error(e.getMessage(), e);
/*      */       }
/*      */     }
/*      */ 
/* 2318 */     return sessionVector;
/*      */   }
/*      */ 
/*      */   TsapiRouteMonitor getTSRouteCallback()
/*      */   {
/* 2323 */     recreate();
/*      */ 
/* 2325 */     return this.tsRouteCallback;
/*      */   }
/*      */ 
/*      */   public Object getAddrPrivateData()
/*      */   {
/* 2333 */     recreate();
/*      */ 
/* 2335 */     if (this.replyAddrPriv instanceof CSTAPrivate)
/* 2336 */       return this.replyAddrPriv;
/* 2337 */     return null;
/*      */   }
/*      */ 
/*      */   Object getTermPrivateData()
/*      */   {
/* 2343 */     recreate();
/*      */ 
/* 2345 */     if (this.replyTermPriv instanceof CSTAPrivate)
/* 2346 */       return this.replyTermPriv;
/* 2347 */     return null;
/*      */   }
/*      */ 
/*      */   public Object sendPrivateData(CSTAPrivate data)
/*      */   {
/* 2353 */     recreate();
/*      */     try
/*      */     {
/* 2357 */       return this.provider.sendPrivateData(data);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2361 */       throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setState(TSDeviceState _newState)
/*      */   {
/* 2373 */     log.info("TSDevice state transition: " + this.curState + " -> " + _newState + ", device " + this);
/*      */ 
/* 2375 */     this.curState = _newState;
/*      */   }
/*      */ 
/*      */   TSDevice(TSProviderImpl _provider, CSTAExtendedDeviceID deviceID)
/*      */   {
/* 2391 */     this.asyncInitializationComplete = false;
/*      */ 
/* 2393 */     this.provider = _provider;
/* 2394 */     this.dndInitialized = false;
/* 2395 */     this.mwiInitialized = false;
/* 2396 */     this.forwardingInitialized = false;
/* 2397 */     this.dndState = false;
/* 2398 */     this.msgWaitingBits = 0;
/* 2399 */     this.numQueued = 0;
/* 2400 */     this.isATerminal = true;
/* 2401 */     this.isExternal = false;
/* 2402 */     this.deviceType = 0;
/* 2403 */     g_CreationCnt += 1;
/*      */ 
/* 2405 */     this.devNameVector = new Vector();
/* 2406 */     this.devNameVector.addElement(deviceID);
/* 2407 */     this.connections = new Vector();
/* 2408 */     this.terminalConnections = new Vector();
/* 2409 */     this.internalDeviceMonitors = new Vector();
/* 2410 */     this.terminalMonitorThreads = new Vector();
/* 2411 */     this.addressMonitorThreads = new Vector();
/* 2412 */     this.callsViaAddressMonitorThreads = new Vector();
/* 2413 */     this.callsAtAddressMonitorThreads = new Vector();
/* 2414 */     this.callsAtTerminalMonitorThreads = new Vector();
/* 2415 */     this.tsACDVector = new Vector();
/* 2416 */     this.tsAgentTermVector = new Vector();
/* 2417 */     this.fwdVector = new Vector();
/*      */ 
/* 2423 */     this.curState = new TSDeviceStateActive();
/*      */ 
/* 2425 */     this.obsSync = new Object();
/*      */ 
/* 2427 */     log.info("Constructing device " + this + " with name " + getName() + " for " + this.provider);
/*      */     try
/*      */     {
/* 2438 */       if (((this.provider.isLucent()) && (getName().regionMatches(true, 0, "T", 0, 1))) || ((((!this.provider.isLucent()) || (deviceID.getDeviceIDType() != 55))) && (((!this.provider.isLucent()) || (deviceID.getDeviceIDType() != 40))) && (((!this.provider.isLucent()) || (deviceID.getDeviceIDType() != 0)))))
/*      */       {
/* 2445 */         setIsATerminal(false);
/* 2446 */         setIsExternal(true);
/* 2447 */         notifyAsyncInitializationComplete();
/*      */       }
/* 2449 */       else if (this.provider.getCapabilities().getQueryDeviceInfo() != 0)
/*      */       {
/* 2451 */         this.provider.tsapi.queryDeviceInfoAsync(getName(), null, new AsyncQueryDeviceInfoConfHandler(this));
/*      */       }
/*      */       else
/*      */       {
/* 2455 */         notifyAsyncInitializationComplete();
/*      */       }
/*      */     }
/*      */     catch (TsapiUnableToSendException tue)
/*      */     {
/* 2460 */       throw tue;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2465 */       setIsATerminal(false);
/* 2466 */       setIsExternal(true);
/* 2467 */       notifyAsyncInitializationComplete();
/*      */     }
/*      */   }
/*      */ 
/*      */   synchronized void waitForAsyncInitialization()
/*      */   {
/* 2473 */     recreate();
/*      */ 
/* 2475 */     if (this.asyncInitializationComplete)
/*      */       return;
/*      */     try
/*      */     {
/* 2479 */       log.info(this + " waiting for initialization to complete");
/* 2480 */       super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
/*      */     } catch (InterruptedException e) {
/*      */     }
/* 2483 */     if (this.asyncInitializationComplete)
/*      */       return;
/* 2485 */     throw new TsapiPlatformException(4, 0, "could not finish address/terminal construction");
/*      */   }
/*      */ 
/*      */   boolean updateObject()
/*      */   {
/* 2493 */     recreate();
/*      */ 
/* 2505 */     if ((isMonitorSet() == true) && 
/* 2511 */       (cleanUCIDsInCallsInConnections()))
/*      */     {
/* 2513 */       return true;
/*      */     }
/*      */ 
/* 2517 */     return doCallSnapshot();
/*      */   }
/*      */ 
/*      */   boolean cleanUCIDsInCallsInConnections()
/*      */   {
/* 2534 */     boolean bfound = false;
/* 2535 */     Vector conns = new Vector(this.connections);
/* 2536 */     for (int i = 0; i < conns.size(); ++i)
/*      */     {
/* 2538 */       TSConnection conn = (TSConnection)conns.elementAt(i);
/* 2539 */       TSCall call = conn.getTSCall();
/*      */ 
/* 2544 */       if ((call.state == 34) || 
/* 2546 */         (call.cleanUCIDInCall()))
/*      */         continue;
/* 2548 */       bfound = true;
/*      */     }
/*      */ 
/* 2553 */     return !bfound;
/*      */   }
/*      */ 
/*      */   void setMonitor(boolean followCall, boolean callObserver)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/* 2561 */     setMonitor(followCall, callObserver, false, null);
/*      */   }
/*      */ 
/*      */   void setMonitor(boolean followCall, boolean callObserver, boolean predictive, CSTAPrivate reqPriv) throws TsapiResourceUnavailableException
/*      */   {
/* 2566 */     recreate();
/*      */     CSTAEvent event;
/* 2570 */     if ((followCall) || ((callObserver) && (((getDeviceType() == 2) || (getDeviceType() == 1)))))
/*      */     {
/* 2572 */       synchronized (this)
/*      */       {
/* 2574 */         if (this.monitorCallsViaDeviceXRefID != 0) {
/* 2575 */           return;
/*      */         }
/* 2577 */         if (this.provider.getCapabilities().getMonitorCallsViaDevice() != 0)
/*      */         {
/*      */           try
/*      */           {
/* 2582 */             event = this.provider.tsapi.monitorCallsViaDevice(getName(), new CSTAMonitorFilter(), reqPriv);
/* 2583 */             if (event != null) {
/* 2584 */               CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent)event.getEvent();
/* 2585 */               this.monitorCallsViaDeviceXRefID = monitorConf.getMonitorCrossRefID();
/* 2586 */               this.monitorPredictiveCallsViaDevice = predictive;
/*      */             }
/*      */           }
/*      */           catch (TsapiUnableToSendException tue)
/*      */           {
/* 2591 */             throw tue;
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 2595 */             log.error("MonitorCallsViaDevice request failed - retrying");
/*      */             try
/*      */             {
/* 2601 */               event = this.provider.tsapi.monitorCallsViaDevice(getName(), new CSTAMonitorFilter(), reqPriv);
/* 2602 */               CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent)event.getEvent();
/* 2603 */               this.monitorCallsViaDeviceXRefID = monitorConf.getMonitorCrossRefID();
/* 2604 */               this.monitorPredictiveCallsViaDevice = predictive;
/*      */             }
/*      */             catch (TsapiResourceUnavailableException e1)
/*      */             {
/* 2608 */               throw e1;
/*      */             }
/*      */             catch (Exception e1)
/*      */             {
/* 2612 */               if (followCall)
/*      */               {
/* 2614 */                 throw new TsapiResourceUnavailableException(2, 0, 0, "failure to monitor calls via device");
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2626 */     if ((!followCall) && (((!this.provider.isLucent()) || ((this.provider.isLucent()) && (getDeviceType() != 1)))))
/*      */     {
/* 2628 */       if (this.provider.getCapabilities().getMonitorDevice() == 0) {
/* 2629 */         throw new TsapiResourceUnavailableException(2, 0, 0, "no capability to monitor device");
/*      */       }
/* 2631 */       synchronized (this)
/*      */       {
/* 2633 */         if (this.monitorDeviceXRefID != 0) {
/* 2634 */           return;
/*      */         }
/*      */ 
/*      */         try
/*      */         {
/* 2639 */           event = this.provider.tsapi.monitorDevice(getName(), new CSTAMonitorFilter(), null);
/*      */         }
/*      */         catch (TsapiUnableToSendException tue)
/*      */         {
/* 2643 */           throw tue;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2647 */           log.error("MonitorDevice request failed - retrying", e);
/* 2648 */           log.info("MonitorDevice request failed - retrying");
/*      */           try
/*      */           {
/* 2653 */             event = this.provider.tsapi.monitorDevice(getName(), new CSTAMonitorFilter(), null);
/*      */           }
/*      */           catch (TsapiUnableToSendException tue)
/*      */           {
/* 2657 */             throw tue;
/*      */           }
/*      */           catch (TsapiResourceUnavailableException e1)
/*      */           {
/* 2661 */             throw e1;
/*      */           }
/*      */           catch (Exception e1)
/*      */           {
/* 2665 */             throw new TsapiResourceUnavailableException(2, 0, 0, "failure to monitor device");
/*      */           }
/*      */         }
/* 2668 */         if (event != null) {
/* 2669 */           CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent)event.getEvent();
/* 2670 */           this.monitorDeviceXRefID = monitorConf.getMonitorCrossRefID();
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2676 */     if ((followCall) || ((callObserver) && (((getDeviceType() == 2) || (getDeviceType() == 1))))) {
/* 2677 */       this.provider.addMonitor(this.monitorCallsViaDeviceXRefID, this);
/*      */     }
/*      */ 
/* 2680 */     if ((followCall) || ((this.provider.isLucent()) && (((!this.provider.isLucent()) || (getDeviceType() == 1)))))
/*      */     {
/*      */       return;
/*      */     }
/*      */ 
/* 2686 */     doTerminalSnapshot();
/* 2687 */     doAddressSnapshot();
/* 2688 */     doCallSnapshot();
/* 2689 */     this.provider.addMonitor(this.monitorDeviceXRefID, this);
/*      */   }
/*      */ 
/*      */   void removeInternalMonitor(TSCall call)
/*      */   {
/* 2697 */     recreate();
/*      */ 
/* 2699 */     this.internalDeviceMonitors.removeElement(call);
/*      */ 
/* 2719 */     testDelete();
/*      */   }
/*      */ 
/*      */   TSDevice setInternalMonitor(TSCall call) throws TsapiResourceUnavailableException
/*      */   {
/* 2724 */     recreate();
/*      */ 
/* 2726 */     if (this.provider.getCapabilities().getMonitorDevice() == 0) {
/* 2727 */       throw new TsapiResourceUnavailableException(2, 0, 0, "no capability to monitor device");
/*      */     }
/* 2729 */     synchronized (this.obsSync)
/*      */     {
/* 2731 */       if (isMonitorSet())
/*      */       {
/* 2733 */         this.internalDeviceMonitors.addElement(call);
/* 2734 */         return this;
/*      */       }
/* 2736 */       synchronized (this)
/*      */       {
/* 2738 */         if (this.monitorDeviceXRefID != 0)
/*      */         {
/* 2740 */           this.internalDeviceMonitors.addElement(call);
/* 2741 */           return this;
/*      */         }
/*      */ 
/*      */         CSTAEvent event;
/*      */         try
/*      */         {
/* 2748 */           event = this.provider.tsapi.monitorDevice(getName(), new CSTAMonitorFilter(), null);
/*      */         }
/*      */         catch (TsapiUnableToSendException tue)
/*      */         {
/* 2752 */           throw tue;
/*      */         }
/*      */         catch (TsapiResourceUnavailableException e)
/*      */         {
/* 2756 */           throw e;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2760 */           throw new TsapiResourceUnavailableException(2, 0, 0, "failure to monitor device");
/*      */         }
/*      */ 
/* 2763 */         CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent)event.getEvent();
/* 2764 */         this.monitorDeviceXRefID = monitorConf.getMonitorCrossRefID();
/*      */       }
/*      */ 
/* 2771 */       doTerminalSnapshot();
/* 2772 */       doAddressSnapshot();
/* 2773 */       doCallSnapshot();
/*      */ 
/* 2775 */       this.provider.addMonitor(this.monitorDeviceXRefID, this);
/*      */ 
/* 2777 */       this.internalDeviceMonitors.addElement(call);
/*      */     }
/* 2779 */     return this;
/*      */   }
/*      */ 
/*      */   boolean doTerminalSnapshot()
/*      */   {
/* 2786 */     recreate();
/*      */ 
/* 2789 */     if (!isTerminal())
/*      */     {
/* 2791 */       return true;
/*      */     }
/*      */ 
/* 2794 */     snapshotAgentsInTerminal(null, null);
/*      */ 
/* 2797 */     return true;
/*      */   }
/*      */ 
/*      */   boolean doAddressSnapshot()
/*      */   {
/* 2805 */     recreate();
/*      */ 
/* 2808 */     if (getDeviceType() == 1)
/*      */     {
/* 2810 */       return true;
/*      */     }
/*      */ 
/* 2815 */     if (this.provider.getCapabilities().getQueryDnd() != 0)
/*      */     {
/* 2817 */       ConfHandler handler = new DNDConfHandler(this);
/*      */       try
/*      */       {
/* 2821 */         this.provider.tsapi.queryDoNotDisturb(getName(), null, handler);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/* 2828 */         throw tue;
/*      */       } catch (Exception e) {
/* 2830 */         log.error(e.getMessage(), e);
/*      */       }
/*      */     }
/* 2833 */     if (this.provider.getCapabilities().getQueryMwi() != 0)
/*      */     {
/* 2835 */       ConfHandler handler = new MWIConfHandler(this);
/*      */       try
/*      */       {
/* 2839 */         this.provider.tsapi.queryMsgWaitingInd(getName(), null, handler);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/* 2846 */         throw tue;
/*      */       } catch (Exception e) {
/* 2848 */         log.error(e.getMessage(), e);
/*      */       }
/*      */     }
/* 2851 */     if (this.provider.getCapabilities().getQueryFwd() != 0)
/*      */     {
/* 2853 */       ConfHandler handler = new FwdConfHandler(this);
/*      */       try
/*      */       {
/* 2857 */         this.provider.tsapi.queryFwd(getName(), null, handler);
/*      */       }
/*      */       catch (TsapiUnableToSendException tue)
/*      */       {
/* 2864 */         throw tue;
/*      */       } catch (Exception e) {
/* 2866 */         log.error(e.getMessage(), e);
/*      */       }
/*      */     }
/* 2868 */     snapshotAgentsInACD();
/*      */ 
/* 2870 */     return true;
/*      */   }
/*      */ 
/*      */   boolean doCallSnapshot()
/*      */   {
/* 2879 */     recreate();
/*      */ 
/* 2881 */     if (this.provider.getCapabilities().getSnapshotDeviceReq() == 0)
/*      */     {
/* 2883 */       return false;
/*      */     }
/*      */ 
/* 2888 */     SnapshotDeviceConfHandler handler = new SnapshotDeviceConfHandler(this);
/*      */     try
/*      */     {
/* 2891 */       this.provider.tsapi.snapshotDevice(getName(), null, handler);
/*      */     }
/*      */     catch (TsapiUnableToSendException tue)
/*      */     {
/* 2895 */       throw tue;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2899 */       log.error(e.getMessage(), e);
/* 2900 */       return false;
/*      */     }
/*      */ 
/* 2903 */     Vector newCalls = new Vector();
/*      */ 
/* 2905 */     if (handler.info != null)
/*      */     {
/* 2907 */       TSCall call = null;
/* 2908 */       for (int i = 0; i < handler.info.length; ++i)
/*      */       {
/*      */         try
/*      */         {
/* 2912 */           call = this.provider.createCall(handler.info[i].getCallIdentifier().getCallID());
/* 2913 */           if (call.getTSState() == 34)
/*      */           {
/* 2917 */             this.provider.dumpCall(handler.info[i].getCallIdentifier().getCallID());
/* 2918 */             call = this.provider.createCall(handler.info[i].getCallIdentifier().getCallID());
/*      */           }
/* 2920 */           call.doSnapshot(handler.info[i].getCallIdentifier(), null, true);
/* 2921 */           if (isMonitorSet())
/*      */           {
/* 2923 */             call.setNeedSnapshot(false);
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (TsapiPlatformException e)
/*      */         {
/* 2930 */           break label215:
/*      */         }
/*      */ 
/* 2933 */         label215: if (!newCalls.contains(call)) {
/* 2934 */           newCalls.addElement(call);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2943 */     Vector conns = new Vector(this.connections);
/* 2944 */     for (int i = 0; i < conns.size(); ++i)
/*      */     {
/* 2946 */       TSConnection conn = (TSConnection)conns.elementAt(i);
/* 2947 */       if (newCalls.contains(conn.getTSCall()))
/*      */         continue;
/* 2949 */       conn.setConnectionState(89, null);
/*      */     }
/*      */ 
/* 2952 */     conns = new Vector(this.terminalConnections);
/* 2953 */     for (int i = 0; i < conns.size(); ++i)
/*      */     {
/* 2955 */       TSConnection conn = (TSConnection)conns.elementAt(i);
/* 2956 */       if (newCalls.contains(conn.getTSCall()))
/*      */         continue;
/* 2958 */       conn.setTermConnState(102, null);
/*      */     }
/*      */ 
/* 2962 */     return true;
/*      */   }
/*      */ 
/*      */   void sendCallSnapshot(TsapiCallMonitor callback, boolean forTerminal)
/*      */   {
/* 2969 */     recreate();
/*      */ 
/* 2971 */     if (callback == null)
/*      */     {
/* 2973 */       return;
/*      */     }
/*      */ 
/* 2976 */     Vector eventList = new Vector();
/*      */ 
/* 2978 */     if (forTerminal)
/*      */     {
/* 2980 */       synchronized (this.terminalConnections)
/*      */       {
/* 2982 */         for (int i = 0; i < this.terminalConnections.size(); ++i)
/*      */         {
/* 2984 */           TSCall call = ((TSConnection)this.terminalConnections.elementAt(i)).getTSCall();
/*      */ 
/* 2986 */           if (call.getCallObservers().contains(callback)) {
/*      */             continue;
/*      */           }
/*      */ 
/* 2990 */           call.getSnapshot(eventList);
/* 2991 */           call.addDeviceObservers(this, this.callsAtTerminalMonitorThreads, null, null, false);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     else {
/* 2997 */       synchronized (this.connections)
/*      */       {
/* 2999 */         for (int i = 0; i < this.connections.size(); ++i)
/*      */         {
/* 3001 */           TSCall call = ((TSConnection)this.connections.elementAt(i)).getTSCall();
/*      */ 
/* 3003 */           if (call.getCallObservers().contains(callback)) {
/*      */             continue;
/*      */           }
/*      */ 
/* 3007 */           call.getSnapshot(eventList);
/* 3008 */           call.addDeviceObservers(this, null, this.callsAtAddressMonitorThreads, this.callsViaAddressMonitorThreads, false);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3013 */     if (eventList.size() <= 0) {
/*      */       return;
/*      */     }
/* 3016 */     callback.deliverEvents(eventList, 110, true);
/*      */   }
/*      */ 
/*      */   void sendAddressSnapshot(TsapiAddressMonitor callback)
/*      */   {
/* 3024 */     recreate();
/*      */ 
/* 3026 */     if (callback == null)
/*      */     {
/* 3028 */       return;
/*      */     }
/*      */ 
/* 3031 */     Vector eventList = new Vector();
/*      */ 
/* 3033 */     eventList.addElement(new TSEvent(37, this));
/* 3034 */     eventList.addElement(new TSEvent(38, this));
/* 3035 */     eventList.addElement(new TSEvent(39, this));
/*      */ 
/* 3037 */     for (int i = 0; i < this.tsACDVector.size(); ++i)
/*      */     {
/* 3039 */       ((TSAgent)this.tsACDVector.elementAt(i)).getSnapshot(eventList);
/*      */     }
/*      */ 
/* 3042 */     if (eventList.size() <= 0) {
/*      */       return;
/*      */     }
/* 3045 */     callback.deliverEvents(eventList, true);
/*      */   }
/*      */ 
/*      */   void sendTerminalSnapshot(TsapiTerminalMonitor callback)
/*      */   {
/* 3053 */     recreate();
/*      */ 
/* 3055 */     if (callback == null)
/*      */     {
/* 3057 */       return;
/*      */     }
/*      */ 
/* 3060 */     Vector eventList = new Vector();
/*      */ 
/* 3062 */     eventList.addElement(new TSEvent(58, this));
/*      */ 
/* 3064 */     for (int i = 0; i < this.tsAgentTermVector.size(); ++i)
/*      */     {
/* 3066 */       ((TSAgent)this.tsAgentTermVector.elementAt(i)).getSnapshot(eventList);
/*      */     }
/*      */ 
/* 3069 */     if (eventList.size() <= 0) {
/*      */       return;
/*      */     }
/* 3072 */     callback.deliverEvents(eventList, true);
/*      */   }
/*      */ 
/*      */   void updateDNDState(boolean _dndState, Vector<TSEvent> eventList)
/*      */   {
/* 3080 */     recreate();
/*      */ 
/* 3082 */     synchronized (this)
/*      */     {
/* 3084 */       if (this.dndState == _dndState)
/*      */       {
/* 3086 */         if (!this.dndInitialized)
/* 3087 */           this.dndInitialized = true;
/* 3088 */         return;
/*      */       }
/*      */ 
/* 3091 */       this.dndState = _dndState;
/*      */     }
/*      */ 
/* 3094 */     if ((eventList != null) && (this.dndInitialized))
/*      */     {
/* 3096 */       eventList.addElement(new TSEvent(37, this));
/* 3097 */       eventList.addElement(new TSEvent(58, this));
/*      */     }
/*      */ 
/* 3104 */     if (!this.dndInitialized)
/* 3105 */       this.dndInitialized = true;
/*      */   }
/*      */ 
/*      */   void updateMessageWaitingBits(int _msgWaitingBits, Vector<TSEvent> eventList)
/*      */   {
/* 3112 */     recreate();
/*      */ 
/* 3114 */     synchronized (this)
/*      */     {
/* 3116 */       if (this.msgWaitingBits == _msgWaitingBits)
/*      */       {
/* 3118 */         if (!this.mwiInitialized)
/* 3119 */           this.mwiInitialized = true;
/* 3120 */         return;
/*      */       }
/*      */ 
/* 3123 */       this.msgWaitingBits = _msgWaitingBits;
/*      */     }
/*      */ 
/* 3126 */     if ((eventList != null) && (this.mwiInitialized))
/*      */     {
/* 3128 */       eventList.addElement(new TSEvent(38, this));
/*      */     }
/*      */ 
/* 3135 */     if (!this.mwiInitialized)
/* 3136 */       this.mwiInitialized = true;
/*      */   }
/*      */ 
/*      */   void updateForwarding(CSTAForwardingInfo[] fwdInfo, Vector<TSEvent> eventList)
/*      */   {
/* 3143 */     recreate();
/*      */ 
/* 3145 */     boolean update = false;
/* 3146 */     boolean typeMatch = false;
/* 3147 */     synchronized (this)
/*      */     {
/* 3149 */       TsapiCallControlForwarding newFwd = null;
/* 3150 */       TsapiCallControlForwarding oldFwd = null;
/* 3151 */       for (int i = 0; i < fwdInfo.length; ++i)
/*      */       {
/* 3153 */         switch (fwdInfo[i].getForwardingType())
/*      */         {
/*      */         case 1:
/* 3156 */           newFwd = new TsapiCallControlForwarding(fwdInfo[i].getForwardDN(), 2);
/*      */ 
/* 3158 */           break;
/*      */         case 4:
/* 3160 */           newFwd = new TsapiCallControlForwarding(fwdInfo[i].getForwardDN(), 2, false);
/*      */ 
/* 3162 */           break;
/*      */         case 3:
/* 3164 */           newFwd = new TsapiCallControlForwarding(fwdInfo[i].getForwardDN(), 2, true);
/*      */ 
/* 3166 */           break;
/*      */         case 0:
/* 3168 */           newFwd = new TsapiCallControlForwarding(fwdInfo[i].getForwardDN(), 1);
/*      */ 
/* 3170 */           break;
/*      */         case 2:
/* 3172 */           newFwd = new TsapiCallControlForwarding(fwdInfo[i].getForwardDN(), 3);
/*      */ 
/* 3174 */           break;
/*      */         case 6:
/* 3176 */           newFwd = new TsapiCallControlForwarding(fwdInfo[i].getForwardDN(), 3, false);
/*      */ 
/* 3178 */           break;
/*      */         case 5:
/* 3180 */           newFwd = new TsapiCallControlForwarding(fwdInfo[i].getForwardDN(), 3, true);
/*      */         }
/*      */ 
/* 3184 */         typeMatch = false;
/* 3185 */         synchronized (this.fwdVector) {
/* 3186 */           for (int j = 0; j < this.fwdVector.size(); ++j) {
/* 3187 */             oldFwd = (TsapiCallControlForwarding)this.fwdVector.elementAt(j);
/* 3188 */             if ((oldFwd.getType() != newFwd.getType()) || (oldFwd.getFilter() != newFwd.getFilter())) {
/*      */               continue;
/*      */             }
/* 3191 */             typeMatch = true;
/* 3192 */             if (!fwdInfo[i].isForwardingOn())
/*      */             {
/* 3195 */               update = true;
/*      */ 
/* 3198 */               this.fwdVector.removeElement(oldFwd);
/* 3199 */               break;
/*      */             }
/* 3201 */             if (newFwd.getDestinationAddress() == null)
/*      */             {
/* 3203 */               if (oldFwd.getDestinationAddress() == null)
/*      */               {
/* 3210 */                 update = true; continue;
/*      */               }
/*      */ 
/* 3215 */               update = true;
/*      */ 
/* 3218 */               this.fwdVector.removeElement(oldFwd);
/* 3219 */               this.fwdVector.addElement(newFwd);
/* 3220 */               break;
/*      */             }
/*      */ 
/* 3223 */             if ((newFwd.getDestinationAddress().equals(oldFwd.getDestinationAddress())) || (newFwd.getDestinationAddress().equals(oldFwd.getDestinationAddress() + "#"))) {
/*      */               continue;
/*      */             }
/*      */ 
/* 3227 */             update = true;
/*      */ 
/* 3230 */             this.fwdVector.removeElement(oldFwd);
/* 3231 */             this.fwdVector.addElement(newFwd);
/* 3232 */             break;
/*      */           }
/*      */ 
/* 3237 */           if ((!typeMatch) && (fwdInfo[i].isForwardingOn() == true))
/*      */           {
/* 3239 */             update = true;
/* 3240 */             this.fwdVector.addElement(newFwd);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3246 */     if ((eventList != null) && (update == true) && (this.forwardingInitialized))
/*      */     {
/* 3248 */       eventList.addElement(new TSEvent(39, this));
/*      */     }
/*      */ 
/* 3251 */     if (!this.forwardingInitialized)
/* 3252 */       this.forwardingInitialized = true;
/*      */   }
/*      */ 
/*      */   public Vector<TsapiAddressMonitor> getAddressObservers()
/*      */   {
/* 3259 */     recreate();
/*      */ 
/* 3261 */     return new Vector(this.addressMonitorThreads);
/*      */   }
/*      */ 
/*      */   public Vector<TsapiTerminalMonitor> getTerminalObservers()
/*      */   {
/* 3268 */     recreate();
/*      */ 
/* 3270 */     return new Vector(this.terminalMonitorThreads);
/*      */   }
/*      */ 
/*      */   public Vector<TsapiRouteMonitor> getRouteObservers()
/*      */   {
/* 3275 */     recreate();
/*      */ 
/* 3277 */     Vector obs = new Vector(1);
/* 3278 */     if (this.tsRouteCallback != null)
/*      */     {
/* 3280 */       obs.addElement(this.tsRouteCallback);
/*      */     }
/* 3282 */     return obs;
/*      */   }
/*      */ 
/*      */   void removeObservers(int cause, Object privateData, int xrefID)
/*      */   {
/* 3289 */     recreate();
/*      */ 
/* 3291 */     if (xrefID != 0)
/*      */     {
/* 3293 */       if (xrefID == this.monitorDeviceXRefID)
/*      */       {
/* 3295 */         this.provider.deleteMonitor(this.monitorDeviceXRefID);
/* 3296 */         this.monitorDeviceXRefID = 0;
/*      */       }
/* 3298 */       if (xrefID == this.monitorCallsViaDeviceXRefID)
/*      */       {
/* 3300 */         this.provider.deleteMonitor(this.monitorCallsViaDeviceXRefID);
/* 3301 */         clearMonitorCallsViaDeviceAttributes();
/*      */       }
/*      */     }
/*      */ 
/* 3305 */     Vector observers = new Vector(this.addressMonitorThreads);
/* 3306 */     for (int i = 0; i < observers.size(); ++i)
/*      */     {
/* 3308 */       removeAddressMonitor((TsapiAddressMonitor)observers.elementAt(i), cause, privateData);
/*      */     }
/* 3310 */     Vector terminalObservers = new Vector(this.terminalMonitorThreads);
/* 3311 */     for (int i = 0; i < terminalObservers.size(); ++i)
/*      */     {
/* 3313 */       removeTerminalMonitor((TsapiTerminalMonitor)terminalObservers.elementAt(i), cause, privateData);
/*      */     }
/* 3315 */     Vector callsViaAddressObservers = new Vector(this.callsViaAddressMonitorThreads);
/* 3316 */     for (int i = 0; i < callsViaAddressObservers.size(); ++i)
/*      */     {
/* 3318 */       removeAddressCallMonitor((TsapiCallMonitor)callsViaAddressObservers.elementAt(i), cause, privateData);
/*      */     }
/*      */ 
/* 3321 */     Vector callsAtAddressObservers = new Vector(this.callsAtAddressMonitorThreads);
/* 3322 */     for (int i = 0; i < callsAtAddressObservers.size(); ++i)
/*      */     {
/* 3324 */       removeAddressCallMonitor((TsapiCallMonitor)callsAtAddressObservers.elementAt(i), cause, privateData);
/*      */     }
/*      */ 
/* 3327 */     Vector callsAtTerminalObservers = new Vector(this.callsAtTerminalMonitorThreads);
/* 3328 */     for (int i = 0; i < callsAtTerminalObservers.size(); ++i)
/*      */     {
/* 3330 */       removeTerminalCallMonitor((TsapiCallMonitor)callsAtTerminalObservers.elementAt(i), cause, privateData);
/*      */     }
/*      */ 
/* 3334 */     this.internalDeviceMonitors.removeAllElements();
/*      */ 
/* 3337 */     stopMonitorForThisDevice();
/*      */   }
/*      */ 
/*      */   boolean isMonitorSet()
/*      */   {
/* 3344 */     recreate();
/*      */ 
/* 3346 */     return (this.monitorDeviceXRefID != 0) || (this.monitorCallsViaDeviceXRefID != 0);
/*      */   }
/*      */ 
/*      */   boolean isCallsViaDeviceMonitorSet()
/*      */   {
/* 3359 */     recreate();
/*      */ 
/* 3361 */     return this.monitorCallsViaDeviceXRefID != 0;
/*      */   }
/*      */ 
/*      */   boolean isPredictiveCallsViaDeviceMonitorSet()
/*      */   {
/* 3368 */     recreate();
/*      */ 
/* 3370 */     return (this.monitorCallsViaDeviceXRefID != 0) && (this.monitorPredictiveCallsViaDevice);
/*      */   }
/*      */ 
/*      */   private void clearMonitorCallsViaDeviceAttributes()
/*      */   {
/* 3379 */     this.monitorCallsViaDeviceXRefID = 0;
/* 3380 */     this.monitorPredictiveCallsViaDevice = false;
/*      */   }
/*      */ 
/*      */   void addConnection(TSConnection tsConn)
/*      */   {
/* 3389 */     recreate();
/*      */ 
/* 3391 */     boolean doTerminalObservers = false;
/* 3392 */     boolean doAddressObservers = false;
/*      */ 
/* 3394 */     if ((tsConn.isTerminalConnection()) && 
/* 3396 */       (isTerminal()))
/*      */     {
/* 3398 */       synchronized (this.terminalConnections) {
/* 3399 */         if (!this.terminalConnections.contains(tsConn)) {
/* 3400 */           this.terminalConnections.addElement(tsConn);
/* 3401 */           doTerminalObservers = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3408 */     if ((!this.provider.isLucent()) || (!tsConn.isTerminalConnection()))
/*      */     {
/* 3410 */       synchronized (this.connections) {
/* 3411 */         if (!this.connections.contains(tsConn)) {
/* 3412 */           this.connections.addElement(tsConn);
/* 3413 */           doAddressObservers = true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3418 */     if ((!doTerminalObservers) && (!doAddressObservers))
/*      */     {
/* 3420 */       return;
/*      */     }
/*      */ 
/* 3423 */     TSCall call = tsConn.getTSCall();
/* 3424 */     call.addDeviceObservers(this, (doTerminalObservers) ? this.callsAtTerminalMonitorThreads : null, (doAddressObservers) ? this.callsAtAddressMonitorThreads : null, (doAddressObservers) ? this.callsViaAddressMonitorThreads : null, true);
/*      */   }
/*      */ 
/*      */   CSTAConnectionID matchConn(CSTAConnectionID connID)
/*      */   {
/* 3433 */     recreate();
/*      */ 
/* 3435 */     if (connID == null) {
/* 3436 */       return null;
/*      */     }
/*      */ 
/* 3439 */     synchronized (this.terminalConnections)
/*      */     {
/* 3441 */       for (int i = 0; i < this.terminalConnections.size(); ++i)
/*      */       {
/* 3443 */         TSConnection conn = (TSConnection)this.terminalConnections.elementAt(i);
/* 3444 */         if (connID.equals(conn.connID))
/*      */         {
/* 3446 */           return conn.connID;
/*      */         }
/*      */       }
/*      */     }
/* 3450 */     synchronized (this.connections)
/*      */     {
/* 3452 */       for (int i = 0; i < this.connections.size(); ++i)
/*      */       {
/* 3454 */         TSConnection conn = (TSConnection)this.connections.elementAt(i);
/* 3455 */         if (connID.equals(conn.connID))
/*      */         {
/* 3457 */           return conn.connID;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3462 */     return null;
/*      */   }
/*      */ 
/*      */   void removeConnection(TSConnection tsConn)
/*      */   {
/* 3470 */     recreate();
/*      */ 
/* 3472 */     if (this.connections.removeElement(tsConn))
/*      */     {
/* 3474 */       TSCall call = tsConn.getTSCall();
/* 3475 */       call.removeDefaultDeviceObservers(this, false);
/*      */     }
/* 3477 */     if (this.terminalConnections.removeElement(tsConn))
/*      */     {
/* 3479 */       TSCall call = tsConn.getTSCall();
/* 3480 */       call.removeDefaultDeviceObservers(this, true);
/*      */     }
/*      */ 
/* 3489 */     synchronized (this)
/*      */     {
/* 3491 */       synchronized (this.devNameVector)
/*      */       {
/* 3493 */         Vector keys = new Vector(this.devNameVector);
/* 3494 */         for (int i = 0; i < keys.size(); ++i)
/*      */         {
/* 3496 */           CSTAExtendedDeviceID devID = (CSTAExtendedDeviceID)keys.elementAt(i);
/* 3497 */           if ((devID.getDeviceIDStatus() != 0) || ((devID.getDeviceIDType() != 70) && (devID.getDeviceIDType() != 71))) {
/*      */             continue;
/*      */           }
/*      */ 
/* 3501 */           if (this.devNameVector.size() == 1)
/*      */           {
/* 3503 */             addName(new CSTAExtendedDeviceID("", (short)70, (short)1));
/*      */           }
/* 3505 */           this.provider.deleteDeviceFromHash(devID.getDeviceID());
/* 3506 */           this.devNameVector.removeElement(devID);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3513 */     testDelete();
/*      */   }
/*      */ 
/*      */   String getAgentID()
/*      */   {
/* 3520 */     recreate();
/*      */ 
/* 3523 */     if (this.provider.getCapabilities().getQueryDeviceInfo() != 0)
/*      */     {
/* 3525 */       if (this.provider.isLucentV5())
/*      */       {
/*      */         try
/*      */         {
/* 3529 */           CSTAEvent event = this.provider.tsapi.queryDeviceInfo(getName(), null);
/* 3530 */           if (event != null) {
/* 3531 */             Object replyPriv = event.getPrivData();
/* 3532 */             if (replyPriv instanceof LucentV5QueryDeviceInfoConfEvent)
/*      */             {
/* 3534 */               LucentV5QueryDeviceInfoConfEvent V5devInfoConf = (LucentV5QueryDeviceInfoConfEvent)replyPriv;
/* 3535 */               return V5devInfoConf.getAssociatedDevice();
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (TsapiUnableToSendException tue)
/*      */         {
/* 3544 */           throw tue;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3548 */           log.error(e.getMessage(), e);
/*      */         }
/*      */ 
/*      */       }
/* 3552 */       else if (this.provider.isLucent())
/*      */       {
/* 3554 */         LucentQueryCallClassifier lqcc = null;
/* 3555 */         lqcc = new LucentQueryCallClassifier();
/* 3556 */         CSTAPrivate reqPriv = lqcc.makeTsapiPrivate();
/*      */         try
/*      */         {
/* 3560 */           CSTAEvent event = this.provider.tsapi.queryDeviceInfo(getName(), reqPriv);
/* 3561 */           if (event != null) {
/* 3562 */             CSTAQueryDeviceInfoConfEvent devInfoConf = (CSTAQueryDeviceInfoConfEvent)event.getEvent();
/* 3563 */             return devInfoConf.getDevice();
/*      */           }
/* 3565 */           return null;
/*      */         }
/*      */         catch (TsapiUnableToSendException tue)
/*      */         {
/* 3572 */           throw tue;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3576 */           log.error(e.getMessage(), e);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3581 */     return null;
/*      */   }
/*      */ 
/*      */   synchronized void testDelete()
/*      */   {
/* 3602 */     if ((!this.terminalMonitorThreads.isEmpty()) || (!this.addressMonitorThreads.isEmpty()) || (!this.callsViaAddressMonitorThreads.isEmpty()) || (!this.callsAtAddressMonitorThreads.isEmpty()) || (!this.callsAtTerminalMonitorThreads.isEmpty()) || (this.tsRouteCallback != null) || (!this.tsACDVector.isEmpty()) || (!this.internalDeviceMonitors.isEmpty()) || (this.connections.size() > 0) || (this.terminalConnections.size() > 0))
/*      */     {
/* 3613 */       return;
/*      */     }
/*      */ 
/* 3618 */     stopMonitorForThisDevice();
/*      */ 
/* 3621 */     if (this.refCount > 0)
/*      */     {
/* 3623 */       return;
/*      */     }
/*      */ 
/* 3630 */     for (int i = 0; i < this.tsAgentTermVector.size(); ++i)
/*      */     {
/* 3632 */       TSAgent tsAgent = (TSAgent)this.tsAgentTermVector.elementAt(i);
/* 3633 */       if ((tsAgent != null) && (tsAgent.isReferenced()))
/*      */       {
/* 3635 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3640 */     realDelete();
/*      */   }
/*      */ 
/*      */   private void realDelete()
/*      */   {
/* 3649 */     if (this.curState.wasDeleteDone())
/*      */     {
/* 3651 */       log.info("Device " + this + " realDelete: already deleted - no further action taken, " + this.provider);
/* 3652 */       return;
/*      */     }
/*      */ 
/* 3659 */     log.info("Device " + this + " being deleted for " + this.provider);
/*      */ 
/* 3663 */     stopMonitorForThisDevice();
/*      */ 
/* 3673 */     if ((Tsapi.getTSDevicePerformanceOptimization() == true) && (this.asyncInitializationComplete) && (!isDeviceExternal()))
/*      */     {
/* 3676 */       log.info("NOT deleting " + this + " due to TSDevice Performance Optimization");
/* 3677 */       return;
/*      */     }
/*      */ 
/* 3685 */     setState(new TSDeviceStateBeingDeleted());
/*      */ 
/* 3689 */     this.provider.deleteInstanceOfDeviceFromHash(this);
/*      */ 
/* 3691 */     setState(new TSDeviceStateDeleted());
/*      */   }
/*      */ 
/*      */   private void stopMonitorForThisDevice()
/*      */   {
/* 3700 */     log.info("stopMonitorForThisDevice: Device " + this + " about to consider DevMon stop (xref=" + this.monitorDeviceXRefID + " mcvdxref=" + this.monitorCallsViaDeviceXRefID + "), for " + this.provider);
/*      */ 
/* 3705 */     if (this.monitorDeviceXRefID != 0) {
/* 3706 */       this.provider.deleteMonitor(this.monitorDeviceXRefID);
/*      */     }
/*      */ 
/* 3709 */     if (this.monitorCallsViaDeviceXRefID != 0) {
/* 3710 */       this.provider.deleteMonitor(this.monitorCallsViaDeviceXRefID);
/*      */     }
/*      */ 
/* 3713 */     if (this.monitorDeviceXRefID != 0)
/*      */     {
/* 3715 */       if (this.provider.getCapabilities().getMonitorStop() != 0)
/*      */       {
/* 3717 */         if (this.provider.getState() != 18)
/*      */         {
/*      */           try
/*      */           {
/* 3721 */             this.provider.tsapi.monitorStop(this.monitorDeviceXRefID, null, null);
/*      */           }
/*      */           catch (Exception e) {
/* 3724 */             log.error(e.getMessage(), e);
/*      */           }
/*      */ 
/*      */         }
/*      */         else {
/* 3729 */           log.info("stopMonitorForThisDevice: " + this + " Skipping Monitor Stop because Provider is " + "in SHUTDOWN state, monitorDeviceXRefID=" + this.monitorDeviceXRefID + " for " + this.provider);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3735 */       this.monitorDeviceXRefID = 0;
/*      */     }
/*      */ 
/* 3738 */     if (this.monitorCallsViaDeviceXRefID == 0)
/*      */       return;
/* 3740 */     if (this.provider.getCapabilities().getMonitorStop() != 0)
/*      */     {
/* 3742 */       if (this.provider.getState() != 18)
/*      */       {
/*      */         try
/*      */         {
/* 3746 */           this.provider.tsapi.monitorStop(this.monitorCallsViaDeviceXRefID, null, null);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3750 */           log.error(e.getMessage(), e);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 3756 */         log.info("stopMonitorForThisDevice: " + this + " Skipping Monitor Stop because Provider is " + "in SHUTDOWN state, " + "monitorCallsViaDeviceXRefID=" + this.monitorCallsViaDeviceXRefID + " for " + this.provider);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3765 */     clearMonitorCallsViaDeviceAttributes();
/*      */ 
/* 3769 */     this.provider.removeAllCallsForDomain(this);
/*      */   }
/*      */ 
/*      */   public String referenced()
/*      */   {
/* 3779 */     recreate();
/*      */ 
/* 3781 */     this.refCount += 1;
/* 3782 */     if (!this.wasReferenced)
/*      */     {
/* 3786 */       g_RefCnt += 1;
/* 3787 */       this.wasReferenced = true;
/*      */     }
/*      */ 
/* 3790 */     return ((CSTAExtendedDeviceID)this.devNameVector.lastElement()).getDeviceID();
/*      */   }
/*      */ 
/*      */   public void unreferenced()
/*      */   {
/* 3800 */     recreate();
/*      */ 
/* 3802 */     this.refCount -= 1;
/* 3803 */     testDelete();
/*      */   }
/*      */ 
/*      */   public short getDeviceType()
/*      */   {
/* 3809 */     recreate();
/*      */ 
/* 3811 */     if (!this.asyncInitializationComplete) {
/* 3812 */       log.info("getDeviceType() for " + this);
/*      */     }
/*      */ 
/* 3815 */     waitForAsyncInitialization();
/*      */ 
/* 3817 */     return this.deviceType;
/*      */   }
/*      */ 
/*      */   void setDeviceType(short _deviceType)
/*      */   {
/* 3823 */     recreate();
/*      */ 
/* 3825 */     this.deviceType = _deviceType;
/*      */   }
/*      */ 
/*      */   String getName()
/*      */   {
/* 3831 */     recreate();
/*      */ 
/* 3833 */     return internalGetName();
/*      */   }
/*      */ 
/*      */   private String internalGetName()
/*      */   {
/* 3838 */     return ((CSTAExtendedDeviceID)this.devNameVector.lastElement()).getDeviceID();
/*      */   }
/*      */ 
/*      */   Vector<CSTAExtendedDeviceID> getKeys()
/*      */   {
/* 3843 */     recreate();
/*      */ 
/* 3845 */     return this.devNameVector;
/*      */   }
/*      */ 
/*      */   void addName(CSTAExtendedDeviceID deviceID)
/*      */   {
/* 3850 */     recreate();
/* 3851 */     String devName = getName();
/*      */ 
/* 3853 */     synchronized (this.devNameVector)
/*      */     {
/* 3855 */       if (!this.devNameVector.contains(deviceID))
/*      */       {
/* 3857 */         log.info("Renaming device " + this + " with name " + devName + " to " + deviceID + " for " + this.provider);
/* 3858 */         this.devNameVector.addElement(deviceID);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isTerminal()
/*      */   {
/* 3867 */     recreate();
/*      */ 
/* 3869 */     if (!this.asyncInitializationComplete) {
/* 3870 */       log.info("isTerminal() for " + this);
/*      */     }
/*      */ 
/* 3873 */     waitForAsyncInitialization();
/*      */ 
/* 3875 */     return this.isATerminal;
/*      */   }
/*      */ 
/*      */   boolean isDeviceExternal()
/*      */   {
/* 3880 */     recreate();
/*      */ 
/* 3882 */     if (!this.asyncInitializationComplete) {
/* 3883 */       log.info("isDeviceExternal() for " + this);
/*      */     }
/*      */ 
/* 3886 */     waitForAsyncInitialization();
/*      */ 
/* 3888 */     return this.isExternal;
/*      */   }
/*      */ 
/*      */   boolean wereChangesHeldPending()
/*      */   {
/* 3894 */     recreate();
/*      */ 
/* 3896 */     return this.changesWereHeldPending;
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 3902 */     return "TSDevice[" + internalGetName() + "]@" + Integer.toHexString(super.hashCode());
/*      */   }
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/*      */     try
/*      */     {
/* 3910 */       log.info("TSDevice " + this + " - finalize() in state " + this.curState);
/* 3911 */       realDelete();
/*      */     }
/*      */     finally
/*      */     {
/* 3915 */       super.finalize();
/*      */     }
/*      */   }
/*      */ 
/*      */   void setAssociatedDevice(String associatedDevice)
/*      */   {
/* 3924 */     recreate();
/*      */ 
/* 3926 */     this.associatedDevice = associatedDevice;
/*      */   }
/*      */ 
/*      */   String getAssociatedDevice()
/*      */   {
/* 3934 */     recreate();
/*      */ 
/* 3936 */     if (!this.asyncInitializationComplete) {
/* 3937 */       log.info("getAssociatedDevice() for " + this);
/*      */     }
/*      */ 
/* 3940 */     waitForAsyncInitialization();
/*      */ 
/* 3942 */     return this.associatedDevice;
/*      */   }
/*      */ 
/*      */   void setAssociatedClass(short associatedClass)
/*      */   {
/* 3950 */     recreate();
/*      */ 
/* 3952 */     this.associatedClass = associatedClass;
/*      */   }
/*      */ 
/*      */   short getAssociatedClass()
/*      */   {
/* 3960 */     recreate();
/*      */ 
/* 3962 */     if (!this.asyncInitializationComplete) {
/* 3963 */       log.info("getAssociatedClass() for " + this);
/*      */     }
/*      */ 
/* 3966 */     waitForAsyncInitialization();
/*      */ 
/* 3968 */     return this.associatedClass;
/*      */   }
/*      */ 
/*      */   void setIsATerminal(boolean b)
/*      */   {
/* 4080 */     recreate();
/*      */ 
/* 4082 */     this.isATerminal = b;
/*      */   }
/*      */ 
/*      */   void setIsExternal(boolean b)
/*      */   {
/* 4087 */     recreate();
/*      */ 
/* 4089 */     this.isExternal = b;
/*      */   }
/*      */ 
/*      */   synchronized void notifyAsyncInitializationComplete()
/*      */   {
/* 4099 */     recreate();
/*      */ 
/* 4101 */     log.info("Initialization complete for TSDevice " + this + " - making values available - for " + this.provider);
/* 4102 */     this.asyncInitializationComplete = true;
/* 4103 */     super.notifyAll();
/*      */   }
/*      */ 
/*      */   public String getDomainName()
/*      */   {
/* 4111 */     return internalGetName();
/*      */   }
/*      */ 
/*      */   public static boolean hasMixOfPublicAndPrivate(Vector<CSTAExtendedDeviceID> deviceList)
/*      */   {
/* 4122 */     if (deviceList.size() <= 1) {
/* 4123 */       return false;
/*      */     }
/* 4125 */     CSTAExtendedDeviceID tempDevID = (CSTAExtendedDeviceID)deviceList.elementAt(0);
/* 4126 */     String type = null;
/*      */ 
/* 4128 */     if (tempDevID.hasPrivateDeviceIDType())
/* 4129 */       type = "PRIVATE";
/* 4130 */     else if (tempDevID.hasPublicDeviceIDType())
/* 4131 */       type = "PUBLIC";
/*      */     else {
/* 4133 */       return true;
/*      */     }
/* 4135 */     int i = 1; if (i < deviceList.size()) {
/* 4136 */       tempDevID = (CSTAExtendedDeviceID)deviceList.elementAt(i);
/*      */ 
/* 4138 */       if ((type.equals("PUBLIC")) && (!tempDevID.hasPublicDeviceIDType())) {
/* 4139 */         return true;
/*      */       }
/* 4141 */       return (type.equals("PRIVATE")) && (!tempDevID.hasPrivateDeviceIDType());
/*      */     }
/*      */ 
/* 4146 */     return false;
/*      */   }
/*      */ 
/*      */   boolean isForExternalDeviceMatchingLocalExtensionNumber(CSTAExtendedDeviceID devIDOnCall)
/*      */   {
/* 4159 */     if (hasMixOfPublicAndPrivate(this.devNameVector)) {
/* 4160 */       StringBuffer tmpStrBuf = new StringBuffer();
/* 4161 */       for (CSTAExtendedDeviceID tmpDevID : this.devNameVector) {
/* 4162 */         tmpStrBuf.append(tmpDevID + " ");
/*      */       }
/* 4164 */       log.info("TSDevice [" + this + "] has both public and private deviceID types. Here is a list : " + tmpStrBuf.toString());
/*      */     }
/*      */ 
/* 4167 */     CSTAExtendedDeviceID lastDeviceExtDevID = (CSTAExtendedDeviceID)getKeys().lastElement();
/*      */ 
/* 4170 */     return (lastDeviceExtDevID.hasPrivateDeviceIDType()) && (devIDOnCall.hasPublicDeviceIDType());
/*      */   }
/*      */ 
/*      */   public CallControlForwarding[] createForwarding()
/*      */   {
/*      */     TsapiCallControlForwarding[] tsapiInstructions;
/* 4178 */     synchronized (this.fwdVector) {
/* 4179 */       tsapiInstructions = new TsapiCallControlForwarding[this.fwdVector.size()];
/* 4180 */       this.fwdVector.copyInto(tsapiInstructions);
/*      */     }
/* 4182 */     if (tsapiInstructions.length == 0)
/*      */     {
/* 4184 */       return null;
/*      */     }
/* 4186 */     CallControlForwarding[] instructions = new CallControlForwarding[tsapiInstructions.length];
/*      */     int i;
/* 4187 */     for (i = 0; i < tsapiInstructions.length; ++i)
/*      */     {
/* 4189 */       if (tsapiInstructions[i].getFilter() == 1)
/*      */       {
/* 4191 */         instructions[i] = new CallControlForwarding(tsapiInstructions[i].getDestinationAddress(), tsapiInstructions[i].getType());
/*      */       }
/*      */       else
/*      */       {
/* 4196 */         instructions[i] = new CallControlForwarding(tsapiInstructions[i].getDestinationAddress(), tsapiInstructions[i].getType(), tsapiInstructions[i].getFilter() == 2);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4201 */     return instructions;
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSDevice
 * JD-Core Version:    0.5.4
 */