/*      */ package com.avaya.jtapi.tsapi.impl;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.CallClassifierInfo;
/*      */ import com.avaya.jtapi.tsapi.ConnectionID;
/*      */ import com.avaya.jtapi.tsapi.ExtendedDeviceID;
/*      */ import com.avaya.jtapi.tsapi.ITsapiAddress;
/*      */ import com.avaya.jtapi.tsapi.ITsapiProviderEx;
/*      */ import com.avaya.jtapi.tsapi.ITsapiProviderPrivate;
/*      */ import com.avaya.jtapi.tsapi.ITsapiTerminal;
/*      */ import com.avaya.jtapi.tsapi.LucentV7Provider;
/*      */ import com.avaya.jtapi.tsapi.TrunkGroupInfo;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*      */ import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
/*      */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPlatformException;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivate;
/*      */ import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
/*      */ import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentTrunkGroupInfo;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*      */ import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
/*      */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Vector;
/*      */ import javax.telephony.Address;
/*      */ import javax.telephony.Call;
/*      */ import javax.telephony.Connection;
/*      */ import javax.telephony.InvalidArgumentException;
/*      */ import javax.telephony.PlatformException;
/*      */ import javax.telephony.ProviderListener;
/*      */ import javax.telephony.ProviderObserver;
/*      */ import javax.telephony.ResourceUnavailableException;
/*      */ import javax.telephony.Terminal;
/*      */ import javax.telephony.TerminalConnection;
/*      */ import javax.telephony.callcenter.ACDAddress;
/*      */ import javax.telephony.callcenter.ACDManagerAddress;
/*      */ import javax.telephony.callcenter.RouteAddress;
/*      */ import javax.telephony.capabilities.AddressCapabilities;
/*      */ import javax.telephony.capabilities.CallCapabilities;
/*      */ import javax.telephony.capabilities.ConnectionCapabilities;
/*      */ import javax.telephony.capabilities.ProviderCapabilities;
/*      */ import javax.telephony.capabilities.TerminalCapabilities;
/*      */ import javax.telephony.capabilities.TerminalConnectionCapabilities;
/*      */ import javax.telephony.privatedata.PrivateData;
/*      */ 
/*      */ class TsapiProvider
/*      */   implements ITsapiProviderEx, PrivateData, ITsapiProviderPrivate, LucentV7Provider
/*      */ {
/*      */   TSProviderImpl tsProvider;
/*      */   private String name;
/*      */ 
/*      */   public TsapiProvider(String url, Vector<TsapiVendor> vendors)
/*      */   {
/*   79 */     TsapiTrace.traceEntry("TsapiProvider[String url, Vector<TsapiVendor> vendors]", this);
/*   80 */     this.name = url;
/*      */ 
/*   82 */     this.tsProvider = new TSProviderImpl(url, vendors);
/*   83 */     if (this.tsProvider == null)
/*      */     {
/*   85 */       throw new TsapiPlatformException(4, 0, "could not create provider");
/*      */     }
/*   87 */     TsapiTrace.traceExit("TsapiProvider[String url, Vector<TsapiVendor> vendors]", this);
/*      */   }
/*      */ 
/*      */   public final int getState()
/*      */   {
/*   95 */     TsapiTrace.traceEntry("getState[]", this);
/*   96 */     int state = this.tsProvider.getState();
/*   97 */     TsapiTrace.traceExit("getState[]", this);
/*   98 */     return state;
/*      */   }
/*      */ 
/*      */   public int getCurrentStateOfCallByForceQueryOnTelephonyServer(int callId)
/*      */   {
/*  104 */     TsapiTrace.traceEntry("getCurrentStateOfCall[]", this);
/*      */     try
/*      */     {
/*  107 */       int i = this.tsProvider.getCurrentStateOfCallFromTelephonyServer(callId);
/*  108 */       TsapiTrace.traceExit("getCurrentStateOfCall[]", this);
/*  109 */       return i;
/*      */     }
/*      */     catch (Exception ex) {
/*  112 */       throw new TsapiPlatformException(4, 0, "Could not get status of the call. [" + ex + "]");
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getCurrentStateOfCallByForceQueryOnTelephonyServer(Call tsapiCall) {
/*  117 */     TsapiTrace.traceEntry("getCurrentStateOfCall[]", this);
/*  118 */     int i = this.tsProvider.getCurrentStateOfCallFromTelephonyServer(((TsapiCall)tsapiCall).getTSCall());
/*  119 */     TsapiTrace.traceExit("getCurrentStateOfCall[]", this);
/*  120 */     return i;
/*      */   }
/*      */ 
/*      */   public final int getTsapiState()
/*      */   {
/*  129 */     TsapiTrace.traceEntry("getTsapiState[]", this);
/*  130 */     int state = this.tsProvider.getTsapiState();
/*  131 */     TsapiTrace.traceExit("getTsapiState[]", this);
/*  132 */     return state;
/*      */   }
/*      */ 
/*      */   public final String getName()
/*      */   {
/*  139 */     TsapiTrace.traceEntry("getName[]", this);
/*  140 */     TsapiTrace.traceExit("getName[]", this);
/*  141 */     return this.name;
/*      */   }
/*      */ 
/*      */   public final Call[] getCalls()
/*      */   {
/*  149 */     TsapiTrace.traceEntry("getCalls[]", this);
/*  150 */     Vector tsCall = this.tsProvider.getTSCalls();
/*      */ 
/*  152 */     if (tsCall == null)
/*      */     {
/*  154 */       TsapiTrace.traceExit("getCalls[]", this);
/*  155 */       return null;
/*      */     }
/*      */ 
/*  158 */     synchronized (tsCall)
/*      */     {
/*  160 */       if (tsCall.size() == 0)
/*      */       {
/*  162 */         TsapiTrace.traceExit("getCalls[]", this);
/*  163 */         return null;
/*      */       }
/*      */ 
/*  166 */       Call[] tsapiCall = new Call[tsCall.size()];
/*  167 */       for (int i = 0; i < tsCall.size(); ++i)
/*      */       {
/*  169 */         tsapiCall[i] = ((Call)TsapiCreateObject.getTsapiObject((TSCall)tsCall.elementAt(i), false));
/*      */       }
/*  171 */       TsapiTrace.traceExit("getCalls[]", this);
/*  172 */       return tsapiCall;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Address[] getAddresses()
/*      */   {
/*  182 */     TsapiTrace.traceEntry("getAddresses[]", this);
/*  183 */     Vector tsDevice = this.tsProvider.getTSAddressDevices();
/*      */ 
/*  185 */     if (tsDevice == null)
/*      */     {
/*  187 */       TsapiTrace.traceExit("getAddresses[]", this);
/*  188 */       return null;
/*      */     }
/*      */ 
/*  191 */     synchronized (tsDevice)
/*      */     {
/*  193 */       if (tsDevice.size() == 0)
/*      */       {
/*  195 */         TsapiTrace.traceExit("getAddresses[]", this);
/*  196 */         return null;
/*      */       }
/*      */ 
/*  199 */       Address[] tsapiAddress = new Address[tsDevice.size()];
/*  200 */       for (int i = 0; i < tsDevice.size(); ++i)
/*      */       {
/*  202 */         tsapiAddress[i] = ((Address)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), true));
/*      */       }
/*  204 */       TsapiTrace.traceExit("getAddresses[]", this);
/*  205 */       return tsapiAddress;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Terminal[] getTerminals()
/*      */   {
/*  215 */     TsapiTrace.traceEntry("getTerminals[]", this);
/*  216 */     Vector tsDevice = this.tsProvider.getTSTerminalDevices();
/*      */ 
/*  218 */     if (tsDevice == null)
/*      */     {
/*  220 */       TsapiTrace.traceExit("getTerminals[]", this);
/*  221 */       return null;
/*      */     }
/*      */ 
/*  224 */     synchronized (tsDevice)
/*      */     {
/*  226 */       if (tsDevice.size() == 0)
/*      */       {
/*  228 */         TsapiTrace.traceExit("getTerminals[]", this);
/*  229 */         return null;
/*      */       }
/*      */ 
/*  232 */       Terminal[] tsapiTerminal = new Terminal[tsDevice.size()];
/*  233 */       for (int i = 0; i < tsDevice.size(); ++i)
/*      */       {
/*  235 */         tsapiTerminal[i] = ((Terminal)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), false));
/*      */       }
/*  237 */       TsapiTrace.traceExit("getTerminals[]", this);
/*  238 */       return tsapiTerminal;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Call createCall()
/*      */     throws TsapiResourceUnavailableException, TsapiInvalidStateException, TsapiPrivilegeViolationException, TsapiMethodNotSupportedException
/*      */   {
/*  249 */     TsapiTrace.traceEntry("createCall[]", this);
/*  250 */     Call call = null;
/*  251 */     if (this.tsProvider.isLucentV7())
/*      */     {
/*  253 */       call = new LucentV7CallImpl((LucentV7ProviderImpl)this);
/*      */     }
/*  255 */     else if (this.tsProvider.isLucentV5())
/*      */     {
/*  257 */       call = new LucentV5CallImpl((LucentV5ProviderImpl)this);
/*      */     }
/*  259 */     else if (this.tsProvider.isLucent())
/*      */     {
/*  261 */       call = new LucentCallEx2Impl((LucentProviderImpl)this);
/*      */     }
/*      */     else
/*      */     {
/*  265 */       call = new TsapiCall(this);
/*      */     }
/*  267 */     TsapiTrace.traceExit("createCall[]", this);
/*  268 */     return call;
/*      */   }
/*      */ 
/*      */   public final Address getAddress(String number)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/*  278 */     TsapiTrace.traceEntry("getAddress[String number]", this);
/*  279 */     TSDevice tsDevice = this.tsProvider.createDevice(number, true);
/*  280 */     if (tsDevice != null)
/*      */     {
/*  282 */       Address addr = (Address)TsapiCreateObject.getTsapiObject(tsDevice, true);
/*  283 */       TsapiTrace.traceExit("getAddress[String number]", this);
/*  284 */       return addr;
/*      */     }
/*      */ 
/*  288 */     throw new TsapiPlatformException(4, 0, "could not create address");
/*      */   }
/*      */ 
/*      */   public final Terminal getTerminal(String name)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/*  299 */     TsapiTrace.traceEntry("getTerminal[String name]", this);
/*  300 */     TSDevice tsDevice = this.tsProvider.createDevice(name, true);
/*      */ 
/*  303 */     if ((tsDevice != null) && (tsDevice.isTerminal()))
/*      */     {
/*  305 */       Terminal term = (Terminal)TsapiCreateObject.getTsapiObject(tsDevice, false);
/*  306 */       TsapiTrace.traceExit("getTerminal[String name]", this);
/*  307 */       return term;
/*      */     }
/*      */ 
/*  311 */     String info = "";
/*  312 */     if (tsDevice == null)
/*      */     {
/*  314 */       info = "; device is null";
/*      */     }
/*  316 */     else if (!tsDevice.isTerminal())
/*      */     {
/*  318 */       info = "; device is not a terminal";
/*      */     }
/*  320 */     throw new TsapiPlatformException(4, 0, "could not create terminal: " + name + info);
/*      */   }
/*      */ 
/*      */   public final void shutdown()
/*      */   {
/*  330 */     TsapiTrace.traceEntry("shutdown[]", this);
/*  331 */     this.tsProvider.shutdown();
/*  332 */     TsapiTrace.traceExit("shutdown[]", this);
/*      */   }
/*      */ 
/*      */   public void addObserver(ProviderObserver observer)
/*      */     throws TsapiResourceUnavailableException
/*      */   {
/*  341 */     TsapiTrace.traceEntry("addObserver[ProviderObserver observer]", this);
/*      */ 
/*  343 */     Vector monitors = this.tsProvider.getProviderMonitorThreads();
/*      */ 
/*  345 */     TsapiProviderMonitor monitor = null;
/*  346 */     synchronized (monitors)
/*      */     {
/*  348 */       for (int i = 0; i < monitors.size(); ++i)
/*      */       {
/*  350 */         monitor = (TsapiProviderMonitor)monitors.elementAt(i);
/*  351 */         if (monitor.getMonitor() != observer)
/*      */           continue;
/*  353 */         this.tsProvider.addMonitor(monitor);
/*  354 */         TsapiTrace.traceExit("addObserver[ProviderObserver observer]", this);
/*  355 */         return;
/*      */       }
/*      */ 
/*  358 */       monitor = new TsapiProviderMonitor(this.tsProvider, observer);
/*      */     }
/*      */ 
/*  361 */     this.tsProvider.addMonitor(monitor);
/*  362 */     TsapiTrace.traceExit("addObserver[ProviderObserver observer]", this);
/*      */   }
/*      */ 
/*      */   public ProviderObserver[] getObservers()
/*      */   {
/*  370 */     TsapiTrace.traceEntry("getObservers[]", this);
/*  371 */     Vector monitors = this.tsProvider.getProviderMonitorThreads();
/*      */ 
/*  373 */     if ((monitors == null) || (monitors.size() == 0))
/*      */     {
/*  375 */       TsapiTrace.traceExit("getObservers[]", this);
/*  376 */       return null;
/*      */     }
/*      */ 
/*  379 */     synchronized (monitors)
/*      */     {
/*  381 */       List observers = new ArrayList();
/*      */ 
/*  383 */       for (int i = 0; i < monitors.size(); ++i)
/*      */       {
/*  385 */         TsapiProviderMonitor monitor = (TsapiProviderMonitor)monitors.elementAt(i);
/*  386 */         if (monitor.getMonitor() instanceof ProviderObserver) {
/*  387 */           observers.add((ProviderObserver)monitor.getMonitor());
/*      */         }
/*      */       }
/*  390 */       TsapiTrace.traceExit("getObservers[]", this);
/*  391 */       return (ProviderObserver[])observers.toArray(new ProviderObserver[0]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeObserver(ProviderObserver observer)
/*      */   {
/*  400 */     TsapiTrace.traceEntry("removeObserver[ProviderObserver observer]", this);
/*  401 */     Vector monitors = this.tsProvider.getProviderMonitorThreads();
/*      */ 
/*  403 */     if ((monitors == null) || (monitors.size() == 0))
/*      */     {
/*  405 */       TsapiTrace.traceExit("removeObserver[ProviderObserver observer]", this);
/*  406 */       return;
/*      */     }
/*      */ 
/*  409 */     synchronized (monitors)
/*      */     {
/*  411 */       for (int i = 0; i < monitors.size(); ++i)
/*      */       {
/*  413 */         TsapiProviderMonitor monitor = (TsapiProviderMonitor)monitors.elementAt(i);
/*  414 */         if (monitor.getMonitor() != observer)
/*      */           continue;
/*  416 */         this.tsProvider.removeMonitor(monitor);
/*  417 */         TsapiTrace.traceExit("removeObserver[ProviderObserver observer]", this);
/*  418 */         return;
/*      */       }
/*      */     }
/*      */ 
/*  422 */     TsapiTrace.traceExit("removeObserver[ProviderObserver observer]", this);
/*      */   }
/*      */ 
/*      */   public final ProviderCapabilities getProviderCapabilities()
/*      */   {
/*  430 */     TsapiTrace.traceEntry("getProviderCapabilities[]", this);
/*  431 */     ProviderCapabilities caps = this.tsProvider.getTsapiProviderCapabilities();
/*  432 */     TsapiTrace.traceExit("getProviderCapabilities[]", this);
/*  433 */     return caps;
/*      */   }
/*      */ 
/*      */   public final ProviderCapabilities getCapabilities()
/*      */   {
/*  441 */     TsapiTrace.traceEntry("getCapabilities[]", this);
/*  442 */     ProviderCapabilities caps = getProviderCapabilities();
/*  443 */     TsapiTrace.traceExit("getCapabilities[]", this);
/*  444 */     return caps;
/*      */   }
/*      */ 
/*      */   public final CallCapabilities getCallCapabilities()
/*      */   {
/*  452 */     TsapiTrace.traceEntry("getCallCapabilities[]", this);
/*  453 */     CallCapabilities caps = this.tsProvider.getTsapiCallCapabilities();
/*  454 */     TsapiTrace.traceExit("getCallCapabilities[]", this);
/*  455 */     return caps;
/*      */   }
/*      */ 
/*      */   public final ConnectionCapabilities getConnectionCapabilities()
/*      */   {
/*  463 */     TsapiTrace.traceEntry("getConnectionCapabilities[]", this);
/*  464 */     ConnectionCapabilities caps = this.tsProvider.getTsapiConnCapabilities();
/*  465 */     TsapiTrace.traceExit("getConnectionCapabilities[]", this);
/*  466 */     return caps;
/*      */   }
/*      */ 
/*      */   public final AddressCapabilities getAddressCapabilities()
/*      */   {
/*  474 */     TsapiTrace.traceEntry("getAddressCapabilities[]", this);
/*  475 */     AddressCapabilities caps = this.tsProvider.getTsapiAddressCapabilities();
/*  476 */     TsapiTrace.traceExit("getAddressCapabilities[]", this);
/*  477 */     return caps;
/*      */   }
/*      */ 
/*      */   public final TerminalConnectionCapabilities getTerminalConnectionCapabilities()
/*      */   {
/*  485 */     TsapiTrace.traceEntry("getTerminalConnectionCapabilities[]", this);
/*  486 */     TerminalConnectionCapabilities caps = this.tsProvider.getTsapiTermConnCapabilities();
/*  487 */     TsapiTrace.traceExit("getTerminalConnectionCapabilities[]", this);
/*  488 */     return caps;
/*      */   }
/*      */ 
/*      */   public final TerminalCapabilities getTerminalCapabilities()
/*      */   {
/*  496 */     TsapiTrace.traceEntry("getTerminalCapabilities[]", this);
/*  497 */     TerminalCapabilities caps = this.tsProvider.getTsapiTerminalCapabilities();
/*  498 */     TsapiTrace.traceExit("getTerminalCapabilities[]", this);
/*  499 */     return caps;
/*      */   }
/*      */ 
/*      */   public final ProviderCapabilities getProviderCapabilities(Terminal terminal)
/*      */     throws InvalidArgumentException, PlatformException
/*      */   {
/*  506 */     TsapiTrace.traceEntry("getProviderCapabilities[Terminal terminal]", this);
/*  507 */     ProviderCapabilities caps = getProviderCapabilities();
/*  508 */     TsapiTrace.traceExit("getProviderCapabilities[Terminal terminal]", this);
/*  509 */     return caps;
/*      */   }
/*      */ 
/*      */   public final CallCapabilities getCallCapabilities(Terminal terminal, Address address)
/*      */     throws InvalidArgumentException, PlatformException
/*      */   {
/*  516 */     TsapiTrace.traceEntry("getCallCapabilities[Terminal terminal, Address address]", this);
/*  517 */     CallCapabilities caps = getCallCapabilities();
/*  518 */     TsapiTrace.traceExit("getCallCapabilities[Terminal terminal, Address address]", this);
/*  519 */     return caps;
/*      */   }
/*      */ 
/*      */   public final ConnectionCapabilities getConnectionCapabilities(Terminal terminal, Address address)
/*      */     throws InvalidArgumentException, PlatformException
/*      */   {
/*  527 */     TsapiTrace.traceEntry("getConnectionCapabilities[Terminal terminal, Address address]", this);
/*  528 */     ConnectionCapabilities caps = getConnectionCapabilities();
/*  529 */     TsapiTrace.traceExit("getConnectionCapabilities[Terminal terminal, Address address]", this);
/*  530 */     return caps;
/*      */   }
/*      */ 
/*      */   public final AddressCapabilities getAddressCapabilities(Terminal terminal)
/*      */     throws InvalidArgumentException, PlatformException
/*      */   {
/*  537 */     TsapiTrace.traceEntry("getAddressCapabilities[Terminal terminal]", this);
/*  538 */     AddressCapabilities caps = getAddressCapabilities();
/*  539 */     TsapiTrace.traceExit("getAddressCapabilities[Terminal terminal]", this);
/*  540 */     return caps;
/*      */   }
/*      */ 
/*      */   public final TerminalConnectionCapabilities getTerminalConnectionCapabilities(Terminal terminal)
/*      */     throws InvalidArgumentException, PlatformException
/*      */   {
/*  547 */     TsapiTrace.traceEntry("getTerminalConnectionCapabilities[Terminal terminal]", this);
/*  548 */     TerminalConnectionCapabilities caps = getTerminalConnectionCapabilities();
/*  549 */     TsapiTrace.traceExit("getTerminalConnectionCapabilities[Terminal terminal]", this);
/*  550 */     return caps;
/*      */   }
/*      */ 
/*      */   public final TerminalCapabilities getTerminalCapabilities(Terminal terminal)
/*      */     throws InvalidArgumentException, PlatformException
/*      */   {
/*  557 */     TsapiTrace.traceEntry("getTerminalCapabilities[Terminal terminal]", this);
/*  558 */     TerminalCapabilities caps = getTerminalCapabilities();
/*  559 */     TsapiTrace.traceExit("getTerminalCapabilities[Terminal terminal]", this);
/*  560 */     return caps;
/*      */   }
/*      */ 
/*      */   public final RouteAddress[] getRouteableAddresses()
/*      */   {
/*  568 */     TsapiTrace.traceEntry("getRouteableAddresses[]", this);
/*  569 */     Vector tsDevice = this.tsProvider.getTSRouteDevices();
/*      */ 
/*  571 */     if (tsDevice == null)
/*      */     {
/*  573 */       TsapiTrace.traceExit("getRouteableAddresses[]", this);
/*  574 */       return null;
/*      */     }
/*      */ 
/*  577 */     synchronized (tsDevice)
/*      */     {
/*  579 */       if (tsDevice.size() == 0)
/*      */       {
/*  581 */         TsapiTrace.traceExit("getRouteableAddresses[]", this);
/*  582 */         return null;
/*      */       }
/*      */ 
/*  585 */       RouteAddress[] routeAddress = new RouteAddress[tsDevice.size()];
/*  586 */       for (int i = 0; i < tsDevice.size(); ++i)
/*      */       {
/*  588 */         routeAddress[i] = ((RouteAddress)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), true));
/*      */       }
/*  590 */       TsapiTrace.traceExit("getRouteableAddresses[]", this);
/*  591 */       return routeAddress;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final ACDAddress[] getACDAddresses()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  599 */     TsapiTrace.traceEntry("getACDAddresses[]", this);
/*  600 */     Vector tsDevice = this.tsProvider.getTSACDDevices();
/*      */ 
/*  602 */     if (tsDevice == null)
/*      */     {
/*  604 */       TsapiTrace.traceExit("getACDAddresses[]", this);
/*  605 */       return null;
/*      */     }
/*      */ 
/*  608 */     synchronized (tsDevice)
/*      */     {
/*  610 */       if (tsDevice.size() == 0)
/*      */       {
/*  612 */         TsapiTrace.traceExit("getACDAddresses[]", this);
/*  613 */         return null;
/*      */       }
/*      */ 
/*  616 */       ACDAddress[] acdAddress = new ACDAddress[tsDevice.size()];
/*  617 */       for (int i = 0; i < tsDevice.size(); ++i)
/*      */       {
/*  619 */         acdAddress[i] = ((ACDAddress)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), true));
/*      */       }
/*  621 */       TsapiTrace.traceExit("getACDAddresses[]", this);
/*  622 */       return acdAddress;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final ACDManagerAddress[] getACDManagerAddresses()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  630 */     TsapiTrace.traceEntry("getACDManagerAddresses[]", this);
/*  631 */     Vector tsDevice = this.tsProvider.getTSACDManagerDevices();
/*      */ 
/*  633 */     if (tsDevice == null)
/*      */     {
/*  635 */       TsapiTrace.traceExit("getACDManagerAddresses[]", this);
/*  636 */       return null;
/*      */     }
/*      */ 
/*  639 */     synchronized (tsDevice)
/*      */     {
/*  641 */       if (tsDevice.size() == 0)
/*      */       {
/*  643 */         TsapiTrace.traceExit("getACDManagerAddresses[]", this);
/*  644 */         return null;
/*      */       }
/*      */ 
/*  647 */       ACDManagerAddress[] acdManagerAddress = new ACDManagerAddress[tsDevice.size()];
/*  648 */       for (int i = 0; i < tsDevice.size(); ++i)
/*      */       {
/*  650 */         acdManagerAddress[i] = ((ACDManagerAddress)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), true));
/*      */       }
/*  652 */       TsapiTrace.traceExit("getACDManagerAddresses[]", this);
/*  653 */       return acdManagerAddress;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final String getVendor()
/*      */   {
/*  660 */     TsapiTrace.traceEntry("getVendor[]", this);
/*  661 */     String vendor = this.tsProvider.getVendor();
/*  662 */     TsapiTrace.traceExit("getVendor[]", this);
/*  663 */     return vendor;
/*      */   }
/*      */ 
/*      */   public final byte[] getVendorVersion()
/*      */   {
/*  668 */     TsapiTrace.traceEntry("getVendorVersion[]", this);
/*  669 */     byte[] version = this.tsProvider.getVendorVersion();
/*  670 */     TsapiTrace.traceExit("getVendorVersion[]", this);
/*  671 */     return version;
/*      */   }
/*      */ 
/*      */   public final void updateAddresses()
/*      */   {
/*  676 */     TsapiTrace.traceEntry("updateAddresses[]", this);
/*  677 */     this.tsProvider.updateAddresses();
/*  678 */     TsapiTrace.traceExit("updateAddresses[]", this);
/*      */   }
/*      */ 
/*      */   public final void setDebugPrinting(boolean enable)
/*      */   {
/*  686 */     TsapiTrace.traceEntry("setDebugPrinting[boolean enable]", this);
/*  687 */     this.tsProvider.setDebugPrinting(enable);
/*  688 */     TsapiTrace.traceExit("setDebugPrinting[boolean enable]", this);
/*      */   }
/*      */ 
/*      */   public final String getServerID()
/*      */   {
/*  696 */     TsapiTrace.traceEntry("getServerID[]", this);
/*  697 */     String id = this.tsProvider.getServerID();
/*  698 */     TsapiTrace.traceExit("getServerID[]", this);
/*  699 */     return id;
/*      */   }
/*      */ 
/*      */   public final void setHeartbeatInterval(short heartbeatInterval)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/*  711 */     TsapiTrace.traceEntry("setHeartbeatInterval[short heartbeatInterval]", this);
/*  712 */     this.tsProvider.setHeartbeatInterval(heartbeatInterval);
/*  713 */     TsapiTrace.traceExit("setHeartbeatInterval[short heartbeatInterval]", this);
/*      */   }
/*      */ 
/*      */   public final Call getCall(int callID)
/*      */   {
/*  718 */     TsapiTrace.traceEntry("getCall[int callID]", this);
/*  719 */     TSCall call = this.tsProvider.createTSCall(callID);
/*  720 */     if (call != null)
/*      */     {
/*  722 */       Call callObj = (Call)TsapiCreateObject.getTsapiObject(call, false);
/*  723 */       TsapiTrace.traceExit("getCall[int callID]", this);
/*  724 */       return callObj;
/*      */     }
/*      */ 
/*  728 */     throw new TsapiPlatformException(4, 0, "could not create call");
/*      */   }
/*      */ 
/*      */   public final Address getAddress(ExtendedDeviceID tsapiDeviceID)
/*      */   {
/*  734 */     TsapiTrace.traceEntry("getAddress[com.avaya.jtapi.tsapi.impl.ExtendedDeviceID tsapiDeviceID]", this);
/*      */ 
/*  736 */     CSTAExtendedDeviceID deviceID = new CSTAExtendedDeviceID(tsapiDeviceID.getDeviceID(), tsapiDeviceID.getDeviceIDType(), tsapiDeviceID.getDeviceIDStatus());
/*      */ 
/*  740 */     TSDevice device = this.tsProvider.createDevice(deviceID);
/*  741 */     if (device != null)
/*      */     {
/*  743 */       Address addr = (Address)TsapiCreateObject.getTsapiObject(device, true);
/*  744 */       TsapiTrace.traceExit("getAddress[com.avaya.jtapi.tsapi.impl.ExtendedDeviceID tsapiDeviceID]", this);
/*  745 */       return addr;
/*      */     }
/*      */ 
/*  749 */     throw new TsapiPlatformException(4, 0, "could not create address");
/*      */   }
/*      */ 
/*      */   public final Terminal getTerminal(ExtendedDeviceID tsapiDeviceID)
/*      */   {
/*  755 */     TsapiTrace.traceEntry("getTerminal[com.avaya.jtapi.tsapi.impl.ExtendedDeviceID tsapiDeviceID]", this);
/*      */ 
/*  757 */     CSTAExtendedDeviceID deviceID = new CSTAExtendedDeviceID(tsapiDeviceID.getDeviceID(), tsapiDeviceID.getDeviceIDType(), tsapiDeviceID.getDeviceIDStatus());
/*      */ 
/*  760 */     TSDevice device = this.tsProvider.createDevice(deviceID);
/*  761 */     if ((device != null) && (device.isTerminal()))
/*      */     {
/*  763 */       Terminal term = (Terminal)TsapiCreateObject.getTsapiObject(device, false);
/*  764 */       TsapiTrace.traceExit("getTerminal[com.avaya.jtapi.tsapi.impl.ExtendedDeviceID tsapiDeviceID]", this);
/*  765 */       return term;
/*      */     }
/*      */ 
/*  769 */     String info = "";
/*      */ 
/*  771 */     if (device == null)
/*      */     {
/*  773 */       info = "; device is null";
/*      */     }
/*  775 */     else if (!device.isTerminal())
/*      */     {
/*  777 */       info = "; device is not a terminal";
/*      */     }
/*      */ 
/*  780 */     throw new TsapiPlatformException(4, 0, "could not create terminal for deviceID " + tsapiDeviceID.getDeviceID() + info);
/*      */   }
/*      */ 
/*      */   public final Connection getConnection(ConnectionID tsapiConnID, Address address)
/*      */   {
/*  786 */     TsapiTrace.traceEntry("getConnection[com.avaya.jtapi.tsapi.ConnectionID tsapiConnID, Address address]", this);
/*  787 */     CSTAConnectionID connID = new CSTAConnectionID(tsapiConnID.getCallID(), tsapiConnID.getDeviceID(), (short)tsapiConnID.getDevIDType());
/*      */ 
/*  790 */     if (!(address instanceof ITsapiAddress)) {
/*  791 */       throw new TsapiPlatformException(3, 0, "The given Address is not an instanceof ITsapiAddress");
/*      */     }
/*      */ 
/*  794 */     TSDevice tsDevice = ((TsapiAddress)address).getTSDevice();
/*  795 */     if (tsDevice != null)
/*      */     {
/*  797 */       TSConnection conn = this.tsProvider.createTSConnection(connID, tsDevice);
/*  798 */       if (conn != null)
/*      */       {
/*  800 */         Connection connObj = (Connection)TsapiCreateObject.getTsapiObject(conn, true);
/*  801 */         TsapiTrace.traceExit("getConnection[com.avaya.jtapi.tsapi.ConnectionID tsapiConnID, Address address]", this);
/*  802 */         return connObj;
/*      */       }
/*      */ 
/*  806 */       throw new TsapiPlatformException(4, 0, "could not create connection");
/*      */     }
/*      */ 
/*  811 */     throw new TsapiPlatformException(4, 0, "could not locate address");
/*      */   }
/*      */ 
/*      */   public final TerminalConnection getTerminalConnection(ConnectionID tsapiConnID, Terminal terminal)
/*      */   {
/*  817 */     TsapiTrace.traceEntry("getTerminalConnection[com.avaya.jtapi.tsapi.ConnectionID tsapiConnID, Terminal terminal]", this);
/*  818 */     CSTAConnectionID connID = new CSTAConnectionID(tsapiConnID.getCallID(), tsapiConnID.getDeviceID(), (short)tsapiConnID.getDevIDType());
/*      */ 
/*  821 */     if (!(terminal instanceof ITsapiTerminal)) {
/*  822 */       throw new TsapiPlatformException(3, 0, "The given Terminal is not an instanceof ITsapiTerminal");
/*      */     }
/*      */ 
/*  825 */     TSDevice tsDevice = ((TsapiTerminal)terminal).getTSDevice();
/*  826 */     if (tsDevice != null)
/*      */     {
/*  828 */       TSConnection conn = this.tsProvider.createTSConnection(connID, tsDevice);
/*  829 */       if (conn != null)
/*      */       {
/*  831 */         TerminalConnection tconn = (TerminalConnection)TsapiCreateObject.getTsapiObject(conn, false);
/*  832 */         TsapiTrace.traceExit("getTerminalConnection[com.avaya.jtapi.tsapi.ConnectionID tsapiConnID, Terminal terminal]", this);
/*  833 */         return tconn;
/*      */       }
/*      */ 
/*  837 */       throw new TsapiPlatformException(4, 0, "could not create terminal connection");
/*      */     }
/*      */ 
/*  842 */     throw new TsapiPlatformException(4, 0, "could not locate terminal");
/*      */   }
/*      */ 
/*      */   public final void setPrivateData(Object data)
/*      */   {
/*  856 */     TsapiTrace.traceEntry("setPrivateData[Object data]", this);
/*      */     try
/*      */     {
/*  859 */       CSTAPrivate privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data);
/*  860 */       this.tsProvider.setPrivateData(privData);
/*      */     }
/*      */     catch (ClassCastException e)
/*      */     {
/*  864 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*      */     }
/*      */ 
/*  867 */     TsapiTrace.traceExit("setPrivateData[Object data]", this);
/*      */   }
/*      */ 
/*      */   public final Object getPrivateData()
/*      */   {
/*  872 */     TsapiTrace.traceEntry("getPrivateData[]", this);
/*  873 */     Object data = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate)this.tsProvider.getPrivateData());
/*  874 */     TsapiTrace.traceExit("getPrivateData[]", this);
/*  875 */     return data;
/*      */   }
/*      */ 
/*      */   public final Object sendPrivateData(Object data)
/*      */   {
/*  880 */     TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
/*      */     try
/*      */     {
/*  883 */       Object obj = this.tsProvider.sendPrivateData(TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data));
/*  884 */       TsapiTrace.traceExit("sendPrivateData[Object data]", this);
/*  885 */       return obj;
/*      */     }
/*      */     catch (ClassCastException e)
/*      */     {
/*  889 */       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  894 */       throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
/*      */     }
/*      */   }
/*      */ 
/*      */   public final TrunkGroupInfo getTrunkGroupInfo(String trunkAccessCode)
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  904 */     TsapiTrace.traceEntry("getTrunkGroupInfo[String trunkAccessCode]", this);
/*  905 */     LucentTrunkGroupInfo tInfo = this.tsProvider.getTrunkGroupInfo(trunkAccessCode);
/*  906 */     TrunkGroupInfo tgi = new TrunkGroupInfo(tInfo.idleTrunks, tInfo.usedTrunks);
/*  907 */     TsapiTrace.traceExit("getTrunkGroupInfo[String trunkAccessCode]", this);
/*  908 */     return tgi;
/*      */   }
/*      */ 
/*      */   public final CallClassifierInfo getCallClassifierInfo()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  917 */     TsapiTrace.traceEntry("getCallClassifierInfo[]", this);
/*  918 */     CallClassifierInfo info = this.tsProvider.getCallClassifierInfo();
/*  919 */     TsapiTrace.traceExit("getCallClassifierInfo[]", this);
/*  920 */     return info;
/*      */   }
/*      */ 
/*      */   public final Date getSwitchDateAndTime()
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  929 */     TsapiTrace.traceEntry("getSwitchDateAndTime[]", this);
/*  930 */     Date date = this.tsProvider.getSwitchDateAndTime();
/*  931 */     TsapiTrace.traceExit("getSwitchDateAndTime[]", this);
/*  932 */     return date;
/*      */   }
/*      */ 
/*      */   public final void setAdviceOfCharge(boolean flag)
/*      */     throws TsapiMethodNotSupportedException
/*      */   {
/*  942 */     TsapiTrace.traceEntry("setAdviceOfCharge[boolean flag]", this);
/*  943 */     this.tsProvider.setAdviceOfCharge(flag);
/*  944 */     TsapiTrace.traceExit("setAdviceOfCharge[boolean flag]", this);
/*      */   }
/*      */ 
/*      */   public final int hashCode()
/*      */   {
/*  951 */     return this.tsProvider.hashCode();
/*      */   }
/*      */ 
/*      */   public boolean equals(Object obj)
/*      */   {
/*  957 */     if (obj instanceof TsapiProvider)
/*      */     {
/*  959 */       return this.tsProvider.equals(((TsapiProvider)obj).tsProvider);
/*      */     }
/*      */ 
/*  963 */     return false;
/*      */   }
/*      */ 
/*      */   public TsapiProvider(TSProviderImpl _tsProvider)
/*      */   {
/*  973 */     this.tsProvider = _tsProvider;
/*  974 */     this.name = _tsProvider.getName();
/*  975 */     TsapiTrace.traceConstruction(this, TsapiProvider.class);
/*      */   }
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/*  981 */     super.finalize();
/*  982 */     TsapiTrace.traceDestruction(this, TsapiProvider.class);
/*      */   }
/*      */ 
/*      */   final TSProviderImpl getTSProviderImpl()
/*      */   {
/*  990 */     TsapiTrace.traceEntry("getTSProviderImpl[]", this);
/*  991 */     TsapiTrace.traceExit("getTSProviderImpl[]", this);
/*  992 */     return this.tsProvider;
/*      */   }
/*      */ 
/*      */   public void addProviderListener(ProviderListener listener)
/*      */     throws ResourceUnavailableException
/*      */   {
/* 1001 */     TsapiTrace.traceEntry("addProviderListener[ProviderListener listener]", this);
/*      */ 
/* 1003 */     Vector tsapiProviderMonitors = this.tsProvider.getProviderMonitorThreads();
/*      */ 
/* 1005 */     TsapiProviderMonitor monitor = null;
/* 1006 */     synchronized (tsapiProviderMonitors)
/*      */     {
/* 1008 */       for (int i = 0; i < tsapiProviderMonitors.size(); ++i)
/*      */       {
/* 1010 */         monitor = (TsapiProviderMonitor)tsapiProviderMonitors.elementAt(i);
/* 1011 */         if (monitor.getMonitor() != listener)
/*      */           continue;
/* 1013 */         this.tsProvider.addMonitor(monitor);
/* 1014 */         TsapiTrace.traceExit("addProviderListener(ProviderListener listener)", this);
/* 1015 */         return;
/*      */       }
/*      */ 
/* 1018 */       monitor = new TsapiProviderMonitor(this.tsProvider, listener);
/*      */     }
/*      */ 
/* 1021 */     this.tsProvider.addMonitor(monitor);
/* 1022 */     TsapiTrace.traceExit("addProviderListener[ProviderListener listener]", this);
/*      */   }
/*      */ 
/*      */   public ProviderListener[] getProviderListeners()
/*      */   {
/* 1027 */     TsapiTrace.traceEntry("getProviderListeners[]", this);
/* 1028 */     Vector tsapiProviderMonitors = this.tsProvider.getProviderMonitorThreads();
/*      */ 
/* 1030 */     if ((tsapiProviderMonitors == null) || (tsapiProviderMonitors.size() == 0))
/*      */     {
/* 1032 */       TsapiTrace.traceExit("getProviderListeners[]", this);
/* 1033 */       return null;
/*      */     }
/*      */ 
/* 1036 */     synchronized (tsapiProviderMonitors)
/*      */     {
/* 1038 */       List listeners = new ArrayList();
/*      */ 
/* 1040 */       for (int i = 0; i < tsapiProviderMonitors.size(); ++i)
/*      */       {
/* 1042 */         TsapiProviderMonitor monitor = (TsapiProviderMonitor)tsapiProviderMonitors.elementAt(i);
/* 1043 */         if (monitor.getMonitor() instanceof ProviderListener) {
/* 1044 */           listeners.add((ProviderListener)monitor.getMonitor());
/*      */         }
/*      */       }
/* 1047 */       TsapiTrace.traceExit("getProviderListeners[]", this);
/* 1048 */       return (ProviderListener[])listeners.toArray(new ProviderListener[0]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeProviderListener(ProviderListener listener)
/*      */   {
/* 1054 */     TsapiTrace.traceEntry("removeProviderListener[ProviderListener listener]", this);
/* 1055 */     Vector tsapiProviderMonitors = this.tsProvider.getProviderMonitorThreads();
/*      */ 
/* 1057 */     if ((tsapiProviderMonitors == null) || (tsapiProviderMonitors.size() == 0))
/*      */     {
/* 1059 */       TsapiTrace.traceExit("removeProviderListener[ProviderListener listener]", this);
/* 1060 */       return;
/*      */     }
/*      */ 
/* 1063 */     synchronized (tsapiProviderMonitors)
/*      */     {
/* 1065 */       for (int i = 0; i < tsapiProviderMonitors.size(); ++i)
/*      */       {
/* 1067 */         TsapiProviderMonitor monitor = (TsapiProviderMonitor)tsapiProviderMonitors.elementAt(i);
/* 1068 */         if (monitor.getMonitor() != listener)
/*      */           continue;
/* 1070 */         this.tsProvider.removeMonitor(monitor);
/* 1071 */         TsapiTrace.traceExit("removeProviderListener[ProviderListener listener]", this);
/* 1072 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getAdministeredSwitchSoftwareVersion()
/*      */   {
/* 1081 */     TsapiTrace.traceEntry("getAdministeredSwitchSoftwareVersion[]", this);
/* 1082 */     String version = this.tsProvider.getAdministeredSwitchSoftwareVersion();
/* 1083 */     TsapiTrace.traceExit("getAdministeredSwitchSoftwareVersion[]", this);
/* 1084 */     return version;
/*      */   }
/*      */ 
/*      */   public String getSwitchSoftwareVersion() {
/* 1088 */     TsapiTrace.traceEntry("getSwitchSoftwareVersion[]", this);
/* 1089 */     String version = this.tsProvider.getSwitchSoftwareVersion();
/* 1090 */     TsapiTrace.traceExit("getSwitchSoftwareVersion[]", this);
/* 1091 */     return version;
/*      */   }
/*      */ 
/*      */   public String getOfferType() {
/* 1095 */     TsapiTrace.traceEntry("getOfferType[]", this);
/* 1096 */     String type = this.tsProvider.getOfferType();
/* 1097 */     TsapiTrace.traceExit("getOfferType[]", this);
/* 1098 */     return type;
/*      */   }
/*      */ 
/*      */   public String getServerType() {
/* 1102 */     TsapiTrace.traceEntry("getServerType[]", this);
/* 1103 */     String type = this.tsProvider.getServerType();
/* 1104 */     TsapiTrace.traceExit("getServerType[]", this);
/* 1105 */     return type;
/*      */   }
/*      */ 
/*      */   public final String requestPrivileges()
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/* 1115 */     TsapiTrace.traceEntry("requestPrivileges[]", this);
/* 1116 */     String privs = this.tsProvider.requestPrivileges();
/* 1117 */     TsapiTrace.traceExit("requestPrivileges[]", this);
/* 1118 */     return privs;
/*      */   }
/*      */ 
/*      */   public final void setPrivileges(String xmlData)
/*      */     throws TsapiInvalidArgumentException
/*      */   {
/* 1129 */     TsapiTrace.traceEntry("setPrivileges[String xmlData]", this);
/* 1130 */     this.tsProvider.setPrivileges(xmlData);
/* 1131 */     TsapiTrace.traceExit("setPrivileges[String xmlData]", this);
/*      */   }
/*      */ 
/*      */   public void setSessionTimeout(int timeout) {
/* 1135 */     TsapiTrace.traceEntry("setSessionTimeout[int timeout]", this);
/* 1136 */     this.tsProvider.setSessionTimeout(timeout);
/* 1137 */     TsapiTrace.traceExit("setSessionTimeout[int timeout]", this);
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiProvider
 * JD-Core Version:    0.5.4
 */