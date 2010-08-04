/*     */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.LucentCall;
/*     */ import com.avaya.jtapi.tsapi.LucentCallInfo;
/*     */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*     */ import javax.telephony.MetaEvent;
/*     */ import javax.telephony.callcenter.ACDAddress;
/*     */ import javax.telephony.callcenter.CallCenterAddress;
/*     */ import javax.telephony.callcenter.CallCenterTrunk;
/*     */ 
/*     */ public class LucentCallControlConnectionNetworkReachedEvent extends CallControlConnectionNetworkReachedEventImpl
/*     */   implements LucentCallInfo
/*     */ {
/*     */   public LucentCallControlConnectionNetworkReachedEvent(CallEventParams params, MetaEvent event, int eventId, int numInQueue, String digits)
/*     */   {
/*  29 */     super(params, event, eventId, numInQueue, digits);
/*     */   }
/*     */ 
/*     */   public ACDAddress getDeliveringACDAddress()
/*     */   {
/*  36 */     ACDAddress acdAddr = null;
/*  37 */     if (this.callEventParams.getSplit() != null) {
/*  38 */       acdAddr = this.callEventParams.getSplit();
/*     */     }
/*  40 */     else if (getCall() instanceof LucentCall) {
/*  41 */       acdAddr = ((LucentCall)getCall()).getDeliveringACDAddress();
/*     */     }
/*  43 */     return acdAddr;
/*     */   }
/*     */ 
/*     */   public CallCenterAddress getDistributingAddress()
/*     */   {
/*  50 */     CallCenterAddress cca = null;
/*  51 */     if (this.callEventParams.getDistributingDevice() != null) {
/*  52 */       cca = this.callEventParams.getDistributingDevice();
/*     */     }
/*  54 */     else if (getCall() instanceof LucentCall) {
/*  55 */       cca = ((LucentCall)getCall()).getDistributingAddress();
/*     */     }
/*  57 */     return cca;
/*     */   }
/*     */ 
/*     */   public LookaheadInfo getLookaheadInfo()
/*     */   {
/*  64 */     LookaheadInfo lai = null;
/*  65 */     if (this.callEventParams.getLookaheadInfo() != null) {
/*  66 */       lai = this.callEventParams.getLookaheadInfo();
/*     */     }
/*  68 */     else if (getCall() instanceof LucentCall) {
/*  69 */       lai = ((LucentCall)getCall()).getLookaheadInfo();
/*     */     }
/*  71 */     return lai;
/*     */   }
/*     */ 
/*     */   public OriginalCallInfo getOriginalCallInfo()
/*     */   {
/*  78 */     OriginalCallInfo oci = null;
/*  79 */     if (this.callEventParams.getOriginalCallInfo() != null) {
/*  80 */       oci = this.callEventParams.getOriginalCallInfo();
/*     */     }
/*  82 */     else if (getCall() instanceof LucentCall) {
/*  83 */       oci = ((LucentCall)getCall()).getOriginalCallInfo();
/*     */     }
/*  85 */     return oci;
/*     */   }
/*     */ 
/*     */   public short getReason()
/*     */   {
/*  92 */     return this.callEventParams.getReason();
/*     */   }
/*     */ 
/*     */   public CallCenterTrunk getTrunk()
/*     */   {
/*  99 */     CallCenterTrunk trunk = null;
/* 100 */     if (this.callEventParams.getTrunk() != null) {
/* 101 */       trunk = this.callEventParams.getTrunk();
/*     */     }
/* 103 */     else if (getCall() instanceof LucentCall) {
/* 104 */       trunk = ((LucentCall)getCall()).getTrunk();
/*     */     }
/* 106 */     return trunk;
/*     */   }
/*     */ 
/*     */   public UserEnteredCode getUserEnteredCode()
/*     */   {
/* 113 */     UserEnteredCode uec = null;
/* 114 */     if (this.callEventParams.getUserEnteredCode() != null) {
/* 115 */       uec = this.callEventParams.getUserEnteredCode();
/*     */     }
/* 117 */     else if (getCall() instanceof LucentCall) {
/* 118 */       uec = ((LucentCall)getCall()).getUserEnteredCode();
/*     */     }
/* 120 */     return uec;
/*     */   }
/*     */ 
/*     */   public UserToUserInfo getUserToUserInfo()
/*     */   {
/* 127 */     UserToUserInfo uui = null;
/* 128 */     if (this.callEventParams.getUserToUserInfo() != null) {
/* 129 */       uui = this.callEventParams.getUserToUserInfo();
/*     */     }
/* 131 */     else if (getCall() instanceof LucentCall) {
/* 132 */       uui = ((LucentCall)getCall()).getUserToUserInfo();
/*     */     }
/* 134 */     return uui;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.LucentCallControlConnectionNetworkReachedEvent
 * JD-Core Version:    0.5.4
 */