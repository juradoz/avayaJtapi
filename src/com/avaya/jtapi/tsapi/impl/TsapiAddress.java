/*      */ package com.avaya.jtapi.tsapi.impl;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.ITsapiAddress;
/*      */ import com.avaya.jtapi.tsapi.LucentAddress;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*      */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivate;
/*      */ import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor;
/*      */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Vector;
/*      */ import javax.telephony.AddressListener;
/*      */ import javax.telephony.AddressObserver;
/*      */ import javax.telephony.CallListener;
/*      */ import javax.telephony.CallObserver;
/*      */ import javax.telephony.Connection;
/*      */ import javax.telephony.InvalidArgumentException;
/*      */ import javax.telephony.MethodNotSupportedException;
/*      */ import javax.telephony.PlatformException;
/*      */ import javax.telephony.Provider;
/*      */ import javax.telephony.ResourceUnavailableException;
/*      */ import javax.telephony.Terminal;
/*      */ import javax.telephony.callcenter.RouteCallback;
/*      */ import javax.telephony.callcenter.RouteSession;
/*      */ import javax.telephony.callcontrol.CallControlForwarding;
/*      */ import javax.telephony.capabilities.AddressCapabilities;
/*      */ import javax.telephony.privatedata.PrivateData;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class TsapiAddress
/*      */   implements ITsapiAddress, PrivateData, LucentAddress
/*      */ {
/*   50 */   private static Logger log = Logger.getLogger(TsapiAddress.class);
/*      */   TSDevice tsDevice;
/*   67 */   CSTAPrivate privData = null;
/*      */   String name;
/*      */ 
/*      */   TsapiAddress(TSDevice _tsDevice)
/*      */   {
/*   75 */     this.tsDevice = _tsDevice;
/*      */ 
/*   77 */     this.name = this.tsDevice.referenced();
/*   78 */     TsapiTrace.traceConstruction(this, TsapiAddress.class);
/*      */   }
/*      */ 
/*      */   TsapiAddress(TSProviderImpl TSProviderImpl, String number)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/*   85 */     this.tsDevice = TSProviderImpl.createDevice(number, false);
/*   86 */     if (this.tsDevice == null)
/*      */     {
/*   88 */       throw new TsapiPlatformException(4, 0, "could not create");
/*      */     }
/*   90 */     this.name = this.tsDevice.referenced();
/*   91 */     TsapiTrace.traceConstruction(this, TsapiAddress.class);
/*      */   }
/*      */ 
/*      */   TsapiAddress(TSProviderImpl TSProviderImpl, CSTAExtendedDeviceID deviceID)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/*   97 */     this.tsDevice = TSProviderImpl.createDevice(deviceID, false);
/*   98 */     if (this.tsDevice == null)
/*      */     {
/*  100 */       throw new TsapiPlatformException(4, 0, "could not create");
/*      */     }
/*  102 */     this.name = this.tsDevice.referenced();
/*  103 */     TsapiTrace.traceConstruction(this, TsapiAddress.class);
/*      */   }
/*      */ 
/*      */   public final String getName()
/*      */   {
/*  113 */     TsapiTrace.traceEntry("getName[]", this);
/*      */     try
/*      */     {
/*  116 */       TsapiTrace.traceExit("getName[]", this);
/*  117 */       String str = this.name;
/*      */ 
/*  121 */       return str; } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final String getDirectoryName()
/*      */   {
/*  128 */     TsapiTrace.traceEntry("getDirectoryName[]", this);
/*  129 */     String name = this.tsDevice.getDirectoryName();
/*  130 */     TsapiTrace.traceExit("getDirectoryName[]", this);
/*  131 */     return name;
/*      */   }
/*      */ 
/*      */   public final Provider getProvider()
/*      */   {
/*  140 */     TsapiTrace.traceEntry("getProvider[]", this);
/*      */     try
/*      */     {
/*  143 */       TSProviderImpl TSProviderImpl = this.tsDevice.getTSProviderImpl();
/*      */       Provider localProvider1;
/*  144 */       if (TSProviderImpl != null)
/*      */       {
/*  146 */         Provider provider = (Provider)TsapiCreateObject.getTsapiObject(TSProviderImpl, false);
/*  147 */         TsapiTrace.traceExit("getProvider[]", this);
/*  148 */         localProvider1 = provider;
/*      */ 
/*  157 */         this.privData = null;
/*      */       }
/*  152 */       throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */     }
/*      */     finally
/*      */     {
/*  157 */       this.privData = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Terminal[] getTerminals()
/*      */   {
/*  166 */     TsapiTrace.traceEntry("getTerminals[]", this);
/*      */     try
/*      */     {
/*  169 */       Vector tsTermDevices = this.tsDevice.getTSTerminalDevices();
/*      */       Object localObject1;
/*  171 */       if (tsTermDevices == null)
/*      */       {
/*  173 */         TsapiTrace.traceExit("getTerminals[]", this);
/*  174 */         localObject1 = null;
/*      */ 
/*  196 */         this.privData = null;
/*      */       }
/*  177 */       synchronized (tsTermDevices)
/*      */       {
/*  179 */         if (tsTermDevices.size() == 0)
/*      */         {
/*  181 */           TsapiTrace.traceExit("getTerminals[]", this);
/*      */ 
/*  196 */           this.privData = null; return null;
/*      */         }
/*  185 */         Terminal[] tsapiTerm = new Terminal[tsTermDevices.size()];
/*  186 */         for (int i = 0; i < tsTermDevices.size(); ++i)
/*      */         {
/*  188 */           tsapiTerm[i] = ((Terminal)TsapiCreateObject.getTsapiObject((TSDevice)tsTermDevices.elementAt(i), false));
/*      */         }
/*  190 */         TsapiTrace.traceExit("getTerminals[]", this);
/*      */ 
/*  196 */         this.privData = null; return tsapiTerm;
/*      */       } } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final Connection[] getConnections()
/*      */   {
/*  207 */     TsapiTrace.traceEntry("getConnections[]", this);
/*      */     try
/*      */     {
/*      */       try
/*      */       {
/*  214 */         Vector tsconn = this.tsDevice.getTSConnections();
/*      */         Object localObject1;
/*  216 */         if (tsconn == null)
/*      */         {
/*  218 */           TsapiTrace.traceExit("getConnections[]", this);
/*  219 */           localObject1 = null;
/*      */ 
/*  241 */           this.privData = null;
/*      */ 
/*  247 */           log.info("API CALL END: Address.getConnections() for " + this);
/*      */         }
/*  222 */         synchronized (tsconn)
/*      */         {
/*  224 */           if (tsconn.size() == 0)
/*      */           {
/*  226 */             TsapiTrace.traceExit("getConnections[]", this);
/*      */ 
/*  241 */             this.privData = null;
/*      */ 
/*  247 */             log.info("API CALL END: Address.getConnections() for " + this); return null;
/*      */           }
/*  230 */           Connection[] tsapiConn = new Connection[tsconn.size()];
/*  231 */           for (int i = 0; i < tsconn.size(); ++i)
/*      */           {
/*  233 */             tsapiConn[i] = ((Connection)TsapiCreateObject.getTsapiObject((TSConnection)tsconn.elementAt(i), true));
/*      */           }
/*  235 */           TsapiTrace.traceExit("getConnections[]", this);
/*      */ 
/*  241 */           this.privData = null;
/*      */ 
/*  247 */           log.info("API CALL END: Address.getConnections() for " + this); return tsapiConn;
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/*  241 */         this.privData = null;
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  247 */       log.info("API CALL END: Address.getConnections() for " + this);
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void addObserver(AddressObserver observer)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*  257 */     TsapiTrace.traceEntry("addObserver[AddressObserver observer]", this);
/*      */     try
/*      */     {
/*  260 */       TSProviderImpl prov = null;
/*  261 */       prov = this.tsDevice.getTSProviderImpl();
/*      */ 
/*  263 */       if (prov == null)
/*      */       {
/*  265 */         throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */       }
/*      */ 
/*  270 */       Vector observers = prov.getAddressMonitorThreads();
/*      */ 
/*  272 */       TsapiAddressMonitor obs = null;
/*      */ 
/*  274 */       boolean found = false;
/*      */ 
/*  277 */       synchronized (observers)
/*      */       {
/*  279 */         for (int i = 0; i < observers.size(); ++i)
/*      */         {
/*  281 */           obs = (TsapiAddressMonitor)observers.elementAt(i);
/*  282 */           if (obs.getObserver() != observer)
/*      */             continue;
/*  284 */           found = true;
/*  285 */           break;
/*      */         }
/*      */ 
/*  288 */         if (!found)
/*      */         {
/*  290 */           obs = new TsapiAddressMonitor(prov, observer);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/*  297 */         this.tsDevice.addAddressMonitor(obs);
/*      */       }
/*      */       catch (TsapiResourceUnavailableException e)
/*      */       {
/*  304 */         if ((!found) && (obs != null))
/*      */         {
/*  306 */           prov.removeAddressMonitorThread(obs);
/*      */         }
/*  308 */         throw e;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  313 */       this.privData = null;
/*      */     }
/*  315 */     TsapiTrace.traceExit("addObserver[AddressObserver observer]", this);
/*      */   }
/*      */ 
/*      */   public final AddressObserver[] getObservers()
/*      */   {
/*  324 */     TsapiTrace.traceEntry("getObservers[]", this);
/*      */     try
/*      */     {
/*  327 */       Vector tsapiAddressObservers = this.tsDevice.getAddressObservers();
/*      */ 
/*  329 */       if ((tsapiAddressObservers == null) || (tsapiAddressObservers.size() == 0))
/*      */       {
/*  331 */         TsapiTrace.traceExit("getObservers[]", this);
/*  332 */         Object localObject1 = null;
/*      */         return null;
/*      */       }
/*  335 */       ArrayList observers = new ArrayList();
/*      */       TsapiAddressMonitor obs;
/*  337 */       for (int i = 0; i < tsapiAddressObservers.size(); ++i)
/*      */       {
/*  339 */         obs = (TsapiAddressMonitor)tsapiAddressObservers.elementAt(i);
/*  340 */         AddressObserver addressObserver = obs.getObserver();
/*  341 */         if (addressObserver == null)
/*      */           continue;
/*  343 */         observers.add(addressObserver);
/*      */       }
/*      */ 
/*  346 */       int size = observers.size();
/*  347 */       if (size == 0)
/*      */       {
/*  349 */         TsapiTrace.traceExit("getObservers[]", this);
/*      */         return null;
/*      */       }
/*  352 */       AddressObserver[] arrayOfObservers = new AddressObserver[size];
/*  353 */       observers.toArray(arrayOfObservers);
/*  354 */       TsapiTrace.traceExit("getObservers[]", this);
/*      */ 
/*  359 */       return arrayOfObservers; } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final void removeObserver(AddressObserver observer)
/*      */   {
/*  369 */     TsapiTrace.traceEntry("removeObserver[AddressObserver observer]", this);
/*      */     try
/*      */     {
/*  372 */       Vector tsapiAddressObservers = this.tsDevice.getAddressObservers();
/*      */ 
/*  374 */       if ((tsapiAddressObservers == null) || (tsapiAddressObservers.size() == 0))
/*      */       {
/*  376 */         TsapiTrace.traceExit("removeObserver[AddressObserver observer]", this);
/*      */         return;
/*      */       }
/*      */ 
/*  380 */       for (int i = 0; i < tsapiAddressObservers.size(); ++i)
/*      */       {
/*  382 */         TsapiAddressMonitor obs = (TsapiAddressMonitor)tsapiAddressObservers.elementAt(i);
/*  383 */         if (obs.getObserver() != observer) {
/*      */           continue;
/*      */         }
/*  385 */         this.tsDevice.removeAddressMonitor(obs);
/*  386 */         TsapiTrace.traceExit("removeObserver[AddressObserver observer]", this);
/*      */         return;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  393 */       this.privData = null;
/*      */     }
/*  395 */     TsapiTrace.traceExit("removeObserver[AddressObserver observer]", this);
/*      */   }
/*      */ 
/*      */   public final void addCallObserver(CallObserver observer)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*  401 */     TsapiTrace.traceEntry("addCallObserver[CallObserver observer]", this);
/*      */     try
/*      */     {
/*  404 */       addCallObserver(observer, false);
/*      */     }
/*      */     catch (TsapiMethodNotSupportedException e)
/*      */     {
/*      */     }
/*      */     finally
/*      */     {
/*  413 */       this.privData = null;
/*      */     }
/*  415 */     TsapiTrace.traceExit("addCallObserver[CallObserver observer]", this);
/*      */   }
/*      */ 
/*      */   public final CallObserver[] getCallObservers() {
/*      */     try {
/*  420 */       TsapiTrace.traceEntry("getCallObservers[]", this);
/*  421 */       Vector tsapiAddressCallObservers = this.tsDevice.getAddressCallObservers();
/*      */ 
/*  424 */       if ((tsapiAddressCallObservers == null) || (tsapiAddressCallObservers.size() == 0))
/*      */       {
/*  426 */         TsapiTrace.traceExit("getCallObservers[]", this);
/*      */         return null;
/*      */       }
/*  430 */       ArrayList observerList = new ArrayList();
/*  431 */       CallObserver[] observers = null;
/*      */ 
/*  433 */       for (Object obs : tsapiAddressCallObservers) {
/*  434 */         CallObserver observer = ((TsapiCallMonitor) obs).getObserver();
/*  435 */         if (observer != null)
/*  436 */           observerList.add(observer);
/*      */       }
/*  438 */       observers = new CallObserver[observerList.size()];
/*  439 */       TsapiTrace.traceExit("getCallObservers[]", this);
/*      */ 
/*  442 */       return (CallObserver[])observerList.toArray(observers); } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final void removeCallObserver(CallObserver observer)
/*      */   {
/*  448 */     TsapiTrace.traceEntry("removeCallObserver[CallObserver observer]", this);
/*      */     try
/*      */     {
/*  451 */       Vector tsapiAddressCallObservers = this.tsDevice.getAddressCallObservers();
/*      */ 
/*  453 */       if ((tsapiAddressCallObservers == null) || (tsapiAddressCallObservers.size() == 0))
/*      */       {
/*  455 */         TsapiTrace.traceExit("removeCallObserver[CallObserver observer]", this);
/*      */         return;
/*      */       }
/*      */ 
/*  459 */       for (int i = 0; i < tsapiAddressCallObservers.size(); ++i)
/*      */       {
/*  461 */         TsapiCallMonitor obs = (TsapiCallMonitor)tsapiAddressCallObservers.elementAt(i);
/*  462 */         if (obs.getObserver() != observer) {
/*      */           continue;
/*      */         }
/*  464 */         this.tsDevice.removeAddressCallMonitor(obs);
/*  465 */         TsapiTrace.traceExit("removeCallObserver[CallObserver observer]", this);
/*      */         return;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  472 */       this.privData = null;
/*      */     }
/*  474 */     TsapiTrace.traceExit("removeCallObserver[CallObserver observer]", this);
/*      */   }
/*      */ 
/*      */   public final AddressCapabilities getCapabilities()
/*      */   {
/*  483 */     TsapiTrace.traceEntry("getCapabilities[]", this);
/*      */     try
/*      */     {
/*  487 */       AddressCapabilities caps = this.tsDevice.getTsapiAddressCapabilities();
/*  488 */       TsapiTrace.traceExit("getCapabilities[]", this);
/*  489 */       AddressCapabilities localAddressCapabilities1 = caps;
/*      */ 
/*  493 */       return localAddressCapabilities1; } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final AddressCapabilities getAddressCapabilities(Terminal terminal)
/*      */     throws InvalidArgumentException, PlatformException
/*      */   {
/*  501 */     TsapiTrace.traceEntry("getAddressCapabilities[Terminal terminal]", this);
/*  502 */     AddressCapabilities addressCapabilities = getCapabilities();
/*  503 */     TsapiTrace.traceExit("getAddressCapabilities[Terminal terminal]", this);
/*  504 */     return addressCapabilities;
/*      */   }
/*      */ 
/*      */   public final void setForwarding(CallControlForwarding[] instructions)
/*      */     throws TsapiMethodNotSupportedException, TsapiInvalidArgumentException, TsapiInvalidStateException
/*      */   {
/*  514 */     TsapiTrace.traceEntry("setForwarding[CallControlForwarding[] instructions]", this);
/*      */     try
/*      */     {
/*  517 */       Vector fwdVector = new Vector();
/*  518 */       TsapiCallControlForwarding fwd = null;
/*  519 */       for (int i = 0; i < instructions.length; ++i)
/*      */       {
/*  524 */         if (instructions[i].getFilter() == 4)
/*      */         {
/*  527 */           throw new TsapiInvalidArgumentException(3, 0, "forwarding from a specific address unsupported");
/*      */         }
/*  529 */         if ((instructions[i].getType() == 1) && (instructions[i].getFilter() != 1))
/*      */         {
/*  533 */           throw new TsapiInvalidArgumentException(3, 0, "only unconditional forwarding supported for all calls");
/*      */         }
/*  535 */         if (instructions[i].getFilter() == 1)
/*      */         {
/*  537 */           fwd = new TsapiCallControlForwarding(instructions[i].getDestinationAddress(), instructions[i].getType());
/*      */         }
/*      */         else
/*      */         {
/*  542 */           fwd = new TsapiCallControlForwarding(instructions[i].getDestinationAddress(), instructions[i].getType(), instructions[i].getFilter() == 2);
/*      */         }
/*      */ 
/*  546 */         fwdVector.addElement(fwd);
/*      */       }
/*      */ 
/*  549 */       this.tsDevice.setForwarding(fwdVector, this.privData);
/*      */     }
/*      */     finally
/*      */     {
/*  553 */       this.privData = null;
/*      */     }
/*  555 */     TsapiTrace.traceExit("setForwarding[CallControlForwarding[] instructions]", this);
/*      */   }
/*      */ 
/*      */   public final CallControlForwarding[] getForwarding()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  565 */     TsapiTrace.traceEntry("getForwarding[]", this);
/*      */     try
/*      */     {
/*  568 */       Vector fwdVector = this.tsDevice.getForwarding();
/*      */ 
/*  570 */       if ((fwdVector == null) || (fwdVector.size() == 0))
/*      */       {
/*  572 */         TsapiTrace.traceExit("getForwarding[]", this);
/*      */         return null;
/*      */       }
/*  576 */       TsapiCallControlForwarding[] tsapiInstructions = new TsapiCallControlForwarding[fwdVector.size()];
/*  577 */       CallControlForwarding[] instructions = new CallControlForwarding[fwdVector.size()];
/*  578 */       fwdVector.copyInto(tsapiInstructions);
/*  579 */       for (int i = 0; i < tsapiInstructions.length; ++i)
/*      */       {
/*  581 */         if (tsapiInstructions[i].getFilter() == 1)
/*      */         {
/*  583 */           instructions[i] = new CallControlForwarding(tsapiInstructions[i].getDestinationAddress(), tsapiInstructions[i].getType());
/*      */         }
/*      */         else
/*      */         {
/*  588 */           instructions[i] = new CallControlForwarding(tsapiInstructions[i].getDestinationAddress(), tsapiInstructions[i].getType(), tsapiInstructions[i].getFilter() == 2);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  593 */       TsapiTrace.traceExit("getForwarding[]", this);
/*      */ 
/*  598 */       return instructions; } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final void cancelForwarding()
/*      */     throws TsapiMethodNotSupportedException, TsapiInvalidStateException
/*      */   {
/*  607 */     TsapiTrace.traceEntry("cancelForwarding[]", this);
/*      */     try
/*      */     {
/*  610 */       this.tsDevice.cancelForwarding(this.privData);
/*      */     }
/*      */     finally
/*      */     {
/*  614 */       this.privData = null;
/*      */     }
/*  616 */     TsapiTrace.traceExit("cancelForwarding[]", this);
/*      */   }
/*      */ 
/*      */   public final boolean getDoNotDisturb()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  623 */     TsapiTrace.traceEntry("getDoNotDisturb[]", this);
/*      */     try
/*      */     {
/*  626 */       boolean dont = this.tsDevice.getDoNotDisturb();
/*  627 */       TsapiTrace.traceExit("getDoNotDisturb[]", this);
/*  628 */       boolean bool1 = dont;
/*      */ 
/*  632 */       return bool1; } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final void setDoNotDisturb(boolean enable)
/*      */     throws TsapiMethodNotSupportedException, TsapiInvalidStateException
/*      */   {
/*  644 */     TsapiTrace.traceEntry("setDoNotDisturb[boolean enable]", this);
/*      */     try
/*      */     {
/*  647 */       this.tsDevice.setDoNotDisturb(enable, this.privData);
/*      */     }
/*      */     finally
/*      */     {
/*  651 */       this.privData = null;
/*      */     }
/*  653 */     TsapiTrace.traceExit("setDoNotDisturb[boolean enable]", this);
/*      */   }
/*      */ 
/*      */   public final boolean getMessageWaiting()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  660 */     TsapiTrace.traceEntry("getMessageWaiting[]", this);
/*      */     try
/*      */     {
/*  663 */       boolean msgWaiting = this.tsDevice.getMessageWaitingBits() != 0;
/*  664 */       TsapiTrace.traceExit("getMessageWaiting[]", this);
/*  665 */       boolean bool1 = msgWaiting;
/*      */ 
/*  669 */       return bool1; } finally { this.privData = null; }
/*      */   }
/*      */ 
/*      */   public final int getMessageWaitingBits()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  675 */     TsapiTrace.traceEntry("getMessageWaitingBits[]", this);
/*      */     try
/*      */     {
/*  678 */       int i = this.tsDevice.getMessageWaitingBits();
/*  679 */       TsapiTrace.traceExit("getMessageWaitingBits[]", this);
/*      */ 
/*  684 */       return i; } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final void setMessageWaiting(boolean enable)
/*      */     throws TsapiMethodNotSupportedException, TsapiInvalidStateException
/*      */   {
/*  694 */     TsapiTrace.traceEntry("setMessageWaiting[boolean enable]", this);
/*      */     try
/*      */     {
/*  697 */       this.tsDevice.setMessageWaiting(enable, this.privData);
/*      */     }
/*      */     finally
/*      */     {
/*  701 */       this.privData = null;
/*      */     }
/*  703 */     TsapiTrace.traceExit("setMessageWaiting[boolean enable]", this);
/*      */   }
/*      */ 
/*      */   public final void addCallObserver(CallObserver observer, boolean remain)
/*      */     throws TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  712 */     TsapiTrace.traceEntry("addCallObserver[CallObserver observer, boolean remain]", this);
/*  713 */     addCallEventMonitor(observer, remain, null);
/*  714 */     TsapiTrace.traceExit("addCallObserver[CallObserver observer, boolean remain]", this);
/*      */   }
/*      */ 
/*      */   private void addCallEventMonitor(CallObserver observer, boolean remain, CallListener listener)
/*      */     throws TsapiResourceUnavailableException, TsapiMethodNotSupportedException
/*      */   {
/*  730 */     TsapiTrace.traceEntry("addCallEventMonitor(CallObserver observer, boolean remain,CallListener listener)", this);
/*      */     try
/*      */     {
/*  733 */       TSProviderImpl prov = this.tsDevice.getTSProviderImpl();
/*      */ 
/*  735 */       if (prov == null) {
/*  736 */         throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */       }
/*      */ 
/*  741 */       Vector observers = prov.getCallMonitorThreads();
/*      */ 
/*  743 */       TsapiCallMonitor obs = null;
/*  744 */       TsapiCallMonitor obsToUse = null;
/*      */ 
/*  747 */       synchronized (observers) {
/*  748 */         for (int i = 0; i < observers.size(); ++i) {
/*  749 */           obs = (TsapiCallMonitor)observers.elementAt(i);
/*  750 */           if (observer != null) {
/*  751 */             if (obs.getObserver() != observer) continue;
/*  752 */             obsToUse = obs;
/*      */ 
/*  754 */             break;
/*      */           }
/*  756 */           if ((listener == null) || 
/*  757 */             (obs.getListener() != listener)) continue;
/*  758 */           obsToUse = obs;
/*      */ 
/*  760 */           break;
/*      */         }
/*      */ 
/*  765 */         if (obsToUse == null) {
/*  766 */           if (observer != null) {
/*  767 */             obsToUse = new TsapiCallMonitor(prov, observer);
/*      */           }
/*  774 */           else if (listener != null) {
/*  775 */             obsToUse = new TsapiCallMonitor(prov, listener);
/*      */           }
/*  777 */           if (obsToUse == null) {
/*  778 */             throw new TsapiPlatformException(4, 0, "could not allocate Monitor wrapper");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  787 */         if (this.tsDevice.getDeviceType() == 1)
/*  788 */           obsToUse.setVDN(true);
/*      */         else {
/*  790 */           obsToUse.setVDN(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  812 */       this.tsDevice.addAddressCallMonitor(obsToUse, remain);
/*      */     } finally {
/*  814 */       this.privData = null;
/*      */     }
/*  816 */     TsapiTrace.traceExit("addCallEventMonitor(CallObserver observer, boolean remain,CallListener listener)", this);
/*      */   }
/*      */ 
/*      */   public final void setPrivateData(Object data)
/*      */   {
/*  823 */     TsapiTrace.traceEntry("setPrivateData[Object data]", this);
/*      */     try
/*      */     {
/*  826 */       this.privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data);
/*      */     }
/*      */     catch (ClassCastException e)
/*      */     {
/*  830 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*      */     }
/*      */ 
/*  833 */     TsapiTrace.traceExit("setPrivateData[Object data]", this);
/*      */   }
/*      */ 
/*      */   public final Object getPrivateData()
/*      */   {
/*  838 */     TsapiTrace.traceEntry("getPrivateData[]", this);
/*  839 */     Object obj = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate)this.tsDevice.getAddrPrivateData());
/*  840 */     TsapiTrace.traceExit("getPrivateData[]", this);
/*  841 */     return obj;
/*      */   }
/*      */ 
/*      */   public final Object sendPrivateData(Object data)
/*      */   {
/*  846 */     TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
/*      */     try
/*      */     {
/*  849 */       Object obj = this.tsDevice.sendPrivateData(TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data));
/*  850 */       TsapiTrace.traceExit("sendPrivateData[Object data]", this);
/*  851 */       return obj;
/*      */     }
/*      */     catch (ClassCastException e)
/*      */     {
/*  855 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void registerRouteCallback(RouteCallback routeCallback)
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  866 */     TsapiTrace.traceEntry("registerRouteCallback[RouteCallback routeCallback]", this);
/*      */     try
/*      */     {
/*  869 */       TSProviderImpl prov = null;
/*  870 */       prov = this.tsDevice.getTSProviderImpl();
/*      */ 
/*  872 */       if (prov == null)
/*      */       {
/*  874 */         throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */       }
/*      */ 
/*  879 */       Vector observers = prov.getRouteMonitorThreads();
/*      */ 
/*  881 */       TsapiRouteMonitor obs = null;
/*      */ 
/*  883 */       synchronized (observers)
/*      */       {
/*  885 */         for (int i = 0; i < observers.size(); ++i)
/*      */         {
/*  887 */           obs = (TsapiRouteMonitor)observers.elementAt(i);
/*  888 */           if (obs.getObserver() != routeCallback)
/*      */             continue;
/*  890 */           this.tsDevice.addRouteMonitor(obs);
/*  891 */           TsapiTrace.traceExit("registerRouteCallback[RouteCallback routeCallback]", this);
/*      */ 
/*  903 */           this.privData = null; return;
/*      */         }
/*  896 */         obs = new TsapiRouteMonitor(prov, routeCallback);
/*      */       }
/*      */ 
/*  899 */       this.tsDevice.addRouteMonitor(obs);
/*      */     }
/*      */     finally
/*      */     {
/*  903 */       this.privData = null;
/*      */     }
/*  905 */     TsapiTrace.traceExit("registerRouteCallback[RouteCallback routeCallback]", this);
/*      */   }
/*      */ 
/*      */   public final void cancelRouteCallback(RouteCallback routeCallback)
/*      */   {
/*  912 */     TsapiTrace.traceEntry("cancelRouteCallback[RouteCallback routeCallback]", this);
/*      */     try
/*      */     {
/*  915 */       Vector tsapiRouteObservers = this.tsDevice.getRouteObservers();
/*      */ 
/*  917 */       if ((tsapiRouteObservers == null) || (tsapiRouteObservers.size() == 0))
/*      */       {
/*  919 */         TsapiTrace.traceExit("cancelRouteCallback[RouteCallback routeCallback]", this);
/*      */         return;
/*      */       }
/*      */ 
/*  923 */       synchronized (tsapiRouteObservers)
/*      */       {
/*  925 */         for (int i = 0; i < tsapiRouteObservers.size(); ++i)
/*      */         {
/*  927 */           TsapiRouteMonitor obs = (TsapiRouteMonitor)tsapiRouteObservers.elementAt(i);
/*  928 */           if (obs.getObserver() != routeCallback)
/*      */             continue;
/*  930 */           this.tsDevice.removeRouteMonitor(obs);
/*  931 */           TsapiTrace.traceExit("cancelRouteCallback[RouteCallback routeCallback]", this);
/*      */ 
/*  939 */           this.privData = null; return;
/*      */         }
/*      */       } } finally { this.privData = null; }
/*      */ 
/*  941 */     TsapiTrace.traceExit("cancelRouteCallback[RouteCallback routeCallback]", this);
/*      */   }
/*      */ 
/*      */   public final RouteCallback[] getRouteCallback()
/*      */   {
/*  948 */     TsapiTrace.traceEntry("getRouteCallback[]", this);
/*      */     try
/*      */     {
/*  951 */       Vector tsapiRouteObservers = this.tsDevice.getRouteObservers();
/*      */       Object localObject1;
/*  953 */       if ((tsapiRouteObservers == null) || (tsapiRouteObservers.size() == 0))
/*      */       {
/*  955 */         TsapiTrace.traceExit("getRouteCallback[]", this);
/*  956 */         localObject1 = null;
/*      */ 
/*  974 */         this.privData = null;
/*      */       }
/*  959 */       synchronized (tsapiRouteObservers)
/*      */       {
/*  961 */         RouteCallback[] observers = new RouteCallback[tsapiRouteObservers.size()];
/*      */ 
/*  963 */         for (int i = 0; i < tsapiRouteObservers.size(); ++i)
/*      */         {
/*  965 */           TsapiRouteMonitor obs = (TsapiRouteMonitor)tsapiRouteObservers.elementAt(i);
/*  966 */           observers[i] = obs.getObserver();
/*      */         }
/*  968 */         TsapiTrace.traceExit("getRouteCallback[]", this);
/*      */ 
/*  974 */         this.privData = null; return observers;
/*      */       } } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final RouteSession[] getActiveRouteSessions()
/*      */   {
/*  983 */     TsapiTrace.traceEntry("getActiveRouteSessions[]", this);
/*      */     try
/*      */     {
/*  986 */       Vector tsSession = this.tsDevice.getTSRouteSessions();
/*      */       Object localObject1;
/*  988 */       if (tsSession == null)
/*      */       {
/*  990 */         TsapiTrace.traceExit("getActiveRouteSessions[]", this);
/*  991 */         localObject1 = null;
/*      */ 
/* 1013 */         this.privData = null;
/*      */       }
/*  994 */       synchronized (tsSession)
/*      */       {
/*  996 */         if (tsSession.size() == 0)
/*      */         {
/*  998 */           TsapiTrace.traceExit("getActiveRouteSessions[]", this);
/*      */ 
/* 1013 */           this.privData = null; return null;
/*      */         }
/* 1002 */         RouteSession[] tsapiSessions = new RouteSession[tsSession.size()];
/* 1003 */         for (int i = 0; i < tsSession.size(); ++i)
/*      */         {
/* 1005 */           tsapiSessions[i] = ((RouteSession)TsapiCreateObject.getTsapiObject((TSRouteSession)tsSession.elementAt(i), false));
/*      */         }
/* 1007 */         TsapiTrace.traceExit("getActiveRouteSessions[]", this);
/*      */ 
/* 1013 */         this.privData = null; return tsapiSessions;
/*      */       } } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public final int hashCode()
/*      */   {
/* 1019 */     return this.tsDevice.hashCode();
/*      */   }
/*      */ 
/*      */   public boolean equals(Object obj)
/*      */   {
/* 1025 */     if (obj instanceof TsapiAddress)
/*      */     {
/* 1027 */       return this.tsDevice.equals(((TsapiAddress)obj).tsDevice);
/*      */     }
/*      */ 
/* 1031 */     return false;
/*      */   }
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/* 1039 */     super.finalize();
/* 1040 */     if (this.tsDevice != null)
/*      */     {
/* 1042 */       this.tsDevice.unreferenced();
/* 1043 */       this.tsDevice = null;
/*      */     }
/* 1045 */     TsapiTrace.traceDestruction(this, TsapiAddress.class);
/*      */   }
/*      */ 
/*      */   public final TSDevice getTSDevice()
/*      */   {
/* 1054 */     TsapiTrace.traceEntry("getTSDevice[]", this);
/* 1055 */     TsapiTrace.traceExit("getTSDevice[]", this);
/* 1056 */     return this.tsDevice;
/*      */   }
/*      */ 
/*      */   public void addAddressListener(AddressListener listener)
/*      */     throws ResourceUnavailableException
/*      */   {
/* 1065 */     TsapiTrace.traceEntry("addAddressListener[AddressListener listener]", this);
/*      */     try
/*      */     {
/* 1068 */       TSProviderImpl prov = null;
/* 1069 */       prov = this.tsDevice.getTSProviderImpl();
/*      */ 
/* 1071 */       if (prov == null)
/*      */       {
/* 1073 */         throw new TsapiPlatformException(4, 0, "could not locate provider");
/*      */       }
/*      */ 
/* 1078 */       Vector observers = prov.getAddressMonitorThreads();
/*      */ 
/* 1080 */       TsapiAddressMonitor obs = null;
/*      */ 
/* 1082 */       boolean found = false;
/*      */ 
/* 1085 */       synchronized (observers)
/*      */       {
/* 1087 */         for (int i = 0; i < observers.size(); ++i)
/*      */         {
/* 1089 */           obs = (TsapiAddressMonitor)observers.elementAt(i);
/* 1090 */           if (obs.getAddressListener() != listener)
/*      */             continue;
/* 1092 */           found = true;
/* 1093 */           break;
/*      */         }
/*      */ 
/* 1096 */         if (!found)
/*      */         {
/* 1098 */           obs = new TsapiAddressMonitor(prov, listener);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1105 */         this.tsDevice.addAddressMonitor(obs);
/*      */       }
/*      */       catch (TsapiResourceUnavailableException e)
/*      */       {
/* 1112 */         if ((!found) && (obs != null))
/*      */         {
/* 1114 */           prov.removeAddressMonitorThread(obs);
/*      */         }
/* 1116 */         throw e;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 1121 */       this.privData = null;
/*      */     }
/* 1123 */     TsapiTrace.traceExit("addAddressListener[AddressListener listener]", this);
/*      */   }
/*      */ 
/*      */   public AddressListener[] getAddressListeners()
/*      */   {
/* 1132 */     TsapiTrace.traceEntry("getAddressListeners[]", this);
/*      */     try
/*      */     {
/* 1135 */       Vector tsapiAddressObservers = this.tsDevice.getAddressObservers();
/*      */ 
/* 1137 */       if ((tsapiAddressObservers == null) || (tsapiAddressObservers.size() == 0))
/*      */       {
/* 1139 */         TsapiTrace.traceExit("getAddressListeners[]", this);
/*      */         return null;
/*      */       }
/* 1143 */       ArrayList listeners = new ArrayList();
/*      */       TsapiAddressMonitor obs;
/* 1145 */       for (int i = 0; i < tsapiAddressObservers.size(); ++i)
/*      */       {
/* 1147 */         obs = (TsapiAddressMonitor)tsapiAddressObservers.elementAt(i);
/* 1148 */         AddressListener addressListener = obs.getAddressListener();
/* 1149 */         if (addressListener == null)
/*      */           continue;
/* 1151 */         listeners.add(addressListener);
/*      */       }
/*      */ 
/* 1154 */       int size = listeners.size();
/* 1155 */       if (size == 0)
/*      */       {
/* 1157 */         TsapiTrace.traceExit("getAddressListeners[]", this);
/* 1158 */         obs = null;
/*      */         return null;
/*      */       }
/* 1160 */       AddressListener[] arrayOfListeners = new AddressListener[size];
/* 1161 */       listeners.toArray(arrayOfListeners);
/* 1162 */       TsapiTrace.traceExit("getAddressListeners[]", this);
/*      */ 
/* 1167 */       return arrayOfListeners; } finally { this.privData = null; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public void removeAddressListener(AddressListener listener)
/*      */   {
/* 1176 */     TsapiTrace.traceEntry("removeAddressListener[AddressListener listener]", this);
/*      */     try
/*      */     {
/* 1179 */       Vector tsapiAddressObservers = this.tsDevice.getAddressObservers();
/*      */ 
/* 1181 */       if ((tsapiAddressObservers == null) || (tsapiAddressObservers.size() == 0))
/*      */       {
/* 1183 */         TsapiTrace.traceExit("removeAddressListener[AddressListener listener]", this);
/*      */         return;
/*      */       }
/*      */ 
/* 1187 */       for (int i = 0; i < tsapiAddressObservers.size(); ++i)
/*      */       {
/* 1189 */         TsapiAddressMonitor obs = (TsapiAddressMonitor)tsapiAddressObservers.elementAt(i);
/* 1190 */         if (obs.getAddressListener() != listener) {
/*      */           continue;
/*      */         }
/* 1192 */         this.tsDevice.removeAddressMonitor(obs);
/* 1193 */         TsapiTrace.traceExit("removeAddressListener[AddressListener listener]", this);
/*      */         return;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 1200 */       this.privData = null;
/*      */     }
/* 1202 */     TsapiTrace.traceExit("removeAddressListener[AddressListener listener]", this);
/*      */   }
/*      */ 
/*      */   public void addCallListener(CallListener listener) throws ResourceUnavailableException, MethodNotSupportedException
/*      */   {
/* 1207 */     TsapiTrace.traceEntry("addCallListener[CallListener listener]", this);
/* 1208 */     addCallEventMonitor(null, false, listener);
/* 1209 */     TsapiTrace.traceExit("addCallListener[CallListener listener]", this);
/*      */   }
/*      */ 
/*      */   public CallListener[] getCallListeners()
/*      */   {
/* 1214 */     TsapiTrace.traceEntry("getCallListeners[]", this);
/*      */     try {
/* 1216 */       Vector tsapiAddressCallObservers = this.tsDevice.getAddressCallObservers();
/*      */ 
/* 1219 */       if ((tsapiAddressCallObservers == null) || (tsapiAddressCallObservers.size() == 0))
/*      */       {
/* 1221 */         TsapiTrace.traceExit("getCallListeners[]", this);
/*      */         return null;
/*      */       }
/* 1225 */       ArrayList listenerList = new ArrayList();
/* 1226 */       CallListener[] listeners = null;
/*      */ 
/* 1228 */       synchronized (tsapiAddressCallObservers) {
/* 1229 */         for (Object obs : tsapiAddressCallObservers) {
/* 1230 */           CallListener listener = ((TsapiCallMonitor) obs).getListener();
/* 1231 */           if (listener != null)
/* 1232 */             listenerList.add(listener);
/*      */         }
/*      */       }
/* 1235 */       listeners = new CallListener[listenerList.size()];
/* 1236 */       TsapiTrace.traceExit("getCallListeners[]", this);
/*      */ 
/* 1239 */       return (CallListener[])listenerList.toArray(listeners); } finally { this.privData = null; }
/*      */   }
/*      */ 
/*      */   public void removeCallListener(CallListener listener)
/*      */   {
/* 1244 */     TsapiTrace.traceEntry("removeCallListener[CallListener listener]", this);
/*      */     try
/*      */     {
/* 1247 */       Vector tsapiAddressCallObservers = this.tsDevice.getAddressCallObservers();
/*      */ 
/* 1250 */       if ((tsapiAddressCallObservers == null) || (tsapiAddressCallObservers.size() == 0))
/*      */       {
/* 1252 */         TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
/*      */         return;
/*      */       }
/*      */ 
/* 1257 */       for (Object obs : tsapiAddressCallObservers)
/* 1258 */         if (((TsapiCallMonitor) obs).getListener() == listener) {
/* 1259 */           this.tsDevice.removeAddressCallMonitor((TsapiCallMonitor) obs);
/* 1260 */           TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
/*      */           return;
/*      */         }
/*      */     }
/*      */     finally
/*      */     {
/* 1266 */       this.privData = null;
/*      */     }
/* 1268 */     TsapiTrace.traceExit("removeCallListener[CallListener listener]", this);
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiAddress
 * JD-Core Version:    0.5.4
 */