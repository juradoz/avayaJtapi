/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import javax.telephony.Address;
/*     */ import javax.telephony.Connection;
/*     */ import javax.telephony.TerminalConnection;
/*     */ import javax.telephony.callcenter.capabilities.AgentTerminalCapabilities;
/*     */ import javax.telephony.callcontrol.capabilities.CallControlTerminalCapabilities;
/*     */ import javax.telephony.capabilities.TerminalCapabilities;
/*     */ 
/*     */ public final class TsapiTerminalCapabilities
/*     */   implements TerminalCapabilities, CallControlTerminalCapabilities, AgentTerminalCapabilities
/*     */ {
/*  22 */   private TSCapabilities tsCaps = null;
/*     */ 
/*     */   public boolean isObservable()
/*     */   {
/*  28 */     TsapiTrace.traceEntry("isObservable[]", this);
/*  29 */     boolean can = this.tsCaps.getMonitorDevice() == 1;
/*  30 */     TsapiTrace.traceExit("isObservable[]", this);
/*  31 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canGetDoNotDisturb()
/*     */   {
/*  37 */     TsapiTrace.traceEntry("canGetDoNotDisturb[]", this);
/*  38 */     boolean can = (this.tsCaps.getDoNotDisturbEvent() == 1) || (this.tsCaps.getQueryDnd() == 1);
/*  39 */     TsapiTrace.traceExit("canGetDoNotDisturb[]", this);
/*  40 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canSetDoNotDisturb()
/*     */   {
/*  45 */     TsapiTrace.traceEntry("canSetDoNotDisturb[]", this);
/*  46 */     boolean can = this.tsCaps.getSetDnd() == 1;
/*  47 */     TsapiTrace.traceExit("canSetDoNotDisturb[]", this);
/*  48 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canPickup()
/*     */   {
/*  54 */     TsapiTrace.traceEntry("canPickup[]", this);
/*  55 */     boolean can = this.tsCaps.getPickupCall() == 1;
/*  56 */     TsapiTrace.traceExit("canPickup[]", this);
/*  57 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canPickup(Connection connection, Address address)
/*     */   {
/*  62 */     TsapiTrace.traceEntry("canPickup[Connection connection, Address address]", this);
/*  63 */     boolean can = this.tsCaps.getPickupCall() == 1;
/*  64 */     TsapiTrace.traceExit("canPickup[Connection connection, Address address]", this);
/*  65 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canPickup(TerminalConnection tc, Address address)
/*     */   {
/*  70 */     TsapiTrace.traceEntry("canPickup[TerminalConnection tc, Address address]", this);
/*  71 */     boolean can = this.tsCaps.getPickupCall() == 1;
/*  72 */     TsapiTrace.traceExit("canPickup[TerminalConnection tc, Address address]", this);
/*  73 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canPickup(Address address1, Address address2)
/*     */   {
/*  78 */     TsapiTrace.traceEntry("canPickup[Address address1, Address address2]", this);
/*  79 */     boolean can = this.tsCaps.getPickupCall() == 1;
/*  80 */     TsapiTrace.traceExit("canPickup[Address address1, Address address2]", this);
/*  81 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canPickupFromGroup()
/*     */   {
/*  87 */     TsapiTrace.traceEntry("canPickupFromGroup[]", this);
/*  88 */     boolean can = canPickupFromGroup((Address)null);
/*  89 */     TsapiTrace.traceExit("canPickupFromGroup[]", this);
/*  90 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canPickupFromGroup(String group, Address address)
/*     */   {
/*  95 */     TsapiTrace.traceEntry("canPickupFromGroup[String group, Address address]", this);
/*  96 */     TsapiTrace.traceExit("canPickupFromGroup[String group, Address address]", this);
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canPickupFromGroup(Address address)
/*     */   {
/* 102 */     TsapiTrace.traceEntry("canPickupFromGroup[Address address]", this);
/* 103 */     boolean can = this.tsCaps.getGroupPickupCall() == 1;
/* 104 */     TsapiTrace.traceExit("canPickupFromGroup[Address address]", this);
/* 105 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canHandleAgents()
/*     */   {
/* 112 */     TsapiTrace.traceEntry("canHandleAgents[]", this);
/* 113 */     boolean can = (this.tsCaps.getSetAgentState() == 1) && (this.tsCaps.getQueryAgentState() == 1);
/* 114 */     TsapiTrace.traceExit("canHandleAgents[]", this);
/* 115 */     return can;
/*     */   }
/*     */ 
/*     */   public TsapiTerminalCapabilities(TSCapabilities _tsCaps)
/*     */   {
/* 121 */     this.tsCaps = _tsCaps;
/* 122 */     TsapiTrace.traceConstruction(this, TsapiTerminalCapabilities.class);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 128 */     super.finalize();
/* 129 */     TsapiTrace.traceDestruction(this, TsapiTerminalCapabilities.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiTerminalCapabilities
 * JD-Core Version:    0.5.4
 */