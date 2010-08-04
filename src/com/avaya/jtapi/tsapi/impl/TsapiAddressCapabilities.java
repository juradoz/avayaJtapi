/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import javax.telephony.callcenter.capabilities.ACDAddressCapabilities;
/*     */ import javax.telephony.callcenter.capabilities.ACDManagerAddressCapabilities;
/*     */ import javax.telephony.callcenter.capabilities.CallCenterAddressCapabilities;
/*     */ import javax.telephony.callcenter.capabilities.RouteAddressCapabilities;
/*     */ import javax.telephony.callcontrol.capabilities.CallControlAddressCapabilities;
/*     */ import javax.telephony.capabilities.AddressCapabilities;
/*     */ 
/*     */ public final class TsapiAddressCapabilities
/*     */   implements AddressCapabilities, CallControlAddressCapabilities, CallCenterAddressCapabilities, RouteAddressCapabilities, ACDAddressCapabilities, ACDManagerAddressCapabilities
/*     */ {
/*  22 */   TSCapabilities tsCaps = null;
/*     */ 
/*     */   public TsapiAddressCapabilities(TSCapabilities _tsCaps)
/*     */   {
/*  27 */     this.tsCaps = _tsCaps;
/*  28 */     TsapiTrace.traceConstruction(this, TsapiAddressCapabilities.class);
/*     */   }
/*     */ 
/*     */   public boolean isObservable()
/*     */   {
/*  35 */     TsapiTrace.traceEntry("isObservable[]", this);
/*  36 */     boolean is = this.tsCaps.getMonitorDevice() == 1;
/*  37 */     TsapiTrace.traceExit("isObservable[]", this);
/*  38 */     return is;
/*     */   }
/*     */ 
/*     */   public boolean canSetForwarding()
/*     */   {
/*  45 */     TsapiTrace.traceEntry("canSetForwarding[]", this);
/*  46 */     boolean can = this.tsCaps.getSetFwd() == 1;
/*  47 */     TsapiTrace.traceExit("canSetForwarding[]", this);
/*  48 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canGetForwarding()
/*     */   {
/*  53 */     TsapiTrace.traceEntry("canGetForwarding[]", this);
/*  54 */     boolean can = this.tsCaps.getQueryFwd() == 1;
/*  55 */     TsapiTrace.traceExit("canGetForwarding[]", this);
/*  56 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canCancelForwarding()
/*     */   {
/*  61 */     TsapiTrace.traceEntry("canCancelForwarding[]", this);
/*  62 */     boolean can = this.tsCaps.getSetFwd() == 1;
/*  63 */     TsapiTrace.traceExit("canCancelForwarding[]", this);
/*  64 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canGetDoNotDisturb()
/*     */   {
/*  69 */     TsapiTrace.traceEntry("canGetDoNotDisturb[]", this);
/*  70 */     boolean can = (this.tsCaps.getDoNotDisturbEvent() == 1) || (this.tsCaps.getQueryDnd() == 1);
/*  71 */     TsapiTrace.traceExit("canGetDoNotDisturb[]", this);
/*  72 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canSetDoNotDisturb()
/*     */   {
/*  77 */     TsapiTrace.traceEntry("canSetDoNotDisturb[]", this);
/*  78 */     boolean can = this.tsCaps.getSetDnd() == 1;
/*  79 */     TsapiTrace.traceExit("canSetDoNotDisturb[]", this);
/*  80 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canGetMessageWaiting()
/*     */   {
/*  85 */     TsapiTrace.traceEntry("canGetMessageWaiting[]", this);
/*  86 */     boolean can = (this.tsCaps.getMessageWaitingEvent() == 1) || (this.tsCaps.getQueryMwi() == 1);
/*  87 */     TsapiTrace.traceExit("canGetMessageWaiting[]", this);
/*  88 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canSetMessageWaiting()
/*     */   {
/*  93 */     TsapiTrace.traceEntry("canSetMessageWaiting[]", this);
/*  94 */     boolean can = this.tsCaps.getSetMwi() == 1;
/*  95 */     TsapiTrace.traceExit("canSetMessageWaiting[]", this);
/*  96 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canAddCallObserver(boolean remain)
/*     */   {
/* 103 */     TsapiTrace.traceEntry("canAddCallObserver[boolean remain]", this);
/* 104 */     boolean can = this.tsCaps.getMonitorCallsViaDevice() == 1;
/* 105 */     TsapiTrace.traceExit("canAddCallObserver[boolean remain]", this);
/* 106 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canRouteCalls()
/*     */   {
/* 113 */     TsapiTrace.traceEntry("canRouteCalls[]", this);
/* 114 */     boolean can = this.tsCaps.getRouteRequestEvent() == 1;
/*     */ 
/* 116 */     TsapiTrace.traceExit("canRouteCalls[]", this);
/* 117 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canGetLoggedOnAgents()
/*     */   {
/* 123 */     TsapiTrace.traceEntry("canGetLoggedOnAgents[]", this);
/* 124 */     TsapiTrace.traceExit("canGetLoggedOnAgents[]", this);
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean canGetNumberQueued()
/*     */   {
/* 130 */     TsapiTrace.traceEntry("canGetNumberQueued[]", this);
/* 131 */     boolean can = (this.tsCaps.isLucent()) || (this.tsCaps.getQueuedEvent() == 1);
/* 132 */     TsapiTrace.traceExit("canGetNumberQueued[]", this);
/* 133 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canGetOldestCallQueued()
/*     */   {
/* 138 */     TsapiTrace.traceEntry("canGetOldestCallQueued[]", this);
/* 139 */     TsapiTrace.traceExit("canGetOldestCallQueued[]", this);
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canGetRelativeQueueLoad()
/*     */   {
/* 145 */     TsapiTrace.traceEntry("canGetRelativeQueueLoad[]", this);
/* 146 */     TsapiTrace.traceExit("canGetRelativeQueueLoad[]", this);
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canGetQueueWaitTime()
/*     */   {
/* 152 */     TsapiTrace.traceEntry("canGetQueueWaitTime[]", this);
/* 153 */     TsapiTrace.traceExit("canGetQueueWaitTime[]", this);
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canGetACDManagerAddress()
/*     */   {
/* 159 */     TsapiTrace.traceEntry("canGetACDManagerAddress[]", this);
/* 160 */     TsapiTrace.traceExit("canGetACDManagerAddress[]", this);
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canGetACDAddresses()
/*     */   {
/* 168 */     TsapiTrace.traceEntry("canGetACDAddresses[]", this);
/* 169 */     TsapiTrace.traceExit("canGetACDAddresses[]", this);
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 176 */     super.finalize();
/* 177 */     TsapiTrace.traceDestruction(this, TsapiAddressCapabilities.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiAddressCapabilities
 * JD-Core Version:    0.5.4
 */