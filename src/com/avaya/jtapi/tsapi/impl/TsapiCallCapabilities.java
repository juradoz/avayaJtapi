/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import javax.telephony.Call;
/*     */ import javax.telephony.TerminalConnection;
/*     */ import javax.telephony.callcenter.capabilities.CallCenterCallCapabilities;
/*     */ import javax.telephony.callcontrol.capabilities.CallControlCallCapabilities;
/*     */ import javax.telephony.capabilities.CallCapabilities;
/*     */ 
/*     */ public final class TsapiCallCapabilities
/*     */   implements CallCapabilities, CallCenterCallCapabilities, CallControlCallCapabilities
/*     */ {
/*  18 */   private TSCapabilities tsCaps = null;
/*     */ 
/*     */   public boolean isObservable()
/*     */   {
/*  24 */     TsapiTrace.traceEntry("isObservable[]", this);
/*  25 */     boolean can = this.tsCaps.getMonitorCall() == 1;
/*  26 */     TsapiTrace.traceExit("isObservable[]", this);
/*  27 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canConnect()
/*     */   {
/*  32 */     TsapiTrace.traceEntry("canConnect[]", this);
/*  33 */     boolean can = this.tsCaps.getMakeCall() == 1;
/*  34 */     TsapiTrace.traceExit("canConnect[]", this);
/*  35 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canConnectPredictive()
/*     */   {
/*  42 */     TsapiTrace.traceEntry("canConnectPredictive[]", this);
/*  43 */     boolean can = this.tsCaps.getMakePredictiveCall() == 1;
/*  44 */     TsapiTrace.traceExit("canConnectPredictive[]", this);
/*  45 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canGetTrunks()
/*     */   {
/*  50 */     TsapiTrace.traceEntry("canGetTrunks[]", this);
/*  51 */     boolean can = this.tsCaps.isLucent();
/*  52 */     TsapiTrace.traceExit("canGetTrunks[]", this);
/*  53 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canHandleApplicationData()
/*     */   {
/*  58 */     TsapiTrace.traceEntry("canHandleApplicationData[]", this);
/*  59 */     TsapiTrace.traceExit("canHandleApplicationData[]", this);
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canDrop()
/*     */   {
/*  66 */     TsapiTrace.traceEntry("canDrop[]", this);
/*  67 */     boolean can = this.tsCaps.getClearCall() == 1;
/*  68 */     TsapiTrace.traceExit("canDrop[]", this);
/*  69 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canOffHook()
/*     */   {
/*  74 */     TsapiTrace.traceEntry("canOffHook[]", this);
/*  75 */     TsapiTrace.traceExit("canOffHook[]", this);
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canSetConferenceController()
/*     */   {
/*  81 */     TsapiTrace.traceEntry("canSetConferenceController[]", this);
/*  82 */     TsapiTrace.traceExit("canSetConferenceController[]", this);
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean canSetTransferController()
/*     */   {
/*  88 */     TsapiTrace.traceEntry("canSetTransferController[]", this);
/*  89 */     TsapiTrace.traceExit("canSetTransferController[]", this);
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean canSetTransferEnable()
/*     */   {
/*  95 */     TsapiTrace.traceEntry("canSetTransferEnable[]", this);
/*  96 */     TsapiTrace.traceExit("canSetTransferEnable[]", this);
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean canSetConferenceEnable()
/*     */   {
/* 102 */     TsapiTrace.traceEntry("canSetConferenceEnable[]", this);
/* 103 */     TsapiTrace.traceExit("canSetConferenceEnable[]", this);
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean canTransfer()
/*     */   {
/* 110 */     TsapiTrace.traceEntry("canTransfer[]", this);
/* 111 */     boolean can = canTransfer((Call)null);
/* 112 */     TsapiTrace.traceExit("canTransfer[]", this);
/* 113 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canTransfer(Call call)
/*     */   {
/* 118 */     TsapiTrace.traceEntry("canTransfer[Call call]", this);
/* 119 */     boolean can = this.tsCaps.getTransferCall() == 1;
/* 120 */     TsapiTrace.traceExit("canTransfer[Call call]", this);
/* 121 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canTransfer(String destination)
/*     */   {
/* 126 */     TsapiTrace.traceEntry("canTransfer[String destination]", this);
/* 127 */     if (this.tsCaps.isLucent())
/*     */     {
/* 129 */       boolean isV8 = this.tsCaps.isLucentV8();
/* 130 */       TsapiTrace.traceExit("canTransfer[String destination]", this);
/*     */ 
/* 132 */       return isV8;
/*     */     }
/*     */ 
/* 139 */     boolean can = this.tsCaps.getTransferCall() == 1;
/* 140 */     TsapiTrace.traceExit("canTransfer[String destination]", this);
/* 141 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canConference()
/*     */   {
/* 147 */     TsapiTrace.traceEntry("canConference[]", this);
/* 148 */     boolean can = this.tsCaps.getConferenceCall() == 1;
/* 149 */     TsapiTrace.traceExit("canConference[]", this);
/* 150 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canAddParty()
/*     */   {
/* 155 */     TsapiTrace.traceEntry("canAddParty[]", this);
/* 156 */     boolean can = this.tsCaps.getAddParty() == 1;
/* 157 */     TsapiTrace.traceExit("canAddParty[]", this);
/* 158 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canConsult()
/*     */   {
/* 164 */     TsapiTrace.traceEntry("canConsult[]", this);
/* 165 */     boolean can = canConsult((TerminalConnection)null, (String)null);
/* 166 */     TsapiTrace.traceExit("canConsult[]", this);
/* 167 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canConsult(TerminalConnection tc, String destination)
/*     */   {
/* 172 */     TsapiTrace.traceEntry("canConsult[TerminalConnection tc, String destination]", this);
/* 173 */     boolean can = this.tsCaps.getConsultationCall() == 1;
/* 174 */     TsapiTrace.traceExit("canConsult[TerminalConnection tc, String destination]", this);
/* 175 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canConsult(TerminalConnection tc)
/*     */   {
/* 180 */     TsapiTrace.traceEntry("canConsult[TerminalConnection tc]", this);
/* 181 */     TsapiTrace.traceExit("canConsult[TerminalConnection tc]", this);
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */   public TsapiCallCapabilities(TSCapabilities _tsCaps)
/*     */   {
/* 188 */     this.tsCaps = _tsCaps;
/* 189 */     TsapiTrace.traceConstruction(this, TsapiCallCapabilities.class);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 195 */     super.finalize();
/* 196 */     TsapiTrace.traceDestruction(this, TsapiCallCapabilities.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiCallCapabilities
 * JD-Core Version:    0.5.4
 */