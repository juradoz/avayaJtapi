/*     */ package com.avaya.jtapi.tsapi.impl.events.call;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiCallEvent;
/*     */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
/*     */ import javax.telephony.Address;
/*     */ import javax.telephony.Call;
/*     */ import javax.telephony.Terminal;
/*     */ import javax.telephony.callcenter.ACDAddress;
/*     */ import javax.telephony.callcenter.CallCenterAddress;
/*     */ import javax.telephony.callcenter.CallCenterTrunk;
/*     */ import javax.telephony.privatedata.events.PrivateCallEv;
/*     */ 
/*     */ public abstract class TsapiCallEvent extends TsapiObserverEvent
/*     */   implements PrivateCallEv, ITsapiCallEvent
/*     */ {
/*  39 */   protected CallEventParams params = null;
/*     */ 
/*     */   public final Call getCall()
/*     */   {
/*  44 */     return this.params.getCall();
/*     */   }
/*     */ 
/*     */   public final Address getCallingAddress()
/*     */   {
/*  53 */     return this.params.getCallingAddress();
/*     */   }
/*     */ 
/*     */   public final Terminal getCallingTerminal()
/*     */   {
/*  59 */     return this.params.getCallingTerminal();
/*     */   }
/*     */ 
/*     */   public final Address getCalledAddress()
/*     */   {
/*  65 */     return this.params.getCalledAddress();
/*     */   }
/*     */ 
/*     */   public final Address getLastRedirectedAddress()
/*     */   {
/*  71 */     return this.params.getLastRedirectionAddress();
/*     */   }
/*     */ 
/*     */   public CallCenterTrunk[] getTrunks()
/*     */   {
/*  77 */     return this.params.getTrunks();
/*     */   }
/*     */ 
/*     */   public final UserToUserInfo getUserToUserInfo()
/*     */   {
/*  83 */     return this.params.getUserToUserInfo();
/*     */   }
/*     */ 
/*     */   public final LookaheadInfo getLookaheadInfo()
/*     */   {
/*  89 */     return this.params.getLookaheadInfo();
/*     */   }
/*     */ 
/*     */   public final UserEnteredCode getUserEnteredCode()
/*     */   {
/*  95 */     return this.params.getUserEnteredCode();
/*     */   }
/*     */ 
/*     */   public final OriginalCallInfo getOriginalCallInfo()
/*     */   {
/* 101 */     return this.params.getOriginalCallInfo();
/*     */   }
/*     */ 
/*     */   public final String getUCID()
/*     */   {
/* 107 */     return this.params.getUcid();
/*     */   }
/*     */ 
/*     */   public final int getCallOriginatorType()
/*     */   {
/* 113 */     return this.params.getCallOriginatorType();
/*     */   }
/*     */ 
/*     */   public final boolean hasCallOriginatorType()
/*     */   {
/* 119 */     return this.params.hasCallOriginatorType();
/*     */   }
/*     */ 
/*     */   public final boolean canSetBillRate()
/*     */   {
/* 125 */     return this.params.isFlexibleBilling();
/*     */   }
/*     */ 
/*     */   public final CallCenterAddress getDistributingAddress()
/*     */   {
/* 131 */     return this.params.getDistributingDevice();
/*     */   }
/*     */ 
/*     */   public final ACDAddress getDeliveringACDAddress()
/*     */   {
/* 137 */     return this.params.getSplit();
/*     */   }
/*     */ 
/*     */   public CallCenterTrunk getTrunk()
/*     */   {
/* 143 */     return this.params.getTrunk();
/*     */   }
/*     */ 
/*     */   public final short getReason()
/*     */   {
/* 149 */     return this.params.getReason();
/*     */   }
/*     */ 
/*     */   public short getCSTACause()
/*     */   {
/* 154 */     return this.params.getCstaCause();
/*     */   }
/*     */ 
/*     */   public TsapiCallEvent(CallEventParams params, int _eventPackage)
/*     */   {
/* 159 */     super(params.getCause(), params.getMetaCode(), params.getPrivateData(), _eventPackage);
/* 160 */     this.params = params;
/*     */   }
/*     */ 
/*     */   public TsapiCallEvent(CallEventParams params)
/*     */   {
/* 165 */     this(params, 0);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent
 * JD-Core Version:    0.5.4
 */