/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import javax.telephony.callcontrol.capabilities.CallControlTerminalConnectionCapabilities;
import javax.telephony.capabilities.TerminalConnectionCapabilities;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ 
/*     */ public final class TsapiTermConnCapabilities
/*     */   implements TerminalConnectionCapabilities, CallControlTerminalConnectionCapabilities
/*     */ {
/*  18 */   private TSCapabilities tsCaps = null;
/*     */ 
/*     */   public boolean canAnswer()
/*     */   {
/*  24 */     TsapiTrace.traceEntry("canAnswer[]", this);
/*  25 */     boolean can = this.tsCaps.getAnswerCall() == 1;
/*  26 */     TsapiTrace.traceExit("canAnswer[]", this);
/*  27 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canHold()
/*     */   {
/*  33 */     TsapiTrace.traceEntry("canHold[]", this);
/*  34 */     boolean can = this.tsCaps.getHoldCall() == 1;
/*  35 */     TsapiTrace.traceExit("canHold[]", this);
/*  36 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canUnhold()
/*     */   {
/*  41 */     TsapiTrace.traceEntry("canUnhold[]", this);
/*  42 */     boolean can = this.tsCaps.getRetrieveCall() == 1;
/*  43 */     TsapiTrace.traceExit("canUnhold[]", this);
/*  44 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canJoin()
/*     */   {
/*  49 */     TsapiTrace.traceEntry("canJoin[]", this);
/*  50 */     boolean can = this.tsCaps.isLucent();
/*  51 */     TsapiTrace.traceExit("canJoin[]", this);
/*  52 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canLeave()
/*     */   {
/*  57 */     TsapiTrace.traceEntry("canLeave[]", this);
/*  58 */     boolean can = this.tsCaps.isLucent();
/*  59 */     TsapiTrace.traceExit("canLeave[]", this);
/*  60 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canUseDefaultSpeaker()
/*     */   {
/*  67 */     TsapiTrace.traceEntry("canUseDefaultSpeaker[]", this);
/*  68 */     TsapiTrace.traceExit("canUseDefaultSpeaker[]", this);
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canUseDefaultMicrophone()
/*     */   {
/*  74 */     TsapiTrace.traceEntry("canUseDefaultMicrophone[]", this);
/*  75 */     TsapiTrace.traceExit("canUseDefaultMicrophone[]", this);
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canUseRecordURL()
/*     */   {
/*  81 */     TsapiTrace.traceEntry("canUseRecordURL[]", this);
/*  82 */     TsapiTrace.traceExit("canUseRecordURL[]", this);
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canUsePlayURL()
/*     */   {
/*  88 */     TsapiTrace.traceEntry("canUsePlayURL[]", this);
/*  89 */     TsapiTrace.traceExit("canUsePlayURL[]", this);
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canStartPlaying()
/*     */   {
/*  95 */     TsapiTrace.traceEntry("canStartPlaying[]", this);
/*  96 */     TsapiTrace.traceExit("canStartPlaying[]", this);
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canStopPlaying()
/*     */   {
/* 102 */     TsapiTrace.traceEntry("canStopPlaying[]", this);
/* 103 */     TsapiTrace.traceExit("canStopPlaying[]", this);
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canStartRecording()
/*     */   {
/* 109 */     TsapiTrace.traceEntry("canStartRecording[]", this);
/* 110 */     TsapiTrace.traceExit("canStartRecording[]", this);
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canStopRecording()
/*     */   {
/* 116 */     TsapiTrace.traceEntry("canStopRecording[]", this);
/* 117 */     TsapiTrace.traceExit("canStopRecording[]", this);
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canDetectDtmf()
/*     */   {
/* 123 */     TsapiTrace.traceEntry("canDetectDtmf[]", this);
/* 124 */     TsapiTrace.traceExit("canDetectDtmf[]", this);
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canGenerateDtmf()
/*     */   {
/* 130 */     TsapiTrace.traceEntry("canGenerateDtmf[]", this);
/* 131 */     boolean can = this.tsCaps.isLucent();
/* 132 */     TsapiTrace.traceExit("canGenerateDtmf[]", this);
/* 133 */     return can;
/*     */   }
/*     */ 
/*     */   public TsapiTermConnCapabilities(TSCapabilities _tsCaps)
/*     */   {
/* 139 */     this.tsCaps = _tsCaps;
/* 140 */     TsapiTrace.traceConstruction(this, TsapiTermConnCapabilities.class);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 146 */     super.finalize();
/* 147 */     TsapiTrace.traceDestruction(this, TsapiTermConnCapabilities.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiTermConnCapabilities
 * JD-Core Version:    0.5.4
 */