 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import javax.telephony.callcenter.capabilities.ACDAddressCapabilities;
 import javax.telephony.callcenter.capabilities.ACDManagerAddressCapabilities;
 import javax.telephony.callcenter.capabilities.CallCenterAddressCapabilities;
 import javax.telephony.callcenter.capabilities.RouteAddressCapabilities;
 import javax.telephony.callcontrol.capabilities.CallControlAddressCapabilities;
 import javax.telephony.capabilities.AddressCapabilities;
 
 public final class TsapiAddressCapabilities
   implements AddressCapabilities, CallControlAddressCapabilities, CallCenterAddressCapabilities, RouteAddressCapabilities, ACDAddressCapabilities, ACDManagerAddressCapabilities
 {
   TSCapabilities tsCaps = null;
 
   public TsapiAddressCapabilities(TSCapabilities _tsCaps)
   {
     this.tsCaps = _tsCaps;
     TsapiTrace.traceConstruction(this, TsapiAddressCapabilities.class);
   }
 
   public boolean isObservable()
   {
     TsapiTrace.traceEntry("isObservable[]", this);
     boolean is = this.tsCaps.getMonitorDevice() == 1;
     TsapiTrace.traceExit("isObservable[]", this);
     return is;
   }
 
   public boolean canSetForwarding()
   {
     TsapiTrace.traceEntry("canSetForwarding[]", this);
     boolean can = this.tsCaps.getSetFwd() == 1;
     TsapiTrace.traceExit("canSetForwarding[]", this);
     return can;
   }
 
   public boolean canGetForwarding()
   {
     TsapiTrace.traceEntry("canGetForwarding[]", this);
     boolean can = this.tsCaps.getQueryFwd() == 1;
     TsapiTrace.traceExit("canGetForwarding[]", this);
     return can;
   }
 
   public boolean canCancelForwarding()
   {
     TsapiTrace.traceEntry("canCancelForwarding[]", this);
     boolean can = this.tsCaps.getSetFwd() == 1;
     TsapiTrace.traceExit("canCancelForwarding[]", this);
     return can;
   }
 
   public boolean canGetDoNotDisturb()
   {
     TsapiTrace.traceEntry("canGetDoNotDisturb[]", this);
     boolean can = (this.tsCaps.getDoNotDisturbEvent() == 1) || (this.tsCaps.getQueryDnd() == 1);
     TsapiTrace.traceExit("canGetDoNotDisturb[]", this);
     return can;
   }
 
   public boolean canSetDoNotDisturb()
   {
     TsapiTrace.traceEntry("canSetDoNotDisturb[]", this);
     boolean can = this.tsCaps.getSetDnd() == 1;
     TsapiTrace.traceExit("canSetDoNotDisturb[]", this);
     return can;
   }
 
   public boolean canGetMessageWaiting()
   {
     TsapiTrace.traceEntry("canGetMessageWaiting[]", this);
     boolean can = (this.tsCaps.getMessageWaitingEvent() == 1) || (this.tsCaps.getQueryMwi() == 1);
     TsapiTrace.traceExit("canGetMessageWaiting[]", this);
     return can;
   }
 
   public boolean canSetMessageWaiting()
   {
     TsapiTrace.traceEntry("canSetMessageWaiting[]", this);
     boolean can = this.tsCaps.getSetMwi() == 1;
     TsapiTrace.traceExit("canSetMessageWaiting[]", this);
     return can;
   }
 
   public boolean canAddCallObserver(boolean remain)
   {
     TsapiTrace.traceEntry("canAddCallObserver[boolean remain]", this);
     boolean can = this.tsCaps.getMonitorCallsViaDevice() == 1;
     TsapiTrace.traceExit("canAddCallObserver[boolean remain]", this);
     return can;
   }
 
   public boolean canRouteCalls()
   {
     TsapiTrace.traceEntry("canRouteCalls[]", this);
     boolean can = this.tsCaps.getRouteRequestEvent() == 1;
 
     TsapiTrace.traceExit("canRouteCalls[]", this);
     return can;
   }
 
   public boolean canGetLoggedOnAgents()
   {
     TsapiTrace.traceEntry("canGetLoggedOnAgents[]", this);
     TsapiTrace.traceExit("canGetLoggedOnAgents[]", this);
     return true;
   }
 
   public boolean canGetNumberQueued()
   {
     TsapiTrace.traceEntry("canGetNumberQueued[]", this);
     boolean can = (this.tsCaps.isLucent()) || (this.tsCaps.getQueuedEvent() == 1);
     TsapiTrace.traceExit("canGetNumberQueued[]", this);
     return can;
   }
 
   public boolean canGetOldestCallQueued()
   {
     TsapiTrace.traceEntry("canGetOldestCallQueued[]", this);
     TsapiTrace.traceExit("canGetOldestCallQueued[]", this);
     return false;
   }
 
   public boolean canGetRelativeQueueLoad()
   {
     TsapiTrace.traceEntry("canGetRelativeQueueLoad[]", this);
     TsapiTrace.traceExit("canGetRelativeQueueLoad[]", this);
     return false;
   }
 
   public boolean canGetQueueWaitTime()
   {
     TsapiTrace.traceEntry("canGetQueueWaitTime[]", this);
     TsapiTrace.traceExit("canGetQueueWaitTime[]", this);
     return false;
   }
 
   public boolean canGetACDManagerAddress()
   {
     TsapiTrace.traceEntry("canGetACDManagerAddress[]", this);
     TsapiTrace.traceExit("canGetACDManagerAddress[]", this);
     return false;
   }
 
   public boolean canGetACDAddresses()
   {
     TsapiTrace.traceEntry("canGetACDAddresses[]", this);
     TsapiTrace.traceExit("canGetACDAddresses[]", this);
     return false;
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, TsapiAddressCapabilities.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiAddressCapabilities
 * JD-Core Version:    0.5.4
 */