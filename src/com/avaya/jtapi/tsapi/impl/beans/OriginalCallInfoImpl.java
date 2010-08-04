/*     */ package com.avaya.jtapi.tsapi.impl.beans;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiAddress;
/*     */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*     */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*     */ import com.avaya.jtapi.tsapi.TsapiTrunk;
/*     */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*     */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*     */ 
/*     */ public class OriginalCallInfoImpl
/*     */   implements OriginalCallInfo
/*     */ {
/*     */   private short reason;
/*     */   private LookaheadInfo lookaheadInfo;
/*     */   private UserEnteredCode userEnteredCode;
/*     */   private UserToUserInfo userInfo;
/*     */   private ITsapiAddress callingDevice;
/*     */   private ITsapiAddress calledDevice;
/*     */   private TsapiTrunk trunk;
/*     */ 
/*     */   public short getReason()
/*     */   {
/*  37 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public ITsapiAddress getCallingDevice()
/*     */   {
/*  45 */     return this.callingDevice;
/*     */   }
/*     */ 
/*     */   public ITsapiAddress getCalledDevice()
/*     */   {
/*  53 */     return this.calledDevice;
/*     */   }
/*     */ 
/*     */   public TsapiTrunk getTrunk()
/*     */   {
/*  61 */     return this.trunk;
/*     */   }
/*     */ 
/*     */   public UserToUserInfo getUserToUserInfo()
/*     */   {
/*  69 */     return this.userInfo;
/*     */   }
/*     */ 
/*     */   public LookaheadInfo getLookaheadInfo()
/*     */   {
/*  77 */     return this.lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public UserEnteredCode getUserEnteredCode()
/*     */   {
/*  85 */     return this.userEnteredCode;
/*     */   }
/*     */ 
/*     */   public void setReason(short _reason)
/*     */   {
/*  90 */     this.reason = _reason;
/*     */   }
/*     */ 
/*     */   public void setLookaheadInfo(LookaheadInfo _lookaheadInfo) {
/*  94 */     this.lookaheadInfo = _lookaheadInfo;
/*     */   }
/*     */ 
/*     */   public void setUserEnteredCode(UserEnteredCode _userEnteredCode) {
/*  98 */     this.userEnteredCode = _userEnteredCode;
/*     */   }
/*     */ 
/*     */   public void setUserInfo(UserToUserInfo _userInfo) {
/* 102 */     this.userInfo = _userInfo;
/*     */   }
/*     */ 
/*     */   public void setCallingDevice(ITsapiAddress _dev) {
/* 106 */     this.callingDevice = _dev;
/*     */   }
/*     */ 
/*     */   public void setCalledDevice(ITsapiAddress _dev) {
/* 110 */     this.calledDevice = _dev;
/*     */   }
/*     */ 
/*     */   public void setTrunk(TsapiTrunk _trunk) {
/* 114 */     this.trunk = _trunk;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.beans.OriginalCallInfoImpl
 * JD-Core Version:    0.5.4
 */