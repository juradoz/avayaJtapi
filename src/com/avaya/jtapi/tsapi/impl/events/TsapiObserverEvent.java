/*     */ package com.avaya.jtapi.tsapi.impl.events;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.ITsapiEvent;
/*     */ import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
/*     */ import com.avaya.jtapi.tsapi.TsapiPrivate;
/*     */ 
/*     */ public abstract class TsapiObserverEvent
/*     */   implements ITsapiEvent
/*     */ {
/* 130 */   int eventPackage = 0;
/* 131 */   int cause = 0;
/* 132 */   int metaCode = 0;
/* 133 */   boolean newMetaEvent = false;
/*     */ 
/* 138 */   protected Object privateData = null;
/*     */ 
/*     */   public final int getCause()
/*     */   {
/*  23 */     if ((this.cause == 101) || (this.cause == 102) || (this.cause == 103) || (this.cause == 104) || (this.cause == 105) || (this.cause == 106) || (this.cause == 107) || (this.cause == 108) || (this.cause == 109) || (this.cause == 110))
/*     */     {
/*  33 */       return this.cause;
/*     */     }
/*  35 */     return 100;
/*     */   }
/*     */ 
/*     */   public final int getMetaCode()
/*     */   {
/*  41 */     return this.metaCode;
/*     */   }
/*     */ 
/*     */   public final boolean isNewMetaEvent()
/*     */   {
/*  47 */     return this.newMetaEvent;
/*     */   }
/*     */ 
/*     */   public final Object getPrivateData()
/*     */   {
/*  55 */     if ((this.privateData instanceof TsapiPrivate) || (this.privateData instanceof LucentChargeAdviceEvent) || (this.privateData instanceof TsapiPrivateStateEvent))
/*     */     {
/*  59 */       return this.privateData;
/*     */     }
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getObserved()
/*     */   {
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   public final int getCallControlCause()
/*     */   {
/*  75 */     if ((this.cause == 101) || (this.cause == 202) || (this.cause == 203) || (this.cause == 204) || (this.cause == 205) || (this.cause == 206) || (this.cause == 207) || (this.cause == 208) || (this.cause == 209) || (this.cause == 210) || (this.cause == 211) || (this.cause == 212) || (this.cause == 213) || (this.cause == 214))
/*     */     {
/*  89 */       return this.cause;
/*     */     }
/*  91 */     return 100;
/*     */   }
/*     */ 
/*     */   public final int getCallCenterCause()
/*     */   {
/*  99 */     if ((this.cause == 101) || (this.cause == 302))
/*     */     {
/* 101 */       return this.cause;
/*     */     }
/* 103 */     return 100;
/*     */   }
/*     */ 
/*     */   public final int getMediaCause()
/*     */   {
/* 111 */     if (this.cause == 401) {
/* 112 */       return this.cause;
/*     */     }
/* 114 */     return 400;
/*     */   }
/*     */ 
/*     */   public final int getEventPackage()
/*     */   {
/* 122 */     return this.eventPackage;
/*     */   }
/*     */ 
/*     */   public final void setNewMetaEventFlag()
/*     */   {
/* 127 */     this.newMetaEvent = true;
/*     */   }
/*     */ 
/*     */   public TsapiObserverEvent(int _cause, int _metaCode, Object _privateData, int _eventPackage)
/*     */   {
/* 143 */     this.cause = _cause;
/* 144 */     this.metaCode = _metaCode;
/* 145 */     this.privateData = _privateData;
/* 146 */     this.eventPackage = _eventPackage;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent
 * JD-Core Version:    0.5.4
 */