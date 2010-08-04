/*     */ package com.avaya.jtapi.tsapi.impl.events.call;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiAddress;
/*     */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ import javax.telephony.callcenter.ACDAddress;
/*     */ import javax.telephony.callcenter.CallCenterTrunk;
/*     */ 
/*     */ public class PrivateDataParams
/*     */ {
/*     */   private CallCenterTrunk trunk;
/*     */   private short reason;
/*     */   private UserToUserInfo userToUserInfo;
/*     */   private LookaheadInfo lookaheadInfo;
/*     */   private UserEnteredCode userEnteredCode;
/*     */   private OriginalCallInfo originalCallInfo;
/*     */   private String ucid;
/*     */   private int callOriginatorType;
/*     */   private boolean hasCallOriginatorType;
/*     */   private ACDAddress split;
/*     */   private ITsapiAddress distributingDevice;
/*     */   private boolean flexibleBilling;
/*     */ 
/*     */   public PrivateDataParams()
/*     */   {
/*  21 */     this.trunk = null;
/*  22 */     this.reason = 0;
/*  23 */     this.userToUserInfo = null;
/*  24 */     this.lookaheadInfo = null;
/*  25 */     this.userEnteredCode = null;
/*  26 */     this.originalCallInfo = null;
/*  27 */     this.ucid = null;
/*  28 */     this.callOriginatorType = -1;
/*  29 */     this.hasCallOriginatorType = false;
/*  30 */     this.split = null;
/*  31 */     this.distributingDevice = null;
/*  32 */     this.flexibleBilling = false;
/*     */   }
/*     */   public CallCenterTrunk getTrunk() {
/*  35 */     return this.trunk;
/*     */   }
/*     */ 
/*     */   public void setTrunk(CallCenterTrunk trunk) {
/*  39 */     this.trunk = trunk;
/*     */   }
/*     */ 
/*     */   public short getReason() {
/*  43 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public void setReason(short reason) {
/*  47 */     this.reason = reason;
/*     */   }
/*     */ 
/*     */   public int getCallOriginatorType() {
/*  51 */     return this.callOriginatorType;
/*     */   }
/*     */ 
/*     */   public void setCallOriginatorType(int callOriginatorType) {
/*  55 */     this.callOriginatorType = callOriginatorType;
/*     */   }
/*     */ 
/*     */   public boolean hasCallOriginatorType() {
/*  59 */     return this.hasCallOriginatorType;
/*     */   }
/*     */ 
/*     */   public void setHasCallOriginatorType(boolean hasCallOriginatorType) {
/*  63 */     this.hasCallOriginatorType = hasCallOriginatorType;
/*     */   }
/*     */ 
/*     */   public UserToUserInfo getUserToUserInfo() {
/*  67 */     return this.userToUserInfo;
/*     */   }
/*     */ 
/*     */   public void setUserToUserInfo(UserToUserInfo userToUserInfo) {
/*  71 */     this.userToUserInfo = userToUserInfo;
/*     */   }
/*     */ 
/*     */   public LookaheadInfo getLookaheadInfo() {
/*  75 */     return this.lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public void setLookaheadInfo(LookaheadInfo lookaheadInfo) {
/*  79 */     this.lookaheadInfo = lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public UserEnteredCode getUserEnteredCode() {
/*  83 */     return this.userEnteredCode;
/*     */   }
/*     */ 
/*     */   public void setUserEnteredCode(UserEnteredCode userEnteredCode) {
/*  87 */     this.userEnteredCode = userEnteredCode;
/*     */   }
/*     */ 
/*     */   public OriginalCallInfo getOriginalCallInfo() {
/*  91 */     return this.originalCallInfo;
/*     */   }
/*     */ 
/*     */   public void setOriginalCallInfo(OriginalCallInfo originalCallInfo) {
/*  95 */     this.originalCallInfo = originalCallInfo;
/*     */   }
/*     */ 
/*     */   public String getUcid() {
/*  99 */     return this.ucid;
/*     */   }
/*     */ 
/*     */   public void setUcid(String ucid) {
/* 103 */     this.ucid = ucid;
/*     */   }
/*     */ 
/*     */   public ACDAddress getSplit() {
/* 107 */     return this.split;
/*     */   }
/*     */ 
/*     */   public void setSplit(ACDAddress split) {
/* 111 */     this.split = split;
/*     */   }
/*     */ 
/*     */   public ITsapiAddress getDistributingDevice() {
/* 115 */     return this.distributingDevice;
/*     */   }
/*     */ 
/*     */   public void setDistributingDevice(ITsapiAddress distributingDevice) {
/* 119 */     this.distributingDevice = distributingDevice;
/*     */   }
/*     */ 
/*     */   public boolean isFlexibleBilling() {
/* 123 */     return this.flexibleBilling;
/*     */   }
/*     */ 
/*     */   public void setFlexibleBilling(boolean flexibleBilling) {
/* 127 */     this.flexibleBilling = flexibleBilling;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.PrivateDataParams
 * JD-Core Version:    0.5.4
 */