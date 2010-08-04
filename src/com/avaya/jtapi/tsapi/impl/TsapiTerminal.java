/*      */ package com.avaya.jtapi.tsapi.impl;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.ITsapiAgent;
/*      */ import com.avaya.jtapi.tsapi.ITsapiTerminal;
/*      */ import com.avaya.jtapi.tsapi.LucentAddress;
/*      */ import com.avaya.jtapi.tsapi.LucentV5TerminalEx;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*      */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivate;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
/*      */ import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSAgent;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
/*      */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Vector;
/*      */ import javax.telephony.Address;
/*      */ import javax.telephony.CallListener;
/*      */ import javax.telephony.CallObserver;
/*      */ import javax.telephony.InvalidArgumentException;
/*      */ import javax.telephony.MethodNotSupportedException;
/*      */ import javax.telephony.PlatformException;
/*      */ import javax.telephony.ResourceUnavailableException;
/*      */ import javax.telephony.Terminal;
/*      */ import javax.telephony.TerminalConnection;
/*      */ import javax.telephony.TerminalListener;
/*      */ import javax.telephony.TerminalObserver;
/*      */ import javax.telephony.callcenter.ACDAddress;
/*      */ import javax.telephony.callcenter.Agent;
/*      */ import javax.telephony.capabilities.TerminalCapabilities;
/*      */ import javax.telephony.privatedata.PrivateData;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ class TsapiTerminal
/*      */   implements ITsapiTerminal, PrivateData, LucentV5TerminalEx
/*      */ {
/*   58 */   Logger logger = Logger.getLogger(TsapiTerminal.class);
/*      */   TSDevice tsDevice;
/* 1065 */   CSTAPrivate privData = null;
/*      */   String name;
/*      */ 
/*      */   public final String getName()
/*      */   {
/*   67 */     TsapiTrace.traceEntry("getName[]", this);
/*      */     try
/*      */     {
/*   70 */       TsapiTrace.traceExit("getName[]", this);
/*   71 */       return this.name;
/*      */     }
/*      */     finally
/*      */     {
/*   75 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final String getDirectoryName()
/*      */   {
/*   82 */     TsapiTrace.traceEntry("getDirectoryName[]", this);
/*   83 */     String name = this.tsDevice.getDirectoryName();
/*   84 */     TsapiTrace.traceExit("getDirectoryName[]", this);
/*   85 */     return name; } 
/*      */   // ERROR //
/*      */   public final javax.telephony.Provider getProvider() { // Byte code:
/*      */     //   0: ldc 9
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_0
/*      */     //   7: getfield 7	com/avaya/jtapi/tsapi/impl/TsapiTerminal:tsDevice	Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   10: invokevirtual 10	com/avaya/jtapi/tsapi/impl/core/TSDevice:getTSProviderImpl	()Lcom/avaya/jtapi/tsapi/impl/core/TSProviderImpl;
/*      */     //   13: astore_1
/*      */     //   14: aload_1
/*      */     //   15: ifnull +25 -> 40
/*      */     //   18: aload_1
/*      */     //   19: iconst_0
/*      */     //   20: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   23: checkcast 12	javax/telephony/Provider
/*      */     //   26: astore_2
/*      */     //   27: ldc 9
/*      */     //   29: aload_0
/*      */     //   30: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   33: aload_2
/*      */     //   34: astore_3
/*      */     //   35: jsr +25 -> 60
/*      */     //   38: aload_3
/*      */     //   39: areturn
/*      */     //   40: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   43: dup
/*      */     //   44: iconst_4
/*      */     //   45: iconst_0
/*      */     //   46: ldc 14
/*      */     //   48: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   51: athrow
/*      */     //   52: astore 4
/*      */     //   54: jsr +6 -> 60
/*      */     //   57: aload 4
/*      */     //   59: athrow
/*      */     //   60: astore 5
/*      */     //   62: aload_0
/*      */     //   63: aconst_null
/*      */     //   64: putfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   67: ret 5
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   6	38	52	finally
/*      */     //   40	57	52	finally } 
/*  119 */   public final Address[] getAddresses() { TsapiTrace.traceEntry("getAddresses[]", this);
/*      */     try
/*      */     {
/*  122 */       Vector tsAddrDevices = this.tsDevice.getTSAddressDevices();
/*  123 */       if ((tsAddrDevices == null) || (tsAddrDevices.size() == 0))
/*      */       {
/*  125 */         TsapiTrace.traceExit("getAddresses[]", this);
/*  126 */         return null;
/*      */       }
/*      */ 
/*  129 */       Address[] tsapiAddr = new Address[tsAddrDevices.size()];
/*  130 */       for (int i = 0; i < tsAddrDevices.size(); ++i)
/*      */       {
/*  132 */         tsapiAddr[i] = ((Address)TsapiCreateObject.getTsapiObject((TSDevice)tsAddrDevices.elementAt(i), true));
/*      */       }
/*      */ 
/*  135 */       TsapiTrace.traceExit("getAddresses[]", this);
/*  136 */       return tsapiAddr;
/*      */     }
/*      */     finally
/*      */     {
/*  140 */       this.privData = null; }  } 
/*      */   // ERROR //
/*      */   public final TerminalConnection[] getTerminalConnections() { // Byte code:
/*      */     //   0: ldc 22
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_0
/*      */     //   7: getfield 7	com/avaya/jtapi/tsapi/impl/TsapiTerminal:tsDevice	Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   10: invokevirtual 23	com/avaya/jtapi/tsapi/impl/core/TSDevice:getTSTerminalConnections	()Ljava/util/Vector;
/*      */     //   13: astore_1
/*      */     //   14: aload_1
/*      */     //   15: ifnonnull +16 -> 31
/*      */     //   18: ldc 22
/*      */     //   20: aload_0
/*      */     //   21: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   24: aconst_null
/*      */     //   25: astore_2
/*      */     //   26: jsr +109 -> 135
/*      */     //   29: aload_2
/*      */     //   30: areturn
/*      */     //   31: aload_1
/*      */     //   32: dup
/*      */     //   33: astore_2
/*      */     //   34: monitorenter
/*      */     //   35: aload_1
/*      */     //   36: invokevirtual 18	java/util/Vector:size	()I
/*      */     //   39: ifne +18 -> 57
/*      */     //   42: ldc 22
/*      */     //   44: aload_0
/*      */     //   45: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   48: aconst_null
/*      */     //   49: astore_3
/*      */     //   50: aload_2
/*      */     //   51: monitorexit
/*      */     //   52: jsr +83 -> 135
/*      */     //   55: aload_3
/*      */     //   56: areturn
/*      */     //   57: aload_1
/*      */     //   58: invokevirtual 18	java/util/Vector:size	()I
/*      */     //   61: anewarray 24	javax/telephony/TerminalConnection
/*      */     //   64: astore_3
/*      */     //   65: iconst_0
/*      */     //   66: istore 4
/*      */     //   68: iload 4
/*      */     //   70: aload_1
/*      */     //   71: invokevirtual 18	java/util/Vector:size	()I
/*      */     //   74: if_icmpge +29 -> 103
/*      */     //   77: aload_3
/*      */     //   78: iload 4
/*      */     //   80: aload_1
/*      */     //   81: iload 4
/*      */     //   83: invokevirtual 20	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*      */     //   86: checkcast 25	com/avaya/jtapi/tsapi/impl/core/TSConnection
/*      */     //   89: iconst_0
/*      */     //   90: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   93: checkcast 24	javax/telephony/TerminalConnection
/*      */     //   96: aastore
/*      */     //   97: iinc 4 1
/*      */     //   100: goto -32 -> 68
/*      */     //   103: ldc 22
/*      */     //   105: aload_0
/*      */     //   106: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   109: aload_3
/*      */     //   110: astore 4
/*      */     //   112: aload_2
/*      */     //   113: monitorexit
/*      */     //   114: jsr +21 -> 135
/*      */     //   117: aload 4
/*      */     //   119: areturn
/*      */     //   120: astore 5
/*      */     //   122: aload_2
/*      */     //   123: monitorexit
/*      */     //   124: aload 5
/*      */     //   126: athrow
/*      */     //   127: astore 6
/*      */     //   129: jsr +6 -> 135
/*      */     //   132: aload 6
/*      */     //   134: athrow
/*      */     //   135: astore 7
/*      */     //   137: aload_0
/*      */     //   138: aconst_null
/*      */     //   139: putfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   142: ret 7
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   35	52	120	finally
/*      */     //   57	114	120	finally
/*      */     //   120	124	120	finally
/*      */     //   6	29	127	finally
/*      */     //   31	55	127	finally
/*      */     //   57	117	127	finally
/*      */     //   120	132	127	finally } 
/*  190 */   public void addObserver(TerminalObserver observer) throws TsapiResourceUnavailableException { TsapiTrace.traceEntry("addObserver[TerminalObserver observer]", this);
/*      */     try
/*      */     {
/*  193 */       TSProviderImpl prov = null;
/*  194 */       prov = this.tsDevice.getTSProviderImpl();
/*      */ 
/*  196 */       if (prov == null)
/*      */       {
/*  198 */         throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */       }
/*      */ 
/*  203 */       Vector observers = prov.getTerminalMonitorThreads();
/*      */ 
/*  205 */       TsapiTerminalMonitor obs = null;
/*      */ 
/*  207 */       boolean found = false;
/*      */ 
/*  210 */       synchronized (observers)
/*      */       {
/*  212 */         for (int i = 0; i < observers.size(); ++i)
/*      */         {
/*  214 */           obs = (TsapiTerminalMonitor)observers.elementAt(i);
/*  215 */           if (obs.getObserver() != observer)
/*      */             continue;
/*  217 */           found = true;
/*  218 */           break;
/*      */         }
/*      */ 
/*  221 */         if (!found)
/*      */         {
/*  223 */           obs = new TsapiTerminalMonitor(prov, observer);
/*      */         }
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/*  229 */         this.tsDevice.addTerminalMonitor(obs);
/*      */       }
/*      */       catch (TsapiResourceUnavailableException e)
/*      */       {
/*  234 */         if ((!found) && (obs != null))
/*      */         {
/*  236 */           prov.removeTerminalMonitorThread(obs);
/*      */         }
/*  238 */         throw e;
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  244 */       this.privData = null;
/*      */     }
/*  246 */     TsapiTrace.traceExit("addObserver[TerminalObserver observer]", this); }
/*      */ 
/*      */ 
/*      */   public TerminalObserver[] getObservers()
/*      */   {
/*  254 */     TsapiTrace.traceEntry("getObservers[]", this);
/*      */     try
/*      */     {
/*  257 */       Vector tsapiTerminalObservers = this.tsDevice.getTerminalObservers();
/*      */ 
/*  259 */       if ((tsapiTerminalObservers == null) || (tsapiTerminalObservers.size() == 0))
/*      */       {
/*  261 */         TsapiTrace.traceExit("getObservers[]", this);
/*  262 */         return null;
/*      */       }
/*      */ 
/*  270 */       Vector observers = new Vector();
/*      */ 
/*  272 */       for (int i = 0; i < tsapiTerminalObservers.size(); ++i)
/*      */       {
/*  274 */         TsapiTerminalMonitor obs = (TsapiTerminalMonitor)tsapiTerminalObservers.elementAt(i);
/*  275 */         if (obs.getObserver() != null)
/*  276 */           observers.add(obs.getObserver());
/*      */       }
/*  278 */       TsapiTrace.traceExit("getObservers[]", this);
/*  279 */       if (observers.size() > 0) {
/*  280 */         return (TerminalObserver[])observers.toArray(new TerminalObserver[1]);
/*      */       }
/*  282 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*  286 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeObserver(TerminalObserver observer)
/*      */   {
/*  295 */     TsapiTrace.traceEntry("removeObserver[TerminalObserver observer]", this);
/*      */     try
/*      */     {
/*  298 */       Vector tsapiTerminalObservers = this.tsDevice.getTerminalObservers();
/*      */ 
/*  300 */       if ((tsapiTerminalObservers == null) || (tsapiTerminalObservers.size() == 0))
/*      */       {
/*  302 */         TsapiTrace.traceExit("removeObserver[TerminalObserver observer]", this);
/*  303 */         return;
/*      */       }
/*      */ 
/*  306 */       for (int i = 0; i < tsapiTerminalObservers.size(); ++i)
/*      */       {
/*  308 */         TsapiTerminalMonitor obs = (TsapiTerminalMonitor)tsapiTerminalObservers.elementAt(i);
/*  309 */         if (obs.getObserver() != observer)
/*      */           continue;
/*  311 */         this.tsDevice.removeTerminalMonitor(obs);
/*  312 */         TsapiTrace.traceExit("removeObserver[TerminalObserver observer]", this);
/*  313 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  319 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addCallObserver(CallObserver observer)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*  326 */     TsapiTrace.traceEntry("addCallObserver[CallObserver observer]", this);
/*  327 */     addTsapiCallEventMonitor(observer, null);
/*  328 */     TsapiTrace.traceExit("addCallObserver[CallObserver observer]", this);
/*      */   }
/*      */ 
/*      */   private void addTsapiCallEventMonitor(CallObserver observer, CallListener listener)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*      */     try
/*      */     {
/*  342 */       TSProviderImpl prov = this.tsDevice.getTSProviderImpl();
/*      */ 
/*  344 */       if (prov == null) {
/*  345 */         throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */       }
/*      */ 
/*  350 */       Vector observers = prov.getCallMonitorThreads();
/*      */ 
/*  352 */       TsapiCallMonitor obs = null;
/*  353 */       TsapiCallMonitor obsToUse = null;
/*      */ 
/*  356 */       synchronized (observers) {
/*  357 */         for (int i = 0; i < observers.size(); ++i) {
/*  358 */           obs = (TsapiCallMonitor)observers.elementAt(i);
/*  359 */           if (observer != null) {
/*  360 */             if (obs.getObserver() != observer) continue;
/*  361 */             obsToUse = obs;
/*      */ 
/*  363 */             break;
/*      */           }
/*  365 */           if ((listener == null) || 
/*  366 */             (obs.getListener() != listener)) continue;
/*  367 */           obsToUse = obs;
/*  368 */           break;
/*      */         }
/*      */ 
/*  373 */         if (obsToUse == null) {
/*  374 */           if (observer != null) {
/*  375 */             obsToUse = new TsapiCallMonitor(prov, observer);
/*      */           }
/*  382 */           else if (listener != null) {
/*  383 */             obsToUse = new TsapiCallMonitor(prov, listener);
/*      */           }
/*  385 */           if (obsToUse == null) {
/*  386 */             throw new TsapiPlatformException(4, 0, "could not allocate Monitor wrapper");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  410 */       this.tsDevice.addTerminalCallMonitor(obsToUse);
/*      */     } finally {
/*  412 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public CallObserver[] getCallObservers()
/*      */   {
/*  418 */     TsapiTrace.traceEntry("getCallObservers[]", this);
/*      */     try
/*      */     {
/*  421 */       Vector tsapiTerminalCallObservers = this.tsDevice.getTerminalCallObservers();
/*      */ 
/*  423 */       if ((tsapiTerminalCallObservers == null) || (tsapiTerminalCallObservers.size() == 0))
/*      */       {
/*  425 */         TsapiTrace.traceExit("getCallObservers[]", this);
/*  426 */         return null;
/*      */       }
/*      */ 
/*  429 */       ArrayList callObserverList = new ArrayList();
/*  430 */       CallObserver[] observers = null;
/*      */ 
/*  432 */       for (Object obs : tsapiTerminalCallObservers)
/*      */       {
/*  434 */         CallObserver callObserver = ((TsapiCallMonitor) obs).getObserver();
/*  435 */         if (callObserver != null)
/*  436 */           callObserverList.add(callObserver);
/*      */       }
/*  438 */       observers = new CallObserver[callObserverList.size()];
/*  439 */       return (CallObserver[])callObserverList.toArray(observers);
/*      */     }
/*      */     finally
/*      */     {
/*  443 */       this.privData = null;
/*  444 */       TsapiTrace.traceExit("getCallObservers[]", this);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeCallObserver(CallObserver observer)
/*      */   {
/*  450 */     TsapiTrace.traceEntry("removeCallObserver[CallObserver observer]", this);
/*      */     try
/*      */     {
/*  453 */       Vector tsapiTerminalCallObservers = this.tsDevice.getTerminalCallObservers();
/*      */ 
/*  455 */       if ((tsapiTerminalCallObservers == null) || (tsapiTerminalCallObservers.size() == 0))
/*      */       {
/*  457 */         TsapiTrace.traceExit("removeCallObserver[CallObserver observer]", this);
/*  458 */         return;
/*      */       }
/*      */ 
/*  461 */       for (int i = 0; i < tsapiTerminalCallObservers.size(); ++i)
/*      */       {
/*  463 */         TsapiCallMonitor obs = (TsapiCallMonitor)tsapiTerminalCallObservers.elementAt(i);
/*  464 */         if (obs.getObserver() != observer)
/*      */           continue;
/*  466 */         this.tsDevice.removeTerminalCallMonitor(obs);
/*  467 */         TsapiTrace.traceExit("removeCallObserver[CallObserver observer]", this);
/*  468 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  474 */       this.privData = null;
/*      */     }
/*  476 */     TsapiTrace.traceExit("removeCallObserver[CallObserver observer]", this);
/*      */   }
/*      */ 
/*      */   public final TerminalCapabilities getCapabilities()
/*      */   {
/*  484 */     TsapiTrace.traceEntry("getCapabilities[]", this);
/*      */     try
/*      */     {
/*  487 */       TerminalCapabilities caps = this.tsDevice.getTsapiTerminalCapabilities();
/*  488 */       TsapiTrace.traceExit("getCapabilities[]", this);
/*  489 */       return caps;
/*      */     }
/*      */     finally
/*      */     {
/*  493 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final TerminalCapabilities getTerminalCapabilities(Terminal terminal, Address address)
/*      */     throws InvalidArgumentException, PlatformException
/*      */   {
/*  501 */     TsapiTrace.traceEntry("getTerminalCapabilities[Terminal terminal, Address address]", this);
/*  502 */     TerminalCapabilities caps = getCapabilities();
/*  503 */     TsapiTrace.traceExit("getTerminalCapabilities[Terminal terminal, Address address]", this);
/*  504 */     return caps;
/*      */   }
/*      */ 
/*      */   public final boolean getDoNotDisturb()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  513 */     TsapiTrace.traceEntry("getDoNotDisturb[]", this);
/*      */     try
/*      */     {
/*  516 */       boolean dnd = this.tsDevice.getDoNotDisturb();
/*  517 */       TsapiTrace.traceExit("getDoNotDisturb[]", this);
/*  518 */       return dnd;
/*      */     }
/*      */     finally
/*      */     {
/*  522 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void setDoNotDisturb(boolean enable)
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  533 */     TsapiTrace.traceEntry("setDoNotDisturb[boolean enable]", this);
/*      */     try
/*      */     {
/*  536 */       this.tsDevice.setDoNotDisturb(enable, this.privData);
/*      */     }
/*      */     finally
/*      */     {
/*  540 */       this.privData = null;
/*      */     }
/*  542 */     TsapiTrace.traceExit("setDoNotDisturb[boolean enable]", this); } 
/*      */   // ERROR //
/*      */   public final TerminalConnection pickup(javax.telephony.Connection pickConnection, Address terminalAddress) throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException { // Byte code:
/*      */     //   0: ldc 76
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_1
/*      */     //   7: instanceof 77
/*      */     //   10: ifne +15 -> 25
/*      */     //   13: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   16: dup
/*      */     //   17: iconst_3
/*      */     //   18: iconst_0
/*      */     //   19: ldc 79
/*      */     //   21: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   24: athrow
/*      */     //   25: aload_2
/*      */     //   26: instanceof 81
/*      */     //   29: ifne +15 -> 44
/*      */     //   32: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   35: dup
/*      */     //   36: iconst_3
/*      */     //   37: iconst_0
/*      */     //   38: ldc 82
/*      */     //   40: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   43: athrow
/*      */     //   44: aload_1
/*      */     //   45: checkcast 83	com/avaya/jtapi/tsapi/impl/TsapiConnection
/*      */     //   48: invokevirtual 84	com/avaya/jtapi/tsapi/impl/TsapiConnection:getTSConnection	()Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*      */     //   51: astore_3
/*      */     //   52: aload_2
/*      */     //   53: checkcast 85	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   56: invokevirtual 86	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   59: astore 4
/*      */     //   61: aload_3
/*      */     //   62: ifnull +66 -> 128
/*      */     //   65: aload 4
/*      */     //   67: ifnull +61 -> 128
/*      */     //   70: aload 4
/*      */     //   72: aload_3
/*      */     //   73: aload 4
/*      */     //   75: aload_0
/*      */     //   76: getfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   79: invokevirtual 87	com/avaya/jtapi/tsapi/impl/core/TSDevice:pickup	(Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*      */     //   82: astore 5
/*      */     //   84: aload 5
/*      */     //   86: ifnull +30 -> 116
/*      */     //   89: aload 5
/*      */     //   91: iconst_0
/*      */     //   92: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   95: checkcast 24	javax/telephony/TerminalConnection
/*      */     //   98: astore 6
/*      */     //   100: ldc 76
/*      */     //   102: aload_0
/*      */     //   103: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   106: aload 6
/*      */     //   108: astore 7
/*      */     //   110: jsr +54 -> 164
/*      */     //   113: aload 7
/*      */     //   115: areturn
/*      */     //   116: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   119: dup
/*      */     //   120: iconst_4
/*      */     //   121: iconst_0
/*      */     //   122: ldc 88
/*      */     //   124: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   127: athrow
/*      */     //   128: aload_3
/*      */     //   129: ifnonnull +15 -> 144
/*      */     //   132: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   135: dup
/*      */     //   136: iconst_4
/*      */     //   137: iconst_0
/*      */     //   138: ldc 89
/*      */     //   140: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   143: athrow
/*      */     //   144: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   147: dup
/*      */     //   148: iconst_4
/*      */     //   149: iconst_0
/*      */     //   150: ldc 90
/*      */     //   152: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   155: athrow
/*      */     //   156: astore 8
/*      */     //   158: jsr +6 -> 164
/*      */     //   161: aload 8
/*      */     //   163: athrow
/*      */     //   164: astore 9
/*      */     //   166: aload_0
/*      */     //   167: aconst_null
/*      */     //   168: putfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   171: ret 9
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   6	113	156	finally
/*      */     //   116	161	156	finally } 
/*      */   // ERROR //
/*      */   public final TerminalConnection pickup(TerminalConnection pickTermConn, Address terminalAddress) throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException { // Byte code:
/*      */     //   0: ldc 91
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_1
/*      */     //   7: instanceof 92
/*      */     //   10: ifne +15 -> 25
/*      */     //   13: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   16: dup
/*      */     //   17: iconst_3
/*      */     //   18: iconst_0
/*      */     //   19: ldc 93
/*      */     //   21: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   24: athrow
/*      */     //   25: aload_2
/*      */     //   26: instanceof 81
/*      */     //   29: ifne +15 -> 44
/*      */     //   32: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   35: dup
/*      */     //   36: iconst_3
/*      */     //   37: iconst_0
/*      */     //   38: ldc 82
/*      */     //   40: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   43: athrow
/*      */     //   44: aload_1
/*      */     //   45: checkcast 94	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection
/*      */     //   48: invokevirtual 95	com/avaya/jtapi/tsapi/impl/TsapiTerminalConnection:getTSConnection	()Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*      */     //   51: astore_3
/*      */     //   52: aload_2
/*      */     //   53: checkcast 85	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   56: invokevirtual 86	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   59: astore 4
/*      */     //   61: aload_3
/*      */     //   62: ifnull +66 -> 128
/*      */     //   65: aload 4
/*      */     //   67: ifnull +61 -> 128
/*      */     //   70: aload 4
/*      */     //   72: aload_3
/*      */     //   73: aload 4
/*      */     //   75: aload_0
/*      */     //   76: getfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   79: invokevirtual 87	com/avaya/jtapi/tsapi/impl/core/TSDevice:pickup	(Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*      */     //   82: astore 5
/*      */     //   84: aload 5
/*      */     //   86: ifnull +30 -> 116
/*      */     //   89: aload 5
/*      */     //   91: iconst_0
/*      */     //   92: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   95: checkcast 24	javax/telephony/TerminalConnection
/*      */     //   98: astore 6
/*      */     //   100: ldc 91
/*      */     //   102: aload_0
/*      */     //   103: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   106: aload 6
/*      */     //   108: astore 7
/*      */     //   110: jsr +54 -> 164
/*      */     //   113: aload 7
/*      */     //   115: areturn
/*      */     //   116: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   119: dup
/*      */     //   120: iconst_4
/*      */     //   121: iconst_0
/*      */     //   122: ldc 88
/*      */     //   124: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   127: athrow
/*      */     //   128: aload_3
/*      */     //   129: ifnonnull +15 -> 144
/*      */     //   132: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   135: dup
/*      */     //   136: iconst_4
/*      */     //   137: iconst_0
/*      */     //   138: ldc 96
/*      */     //   140: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   143: athrow
/*      */     //   144: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   147: dup
/*      */     //   148: iconst_4
/*      */     //   149: iconst_0
/*      */     //   150: ldc 90
/*      */     //   152: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   155: athrow
/*      */     //   156: astore 8
/*      */     //   158: jsr +6 -> 164
/*      */     //   161: aload 8
/*      */     //   163: athrow
/*      */     //   164: astore 9
/*      */     //   166: aload_0
/*      */     //   167: aconst_null
/*      */     //   168: putfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   171: ret 9
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   6	113	156	finally
/*      */     //   116	161	156	finally } 
/*      */   // ERROR //
/*      */   public final TerminalConnection pickup(Address pickAddress, Address terminalAddress) throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException { // Byte code:
/*      */     //   0: ldc 97
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_1
/*      */     //   7: instanceof 81
/*      */     //   10: ifne +15 -> 25
/*      */     //   13: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   16: dup
/*      */     //   17: iconst_3
/*      */     //   18: iconst_0
/*      */     //   19: ldc 98
/*      */     //   21: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   24: athrow
/*      */     //   25: aload_2
/*      */     //   26: instanceof 81
/*      */     //   29: ifne +15 -> 44
/*      */     //   32: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   35: dup
/*      */     //   36: iconst_3
/*      */     //   37: iconst_0
/*      */     //   38: ldc 82
/*      */     //   40: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   43: athrow
/*      */     //   44: aload_1
/*      */     //   45: checkcast 85	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   48: invokevirtual 86	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   51: astore_3
/*      */     //   52: aload_2
/*      */     //   53: checkcast 85	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   56: invokevirtual 86	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   59: astore 4
/*      */     //   61: aload_3
/*      */     //   62: ifnull +68 -> 130
/*      */     //   65: aload 4
/*      */     //   67: ifnull +63 -> 130
/*      */     //   70: aload_0
/*      */     //   71: getfield 7	com/avaya/jtapi/tsapi/impl/TsapiTerminal:tsDevice	Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   74: aload_3
/*      */     //   75: aload 4
/*      */     //   77: aload_0
/*      */     //   78: getfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   81: invokevirtual 99	com/avaya/jtapi/tsapi/impl/core/TSDevice:pickup	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*      */     //   84: astore 5
/*      */     //   86: aload 5
/*      */     //   88: ifnull +30 -> 118
/*      */     //   91: aload 5
/*      */     //   93: iconst_0
/*      */     //   94: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   97: checkcast 24	javax/telephony/TerminalConnection
/*      */     //   100: astore 6
/*      */     //   102: ldc 97
/*      */     //   104: aload_0
/*      */     //   105: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   108: aload 6
/*      */     //   110: astore 7
/*      */     //   112: jsr +54 -> 166
/*      */     //   115: aload 7
/*      */     //   117: areturn
/*      */     //   118: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   121: dup
/*      */     //   122: iconst_4
/*      */     //   123: iconst_0
/*      */     //   124: ldc 88
/*      */     //   126: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   129: athrow
/*      */     //   130: aload_3
/*      */     //   131: ifnonnull +15 -> 146
/*      */     //   134: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   137: dup
/*      */     //   138: iconst_4
/*      */     //   139: iconst_0
/*      */     //   140: ldc 100
/*      */     //   142: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   145: athrow
/*      */     //   146: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   149: dup
/*      */     //   150: iconst_4
/*      */     //   151: iconst_0
/*      */     //   152: ldc 101
/*      */     //   154: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   157: athrow
/*      */     //   158: astore 8
/*      */     //   160: jsr +6 -> 166
/*      */     //   163: aload 8
/*      */     //   165: athrow
/*      */     //   166: astore 9
/*      */     //   168: aload_0
/*      */     //   169: aconst_null
/*      */     //   170: putfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   173: ret 9
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   6	115	158	finally
/*      */     //   118	163	158	finally } 
/*  702 */   public final TerminalConnection pickupFromGroup(String pickupGroup, Address terminalAddress) throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException { TsapiTrace.traceEntry("pickupFromGroup[String pickupGroup, Address terminalAddress]", this);
/*      */     try
/*      */     {
/*  705 */       throw new TsapiMethodNotSupportedException(4, 0, "unsupported by implementation");
/*      */     }
/*      */     finally
/*      */     {
/*  709 */       this.privData = null; }  } 
/*      */   // ERROR //
/*      */   public final TerminalConnection pickupFromGroup(Address terminalAddress) throws TsapiInvalidArgumentException, TsapiInvalidStateException, TsapiMethodNotSupportedException, TsapiPrivilegeViolationException, TsapiResourceUnavailableException { // Byte code:
/*      */     //   0: ldc 106
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_1
/*      */     //   7: instanceof 81
/*      */     //   10: ifne +15 -> 25
/*      */     //   13: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   16: dup
/*      */     //   17: iconst_3
/*      */     //   18: iconst_0
/*      */     //   19: ldc 82
/*      */     //   21: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   24: athrow
/*      */     //   25: aload_1
/*      */     //   26: checkcast 85	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   29: invokevirtual 86	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   32: astore_2
/*      */     //   33: aload_2
/*      */     //   34: ifnull +55 -> 89
/*      */     //   37: aload_2
/*      */     //   38: aload_2
/*      */     //   39: aload_0
/*      */     //   40: getfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   43: invokevirtual 107	com/avaya/jtapi/tsapi/impl/core/TSDevice:groupPickup	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Lcom/avaya/jtapi/tsapi/impl/core/TSConnection;
/*      */     //   46: astore_3
/*      */     //   47: aload_3
/*      */     //   48: ifnull +29 -> 77
/*      */     //   51: aload_3
/*      */     //   52: iconst_0
/*      */     //   53: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   56: checkcast 24	javax/telephony/TerminalConnection
/*      */     //   59: astore 4
/*      */     //   61: ldc 106
/*      */     //   63: aload_0
/*      */     //   64: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   67: aload 4
/*      */     //   69: astore 5
/*      */     //   71: jsr +38 -> 109
/*      */     //   74: aload 5
/*      */     //   76: areturn
/*      */     //   77: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   80: dup
/*      */     //   81: iconst_4
/*      */     //   82: iconst_0
/*      */     //   83: ldc 88
/*      */     //   85: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   88: athrow
/*      */     //   89: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   92: dup
/*      */     //   93: iconst_4
/*      */     //   94: iconst_0
/*      */     //   95: ldc 90
/*      */     //   97: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   100: athrow
/*      */     //   101: astore 6
/*      */     //   103: jsr +6 -> 109
/*      */     //   106: aload 6
/*      */     //   108: athrow
/*      */     //   109: astore 7
/*      */     //   111: aload_0
/*      */     //   112: aconst_null
/*      */     //   113: putfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   116: ret 7
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   6	74	101	finally
/*      */     //   77	106	101	finally } 
/*  755 */   public final void setAgents(Agent[] agents) throws TsapiMethodNotSupportedException { TsapiTrace.traceEntry("setAgents[Agent[] agents]", this);
/*      */     try
/*      */     {
/*  758 */       throw new TsapiMethodNotSupportedException(4, 0, "deprecated method, not supported by implementation");
/*      */     }
/*      */     finally
/*      */     {
/*  762 */       this.privData = null; }  } 
/*      */   // ERROR //
/*      */   public final Agent addAgent(Address agentAddress, ACDAddress acdAddress, int initialState, String agentID, String password) throws TsapiInvalidArgumentException, TsapiInvalidStateException { // Byte code:
/*      */     //   0: ldc 110
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_1
/*      */     //   7: instanceof 81
/*      */     //   10: ifne +15 -> 25
/*      */     //   13: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   16: dup
/*      */     //   17: iconst_3
/*      */     //   18: iconst_0
/*      */     //   19: ldc 111
/*      */     //   21: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   24: athrow
/*      */     //   25: aload_0
/*      */     //   26: getfield 7	com/avaya/jtapi/tsapi/impl/TsapiTerminal:tsDevice	Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   29: aload_1
/*      */     //   30: checkcast 85	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   33: invokevirtual 86	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   36: if_acmpeq +15 -> 51
/*      */     //   39: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   42: dup
/*      */     //   43: iconst_3
/*      */     //   44: iconst_0
/*      */     //   45: ldc 112
/*      */     //   47: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   50: athrow
/*      */     //   51: aconst_null
/*      */     //   52: astore 6
/*      */     //   54: aload_2
/*      */     //   55: ifnull +48 -> 103
/*      */     //   58: aload_2
/*      */     //   59: instanceof 81
/*      */     //   62: ifne +15 -> 77
/*      */     //   65: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   68: dup
/*      */     //   69: iconst_3
/*      */     //   70: iconst_0
/*      */     //   71: ldc 113
/*      */     //   73: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   76: athrow
/*      */     //   77: aload_2
/*      */     //   78: checkcast 85	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   81: invokevirtual 86	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   84: astore 6
/*      */     //   86: aload 6
/*      */     //   88: ifnonnull +35 -> 123
/*      */     //   91: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   94: dup
/*      */     //   95: iconst_4
/*      */     //   96: iconst_0
/*      */     //   97: ldc 90
/*      */     //   99: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   102: athrow
/*      */     //   103: aload 4
/*      */     //   105: ifnull +6 -> 111
/*      */     //   108: goto +15 -> 123
/*      */     //   111: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   114: dup
/*      */     //   115: iconst_3
/*      */     //   116: iconst_0
/*      */     //   117: ldc 114
/*      */     //   119: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   122: athrow
/*      */     //   123: aload_0
/*      */     //   124: getfield 7	com/avaya/jtapi/tsapi/impl/TsapiTerminal:tsDevice	Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   127: aload 6
/*      */     //   129: iload_3
/*      */     //   130: iconst_0
/*      */     //   131: iconst_0
/*      */     //   132: aload 4
/*      */     //   134: aload 5
/*      */     //   136: aload_0
/*      */     //   137: getfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   140: invokevirtual 115	com/avaya/jtapi/tsapi/impl/core/TSDevice:addTSAgent	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;IIILjava/lang/String;Ljava/lang/String;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Lcom/avaya/jtapi/tsapi/impl/core/TSAgent;
/*      */     //   143: astore 7
/*      */     //   145: aload 7
/*      */     //   147: ifnull +30 -> 177
/*      */     //   150: aload 7
/*      */     //   152: iconst_0
/*      */     //   153: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   156: checkcast 116	javax/telephony/callcenter/Agent
/*      */     //   159: astore 8
/*      */     //   161: ldc 110
/*      */     //   163: aload_0
/*      */     //   164: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   167: aload 8
/*      */     //   169: astore 9
/*      */     //   171: jsr +26 -> 197
/*      */     //   174: aload 9
/*      */     //   176: areturn
/*      */     //   177: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   180: dup
/*      */     //   181: iconst_4
/*      */     //   182: iconst_0
/*      */     //   183: ldc 117
/*      */     //   185: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   188: athrow
/*      */     //   189: astore 10
/*      */     //   191: jsr +6 -> 197
/*      */     //   194: aload 10
/*      */     //   196: athrow
/*      */     //   197: astore 11
/*      */     //   199: aload_0
/*      */     //   200: aconst_null
/*      */     //   201: putfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   204: ret 11
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   6	174	189	finally
/*      */     //   177	194	189	finally } 
/*  837 */   public final Agent addAgent(LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, String agentID, String password) throws TsapiInvalidArgumentException, TsapiInvalidStateException { TsapiTrace.traceEntry("addAgent[LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, String agentID, String password]", this);
/*  838 */     if (agentAddress == null) {
/*  839 */       throw new TsapiInvalidArgumentException(3, 0, "agent Address is null");
/*      */     }
/*      */ 
/*  842 */     Agent agent = addAgent(agentAddress, acdAddress, initialState, workMode, 0, agentID, password);
/*      */ 
/*  844 */     TsapiTrace.traceExit("addAgent[LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, String agentID, String password]", this);
/*  845 */     return agent; } 
/*      */   // ERROR //
/*      */   public final Agent addAgent(LucentAddress agentAddress, ACDAddress acdAddress, int initialState, int workMode, int reasonCode, String agentID, String password) throws TsapiInvalidArgumentException, TsapiInvalidStateException { // Byte code:
/*      */     //   0: ldc 121
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_1
/*      */     //   7: ifnonnull +15 -> 22
/*      */     //   10: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   13: dup
/*      */     //   14: iconst_3
/*      */     //   15: iconst_0
/*      */     //   16: ldc 119
/*      */     //   18: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   21: athrow
/*      */     //   22: aload_0
/*      */     //   23: getfield 7	com/avaya/jtapi/tsapi/impl/TsapiTerminal:tsDevice	Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   26: aload_1
/*      */     //   27: checkcast 85	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   30: invokevirtual 86	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   33: if_acmpeq +15 -> 48
/*      */     //   36: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   39: dup
/*      */     //   40: iconst_3
/*      */     //   41: iconst_0
/*      */     //   42: ldc 112
/*      */     //   44: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   47: athrow
/*      */     //   48: aconst_null
/*      */     //   49: astore 8
/*      */     //   51: aload_2
/*      */     //   52: ifnull +48 -> 100
/*      */     //   55: aload_2
/*      */     //   56: instanceof 122
/*      */     //   59: ifne +15 -> 74
/*      */     //   62: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   65: dup
/*      */     //   66: iconst_3
/*      */     //   67: iconst_0
/*      */     //   68: ldc 123
/*      */     //   70: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   73: athrow
/*      */     //   74: aload_2
/*      */     //   75: checkcast 85	com/avaya/jtapi/tsapi/impl/TsapiAddress
/*      */     //   78: invokevirtual 86	com/avaya/jtapi/tsapi/impl/TsapiAddress:getTSDevice	()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   81: astore 8
/*      */     //   83: aload 8
/*      */     //   85: ifnonnull +35 -> 120
/*      */     //   88: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   91: dup
/*      */     //   92: iconst_4
/*      */     //   93: iconst_0
/*      */     //   94: ldc 90
/*      */     //   96: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   99: athrow
/*      */     //   100: aload 6
/*      */     //   102: ifnull +6 -> 108
/*      */     //   105: goto +15 -> 120
/*      */     //   108: new 78	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException
/*      */     //   111: dup
/*      */     //   112: iconst_3
/*      */     //   113: iconst_0
/*      */     //   114: ldc 114
/*      */     //   116: invokespecial 80	com/avaya/jtapi/tsapi/TsapiInvalidArgumentException:<init>	(IILjava/lang/String;)V
/*      */     //   119: athrow
/*      */     //   120: aload_0
/*      */     //   121: getfield 7	com/avaya/jtapi/tsapi/impl/TsapiTerminal:tsDevice	Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   124: aload 8
/*      */     //   126: iload_3
/*      */     //   127: iload 4
/*      */     //   129: iload 5
/*      */     //   131: aload 6
/*      */     //   133: aload 7
/*      */     //   135: aload_0
/*      */     //   136: getfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   139: invokevirtual 115	com/avaya/jtapi/tsapi/impl/core/TSDevice:addTSAgent	(Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;IIILjava/lang/String;Ljava/lang/String;Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;)Lcom/avaya/jtapi/tsapi/impl/core/TSAgent;
/*      */     //   142: astore 9
/*      */     //   144: aload 9
/*      */     //   146: ifnull +30 -> 176
/*      */     //   149: aload 9
/*      */     //   151: iconst_0
/*      */     //   152: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   155: checkcast 116	javax/telephony/callcenter/Agent
/*      */     //   158: astore 10
/*      */     //   160: ldc 121
/*      */     //   162: aload_0
/*      */     //   163: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   166: aload 10
/*      */     //   168: astore 11
/*      */     //   170: jsr +26 -> 196
/*      */     //   173: aload 11
/*      */     //   175: areturn
/*      */     //   176: new 13	com/avaya/jtapi/tsapi/TsapiPlatformException
/*      */     //   179: dup
/*      */     //   180: iconst_4
/*      */     //   181: iconst_0
/*      */     //   182: ldc 117
/*      */     //   184: invokespecial 15	com/avaya/jtapi/tsapi/TsapiPlatformException:<init>	(IILjava/lang/String;)V
/*      */     //   187: athrow
/*      */     //   188: astore 12
/*      */     //   190: jsr +6 -> 196
/*      */     //   193: aload 12
/*      */     //   195: athrow
/*      */     //   196: astore 13
/*      */     //   198: aload_0
/*      */     //   199: aconst_null
/*      */     //   200: putfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   203: ret 13
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   6	173	188	finally
/*      */     //   176	193	188	finally } 
/*  918 */   public final void removeAgent(Agent agent) throws TsapiInvalidStateException, TsapiInvalidArgumentException { TsapiTrace.traceEntry("removeAgent[Agent agent]", this);
/*  919 */     removeAgent(agent, 0);
/*  920 */     TsapiTrace.traceExit("removeAgent[Agent agent]", this); }
/*      */ 
/*      */ 
/*      */   public final void removeAgent(Agent agent, int reasonCode)
/*      */     throws TsapiInvalidStateException, TsapiInvalidArgumentException
/*      */   {
/*  928 */     TsapiTrace.traceEntry("removeAgent[Agent agent, int reasonCode]", this);
/*      */     try
/*      */     {
/*  931 */       if (agent == null)
/*      */       {
/*  933 */         this.tsDevice.removeTSAgent(null, reasonCode);
/*      */       }
/*      */       else
/*      */       {
/*  937 */         if (!(agent instanceof ITsapiAgent)) {
/*  938 */           throw new TsapiInvalidArgumentException(3, 0, "The given Agent is not an instanceof ITsapiAgent");
/*      */         }
/*      */ 
/*  941 */         TSAgent tsAgent = ((TsapiAgent)agent).getTSAgent();
/*  942 */         if (tsAgent != null)
/*      */         {
/*  944 */           this.tsDevice.removeTSAgent(tsAgent, reasonCode);
/*      */         }
/*      */         else
/*      */         {
/*  948 */           throw new TsapiPlatformException(4, 0, "could not locate agent");
/*      */         }
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  954 */       this.privData = null;
/*      */     }
/*  956 */     TsapiTrace.traceExit("removeAgent[Agent agent, int reasonCode]", this); } 
/*      */   // ERROR //
/*      */   public final Agent[] getAgents() { // Byte code:
/*      */     //   0: ldc 133
/*      */     //   2: aload_0
/*      */     //   3: invokestatic 2	com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   6: aload_0
/*      */     //   7: getfield 7	com/avaya/jtapi/tsapi/impl/TsapiTerminal:tsDevice	Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
/*      */     //   10: invokevirtual 134	com/avaya/jtapi/tsapi/impl/core/TSDevice:getTSAgentsForAgentTerm	()Ljava/util/Vector;
/*      */     //   13: astore_1
/*      */     //   14: aload_1
/*      */     //   15: ifnonnull +16 -> 31
/*      */     //   18: ldc 133
/*      */     //   20: aload_0
/*      */     //   21: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   24: aconst_null
/*      */     //   25: astore_2
/*      */     //   26: jsr +109 -> 135
/*      */     //   29: aload_2
/*      */     //   30: areturn
/*      */     //   31: aload_1
/*      */     //   32: dup
/*      */     //   33: astore_2
/*      */     //   34: monitorenter
/*      */     //   35: aload_1
/*      */     //   36: invokevirtual 18	java/util/Vector:size	()I
/*      */     //   39: ifne +18 -> 57
/*      */     //   42: ldc 133
/*      */     //   44: aload_0
/*      */     //   45: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   48: aconst_null
/*      */     //   49: astore_3
/*      */     //   50: aload_2
/*      */     //   51: monitorexit
/*      */     //   52: jsr +83 -> 135
/*      */     //   55: aload_3
/*      */     //   56: areturn
/*      */     //   57: aload_1
/*      */     //   58: invokevirtual 18	java/util/Vector:size	()I
/*      */     //   61: anewarray 116	javax/telephony/callcenter/Agent
/*      */     //   64: astore_3
/*      */     //   65: iconst_0
/*      */     //   66: istore 4
/*      */     //   68: iload 4
/*      */     //   70: aload_1
/*      */     //   71: invokevirtual 18	java/util/Vector:size	()I
/*      */     //   74: if_icmpge +29 -> 103
/*      */     //   77: aload_3
/*      */     //   78: iload 4
/*      */     //   80: aload_1
/*      */     //   81: iload 4
/*      */     //   83: invokevirtual 20	java/util/Vector:elementAt	(I)Ljava/lang/Object;
/*      */     //   86: checkcast 135	com/avaya/jtapi/tsapi/impl/core/TSAgent
/*      */     //   89: iconst_0
/*      */     //   90: invokestatic 11	com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject	(Ljava/lang/Object;Z)Ljava/lang/Object;
/*      */     //   93: checkcast 116	javax/telephony/callcenter/Agent
/*      */     //   96: aastore
/*      */     //   97: iinc 4 1
/*      */     //   100: goto -32 -> 68
/*      */     //   103: ldc 133
/*      */     //   105: aload_0
/*      */     //   106: invokestatic 3	com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   109: aload_3
/*      */     //   110: astore 4
/*      */     //   112: aload_2
/*      */     //   113: monitorexit
/*      */     //   114: jsr +21 -> 135
/*      */     //   117: aload 4
/*      */     //   119: areturn
/*      */     //   120: astore 5
/*      */     //   122: aload_2
/*      */     //   123: monitorexit
/*      */     //   124: aload 5
/*      */     //   126: athrow
/*      */     //   127: astore 6
/*      */     //   129: jsr +6 -> 135
/*      */     //   132: aload 6
/*      */     //   134: athrow
/*      */     //   135: astore 7
/*      */     //   137: aload_0
/*      */     //   138: aconst_null
/*      */     //   139: putfield 5	com/avaya/jtapi/tsapi/impl/TsapiTerminal:privData	Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
/*      */     //   142: ret 7
/*      */     //
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   35	52	120	finally
/*      */     //   57	114	120	finally
/*      */     //   120	124	120	finally
/*      */     //   6	29	127	finally
/*      */     //   31	55	127	finally
/*      */     //   57	117	127	finally
/*      */     //   120	132	127	finally } 
/*  996 */   public final void setPrivateData(Object data) { TsapiTrace.traceEntry("setPrivateData[Object data]", this);
/*      */     try
/*      */     {
/*  999 */       this.privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data);
/*      */     }
/*      */     catch (ClassCastException e)
/*      */     {
/* 1003 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*      */     }
/*      */ 
/* 1006 */     TsapiTrace.traceExit("setPrivateData[Object data]", this); }
/*      */ 
/*      */ 
/*      */   public final Object getPrivateData()
/*      */   {
/* 1011 */     TsapiTrace.traceEntry("getPrivateData[]", this);
/* 1012 */     Object obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate)this.tsDevice.getAddrPrivateData());
/* 1013 */     TsapiTrace.traceExit("getPrivateData[]", this);
/* 1014 */     return obj;
/*      */   }
/*      */ 
/*      */   public final Object sendPrivateData(Object data)
/*      */   {
/* 1019 */     TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
/*      */     try
/*      */     {
/* 1022 */       Object obj = this.tsDevice.sendPrivateData(TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data));
/* 1023 */       TsapiTrace.traceExit("sendPrivateData[Object data]", this);
/* 1024 */       return obj;
/*      */     }
/*      */     catch (ClassCastException e)
/*      */     {
/* 1028 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*      */     }
/*      */   }
/*      */ 
/*      */   public final int hashCode()
/*      */   {
/* 1035 */     return this.tsDevice.hashCode();
/*      */   }
/*      */ 
/*      */   public boolean equals(Object obj)
/*      */   {
/* 1041 */     if (obj instanceof TsapiTerminal)
/*      */     {
/* 1043 */       return this.tsDevice.equals(((TsapiTerminal)obj).tsDevice);
/*      */     }
/*      */ 
/* 1047 */     return false;
/*      */   }
/*      */ 
/*      */   TsapiTerminal(TsapiProvider _provider, String _name, boolean checkValidity)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/* 1075 */     TSProviderImpl tsProv = _provider.getTSProviderImpl();
/* 1076 */     if (tsProv != null)
/*      */     {
/* 1078 */       this.tsDevice = tsProv.createDevice(_name, checkValidity);
/* 1079 */       if ((this.tsDevice == null) || (!this.tsDevice.isTerminal()))
/*      */       {
/* 1081 */         String info = "";
/*      */ 
/* 1083 */         if (this.tsDevice == null)
/*      */         {
/* 1085 */           info = "; device is null";
/*      */         }
/* 1087 */         else if (!this.tsDevice.isTerminal())
/*      */         {
/* 1089 */           info = "; device is not a terminal";
/*      */         }
/* 1091 */         throw new TsapiPlatformException(4, 0, "could not create terminal: " + _name + info);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1096 */       throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */     }
/* 1098 */     this.name = this.tsDevice.referenced();
/* 1099 */     TsapiTrace.traceConstruction(this, TsapiTerminal.class);
/*      */   }
/*      */ 
/*      */   TsapiTerminal(TSProviderImpl _provider, String _name)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/* 1107 */     this.tsDevice = _provider.createDevice(_name, false);
/*      */ 
/* 1109 */     if ((this.tsDevice == null) || (!this.tsDevice.isTerminal()))
/*      */     {
/* 1111 */       String info = "";
/*      */ 
/* 1113 */       if (this.tsDevice == null)
/*      */       {
/* 1115 */         info = "; device is null";
/*      */       }
/* 1117 */       else if (!this.tsDevice.isTerminal())
/*      */       {
/* 1119 */         info = "; device is not a terminal";
/*      */       }
/* 1121 */       throw new TsapiPlatformException(4, 0, "could not create terminal: " + _name + info);
/*      */     }
/*      */ 
/* 1124 */     this.name = this.tsDevice.referenced();
/* 1125 */     TsapiTrace.traceConstruction(this, TsapiTerminal.class);
/*      */   }
/*      */ 
/*      */   TsapiTerminal(TsapiProvider _provider, String _name)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/* 1132 */     this(_provider, _name, false);
/*      */   }
/*      */ 
/*      */   TsapiTerminal(TSDevice _tsDevice)
/*      */   {
/* 1138 */     this.tsDevice = _tsDevice;
/*      */ 
/* 1140 */     this.name = this.tsDevice.referenced();
/* 1141 */     TsapiTrace.traceConstruction(this, TsapiTerminal.class);
/*      */   }
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/* 1147 */     super.finalize();
/* 1148 */     if (this.tsDevice != null)
/*      */     {
/* 1150 */       this.tsDevice.unreferenced();
/* 1151 */       this.tsDevice = null;
/*      */     }
/* 1153 */     TsapiTrace.traceDestruction(this, TsapiTerminal.class);
/*      */   }
/*      */ 
/*      */   public final TSDevice getTSDevice()
/*      */   {
/* 1162 */     TsapiTrace.traceEntry("getTSDevice[]", this);
/* 1163 */     TsapiTrace.traceExit("getTSDevice[]", this);
/* 1164 */     return this.tsDevice;
/*      */   }
/*      */ 
/*      */   public void addTerminalListener(TerminalListener listener)
/*      */     throws ResourceUnavailableException, MethodNotSupportedException
/*      */   {
/* 1172 */     TsapiTrace.traceEntry("addTerminalListener[TerminalListener listener]", this);
/*      */     try
/*      */     {
/* 1175 */       TSProviderImpl prov = null;
/* 1176 */       prov = this.tsDevice.getTSProviderImpl();
/*      */ 
/* 1178 */       if (prov == null)
/*      */       {
/* 1180 */         throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */       }
/*      */ 
/* 1185 */       Vector observers = prov.getTerminalMonitorThreads();
/*      */ 
/* 1187 */       TsapiTerminalMonitor obs = null;
/*      */ 
/* 1189 */       boolean found = false;
/*      */ 
/* 1192 */       synchronized (observers)
/*      */       {
/* 1194 */         for (int i = 0; i < observers.size(); ++i)
/*      */         {
/* 1196 */           obs = (TsapiTerminalMonitor)observers.elementAt(i);
/* 1197 */           if (obs.getListener() != listener)
/*      */             continue;
/* 1199 */           found = true;
/* 1200 */           break;
/*      */         }
/*      */ 
/* 1203 */         if (!found)
/*      */         {
/* 1205 */           obs = new TsapiTerminalMonitor(prov, listener);
/*      */         }
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1211 */         this.tsDevice.addTerminalMonitor(obs);
/*      */       }
/*      */       catch (TsapiResourceUnavailableException e)
/*      */       {
/* 1216 */         if ((!found) && (obs != null))
/*      */         {
/* 1218 */           prov.removeTerminalMonitorThread(obs);
/*      */         }
/* 1220 */         throw e;
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1226 */       this.privData = null;
/*      */     }
/* 1228 */     TsapiTrace.traceExit("addTerminalListener[TerminalListener listener]", this);
/*      */   }
/*      */ 
/*      */   public TerminalListener[] getTerminalListeners()
/*      */   {
/* 1233 */     TsapiTrace.traceEntry("getTerminalListeners[]", this);
/*      */     try
/*      */     {
/* 1236 */       Vector tsapiTerminalObservers = this.tsDevice.getTerminalObservers();
/*      */ 
/* 1238 */       if ((tsapiTerminalObservers == null) || (tsapiTerminalObservers.size() == 0))
/*      */       {
/* 1240 */         TsapiTrace.traceExit("getTerminalListeners[]", this);
/* 1241 */         return null;
/*      */       }
/*      */ 
/* 1244 */       Vector listeners = new Vector();
/*      */ 
/* 1246 */       for (int i = 0; i < tsapiTerminalObservers.size(); ++i)
/*      */       {
/* 1248 */         TsapiTerminalMonitor obs = (TsapiTerminalMonitor)tsapiTerminalObservers.elementAt(i);
/* 1249 */         if (obs.getListener() != null)
/* 1250 */           listeners.add(obs.getListener());
/*      */       }
/* 1252 */       TsapiTrace.traceExit("getTerminalListeners[]", this);
/* 1253 */       if (listeners.size() > 0) {
/* 1254 */         return (TerminalListener[])listeners.toArray(new TerminalListener[1]);
/*      */       }
/*      */ 
/* 1257 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/* 1262 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeTerminalListener(TerminalListener listener)
/*      */   {
/* 1269 */     TsapiTrace.traceEntry("removeTerminalListener[TerminalListener listener]", this);
/*      */     try
/*      */     {
/* 1272 */       Vector tsapiTerminalObservers = this.tsDevice.getTerminalObservers();
/*      */ 
/* 1274 */       if ((tsapiTerminalObservers == null) || (tsapiTerminalObservers.size() == 0))
/*      */       {
/* 1276 */         TsapiTrace.traceExit("removeTerminalListener[TerminalListener listener]", this);
/* 1277 */         return;
/*      */       }
/*      */ 
/* 1280 */       for (int i = 0; i < tsapiTerminalObservers.size(); ++i)
/*      */       {
/* 1282 */         TsapiTerminalMonitor obs = (TsapiTerminalMonitor)tsapiTerminalObservers.elementAt(i);
/* 1283 */         if (obs.getListener() != listener)
/*      */           continue;
/* 1285 */         this.tsDevice.removeTerminalMonitor(obs);
/* 1286 */         TsapiTrace.traceExit("removeTerminalListener[TerminalListener listener]", this);
/* 1287 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1293 */       this.privData = null;
/*      */     }
/* 1295 */     TsapiTrace.traceExit("removeTerminalListener[TerminalListener listener]", this);
/*      */   }
/*      */ 
/*      */   public void addCallListener(CallListener listener) throws ResourceUnavailableException
/*      */   {
/* 1300 */     TsapiTrace.traceEntry("addCallListener(CallListener listener)", this);
/* 1301 */     addTsapiCallEventMonitor(null, listener);
/* 1302 */     TsapiTrace.traceExit("addCallListener(CallListener listener)", this);
/*      */   }
/*      */ 
/*      */   public CallListener[] getCallListeners() {
/* 1306 */     TsapiTrace.traceEntry("getCallListeners[]", this);
/*      */     try {
/* 1308 */       Vector tsapiTerminalCallObservers = this.tsDevice.getTerminalCallObservers();
/*      */ 
/* 1311 */       if ((tsapiTerminalCallObservers == null) || (tsapiTerminalCallObservers.size() == 0))
/*      */       {
/* 1313 */         TsapiTrace.traceExit("getCallListeners[]", this);
/* 1314 */         return null;
/*      */       }
/*      */ 
/* 1317 */       CallListener[] listeners = null;
/* 1318 */       ArrayList callListenerList = new ArrayList();
/*      */ 
/* 1320 */       synchronized (tsapiTerminalCallObservers) {
/* 1321 */         for (Object obs : tsapiTerminalCallObservers) {
/* 1322 */           CallListener listener = ((TsapiCallMonitor) obs).getListener();
/* 1323 */           if (listener != null)
/* 1324 */             callListenerList.add(((TsapiCallMonitor) obs).getListener());
/*      */         }
/*      */       }
/* 1327 */       listeners = new CallListener[callListenerList.size()];
/* 1328 */       TsapiTrace.traceExit("getCallListeners[]", this);
/* 1329 */       return (CallListener[])callListenerList.toArray(listeners);
/*      */     } finally {
/* 1331 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeCallListener(CallListener listener) {
/* 1336 */     TsapiTrace.traceEntry("removeCallListener[CallListener listener]", this);
/*      */     try
/*      */     {
/* 1339 */       Vector tsapiTerminalCallObservers = this.tsDevice.getTerminalCallObservers();
/*      */ 
/* 1342 */       if ((tsapiTerminalCallObservers == null) || (tsapiTerminalCallObservers.size() == 0))
/*      */       {
/* 1344 */         TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
/*      */ 
/* 1346 */         return;
/*      */       }
/*      */ 
/* 1349 */       for (int i = 0; i < tsapiTerminalCallObservers.size(); ++i) {
/* 1350 */         TsapiCallMonitor obs = (TsapiCallMonitor)tsapiTerminalCallObservers.elementAt(i);
/* 1351 */         if (obs.getListener() == listener) {
/* 1352 */           this.tsDevice.removeTerminalCallMonitor(obs);
/* 1353 */           TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
/* 1354 */           return;
/*      */         }
/*      */       }
/*      */     } finally {
/* 1358 */       this.privData = null;
/*      */     }
/* 1360 */     TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiTerminal
 * JD-Core Version:    0.5.4
 */