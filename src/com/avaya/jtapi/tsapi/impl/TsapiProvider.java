 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.CallClassifierInfo;
 import com.avaya.jtapi.tsapi.ConnectionID;
 import com.avaya.jtapi.tsapi.ExtendedDeviceID;
 import com.avaya.jtapi.tsapi.ITsapiAddress;
 import com.avaya.jtapi.tsapi.ITsapiProviderEx;
 import com.avaya.jtapi.tsapi.ITsapiProviderPrivate;
 import com.avaya.jtapi.tsapi.ITsapiTerminal;
 import com.avaya.jtapi.tsapi.LucentV7Provider;
 import com.avaya.jtapi.tsapi.TrunkGroupInfo;
 import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
 import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
 import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
 import com.avaya.jtapi.tsapi.TsapiPlatformException;
 import com.avaya.jtapi.tsapi.TsapiPrivate;
 import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
 import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
 import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
 import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
 import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
 import com.avaya.jtapi.tsapi.csta1.LucentTrunkGroupInfo;
 import com.avaya.jtapi.tsapi.impl.core.TSCall;
 import com.avaya.jtapi.tsapi.impl.core.TSConnection;
 import com.avaya.jtapi.tsapi.impl.core.TSDevice;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiProviderMonitor;
 import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.util.Vector;
 import javax.telephony.Address;
 import javax.telephony.Call;
 import javax.telephony.Connection;
 import javax.telephony.InvalidArgumentException;
 import javax.telephony.PlatformException;
 import javax.telephony.ProviderListener;
 import javax.telephony.ProviderObserver;
 import javax.telephony.ResourceUnavailableException;
 import javax.telephony.Terminal;
 import javax.telephony.TerminalConnection;
 import javax.telephony.callcenter.ACDAddress;
 import javax.telephony.callcenter.ACDManagerAddress;
 import javax.telephony.callcenter.RouteAddress;
 import javax.telephony.capabilities.AddressCapabilities;
 import javax.telephony.capabilities.CallCapabilities;
 import javax.telephony.capabilities.ConnectionCapabilities;
 import javax.telephony.capabilities.ProviderCapabilities;
 import javax.telephony.capabilities.TerminalCapabilities;
 import javax.telephony.capabilities.TerminalConnectionCapabilities;
 import javax.telephony.privatedata.PrivateData;
 
 class TsapiProvider
   implements ITsapiProviderEx, PrivateData, ITsapiProviderPrivate, LucentV7Provider
 {
   TSProviderImpl tsProvider;
   private String name;
 
   public TsapiProvider(String url, Vector<TsapiVendor> vendors)
   {
     TsapiTrace.traceEntry("TsapiProvider[String url, Vector<TsapiVendor> vendors]", this);
     this.name = url;
 
     this.tsProvider = new TSProviderImpl(url, vendors);
     if (this.tsProvider == null)
     {
       throw new TsapiPlatformException(4, 0, "could not create provider");
     }
     TsapiTrace.traceExit("TsapiProvider[String url, Vector<TsapiVendor> vendors]", this);
   }
 
   public final int getState()
   {
     TsapiTrace.traceEntry("getState[]", this);
     int state = this.tsProvider.getState();
     TsapiTrace.traceExit("getState[]", this);
     return state;
   }
 
   public int getCurrentStateOfCallByForceQueryOnTelephonyServer(int callId)
   {
     TsapiTrace.traceEntry("getCurrentStateOfCall[]", this);
     try
     {
       int i = this.tsProvider.getCurrentStateOfCallFromTelephonyServer(callId);
       TsapiTrace.traceExit("getCurrentStateOfCall[]", this);
       return i;
     }
     catch (Exception ex) {
       throw new TsapiPlatformException(4, 0, "Could not get status of the call. [" + ex + "]");
     }
   }
 
   public int getCurrentStateOfCallByForceQueryOnTelephonyServer(Call tsapiCall) {
     TsapiTrace.traceEntry("getCurrentStateOfCall[]", this);
     int i = this.tsProvider.getCurrentStateOfCallFromTelephonyServer(((TsapiCall)tsapiCall).getTSCall());
     TsapiTrace.traceExit("getCurrentStateOfCall[]", this);
     return i;
   }
 
   public final int getTsapiState()
   {
     TsapiTrace.traceEntry("getTsapiState[]", this);
     int state = this.tsProvider.getTsapiState();
     TsapiTrace.traceExit("getTsapiState[]", this);
     return state;
   }
 
   public final String getName()
   {
     TsapiTrace.traceEntry("getName[]", this);
     TsapiTrace.traceExit("getName[]", this);
     return this.name;
   }
 
   public final Call[] getCalls()
   {
     TsapiTrace.traceEntry("getCalls[]", this);
     Vector tsCall = this.tsProvider.getTSCalls();
 
     if (tsCall == null)
     {
       TsapiTrace.traceExit("getCalls[]", this);
       return null;
     }
 
     synchronized (tsCall)
     {
       if (tsCall.size() == 0)
       {
         TsapiTrace.traceExit("getCalls[]", this);
         return null;
       }
 
       Call[] tsapiCall = new Call[tsCall.size()];
       for (int i = 0; i < tsCall.size(); ++i)
       {
         tsapiCall[i] = ((Call)TsapiCreateObject.getTsapiObject((TSCall)tsCall.elementAt(i), false));
       }
       TsapiTrace.traceExit("getCalls[]", this);
       return tsapiCall;
     }
   }
 
   public final Address[] getAddresses()
   {
     TsapiTrace.traceEntry("getAddresses[]", this);
     Vector tsDevice = this.tsProvider.getTSAddressDevices();
 
     if (tsDevice == null)
     {
       TsapiTrace.traceExit("getAddresses[]", this);
       return null;
     }
 
     synchronized (tsDevice)
     {
       if (tsDevice.size() == 0)
       {
         TsapiTrace.traceExit("getAddresses[]", this);
         return null;
       }
 
       Address[] tsapiAddress = new Address[tsDevice.size()];
       for (int i = 0; i < tsDevice.size(); ++i)
       {
         tsapiAddress[i] = ((Address)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), true));
       }
       TsapiTrace.traceExit("getAddresses[]", this);
       return tsapiAddress;
     }
   }
 
   public final Terminal[] getTerminals()
   {
     TsapiTrace.traceEntry("getTerminals[]", this);
     Vector tsDevice = this.tsProvider.getTSTerminalDevices();
 
     if (tsDevice == null)
     {
       TsapiTrace.traceExit("getTerminals[]", this);
       return null;
     }
 
     synchronized (tsDevice)
     {
       if (tsDevice.size() == 0)
       {
         TsapiTrace.traceExit("getTerminals[]", this);
         return null;
       }
 
       Terminal[] tsapiTerminal = new Terminal[tsDevice.size()];
       for (int i = 0; i < tsDevice.size(); ++i)
       {
         tsapiTerminal[i] = ((Terminal)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), false));
       }
       TsapiTrace.traceExit("getTerminals[]", this);
       return tsapiTerminal;
     }
   }
 
   public final Call createCall()
     throws TsapiResourceUnavailableException, TsapiInvalidStateException, TsapiPrivilegeViolationException, TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("createCall[]", this);
     Call call = null;
     if (this.tsProvider.isLucentV7())
     {
       call = new LucentV7CallImpl((LucentV7ProviderImpl)this);
     }
     else if (this.tsProvider.isLucentV5())
     {
       call = new LucentV5CallImpl((LucentV5ProviderImpl)this);
     }
     else if (this.tsProvider.isLucent())
     {
       call = new LucentCallEx2Impl((LucentProviderImpl)this);
     }
     else
     {
       call = new TsapiCall(this);
     }
     TsapiTrace.traceExit("createCall[]", this);
     return call;
   }
 
   public final Address getAddress(String number)
     throws TsapiInvalidArgumentException
   {
     TsapiTrace.traceEntry("getAddress[String number]", this);
     TSDevice tsDevice = this.tsProvider.createDevice(number, true);
     if (tsDevice != null)
     {
       Address addr = (Address)TsapiCreateObject.getTsapiObject(tsDevice, true);
       TsapiTrace.traceExit("getAddress[String number]", this);
       return addr;
     }
 
     throw new TsapiPlatformException(4, 0, "could not create address");
   }
 
   public final Terminal getTerminal(String name)
     throws TsapiInvalidArgumentException
   {
     TsapiTrace.traceEntry("getTerminal[String name]", this);
     TSDevice tsDevice = this.tsProvider.createDevice(name, true);
 
     if ((tsDevice != null) && (tsDevice.isTerminal()))
     {
       Terminal term = (Terminal)TsapiCreateObject.getTsapiObject(tsDevice, false);
       TsapiTrace.traceExit("getTerminal[String name]", this);
       return term;
     }
 
     String info = "";
     if (tsDevice == null)
     {
       info = "; device is null";
     }
     else if (!tsDevice.isTerminal())
     {
       info = "; device is not a terminal";
     }
     throw new TsapiPlatformException(4, 0, "could not create terminal: " + name + info);
   }
 
   public final void shutdown()
   {
     TsapiTrace.traceEntry("shutdown[]", this);
     this.tsProvider.shutdown();
     TsapiTrace.traceExit("shutdown[]", this);
   }
 
   public void addObserver(ProviderObserver observer)
     throws TsapiResourceUnavailableException
   {
     TsapiTrace.traceEntry("addObserver[ProviderObserver observer]", this);
 
     Vector monitors = this.tsProvider.getProviderMonitorThreads();
 
     TsapiProviderMonitor monitor = null;
     synchronized (monitors)
     {
       for (int i = 0; i < monitors.size(); ++i)
       {
         monitor = (TsapiProviderMonitor)monitors.elementAt(i);
         if (monitor.getMonitor() != observer)
           continue;
         this.tsProvider.addMonitor(monitor);
         TsapiTrace.traceExit("addObserver[ProviderObserver observer]", this);
         return;
       }
 
       monitor = new TsapiProviderMonitor(this.tsProvider, observer);
     }
 
     this.tsProvider.addMonitor(monitor);
     TsapiTrace.traceExit("addObserver[ProviderObserver observer]", this);
   }
 
   public ProviderObserver[] getObservers()
   {
     TsapiTrace.traceEntry("getObservers[]", this);
     Vector monitors = this.tsProvider.getProviderMonitorThreads();
 
     if ((monitors == null) || (monitors.size() == 0))
     {
       TsapiTrace.traceExit("getObservers[]", this);
       return null;
     }
 
     synchronized (monitors)
     {
       List observers = new ArrayList();
 
       for (int i = 0; i < monitors.size(); ++i)
       {
         TsapiProviderMonitor monitor = (TsapiProviderMonitor)monitors.elementAt(i);
         if (monitor.getMonitor() instanceof ProviderObserver) {
           observers.add((ProviderObserver)monitor.getMonitor());
         }
       }
       TsapiTrace.traceExit("getObservers[]", this);
       return (ProviderObserver[])observers.toArray(new ProviderObserver[0]);
     }
   }
 
   public void removeObserver(ProviderObserver observer)
   {
     TsapiTrace.traceEntry("removeObserver[ProviderObserver observer]", this);
     Vector monitors = this.tsProvider.getProviderMonitorThreads();
 
     if ((monitors == null) || (monitors.size() == 0))
     {
       TsapiTrace.traceExit("removeObserver[ProviderObserver observer]", this);
       return;
     }
 
     synchronized (monitors)
     {
       for (int i = 0; i < monitors.size(); ++i)
       {
         TsapiProviderMonitor monitor = (TsapiProviderMonitor)monitors.elementAt(i);
         if (monitor.getMonitor() != observer)
           continue;
         this.tsProvider.removeMonitor(monitor);
         TsapiTrace.traceExit("removeObserver[ProviderObserver observer]", this);
         return;
       }
     }
 
     TsapiTrace.traceExit("removeObserver[ProviderObserver observer]", this);
   }
 
   public final ProviderCapabilities getProviderCapabilities()
   {
     TsapiTrace.traceEntry("getProviderCapabilities[]", this);
     ProviderCapabilities caps = this.tsProvider.getTsapiProviderCapabilities();
     TsapiTrace.traceExit("getProviderCapabilities[]", this);
     return caps;
   }
 
   public final ProviderCapabilities getCapabilities()
   {
     TsapiTrace.traceEntry("getCapabilities[]", this);
     ProviderCapabilities caps = getProviderCapabilities();
     TsapiTrace.traceExit("getCapabilities[]", this);
     return caps;
   }
 
   public final CallCapabilities getCallCapabilities()
   {
     TsapiTrace.traceEntry("getCallCapabilities[]", this);
     CallCapabilities caps = this.tsProvider.getTsapiCallCapabilities();
     TsapiTrace.traceExit("getCallCapabilities[]", this);
     return caps;
   }
 
   public final ConnectionCapabilities getConnectionCapabilities()
   {
     TsapiTrace.traceEntry("getConnectionCapabilities[]", this);
     ConnectionCapabilities caps = this.tsProvider.getTsapiConnCapabilities();
     TsapiTrace.traceExit("getConnectionCapabilities[]", this);
     return caps;
   }
 
   public final AddressCapabilities getAddressCapabilities()
   {
     TsapiTrace.traceEntry("getAddressCapabilities[]", this);
     AddressCapabilities caps = this.tsProvider.getTsapiAddressCapabilities();
     TsapiTrace.traceExit("getAddressCapabilities[]", this);
     return caps;
   }
 
   public final TerminalConnectionCapabilities getTerminalConnectionCapabilities()
   {
     TsapiTrace.traceEntry("getTerminalConnectionCapabilities[]", this);
     TerminalConnectionCapabilities caps = this.tsProvider.getTsapiTermConnCapabilities();
     TsapiTrace.traceExit("getTerminalConnectionCapabilities[]", this);
     return caps;
   }
 
   public final TerminalCapabilities getTerminalCapabilities()
   {
     TsapiTrace.traceEntry("getTerminalCapabilities[]", this);
     TerminalCapabilities caps = this.tsProvider.getTsapiTerminalCapabilities();
     TsapiTrace.traceExit("getTerminalCapabilities[]", this);
     return caps;
   }
 
   public final ProviderCapabilities getProviderCapabilities(Terminal terminal)
     throws InvalidArgumentException, PlatformException
   {
     TsapiTrace.traceEntry("getProviderCapabilities[Terminal terminal]", this);
     ProviderCapabilities caps = getProviderCapabilities();
     TsapiTrace.traceExit("getProviderCapabilities[Terminal terminal]", this);
     return caps;
   }
 
   public final CallCapabilities getCallCapabilities(Terminal terminal, Address address)
     throws InvalidArgumentException, PlatformException
   {
     TsapiTrace.traceEntry("getCallCapabilities[Terminal terminal, Address address]", this);
     CallCapabilities caps = getCallCapabilities();
     TsapiTrace.traceExit("getCallCapabilities[Terminal terminal, Address address]", this);
     return caps;
   }
 
   public final ConnectionCapabilities getConnectionCapabilities(Terminal terminal, Address address)
     throws InvalidArgumentException, PlatformException
   {
     TsapiTrace.traceEntry("getConnectionCapabilities[Terminal terminal, Address address]", this);
     ConnectionCapabilities caps = getConnectionCapabilities();
     TsapiTrace.traceExit("getConnectionCapabilities[Terminal terminal, Address address]", this);
     return caps;
   }
 
   public final AddressCapabilities getAddressCapabilities(Terminal terminal)
     throws InvalidArgumentException, PlatformException
   {
     TsapiTrace.traceEntry("getAddressCapabilities[Terminal terminal]", this);
     AddressCapabilities caps = getAddressCapabilities();
     TsapiTrace.traceExit("getAddressCapabilities[Terminal terminal]", this);
     return caps;
   }
 
   public final TerminalConnectionCapabilities getTerminalConnectionCapabilities(Terminal terminal)
     throws InvalidArgumentException, PlatformException
   {
     TsapiTrace.traceEntry("getTerminalConnectionCapabilities[Terminal terminal]", this);
     TerminalConnectionCapabilities caps = getTerminalConnectionCapabilities();
     TsapiTrace.traceExit("getTerminalConnectionCapabilities[Terminal terminal]", this);
     return caps;
   }
 
   public final TerminalCapabilities getTerminalCapabilities(Terminal terminal)
     throws InvalidArgumentException, PlatformException
   {
     TsapiTrace.traceEntry("getTerminalCapabilities[Terminal terminal]", this);
     TerminalCapabilities caps = getTerminalCapabilities();
     TsapiTrace.traceExit("getTerminalCapabilities[Terminal terminal]", this);
     return caps;
   }
 
   public final RouteAddress[] getRouteableAddresses()
   {
     TsapiTrace.traceEntry("getRouteableAddresses[]", this);
     Vector tsDevice = this.tsProvider.getTSRouteDevices();
 
     if (tsDevice == null)
     {
       TsapiTrace.traceExit("getRouteableAddresses[]", this);
       return null;
     }
 
     synchronized (tsDevice)
     {
       if (tsDevice.size() == 0)
       {
         TsapiTrace.traceExit("getRouteableAddresses[]", this);
         return null;
       }
 
       RouteAddress[] routeAddress = new RouteAddress[tsDevice.size()];
       for (int i = 0; i < tsDevice.size(); ++i)
       {
         routeAddress[i] = ((RouteAddress)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), true));
       }
       TsapiTrace.traceExit("getRouteableAddresses[]", this);
       return routeAddress;
     }
   }
 
   public final ACDAddress[] getACDAddresses()
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("getACDAddresses[]", this);
     Vector tsDevice = this.tsProvider.getTSACDDevices();
 
     if (tsDevice == null)
     {
       TsapiTrace.traceExit("getACDAddresses[]", this);
       return null;
     }
 
     synchronized (tsDevice)
     {
       if (tsDevice.size() == 0)
       {
         TsapiTrace.traceExit("getACDAddresses[]", this);
         return null;
       }
 
       ACDAddress[] acdAddress = new ACDAddress[tsDevice.size()];
       for (int i = 0; i < tsDevice.size(); ++i)
       {
         acdAddress[i] = ((ACDAddress)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), true));
       }
       TsapiTrace.traceExit("getACDAddresses[]", this);
       return acdAddress;
     }
   }
 
   public final ACDManagerAddress[] getACDManagerAddresses()
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("getACDManagerAddresses[]", this);
     Vector tsDevice = this.tsProvider.getTSACDManagerDevices();
 
     if (tsDevice == null)
     {
       TsapiTrace.traceExit("getACDManagerAddresses[]", this);
       return null;
     }
 
     synchronized (tsDevice)
     {
       if (tsDevice.size() == 0)
       {
         TsapiTrace.traceExit("getACDManagerAddresses[]", this);
         return null;
       }
 
       ACDManagerAddress[] acdManagerAddress = new ACDManagerAddress[tsDevice.size()];
       for (int i = 0; i < tsDevice.size(); ++i)
       {
         acdManagerAddress[i] = ((ACDManagerAddress)TsapiCreateObject.getTsapiObject((TSDevice)tsDevice.elementAt(i), true));
       }
       TsapiTrace.traceExit("getACDManagerAddresses[]", this);
       return acdManagerAddress;
     }
   }
 
   public final String getVendor()
   {
     TsapiTrace.traceEntry("getVendor[]", this);
     String vendor = this.tsProvider.getVendor();
     TsapiTrace.traceExit("getVendor[]", this);
     return vendor;
   }
 
   public final byte[] getVendorVersion()
   {
     TsapiTrace.traceEntry("getVendorVersion[]", this);
     byte[] version = this.tsProvider.getVendorVersion();
     TsapiTrace.traceExit("getVendorVersion[]", this);
     return version;
   }
 
   public final void updateAddresses()
   {
     TsapiTrace.traceEntry("updateAddresses[]", this);
     this.tsProvider.updateAddresses();
     TsapiTrace.traceExit("updateAddresses[]", this);
   }
 
   public final void setDebugPrinting(boolean enable)
   {
     TsapiTrace.traceEntry("setDebugPrinting[boolean enable]", this);
     this.tsProvider.setDebugPrinting(enable);
     TsapiTrace.traceExit("setDebugPrinting[boolean enable]", this);
   }
 
   public final String getServerID()
   {
     TsapiTrace.traceEntry("getServerID[]", this);
     String id = this.tsProvider.getServerID();
     TsapiTrace.traceExit("getServerID[]", this);
     return id;
   }
 
   public final void setHeartbeatInterval(short heartbeatInterval)
     throws TsapiInvalidArgumentException
   {
     TsapiTrace.traceEntry("setHeartbeatInterval[short heartbeatInterval]", this);
     this.tsProvider.setHeartbeatInterval(heartbeatInterval);
     TsapiTrace.traceExit("setHeartbeatInterval[short heartbeatInterval]", this);
   }
 
   public final Call getCall(int callID)
   {
     TsapiTrace.traceEntry("getCall[int callID]", this);
     TSCall call = this.tsProvider.createTSCall(callID);
     if (call != null)
     {
       Call callObj = (Call)TsapiCreateObject.getTsapiObject(call, false);
       TsapiTrace.traceExit("getCall[int callID]", this);
       return callObj;
     }
 
     throw new TsapiPlatformException(4, 0, "could not create call");
   }
 
   public final Address getAddress(ExtendedDeviceID tsapiDeviceID)
   {
     TsapiTrace.traceEntry("getAddress[com.avaya.jtapi.tsapi.impl.ExtendedDeviceID tsapiDeviceID]", this);
 
     CSTAExtendedDeviceID deviceID = new CSTAExtendedDeviceID(tsapiDeviceID.getDeviceID(), tsapiDeviceID.getDeviceIDType(), tsapiDeviceID.getDeviceIDStatus());
 
     TSDevice device = this.tsProvider.createDevice(deviceID);
     if (device != null)
     {
       Address addr = (Address)TsapiCreateObject.getTsapiObject(device, true);
       TsapiTrace.traceExit("getAddress[com.avaya.jtapi.tsapi.impl.ExtendedDeviceID tsapiDeviceID]", this);
       return addr;
     }
 
     throw new TsapiPlatformException(4, 0, "could not create address");
   }
 
   public final Terminal getTerminal(ExtendedDeviceID tsapiDeviceID)
   {
     TsapiTrace.traceEntry("getTerminal[com.avaya.jtapi.tsapi.impl.ExtendedDeviceID tsapiDeviceID]", this);
 
     CSTAExtendedDeviceID deviceID = new CSTAExtendedDeviceID(tsapiDeviceID.getDeviceID(), tsapiDeviceID.getDeviceIDType(), tsapiDeviceID.getDeviceIDStatus());
 
     TSDevice device = this.tsProvider.createDevice(deviceID);
     if ((device != null) && (device.isTerminal()))
     {
       Terminal term = (Terminal)TsapiCreateObject.getTsapiObject(device, false);
       TsapiTrace.traceExit("getTerminal[com.avaya.jtapi.tsapi.impl.ExtendedDeviceID tsapiDeviceID]", this);
       return term;
     }
 
     String info = "";
 
     if (device == null)
     {
       info = "; device is null";
     }
     else if (!device.isTerminal())
     {
       info = "; device is not a terminal";
     }
 
     throw new TsapiPlatformException(4, 0, "could not create terminal for deviceID " + tsapiDeviceID.getDeviceID() + info);
   }
 
   public final Connection getConnection(ConnectionID tsapiConnID, Address address)
   {
     TsapiTrace.traceEntry("getConnection[com.avaya.jtapi.tsapi.ConnectionID tsapiConnID, Address address]", this);
     CSTAConnectionID connID = new CSTAConnectionID(tsapiConnID.getCallID(), tsapiConnID.getDeviceID(), (short)tsapiConnID.getDevIDType());
 
     if (!(address instanceof ITsapiAddress)) {
       throw new TsapiPlatformException(3, 0, "The given Address is not an instanceof ITsapiAddress");
     }
 
     TSDevice tsDevice = ((TsapiAddress)address).getTSDevice();
     if (tsDevice != null)
     {
       TSConnection conn = this.tsProvider.createTSConnection(connID, tsDevice);
       if (conn != null)
       {
         Connection connObj = (Connection)TsapiCreateObject.getTsapiObject(conn, true);
         TsapiTrace.traceExit("getConnection[com.avaya.jtapi.tsapi.ConnectionID tsapiConnID, Address address]", this);
         return connObj;
       }
 
       throw new TsapiPlatformException(4, 0, "could not create connection");
     }
 
     throw new TsapiPlatformException(4, 0, "could not locate address");
   }
 
   public final TerminalConnection getTerminalConnection(ConnectionID tsapiConnID, Terminal terminal)
   {
     TsapiTrace.traceEntry("getTerminalConnection[com.avaya.jtapi.tsapi.ConnectionID tsapiConnID, Terminal terminal]", this);
     CSTAConnectionID connID = new CSTAConnectionID(tsapiConnID.getCallID(), tsapiConnID.getDeviceID(), (short)tsapiConnID.getDevIDType());
 
     if (!(terminal instanceof ITsapiTerminal)) {
       throw new TsapiPlatformException(3, 0, "The given Terminal is not an instanceof ITsapiTerminal");
     }
 
     TSDevice tsDevice = ((TsapiTerminal)terminal).getTSDevice();
     if (tsDevice != null)
     {
       TSConnection conn = this.tsProvider.createTSConnection(connID, tsDevice);
       if (conn != null)
       {
         TerminalConnection tconn = (TerminalConnection)TsapiCreateObject.getTsapiObject(conn, false);
         TsapiTrace.traceExit("getTerminalConnection[com.avaya.jtapi.tsapi.ConnectionID tsapiConnID, Terminal terminal]", this);
         return tconn;
       }
 
       throw new TsapiPlatformException(4, 0, "could not create terminal connection");
     }
 
     throw new TsapiPlatformException(4, 0, "could not locate terminal");
   }
 
   public final void setPrivateData(Object data)
   {
     TsapiTrace.traceEntry("setPrivateData[Object data]", this);
     try
     {
       CSTAPrivate privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data);
       this.tsProvider.setPrivateData(privData);
     }
     catch (ClassCastException e)
     {
       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
     }
 
     TsapiTrace.traceExit("setPrivateData[Object data]", this);
   }
 
   public final Object getPrivateData()
   {
     TsapiTrace.traceEntry("getPrivateData[]", this);
     Object data = TsapiPromoter.promoteTsapiPrivate((CSTAPrivate)this.tsProvider.getPrivateData());
     TsapiTrace.traceExit("getPrivateData[]", this);
     return data;
   }
 
   public final Object sendPrivateData(Object data)
   {
     TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
     try
     {
       Object obj = this.tsProvider.sendPrivateData(TsapiPromoter.demoteTsapiPrivate((TsapiPrivate)data));
       TsapiTrace.traceExit("sendPrivateData[Object data]", this);
       return obj;
     }
     catch (ClassCastException e)
     {
       throw new TsapiPlatformException(3, 0, "data is not a TsapiPrivate object");
     }
     catch (Exception e)
     {
       throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
     }
   }
 
   public final TrunkGroupInfo getTrunkGroupInfo(String trunkAccessCode)
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("getTrunkGroupInfo[String trunkAccessCode]", this);
     LucentTrunkGroupInfo tInfo = this.tsProvider.getTrunkGroupInfo(trunkAccessCode);
     TrunkGroupInfo tgi = new TrunkGroupInfo(tInfo.idleTrunks, tInfo.usedTrunks);
     TsapiTrace.traceExit("getTrunkGroupInfo[String trunkAccessCode]", this);
     return tgi;
   }
 
   public final CallClassifierInfo getCallClassifierInfo()
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("getCallClassifierInfo[]", this);
     CallClassifierInfo info = this.tsProvider.getCallClassifierInfo();
     TsapiTrace.traceExit("getCallClassifierInfo[]", this);
     return info;
   }
 
   public final Date getSwitchDateAndTime()
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("getSwitchDateAndTime[]", this);
     Date date = this.tsProvider.getSwitchDateAndTime();
     TsapiTrace.traceExit("getSwitchDateAndTime[]", this);
     return date;
   }
 
   public final void setAdviceOfCharge(boolean flag)
     throws TsapiMethodNotSupportedException
   {
     TsapiTrace.traceEntry("setAdviceOfCharge[boolean flag]", this);
     this.tsProvider.setAdviceOfCharge(flag);
     TsapiTrace.traceExit("setAdviceOfCharge[boolean flag]", this);
   }
 
   public final int hashCode()
   {
     return this.tsProvider.hashCode();
   }
 
   public boolean equals(Object obj)
   {
     if (obj instanceof TsapiProvider)
     {
       return this.tsProvider.equals(((TsapiProvider)obj).tsProvider);
     }
 
     return false;
   }
 
   public TsapiProvider(TSProviderImpl _tsProvider)
   {
     this.tsProvider = _tsProvider;
     this.name = _tsProvider.getName();
     TsapiTrace.traceConstruction(this, TsapiProvider.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, TsapiProvider.class);
   }
 
   final TSProviderImpl getTSProviderImpl()
   {
     TsapiTrace.traceEntry("getTSProviderImpl[]", this);
     TsapiTrace.traceExit("getTSProviderImpl[]", this);
     return this.tsProvider;
   }
 
   public void addProviderListener(ProviderListener listener)
     throws ResourceUnavailableException
   {
     TsapiTrace.traceEntry("addProviderListener[ProviderListener listener]", this);
 
     Vector tsapiProviderMonitors = this.tsProvider.getProviderMonitorThreads();
 
     TsapiProviderMonitor monitor = null;
     synchronized (tsapiProviderMonitors)
     {
       for (int i = 0; i < tsapiProviderMonitors.size(); ++i)
       {
         monitor = (TsapiProviderMonitor)tsapiProviderMonitors.elementAt(i);
         if (monitor.getMonitor() != listener)
           continue;
         this.tsProvider.addMonitor(monitor);
         TsapiTrace.traceExit("addProviderListener(ProviderListener listener)", this);
         return;
       }
 
       monitor = new TsapiProviderMonitor(this.tsProvider, listener);
     }
 
     this.tsProvider.addMonitor(monitor);
     TsapiTrace.traceExit("addProviderListener[ProviderListener listener]", this);
   }
 
   public ProviderListener[] getProviderListeners()
   {
     TsapiTrace.traceEntry("getProviderListeners[]", this);
     Vector tsapiProviderMonitors = this.tsProvider.getProviderMonitorThreads();
 
     if ((tsapiProviderMonitors == null) || (tsapiProviderMonitors.size() == 0))
     {
       TsapiTrace.traceExit("getProviderListeners[]", this);
       return null;
     }
 
     synchronized (tsapiProviderMonitors)
     {
       List listeners = new ArrayList();
 
       for (int i = 0; i < tsapiProviderMonitors.size(); ++i)
       {
         TsapiProviderMonitor monitor = (TsapiProviderMonitor)tsapiProviderMonitors.elementAt(i);
         if (monitor.getMonitor() instanceof ProviderListener) {
           listeners.add((ProviderListener)monitor.getMonitor());
         }
       }
       TsapiTrace.traceExit("getProviderListeners[]", this);
       return (ProviderListener[])listeners.toArray(new ProviderListener[0]);
     }
   }
 
   public void removeProviderListener(ProviderListener listener)
   {
     TsapiTrace.traceEntry("removeProviderListener[ProviderListener listener]", this);
     Vector tsapiProviderMonitors = this.tsProvider.getProviderMonitorThreads();
 
     if ((tsapiProviderMonitors == null) || (tsapiProviderMonitors.size() == 0))
     {
       TsapiTrace.traceExit("removeProviderListener[ProviderListener listener]", this);
       return;
     }
 
     synchronized (tsapiProviderMonitors)
     {
       for (int i = 0; i < tsapiProviderMonitors.size(); ++i)
       {
         TsapiProviderMonitor monitor = (TsapiProviderMonitor)tsapiProviderMonitors.elementAt(i);
         if (monitor.getMonitor() != listener)
           continue;
         this.tsProvider.removeMonitor(monitor);
         TsapiTrace.traceExit("removeProviderListener[ProviderListener listener]", this);
         return;
       }
     }
   }
 
   public String getAdministeredSwitchSoftwareVersion()
   {
     TsapiTrace.traceEntry("getAdministeredSwitchSoftwareVersion[]", this);
     String version = this.tsProvider.getAdministeredSwitchSoftwareVersion();
     TsapiTrace.traceExit("getAdministeredSwitchSoftwareVersion[]", this);
     return version;
   }
 
   public String getSwitchSoftwareVersion() {
     TsapiTrace.traceEntry("getSwitchSoftwareVersion[]", this);
     String version = this.tsProvider.getSwitchSoftwareVersion();
     TsapiTrace.traceExit("getSwitchSoftwareVersion[]", this);
     return version;
   }
 
   public String getOfferType() {
     TsapiTrace.traceEntry("getOfferType[]", this);
     String type = this.tsProvider.getOfferType();
     TsapiTrace.traceExit("getOfferType[]", this);
     return type;
   }
 
   public String getServerType() {
     TsapiTrace.traceEntry("getServerType[]", this);
     String type = this.tsProvider.getServerType();
     TsapiTrace.traceExit("getServerType[]", this);
     return type;
   }
 
   public final String requestPrivileges()
     throws TsapiInvalidArgumentException
   {
     TsapiTrace.traceEntry("requestPrivileges[]", this);
     String privs = this.tsProvider.requestPrivileges();
     TsapiTrace.traceExit("requestPrivileges[]", this);
     return privs;
   }
 
   public final void setPrivileges(String xmlData)
     throws TsapiInvalidArgumentException
   {
     TsapiTrace.traceEntry("setPrivileges[String xmlData]", this);
     this.tsProvider.setPrivileges(xmlData);
     TsapiTrace.traceExit("setPrivileges[String xmlData]", this);
   }
 
   public void setSessionTimeout(int timeout) {
     TsapiTrace.traceEntry("setSessionTimeout[int timeout]", this);
     this.tsProvider.setSessionTimeout(timeout);
     TsapiTrace.traceExit("setSessionTimeout[int timeout]", this);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiProvider
 * JD-Core Version:    0.5.4
 */